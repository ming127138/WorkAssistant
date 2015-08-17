package com.gzrijing.workassistant.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.PipeRepairDistributeOnAdapter;
import com.gzrijing.workassistant.entity.PipeRepairDistributeOn;

import java.util.ArrayList;
import java.util.List;

public class PipeRepairDistributeOnFragment extends Fragment {

    private View layoutView;
    private ListView lv_on;
    private List<PipeRepairDistributeOn> repairOnInfos;

    public PipeRepairDistributeOnFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_pipe_repair_distribute_on, container, false);

        initData();
        initViews();
        setListeners();

        return layoutView;
    }

    private void initData() {
        repairOnInfos = new ArrayList<PipeRepairDistributeOn>();
        for (int i = 0; i < 3; i++) {
            PipeRepairDistributeOn repairOnInfo = new PipeRepairDistributeOn(
                    "工单ID"+i, true, "XXXX信息"+i, "XXXX信息"+i, "XXXX信息"+i);
            repairOnInfos.add(repairOnInfo);
        }
    }

    private void initViews() {
        lv_on = (ListView) layoutView.findViewById(R.id.fragment_pipe_repair_distribute_on_lv);
        PipeRepairDistributeOnAdapter adapter = new PipeRepairDistributeOnAdapter(
                layoutView.getContext(), repairOnInfos);
        lv_on.setAdapter(adapter);
    }

    private void setListeners() {

    }

}
