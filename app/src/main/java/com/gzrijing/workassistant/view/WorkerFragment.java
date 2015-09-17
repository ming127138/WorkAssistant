package com.gzrijing.workassistant.view;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.BusinessLeaderAdapter;
import com.gzrijing.workassistant.adapter.BusinessWorkerAdapter;
import com.gzrijing.workassistant.data.BusinessData;
import com.gzrijing.workassistant.entity.BusinessByLeader;
import com.gzrijing.workassistant.entity.BusinessByWorker;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class WorkerFragment extends Fragment {


    private View layoutView;
    private Spinner sp_business;
    private Spinner sp_state;
    private String userName;
    private List<BusinessByWorker> orderList;
    private ListView lv_order;
    private BusinessWorkerAdapter adapter;

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
                "saveUserInfo", getActivity().MODE_PRIVATE);
        userName = app.getString("userName", "");
        orderList = new ArrayList<BusinessByWorker>();
        List<BusinessData> list = DataSupport.where("user = ?", userName).find(BusinessData.class);
        for (BusinessData data : list) {
            BusinessByWorker order = new BusinessByWorker();
            order.setOrderId(data.getOrderId());
            order.setTemInfo(data.isTemInfo());
            order.setUrgent(data.isUrgent());
            order.setType(data.getType());
            order.setState(data.getState());
            order.setFlag(data.getFlag());
            orderList.add(order);
        }
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
        adapter = new BusinessWorkerAdapter(getActivity(), orderList);
        lv_order.setAdapter(adapter);
    }

    private void setListeners() {
        sp_business.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void select(View view){
        TextView tv = (TextView) view;
        tv.setTextColor(getResources().getColor(R.color.black));
        String type = sp_business.getSelectedItem().toString();
        String state = sp_state.getSelectedItem().toString();
        List<BusinessData> list = null;
        if (type.equals("全部") && state.equals("全部")) {
            list = DataSupport.where("user = ?", userName).find(BusinessData.class);
        } else if (type.equals("全部") && !state.equals("全部")) {
            list = DataSupport.where("user = ? and state = ?", userName, state)
                    .find(BusinessData.class);
        } else if (!type.equals("全部") && state.equals("全部")) {
            list = DataSupport.where("user = ? and type = ?", userName, type)
                    .find(BusinessData.class);
        } else {
            list = DataSupport.where("user = ? and type = ? and state = ?", userName, type, state)
                    .find(BusinessData.class);
        }
        orderList.clear();
        for (BusinessData data : list) {
            BusinessByWorker order = new BusinessByWorker();
            order.setOrderId(data.getOrderId());
            order.setTemInfo(data.isTemInfo());
            order.setUrgent(data.isUrgent());
            order.setType(data.getType());
            order.setState(data.getState());
            order.setDeadline(data.getDeadline());
            order.setFlag(data.getFlag());
            orderList.add(order);
        }
        adapter.notifyDataSetChanged();
        list = null;
    }

}
