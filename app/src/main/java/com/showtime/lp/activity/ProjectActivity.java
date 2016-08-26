package com.showtime.lp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.devspark.appmsg.AppMsg;
import com.showtime.lp.R;
import com.showtime.lp.base.BaseKjActivity;

import org.kymjs.kjframe.KJBitmap;
import org.kymjs.kjframe.bitmap.BitmapCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.widget.KJScrollView;

import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * 作者:liupeng
 * 16/8/24 10:36
 * 注释:
 */
public class ProjectActivity extends BaseKjActivity {
    @BindView(id = R.id.textView, click = true)
    private TextView textView;
    @BindView(id = R.id.textView1, click = true)
    private TextView textView1;
    @BindView(id = R.id.textView2, click = true)
    private TextView textView2;
    @BindView(id = R.id.textView3, click = true)
    private TextView textView3;
    @BindView(id = R.id.textView4, click = true)
    private TextView textView4;
    @BindView(id = R.id.textView5, click = true)
    private TextView textView5;
    @BindView(id = R.id.kjScrollView)
    private KJScrollView kjScrollView;
    private Context context;


//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_project);
//        context = ProjectActivity.this;
//        Log.e("onCreate", "onCreate------");
//
////        textView = (TextView) findViewById(R.id.textView);
////        textView.setOnClickListener(this);
//        textView1 = (TextView) findViewById(R.id.textView1);
//        textView1.setOnClickListener(this);
//        textView2 = (TextView) findViewById(R.id.textView2);
//        textView2.setOnClickListener(this);
//        textView3 = (TextView) findViewById(R.id.textView3);
//        textView3.setOnClickListener(this);
//        textView4 = (TextView) findViewById(R.id.textView4);
//        textView4.setOnClickListener(this);
//        textView5 = (TextView) findViewById(R.id.textView5);
//        textView5.setOnClickListener(this);
//
//    }

    @Override
    public void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_project);
        Log.e("KJ---", "-111111111");
    }

    @Override
    public void initWidget() {
        super.initWidget();
        Log.e("KJ---", "-222222222");

    }

    private void KJ() {

        KJBitmap kjb = new KJBitmap();
        kjb.display(textView, "图片所在位置"); // 加载本地SD卡图片
        kjb.display(textView, "网络图片地址"); // 加载网络图片
        kjb.display(textView, "图片地址", 100, 100); // 加载固定大小的图片
        kjb.saveImage(context, "要保存的图片地址", "保存的本地路径");
        kjb.display(textView, "图片地址", new BitmapCallBack() { // 添加动画效果
            @Override
            public void onPreLoad() {
                super.onPreLoad();
            }

            @Override
            public void onSuccess(Bitmap bitmap) {
                super.onSuccess(bitmap);
            }

            @Override
            public void onFailure(Exception e) {
                super.onFailure(e);
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onDoHttp() {
                super.onDoHttp();
            }
        });


    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.textView:
                Log.e("KJ---", "-3333333");
                MultiImageSelector.create(context)
                        .count(12)
                        .showCamera(true)
                        .start(ProjectActivity.this, 100);
                break;
            case R.id.textView1:
                AppMsg.makeText(this, "什么！！！！！！", AppMsg.STYLE_ALERT).setLayoutGravity(Gravity.CENTER).show();
                break;
            case R.id.textView2:
                AppMsg.makeText(this, "什么！！！！！！", new AppMsg.Style(AppMsg.LENGTH_SHORT, R.color.blue_00)).setLayoutGravity(Gravity.BOTTOM).show();
                break;
            case R.id.textView3:
                break;
            case R.id.textView4:
                break;
            case R.id.textView5:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT).size() > 0) {
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                Log.e("path", path + "---" + requestCode + "----" + resultCode);
            }
        }
    }
}
