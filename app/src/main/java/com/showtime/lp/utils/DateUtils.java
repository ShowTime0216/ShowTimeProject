package com.showtime.lp.utils;

import android.net.ParseException;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 作者:liupeng
 * 16/7/25 14:41
 * 注释:
 */
public class DateUtils {
    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * 把字符串转为日期
     */
    public static Date ToDate(String strDate) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.parse(strDate);
    }

    /**
     * 返回文字描述的日期
     * (时间显示多少天以前)
     *
     * @return
     */
    public static String getTimeFormatText(String str) throws Exception {
        Date date = ToDate(str);
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    /**
     * @param
     * @return String
     * @Description: 获取系统当前是上午、下午
     */
    public static String getTime() {
        Calendar c = Calendar.getInstance();
        String ampm = String.valueOf(c.get(Calendar.AM_PM));
        if (ampm.equals("0")) {
            return "上午";
        } else if (ampm.equals("1")) {
            return "下午";
        }
        return "";
    }

    /**
     * @param
     * @return String
     * @Description: 获取系统当前是星期几
     */
    public static String getWeekDate(Calendar c) {
        if (Calendar.MONDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期一";
        } else if (Calendar.TUESDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期二";
        } else if (Calendar.WEDNESDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期三";
        } else if (Calendar.THURSDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期四";
        } else if (Calendar.FRIDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期五";
        } else if (Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期六";
        } else if (Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return "星期日";
        }
        return "";
    }

    /**
     * @param
     * @return String
     * @Description: 获取时间格式
     */
    public static String getFormatTime(Calendar c) {
        String parten = "00";
        DecimalFormat decimal = new DecimalFormat(parten);
        Calendar calendar = c;
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        return year + "-" + decimal.format(month + 1) + "-" + decimal.format(day);
    }

    /**
     * @param
     * @return long
     * @Description: 计算两个日期间的天数 time2 为选择日期 time1系统当前日期
     */
    public static long getDateNum(String time1, String time2) {
        long quot = 0;
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = ft.parse(time1);
            Date date2 = ft.parse(time2);
            quot = (date1.getTime()) - (date2.getTime());
            quot = quot / 1000 / 60 / 60 / 24;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quot;
    }
}
