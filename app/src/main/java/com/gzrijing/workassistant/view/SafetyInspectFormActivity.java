package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.SafetyInspectFormExpandableAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.SafetyInspectFirstItem;
import com.gzrijing.workassistant.entity.SafetyInspectSecondItem;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.JsonParseUtils;
import com.gzrijing.workassistant.util.ToastUtil;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SafetyInspectFormActivity extends BaseActivity {

    private String userNo;
    private String orderId;
    private ExpandableListView elv_item;
    private ArrayList<SafetyInspectFirstItem> groupList = new ArrayList<SafetyInspectFirstItem>();
    private SafetyInspectFormExpandableAdapter expandableAdapter;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_inspect_form);

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
        getItems();
    }

    private void getItems() {
        String url = "?cmd=LoadSafeItem";

        HttpUtils.sendHttpGetRequest(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                ArrayList<SafetyInspectFirstItem> list = JsonParseUtils.getSafetyInspectFormItem(response);
                groupList.addAll(list);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        expandableAdapter = new SafetyInspectFormExpandableAdapter(SafetyInspectFormActivity.this, groupList);
                        elv_item.setAdapter(expandableAdapter);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(SafetyInspectFormActivity.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        elv_item = (ExpandableListView) findViewById(R.id.safety_inspect_form_elv);
    }

    private void setListeners() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_safety_inspect_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.action_submit) {
            submit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void submit() {
        JSONArray jsonArray = new JSONArray();
        try {
            for (SafetyInspectFirstItem group : groupList) {
                for (SafetyInspectSecondItem child : group.getChildList()) {
                    if(child.isCheck()){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("TaskNo", orderId);
                        jsonObject.put("FileNO", child.getId());
                        jsonObject.put("UpUserno", userNo);
                        jsonArray.put(jsonObject);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("jsonArray", jsonArray.toString());
        RequestBody requestBody = new FormEncodingBuilder()
                .add("cmd", "UpSafeInf")
                .add("fileid", orderId)
                .add("fileJson", jsonArray.toString())
                .build();
        HttpUtils.sendHttpPostRequest(requestBody, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                Log.e("response", response);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(response.equals("ok")){
                            ToastUtil.showToast(SafetyInspectFormActivity.this, "提交成功", Toast.LENGTH_SHORT);
                        }else{
                            ToastUtil.showToast(SafetyInspectFormActivity.this, "提交失败", Toast.LENGTH_SHORT);
                        }
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(SafetyInspectFormActivity.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }
}
