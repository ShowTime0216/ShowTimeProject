package com.showtime.lp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.showtime.lp.R;
import com.showtime.lp.adapter.TabLayoutAdapter;
import com.showtime.lp.base.BaseActivity;
import com.showtime.lp.fragment.example.ExampleLeftFragment;
import com.showtime.lp.fragment.example.ExampleRightFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/23 0023.
 */

public class TabLayoutExampleActivity extends BaseActivity {

    @BindView(R.id.left_layout)
    LinearLayout leftLayout;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private TabLayoutAdapter tabLayoutAdapter;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablayout_example);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        titleText.setText("测试");
        fragments.add(new ExampleLeftFragment());
        fragments.add(new ExampleRightFragment());
        titles.add("LEFT");
        titles.add("RIGHT");
        tabLayoutAdapter = new TabLayoutAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(tabLayoutAdapter);
        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE); // TabLayout设置tab类型
    }

    @OnClick({R.id.left_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
        }
    }
}
