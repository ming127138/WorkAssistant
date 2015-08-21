package com.gzrijing.workassistant.view;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.gzrijing.workassistant.R;

public class PipeRepairOrderActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_on;
    private Button btn_off;
    private FragmentManager fragmentManager;
    private PipeRepairOrderOnFragment pipeRepairOrderOnFragment;
    private PipeRepairOrderOffFragment pipeRepairOrderOffFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipe_repair_order);

        initViews();
        initData();
        setListeners();
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_on = (Button) findViewById(R.id.pipe_repair_order_on_btn);
        btn_off = (Button) findViewById(R.id.pipe_repair_order_off_btn);
    }

    private void initData() {
        fragmentManager = getSupportFragmentManager();
        setTabSelection(0);
    }

    private void setListeners() {
        btn_on.setOnClickListener(this);
        btn_off.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pipe_repair_order_on_btn:
                setTabSelection(0);
                break;

            case R.id.pipe_repair_order_off_btn:
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
                if (pipeRepairOrderOnFragment == null) {
                    pipeRepairOrderOnFragment = new PipeRepairOrderOnFragment();
                    transaction.add(R.id.fragment_pipe_repair_order, pipeRepairOrderOnFragment);
                } else {
                    transaction.show(pipeRepairOrderOnFragment);
                }
                break;
            case 1:
                btn_on.setBackgroundResource(R.drawable.btn_paging_left_off);
                btn_on.setTextColor(getResources().getColor(R.color.blue));
                btn_off.setBackgroundResource(R.drawable.btn_paging_right_on);
                btn_off.setTextColor(getResources().getColor(R.color.white));
                if (pipeRepairOrderOffFragment == null) {
                    pipeRepairOrderOffFragment = new PipeRepairOrderOffFragment();
                    transaction.add(R.id.fragment_pipe_repair_order, pipeRepairOrderOffFragment);
                } else {
                    transaction.show(pipeRepairOrderOffFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (pipeRepairOrderOnFragment != null) {
            transaction.hide(pipeRepairOrderOnFragment);
        }
        if (pipeRepairOrderOffFragment != null) {
            transaction.hide(pipeRepairOrderOffFragment);
        }
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
