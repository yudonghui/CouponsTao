package com.ydh.couponstao.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ydh.couponstao.R;
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

public class TbDetailActivity extends BaseActivity {

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
        setContentView(R.layout.activity_tb_detail);
        unBind = ButterKnife.bind(this);
        materialEntity = (MaterialEntity) getIntent().getSerializableExtra("materialEntity");
    }

}