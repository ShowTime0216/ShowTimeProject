package com.showtime.lp.utils;

import android.content.Context;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * 作者:liupeng
 * 16/7/25 14:40
 * 注释:
 */
public class FormatUtils {

    /**
     * dip转为 px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px 转为 dip
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * double类型数据减法
     *
     * @param v1
     * @param v2
     * @return
     */
    public static Double doubleSub(Double v1, Double v2) {
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.subtract(b2).doubleValue());
    }

    /**
     * * 两个Double数相除，并保留scale位小数 *
     *
     * @param v1    *
     * @param v2    *
     * @param scale * 保留几位小数
     * @return Double
     */
    public static Double doubleDiv(Double v1, Double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return new Double(b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    /**
     * 字符串转百分数
     *
     * @param string
     * @param i      保留几位小数
     * @return
     */
    public static String stringToPer(String string, int i) {
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(i);
        float f = Float.parseFloat(string);
        return nf.format(f);
    }
}
