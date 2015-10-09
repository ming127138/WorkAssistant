package com.gzrijing.workassistant.view;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.data.BusinessData;
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

public class DistributeActivity extends AppCompatActivity implements View.OnClickListener {

    private String userName;
    private String orderId;
    private int position;
    private EditText et_remarks;
    private TextView tv_executor;
    private LinearLayout ll_executor;
    private TextView tv_deadline;
    private LinearLayout ll_deadline;
    private List<Subordinate> subordinates = new ArrayList<Subordinate>();
    private WheelMain wheelMain;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribute);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        SharedPreferences app = getSharedPreferences(
                "saveUserInfo", MODE_PRIVATE);
        userName = app.getString("userName", "");
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        position = intent.getIntExtra("position", -1);

    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_remarks = (EditText) findViewById(R.id.distribute_remarks_et);
        tv_executor = (TextView) findViewById(R.id.distribute_executor_tv);
        ll_executor = (LinearLayout) findViewById(R.id.distribute_executor_ll);
        tv_deadline = (TextView) findViewById(R.id.distribute_deadline_tv);
        ll_deadline = (LinearLayout) findViewById(R.id.distribute_deadline_ll);
    }

    private void setListeners() {
        ll_executor.setOnClickListener(this);
        ll_deadline.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.distribute_executor_ll:
                Intent intent = new Intent(this, SubordinateActivity.class);
                intent.putExtra("subordinates", (Serializable) subordinates);
                startActivityForResult(intent, 10);
                break;

            case R.id.distribute_deadline_ll:
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
            if(tv_executor.getText().toString().equals("")){
                if (mToast == null) {
                    mToast = Toast.makeText(this, "请选择施工员", Toast.LENGTH_SHORT);
                } else {
                    mToast.setText("请选择施工员");
                    mToast.setDuration(Toast.LENGTH_SHORT);
                }
                mToast.show();
                return true;
            }

            if(tv_deadline.getText().toString().equals("")){
                if (mToast == null) {
                    mToast = Toast.makeText(this, "请选择工程期限", Toast.LENGTH_SHORT);
                } else {
                    mToast.setText("请选择工程期限");
                    mToast.setDuration(Toast.LENGTH_SHORT);
                }
                mToast.show();
                return true;
            }

            ContentValues values = new ContentValues();
            values.put("deadline", tv_deadline.getText().toString());
            values.put("state", "已派发");
            DataSupport.updateAll(BusinessData.class, values, "user = ? and orderId = ?", userName, orderId);
            LeaderFragment.orderList.get(position).setState("已派发");
            LeaderFragment.orderList.get(position).setDeadline(tv_deadline.getText().toString());
            LeaderFragment.adapter.notifyDataSetChanged();
            Toast.makeText(this, "派发成功", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
