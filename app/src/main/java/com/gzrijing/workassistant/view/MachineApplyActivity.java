package com.gzrijing.workassistant.view;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Parcelable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.gzrijing.workassistant.adapter.MachineApplyReturnAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.db.BusinessData;
import com.gzrijing.workassistant.db.MachineData;
import com.gzrijing.workassistant.db.MachineNoData;
import com.gzrijing.workassistant.db.SuppliesNoData;
import com.gzrijing.workassistant.entity.Machine;
import com.gzrijing.workassistant.entity.MachineNo;
import com.gzrijing.workassistant.entity.SuppliesNo;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.JudgeDate;
import com.gzrijing.workassistant.util.ToastUtil;
import com.gzrijing.workassistant.widget.MyListView;
import com.gzrijing.workassistant.widget.selectdate.ScreenInfo;
import com.gzrijing.workassistant.widget.selectdate.WheelMain;
import com.gzrijing.workassistant.zxing.view.MipcaActivityCapture;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MachineApplyActivity extends BaseActivity implements View.OnClickListener {

    private final static int MACHINE_RECEIVED = 2001;
    private final static int MACHINE_RETURN = 2002;
    private String userNo;
    private String orderId;
    private Button btn_applyEdit;
    private TextView tv_useTime;
    private TextView tv_returnTime;
    private EditText et_useAddress;
    private EditText et_applyRemarks;
    private Button btn_apply;
    private Button btn_received;
    private Button btn_returnEdit;
    private Button btn_return;
    private MyListView lv_created;
    private MyListView lv_applying;
    private MyListView lv_received;
    private MyListView lv_approval;
    private MyListView lv_return;
    private ArrayList<Machine> createdList = new ArrayList<Machine>();
    private List<MachineNo> applyingList = new ArrayList<MachineNo>();
    private List<MachineNo> approvalList = new ArrayList<MachineNo>();
    private ArrayList<Machine> receivedList = new ArrayList<Machine>();
    private List<MachineNo> returnList = new ArrayList<MachineNo>();
    private MachineApplyCreatedAdapter createdAdapter;
    private MachineApplyApplyingAdapter applyingAdapter;
    private MachineApplyReceivedAdapter receivedAdapter;
    private MachineApplyApprovalAdapter approvalAdapter;
    private MachineApplyReturnAdapter returnAdapter;
    private WheelMain wheelMain;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private Handler handler = new Handler();
    private BusinessData businessData;

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

        businessData = DataSupport.where("user = ? and orderId = ?", userNo, orderId).find(BusinessData.class, true).get(0);
        List<MachineNoData> machineNoDataList = businessData.getMachineNoList();
        for(MachineNoData data : machineNoDataList){
            if(!data.getApplyId().equals("")){
                if(data.getApplyState().equals("申请中") || data.getApplyState().equals("不批准")){
                    MachineNo applying = new MachineNo();
                    applying.setApplyId(data.getApplyId());
                    applying.setApplyTime(data.getApplyTime());
                    applying.setApplyState(data.getApplyState());
                    applying.setUseTime(data.getUseTime());
                    applying.setReturnTime(data.getReturnTime());
                    applying.setUseAddress(data.getUseAddress());
                    applying.setRemarks(data.getRemarks());
                    applyingList.add(applying);
                }

                if(data.getApplyState().equals("已审批")){
                    MachineNo approval = new MachineNo();
                    approval.setApplyId(data.getApplyId());
                    approval.setApprovalTime(data.getApprovalTime());
                    approval.setApplyState(data.getApplyState());
                    approval.setUseTime(data.getUseTime());
                    approval.setReturnTime(data.getReturnTime());
                    approval.setUseAddress(data.getUseAddress());
                    approval.setRemarks(data.getRemarks());
                    approvalList.add(approval);
                }
            }else{
                if(!data.getReturnId().equals("")){
                    MachineNo returnNo = new MachineNo();
                    returnNo.setReturnId(data.getReturnId());
                    returnNo.setReturnApplyTime(data.getReturnApplyTime());
                    returnNo.setReturnType(data.getReturnType());
                    returnNo.setReturnState(data.getReturnState());
                    returnNo.setReturnTime(data.getReturnTime());
                    returnNo.setReturnAddress(data.getReturnAddress());
                    returnNo.setRemarks(data.getRemarks());
                    returnList.add(returnNo);
                }
            }
        }

        List<MachineData> machineDataList = businessData.getMachineDataList();
        for(MachineData machineData : machineDataList){
            if(machineData.getReceivedState().equals("已安排") || machineData.getReceivedState().equals("已领出")){
                Machine machine = new Machine();
                machine.setId(machineData.getNo());
                machine.setName(machineData.getName());
                machine.setUnit(machineData.getUnit());
                machine.setNum(machineData.getNum());
                machine.setState(machineData.getReceivedState());
                receivedList.add(machine);
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

        btn_received = (Button) findViewById(R.id.machine_apply_received_btn);

        btn_returnEdit = (Button) findViewById(R.id.machine_apply_return_edit_btn);
        btn_return = (Button) findViewById(R.id.machine_apply_return_btn);

        lv_created = (MyListView) findViewById(R.id.machine_apply_created_lv);
        createdAdapter = new MachineApplyCreatedAdapter(this, createdList);
        lv_created.setAdapter(createdAdapter);

        lv_applying = (MyListView) findViewById(R.id.machine_apply_applying_lv);
        applyingAdapter = new MachineApplyApplyingAdapter(this, applyingList);
        lv_applying.setAdapter(applyingAdapter);

        lv_approval = (MyListView) findViewById(R.id.machine_apply_approval_lv);
        approvalAdapter = new MachineApplyApprovalAdapter(this, approvalList);
        lv_approval.setAdapter(approvalAdapter);

        lv_received = (MyListView) findViewById(R.id.machine_apply_received_lv);
        receivedAdapter = new MachineApplyReceivedAdapter(this, receivedList);
        lv_received.setAdapter(receivedAdapter);

        lv_return = (MyListView) findViewById(R.id.machine_apply_return_lv);
        returnAdapter = new MachineApplyReturnAdapter(this, returnList);
        lv_return.setAdapter(returnAdapter);

    }

    private void setListeners() {
        btn_applyEdit.setOnClickListener(this);
        tv_useTime.setOnClickListener(this);
        tv_returnTime.setOnClickListener(this);
        btn_apply.setOnClickListener(this);
        btn_received.setOnClickListener(this);
        btn_returnEdit.setOnClickListener(this);
        btn_return.setOnClickListener(this);

        lv_applying.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String state = applyingList.get(position).getApplyState();
                if (state.equals("不批准")) {
                    Intent intent = new Intent(MachineApplyActivity.this, MachineApplyingActivity.class);
                    intent.putExtra("machineNo", (Parcelable) applyingList.get(position));
                    intent.putExtra("position", position);
                    intent.putExtra("userNo", userNo);
                    intent.putExtra("orderId", orderId);
                    startActivityForResult(intent, 20);
                }else{
                    Intent intent = new Intent(MachineApplyActivity.this, MachineApplyingScanActivity.class);
                    intent.putExtra("machineNo", (Parcelable) applyingList.get(position));
                    startActivity(intent);
                }
            }
        });

        lv_approval.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MachineApplyActivity.this, MachineApprovalActivity.class);
                intent.putExtra("machineNo", (Parcelable) approvalList.get(position));
                startActivity(intent);
            }
        });

        lv_return.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MachineApplyActivity.this, MachineReturnActivity.class);
                intent.putExtra("machineNo", (Parcelable) returnList.get(position));
                startActivity(intent);
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

            case R.id.machine_apply_received_btn:
                Intent intent2 = new Intent();
                intent2.setClass(this, MipcaActivityCapture.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent2, MACHINE_RECEIVED);
                break;

            case R.id.machine_apply_return_edit_btn:
                Intent intent3 = new Intent(this, MachineReturnEditActivity.class);
                intent3.putParcelableArrayListExtra("machineList", receivedList);
                startActivityForResult(intent3, 30);
                break;

            case R.id.machine_apply_return_btn:
                Intent intent4 = new Intent();
                intent4.setClass(this, MipcaActivityCapture.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent4, MACHINE_RETURN);
                break;
        }
    }

    private void apply() {
        String useTime = tv_useTime.getText().toString();
        String returnTime = tv_returnTime.getText().toString();
        String useAddress = et_useAddress.getText().toString().trim();
        String remarks = et_applyRemarks.getText().toString().trim();
        if (useTime.equals("")) {
            ToastUtil.showToast(MachineApplyActivity.this, "请选择领用时间", Toast.LENGTH_SHORT);
            return;
        }
        if (returnTime.equals("")) {
            ToastUtil.showToast(MachineApplyActivity.this, "请选择退回时间", Toast.LENGTH_SHORT);
            return;
        }
        if (useAddress.equals("")) {
            ToastUtil.showToast(MachineApplyActivity.this, "请填写使用地点", Toast.LENGTH_SHORT);
            return;
        }

        JSONArray jsonArray = new JSONArray();
        for (Machine machine : createdList) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", "");
                jsonObject.put("MachineName", machine.getName());
                jsonObject.put("Qty", machine.getNum());
                jsonObject.put("BeginDate", useTime);
                jsonObject.put("EstimateFinishDate", returnTime);
                jsonObject.put("Remark", remarks);
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.e("json", jsonArray.toString());
        RequestBody requestBody = new FormEncodingBuilder()
                .add("cmd", "dosavemachineneed")
                .add("billno", "")
                .add("billtype", "工程机械申请")
                .add("fileno", orderId)
                .add("proaddress", useAddress)
                .add("userno", userNo)
                .add("machinedetailjson", jsonArray.toString())
                .build();

        HttpUtils.sendHttpPostRequest(requestBody, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                Log.e("response", response);
                if (response.substring(0, 1).equals("E")) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(MachineApplyActivity.this, "申请失败", Toast.LENGTH_SHORT);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            savaMachineNo(response);
                        }
                    });

                }
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(MachineApplyActivity.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }

    private void savaMachineNo(String applyId) {
        Calendar rightNow = Calendar.getInstance();
        String applyTime = dateFormat.format(rightNow.getTime());

        for (int i = 0; i < createdList.size(); i++) {
            MachineData data = new MachineData();
            data.setApplyId(applyId);
            data.setNo(createdList.get(i).getId());
            data.setName(createdList.get(i).getName());
            data.setUnit(createdList.get(i).getUnit());
            data.setNum(createdList.get(i).getNum());
            data.save();
            businessData.getMachineDataList().add(data);
        }

        MachineNoData data1 = new MachineNoData();
        data1.setApplyId(applyId);
        data1.setApplyTime(applyTime);
        data1.setUseTime(tv_useTime.getText().toString());
        data1.setReturnTime(tv_returnTime.getText().toString());
        data1.setUseAddress(et_useAddress.getText().toString().trim());
        data1.setRemarks(et_applyRemarks.getText().toString().trim());
        data1.setApplyState("申请中");
        data1.save();
        businessData.getMachineNoList().add(data1);
        businessData.save();

        MachineNo machineNo = new MachineNo();
        machineNo.setApplyId(applyId);
        machineNo.setApplyTime(applyTime);
        machineNo.setUseTime(tv_useTime.getText().toString());
        machineNo.setReturnTime(tv_returnTime.getText().toString());
        machineNo.setUseAddress(et_useAddress.getText().toString().trim());
        machineNo.setRemarks(et_applyRemarks.getText().toString().trim());
        machineNo.setApplyState("申请中");
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
                MachineNo machineNo = (MachineNo)data.getParcelableExtra("machineNo");
                returnList.add(machineNo);
                returnAdapter.notifyDataSetChanged();
            }
        }

        if (requestCode == MACHINE_RECEIVED) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                // 显示扫描到的内容
                String id = bundle.getString("result");
                receivedConfirm(id);
            }
        }

        if (requestCode == MACHINE_RECEIVED) {
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                // 显示扫描到的内容
                String id = bundle.getString("result");
                returnConfirm(id);
            }
        }
    }

    private void receivedConfirm(final String id) {
        final String applyId = id.split(",")[0];
        final String machineNo = id.split(",")[1];
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("PicType", "WnW_MachineSend");
            jsonObject.put("FileNo", orderId);
            jsonObject.put("BillNo", "");
            jsonObject.put("MachineNo", machineNo);
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final RequestBody requestBody = new FormEncodingBuilder()
                .add("cmd", "doreceivematerialandmachine")
                .add("userno", userNo)
                .add("detail", jsonArray.toString())
                .build();

        HttpUtils.sendHttpPostRequest(requestBody, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (response.equals("ok")) {
                            ContentValues values = new ContentValues();
                            values.put("receivedState", "已领出");
                            DataSupport.updateAll(MachineData.class, values, "applyId = ? and No = ?", applyId, machineNo);
                            for (Machine machine : receivedList) {
                                if (machine.getId().equals(machineNo)) {
                                    machine.setState("已领出");
                                    receivedAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            ToastUtil.showToast(MachineApplyActivity.this, "领出失败", Toast.LENGTH_SHORT);
                        }
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(MachineApplyActivity.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }

    private void returnConfirm(final String id) {
        RequestBody requestBody = new FormEncodingBuilder()
                .add("cmd", "domachineback")
                .add("userno", userNo)
                .add("id", id)
                .build();
        HttpUtils.sendHttpPostRequest(requestBody, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (response.equals("ok")) {
                            ContentValues values = new ContentValues();
                            values.put("returnState", "已退回");
                            DataSupport.updateAll(MachineNoData.class, values, "returnId = ?", id);
                            for (MachineNo machineNo : returnList) {
                                if (machineNo.getReturnId().equals(id)) {
                                    machineNo.setReturnState("已退回");
                                    returnAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            ToastUtil.showToast(MachineApplyActivity.this, "退回失败", Toast.LENGTH_SHORT);
                        }
                    }
                });

            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(MachineApplyActivity.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                    }
                });
            }
        });

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
