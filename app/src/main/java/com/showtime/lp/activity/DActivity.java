package com.showtime.lp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.showtime.lp.R;
import com.showtime.lp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Trouble Maker
 * @date: 2017/10/23 0023
 * @Description:
 */
public class DActivity extends BaseActivity {

    @BindView(R.id.left_layout)
    LinearLayout leftLayout;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.text1)
    TextView text1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);
        ButterKnife.bind(this);

        titleText.setText("Activity DDDD");
    }

    @OnClick({R.id.left_layout, R.id.text1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.text1:
                break;
        }
    }
}
