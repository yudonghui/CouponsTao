package com.ydh.couponstao.smallwidget;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.RemoteController;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.ydh.couponstao.R;

import java.util.List;

import static android.app.Notification.VISIBILITY_PUBLIC;
import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;

/**
 * Created by ydh on 2022/7/28
 * 监听通知栏android控制媒体播放暂停的广播
 */
@SuppressLint("OverrideAbstract")
public class MediaControllerService extends NotificationListenerService implements RemoteController.OnClientUpdateListener {
    private static String MY_TAG = "Service日志";

    private MyBinder mBinder = new MyBinder();

    public class MyBinder extends Binder {
        public MediaControllerService getService() {
            return MediaControllerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public MediaControllerService() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(MY_TAG, "MediaControllerService onCreate");
        initNotify("MediaController", "MediaControllerService");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        Log.e(MY_TAG, "MediaControllerService onStartCommand");
        if (Utils.isNotificationListenerEnabled(this)) {//开启通知使用权后再设置,否则会报权限错误
            initMediaSessionManager();
            registerRemoteController();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Log.e(MY_TAG, "收到包名：" + sbn.getPackageName());
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Log.e(MY_TAG, "移除包名：" + sbn.getPackageName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(MY_TAG, "MediaControllerService onDestroy");
    }

    //////////////////////////////////MediaController获取音乐信息/////////////////////////////////////
    private List<MediaController> mActiveSessions;
    private MediaController.Callback mSessionCallback;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initMediaSessionManager() {
        MediaSessionManager mediaSessionManager = (MediaSessionManager) getSystemService(MEDIA_SESSION_SERVICE);
        ComponentName localComponentName = new ComponentName(this, MediaControllerService.class);
        mediaSessionManager.addOnActiveSessionsChangedListener(new MediaSessionManager.OnActiveSessionsChangedListener() {
            @Override
            public void onActiveSessionsChanged(@Nullable final List<MediaController> controllers) {
                for (MediaController mediaController : controllers) {
                    String packageName = mediaController.getPackageName();
                    Log.e(MY_TAG, "MyApplication onActiveSessionsChanged mediaController.getPackageName: " + packageName);
                    synchronized (this) {
                        mActiveSessions = controllers;
                        registerSessionCallbacks();
                    }
                }
            }
        }, localComponentName);
        synchronized (this) {
            mActiveSessions = mediaSessionManager.getActiveSessions(localComponentName);
            registerSessionCallbacks();
        }
    }

    private void registerSessionCallbacks() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (MediaController controller : mActiveSessions) {
                if (mSessionCallback == null) {
                    mSessionCallback = new MediaController.Callback() {
                        @Override
                        public void onSessionEvent(@NonNull String event, @Nullable Bundle extras) {
                            super.onSessionEvent(event, extras);
                            Log.e(MY_TAG, "onSessionEvent:");
                        }

                        @Override
                        public void onExtrasChanged(@Nullable Bundle extras) {
                            super.onExtrasChanged(extras);
                            Log.e(MY_TAG, "onExtrasChanged:");
                        }

                        @Override
                        public void onQueueChanged(@Nullable List<MediaSession.QueueItem> queue) {
                            super.onQueueChanged(queue);
                            Log.e(MY_TAG, "onQueueChanged:");
                        }

                        @Override
                        public void onAudioInfoChanged(MediaController.PlaybackInfo info) {
                            super.onAudioInfoChanged(info);
                            Log.e(MY_TAG, "onAudioInfoChanged:");
                        }

                        @Override
                        public void onMetadataChanged(MediaMetadata metadata) {
                            if (metadata != null) {
                                String trackName =
                                        metadata.getString(MediaMetadata.METADATA_KEY_TITLE);
                                String artistName =
                                        metadata.getString(MediaMetadata.METADATA_KEY_ARTIST);
                                String albumArtistName =
                                        metadata.getString(MediaMetadata.METADATA_KEY_ALBUM_ARTIST);
                                String albumName =
                                        metadata.getString(MediaMetadata.METADATA_KEY_ALBUM);
                                Log.e(MY_TAG + "onMetadataChanged", "---------------------------------");
                                Log.e(MY_TAG + "onMetadataChanged", "| trackName: " + trackName);
                                Log.e(MY_TAG + "onMetadataChanged", "| artistName: " + artistName);
                                Log.e(MY_TAG + "onMetadataChanged", "| albumArtistName: " + albumArtistName);
                                Log.e(MY_TAG + "onMetadataChanged", "| albumName: " + albumName);
                                Log.e(MY_TAG + "onMetadataChanged", "---------------------------------");
                                Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                                intent.putExtra("trackName", trackName);
                                intent.putExtra("artistName", artistName);
                                intent.putExtra("albumArtistName", albumArtistName);
                                intent.putExtra("albumName", albumName);
                                sendBroadcast(intent);
                            }
                        }

                        @Override
                        public void onPlaybackStateChanged(PlaybackState state) {
                            if (state != null) {
                                boolean isPlaying = state.getState() == PlaybackState.STATE_PLAYING;
                                Log.e(MY_TAG, "MediaController.Callback onPlaybackStateChanged isPlaying: " + isPlaying);
                            }
                        }
                    };
                }
                controller.registerCallback(mSessionCallback);
            }
        }
    }

    //////////////////////////////////RemoteController获取音乐信息/////////////////////////////////////
    public RemoteController remoteController;

    public void registerRemoteController() {
        remoteController = new RemoteController(this, this);
        boolean registered;
        try {
            registered = ((AudioManager) getSystemService(AUDIO_SERVICE)).registerRemoteController(remoteController);
        } catch (NullPointerException e) {
            registered = false;
        }
        if (registered) {
            try {
                remoteController.setArtworkConfiguration(100, 100);
                remoteController.setSynchronizationMode(RemoteController.POSITION_SYNCHRONIZATION_CHECK);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    private RemoteController.OnClientUpdateListener mExternalClientUpdateListener;

    public void setExternalClientUpdateListener(RemoteController.OnClientUpdateListener externalClientUpdateListener) {
        mExternalClientUpdateListener = externalClientUpdateListener;
    }

    @Override
    public void onClientChange(boolean clearing) {
        Log.e(MY_TAG, "registerRemoteController --- clearing == " + clearing);
        if (mExternalClientUpdateListener != null)
            mExternalClientUpdateListener.onClientChange(clearing);
    }

    @Override
    public void onClientPlaybackStateUpdate(int state) {
        Log.e(MY_TAG, "registerRemoteController --- state1 == " + state);
        if (mExternalClientUpdateListener != null)
            mExternalClientUpdateListener.onClientPlaybackStateUpdate(state);
    }

    @Override
    public void onClientPlaybackStateUpdate(int state, long stateChangeTimeMs, long currentPosMs, float speed) {
        Log.e(MY_TAG, "registerRemoteController --- state2 == " + state + "  stateChangeTimeMs == " + stateChangeTimeMs + "  currentPosMs == " + currentPosMs + "  speed == " + speed);
        if (mExternalClientUpdateListener != null)
            mExternalClientUpdateListener.onClientPlaybackStateUpdate(state, stateChangeTimeMs, currentPosMs, speed);
    }

    @Override
    public void onClientTransportControlUpdate(int transportControlFlags) {
        Log.e(MY_TAG, "registerRemoteController --- transportControlFlags == " + transportControlFlags);
        if (mExternalClientUpdateListener != null)
            mExternalClientUpdateListener.onClientTransportControlUpdate(transportControlFlags);
    }

    @Override
    public void onClientMetadataUpdate(RemoteController.MetadataEditor metadataEditor) {
        String artist = metadataEditor.
                getString(MediaMetadataRetriever.METADATA_KEY_ARTIST, "null");
        String album = metadataEditor.
                getString(MediaMetadataRetriever.METADATA_KEY_ALBUM, "null");
        String title = metadataEditor.
                getString(MediaMetadataRetriever.METADATA_KEY_TITLE, "null");
        Long duration = metadataEditor.
                getLong(MediaMetadataRetriever.METADATA_KEY_DURATION, -1);
        Bitmap defaultCover = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_compass);
        Bitmap bitmap = metadataEditor.
                getBitmap(RemoteController.MetadataEditor.BITMAP_KEY_ARTWORK, defaultCover);
        Log.e(MY_TAG, "registerRemoteController --- artist:" + artist + ", album:" + album + ", title:" + title + ", duration:" + duration);

        if (mExternalClientUpdateListener != null)
            mExternalClientUpdateListener.onClientMetadataUpdate(metadataEditor);
    }

    /**
     * 添加一个常驻通知
     *
     * @param title
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void initNotify(String title, String context) {
        String CHANNEL_ONE_ID = "1000";

        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher, null);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        //Bitmap bitmapIcon = BitmapUtils.getBitmapFromDrawable(drawable);

        Intent nfIntent = new Intent(this, SmallWidgetActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, nfIntent, 0);
        @SuppressLint("WrongConstant") NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getApplicationContext(), CHANNEL_ONE_ID)
                .setContentIntent(pendingIntent) // 设置PendingIntent
                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
                //.setLargeIcon(bitmapIcon)// 设置大图标
                .setContentTitle(title)
                .setContentText(context) // 设置上下文内容
                .setWhen(System.currentTimeMillis())// 设置该通知发生的时间
                .setVisibility(VISIBILITY_PUBLIC)// 锁屏显示全部通知
                //.setDefaults(Notification.DEFAULT_ALL)// //使用默认的声音、振动、闪光
                .setPriority(PRIORITY_HIGH);// 通知的优先级

        //----------------  新增代码 ------------------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //修改安卓8.1以上系统报错
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID, "app_service_notify", NotificationManager.IMPORTANCE_MIN);
            notificationChannel.enableLights(false);//如果使用中的设备支持通知灯，则说明此通知通道是否应显示灯
            notificationChannel.setShowBadge(false);//是否显示角标
            notificationChannel.enableVibration(false);//是否震动
            notificationChannel.setLockscreenVisibility(VISIBILITY_PUBLIC);//锁屏显示全部通知
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
            builder.setChannelId(CHANNEL_ONE_ID);
        }
        Notification notification = builder.build(); // 获取构建好的Notification
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        startForeground(1, notification);
    }

    public boolean sendMusicKeyEvent(int keyCode) {
        if (remoteController != null) {
            KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
            boolean down = remoteController.sendMediaKeyEvent(keyEvent);
            keyEvent = new KeyEvent(KeyEvent.ACTION_UP, keyCode);
            boolean up = remoteController.sendMediaKeyEvent(keyEvent);
            return down && up;
        }
        return false;
    }
}
