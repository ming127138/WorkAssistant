package com.gzrijing.workassistant.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.gzrijing.workassistant.entity.PicUrl;
import com.gzrijing.workassistant.entity.Subordinate;
import com.gzrijing.workassistant.entity.WorkGroup;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.service.DownLoadAllImageService;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.JsonParseUtils;
import com.gzrijing.workassistant.util.JudgeDate;
import com.gzrijing.workassistant.util.ToastUtil;
import com.gzrijing.workassistant.widget.selectdate.ScreenInfo;
import com.gzrijing.workassistant.widget.selectdate.WheelMain;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.RequestBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DistributeByDirectorActivity extends BaseActivity implements View.OnClickListener {

    private String userNo;
    private String orderId;
    private TextView tv_executor;
    private LinearLayout ll_executor;
    private TextView tv_deadline;
    private LinearLayout ll_deadline;
    private ArrayList<WorkGroup> workGroupList = new ArrayList<WorkGroup>();
    private WheelMain wheelMain;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private DistributeGriViewAdapter adapter;
    private Handler handler = new Handler();
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribute_by_director);

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

    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_executor = (TextView) findViewById(R.id.distribute_by_director_executor_tv);
        ll_executor = (LinearLayout) findViewById(R.id.distribute_by_director_executor_ll);
        tv_deadline = (TextView) findViewById(R.id.distribute_by_director_deadline_tv);
        ll_deadline = (LinearLayout) findViewById(R.id.distribute_by_director_deadline_ll);
    }

    private void setListeners() {
        ll_executor.setOnClickListener(this);
        ll_deadline.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.distribute_by_director_executor_ll:
                Intent intent = new Intent(this, WorkGroupActivity.class);
                intent.putExtra("userNo", userNo);
                intent.putParcelableArrayListExtra("workGroup", workGroupList);
                startActivityForResult(intent, 10);
                break;

            case R.id.distribute_by_director_deadline_ll:
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
                workGroupList = data.getParcelableArrayListExtra("workGroup");
                tv_executor.setText(executors);
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
        pDialog = new ProgressDialog(this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("正在派工...");
        pDialog.show();
        StringBuilder sb = new StringBuilder();
        for (WorkGroup workGroup : workGroupList) {
            if (workGroup.isCheck()) {
                sb.append(workGroup.getGroupNo() + ",");
            }
        }
        String workGroupNo = sb.toString().substring(0, sb.toString().length() - 1);

        final RequestBody requestBody = new FormEncodingBuilder()
                .add("cmd", "dosaveconsappointtask")
                .add("userno", userNo)
                .add("fileno", orderId)
                .add("worksitno", workGroupNo)
                .add("estimatefinishdate", tv_deadline.getText().toString())
                .build();
        HttpUtils.sendHttpPostRequest(requestBody, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                Log.e("response", response);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(response.equals("ok")){
                            saveInfo();
                        }else{
                            ToastUtil.showToast(DistributeByDirectorActivity.this, "派发失败", Toast.LENGTH_SHORT);
                        }
                        pDialog.dismiss();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(DistributeByDirectorActivity.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                        pDialog.dismiss();
                    }
                });
            }
        });
    }

    private void saveInfo() {
        ContentValues values = new ContentValues();
        values.put("state", "已派工");
        DataSupport.updateAll(BusinessData.class, values, "user = ? and orderId = ?", userNo, orderId);
        ToastUtil.showToast(DistributeByDirectorActivity.this, "派工成功", Toast.LENGTH_SHORT);

        Intent intent = new Intent("action.com.gzrijing.workassistant.LeaderFragment.Distribute");
        intent.putExtra("orderId", orderId);
        sendBroadcast(intent);
        finish();
    }
}
