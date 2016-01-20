package com.gzrijing.workassistant.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.ReportInfoProblemAdapter;
import com.gzrijing.workassistant.adapter.ReportInfoProgressAdapter;
import com.gzrijing.workassistant.adapter.ReportInfoProjectAmountAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.ReportInfo;
import com.gzrijing.workassistant.entity.ReportInfoProjectAmount;
import com.gzrijing.workassistant.service.GetReportInfoProjectAmountService;
import com.gzrijing.workassistant.util.JsonParseUtils;

import java.util.ArrayList;
import java.util.List;

public class ReportInfoByProjectAmountActivity extends BaseActivity {

    private String togetherid;
    private Intent projectAmountIntent;
    private ListView lv_projectAmount;
    private List<ReportInfoProjectAmount> projectAmountList = new ArrayList<ReportInfoProjectAmount>();
    private ReportInfoProjectAmountAdapter projectAmountAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_info_by_project_amount);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        togetherid = intent.getStringExtra("id");

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.com.gzrijing.workassistant.ReportInfo.projectAmount");
        intentFilter.addAction("action.com.gzrijing.workassistant.ReportInfo.projectAmount.refresh");
        registerReceiver(mBroadcastReceiver, intentFilter);

        initProjectAmountReportInfo();
    }

    private void initProjectAmountReportInfo() {
        projectAmountIntent = new Intent(this, GetReportInfoProjectAmountService.class);
        projectAmountIntent.putExtra("id", togetherid);
        startService(projectAmountIntent);
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_projectAmount = (ListView) findViewById(R.id.report_info_by_project_amount_lv);

    }

    private void setListeners() {
        lv_projectAmount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ReportInfoByProjectAmountActivity.this, ReportInfoProjectAmountActivity.class);
                intent.putExtra("id", togetherid);
                intent.putExtra("projectAmount", projectAmountList.get(position));
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("action.com.gzrijing.workassistant.ReportInfo.projectAmount")){
                String jsonData = intent.getStringExtra("jsonData");
                List<ReportInfoProjectAmount> infos = JsonParseUtils.getProjectAmountReportInfo(jsonData);
                projectAmountList.addAll(infos);
                projectAmountAdapter = new ReportInfoProjectAmountAdapter(ReportInfoByProjectAmountActivity.this, projectAmountList);
                lv_projectAmount.setAdapter(projectAmountAdapter);
            }

            if(action.equals("action.com.gzrijing.workassistant.ReportInfo.projectAmount.refresh")){
                projectAmountList.clear();
                initProjectAmountReportInfo();
                projectAmountAdapter.notifyDataSetChanged();
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
        stopService(projectAmountIntent);
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}
