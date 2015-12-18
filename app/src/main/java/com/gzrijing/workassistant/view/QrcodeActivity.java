package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.base.BaseActivity;

public class QrcodeActivity extends BaseActivity {

    private ImageView iv_qrcode;
    private String billNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        initViews();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iv_qrcode = (ImageView) findViewById(R.id.qrcode_iv);
    }
}
