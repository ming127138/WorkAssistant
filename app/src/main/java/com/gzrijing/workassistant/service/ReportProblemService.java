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


public class ReportProblemService extends IntentService {

    private Handler handler = new Handler();

    public ReportProblemService() {
        super("ReportProblemService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        ArrayList<PicUrl> picUrls = intent.getParcelableArrayListExtra("picUrls");
        picUrls.remove(0);
        Log.e("picUrl", picUrls.size()+"");
        String userNo = intent.getStringExtra("userNo");
        String orderId = intent.getStringExtra("orderId");
        String content = intent.getStringExtra("content");

        String[] key = {"cmd", "fileno", "userno", "reason"};
        String[] value = {"dosaveconsaccident", orderId, userNo, content};

        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        for (int i = 0; i < key.length; i++) {
            builder.addFormDataPart(key[i], value[i]);
        }

        for (PicUrl picUrl : picUrls) {
            Log.e("picUrl", picUrl.getPicUrl());
            File file = new File(picUrl.getPicUrl());
            if (file != null) {
                String fileName = file.getName();
                Log.e("fileName", fileName);
                RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                builder.addFormDataPart("accidentpicjson", fileName, fileBody);
            }
        }

        HttpUtils.sendHttpPostRequest(builder.build(), new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if (response.equals("ok")) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(ReportProblemService.this, "汇报成功", Toast.LENGTH_SHORT);
                            Log.e("ok", "ok");
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(ReportProblemService.this, "汇报失败", Toast.LENGTH_SHORT);
                            Log.e("ok", "no");
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(ReportProblemService.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                        Log.e("ok", "与服务器断开连接");
                    }
                });
            }
        });

    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
