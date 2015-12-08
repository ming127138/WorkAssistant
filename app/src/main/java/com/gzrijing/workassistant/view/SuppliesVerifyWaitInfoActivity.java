package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.SuppliesVerifyWaitAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.SuppliesVerifyInfo;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.ToastUtil;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SuppliesVerifyWaitInfoActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_useTime;
    private TextView tv_remarks;
    private LinearLayout ll_reason;
    private EditText et_reason;
    private ListView lv_info;
    private ArrayList<SuppliesVerifyInfo> svInfoList;
    private SuppliesVerifyWaitAdapter adapter;
    private String userTime;
    private String remarks;
    private LinearLayout ll_yes;
    private ImageView iv_yes;
    private LinearLayout ll_no;
    private ImageView iv_no;
    private Button btn_submit;
    private boolean isCheck = true;
    private String userNo;
    private String id;
    private int position;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplies_verify_wait_info);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        SharedPreferences app = getSharedPreferences(
                "saveUser", MODE_PRIVATE);
        userNo = app.getString("userNo", "");

        Intent intent = getIntent();
        svInfoList = intent.getParcelableArrayListExtra("suppliesVerifyInfoList");
        id = intent.getStringExtra("id");
        userTime = intent.getStringExtra("userTime");
        remarks = intent.getStringExtra("remarks");
        position = intent.getIntExtra("position", -1);

    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_useTime = (TextView) findViewById(R.id.supplies_verify_wait_info_use_time_tv);
        tv_useTime.setText(userTime);
        tv_remarks = (TextView) findViewById(R.id.supplies_verify_wait_info_remarks_tv);
        tv_remarks.setText(remarks);

        ll_yes = (LinearLayout) findViewById(R.id.supplies_verify_wait_info_yes_ll);
        iv_yes = (ImageView) findViewById(R.id.supplies_verify_wait_info_yes_iv);
        ll_no = (LinearLayout) findViewById(R.id.supplies_verify_wait_info_no_ll);
        iv_no = (ImageView) findViewById(R.id.supplies_verify_wait_info_no_iv);

        ll_reason = (LinearLayout) findViewById(R.id.supplies_verify_wait_info_reason_ll);
        et_reason = (EditText) findViewById(R.id.supplies_verify_wait_info_reason_et);

        btn_submit = (Button) findViewById(R.id.supplies_verify_wait_info_submit_btn);

        lv_info = (ListView) findViewById(R.id.supplies_verify_wait_info_lv);
        adapter = new SuppliesVerifyWaitAdapter(this, svInfoList);
        lv_info.setAdapter(adapter);
    }

    private void setListeners() {
        ll_yes.setOnClickListener(this);
        ll_no.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.supplies_verify_wait_info_yes_ll:
                if (!isCheck) {
                    iv_yes.setImageResource(R.drawable.spinner_item_check_on);
                    iv_no.setImageResource(R.drawable.spinner_item_check_off);
                    ll_reason.setVisibility(View.GONE);
                    isCheck = !isCheck;
                }
                break;

            case R.id.supplies_verify_wait_info_no_ll:
                if (isCheck) {
                    iv_no.setImageResource(R.drawable.spinner_item_check_on);
                    iv_yes.setImageResource(R.drawable.spinner_item_check_off);
                    ll_reason.setVisibility(View.VISIBLE);
                    isCheck = !isCheck;
                }
                break;

            case R.id.supplies_verify_wait_info_submit_btn:
                submit();
                break;
        }
    }

    private void submit() {
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", id);
            if (isCheck) {
                jsonObject.put("IsPass", "1");
                jsonObject.put("UnPassReason", "");
            } else {
                jsonObject.put("IsPass", "0");
                if (et_reason.getText().toString().trim().equals("")) {
                    ToastUtil.showToast(this, "请填写不批原因", Toast.LENGTH_SHORT);
                    return;
                } else {
                    jsonObject.put("UnPassReason", et_reason.getText().toString().trim());
                }
            }
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("jsonData", jsonArray.toString());
        String jsonData = jsonArray.toString();

        RequestBody requestBody = new FormEncodingBuilder()
                .add("cmd", "docheckmaterialneed")
                .add("userno", userNo)
                .add("materialneedjson", jsonData)
                .build();
        HttpUtils.sendHttpPostRequest(requestBody, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.e("response", response);
                if (response.equals("ok")) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(SuppliesVerifyWaitInfoActivity.this, "提交成功", Toast.LENGTH_SHORT);
                            Intent intent = getIntent();
                            intent.putExtra("position", position);
                            if(isCheck){
                                intent.putExtra("isPass", "1");
                            }else {
                                intent.putExtra("isPass", "0");
                            }
                            setResult(10, intent);
                            finish();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(SuppliesVerifyWaitInfoActivity.this, "提交失败", Toast.LENGTH_SHORT);
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(SuppliesVerifyWaitInfoActivity.this, "与服务器断开连接", Toast.LENGTH_SHORT);
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
