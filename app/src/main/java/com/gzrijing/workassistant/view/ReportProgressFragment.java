package com.gzrijing.workassistant.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gzrijing.workassistant.R;

public class ReportProgressFragment extends Fragment implements View.OnClickListener{


    private View layoutView;
    private EditText et_content;
    private Button btn_report;

    public ReportProgressFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        layoutView = inflater.inflate(R.layout.fragment_report_progress, container, false);

        initViews();
        setListeners();

        return layoutView;
    }

    private void initViews() {
        et_content = (EditText) layoutView.findViewById(R.id.fragment_report_progress_content_et);
        btn_report = (Button) layoutView.findViewById(R.id.fragment_report_progress_report_btn);
    }

    private void setListeners() {
        btn_report.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.fragment_report_progress_report_btn:

            break;
        }
    }
}
