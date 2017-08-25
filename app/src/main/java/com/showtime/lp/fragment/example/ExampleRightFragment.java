package com.showtime.lp.fragment.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.showtime.lp.R;
import com.showtime.lp.base.BaseFragment;

/**
 * Created by Administrator
 on 2017/8/22 0022.
 */

public class ExampleRightFragment extends BaseFragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.frag_example_left, container, false);
            initView();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }






        Log.e("right--onCreateView--", "--------------");

        initView();

        return view;
    }

    private void initView() {




        Log.e("right--init--", "--------------");

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            Log.e("right--onResume-----", "   --------------");
        } else {
            Log.e("right--onPause------", "   --------------");
        }
    }

}
