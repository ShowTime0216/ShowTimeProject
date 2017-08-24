package com.showtime.lp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.showtime.lp.R;
import com.showtime.lp.activity.TabLayoutExampleActivity;
import com.showtime.lp.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者:liupeng
 * 16/7/21 17:00
 * 注释:
 */
public class MessageFragment extends BaseFragment {
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.text)
    TextView text;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.frag_message, container, false);
            ButterKnife.bind(this, view);
            initView();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        Log.e("msg--onCreateView----", "msg--onCreateView");

        return view;
    }

    private void initView() {
        titleText.setText("消息");

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
        } else {
        }
    }

    @OnClick({R.id.text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text:
                Intent intent = new Intent(getActivity(), TabLayoutExampleActivity.class);
                startActivity(intent);
                break;
        }
    }
}
