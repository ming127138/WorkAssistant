package com.gzrijing.workassistant.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.gzrijing.workassistant.entity.PicUrl;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.ToastUtil;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;


public class ReportProgressService extends IntentService {

    private Handler handler = new Handler();
    private String id;

    public ReportProgressService() {
        super("ReportProgressService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        ArrayList<PicUrl> picUrls = intent.getParcelableArrayListExtra("picUrls");
        String userNo = intent.getStringExtra("userNo");
        String orderId = intent.getStringExtra("orderId");
        String content = intent.getStringExtra("content");

        RequestBody requestBody = new FormEncodingBuilder()
                .add("cmd", "doconsreport")
                .add("fileno", orderId)
                .add("userno", userNo)
                .add("content", content)
                .build();

        HttpUtils.sendHttpPostRequest(requestBody, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if(response.equals("Error")){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(ReportProgressService.this, "汇报失败", Toast.LENGTH_SHORT);
                            stopSelf();
                        }
                    });
                }else{
                    id = response;
                }
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(ReportProgressService.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                        stopSelf();
                    }
                });
            }
        });

        String[] key = {"cmd", "userno", "relationid", "picdescription"};
        String[] value = {"uploadconstaskpic", userNo, id, ""};

        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        for (int i = 0; i < key.length; i++) {
            builder.addFormDataPart(key[i], value[i]);
        }

        for(PicUrl picUrl : picUrls){
            File file = new File(picUrl.getPicUrl());
            if(file != null){
                String fileName = file.getName();
                Log.e("fileName", fileName);
                RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                builder.addFormDataPart("constaskpic", fileName, fileBody);
            }
        }

        HttpUtils.sendHttpPostRequest(builder.build(), new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if(response.equals("Error")){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(ReportProgressService.this, "上传图片失败", Toast.LENGTH_SHORT);
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(ReportProgressService.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                    }
                });
            }
        });

    }

    private String guessMimeType(String path)
    {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null)
        {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
