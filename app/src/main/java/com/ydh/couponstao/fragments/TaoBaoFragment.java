package com.ydh.couponstao.fragments;

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
import com.ydh.couponstao.activitys.TaoBaoActivity;
import com.ydh.couponstao.common.bases.BaseFragment;
import com.ydh.couponstao.entitys.HomeEntity;
import com.ydh.couponstao.entitys.MaterialContentEntity;
import com.ydh.couponstao.entitys.MaterialEntity;
import com.ydh.couponstao.http.BaseBack;
import com.ydh.couponstao.http.BaseEntity;
import com.ydh.couponstao.http.ErrorEntity;
import com.ydh.couponstao.http.HttpClient;
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

    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        initData();
    }

    private void initData() {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("method", "taobao.tbk.spread.get");
        map.put("app_key", "28252696");
        map.put("timestamp", DateFormtUtils.getCurrentDate(DateFormtUtils.YMD_HMS));
        map.put("sign_method", "md5");
        map.put("format", "json");
        map.put("adzone_id", "109915700451");
        map.put("v", "2.0");
        ArrayList<TreeMap<String, Object>> requests = new ArrayList<>();
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("url", "http://temai.taobao.com");
        requests.add(treeMap);
        map.put("requests","[{\"url\":\"http://temai.taobao.com\"}]");
        String sign = HttpMd5.buildSignTb(map);
        map.put("sign", sign);
        Call<MaterialContentEntity> call = HttpClient.getHttpApiTb().getMaterailTb(map);
        mNetWorkList.add(call);
        call.enqueue(new Callback<MaterialContentEntity>() {
            @Override
            public void onResponse(Call<MaterialContentEntity> call, Response<MaterialContentEntity> response) {

            }

            @Override
            public void onFailure(Call<MaterialContentEntity> call, Throwable t) {
            }
        });

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


}
