package com.ydh.couponstao.http;

import com.ydh.couponstao.entitys.MaterialContentEntity;
import com.ydh.couponstao.entitys.TbCodeEntity;
import com.ydh.couponstao.entitys.TbDetailEntity;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by zhengluping on 2018/6/4.
 */
public interface ServersApi {

    //登录
    @POST("mylike-crm/api/user/login.do")
    Call<BaseEntity<Object>> getOldLogin(@Body RequestBody body);

    //登录
    @POST("his-api/v1.0/app/login")
    Call<BaseEntity<Object>> getNewLogin(@Body RequestBody body);

    /**
     * 治疗详情
     */
    @GET("his-api/v1.0/treatProcFlowId/{tenantId}/{businessPkid}")
    Call<BaseEntity<Object>> treatProcFlowId(@Path("tenantId") String tenantId, @Path("businessPkid") String businessPkid);

    //
    @FormUrlEncoded
    @POST("router/rest")
    Call<MaterialContentEntity> getMaterailTb(@FieldMap Map<String, Object> paramsMap);

    //公用-淘口令生成
    @FormUrlEncoded
    @POST("router/rest")
    Call<TbCodeEntity> getMaterailTbCode(@FieldMap Map<String, Object> paramsMap);

    //公用-淘宝客商品详情查询(简版)
    @FormUrlEncoded
    @POST("router/rest")
    Call<TbDetailEntity> getMaterailTbDetail(@FieldMap Map<String, Object> paramsMap);

    //推广者-物料搜索 taobao.tbk.dg.material.optional
    @FormUrlEncoded
    @POST("router/rest")
    Call<MaterialContentEntity> getMaterailOptional(@FieldMap Map<String, Object> paramsMap);

    //公用-长链转短链
    @FormUrlEncoded
    @POST("router/rest")
    Call<TbDetailEntity> getSpreadGet(@FieldMap Map<String, Object> paramsMap);

    //
    @FormUrlEncoded
    @POST("routerjson")
    Call<BaseEntity<Object>> getMaterailJd(@FieldMap Map<String, Object> paramsMap);
}
