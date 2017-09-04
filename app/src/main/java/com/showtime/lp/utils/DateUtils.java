package com.showtime.lp.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
     * 字符串转时间戳
     *
     * @param timeString
     * @return
     */
    public static String getStrToTime(String timeString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdf.parse(timeString);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    /**
     * 时间戳转字符串
     *
     * @param timeStamp
     * @return
     */
    public static String getTimeToStr(String timeStamp) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(timeStamp);
        int i = Integer.parseInt(timeStamp);
        String times = sdr.format(new Date(i * 1000L));
        return times;
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


    /***********************************************************/

    /*
	 * 将时间戳转为字符串 ，格式：yyyy-MM-dd HH:mm
	 */
    public static String getStrTime_ymd_hm(String cc_time) {
        String re_StrTime = "";
        if (TextUtils.isEmpty(cc_time) || "null".equals(cc_time)) {
            return re_StrTime;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;

    }

    /*
     * 将时间戳转为字符串 ，格式：yyyy-MM-dd HH:mm:ss
     */
    public static String getStrTime_ymd_hms(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;

    }

    /*
     * 将时间戳转为字符串 ，格式：yyyy.MM.dd
     */
    public static String getStrTime_ymd(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /*
     * 将时间戳转为字符串 ，格式：yyyy
     */
    public static String getStrTime_y(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /*
     * 将时间戳转为字符串 ，格式：MM-dd
     */
    public static String getStrTime_md(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /*
     * 将时间戳转为字符串 ，格式：HH:mm
     */
    public static String getStrTime_hm(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /*
     * 将时间戳转为字符串 ，格式：HH:mm:ss
     */
    public static String getStrTime_hms(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /*
     * 将时间戳转为字符串 ，格式：MM-dd HH:mm:ss
     */
    public static String getNewsDetailsDate(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /*
     * 将字符串转为时间戳
     */
    public static String getTimesss() {
        String re_time = null;
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Date d;
        d = new Date(currentTime);
        long l = d.getTime();
        String str = String.valueOf(l);
        re_time = str.substring(0, 10);
        return re_time;
    }

    /*
     * 将字符串转为日期
     */
    public static String getTimeToday(long times) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Date d;
        d = new Date(times);
        String str = sdf.format(d);
        re_time = str.substring(5, 10);
        return re_time;
    }

    /*
     * 将时间戳转为字符串 ，格式：yyyy.MM.dd 星期几
     */
    public static String getSection(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd  EEEE");
        // 对于创建SimpleDateFormat传入的参数：EEEE代表星期，如“星期四”；MMMM代表中文月份，如“十一月”；MM代表月份，如“11”；
        // yyyy代表年份，如“2010”；dd代表天，如“25”
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /** 将时间String转换成long 例如2015-12-12 12:15 */
    public static long getLongTim(String strTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return sdf.parse(strTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 计算两个##:##格式的时间 段(视频总播放时间--格式##:##String)
     *
     * @param start
     *            开始的时间
     * @param end
     *            结束的时间
     * @return
     */
    public static String getTimePeriod(String start, String end) {
        String timeStr = null;
        int startInt = Integer.parseInt(start.split(":")[0]) * 60
                + Integer.parseInt(start.split(":")[1]);
        int endInt = Integer.parseInt(end.split(":")[0]) * 60
                + Integer.parseInt(end.split(":")[1]);
        int timeInt = endInt - startInt;
        if (timeInt >= 60) {
            if (timeInt % 60 >= 10) {
                timeStr = timeInt / 60 + ":" + timeInt % 60 + ":00";
            } else {
                timeStr = timeInt / 60 + ":0" + timeInt % 60 + ":00";
            }
        } else {
            timeStr = timeInt + ":00";
        }
        return timeStr;
    }

    /**
     * 计算两个##:##格式的时间 段(总时间int)---视频进度条使用
     *
     * @param start
     *            开始的时间
     * @param end
     *            结束的时间
     * @return
     */
    public static int getTimePeriodInt(String start, String end) {
        int timeInt = 0;
        int startInt = Integer.parseInt(start.split(":")[0]) * 60
                + Integer.parseInt(start.split(":")[1]);
        int endInt = Integer.parseInt(end.split(":")[0]) * 60
                + Integer.parseInt(end.split(":")[1]);
        timeInt = (endInt - startInt) * 60;

        return timeInt;
    }

    /**
     * 将int型的时间转成##:##格式的时间
     *
     * @return
     */
    public static String getTimeStr(int timeInt) {
        int mi = 1 * 60;
        int hh = mi * 60;

        long hour = (timeInt) / hh;
        long minute = (timeInt - hour * hh) / mi;
        long second = timeInt - hour * hh - minute * mi;

        String strHour = hour < 10 ? "0" + hour : "" + hour;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        if (hour > 0) {
            return strHour + ":" + strMinute + ":" + strSecond;
        } else {
            return strMinute + ":" + strSecond;
        }
    }

}
