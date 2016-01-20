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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private LinearLayout ll_water;
    private ImageView iv_water;
    private LinearLayout ll_customer;
    private ImageView iv_customer;
    private EditText et_content;
    private EditText et_civil;
    private ArrayList<Supplies> suppliesList = new ArrayList<Supplies>();
    private ListView lv_supplies;
    private SuppliesAdapter adapter;
    private Button btn_need;
    private Button btn_wait;
    private boolean isCheck;

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
            if (suppliesNodata.getReceivedState() != null && suppliesNodata.getReceivedState().equals("已领出")) {
                for (SuppliesData suppliesData : suppliesDataList) {
                    if (suppliesData.getReceivedId() != null && suppliesData.getReceivedId().equals(suppliesNodata.getReceivedId())) {
                        Supplies supplies = new Supplies();
                        supplies.setId(suppliesData.getNo());
                        supplies.setName(suppliesData.getName());
                        supplies.setSpec(suppliesData.getSpec());
                        supplies.setUnit(suppliesData.getUnit());
                        supplies.setSendNum(suppliesData.getSendNum());
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
        ll_water = (LinearLayout) layoutView.findViewById(R.id.fragment_report_project_amount_water_ll);
        iv_water = (ImageView) layoutView.findViewById(R.id.fragment_report_project_amount_water_iv);
        ll_customer = (LinearLayout) layoutView.findViewById(R.id.fragment_report_project_amount_customer_ll);
        iv_customer = (ImageView) layoutView.findViewById(R.id.fragment_report_project_amount_customer_iv);

        et_content = (EditText) layoutView.findViewById(R.id.fragment_report_project_amount_content_et);
        et_civil = (EditText) layoutView.findViewById(R.id.fragment_report_project_amount_civil_et);
        btn_need = (Button) layoutView.findViewById(R.id.fragment_report_project_amount_submit_need_btn);
        btn_wait = (Button) layoutView.findViewById(R.id.fragment_report_project_amount_submit_wait_btn);

        lv_supplies = (ListView) layoutView.findViewById(R.id.fragment_report_project_amount_supplies_lv);
        adapter = new SuppliesAdapter(getActivity(), suppliesList);
        lv_supplies.setAdapter(adapter);

    }

    private void setListeners() {
        ll_water.setOnClickListener(this);
        ll_customer.setOnClickListener(this);
        btn_need.setOnClickListener(this);
        btn_wait.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_report_project_amount_water_ll:
                if (isCheck) {
                    iv_water.setImageResource(R.drawable.spinner_item_check_on);
                    iv_customer.setImageResource(R.drawable.spinner_item_check_off);
                    isCheck = !isCheck;
                }
                break;

            case R.id.fragment_report_project_amount_customer_ll:
                if (!isCheck) {
                    iv_water.setImageResource(R.drawable.spinner_item_check_off);
                    iv_customer.setImageResource(R.drawable.spinner_item_check_on);
                    isCheck = !isCheck;
                }
                break;

            case R.id.fragment_report_project_amount_submit_need_btn:
                report("1");
                break;

            case R.id.fragment_report_project_amount_submit_wait_btn:
                report("0");
                break;

        }
    }

    private void report(String flag) {
        btn_need.setVisibility(View.GONE);
        btn_wait.setVisibility(View.GONE);

        String type;
        if (isCheck) {
            type = "客户";
        } else {
            type = "水务";
        }

        Intent intent = new Intent(getActivity(), ReportProjectAmountService.class);
        intent.putExtra("userNo", userNo);
        intent.putExtra("orderId", orderId);
        intent.putExtra("type", type);
        intent.putExtra("content", et_content.getText().toString().trim());
        intent.putExtra("civil", et_civil.getText().toString().trim());
        intent.putParcelableArrayListExtra("suppliesList", suppliesList);
        intent.putExtra("flag", flag);  //0=施工员审核通过，不通知组长   1=保存状态，通知组长审核
        getActivity().startService(intent);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.com.gzrijing.workassistant.ReportProjectAmountFragment")) {
                String result = intent.getStringExtra("result");
                ToastUtil.showToast(context, result, Toast.LENGTH_SHORT);
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
