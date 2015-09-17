package com.gzrijing.workassistant.view;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.SuppliesApplyApplyingAdapter;
import com.gzrijing.workassistant.adapter.SuppliesApplyCreatedAdapter;
import com.gzrijing.workassistant.adapter.SuppliesApplyReceivedAdapter;
import com.gzrijing.workassistant.data.BusinessData;
import com.gzrijing.workassistant.data.SuppliesData;
import com.gzrijing.workassistant.entity.Supplies;
import com.gzrijing.workassistant.util.JudgeDate;
import com.gzrijing.workassistant.widget.MyListView;
import com.gzrijing.workassistant.widget.selectdate.ScreenInfo;
import com.gzrijing.workassistant.widget.selectdate.WheelMain;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SuppliesApplyActivity extends AppCompatActivity implements View.OnClickListener {

    private String orderId;
    private Button btn_edit;
    private Button btn_apply;
    private TextView tv_useTime;
    private EditText et_remarks;
    private MyListView lv_created;
    private MyListView lv_applying;
    private MyListView lv_received;
    private List<Supplies> createdList = new ArrayList<Supplies>();
    private List<Supplies> applyingList = new ArrayList<Supplies>();
    private List<Supplies> receivedList = new ArrayList<Supplies>();
    private SuppliesApplyCreatedAdapter createdAdapter;
    private SuppliesApplyApplyingAdapter applyingAdapter;
    private SuppliesApplyReceivedAdapter receivedAdapter;
    private WheelMain wheelMain;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplies_apply);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");

        BusinessData businessData = DataSupport.where("orderId = ?", orderId).find(BusinessData.class, true).get(0);
        List<SuppliesData> suppliesDataList = businessData.getSuppliesDataList();
        for(SuppliesData data : suppliesDataList){
            if(data.getFlag().equals("申请")){
                Supplies supplies = new Supplies();
                supplies.setDataId(data.getId());
                supplies.setId(data.getNo());
                supplies.setName(data.getName());
                supplies.setSpec(data.getSpec());
                supplies.setUnit(data.getUnit());
                supplies.setNum(data.getNum());
                supplies.setState(data.getState());
                applyingList.add(supplies);
            }
            if(data.getFlag().equals("领出")){
                Supplies supplies = new Supplies();
                supplies.setId(data.getNo());
                supplies.setName(data.getName());
                supplies.setSpec(data.getSpec());
                supplies.setUnit(data.getUnit());
                supplies.setNum(data.getNum());
                receivedList.add(supplies);
            }
        }
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_edit = (Button) findViewById(R.id.supplies_apply_edit_btn);
        btn_apply = (Button) findViewById(R.id.supplies_apply_apply_btn);
        tv_useTime = (TextView) findViewById(R.id.supplies_apply_use_time_tv);
        et_remarks = (EditText) findViewById(R.id.supplies_apply_remarks_et);

        lv_created = (MyListView) findViewById(R.id.supplies_apply_created_lv);
        createdAdapter = new SuppliesApplyCreatedAdapter(this, createdList);
        lv_created.setAdapter(createdAdapter);
        lv_received = (MyListView) findViewById(R.id.supplies_apply_received_lv);
        receivedAdapter = new SuppliesApplyReceivedAdapter(this, receivedList);
        lv_received.setAdapter(receivedAdapter);
        lv_applying = (MyListView) findViewById(R.id.supplies_apply_applying_lv);
        applyingAdapter = new SuppliesApplyApplyingAdapter(this, applyingList, receivedList, receivedAdapter);
        lv_applying.setAdapter(applyingAdapter);
    }

    private void setListeners() {
        btn_edit.setOnClickListener(this);
        btn_apply.setOnClickListener(this);
        tv_useTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.supplies_apply_edit_btn:
                Intent intent = new Intent(this, SuppliesActivity.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("suppliesList", (Serializable) createdList);
                startActivityForResult(intent, 10);
                break;

            case R.id.supplies_apply_apply_btn:
                apply();
                break;

            case R.id.supplies_apply_use_time_tv:
                getUseTime();
                break;
        }
    }

    private void apply() {
        if(tv_useTime.getText().toString().equals("")){
            Toast.makeText(SuppliesApplyActivity.this, "请选择领用时间", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put("flag", "申请");
        DataSupport.updateAll(SuppliesData.class, values, "flag = ?", "创建");
        BusinessData businessData = DataSupport.where("orderId = ?", orderId).find(BusinessData.class, true).get(0);
        List<SuppliesData> suppliesDataList = businessData.getSuppliesDataList();
        applyingList.clear();
        for(SuppliesData data : suppliesDataList){
            if(data.getFlag().equals("申请")){
                Supplies supplies = new Supplies();
                supplies.setDataId(data.getId());
                supplies.setId(data.getNo());
                supplies.setName(data.getName());
                supplies.setSpec(data.getSpec());
                supplies.setUnit(data.getUnit());
                supplies.setNum(data.getNum());
                supplies.setState(data.getState());
                applyingList.add(supplies);
            }
        }
        btn_apply.setVisibility(View.GONE);
        tv_useTime.setText("");
        tv_useTime.setHint("未选择");
        et_remarks.setText("");
        createdList.clear();
        createdAdapter.notifyDataSetChanged();
        applyingAdapter.notifyDataSetChanged();
    }

    private void getUseTime() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View timepickerview = inflater.inflate(R.layout.timepicker, null);
        ScreenInfo screenInfo = new ScreenInfo(this);
        wheelMain = new WheelMain(timepickerview, true);
        wheelMain.screenheight = screenInfo.getHeight();
        String time = tv_useTime.getText().toString();
        Calendar calendar = Calendar.getInstance();
        if (JudgeDate.isDate(time, "yyyy-MM-dd HH:mm")) {
            try {
                calendar.setTime(dateFormat.parse(time));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DAY_OF_MONTH);
        int h = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        wheelMain.initDateTimePicker(y, m - 1, d, h, min);
        new AlertDialog.Builder(this)
                .setTitle("选择时间")
                .setView(timepickerview)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_useTime.setText(wheelMain.getTime());
                        tv_useTime.setTextColor(getResources().getColor(
                                R.color.black));
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == 10) {
                List<Supplies> suppliess = (List<Supplies>) data.getSerializableExtra("suppliesList");
                if (suppliess.size() > 0) {
                    btn_apply.setVisibility(View.VISIBLE);
                } else {
                    btn_apply.setVisibility(View.GONE);
                }
                createdList.clear();
                createdList.addAll(suppliess);
                createdAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            DataSupport.deleteAll(SuppliesData.class, "flag = ?", "创建");
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
