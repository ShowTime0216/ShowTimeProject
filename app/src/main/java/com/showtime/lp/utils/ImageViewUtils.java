package com.showtime.lp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

/**
 * 作者:liupeng
 * 16/8/23 14:51
 * 注释:
 */
public class ImageViewUtils {

    /**
     * @param context
     * @param imgUrl      图片地址
     * @param imageView   加载图片控件
     * @param placeholder 未加载图片占位图
     * @param error       加载错误占位图
     */
    public static void getGlide(Context context, String imgUrl, ImageView imageView, int placeholder, int error) {
        Glide.with(context)
                .load(imgUrl)
                .placeholder(placeholder)
                .error(error)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(imageView);
    }

    /**
     * @param
     * @return void
     * @getPicassoImgSize getPicassoImgSize
     * @Description: 加载自定义大小的网络图片
     */
    public static void getPicassoSize(Context context, String url, int width, int height, ImageView view) {
        Picasso.with(context).load(url)
                .resize(width, height)
                .centerCrop()
                .into(view);
    }

    /**
     * @param
     * @return void
     * @getPicassoImgError getPicassoImgError
     * @Description: 加载网络图片（未加载出来和加载错误）
     */
    public static void getPicasso(Context context, String url, Drawable notImg, Drawable errorImg, ImageView view) {
        Picasso.with(context).load(url)
                .placeholder(notImg)
                .error(errorImg)
                .into(view);
    }
}
