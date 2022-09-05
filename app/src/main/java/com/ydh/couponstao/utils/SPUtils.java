package com.ydh.couponstao.utils;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.ydh.couponstao.MyApplication;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by zhengluping on 2017/12/28.
 * 缓存
 */
public class SPUtils {

    //用户账号文件
    public static final String FILE_ACCOUNT = "cache_account";
    public static final String ACCOUNT = "account"; //用户账户
    public static final String PASSWORD = "password"; //用户密码
    // public static final String IS_REMEMBER_PASSWORD = "is_remember_password"; //是否记住密码

    //用户信息文件
    public static final String FILE_USER = "cache_user";
    public static final String TOKEN = "token"; //用户token
    public static final String EMP_ID = "empId"; //咨询师ID
    public static final String USER_ID = "user_id"; //用户ID
    public static final String USER_NAME = "userName"; //咨询师姓名
    //存储信息，除非卸载否则一直存在
    public static final String FILE_DATA = "file_data";
    public static final String IS_FIRST = "is_first"; //是否第一次打开


    /**
     * 用于存储String类型的数据
     *
     * @param fileNaem 缓存的文件名
     * @param spNaem   缓存数据的key
     * @param spValue  缓存数据的值
     */
    public static void setCache(String fileNaem, String spNaem, String spValue) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences(fileNaem, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(spNaem, spValue);
        editor.commit();
    }

    /**
     * 用于存储String类型的数据
     *
     * @param fileNaem 缓存的文件名
     * @param spNaem   缓存数据的key
     * @param spValue  缓存数据的值
     */
    public static void setCache(String fileNaem, String[] spNaem, String[] spValue) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences(fileNaem, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for (int i = 0; i < spNaem.length; i++) {
            editor.putString(spNaem[i], spValue[i]);
        }
        editor.commit();
    }

    /**
     * 获取缓存值
     *
     * @param fileNaem
     * @param spNaem
     * @return 缓存数据的值
     */
    public static String getCache(String fileNaem, String spNaem) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences(fileNaem, MODE_PRIVATE);
        String spValue = sp.getString(spNaem, "");
        return spValue;
    }


    public static void clearCache(String fileName) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences(fileName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }


}
