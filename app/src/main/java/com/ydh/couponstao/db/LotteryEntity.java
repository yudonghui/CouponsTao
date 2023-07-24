package com.ydh.couponstao.db;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Date:2023/7/4
 * Time:10:57
 * author:ydh
 */
@Entity
public class LotteryEntity {
    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    private long id;
    private int type = 0;//0大乐透，1双色球
    private String lotteryResult;//中奖号码
    private String lotteryNum;//期号

    public LotteryEntity() {
    }

    @Ignore
    public LotteryEntity(int type, long id, String lotteryResult, String lotteryNum) {
        this.id = id;
        this.type = type;
        this.lotteryResult = lotteryResult;
        this.lotteryNum = lotteryNum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLotteryResult() {
        return lotteryResult;
    }

    public void setLotteryResult(String lotteryResult) {
        this.lotteryResult = lotteryResult;
    }

    public String getLotteryNum() {
        return lotteryNum;
    }

    public void setLotteryNum(String lotteryNum) {
        this.lotteryNum = lotteryNum;
    }
}
