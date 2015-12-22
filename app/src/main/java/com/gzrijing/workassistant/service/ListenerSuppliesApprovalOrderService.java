package com.gzrijing.workassistant.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.gzrijing.workassistant.db.BusinessData;
import com.gzrijing.workassistant.db.MachineNoData;
import com.gzrijing.workassistant.db.SuppliesNoData;
import com.gzrijing.workassistant.entity.MachineNo;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.receiver.NotificationReceiver;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.JsonParseUtils;
import com.gzrijing.workassistant.util.ToastUtil;

import org.litepal.crud.DataSupport;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class ListenerSuppliesApprovalOrderService extends IntentService {

    private Handler handler = new Handler();
    private String userNo;
    private String orderId;

    public ListenerSuppliesApprovalOrderService() {
        super("ListenerSuppliesApprovalOrderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        orderId = intent.getStringExtra("orderId");
        userNo = intent.getStringExtra("userNo");

        BusinessData businessData = DataSupport.where("user = ? and orderId = ?", userNo, orderId)
                .find(BusinessData.class, true).get(0);
        int num = businessData.getSuppliesApplyNum();
        ContentValues values = new ContentValues();
        values.put("suppliesApplyNum", num+1);
        DataSupport.updateAll(BusinessData.class, values, "user = ? and orderId = ?", userNo, orderId);
        sendNotification();

        Intent intent1 = new Intent("action.com.gzrijing.workassistant.LeaderFragment.SuppliesVerify");
        intent1.putExtra("orderId", orderId);
        sendBroadcast(intent1);

    }

    private void sendNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(orderId)
                .setContentText("有一条新的材料申请单需要审核")
                .setTicker("有一条新的材料申请单需要审核")
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.ic_notification_clear_all)
                .build();
        notification.defaults = Notification.DEFAULT_SOUND;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(0, notification);
    }

}
