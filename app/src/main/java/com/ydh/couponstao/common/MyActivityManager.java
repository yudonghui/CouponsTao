package com.ydh.couponstao.common;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * Created by ydh on 2021/4/26
 */
public class MyActivityManager {
    private static MyActivityManager mInstance = new MyActivityManager();

    private WeakReference<Activity> sCurrentActivityWeakRef;

    public MyActivityManager(){

    }

    public static MyActivityManager getInstance(){
        return mInstance;
    }

    public Activity getCurrentActivity(){
        Activity currentActivity = null;
        if(sCurrentActivityWeakRef != null){
            currentActivity = sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }

    public void setCurrentActivity(Activity activity){
        sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
    }

}
