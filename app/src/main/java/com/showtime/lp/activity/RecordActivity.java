package com.showtime.lp.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.showtime.lp.model.NumberCodeBean;
import com.showtime.lp.model.RecordDataBean;
import com.showtime.lp.view.record.AudioRecordButton;
import com.showtime.lp.view.record.MediaManager;

import java.math.BigDecimal;
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
    @BindView(R.id.right_btn)
    TextView rightText;
    @BindView(R.id.record_text)
    TextView recordText;
    @BindView(R.id.right_layout)
    LinearLayout rightLayout;
    @BindView(R.id.title_score)
    TextView titleScore;
    @BindView(R.id.title_time)
    TextView titleTime;
    @BindView(R.id.right_text)
    TextView rightTextView;
    private RecordExampleAdapter recordExampleAdapter;
    private List<RecordDataBean> list = new ArrayList<>();
    private View play_view;
    private SQLiteDatabase dataBase;
    private int count = 0; // 每次打点的id标识
    private String idType;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);
        context = RecordActivity.this;
        idType = "id_1234"; // 根据比赛id和场地id来建表，确保唯一性
        initView();
    }

    private void initView() {
        rightTextView.setVisibility(View.VISIBLE);
        recordExampleAdapter = new RecordExampleAdapter(RecordActivity.this, list);
        listView.setAdapter(recordExampleAdapter);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(RecordActivity.this);
        dataBase = dataBaseHelper.getWritableDatabase();
        if (tabIsExist(idType)) { // 判断是否存在该表
            list.addAll(query());
            recordExampleAdapter.notifyDataSetChanged();
            leftText.setVisibility(View.VISIBLE);
            recordText.setText("长按语音打点");
            if (list != null && list.size() > 0) {
                titleScore.setText(list.get(0).getScore());
                count = list.get(list.size() - 1).getId();
            }
        } else {
            dataBase.execSQL("CREATE TABLE IF NOT EXISTS " + idType +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, id INTEGER, time TEXT," +
                    " recordUrl TEXT, number TEXT, numScore TEXT, score TEXT, duration TEXT, isRead TEXT)");
        }
        recordLayout.setOnClickListener(new View.OnClickListener() { // 点击补点
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(recordText.getText().toString()) && recordText.getText().toString().equals("开始评分")) {
                    leftText.setVisibility(View.VISIBLE);
                    recordText.setText("长按语音打点");
                    Toast.makeText(context, "开始评分", Toast.LENGTH_SHORT).show();
                } else {
                    addData("", "0''", "", "");
                }
            }
        });
        recordLayout.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() { // 长按录音
            @Override
            public void onFinished(float seconds, String filePath) {
                if (!TextUtils.isEmpty(recordText.getText().toString()) && recordText.getText().toString().equals("开始评分")) {
                    Toast.makeText(context, "请先点击开始评分", Toast.LENGTH_SHORT).show();
                } else {
                    double d = Double.parseDouble(String.valueOf(seconds));
                    String str = (double) Math.round(d * 10) / 10.0 + "''";
                    addData(filePath, str, "", "");
                }
            }
        });
    }

    @OnClick({R.id.record_layout, R.id.left_text, R.id.right_btn, R.id.right_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_layout:
                break;
            case R.id.left_text: // 开始结束
                leftText.setVisibility(View.GONE);
                recordText.setText("继续");
                break;
            case R.id.right_btn: // 补点
                addScoreDialog();
                break;
            case R.id.right_layout: // 提交比分
                submitDialog();
                break;
        }
    }

    /**
     * 打点（添加数据到数据库）
     *
     * @param path     录音文件地址
     * @param duration 录音时长
     * @param time     补点时间（只用于补点）
     * @param number   补点输入的编号（只用于补点）
     */
    private void addData(String path, String duration, String time, String number) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        if (!TextUtils.isEmpty(setTime)) {
//            String current = DateUtils.getStrToTime(format.format(new Date()));
//            String last = DateUtils.getStrToTime(setTime);
//            long l = Long.parseLong(current) - Long.parseLong(last);
//            if (l < 3) {
//                Toast.makeText(context, "点慢点！！！", Toast.LENGTH_SHORT).show();
//                return;
//            } else {
//                setTime = format.format(new Date());
//            }
//        } else {
//            setTime = format.format(new Date());
//        }
        count++;
        SimpleDateFormat sDateFormat = new SimpleDateFormat("hh:mm:ss");
        String date = sDateFormat.format(new Date());
        ContentValues cv = new ContentValues();
        cv.put("id", count);
        if (!TextUtils.isEmpty(time)) {
            cv.put("time", time);
        } else {
            cv.put("time", date);
        }
        cv.put("recordUrl", path);
        if (!TextUtils.isEmpty(number)) {
            cv.put("number", number);
        } else {
            cv.put("number", "");
        }
        cv.put("numScore", "");
        if (list != null && list.size() > 0) {
            cv.put("score", list.get(0).getScore());
        } else {
            cv.put("score", "5.0");
        }
        cv.put("duration", duration);
        cv.put("isRead", "0"); // 0 未读，1 已读
        dataBase.insert(idType, null, cv);
        list.clear();
        list.addAll(query());
        recordExampleAdapter.notifyDataSetChanged();
        listView.setSelection(list.size() - 1);
        if (!TextUtils.isEmpty(number)) { // 补点需要改变title的总得分
            ContentValues c = new ContentValues();
            if (list != null && list.size() > 0) {
                if (TextUtils.isEmpty(checkNumber(number))) {
                    Toast.makeText(context, "输入的编号不存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                BigDecimal b1 = new BigDecimal(list.get(0).getScore());
                BigDecimal b2 = new BigDecimal(checkNumber(number));
                for (int i = 0; i < list.size(); i++) {
                    c.put("score", String.valueOf(b1.subtract(b2)));
                    dataBase.update(idType, c, "id=?", new String[]{String.valueOf(list.get(i).getId())});
                }
                titleScore.setText(list.get(0).getScore());
            }
        }
    }

    /**
     * 查询该表（相当于请求接口刷新list）
     */
    private List<RecordDataBean> query() {
        List<RecordDataBean> persons = new ArrayList<>();
        Cursor c = dataBase.rawQuery("SELECT * FROM " + idType, null);
        while (c.moveToNext()) {
            RecordDataBean person = new RecordDataBean();
            person.setId(c.getInt(c.getColumnIndex("id")));
            person.setTime(c.getString(c.getColumnIndex("time")));
            person.setRecordUrl(c.getString(c.getColumnIndex("recordUrl")));
            person.setNumber(c.getString(c.getColumnIndex("number")));
            person.setNumScore(c.getString(c.getColumnIndex("numScore")));
            person.setScore(c.getString(c.getColumnIndex("score")));
            person.setDuration(c.getString(c.getColumnIndex("duration")));
            person.setIsRead(c.getString(c.getColumnIndex("isRead")));
            persons.add(person);
        }
        c.close();
        Log.e("list--------", persons.size() + "");
        return persons;
    }

    /**
     * 提交比分dialog
     */
    private void submitDialog() {
        View view = View.inflate(context, R.layout.dialog_record_submit, null);
        TextView submit_text = (TextView) view.findViewById(R.id.submit_text);
        TextView cancel_text = (TextView) view.findViewById(R.id.cancel_text);
        final EditText score_et = (EditText) view.findViewById(R.id.score_et);
        final MaterialDialog dialog = new MaterialDialog.Builder(context).customView(view, false).show();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager imm = (InputMethodManager) score_et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(score_et, 0);
            }
        }, 100);
        submit_text.setOnClickListener(new View.OnClickListener() {
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

    /**
     * 补点dialog
     */
    private void addScoreDialog() {
        View view = View.inflate(context, R.layout.dialog_record_add, null);
        TextView ok_text = (TextView) view.findViewById(R.id.ok_text);
        TextView cancel_text = (TextView) view.findViewById(R.id.cancel_text);
        final EditText hour_et = (EditText) view.findViewById(R.id.hour_et);
        final EditText minute_et = (EditText) view.findViewById(R.id.minute_et);
        final EditText second_et = (EditText) view.findViewById(R.id.second_et);
        final EditText code_et = (EditText) view.findViewById(R.id.code_et);
        final MaterialDialog dialog = new MaterialDialog.Builder(context).customView(view, false).show();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager imm = (InputMethodManager) hour_et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(hour_et, 0);
            }
        }, 100);
        dateSetting(hour_et, 23);
        dateSetting(minute_et, 59);
        dateSetting(second_et, 59);
        ok_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(hour_et.getText().toString()) && !TextUtils.isEmpty(minute_et.getText().toString())
                        && !TextUtils.isEmpty(second_et.getText().toString()) && !TextUtils.isEmpty(code_et.getText().toString())) {
                    dialog.dismiss();
                    String time = timeType(hour_et.getText().toString().trim()) + ":" + timeType(minute_et.getText().toString().trim()) + ":"
                            + timeType(second_et.getText().toString().trim());
                    addData("", "0''", time, code_et.getText().toString().trim());
                } else {
                    Toast.makeText(context, "请输入完整数据", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 统一时间格式
     */
    private String timeType(String time) {
        String str;
        if (time.length() == 1) {
            str = "0" + time;
        } else {
            str = time;
        }
        return str;
    }

    /**
     * 输入的时间验证
     */
    private void dateSetting(EditText editText, final int time) {
        InputFilter[] inputFilters = {new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String sourceText = source.toString();
                String destText = dest.toString();
                //验证删除等按键
                if (TextUtils.isEmpty(sourceText) || sourceText.contains("-")) {
                    return "";
                }
                if (Integer.valueOf(destText + sourceText) > time) {
                    return dest.subSequence(dstart, dend);
                }
                return dest.subSequence(dstart, dend) + sourceText;
            }
        }};
        editText.setFilters(inputFilters);
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

    /**
     * 本地动作编码和分数
     */
    private String checkNumber(String num) {
        List<NumberCodeBean> list = new ArrayList<>();
        NumberCodeBean bean1 = new NumberCodeBean();
        bean1.setNumber("70");
        bean1.setScore("-0.1");
        list.add(bean1);
        NumberCodeBean bean2 = new NumberCodeBean();
        bean2.setNumber("71");
        bean2.setScore("-0.2");
        list.add(bean2);
        NumberCodeBean bean3 = new NumberCodeBean();
        bean3.setNumber("72");
        bean3.setScore("-0.3");
        list.add(bean3);
        NumberCodeBean bean4 = new NumberCodeBean();
        bean4.setNumber("73");
        bean4.setScore("-0.1");
        list.add(bean4);
        NumberCodeBean bean5 = new NumberCodeBean();
        bean5.setNumber("74");
        bean5.setScore("-0.2");
        list.add(bean5);
        NumberCodeBean bean6 = new NumberCodeBean();
        bean6.setNumber("75");
        bean6.setScore("-0.3");
        list.add(bean6);
        NumberCodeBean bean7 = new NumberCodeBean();
        bean7.setNumber("76");
        bean7.setScore("-0.1");
        list.add(bean7);
        NumberCodeBean bean8 = new NumberCodeBean();
        bean8.setNumber("77");
        bean8.setScore("-0.1");
        list.add(bean8);
        NumberCodeBean bean9 = new NumberCodeBean();
        bean9.setNumber("78");
        bean9.setScore("-0.1");
        list.add(bean9);
        NumberCodeBean bean10 = new NumberCodeBean();
        bean10.setNumber("79");
        bean10.setScore("-0.1");
        list.add(bean10);
        List<String> numList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            numList.add(list.get(i).getNumber());
        }
        String score = null;
        for (int k = 0; k < list.size(); k++) {
            if (!numList.contains(num)) { // 判断输入的编号是否存在
                score = "";
                break;
            } else {
                if (num.equals(list.get(k).getNumber())) {
                    score = list.get(k).getScore().substring(1, list.get(k).getScore().length() - 1);
                }
            }
        }
        return score;
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

        private List<RecordDataBean> data;
        private Context mContext;
        private LayoutInflater layoutInflater;

        public RecordExampleAdapter(Context context, List<RecordDataBean> list) {
            this.data = list;
            this.mContext = context;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
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
                viewHolder.duration_text = (TextView) convertView.findViewById(R.id.duration_text);
                viewHolder.read_text = (TextView) convertView.findViewById(R.id.read_text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final RecordDataBean bean = data.get(position);
            viewHolder.time_text.setText(bean.getTime()); // 时间
            viewHolder.code_text.setText(bean.getNumber()); // 编码
            viewHolder.score_text.setText(bean.getNumScore()); // 编码对应的扣分
            viewHolder.duration_text.setText(bean.getDuration()); // 录音时长
            if (bean.getIsRead().equals("0")) {
                viewHolder.read_text.setVisibility(View.VISIBLE);
            } else {
                viewHolder.read_text.setVisibility(View.INVISIBLE);
            }
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
                    ContentValues cv = new ContentValues(); // 更新是否读取录音状态
                    cv.put("isRead", "1");
                    dataBase.update(idType, cv, "id=?", new String[]{String.valueOf(bean.getId())});
                    list.clear();
                    list.addAll(query());
                    notifyDataSetChanged();
                }
            });
            viewHolder.code_text.setOnClickListener(new View.OnClickListener() { // 输入编号
                @Override
                public void onClick(View v) {
                    codeDialog(mContext, viewHolder.code_text.getText().toString().trim(), bean);
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
            TextView duration_text;
            TextView read_text;
        }

        /**
         * 输入编码dialog
         */
        private void codeDialog(Context context, String number, final RecordDataBean bean) {
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
            }, 100);
            if (!TextUtils.isEmpty(number)) {
                code_et.setText(number);
            }
            submit_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(code_et.getText().toString())) {
                        Toast.makeText(mContext, "请输入编号", Toast.LENGTH_SHORT).show();
                    } else {
                        ContentValues cv = new ContentValues();
                        if (list != null && list.size() > 0) {
                            if (TextUtils.isEmpty(checkNumber(code_et.getText().toString().trim()))) {
                                Toast.makeText(mContext, "输入的编号不存在", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            BigDecimal b1 = new BigDecimal(list.get(0).getScore());
                            BigDecimal b2 = new BigDecimal(checkNumber(code_et.getText().toString().trim()));
                            for (int i = 0; i < list.size(); i++) {
                                cv.put("score", String.valueOf(b1.subtract(b2)));
                                dataBase.update(idType, cv, "id=?", new String[]{String.valueOf(list.get(i).getId())});
                            }
                        }
                        cv.put("number", code_et.getText().toString().trim());
                        dataBase.update(idType, cv, "id=?", new String[]{String.valueOf(bean.getId())});
                        list.clear();
                        list.addAll(query());
                        notifyDataSetChanged();
                        if (list != null && list.size() > 0) {
                            titleScore.setText(list.get(0).getScore());
                        }
                        dialog.dismiss();
                        Log.e("updata--------list", bean.getId() + "    " + list.size() + "     " + titleScore.getText());
                    }
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
