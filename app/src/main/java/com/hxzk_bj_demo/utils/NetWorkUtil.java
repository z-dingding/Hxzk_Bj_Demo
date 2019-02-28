package com.hxzk_bj_demo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.io.IOException;
import java.util.List;

/**
 * Used 网络连接的判断：是否有网络、网络是否可用、判断是wifi还是3G、判断wifi是否可用、判断3G是否可用
 *
 * @权限 android.permission.ACCESS_NETWORK_STATE
 */
public class NetWorkUtil {

    /**
     * 仅仅用来判断是否有网络连接,不能够判断网络是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            // 获取当前活动的NetworkInfo对象
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null) {
                // networkInfo.isAvailable此方法只是判断手机联网状态是否就绪，而不是真的判断忘络是否已经链接
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断是否有可用的网络连接需要和isNetworkOnline配合使用,单独只能判断是否有网络连接
     * 只打开3G连接的时候：
     * 0===类型===MOBILE
     * 0===状态===CONNECTED
     * 打开wifi连接和3G连接的时候：
     * 0===类型===MOBILE
     * 0===状态===DISCONNECTED
     * 1===类型===WIFI
     * 1===状态===CONNECTED
     */
    public static boolean isNetworkConnected(Context context) {
        boolean netstate = false;
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            if (manager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    NetworkCapabilities networkCapabilities = manager.getNetworkCapabilities(manager.getActiveNetwork());
                    if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                        netstate = true;
                    }
                } else {//6.0一下判断不是很准确,目前只能判断是否连接是网络
                    NetworkInfo[] networkInfo = manager.getAllNetworkInfo();
                    if (networkInfo != null) {
                        for (int i = 0; i < networkInfo.length; i++) {
                            // 判断当前网络状态是否为连接状态
                            if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                                netstate = true;
                                break;
                            }
                        }
                    }
                }
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return netstate;
    }



    /**
     * 判断网络链接后能否上网，子线程中操作，需要和isNetworkConnected配合使用
     *
     * @return
     */
    public static boolean isNetworkOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("ping -c 3 www.baidu.com");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }



    /**
     * 6.0及以上手机判断链接网络是否可用
     */

    public static boolean sixNetWorkConnection(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities networkCapabilities = mConnectivityManager.getNetworkCapabilities(mConnectivityManager.getActiveNetwork());
            if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                return true;
            }
        }
        return false;
    }



    /**
     * 判断是否是wifi,用户的体现性在这里了，wifi就可以建议下载或者在线播放
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }



    /**
     * 判断wifi 是否可用
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static boolean isWifiDataEnable(Context context) throws Exception {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isWifiDataEnable = false;

        isWifiDataEnable = manager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        return isWifiDataEnable;
    }



    /**
     * 判断是否是3G网络
     */
    public static boolean is3rd(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    /**
     * 判断3G网络是否可用
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static boolean isMobileDataEnable(Context context) throws Exception {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isMobileDataEnable = false;
        isMobileDataEnable = manager.getNetworkInfo(
                ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();

        return isMobileDataEnable;
    }

    /**
     * 判断网络是否为漫游
     */
    public static boolean isNetworkRoaming(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE) {
                TelephonyManager tm = (TelephonyManager) context
                        .getSystemService(Context.TELEPHONY_SERVICE);
                if (tm != null && tm.isNetworkRoaming()) {
                    return true;
                } else {
                }
            }
        }
        return false;
    }

    /**
     * 判断GPS是否打开
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = ((LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = lm.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }

}