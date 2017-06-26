package com.showtime.lp.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.showtime.lp.R;
import com.showtime.lp.activity.ProjectActivity;
import com.showtime.lp.base.BaseFragment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 作者:liupeng
 * 16/7/21 16:57
 * 注释:
 */
public class HomePageFragment extends BaseFragment {
    private View view;
    private TextView titleText;
    private TextView textView;
    private Banner banner;
    private PullToRefreshScrollView mPullList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        if (view == null) {
//            view = inflater.inflate(R.layout.frag_home, container, false);
//            initView();
//        }
//        ViewGroup parent = (ViewGroup) view.getParent();
//        if (parent != null) {
//            parent.removeView(view);
//        }
        view = inflater.inflate(R.layout.frag_home, container, false);
        Log.e("home--onCreateView", "home--onCreateView");
        initView();


        ButterKnife.bind(this, view);
        return view;
    }


//    @Override
//    protected View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
//        Log.e("home--inflaterView", "home--inflaterView");
//        return null;
//    }
//
//    @Override
//    protected void initWidget(View parentView) {
//        super.initWidget(parentView);
//        Log.e("home--initWidget", "home--initWidget");
//    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("home--onResume", "home--onResume");
    }

    /**
     * Fragment真正的onResume()和onPause()
     *
     * @param hidden true: onPause();  false: onResume();
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (hidden) {
            Log.e("home--onHidden--onPause", "home--onHiddenChanged");
        } else {
            Log.e("home--onHden--onResume", "home--onHiddenChanged");
        }
    }

    private void initView() {
        titleText = (TextView) view.findViewById(R.id.title_text);
        titleText.setText("首页");

        textView = (TextView) view.findViewById(R.id.textView);
        textView.setOnClickListener(this);

        mPullList = (PullToRefreshScrollView) view.findViewById(R.id.pullToRefreshScrollView);

        banner = (Banner) view.findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setDelayTime(3000);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerClickListener(new Banner.OnBannerClickListener() {
            @Override
            public void OnBannerClick(View view, int position) {
            }
        });

        List<String> imgList = new ArrayList<>();
        imgList.add("http://b97.photo.store.qq.com/psb?/V111YZBy0Fmora/fM8KZJfR6qE28BhcxfPMBM.1YMJ01K2PDZMfvGgekMw!/c/dGEAAAAAAAAA&bo=wAOAAgAFVQMFADE!");
        imgList.add("http://b97.photo.store.qq.com/psb?/V111YZBy0Fmora/JU48WpdKXDYwkKbCwLpg*AfuixHHj6kPPdTQVK7nqP8!/c/dGEAAAAAAAAA&bo=wAOAAgAFVQMFADE!");
        imgList.add("http://b115.photo.store.qq.com/psb?/V111YZBy0Fmora/FXeOVPOYuTOjxkDoDyJM8Va9x2vMZmnDO7v9FYN9uj4!/c/dHMAAAAAAAAA&bo=wAOAAgAFVQMFADE!");
        imgList.add("http://b346.photo.store.qq.com/psb?/V111YZBy0Fmora/QVfszoeqrckdNudemajnXdE3uBx.wLugkrnyp8L7mbc!/c/dFoBAAAAAAAA&bo=wwOAAgAFUwMFADQ!");
        imgList.add("http://b343.photo.store.qq.com/psb?/V111YZBy0Fmora/VvNRXnV7mq2GlinFWWKDUF7DObSOEpXmBGKfiT7su2Y!/c/dFcBAAAAAAAA&bo=wAOAAgAFVQMFADE!");
        banner.setImages(imgList);

        pullListener();
        setPullList(mPullList);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.textView:
                Intent intent = new Intent(getActivity(), ProjectActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * @param
     * @return void
     * @setEventListener setEventListener
     * @Description: 上拉下拉监听事件
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void pullListener() {
        mPullList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                // 下拉刷新触发的事件,获取格式化的时间
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                        | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label); // 更新LastUpdatedLabel
                new GetDataTask().execute(); // /开启线程模拟调接口填充数据
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {
                new GetDataTask().execute(); // 上提加载触发的事件,开启线程模拟调接口填充数据
            }
        });
    }

    /**
     * @param
     * @return void
     * @initData initData
     * @Description: BOTH 上拉下拉的View
     */
    private void setPullList(PullToRefreshScrollView mPullList) {
        mPullList.setMode(PullToRefreshBase.Mode.BOTH); // 设置PullToRefreshListView的模式
        mPullList.setMode(PullToRefreshBase.Mode.BOTH); // 设置PullRefreshListView上提加载时的加载提示
        mPullList.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载...");
        mPullList.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
        mPullList.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载更多...");
        // 设置PullRefreshListView下拉加载时的加载提示
        mPullList.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        mPullList.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在加载...");
        mPullList.getLoadingLayoutProxy(true, false).setReleaseLabel("松开加载更多...");
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (mPullList.isHeaderShown()) { // 下拉操作(刷新列表)
            } else if (mPullList.isFooterShown()) { // 上拉操作(加载下一页)
            }
            mPullList.onRefreshComplete(); // 调用刷新完成(刷新完刷新动画停止)
            super.onPostExecute(result);
        }
    }


}
