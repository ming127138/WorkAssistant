package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.BusinessHaveSendAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.db.BusinessData;
import com.gzrijing.workassistant.db.BusinessHaveSendData;
import com.gzrijing.workassistant.entity.BusinessByWorker;
import com.gzrijing.workassistant.entity.BusinessHaveSend;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.JsonParseUtils;
import com.gzrijing.workassistant.util.ToastUtil;

import org.litepal.crud.DataSupport;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class BusinessHaveSendActivity extends BaseActivity {

    private String userNo;
    private String orderId;
    private BusinessData businessData;
    private ListView lv_business;
    private List<BusinessHaveSend> BHSList = new ArrayList<BusinessHaveSend>();
    private BusinessHaveSendAdapter adapter;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_have_send);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        SharedPreferences app = getSharedPreferences(
                "saveUser", MODE_PRIVATE);
        userNo = app.getString("userNo", "");
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");

        getBusinessHaveSend();

//        getDBData();

    }

    private void getBusinessHaveSend() {
        String url = null;
        try {
            url = "?cmd=getinstalllocatetask&fileno=" + URLEncoder.encode(orderId, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpUtils.sendHttpGetRequest(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                List<BusinessHaveSend> list = JsonParseUtils.getBusinessHaveSend(response);
                BHSList.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(BusinessHaveSendActivity.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }

    private void getDBData() {
        businessData = DataSupport.where("user = ? and orderId = ?", userNo, orderId)
                .find(BusinessData.class, true).get(0);
        List<BusinessHaveSendData> dataList = businessData.getBusinessHaveSendDataList();
        for(BusinessHaveSendData data : dataList){
            BusinessHaveSend bhs = new BusinessHaveSend();
            bhs.setId(data.getOrderId());
            bhs.setContent(data.getContent());
            bhs.setState(data.getState());
            bhs.setExecutors(data.getExecutors());
            bhs.setDeadline(data.getDeadline());
            BHSList.add(bhs);
        }
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_business = (ListView) findViewById(R.id.business_have_send_lv);
        adapter = new BusinessHaveSendAdapter(this, BHSList);
        lv_business.setAdapter(adapter);
    }

    private void setListeners() {

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
