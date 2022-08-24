package com.ydh.couponstao.entitys;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ydh on 2022/8/23
 */
public class JdMaterialEntity implements Serializable {
    private String brandName;//品牌名
    private String brandCode;//品牌code
    private String comments;//评论数
    private String goodCommentsShare;//好评率
    private String skuName;//商品名称
    private String skuId;//商品ID
    private String spuid;//其值为同款商品的主skuid
    private String materialUrl;//商品落地页

    private CommissionInfoEntity commissionInfo;//佣金信息
    private CouponInfoContentEntity couponInfo;//优惠券信息
    private ImageInfoContentEntity imageInfo;//第一个图片链接为主图链接
    private PriceInfoEntity priceInfo;//价格信息
    private ShopInfoEntity shopInfo;//店铺信息

    public String getGoodCommentsShare() {
        return goodCommentsShare;
    }

    public void setGoodCommentsShare(String goodCommentsShare) {
        this.goodCommentsShare = goodCommentsShare;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSpuid() {
        return spuid;
    }

    public void setSpuid(String spuid) {
        this.spuid = spuid;
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl;
    }

    public CommissionInfoEntity getCommissionInfo() {
        return commissionInfo;
    }

    public void setCommissionInfo(CommissionInfoEntity commissionInfo) {
        this.commissionInfo = commissionInfo;
    }

    public CouponInfoContentEntity getCouponInfo() {
        return couponInfo;
    }

    public void setCouponInfo(CouponInfoContentEntity couponInfo) {
        this.couponInfo = couponInfo;
    }

    public ImageInfoContentEntity getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(ImageInfoContentEntity imageInfo) {
        this.imageInfo = imageInfo;
    }

    public PriceInfoEntity getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(PriceInfoEntity priceInfo) {
        this.priceInfo = priceInfo;
    }

    public ShopInfoEntity getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShopInfoEntity shopInfo) {
        this.shopInfo = shopInfo;
    }

    public static class CouponInfoContentEntity implements Serializable{
        private List<CouponInfoEntity> couponList;

        public List<CouponInfoEntity> getCouponList() {
            return couponList;
        }

        public void setCouponList(List<CouponInfoEntity> couponList) {
            this.couponList = couponList;
        }
    }


    public static class PriceInfoEntity implements Serializable{

        /**
         * historyPriceDay : 180
         * lowestCouponPrice : 298
         * lowestPrice : 598
         * lowestPriceType : 1
         * price : 598
         */

        private int historyPriceDay;//历史最低价天数（例：当前券后价最近180天最低）
        private double lowestCouponPrice;//券后价（有无券都返回此字段）
        private double lowestPrice;//促销价
        private int lowestPriceType;//促销价类型，1：无线价格；2：拼购价格； 3：秒杀价格；4：预售价格
        private double price;//商品价格

        public int getHistoryPriceDay() {
            return historyPriceDay;
        }

        public void setHistoryPriceDay(int historyPriceDay) {
            this.historyPriceDay = historyPriceDay;
        }

        public double getLowestCouponPrice() {
            return lowestCouponPrice;
        }

        public void setLowestCouponPrice(double lowestCouponPrice) {
            this.lowestCouponPrice = lowestCouponPrice;
        }

        public double getLowestPrice() {
            return lowestPrice;
        }

        public void setLowestPrice(double lowestPrice) {
            this.lowestPrice = lowestPrice;
        }

        public int getLowestPriceType() {
            return lowestPriceType;
        }

        public void setLowestPriceType(int lowestPriceType) {
            this.lowestPriceType = lowestPriceType;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
