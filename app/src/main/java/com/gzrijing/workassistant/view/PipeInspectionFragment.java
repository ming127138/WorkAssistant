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

public class PipeInspectionFragment extends Fragment implements View.OnClickListener {

    private View PipeInspectionLayout;
    private RelativeLayout rl_item1;
    private TextView tv_num1;
    private RelativeLayout rl_item2;
    private TextView tv_num2;
    private RelativeLayout rl_item3;
    private TextView tv_num3;
    private RelativeLayout rl_item4;
    private TextView tv_num4;
    private RelativeLayout rl_item5;
    private TextView tv_num5;
    private RelativeLayout rl_item6;
    private TextView tv_num6;
    private RelativeLayout rl_item7;
    private TextView tv_num7;
    private RelativeLayout rl_item8;
    private TextView tv_num8;
    private RelativeLayout rl_item9;
    private TextView tv_num9;
    private RelativeLayout rl_item10;
    private TextView tv_num10;
    private RelativeLayout rl_item11;
    private TextView tv_num11;
    private RelativeLayout rl_item12;
    private TextView tv_num12;

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

    }

    private void initViews() {
        rl_item1 = (RelativeLayout) PipeInspectionLayout.findViewById(R.id.pipe_inspection_item1_rl);
        tv_num1 = (TextView) PipeInspectionLayout.findViewById(R.id.pipe_inspection_num1_tv);
        rl_item2 = (RelativeLayout) PipeInspectionLayout.findViewById(R.id.pipe_inspection_item2_rl);
        tv_num2 = (TextView) PipeInspectionLayout.findViewById(R.id.pipe_inspection_num2_tv);
        rl_item3 = (RelativeLayout) PipeInspectionLayout.findViewById(R.id.pipe_inspection_item3_rl);
        tv_num3 = (TextView) PipeInspectionLayout.findViewById(R.id.pipe_inspection_num3_tv);
        rl_item4 = (RelativeLayout) PipeInspectionLayout.findViewById(R.id.pipe_inspection_item4_rl);
        tv_num4 = (TextView) PipeInspectionLayout.findViewById(R.id.pipe_inspection_num4_tv);
        rl_item5 = (RelativeLayout) PipeInspectionLayout.findViewById(R.id.pipe_inspection_item5_rl);
        tv_num5 = (TextView) PipeInspectionLayout.findViewById(R.id.pipe_inspection_num5_tv);
        rl_item6 = (RelativeLayout) PipeInspectionLayout.findViewById(R.id.pipe_inspection_item6_rl);
        tv_num6 = (TextView) PipeInspectionLayout.findViewById(R.id.pipe_inspection_num6_tv);
        rl_item7 = (RelativeLayout) PipeInspectionLayout.findViewById(R.id.pipe_inspection_item7_rl);
        tv_num7 = (TextView) PipeInspectionLayout.findViewById(R.id.pipe_inspection_num7_tv);
        rl_item8 = (RelativeLayout) PipeInspectionLayout.findViewById(R.id.pipe_inspection_item8_rl);
        tv_num8 = (TextView) PipeInspectionLayout.findViewById(R.id.pipe_inspection_num8_tv);
        rl_item9 = (RelativeLayout) PipeInspectionLayout.findViewById(R.id.pipe_inspection_item9_rl);
        tv_num9 = (TextView) PipeInspectionLayout.findViewById(R.id.pipe_inspection_num9_tv);
        rl_item10 = (RelativeLayout) PipeInspectionLayout.findViewById(R.id.pipe_inspection_item10_rl);
        tv_num10 = (TextView) PipeInspectionLayout.findViewById(R.id.pipe_inspection_num10_tv);
        rl_item11 = (RelativeLayout) PipeInspectionLayout.findViewById(R.id.pipe_inspection_item11_rl);
        tv_num11 = (TextView) PipeInspectionLayout.findViewById(R.id.pipe_inspection_num11_tv);
        rl_item12 = (RelativeLayout) PipeInspectionLayout.findViewById(R.id.pipe_inspection_item12_rl);
        tv_num12 = (TextView) PipeInspectionLayout.findViewById(R.id.pipe_inspection_num12_tv);
    }

    private void setListeners() {
        rl_item1.setOnClickListener(this);
        rl_item2.setOnClickListener(this);
        rl_item3.setOnClickListener(this);
        rl_item4.setOnClickListener(this);
        rl_item5.setOnClickListener(this);
        rl_item6.setOnClickListener(this);
        rl_item7.setOnClickListener(this);
        rl_item8.setOnClickListener(this);
        rl_item9.setOnClickListener(this);
        rl_item10.setOnClickListener(this);
        rl_item11.setOnClickListener(this);
        rl_item12.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pipe_inspection_item1_rl:
                Intent intent1 = new Intent(getActivity(), PipeInspectionMapActivity.class);
                intent1.putExtra("title", "一月计划");
                startActivity(intent1);
                break;
            case R.id.pipe_inspection_item2_rl:
                Intent intent2 = new Intent(getActivity(), PipeInspectionMapActivity.class);
                intent2.putExtra("title", "二月计划");
                startActivity(intent2);
                break;
            case R.id.pipe_inspection_item3_rl:
                Intent intent3 = new Intent(getActivity(), PipeInspectionMapActivity.class);
                intent3.putExtra("title", "三月计划");
                startActivity(intent3);
                break;
            case R.id.pipe_inspection_item4_rl:
                Intent intent4 = new Intent(getActivity(), PipeInspectionMapActivity.class);
                intent4.putExtra("title", "四月计划");
                startActivity(intent4);
                break;
            case R.id.pipe_inspection_item5_rl:
                Intent intent5 = new Intent(getActivity(), PipeInspectionMapActivity.class);
                intent5.putExtra("title", "五月计划");
                startActivity(intent5);
                break;
            case R.id.pipe_inspection_item6_rl:
                Intent intent6 = new Intent(getActivity(), PipeInspectionMapActivity.class);
                intent6.putExtra("title", "六月计划");
                startActivity(intent6);
                break;
            case R.id.pipe_inspection_item7_rl:
                Intent intent7 = new Intent(getActivity(), PipeInspectionMapActivity.class);
                intent7.putExtra("title", "七月计划");
                startActivity(intent7);
                break;
            case R.id.pipe_inspection_item8_rl:
                Intent intent8 = new Intent(getActivity(), PipeInspectionMapActivity.class);
                intent8.putExtra("title", "八月计划");
                startActivity(intent8);
                break;
            case R.id.pipe_inspection_item9_rl:
                Intent intent9 = new Intent(getActivity(), PipeInspectionMapActivity.class);
                intent9.putExtra("title", "九月计划");
                startActivity(intent9);
                break;
            case R.id.pipe_inspection_item10_rl:
                Intent intent10 = new Intent(getActivity(), PipeInspectionMapActivity.class);
                intent10.putExtra("title", "十月计划");
                startActivity(intent10);
                break;
            case R.id.pipe_inspection_item11_rl:
                Intent intent11 = new Intent(getActivity(), PipeInspectionMapActivity.class);
                intent11.putExtra("title", "十一月计划");
                startActivity(intent11);
                break;
            case R.id.pipe_inspection_item12_rl:
                Intent intent12 = new Intent(getActivity(), PipeInspectionMapActivity.class);
                intent12.putExtra("title", "十二月计划");
                startActivity(intent12);
                break;
        }
    }
}
