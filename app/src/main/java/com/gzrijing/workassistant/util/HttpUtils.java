package com.gzrijing.workassistant.util;

import android.util.Log;

import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HttpUtils {
    /**
     * get请求路径
     */
    private static final String URLPath = "http://120.24.62.15:90/main.ashx";
    /**
     * post请求路径
     */
    private static final String postURLPath = "http://120.24.62.15:90/mainpost.ashx";
    /**
     * 图片请求路径
     */
    public static final String imageURLPath = "http://120.24.62.15:90";

    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    public static void sendHttpGetRequest(final String url, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder().url(URLPath + url).build();
                    Response response = mOkHttpClient.newCall(request).execute();
                    if (listener != null) {
                        listener.onFinish(response.body().string());
                    }
                } catch (IOException e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }

    public static void sendHttpPostRequest(final RequestBody requestBody,
                                           final HttpCallbackListener listener) {
        mOkHttpClient.setWriteTimeout(100, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(100, TimeUnit.SECONDS);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url(postURLPath)
                            .post(requestBody)
                            .build();
                    Response response = mOkHttpClient.newCall(request).execute();
                    if (listener != null) {
                        listener.onFinish(response.body().string());
                    }
                } catch (IOException e) {
                    if (listener != null) {
                        listener.onError(e);
                    }
                }
            }
        }).start();
    }
}
