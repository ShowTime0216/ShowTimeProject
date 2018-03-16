package com.showtime.lp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.showtime.lp.R;
import com.showtime.lp.activity.AActivity;
import com.showtime.lp.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者:liupeng
 * 16/7/21 16:58
 * 注释:
 */
public class TaskFragment extends BaseFragment {

    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.text1)
    TextView text1;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.frag_task, container, false);
            initView();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        ButterKnife.bind(this, view);
        return view;
    }

    private void initView() {
        titleText = (TextView) view.findViewById(R.id.title_text);
        titleText.setText("任务");

    }

    @OnClick({R.id.text1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text1:
                Intent intent = new Intent(getActivity(), AActivity.class);
                startActivity(intent);
                break;
        }
    }
}
