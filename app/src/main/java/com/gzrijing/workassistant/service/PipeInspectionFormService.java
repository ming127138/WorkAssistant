package com.gzrijing.workassistant.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.gzrijing.workassistant.entity.PicUrl;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;

public class PipeInspectionFormService extends IntentService {

    public PipeInspectionFormService() {
        super("PipeInspectionFormService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ArrayList<PicUrl> picUrls = intent.getParcelableArrayListExtra("picUrls");
        picUrls.remove(0);
        String userNo = intent.getStringExtra("userNo");
        String type = intent.getStringExtra("type");
        String id = intent.getStringExtra("id");

        String jsonData = intent.getStringExtra("jsonData");
        Log.e("jsonData", jsonData);


        String[] key = {"cmd", "userno", "class", "picdescription", "fileno"};
        String[] value = {"uploadtaskpicValve", userNo, type, "", id};

        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        for (int i = 0; i < key.length; i++) {
            Log.e("key", key[i]);
            Log.e("value", value[i]);
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
                Log.e("response", "111111111");
                Log.e("response", response);

            }

            @Override
            public void onError(Exception e) {
                Log.e("e", e.toString());
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
