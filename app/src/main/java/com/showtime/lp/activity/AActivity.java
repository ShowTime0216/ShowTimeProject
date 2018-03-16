package com.showtime.lp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;
import com.showtime.lp.R;
import com.showtime.lp.base.BaseActivity;
import com.showtime.lp.utils.ToastUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * @author: Trouble Maker
 * @date: 2017/9/7 0007
 * @Description:
 */
public class AActivity extends BaseActivity {

    @BindView(R.id.left_layout)
    LinearLayout leftLayout;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.pieChartView)
    PieChartView pieChartView;
    @BindView(R.id.imageView)
    CircleImageView imageView;
    @BindView(R.id.edit1)
    EditText edit1;
    @BindView(R.id.edit2)
    EditText edit2;
    private int[] data = {21, 20, 9, 2, 8};
    private int[] colorData = {Color.parseColor("#ec063d"),
            Color.parseColor("#f1c704"),
            Color.parseColor("#c9c9c9"),
            Color.parseColor("#2bc208"),
            Color.parseColor("#333333")};
    List<SliceValue> values = new ArrayList<>();
    private PieChartData pieChardata;
    private String tag = "1";
    private WebSocket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        ButterKnife.bind(this);
        titleText.setText("Activity AAAA");
        text1.setText("跳转到B页面！！！");
        initView();
    }

    public enum StringNum {
        ONE, TWO, THREE
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        setIntent(intent);

    }

    private void initView() {

        ToastUtils.showToast(AActivity.this, String.valueOf(StringNum.ONE));

        Glide.with(AActivity.this)
                .load("https://wx1.sinaimg.cn/mw690/8d4b7be9gy1fmhfv93tufj205k05k3yg.jpg")
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(imageView);

        List<String> colorList = new ArrayList<>();
        colorList.add("#ff9900");
        colorList.add("#ff3300");
        colorList.add("#0000cc");
        colorList.add("#0099ff");
        colorList.add("#ffcc00");
        colorList.add("#66ccff");
        for (int i = 0; i < data.length; ++i) {
            SliceValue sliceValue = new SliceValue((float) data[i], colorData[i]);
            values.add(sliceValue);
        }

        pieChardata = new PieChartData();
        pieChardata.setHasLabels(true);//显示表情
        pieChardata.setHasLabelsOnlyForSelected(false);//不用点击显示占的百分比
        pieChardata.setHasLabelsOutside(false);//占的百分比是否显示在饼图外面
        pieChardata.setHasCenterCircle(true);//是否是环形显示
        pieChardata.setValueLabelBackgroundEnabled(false);
        pieChardata.setValues(values);//填充数据
        pieChardata.setValueLabelsTextColor(Color.WHITE);//设置值得颜色
        pieChardata.setValueLabelTextSize(8);
        pieChardata.setValueLabelBackgroundColor(Color.BLACK);//设置值得背景透明
        pieChardata.setCenterCircleScale(0.5f);//设置环形的大小级别 设置第二个圈的大小比例
//        pieChardata.setCenterText1("asd");//环形中间的文字1
//        pieChardata.setCenterText1Color(Color.GREEN);//文字颜色
//        pieChardata.setCenterText1FontSize(12);//文字大小
        pieChardata.setSlicesSpacing(0);//设置间隔为0

        pieChartView.setPieChartData(pieChardata);
        pieChartView.setValueSelectionEnabled(false);//选择饼图某一块变大
        pieChartView.setChartRotationEnabled(true);//设置饼图是否可以手动旋转
        pieChartView.setChartRotation(180, true);//设置饼图旋转角度，且是否为动画
        pieChartView.setValueTouchEnabled(true);
        pieChartView.setClickable(false);
        pieChartView.setCircleFillRatio(0);
        pieChartView.setFadingEdgeLength(0);
        pieChartView.setAlpha(0.9f);//设置透明度
        pieChartView.setCircleFillRatio(1f);//设置饼图大小
        pieChartView.setOnValueTouchListener(new PieChartOnValueSelectListener() {
            @Override
            public void onValueDeselected() {

            }

            @Override
            public void onValueSelected(int arg0, SliceValue value) {
                //选择对应图形后，在中间部分显示相应信息
                pieChardata.setCenterText1("ASD");
                pieChardata.setCenterText1Color(colorData[arg0]);
                pieChardata.setCenterText1FontSize(4);
                pieChardata.setCenterText2(value.getValue() + "(" + calPercent(arg0) + ")");
                pieChardata.setCenterText2Color(colorData[arg0]);
                pieChardata.setCenterText2FontSize(6);
            }
        });
    }

    private String calPercent(int i) {
        String result = "";
        int sum = 0;
        for (int a = 0; a < data.length; a++) {
            sum += data[a];
        }
        result = String.format("%.2f", (float) data[i] * 100 / sum) + "%";
        return result;
    }

    @OnClick({R.id.left_layout, R.id.text1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                String token = "DKLDKJ-cb4a9d95dca40273913ba888c4d7ef45436a5ecd";
                Map<String, Object> body = new HashMap<>();
                body.put("ver", 1);
                body.put("op", 7);
                body.put("seq", 0);

                Map<String, Object> item = new HashMap<>();
                item.put("token", token);
                item.put("issave", 0);
                body.put("body", item);
                Gson gson = new Gson();
                final String string = gson.toJson(body);
                Log.e("string------", string);

                AsyncHttpClient.getDefaultInstance().websocket("ws://121.40.66.6:9025/sub", null, new AsyncHttpClient.WebSocketConnectCallback() {
                    @Override
                    public void onCompleted(Exception ex, WebSocket webSocket) {
                        if (ex != null) {
                            ex.printStackTrace();
                            return;
                        }
                        socket = webSocket;
                        if (tag.equals("1")) {
                            tag = "2";
                            webSocket.send(string);
                        } else {
                            Timer timer = new Timer();
                            MyTimerTask timerTask = new MyTimerTask();
                            timer.schedule(timerTask, 10, 3000);
                        }
//                        webSocket.send(new byte[10]);
                        webSocket.setStringCallback(new WebSocket.StringCallback() {
                            @Override
                            public void onStringAvailable(String s) {
                                Log.e("s------", s);
                            }
                        });
                        webSocket.setDataCallback(new DataCallback() {
                            public void onDataAvailable(DataEmitter emitter, ByteBufferList byteBufferList) {
                                Log.e("s111------", "-----------------");
                                // note that this data has been read
                                byteBufferList.recycle();
                            }
                        });
                    }
                });
                break;
            case R.id.text1:
//                Intent intent = new Intent(AActivity.this, BActivity.class);
//                startActivity(intent);
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        text1.setText(df.format(date));

                        Log.e("111------", text1.getText().toString());
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date dates = null;
                        try {
                            dates = sdf.parse(text1.getText().toString());
                            SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Log.e("222------", ss.format(dates));

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(dates);
                            SimpleDateFormat sss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Log.e("333------", sss.format(calendar.getTime()));

                            String dayDuration = edit1.getText().toString().trim();
                            String hourDuration = edit2.getText().toString().trim();
                            if (!TextUtils.isEmpty(dayDuration) && !"0".equals(dayDuration) && !"00".equals(dayDuration)) {
                                calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(dayDuration));
                            }
                            if (!TextUtils.isEmpty(hourDuration) && !"0".equals(hourDuration) && !"00".equals(hourDuration)) {
                                calendar.add(Calendar.MINUTE, Integer.parseInt(hourDuration) * 60);
                            }
                            SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String dateStr = s.format(calendar.getTime());
                            Log.e("-------", dateStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }).setType(new boolean[]{true, true, true, true, true, false}) // 默认全部显示
                        .setLabel("年", "月", "日", "时", "分", "") //默认设置为年月日时分秒
                        .build();
                pvTime.setDate(Calendar.getInstance());
                pvTime.show();
                break;
            default:
                break;
        }
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            handler.sendEmptyMessage(99);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 99:
                    ToastUtils.showToastMsg(AActivity.this, "----", 1000);
                    Map<String, Object> map = new HashMap<>();
                    map.put("ver", 1);
                    map.put("op", 2);
                    map.put("seq", 0);
                    Map<String, Object> map1 = new HashMap<>();
                    map.put("body", map1);
                    Gson gson1 = new Gson();
                    final String string1 = gson1.toJson(map);
                    Log.e("string1------", string1);
                    socket.send(string1);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("AA-----", requestCode + "  " + resultCode + "  " + data);
    }
}
