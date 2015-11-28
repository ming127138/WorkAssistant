package com.gzrijing.workassistant.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gzrijing.workassistant.db.BusinessData;
import com.gzrijing.workassistant.db.DetailedInfoData;
import com.gzrijing.workassistant.db.ImageData;
import com.gzrijing.workassistant.entity.BusinessByLeader;
import com.gzrijing.workassistant.entity.BusinessByWorker;
import com.gzrijing.workassistant.entity.DetailedInfo;
import com.gzrijing.workassistant.entity.PicUrl;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.receiver.NotificationReceiver;
import com.gzrijing.workassistant.util.ActivityManagerUtil;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.ImageUtils;
import com.gzrijing.workassistant.util.JsonParseUtils;
import com.gzrijing.workassistant.util.ToastUtil;
import com.gzrijing.workassistant.view.LeaderFragment;
import com.gzrijing.workassistant.view.WorkerFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class GetWorkerBusinessService extends IntentService {

    private String userNo;
    private ImageLoader mImageLoader;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            List<BusinessByWorker> list = (List<BusinessByWorker>) msg.obj;
            WorkerFragment.orderList.addAll(list);
            WorkerFragment.orderListByWorker.addAll(list);
            WorkerFragment.adapter.notifyDataSetChanged();
        }
    };


    public GetWorkerBusinessService() {
        super("GetWorkerBusinessService");
        mImageLoader = ImageLoader.getInstance();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sp = getSharedPreferences("saveUser", MODE_PRIVATE);
        userNo = sp.getString("userNo", "");

        String date = "2015-10-01 00:00";
        String url = null;
        try {
            url = "?cmd=getmycons&userno=00004&fileno=&begindate=" + URLEncoder.encode(date, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpUtils.sendHttpGetRequest(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                saveData(response);
                sendNotification();
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(GetWorkerBusinessService.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }


    private void saveData(String data) {
        List<BusinessByWorker> list = JsonParseUtils.getWorkerBusiness(data);
        for (BusinessByWorker order : list) {
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
            List<PicUrl> picUrls = order.getPicUrls();
            for (final PicUrl picUrl : picUrls) {
                mImageLoader.loadImage(HttpUtils.imageURLPath + picUrl.getPicUrl(), new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        try {
                            ImageUtils.saveFile(GetWorkerBusinessService.this, loadedImage, picUrl.getPicUrl());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                ImageData data3 = new ImageData();
                data3.setUrl(picUrl.getPicUrl());
                data3.save();
                data1.getImageDataList().add(data3);
            }
            data1.save();
        }

        if (ActivityManagerUtil.activities.size() != 0) {
            Message msg = handler.obtainMessage();
            msg.obj = list;
            handler.sendMessage(msg);
        }
    }

    private void sendNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("这就是通知的头")
                .setTicker("这是通知的ticker")
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.ic_notification_clear_all)
                .build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(1, notification);
    }
}