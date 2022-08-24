package com.ydh.couponstao.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ydh.couponstao.R;
import com.ydh.couponstao.activitys.JdDetailActivity;
import com.ydh.couponstao.activitys.TbDetailActivity;
import com.ydh.couponstao.entitys.CommissionInfoEntity;
import com.ydh.couponstao.entitys.CouponInfoEntity;
import com.ydh.couponstao.entitys.ImageInfoContentEntity;
import com.ydh.couponstao.entitys.JdMaterialEntity;
import com.ydh.couponstao.entitys.MaterialEntity;
import com.ydh.couponstao.utils.CommonUtil;
import com.ydh.couponstao.utils.PicassoUtils;
import com.ydh.couponstao.utils.Strings;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by ydh on 2022/8/23
 */
public class MaterialJdAdapter extends CommonAdapter<JdMaterialEntity> {
    public MaterialJdAdapter(Context context, int layoutId, List<JdMaterialEntity> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, JdMaterialEntity materialEntity, int position) {
        ImageView mIvPhoto = holder.getView(R.id.iv_photo);
        TextView mTvOriginPrice = holder.getView(R.id.tv_origin_price);
        TextView mTvPrice = holder.getView(R.id.tv_price);
        TextView mTvCoupon = holder.getView(R.id.tv_coupon);//优惠券
        List<ImageInfoContentEntity.ImageInfoEntity> imageList = materialEntity.getImageInfo().getImageList();
        if (imageList != null && imageList.size() > 0) {
            PicassoUtils.setNetImg(imageList.get(0).getUrl(), mContext, mIvPhoto);
        } else {
            mIvPhoto.setImageResource(0);
        }
        holder.setText(R.id.tv_product_name, Strings.getString(materialEntity.getSkuName()));//商品名称
        holder.setText(R.id.tv_product_name, Strings.getString(materialEntity.getShopInfo().getShopName()));
        JdMaterialEntity.CouponInfoContentEntity couponInfo = materialEntity.getCouponInfo();
        if (couponInfo != null && couponInfo.getCouponList() != null && couponInfo.getCouponList().size() > 0) {
            mTvCoupon.setVisibility(View.VISIBLE);
            List<CouponInfoEntity> couponList = couponInfo.getCouponList();
            for (int i = 0; i < couponList.size(); i++) {
                int isBest = couponList.get(i).getIsBest();
                if (isBest == 1) {
                    CouponInfoEntity couponInfoEntity = couponList.get(i);
                    int discount = couponInfoEntity.getDiscount();
                    mTvCoupon.setText("券" + discount);
                    break;
                }
            }
        } else {
            mTvCoupon.setVisibility(View.GONE);
        }
        JdMaterialEntity.PriceInfoEntity priceInfo = materialEntity.getPriceInfo();
        if (priceInfo != null) {
            mTvOriginPrice.setText(Html.fromHtml("&yen") + "" + priceInfo.getPrice());
            mTvOriginPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            mTvOriginPrice.getPaint().setAntiAlias(true);
            mTvPrice.setText(Html.fromHtml("&yen") + "" + priceInfo.getLowestCouponPrice());
        } else {
            mTvOriginPrice.setText("");
            mTvPrice.setText("");
        }
        holder.setText(R.id.tv_comments, Strings.getString(materialEntity.getComments()) + "条评价");
        holder.setText(R.id.tv_good_comments_share, Strings.getString(materialEntity.getGoodCommentsShare()) + "%好评");
        holder.setOnClickListener(R.id.ll_content, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("materialEntity", materialEntity);
                Intent intent = new Intent(mContext, JdDetailActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        holder.setOnLongClickListener(R.id.tv_product_name, new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cm1 = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                cm1.setText(materialEntity.getSkuName());
                CommonUtil.showToast("复制成功");
                return true;
            }
        });
    }
}
