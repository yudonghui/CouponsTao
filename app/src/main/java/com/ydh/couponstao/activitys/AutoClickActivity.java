package com.ydh.couponstao.activitys;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ydh.couponstao.R;
import com.ydh.couponstao.common.bases.BaseActivity;
import com.ydh.couponstao.dialogs.EditeDialog;
import com.ydh.couponstao.services.FloatingService;
import com.ydh.couponstao.services.ScrollService;
import com.ydh.couponstao.utils.ClipboardUtils;
import com.ydh.couponstao.utils.SPUtils;

/**
 * Created by ydh on 2022/9/25
 * 自动点击 滑动 返回
 */
public class AutoClickActivity extends BaseActivity {
    private Context mContext;
    private TextView mTvLocation;
    private RadioGroup mRgScroll;
    private TextView mTvInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_click);
        mContext = this;
        mRgScroll = findViewById(R.id.rg_scroll);
        mTvInput = findViewById(R.id.tv_input);
        mTvLocation = findViewById(R.id.tv_location);
        checkPermission();
        startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 111);
        initListener();
    }

    private void initListener() {
        if (SPUtils.getInt(SPUtils.FILE_USER, SPUtils.ORIENTATION) == 0) {
            mRgScroll.check(R.id.rb_up_down);
        } else {
            mRgScroll.check(R.id.rb_left_right);
        }
        mTvInput.setText(SPUtils.getLong(SPUtils.FILE_USER, SPUtils.DURATION) + "");
        mRgScroll.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_up_down) {
                    SPUtils.setInt(SPUtils.FILE_USER, SPUtils.ORIENTATION, 0);
                } else if (checkedId == R.id.rb_left_right) {
                    SPUtils.setInt(SPUtils.FILE_USER, SPUtils.ORIENTATION, 1);
                }
            }
        });
        mTvInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditeDialog(mContext, "请输入滑动频率", 1, new EditeDialog.EditInterface() {
                    @Override
                    public void onClick(String s) {
                        if (TextUtils.isEmpty(s)) return;
                        SPUtils.setLong(SPUtils.FILE_USER, SPUtils.DURATION, Long.parseLong(s));
                        mTvInput.setText(s);
                    }
                });
            }
        });
    }

    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT).show();
            startActivityForResult(
                    new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName())), 0);
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && !Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void buttonStart(View view) {
        Intent serviceFloat = new Intent(this, FloatingService.class);
        startService(serviceFloat);
    }

    public void buttonScroll(View view) {
        Intent serviceScroll = new Intent(this, ScrollService.class);
        startService(serviceScroll);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                mTvLocation.setText(x + "," + y);
                break;
            case MotionEvent.ACTION_UP:
                int nowX = (int) event.getRawX();
                int nowY = (int) event.getRawY();
                ClipboardUtils.setClipboard(nowX + "," + nowY);
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}