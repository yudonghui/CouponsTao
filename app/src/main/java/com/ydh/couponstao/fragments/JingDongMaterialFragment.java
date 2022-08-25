package com.ydh.couponstao.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ydh.couponstao.R;
import com.ydh.couponstao.adapter.MaterialJdAdapter;
import com.ydh.couponstao.common.Constant;
import com.ydh.couponstao.common.SpaceItemDecoration;
import com.ydh.couponstao.common.bases.BaseFragment;
import com.ydh.couponstao.entitys.JdBaseEntity;
import com.ydh.couponstao.entitys.JdMaterialEntity;
import com.ydh.couponstao.entitys.MaterialEntity;
import com.ydh.couponstao.http.HttpClient;
import com.ydh.couponstao.utils.CommonUtil;
import com.ydh.couponstao.utils.DateFormtUtils;
import com.ydh.couponstao.utils.HttpMd5;
import com.zhy.adapter.recyclerview.CommonAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ydh on 2022/8/16
 */
public class JingDongMaterialFragment extends BaseFragment {


    @BindView(R.id.rv_material)
    RecyclerView rvMaterial;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private List<JdMaterialEntity> mMaterialList = new ArrayList<>();
    private CommonAdapter<JdMaterialEntity> mMaterialAdapter;
    private int pageIndex = 1;
    private int pageSize = 10;
    private String eliteId;

    public static JingDongMaterialFragment newInstance(String eliteId) {
        Bundle args = new Bundle();
        args.putString("eliteId", eliteId);
        JingDongMaterialFragment fragment = new JingDongMaterialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jing_dong_material, container, false);
        unbinder = ButterKnife.bind(this, view);
        eliteId = getArguments().getString("eliteId");
        initAdapter();
        initListener();
        initData();
        return view;
    }

    private void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                initData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                initData();
            }
        });
    }

    private void initAdapter() {
        mMaterialAdapter = new MaterialJdAdapter(mContext, R.layout.item_jing_dong, mMaterialList);
        rvMaterial.addItemDecoration(new SpaceItemDecoration(CommonUtil.dp2px(10), SpaceItemDecoration.STAGGEREDGRIDLAYOUT));
        rvMaterial.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rvMaterial.setAdapter(mMaterialAdapter);
    }

    public void initData() {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("app_key", Constant.APP_KEY_JD);
        map.put("method", HttpClient.JD_MATERIAL_FORM);
        map.put("v", "1.0");
        map.put("sign_method", "md5");
        map.put("format", "json");
        map.put("timestamp", DateFormtUtils.getCurrentDate(DateFormtUtils.YMD_HMS));

        TreeMap<String, Object> buy_param_json = new TreeMap<>();
        TreeMap<String, Object> goodsReq = new TreeMap<>();
        goodsReq.put("eliteId", eliteId);
        goodsReq.put("pageIndex", pageIndex);
        goodsReq.put("pageSize", pageSize);
        buy_param_json.put("goodsReq", goodsReq);
        map.put("360buy_param_json", new Gson().toJson(buy_param_json));

        String sign = HttpMd5.buildSignJd(map);
        map.put("sign", sign);
        // LogUtils.e("请求参数：",map.);
        Call<ResponseBody> call = HttpClient.getHttpApiJd().getMaterailJd(map);
        mNetWorkList.add(call);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                stopOver(refreshLayout);
                if (response != null && response.isSuccessful() && response.body() != null) {
                    try {
                        String body = response.body().string();
                        JSONObject jsonObject = new JSONObject(body);
                        if (!body.contains("error_response")) {
                            JSONObject jd_union_open_goods_jingfen_query_responce = jsonObject.getJSONObject("jd_union_open_goods_jingfen_query_responce");
                            String code = jd_union_open_goods_jingfen_query_responce.getString("code");
                            String queryResult = jd_union_open_goods_jingfen_query_responce.getString("queryResult");
                            JdBaseEntity<List<JdMaterialEntity>> jdBaseEntity = new Gson().fromJson(queryResult, new TypeToken<JdBaseEntity<List<JdMaterialEntity>>>() {
                            }.getType());
                            List<JdMaterialEntity> dataList = jdBaseEntity.getData();
                            if (pageIndex == 1) mMaterialList.clear();
                            if (dataList != null) {
                                mMaterialList.addAll(dataList);
                            }
                            mMaterialAdapter.notifyDataSetChanged();
                        } else {
                            JSONObject error_response = jsonObject.getJSONObject("error_response");
                            String code = error_response.getString("code");
                            String zh_desc = error_response.getString("zh_desc");
                            CommonUtil.showToast(zh_desc + "");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                stopOver(refreshLayout);
            }
        });
    }

}
