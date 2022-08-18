package com.ydh.couponstao.utils;

/**
 * Created by ydh on 2022/8/15
 */
public class ClickUtils {
    private static long lastClickTime;

    public synchronized static boolean isFastClickShort() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public synchronized static boolean isFastClickLong() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 3000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}
