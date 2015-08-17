package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.PipeInspectionParameterAdapter;
import com.gzrijing.workassistant.adapter.PipeInspectionStandardAdapter;
import com.gzrijing.workassistant.entity.InspectionParameter;
import com.gzrijing.workassistant.entity.InspectionStandard;
import com.gzrijing.workassistant.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

public class PipeInspectionFormActivity extends AppCompatActivity {

    private MyListView lv_standard;
    private MyListView lv_parameter;
    private ImageView iv_checkAll;
    public static boolean isCheck;
    private List<InspectionStandard> standards;
    private List<InspectionParameter> parameters;
    private PipeInspectionStandardAdapter standardAdapter;
    private PipeInspectionParameterAdapter parameterAdapter;
    private String number;

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
        number = intent.getStringExtra("number");

        standards = new ArrayList<InspectionStandard>();
        for (int i = 1; i < 6; i++) {
            InspectionStandard standard = new InspectionStandard();
            standard.setIsCheck(false);
            standard.setStandard("标准" + i);
            standards.add(standard);
        }

        parameters = new ArrayList<InspectionParameter>();
        for (int i = 1; i < 3; i++) {
            InspectionParameter parameter = new InspectionParameter();
            parameter.setKey("参数" + i + "：");
            parameters.add(parameter);
        }

    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(number);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iv_checkAll = (ImageView) findViewById(R.id.pipe_inspection_form_check_all_iv);
        lv_standard = (MyListView) findViewById(R.id.pipe_inspection_form_standard_lv);
        standardAdapter = new PipeInspectionStandardAdapter(this, standards, iv_checkAll);
        lv_standard.setAdapter(standardAdapter);
        lv_parameter = (MyListView) findViewById(R.id.pipe_inspection_form_parameter_lv);
        parameterAdapter = new PipeInspectionParameterAdapter(this, parameters);
        lv_parameter.setAdapter(parameterAdapter);
    }

    private void setListeners() {
        iv_checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck) {
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
                isCheck = !isCheck;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pipe_inspection_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if(id == R.id.action_submit){
            for(InspectionParameter parameter : parameters){
                Toast.makeText(this, parameter.getKey()+parameter.getValue(), Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
