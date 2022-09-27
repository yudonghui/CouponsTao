package com.ydh.couponstao.smallwidget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.ydh.couponstao.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ydh on 2022/8/4
 */
public class TestWorker extends Worker {

    public TestWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        //模拟耗时/网络请求操作
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //刷新widget
        updateWidget(getApplicationContext());

        return Result.success();
    }

    /**
     * 刷新widget
     */
    private void updateWidget(Context context) {
        //只能通过远程对象来设置appwidget中的控件状态
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        //通过远程对象修改textview
        Lunar lunar = new Lunar(Calendar.getInstance());
        remoteViews.setTextViewText(R.id.tv_time, getCurrentDate());
        remoteViews.setTextViewText(R.id.tv_calendar, lunar.toString());
        remoteViews.setTextViewText(R.id.tv_animal, lunar.cyclical() + "    " + lunar.animalsYear());
        //获得appwidget管理实例，用于管理appwidget以便进行更新操作
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        //获得所有本程序创建的appwidget
        ComponentName componentName = new ComponentName(context, NewAppWidget.class);
        //更新appwidget
        appWidgetManager.updateAppWidget(componentName, remoteViews);
    }

    public static String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String sim = dateFormat.format(date);
        return sim;
    }
}
