package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.MachineVerifyAdapter;
import com.gzrijing.workassistant.entity.MachineVerify;

import java.util.ArrayList;
import java.util.List;

public class MachineVerifyActivity extends AppCompatActivity {

    private ListView lv_wait;
    private ListView lv_ok;
    private String orderId;
    private List<MachineVerify> waitList = new ArrayList<MachineVerify>();
    private List<MachineVerify> okList = new ArrayList<MachineVerify>();
    private MachineVerifyAdapter waitAdapter;
    private MachineVerifyAdapter okAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_verify);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");

        for (int i = 1; i < 5; i++) {
            MachineVerify machineVerify = new MachineVerify("工单"+i, "申请人"+i, "2015-10-8 10:00",
                    "2015-10-9 10:00", "地点"+i, "备注"+i);
            if (i % 2 == 0) {
                waitList.add(machineVerify);
            }else {
                okList.add(machineVerify);
            }
        }
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_wait = (ListView) findViewById(R.id.machine_verify_wait);
        waitAdapter = new MachineVerifyAdapter(this, waitList);
        lv_wait.setAdapter(waitAdapter);
        lv_ok = (ListView) findViewById(R.id.machine_verify_ok);
        okAdapter = new MachineVerifyAdapter(this, okList);
        lv_ok.setAdapter(okAdapter);
    }

    private void setListeners() {
        lv_wait.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MachineVerifyActivity.this, MachineVerifyWaitInfoActivity.class);
                intent.putExtra("machineVerify", waitList.get(position));
                startActivityForResult(intent, 10);
            }
        });

        lv_ok.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MachineVerifyActivity.this, MachineVerifyOkInfoActivity.class);
                intent.putExtra("machineVerify", waitList.get(position));
                startActivityForResult(intent, 20);
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
