package com.showtime.lp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

/**
 * 作者:liupeng
 * 16/7/25 14:10
 * 注释: 提示框
 */
public class ProgressDialogUtils {
    private static ProgressDialog progressDialogs;

    /**
     * 用来显示进度条
     * Android系统提示框
     */
    public static void createProgressDialog(final Context context) {
        if (progressDialogs == null || !progressDialogs.isShowing()) {
            progressDialogs = new ProgressDialog(context);
            progressDialogs.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialogs.setTitle("提示");
            progressDialogs.setMessage("正在加载...");
            progressDialogs.setCancelable(false);
            progressDialogs.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (progressDialogs != null) {
                        progressDialogs.dismiss();
                        progressDialogs = null;
                        ToastUtils.showToast(context, "加载失败");
                    }
                }
            }, 10000);
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
