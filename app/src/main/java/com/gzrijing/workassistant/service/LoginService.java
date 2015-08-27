package com.gzrijing.workassistant.service;

import android.util.Log;

import com.google.gson.Gson;
import com.gzrijing.workassistant.entity.User;
import com.gzrijing.workassistant.util.HttpUtil;

public class LoginService {
    public void getLoginData(String userName, String password){
        String jsonData = HttpUtil.httpLogin(userName, password);
        Gson gson = new Gson();
        User user = gson.fromJson(jsonData, User.class);
        Log.e("user", user.toString());
    }
}
