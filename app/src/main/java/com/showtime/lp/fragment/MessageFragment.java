package com.showtime.lp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.showtime.lp.R;
import com.showtime.lp.base.BaseFragment;

/**
 * 作者:liupeng
 * 16/7/21 17:00
 * 注释:
 */
public class MessageFragment extends BaseFragment{
    private View view;
    private TextView titleText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.frag_message, container, false);
            initView();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    private void initView() {
        titleText = (TextView) view.findViewById(R.id.title_text);
        titleText.setText("消息");

    }
}
