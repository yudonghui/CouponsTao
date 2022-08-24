package com.ydh.couponstao.entitys;

import java.io.Serializable;

/**
 * Created by ydh on 2022/8/23
 */
public class CouponInfoEntity implements Serializable {

    /**
     * bindType : 3
     * discount : 300
     * getEndTime : 1661443198000
     * getStartTime : 1660294018000
     * hotValue : 2
     * isBest : 1
     * link : http://coupon.m.jd.com/coupons/show.action?key=d7794bc9e8194faf9ec9629a7f1016bd&amp;roleId=83610320&amp;to=item.jd.com/10053653976621.html
     * platformType : 0
     * quota : 500
     * useEndTime : 1661443199000
     * useStartTime : 1660233600000
     */

    private int bindType;//券种类 (优惠券种类：0 - 全品类，1 - 限品类（自营商品），2 - 限店铺，3 - 店铺限商品券)
    private int discount;//券面额
    private long getEndTime;//券领取结束时间(时间戳，毫秒)
    private long getStartTime;//领取开始时间(时间戳，毫秒)
    private int hotValue;//券热度，值越大热度越高，区间:[0,10]
    private int isBest;//最优优惠券，1：是；0：否，购买一件商品可使用的面额最大优惠券
    private String link;//券链接
    private int platformType;//券使用平台 (平台类型：0 - 全平台券，1 - 限平台券)
    private int quota;//券消费限额
    private long useEndTime;//券有效使用结束时间(时间戳，毫秒)
    private long useStartTime;//券有效使用开始时间(时间戳，毫秒)

    public int getBindType() {
        return bindType;
    }

    public void setBindType(int bindType) {
        this.bindType = bindType;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public long getGetEndTime() {
        return getEndTime;
    }

    public void setGetEndTime(long getEndTime) {
        this.getEndTime = getEndTime;
    }

    public long getGetStartTime() {
        return getStartTime;
    }

    public void setGetStartTime(long getStartTime) {
        this.getStartTime = getStartTime;
    }

    public int getHotValue() {
        return hotValue;
    }

    public void setHotValue(int hotValue) {
        this.hotValue = hotValue;
    }

    public int getIsBest() {
        return isBest;
    }

    public void setIsBest(int isBest) {
        this.isBest = isBest;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getPlatformType() {
        return platformType;
    }

    public void setPlatformType(int platformType) {
        this.platformType = platformType;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public long getUseEndTime() {
        return useEndTime;
    }

    public void setUseEndTime(long useEndTime) {
        this.useEndTime = useEndTime;
    }

    public long getUseStartTime() {
        return useStartTime;
    }

    public void setUseStartTime(long useStartTime) {
        this.useStartTime = useStartTime;
    }
}
