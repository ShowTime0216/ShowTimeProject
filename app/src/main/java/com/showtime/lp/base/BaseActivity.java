package com.showtime.lp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.showtime.lp.R;

/**
 * 作者:liupeng
 * 16/7/21 16:26
 * 注释:
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, View.OnTouchListener, ViewPager.OnPageChangeListener {

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_activity_right_in, R.anim.anim_activity_left_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.anim_activity_right_in, R.anim.anim_activity_left_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_activity_left_in, R.anim.anim_activity_right_out);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
