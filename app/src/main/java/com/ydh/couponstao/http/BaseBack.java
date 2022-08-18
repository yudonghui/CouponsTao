package com.ydh.couponstao.http;


import android.content.Intent;

import com.google.gson.stream.MalformedJsonException;
import com.ydh.couponstao.common.MyActivityManager;
import com.ydh.couponstao.utils.CommonUtil;
import com.ydh.couponstao.utils.LogUtils;
import com.ydh.couponstao.utils.MsgCode;
import com.ydh.couponstao.utils.SPUtils;


import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseBack<T> implements Callback<BaseEntity<T>> {
    protected abstract void onSuccess(T t);

    protected abstract void onFailed(int code, String msg);

    protected void onSuccess(BaseEntity<T> baseEntity) {
    }

    @Override
    public void onResponse(Call<BaseEntity<T>> call, Response<BaseEntity<T>> response) {
        BaseEntity<T> baseEntity = response.body();
        if (response.isSuccessful() && baseEntity != null) {
            onSuccess(baseEntity);
            if (baseEntity.getError_response() == null) {//成功
                onSuccess(baseEntity.getData());
            } else {
                CommonUtil.showToast(MsgCode.getStrByCode(baseEntity.getError_response().getCode()));
                onFailed(baseEntity.getError_response().getCode(), baseEntity.getError_response().getMsg());
            }
        } else {
            onFailed(0, response.message());
        }

    }

    @Override
    public void onFailure(Call<BaseEntity<T>> call, Throwable t) {
        LogUtils.e("请求出错" + t.getMessage());
        if (t instanceof ConnectException) {//网络连接失败
            CommonUtil.showToast("网络开小差了，请检查网络");
            onFailed(0, "");
        } else if (t instanceof MalformedJsonException) {
            CommonUtil.showToast("解析数据失败");
            onFailed(0, "");
        } else if (t instanceof SocketTimeoutException) {
            CommonUtil.showToast("网络请求超时");
            onFailed(0, "");
        } else if (t instanceof UnknownHostException) {
            CommonUtil.showToast("网络请求失败");
        } else if (t instanceof IOException && "Canceled".equals(t.getMessage())) {//在网络还没有结束的时候关闭了页面。取消

        } else {//网络请求关闭。或者其他异常

        }

    }
}
