package com.showtime.lp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.showtime.lp.base.BaseActivity;
import com.showtime.lp.fragment.HomePageFragment;
import com.showtime.lp.fragment.MenuFragment;
import com.showtime.lp.fragment.MessageFragment;
import com.showtime.lp.fragment.MineFragment;
import com.showtime.lp.fragment.TaskFragment;

/**
 * 作者:liupeng
 * 16/7/21 10:53
 * 注释: 主页面
 */
public class MainActivity extends BaseActivity {
    private Context context;
    private FragmentManager fragmentManager;
    private HomePageFragment homePageFragment;
    private MenuFragment menuFragment;
    private MessageFragment messageFragment;
    private TaskFragment taskFragment;
    private MineFragment mineFragment;
    private LinearLayout homeLayout;
    private LinearLayout menuLayout;
    private LinearLayout messageLayout;
    private LinearLayout taskLayout;
    private LinearLayout mineLayout;
    private ImageView homeImg;
    private ImageView menuImg;
    private ImageView messageImg;
    private ImageView taskImg;
    private ImageView mineImg;
    private TextView homeText;
    private TextView menuText;
    private TextView messageText;
    private TextView taskText;
    private TextView mineText;
    private long exitTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;
        initView();
    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
        homeLayout = (LinearLayout) findViewById(R.id.tab_home_layout);
        menuLayout = (LinearLayout) findViewById(R.id.tab_menu_layout);
        messageLayout = (LinearLayout) findViewById(R.id.tab_message_layout);
        taskLayout = (LinearLayout) findViewById(R.id.tab_task_layout);
        mineLayout = (LinearLayout) findViewById(R.id.tab_mine_layout);
        homeImg = (ImageView) findViewById(R.id.tab_home_img);
        menuImg = (ImageView) findViewById(R.id.tab_menu_img);
        messageImg = (ImageView) findViewById(R.id.tab_message_img);
        taskImg = (ImageView) findViewById(R.id.tab_task_img);
        mineImg = (ImageView) findViewById(R.id.tab_mine_img);
        homeText = (TextView) findViewById(R.id.tab_home_tv);
        menuText = (TextView) findViewById(R.id.tab_menu_tv);
        messageText = (TextView) findViewById(R.id.tab_message_tv);
        taskText = (TextView) findViewById(R.id.tab_task_tv);
        mineText = (TextView) findViewById(R.id.tab_mine_tv);

        homeLayout.setOnClickListener(this);
        menuLayout.setOnClickListener(this);
        messageLayout.setOnClickListener(this);
        taskLayout.setOnClickListener(this);
        mineLayout.setOnClickListener(this);
        setTabSelection(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_home_layout:
                setTabSelection(0);
                break;
            case R.id.tab_menu_layout:
                setTabSelection(1);
                break;
            case R.id.tab_message_layout:
                setTabSelection(2);
                break;
            case R.id.tab_task_layout:
                setTabSelection(3);
                break;
            case R.id.tab_mine_layout:
                setTabSelection(4);
                break;
        }
    }

    /**
     * fragment切换操作
     *
     * @param index
     */
    private void setTabSelection(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction(); // 开启一个Fragment事务
        hideFragments(transaction); // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        changeBackground();
        switch (index) {
            case 0:
                homeImg.setImageResource(R.mipmap.frag_homepage_click);
                homeText.setTextColor(getResources().getColor(R.color.blue_00));
                if (homePageFragment == null) {
                    homePageFragment = new HomePageFragment();
                    transaction.add(R.id.main_frag, homePageFragment);
                } else {
                    transaction.show(homePageFragment);
                }
                break;
            case 1:
                menuImg.setImageResource(R.mipmap.frag_menu_click);
                menuText.setTextColor(getResources().getColor(R.color.blue_00));
                if (menuFragment == null) {
                    menuFragment = new MenuFragment();
                    transaction.add(R.id.main_frag, menuFragment);
                } else {
                    transaction.show(menuFragment);
                }
                break;
            case 2:
                messageImg.setImageResource(R.mipmap.frag_message_click);
                messageText.setTextColor(getResources().getColor(R.color.blue_00));
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    transaction.add(R.id.main_frag, messageFragment);
                } else {
                    transaction.show(messageFragment);
                }
                break;
            case 3:
                taskImg.setImageResource(R.mipmap.frag_task_click);
                taskText.setTextColor(getResources().getColor(R.color.blue_00));
                if (taskFragment == null) {
                    taskFragment = new TaskFragment();
                    transaction.add(R.id.main_frag, taskFragment);
                } else {
                    transaction.show(taskFragment);
                }
                break;
            case 4:
                mineImg.setImageResource(R.mipmap.frag_centre_click);
                mineText.setTextColor(getResources().getColor(R.color.blue_00));
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.main_frag, mineFragment);
                } else {
                    transaction.show(mineFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (homePageFragment != null) {
            transaction.hide(homePageFragment);
        }
        if (menuFragment != null) {
            transaction.hide(menuFragment);
        }
        if (messageFragment != null) {
            transaction.hide(messageFragment);
        }
        if (taskFragment != null) {
            transaction.hide(taskFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }

    private void changeBackground() {
        homeImg.setImageResource(R.mipmap.frag_homepage);
        menuImg.setImageResource(R.mipmap.frag_menu);
        messageImg.setImageResource(R.mipmap.frag_message);
        taskImg.setImageResource(R.mipmap.frag_task);
        mineImg.setImageResource(R.mipmap.frag_centre);
        homeText.setTextColor(getResources().getColor(R.color.black_2c));
        menuText.setTextColor(getResources().getColor(R.color.black_2c));
        messageText.setTextColor(getResources().getColor(R.color.black_2c));
        taskText.setTextColor(getResources().getColor(R.color.black_2c));
        mineText.setTextColor(getResources().getColor(R.color.black_2c));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
        return false;
    }


}
