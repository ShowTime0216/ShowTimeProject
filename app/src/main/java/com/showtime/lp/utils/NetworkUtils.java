package com.showtime.lp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

/**
 * 作者:liupeng
 * 16/7/25 14:08
 * 注释: 判断网络是否可用
 */
public class NetworkUtils {

    /**
     * @param
     * @return boolean
     * @networkStatusOK networkStatusOK
     * @Description: 在android中判断是否有网络连接
     */
    public static boolean networkStatusOK(Context mContext) {
        boolean netStatus = false;
        try {
            ConnectivityManager connectManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                if (activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected()) {
                    netStatus = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return netStatus;
    }

    /**
     * @param
     * @return boolean
     * @isNetWork isNetWork
     * @Description: 判断网络是否可用
     */
    public static boolean isNetWork(Context context) {
        if (!NetworkUtils.isNetworkAvailable(context) && !NetworkUtils.isWiFiActive(context)) {
            Toast.makeText(context, "无可用网络，请检查网络", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * @param
     * @return boolean
     * @isWiFiActive isWiFiActive
     * @Description: 判断WIFI是否可以使用
     */
    public static boolean isWiFiActive(Context context) {
        WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
        if (mWifiManager.isWifiEnabled() && ipAddress != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param
     * @return boolean
     * @isNetworkAvailable isNetworkAvailable
     * @Description: 判断3G网络是否可以使用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info == null) {
                return false;
            }
        }
        return true;
    }
}