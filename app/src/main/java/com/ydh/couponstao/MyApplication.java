package com.ydh.couponstao;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.ydh.couponstao.common.MyActivityManager;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * Created by ydh on 2022/8/15
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        RetrofitUrlManager.getInstance().setDebug(true);
        //监听activity生命周期，方便回头获取栈顶的activity
        registerActivity();
    }

    public static Context getContext() {
        return context;
    }

    private void registerActivity() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                MyActivityManager.getInstance().setCurrentActivity(activity);

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    //初始化列表上下拉刷新加载控件样式
    static {
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
                ClassicsHeader classicsHeader = new ClassicsHeader(context);
                classicsHeader.setEnableLastTime(false);
                classicsHeader.setPrimaryColor(Color.TRANSPARENT);
//                classicsHeader.setPrimaryColor(context.getResources().getColor(R.color.green_50));
                classicsHeader.setAccentColor(context.getResources().getColor(R.color.color_theme));
                classicsHeader.setDrawableMarginRight(10);
                classicsHeader.setSpinnerStyle(SpinnerStyle.Scale);

                layout.setRefreshHeader(classicsHeader);
                layout.setPrimaryColorsId(R.color.color_theme, R.color.color_white);//全局设置主题颜色
                layout.setHeaderHeight(30);
                layout.setFooterHeight(40);
            }
        });
    }
}
