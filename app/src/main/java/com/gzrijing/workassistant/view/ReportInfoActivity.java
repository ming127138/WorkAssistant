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
import com.gzrijing.workassistant.adapter.ReportInfoCompleteAdapter;
import com.gzrijing.workassistant.adapter.ReportInfoProblemAdapter;
import com.gzrijing.workassistant.adapter.ReportInfoProgressAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.ReportInfo;
import com.gzrijing.workassistant.service.GetCompleteReportInfoService;
import com.gzrijing.workassistant.service.GetProblemReportInfoService;
import com.gzrijing.workassistant.service.GetProgressReportInfoService;
import com.gzrijing.workassistant.util.JsonParseUtils;

import java.util.ArrayList;
import java.util.List;

public class ReportInfoActivity extends BaseActivity {

    private String id;
    private ListView lv_problem;
    private ListView lv_progress;
    private ListView lv_complete;
    private List<ReportInfo> problemList = new ArrayList<ReportInfo>();
    private List<ReportInfo> progressList = new ArrayList<ReportInfo>();
    private List<ReportInfo> completeList = new ArrayList<ReportInfo>();
    private ReportInfoProblemAdapter problemAdapter;
    private ReportInfoProgressAdapter progressAdapter;
    private ReportInfoCompleteAdapter completeAdapter;
    private Intent problemIntent;
    private Intent progressIntent;
    private Intent completeIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_info);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        initProblemReportInfo();
        initProgressReportInfo();
        initCompleteReportInfo();

    }

    private void initProblemReportInfo() {
        IntentFilter intentFilter = new IntentFilter("action.com.gzrijing.workassistant.ReportInfo.problem");
        registerReceiver(mBroadcastReceiver, intentFilter);
        problemIntent = new Intent(this, GetProblemReportInfoService.class);
        problemIntent.putExtra("id", id);
        startService(problemIntent);

    }

    private void initProgressReportInfo() {
        IntentFilter intentFilter = new IntentFilter("action.com.gzrijing.workassistant.ReportInfo.progress");
        registerReceiver(mBroadcastReceiver, intentFilter);
        progressIntent = new Intent(this, GetProgressReportInfoService.class);
        progressIntent.putExtra("id", id);
        startService(progressIntent);
    }

    private void initCompleteReportInfo() {
        IntentFilter intentFilter = new IntentFilter("action.com.gzrijing.workassistant.ReportInfo.complete");
        registerReceiver(mBroadcastReceiver, intentFilter);
        completeIntent = new Intent(this, GetCompleteReportInfoService.class);
        completeIntent.putExtra("id", id);
        startService(completeIntent);
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_problem = (ListView) findViewById(R.id.report_info_problem_lv);

        lv_progress = (ListView) findViewById(R.id.report_info_progress_lv);

        lv_complete = (ListView) findViewById(R.id.report_info_complete_lv);
        completeAdapter = new ReportInfoCompleteAdapter(this, completeList);
        lv_complete.setAdapter(completeAdapter);

    }

    private void setListeners() {
        lv_problem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ReportInfoActivity.this, ReportInfoProblemActivity.class);
                intent.putExtra("id", problemList.get(position).getId());
                intent.putExtra("fileNo", problemList.get(position).getFileNo());
                intent.putExtra("reportor", problemList.get(position).getReportor());
                intent.putExtra("reportTime", problemList.get(position).getReportTime());
                intent.putExtra("content", problemList.get(position).getContent());
                startActivity(intent);
            }
        });

        lv_progress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ReportInfoActivity.this, ReportInfoProgressActivity.class);
                intent.putExtra("id", progressList.get(position).getId());
                intent.putExtra("fileNo", progressList.get(position).getFileNo());
                intent.putExtra("reportor", progressList.get(position).getReportor());
                intent.putExtra("reportTime", progressList.get(position).getReportTime());
                intent.putExtra("content", progressList.get(position).getContent());
                startActivity(intent);
            }
        });

        lv_complete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ReportInfoActivity.this, ReportInfoCompleteActivity.class);
                startActivityForResult(intent, 10);
            }
        });
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("action.com.gzrijing.workassistant.ReportInfo.problem")){
                String jsonData = intent.getStringExtra("jsonData");
                List<ReportInfo> infos = JsonParseUtils.getProblemReportInfo(jsonData);
                problemList.addAll(infos);
                problemAdapter = new ReportInfoProblemAdapter(ReportInfoActivity.this, problemList);
                lv_problem.setAdapter(problemAdapter);
            }
            if(action.equals("action.com.gzrijing.workassistant.ReportInfo.progress")){
                String jsonData = intent.getStringExtra("jsonData");
                List<ReportInfo> infos = JsonParseUtils.getProblemReportInfo(jsonData);
                progressList.addAll(infos);
                progressAdapter = new ReportInfoProgressAdapter(ReportInfoActivity.this, progressList);
                lv_progress.setAdapter(progressAdapter);
            }
            if(action.equals("action.com.gzrijing.workassistant.ReportInfo.complete")){

            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == 10) {
            }
        }

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

    @Override
    protected void onDestroy() {
        stopService(problemIntent);
        stopService(progressIntent);
        stopService(completeIntent);
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}
