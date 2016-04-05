package com.gzrijing.workassistant.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.SafetyInspectHistoryRecordItemAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.SafetyInspectHistoryRecord;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.JsonParseUtils;
import com.gzrijing.workassistant.util.ToastUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SafetyInspectHistoryRecordQueryActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_id;
    private Button btn_query;
    private ListView lv_item;
    private ProgressDialog pDialog;
    private ArrayList<SafetyInspectHistoryRecord> failureList = new ArrayList<SafetyInspectHistoryRecord>();
    private SafetyInspectHistoryRecordItemAdapter adapter;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_inspect_history_record_query);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {

    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_id = (EditText) findViewById(R.id.safety_inspect_history_record_query_order_id_et);
        btn_query = (Button) findViewById(R.id.safety_inspect_history_record_query_query_btn);

        lv_item = (ListView) findViewById(R.id.safety_inspect_history_record_query_lv);
        adapter = new SafetyInspectHistoryRecordItemAdapter(SafetyInspectHistoryRecordQueryActivity.this, failureList);
        lv_item.setAdapter(adapter);
    }

    private void setListeners() {
        btn_query.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.safety_inspect_history_record_query_query_btn:
                query();
                break;
        }
    }

    private void query() {
        pDialog = new ProgressDialog(this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("正在加载数据");
        pDialog.show();
        String orderId = et_id.getText().toString().trim();
        String url = null;
        try {
            url = "?cmd=GetSafeOldInf&fileid=" + URLEncoder.encode(orderId, "UTF-8");
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
                        ArrayList<SafetyInspectHistoryRecord> list = JsonParseUtils.getSafetyInspectHistoryRecord(response);
                        failureList.clear();
                        for (SafetyInspectHistoryRecord form : list) {
                            if (form.getFlag().equals("1")) {
                                failureList.add(form);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        pDialog.dismiss();
                    }
                });

            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(SafetyInspectHistoryRecordQueryActivity.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                        pDialog.dismiss();
                    }
                });
            }
        });
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
