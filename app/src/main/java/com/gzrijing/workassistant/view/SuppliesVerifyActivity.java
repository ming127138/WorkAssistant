package com.gzrijing.workassistant.view;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.SuppliesVerifyAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.db.BusinessData;
import com.gzrijing.workassistant.entity.SuppliesVerify;
import com.gzrijing.workassistant.service.GetSuppliesVerifyService;
import com.gzrijing.workassistant.util.JsonParseUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

public class SuppliesVerifyActivity extends BaseActivity {

    private ListView lv_wait;
    private ListView lv_ok;
    private String orderId;
    private ArrayList<SuppliesVerify> waitList = new ArrayList<SuppliesVerify>();
    private ArrayList<SuppliesVerify> okList = new ArrayList<SuppliesVerify>();
    private SuppliesVerifyAdapter waitAdapter;
    private SuppliesVerifyAdapter okAdapter;
    private String userNo;
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplies_verify);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        SharedPreferences app = getSharedPreferences(
                "saveUser", MODE_PRIVATE);
        userNo = app.getString("userNo", "");

        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");

        getSuppliesVerify();
    }

    private void getSuppliesVerify() {
        IntentFilter intentFilter = new IntentFilter("action.com.gzrijing.workassistant.SuppliesVerify");
        registerReceiver(mBroadcastReceiver, intentFilter);
        serviceIntent = new Intent(this, GetSuppliesVerifyService.class);
        serviceIntent.putExtra("userNo", userNo);
        serviceIntent.putExtra("orderId", orderId);
        startService(serviceIntent);
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_wait = (ListView) findViewById(R.id.supplies_verify_wait);
        waitAdapter = new SuppliesVerifyAdapter(this, waitList);
        lv_wait.setAdapter(waitAdapter);
        lv_ok = (ListView) findViewById(R.id.supplies_verify_ok);
        okAdapter = new SuppliesVerifyAdapter(this, okList);
        lv_ok.setAdapter(okAdapter);
    }

    private void setListeners() {
        lv_wait.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SuppliesVerifyActivity.this, SuppliesVerifyWaitInfoActivity.class);
                intent.putParcelableArrayListExtra("suppliesVerifyInfoList", waitList.get(position).getSuppliesVerifyInfoList());
                intent.putExtra("id", waitList.get(position).getId());
                intent.putExtra("userTime", waitList.get(position).getUseTime());
                intent.putExtra("remarks", waitList.get(position).getRemarks());
                intent.putExtra("position", position);
                startActivityForResult(intent, 10);
            }
        });

        lv_ok.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SuppliesVerifyActivity.this, SuppliesVerifyOkInfoActivity.class);
                intent.putParcelableArrayListExtra("suppliesVerifyInfoList", okList.get(position).getSuppliesVerifyInfoList());
                intent.putExtra("userTime", okList.get(position).getUseTime());
                intent.putExtra("remarks", okList.get(position).getRemarks());
                startActivity(intent);
            }
        });
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("action.com.gzrijing.workassistant.SuppliesVerify")){
                String jsonData = intent.getStringExtra("jsonData");
                ArrayList<SuppliesVerify> SuppliesVerifyList = JsonParseUtils.getSuppliesVerify(jsonData);
                for(SuppliesVerify SuppliesVerify : SuppliesVerifyList){
                    if(SuppliesVerify.getState().equals("审核")){
                        okList.add(SuppliesVerify);
                    }
                    if(SuppliesVerify.getState().equals("保存")){
                        waitList.add(SuppliesVerify);
                    }
                }
                okAdapter.notifyDataSetChanged();
                waitAdapter.notifyDataSetChanged();

                BusinessData businessData = DataSupport.where("user = ? and orderId = ?", userNo, orderId)
                        .find(BusinessData.class, true).get(0);
                int num = businessData.getSuppliesApplyNum();

                ContentValues values = new ContentValues();
                values.put("suppliesApplyNum", 0);
                DataSupport.updateAll(BusinessData.class, values, "user = ? and orderId = ?", userNo, orderId);

                Intent intent1 = new Intent("action.com.gzrijing.workassistant.LeaderFragment.SuppliesVerify0");
                intent1.putExtra("orderId", orderId);
                sendBroadcast(intent1);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            if(resultCode == 10){
                int position = data.getIntExtra("position", -1);
                String isPass = data.getStringExtra("isPass");
                if(isPass.equals("1")){
                    okList.add(waitList.get(position));
                    waitList.remove(position);
                }
                if(isPass.equals("0")){
                    waitList.remove(position);
                }
                waitAdapter.notifyDataSetChanged();
                okAdapter.notifyDataSetChanged();
            }
        }
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

    @Override
    protected void onDestroy() {
        stopService(serviceIntent);
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}
