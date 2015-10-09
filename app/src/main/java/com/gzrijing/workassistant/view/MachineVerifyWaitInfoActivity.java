package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.os.Parcelable;
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
import com.gzrijing.workassistant.adapter.MachineVerifyWaitAdapter;
import com.gzrijing.workassistant.entity.MachineVerify;
import com.gzrijing.workassistant.entity.MachineVerifyInfo;

import java.util.ArrayList;
import java.util.List;

public class MachineVerifyWaitInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private MachineVerify machineVerify;
    private TextView tv_useTime;
    private TextView tv_reTime;
    private TextView tv_useAddress;
    private TextView tv_remarks;
    private ImageView iv_checkAll;
    private boolean isCheckAll;
    private List<MachineVerifyInfo> mvInfoList = new ArrayList<MachineVerifyInfo>();
    private ListView lv_info;
    private MachineVerifyWaitAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_verify_wait_info);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        machineVerify = (MachineVerify) intent.getParcelableExtra("machineVerify");

        for (int i = 1; i < 5; i++) {
            MachineVerifyInfo mvInfo = new MachineVerifyInfo("名称" + i, "规格" + i, "单位" + i, i, false);
            mvInfoList.add(mvInfo);
        }
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_useTime = (TextView) findViewById(R.id.machine_verify_wait_info_use_time_tv);
        tv_useTime.setText(machineVerify.getUseTime());
        tv_reTime = (TextView) findViewById(R.id.machine_verify_wait_info_return_time_tv);
        tv_reTime.setText(machineVerify.getReturnTime());
        tv_useAddress = (TextView) findViewById(R.id.machine_verify_wait_info_use_address_tv);
        tv_useAddress.setText(machineVerify.getUseAdress());
        tv_remarks = (TextView) findViewById(R.id.machine_verify_wait_info_remarks_tv);
        tv_remarks.setText(machineVerify.getRemarks());
        iv_checkAll = (ImageView) findViewById(R.id.machine_verify_wait_info_check_all_iv);
        lv_info = (ListView) findViewById(R.id.machine_verify_wait_info_lv);
        adapter = new MachineVerifyWaitAdapter(this, mvInfoList, iv_checkAll, isCheckAll);
        lv_info.setAdapter(adapter);
    }

    private void setListeners() {
        iv_checkAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.machine_verify_wait_info_check_all_iv:
                if (isCheckAll) {
                    for (MachineVerifyInfo mvInfo : mvInfoList) {
                        mvInfo.setIsCheck(false);
                    }
                    iv_checkAll.setImageResource(R.drawable.spinner_item_check_off);
                } else {
                    for (MachineVerifyInfo mvInfo : mvInfoList) {
                        mvInfo.setIsCheck(true);
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
        getMenuInflater().inflate(R.menu.menu_machine_verify_wait_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.action_sure) {

        }

        return super.onOptionsItemSelected(item);
    }
}
