package com.ydh.couponstao.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.huawei.hms.hmsscankit.OnLightVisibleCallBack;
import com.huawei.hms.hmsscankit.OnResultCallback;
import com.huawei.hms.hmsscankit.RemoteView;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.ydh.couponstao.R;
import com.ydh.couponstao.common.Constant;
import com.ydh.couponstao.common.bases.BaseActivity;
import com.ydh.couponstao.utils.CommonUtil;
import com.ydh.couponstao.utils.DeviceUtils;
import com.ydh.couponstao.utils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HWScanActivity extends BaseActivity {
    @BindView(R.id.rim)
    FrameLayout rim;
    @BindView(R.id.tv_open_hint)
    TextView tvOpenHint;
    @BindView(R.id.iv_open_light)
    ImageView ivOpenLight;
    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.scan_auto_layout)
    LinearLayout autoScanView;
    private Context mContext;
    private int mScreenWidth;
    private int mScreenHeight;
    private int SCAN_FRAME_SIZE = 240;
    private RemoteView remoteView;
    private ArrayList<String> successList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_w_scan);
        unBind = ButterKnife.bind(this);
        mContext = this;
        mScreenWidth = DeviceUtils.getDisplayWidth(mContext);
        mScreenHeight = DeviceUtils.getDisplayHeight(mContext);
        int scanFrameSize = (int) (SCAN_FRAME_SIZE * getResources().getDisplayMetrics().density);
        Rect rect = new Rect();
        rect.left = mScreenWidth / 2 - scanFrameSize / 2;
        rect.right = mScreenWidth / 2 + scanFrameSize / 2;
        rect.top = mScreenHeight / 2 - scanFrameSize / 2;
        rect.bottom = mScreenHeight / 2 + scanFrameSize / 2;
        Rect rect1 = new Rect(0, 0, mScreenWidth, mScreenHeight); // 设置扫码识别区域，您可以按照需求调整参数。
        /**
         *setContinuouslyScan 默认为true，连续扫码模式，此时扫码结果会连续返回；设置为false，非连续扫码模式，此时相同的码值只会返回一次。
         */
        remoteView = new RemoteView.Builder().setContext(this)
                .setBoundingBox(rect1)
                .setFormat(HmsScan.ALL_SCAN_TYPE)
                .build();
        initListener();
        remoteView.onCreate(savedInstanceState);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        rim.addView(remoteView, layoutParams);
    }

    @OnClick({R.id.iv_open_light, R.id.return_btn, R.id.tv_album})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_open_light:
                remoteView.switchLight();
                break;
            case R.id.tv_album:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                Intent intent2 = Intent.createChooser(intent, "选择二维码图片");
                startActivityForResult(intent2, 6321);
                break;
            case R.id.return_btn:
                finish();
                break;

        }
    }

    private void initListener() {
        //扫描结果监听
        remoteView.setOnResultCallback(new OnResultCallback() {
            @Override
            public void onResult(HmsScan[] result) {
                if (result != null && result.length > 0 && result[0] != null && !TextUtils.isEmpty(result[0].getOriginalValue())) {
                    LogUtils.e("扫描结果：" + result[0].getOriginalValue());
                    vibrate();
                    backResult(result[0].getOriginalValue());
                }
            }
        });
        // 光线暗时，会回调该接口，用于控制显示闪光灯开关。
        remoteView.setOnLightVisibleCallback(new OnLightVisibleCallBack() {
            @Override
            public void onVisibleChanged(boolean b) {
              /*  if (b) {
                    tvOpenHint.setVisibility(View.VISIBLE);
                } else {
                    tvOpenHint.setVisibility(View.GONE);
                }*/
            }
        });
    }

    private void vibrate() {
        //直接创建，不需要设置setDataSource
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    private void backResult(String result) {
        Intent intent = getIntent();
        intent.putExtra("result", result);
        setResult(Constant.RESULT_CODE1, intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        remoteView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        remoteView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        remoteView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        remoteView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        remoteView.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 6321 && resultCode == RESULT_OK) {
            // data是Intent类型，data.getData是待扫描的条码图片Uri。
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
            HmsScanAnalyzerOptions options = new HmsScanAnalyzerOptions.Creator()
                    .setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE, HmsScan.DATAMATRIX_SCAN_TYPE)
                    .setPhotoMode(true)
                    .create();
            HmsScan[] hmsScans = ScanUtil.decodeWithBitmap(this, bitmap, options);
            // 处理识别二维码
            if (hmsScans != null && hmsScans.length > 0) {
                CommonUtil.showToast(hmsScans[0].getOriginalValue());
            }
        }
    }
}