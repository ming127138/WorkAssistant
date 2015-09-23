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
import com.gzrijing.workassistant.adapter.MachineReturnEditAdapter;
import com.gzrijing.workassistant.entity.Machine;
import com.gzrijing.workassistant.widget.MyListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MachineReturnEditActivity extends AppCompatActivity implements View.OnClickListener{

    private String orderId;
    private List<Machine> machineList;
    private ImageView iv_checkAll;
    private Button btn_ok;
    private MyListView lv_return;
    private MachineReturnEditAdapter adapter;
    private boolean isCheckAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_return_edit);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        machineList = (List<Machine>) intent.getSerializableExtra("machineList");
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iv_checkAll = (ImageView) findViewById(R.id.machine_return_edit_check_all_iv);
        btn_ok = (Button) findViewById(R.id.machine_return_edit_ok_btn);
        lv_return = (MyListView) findViewById(R.id.machine_return_edit_return_lv);
        adapter = new MachineReturnEditAdapter(this, machineList, iv_checkAll, isCheckAll);
        lv_return.setAdapter(adapter);
    }

    private void setListeners() {
        iv_checkAll.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.machine_return_edit_check_all_iv:
            if (isCheckAll) {
                for (Machine machine : machineList) {
                    machine.setIsCheck(false);
                }
                iv_checkAll.setImageResource(R.drawable.spinner_item_check_off);
            } else {
                for (Machine machine : machineList) {
                    machine.setIsCheck(true);
                }
                iv_checkAll.setImageResource(R.drawable.spinner_item_check_on);
            }
            adapter.notifyDataSetChanged();
            isCheckAll = !isCheckAll;
            break;

        case R.id.machine_return_edit_ok_btn:
            save();
            break;
        }
    }

    private void save() {
        List<Machine> machines = new ArrayList<Machine>();
        for(Machine machine : machineList){
            if(machine.isCheck()){
                machines.add(machine);
            }
        }
        Intent intent = getIntent();
        intent.putExtra("machineList", (Serializable) machines);
        setResult(20, intent);
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
