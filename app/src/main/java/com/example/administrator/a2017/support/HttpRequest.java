package com.example.administrator.a2017.support;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

// 处理 HTTP 请求
public class HttpRequest {

    public static String serverHost = "10.1.5.111";
    public static int serverPort = 8088;

    public Thread doPostOnNewThread(String action, JSONObject json) {
        return doPostOnNewThread(action, json.toString());
    }

    public Thread doPostOnNewThread(final String action, final String params) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                doPost(action, params);
            }
        };
        thread.start();
        return thread;
    }

    public void doPost(String action, JSONObject json) {
        doPost(action, json.toString());
    }

    public void doPost(final String action, final String params) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(String.format("http://%s:%s/transportservice/action/%s", serverHost,serverPort,action));
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(6000);
            conn.setReadTimeout(6000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.connect();
            try (OutputStream out = conn.getOutputStream()) {
                out.write(params.getBytes("UTF-8"));
                out.flush();
            }
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            try (InputStream in = conn.getInputStream()) {
                int b;
                while ((b = in.read()) != -1) {
                    out.write(b);
                }
            }
            onResponse(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != conn) {
                conn.disconnect();
            }
        }
    }

    // 以字节数组形式处理 HTTP 服务器响应的数据，默认将字节数组转化为字符串并调用对应的 onResponse 方法；
    protected void onResponse(byte[] bytes) {
        try {
            String text = new String(bytes, "UTF-8");
            Log.i("Http Response", text);
            onResponse(text);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // 以字符串形式处理 HTTP 服务器响应的数据，默认将字符串转化为 JSON 对象并调用对应的 onResponse 方法；
    protected void onResponse(String text) {
        try {
            onResponse(new JSONObject(text));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // 以 JSON 对象形式处理 HTTP 服务器响应的数据；
    protected void onResponse(JSONObject json) {

    }

}
