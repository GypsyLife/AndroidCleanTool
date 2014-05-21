package com.cleanmanager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.widget.Toast;

import org.apache.http.conn.util.InetAddressUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * some other miscellaneous util functions
 * Created by zhangge on 14-1-3.
 */
public class Miscellaneous {
    public static boolean copyAssets(Context context, String assetName, File to, boolean overwrite) {
        if (to.exists() && !overwrite) {
            return true;
        }
        AssetManager assets = context.getAssets();
        try {
            InputStream in = assets.open(assetName);
            FileOutputStream fos = new FileOutputStream(to);
            copyFile(in, fos);
            in.close();
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return to.exists();
    }

    public static boolean copyAssets(Context context, String assetName, FileOutputStream to, boolean overwrite) {
        AssetManager assets = context.getAssets();
        try {
            InputStream in = assets.open(assetName);
            FileOutputStream fos = to;
            copyFile(in, fos);
            in.close();
            fos.flush();
            fos.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[2048];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public static boolean isPackageAbsent(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(packageName,
                    PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return true;
        }
        return false;
    }

    public static String getDeviceName(String defaultname) {
        String deviceComplex = Build.MODEL.toLowerCase() + Build.HOST.toLowerCase();
        if (deviceComplex.contains("mele_a100")) {
            return "迈乐盒子";
        } else if (deviceComplex.contains("mele")) {
            return "迈乐盒子";
        } else if (deviceComplex.contains("kiui")) {
            return "开博尔";
        } else if (deviceComplex.contains("Letvx")) {
            return "乐视电视";
        } else if (deviceComplex.contains("mygica")) {
            return "美如画盒子";
        } else if (deviceComplex.contains("i5d")) {
            return "我播盒子";
        } else if (deviceComplex.contains("mitv")) {
            return "小米电视";
        } else if (deviceComplex.contains("mibox")) {
            return "小米盒子";
        } else if (deviceComplex.contains("aliyun")) {
            return "阿里云盒子";
        } else if (deviceComplex.contains("skyworth")) {
            return "创维电视";
        } else if (deviceComplex.contains("konka")) {
            return "康佳电视";
        } else if (deviceComplex.contains("tcl")) {
            return "TCL电视";
        } else if (deviceComplex.contains("hisense")) {
            return "海信电视";
        } else if (deviceComplex.contains("wasu_")) {
            return "华数盒子";
        } else if (deviceComplex.contains("i71")) {
            return "爱奇艺盒子";
        } else if (deviceComplex.contains("diyomate")) {
            return "迪优美特";
        } else if (deviceComplex.contains("magicbox")) {
            return "天猫魔盒";
        } else if (deviceComplex.contains("tianyun")) {
            return "天敏盒子";
        } else if (deviceComplex.contains("10moons")) {
            return "天敏盒子";
        } else if (deviceComplex.contains("lcd1server")) {
            return "创维电视";
        } else if (deviceComplex.contains("full aosp on godbox") || deviceComplex.contains("hiandroid")
                || deviceComplex.contains("himedia") || deviceComplex.contains("histbandroid")) {
            return "海美迪盒子";
        } else if (deviceComplex.contains("letv") || deviceComplex.contains("alexwang")) {
            return "乐视盒子";
        } else if (deviceComplex.contains("s1001") && deviceComplex.contains("ubuntu")) {
            return "飞看盒子";
        } else if (deviceComplex.contains("geeya_ott_v")) {
            return "金亚智能盒";
        } else if(deviceComplex.contains("b-200")){
            return "百度影棒";
        }
        return defaultname;
    }

    public static void makeToast(Activity someActivity, String toastText) {
        Toast.makeText(someActivity, toastText, Toast.LENGTH_SHORT).show();
    }

    public static long getAvailableMemory(Context context) {
        // 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem / (1024 * 1024);
    }

    public static long getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+");
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();
        } catch (IOException e) {
        }
        //return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
        return initial_memory / (1024 * 1024);
    }

    public static void clearMemory(Context context) {
        ActivityManager activityManger = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = activityManger.getRunningAppProcesses();
        if (list != null)
            for (int i = 0; i < list.size(); i++) {
                ActivityManager.RunningAppProcessInfo apinfo = list.get(i);

                String[] pkgList = apinfo.pkgList;

                if (apinfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_PERCEPTIBLE ) {
                    for (int j = 0; j < pkgList.length; j++) {
                        activityManger.killBackgroundProcesses(pkgList[j]);
                    }
                }
            }
    }

    /**
     * Get IP address from first non-localhost interface
     * @param ipv4  true=return ipv4, false=return ipv6
     * @return  address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                                return delim<0 ? sAddr : sAddr.substring(0, delim);
                            }
                        }
                    }
                }
            }
        } catch (Exception ignore) { } // for now eat exceptions
        return "";
    }

    public static int getPackageVersion(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(packageName,
                    PackageManager.GET_META_DATA);
            return info.versionCode;
        } catch (Exception e) {
            return 0;
        }
    }
}
