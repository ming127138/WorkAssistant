package com.gzrijing.workassistant.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.gzrijing.workassistant.entity.ReportComplete;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import java.util.ArrayList;

public class ReportCompleteService extends IntentService {

    private String orderId;
    private String userNo;
    private String supervision;
    private String supervisionname;
    private String supervisiontel;
    private String meterno;
    private String installname;
    private String earthworkname;
    private String drainsize;
    private String draintime;
    private String safename;
    private String electricname;
    private String begindate;
    private String finishdate;
    private String acceptdate;
    private String conscontent;
    private String earthworkcontent;

    public ReportCompleteService() {
        super("ReportCompleteService");
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        initData(intent);

        RequestBody requestBody = getRequestBody(intent);

        HttpUtils.sendHttpPostRequest(requestBody, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.e("response", response);
                if (response.equals("ok")) {
                    Intent intent1 = new Intent("action.com.gzrijing.workassistant.ReportComplete");
                    intent1.putExtra("result", "汇报成功");
                    sendBroadcast(intent1);
                } else {
                    Intent intent1 = new Intent("action.com.gzrijing.workassistant.ReportComplete");
                    intent1.putExtra("result", "汇报失败");
                    sendBroadcast(intent1);
                }
            }

            @Override
            public void onError(Exception e) {
                Intent intent1 = new Intent("action.com.gzrijing.workassistant.ReportComplete");
                intent1.putExtra("result", "与服务器断开连接");
                sendBroadcast(intent1);
            }
        });

    }

    private void initData(Intent intent) {
        SharedPreferences app = getSharedPreferences(
                "saveUser", MODE_PRIVATE);
        userNo = app.getString("userNo", "");

        supervision = "noc";
        supervisionname = "noc";
        supervisiontel = "noc";
        meterno = "noc";
        installname = "noc";
        earthworkname = "noc";
        drainsize = "noc";
        draintime = "noc";
        safename = "noc";
        electricname = "noc";
        begindate = "noc";
        finishdate = "noc";
        acceptdate = "noc";
        conscontent = "noc";
        earthworkcontent = "noc";

        orderId = intent.getStringExtra("orderId");
    }

    private RequestBody getRequestBody(Intent intent) {
        ArrayList<ReportComplete> list = intent.getParcelableArrayListExtra("reportComplete");
        for (ReportComplete reportComplete : list) {
            if(reportComplete.getValue() !=null && !reportComplete.getValue().equals("")){
                if (reportComplete.getKey().equals("监理单位")) {
                    supervision = reportComplete.getValue();
                }
                if (reportComplete.getKey().equals("监理员")) {
                    supervisionname = reportComplete.getValue();
                }
                if (reportComplete.getKey().equals("监理联系电话")) {
                    supervisiontel = reportComplete.getValue();
                }
                if (reportComplete.getKey().equals("水表编号")) {
                    meterno = reportComplete.getValue();
                }
                if (reportComplete.getKey().equals("安装人员")) {
                    installname = reportComplete.getValue();
                }
                if (reportComplete.getKey().equals("土方人员")) {
                    earthworkname = reportComplete.getValue();
                }
                if (reportComplete.getKey().equals("排水口径")) {
                    drainsize = reportComplete.getValue();
                }
                if (reportComplete.getKey().equals("排水时间")) {
                    draintime = reportComplete.getValue();
                }
                if (reportComplete.getKey().equals("安全员")) {
                    safename = reportComplete.getValue();
                }
                if (reportComplete.getKey().equals("电工")) {
                    electricname = reportComplete.getValue();
                }
                if (reportComplete.getKey().equals("施工日期")) {
                    begindate = reportComplete.getValue();
                }
                if (reportComplete.getKey().equals("完工日期")) {
                    finishdate = reportComplete.getValue();
                }
                if (reportComplete.getKey().equals("验收日期")) {
                    acceptdate = reportComplete.getValue();
                }
                if (reportComplete.getKey().equals("施工内容")) {
                    conscontent = reportComplete.getValue();
                }
                if (reportComplete.getKey().equals("土方项目")) {
                    earthworkcontent = reportComplete.getValue();
                }
            }

        }

        RequestBody requestBody = new FormEncodingBuilder()
                .add("cmd", "dofinishcons")
                .add("userno", userNo)
                .add("fileno", orderId)
                .add("supervision", supervision)
                .add("supervisionname", supervisionname)
                .add("supervisiontel", supervisiontel)
                .add("meterno", meterno)
                .add("installname", installname)
                .add("earthworkname", earthworkname)
                .add("drainsize", drainsize)
                .add("draintime", draintime)
                .add("safename", safename)
                .add("electricname", electricname)
                .add("begindate", begindate)
                .add("finishdate", finishdate)
                .add("acceptdate", acceptdate)
                .add("conscontent", conscontent)
                .add("earthworkcontent", earthworkcontent)
                .build();

        return requestBody;
    }

}
