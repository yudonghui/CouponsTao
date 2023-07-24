package com.ydh.couponstao.smallwidget;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;

import com.ydh.couponstao.utils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ydh on 2020/11/25
 */
public class FileUtils {
    public static String screenshot(Activity mActivity) {
        // 获取屏幕
        View dView = mActivity.getWindow().getDecorView().getRootView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        if (bmp != null) {
            try {
                // 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                String filePath = sdCardPath + File.separator + "screenshot.png";

                File file = new File(filePath);

                FileOutputStream os = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
                return filePath;//176,91
            } catch (Exception e) {
            } finally {
                dView.setDrawingCacheEnabled(false);
            }
        }
        return "";
    }

    public static String screenshot() {
        try {

            // 获取内置SD卡路径
            String sdCardPath = Environment.getExternalStorageDirectory().getPath();
            // 图片文件路径
            String filePath = sdCardPath + File.separator + "screenshot.png";
            LogUtils.e("路径：" + filePath);
            Process process = Runtime.getRuntime().exec("screencap " + filePath);
            process.waitFor();


           /* // 获取内置SD卡路径
            String sdCardPath = Environment.getExternalStorageDirectory().getPath();
            // 图片文件路径
            String filePath = sdCardPath + File.separator + "screenshot.png";

            Process process = Runtime.getRuntime().exec("adb shell");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("screencap " + filePath + "\n");
            os.writeBytes("exit\n");
            os.flush();
            os.close();
            process.waitFor();*/
            return "";
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("doCommand.IOException");
        }
        return "";
    }

    public static String writeSD(String content) {
        //文件输出流
        FileOutputStream out = null;
        //设置文件路径
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mylike";
        File files = new File(path);
        if (!files.exists()) {
            // 创建文件夹
            files.mkdirs();
        }
        File file = new File(path, "baoming.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            out = new FileOutputStream(file, true);
            out.write(content.getBytes());
            out.write(("\r\n" + content).getBytes());
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 从SD卡中读取文件(读取SD卡需要权限，在AndroidManifest.xml中设置android.permission.READ_EXTERNAL_STORAGE)
     */
    public static String readSD() {
        //文件输入流
        FileInputStream in = null;
        //设置文件路径
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mylike";
        File files = new File(path);
        if (!files.exists()) {
            // 创建文件夹
            files.mkdirs();
        }
        File file = new File(path, "imei.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            in = new FileInputStream(file);
            //使用缓冲来读
            byte[] buf = new byte[1024];//每1024字节读一次
            StringBuilder builder = new StringBuilder();
            while (in.read(buf) != -1) {
                builder.append(new String(buf).trim());
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
