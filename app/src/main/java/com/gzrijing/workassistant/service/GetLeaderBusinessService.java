package com.gzrijing.workassistant.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.NotificationCompat;

import com.gzrijing.workassistant.base.MyApplication;
import com.gzrijing.workassistant.db.BusinessData;
import com.gzrijing.workassistant.db.DetailedInfoData;
import com.gzrijing.workassistant.entity.BusinessByLeader;
import com.gzrijing.workassistant.entity.DetailedInfo;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtil;
import com.gzrijing.workassistant.util.JsonParseUtil;
import com.gzrijing.workassistant.view.MainActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class GetLeaderBusinessService extends IntentService{

    private String userNo;

    public GetLeaderBusinessService() {
        super("GetLeaderBusinessService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sp = getSharedPreferences("saveUser", MODE_PRIVATE);
        userNo = sp.getString("userNo", "");

        String date = "2015-10-29 00:00";
        String url = null;
        try {
            url = "?cmd=getconstruction&userno=00004&begindate=" + URLEncoder.encode(date, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpUtil.sendHttpGetRequest(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                saveData(response);
                sendNotification();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }


    private void saveData(String data){
        List<BusinessByLeader> list = JsonParseUtil.getLeaderBusiness(data);
        for(BusinessByLeader order : list){
            BusinessData data1 = new BusinessData();
            data1.setUser(userNo);
            data1.setOrderId(order.getOrderId());
            data1.setUrgent(order.isUrgent());
            data1.setType(order.getType());
            data1.setState(order.getState());
            data1.setDeadline(order.getDeadline());
            data1.setFlag("确认收到");
            List<DetailedInfo> infos = order.getDetailedInfos();
            for (DetailedInfo info : infos) {
                DetailedInfoData data2 = new DetailedInfoData();
                data2.setKey(info.getKey());
                data2.setValue(info.getValue());
                data2.save();
                data1.getDetailedInfoList().add(data2);
            }
            data1.save();
        }
    }

    private void sendNotification(){
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getContext(), 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(
                MyApplication.getContext())
                .setTicker("你有一条任务").setContentInfo("contentInfo")
                .setContentTitle("日京管理通知")
                .setContentText("有临时任务，请赶紧查看")
                .setAutoCancel(true).setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL).build();
        notification.flags = Notification.FLAG_INSISTENT;
        manager.notify(0, notification);
    }

}
