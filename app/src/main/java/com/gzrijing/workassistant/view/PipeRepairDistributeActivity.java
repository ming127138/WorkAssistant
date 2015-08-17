package com.gzrijing.workassistant.view;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.gzrijing.workassistant.R;

public class PipeRepairDistributeActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_on;
    private Button btn_off;
    private FragmentManager fragmentManager;
    private PipeRepairDistributeOnFragment pipeRepairDistributeOnFragment;
    private PipeRepairDistributeOffFragment pipeRepairDistributeOffFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipe_repair_distribute);

        initViews();
        initData();
        setListeners();
    }

    private void initData() {
        fragmentManager = getSupportFragmentManager();
        setTabSelection(0);
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_on = (Button) findViewById(R.id.pipe_inspection_distribute_on_btn);
        btn_off = (Button) findViewById(R.id.pipe_inspection_distribute_off_btn);

    }

    private void setListeners() {
        btn_on.setOnClickListener(this);
        btn_off.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.pipe_inspection_distribute_on_btn:
            setTabSelection(0);
            break;

        case R.id.pipe_inspection_distribute_off_btn:
            setTabSelection(1);
            break;
        }
    }

    private void setTabSelection(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                btn_on.setBackgroundResource(R.drawable.btn_paging_left_on);
                btn_on.setTextColor(getResources().getColor(R.color.white));
                btn_off.setBackgroundResource(R.drawable.btn_paging_right_off);
                btn_off.setTextColor(getResources().getColor(R.color.blue));
                if (pipeRepairDistributeOnFragment == null) {
                    pipeRepairDistributeOnFragment = new PipeRepairDistributeOnFragment();
                    transaction.add(R.id.fragment_pipe_repair_distribute, pipeRepairDistributeOnFragment);
                } else {
                    transaction.show(pipeRepairDistributeOnFragment);
                }
                break;
            case 1:
                btn_on.setBackgroundResource(R.drawable.btn_paging_left_off);
                btn_on.setTextColor(getResources().getColor(R.color.blue));
                btn_off.setBackgroundResource(R.drawable.btn_paging_right_on);
                btn_off.setTextColor(getResources().getColor(R.color.white));
                if (pipeRepairDistributeOffFragment == null) {
                    pipeRepairDistributeOffFragment = new PipeRepairDistributeOffFragment();
                    transaction.add(R.id.fragment_pipe_repair_distribute, pipeRepairDistributeOffFragment);
                } else {
                    transaction.show(pipeRepairDistributeOffFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (pipeRepairDistributeOnFragment != null) {
            transaction.hide(pipeRepairDistributeOnFragment);
        }
        if (pipeRepairDistributeOffFragment != null) {
            transaction.hide(pipeRepairDistributeOffFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pipe_repair_distribute, menu);
        return true;
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
