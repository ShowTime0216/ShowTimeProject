package com.showtime.lp.base;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.File;

import mabeijianxi.camera.VCamera;
import mabeijianxi.camera.util.DeviceUtils;

/**
 * 作者:liupeng
 * 16/7/22 11:34
 * 注释:
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        File file = new File(Constants.PATH_PROJECT);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
        initSmallVideo(getApplicationContext());
    }

    public static void initSmallVideo(Context context) {
        // 设置拍摄视频缓存路径
        File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                VCamera.setVideoCachePath(dcim + "/chaoyu_train/");
            } else {
                VCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/", "/sdcard-ext/") + "/chaoyu_train/");
            }
        } else {
            VCamera.setVideoCachePath(dcim + "/chaoyu_train/");
        }
        VCamera.setDebugMode(true);
        VCamera.initialize(context);
    }
}
