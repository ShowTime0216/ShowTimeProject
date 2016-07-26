package com.showtime.lp.utils;

import java.text.NumberFormat;

/**
 * 作者:liupeng
 * 16/7/25 14:40
 * 注释:
 */
public class FormatUtils {

    /**
     * 字符串转百分数
     *
     * @param string
     * @return
     */
    public static String stringToPer(String string) {
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2);
        float f = Float.parseFloat(string);
        return nf.format(f);
    }
}
