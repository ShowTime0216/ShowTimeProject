package com.showtime.lp.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.showtime.lp.R;
import com.showtime.lp.base.BaseActivity;
import com.showtime.lp.database.DataBaseHelper;
import com.showtime.lp.model.RecordDataBean;
import com.showtime.lp.utils.DateUtils;
import com.showtime.lp.view.record.AudioRecordButton;
import com.showtime.lp.view.record.MediaManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    @BindView(R.id.left_text)
    TextView leftText;
    @BindView(R.id.right_text)
    TextView rightText;
    @BindView(R.id.record_text)
    TextView recordText;
    private RecordExampleAdapter recordExampleAdapter;
    private List<RecordDataBean> list = new ArrayList<>();
    private View play_view;
    //    private SQLiteDatabase dbWrite;
//    private SQLiteDatabase dbRead;
    private SQLiteDatabase dataBase;
    private int count;
    private String idType;
    private Context context;
    private String setTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);
        context = RecordActivity.this;
        idType = "id_1234";
        initView();
    }

    private void initView() {
        recordExampleAdapter = new RecordExampleAdapter(RecordActivity.this, list);
        listView.setAdapter(recordExampleAdapter);
//        dataBase = openOrCreateDatabase("record.db", Context.MODE_PRIVATE, null);
//        dbWrite = dataBaseHelper.getWritableDatabase();
//        dbRead = dataBaseHelper.getReadableDatabase();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(RecordActivity.this);
        dataBase = dataBaseHelper.getWritableDatabase();
        if (tabIsExist(idType)) {
            list.addAll(query());
            recordExampleAdapter.notifyDataSetChanged();
            listView.setSelection(list.size() - 1);

            leftText.setVisibility(View.VISIBLE);
            recordText.setText("长按语音打点");
        } else {
            dataBase.execSQL("CREATE TABLE IF NOT EXISTS " + idType +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, id INTEGER, time TEXT, recordUrl TEXT)");
        }
        recordLayout.setOnClickListener(new View.OnClickListener() { // 点击补点
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(recordText.getText().toString().toString()) && recordText.getText().toString().toString().equals("开始评分")) {
                    leftText.setVisibility(View.VISIBLE);
                    recordText.setText("长按语音打点");
                } else {
                    addData("");
                }
            }
        });

        if (!TextUtils.isEmpty(recordText.getText().toString().toString()) && recordText.getText().toString().toString().equals("开始评分")) {
            return;
        } else {
            recordLayout.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() { // 长按录音
                @Override
                public void onFinished(float seconds, String filePath) {
//                Map<String, String> map = new HashMap<>();
//                map.put("time", date);
//                map.put("url", filePath);
//                list.add(map);
//                recordExampleAdapter.notifyDataSetChanged();
//                listView.setSelection(list.size() - 1);
//                Toast.makeText(RecordActivity.this, filePath, Toast.LENGTH_SHORT).show();

                    addData(filePath);
                }
            });
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(RecordActivity.this,
                        list.get(position).getId() + "     " + list.get(position).getTime() + "     " + list.get(position).getRecordUrl(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.record_layout, R.id.left_text, R.id.right_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_layout:
                break;
            case R.id.left_text: // 开始结束
                leftText.setVisibility(View.GONE);
                recordText.setText("继续");
                break;
            case R.id.right_text: // 补点
                addScoreDialog();
                break;
        }
    }

    /**
     * 打点（添加数据到数据库）
     */
    private void addData(String path) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (!TextUtils.isEmpty(setTime)) {
            String current = DateUtils.getStrToTime(format.format(new Date()));
            String last = DateUtils.getStrToTime(setTime);
            long l = Long.parseLong(current) - Long.parseLong(last);
            if (l < 3) {
                Toast.makeText(context, "点慢点---------------", Toast.LENGTH_SHORT).show();
                return;
            } else {
                setTime = format.format(new Date());
            }
        } else {
            setTime = format.format(new Date());
        }
        count++;
        SimpleDateFormat sDateFormat = new SimpleDateFormat("hh:mm:ss");
        String date = sDateFormat.format(new Date());
        ContentValues cv = new ContentValues();
        cv.put("id", count);
        cv.put("time", date);
        cv.put("recordUrl", path);
        dataBase.insert(idType, null, cv);
        list.clear();
        list.addAll(query());
        recordExampleAdapter.notifyDataSetChanged();
        listView.setSelection(list.size() - 1);
    }

    /**
     * 补点dialog
     */
    private void addScoreDialog() {
        View view = View.inflate(context, R.layout.dialog_record_add, null);
        TextView ok_text = (TextView) view.findViewById(R.id.ok_text);
        TextView cancel_text = (TextView) view.findViewById(R.id.cancel_text);
        final EditText hour_et = (EditText) view.findViewById(R.id.hour_et);
        EditText minute_et = (EditText) view.findViewById(R.id.minute_et);
        EditText second_et = (EditText) view.findViewById(R.id.second_et);
        EditText code_et = (EditText) view.findViewById(R.id.code_et);
        final MaterialDialog dialog = new MaterialDialog.Builder(context).customView(view, false).show();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager imm = (InputMethodManager) hour_et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(hour_et, 0);
            }
        }, 200);
        hour_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.e("hour---------1", s + "     " + start + "     " + after + "    " + count);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (TextUtils.isEmpty(s.toString()) || Integer.parseInt(s.toString()) > 24) {
//                    Log.e("hour---------", s + "     " + start + "     " + before + "    " + count);
//                    hour_et.setText(s + "");
//                    return;
//                }
//                hour_et.setText(start + "      " + before + "     " + s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ok_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public List<RecordDataBean> query() {
        List<RecordDataBean> persons = new ArrayList<>();
        Cursor c = dataBase.rawQuery("SELECT * FROM " + idType, null);
        while (c.moveToNext()) {
            RecordDataBean person = new RecordDataBean();
            person.setId(c.getInt(c.getColumnIndex("id")));
            person.setTime(c.getString(c.getColumnIndex("time")));
            person.setRecordUrl(c.getString(c.getColumnIndex("recordUrl")));
            persons.add(person);
        }
        c.close();
        return persons;
    }

    /**
     * 判断是否存在该表
     */
    public boolean tabIsExist(String id) {
        boolean result = false;
        try {
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='" + id + "' ";
            SQLiteDatabase db = openOrCreateDatabase("record.db", 0, null);
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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

    private class RecordExampleAdapter extends BaseAdapter {

        private List<RecordDataBean> list;
        private Context mContext;
        private LayoutInflater layoutInflater;

        public RecordExampleAdapter(Context context, List<RecordDataBean> list) {
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
                viewHolder.code_text = (TextView) convertView.findViewById(R.id.code_text);
                viewHolder.score_text = (TextView) convertView.findViewById(R.id.score_text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final RecordDataBean bean = list.get(position);
            viewHolder.time_text.setText(bean.getTime());
            viewHolder.play_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 播放动画
                    if (play_view != null) {//让第二个播放的时候第一个停止播放
                        play_view.setBackgroundResource(R.mipmap.icon_recording);
                        play_view = null;
                    }
                    // 播放音频
                    if (!TextUtils.isEmpty(bean.getRecordUrl())) {
                        play_view = v.findViewById(R.id.play_view);
                        play_view.setBackgroundResource(R.drawable.play);
                        AnimationDrawable drawable = (AnimationDrawable) play_view.getBackground();
                        drawable.start();
                        MediaManager.playSound(bean.getRecordUrl(), new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                play_view.setBackgroundResource(R.mipmap.icon_recording);
                            }
                        });
                    }
                }
            });
            viewHolder.code_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    codeDialog(mContext, viewHolder.code_text, viewHolder.code_text.getText().toString().trim());
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView time_text;
            LinearLayout play_layout;
            // View play_view;
            TextView code_text;
            TextView score_text;
        }

        private void codeDialog(Context context, final TextView text, String number) {
            View view = View.inflate(context, R.layout.dialog_record_code, null);
            final EditText code_et = (EditText) view.findViewById(R.id.code_et);
            TextView submit_text = (TextView) view.findViewById(R.id.submit_text); // 提交比分
            TextView check_text = (TextView) view.findViewById(R.id.check_text); // 查找编码
            TextView cancel_text = (TextView) view.findViewById(R.id.cancel_text); // 取消
            final MaterialDialog dialog = new MaterialDialog.Builder(context).customView(view, false).show();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    InputMethodManager imm = (InputMethodManager) code_et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(code_et, 0);
                }
            }, 200);
            if (!TextUtils.isEmpty(number)) {
                code_et.setText(number);
            }
            submit_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    text.setText(code_et.getText().toString());
                }
            });
            check_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            cancel_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

    }


}
