package com.gzrijing.workassistant.view;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.DistributeGriViewAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.db.BusinessData;
import com.gzrijing.workassistant.db.BusinessHaveSendData;
import com.gzrijing.workassistant.db.SuppliesNoData;
import com.gzrijing.workassistant.entity.PicUrl;
import com.gzrijing.workassistant.entity.Subordinate;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.service.DownLoadAllImageService;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.JsonParseUtils;
import com.gzrijing.workassistant.util.JudgeDate;
import com.gzrijing.workassistant.util.ToastUtil;
import com.gzrijing.workassistant.widget.selectdate.ScreenInfo;
import com.gzrijing.workassistant.widget.selectdate.WheelMain;
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

public class DistributeActivity extends BaseActivity implements View.OnClickListener {

    private String userNo;
    private String orderId;
    private EditText et_remarks;
    private TextView tv_executor;
    private LinearLayout ll_executor;
    private TextView tv_deadline;
    private LinearLayout ll_deadline;
    private ArrayList<Subordinate> subordinates = new ArrayList<Subordinate>();
    private WheelMain wheelMain;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private GridView gv_image;
    private ArrayList<PicUrl> picUrls = new ArrayList<PicUrl>(); //选中的图片
    private ArrayList<PicUrl> imageUrls = new ArrayList<PicUrl>(); //这个工程的所有图片
    private DistributeGriViewAdapter adapter;
    private BusinessData businessData;
    private Intent imageIntent;

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
                "saveUser", MODE_PRIVATE);
        userNo = app.getString("userNo", "");
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");

        PicUrl picUrl = new PicUrl();
        picUrls.add(picUrl);

        IntentFilter mIntentFilter = new IntentFilter("action.com.gzrijing.workassistant.Distribute");
        registerReceiver(mBroadcastReceiver, mIntentFilter);

        initImageUrl();

    }

    private void initImageUrl() {
        imageIntent = new Intent(this, DownLoadAllImageService.class);
        imageIntent.putExtra("orderId", orderId);
        startService(imageIntent);
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

        gv_image = (GridView) findViewById(R.id.distribute_image_gv);
    }

    private void setListeners() {
        ll_executor.setOnClickListener(this);
        ll_deadline.setOnClickListener(this);

        gv_image.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position + 1 == picUrls.size()) {
                    Intent intent = new Intent(DistributeActivity.this, ImageSelectorActivity.class);
                    intent.putParcelableArrayListExtra("picUrls", imageUrls);
                    startActivityForResult(intent, 20);
                } else {

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.distribute_executor_ll:
                Intent intent = new Intent(this, SubordinateActivity.class);
                intent.putExtra("orderId", orderId);
                intent.putParcelableArrayListExtra("subordinates", subordinates);
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
        if (requestCode == 10) {
            if (resultCode == 10) {
                String executors = data.getStringExtra("executors");
                subordinates = data.getParcelableArrayListExtra("subordinates");
                tv_executor.setText(executors);
            }
        }

        if (requestCode == 20) {
            if (resultCode == 20) {
                imageUrls.clear();
                picUrls.clear();
                ArrayList<PicUrl> imageUrlList = data.getParcelableArrayListExtra("picUrls");
                imageUrls.addAll(imageUrlList);
                for (PicUrl picUrl : imageUrls) {
                    if (picUrl.isCheck()) {
                        picUrls.add(picUrl);
                    }
                }
                PicUrl picUrl = new PicUrl();
                picUrls.add(picUrl);
                Log.e("pic", picUrls.size() + "");
                Log.e("image", imageUrls.size() + "");
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_distribute, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.action_distribute) {
            if (tv_executor.getText().toString().equals("")) {
                ToastUtil.showToast(this, "请选择施工员", Toast.LENGTH_SHORT);
                return true;
            }

            if (tv_deadline.getText().toString().equals("")) {
                ToastUtil.showToast(this, "请选择工程期限", Toast.LENGTH_SHORT);
                return true;
            }
            distribute();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void distribute() {
        StringBuilder sb = new StringBuilder();
        for (Subordinate sub : subordinates) {
            if (sub.isCheck()) {
                sb.append(sub.getUserNo() + ",");
            }
        }
        String executors = sb.toString().substring(0, sb.toString().length() - 1);
        Log.e("executors", executors);


        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < picUrls.size() - 1; i++) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("PicUri", picUrls.get(i).getPicUrl());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.e("json", jsonArray.toString());

        RequestBody requestBody = new FormEncodingBuilder()
                .add("cmd", "doappoint")
                .add("userno", userNo)
                .add("fileno", orderId)
                .add("installuserno", executors)
                .add("installcontent", et_remarks.getText().toString().trim())
                .add("estimatefinishdate", tv_deadline.getText().toString())
                .add("picuri", jsonArray.toString())
                .build();
        HttpUtils.sendHttpPostRequest(requestBody, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.e("response", response);
                Message msg;
                if (response.substring(0, 1).equals("E")) {
                    msg = handler.obtainMessage(1);
                } else {
                    msg = handler.obtainMessage(0);
                    msg.obj = response;
                }
                handler.sendMessage(msg);
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(DistributeActivity.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String id = (String) msg.obj;
                    saveInfo(id);
                    break;

                case 1:
                    ToastUtil.showToast(DistributeActivity.this, "派工失败", Toast.LENGTH_SHORT);
                    break;
            }
        }
    };

    private void saveInfo(String id) {
//        BusinessHaveSendData data = new BusinessHaveSendData();
//        data.setOrderId(id);
//        data.setContent(et_remarks.getText().toString().trim());
//        data.setState("未接受");
//        data.setExecutors(tv_executor.getText().toString());
//        data.setDeadline(tv_deadline.getText().toString());
//        data.save();
//        businessData = DataSupport.where("user = ? and orderId = ?", userNo, orderId)
//                .find(BusinessData.class, true).get(0);
//        List<BusinessHaveSendData> dataList = businessData.getBusinessHaveSendDataList();
//        dataList.add(data);
//        businessData.save();

        ContentValues values = new ContentValues();
        values.put("state", "已派工");
        DataSupport.updateAll(BusinessData.class, values, "user = ? and orderId = ?", userNo, orderId);
        ToastUtil.showToast(DistributeActivity.this, "派工成功", Toast.LENGTH_SHORT);

        Intent intent = new Intent("action.com.gzrijing.workassistant.LeaderFragment.Distribute");
        intent.putExtra("orderId", orderId);
        sendBroadcast(intent);
        finish();
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("action.com.gzrijing.workassistant.Distribute")) {
                String response = intent.getStringExtra("response");
                imageUrls = JsonParseUtils.getImageUrl(response);
                adapter = new DistributeGriViewAdapter(DistributeActivity.this, picUrls, imageUrls);
                gv_image.setAdapter(adapter);

            }
        }
    };

    @Override
    protected void onDestroy() {
        stopService(imageIntent);
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}
