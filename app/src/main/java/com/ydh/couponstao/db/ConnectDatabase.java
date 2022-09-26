package com.ydh.couponstao.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ClickEntity.class}, version = 1, exportSchema = false)
public abstract class ConnectDatabase extends RoomDatabase {

    private static final String DB_NAME = "ConnectDatabase.db";
    private static volatile ConnectDatabase instance;

    public static synchronized ConnectDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static ConnectDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                ConnectDatabase.class,
                DB_NAME).build();
    }

    public abstract ClickDao getClickDao();

}
