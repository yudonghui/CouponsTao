package com.ydh.couponstao.entitys;

import java.io.Serializable;

/**
 * Created by ydh on 2022/8/23
 */
public class ShopInfoEntity implements Serializable {

    /**
     * afsFactorScoreRankGrade : 低
     * afterServiceScore : 8.64
     * commentFactorScoreRankGrade : 高
     * logisticsFactorScoreRankGrade : 低
     * logisticsLvyueScore : 8.72
     * scoreRankRate : 57.48
     * shopId : 11456097
     * shopLabel : 0
     * shopLevel : 4
     * shopName : 琼郁醇香官方旗舰店
     * userEvaluateScore : 10.00
     */

    private String afsFactorScoreRankGrade;//售后服务评级（仅pop店铺有值）
    private String afterServiceScore;//售后服务评分（仅pop店铺有值）
    private String commentFactorScoreRankGrade;//用户评价评级（仅pop店铺有值）
    private String logisticsFactorScoreRankGrade;//物流履约评级（仅pop店铺有值）
    private String logisticsLvyueScore;//物流履约评分（仅pop店铺有值）
    private String scoreRankRate;//店铺风向标（仅pop店铺有值）
    private String shopId;//店铺Id
    private String shopLabel;//1 代表京东好店
    private String shopLevel;//店铺评分
    private String shopName;//店铺名称
    private String userEvaluateScore;//用户评价评分（仅pop店铺有值）

    public String getAfsFactorScoreRankGrade() {
        return afsFactorScoreRankGrade;
    }

    public void setAfsFactorScoreRankGrade(String afsFactorScoreRankGrade) {
        this.afsFactorScoreRankGrade = afsFactorScoreRankGrade;
    }

    public String getAfterServiceScore() {
        return afterServiceScore;
    }

    public void setAfterServiceScore(String afterServiceScore) {
        this.afterServiceScore = afterServiceScore;
    }

    public String getCommentFactorScoreRankGrade() {
        return commentFactorScoreRankGrade;
    }

    public void setCommentFactorScoreRankGrade(String commentFactorScoreRankGrade) {
        this.commentFactorScoreRankGrade = commentFactorScoreRankGrade;
    }

    public String getLogisticsFactorScoreRankGrade() {
        return logisticsFactorScoreRankGrade;
    }

    public void setLogisticsFactorScoreRankGrade(String logisticsFactorScoreRankGrade) {
        this.logisticsFactorScoreRankGrade = logisticsFactorScoreRankGrade;
    }

    public String getLogisticsLvyueScore() {
        return logisticsLvyueScore;
    }

    public void setLogisticsLvyueScore(String logisticsLvyueScore) {
        this.logisticsLvyueScore = logisticsLvyueScore;
    }

    public String getScoreRankRate() {
        return scoreRankRate;
    }

    public void setScoreRankRate(String scoreRankRate) {
        this.scoreRankRate = scoreRankRate;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setShopLevel(String shopLevel) {
        this.shopLevel = shopLevel;
    }

    public String getShopLabel() {
        return shopLabel;
    }

    public void setShopLabel(String shopLabel) {
        this.shopLabel = shopLabel;
    }


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getUserEvaluateScore() {
        return userEvaluateScore;
    }

    public void setUserEvaluateScore(String userEvaluateScore) {
        this.userEvaluateScore = userEvaluateScore;
    }
}
