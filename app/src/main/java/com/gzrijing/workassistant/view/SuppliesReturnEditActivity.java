package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.SuppliesReturnEditAdapter;
import com.gzrijing.workassistant.entity.Supplies;
import com.gzrijing.workassistant.widget.MyListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SuppliesReturnEditActivity extends AppCompatActivity implements View.OnClickListener{

    private String orderId;
    private List<Supplies> suppliesList;
    private ImageView iv_checkAll;
    private Button btn_ok;
    private MyListView lv_return;
    private SuppliesReturnEditAdapter adapter;
    private boolean isCheckAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplies_return_edit);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        suppliesList = (List<Supplies>) intent.getSerializableExtra("suppliesList");
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iv_checkAll = (ImageView) findViewById(R.id.supplies_return_edit_check_all_iv);
        btn_ok = (Button) findViewById(R.id.supplies_return_edit_ok_btn);
        lv_return = (MyListView) findViewById(R.id.supplies_return_edit_return_lv);
        adapter = new SuppliesReturnEditAdapter(this, suppliesList, iv_checkAll, isCheckAll);
        lv_return.setAdapter(adapter);
    }

    private void setListeners() {
        iv_checkAll.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.supplies_return_edit_check_all_iv:
            if (isCheckAll) {
                for (Supplies supplies : suppliesList) {
                    supplies.setIsCheck(false);
                }
                iv_checkAll.setImageResource(R.drawable.spinner_item_check_off);
            } else {
                for (Supplies supplies : suppliesList) {
                    supplies.setIsCheck(true);
                }
                iv_checkAll.setImageResource(R.drawable.spinner_item_check_on);
            }
            adapter.notifyDataSetChanged();
            isCheckAll = !isCheckAll;
            break;

        case R.id.supplies_return_edit_ok_btn:
            save();
            break;
        }
    }

    private void save() {
        List<Supplies> suppList = new ArrayList<Supplies>();
        for(Supplies supplies : suppliesList){
            if(supplies.isCheck()){
                suppList.add(supplies);
            }
        }
        Intent intent = getIntent();
        intent.putExtra("suppliesList", (Serializable) suppList);
        setResult(30, intent);
        finish();
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
