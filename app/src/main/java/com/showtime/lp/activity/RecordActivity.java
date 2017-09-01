package com.showtime.lp.activity;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.showtime.lp.R;
import com.showtime.lp.base.BaseActivity;
import com.showtime.lp.view.record.AudioRecordButton;
import com.showtime.lp.view.record.MediaManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Trouble Maker
 * @date: 2017/8/31 0031
 * @Description: 录音功能
 */
public class RecordActivity extends BaseActivity {

    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.record_layout)
    AudioRecordButton recordLayout;
    private RecordExampleAdapter recordExampleAdapter;
    private List<Map<String, String>> list = new ArrayList<>();
    private View play_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        recordExampleAdapter = new RecordExampleAdapter(RecordActivity.this, list);
        listView.setAdapter(recordExampleAdapter);
        recordLayout.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() {
            @Override
            public void onFinished(float seconds, String filePath) {
                SimpleDateFormat sDateFormat = new SimpleDateFormat("hh:mm:ss");
                String date = sDateFormat.format(new Date());
                Map<String, String> map = new HashMap<>();
                map.put("time", date);
                map.put("url", filePath);
                list.add(map);
                recordExampleAdapter.notifyDataSetChanged();
                listView.setSelection(list.size() - 1);
                Toast.makeText(RecordActivity.this, filePath, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @OnClick({R.id.record_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_layout:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaManager.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaManager.release();
    }

    public class RecordExampleAdapter extends BaseAdapter {

        private List<Map<String, String>> list;
        private Context mContext;
        private LayoutInflater layoutInflater;

        public RecordExampleAdapter(Context context, List<Map<String, String>> list) {
            this.list = list;
            this.mContext = context;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.item_record_example, null);
                viewHolder.time_text = (TextView) convertView.findViewById(R.id.time_text);
                viewHolder.play_layout = (LinearLayout) convertView.findViewById(R.id.play_layout);
//                viewHolder.play_view = (View) convertView.findViewById(R.id.play_view);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final Map<String, String> map = list.get(position);
            viewHolder.time_text.setText(map.get("time"));
            viewHolder.play_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 播放动画
                    if (play_view != null) {//让第二个播放的时候第一个停止播放
                        play_view.setBackgroundResource(R.mipmap.icon_recording);
                        play_view = null;
                    }
                    play_view = v.findViewById(R.id.play_view);
                    play_view.setBackgroundResource(R.drawable.play);
                    AnimationDrawable drawable = (AnimationDrawable) play_view.getBackground();
                    drawable.start();
                    // 播放音频
                    MediaManager.playSound(map.get("url"), new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    play_view.setBackgroundResource(R.mipmap.icon_recording);
                                }
                            });
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView time_text;
            LinearLayout play_layout;
//            View play_view;
        }
    }


}
