package com.gzrijing.workassistant.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gzrijing.workassistant.R;

public class PipeRepairFragment extends Fragment implements View.OnClickListener{

    private View PipeRepairLayout;
    private RelativeLayout rl_distribute;
    private TextView tv_disNum;
    private RelativeLayout rl_order;
    private TextView tv_ordNum;
    private RelativeLayout rl_complete;
    private TextView tv_comNum;

    public PipeRepairFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        PipeRepairLayout = inflater.inflate(R.layout.fragment_pipe_repair, container, false);

        initData();
        initViews();
        setListeners();

        return PipeRepairLayout;
    }

    private void initData() {

    }

    private void initViews() {
        rl_distribute = (RelativeLayout) PipeRepairLayout.findViewById(R.id.pipe_repair_distribute_item_rl);
        tv_disNum = (TextView) PipeRepairLayout.findViewById(R.id.pipe_repair_distribute_num_tv);
        rl_order = (RelativeLayout) PipeRepairLayout.findViewById(R.id.pipe_repair_order_item_rl);
        tv_ordNum = (TextView) PipeRepairLayout.findViewById(R.id.pipe_repair_order_num_tv);
        rl_complete = (RelativeLayout) PipeRepairLayout.findViewById(R.id.pipe_repair_complete_item_rl);
        tv_comNum = (TextView) PipeRepairLayout.findViewById(R.id.pipe_repair_complete_num_tv);
    }

    private void setListeners() {
        rl_distribute.setOnClickListener(this);
        rl_order.setOnClickListener(this);
        rl_complete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pipe_repair_distribute_item_rl:
                Intent intent1 = new Intent(PipeRepairLayout.getContext(), PipeRepairDistributeActivity.class);
                startActivity(intent1);
                break;

            case R.id.pipe_repair_order_item_rl:
                Intent intent2 = new Intent(PipeRepairLayout.getContext(), PipeRepairOrderActivity.class);
                startActivity(intent2);
                break;

            case R.id.pipe_repair_complete_item_rl:

                break;
        }
    }

}
