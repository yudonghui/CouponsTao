package com.ydh.couponstao.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ydh.couponstao.R;
import com.ydh.couponstao.adapter.MaterialFormAdapter;
import com.ydh.couponstao.common.Constant;
import com.ydh.couponstao.common.SpaceItemDecoration;
import com.ydh.couponstao.common.bases.BaseTaoActivity;
import com.ydh.couponstao.entitys.MaterialContentEntity;
import com.ydh.couponstao.entitys.MaterialEntity;
import com.ydh.couponstao.http.ErrorEntity;
import com.ydh.couponstao.http.HttpClient;
import com.ydh.couponstao.utils.CommonUtil;
import com.ydh.couponstao.utils.DateFormtUtils;
import com.ydh.couponstao.utils.HttpMd5;
import com.ydh.couponstao.utils.MsgCode;
import com.ydh.couponstao.utils.Strings;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTbActivity extends BaseTaoActivity {

    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.rv_material)
    RecyclerView rvMaterial;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private CommonAdapter<MaterialEntity> mMaterialAdapter;
    private ArrayList<MaterialEntity> mMaterialList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tb);
        unBind = ButterKnife.bind(this);
        String searchContent = getIntent().getStringExtra("searchContent");
        etContent.setText(Strings.getString(searchContent));
        initAdapter();
        initListener();
        if (!TextUtils.isEmpty(searchContent))
            refreshLayout.autoRefresh();
    }


    @OnClick({R.id.return_btn, R.id.iv_scan, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.iv_scan:
                startActivityForResult(HWScanActivity.class, Constant.REQUEST_CODE1);
                break;
            case R.id.tv_search:
                String searchContent = etContent.getText().toString();
                if (TextUtils.isEmpty(searchContent)) {
                    etContent.setText(etContent.getHint().toString());
                }
                refreshLayout.autoRefresh();
                break;
        }
    }

    private void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page_no = 1;
                searchData(etContent.getText().toString());
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page_no++;
                searchData(etContent.getText().toString());
            }
        });
    }

    private void initAdapter() {
        mMaterialAdapter = new MaterialFormAdapter(mContext, R.layout.item_tao_bao, mMaterialList);
        rvMaterial.addItemDecoration(new SpaceItemDecoration(CommonUtil.dp2px(10), SpaceItemDecoration.STAGGEREDGRIDLAYOUT));
        rvMaterial.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvMaterial.setAdapter(mMaterialAdapter);
    }

    private int page_no = 1;
    private int page_size = 20;

    private void searchData(String searchContent) {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("method", "taobao.tbk.dg.material.optional");
        map.put("app_key", Constant.APP_KEY_TB);
        map.put("timestamp", DateFormtUtils.getCurrentDate(DateFormtUtils.YMD_HMS));
        map.put("sign_method", "md5");
        map.put("format", "json");
        map.put("adzone_id", Constant.ADZONE_ID);
        map.put("v", "2.0");
        map.put("q", searchContent);
        map.put("material_id", 17004);//默认2836， 佣金比较高6707，官方个性化算法优化17004
        map.put("simplify", true);
        map.put("page_no", page_no);
        map.put("page_size", page_size);
        String sign = HttpMd5.buildSignTb(map);
        map.put("sign", sign);
        Call<MaterialContentEntity> call = HttpClient.getHttpApiTb().getMaterailOptional(map);
        mNetWorkList.add(call);
        call.enqueue(new Callback<MaterialContentEntity>() {
            @Override
            public void onResponse(Call<MaterialContentEntity> call, Response<MaterialContentEntity> response) {
                stopOver(refreshLayout);
                if (response != null && response.isSuccessful() && response.body() != null) {
                    ErrorEntity error_response = response.body().getError_response();
                    if (error_response == null) {
                        List<MaterialEntity> map_data = response.body().getResult_list();
                        if (map_data != null && map_data.size() > 0) {
                            if (page_no == 1) {
                                mMaterialList.clear();
                            }
                            mMaterialList.addAll(map_data);
                            mMaterialAdapter.notifyDataSetChanged();
                        } else {
                            CommonUtil.showToast("暂无数据");
                        }
                    } else {
                        CommonUtil.showToast(MsgCode.getStrByCode(error_response.getCode()));
                    }
                }
            }

            @Override
            public void onFailure(Call<MaterialContentEntity> call, Throwable t) {
                stopOver(refreshLayout);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE1 && resultCode == Constant.RESULT_CODE1) {
            String result = data.getStringExtra("result");
            etContent.setText(result);
            searchData(result);
        }
    }
}