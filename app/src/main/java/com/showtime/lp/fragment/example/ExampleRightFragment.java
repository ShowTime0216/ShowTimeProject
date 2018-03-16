package com.showtime.lp.fragment.example;

import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.showtime.lp.R;
import com.showtime.lp.base.BaseFragment;
import com.showtime.lp.utils.Constants;
import com.showtime.lp.utils.ToastUtils;

import java.io.File;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_example_right, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e("right--onResume-----", "   --------------");
        }
    }

    @OnClick({R.id.toast1, R.id.toast2, R.id.toast3, R.id.toast4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toast1:
                if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.RECORD_AUDIO}, 1);
                    ToastUtils.getetToatsBytTime(getActivity(), "开始！！！", 500);
                } else {
                    ToastUtils.getetToatsBytTime(getActivity(), "结束！！！", 500);
                }
                break;
            case R.id.toast2:
                MediaRecorder mRecorders = new MediaRecorder();
                mRecorders.setAudioSource(MediaRecorder.AudioSource.MIC);
                break;
            case R.id.toast3:
                Log.e("333---------", voicePermission() + "");
                MediaRecorder recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
                recorder.setAudioChannels(1);
                recorder.setAudioSamplingRate(8000);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                File file = new File(Constants.PATH_PROJECT, "asd.amr");
                recorder.setOutputFile(file.getAbsolutePath());
                try {
                    recorder.prepare();
                    recorder.start();
                    recorder.stop();
                    recorder.release();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.toast4:
                break;
        }
    }

    private boolean voicePermission() {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.
                checkSelfPermission(getActivity(), android.Manifest.permission.RECORD_AUDIO));
    }
}
