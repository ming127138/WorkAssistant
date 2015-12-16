package com.gzrijing.workassistant.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.gzrijing.workassistant.base.MyApplication;
import com.gzrijing.workassistant.db.BusinessData;
import com.gzrijing.workassistant.db.SuppliesNoData;
import com.gzrijing.workassistant.entity.Supplies;
import com.gzrijing.workassistant.service.GetLeaderBusinessService;
import com.gzrijing.workassistant.service.GetWorkerBusinessService;
import com.igexin.sdk.PushConsts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.List;

public class MainReceiver extends BroadcastReceiver {
    /**
     * 推送入口
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences app = context.getSharedPreferences(
                "saveUser", context.MODE_PRIVATE);
        String userNo = app.getString("userNo", "");

        Bundle bundle = intent.getExtras();
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                byte[] payload = bundle.getByteArray("payload");
                if (payload != null) {
                    String data = new String(payload);
                    Log.e("data", data.toString());
                    try {
                        JSONArray jsonArray = new JSONArray(data);
                        for(int i = 0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String user = jsonObject.getString("user");
                            String cmd = jsonObject.getString("cmd");
                            String orderId = jsonObject.getString("orderId");
                            String suppliesApplyId = jsonObject.getString("");
                            if(userNo.equals(user)){
                                if(cmd.equals("getconstruction")){
                                    getLeaderBusiness();
                                }
                                if(cmd.equals("getmycons")){
                                    getLeaderBusiness();
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    /**
     * 获取Leader工程单
     */
    private void getLeaderBusiness() {
        Intent intent = new Intent(MyApplication.getContext(), GetLeaderBusinessService.class);
        MyApplication.getContext().startService(intent);
    }

    /**
     * 获取worker工程单
     */
    private void getWorkerBusiness() {
        Intent intent = new Intent(MyApplication.getContext(), GetWorkerBusinessService.class);
        MyApplication.getContext().startService(intent);
    }

    /**
     * 监听材料申请单状态
     */
    private void listenerSuppliesState(String userNo, String orderId){

    }
}
