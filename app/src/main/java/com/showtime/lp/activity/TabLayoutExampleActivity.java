package com.showtime.lp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.showtime.lp.R;
import com.showtime.lp.adapter.TabLayoutAdapter;
import com.showtime.lp.base.BaseActivity;
import com.showtime.lp.fragment.example.ExampleLeftFragment;
import com.showtime.lp.fragment.example.ExampleRightFragment;
import com.showtime.lp.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Trouble Maker
 * @date: 2017/8/25 0025
 * @Description:
 */
public class TabLayoutExampleActivity extends BaseActivity {

    @BindView(R.id.left_layout)
    LinearLayout leftLayout;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    MyViewPager viewPager;
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
//        viewPager.setOffscreenPageLimit(0);
        TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(getSupportFragmentManager(), fragments, titles);
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

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        android.os.Process.killProcess(android.os.Process.myPid());
//    }


}
