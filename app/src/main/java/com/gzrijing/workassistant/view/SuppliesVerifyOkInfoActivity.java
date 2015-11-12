package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.SuppliesVerifyOkAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.SuppliesVerify;
import com.gzrijing.workassistant.entity.SuppliesVerifyInfo;

import java.util.ArrayList;
import java.util.List;

public class SuppliesVerifyOkInfoActivity extends BaseActivity {

    private SuppliesVerify suppliesVerify;
    private TextView tv_useTime;
    private TextView tv_remarks;
    private ListView lv_info;
    private List<SuppliesVerifyInfo> svInfoList = new ArrayList<SuppliesVerifyInfo>();
    private SuppliesVerifyOkAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplies_verify_ok_info);

        initData();
        initViews();
    }

    private void initData() {
        Intent intent = getIntent();
        suppliesVerify = (SuppliesVerify) intent.getParcelableExtra("suppliesVerify");

        for (int i = 1; i < 5; i++) {
            SuppliesVerifyInfo svInfo = new SuppliesVerifyInfo("名称" + i, "规格" + i, "单位" + i, i, true);
            svInfoList.add(svInfo);
        }
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_useTime = (TextView) findViewById(R.id.supplies_verify_ok_info_use_time_tv);
        tv_useTime.setText(suppliesVerify.getUseTime());
        tv_remarks = (TextView) findViewById(R.id.supplies_verify_ok_info_remarks_tv);
        tv_remarks.setText(suppliesVerify.getRemarks());
        lv_info = (ListView) findViewById(R.id.supplies_verify_ok_info_lv);
        adapter = new SuppliesVerifyOkAdapter(this, svInfoList);
        lv_info.setAdapter(adapter);
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
