package com.ydh.couponstao.entitys;

import java.io.Serializable;

/**
 * Created by ydh on 2022/8/23
 * 佣金信息
 */
public class CommissionInfoEntity implements Serializable {

    /**
     * commission : 119.6
     * commissionShare : 20
     * couponCommission : 59.6
     * endTime : 2147529599000
     * isLock : 0
     * plusCommissionShare : 20
     * startTime : 1654531200000
     */

    private double commission;//佣金
    private double commissionShare;//佣金比例
    private double couponCommission;//券后佣金，（促销价-优惠券面额）*佣金比例
    private long endTime;//计划结束时间（时间戳，毫秒）
    private int isLock;//是否锁定佣金比例：1是，0否
    private double plusCommissionShare;//plus佣金比例，plus用户购买推广者能获取到的佣金比例
    private long startTime;//计划开始时间（时间戳，毫秒）

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getCommissionShare() {
        return commissionShare;
    }

    public void setCommissionShare(double commissionShare) {
        this.commissionShare = commissionShare;
    }

    public double getCouponCommission() {
        return couponCommission;
    }

    public void setCouponCommission(double couponCommission) {
        this.couponCommission = couponCommission;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getIsLock() {
        return isLock;
    }

    public void setIsLock(int isLock) {
        this.isLock = isLock;
    }

    public double getPlusCommissionShare() {
        return plusCommissionShare;
    }

    public void setPlusCommissionShare(double plusCommissionShare) {
        this.plusCommissionShare = plusCommissionShare;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
