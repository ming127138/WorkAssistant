package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.Inspection;

public class PipeInspectionUpdateByValveActivity extends BaseActivity {

    private EditText et_item1;
    private EditText et_item2;
    private EditText et_item3;
    private EditText et_item4;
    private EditText et_item5;
    private EditText et_item6;
    private TextView tv_item7;
    private Button btn_item7;
    private Inspection valve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipe_inspection_update_by_valve);

        initData();
        initViews();
    }

    private void initData() {
        Intent intent = getIntent();
        valve = intent.getParcelableExtra("valve");

    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_item1 = (EditText) findViewById(R.id.pipe_inspection_update_by_valve_item1_et);
        et_item2 = (EditText) findViewById(R.id.pipe_inspection_update_by_valve_item2_et);
        et_item3 = (EditText) findViewById(R.id.pipe_inspection_update_by_valve_item3_et);
        et_item4 = (EditText) findViewById(R.id.pipe_inspection_update_by_valve_item4_et);
        et_item5 = (EditText) findViewById(R.id.pipe_inspection_update_by_valve_item5_et);
        et_item6 = (EditText) findViewById(R.id.pipe_inspection_update_by_valve_item6_et);
        tv_item7 = (TextView) findViewById(R.id.pipe_inspection_update_by_valve_item7_tv);
        btn_item7 = (Button) findViewById(R.id.pipe_inspection_update_by_valve_item7_btn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_distribute, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if(id == R.id.action_update){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
