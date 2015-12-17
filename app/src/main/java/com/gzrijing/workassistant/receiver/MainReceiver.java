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
import com.gzrijing.workassistant.service.ListenerMachineApplyStateService;
import com.gzrijing.workassistant.service.ListenerMachineReceivedStateService;
import com.gzrijing.workassistant.service.ListenerSuppliesApplyStateService;
import com.gzrijing.workassistant.service.ListenerSuppliesApprovalOrderService;
import com.gzrijing.workassistant.service.ListenerSuppliesReceivedStateService;
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
                            String user = jsonObject.getString("UserNo");
                            String cmd = jsonObject.getString("cmd");
                            if(userNo.equals(user)){
                                if(cmd.equals("getconstruction")){
                                    getLeaderBusiness();
                                }
                                if(cmd.equals("getmycons")){
                                    getWorkerBusiness();
                                }
                                if(cmd.equals("getmymaterialneedmain")){
                                    String orderId = jsonObject.getString("FileNo");
                                    listenerSuppliesApplyState(user, orderId);
                                }
                                if(cmd.equals("getmaterialsend")){
                                    String orderId = jsonObject.getString("FileNo");
                                    String billNo = jsonObject.getString("BillNo");
                                    listenerSuppliesReceivedState(user, orderId, billNo);
                                }
                                if(cmd.equals("getmymachineneed1")){
                                    String orderId = jsonObject.getString("FileNo");
                                    String billNo = jsonObject.getString("BillNo");
                                    listenerMachineApplyState(user, orderId, billNo);
                                }
                                if(cmd.equals("getmymachineneed2")){
                                    String orderId = jsonObject.getString("FileNo");
                                    String billNo = jsonObject.getString("BillNo");
                                    listenerMachineReceivedState(user, orderId, billNo);
                                }
                                if(cmd.equals("getmaterialneedlist")){
                                    String orderId = jsonObject.getString("FileNo");
                                    listenerSuppliesApprovalOrder(user, orderId);
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
    private void listenerSuppliesApplyState(String userNo, String orderId){
        Intent intent = new Intent(MyApplication.getContext(), ListenerSuppliesApplyStateService.class);
        intent.putExtra("userNo", userNo);
        intent.putExtra("orderId", orderId);
        MyApplication.getContext().startService(intent);
    }

    /**
     * 监听哪些材料可以领用
     */
    private void listenerSuppliesReceivedState(String userNo, String orderId, String billNo){
        Intent intent = new Intent(MyApplication.getContext(), ListenerSuppliesReceivedStateService.class);
        intent.putExtra("userNo", userNo);
        intent.putExtra("orderId", orderId);
        intent.putExtra("billNo", billNo);
        MyApplication.getContext().startService(intent);
    }

    /**
     * 监听材料申请单状态
     */
    private void listenerMachineApplyState(String userNo, String orderId, String billNo){
        Intent intent = new Intent(MyApplication.getContext(), ListenerMachineApplyStateService.class);
        intent.putExtra("userNo", userNo);
        intent.putExtra("orderId", orderId);
        intent.putExtra("billNo", billNo);
        MyApplication.getContext().startService(intent);
    }

    /**
     * 监听哪些机械可以领用
     */
    private void listenerMachineReceivedState(String userNo, String orderId, String billNo){
        Intent intent = new Intent(MyApplication.getContext(), ListenerMachineReceivedStateService.class);
        intent.putExtra("userNo", userNo);
        intent.putExtra("orderId", orderId);
        intent.putExtra("billNo", billNo);
        MyApplication.getContext().startService(intent);
    }

    /**
     * 监听是否有材料审核单要审核
     */
    private void listenerSuppliesApprovalOrder(String userNo, String orderId){
        Intent intent = new Intent(MyApplication.getContext(), ListenerSuppliesApprovalOrderService.class);
        intent.putExtra("userNo", userNo);
        intent.putExtra("orderId", orderId);
        MyApplication.getContext().startService(intent);
    }
}
