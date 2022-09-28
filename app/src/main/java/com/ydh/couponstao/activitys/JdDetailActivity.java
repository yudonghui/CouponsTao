package com.ydh.couponstao.activitys;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ydh.couponstao.R;
import com.ydh.couponstao.adapter.MaterialJdAdapter;
import com.ydh.couponstao.common.Constant;
import com.ydh.couponstao.common.SpaceItemDecoration;
import com.ydh.couponstao.common.bases.BaseTaoActivity;
import com.ydh.couponstao.entitys.CouponInfoEntity;
import com.ydh.couponstao.entitys.ImageInfoContentEntity;
import com.ydh.couponstao.entitys.JdBaseEntity;
import com.ydh.couponstao.entitys.JdDetailEntity;
import com.ydh.couponstao.entitys.JdMaterialEntity;
import com.ydh.couponstao.entitys.UrlEntity;
import com.ydh.couponstao.http.HttpClient;
import com.ydh.couponstao.utils.ClipboardUtils;
import com.ydh.couponstao.utils.CommonUtil;
import com.ydh.couponstao.utils.DateFormtUtils;
import com.ydh.couponstao.utils.HttpMd5;
import com.ydh.couponstao.utils.PicassoUtils;
import com.ydh.couponstao.utils.Strings;
import com.zhy.adapter.recyclerview.CommonAdapter;

import org.json.JSONObject;

import java.io.Serializable;
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

public class JdDetailActivity extends BaseTaoActivity {

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
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.rv_material)
    RecyclerView rvMaterial;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    private JdMaterialEntity materialEntity;
    private List<ImageView> mLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(false);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_jd_detail);
        unBind = ButterKnife.bind(this);
        materialEntity = (JdMaterialEntity) getIntent().getSerializableExtra("materialEntity");
        initView();
        initAdapter();
        initDetailData();
        initData();
        initListener();
    }

    @OnClick({R.id.return_btn, R.id.tv_title, R.id.iv_share, R.id.tv_skip_jd, R.id.tv_skip_web})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.tv_title:
                ClipboardUtils.setClipboard(materialEntity.getSkuName());
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

    int curPosition = 0;

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
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                curPosition = i;
                tvNum.setText((curPosition + 1) + "/" + mLists.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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
        mMaterialAdapter = new MaterialJdAdapter(mContext, R.layout.item_jing_dong, mMaterialList);
        rvMaterial.addItemDecoration(new SpaceItemDecoration(CommonUtil.dp2px(10), SpaceItemDecoration.STAGGEREDGRIDLAYOUT));
        rvMaterial.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvMaterial.setAdapter(mMaterialAdapter);
    }

    /**
     *
     */
    private void initViewPageAdapter(List<String> mImageUrlList) {
        mLists.clear();
        for (int i = 0; i < mImageUrlList.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            int finalI = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PictureActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", finalI);
                    bundle.putSerializable("mUrlList", (Serializable) mImageUrlList);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });
            PicassoUtils.setNetImg(mImageUrlList.get(i), mContext, imageView);
            mLists.add(imageView);
        }
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            public int getCount() {
                return mLists == null ? 0 : mLists.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(mLists.get(position));
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(mLists.get(position));
                return mLists.get(position);
            }
        };
        viewPager.setAdapter(mPagerAdapter);
    }

    private void initDetailView(JdDetailEntity jdDetailEntity) {
        if (jdDetailEntity.getImageInfo() != null && jdDetailEntity.getImageInfo().getImageList() != null && jdDetailEntity.getImageInfo().getImageList().size() > 0) {
            List<ImageInfoContentEntity.ImageInfoEntity> imageList = jdDetailEntity.getImageInfo().getImageList();
            ArrayList<String> strings = new ArrayList<>();
            for (int i = 0; i < imageList.size(); i++) {
                strings.add(imageList.get(i).getUrl());
            }
            tvNum.setText(1 + "/" + strings.size());
            initViewPageAdapter(strings);
        } else {
            String[] split = jdDetailEntity.getDetailImages().split(",");
            if (split.length > 0) {
                initViewPageAdapter(Arrays.asList(split));
            }
            tvNum.setText(1 + "/" + split.length);
        }
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
                                ClipboardUtils.setClipboardNo(materialEntity.getSkuName());
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

    private int pageIndex = 1;
    private int pageSize = 10;
    private List<JdMaterialEntity> mMaterialList = new ArrayList<>();
    private CommonAdapter<JdMaterialEntity> mMaterialAdapter;

    public void initData() {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("app_key", Constant.APP_KEY_JD);
        map.put("method", HttpClient.JD_MATERIAL_QUERY);
        map.put("v", "1.0");
        map.put("sign_method", "md5");
        map.put("format", "json");
        map.put("timestamp", DateFormtUtils.getCurrentDate(DateFormtUtils.YMD_HMS));

        TreeMap<String, Object> buy_param_json = new TreeMap<>();
        TreeMap<String, Object> goodsReq = new TreeMap<>();
        goodsReq.put("eliteId", 1);
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
                            JSONObject jd_union_open_goods_jingfen_query_responce = jsonObject.getJSONObject("jd_union_open_goods_material_query_responce");
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