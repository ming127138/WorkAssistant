package com.gzrijing.workassistant.view;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.MachineApplyApplyingAdapter;
import com.gzrijing.workassistant.adapter.MachineApplyApprovalAdapter;
import com.gzrijing.workassistant.adapter.MachineApplyCreatedAdapter;
import com.gzrijing.workassistant.adapter.MachineApplyReceivedAdapter;
import com.gzrijing.workassistant.adapter.MachineApplyReturnApplyingAdapter;
import com.gzrijing.workassistant.adapter.MachineApplyReturnCreatedAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.db.BusinessData;
import com.gzrijing.workassistant.db.MachineData;
import com.gzrijing.workassistant.db.MachineNoData;
import com.gzrijing.workassistant.entity.Machine;
import com.gzrijing.workassistant.entity.MachineNo;
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

public class MachineApplyActivity extends BaseActivity implements View.OnClickListener {

    private String userNo;
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
    private MyListView lv_applying;
    private MyListView lv_received;
    private MyListView lv_approval;
    private MyListView lv_returnCreated;
    private MyListView lv_returnApplying;
    private ArrayList<Machine> createdList = new ArrayList<Machine>();
    private List<MachineNo> applyingList = new ArrayList<MachineNo>();
    private List<Machine> receivedList = new ArrayList<Machine>();
    private List<Machine> approvalList = new ArrayList<Machine>();
    private List<Machine> returnCreatedList = new ArrayList<Machine>();
    private List<Machine> returnApplyingList = new ArrayList<Machine>();
    private MachineApplyCreatedAdapter createdAdapter;
    private MachineApplyApplyingAdapter applyingAdapter;
    private MachineApplyReceivedAdapter receivedAdapter;
    private MachineApplyApprovalAdapter approvalAdapter;
    private MachineApplyReturnCreatedAdapter returnCreatedAdapter;
    private MachineApplyReturnApplyingAdapter returnApplyingAdapter;
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
                "saveUser", MODE_PRIVATE);
        userNo = app.getString("userNo", "");
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");

        BusinessData businessData = DataSupport.where("user = ? and orderId = ?", userNo, orderId).find(BusinessData.class, true).get(0);
        List<MachineNoData> machineNoData = businessData.getMachineNoList();
        for (MachineNoData data : machineNoData) {
            MachineNo machineNo = new MachineNo();
            machineNo.setApplyId(data.getApplyId());
            machineNo.setApplyTime(data.getApplyTime());
            machineNo.setUseTime(data.getUseTime());
            machineNo.setReturnTime(data.getReturnTime());
            machineNo.setUseAddress(data.getUseAddress());
            machineNo.setState(data.getState());
            applyingList.add(machineNo);
        }

        List<MachineData> machineDataList = businessData.getMachineDataList();
        for (MachineData data : machineDataList) {
            if (data.getState().equals("已审核") || data.getState().equals("可领用")) {
                Machine machine = new Machine();
                machine.setDataId(data.getId());
                machine.setId(data.getNo());
                machine.setName(data.getName());
                machine.setSpec(data.getSpec());
                machine.setUnit(data.getUnit());
                machine.setNum(data.getNum());
                machine.setState(data.getState());
                approvalList.add(machine);
            }
            if (data.getState().equals("已领用")) {
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

            if (data.getState().equals("退回申请中") || data.getState().equals("退回已批准")) {
                Machine machine = new Machine();
                machine.setDataId(data.getId());
                machine.setId(data.getNo());
                machine.setName(data.getName());
                machine.setSpec(data.getSpec());
                machine.setUnit(data.getUnit());
                machine.setNum(data.getNum());
                machine.setState(data.getState());
                machine.setReturnType(data.getReturnType());
                returnApplyingList.add(machine);
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

        lv_created = (MyListView) findViewById(R.id.machine_apply_created_lv);
        createdAdapter = new MachineApplyCreatedAdapter(this, createdList);
        lv_created.setAdapter(createdAdapter);
        lv_received = (MyListView) findViewById(R.id.machine_apply_received_lv);
        lv_applying = (MyListView) findViewById(R.id.machine_apply_applying_lv);
        applyingAdapter = new MachineApplyApplyingAdapter(this, applyingList);
        lv_applying.setAdapter(applyingAdapter);
        receivedAdapter = new MachineApplyReceivedAdapter(this, receivedList);
        lv_received.setAdapter(receivedAdapter);
        lv_approval = (MyListView) findViewById(R.id.machine_apply_approval_lv);
        approvalAdapter = new MachineApplyApprovalAdapter(this, approvalList, receivedList, receivedAdapter);
        lv_approval.setAdapter(approvalAdapter);
        lv_returnCreated = (MyListView) findViewById(R.id.machine_apply_return_lv);
        returnCreatedAdapter = new MachineApplyReturnCreatedAdapter(this, returnCreatedList);
        lv_returnCreated.setAdapter(returnCreatedAdapter);
        lv_returnApplying = (MyListView) findViewById(R.id.machine_apply_return_applying_lv);
        returnApplyingAdapter = new MachineApplyReturnApplyingAdapter(this, returnApplyingList);
        lv_returnApplying.setAdapter(returnApplyingAdapter);
    }

    private void setListeners() {
        btn_applyEdit.setOnClickListener(this);
        tv_useTime.setOnClickListener(this);
        tv_returnTime.setOnClickListener(this);
        btn_apply.setOnClickListener(this);
        btn_returnEdit.setOnClickListener(this);
        tv_returnReTime.setOnClickListener(this);
        btn_return.setOnClickListener(this);

        lv_applying.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String applyId = applyingList.get(position).getApplyId();
                String state = applyingList.get(position).getState();

                if (applyId.equals("申请单00X") && state.equals("申请中")) {
                    applyingList.get(position).setState("不批准");
                    applyingAdapter.notifyDataSetChanged();
                }

                if (applyId.equals("申请单00X") && state.equals("不批准")) {
                    Intent intent = new Intent(MachineApplyActivity.this, MachineApplyingActivity.class);
                    intent.putExtra("machineNo", (Parcelable) applyingList.get(position));
                    intent.putExtra("position", position);
                    intent.putExtra("userNo", userNo);
                    intent.putExtra("orderId", orderId);
                    startActivityForResult(intent, 20);
                }

                if (applyId.equals("重新申请单00Y") && state.equals("申请中")) {
                    List<MachineData> machineDataList = DataSupport.where("applyId = ?", applyId).find(MachineData.class);
                    for (MachineData data : machineDataList) {
                        Machine machine = new Machine();
                        machine.setDataId(data.getId());
                        machine.setId(data.getNo());
                        machine.setName(data.getName());
                        machine.setSpec(data.getSpec());
                        machine.setUnit(data.getUnit());
                        machine.setNum(data.getNum());
                        machine.setState("已审核");
                        approvalList.add(machine);
                    }
                    ContentValues values = new ContentValues();
                    values.put("state", "已审核");
                    DataSupport.updateAll(MachineData.class, values, "applyId = ?", applyId);
                    DataSupport.deleteAll(MachineNoData.class, "applyId = ?", applyId);
                    applyingList.remove(position);
                    applyingAdapter.notifyDataSetChanged();
                    approvalAdapter.notifyDataSetChanged();
                }
            }
        });

        lv_approval.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String state = approvalList.get(position).getState();
                if (state.equals("已审核")) {
                    ContentValues values = new ContentValues();
                    values.put("state", "可领用");
                    DataSupport.update(MachineData.class, values, approvalList.get(position).getDataId());
                    approvalList.get(position).setState("可领用");
                    approvalAdapter.notifyDataSetChanged();
                }

                if (state.equals("可领用")) {
                    ContentValues values = new ContentValues();
                    values.put("state", "已领用");
                    values.put("returnType", "正常");
                    DataSupport.update(MachineData.class, values, approvalList.get(position).getDataId());
                    approvalList.get(position).setReturnType("正常");
                    receivedList.add(approvalList.get(position));
                    receivedAdapter.notifyDataSetChanged();
                    approvalList.remove(position);
                    approvalAdapter.notifyDataSetChanged();
                }
            }
        });

        lv_returnApplying.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String state = returnApplyingList.get(position).getState();
                if (state.equals("退回申请中")) {
                    ContentValues values = new ContentValues();
                    values.put("state", "退回已批准");
                    DataSupport.update(MachineData.class, values, returnApplyingList.get(position).getDataId());
                    returnApplyingList.get(position).setState("退回已批准");
                    returnApplyingAdapter.notifyDataSetChanged();
                }
                if (state.equals("退回已批准")) {
                    DataSupport.delete(MachineData.class, returnApplyingList.get(position).getDataId());
                    returnApplyingList.remove(position);
                    returnApplyingAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.machine_apply_apply_edit_btn:
                Intent intent1 = new Intent(this, MachineApplyEditActivity.class);
                intent1.putParcelableArrayListExtra("machineList", createdList);
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
                intent2.putExtra("machineList", (Serializable) receivedList);
                startActivityForResult(intent2, 30);
                break;

            case R.id.machine_apply_return_return_time_tv:
                getTime(tv_returnReTime);
                break;

            case R.id.machine_apply_return_btn:
                returnApply();
                break;
        }
    }

    private void apply() {
        String useTime = tv_useTime.getText().toString();
        String returnTime = tv_returnTime.getText().toString();
        String useAddress = et_useAddress.getText().toString().trim();
        if (useTime.equals("")) {
            if (mToast == null) {
                mToast = Toast.makeText(this, "请选择领用时间", Toast.LENGTH_SHORT);
            } else {
                mToast.setText("请选择领用时间");
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
            return;
        }
        if (returnTime.equals("")) {
            if (mToast == null) {
                mToast = Toast.makeText(this, "请选择退回时间", Toast.LENGTH_SHORT);
            } else {
                mToast.setText("请选择退回时间");
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
            return;
        }
        if (useAddress.equals("")) {
            if (mToast == null) {
                mToast = Toast.makeText(this, "请填写使用地点", Toast.LENGTH_SHORT);
            } else {
                mToast.setText("请填写使用地点");
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
            return;
        }

        String remarks = et_applyRemarks.getText().toString().trim();
        String applyId = "申请单00X";
        Calendar rightNow = Calendar.getInstance();
        String applyTime = dateFormat.format(rightNow.getTime());

        BusinessData businessData = DataSupport.where("user = ? and orderId = ?", userNo, orderId).find(BusinessData.class, true).get(0);
        for (int i = 0; i < createdList.size(); i++) {
            MachineData data = new MachineData();
            data.setNo(createdList.get(i).getId());
            data.setApplyId(applyId);
            data.setName(createdList.get(i).getName());
            data.setSpec(createdList.get(i).getSpec());
            data.setUnit(createdList.get(i).getUnit());
            data.setNum(createdList.get(i).getNum());
            data.setState("申请中");
            data.save();
            businessData.getMachineDataList().add(data);
        }
        MachineNoData data1 = new MachineNoData();
        data1.setApplyId(applyId);
        data1.setApplyTime(applyTime);
        data1.setUseTime(useTime);
        data1.setReturnTime(returnTime);
        data1.setUseAddress(useAddress);
        data1.setRemarks(remarks);
        data1.setState("申请中");
        data1.save();
        businessData.getMachineNoList().add(data1);
        businessData.save();

        MachineNo machineNo = new MachineNo();
        machineNo.setApplyId(applyId);
        machineNo.setApplyTime(applyTime);
        machineNo.setUseTime(useTime);
        machineNo.setReturnTime(returnTime);
        machineNo.setUseAddress(useAddress);
        machineNo.setRemarks(remarks);
        machineNo.setState("申请中");
        applyingList.add(machineNo);

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

        for (Machine reList : returnCreatedList) {
            MachineData machineData = DataSupport.find(MachineData.class, reList.getDataId());
            int num = machineData.getNum() - reList.getNum();
            if (num == 0) {
                ContentValues values = new ContentValues();
                values.put("returnType", reList.getReturnType());
                values.put("state", "退回申请中");
                DataSupport.update(MachineData.class, values, reList.getDataId());
            } else {
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
                data.setState("退回申请中");
                data.save();
                BusinessData businessData = DataSupport.where("user = ? and orderId = ?", userNo, orderId).find(BusinessData.class, true).get(0);
                businessData.getMachineDataList().add(data);
                businessData.save();
            }
            reList.setState("退回申请中");
        }

        receivedList.clear();
        returnCreatedList.clear();
        returnApplyingList.clear();
        BusinessData businessData = DataSupport.where("user = ? and orderId = ?", userNo, orderId).find(BusinessData.class, true).get(0);
        List<MachineData> machineDatas = businessData.getMachineDataList();
        for (MachineData data : machineDatas) {
            if (data.getState().equals("已领用")) {
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

            if (data.getState().equals("退回申请中") || data.getState().equals("退回已批准")) {
                Machine machine = new Machine();
                machine.setDataId(data.getId());
                machine.setId(data.getNo());
                machine.setName(data.getName());
                machine.setSpec(data.getSpec());
                machine.setUnit(data.getUnit());
                machine.setNum(data.getNum());
                machine.setState(data.getState());
                machine.setReturnType(data.getReturnType());
                returnApplyingList.add(machine);
            }
        }
        receivedAdapter.notifyDataSetChanged();
        returnCreatedAdapter.notifyDataSetChanged();
        returnApplyingAdapter.notifyDataSetChanged();
        btn_return.setVisibility(View.GONE);
        tv_returnReTime.setText("");
        tv_returnReTime.setHint("未选择");
        et_returnAddress.setText("");
        et_returnRemarks.setText("");
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
                ArrayList<Machine> machines = data.getParcelableArrayListExtra("machineList");
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
                MachineNo machineNo = data.getParcelableExtra("machineNo");
                int position = data.getIntExtra("position", -1);
                applyingList.remove(position);
                applyingList.add(machineNo);
                applyingAdapter.notifyDataSetChanged();
            }
        }

        if (requestCode == 30) {
            if (resultCode == 30) {
                List<Machine> machines = (List<Machine>) data.getSerializableExtra("machineList");
                if (machines.size() > 0) {
                    btn_return.setVisibility(View.VISIBLE);
                } else {
                    btn_return.setVisibility(View.GONE);
                }
                returnCreatedList.clear();
                returnCreatedList.addAll(machines);
                returnCreatedAdapter.notifyDataSetChanged();
            }
        }
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
