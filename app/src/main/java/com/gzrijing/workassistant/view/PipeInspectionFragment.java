package com.gzrijing.workassistant.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.PipeInspectionGridViewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PipeInspectionFragment extends Fragment {

    private View PipeInspectionLayout;
    private TextView tv_date;
    private GridView gv_item;
    private String[] months;
    private List<Integer> tNums;

    public PipeInspectionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        PipeInspectionLayout = inflater.inflate(R.layout.fragment_pipe_inspection, container, false);

        initData();
        initViews();
        setListeners();

        return PipeInspectionLayout;
    }

    private void initData() {
        months = new String[]{"一月份", "二月份", "三月份", "四月份", "五月份", "六月份", "七月份",
                "八月份", "九月份", "十月份", "十一月份", "十二月份"};
        tNums = new ArrayList<Integer>();
        for (int i = 0; i < 12; i++) {
            tNums.add(60);
        }
    }

    private void initViews() {
        tv_date = (TextView) PipeInspectionLayout.findViewById(R.id.pipe_inspection_date_tv);
        Calendar rightNow = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        tv_date.setText(fmt.format(rightNow.getTime()));
        gv_item = (GridView) PipeInspectionLayout.findViewById(R.id.pipe_inspection_gv);
        PipeInspectionGridViewAdapter adapter = new PipeInspectionGridViewAdapter(
                PipeInspectionLayout.getContext(), tNums, months);
        gv_item.setAdapter(adapter);
    }

    private void setListeners() {

    }

}
