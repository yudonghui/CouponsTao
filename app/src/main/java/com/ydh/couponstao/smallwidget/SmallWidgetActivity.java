package com.ydh.couponstao.smallwidget;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.RemoteController;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ydh.couponstao.R;
import com.ydh.couponstao.common.bases.BaseActivity;
import com.ydh.couponstao.utils.LogUtils;

import java.util.List;

public class SmallWidgetActivity extends BaseActivity {

    private TextView mTvContent;
    private Button mBtPlayPause;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_widget);
        mTvContent = findViewById(R.id.tv_content);
        mBtPlayPause = findViewById(R.id.bt_play_pause);
        String[] aa = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.MEDIA_CONTENT_CONTROL,
        };
        requestPermission(aa, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
                return;
            }
        }
        if (!Utils.isNotificationListenerEnabled(this)) {//是否开启通知使用权
            Utils.openNotificationListenSettings(this);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (Utils.isNotificationListenerEnabled(this)) {//开启通知使用权后,重启服务再走一下onStartCommand方法,使设置有效
            startService(new Intent(this, MediaControllerService.class));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addWidget(View view) {
        if (isAccessibilitySettingsOn(SmallWidgetActivity.this)) {
            Intent intent = new Intent(SmallWidgetActivity.this, PayAccessibility.class);
            startService(intent);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent1 = new Intent();
                    ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                    intent1.setAction(Intent.ACTION_MAIN);
                    intent1.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent1.setComponent(cmp);
                    startActivity(intent1);
                }
            }, 200);
            ;
        } else {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            finish();
        }
    }

    /**
     * 是否安装了微信
     */
    private static boolean isWxInstall(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        if (installedPackages != null) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < installedPackages.size(); i++) {
                String packageName = installedPackages.get(i).applicationInfo.packageName;
                Log.e("包名：", packageName);
                stringBuffer.append(packageName + "\n");

                if (packageName.equals("com.tencent.mm")) {//微信
                    return true;
                }
            }
            FileUtils.writeSD(stringBuffer.toString());
        }
        return false;
    }

    /**
     * 辅助服务是否开启
     *
     * @param context 活动context
     * @return
     */
    public static boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        final String service = context.getPackageName() + "/" + PayAccessibility.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getApplicationContext().getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(context.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private MediaControllerService mediaControllerService;


    public void previous(View view) {
        if (mediaControllerService != null)
            mediaControllerService.sendMusicKeyEvent(KeyEvent.KEYCODE_MEDIA_PREVIOUS);
    }

    public void playOrPause(View view) {
        if (mediaControllerService != null)
            mediaControllerService.sendMusicKeyEvent(KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
    }

    public void next(View view) {
        if (mediaControllerService != null)
            mediaControllerService.sendMusicKeyEvent(KeyEvent.KEYCODE_MEDIA_NEXT);
    }

    public void bindService(View view) {
        bindService(new Intent(this, MediaControllerService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LogUtils.e("服务：onServiceConnected  " + name);
                MediaControllerService.MyBinder rcBinder = (MediaControllerService.MyBinder) service;
                mediaControllerService = rcBinder.getService();
                mediaControllerService.registerRemoteController();
                mediaControllerService.setExternalClientUpdateListener(new RemoteController.OnClientUpdateListener() {
                    @Override
                    public void onClientChange(boolean clearing) {

                    }

                    @Override
                    public void onClientPlaybackStateUpdate(int state) {

                    }

                    @Override
                    public void onClientPlaybackStateUpdate(int state, long stateChangeTimeMs, long currentPosMs, float speed) {
                        if (state == 2) {
                            mBtPlayPause.setText("播放");
                        } else if (state == 3) {
                            mBtPlayPause.setText("暂停");
                        }
                    }

                    @Override
                    public void onClientTransportControlUpdate(int transportControlFlags) {
                        LogUtils.e("服务：onClientTransportControlUpdate  " + transportControlFlags);
                    }

                    @Override
                    public void onClientMetadataUpdate(RemoteController.MetadataEditor metadataEditor) {
                        String artist = metadataEditor.getString(MediaMetadataRetriever.METADATA_KEY_ARTIST, "null");
                        String album = metadataEditor.getString(MediaMetadataRetriever.METADATA_KEY_ALBUM, "null");
                        String title = metadataEditor.getString(MediaMetadataRetriever.METADATA_KEY_TITLE, "null");
                        Long duration = metadataEditor.getLong(MediaMetadataRetriever.METADATA_KEY_DURATION, -1);
                        Bitmap defaultCover = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_compass);
                        Bitmap bitmap = metadataEditor.getBitmap(RemoteController.MetadataEditor.BITMAP_KEY_ARTWORK, defaultCover);
                        mTvContent.setText("artist: " + artist + "album: " + album + "title: " + title + "duration: " + duration);
                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                LogUtils.e("服务：onServiceDisconnected  " + name);
            }
        }, Context.BIND_AUTO_CREATE);
    }

    public void clickAlipay(View view) {
        try {
            Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=2019072665939857&page=pages%2Fside-code%2Fside-code");
           // ClipboardUtils.setClipboardNo("http://qrcode.sh.gov.cn/enterprise/scene?f=1&m=NlxwFa2pjiTRm6RXo5q4OQZFSgdsCCVTd073S006ueObkNt629ISHyDD2LEHPfOb%2Fn7a2G5Eq%2FV7NhOwqSeYpE7%2BssjCuqQ2mftqs58076E%3D&qrcodeType=80");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.putExtra("query", "f=1&m=NlxwFa2pjiTRm6RXo5q4OQZFSgdsCCVTd073S006ueObkNt629ISHyDD2LEHPfOb%2Fn7a2G5Eq%2FV7NhOwqSeYpE7%2BssjCuqQ2mftqs58076E%3D&qrcodeType=80");
            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(this, "打开失败，请检查是否安装了支付宝", Toast.LENGTH_SHORT).show();

        }
    }
    public void clickWx(View view){

        String appId = ""; // 填移动应用的 AppId，一堆的审核。
        IWXAPI api = WXAPIFactory.createWXAPI(this, appId);

        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = "gh_d4acc9de8978"; // 填小程序原始id
       // req.path = "pages%2Fside-code%2Fside-code";                  ////拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
        api.sendReq(req);
    }
}