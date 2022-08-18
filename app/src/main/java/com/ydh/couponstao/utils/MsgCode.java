package com.ydh.couponstao.utils;

/**
 * Created by ydh on 2022/8/15
 */
public class MsgCode {
    public static String getStrByCode(int code) {
        String str = "";
        switch (code) {
            case 21:
                str = "错误";
                break;
            case 25:
                str = "无效签名";
                break;
            default:
                str = "网络错误";
                break;
        }
        return str;
    }
}
