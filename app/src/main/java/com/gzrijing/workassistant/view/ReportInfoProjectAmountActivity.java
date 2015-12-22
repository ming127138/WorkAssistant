package com.gzrijing.workassistant.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.PrintInfoAdapter;
import com.gzrijing.workassistant.adapter.SuppliesAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.DetailedInfo;
import com.gzrijing.workassistant.entity.ReportInfoProjectAmount;
import com.gzrijing.workassistant.entity.Supplies;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.service.GetReportInfoProjectAmountSuppliesService;
import com.gzrijing.workassistant.service.ReportProgressService;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.JsonParseUtils;
import com.gzrijing.workassistant.util.ToastUtil;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReportInfoProjectAmountActivity extends BaseActivity implements View.OnClickListener {

    private String userNo;
    private Intent serviceIntent;
    private ListView lv_supplies;
    private SuppliesAdapter suppliesAdapter;
    private List<Supplies> suppliesList = new ArrayList<Supplies>();
    private List<DetailedInfo> infos = new ArrayList<DetailedInfo>();
    private ListView lv_infos;
    private PrintInfoAdapter infoAdapter;
    private Button btn_approval;
    private ReportInfoProjectAmount info;
    private LinearLayout ll_yes;
    private ImageView iv_yes;
    private LinearLayout ll_no;
    private ImageView iv_no;
    private LinearLayout ll_isApproval;
    private boolean isCheck = true;
    private Handler handler = new Handler();
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_info_project_amount);

        initData();
        initView();
        setListeners();
    }

    private void initData() {
        SharedPreferences app = getSharedPreferences(
                "saveUser", MODE_PRIVATE);
        userNo = app.getString("userNo", "");

        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        String togetherid = intent.getStringExtra("id");
        info = intent.getParcelableExtra("projectAmount");
        if (info.getCheckData().equals("")) {
            String[] key = {"汇报人", "汇报时间", "施工内容", "土建项目"};
            String[] value = {info.getReportName(), info.getReportDate(), info.getContent(), info.getCivil()};
            for (int i = 0; i < key.length; i++) {
                DetailedInfo detailedInfo = new DetailedInfo();
                detailedInfo.setKey(key[i]);
                detailedInfo.setValue(value[i]);
                infos.add(detailedInfo);
            }
        } else {
            String[] key = {"汇报人", "汇报时间", "审核时间", "施工内容", "土建项目"};
            String[] value = {info.getReportName(), info.getReportDate(), info.getCheckData(), info.getContent(), info.getCivil()};
            for (int i = 0; i < key.length; i++) {
                DetailedInfo detailedInfo = new DetailedInfo();
                detailedInfo.setKey(key[i]);
                detailedInfo.setValue(value[i]);
                infos.add(detailedInfo);
            }
        }

        IntentFilter intentFilter = new IntentFilter("action.com.gzrijing.workassistant.ReportInfoProjectAmount");
        registerReceiver(mBroadcastReceiver, intentFilter);

        serviceIntent = new Intent(this, GetReportInfoProjectAmountSuppliesService.class);
        serviceIntent.putExtra("togetherid", togetherid);
        serviceIntent.putExtra("confirmid", info.getId());
        startService(serviceIntent);
    }

    private void initView() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_infos = (ListView) findViewById(R.id.report_info_project_amount_info_lv);
        infoAdapter = new PrintInfoAdapter(this, infos);
        lv_infos.setAdapter(infoAdapter);

        lv_supplies = (ListView) findViewById(R.id.report_info_project_amount_supplies_lv);
        suppliesAdapter = new SuppliesAdapter(this, suppliesList);
        lv_supplies.setAdapter(suppliesAdapter);

        ll_isApproval = (LinearLayout) findViewById(R.id.report_info_project_amount_is_approval_ll);
        ll_yes = (LinearLayout) findViewById(R.id.report_info_project_amount_yes_ll);
        iv_yes = (ImageView) findViewById(R.id.report_info_project_amount_yes_iv);
        ll_no = (LinearLayout) findViewById(R.id.report_info_project_amount_no_ll);
        iv_no = (ImageView) findViewById(R.id.report_info_project_amount_no_iv);


        btn_approval = (Button) findViewById(R.id.report_info_project_amount_approval_btn);
        if (info.getState().equals("已审核") || info.getState().equals("不通过")) {
            btn_approval.setVisibility(View.GONE);
            ll_isApproval.setVisibility(View.GONE);
        }

    }

    private void setListeners() {
        ll_yes.setOnClickListener(this);
        ll_no.setOnClickListener(this);
        btn_approval.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.report_info_project_amount_yes_ll:
                if (!isCheck) {
                    iv_yes.setImageResource(R.drawable.spinner_item_check_on);
                    iv_no.setImageResource(R.drawable.spinner_item_check_off);
                    isCheck = !isCheck;
                }
                break;

            case R.id.report_info_project_amount_no_ll:
                if (isCheck) {
                    iv_no.setImageResource(R.drawable.spinner_item_check_on);
                    iv_yes.setImageResource(R.drawable.spinner_item_check_off);
                    isCheck = !isCheck;
                }
                break;

            case R.id.report_info_project_amount_approval_btn:
                approval();
                break;
        }
    }

    private void approval() {
        btn_approval.setVisibility(View.GONE);
        String isPass;
        if (isCheck) {
            isPass = "1";
        } else {
            isPass = "0";
        }

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < suppliesList.size(); i++) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", info.getId());
                jsonObject.put("IsPass", isPass);
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        Log.e("jsonData",jsonArray.toString());
        RequestBody requestBody = new FormEncodingBuilder()
                .add("cmd", "docheckconsconfirm")
                .add("userno", userNo)
                .add("consconfirmjson", jsonArray.toString())
                .build();

        HttpUtils.sendHttpPostRequest(requestBody, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.e("response", response);
                if (response.equals("ok")) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(ReportInfoProjectAmountActivity.this, "提交成功", Toast.LENGTH_SHORT);
                            Intent intent = new Intent("action.com.gzrijing.workassistant.ReportInfo.projectAmount.refresh");
                            sendBroadcast(intent);
                            finish();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(ReportInfoProjectAmountActivity.this, "提交失败", Toast.LENGTH_SHORT);
                            btn_approval.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(ReportInfoProjectAmountActivity.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                        btn_approval.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.com.gzrijing.workassistant.ReportInfoProjectAmount")) {
                String jsonData = intent.getStringExtra("jsonData");
                List<Supplies> list = JsonParseUtils.getProjectAmountReportInfoSupplies(jsonData);
                suppliesList.addAll(list);
                suppliesAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        stopService(serviceIntent);
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}
