package com.ydh.couponstao.smallwidget;

import android.Manifest;
import android.appwidget.AppWidgetManager;
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
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.ydh.couponstao.R;
import com.ydh.couponstao.common.bases.BaseActivity;
import com.ydh.couponstao.utils.CommonUtil;
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
            //Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=2019072665939857&page=pages%2Fside-code%2Fside-code&query=f=1&m=NlxwFa2pjiTRm6RXo5q4OQZFSgdsCCVTd073S006ueObkNt629ISHyDD2LEHPfOb/n7a2G5Eq/V7NhOwqSeYpE7+ssjCuqQ2mftqs58076E=&qrcodeType=80");
            // ClipboardUtils.setClipboardNo("http://qrcode.sh.gov.cn/enterprise/scene?f=1&m=NlxwFa2pjiTRm6RXo5q4OQZFSgdsCCVTd073S006ueObkNt629ISHyDD2LEHPfOb%2Fn7a2G5Eq%2FV7NhOwqSeYpE7%2BssjCuqQ2mftqs58076E%3D&qrcodeType=80");

            //Uri uri = Uri.parse("alipays://platformapi/startapp?saId=10000007&qrcode=http%3A%2F%2Fqrcode.sh.gov.cn%2Fenterprise%2Fscene%3Ff%3D1%26m%3DNlxwFa2pjiTRm6RXo5q4OQZFSgdsCCVTd073S006ueObkNt629ISHyDD2LEHPfOb%2Fn7a2G5Eq%2FV7NhOwqSeYpE7%2BssjCuqQ2mftqs58076E%3D%26qrcodeType%3D80");
            Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=2019072665939857&url=http://qrcode.sh.gov.cn/enterprise/scene?f=1&m=EcPfEFNldeTEhzneoJ27FKpn7alUgJ2e6H8Nxvhk5iDGZvJZzvzRStYjesV%2Fj%2FsKWfwC4Q8PZH8utHsMtKJNt%2F1KT01n3VyzEwLsjZJCFvE%3D&qrcodeType=80");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            //intent.putExtra("query", "f=1&m=NlxwFa2pjiTRm6RXo5q4OQZFSgdsCCVTd073S006ueObkNt629ISHyDD2LEHPfOb/n7a2G5Eq/V7NhOwqSeYpE7+ssjCuqQ2mftqs58076E=&qrcodeType=80");
            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(this, "打开失败，请检查是否安装了支付宝", Toast.LENGTH_SHORT).show();

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addCommonWidget(View view) {
        addSmallWidget(CommonWidget.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addNewAppWidget(View view) {
        addSmallWidget(NewAppWidget.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addMusicWidget(View view) {
        addSmallWidget(MusicWidget.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addSmallWidget(Class<?> widgetClass) {
        AppWidgetManager instance = AppWidgetManager.getInstance(mContext);
        ComponentName componentName = new ComponentName(mContext, widgetClass);
        int[] appWidgetIds = instance.getAppWidgetIds(componentName);
        if (appWidgetIds != null && appWidgetIds.length > 0) {
            CommonUtil.showToast("组件已经添加过");
            return;
        }
        if (instance.isRequestPinAppWidgetSupported()) {
            instance.requestPinAppWidget(componentName, null, null);
        }

    }
}