package com.showtime.lp.utils;

import android.util.Log;

import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Trouble Maker
 * @date: 2018/2/6 0006
 * @Description:
 */
public class EmptyClient extends WebSocketClient {

    public EmptyClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public EmptyClient(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
//        send("Hello, it is me. Mario :)");
        Map<String, Object> body = new HashMap<>();
        body.put("ver", 1);
        body.put("op", 7);
        body.put("seq", 0);
        Map<String, Object> item = new HashMap<>();
        item.put("token", "DKLDR2-abd003e7dbc65a35813cb99b26051bc742346409");
        item.put("issave", 0);
        body.put("body", item);
        Gson gson = new Gson();
        String string = gson.toJson(body);
        send(string);
        Log.e("connection open------", "-------");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.e("closed exit code-----", code + " additional info: " + reason);
    }

    @Override
    public void onMessage(String message) {
        Log.e("received message:----- ", message);
    }

    @Override
    public void onMessage(ByteBuffer message) {
        Log.e("received Byte------", message.toString());
    }

    @Override
    public void onError(Exception ex) {
        Log.e("error occurred:------",  ""+ ex);
    }

//    public static void main(String[] args) throws URISyntaxException {
//        WebSocketClient client = new EmptyClient(new URI("ws://localhost:8887"));
//        client.connect();
//    }
}
