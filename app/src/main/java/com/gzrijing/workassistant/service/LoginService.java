package com.gzrijing.workassistant.service;

import android.util.Log;

import com.gzrijing.workassistant.entity.User;
import com.gzrijing.workassistant.util.HttpUtil;

import java.util.List;

public class LoginService {
    public void login(String userName, String password){
        String jsonData = HttpUtil.httpLogin(userName, password);
        Log.e("jsonData", jsonData);
    }
}
