package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.SuppliesApplyingAdapter;
import com.gzrijing.workassistant.db.SuppliesData;
import com.gzrijing.workassistant.entity.Supplies;
import com.gzrijing.workassistant.entity.SuppliesNo;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class SuppliesApplyingScanActivity extends AppCompatActivity {

    private ListView lv_list;
    private ArrayList<Supplies> suppliesList = new ArrayList<Supplies>();
    private SuppliesApplyingAdapter adapter;
    private TextView tv_useTime;
    private TextView tv_remark;
    private SuppliesNo suppliesNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplies_applying_scan);

        initData();
        initViews();
    }

    private void initData() {
        Intent intent = getIntent();
        suppliesNo = (SuppliesNo) intent.getParcelableExtra("suppliesNo");
        List<SuppliesData> suppliesDataList = DataSupport.where("applyId = ?", suppliesNo.getApplyId()).find(SuppliesData.class);
        for (SuppliesData data : suppliesDataList) {
            Supplies supplies = new Supplies();
            supplies.setName(data.getName());
            supplies.setSpec(data.getSpec());
            supplies.setUnit(data.getUnit());
            supplies.setNum(data.getNum());
            suppliesList.add(supplies);
        }
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_useTime = (TextView) findViewById(R.id.supplies_applying_scan_use_time_tv);
        tv_useTime.setText(suppliesNo.getUseTime());
        tv_remark = (TextView) findViewById(R.id.supplies_applying_scan_remark_tv);
        tv_remark.setText(suppliesNo.getRemarks());

        lv_list = (ListView) findViewById(R.id.supplies_applying_scan_lv);
        adapter = new SuppliesApplyingAdapter(this, suppliesList);
        lv_list.setAdapter(adapter);
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
