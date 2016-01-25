package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.QueryProjectAmountAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.QueryProjectAmount;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.JsonParseUtils;
import com.gzrijing.workassistant.util.ToastUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class QueryProjectAmountActivity extends BaseActivity {

    private ListView lv_ProjectAmount;
    private String userNo;
    private String orderId;
    private ArrayList<QueryProjectAmount> projectAmountList = new ArrayList<QueryProjectAmount>();
    private Handler handler = new Handler();
    private QueryProjectAmountAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_project_amount);

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

        getProjectAmount();
    }

    private void getProjectAmount() {
        String url = null;
        try {
            url = "?cmd=getmyconsconfirm&userno=" + URLEncoder.encode(userNo, "UTF-8")
                    + "&ispass=&fileno=" + URLEncoder.encode(orderId, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpUtils.sendHttpGetRequest(url, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<QueryProjectAmount> list = JsonParseUtils.getQueryProjectAmount(response);
                        projectAmountList.addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(QueryProjectAmountActivity.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_ProjectAmount = (ListView) findViewById(R.id.query_project_amount_lv);
        adapter = new QueryProjectAmountAdapter(this, projectAmountList, orderId);
        lv_ProjectAmount.setAdapter(adapter);

    }

    private void setListeners() {

    }
}
