package com.gzrijing.workassistant.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gzrijing.workassistant.R;

public class ReportProblemFragment extends Fragment implements View.OnClickListener{

    private String orderId;
    private View layoutView;
    private EditText et_describe;
    private Button btn_report;

    public ReportProblemFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_report_problem, container, false);

        initViews();
        setListeners();

        return layoutView;
    }

    private void initData() {
        orderId = getArguments().getString("orderId");
    }

    private void initViews() {
        et_describe = (EditText) layoutView.findViewById(R.id.fragment_report_problem_describe_et);
        btn_report = (Button) layoutView.findViewById(R.id.fragment_report_problem_report_btn);
    }

    private void setListeners() {

    }

    @Override
    public void onClick(View v) {
      switch (v.getId()) {
      case R.id.fragment_report_problem_report_btn:
          report();
          break;
      }
    }

    private void report() {

    }
}
