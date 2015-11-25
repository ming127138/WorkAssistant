package com.gzrijing.workassistant.view;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.gzrijing.workassistant.db.ImageData;
import com.gzrijing.workassistant.entity.PicUrl;
import com.gzrijing.workassistant.entity.Subordinate;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.JudgeDate;
import com.gzrijing.workassistant.util.ToastUtil;
import com.gzrijing.workassistant.widget.selectdate.ScreenInfo;
import com.gzrijing.workassistant.widget.selectdate.WheelMain;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

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
    private String deadline;
    private int position;
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
    private ArrayList<PicUrl> imageUrls; //这个工程的所有图片
    private DistributeGriViewAdapter adapter;

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
        deadline = intent.getStringExtra("deadline");
        position = intent.getIntExtra("position", -1);

        PicUrl picUrl = new PicUrl();
        picUrls.add(picUrl);

        imageUrls = initImageUrl();
    }

    private ArrayList<PicUrl> initImageUrl() {
        imageUrls = new ArrayList<PicUrl>();
        BusinessData businessData = DataSupport.where("user = ? and orderId = ?", userNo, orderId)
                .find(BusinessData.class, true).get(0);
        List<ImageData> imageDatas = businessData.getImageDataList();
        for (ImageData data : imageDatas) {
            PicUrl picUrl = new PicUrl();
            picUrl.setPicUrl(data.getUrl());
            imageUrls.add(picUrl);
        }

        return imageUrls;
    }


    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_remarks = (EditText) findViewById(R.id.distribute_remarks_et);
        tv_executor = (TextView) findViewById(R.id.distribute_executor_tv);
        ll_executor = (LinearLayout) findViewById(R.id.distribute_executor_ll);
        tv_deadline = (TextView) findViewById(R.id.distribute_deadline_tv);
        tv_deadline.setText(deadline);
        ll_deadline = (LinearLayout) findViewById(R.id.distribute_deadline_ll);

        gv_image = (GridView) findViewById(R.id.distribute_image_gv);
        adapter = new DistributeGriViewAdapter(this, picUrls, imageUrls);
        gv_image.setAdapter(adapter);
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
                        deadline = wheelMain.getTime();
                        tv_deadline.setText(deadline);
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
                List<PicUrl> imageUrlList = data.getParcelableArrayListExtra("picUrls");
                imageUrls.addAll(imageUrlList);
                for (PicUrl picUrl : imageUrls) {
                    if (picUrl.isCheck()) {
                        picUrls.add(picUrl);
                    }
                }
                PicUrl picUrl = new PicUrl();
                picUrls.add(picUrl);
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

        sb.delete(0, sb.toString().length());
        Log.e("sb", sb.toString());
        for (int i = 0; i < picUrls.size()-1; i++) {
            sb.append(picUrls.get(position).getPicUrl()+",");
        }
        String urls = sb.toString().substring(0, sb.toString().length() - 1);
        Log.e("urls", sb.toString());

        RequestBody requestBody = new FormEncodingBuilder()
                .add("cmd", "doappoint")
                .add("fileno", orderId)
                .add("installuserno", executors)
                .add("installcontent", et_remarks.getText().toString().trim())
                .add("estimatefinishdate", deadline)
                .add("picuri", urls)
                .build();
        HttpUtils.sendHttpPostRequest(requestBody, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.e("response", response);
                Message msg;
                if (response.equals("ok")) {
                    msg = handler.obtainMessage(0);
                } else {
                    msg = handler.obtainMessage(1);
                }
                handler.sendMessage(msg);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    ContentValues values = new ContentValues();
                    values.put("deadline", deadline);
                    values.put("state", "已派工");
                    DataSupport.updateAll(BusinessData.class, values, "user = ? and orderId = ?", userNo, orderId);
                    LeaderFragment.orderList.get(position).setState("已派工");
                    LeaderFragment.orderListByLeader.get(position).setState("已派工");
                    LeaderFragment.orderList.get(position).setDeadline(deadline);
                    LeaderFragment.orderListByLeader.get(position).setDeadline(deadline);
                    LeaderFragment.adapter.notifyDataSetChanged();
                    ToastUtil.showToast(DistributeActivity.this, "派工成功", Toast.LENGTH_SHORT);
                    finish();
                    break;

                case 1:
                    ToastUtil.showToast(DistributeActivity.this, "派工失败", Toast.LENGTH_SHORT);
                    break;
            }
        }
    };

}
