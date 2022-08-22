package com.ydh.couponstao.utils;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;


public class Strings {
    public static String getString(String string) {
        return TextUtils.isEmpty(string) ? "" : string;
    }

    public static String getString(Object string) {
        if (string == null) return "";
        if (string instanceof String)
            return (String) string;
        return "";
    }

    public static String getStringS(String string) {
        return TextUtils.isEmpty(string) ? "暂无" : string;
    }

    public static String getStringSn(String string) {
        return TextUtils.isEmpty(string) ? "无" : string;
    }

    public static String getStringL(String string) {
        return TextUtils.isEmpty(string) ? "-" : string;
    }

    public static String getThreeMerge(String one, String two, String three) {
        if (TextUtils.isEmpty(one) && TextUtils.isEmpty(two) && TextUtils.isEmpty(three)) {
            return "";
        } else if ((!TextUtils.isEmpty(one)) && TextUtils.isEmpty(two) && TextUtils.isEmpty(three)) {
            return one;
        } else if ((!TextUtils.isEmpty(one)) && (!TextUtils.isEmpty(two)) && TextUtils.isEmpty(three)) {
            return one + "/" + two;
        } else {
            return one + "/" + two + "/" + three;
        }
    }

    public static String getTwoMerge(String one, String two) {
        if (TextUtils.isEmpty(one) && TextUtils.isEmpty(two)) {
            return "";
        } else if ((!TextUtils.isEmpty(one)) && TextUtils.isEmpty(two)) {
            return one;
        } else {
            return one + "/" + two;
        }
    }

    public static String getStringN(String string) {
        if (TextUtils.isEmpty(string) || "null".equals(string)) {
            return "";
        }
        return string;
    }

    public static String getSecretPhone(String string) {
        if (TextUtils.isEmpty(string) || "null".equals(string)) {
            return "";
        }
        if (string.length() != 11) return string;
        return string.substring(0, 3) + "****" + string.substring(7);
    }

    public static String getString0(String string) {
        if (TextUtils.isEmpty(string) || "null".equals(string)) {
            return "0";
        }
        return string;
    }

    public static boolean isEmty0(String string) {
        if (TextUtils.isEmpty(string) || "0".equals(string)) {
            return true;
        }
        return false;
    }

    public static int getIntByString(String string) {
        if (TextUtils.isEmpty(string) || "null".equals(string)) {
            return 0;
        }
        return Integer.parseInt(string);
    }

    public static int getIntNo0(String string) {
        if (TextUtils.isEmpty(string) || "null".equals(string) || "0".equals(string)) {
            return 1;
        }
        try {
            int anInt = Integer.parseInt(string);
            return anInt;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * @param str1 是否相等
     */
    public static boolean equals(String str1, String str2) {
        if (!TextUtils.isEmpty(str1)) {
            return str1.equals(str2);
        } else {
            if (TextUtils.isEmpty(str2)) {
                return true;//str1和str2都是空
            } else {
                return false;//str1空 str2不为空
            }
        }
    }

    public static boolean equals(String str1, String str2, String str3) {
        if (TextUtils.isEmpty(str1) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3))
            return false;
        return str1.equals(str2) && str1.equals(str3);
    }

    public static double getDouble(String string) {
        if (TextUtils.isEmpty(string)) return 0;
        try {
            double doub = Double.parseDouble(string);
            return doub;
        } catch (Exception e) {
            return 0;
        }
    }

    public static long getLong(String string) {
        if (TextUtils.isEmpty(string)) return 0;
        try {
            long anInt = Long.parseLong(string);
            return anInt;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getInt(String string) {
        if (TextUtils.isEmpty(string)) return 0;
        try {
            int anInt = Integer.parseInt(string);
            return anInt;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 默认返回 1
     */
    public static int getInt1(String string) {
        if (TextUtils.isEmpty(string)) return 1;
        try {
            int anInt = Integer.parseInt(string);
            return anInt;
        } catch (Exception e) {
            return 1;
        }
    }

    public static int getIntMax(String string) {
        if (TextUtils.isEmpty(string) || "0".equals(string)) return 100000;
        try {
            int anInt = Integer.parseInt(string);
            return anInt;
        } catch (Exception e) {
            return 0;
        }
    }


    public static SpannableString getSpannable(String text1, int resId1, String text2, int resId2) {
        String content = text1 + text2;
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(resId1), 0, text1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(resId2), text1.length(), content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static String getStrByInt(int aint) {
        if (aint < 10) {
            return "0" + aint;
        } else {
            return aint + "";
        }
    }

    public static String getStrByStr(String money) {
        double moneyInt = getDouble(money);
        String nnmber;
        if (moneyInt >= 10000 && moneyInt < 99999999) {//1万到一千万
            nnmber = CommonUtil.getNnmber(moneyInt / 10000.0);
            return getDecimalPointHandl(nnmber) + "万";
        } else if (moneyInt < 10000) {
            return moneyInt + "";
        } else {
            nnmber = CommonUtil.getNnmber(moneyInt / 100000000.0);
            return getDecimalPointHandl(nnmber) + "亿";
        }

    }

    public static String getDecimalPointHandl(String nnmber) {
        if (nnmber.endsWith("00")) {
            return nnmber.substring(0, nnmber.length() - 3);
        } else if (nnmber.endsWith("0")) {
            return nnmber.substring(0, nnmber.length() - 1);
        } else {
            return nnmber;
        }
    }
}
