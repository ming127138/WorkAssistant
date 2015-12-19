package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.PrintInfoAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.DetailedInfo;
import com.gzrijing.workassistant.entity.ReturnMachine;
import com.gzrijing.workassistant.entity.SendMachine;

import java.util.ArrayList;
import java.util.List;

public class SendMachineInfoActivity extends BaseActivity {

    private SendMachine sendMachineInfo;
    private ReturnMachine returnMachineInfo;
    private List<DetailedInfo> infos = new ArrayList<DetailedInfo>();
    private ListView lv_info;
    private PrintInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_machine_info);

        initData();
        initViews();
    }

    private void initData() {
        Intent intent = getIntent();
        String flag = intent.getStringExtra("flag");
        if (flag.equals("ReturnMachine")) {
            returnMachineInfo = intent.getParcelableExtra("ReturnMachine");
            String[] key = {"工程单号", "申请单号", "退机地址", "机械名称", "退机人", "退机时间", "退机类型"};
            String[] value = {returnMachineInfo.getOrderId(), returnMachineInfo.getBillNo(),
                    returnMachineInfo.getAddress(), returnMachineInfo.getMachineName(),
                    returnMachineInfo.getReturnName(), returnMachineInfo.getReturnTiem(), returnMachineInfo.getType()};

            for (int i = 0; i < key.length; i++) {
                DetailedInfo info = new DetailedInfo();
                info.setKey(key[i]);
                info.setValue(value[i]);
                infos.add(info);
            }
        }
        if (flag.equals("SendMachine")) {
            sendMachineInfo = intent.getParcelableExtra("SendMachine");
            String[] key1 = {"工程单号", "申请单号", "用机地址", "机械编号", "机械名称", "领机人", "发放时间"};
            String[] value1 = {sendMachineInfo.getOrderId(), sendMachineInfo.getBillNo(),
                    sendMachineInfo.getAddress(), sendMachineInfo.getMachineNo(), sendMachineInfo.getMachineName(),
                    sendMachineInfo.getApplyName(), sendMachineInfo.getSendData()};

            for (int i = 0; i < key1.length; i++) {
                DetailedInfo info = new DetailedInfo();
                info.setKey(key1[i]);
                info.setValue(value1[i]);
                infos.add(info);
            }
        }
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_info = (ListView) findViewById(R.id.send_machine_info_lv);
        adapter = new PrintInfoAdapter(this, infos);
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
