package com.showtime.lp.fragment.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.showtime.lp.R;
import com.showtime.lp.base.BaseFragment;
import com.showtime.lp.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/22 0022.
 */

public class ExampleLeftFragment extends BaseFragment {

    @BindView(R.id.toast1)
    TextView toast1;
    @BindView(R.id.toast2)
    TextView toast2;
    @BindView(R.id.toast3)
    TextView toast3;
    private View view;
    private int number;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) { // 禁止TabLayout预加载属性
            view = inflater.inflate(R.layout.frag_example_left, container, false);
            initView();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        Log.e("left--onCreateView-----", "--------------");
        ButterKnife.bind(this, view);
        return view;

    }

    private void initView() {

        Log.e("left--init-----", "--------------");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            Log.e("left--onResume------", isVisibleToUser + "   --------------");
        } else {
            Log.e("left--onPause-------", isVisibleToUser + "   --------------");
        }
    }

    @OnClick({R.id.toast1, R.id.toast2, R.id.toast3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toast1:
                ToastUtils.showToast(getActivity(), "--------111");
                break;
            case R.id.toast2:
                ToastUtils.getetToatsBytTime(getActivity(), "----------222", 100);
                break;
            case R.id.toast3:
                number++;
                ToastUtils.showToastMsg(getActivity(), "======== " + "3", 1500);
                break;
        }
    }
}
