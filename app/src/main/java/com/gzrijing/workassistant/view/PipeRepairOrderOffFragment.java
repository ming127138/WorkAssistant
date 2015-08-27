package com.gzrijing.workassistant.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.PipeRepairOrderOffAdapter;
import com.gzrijing.workassistant.data.PipeRepairOrderData;
import com.gzrijing.workassistant.entity.PipeRepairOrderOff;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PipeRepairOrderOffFragment extends Fragment {

    private View layoutView;
    private ListView lv_off;
    private List<PipeRepairOrderOff> repairOffInfos;

    public PipeRepairOrderOffFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_pipe_repair_order_off, container, false);
        initViews();
        setListeners();
        return layoutView;
    }

    private void initData() {
        repairOffInfos = new ArrayList<PipeRepairOrderOff>();
        List<PipeRepairOrderData> datas = DataSupport.findAll(PipeRepairOrderData.class);
        for (PipeRepairOrderData data : datas) {
            if(!data.isState()){
                PipeRepairOrderOff info = new PipeRepairOrderOff();
                info.setId(data.getOrderId());
                info.setUrgent(data.isUrgent());
                info.setInfo1(data.getInfo1());
                info.setInfo2(data.getInfo2());
                info.setInfo3(data.getInfo3());
                info.setDeadline(data.getDeadline());
                info.setReceive(data.isReceive());
                info.setRemindTime(data.getRemindTime());
                repairOffInfos.add(info);
            }
        }

        List<PipeRepairOrderOff> infos = new ArrayList<PipeRepairOrderOff>();
        for (int i = 0; i < 3; i++) {
            PipeRepairOrderOff info = new PipeRepairOrderOff(
                    "工单ID" + i, true, "XXXX信息" + i, "XXXX信息" + i, "XXXX信息" + i, "2015-8-25 16:30", false, "00时30分");
            infos.add(info);
        }

        List<PipeRepairOrderOff> delInfos = new ArrayList<PipeRepairOrderOff>();
        for(PipeRepairOrderOff info : infos){
            for(PipeRepairOrderData data : datas){
                if(data.getOrderId().equals(info.getId())){
                    delInfos.add(info);
                }
            }
        }

        infos.removeAll(delInfos);

        for (PipeRepairOrderOff info : infos){
            repairOffInfos.add(info);
            PipeRepairOrderData data = new PipeRepairOrderData();
            data.setOrderId(info.getId());
            data.setUrgent(info.isUrgent());
            data.setInfo1(info.getInfo1());
            data.setInfo2(info.getInfo2());
            data.setInfo3(info.getInfo3());
            data.setDeadline(info.getDeadline());
            data.setReceive(info.isReceive());
            data.setRemindTime(info.getRemindTime());
            data.setState(false);
            boolean a = data.save();
            Log.e("aaaaaaaaaa", a+"");
        }
    }

    private void initViews() {
        lv_off = (ListView) layoutView.findViewById(R.id.fragment_pipe_repair_order_off_lv);
        PipeRepairOrderOffAdapter adapter = new PipeRepairOrderOffAdapter(
                layoutView.getContext(), repairOffInfos);
        lv_off.setAdapter(adapter);
    }

    private void setListeners() {

    }


}
