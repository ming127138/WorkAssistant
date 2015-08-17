package com.gzrijing.workassistant.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.PipeRepairDistributeOffAdapter;
import com.gzrijing.workassistant.entity.PipeRepairDistributeOff;
import com.gzrijing.workassistant.entity.PipeRepairDistributeOn;

import java.util.ArrayList;
import java.util.List;

public class PipeRepairDistributeOffFragment extends Fragment {


    private View layoutView;
    private ListView lv_off;
    private List<PipeRepairDistributeOff> repairOffInfos;

    public PipeRepairDistributeOffFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_pipe_repair_distribute_off, container, false);

        initData();
        initViews();
        setListeners();

        return layoutView;
    }

    private void initData() {
        repairOffInfos = new ArrayList<PipeRepairDistributeOff>();
        PipeRepairDistributeOff repairOffInfo1 = new PipeRepairDistributeOff(
                "工单ID1", true, "XXXX信息1", "XXXX信息1", "XXXX信息1", "00时30分", false);
        PipeRepairDistributeOff repairOffInfo2 = new PipeRepairDistributeOff(
                "工单ID2", true, "XXXX信息2", "XXXX信息2", "XXXX信息2", "00时30分", true);
        repairOffInfos.add(repairOffInfo1);
        repairOffInfos.add(repairOffInfo2);
    }

    private void initViews() {
        lv_off = (ListView) layoutView.findViewById(R.id.fragment_pipe_repair_distribute_off_lv);
        PipeRepairDistributeOffAdapter adapter = new PipeRepairDistributeOffAdapter(
                layoutView.getContext(), repairOffInfos);
        lv_off.setAdapter(adapter);
    }

    private void setListeners() {

    }


}
