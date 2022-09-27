package com.ydh.couponstao.smallwidget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.ydh.couponstao.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of App Widget functionality.
 */
public class CommonWidget extends AppWidgetProvider {
    public static final String WECHAT_APP_PACKAGE = "com.tencent.mm"; // 微信
    public static final String WECHAT_LAUNCHER_UI_CLASS = "com.tencent.mm.ui.LauncherUI"; // 微信
    public static final String WECHAT_OPEN_SCANER_NAME = "LauncherUI.From.Scaner.Shortcut"; // 微信
    public static final String TIME_TASK_ACTION = "time_task_action"; // 定时任务

    //自定义的刷新广播
    private static final String REFRESH_ACTION = "android.appwidget.action.REFRESH";
    //定期任务的name
    private static final String WORKER_NAME = "CommonWorker";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Log.e("CommonWidget：", "updateAppWidget");
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.common_widget);
        if (!isAliPayInstalled(context)) {
            views.setTextViewText(R.id.tv_alipay, "未安装支付宝");
            views.setTextViewText(R.id.tv_alipay_pay, "未安装支付宝");
        }
        if (!isWxInstall(context)) {
            views.setTextViewText(R.id.tv_wx_scan, "未安装微信");
            views.setTextViewText(R.id.tv_wx_pay, "未安装微信");
        }

        Uri uriAliScan = Uri.parse("alipayqr://platformapi/startapp?saId=10000007");
        Intent intentAliScan = new Intent(Intent.ACTION_VIEW, uriAliScan);
        PendingIntent pendingIntentAliScan = PendingIntent.getActivity(context, 0, intentAliScan, 0);
        views.setOnClickPendingIntent(R.id.ll_alipay_scan, pendingIntentAliScan);


        Uri uriAliPay = Uri.parse("alipayqr://platformapi/startapp?saId=20000056");
        Intent intentAliPay = new Intent(Intent.ACTION_VIEW, uriAliPay);
        PendingIntent pendingIntentAliPay = PendingIntent.getActivity(context, 0, intentAliPay, 0);
        views.setOnClickPendingIntent(R.id.ll_alipay_pay, pendingIntentAliPay);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra(WECHAT_OPEN_SCANER_NAME, true);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ComponentName componentName = new ComponentName(WECHAT_APP_PACKAGE, WECHAT_LAUNCHER_UI_CLASS);
        intent.setComponent(componentName);
        //PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, R.id.ll_wx_scan, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.ll_wx_scan, pendingIntent);

        Intent intentWx = new Intent(Intent.ACTION_VIEW);
        intentWx.putExtra(WECHAT_OPEN_SCANER_NAME, true);
        intentWx.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ComponentName componentNameWx = new ComponentName(WECHAT_APP_PACKAGE, WECHAT_LAUNCHER_UI_CLASS);
        intentWx.setComponent(componentNameWx);
        //PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, R.id.ll_wx_scan, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        @SuppressLint("WrongConstant") PendingIntent pendingIntentWx = PendingIntent.getActivity(context, 0, intentWx, 1);
        views.setOnClickPendingIntent(R.id.ll_wx_pay, pendingIntentWx);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.e("CommonWidget：", "onUpdate");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        final String action = intent.getAction();
        Log.e("CommonWidget：", "onReceive-------" + action);
        //接收主动点击刷新广播/系统刷新广播
        if (TextUtils.equals(action, REFRESH_ACTION)
                || TextUtils.equals(action, AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            //执行一次任务
            WorkManager.getInstance(context).enqueue(OneTimeWorkRequest.from(CommonWorker.class));
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        //开始定时工作,间隔15分钟刷新一次
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(CommonWorker.class,
                PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
                .setConstraints(new Constraints.Builder()
                        .setRequiresCharging(true)
                        .build())
                .build();
        WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(WORKER_NAME, ExistingPeriodicWorkPolicy.KEEP, workRequest);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        //停止任务
        WorkManager.getInstance(context).cancelUniqueWork(WORKER_NAME);
    }

    /**
     * 是否安装了微信
     */
    private static boolean isWxInstall(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (int i = 0; i < installedPackages.size(); i++) {
                String packageName = installedPackages.get(i).packageName;
                if (packageName.equals(WECHAT_APP_PACKAGE)) {//微信
                    return true;
                }

            }
        }
        return false;
    }

    /**
     * 判断 用户是否安装QQ客户端
     */

    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (int i = 0; i < installedPackages.size(); i++) {
                String pn = installedPackages.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * 是否安装了支付宝
     */
    public static boolean isAliPayInstalled(Context context) {
        Uri uri = Uri.parse("alipays://platformapi/startApp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return componentName != null;

    }

    /**
     * sina
     * <p>
     * 判断是否安装新浪微博
     */

    public static boolean isSinaInstalled(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (installedPackages != null) {
            for (int i = 0; i < installedPackages.size(); i++) {
                String pn = installedPackages.get(i).packageName;
                if (pn.equals("com.sina.weibo")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 更新组件
     */
    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, int imgRes) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        //这里设置更新的控件内容，例如 views.setTextViewText(R.id.appwidget_text, widgetText);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String sim = dateFormat.format(date);
        return sim;
    }
}

