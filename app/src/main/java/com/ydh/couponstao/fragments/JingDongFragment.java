package com.ydh.couponstao.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ydh.couponstao.R;
import com.ydh.couponstao.common.Constant;
import com.ydh.couponstao.common.bases.BaseFragment;
import com.ydh.couponstao.http.BaseBack;
import com.ydh.couponstao.http.BaseEntity;
import com.ydh.couponstao.http.HttpClient;
import com.ydh.couponstao.utils.DateFormtUtils;
import com.ydh.couponstao.utils.HttpMd5;

import java.util.TreeMap;

import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by ydh on 2022/8/16
 */
public class JingDongFragment extends BaseFragment {

    public static JingDongFragment newInstance() {
        Bundle args = new Bundle();
        JingDongFragment fragment = new JingDongFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jing_dong, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void requestData() {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("app_key", Constant.APP_KEY_JD);
        map.put("method", "jd.union.open.goods.jingfen.query");
        map.put("v", "1.0");
        map.put("sign_method", "md5");
        map.put("format", "json");
        TreeMap<String, Object> buy_param_json = new TreeMap<>();
        TreeMap<String, Object> goodsReq = new TreeMap<>();
        goodsReq.put("eliteId", "22");
        buy_param_json.put("goodsReq", goodsReq);
        map.put("360buy_param_json", buy_param_json);
        map.put("eliteId", "22");
        map.put("pageIndex", "1");
        map.put("pageSize", "10");
        map.put("timestamp", DateFormtUtils.getCurrentDate(DateFormtUtils.YMD_HMS));
        String sign = HttpMd5.buildSignJd(map);
        map.put("sign", sign);
        // LogUtils.e("请求参数：",map.);
        Call<BaseEntity<Object>> call = HttpClient.getHttpApiJd().getMaterailJd(map);
        mNetWorkList.add(call);
        call.enqueue(new BaseBack<Object>() {
            @Override
            protected void onSuccess(Object o) {

            }

            @Override
            protected void onFailed(int code, String msg) {

            }
        });
    }
}
