package com.showtime.lp.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/8/24 0024.
 * Toast封装类
 */

public class ToastUtils {

    private static Toast toast;
    private static String oldMsg;
    private static long time;

    /**
     * 防止Toast一直弹出
     *
     * @param context
     * @param content
     */
    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    /**
     * 当弹出内容有变化才会弹出
     *
     * @param context
     * @param msg
     * @param duration
     */
    public static void showToastMsg(Context context, String msg, int duration) {
        if (!msg.equals(oldMsg)) { // 当显示的内容不一样时，即断定为不是同一个Toast
            Toast.makeText(context, msg, duration).show();
            time = System.currentTimeMillis();
        } else {
            // 显示内容一样时，只有间隔时间大于2秒时才显示
            if (System.currentTimeMillis() - time > 1500) {
                Toast.makeText(context, msg, duration).show();
                time = System.currentTimeMillis();
            }
        }
        oldMsg = msg;
    }

    /**
     * 带有时间长短的Toast
     *
     * @param c
     * @param info
     * @param time
     */
    public static void getetToatsBytTime(Context c, String info, int time) {
        final Toast toast = Toast.makeText(c, info, Toast.LENGTH_SHORT);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                toast.cancel();
            }
        }, time);
    }
}
