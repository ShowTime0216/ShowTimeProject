package com.showtime.lp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者:liupeng
 * 16/7/25 14:21
 * 注释: 数据存储
 */
public class SharedPreferencesUtils {

    /**
     * 存储数据
     *
     * @param context
     * @param key
     * @param value
     */
    public void setSharedPreferences(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 取出数据
     *
     * @param context
     * @param key
     * @return
     */
    public String getSharedPreferences(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }
}
