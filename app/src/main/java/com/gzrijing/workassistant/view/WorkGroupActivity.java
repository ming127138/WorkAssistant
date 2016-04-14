package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.SubordinateAdapter;
import com.gzrijing.workassistant.adapter.WorkGroupAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.Subordinate;
import com.gzrijing.workassistant.entity.WorkGroup;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.JsonParseUtils;
import com.gzrijing.workassistant.util.ToastUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorkGroupActivity extends BaseActivity {

    private TextView tv_selected;
    private ImageView iv_checkAll;
    private ListView lv_workGroup;
    public static boolean isCheck;
    private ArrayList<WorkGroup> workGroupList;
    private WorkGroupAdapter adapter;
    private HashMap<Integer, String> names = new HashMap<Integer, String>();
    private String userNo;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_group);

        initViews();
        initData();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        userNo = intent.getStringExtra("userNo");
        workGroupList = intent.getParcelableArrayListExtra("workGroup");
        if (workGroupList.size() == 0) {
            initWorkGroup();
        } else {
            for (int i = 0; i < workGroupList.size(); i++) {
                if (workGroupList.get(i).isCheck()) {
                    names.put(i, workGroupList.get(i).getGroupName());
                }
            }
            adapter = new WorkGroupAdapter(WorkGroupActivity.this, workGroupList, iv_checkAll, tv_selected, names);
            lv_workGroup.setAdapter(adapter);
        }
    }

    private void initWorkGroup() {
        String url = null;
        try {
            url = "?cmd=getworksit&userno=" + URLEncoder.encode(userNo, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpUtils.sendHttpGetRequest(url, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        List<WorkGroup> list = JsonParseUtils.getWorkGroup(response);
                        workGroupList.addAll(list);
                        adapter = new WorkGroupAdapter(WorkGroupActivity.this, workGroupList, iv_checkAll, tv_selected, names);
                        lv_workGroup.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(WorkGroupActivity.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                    }
                });

            }
        });
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_selected = (TextView) findViewById(R.id.work_group_selected_tv);
        iv_checkAll = (ImageView) findViewById(R.id.work_group_check_all_iv);
        lv_workGroup = (ListView) findViewById(R.id.work_group_lv);
    }

    private void setListeners() {
        iv_checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck) {
                    for (WorkGroup workGroup : workGroupList) {
                        workGroup.setCheck(false);
                    }
                    names.clear();
                    iv_checkAll.setImageResource(R.drawable.login_checkbox_off);
                } else {
                    for (int i = 0; i < workGroupList.size(); i++) {
                        workGroupList.get(i).setCheck(true);
                        names.put(i, workGroupList.get(i).getGroupName());
                    }
                    iv_checkAll.setImageResource(R.drawable.login_checkbox_on);
                }
                adapter.notifyDataSetChanged();
                isCheck = !isCheck;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_subordinate, menu);
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
            String executors = tv_selected.getText().toString().trim();
            Intent intent = getIntent();
            intent.putExtra("executors", executors);
            intent.putParcelableArrayListExtra("workGroup", workGroupList);
            setResult(10, intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
