package com.ydh.couponstao.db;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by ydh on 2022/9/21
 */
@Entity
public class ClickEntity {
    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    private int id;
    private long createTime;//创建日期
    private long upTime;//上次操作时间
    private long actionTime;//操作时间
    private String type;//操作方式（滑动，点击等）
    private String name;//操作名称
    private boolean isCycle;//是否循环
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public ClickEntity() {
    }

    @Ignore
    public ClickEntity(long createTime) {
        this.createTime = createTime;
    }

    @Ignore
    public ClickEntity(long createTime, long upTime, long actionTime, String name, boolean isCycle) {
        this.createTime = createTime;
        this.upTime = upTime;
        this.actionTime = actionTime;
        this.name = name;
        this.isCycle = isCycle;
    }

    @Ignore
    public ClickEntity(long createTime, long upTime, long actionTime, String type, int startX, int startY) {
        this.createTime = createTime;
        this.upTime = upTime;
        this.actionTime = actionTime;
        this.type = type;
        this.startX = startX;
        this.startY = startY;
    }

    @Ignore
    public ClickEntity(long createTime,long upTime, long actionTime, String type, int startX, int startY, int endX, int endY) {
        this.createTime = createTime;
        this.actionTime = actionTime;
        this.type = type;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public boolean isCycle() {
        return isCycle;
    }

    public void setCycle(boolean cycle) {
        isCycle = cycle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpTime() {
        return upTime;
    }

    public void setUpTime(long upTime) {
        this.upTime = upTime;
    }

    public long getActionTime() {
        return actionTime;
    }

    public void setActionTime(long actionTime) {
        this.actionTime = actionTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }
}
