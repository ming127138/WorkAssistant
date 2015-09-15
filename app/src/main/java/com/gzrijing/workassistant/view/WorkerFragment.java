package com.gzrijing.workassistant.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.gzrijing.workassistant.R;

public class WorkerFragment extends Fragment {


    private View layoutView;
    private Spinner sp_business;
    private Spinner sp_state;

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

    }

    private void initViews() {
        sp_business = (Spinner) layoutView.findViewById(R.id.fragment_worker_business_sp);
        String[] businessItem = getResources().getStringArray(R.array.business_item);
        ArrayAdapter<String> businessAdater = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, businessItem);
        businessAdater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_business.setAdapter(businessAdater);

        sp_state = (Spinner) layoutView.findViewById(R.id.fragment_worker_state_sp);
        String[] stateItem = getResources().getStringArray(R.array.leader_fragment_state_item);
        ArrayAdapter<String> stateAdater = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, stateItem);
        stateAdater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_state.setAdapter(stateAdater);
    }

    private void setListeners() {

    }

}
