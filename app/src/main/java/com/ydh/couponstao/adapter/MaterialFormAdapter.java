package com.ydh.couponstao.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ydh.couponstao.R;
import com.ydh.couponstao.activitys.TbDetailActivity;
import com.ydh.couponstao.entitys.MaterialEntity;
import com.ydh.couponstao.utils.ClipboardUtils;
import com.ydh.couponstao.utils.CommonUtil;
import com.ydh.couponstao.utils.PicassoUtils;
import com.ydh.couponstao.utils.Strings;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by ydh on 2022/8/23
 */
public class MaterialFormAdapter extends CommonAdapter<MaterialEntity> {
    public MaterialFormAdapter(Context context, int layoutId, List<MaterialEntity> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MaterialEntity materialEntity, int position) {
        ImageView mIvPhoto = holder.getView(R.id.iv_photo);
        TextView mTvOriginPrice = holder.getView(R.id.tv_origin_price);
        TextView mTvPrice = holder.getView(R.id.tv_price);
        TextView mTvProductName = holder.getView(R.id.tv_product_name);
        TextView mTvShopName= holder.getView(R.id.tv_shop_name);
        TextView mTvCoupon = holder.getView(R.id.tv_coupon);
        String pict_url = materialEntity.getPict_url();
        PicassoUtils.setNetImg(pict_url.startsWith("http") ? pict_url : ("https:" + pict_url), mContext, mIvPhoto);
        mTvProductName.setText(Strings.getString(materialEntity.getTitle()));
        String shop_title = materialEntity.getShop_title();
        if (TextUtils.isEmpty(shop_title)){
            mTvShopName.setVisibility(View.GONE);
        }else {
            mTvShopName.setVisibility(View.VISIBLE);
            mTvShopName.setText(shop_title);
        }
        double reserve_price = Strings.getDouble(materialEntity.getReserve_price());
        double coupon_start_fee = Strings.getDouble(materialEntity.getCoupon_start_fee());//优惠券起用门槛
        int coupon_amount = Strings.getInt(materialEntity.getCoupon_amount());
        double aDouble = Strings.getDouble(materialEntity.getCommission_rate());
        double commission_rate = aDouble > 100 ? aDouble / 100.0 : aDouble;
        if (reserve_price > coupon_start_fee) {//商品价格大于优惠券要求的最低价格
            mTvOriginPrice.setText(Html.fromHtml("&yen") + "" + reserve_price);
            mTvOriginPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            mTvOriginPrice.getPaint().setAntiAlias(true);
            mTvPrice.setText(Html.fromHtml("&yen") + Strings.getDecimalPointHandl(CommonUtil.getNnmber(reserve_price - coupon_amount)));
            if (coupon_amount > 0) {
                mTvCoupon.setVisibility(View.VISIBLE);
                mTvCoupon.setText("券" + coupon_amount);
                holder.setText(R.id.tv_commission, "预估返" + CommonUtil.getNnmber((reserve_price - coupon_amount) * commission_rate / 100.0));
            } else {
                mTvCoupon.setVisibility(View.GONE);
            }
        } else {
            mTvOriginPrice.setVisibility(View.GONE);
            mTvCoupon.setVisibility(View.INVISIBLE);
            mTvPrice.setText(Html.fromHtml("&yen") + Strings.getDecimalPointHandl(CommonUtil.getNnmber(reserve_price)));
            holder.setText(R.id.tv_commission, "预估返" + CommonUtil.getNnmber(reserve_price * commission_rate / 100.0));
        }
        holder.setText(R.id.tv_volume, Strings.getString(materialEntity.getVolume()) + "+人付款");
        holder.setOnClickListener(R.id.ll_content, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("materialEntity", materialEntity);
                Intent intent = new Intent(mContext, TbDetailActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                //tpwdCreate(materialEntity, mTvPrice.getText().toString(), mTvOriginPrice.getText().toString());
            }
        });

        mTvProductName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardUtils.setClipboard(materialEntity.getTitle());
                return false;
            }
        });
    }
}
