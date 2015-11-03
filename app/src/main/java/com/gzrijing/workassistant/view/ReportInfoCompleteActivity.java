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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.ReportInfoApprovalAdapter;
import com.gzrijing.workassistant.entity.ReportComplete;
import com.gzrijing.workassistant.util.JudgeDate;
import com.gzrijing.workassistant.widget.selectdate.ScreenInfo;
import com.gzrijing.workassistant.widget.selectdate.WheelMain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReportInfoCompleteActivity extends AppCompatActivity {

    private List<ReportComplete> infos = new ArrayList<ReportComplete>();
    private ListView lv_info;
    private WheelMain wheelMain;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private ReportInfoApprovalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_info_complete);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        String[] key = {"收款性质", "表身编号", "水表产地", "排水口径", "施工内容", "土建项目", "　　备注", "排水时间", "施工日期", "完工日期", "验收日期", "水表有效日期", ""};
        String[] value = {"用户","SBBH007", "XXXX产地", "DN36", "XXXXXX施工内容", "XXXXXXXXXXXXXXXXXX土建项目", "XXXXXX备注", "2015-10-15 10:10",
                "2015-10-15 11:00", "2015-10-15 14:00", "2015-10-15 15:00","2018-10-15 16:00", ""};
        for (int i = 0; i < key.length; i++) {
            ReportComplete info = new ReportComplete();
            info.setKey(key[i]);
            info.setValue(value[i]);
            infos.add(info);
        }
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_info = (ListView) findViewById(R.id.report_info_complete_info_lv);
        adapter = new ReportInfoApprovalAdapter(this, infos);
        lv_info.setAdapter(adapter);
    }

    private void setListeners() {
        lv_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key = infos.get(position).getKey();
                if (key.equals("水表有效日期") || key.equals("排水时间") || key.equals("施工日期：") || key.equals("完工日期：") || key.equals("验收日期：")) {
                    TextView tv_value = (TextView) view.findViewById(
                            R.id.listview_item_fragment_report_complete_date_value_tv);
                    getDate(position, tv_value);
                }

            }
        });

    }

    private void getDate(final int position, final TextView tv_value) {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View timepickerview = inflater.inflate(R.layout.timepicker, null);
        ScreenInfo screenInfo = new ScreenInfo(this);
        wheelMain = new WheelMain(timepickerview, true);
        wheelMain.screenheight = screenInfo.getHeight();
        String time = tv_value.getText().toString();
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
                        tv_value.setText(wheelMain.getTime());
                        infos.get(position).setValue(tv_value.getText().toString());
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
        getMenuInflater().inflate(R.menu.menu_report_info_complete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.action_sure) {
            setResult(10);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
