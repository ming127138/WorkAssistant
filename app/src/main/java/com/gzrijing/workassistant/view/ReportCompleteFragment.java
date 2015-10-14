package com.gzrijing.workassistant.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.ReportCompleteAdapter;
import com.gzrijing.workassistant.entity.InspectionParameter;
import com.gzrijing.workassistant.entity.ReportCompleteInfo;

import java.util.ArrayList;
import java.util.List;

public class ReportCompleteFragment extends Fragment implements View.OnClickListener {

    private View layoutView;
    private String orderId;
    private ListView lv_info;
    private List<ReportCompleteInfo> infos = new ArrayList<ReportCompleteInfo>();
    private ReportCompleteAdapter adapter;
    private LinearLayout ll_water;
    private ImageView iv_water;
    private LinearLayout ll_customer;
    private ImageView iv_customer;
    private Button btn_report;
    private boolean isCheck = false;

    public ReportCompleteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_report_complete, container, false);

        initViews();
        setListeners();
        return layoutView;
    }

    private void initData() {
        orderId = getArguments().getString("orderId");

        String[] key = {"排水口径：", "排水时间：", "施工日期：", "完工日期：", "验收日期：", "施工内容：", "土建项目：", "　　备注："};
        for (int i = 0; i < key.length; i++) {
            ReportCompleteInfo info = new ReportCompleteInfo();
            info.setKey(key[i]);
            infos.add(info);
        }
    }

    private void initViews() {
        ll_water = (LinearLayout) layoutView.findViewById(R.id.fragment_report_complete_water_ll);
        iv_water = (ImageView) layoutView.findViewById(R.id.fragment_report_complete_water_iv);
        ll_customer = (LinearLayout) layoutView.findViewById(R.id.fragment_report_complete_customer_ll);
        iv_customer = (ImageView) layoutView.findViewById(R.id.fragment_report_complete_customer_iv);
        btn_report = (Button) layoutView.findViewById(R.id.fragment_report_complete_report_btn);

        lv_info = (ListView) layoutView.findViewById(R.id.report_complete_info_lv);
        adapter = new ReportCompleteAdapter(getActivity(), infos);
        lv_info.setAdapter(adapter);
    }

    private void setListeners() {
        ll_water.setOnClickListener(this);
        ll_customer.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_report_complete_water_ll:
                if (isCheck) {
                    iv_water.setImageResource(R.drawable.spinner_item_check_on);
                    iv_customer.setImageResource(R.drawable.spinner_item_check_off);
                    isCheck = !isCheck;
                }
                break;

            case R.id.fragment_report_complete_customer_ll:
                if (!isCheck) {
                    iv_water.setImageResource(R.drawable.spinner_item_check_off);
                    iv_customer.setImageResource(R.drawable.spinner_item_check_on);
                    isCheck = !isCheck;
                }
                break;

            case R.id.fragment_report_complete_report_btn:
                report();
                break;
        }
    }

    private void report() {
        if (isCheck) {

        }
    }
}
