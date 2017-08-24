package com.showtime.lp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * 作者:liupeng
 * 16/9/18 15:59
 * 注释:
 */
public class TabLayoutAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragList;
    private List<String> textText;

    public TabLayoutAdapter(FragmentManager fm, List<Fragment> fragList, List<String> tabTitles) {
        super(fm);
        this.fragList = fragList;
        this.textText = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragList.get(position);
    }

    @Override
    public int getCount() {
        return fragList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return textText.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
