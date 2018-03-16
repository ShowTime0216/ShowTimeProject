package com.showtime.lp.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.showtime.lp.R;

/**
 * 作者:lp
 * 16/7/25 14:10
 * 注释: 提示框
 */
public class ProgressDialogUtils {

    private static ProgressDialogUtils progressDialogUtils;
    private Dialog dialog;
    private AnimationDrawable animation;

    public ProgressDialogUtils() {

    }

    public static synchronized ProgressDialogUtils getInstance() {
        if (progressDialogUtils == null) {
            progressDialogUtils = new ProgressDialogUtils();
        }
        return progressDialogUtils;
    }

    /**
     * 启动loading动画
     */
    public void createDialog(final Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_loading_animation, null);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.layout);
        ImageView imageView = (ImageView) v.findViewById(R.id.image);
        imageView.setImageResource(R.drawable.loading_animation);
        animation = (AnimationDrawable) imageView.getDrawable();
        dialog = new Dialog(context, R.style.custom_loading_dialog); // 创建自定义样式dialog
        dialog.setCancelable(false);
        dialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        dialog.show();
        animation.start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (dialog != null) {
                    animation.stop();
                    dialog.dismiss();
                    dialog = null;
                    ToastUtils.showToast(context, "加载失败");
                }
            }
        }, 10000);
    }

    /**
     * 关闭loading动画
     */
    public void closeDialog() {
        if (dialog != null && dialog.isShowing()) {
            animation.stop();
            animation = null;
            dialog.dismiss();
            dialog = null;
        }
    }

}
