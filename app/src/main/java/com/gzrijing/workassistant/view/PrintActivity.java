package com.gzrijing.workassistant.view;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.DetailedInfoAdapter;
import com.gzrijing.workassistant.adapter.SuppliesApplyReceivedAdapter;
import com.gzrijing.workassistant.entity.DetailedInfo;
import com.gzrijing.workassistant.entity.ReportComplete;
import com.gzrijing.workassistant.entity.Supplies;
import com.zj.btsdk.BluetoothService;

import java.util.ArrayList;
import java.util.List;

public class PrintActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_connect;
    private ListView lv_info;
    private ListView lv_supplies;
    private List<DetailedInfo> infos = new ArrayList<DetailedInfo>();
    private DetailedInfoAdapter adapter;
    private List<Supplies> suppliesList = new ArrayList<Supplies>();
    private SuppliesApplyReceivedAdapter receivedAdapter;
    BluetoothService mService = null;
    BluetoothDevice con_dev = null;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_CONNECT_DEVICE = 1;  //获取设备消息
    private boolean isConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        mService = new BluetoothService(this, mHandler);
        //蓝牙不可用退出程序
        if (mService.isAvailable() == false) {
            Toast.makeText(this, "蓝牙不可用", Toast.LENGTH_LONG).show();
            finish();
        }

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        List<ReportComplete> datas = intent.getParcelableArrayListExtra("infos");
        boolean isCheck = intent.getBooleanExtra("isCheck", false);
        DetailedInfo info = new DetailedInfo();
        info.setKey("收款性质");
        if (isCheck) {
            info.setValue("用户");
        } else {
            info.setValue("水务");
        }
        infos.add(info);
        for (ReportComplete data : datas) {
            DetailedInfo info1 = new DetailedInfo();
            info1.setKey(data.getKey());
            info1.setValue(data.getValue());
            infos.add(info1);
        }
        infos.remove(infos.size() - 1);
        infos.remove(infos.size() - 1);
        for (int i = 1; i < 5; i++) {
            Supplies supplies = new Supplies();
            supplies.setName("名称" + i);
            supplies.setSpec("规格" + i);
            supplies.setUnit("单位" + i);
            supplies.setNum(i);
            suppliesList.add(supplies);
        }
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_connect = (Button) findViewById(R.id.print_connect_btn);

        lv_info = (ListView) findViewById(R.id.print_info_lv);
        adapter = new DetailedInfoAdapter(this, infos);
        lv_info.setAdapter(adapter);
        lv_supplies = (ListView) findViewById(R.id.print_supplies_lv);
        receivedAdapter = new SuppliesApplyReceivedAdapter(this, suppliesList);
        lv_supplies.setAdapter(receivedAdapter);
    }

    private void setListeners() {
        btn_connect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.print_connect_btn:
                Intent serverIntent = new Intent(this, DeviceListActivity.class);    //运行另外一个类的活动
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //蓝牙未打开，打开蓝牙
        if (mService.isBTopen() == false) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mService != null)
            mService.stop();
        mService = null;
    }


    /**
     * 创建一个Handler实例，用于接收BluetoothService类返回回来的消息
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothService.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:   //已连接
                            Toast.makeText(getApplicationContext(), "连接成功",
                                    Toast.LENGTH_SHORT).show();
                            isConnect = true;
                            btn_connect.setText("已连接蓝牙打印机");
                            break;
                        case BluetoothService.STATE_CONNECTING:  //正在连接
                            Log.d("蓝牙调试", "正在连接.....");
                            break;
                        case BluetoothService.STATE_LISTEN:     //监听连接的到来
                        case BluetoothService.STATE_NONE:
                            Log.d("蓝牙调试", "等待连接.....");
                            break;
                    }
                    break;
                case BluetoothService.MESSAGE_CONNECTION_LOST:    //蓝牙已断开连接
                    Toast.makeText(getApplicationContext(), "设备连接丢失",
                            Toast.LENGTH_SHORT).show();
                    isConnect = false;
                    btn_connect.setText("未连接蓝牙打印机");
                    break;
                case BluetoothService.MESSAGE_UNABLE_CONNECT:     //无法连接设备
                    Toast.makeText(getApplicationContext(), "无法连接设备",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:      //请求打开蓝牙
                if (resultCode == Activity.RESULT_OK) {   //蓝牙已经打开
                    Toast.makeText(this, "蓝牙打开成功", Toast.LENGTH_LONG).show();
                } else {                 //用户不允许打开蓝牙
                    finish();
                }
                break;
            case REQUEST_CONNECT_DEVICE:     //请求连接某一蓝牙设备
                if (resultCode == Activity.RESULT_OK) {   //已点击搜索列表中的某个设备项
                    String address = data.getExtras()
                            .getString(com.gzrijing.workassistant.view.DeviceListActivity.EXTRA_DEVICE_ADDRESS);  //获取列表项中设备的mac地址
                    con_dev = mService.getDevByMac(address);

                    mService.connect(con_dev);
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_print, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.action_print) {
            if (isConnect) {
                mService.sendMessage("中山市润丰水业有限公司工程施工作业单\n", "GBK");
                StringBuilder sb = new StringBuilder();
                for(DetailedInfo info : infos){
                    sb.append(info.getKey()+"："+info.getValue()+"\n");
                }
                sb.append("\n耗材列表：\n");
                sb.append("　　名称　　　规格　　　单位　　　数量\n");
                sb.append("————————————————————————\n");
                for(Supplies supplies : suppliesList){
                    sb.append(" 　"+supplies.getName()+"　　"+supplies.getSpec()+"　　"+supplies.getUnit()
                                    +"　　"+supplies.getNum()+"\n");
                }
                sb.append("\n　　用户签字:_________　　日期:_________");
                sb.append("\n\n\n");
                String msg = sb.toString();
                mService.sendMessage(msg, "GBK");
            } else {
                Toast.makeText(getApplicationContext(), "设备连接丢失,请重新连接",
                        Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
