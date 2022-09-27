package com.ydh.couponstao.smallwidget;

import android.accessibilityservice.AccessibilityService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by ydh on 2022/7/26
 * https://blog.csdn.net/qq_44023485/article/details/120374463?utm_medium=distribute.pc_aggpage_search_result.none-task-blog-2~aggregatepage~first_rank_ecpm_v1~rank_v31_ecpm-1-120374463-null-null.pc_agg_new_rank&utm_term=android%20%E6%89%93%E5%BC%80%E5%BE%AE%E4%BF%A1%E4%BB%98%E6%AC%BE%E7%A0%81&spm=1000.2123.3001.4430
 */
public class PayAccessibility extends AccessibilityService {
    private static final String TAG = "打印 ";
    private Intent intent = null;
    private boolean isPaymentReceivedClick = false;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        Log.d(TAG, "onAccessibilityEvent: 活动事件" + event.getEventType());
        switch (eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                Log.d(TAG, "onAccessibilityEvent: 包名：" + event.getPackageName());
                ComponentName componentName = new ComponentName(event.getPackageName() == null ? "" : event.getPackageName().toString(), event.getClassName() == null ? "" : event.getClassName().toString());
                ActivityInfo activityInfo = tryGetActivity(componentName);
                if (event.getPackageName() != null && event.getPackageName().equals("com.tencent.mm")) {
                    Log.d(TAG, "onAccessibilityEvent: 活动名：" + activityInfo);
                    boolean isActivity = activityInfo != null;
                    if (isActivity) {
                        Log.d(TAG, "onAccessibilityEvent: 界面不为空");
                        if (TextUtils.equals("com.tencent.mm.ui.LauncherUI", activityInfo.name) && !isPaymentReceivedClick) {
                            AccessibilityNodeInfo rootNode = getRootInActiveWindow();
                            if (rootNode != null) {
                                pay:
                                for (AccessibilityNodeInfo node : rootNode.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/fcu")) {
                                    Log.d(TAG, "onAccessibilityEvent: 下拉列表");
                                    if (node.isEnabled() && TextUtils.equals(node.getClassName(), "android.widget.RelativeLayout")) {
                                        Log.d(TAG, "autoScreen: 自动点击");
                                        node.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                                        //睡眠一会，等待视图刷新
                                        try {
                                            Thread.sleep(200);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        AccessibilityNodeInfo rootNodePayActivity = getRootInActiveWindow();
                                        Log.d(TAG, "onAccessibilityEvent: rootNodePayActivity.size = " + rootNodePayActivity.getChildCount());
                                        for (AccessibilityNodeInfo payNode : rootNodePayActivity.findAccessibilityNodeInfosByText("收付款")) {//收付款按钮不可点击
                                            Log.d(TAG, "onAccessibilityEvent: 进入首付款界面：" + payNode.getClassName());
                                            if (payNode.isEnabled() && TextUtils.equals(payNode.getClassName(), "android.widget.TextView")) {
                                                Log.d(TAG, "onAccessibilityEvent: 点击收付款按钮");
                                                payNode.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);//获取父级的控件
//                                                    stopSelf();
                                                isPaymentReceivedClick = true;
                                                Log.d(TAG, "onAccessibilityEvent: 停止服务");
                                            }
                                        }
                                        Log.d(TAG, "onAccessibilityEvent: 结束列表框遍历");
                                    }
                                }
                            } else {
                                Log.d(TAG, "autoScreen: 节点为空");
                            }
                        }
                    } else {
                        Log.d(TAG, "onAccessibilityEvent: 界面为空");
                    }
                }
                break;
            case AccessibilityEvent.TYPE_WINDOWS_CHANGED:
                Log.d("EEE", "TYPE_WINDOWS_CHANGED: " + event.getPackageName() + " " + event.getClassName() + " " + event);
                break;
        }
    }

    /**
     * 获取Activity界面信息
     *
     * @param componentName
     * @return
     */
    private ActivityInfo tryGetActivity(ComponentName componentName) {
        try {
            Log.d("EEE", "get appInfo:" + componentName.getPackageName());
            return getPackageManager().getActivityInfo(componentName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.intent = intent;
        isPaymentReceivedClick = false;//重置
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onInterrupt() {

    }

}
