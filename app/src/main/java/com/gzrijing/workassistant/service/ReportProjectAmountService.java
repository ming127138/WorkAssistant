package com.gzrijing.workassistant.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.gzrijing.workassistant.entity.PicUrl;
import com.gzrijing.workassistant.entity.Supplies;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.ToastUtil;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;


public class ReportProjectAmountService extends IntentService {

    private Handler handler = new Handler();

    public ReportProjectAmountService() {
        super("ReportProjectAmountService");
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        final String flag = intent.getStringExtra("flag");
        String userNo = intent.getStringExtra("userNo");
        String orderId = intent.getStringExtra("orderId");
        String content = intent.getStringExtra("content");
        String civil = intent.getStringExtra("civil");
        ArrayList<Supplies> suppliesList = intent.getParcelableArrayListExtra("suppliesList");

        JSONArray jsonArray = new JSONArray();
        for(Supplies supplies : suppliesList){
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("MakingNo", supplies.getId());
                jsonObject.put("Qty", supplies.getNum());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        RequestBody requestBody = new FormEncodingBuilder()
                .add("cmd", "doconsconfirm")
                .add("userno", userNo)
                .add("fileno", orderId)
                .add("conscontent", content)
                .add("earthworkcontent", civil)
                .add("consmakingjson", jsonArray.toString())
                .build();

        HttpUtils.sendHttpPostRequest(requestBody, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.e("respone", response);
                if (response.substring(0, 1).equals("E")) {
                    Intent intent1 = new Intent("action.com.gzrijing.workassistant.ReportProjectAmountFragment");
                    intent1.putExtra("result", "汇报失败");
                    sendBroadcast(intent1);
                } else {
                    Intent intent1 = new Intent("action.com.gzrijing.workassistant.ReportProjectAmountFragment");
                    intent1.putExtra("result", "汇报成功");
                    intent1.putExtra("flag", flag);
                    sendBroadcast(intent1);
                }
            }

            @Override
            public void onError(Exception e) {
                Intent intent1 = new Intent("action.com.gzrijing.workassistant.ReportProjectAmountFragment");
                intent1.putExtra("result", "与服务器断开连接");
                sendBroadcast(intent1);
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
