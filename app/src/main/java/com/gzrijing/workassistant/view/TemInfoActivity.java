package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.TemInfoAdapter;

import java.util.ArrayList;
import java.util.List;

public class TemInfoActivity extends AppCompatActivity {

    private String orderId;
    private ListView lv_noRead;
    private ListView lv_read;
    private List<String> noReadList = new ArrayList<String>();
    private List<String> readList = new ArrayList<String>();
    private TemInfoAdapter noReadAdapter;
    private TemInfoAdapter readAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tem_info);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");

        for (int i = 1; i < 5; i++) {
            if (i % 2 == 0) {
                noReadList.add("未读信息" + i);
            } else {
                readList.add("已读信息" + i);
            }
        }
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_noRead = (ListView) findViewById(R.id.tem_info_no_read_lv);
        noReadAdapter = new TemInfoAdapter(this, noReadList);
        lv_noRead.setAdapter(noReadAdapter);
        lv_read = (ListView) findViewById(R.id.tem_info_read_lv);
        readAdapter = new TemInfoAdapter(this, readList);
        lv_read.setAdapter(readAdapter);
    }

    private void setListeners() {
        lv_noRead.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TemInfoActivity.this, TemInfoContentActivity.class);
                intent.putExtra("content", noReadList.get(position));
                startActivity(intent);
            }
        });

        lv_read.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TemInfoActivity.this, TemInfoContentActivity.class);
                intent.putExtra("content", readList.get(position));
                startActivity(intent);
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
