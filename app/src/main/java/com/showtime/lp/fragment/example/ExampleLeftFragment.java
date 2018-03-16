package com.showtime.lp.fragment.example;

import android.content.Context;
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

import com.google.gson.Gson;
import com.showtime.lp.R;
import com.showtime.lp.activity.AActivity;
import com.showtime.lp.base.BaseFragment;
import com.showtime.lp.utils.ToastUtils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
        view = inflater.inflate(R.layout.frag_example_left, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void initView() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String date = format.format(new Date());
        Log.e("date-----------", date);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 99:
//                    ProgressDialogUtils.closeProgressDialog();
//                    ProgressDialogUtils.closeDiallog();
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
        }
    }

    @OnClick({R.id.toast1, R.id.toast2, R.id.toast3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toast1:
//                ProgressDialogUtils.createProgressDialog(getActivity());
//                ProgressDialogUtils.createDialog(getActivity());
                timer = new Timer();
                MyTimerTask timerTask = new MyTimerTask();
                timer.schedule(timerTask, 3000, 3000);
                break;
            case R.id.toast2:
                Map<String, Object> body = new HashMap<>();
                body.put("ver", 1);
                body.put("op", 7);
                body.put("seq", 0);
                Map<String, Object> item = new HashMap<>();
                item.put("token", "DKLDKJ-cb4a9d95dca40273913ba888c4d7ef45436a5ecd");
                item.put("issave", 0);
                body.put("body", item);
                Gson gson = new Gson();
                final String string = gson.toJson(body);
                Log.e("string------", string);

                try {
                    WebSocketClient webSocketClient = new WebSocketClient(new URI("ws://121.40.66.6:9025/sub")) {
                        @Override
                        public void onOpen(ServerHandshake handshakedata) {
                            Log.e("onOpen----", "------");
                            send(string);
                        }

                        @Override
                        public void onMessage(String message) {
                            Log.e("onMessage----", message);
                            Map<String, Object> body = new HashMap<>();
                            body.put("ver", 1);
                            body.put("op", 5);
                            body.put("seq", 1);
                            Map<String, Object> item = new HashMap<>();
                            item.put("type", "join");
                            item.put("data", "token");
                            item.put("roomid", "race_166_referee_7617");
                            body.put("body", item);
                            Gson gson = new Gson();
                            String string = gson.toJson(body);
                            send(string);
                        }

                        @Override
                        public void onClose(int code, String reason, boolean remote) {
                            Log.e("onClose----", code + "   " + reason + "    " + remote);
                        }

                        @Override
                        public void onError(Exception ex) {
                            Log.e("onError-----", ex + "");
                        }
                    };
                    webSocketClient.connect();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

//                try {
//                    EmptyClient emptyClient = new EmptyClient(new URI("ws://121.40.66.6:9025/sub"));
//                    emptyClient.send(string);
//                    emptyClient.connect();
//                } catch (URISyntaxException e) {
//                    e.printStackTrace();
//                }
                break;
            case R.id.toast3:
                number++;
                Intent intent = new Intent(getActivity(), AActivity.class);
                startActivity(intent);
                break;
        }
    }

    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            handler.sendEmptyMessage(99);
        }
    }

//    private void connect() {
//        try {
//            WebSocketClient webSocketClient = new WebSocketClient(new URI(GlobalSystemUtils.WEBSOCKET_URL)) {
//                @Override
//                public void onOpen(ServerHandshake handshakedata) {
//                    Log.e("onOpen----", "------");
//                    send(WebSocketUtil.getInstance().connectBody());
//                }
//
//                @Override
//                public void onMessage(String message) {
//                    if (TextUtils.isEmpty(message)) {
//                        return;
//                    }
//                    Log.e("onMessage----", message);
//                    Gson gson = new Gson();
//                    WSWebSocketMsg wsWebSocketMsg = gson.fromJson(message, WSWebSocketMsg.class);
//                    if (wsWebSocketMsg.getOp() == 8) {
//                        send(WebSocketUtil.getInstance().joinBody(wsWebSocketMsg.getSeq(), room));
//                    } else if (wsWebSocketMsg.getOp() == 5) {
//
//
//                    }
//
//                }
//
//                @Override
//                public void onClose(int code, String reason, boolean remote) {
//                    Log.e("onClose----", code + "   " + reason + "    " + remote);
//
//                }
//
//                @Override
//                public void onError(Exception ex) {
//                    Log.e("onError-----", ex + "");
//                }
//            };
//            webSocketClient.connect();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }
}
