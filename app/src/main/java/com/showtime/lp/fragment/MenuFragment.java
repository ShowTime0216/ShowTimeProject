package com.showtime.lp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.showtime.lp.R;
import com.showtime.lp.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_menu, container, false);
        Log.e("menu--onCreateView", "menu--onCreateView");
        ButterKnife.bind(this, view);
        initView();
        return view;
    }


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
        titleText.setText("菜单");
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.e("onRatingChanged", "onRatingChanged");
            }
        });
    }
}
