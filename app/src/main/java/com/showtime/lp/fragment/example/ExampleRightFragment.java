package com.showtime.lp.fragment.example;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.showtime.lp.R;
import com.showtime.lp.base.BaseFragment;
import com.showtime.lp.utils.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator
 * on 2017/8/22 0022.
 */

public class ExampleRightFragment extends BaseFragment {

    @BindView(R.id.toast1)
    TextView toast1;
    @BindView(R.id.toast2)
    TextView toast2;
    @BindView(R.id.toast3)
    TextView toast3;
    @BindView(R.id.toast4)
    TextView toast4;
    private View view;

    private static final String LOG_TAG = "AudioRecordTest";
    //语音文件保存路径
    private String FileName = null;

    //语音操作对象
    private MediaPlayer mPlayer = null;
    private MediaRecorder mRecorder = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.frag_example_right, container, false);
            initView();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        Log.e("right--onCreateView--", "--------------");

        initView();

        ButterKnife.bind(this, view);
        return view;
    }

    private void initView() {

        Log.e("right--init--", "--------------");

        //设置sdcard的路径
        FileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        FileName += "/audiorecordtest.3gp";
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            Log.e("right--onResume-----", "   --------------");
        } else {
            Log.e("right--onPause------", "   --------------");
        }
    }

    @OnClick({R.id.toast1, R.id.toast2, R.id.toast3, R.id.toast4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toast1:
                mRecorder = new MediaRecorder();
                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mRecorder.setOutputFile(FileName);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                try {
                    mRecorder.prepare();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "prepare() failed");
                }
                mRecorder.start();
                ToastUtils.getetToatsBytTime(getActivity(), "开始录音！！！", 500);
                break;
            case R.id.toast2:
                if (mRecorder != null) {
                    mRecorder.stop();
                    mRecorder.release();
                    mRecorder = null;
                    ToastUtils.getetToatsBytTime(getActivity(), "结束录音！！！", 500);
                }
                break;
            case R.id.toast3:
                mPlayer = new MediaPlayer();
                try {
                    mPlayer.setDataSource(FileName);
                    mPlayer.prepare();
                    mPlayer.start();
                    ToastUtils.getetToatsBytTime(getActivity(), "开始播放！！！", 500);
                } catch (IOException e) {
                    Log.e(LOG_TAG, "播放失败");
                }
                break;
            case R.id.toast4:
                if (mPlayer != null) {
                    mPlayer.release();
                    mPlayer = null;
                    ToastUtils.getetToatsBytTime(getActivity(), "结束播放！！！", 500);
                }
                break;
        }
    }

}
