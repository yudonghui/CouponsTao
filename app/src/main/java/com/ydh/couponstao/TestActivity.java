package com.ydh.couponstao;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ydh.couponstao.activitys.WebWiewActivity;
import com.ydh.couponstao.common.Constant;
import com.ydh.couponstao.common.bases.BaseActivity;
import com.ydh.couponstao.entitys.JdBaseEntity;
import com.ydh.couponstao.entitys.MaterialEntity;
import com.ydh.couponstao.entitys.TbCodeEntity;
import com.ydh.couponstao.entitys.TbDetailEntity;
import com.ydh.couponstao.entitys.UrlEntity;
import com.ydh.couponstao.http.ErrorEntity;
import com.ydh.couponstao.http.HttpClient;
import com.ydh.couponstao.utils.ClipboardUtils;
import com.ydh.couponstao.utils.CommonUtil;
import com.ydh.couponstao.utils.DateFormtUtils;
import com.ydh.couponstao.utils.HttpMd5;
import com.ydh.couponstao.utils.MsgCode;

import org.json.JSONObject;

import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
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
    @BindView(R.id.webView)
    WebView webView;
    private MaterialEntity materialEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        unBind = ButterKnife.bind(this);
        materialEntity = (MaterialEntity) getIntent().getSerializableExtra("materialEntity");
        // 开启javascript 渲染
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("http") || url.contains("https")) {
                    // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                } else {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    return true;

                }
            }

        });
        // 载入内容
        //webView.loadUrl("https://yudonghui.github.io/pages/test.html");
        webView.loadUrl("file:///android_asset/index.html");
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

    public void jdMaterialQurey(View view) {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("app_key", Constant.APP_KEY_JD);
        map.put("method", "jd.union.open.goods.material.query");
        map.put("v", "1.0");
        map.put("sign_method", "md5");
        map.put("format", "json");
        TreeMap<String, Object> buy_param_json = new TreeMap<>();
        TreeMap<String, Object> goodsReq = new TreeMap<>();
        goodsReq.put("eliteId", "22");
        buy_param_json.put("goodsReq", goodsReq);
        map.put("360buy_param_json", new Gson().toJson(buy_param_json));
        map.put("eliteId", "22");
        map.put("pageIndex", "1");
        map.put("pageSize", "10");
        map.put("timestamp", DateFormtUtils.getCurrentDate(DateFormtUtils.YMD_HMS));
        String sign = HttpMd5.buildSignJd(map);
        map.put("sign", sign);
        // LogUtils.e("请求参数：",map.);
        Call<ResponseBody> call = HttpClient.getHttpApiJd().getMaterailJd(map);
        mNetWorkList.add(call);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

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

    public void commonGet(View view) {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("app_key", Constant.APP_KEY_JD);
        map.put("method", HttpClient.JD_COMMON_GET);
        map.put("v", "1.0");
        map.put("sign_method", "md5");
        map.put("format", "json");
        map.put("timestamp", DateFormtUtils.getCurrentDate(DateFormtUtils.YMD_HMS));

        TreeMap<String, Object> buy_param_json = new TreeMap<>();
        TreeMap<String, Object> promotionCodeReq = new TreeMap<>();
        //promotionCodeReq.put("materialId", materialEntity.getMaterialUrl());
        promotionCodeReq.put("materialId", "item.jd.com/10026248467776.html");
        promotionCodeReq.put("siteId", Constant.APP_ID);
        buy_param_json.put("promotionCodeReq", promotionCodeReq);
        map.put("360buy_param_json", new Gson().toJson(buy_param_json));

        String sign = HttpMd5.buildSignJd(map);
        map.put("sign", sign);
        // LogUtils.e("请求参数：",map.);
        Call<ResponseBody> call = HttpClient.getHttpApiJd().getMaterailJd(map);
        mNetWorkList.add(call);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null && response.isSuccessful() && response.body() != null) {
                    try {
                        String body = response.body().string();
                        JSONObject jsonObject = new JSONObject(body);
                        if (!body.contains("error_response")) {
                            JSONObject jd_union_open_promotion_common_get_responce = jsonObject.getJSONObject("jd_union_open_promotion_common_get_responce");
                            String code = jd_union_open_promotion_common_get_responce.getString("code");
                            String getResult = jd_union_open_promotion_common_get_responce.getString("getResult");

                            JdBaseEntity<UrlEntity> jdBaseEntity = new Gson().fromJson(getResult, new TypeToken<JdBaseEntity<UrlEntity>>() {
                            }.getType());

                            String clickURL = jdBaseEntity.getData().getClickURL();
                            ClipboardUtils.setClipboard(clickURL);
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
            }
        });
    }

    public void clickExtract(View view) {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("method", "taobao.tbk.item.click.extract");
        map.put("app_key", Constant.APP_KEY_TB);
        map.put("timestamp", DateFormtUtils.getCurrentDate(DateFormtUtils.YMD_HMS));
        map.put("sign_method", "md5");
        map.put("format", "json");
        map.put("v", "2.0");
        map.put("simplify", true);
        map.put("click_url", "https://m.tb.cn/h.U0tQoc3?tk=UusL2uSA1W7");
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

    public void startScreen(View view) {
    }
}
