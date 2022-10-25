package com.ydh.couponstao.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import com.ydh.couponstao.MyApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

/**
 * Created by ydh on 2022/10/19
 */
public class DeviceUtil {
    /**
     * 获取OS信息，全称
     */
    public static String getOsInfo() {
        return "Android " + Build.VERSION.RELEASE;
    }

    /**
     * 获取厂商名
     **/
    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取产品名
     **/
    public static String getDeviceProduct() {
        return Build.PRODUCT;
    }

    /**
     * 获取手机品牌
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机型号
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机主板名
     */
    public static String getDeviceBoard() {
        return Build.BOARD;
    }

    /**
     * 设备名
     **/
    public static String getDeviceDevice() {
        return Build.DEVICE;
    }

    /**
     * fingerprit 信息
     **/
    public static String getDeviceFubgerprint() {
        return Build.FINGERPRINT;
    }

    public static int getDisplayHeight() {
        return MyApplication.getContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static int getDisplayWidth() {
        return MyApplication.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static float getDensity() {
        return MyApplication.getContext().getResources().getDisplayMetrics().density;
    }

    /**
     * 获取运行内存信息
     *
     * @return
     */
    public static String getMemoryInfo() {
        //获取运行内存的信息
        ActivityManager manager = (ActivityManager) MyApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
        StringBuilder sb = new StringBuilder();
        sb.append("可用:");
        sb.append(Formatter.formatFileSize(MyApplication.getContext(), info.availMem));
        sb.append(", 全部:");
        sb.append(Formatter.formatFileSize(MyApplication.getContext(), info.totalMem));
        return sb.toString();
    }

    /**
     * 获取剩余SD卡存储空间的大小
     *
     * @return
     */
    public static String getAvailSDSize() {
        try {
            //首先指定需要获取大小的目录
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();

            //得到可用区块
            long availableBlocks = stat.getAvailableBlocksLong();

            long availsd = blockSize * availableBlocks;
            return Formatter.formatFileSize(MyApplication.getContext(), availsd);
        } catch (Exception e) {
        }
        return "get no data";
    }

    /**
     * 获取全部SD卡存储空间大小(去掉了系统所占空间)
     *
     * @return
     */
    public static String getAllSDSize() {
        try {
            //首先指定需要获取大小的目录
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();

            //得到全部区块
            long availableBlocks = stat.getBlockCountLong();

            long availsd = blockSize * availableBlocks;
            return Formatter.formatFileSize(MyApplication.getContext(), availsd);
        } catch (Exception e) {
        }
        return "get no data";
    }


    /**
     * 获取CPU架构信息,支持的指令集
     * adb shell getprop ro.product.cpu.abi
     *
     * @return
     */
    public static String getCPUStruct() {
        String[] abis = new String[]{};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            abis = Build.SUPPORTED_ABIS;
        } else {
            abis = new String[]{Build.CPU_ABI, Build.CPU_ABI2};
        }
        StringBuilder abiStr = new StringBuilder();
        for (String abi : abis) {
            abiStr.append(abi);
            abiStr.append(';');
        }
        return abiStr.toString();
    }

    /**
     * 获取CPU核心数
     *
     * @return
     */
    public static int getNumCores() {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
//            Log.d(TAG, "CPU Count: "+files.length);
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            //Print exception
//            Log.d(TAG, "CPU Count: Failed.");
            e.printStackTrace();
            //Default to return 1 core
            return 1;
        }
    }

    /**
     * 获取CPU完整信息
     *
     * @return
     */
    public static String getCpuINfo() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String result = "";
            String text = "";
            while ((text = br.readLine()) != null) {
                result += text + "\n";
            }
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取CPU最大频率
     *
     * @return
     */
    // "/system/bin/cat" 命令行
    // "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq" 存储最大频率的文件的路径
    public static String getMaxCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }

    /**
     * 获取CPU最小频率
     *
     * @return
     */
    public static String getMinCpuFreq() {
        String result = "";
        ProcessBuilder cmd;
        try {
            String[] args = {"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            InputStream in = process.getInputStream();
            byte[] re = new byte[24];
            while (in.read(re) != -1) {
                result = result + new String(re);
            }
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            result = "N/A";
        }
        return result.trim();
    }

    /**
     * 获取CPU当前频率
     *
     * @return
     */
    public static String getCurCpuFreq() {
        String result = "N/A";
        try {
            FileReader fr = new FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private static final String[] rootRelatedDirs = new String[]{
            "/su", "/su/bin/su", "/sbin/su",
            "/data/local/xbin/su", "/data/local/bin/su", "/data/local/su",
            "/system/xbin/su",
            "/system/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su",
            "/system/bin/cufsdosck", "/system/xbin/cufsdosck", "/system/bin/cufsmgr",
            "/system/xbin/cufsmgr", "/system/bin/cufaevdd", "/system/xbin/cufaevdd",
            "/system/bin/conbb", "/system/xbin/conbb"};

    public static boolean isRoot() {
        boolean hasRootDir = false;
        String[] rootDirs;
        int dirCount = (rootDirs = rootRelatedDirs).length;
        for (int i = 0; i < dirCount; ++i) {
            String dir = rootDirs[i];
            if ((new File(dir)).exists()) {
                hasRootDir = true;
                break;
            }
        }
        return Build.TAGS != null && Build.TAGS.contains("test-keys") || hasRootDir;
    }
}
