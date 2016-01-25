package com.gzrijing.workassistant.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.base.MyApplication;
import com.gzrijing.workassistant.entity.Accident;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.ToastUtil;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

public class AccidentsProcessActivity extends BaseActivity {

    private String userNo;
    private Accident accident;
    private TextView tv_orderId;
    private TextView tv_type;
    private TextView tv_state;
    private TextView tv_reason;
    private TextView tv_handleName;
    private TextView tv_problemName;
    private TextView tv_problemTIme;
    private EditText et_handleResult;
    private Handler handler = new Handler();
    private ProgressDialog pDialog;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accidents_process);

        initData();
        initViews();
    }

    private void initData() {
        SharedPreferences app = getSharedPreferences(
                "saveUser", MODE_PRIVATE);
        userNo = app.getString("userNo", "");

        Intent intent = getIntent();
        accident = intent.getParcelableExtra("accident");
        position = intent.getIntExtra("position", -1);
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_orderId = (TextView) findViewById(R.id.accidents_process_order_id_tv);
        tv_orderId.setText(accident.getOrderId());
        tv_type = (TextView) findViewById(R.id.accidents_process_problem_type_tv);
        tv_type.setText(accident.getType());
        tv_state = (TextView) findViewById(R.id.accidents_process_state_tv);
        tv_state.setText(accident.getState());
        tv_reason = (TextView) findViewById(R.id.accidents_process_reason_tv);
        tv_reason.setText(accident.getReason());
        tv_handleName = (TextView) findViewById(R.id.accidents_process_handle_name_tv);
        tv_handleName.setText(accident.getHandleName());
        tv_problemName = (TextView) findViewById(R.id.accidents_process_problem_name_tv);
        tv_problemName.setText(accident.getProblemName());
        tv_problemTIme = (TextView) findViewById(R.id.accidents_process_problem_time_tv);
        tv_problemTIme.setText(accident.getProblemtime());
        et_handleResult = (EditText) findViewById(R.id.accidents_process_handle_result_et);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_accidents_process, menu);
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
        if(et_handleResult.getText().toString().trim().equals("")){
            ToastUtil.showToast(this, "请填写处理结果再提交", Toast.LENGTH_SHORT);
            return;
        }
        pDialog = new ProgressDialog(this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("正在提交...");
        pDialog.show();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("cmd", "dosaveaccidentresult")
                .add("userno", userNo)
                .add("id", accident.getId())
                .add("handleresult", et_handleResult.getText().toString().trim())
                .build();

        HttpUtils.sendHttpPostRequest(requestBody, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                pDialog.cancel();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(response.equals("ok")){
                            ToastUtil.showToast(AccidentsProcessActivity.this, "提交成功", Toast.LENGTH_SHORT);
                            Intent intent = new Intent("action.com.gzrijing.workassistant.Accidents");
                            intent.putExtra("position", position);
                            sendBroadcast(intent);
                            finish();
                        }else{
                            ToastUtil.showToast(AccidentsProcessActivity.this, "提交失败", Toast.LENGTH_SHORT);
                        }
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(AccidentsProcessActivity.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                    }
                });
                pDialog.cancel();
            }
        });
    }
}
