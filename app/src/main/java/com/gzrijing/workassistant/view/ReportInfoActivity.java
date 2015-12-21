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
import com.gzrijing.workassistant.adapter.ReportInfoProjectAmountAdapter;
import com.gzrijing.workassistant.adapter.ReportInfoProblemAdapter;
import com.gzrijing.workassistant.adapter.ReportInfoProgressAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.ReportInfo;
import com.gzrijing.workassistant.entity.ReportInfoProjectAmount;
import com.gzrijing.workassistant.service.GetReportInfoProjectAmountService;
import com.gzrijing.workassistant.service.GetReportInfoProblemService;
import com.gzrijing.workassistant.service.GetReportInfoProgressService;
import com.gzrijing.workassistant.util.JsonParseUtils;

import java.util.ArrayList;
import java.util.List;

public class ReportInfoActivity extends BaseActivity {

    private String togetherid;
    private ListView lv_problem;
    private ListView lv_progress;
    private ListView lv_projectAmount;
    private List<ReportInfo> problemList = new ArrayList<ReportInfo>();
    private List<ReportInfo> progressList = new ArrayList<ReportInfo>();
    private List<ReportInfoProjectAmount> projectAmountList = new ArrayList<ReportInfoProjectAmount>();
    private ReportInfoProblemAdapter problemAdapter;
    private ReportInfoProgressAdapter progressAdapter;
    private ReportInfoProjectAmountAdapter projectAmountAdapter;
    private Intent problemIntent;
    private Intent progressIntent;
    private Intent projectAmountIntent;

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
        togetherid = intent.getStringExtra("id");

        initProblemReportInfo();
        initProgressReportInfo();
        initCompleteReportInfo();

    }

    private void initProblemReportInfo() {
        IntentFilter intentFilter = new IntentFilter("action.com.gzrijing.workassistant.ReportInfo.problem");
        registerReceiver(mBroadcastReceiver, intentFilter);
        problemIntent = new Intent(this, GetReportInfoProblemService.class);
        problemIntent.putExtra("id", togetherid);
        startService(problemIntent);

    }

    private void initProgressReportInfo() {
        IntentFilter intentFilter = new IntentFilter("action.com.gzrijing.workassistant.ReportInfo.progress");
        registerReceiver(mBroadcastReceiver, intentFilter);
        progressIntent = new Intent(this, GetReportInfoProgressService.class);
        progressIntent.putExtra("id", togetherid);
        startService(progressIntent);
    }

    private void initCompleteReportInfo() {
        IntentFilter intentFilter = new IntentFilter("action.com.gzrijing.workassistant.ReportInfo.projectAmount");
        registerReceiver(mBroadcastReceiver, intentFilter);
        projectAmountIntent = new Intent(this, GetReportInfoProjectAmountService.class);
        projectAmountIntent.putExtra("id", togetherid);
        startService(projectAmountIntent);
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_problem = (ListView) findViewById(R.id.report_info_problem_lv);

        lv_progress = (ListView) findViewById(R.id.report_info_progress_lv);

        lv_projectAmount = (ListView) findViewById(R.id.report_info_complete_lv);

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

        lv_projectAmount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ReportInfoActivity.this, ReportInfoProjectAmountActivity.class);
                intent.putExtra("id", togetherid);
                intent.putExtra("projectAmount", projectAmountList.get(position));
                intent.putExtra("position", position);
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
                List<ReportInfo> infos = JsonParseUtils.getProgressReportInfo(jsonData);
                progressList.addAll(infos);
                progressAdapter = new ReportInfoProgressAdapter(ReportInfoActivity.this, progressList);
                lv_progress.setAdapter(progressAdapter);
            }
            if(action.equals("action.com.gzrijing.workassistant.ReportInfo.projectAmount")){
                String jsonData = intent.getStringExtra("jsonData");
                List<ReportInfoProjectAmount> infos = JsonParseUtils.getProjectAmountReportInfo(jsonData);
                projectAmountList.addAll(infos);
                projectAmountAdapter = new ReportInfoProjectAmountAdapter(ReportInfoActivity.this, projectAmountList);
                lv_projectAmount.setAdapter(projectAmountAdapter);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == 10) {
                projectAmountList.clear();
                initCompleteReportInfo();
                projectAmountAdapter.notifyDataSetChanged();
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
        stopService(projectAmountIntent);
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}
