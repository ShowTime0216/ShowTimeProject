package com.showtime.lp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import org.kymjs.kjframe.ui.KJFragment;

/**
 * 作者:liupeng
 * 16/8/24 12:16
 * 注释:
 */
public class BaseKjFragment extends KJFragment implements View.OnClickListener, AdapterView.OnItemClickListener, View.OnTouchListener {

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

}
