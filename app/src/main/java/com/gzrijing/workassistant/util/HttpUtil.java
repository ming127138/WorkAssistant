package com.gzrijing.workassistant.util;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class HttpUtil {

    private static final String URLPath = "http://120.24.62.15:90/main.ashx";
    private static final String postURLPath = "http://120.24.62.15:90/mainpost.ashx";

    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    public static String httpLogin(String userName, String password) {
        String result = null;
        RequestBody formBody = new FormEncodingBuilder()
                .add("cmd", "login")
                .add("userno", userName)
                .add("pwd", password)
                .build();

        Request request = new Request.Builder()
                .url(postURLPath)
                .post(formBody)
                .build();

        try {
            Response response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String httpGetLeaderBusiness(String userNo, String time) {
        String result = null;
        String url = null;
        try {
            url = URLPath+"?cmd=getconstruction&userno="+userNo+"&begindate="+  URLEncoder.encode(time, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            Request request = new Request.Builder().url(url).build();
            Response response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
