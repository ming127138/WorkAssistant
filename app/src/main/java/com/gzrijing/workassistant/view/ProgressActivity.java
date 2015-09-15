package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.ProgressAdapter;
import com.gzrijing.workassistant.entity.Progress;

import java.util.ArrayList;
import java.util.List;

public class ProgressActivity extends AppCompatActivity {

    private String orderId;
    private ListView lv_progress;
    private List<Progress> proInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");

        proInfos = new ArrayList<Progress>();
        for (int i = 15; i > 10; i--) {
            Progress proInfo = new Progress("2015-8-14"+"\n    "+i+":00",
                    "进度"+i);
            proInfos.add(proInfo);
        }

    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(orderId);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_progress = (ListView) findViewById(R.id.progress_lv);
        ProgressAdapter adapter = new ProgressAdapter(this, proInfos);
        lv_progress.setAdapter(adapter);

    }

    private void setListeners() {

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
