package com.ydh.couponstao.smallwidget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

/**
 * Created by ydh on 2022/7/26
 */
public class WidgetController {
    private String TAG = getClass().getSimpleName();

    /**
     * 更新小组件，触发组件的onUpdate
     */
    public void update(Context context) {
        Intent intent = new Intent(context, NewAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        Bundle bundle = new Bundle();
        bundle.putIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS, getAppWidgetIds(context));
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }

    /**
     * 添加到主屏幕
     */
    @RequiresApi(Build.VERSION_CODES.O)
    public void addToMainScreen(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName myProvider = new ComponentName(context, NewAppWidget.class);

        int[] appWidgetIds = getAppWidgetIds(context);
        if (appWidgetIds != null && appWidgetIds.length > 0) {
            Toast.makeText(context, "组件已经存在", Toast.LENGTH_SHORT).show();
            return;
        }
        if (appWidgetManager.isRequestPinAppWidgetSupported()) {
            // Create the PendingIntent object only if your app needs to be notified
            // that the user allowed the widget to be pinned. Note that, if the pinning
            // operation fails, your app isn't notified. This callback receives the ID
            // of the newly-pinned widget (EXTRA_APPWIDGET_ID).
//            val successCallback = PendingIntent.getBroadcast(
//                /* context = */ context,
//                /* requestCode = */ 0,
//                /* intent = */ null,
//            /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT)

            appWidgetManager.requestPinAppWidget(myProvider, null, null);
        }
    }

    private int[] getAppWidgetIds(Context context) {
        AppWidgetManager awm = AppWidgetManager.getInstance(context);
        int[] appWidgetIDs = awm.getAppWidgetIds(new ComponentName(context, NewAppWidget.class));
        return appWidgetIDs;
    }
}
