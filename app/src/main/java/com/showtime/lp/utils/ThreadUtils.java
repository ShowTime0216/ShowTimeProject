package com.showtime.lp.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者:liupeng
 * 16/7/25 14:19
 * 注释: 线程池
 */
public class ThreadUtils {

    public static ExecutorService threadPool = Executors.newFixedThreadPool(5);
}
