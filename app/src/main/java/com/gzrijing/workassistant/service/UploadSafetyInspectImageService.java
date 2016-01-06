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


public class UploadSafetyInspectImageService extends IntentService {

    private Handler handler = new Handler();

    public UploadSafetyInspectImageService() {
        super("UploadSafetyInspectImageService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final ArrayList<PicUrl> picUrls = intent.getParcelableArrayListExtra("picUrls");
        picUrls.remove(0);
        final String userNo = intent.getStringExtra("userNo");
        String orderId = intent.getStringExtra("orderId");
        final String content = intent.getStringExtra("content");

        for (PicUrl picUrl : picUrls) {
            String[] key = {"cmd", "userno", "picdescription", "fileno"};
            String[] value = {"UpSafePic", userNo, "", orderId};

            MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
            for (int i = 0; i < key.length; i++) {
                builder.addFormDataPart(key[i], value[i]);
            }
            File file = new File(picUrl.getPicUrl());
            if (file != null) {
                String fileName = file.getName();
                RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                builder.addFormDataPart("constaskpic", fileName, fileBody);
            }
            HttpUtils.sendHttpPostRequest(builder.build(), new HttpCallbackListener() {
                @Override
                public void onFinish(String response) {
                    Log.e("res", response);
                    if (response.substring(0, 1).equals("E")) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast(UploadSafetyInspectImageService.this, "上传图片失败", Toast.LENGTH_SHORT);
                            }
                        });
                    }
                }

                @Override
                public void onError(Exception e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(UploadSafetyInspectImageService.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                        }
                    });
                }
            });

        }
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
