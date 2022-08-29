package com.ydh.couponstao.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.ObjectUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ydh.couponstao.R;
import com.ydh.couponstao.activitys.HWScanActivity;
import com.ydh.couponstao.activitys.SearchTbActivity;
import com.ydh.couponstao.activitys.TaoBaoActivity;
import com.ydh.couponstao.adapter.MaterialFormAdapter;
import com.ydh.couponstao.common.Constant;
import com.ydh.couponstao.common.SpaceItemDecoration;
import com.ydh.couponstao.common.bases.BaseFragment;
import com.ydh.couponstao.dialogs.CheckCopyDialog;
import com.ydh.couponstao.entitys.HomeEntity;
import com.ydh.couponstao.entitys.MaterialContentEntity;
import com.ydh.couponstao.entitys.MaterialEntity;
import com.ydh.couponstao.http.ErrorEntity;
import com.ydh.couponstao.http.HttpClient;
import com.ydh.couponstao.utils.ClipboardUtils;
import com.ydh.couponstao.utils.CommonUtil;
import com.ydh.couponstao.utils.DateFormtUtils;
import com.ydh.couponstao.utils.HttpMd5;
import com.ydh.couponstao.utils.MsgCode;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ydh on 2022/8/16
 */
public class TaoBaoFragment extends BaseFragment {
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.rv_home_material)
    RecyclerView rvHomeMaterial;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private ArrayList<HomeEntity> mHomeList = new ArrayList<>();
    private CommonAdapter<MaterialEntity> mMaterialAdapter;
    private ArrayList<MaterialEntity> mMaterialList = new ArrayList<>();
    private int page_no = 1;
    private int page_size = 20;

    public static TaoBaoFragment newInstance() {
        Bundle args = new Bundle();
        TaoBaoFragment fragment = new TaoBaoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tao_bao, container, false);
        unbinder = ButterKnife.bind(this, view);
        initAdapter();
        initListener();
        refreshLayout.autoRefresh();
        return view;
    }

    private void initListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page_no = 1;
                initData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page_no++;
                initData();
            }
        });
    }

    @OnClick({R.id.iv_share,R.id.iv_scan, R.id.tv_hint, R.id.tv_search, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_share:
                ClipboardUtils.setClipboardNo(Constant.DOWNLOAD_URL);
                Uri uriWx = Uri.parse("weixin://");
                Intent intentWx = new Intent(Intent.ACTION_VIEW, uriWx);
                startActivity(intentWx);
                break;
            case R.id.iv_scan:
                startActivityForResult(HWScanActivity.class, Constant.REQUEST_CODE1);
                break;
            case R.id.tv_hint:
                Bundle bundle = new Bundle();
                startActivity(SearchTbActivity.class, bundle);
                break;
            case R.id.tv_search:
                Bundle bundle2 = new Bundle();
                bundle2.putString("searchContent", tvHint.getHint().toString());
                startActivity(SearchTbActivity.class, bundle2);
                break;
            case R.id.iv_search:
                //startActivity(TestActivity.class);
                startActivity(SearchTbActivity.class);
                break;
        }
    }


    private void initAdapter() {
        mHomeList.clear();
        mHomeList.add(new HomeEntity(R.mipmap.three, "大额券", 4));
        mHomeList.add(new HomeEntity(R.mipmap.two, "好券直播", 2));
        mHomeList.add(new HomeEntity(R.mipmap.four, "品牌券", 3));
        mHomeList.add(new HomeEntity(R.mipmap.one, "其他", 9));
        CommonAdapter<HomeEntity> mCommonAdapter = new CommonAdapter<HomeEntity>(mContext, R.layout.item_home, mHomeList) {

            @Override
            protected void convert(ViewHolder holder, HomeEntity homeEntity, int position) {
                holder.setImageResource(R.id.iv_home, homeEntity.getImgRes());
                holder.setText(R.id.tv_title, homeEntity.getTitle());
                holder.setOnClickListener(R.id.ll_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", homeEntity.getType());
                        bundle.putString("title", homeEntity.getTitle());
                        startActivity(TaoBaoActivity.class, bundle);
                    }
                });
            }
        };
        rvHome.setLayoutManager(new GridLayoutManager(mContext, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvHome.setAdapter(mCommonAdapter);
        mMaterialAdapter = new MaterialFormAdapter(mContext, R.layout.item_tao_bao, mMaterialList);
        rvHomeMaterial.addItemDecoration(new SpaceItemDecoration(CommonUtil.dp2px(10), SpaceItemDecoration.STAGGEREDGRIDLAYOUT));
        rvHomeMaterial.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvHomeMaterial.setAdapter(mMaterialAdapter);
    }

    private void initData() {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("method", "taobao.tbk.dg.optimus.material");
        map.put("app_key", Constant.APP_KEY_TB);
        map.put("timestamp", DateFormtUtils.getCurrentDate(DateFormtUtils.YMD_HMS));
        map.put("sign_method", "md5");
        map.put("format", "json");
        map.put("adzone_id", Constant.ADZONE_ID);
        map.put("material_id", "28026");//实时热销榜-综合
        map.put("v", "2.0");
        map.put("simplify", true);
        map.put("page_no", page_no);
        map.put("page_size", page_size);
        String sign = HttpMd5.buildSignTb(map);
        map.put("sign", sign);
        Call<MaterialContentEntity> call = HttpClient.getHttpApiTb().getMaterailTb(map);
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
                            if (page_no == 1)
                                mMaterialList.clear();
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE1 && resultCode == Constant.RESULT_CODE1) {
            Bundle bundle = new Bundle();
            bundle.putString("searchContent", data.getStringExtra("result"));
            startActivity(SearchTbActivity.class, bundle);
        }
    }
}
