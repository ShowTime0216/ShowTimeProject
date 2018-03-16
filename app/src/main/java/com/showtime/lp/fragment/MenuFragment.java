package com.showtime.lp.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.showtime.lp.R;
import com.showtime.lp.activity.RecordActivity;
import com.showtime.lp.activity.ViewScrollActivity;
import com.showtime.lp.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者:liupeng
 * 16/7/21 16:59
 * 注释:
 */
public class MenuFragment extends BaseFragment {
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.text2)
    TextView text2;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_menu, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (hidden) {
        } else {
        }
    }

    private void initView() {
        titleText.setText("菜单");
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.e("onRatingChanged", "onRatingChanged");
            }
        });
    }

    @OnClick({R.id.text1, R.id.text2})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.text1:
                Toast.makeText(getActivity(), Build.CPU_ABI + "        " + Build.CPU_ABI2, Toast.LENGTH_LONG).show();
                intent = new Intent(getActivity(), RecordActivity.class);
                startActivity(intent);
                break;
            case R.id.text2:
                intent = new Intent(getActivity(), ViewScrollActivity.class);
                startActivity(intent);
                break;
        }
    }
}
