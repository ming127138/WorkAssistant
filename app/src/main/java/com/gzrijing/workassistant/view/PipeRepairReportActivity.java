package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;

public class PipeRepairReportActivity extends AppCompatActivity implements View.OnClickListener {

    private String title;
    private LinearLayout ll_progress;
    private ImageView iv_progress;
    private LinearLayout ll_finish;
    private ImageView iv_finish;
    private LinearLayout ll_finishInfo;
    private EditText et_content;
    private boolean isCheck;
    private Button btn_add;
    private ListView lv_supplies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipe_repair_report);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");

    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ll_progress = (LinearLayout) findViewById(R.id.pipe_repair_report_progress_ll);
        iv_progress = (ImageView) findViewById(R.id.pipe_repair_report_progress_iv);
        ll_finish = (LinearLayout) findViewById(R.id.pipe_repair_report_finish_ll);
        iv_finish = (ImageView) findViewById(R.id.pipe_repair_report_finish_iv);
        ll_finishInfo = (LinearLayout) findViewById(R.id.pipe_repair_report_finish_info_ll);
        et_content = (EditText) findViewById(R.id.pipe_repair_report_content_et);
        btn_add = (Button) findViewById(R.id.pipe_repair_report_add_btn);
        lv_supplies = (ListView) findViewById(R.id.pipe_repair_report_supplies_lv);

    }

    private void setListeners() {
        ll_progress.setOnClickListener(this);
        ll_finish.setOnClickListener(this);
        btn_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pipe_repair_report_progress_ll:
                if (isCheck) {
                    iv_progress.setImageResource(R.drawable.spinner_item_check_on);
                    iv_finish.setImageResource(R.drawable.spinner_item_check_off);
                    ll_finishInfo.setVisibility(View.GONE);
                    et_content.setHint("填写进度情况");
                    isCheck = !isCheck;
                }
                break;

            case R.id.pipe_repair_report_finish_ll:
                if (!isCheck) {
                    iv_progress.setImageResource(R.drawable.spinner_item_check_off);
                    iv_finish.setImageResource(R.drawable.spinner_item_check_on);
                    ll_finishInfo.setVisibility(View.VISIBLE);
                    et_content.setHint("备注");
                    isCheck = !isCheck;
                }
                break;
            case R.id.pipe_repair_report_add_btn:
                Intent intent = new Intent(this, SuppliesActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pipe_repair_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.action_report) {
            Toast.makeText(this, "汇报成功", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
