package com.gzrijing.workassistant.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.BusinessWorkerAdapter;
import com.gzrijing.workassistant.base.MyApplication;
import com.gzrijing.workassistant.db.BusinessData;
import com.gzrijing.workassistant.entity.BusinessByWorker;
import com.gzrijing.workassistant.service.GetInspectionService;
import com.gzrijing.workassistant.service.GetSewageWellsService;
import com.gzrijing.workassistant.util.JsonParseUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class WorkerFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private View layoutView;
    private Spinner sp_business;
    private Spinner sp_state;
    private String userNo;
    private ListView lv_order;
    public static List<BusinessByWorker> orderList = new ArrayList<BusinessByWorker>();
    public static BusinessWorkerAdapter adapter;
    public static List<BusinessByWorker> orderListByWorker = new ArrayList<BusinessByWorker>();

    public WorkerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_worker, container, false);

        initViews();
        setListeners();
        return layoutView;
    }

    private void initData() {
        SharedPreferences app = getActivity().getSharedPreferences(
                "saveUser", getActivity().MODE_PRIVATE);
        userNo = app.getString("userNo", "");

        orderListByWorker.clear();
        List<BusinessData> list = DataSupport.where("user = ?", userNo).find(BusinessData.class);
        for (BusinessData data : list) {
            BusinessByWorker order = new BusinessByWorker();
            order.setOrderId(data.getOrderId());
            order.setUrgent(data.isUrgent());
            order.setType(data.getType());
            order.setState(data.getState());
            order.setDeadline(data.getDeadline());
            order.setFlag(data.getFlag());
            orderListByWorker.add(order);
        }

        getInspectionOrder();

//        getSewageWellsOrder();
//
    }

    private void getInspectionOrder() {
        IntentFilter intentFilter = new IntentFilter("action.com.gzrijing.workassistant.WorkerFragment");
        getActivity().registerReceiver(mBroadcastReceiver, intentFilter);
        Intent serviceIntent = new Intent(getActivity(), GetInspectionService.class);
        serviceIntent.putExtra("userNo", userNo);
        getActivity().startService(serviceIntent);
    }

    private void getSewageWellsOrder() {
        Intent serviceIntent = new Intent(getActivity(), GetSewageWellsService.class);
        serviceIntent.putExtra("userNo", userNo);
        getActivity().startService(serviceIntent);
    }

    private void initViews() {
        sp_business = (Spinner) layoutView.findViewById(R.id.fragment_worker_business_sp);
        String[] businessItem = getResources().getStringArray(R.array.business_item);
        ArrayAdapter<String> businessAdater = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, businessItem);
        businessAdater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_business.setAdapter(businessAdater);

        sp_state = (Spinner) layoutView.findViewById(R.id.fragment_worker_state_sp);
        String[] stateItem = getResources().getStringArray(R.array.worker_fragment_state_item);
        ArrayAdapter<String> stateAdater = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, stateItem);
        stateAdater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_state.setAdapter(stateAdater);

        lv_order = (ListView) layoutView.findViewById(R.id.fragment_worker_order_lv);
        adapter = new BusinessWorkerAdapter(getActivity(), orderList, userNo);
        lv_order.setAdapter(adapter);
    }

    private void setListeners() {
        sp_business.setOnItemSelectedListener(this);
        sp_state.setOnItemSelectedListener(this);
    }

    private void select(View view){
        TextView tv = (TextView) view;
        tv.setTextColor(MyApplication.getContext().getResources().getColor(R.color.black));
        String type = sp_business.getSelectedItem().toString();
        String state = sp_state.getSelectedItem().toString();

        orderList.clear();
        if (type.equals("全部") && state.equals("全部")) {
            orderList.addAll(orderListByWorker);
        } else if (type.equals("全部") && !state.equals("全部")) {
            for(BusinessByWorker order : orderListByWorker){
                if(order.getState().equals(state)){
                    orderList.add(order);
                }
            }
        } else if (!type.equals("全部") && state.equals("全部")) {
            for(BusinessByWorker order : orderListByWorker){
                if(order.getType().equals(type)){
                    orderList.add(order);
                }
            }
        } else {
            for(BusinessByWorker order : orderListByWorker){
                if(order.getType().equals(type) && order.getState().equals(state)){
                    orderList.add(order);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        select(view);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("action.com.gzrijing.workassistant.WorkerFragment")){
                Log.e("ok", "asdf");
                String jsonData = intent.getStringExtra("jsonData");
                Log.e("ok", jsonData);
                List<BusinessByWorker> list = JsonParseUtils.getInspection(jsonData);
                for(BusinessByWorker businessByWorker: list){
                    orderListByWorker.add(businessByWorker);
                    orderList.add(businessByWorker);
                }
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}
