package com.showtime.lp.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.showtime.lp.R;

import org.kymjs.kjframe.KJActivity;

/**
 * 作者:liupeng
 * 16/8/24 12:16
 * 注释:
 */
public class BaseKjActivity extends KJActivity implements View.OnClickListener, AdapterView.OnItemClickListener, View.OnTouchListener {

    @Override
    public void setRootView() {

    }

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
