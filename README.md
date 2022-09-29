
Android 自动化操作，辅助功能无障碍，在其他应用的上层显示
====
​apk

在其他应用的上层显示
-------

1、获取权限<br>
  uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" <br>
2、创建服务 用来展示悬浮窗<br>
3、启动悬浮窗<br>

辅助功能无障碍
-------

uses-permission android:name="android.permission.FOREGROUND_SERVICE" <br>
给Service设置无障碍模式

<service
    android:name=".services.ScrollService"
    android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
    <intent-filter>
        <action android:name="android.accessibilityservice.AccessibilityService" />
    </intent-filter>
    <meta-data
        android:name="android.accessibilityservice"
        android:resource="@xml/accessibility" />
</service>
ScrollService需要继承AccessibilityService

点击 滑动 返回操作

@RequiresApi(Build.VERSION_CODES.N)
protected void autoSlideView(float startX, float startY, float endX, float endY) {
    android.graphics.Path path = new android.graphics.Path();
    path.moveTo(startX, startY);
    path.lineTo(endX, endY);
    GestureDescription gestureDescription = new GestureDescription.Builder()
            .addStroke(new GestureDescription.StrokeDescription(path, 0, 500))
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
​
