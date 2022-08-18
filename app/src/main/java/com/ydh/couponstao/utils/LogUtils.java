package com.ydh.couponstao.utils;

import android.util.Log;


import com.ydh.couponstao.common.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by UI on 2018/9/19.
 */

public class LogUtils {
    /**
     * 异常栈位移
     */
    private static final int EXCEPTION_STACK_INDEX = 2;

    // private static final File DIR = new
    // File(Environment.getExternalStorageDirectory() + "/pptv/log/");

    // private static final String FILE_NAME = "log.txt";

    @SuppressWarnings("unused")
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static boolean ISLOG = Constant.ISSUE;

    public static void init(boolean isLog) {
        ISLOG = isLog;
    }

    /**
     * verbose级别的日志
     *
     * @param msg 打印内容
     * @see [类、类#方法、类#成员]
     */
    public static void v(String msg) {
        if (ISLOG) {
            Log.v(getTag(), msg);
        }
    }

    /**
     * debug级别的日志
     *
     * @param msg 打印内容
     * @see [类、类#方法、类#成员]
     */
    public static void d(String msg) {
        if (ISLOG) {
            Log.d(getTag(), msg);
        }
    }

    /**
     * info级别的日志
     *
     * @param msg 打印内容
     * @see [类、类#方法、类#成员]
     */
    public static void i(String msg) {
        if (ISLOG) {
            Log.i(getTag(), msg);
        }
    }

    /**
     * warn级别的日志
     *
     * @param msg 打印内容
     * @see [类、类#方法、类#成员]
     */
    public static void warn(String msg) {
        if (ISLOG) {
            Log.w(getTag(), msg);
        }
    }

    /**
     * error级别的日志
     *
     * @param msg 打印内容
     * @see [类、类#方法、类#成员]
     */
    public static void e(String msg) {
        if (ISLOG) {
             String tag = getTag();
            Log.e(tag, msg);
        }
    }

    public static void e(String msg, Throwable tr) {
        if (ISLOG) {
            String tag = getTag();
            Log.e(tag, msg, tr);
        }
    }


    public static void printJson(String msg, String headString) {
        if (ISLOG) {
            String message;

            try {
                if (msg.startsWith("{")) {
                    JSONObject jsonObject = new JSONObject(msg);
                    message = jsonObject.toString(4);//最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
                } else if (msg.startsWith("[")) {
                    JSONArray jsonArray = new JSONArray(msg);
                    message = jsonArray.toString(4);
                } else {
                    message = msg;
                }
            } catch (JSONException e) {
                message = msg;
            }
            //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
            //  把4*1024的MAX字节打印长度改为2001字符数
            int max_str_length = 3001 - headString.length();
            //大于4000时
            while (message.length() > max_str_length) {
                Log.e(headString, message.substring(0, max_str_length));
                message = message.substring(max_str_length);
            }
            //剩余部分
            Log.e(headString, message);
        }
    }

    /**
     * 获取日志的标签 格式：类名_方法名_行号 （需要权限：android.permission.GET_TASKS）
     *
     * @return tag
     * @see [类、类#方法、类#成员]
     */
    private static String getTag() {
        try {
            Exception exception = new LogException();
            if (exception.getStackTrace() == null || exception.getStackTrace().length <= EXCEPTION_STACK_INDEX) {
                return "***";
            }
            StackTraceElement element = exception.getStackTrace()[EXCEPTION_STACK_INDEX];

            String className = element.getClassName();

            int index = className.lastIndexOf(".");
            if (index > 0) {
                className = className.substring(index + 1);
            }

            return className + "_" + element.getMethodName() + "_" + element.getLineNumber();

        } catch (Throwable e) {
            e.printStackTrace();
            return "***";
        }
    }

    /**
     * 取日志标签用的的异常类，只是用于取得日志标签
     */
    private static class LogException extends Exception {
        /**
         * 注释内容
         */
        private static final long serialVersionUID = 1L;
    }
}
