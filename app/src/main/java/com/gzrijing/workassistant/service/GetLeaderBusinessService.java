package com.gzrijing.workassistant.service;

import android.util.Log;

import com.gzrijing.workassistant.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetLeaderBusinessService {
    public void login(){

        String jsonData = HttpUtil.httpGetLeaderBusiness("00004", "2015-10-29");
        Log.e("jsonData", jsonData);

        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray jsonArray1 = jsonObject.getJSONArray("Detail");
                for(int j = 0; j<jsonArray1.length();j++){
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                    String key = jsonObject1.getString("key");
                    String value = jsonObject1.getString("value");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
