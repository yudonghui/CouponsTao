package com.ydh.couponstao.services;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.ydh.couponstao.R;
import com.ydh.couponstao.utils.SPUtils;


/**
 * Created by ydh on 2022/9/21
 */
public class ScrollService extends BaseService {
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    private View displayView;
    public Handler mHandler = new Handler();
    private Runnable runnable;
    private long durationTime = 5000;
    private int orientation;//0 上下滑动，1 左右滑动

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = dp2px(110);
        layoutParams.height = dp2px(110);
        layoutParams.x = 300;
        layoutParams.y = 300;
        initTimeTask();
    }


    private void initTimeTask() {
        runnable = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                if (orientation == 0) {//上下滑动
                    autoSlideView(dp180, 1500, dp180, 50);
                } else {//左右滑动
                    autoSlideView(dp300, dp180, 0, dp180);
                }

                mHandler.postDelayed(this, durationTime);
            }
        };
    }

    public static int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        durationTime = SPUtils.getLong(SPUtils.FILE_USER, SPUtils.DURATION) == 0 ? 5000 : SPUtils.getLong(SPUtils.FILE_USER, SPUtils.DURATION);
        orientation = SPUtils.getInt(SPUtils.FILE_USER, SPUtils.ORIENTATION);
        Log.e("间隔时间：", " ------ " + durationTime);
        showFloatingWindow();
        return super.onStartCommand(intent, flags, startId);
    }

    private boolean isStart = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showFloatingWindow() {
        if (Settings.canDrawOverlays(this)) {
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            displayView = layoutInflater.inflate(R.layout.scroll_service, null);
            displayView.setOnTouchListener(new FloatingOnTouchListener());
            final ImageView ivStartPause = displayView.findViewById(R.id.iv_start_pause);
            TextView tvDelete = displayView.findViewById(R.id.tv_delete);
            ivStartPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isStart) {
                        isStart = false;
                        ivStartPause.setImageResource(R.mipmap.start_icon);
                        mHandler.removeCallbacks(runnable);
                    } else {
                        isStart = true;
                        ivStartPause.setImageResource(R.mipmap.pause_icon);
                        mHandler.postDelayed(runnable, 1000);
                    }
                }
            });
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHandler.removeCallbacks(runnable);
                    windowManager.removeView(displayView);
                }
            });
            windowManager.addView(displayView, layoutParams);

        }
    }

    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    Log.e("坐标：X", x + "");
                    Log.e("坐标：Y", y + "");
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;
                    windowManager.updateViewLayout(view, layoutParams);
                    break;
                default:
                    break;
            }
            return false;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        windowManager.removeView(displayView);
    }
}
