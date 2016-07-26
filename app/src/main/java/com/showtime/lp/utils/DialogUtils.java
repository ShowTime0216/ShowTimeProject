package com.showtime.lp.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 作者:liupeng
 * 16/7/25 14:10
 * 注释: 提示框
 */
public class DialogUtils {
    private static ProgressDialog progressDialogs;

    /**
     * 用来显示进度条
     * Android系统提示框
     */
    public static void createProgressDialog(Context context) {
        if (progressDialogs == null || !progressDialogs.isShowing()) {
            progressDialogs = new ProgressDialog(context);
            progressDialogs.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialogs.setTitle("提示");
            progressDialogs.setMessage("正在上传...");
            progressDialogs.setCancelable(false);
            progressDialogs.show();
        }
    }

    /**
     * 关闭显示的进度条
     * Android系统提示框
     */
    public static void closeProgressDiallog() {
        if (progressDialogs != null && progressDialogs.isShowing()) {
            progressDialogs.dismiss();
            progressDialogs = null;
        }
    }
}
