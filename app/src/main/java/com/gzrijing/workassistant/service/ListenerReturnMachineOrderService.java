package com.gzrijing.workassistant.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.NotificationCompat;

import com.gzrijing.workassistant.receiver.NotificationReceiver;

public class ListenerReturnMachineOrderService extends IntentService {

    private Handler handler = new Handler();

    public ListenerReturnMachineOrderService() {
        super("ListenerReturnMachineOrderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent intent1 = new Intent("action.com.gzrijing.workassistant.ReturnMachine");
        sendBroadcast(intent1);
        sendNotification();

    }

    private void sendNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("有一条新的退机任务更新")
                .setTicker("有一条新的退机任务更新")
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.ic_notification_clear_all)
                .build();
        notification.defaults = Notification.DEFAULT_SOUND;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(0, notification);
    }

}
