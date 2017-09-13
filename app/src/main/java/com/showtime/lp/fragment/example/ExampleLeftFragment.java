package com.showtime.lp.fragment.example;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.showtime.lp.R;
import com.showtime.lp.activity.AActivity;
import com.showtime.lp.base.BaseFragment;
import com.showtime.lp.utils.ProgressDialogUtils;
import com.showtime.lp.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

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
    private Timer timer;

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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 99:
                    ProgressDialogUtils.closeProgressDiallog();
                    ToastUtils.showToast(getActivity(), "-----------");
                    timer.cancel();
                    break;
            }
        }
    };

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
                ProgressDialogUtils.createProgressDialog(getActivity());
                timer = new Timer();
                MyTimerTask timerTask = new MyTimerTask();
                timer.schedule(timerTask, 3000, 3000);
                break;
            case R.id.toast2:
                String str = "12345";
                ToastUtils.showToast(getActivity(), str.length() + "");
//                timer = new Timer();
//                MyTimerTask timerTask1 = new MyTimerTask();
//                timer.schedule(timerTask1, 0, 3000);
                break;
            case R.id.toast3:
                number++;
                Intent intent = new Intent(getActivity(), AActivity.class);
                startActivity(intent);
                break;
        }
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            handler.sendEmptyMessage(99);
        }
    }
}
