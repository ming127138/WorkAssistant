package com.gzrijing.workassistant.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gzrijing.workassistant.R;

public class PipeRepairDistributeOffFragment extends Fragment {


    private View layoutView;

    public PipeRepairDistributeOffFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_pipe_repair_distribute_off, container, false);

        initData();
        initViews();
        setListeners();

        return layoutView;
    }

    private void initData() {

    }

    private void initViews() {

    }

    private void setListeners() {

    }


}
