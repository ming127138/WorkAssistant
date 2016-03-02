package com.gzrijing.workassistant.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.BusinessHaveSendByAll;
import com.gzrijing.workassistant.entity.Subordinate;

import java.util.ArrayList;

public class SortByWorkerNameFragment extends Fragment {

    private View layoutView;
    private ArrayList<BusinessHaveSendByAll> businessList;
    private ArrayList<Subordinate> subordinateList;
    private ExpandableListView elv_business;

    public SortByWorkerNameFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
    }

    private void initData() {
        Bundle bundle = getArguments();
        businessList = bundle.getParcelableArrayList("businessList");
        subordinateList = bundle.getParcelableArrayList("subordinateList");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_sort_by_worker_name, container, false);

        initViews();

        return layoutView;
    }

    private void initViews() {
        elv_business = (ExpandableListView) layoutView.findViewById(R.id.sort_by_worker_name_fragment_elv);

    }

}
