package com.gzrijing.workassistant.view;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class ReportInfoActivity extends BaseActivity {

    private String orderId;
    private ListView lv_problem;
    private ListView lv_progress;
    private ListView lv_complete;
    private List<ReportInfo> problemList = new ArrayList<ReportInfo>();
    private List<ReportInfo> progressList = new ArrayList<ReportInfo>();
    private List<ReportInfo> completeList = new ArrayList<ReportInfo>();
    private ReportInfoProblemAdapter problemAdapter;
    private ReportInfoProgressAdapter progressAdapter;
    private ReportInfoCompleteAdapter completeAdapter;

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
        orderId = intent.getStringExtra("orderId");

        for (int i = 0; i < 3; i++) {
            if (i % 2 == 0) {
                ReportInfo problemInfo = new ReportInfo("XXXXXXXXXXX问题" + i, false);
                problemList.add(problemInfo);
            } else {
                ReportInfo problemInfo = new ReportInfo("XXXXXXXXXXX问题" + i, true);
                problemList.add(problemInfo);
            }
        }

        for (int i = 0; i < 2; i++) {
            ReportInfo progressInfo = new ReportInfo("XXXXXXX进度汇报" + i, false);
            progressList.add(progressInfo);
        }


        ReportInfo completeInfo = new ReportInfo("XXXXXXXX施工内容", false);
        completeList.add(completeInfo);
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_problem = (ListView) findViewById(R.id.report_info_problem_lv);
        problemAdapter = new ReportInfoProblemAdapter(this, problemList);
        lv_problem.setAdapter(problemAdapter);

        lv_progress = (ListView) findViewById(R.id.report_info_progress_lv);
        progressAdapter = new ReportInfoProgressAdapter(this, progressList);
        lv_progress.setAdapter(progressAdapter);

        lv_complete = (ListView) findViewById(R.id.report_info_complete_lv);
        completeAdapter = new ReportInfoCompleteAdapter(this, completeList);
        lv_complete.setAdapter(completeAdapter);

    }

    private void setListeners() {
        lv_problem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ReportInfoActivity.this, ReportInfoProblemActivity.class);
                intent.putExtra("content", problemList.get(position).getContent());
                startActivity(intent);
            }
        });

        lv_progress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ReportInfoActivity.this, ReportInfoProgressActivity.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == 10) {
                completeList.get(0).setFlag(true);
                completeAdapter.notifyDataSetChanged();
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
}
