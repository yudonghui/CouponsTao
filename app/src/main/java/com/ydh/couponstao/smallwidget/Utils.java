package com.ydh.couponstao.smallwidget;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import androidx.core.app.NotificationManagerCompat;

/**
 * Created by ydh on 2022/7/28
 */
public class Utils {
    /**
     * 是否开启通知权限
     *
     * @param context
     * @return
     */
    public static boolean isNotificationListenerEnabled(Context context) {
        NotificationManagerCompat notification = NotificationManagerCompat.from(context);
        return notification.areNotificationsEnabled();
    }

    /**
     * 开启通知权限
     */
    public static void openNotificationListenSettings(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
            intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
