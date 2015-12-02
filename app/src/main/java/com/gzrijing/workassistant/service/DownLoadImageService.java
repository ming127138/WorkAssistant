package com.gzrijing.workassistant.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;

public class DownLoadImageService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String orderId = intent.getStringExtra("orderId");
        String url = "?cmd=getconspic&fileno=" + orderId + "&pictype=V_App_ConsAllPic";

        HttpUtils.sendHttpGetRequest(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Intent intent1 = new Intent("action.com.gzrijing.workassistant.Distribute");
                intent1.putExtra("response", response);
                intent1.putExtra("result", "汇报成功");
                sendBroadcast(intent1);
            }

            @Override
            public void onError(Exception e) {
                Intent intent1 = new Intent("action.com.gzrijing.workassistant.Distribute");
                intent1.putExtra("result", "与服务器断开连接");
                sendBroadcast(intent1);
            }
        });


        return super.onStartCommand(intent, flags, startId);
    }
}
