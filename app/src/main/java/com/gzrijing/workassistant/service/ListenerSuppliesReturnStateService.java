package com.gzrijing.workassistant.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.NotificationCompat;

import com.gzrijing.workassistant.db.BusinessData;
import com.gzrijing.workassistant.db.SuppliesNoData;
import com.gzrijing.workassistant.receiver.NotificationReceiver;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ListenerSuppliesReturnStateService extends IntentService {

    private String userNo;
    private String orderId;

    public ListenerSuppliesReturnStateService() {
        super("ListenerSuppliesReturnStateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        userNo = intent.getStringExtra("userNo");
        orderId = intent.getStringExtra("orderId");
        String billNo = intent.getStringExtra("billNo");

        saveData(billNo);
        sendNotification();

    }

    private void saveData(String billNo) {
        BusinessData businessData = DataSupport.where("user = ? and orderId = ?", userNo, orderId)
                .find(BusinessData.class, true).get(0);
        List<SuppliesNoData> suppliesNoDataList = businessData.getSuppliesNoList();

        for(SuppliesNoData suppliesNoData : suppliesNoDataList){
            if(suppliesNoData.getReturnId() != null && suppliesNoData.getReturnId().equals(billNo)){
                ContentValues values = new ContentValues();
                values.put("returnState", "可退回");
                DataSupport.updateAll(SuppliesNoData.class, values, "returnId = ?", suppliesNoData.getReturnId());
            }
        }


        Intent intent = new Intent("action.com.gzrijing.workassistant.SuppliesApply.refresh");
        sendBroadcast(intent);

    }

    private void sendNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(orderId)
                .setContentText("有一条材料退回单信息更新")
                .setTicker("有一条材料退回单信息更新")
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.ic_notification_clear_all)
                .build();
        notification.defaults = Notification.DEFAULT_SOUND;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(0, notification);
    }

}
