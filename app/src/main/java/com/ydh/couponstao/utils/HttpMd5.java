package com.ydh.couponstao.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import okio.HashingSink;

/**
 * Created by ydh on 2022/8/15
 */
public class HttpMd5 {
    private static String secretTb = "73aea5ebcbeedec91aa6ff10b3a8c416";//密钥
    private static String secretJd = "c7b290141c2145ba9d052a563b5a03b6";//密钥

    public static String buildSignTb(Map<String, Object> map) {
        StringBuffer buf = new StringBuffer(secretTb);
        String sign;
        try {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                buf.append(entry.getKey()).append(entry.getValue());
            }
            buf.append(secretTb);
            sign = getMD5(buf.toString());
        } catch (Exception e) {
            return null;
        }
        return sign;
    }
    public static String buildSignJd(Map<String, Object> map) {
        StringBuffer buf = new StringBuffer(secretJd);
        String sign;
        try {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                buf.append(entry.getKey()).append(entry.getValue());
            }
            buf.append(secretJd);
            sign = getMD5(buf.toString());
        } catch (Exception e) {
            return null;
        }
        return sign;
    }
    public static String getMD5(String str) {
        String md5 = "";
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            byte[] digest = instance.digest(str.getBytes());
            md5 = bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        int dig = 0;
        for (byte b : bytes) {
            dig = b;
            if (dig < 0) {
                dig += 256;
            }
            if (dig < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(dig));
        }
        return sb.toString().toUpperCase();
    }

}

