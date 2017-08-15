package com.showtime.lp.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoView;
import com.showtime.lp.R;
import com.showtime.lp.base.BaseActivity;
import com.showtime.lp.view.MediaController;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class QNPlayerActivity extends BaseActivity {

    @BindView(R.id.videoView)
    PLVideoView videoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initView();
    }

    private void initView() {

        View loadingView = findViewById(R.id.loadingView);
        loadingView.setVisibility(View.VISIBLE);
        videoView.setBufferingIndicator(loadingView);

        // 1 -> hw codec enable, 0 -> disable [recommended]
//        int codec = getIntent().getIntExtra("mediaCodec", AVOptions.MEDIA_CODEC_SW_DECODE);
        AVOptions options = new AVOptions();
        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        // 1 -> hw codec enable, 0 -> disable [recommended]
        options.setInteger(AVOptions.KEY_MEDIACODEC, AVOptions.MEDIA_CODEC_SW_DECODE);
//        boolean cache = getIntent().getBooleanExtra("cache", false);
//        if (cache) {
//            options.setString(AVOptions.KEY_CACHE_DIR, Config.DEFAULT_CACHE_DIR);
//        }
//        boolean vcallback = getIntent().getBooleanExtra("video-data-callback", false);
//        if (vcallback) {
//            options.setInteger(AVOptions.KEY_VIDEO_DATA_CALLBACK, 1);
//        }
//        boolean acallback = getIntent().getBooleanExtra("audio-data-callback", false);
//        if (acallback) {
//            options.setInteger(AVOptions.KEY_AUDIO_DATA_CALLBACK, 1);
//        }
        videoView.setAVOptions(options);
        videoView.setDebugLoggingEnabled(true);

        // Set some listeners
        videoView.setOnInfoListener(mOnInfoListener);
        videoView.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
        videoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        videoView.setOnCompletionListener(mOnCompletionListener);
        videoView.setOnErrorListener(mOnErrorListener);
        videoView.setOnVideoFrameListener(mOnVideoFrameListener);
        videoView.setOnAudioFrameListener(mOnAudioFrameListener);

        String url = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
        videoView.setVideoPath(url);

        // You can also use a custom `MediaController` widget
        MediaController mediaController = new MediaController(this, false, true);
        mediaController.setOnClickSpeedAdjustListener(mOnClickSpeedAdjustListener);
        videoView.setMediaController(mediaController);
    }


    @Override
    protected void onResume() {
        super.onResume();
        videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mToast = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.stopPlayback();
    }

    // 全屏
//    public void onClickSwitchScreen(View v) {
//        mDisplayAspectRatio = (mDisplayAspectRatio + 1) % 5;
//        mVideoView.setDisplayAspectRatio(mDisplayAspectRatio);
//        switch (videoView.getDisplayAspectRatio()) {
//            case PLVideoView.ASPECT_RATIO_ORIGIN:
//                break;
//            case PLVideoView.ASPECT_RATIO_FIT_PARENT:
//                break;
//            case PLVideoView.ASPECT_RATIO_PAVED_PARENT:
//                break;
//            case PLVideoView.ASPECT_RATIO_16_9:
//                break;
//            case PLVideoView.ASPECT_RATIO_4_3:
//                break;
//            default:
//                break;
//        }
//    }

    private PLMediaPlayer.OnInfoListener mOnInfoListener = new PLMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(PLMediaPlayer plMediaPlayer, int what, int extra) {
            Log.e("mOnInfoListener--------", "OnInfo, what = " + what + ", extra = " + extra);
            switch (what) {
                case PLMediaPlayer.MEDIA_INFO_BUFFERING_START:
                    break;
                case PLMediaPlayer.MEDIA_INFO_BUFFERING_END:
                    break;
                case PLMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
//                    showToastTips("first video render time: " + extra + "ms");
                    break;
                case PLMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                    break;
                case PLMediaPlayer.MEDIA_INFO_VIDEO_FRAME_RENDERING:
                    Log.e("info------11", "video frame rendering, ts = " + extra);
                    break;
                case PLMediaPlayer.MEDIA_INFO_AUDIO_FRAME_RENDERING:
                    Log.e("info------22", "audio frame rendering, ts = " + extra);
                    break;
                case PLMediaPlayer.MEDIA_INFO_VIDEO_GOP_TIME:
                    Log.e("info------33", "Gop Time: " + extra);
                    break;
                case PLMediaPlayer.MEDIA_INFO_SWITCHING_SW_DECODE:
                    Log.e("info------44", "Hardware decoding failure, switching software decoding!");
                    break;
                case PLMediaPlayer.MEDIA_INFO_METADATA:
                    Log.e("info------55", videoView.getMetadata().toString());
                    break;
                case PLMediaPlayer.MEDIA_INFO_VIDEO_BITRATE:
                case PLMediaPlayer.MEDIA_INFO_VIDEO_FPS:
//                    updateStatInfo();
                    break;
                case PLMediaPlayer.MEDIA_INFO_CONNECTED:
                    Log.e("info------66", "Connected !");
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    private PLMediaPlayer.OnErrorListener mOnErrorListener = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer mp, int errorCode) {
            Log.e("mOnErrorListener------", "Error happened, errorCode = " + errorCode);
            switch (errorCode) {
                case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                    /**
                     * SDK will do reconnecting automatically
                     */
                    return false;
                case PLMediaPlayer.ERROR_CODE_OPEN_FAILED:
                    break;
                case PLMediaPlayer.ERROR_CODE_SEEK_FAILED:
                    break;
                default:
                    break;
            }
            finish();
            return true;
        }
    };

    private PLMediaPlayer.OnCompletionListener mOnCompletionListener = new PLMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(PLMediaPlayer plMediaPlayer) {
            Log.e("mOnCompletListener-----", "Play Completed !");
            finish();
        }
    };

    private PLMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new PLMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(PLMediaPlayer plMediaPlayer, int precent) {
            Log.e("mOnBUpdateListener----", "onBufferingUpdate: " + precent);
        }
    };

    private PLMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener = new PLMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int width, int height) {
            Log.e("mOnVideoSizeChan-----", "onVideoSizeChanged: width = " + width + ", height = " + height);
        }
    };

    private PLMediaPlayer.OnVideoFrameListener mOnVideoFrameListener = new PLMediaPlayer.OnVideoFrameListener() {
        @Override
        public void onVideoFrameAvailable(byte[] data, int size, int width, int height, int format, long ts) {
            Log.e("mOnVideoFrameLis-----", "onVideoFrameAvailable: " + size + ", " + width + " x " + height + ", i420, " + ts);
        }
    };

    private PLMediaPlayer.OnAudioFrameListener mOnAudioFrameListener = new PLMediaPlayer.OnAudioFrameListener() {
        @Override
        public void onAudioFrameAvailable(byte[] data, int size, int samplerate, int channels, int datawidth, long ts) {
            Log.e("mOnAudioFrameLis-----", "onAudioFrameAvailable: " + size + ", " + samplerate + ", " + channels + ", " + datawidth + ", " + ts);
        }
    };

    private MediaController.OnClickSpeedAdjustListener mOnClickSpeedAdjustListener = new MediaController.OnClickSpeedAdjustListener() {
        @Override
        public void onClickNormal() {
            // 0x0001/0x0001 = 2
            videoView.setPlaySpeed(0X00010001);
        }

        @Override
        public void onClickFaster() {
            // 0x0002/0x0001 = 2
            videoView.setPlaySpeed(0X00020001);
        }

        @Override
        public void onClickSlower() {
            // 0x0001/0x0002 = 0.5
            videoView.setPlaySpeed(0X00010002);
        }
    };
}
