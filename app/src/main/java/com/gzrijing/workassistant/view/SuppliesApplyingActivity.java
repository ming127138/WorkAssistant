package com.gzrijing.workassistant.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.SuppliesApplyingAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.db.BusinessData;
import com.gzrijing.workassistant.db.SuppliesData;
import com.gzrijing.workassistant.db.SuppliesNoData;
import com.gzrijing.workassistant.entity.Supplies;
import com.gzrijing.workassistant.entity.SuppliesNo;
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

public class SuppliesApplyingActivity extends BaseActivity implements View.OnClickListener {

    private int position;
    private String userName;
    private String orderId;
    private SuppliesNo suppliesNo;
    private List<Supplies> suppliesList = new ArrayList<Supplies>();
    private TextView tv_reason;
    private TextView tv_useTime;
    private EditText et_remarks;
    private Button btn_edit;
    private ListView lv_list;
    private WheelMain wheelMain;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private SuppliesApplyingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplies_applying);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        orderId = intent.getStringExtra("orderId");
        position = intent.getIntExtra("position", -1);
        suppliesNo = (SuppliesNo)intent.getParcelableExtra("suppliesNo");
        List<SuppliesData> suppliesDataList  = DataSupport.where("applyId = ?", suppliesNo.getApplyId()).find(SuppliesData.class);
        for(SuppliesData data : suppliesDataList){
            Supplies supplies = new Supplies();
            supplies.setName(data.getName());
            supplies.setSpec(data.getSpec());
            supplies.setUnit(data.getUnit());
            supplies.setNum(data.getNum());
            supplies.setState(data.getState());
            suppliesList.add(supplies);
        }
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_reason = (TextView) findViewById(R.id.supplies_applying_reason_tv);
        tv_reason.setText("XXXXXX原因");
        tv_useTime = (TextView) findViewById(R.id.supplies_applying_use_time_tv);
        tv_useTime.setText(suppliesNo.getUseTime());
        et_remarks = (EditText) findViewById(R.id.supplies_applying_remarks_et);
        et_remarks.setText(suppliesNo.getRemarks());
        btn_edit = (Button) findViewById(R.id.supplies_applying_edit_btn);

        lv_list = (ListView) findViewById(R.id.supplies_applying_lv);
        adapter = new SuppliesApplyingAdapter(this, suppliesList);
        lv_list.setAdapter(adapter);
    }

    private void setListeners() {
        tv_useTime.setOnClickListener(this);
        btn_edit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.supplies_applying_use_time_tv:
                getUseTime();
                break;

            case R.id.supplies_applying_edit_btn:
                Intent intent = new Intent(this, SuppliesApplyEditActivity.class);
                intent.putExtra("suppliesList", (Serializable) suppliesList);
                startActivityForResult(intent, 10);
                break;
        }
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
                suppliesList.clear();
                suppliesList.addAll(suppliess);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_supplies_applying, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.action_apply) {
            SuppliesNo suppliesNo = apply();
            Intent intent = getIntent();
            intent.putExtra("position", position);
            intent.putExtra("suppliesNo", (Parcelable) suppliesNo);
            setResult(20, intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private SuppliesNo apply() {
        String useTime = tv_useTime.getText().toString();
        String remarks = et_remarks.getText().toString().trim();
        String applyId = "重新申请单00Y";
        Calendar rightNow = Calendar.getInstance();
        String applyTime = dateFormat.format(rightNow.getTime());

        DataSupport.deleteAll(SuppliesData.class, "applyId = ?", suppliesNo.getApplyId());
        DataSupport.deleteAll(SuppliesNoData.class, "applyId = ?", suppliesNo.getApplyId());

        BusinessData businessData = DataSupport.where("user = ? and orderId = ?", userName, orderId).find(BusinessData.class, true).get(0);
        for (int i = 0; i < suppliesList.size(); i++) {
            SuppliesData data = new SuppliesData();
            data.setNo(suppliesList.get(i).getId());
            data.setApplyId(applyId);
            data.setName(suppliesList.get(i).getName());
            data.setSpec(suppliesList.get(i).getSpec());
            data.setUnit(suppliesList.get(i).getUnit());
            data.setNum(suppliesList.get(i).getNum());
            data.setState("申请中");
            data.save();
            businessData.getSuppliesDataList().add(data);
        }
        SuppliesNoData data1 = new SuppliesNoData();
        data1.setApplyId(applyId);
        data1.setApplyTime(applyTime);
        data1.setUseTime(useTime);
        data1.setRemarks(remarks);
        data1.setState("申请中");
        data1.save();
        businessData.getSuppliesNoList().add(data1);
        businessData.save();

        SuppliesNo suppliesNo = new SuppliesNo();
        suppliesNo.setApplyId(applyId);
        suppliesNo.setApplyTime(applyTime);
        suppliesNo.setUseTime(useTime);
        suppliesNo.setRemarks(remarks);
        suppliesNo.setState("申请中");
        return suppliesNo;
    }

}
