package com.gzrijing.workassistant.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.PipeRepairOrderOnAdapter;
import com.gzrijing.workassistant.data.PipeRepairOrderData;
import com.gzrijing.workassistant.entity.PipeRepairOrderOn;

import org.litepal.crud.DataSupport;

import java.nio.channels.Pipe;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PipeRepairOrderOnFragment extends Fragment {

    private View layoutView;
    private ListView lv_on;
    public static ArrayList<PipeRepairOrderOn> repairOnInfos;
    public static PipeRepairOrderOnAdapter adapter;

    public PipeRepairOrderOnFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_pipe_repair_order_on, container, false);
        initViews();
        setListeners();
        return layoutView;
    }

    private void initData() {
        repairOnInfos = new ArrayList<PipeRepairOrderOn>();
        List<PipeRepairOrderData> datas = DataSupport.where("state = ?", "1").find(PipeRepairOrderData.class);
        for (PipeRepairOrderData data : datas) {
            PipeRepairOrderOn info = new PipeRepairOrderOn();
            info.setId(data.getOrderId());
            info.setUrgent(data.isUrgent());
            info.setInfo1(data.getInfo1());
            info.setInfo2(data.getInfo2());
            info.setInfo3(data.getInfo3());
            info.setDeadline(data.getDeadline());
            repairOnInfos.add(info);
        }

    }

    private void initViews() {
        lv_on = (ListView) layoutView.findViewById(R.id.fragment_pipe_repair_order_on_lv);
        adapter = new PipeRepairOrderOnAdapter(
                layoutView.getContext(), repairOnInfos);
        lv_on.setAdapter(adapter);
    }

    private void setListeners() {

    }


}
