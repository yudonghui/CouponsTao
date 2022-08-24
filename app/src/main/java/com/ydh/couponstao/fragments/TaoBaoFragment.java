package com.ydh.couponstao.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ydh.couponstao.R;
import com.ydh.couponstao.TestActivity;
import com.ydh.couponstao.activitys.HWScanActivity;
import com.ydh.couponstao.activitys.SearchTbActivity;
import com.ydh.couponstao.activitys.TaoBaoActivity;
import com.ydh.couponstao.common.Constant;
import com.ydh.couponstao.common.bases.BaseFragment;
import com.ydh.couponstao.entitys.HomeEntity;
import com.ydh.couponstao.entitys.MaterialContentEntity;
import com.ydh.couponstao.http.HttpClient;
import com.ydh.couponstao.utils.DateFormtUtils;
import com.ydh.couponstao.utils.HttpMd5;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
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
    private ArrayList<HomeEntity> mHomeList = new ArrayList<>();

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
        return view;
    }

    @OnClick({R.id.iv_scan, R.id.tv_hint, R.id.tv_search, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
        mHomeList.add(new HomeEntity(R.mipmap.one, "高佣榜", 1));
        mHomeList.add(new HomeEntity(R.mipmap.two, "好券直播", 2));
        mHomeList.add(new HomeEntity(R.mipmap.three, "品牌券", 3));
        mHomeList.add(new HomeEntity(R.mipmap.three, "大额券", 4));
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
        rvHome.setLayoutManager(new GridLayoutManager(mContext, 4));
        rvHome.setAdapter(mCommonAdapter);
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
