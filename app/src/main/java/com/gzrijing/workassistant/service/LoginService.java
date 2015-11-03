package com.gzrijing.workassistant.service;

import com.gzrijing.workassistant.entity.User;
import com.gzrijing.workassistant.util.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginService {
    public User login(String userName, String password) {
        String jsonData = HttpUtil.httpLogin(userName, password);
        if (jsonData == null) {
            return null;
        }

        if(jsonData.substring(0, 5).equals("Error")){
            User user = new User();
            user.setUserNo("Error");
            return user;
        }

        User user = jsonDataParse(jsonData);
        return user;
    }

    private User jsonDataParse(String jsonData) {
        User user = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            String userNo = jsonObject.getString("UserNo");
            String userName = jsonObject.getString("UserName");
            String userDept = jsonObject.getString("UserDept");
            String userSit = jsonObject.getString("UserSit");
            user = new User(userNo, userName, userDept, userSit);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
