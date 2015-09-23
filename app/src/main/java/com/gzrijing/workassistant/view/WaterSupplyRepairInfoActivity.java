package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.data.BusinessData;
import com.gzrijing.workassistant.data.WaterSupplyRepairData;

import org.litepal.crud.DataSupport;

public class WaterSupplyRepairInfoActivity extends AppCompatActivity{

    private String userName;
    private String orderId;
    private BusinessData businessData;
    private WaterSupplyRepairData data;
    private TextView tv_orderId;
    private TextView tv_eventTime;
    private TextView tv_address;
    private TextView tv_reason;
    private TextView tv_repairType;
    private TextView tv_contacts;
    private TextView tv_tel;
    private TextView tv_remarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_supply_repair_info);

        initData();
        initViews();
    }

    private void initData() {
        SharedPreferences app = getSharedPreferences(
                "saveUserInfo", MODE_PRIVATE);
        userName = app.getString("userName", "");
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");

        businessData = DataSupport.where("user = ? and orderId = ?", userName, orderId).find(BusinessData.class, true).get(0);
        data = businessData.getWaterSupplyRepairData();
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_orderId = (TextView) findViewById(R.id.water_supply_repair_info_order_id_tv);
        tv_orderId.setText(orderId);
        tv_eventTime = (TextView) findViewById(R.id.water_supply_repair_info_event_time_tv);
        tv_eventTime.setText(data.getTime());
        tv_address = (TextView) findViewById(R.id.water_supply_repair_info_address_tv);
        tv_address.setText(data.getAddress());
        tv_reason = (TextView) findViewById(R.id.water_supply_repair_info_reason_tv);
        tv_reason.setText(data.getReason());
        tv_repairType = (TextView) findViewById(R.id.water_supply_repair_info_repair_type_tv);
        tv_repairType.setText(data.getRePairType());
        tv_contacts = (TextView) findViewById(R.id.water_supply_repair_info_contacts_tv);
        tv_contacts.setText(data.getContacts());
        tv_tel = (TextView) findViewById(R.id.water_supply_repair_info_tel_tv);
        tv_tel.setText(data.getTel());
        tv_remarks = (TextView) findViewById(R.id.water_supply_repair_info_remarks_tv);
        tv_remarks.setText(data.getRemarks());
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
