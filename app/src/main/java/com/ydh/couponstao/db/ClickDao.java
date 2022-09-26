package com.ydh.couponstao.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Created by ydh on 2022/9/21
 */
@Dao
public interface ClickDao {
    @Query("SELECT * FROM clickentity  ORDER BY actionTime ASC")
    List<ClickEntity> queryAll();

    /**
     * 用过创建时间获取所有的事件，并按照操作时间排序
     */
    @Query("SELECT * FROM clickentity WHERE createTime = :createTime ORDER BY actionTime ASC")
    List<ClickEntity> queryByTime(Long createTime);

    @Insert
    void insert(ClickEntity clickEntity);

    @Insert
    void insert(ClickEntity... clickEntitys);

    @Insert
    void insert(List<ClickEntity> clickEntitys);

    @Update
    void update(ClickEntity clickEntity);

    @Query("DELETE  FROM clickentity WHERE createTime=:createTime")
    void delete(Long createTime);
}
