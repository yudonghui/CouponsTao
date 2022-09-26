package com.ydh.couponstao.entitys;


import com.ydh.couponstao.db.ClickEntity;

import java.util.List;

/**
 * Created by ydh on 2022/9/23
 */
public class DataEntity {
    private String name;
    private boolean isCycle;
    private long createTime;
    private List<ClickEntity> child;


    public boolean isCycle() {
        return isCycle;
    }

    public void setCycle(boolean cycle) {
        isCycle = cycle;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ClickEntity> getChild() {
        return child;
    }

    public void setChild(List<ClickEntity> child) {
        this.child = child;
    }
}
