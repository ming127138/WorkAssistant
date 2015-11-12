package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.DetailedInfoAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.db.BusinessData;
import com.gzrijing.workassistant.db.DetailedInfoData;
import com.gzrijing.workassistant.entity.DetailedInfo;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class DetailedInfoActivity extends BaseActivity {

    private String userName;
    private String orderId;
    private BusinessData businessData;
    private ListView lv_info;
    private List<DetailedInfo> infos = new ArrayList<DetailedInfo>();
    private DetailedInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_info);

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
        List<DetailedInfoData> datas = businessData.getDetailedInfoList();
        for(DetailedInfoData data : datas){
            DetailedInfo info = new DetailedInfo();
            info.setKey(data.getKey());
            info.setValue(data.getValue());
            infos.add(info);
        }
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_info = (ListView) findViewById(R.id.detailed_info_lv);
        adapter = new DetailedInfoAdapter(this, infos);
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
