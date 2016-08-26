package com.showtime.lp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.showtime.lp.R;
import com.showtime.lp.base.BaseFragment;

/**
 * 作者:liupeng
 * 16/7/21 16:59
 * 注释:
 */
public class MenuFragment extends BaseFragment {
    private View view;
    private TextView titleText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_menu, container, false);
        Log.e("menu--onCreateView", "menu--onCreateView");
        initView();
        return view;
    }

//    @Override
//    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
//        Log.e("menu--inflaterView", "menu--inflaterView");
//        return null;
//    }
//
//    @Override
//    protected void initWidget(View parentView) {
//        super.initWidget(parentView);
//        Log.e("menu--initWidget", "menu--initWidget");
//    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("menu--onResume", "menu--onResume");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (hidden) {
            Log.e("menu--onHidden--onPause", "menu--onHiddenChanged");
        } else {
            Log.e("menu--onHden--onResume", "menu--onHiddenChanged");
        }
    }

    private void initView() {
        titleText = (TextView) view.findViewById(R.id.title_text);
        titleText.setText("菜单");

    }
}
