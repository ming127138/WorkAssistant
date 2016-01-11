package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.LeaderMachineApplyBillByPlanAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.LeaderMachineApplyBill;


public class LeaderMachineApplyBillByPlanActivity extends BaseActivity {

    private LeaderMachineApplyBill bill;
    private ListView lv_machineList;
    private LeaderMachineApplyBillByPlanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_machine_apply_bill_by_plan);

        initData();
        initViews();
    }

    private void initData() {
        Intent intent = getIntent();
        bill = intent.getParcelableExtra("bill");
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_machineList = (ListView) findViewById(R.id.leader_machine_apply_bill_by_plan_machine_lv);
        adapter = new LeaderMachineApplyBillByPlanAdapter(this, bill);
        lv_machineList.setAdapter(adapter);
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
