package com.ydh.couponstao.activitys;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ydh.couponstao.R;
import com.ydh.couponstao.common.Constant;
import com.ydh.couponstao.common.bases.BaseActivity;
import com.ydh.couponstao.entitys.CouponInfoEntity;
import com.ydh.couponstao.entitys.ImageInfoContentEntity;
import com.ydh.couponstao.entitys.JdBaseEntity;
import com.ydh.couponstao.entitys.JdDetailEntity;
import com.ydh.couponstao.entitys.JdMaterialEntity;
import com.ydh.couponstao.entitys.UrlEntity;
import com.ydh.couponstao.http.HttpClient;
import com.ydh.couponstao.utils.CommonUtil;
import com.ydh.couponstao.utils.DateFormtUtils;
import com.ydh.couponstao.utils.HttpMd5;
import com.ydh.couponstao.utils.PicassoUtils;
import com.ydh.couponstao.utils.Strings;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JdDetailActivity extends BaseActivity {

    @BindView(R.id.iv_pict)
    ImageView ivPict;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_origin_price)
    TextView tvOriginPrice;
    @BindView(R.id.tv_buy_num)
    TextView tvBuyNum;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_shop_title)
    TextView tvShopTitle;
    @BindView(R.id.tv_coupon_money)
    TextView tvCouponMoney;
    @BindView(R.id.rv_shop_pic)
    RecyclerView rvShopPic;
    @BindView(R.id.tv_skip_jd)
    TextView tvSkipJd;
    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_commission)
    TextView tvCommission;
    @BindView(R.id.tv_skip_web)
    TextView tvSkipWeb;
    private JdMaterialEntity materialEntity;
    private CommonAdapter<String> mImageAdapter;
    private ArrayList<String> mImageUrlList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jd_detail);
        unBind = ButterKnife.bind(this);
        materialEntity = (JdMaterialEntity) getIntent().getSerializableExtra("materialEntity");
        initView();
        initAdapter();
        initDetailData();
    }

    @OnClick({R.id.return_btn, R.id.tv_title, R.id.iv_share, R.id.tv_skip_jd, R.id.tv_skip_web})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.tv_title:
                ClipboardManager cm1 = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                cm1.setText(materialEntity.getSkuName());
                CommonUtil.showToast("复制成功");
                break;
            case R.id.iv_share:
                getCommonGet(1);//获取推广链接 复制 打开微信
                //tpwdCreate(2);
                break;
            case R.id.tv_skip_jd://去京东
                getCommonGet(2);//获取推广链接 跳转webview打开
                break;
            case R.id.tv_skip_web://领优惠券
                JdMaterialEntity.CouponInfoContentEntity couponInfo = materialEntity.getCouponInfo();
                if (couponInfo != null && couponInfo.getCouponList() != null && couponInfo.getCouponList().size() > 0) {
                    List<CouponInfoEntity> couponList = couponInfo.getCouponList();
                    for (int i = 0; i < couponList.size(); i++) {
                        int isBest = couponList.get(i).getIsBest();
                        if (isBest == 1) {
                            CouponInfoEntity couponInfoEntity = couponList.get(i);
                            Bundle bundle = new Bundle();
                            bundle.putString("url", couponInfoEntity.getLink());
                            startActivity(WebWiewActivity.class, bundle);
                            break;
                        }
                    }
                }
                break;
        }
    }

    private void initView() {
        JdMaterialEntity.PriceInfoEntity priceInfo = materialEntity.getPriceInfo();
        JdMaterialEntity.CouponInfoContentEntity couponInfo = materialEntity.getCouponInfo();
        if (priceInfo != null) {
            tvOriginPrice.setText(Html.fromHtml("&yen") + "" + priceInfo.getPrice());//原价
            tvOriginPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tvOriginPrice.getPaint().setAntiAlias(true);
            tvPrice.setText(Html.fromHtml("&yen") + "" + priceInfo.getLowestCouponPrice());
        }

        tvBuyNum.setText(Strings.getString(materialEntity.getComments()) + "条评价    " + Strings.getString(materialEntity.getGoodCommentsShare()) + "%好评");
        tvTitle.setText(Strings.getString(materialEntity.getSkuName()));//商品名称
        tvShopTitle.setText(materialEntity.getShopInfo() == null ? "" : Strings.getString(materialEntity.getShopInfo().getShopName()));//店铺名称
        if (couponInfo != null && couponInfo.getCouponList() != null && couponInfo.getCouponList().size() > 0) {
            tvCouponMoney.setVisibility(View.VISIBLE);
            List<CouponInfoEntity> couponList = couponInfo.getCouponList();
            for (int i = 0; i < couponList.size(); i++) {
                int isBest = couponList.get(i).getIsBest();
                if (isBest == 1) {
                    CouponInfoEntity couponInfoEntity = couponList.get(i);
                    int discount = couponInfoEntity.getDiscount();
                    tvCouponMoney.setText("券" + discount);
                    break;
                }
            }
        } else {
            tvCouponMoney.setVisibility(View.GONE);
        }
    }

    private void initAdapter() {
        mImageAdapter = new CommonAdapter<String>(mContext, R.layout.item_image_view, mImageUrlList) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                ImageView mIvPhoto = holder.getView(R.id.iv_photo);
                PicassoUtils.setNetImg(s, mContext, mIvPhoto);
                mIvPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, PictureActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", position);
                        bundle.putSerializable("mUrlList", mImageUrlList);
                        intent.putExtras(bundle);
                        mContext.startActivity(intent);
                    }
                });
            }
        };
        LinearLayoutManager layout = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        rvShopPic.setLayoutManager(layout);
        rvShopPic.setAdapter(mImageAdapter);
    }

    private void initDetailView(JdDetailEntity jdDetailEntity) {

        if (jdDetailEntity.getImageInfo() != null && jdDetailEntity.getImageInfo().getImageList() != null && jdDetailEntity.getImageInfo().getImageList().size() > 0) {
            PicassoUtils.setNetImg(jdDetailEntity.getImageInfo().getImageList().get(0).getUrl(), mContext, ivPict);
        }
        String[] split = jdDetailEntity.getDetailImages().split(",");
        if (split.length > 0) {
            mImageUrlList.clear();
            mImageUrlList.addAll(Arrays.asList(split));
        }
        mImageAdapter.notifyDataSetChanged();
    }

    private void initDetailData() {
        showLoadingDialog();
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("app_key", Constant.APP_KEY_JD);
        map.put("method", HttpClient.JD_MATERIAL_DETAIL);
        map.put("v", "1.0");
        map.put("sign_method", "md5");
        map.put("format", "json");
        map.put("timestamp", DateFormtUtils.getCurrentDate(DateFormtUtils.YMD_HMS));

        TreeMap<String, Object> buy_param_json = new TreeMap<>();
        TreeMap<String, Object> goodsReq = new TreeMap<>();
        ArrayList<String> skuIds = new ArrayList<>();
        skuIds.add(materialEntity.getSkuId());
        goodsReq.put("skuIds", skuIds);
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
                cancelLoadingDialog();
                if (response != null && response.isSuccessful() && response.body() != null) {
                    try {
                        String body = response.body().string();
                        JSONObject jsonObject = new JSONObject(body);
                        if (!body.contains("error_response")) {
                            JSONObject jd_union_open_goods_bigfield_query_responce = jsonObject.getJSONObject("jd_union_open_goods_bigfield_query_responce");
                            String code = jd_union_open_goods_bigfield_query_responce.getString("code");
                            String queryResult = jd_union_open_goods_bigfield_query_responce.getString("queryResult");

                            JdBaseEntity<List<JdDetailEntity>> jdBaseEntity = new Gson().fromJson(queryResult, new TypeToken<JdBaseEntity<List<JdDetailEntity>>>() {
                            }.getType());

                            List<JdDetailEntity> data = jdBaseEntity.getData();
                            if (data != null && data.size() > 0) {
                                JdDetailEntity jdDetailEntity = data.get(0);
                                initDetailView(jdDetailEntity);
                            } else {
                                CommonUtil.showToast("未获取到详情");
                            }
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
                cancelLoadingDialog();
            }
        });
    }

    /**
     * 网站/APP获取推广链接
     */
    private void getCommonGet(int type) {
        showLoadingDialog();
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("app_key", Constant.APP_KEY_JD);
        map.put("method", HttpClient.JD_COMMON_GET);
        map.put("v", "1.0");
        map.put("sign_method", "md5");
        map.put("format", "json");
        map.put("timestamp", DateFormtUtils.getCurrentDate(DateFormtUtils.YMD_HMS));

        TreeMap<String, Object> buy_param_json = new TreeMap<>();
        TreeMap<String, Object> promotionCodeReq = new TreeMap<>();
        promotionCodeReq.put("materialId", materialEntity.getMaterialUrl());
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
                cancelLoadingDialog();
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
                            if (type == 1) {
                                ClipboardManager cm1 = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                cm1.setText(clickURL);
                                Uri uri = Uri.parse("weixin://");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            } else if (type == 2) {
                                Bundle bundle = new Bundle();
                                bundle.putString("url", clickURL);
                                startActivity(WebWiewActivity.class, bundle);
                            }
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
                cancelLoadingDialog();
            }
        });
    }
}