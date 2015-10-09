package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.SuppliesVerifyWaitAdapter;
import com.gzrijing.workassistant.entity.SuppliesVerify;
import com.gzrijing.workassistant.entity.SuppliesVerifyInfo;

import java.util.ArrayList;
import java.util.List;

public class SuppliesVerifyWaitInfoActivity extends AppCompatActivity implements View.OnClickListener{

    private SuppliesVerify suppliesVerify;
    private TextView tv_useTime;
    private TextView tv_remarks;
    private ImageView iv_checkAll;
    private ListView lv_info;
    private SuppliesVerifyWaitAdapter adapter;
    private List<SuppliesVerifyInfo> svInfoList = new ArrayList<SuppliesVerifyInfo>();
    private boolean isCheckAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplies_verify_wait_info);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        suppliesVerify = (SuppliesVerify) intent.getParcelableExtra("suppliesVerify");

        for (int i = 1; i < 5; i++) {
            SuppliesVerifyInfo svInfo = new SuppliesVerifyInfo("名称" + i, "规格" + i, "单位" + i, i, false);
            svInfoList.add(svInfo);
        }
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_useTime = (TextView) findViewById(R.id.supplies_verify_wait_info_use_time_tv);
        tv_useTime.setText(suppliesVerify.getUseTime());
        tv_remarks = (TextView) findViewById(R.id.supplies_verify_wait_info_remarks_tv);
        tv_remarks.setText(suppliesVerify.getRemarks());
        iv_checkAll = (ImageView) findViewById(R.id.supplies_verify_wait_info_check_all_iv);
        lv_info = (ListView) findViewById(R.id.supplies_verify_wait_info_lv);
        adapter = new SuppliesVerifyWaitAdapter(this, svInfoList, iv_checkAll, isCheckAll);
        lv_info.setAdapter(adapter);
    }

    private void setListeners() {
        iv_checkAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.supplies_verify_wait_info_check_all_iv:
                if (isCheckAll) {
                    for (SuppliesVerifyInfo svInfo : svInfoList) {
                        svInfo.setIsCheck(false);
                    }
                    iv_checkAll.setImageResource(R.drawable.spinner_item_check_off);
                } else {
                    for (SuppliesVerifyInfo svInfo : svInfoList) {
                        svInfo.setIsCheck(true);
                    }
                    iv_checkAll.setImageResource(R.drawable.spinner_item_check_on);
                }
                adapter.notifyDataSetChanged();
                isCheckAll = !isCheckAll;
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_supplies_verify_wait_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if(id == R.id.action_sure){

        }

        return super.onOptionsItemSelected(item);
    }
}
