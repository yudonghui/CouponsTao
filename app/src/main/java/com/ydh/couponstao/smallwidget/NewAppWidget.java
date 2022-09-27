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
import android.os.Bundle;
import android.provider.Settings;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of App Widget functionality.
 * 参考资料：https://blog.csdn.net/hwb04160011/article/details/119728412
 * https://www.jianshu.com/p/d16363d9efd9
 */
public class NewAppWidget extends AppWidgetProvider {
    public static final String WECHAT_APP_PACKAGE = "com.tencent.mm"; // 微信
    public static final String WECHAT_LAUNCHER_UI_CLASS = "com.tencent.mm.ui.LauncherUI"; // 微信
    public static final String WECHAT_OPEN_SCANER_NAME = "LauncherUI.From.Scaner.Shortcut"; // 微信
    public static final String TIME_TASK_ACTION = "time_task_action"; // 定时任务
    //系统更新广播
    public static final String APPWIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";
    //自定义的刷新广播
    private static final String REFRESH_ACTION = "android.appwidget.action.REFRESH";
    //定期任务的name
    private static final String WORKER_NAME = "TestWorker";
    private Intent mIntent;


    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {
        Log.e("NewAppWidget：", "updateAppWidget");
        CharSequence widgetText = context.getString(R.string.app_name);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);
        if (!isAliPayInstalled(context)) {
            views.setTextViewText(R.id.tv_alipay, "未安装支付宝");
            views.setTextViewText(R.id.tv_alipay_pay, "未安装支付宝");
        }
        if (!isWxInstall(context)) {
            views.setTextViewText(R.id.tv_wx_scan, "未安装微信");
            views.setTextViewText(R.id.tv_wx_pay, "未安装微信");
        }
       /* Lunar lunar = new Lunar(Calendar.getInstance());
        views.setTextViewText(R.id.tv_time, getCurrentDate());
        views.setTextViewText(R.id.tv_calendar, lunar.toString());
        views.setTextViewText(R.id.tv_animal, lunar.cyclical() + "    " + lunar.animalsYear());*/

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


  /*      Uri uriWx = Uri.parse("weixin://");
        Intent intentWx = new Intent(Intent.ACTION_VIEW, uriWx);
        PendingIntent pendingIntentWx = PendingIntent.getActivity(context, 0, intentWx, 0);
        views.setOnClickPendingIntent(R.id.ll_wx_pay, pendingIntentWx);*/


        Intent intentWx = new Intent(Intent.ACTION_VIEW);
        intentWx.putExtra(WECHAT_OPEN_SCANER_NAME, true);
        intentWx.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ComponentName componentNameWx = new ComponentName(WECHAT_APP_PACKAGE, WECHAT_LAUNCHER_UI_CLASS);
        intentWx.setComponent(componentNameWx);
        //PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, R.id.ll_wx_scan, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
        @SuppressLint("WrongConstant") PendingIntent pendingIntentWx = PendingIntent.getActivity(context, 0, intentWx, 1);
        views.setOnClickPendingIntent(R.id.ll_wx_pay, pendingIntentWx);


        views.setOnClickPendingIntent(R.id.tv_time, getPendIntent(context));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    /**
     * 每次窗口小部件被更新都调用一次该方法
     */
    @Override
    public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.e("NewAppWidget：", "onUpdate");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private PendingIntent getPendIntent(Context context) {
        //点击事件
        Intent intent = new Intent();
        intent.setClass(context, NewAppWidget.class);
        intent.setAction(REFRESH_ACTION);
        //设置pendingIntent
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        return pendingIntent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        final String action = intent.getAction();
        Log.e("NewAppWidget：", "onReceive-------" + action);
        //接收主动点击刷新广播/系统刷新广播
        if (TextUtils.equals(intent.getAction(), REFRESH_ACTION)
                || TextUtils.equals(intent.getAction(), APPWIDGET_UPDATE)) {
            //执行一次任务
            WorkManager.getInstance(context).enqueue(OneTimeWorkRequest.from(TestWorker.class));
        }
        if (TIME_TASK_ACTION.equals(action)) {
            RemoteViews views = new RemoteViews("com.mylike.smallwidget", R.layout.new_app_widget);

            Lunar lunar = new Lunar(Calendar.getInstance());
            views.setTextViewText(R.id.tv_time, System.currentTimeMillis() + "");
            views.setTextViewText(R.id.tv_calendar, lunar.toString());
            views.setTextViewText(R.id.tv_animal, lunar.cyclical() + "    " + lunar.animalsYear());

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(new ComponentName(context, NewAppWidget.class), views);
        } else if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)) {
            /*mIntent = new Intent(context, WidgetTimerService.class);
            context.startService(mIntent);*/
        }
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


    /**
     * 当最后一个该窗口小部件删除时调用该方法
     */

    @Override

    public void onDisabled(Context context) {
        Log.e("NewAppWidget：", "onDisabled");
        super.onDisabled(context);
        //停止任务
        WorkManager.getInstance(context).cancelUniqueWork(WORKER_NAME);
    }

    /**
     * 周期性更新 widget 的周期
     */
    private static final int UPDATE_TIME = 5000;
    private Timer mTimer;
    private TimerTask mTimerTask;

    /**
     * 当该窗口小部件第一次添加到桌面时调用该方法
     */

    @Override

    public void onEnabled(final Context context) {
        Log.e("NewAppWidget：", "onEnabled");
        super.onEnabled(context);
    /*    mIntent = new Intent(context, WidgetTimerService.class);
        context.startService(mIntent);*/
        //开始定时工作,间隔15分钟刷新一次
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(TestWorker.class,
                PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
                .setConstraints(new Constraints.Builder()
                        .setRequiresCharging(true)
                        .build())
                .build();
        WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(WORKER_NAME, ExistingPeriodicWorkPolicy.KEEP, workRequest);
      /*  // 每经过指定时间，发送一次广播
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                onReceive(context, new Intent(TIME_TASK_ACTION));
            }
        };
        mTimer.schedule(mTimerTask, UPDATE_TIME, UPDATE_TIME);*/
    }

    /**
     * 每删除一次窗口小部件就调用一次
     */

    @Override

    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.e("NewAppWidget：", "onDeleted");
        super.onDeleted(context, appWidgetIds);
        mIntent = new Intent(context, WidgetTimerService.class);
        context.stopService(mIntent);
    }

    /**
     * 当小部件大小改变时
     */

    @Override

    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        Log.e("NewAppWidget：", "onAppWidgetOptionsChanged大小改变了");
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);

    }

    /**
     * 当小部件从备份恢复时调用该方法
     */

    @Override

    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {

        super.onRestored(context, oldWidgetIds, newWidgetIds);

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

