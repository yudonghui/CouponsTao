package com.ydh.couponstao.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.ydh.couponstao.MyApplication;

import java.security.MessageDigest;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by ydh on 2022/10/19
 */
public class DeviceIdUtil {
    public static String getDeviceId() {
        StringBuilder sbDeviceId = new StringBuilder();
//        imei null
        String imei = getIMEI();
        //        imei null

//        手机型号 +手机
        String androidID = getAndroidId();

//        serial  8.0 拿不到    8.0拿到了
//         唯一
        String serial = getSerial();
//        UUID  uuid----》
        String id = getDeviceUUID().replace("-", "");
//追加imei
        if (imei != null && imei.length() > 0) {
            sbDeviceId.append(imei);
            sbDeviceId.append("|");
        }
        //追加androidid
        if (androidID != null && androidID.length() > 0) {
            sbDeviceId.append(androidID);
            sbDeviceId.append("|");
        }
        //追加serial
        if (serial != null && serial.length() > 0) {
            sbDeviceId.append(serial);
            sbDeviceId.append("|");
        }
        //追加硬件uuid
        if (id != null && id.length() > 0) {
            sbDeviceId.append(id);
        }
//        一系列的字符串  ----11 硬件标识有关   手机
        //生成SHA1，统一DeviceId长度
        if (sbDeviceId.length() > 0) {
//                    md  ----
            try {
                byte[] hash = getHashByString(sbDeviceId.toString());
                String sha1 = bytesToHex(hash);
                if (sha1 != null && sha1.length() > 0) {
                    //返回最终的DeviceId
                    return sha1;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return null;

    }

    /**
     * 转16进制字符串
     *
     * @param data 数据
     * @return 16进制字符串
     */
    private static String bytesToHex(byte[] data) {
        StringBuilder sb = new StringBuilder();
        String stmp;
        for (int n = 0; n < data.length; n++) {
            stmp = (Integer.toHexString(data[n] & 0xFF));
            if (stmp.length() == 1)
                sb.append("0");
            sb.append(stmp);
        }
        return sb.toString().toUpperCase(Locale.CHINA);
    }

    /**
     * 取SHA1
     *
     * @param data 数据
     * @return 对应的hash值
     */
    private static byte[] getHashByString(String data) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.reset();
            messageDigest.update(data.getBytes("UTF-8"));
            return messageDigest.digest();
        } catch (Exception e) {
            return "".getBytes();
        }
    }

    // //获得硬件uuid（根据硬件相关属性，生成uuid）（无需权限）  数字  0   -10
    private static String getDeviceUUID() {
        String dev = "100001" + Build.BOARD +
                Build.BRAND +
                Build.DEVICE +
                Build.HARDWARE +
                Build.ID +
                Build.MODEL +
                Build.PRODUCT +
                Build.SERIAL;
        return new UUID(dev.hashCode(), Build.SERIAL.hashCode()).toString();
    }

    private static String getSerial() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Build.getSerial();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * 获得设备的AndroidId
     *
     * @return 设备的AndroidId
     */
    private static String getAndroidId() {
        try {
            return Settings.Secure.getString(MyApplication.getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    //需要获得READ_PHONE_STATE权限，>=6.0，默认返回null
    private static String getIMEI() {
        try {
            TelephonyManager tm = (TelephonyManager)
                    MyApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

}
