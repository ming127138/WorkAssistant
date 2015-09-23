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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.MachineApplyApplyingAdapter;
import com.gzrijing.workassistant.adapter.MachineApplyCreatedAdapter;
import com.gzrijing.workassistant.adapter.MachineApplyReceivedAdapter;
import com.gzrijing.workassistant.adapter.MachineApplyReturnAdapter;
import com.gzrijing.workassistant.data.BusinessData;
import com.gzrijing.workassistant.data.MachineData;
import com.gzrijing.workassistant.entity.Machine;
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

public class MachineApplyActivity extends AppCompatActivity implements View.OnClickListener {

    private String userName;
    private String orderId;
    private Button btn_applyEdit;
    private TextView tv_useTime;
    private TextView tv_returnTime;
    private EditText et_useAddress;
    private EditText et_applyRemarks;
    private Button btn_apply;
    private Button btn_returnEdit;
    private TextView tv_returnReTime;
    private EditText et_returnAddress;
    private EditText et_returnRemarks;
    private Button btn_return;
    private MyListView lv_created;
    private MyListView lv_received;
    private MyListView lv_applying;
    private MyListView lv_return;
    private List<Machine> createdList = new ArrayList<Machine>();
    private List<Machine> receivedList = new ArrayList<Machine>();
    private List<Machine> applyingList = new ArrayList<Machine>();
    private List<Machine> returnList = new ArrayList<Machine>();
    private MachineApplyCreatedAdapter createdAdapter;
    private MachineApplyReceivedAdapter receivedAdapter;
    private MachineApplyApplyingAdapter applyingAdapter;
    private MachineApplyReturnAdapter returnAdapter;
    private WheelMain wheelMain;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_apply);

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

        BusinessData businessData = DataSupport.where("user = ? and orderId = ?", userName, orderId).find(BusinessData.class, true).get(0);
        List<MachineData> machineDataList = businessData.getMachineDataList();
        for (MachineData data : machineDataList) {
            if (data.getFlag().equals("申请")) {
                Machine machine = new Machine();
                machine.setDataId(data.getId());
                machine.setId(data.getNo());
                machine.setName(data.getName());
                machine.setSpec(data.getSpec());
                machine.setUnit(data.getUnit());
                machine.setNum(data.getNum());
                machine.setState(data.getState());
                applyingList.add(machine);
            }
            if (data.getFlag().equals("领用")) {
                Machine machine = new Machine();
                machine.setDataId(data.getId());
                machine.setId(data.getNo());
                machine.setName(data.getName());
                machine.setSpec(data.getSpec());
                machine.setUnit(data.getUnit());
                machine.setNum(data.getNum());
                machine.setState(data.getState());
                machine.setReturnType(data.getReturnType());
                receivedList.add(machine);
            }
            if (data.getFlag().equals("退回")) {
                Machine machine = new Machine();
                machine.setDataId(data.getId());
                machine.setId(data.getNo());
                machine.setName(data.getName());
                machine.setSpec(data.getSpec());
                machine.setUnit(data.getUnit());
                machine.setNum(data.getNum());
                machine.setReturnType(data.getReturnType());
                machine.setState(data.getState());
                returnList.add(machine);
            }
        }
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_applyEdit = (Button) findViewById(R.id.machine_apply_apply_edit_btn);
        tv_useTime = (TextView) findViewById(R.id.machine_apply_use_time_tv);
        tv_returnTime = (TextView) findViewById(R.id.machine_apply_return_time_tv);
        et_useAddress = (EditText) findViewById(R.id.machine_apply_use_address_et);
        et_applyRemarks = (EditText) findViewById(R.id.machine_apply_apply_remarks_et);
        btn_apply = (Button) findViewById(R.id.machine_apply_apply_btn);

        btn_returnEdit = (Button) findViewById(R.id.machine_apply_return_edit_btn);
        tv_returnReTime = (TextView) findViewById(R.id.machine_apply_return_return_time_tv);
        et_returnAddress = (EditText) findViewById(R.id.machine_apply_return_address_et);
        et_returnRemarks = (EditText) findViewById(R.id.machine_apply_return_remarks_et);
        btn_return = (Button) findViewById(R.id.machine_apply_return_btn);

        if(returnList.size() > 0){
            btn_returnEdit.setVisibility(View.GONE);
            btn_return.setText("退回");
            btn_return.setVisibility(View.VISIBLE);
        }

        lv_created = (MyListView) findViewById(R.id.machine_apply_created_lv);
        createdAdapter = new MachineApplyCreatedAdapter(this, createdList);
        lv_created.setAdapter(createdAdapter);
        lv_received = (MyListView) findViewById(R.id.machine_apply_received_lv);
        receivedAdapter = new MachineApplyReceivedAdapter(this, receivedList);
        lv_received.setAdapter(receivedAdapter);
        lv_applying = (MyListView) findViewById(R.id.machine_apply_applying_lv);
        applyingAdapter = new MachineApplyApplyingAdapter(this, applyingList, receivedList, receivedAdapter);
        lv_applying.setAdapter(applyingAdapter);
        lv_return = (MyListView) findViewById(R.id.machine_apply_return_lv);
        returnAdapter = new MachineApplyReturnAdapter(this, returnList);
        lv_return.setAdapter(returnAdapter);
    }

    private void setListeners() {
        btn_applyEdit.setOnClickListener(this);
        tv_useTime.setOnClickListener(this);
        tv_returnTime.setOnClickListener(this);
        btn_apply.setOnClickListener(this);
        btn_returnEdit.setOnClickListener(this);
        tv_returnReTime.setOnClickListener(this);
        btn_return.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.machine_apply_apply_edit_btn:
                Intent intent1 = new Intent(this, MachineApplyEditActivity.class);
                intent1.putExtra("userName", userName);
                intent1.putExtra("orderId", orderId);
                intent1.putExtra("machineList", (Serializable) createdList);
                startActivityForResult(intent1, 10);
                break;

            case R.id.machine_apply_use_time_tv:
                getTime(tv_useTime);
                break;

            case R.id.machine_apply_return_time_tv:
                getTime(tv_returnTime);
                break;

            case R.id.machine_apply_apply_btn:
                apply();
                break;

            case R.id.machine_apply_return_edit_btn:
                Intent intent2 = new Intent(this, MachineReturnEditActivity.class);
                intent2.putExtra("orderId", orderId);
                intent2.putExtra("machineList", (Serializable) receivedList);
                startActivityForResult(intent2, 20);
                break;

            case R.id.machine_apply_return_return_time_tv:
                getTime(tv_returnReTime);
                break;

            case R.id.machine_apply_return_btn:
                if(btn_return.getText().toString().equals("申请")){
                    returnApply();
                    break;
                }
                if(btn_return.getText().toString().equals("退回")){
                    returnConfirm();
                    break;
                }
        }
    }

    private void apply() {
        if (tv_useTime.getText().toString().equals("")) {
            if (mToast == null) {
                mToast = Toast.makeText(this, "请选择领用时间", Toast.LENGTH_SHORT);
            } else {
                mToast.setText("请选择领用时间");
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
            return;
        }
        if (tv_returnTime.getText().toString().equals("")) {
            if (mToast == null) {
                mToast = Toast.makeText(this, "请选择退回时间", Toast.LENGTH_SHORT);
            } else {
                mToast.setText("请选择退回时间");
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
            return;
        }
        if (et_useAddress.getText().toString().trim().equals("")) {
            if (mToast == null) {
                mToast = Toast.makeText(this, "请填写使用地点", Toast.LENGTH_SHORT);
            } else {
                mToast.setText("请填写使用地点");
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put("flag", "申请");
        DataSupport.updateAll(MachineData.class, values, "flag = ?", "创建");
        BusinessData businessData = DataSupport.where("user = ? and orderId = ?", userName, orderId).find(BusinessData.class, true).get(0);
        List<MachineData> machineDataList = businessData.getMachineDataList();
        applyingList.clear();
        for (MachineData data : machineDataList) {
            if (data.getFlag().equals("申请")) {
                Machine machine = new Machine();
                machine.setDataId(data.getId());
                machine.setId(data.getNo());
                machine.setName(data.getName());
                machine.setSpec(data.getSpec());
                machine.setUnit(data.getUnit());
                machine.setNum(data.getNum());
                machine.setState(data.getState());
                applyingList.add(machine);
            }
        }
        btn_apply.setVisibility(View.GONE);
        tv_useTime.setText("");
        tv_useTime.setHint("未选择");
        tv_returnTime.setText("");
        tv_returnTime.setHint("未选择");
        et_useAddress.setText("");
        et_applyRemarks.setText("");
        createdList.clear();
        createdAdapter.notifyDataSetChanged();
        applyingAdapter.notifyDataSetChanged();
    }

    private void returnApply() {
        if (tv_returnReTime.getText().toString().equals("")) {
            if (mToast == null) {
                mToast = Toast.makeText(this, "请选择退回时间", Toast.LENGTH_SHORT);
            } else {
                mToast.setText("请选择退回时间");
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
            return;
        }
        if (et_returnAddress.getText().toString().trim().equals("")) {
            if (mToast == null) {
                mToast = Toast.makeText(this, "请选择退回地点", Toast.LENGTH_SHORT);
            } else {
                mToast.setText("请选择退回地点");
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
            return;
        }

        for(Machine reList : returnList){
            MachineData machineData = DataSupport.find(MachineData.class, reList.getDataId());
            int num = machineData.getNum() - reList.getNum();
            if(num == 0){
                ContentValues values = new ContentValues();
                values.put("flag", "退回");
                values.put("state", "退回申请中");
                DataSupport.update(MachineData.class, values, reList.getDataId());
            }else{
                ContentValues values = new ContentValues();
                values.put("num", num);
                DataSupport.update(MachineData.class, values, reList.getDataId());

                MachineData data = new MachineData();
                data.setNo(reList.getId());
                data.setSpec(reList.getSpec());
                data.setName(reList.getName());
                data.setUnit(reList.getUnit());
                data.setNum(reList.getNum());
                data.setReturnType(reList.getReturnType());
                data.setFlag("退回");
                data.setState("退回申请中");
                data.save();
                BusinessData businessData = DataSupport.where("user = ? and orderId = ?", userName, orderId).find(BusinessData.class, true).get(0);
                businessData.getMachineDataList().add(data);
                businessData.save();
            }
            reList.setState("退回申请中");
        }

        receivedList.clear();
        BusinessData businessData = DataSupport.where("user = ? and orderId = ?", userName, orderId).find(BusinessData.class, true).get(0);
        List<MachineData> machineDatas = businessData.getMachineDataList();
        for(MachineData data : machineDatas){
            if(data.getFlag().equals("领用")){
                Machine machine = new Machine();
                machine.setDataId(data.getId());
                machine.setId(data.getNo());
                machine.setName(data.getName());
                machine.setSpec(data.getSpec());
                machine.setUnit(data.getUnit());
                machine.setNum(data.getNum());
                machine.setState(data.getState());
                receivedList.add(machine);
            }
        }
        returnAdapter.notifyDataSetChanged();
        receivedAdapter.notifyDataSetChanged();
        btn_returnEdit.setVisibility(View.GONE);
        btn_return.setText("退回");
        tv_returnReTime.setText("");
        tv_returnReTime.setHint("未选择");
        et_returnAddress.setText("");
        et_returnRemarks.setText("");
    }

    private void returnConfirm() {
        for(Machine reList : returnList){
            DataSupport.delete(MachineData.class, reList.getDataId());
        }
        returnList.clear();
        returnAdapter.notifyDataSetChanged();
        btn_returnEdit.setVisibility(View.VISIBLE);
        btn_return.setText("申请");
        btn_return.setVisibility(View.GONE);
    }

    private void getTime(final TextView tv) {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View timepickerview = inflater.inflate(R.layout.timepicker, null);
        ScreenInfo screenInfo = new ScreenInfo(this);
        wheelMain = new WheelMain(timepickerview, true);
        wheelMain.screenheight = screenInfo.getHeight();
        String time = tv.getText().toString();
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
                        tv.setText(wheelMain.getTime());
                        tv.setTextColor(getResources().getColor(
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
                List<Machine> machines = (List<Machine>) data.getSerializableExtra("machineList");
                if (machines.size() > 0) {
                    btn_apply.setVisibility(View.VISIBLE);
                } else {
                    btn_apply.setVisibility(View.GONE);
                }
                createdList.clear();
                createdList.addAll(machines);
                createdAdapter.notifyDataSetChanged();
            }
        }

        if (requestCode == 20) {
            if (resultCode == 20) {
                List<Machine> machines = (List<Machine>) data.getSerializableExtra("machineList");
                if (machines.size() > 0) {
                    btn_return.setVisibility(View.VISIBLE);
                } else {
                    btn_return.setVisibility(View.GONE);
                }
                returnList.clear();
                returnList.addAll(machines);
                returnAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            DataSupport.deleteAll(MachineData.class, "flag = ?", "创建");
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
