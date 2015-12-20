package com.gzrijing.workassistant.view;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.SuppliesAdapter;
import com.gzrijing.workassistant.db.BusinessData;
import com.gzrijing.workassistant.db.SuppliesData;
import com.gzrijing.workassistant.db.SuppliesNoData;
import com.gzrijing.workassistant.entity.Supplies;
import com.gzrijing.workassistant.service.ReportProjectAmountService;
import com.gzrijing.workassistant.util.ToastUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ReportProjectAmountFragment extends Fragment implements View.OnClickListener {

    private View layoutView;
    private String orderId;
    private String userNo;
    private EditText et_content;
    private EditText et_civil;
    private ArrayList<Supplies> suppliesList = new ArrayList<Supplies>();
    private ListView lv_supplies;
    private SuppliesAdapter adapter;
    private Button btn_need;
    private Button btn_wait;
    private Button btn_print;

    public ReportProjectAmountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        SharedPreferences app = getActivity().getSharedPreferences(
                "saveUser", getActivity().MODE_PRIVATE);
        userNo = app.getString("userNo", "");

        Intent intent = getActivity().getIntent();
        orderId = intent.getStringExtra("orderId");

        getSupplies();

        IntentFilter intentFilter = new IntentFilter("action.com.gzrijing.workassistant.ReportProjectAmountFragment");
        getActivity().registerReceiver(mBroadcastReceiver, intentFilter);

    }

    private void getSupplies() {
        BusinessData businessData = DataSupport.where("user = ? and orderId = ?", userNo, orderId)
                .find(BusinessData.class, true).get(0);
        List<SuppliesNoData> suppliesNoDataList = businessData.getSuppliesNoList();
        List<SuppliesData> suppliesDataList = businessData.getSuppliesDataList();
        for (SuppliesNoData suppliesNodata : suppliesNoDataList) {
            if (suppliesNodata.getReceivedState() != null && suppliesNodata.getReceivedState().equals("已领用")) {
                for (SuppliesData suppliesData : suppliesDataList) {
                    if (suppliesData.getReceivedId().equals(suppliesNodata.getReceivedId())) {
                        Supplies supplies = new Supplies();
                        supplies.setId(suppliesData.getNo());
                        supplies.setName(suppliesData.getName());
                        supplies.setSpec(suppliesData.getSpec());
                        supplies.setUnit(suppliesData.getUnit());
                        supplies.setNum(suppliesData.getNum());
                        suppliesList.add(supplies);
                    }
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        layoutView = inflater.inflate(R.layout.fragment_report_project_amount, container, false);

        initViews();
        setListeners();

        return layoutView;
    }

    private void initViews() {
        et_content = (EditText) layoutView.findViewById(R.id.fragment_report_project_amount_content_et);
        et_civil = (EditText) layoutView.findViewById(R.id.fragment_report_project_amount_civil_et);
        btn_need = (Button) layoutView.findViewById(R.id.fragment_report_project_amount_submit_need_btn);
        btn_wait = (Button) layoutView.findViewById(R.id.fragment_report_project_amount_submit_wait_btn);
        btn_print = (Button) layoutView.findViewById(R.id.fragment_report_project_amount_print_btn);

        lv_supplies = (ListView) layoutView.findViewById(R.id.fragment_report_project_amount_supplies_lv);
        adapter = new SuppliesAdapter(getActivity(), suppliesList);
        lv_supplies.setAdapter(adapter);

    }

    private void setListeners() {
        btn_need.setOnClickListener(this);
        btn_wait.setOnClickListener(this);
        btn_print.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_report_project_amount_submit_need_btn:
                report("need");
                break;

            case R.id.fragment_report_project_amount_submit_wait_btn:
                report("wait");
                break;

            case R.id.fragment_report_project_amount_print_btn:
                Intent intent = new Intent(getActivity(), PrintActivity.class);
                intent.putExtra("userNo", userNo);
                intent.putExtra("orderId", orderId);
                intent.putExtra("content", et_content.getText().toString().trim());
                intent.putExtra("civil", et_civil.getText().toString().trim());
                intent.putParcelableArrayListExtra("suppliesList", suppliesList);
                getActivity().startActivity(intent);
                break;
        }
    }

    private void report(String flag) {
        btn_need.setVisibility(View.GONE);
        btn_wait.setVisibility(View.GONE);
        Intent intent = new Intent(getActivity(), ReportProjectAmountService.class);
        intent.putExtra("userNo", userNo);
        intent.putExtra("orderId", orderId);
        intent.putExtra("content", et_content.getText().toString().trim());
        intent.putExtra("civil", et_civil.getText().toString().trim());
        intent.putParcelableArrayListExtra("suppliesList", suppliesList);
        intent.putExtra("flag", flag);
        getActivity().startService(intent);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.com.gzrijing.workassistant.ReportProjectAmountFragment")) {
                String result = intent.getStringExtra("result");
                if (result.equals("汇报成功")) {
                    ToastUtil.showToast(context, "汇报成功", Toast.LENGTH_SHORT);
                    if (intent.getStringExtra("flag").equals("wait")) {
                        btn_print.setVisibility(View.VISIBLE);
                    }
                }
                if (result.equals("汇报失败")) {
                    ToastUtil.showToast(context, "汇报失败", Toast.LENGTH_SHORT);
                }
                if (result.equals("与服务器断开连接")) {
                    ToastUtil.showToast(context, "与服务器断开连接", Toast.LENGTH_SHORT);
                }
                btn_need.setVisibility(View.VISIBLE);
                btn_wait.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}
