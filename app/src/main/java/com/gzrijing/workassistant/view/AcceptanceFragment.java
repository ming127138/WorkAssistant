package com.gzrijing.workassistant.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.AcceptanceAdapter;

import java.util.ArrayList;
import java.util.List;

public class AcceptanceFragment extends Fragment {


    private View layoutView;
    private ListView lv_accList;
    private List<String> accList = new ArrayList<String>();
    private AcceptanceAdapter adapter;

    public AcceptanceFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_acceptance, container, false);
        initViews();
        setListeners();
        return layoutView;
    }

    private void initData() {
            accList.add("工单007");
    }

    private void initViews() {
        lv_accList = (ListView) layoutView.findViewById(R.id.acceptance_lv);
        adapter = new AcceptanceAdapter(getActivity(), accList);
        lv_accList.setAdapter(adapter);
    }

    private void setListeners() {

    }
}
