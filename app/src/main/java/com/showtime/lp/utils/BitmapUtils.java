package com.showtime.lp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者:liupeng
 * 16/7/21 16:09
 * 注释: Bitmao工具类
 */
public class BitmapUtils {
    private static final float DISPLAY_WIDTH = 1000;
    private static final float DISPLAY_HEIGHT = 1000;

    /**
     * 本地图片转换成Bitmap
     *
     * @param path
     * @return
     */
    public static Bitmap decodeBitmap(String path) {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(path, op); //获取尺寸信息
        //获取比例大小
        int wRatio = (int) Math.ceil(op.outWidth / DISPLAY_WIDTH);
        int hRatio = (int) Math.ceil(op.outHeight / DISPLAY_HEIGHT);
        //如果超出指定大小，则缩小相应的比例
        if (wRatio > 1 && hRatio > 1) {
            if (wRatio > hRatio) {
                op.inSampleSize = wRatio;
            } else {
                op.inSampleSize = hRatio;
            }
        }
        op.inJustDecodeBounds = false;
        return bmp;
    }

    /**
     * 保存图片
     *
     * @param bitmap
     * @return
     */
    public static File saveImages(Bitmap bitmap) {
        File mediaFile = new File(Constants.PATH_PROJECT_IMAGE + File.separator + createPhotoFileName());
        if (mediaFile.exists()) { // 如果包含此张图片就删除
            mediaFile.delete();
        }
        File dirFile = new File(Constants.PATH_PROJECT_IMAGE);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        FileOutputStream fos = null;
        try {
            mediaFile.createNewFile();
            fos = new FileOutputStream(mediaFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.gc();
        return mediaFile;
    }

    /**
     * 把Bitmap转成图片保存到本地
     *
     * @param bitmap
     * @param fileName
     */
    public static void savePicture(Bitmap bitmap, String fileName) {
        File dirFile = new File(Constants.PATH_PROJECT_IMAGE);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        String pictureName = Constants.PATH_PROJECT_IMAGE + fileName;
        File file = new File(pictureName);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建图片的命名
     **/
    private static String createPhotoFileName() {
        String fileName = "";
        Date date = new Date(System.currentTimeMillis());  //系统当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMddHHmmssSSS");
        fileName = dateFormat.format(date) + ".png";
        return fileName;
    }

    /**
     * 缩放Bitmap图片
     * (压缩大小)
     **/
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    /**
     * 本地图片转换成Bitmap
     *
     * @param path
     * @return
     */
    public static Bitmap getLocationBitmap(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            return BitmapFactory.decodeStream(fis);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取网络图片
     *
     * @param url
     */
    public static void urlSaveBitmap(Context context, final String url) {
        if (NetworkUtils.isNetWork(context)) {
            ThreadUtils.threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL pictureUrl = new URL(url);
                        InputStream in = pictureUrl.openStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(in);
                        in.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * @param
     * @return Bitmap
     * @Description: 将Drawable转化为Bitmap
     */
    public static Bitmap getDrawableToBitmap(Drawable drawable) {
        if (null == drawable) {
            return null;
        }
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * @param
     * @return Drawable
     * @Description: 将bitmap转化为drawable
     */
    public static Drawable getBitmapToDrawable(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        return new BitmapDrawable(bitmap);
    }

    /**
     * @param
     * @return byte[]
     * @Description: 将Bitmap转化为Byte数组
     */
    public static byte[] getBitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] array = baos.toByteArray();
            baos.flush();
            baos.close();
            return array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param
     * @return Bitmap
     * @Description: 将byte数组转化为bitmap
     */
    public static Bitmap getByteToBitmap(byte[] bytes) {
        if (null == bytes) {
            return null;
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

}
