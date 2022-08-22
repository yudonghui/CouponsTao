package com.ydh.couponstao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ydh.couponstao.activitys.WebWiewActivity;
import com.ydh.couponstao.common.Constant;
import com.ydh.couponstao.common.bases.BaseActivity;
import com.ydh.couponstao.entitys.MaterialEntity;
import com.ydh.couponstao.entitys.TbCodeEntity;
import com.ydh.couponstao.entitys.TbDetailEntity;
import com.ydh.couponstao.http.ErrorEntity;
import com.ydh.couponstao.http.HttpClient;
import com.ydh.couponstao.utils.CommonUtil;
import com.ydh.couponstao.utils.DateFormtUtils;
import com.ydh.couponstao.utils.HttpMd5;
import com.ydh.couponstao.utils.MsgCode;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends BaseActivity {
    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    private MaterialEntity materialEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        unBind = ButterKnife.bind(this);
        materialEntity = (MaterialEntity) getIntent().getSerializableExtra("materialEntity");
    }

    public void couponGet(View view) {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("method", "taobao.tbk.coupon.get");
        map.put("app_key", Constant.APP_KEY_TB);
        map.put("timestamp", DateFormtUtils.getCurrentDate(DateFormtUtils.YMD_HMS));
        map.put("sign_method", "md5");
        map.put("format", "json");
        map.put("v", "2.0");
        map.put("simplify", true);
        map.put("item_id", materialEntity.getItem_id());
        String sign = HttpMd5.buildSignTb(map);
        map.put("sign", sign);
        Call<TbCodeEntity> call = HttpClient.getHttpApiTb().getMaterailTbCode(map);
        mNetWorkList.add(call);
        call.enqueue(new Callback<TbCodeEntity>() {
            @Override
            public void onResponse(Call<TbCodeEntity> call, Response<TbCodeEntity> response) {
                if (response != null && response.isSuccessful() && response.body() != null) {
                    ErrorEntity error_response = response.body().getError_response();
                    if (error_response == null) {

                    } else {
                        CommonUtil.showToast(MsgCode.getStrByCode(error_response.getCode()));
                    }
                }
            }

            @Override
            public void onFailure(Call<TbCodeEntity> call, Throwable t) {
            }
        });
    }

    public void infoGet(View view) {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("method", "taobao.tbk.item.info.get");
        map.put("app_key", Constant.APP_KEY_TB);
        map.put("timestamp", DateFormtUtils.getCurrentDate(DateFormtUtils.YMD_HMS));
        map.put("sign_method", "md5");
        map.put("format", "json");
        map.put("v", "2.0");
        map.put("simplify", true);
        map.put("num_iids", materialEntity.getItem_id());
        String sign = HttpMd5.buildSignTb(map);
        map.put("sign", sign);
        Call<TbDetailEntity> call = HttpClient.getHttpApiTb().getMaterailTbDetail(map);
        mNetWorkList.add(call);
        call.enqueue(new Callback<TbDetailEntity>() {
            @Override
            public void onResponse(Call<TbDetailEntity> call, Response<TbDetailEntity> response) {
                if (response != null && response.isSuccessful() && response.body() != null) {
                    ErrorEntity error_response = response.body().getError_response();
                    if (error_response == null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("url", response.body().getResults().get(0).getItem_url());
                        startActivity(WebWiewActivity.class, bundle);
                    } else {
                        CommonUtil.showToast(MsgCode.getStrByCode(error_response.getCode()));
                    }
                }
            }

            @Override
            public void onFailure(Call<TbDetailEntity> call, Throwable t) {
            }
        });
    }

    @OnClick({R.id.return_btn, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.tv_right:
                Uri uriWx = Uri.parse("weixin://");
                Intent intentWx = new Intent(Intent.ACTION_VIEW, uriWx);
                startActivity(intentWx);
                break;
        }
    }
}