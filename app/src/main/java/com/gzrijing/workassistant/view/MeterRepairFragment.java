package com.gzrijing.workassistant.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gzrijing.workassistant.R;

public class MeterRepairFragment extends Fragment implements View.OnClickListener{

    private View meterRepairLayout;

    public MeterRepairFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        meterRepairLayout = inflater.inflate(R.layout.fragment_meter_repair, container, false);

        initData();
        initViews();
        setListeners();

        return meterRepairLayout;
    }

    private void initData() {

    }

    private void initViews() {

    }

    private void setListeners() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 0:

                break;
        }
    }

}
