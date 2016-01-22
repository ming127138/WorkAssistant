package com.gzrijing.workassistant.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.ProblemType;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.JsonParseUtils;
import com.gzrijing.workassistant.util.ToastUtil;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ReportInfoProblemByProcessActivity extends BaseActivity implements View.OnClickListener {

    private String orderId;
    private String userNo;
    private TextView tv_type;
    private TextView tv_state;
    private EditText et_reason;
    private Button btn_submit;
    private ArrayList<ProblemType> problemTypeList = new ArrayList<ProblemType>();
    private Handler handler = new Handler();
    private int typeIndex = 0;
    private int stateIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_info_problem_by_process);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        SharedPreferences app = getSharedPreferences(
                "saveUser", MODE_PRIVATE);
        userNo = app.getString("userNo", "");

        Intent intent = getIntent();
        orderId = intent.getStringExtra("fileNo");

        getProblemType();
    }

    private void getProblemType() {
        String url = null;
        try {
            url = "?cmd=getaccidentreason&userno=" + URLEncoder.encode(userNo, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpUtils.sendHttpGetRequest(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                ArrayList<ProblemType> list = JsonParseUtils.getProblemType(response);
                problemTypeList.addAll(list);
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(ReportInfoProblemByProcessActivity.this,
                                "与服务器断开连接", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_type = (TextView) findViewById(R.id.report_info_problem_by_process_type_tv);
        tv_state = (TextView) findViewById(R.id.report_info_problem_by_process_state_tv);
        et_reason = (EditText) findViewById(R.id.report_info_problem_by_process_reason_et);
        btn_submit = (Button) findViewById(R.id.report_info_problem_by_process_submit_btn);

    }

    private void setListeners() {
        tv_type.setOnClickListener(this);
        tv_state.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.report_info_problem_by_process_type_tv:
                selectType();
                break;

            case R.id.report_info_problem_by_process_state_tv:
                selectState();
                break;

            case R.id.report_info_problem_by_process_reason_et:
                submit();
                break;
        }
    }

    private void selectType() {
        if(problemTypeList.size() != 0){
            String[] type = new String[problemTypeList.size()];
            for (int i = 0; i < problemTypeList.size(); i++) {
                type[i] = problemTypeList.get(i).getType();
            }
            new AlertDialog.Builder(this).setTitle("选择归属类型：").setSingleChoiceItems(
                    type, typeIndex, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            typeIndex = which;
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tv_type.setText(problemTypeList.get(typeIndex).getType());
                        }
                    })
                    .setNegativeButton("取消", null).show();
        }
    }

    private void selectState() {
        new AlertDialog.Builder(this).setTitle("选择归属类型：").setSingleChoiceItems(
                new String[]{"暂缓执行", "停止", "取消"}, stateIndex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stateIndex = which;
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(stateIndex == 0) {
                            tv_state.setText("暂缓执行");
                        }
                        if(stateIndex == 1) {
                            tv_state.setText("停止");
                        }
                        if(stateIndex == 2) {
                            tv_state.setText("取消");
                        }
                    }
                })
                .setNegativeButton("取消", null).show();
    }

    private void submit() {
        RequestBody requestBody = new FormEncodingBuilder()
                .add("cmd", "dosaveconsaccidentfreedom")
                .add("userno", userNo)
                .add("fileno", orderId)
                .add("filestate", tv_state.getText().toString())
                .add("accidentreason", tv_type.getText().toString())
                .add("handleuno", problemTypeList.get(typeIndex).getUserNo())
                .add("handlereason", et_reason.getText().toString().trim())
                .add("relationfileno", et_reason.getText().toString().trim())
                .build();
        HttpUtils.sendHttpPostRequest(requestBody, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if(response.equals("ok")){

                }
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(ReportInfoProblemByProcessActivity.this,
                                "与服务器断开连接", Toast.LENGTH_SHORT);
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
