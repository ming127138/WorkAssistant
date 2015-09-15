package com.gzrijing.workassistant.view;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.data.BusinessData;
import com.gzrijing.workassistant.data.WaterSupplyRepairData;
import com.gzrijing.workassistant.entity.Subordinate;
import com.gzrijing.workassistant.util.JudgeDate;
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

public class WaterSupplyRepairDistributeActivity extends AppCompatActivity implements View.OnClickListener {

    private String orderId;
    private TextView tv_orderId;
    private TextView tv_eventTime;
    private TextView tv_address;
    private TextView tv_reason;
    private TextView tv_repairType;
    private TextView tv_contacts;
    private TextView tv_tel;
    private TextView tv_remarks;
    private TextView tv_executor;
    private LinearLayout ll_executor;
    private TextView tv_deadline;
    private LinearLayout ll_deadline;
    private List<Subordinate> subordinates;
    private WheelMain wheelMain;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private int position;
    private BusinessData businessData;
    private WaterSupplyRepairData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_supply_repair_distribute);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        position = intent.getIntExtra("position", -1);

        businessData = DataSupport.where("orderId = ?", orderId).find(BusinessData.class, true).get(0);
        data = businessData.getWaterSupplyRepairData();

        subordinates = new ArrayList<Subordinate>();
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_orderId = (TextView) findViewById(R.id.water_supply_repair_distribute_order_id_tv);
        tv_orderId.setText(orderId);
        tv_eventTime = (TextView) findViewById(R.id.water_supply_repair_distribute_event_time_tv);
        tv_eventTime.setText(data.getTime());
        tv_address = (TextView) findViewById(R.id.water_supply_repair_distribute_address_tv);
        tv_address.setText(data.getAddress());
        tv_reason = (TextView) findViewById(R.id.water_supply_repair_distribute_reason_tv);
        tv_reason.setText(data.getReason());
        tv_repairType = (TextView) findViewById(R.id.water_supply_repair_distribute_repair_type_tv);
        tv_repairType.setText(data.getRePairType());
        tv_contacts = (TextView) findViewById(R.id.water_supply_repair_distribute_contacts_tv);
        tv_contacts.setText(data.getContacts());
        tv_tel = (TextView) findViewById(R.id.water_supply_repair_distribute_tel_tv);
        tv_tel.setText(data.getTel());
        tv_remarks = (TextView) findViewById(R.id.water_supply_repair_distribute_remarks_tv);
        tv_remarks.setText(data.getRemarks());
        tv_executor = (TextView) findViewById(R.id.water_supply_repair_distribute_executor_tv);
        ll_executor = (LinearLayout) findViewById(R.id.water_supply_repair_distribute_executor_ll);
        tv_deadline = (TextView) findViewById(R.id.water_supply_repair_distribute_deadline_tv);
        ll_deadline = (LinearLayout) findViewById(R.id.water_supply_repair_distribute_deadline_ll);
    }

    private void setListeners() {
        ll_executor.setOnClickListener(this);
        ll_deadline.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.water_supply_repair_distribute_executor_ll:
                Intent intent = new Intent(this, SubordinateActivity.class);
                intent.putExtra("subordinates", (Serializable) subordinates);
                startActivityForResult(intent, 10);
                break;

            case R.id.water_supply_repair_distribute_deadline_ll:
                getDeadline();
                break;
        }
    }

    private void getDeadline() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View timepickerview = inflater.inflate(R.layout.timepicker, null);
        ScreenInfo screenInfo = new ScreenInfo(this);
        wheelMain = new WheelMain(timepickerview, true);
        wheelMain.screenheight = screenInfo.getHeight();
        String time = tv_deadline.getText().toString();
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
                        tv_deadline.setText(wheelMain.getTime());
                        tv_deadline.setTextColor(getResources().getColor(
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
        if(requestCode == 10){
            if(resultCode == 10){
                String executors = data.getStringExtra("executors");
                subordinates = (List<Subordinate>)data.getSerializableExtra("list");
                tv_executor.setText(executors);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_water_supply_repair_distribute, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if(id == R.id.action_distribute){
            ContentValues values = new ContentValues();
            values.put("executor", tv_executor.getText().toString());
            DataSupport.update(WaterSupplyRepairData.class, values, data.getId());
            ContentValues values1 = new ContentValues();
            values1.put("deadline", tv_deadline.getText().toString());
            values1.put("flag", "查看进度");
            DataSupport.update(BusinessData.class, values1, businessData.getId());
            LeaderFragment.orderList.get(position).setDeadline(tv_deadline.getText().toString());
            LeaderFragment.orderList.get(position).setFlag("查看进度");
            LeaderFragment.adapter.notifyDataSetChanged();
            Toast.makeText(this, "派发成功", Toast.LENGTH_SHORT).show();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
