package com.gzrijing.workassistant.util;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class HttpUtil {

    private static final String URLPath = "http://120.24.62.15:81/main.ashx";

    private static OkHttpClient client = new OkHttpClient();

    private static String httpGetRequest(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String httpLogin(String userName, String password) {
        String result = null;
        String loginURL = URLPath + "?cmd=login&userno=" + userName + "&pwd="
                + password;
        try {
            result = httpGetRequest(loginURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
