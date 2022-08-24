package com.ydh.couponstao.activitys;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.ydh.couponstao.R;
import com.ydh.couponstao.common.CommonDialog;
import com.ydh.couponstao.common.Constant;
import com.ydh.couponstao.common.bases.BaseActivity;
import com.ydh.couponstao.entitys.MaterialEntity;
import com.ydh.couponstao.entitys.TbCodeEntity;
import com.ydh.couponstao.entitys.TbDetailEntity;
import com.ydh.couponstao.http.ErrorEntity;
import com.ydh.couponstao.http.HttpClient;
import com.ydh.couponstao.interfaces.ViewInterface;
import com.ydh.couponstao.utils.CommonUtil;
import com.ydh.couponstao.utils.DateFormtUtils;
import com.ydh.couponstao.utils.HttpMd5;
import com.ydh.couponstao.utils.MsgCode;
import com.ydh.couponstao.utils.PicassoUtils;
import com.ydh.couponstao.utils.Strings;
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

public class TbDetailActivity extends BaseActivity {

    @BindView(R.id.return_btn)
    ImageView returnBtn;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_pict)
    ImageView ivPict;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_origin_price)
    TextView tvOriginPrice;
    @BindView(R.id.tv_buy_num)
    TextView tvBuyNum;
    @BindView(R.id.tv_shop_title)
    TextView tvShopTitle;
    @BindView(R.id.tv_coupon_money)
    TextView tvCouponMoney;
    @BindView(R.id.rv_shop_pic)
    RecyclerView rvShopPic;
    @BindView(R.id.tv_skip_tb)
    TextView tvSkipTb;
    @BindView(R.id.tv_commission)
    TextView tvCommission;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    private MaterialEntity materialEntity;
    private ArrayList<String> mImageUrlList = new ArrayList<>();
    private CommonAdapter<String> mImageAdapter;
    private CommonDialog mCommonDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(false);
        StatusBarUtil.setTransparent(this);
        setContentView(R.layout.activity_tb_detail);
        unBind = ButterKnife.bind(this);
        materialEntity = (MaterialEntity) getIntent().getSerializableExtra("materialEntity");
        initView();
        initAdapter();
        infoGet();
    }

    @OnClick({R.id.return_btn, R.id.tv_title, R.id.iv_share, R.id.tv_copy, R.id.tv_skip_tb, R.id.tv_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.return_btn:
                finish();
                break;
            case R.id.tv_title:
                ClipboardManager cm1 = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                cm1.setText(materialEntity.getTitle());
                CommonUtil.showToast("复制成功");
                break;
            case R.id.iv_share:
                tpwdCreate(2);//推广链接(click_url) 转淘口令 复制口令 跳转微信
                break;
            case R.id.tv_copy://复制
                tpwdCreate(1);//推广链接(click_url) 转淘口令 弹窗展示 二维码（coupon_share_url转短连）复制口令或者复制口令并跳转淘宝
                break;
            case R.id.tv_skip_tb:
                Bundle bundle = new Bundle();
                bundle.putString("url", "https:" + materialEntity.getCoupon_share_url());
                startActivity(WebWiewActivity.class, bundle);
                break;
            case R.id.tv_buy://立即抢购
                tpwdCreate();//领券链接(coupon_share_url) 转淘口令 复制淘口令 然后跳转淘宝
                break;
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

    public void infoGet() {
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
                        if (response.body() != null && response.body().getResults() != null && response.body().getResults().size() > 0) {
                            TbDetailEntity.DataBean detailDataBean = response.body().getResults().get(0);
                            String pict_url = detailDataBean.getPict_url();
                            PicassoUtils.setNetImg(pict_url, mContext, ivPict);
                            List<String> small_images = detailDataBean.getSmall_images();
                            if (small_images != null) {
                                mImageUrlList.clear();
                                mImageUrlList.addAll(small_images);
                            }
                            mImageAdapter.notifyDataSetChanged();
                        }
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

    private void initView() {
        double reserve_price = Strings.getDouble(materialEntity.getReserve_price());
        double coupon_start_fee = Strings.getDouble(materialEntity.getCoupon_start_fee());
        int coupon_amount = Strings.getInt(materialEntity.getCoupon_amount());
        double commission_rate = Strings.getDouble(materialEntity.getCommission_rate());
        if (reserve_price > coupon_start_fee) {//商品价格大于优惠券要求的最低价格
            tvOriginPrice.setText(Html.fromHtml("&yen") + "" + reserve_price);//原价
            tvOriginPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tvOriginPrice.getPaint().setAntiAlias(true);
            tvPrice.setText(Html.fromHtml("&yen") + Strings.getDecimalPointHandl(CommonUtil.getNnmber(reserve_price - coupon_amount)));//购买价格
            tvCouponMoney.setText(Html.fromHtml("&yen") + "" + coupon_amount);
            tvCommission.setText(Html.fromHtml("&yen") + CommonUtil.getNnmber((reserve_price - coupon_amount) * commission_rate / 100.0));
        } else {
            tvOriginPrice.setVisibility(View.GONE);
            tvPrice.setText(Html.fromHtml("&yen") + Strings.getDecimalPointHandl(CommonUtil.getNnmber(reserve_price)));
            tvCommission.setText(Html.fromHtml("&yen") + CommonUtil.getNnmber(reserve_price * commission_rate / 100.0));
        }
        tvBuyNum.setText(Strings.getString(materialEntity.getVolume()) + "人已购买");
        tvTitle.setText(Strings.getString(materialEntity.getTitle()));//商品名称
        tvShopTitle.setText(Strings.getString(materialEntity.getShop_title()));//店铺名称
    }

    /**
     * 淘宝客-公用-淘口令生成
     */
    private void tpwdCreate() {
        showLoadingDialog();
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("method", "taobao.tbk.tpwd.create");
        map.put("app_key", Constant.APP_KEY_TB);
        map.put("timestamp", DateFormtUtils.getCurrentDate(DateFormtUtils.YMD_HMS));
        map.put("sign_method", "md5");
        map.put("format", "json");
        map.put("v", "2.0");
        map.put("simplify", true);
        map.put("url", "https:" + materialEntity.getCoupon_share_url());
        String sign = HttpMd5.buildSignTb(map);
        map.put("sign", sign);
        Call<TbCodeEntity> call = HttpClient.getHttpApiTb().getMaterailTbCode(map);
        mNetWorkList.add(call);
        call.enqueue(new Callback<TbCodeEntity>() {
            @Override
            public void onResponse(Call<TbCodeEntity> call, Response<TbCodeEntity> response) {
                cancelLoadingDialog();
                if (response != null && response.isSuccessful() && response.body() != null) {
                    ErrorEntity error_response = response.body().getError_response();
                    if (error_response == null) {
                        TbCodeEntity.DataBean data = response.body().getData();
                        if (data != null) {
                            ClipboardManager cm1 = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                            cm1.setText(data.getModel());
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            intent.setClassName("com.taobao.taobao", "com.taobao.tao.welcome.Welcome");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            CommonUtil.showToast("暂无数据");
                        }
                    } else {
                        CommonUtil.showToast(MsgCode.getStrByCode(error_response.getCode()));
                    }
                }
            }

            @Override
            public void onFailure(Call<TbCodeEntity> call, Throwable t) {
                cancelLoadingDialog();
            }
        });
    }

    /**
     * 淘宝客-公用-淘口令生成
     */
    private void tpwdCreate(int mode) {
        showLoadingDialog();
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("method", "taobao.tbk.tpwd.create");
        map.put("app_key", Constant.APP_KEY_TB);
        map.put("timestamp", DateFormtUtils.getCurrentDate(DateFormtUtils.YMD_HMS));
        map.put("sign_method", "md5");
        map.put("format", "json");
        map.put("v", "2.0");
        map.put("simplify", true);
        map.put("url", "https:" + (TextUtils.isEmpty(materialEntity.getClick_url()) ? materialEntity.getCoupon_share_url() : materialEntity.getClick_url()));
        String sign = HttpMd5.buildSignTb(map);
        map.put("sign", sign);
        Call<TbCodeEntity> call = HttpClient.getHttpApiTb().getMaterailTbCode(map);
        mNetWorkList.add(call);
        call.enqueue(new Callback<TbCodeEntity>() {
            @Override
            public void onResponse(Call<TbCodeEntity> call, Response<TbCodeEntity> response) {
                cancelLoadingDialog();
                if (response != null && response.isSuccessful() && response.body() != null) {
                    ErrorEntity error_response = response.body().getError_response();
                    if (error_response == null) {
                        TbCodeEntity.DataBean data = response.body().getData();
                        if (data != null) {
                            if (mode == 1) {
                                mCommonDialog = new CommonDialog.Builder()
                                        .message("【" + materialEntity.getTitle() + "】\n【价格】" + tvOriginPrice.getText().toString() + "\n【券后价】" + tvPrice.getText().toString())
                                        .message2(data.getPassword_simple())
                                        .leftBtn("复制文案", new ViewInterface() {
                                            @Override
                                            public void onClick(View view) {
                                                ClipboardManager cm1 = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                                cm1.setText(data.getModel());
                                                CommonUtil.showToast("复制成功");
                                            }
                                        })
                                        .rightBtn("去淘宝", new ViewInterface() {
                                            @Override
                                            public void onClick(View view) {
                                                ClipboardManager cm1 = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                                cm1.setText(data.getModel());
                                                Intent intent = new Intent();
                                                intent.setAction("android.intent.action.VIEW");
                                                intent.setClassName("com.taobao.taobao", "com.taobao.tao.welcome.Welcome");
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                        }).build(mContext);
                                spreadGet();
                            } else if (mode == 2) {
                                ClipboardManager cm1 = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                cm1.setText(data.getModel());
                                Uri uri = Uri.parse("weixin://");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }

                        } else {
                            CommonUtil.showToast("暂无数据");
                        }
                    } else {
                        CommonUtil.showToast(MsgCode.getStrByCode(error_response.getCode()));
                    }
                }
            }

            @Override
            public void onFailure(Call<TbCodeEntity> call, Throwable t) {
                cancelLoadingDialog();
            }
        });
    }

    /**
     * 淘宝客-公用-长链转短链
     */
    private void spreadGet() {
        TreeMap<String, Object> map = new TreeMap<>();
        map.put("method", "taobao.tbk.spread.get");
        map.put("app_key", Constant.APP_KEY_TB);
        map.put("timestamp", DateFormtUtils.getCurrentDate(DateFormtUtils.YMD_HMS));
        map.put("sign_method", "md5");
        map.put("format", "json");
        map.put("v", "2.0");
        map.put("simplify", true);
        ArrayList<TreeMap<String, Object>> requests = new ArrayList<>();
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("url", "https:" + materialEntity.getCoupon_share_url());
        requests.add(treeMap);
        map.put("requests", new Gson().toJson(requests));
        String sign = HttpMd5.buildSignTb(map);
        map.put("sign", sign);
        Call<TbDetailEntity> call = HttpClient.getHttpApiTb().getSpreadGet(map);
        mNetWorkList.add(call);
        call.enqueue(new Callback<TbDetailEntity>() {
            @Override
            public void onResponse(Call<TbDetailEntity> call, Response<TbDetailEntity> response) {
                if (response != null && response.isSuccessful() && response.body() != null) {
                    ErrorEntity error_response = response.body().getError_response();
                    if (error_response == null) {
                        if (response.body() != null && response.body().getResults() != null && response.body().getResults().size() > 0) {
                            List<TbDetailEntity.DataBean> results = response.body().getResults();
                            mCommonDialog.setIvCode(results.get(0).getContent());
                        } else {
                            CommonUtil.showToast("二维码失败");
                        }
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
}