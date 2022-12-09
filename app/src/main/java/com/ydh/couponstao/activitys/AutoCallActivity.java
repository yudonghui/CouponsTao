package com.ydh.couponstao.activitys;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.ydh.couponstao.R;
import com.ydh.couponstao.common.bases.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AutoCallActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_call)
    TextView tvCall;
    private final static String simSlotName[] = {
            "extra_asus_dial_use_dualsim",
            "com.android.phone.extra.slot",//基本上是这个
            "slot",
            "simslot",
            "sim_slot",
            "subscription",//华为是这个
            "Subscription",
            "phone",
            "com.android.phone.DialingMode",
            "simSlot",
            "slot_id",//小米是这个
            "simId",
            "simnum",
            "phone_type",
            "slotId",
            "slotIdx"
    };
    @BindView(R.id.tv_time)
    TextView tvTime;
    private Handler mHandler = new Handler();
    private Runnable runnable;
    private int time = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_call);
        unBind = ButterKnife.bind(this);
        requestPermission(new String[]{Manifest.permission.CALL_PHONE}, 1);
        TelephonyManager tpm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tpm.listen(new MyPhontStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
        runnable = new Runnable() {

            @Override
            public void run() {
                time--;
                if (time == 0) {
                    time = 5;
                    String phone = etPhone.getText().toString();
                    try {
                        // 开始直接拨打电话
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        for (int i = 0; i < simSlotName.length; i++) {
                            intent.putExtra(simSlotName[i], 0);
                        }
                        startActivity(intent);
                    } catch (Exception e) {

                    }
                } else {
                    tvTime.setText(time + "");
                    mHandler.postDelayed(this, 1000);
                }
            }
        };

    }

    @OnClick(R.id.tv_call)
    public void onViewClicked() {
        time = 5;
        tvTime.setText("5");
        mHandler.postDelayed(runnable, 500);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        time = 5;
        tvTime.setText("5");
        mHandler.postDelayed(runnable, 500);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(runnable);
        mHandler = null;
        super.onDestroy();
    }

    class MyPhontStateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String phoneNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE://空闲
                    Log.e("电话状态：", "空闲");
                    break;
                case TelephonyManager.CALL_STATE_RINGING://来电
                    Log.e("电话状态：", "来电");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK://正在通话中
                    Log.e("电话状态：", "正在通话中");
                    break;
            }
        }
    }
}