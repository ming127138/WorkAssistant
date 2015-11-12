package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.PipeInspectionStandardAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.InspectionStandard;
import com.gzrijing.workassistant.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

public class PipeInspectionFormActivity extends BaseActivity implements View.OnClickListener{

    private String id;
    private String type;
    private ImageView iv_checkAll;
    private EditText et_problem;
    private Button btn_ok;
    private Button btn_problem;
    private MyListView lv_standard;
    private boolean isCheckAll;
    private List<InspectionStandard> standards = new ArrayList<InspectionStandard>();
    private PipeInspectionStandardAdapter standardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipe_inspection_form);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        type = intent.getStringExtra("type");

        if(type.equals("供水阀门井")){
            String[] data = {"井体","井内杂物","井盖","阀门性能","阀门型号","阀门井型号"};
            for (int i = 0; i < data.length; i++) {
                InspectionStandard standard = new InspectionStandard();
                standard.setIsCheck(false);
                standard.setStandard(data[i]);
                standards.add(standard);
            }
        }
        if(type.equals("供水消防栓")){
            String[] data = {"外观","开关","出水口","出水压力","消防栓型号","阀门型号","阀门性能","阀门井型号"};
            for (int i = 0; i < data.length; i++) {
                InspectionStandard standard = new InspectionStandard();
                standard.setIsCheck(false);
                standard.setStandard(data[i]);
                standards.add(standard);
            }
        }
        if(type.equals("污水井")){
            String[] data = {"井体尺寸","井内批灰情况","井内杂物","井盖","安全网"};
            for (int i = 0; i < data.length; i++) {
                InspectionStandard standard = new InspectionStandard();
                standard.setIsCheck(false);
                standard.setStandard(data[i]);
                standards.add(standard);
            }
        }

    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(id);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iv_checkAll = (ImageView) findViewById(R.id.pipe_inspection_form_check_all_iv);
        et_problem = (EditText) findViewById(R.id.pipe_inspection_form_problem_et);
        btn_ok = (Button) findViewById(R.id.pipe_inspection_form_ok_btn);
        btn_problem = (Button) findViewById(R.id.pipe_inspection_form_problem_btn);
        lv_standard = (MyListView) findViewById(R.id.pipe_inspection_form_standard_lv);
        standardAdapter = new PipeInspectionStandardAdapter(this, standards, iv_checkAll, isCheckAll);
        lv_standard.setAdapter(standardAdapter);
    }

    private void setListeners() {
        iv_checkAll.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        btn_problem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.pipe_inspection_form_check_all_iv:
            if (isCheckAll) {
                for (InspectionStandard standard : standards) {
                    standard.setIsCheck(false);
                }
                iv_checkAll.setImageResource(R.drawable.login_checkbox_off);
            } else {
                for (InspectionStandard standard : standards) {
                    standard.setIsCheck(true);
                }
                iv_checkAll.setImageResource(R.drawable.login_checkbox_on);
            }
            standardAdapter.notifyDataSetChanged();
            isCheckAll = !isCheckAll;
            break;

            case R.id.pipe_inspection_form_ok_btn:
                submitOk();
                break;

            case R.id.pipe_inspection_form_problem_btn:
                submitProblem();
                break;
        }
    }

    private void submitOk() {

    }

    private void submitProblem() {

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
