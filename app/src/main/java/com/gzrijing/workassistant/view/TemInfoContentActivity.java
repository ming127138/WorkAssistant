package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.base.BaseActivity;

public class TemInfoContentActivity extends BaseActivity {

    private TextView tv_content;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tem_info_content);

        intData();
        initViews();
    }

    private void intData() {
        Intent intent = getIntent();
        content = intent.getStringExtra("content");
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_content = (TextView) findViewById(R.id.tem_info_content_tv);
        tv_content.setText(content);
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
