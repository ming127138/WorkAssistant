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
import com.gzrijing.workassistant.db.BusinessData;
import com.gzrijing.workassistant.entity.BusinessByLeader;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class LeaderFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private String userNo;
    private View layoutView;
    private Spinner sp_business;
    private Spinner sp_state;
    private ListView lv_order;
    public static List<BusinessByLeader> orderList = new ArrayList<BusinessByLeader>();
    public static BusinessLeaderAdapter adapter;
    public static List<BusinessByLeader> orderListByLeader = new ArrayList<BusinessByLeader>();

    public LeaderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_leader, container, false);

        initViews();
        setListeners();
        return layoutView;
    }

    private void initData() {
        SharedPreferences app = getActivity().getSharedPreferences(
                "saveUser", getActivity().MODE_PRIVATE);
        userNo = app.getString("userNo", "");

        orderListByLeader.clear();
        List<BusinessData> list = DataSupport.where("user = ?", userNo).find(BusinessData.class);
        for (BusinessData data : list) {
            BusinessByLeader order = new BusinessByLeader();
            order.setOrderId(data.getOrderId());
            order.setUrgent(data.isUrgent());
            order.setType(data.getType());
            order.setState(data.getState());
            order.setDeadline(data.getDeadline());
            order.setFlag(data.getFlag());
            orderListByLeader.add(order);
        }
    }

    private void initViews() {
        sp_business = (Spinner) layoutView.findViewById(R.id.fragment_leader_business_sp);
        String[] businessItem = getResources().getStringArray(R.array.business_item);
        ArrayAdapter<String> businessAdater = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, businessItem);
        businessAdater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_business.setAdapter(businessAdater);

        sp_state = (Spinner) layoutView.findViewById(R.id.fragment_leader_state_sp);
        String[] stateItem = getResources().getStringArray(R.array.leader_fragment_state_item);
        ArrayAdapter<String> stateAdater = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, stateItem);
        stateAdater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_state.setAdapter(stateAdater);

        lv_order = (ListView) layoutView.findViewById(R.id.fragment_leader_order_lv);
        adapter = new BusinessLeaderAdapter(getActivity(), orderList, userNo);
        lv_order.setAdapter(adapter);
    }

    private void setListeners() {
        sp_business.setOnItemSelectedListener(this);
        sp_state.setOnItemSelectedListener(this);
    }

    private void select(View view){
        TextView tv = (TextView) view;
        tv.setTextColor(getResources().getColor(R.color.black));
        String type = sp_business.getSelectedItem().toString();
        String state = sp_state.getSelectedItem().toString();

        orderList.clear();
        if (type.equals("全部") && state.equals("全部")) {
            orderList.addAll(orderListByLeader);
        } else if (type.equals("全部") && !state.equals("全部")) {
            for(BusinessByLeader order : orderListByLeader){
                if(order.getState().equals(state)){
                    orderList.add(order);
                }
            }
        } else if (!type.equals("全部") && state.equals("全部")) {
            for(BusinessByLeader order : orderListByLeader){
                if(order.getType().equals(type)){
                    orderList.add(order);
                }
            }
        } else {
            for(BusinessByLeader order : orderListByLeader){
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

}
