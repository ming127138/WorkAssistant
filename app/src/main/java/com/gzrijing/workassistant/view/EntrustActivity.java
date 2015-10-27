package com.gzrijing.workassistant.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.util.JudgeDate;
import com.gzrijing.workassistant.widget.selectdate.ScreenInfo;
import com.gzrijing.workassistant.widget.selectdate.WheelMain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EntrustActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout ll_onDuty;
    private ImageView iv_onDuty;
    private LinearLayout ll_replace;
    private ImageView iv_replace;
    private TextView tv_person;
    private EditText et_person;
    private TextView tv_beginTime;
    private TextView tv_endTime;
    private boolean flag;
    private WheelMain wheelMain;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrust);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {

    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ll_onDuty = (LinearLayout) findViewById(R.id.entrust_on_duty_ll);
        iv_onDuty = (ImageView) findViewById(R.id.entrust_on_duty_iv);
        ll_replace = (LinearLayout) findViewById(R.id.entrust_replace_ll);
        iv_replace = (ImageView) findViewById(R.id.entrust_replace_iv);
        tv_person = (TextView) findViewById(R.id.entrust_person_tv);
        et_person = (EditText) findViewById(R.id.entrust_person_et);
        tv_beginTime = (TextView) findViewById(R.id.entrust_begin_time_tv);
        tv_endTime = (TextView) findViewById(R.id.entrust_end_time_tv);
    }

    private void setListeners() {
        ll_onDuty.setOnClickListener(this);
        ll_replace.setOnClickListener(this);
        tv_beginTime.setOnClickListener(this);
        tv_endTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.entrust_on_duty_ll:
                if (flag) {
                    iv_onDuty.setImageResource(R.drawable.spinner_item_check_on);
                    iv_replace.setImageResource(R.drawable.spinner_item_check_off);
                    tv_person.setText("　值班人");
                    flag = !flag;
                }
                break;

            case R.id.entrust_replace_ll:
                if (!flag) {
                    iv_replace.setImageResource(R.drawable.spinner_item_check_on);
                    iv_onDuty.setImageResource(R.drawable.spinner_item_check_off);
                    tv_person.setText("　代班人");
                    flag = !flag;
                }
                break;

            case R.id.entrust_begin_time_tv:
                getTime(tv_beginTime);
                break;

            case R.id.entrust_end_time_tv:
                getTime(tv_endTime);
                break;
        }
    }

    private void getTime(final TextView tv_time) {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View timepickerview = inflater.inflate(R.layout.timepicker, null);
        ScreenInfo screenInfo = new ScreenInfo(this);
        wheelMain = new WheelMain(timepickerview, true);
        wheelMain.screenheight = screenInfo.getHeight();
        String time = tv_time.getText().toString();
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
                        tv_time.setText(wheelMain.getTime());
                        tv_time.setTextColor(getResources().getColor(
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_entrust, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if(id == R.id.action_entrust){
            if(et_person.getText().toString().trim().equals("")){
                if (mToast == null) {
                    mToast = Toast.makeText(this, "请填写"+tv_person.getText().toString(), Toast.LENGTH_SHORT);
                } else {
                    mToast.setText("请填写"+tv_person.getText().toString());
                    mToast.setDuration(Toast.LENGTH_SHORT);
                }
                mToast.show();
                return true;
            }

            if(tv_beginTime.getText().toString().equals("")){
                if (mToast == null) {
                    mToast = Toast.makeText(this, "请选择开始时间", Toast.LENGTH_SHORT);
                } else {
                    mToast.setText("请选择开始时间");
                    mToast.setDuration(Toast.LENGTH_SHORT);
                }
                mToast.show();
                return true;
            }

            if(tv_endTime.getText().toString().equals("")){
                if (mToast == null) {
                    mToast = Toast.makeText(this, "请选择结束时间", Toast.LENGTH_SHORT);
                } else {
                    mToast.setText("请选择结束时间");
                    mToast.setDuration(Toast.LENGTH_SHORT);
                }
                mToast.show();
                return true;
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
