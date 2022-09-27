package com.ydh.couponstao.smallwidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.ydh.couponstao.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ydh on 2022/7/26
 */
public class WidgetTimerService extends Service {
    /**
     * 周期性更新 widget 的周期
     */
    private static final int UPDATE_TIME = 1000;
    private Timer mTimer;
    private TimerTask mTimerTask;

    @Override
    public void onCreate() {
        // 每经过指定时间，发送一次广播
        Log.e("启动服务", "onCreate");
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                //Intent updateIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
           /*     Intent updateIntent = new Intent(NewAppWidget.TIME_TASK_ACTION);
                sendBroadcast(updateIntent);*/
                RemoteViews views = new RemoteViews("com.mylike.smallwidget", R.layout.new_app_widget);
                Log.e("NewAppWidget", "run");
                Lunar lunar = new Lunar(Calendar.getInstance());
                views.setTextViewText(R.id.tv_time, getCurrentDate());
                views.setTextViewText(R.id.tv_calendar, lunar.toString());
                views.setTextViewText(R.id.tv_animal, lunar.cyclical() + "    " + lunar.animalsYear());

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getBaseContext());
                appWidgetManager.updateAppWidget(new ComponentName(getBaseContext(), NewAppWidget.class), views);
            }
        };
        mTimer.schedule(mTimerTask, UPDATE_TIME, UPDATE_TIME);
        super.onCreate();
    }

    public static String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String sim = dateFormat.format(date);
        return sim;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override

    public void onDestroy() {
        super.onDestroy();
        mTimerTask.cancel();
        mTimer.cancel();
    }
}
