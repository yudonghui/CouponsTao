package com.ydh.couponstao.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ydh.couponstao.R;
import com.ydh.couponstao.activitys.AutoClickActivity;

/**
 * Date:2023/7/14
 * Time:16:23
 * author:ydh
 */
public class MediaService extends Service {
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "screen_capture_channel";

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(NOTIFICATION_ID, buildNotification());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Notification buildNotification() {
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "Screen Capture";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
            builder = new Notification.Builder(this, CHANNEL_ID);
        } else {
            builder = new Notification.Builder(this);
        }

        builder.setContentTitle("正在截屏")
                .setContentText("请勿关闭此通知")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true);

        Intent notificationIntent = new Intent(this, AutoClickActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentIntent(pendingIntent);
        return builder.build();
    }
}
