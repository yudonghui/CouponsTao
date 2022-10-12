package com.ydh.couponstao.services;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.os.Build;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.RequiresApi;

import com.ydh.couponstao.utils.CommonUtil;


/**
 * Created by ydh on 2022/9/23
 */
public class BaseService extends AccessibilityService {

    protected int dp180;
    protected int dp300;

    @Override
    public void onCreate() {
        super.onCreate();
        dp180 = CommonUtil.dp2px(180);
        dp300 = CommonUtil.dp2px(300);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }

    @RequiresApi(Build.VERSION_CODES.N)
    protected void autoSlideView(float startX, float startY, float endX, float endY) {
        android.graphics.Path path = new android.graphics.Path();
        path.moveTo(startX, startY);
        path.lineTo(endX, endY);
        GestureDescription gestureDescription = new GestureDescription.Builder()
                .addStroke(new GestureDescription.StrokeDescription(path, 0, 800))
                .build();
        dispatchGesture(gestureDescription, new GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
            }

            @Override
            public void onCancelled(GestureDescription gestureDescription) {
                super.onCancelled(gestureDescription);
            }
        }, null);
    }

    @RequiresApi(Build.VERSION_CODES.N)
    protected void autoClickView(float startX, float startY) {
        android.graphics.Path path = new android.graphics.Path();
        path.moveTo(startX, startY);
        GestureDescription gestureDescription = new GestureDescription.Builder()
                .addStroke(new GestureDescription.StrokeDescription(path, 0, 5))
                .build();
        dispatchGesture(gestureDescription, new GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
            }

            @Override
            public void onCancelled(GestureDescription gestureDescription) {
                super.onCancelled(gestureDescription);
            }
        }, null);
    }

    protected void autoBackView() {
        performGlobalAction(GLOBAL_ACTION_BACK);
    }
}
