package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.DetailedInfoAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.DetailedInfo;
import com.gzrijing.workassistant.entity.PicUrl;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.JsonParseUtils;
import com.gzrijing.workassistant.util.ToastUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ReportInfoCompleteActivity extends BaseActivity {

    private String orderId;
    private ArrayList<DetailedInfo> infos = new ArrayList<DetailedInfo>();
    private ArrayList<PicUrl> picUrls = new ArrayList<PicUrl>();
    private String userNo;
    private Handler handler = new Handler();
    private ListView lv_info;
    private DetailedInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_info_complete);

        initViews();
        initData();
    }

    private void initData() {
        SharedPreferences app = getSharedPreferences(
                "saveUser", MODE_PRIVATE);
        userNo = app.getString("userNo", "");

        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        getCompleteInfo();
    }

    private void getCompleteInfo() {
        String url = null;
        try {
            url = "?cmd=getfinishconstruction&userno=" + URLEncoder.encode(userNo, "UTF-8") +
                    "&fileno=" + URLEncoder.encode(orderId, "UTF-8") + "&enddate=&isfinish=1";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpUtils.sendHttpGetRequest(url, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                Log.e("response", response);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<DetailedInfo> list = JsonParseUtils.getReportCompleteInfo(response);
                        ArrayList<PicUrl> picUrlList = JsonParseUtils.getReportCompletePicUrl(response);
                        infos.addAll(list);
                        picUrls.addAll(picUrlList);
                        adapter = new DetailedInfoAdapter(ReportInfoCompleteActivity.this, infos, picUrls, userNo, orderId);
                        lv_info.setAdapter(adapter);
                    }
                });

            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(ReportInfoCompleteActivity.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_info = (ListView) findViewById(R.id.report_info_complete_info_lv);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
