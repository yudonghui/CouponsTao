package com.ydh.couponstao;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshInitializer;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.UpdateError;
import com.xuexiang.xupdate.listener.OnUpdateFailureListener;
import com.ydh.couponstao.common.Constant;
import com.ydh.couponstao.common.MyActivityManager;
import com.ydh.couponstao.common.updateapp.OKHttpUpdateHttpService;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

import static com.xuexiang.xupdate.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION;

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
        initUpdate();
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

    private void initUpdate() {
        XUpdate.get()
                .debug(true)
                .isWifiOnly(false)                                               //默认设置true只在wifi下检查版本更新
                .isGet(true)                                                    //默认设置使用get请求检查版本
                .isAutoMode(false)                                              //默认设置非自动模式，可根据具体使用配置
                // .param("versionCode", UpdateUtils.getVersionCode(this))  //设置默认公共请求参数
                //  .param("appKey", getPackageName())
                .setOnUpdateFailureListener(new OnUpdateFailureListener() { //设置版本更新出错的监听
                    @Override
                    public void onFailure(UpdateError error) {
                        if (error.getCode() != CHECK_NO_NEW_VERSION) {    //对不同错误进行处理
                            if (Constant.ISSUE == true)
                                Toast.makeText(getContext(), "版本" + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .supportSilentInstall(false)                                     //设置是否支持静默安装，默认是true
                .setIUpdateHttpService(new OKHttpUpdateHttpService())           //这个必须设置！实现网络请求功能。
                .init(this);                                          //这个必须初始化
    }

}
