package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.Subordinate;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.JsonParseUtils;
import com.gzrijing.workassistant.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubordinateActivity extends BaseActivity {

    private TextView tv_selected;
    private ImageView iv_checkAll;
    private ListView lv_subordinate;
    public static boolean isCheck;
    private ArrayList<Subordinate> subordinates;
    private SubordinateAdapter adapter;
    private HashMap<Integer, String> names = new HashMap<Integer, String>();
    private String orderId;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    List<Subordinate> subList = (ArrayList<Subordinate>) msg.obj;
                    subordinates.addAll(subList);
                    adapter = new SubordinateAdapter(SubordinateActivity.this, subordinates, iv_checkAll, tv_selected, names);
                    lv_subordinate.setAdapter(adapter);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subordinate);

        initViews();
        initData();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        subordinates = intent.getParcelableArrayListExtra("subordinates");
        if(subordinates.toString().equals("[]")){
            initSubordinates();
        }else{
            for(int i=0; i<subordinates.size(); i++){
                if(subordinates.get(i).isCheck()){
                    names.put(i, subordinates.get(i).getName());
                }
            }
            adapter = new SubordinateAdapter(SubordinateActivity.this, subordinates, iv_checkAll, tv_selected, names);
            lv_subordinate.setAdapter(adapter);
        }
    }

    private void initSubordinates() {
        String url = "?cmd=getsituser&fileno=" + orderId;
        HttpUtils.sendHttpGetRequest(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                List<Subordinate> subList = JsonParseUtils.getSubordinate(response);
                Message msg = handler.obtainMessage(0);
                msg.obj = subList;
                handler.sendMessage(msg);
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(SubordinateActivity.this, "与服务器断开连接", Toast.LENGTH_SHORT);
                    }
                });

            }
        });
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_selected = (TextView) findViewById(R.id.subordinate_selected_tv);
        iv_checkAll = (ImageView) findViewById(R.id.subordinate_check_all_iv);
        lv_subordinate = (ListView) findViewById(R.id.subordinate_lv);
    }

    private void setListeners() {
        iv_checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck) {
                    for (Subordinate subordinate : subordinates) {
                        subordinate.setIsCheck(false);
                    }
                    names.clear();
                    iv_checkAll.setImageResource(R.drawable.login_checkbox_off);
                } else {
                    for (int i = 0; i < subordinates.size(); i++) {
                        subordinates.get(i).setIsCheck(true);
                        names.put(i, subordinates.get(i).getName());
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
            intent.putParcelableArrayListExtra("subordinates", subordinates);
            setResult(10, intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
