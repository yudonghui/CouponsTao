package com.ydh.couponstao.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Created by ydh on 2022/9/21
 */
@Dao
public interface LotteryDao {
    @Query("SELECT * FROM lotteryentity  ORDER BY lotteryNum DESC")
    List<LotteryEntity> queryAll();

    @Query("SELECT * FROM lotteryentity  WHERE type=:type ORDER BY lotteryNum DESC")
    List<LotteryEntity> query(int type);

    @Insert
    void insert(LotteryEntity clickEntity);

    @Insert
    void insert(LotteryEntity... clickEntitys);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<LotteryEntity> clickEntitys);

    @Update
    void update(LotteryEntity clickEntity);

    @Query("DELETE  FROM lotteryentity WHERE lotteryNum=:looteryNum")
    void delete(String looteryNum);
}
