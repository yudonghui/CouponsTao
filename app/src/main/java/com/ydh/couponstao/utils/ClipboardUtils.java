package com.ydh.couponstao.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.ObjectUtils;
import com.ydh.couponstao.MyApplication;

/**
 * Created by ydh on 2022/8/26
 */
public class ClipboardUtils {
    public static void setClipboard(String content) {
        // 获取系统剪贴板服务
        ClipboardManager clipboard = (ClipboardManager) MyApplication.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建一个剪贴数据集，包含一个数据
        ClipData clipData = ClipData.newPlainText("", content);
        // 把数据集放到（复制）剪贴板
        clipboard.setPrimaryClip(clipData);
        CommonUtil.showToast("复制成功");
    }
    public static void setClipboardNo(String content) {
        // 获取系统剪贴板服务
        ClipboardManager clipboard = (ClipboardManager) MyApplication.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建一个剪贴数据集，包含一个数据
        ClipData clipData = ClipData.newPlainText("", content);
        // 把数据集放到（复制）剪贴板
        clipboard.setPrimaryClip(clipData);
    }
    public static String getClipboard() {
        //获取系统剪贴板服务
        ClipboardManager clipboardManager = (ClipboardManager) MyApplication.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (null != clipboardManager) {
            // 获取剪贴板的剪贴数据集
            ClipData clipData = clipboardManager.getPrimaryClip();
            if (null != clipData && clipData.getItemCount() > 0) {
                // 从数据集中获取（粘贴）第一条文本数据
                ClipData.Item item = clipData.getItemAt(0);
                if (null != item) {
                    return item.getText().toString();
                }
            }
        }
        return "";
    }
}
