package com.gzrijing.workassistant.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gzrijing.workassistant.R;

public class PipeInspectionFragment extends Fragment implements View.OnClickListener{


    private View PipeInspectionLayout;

    public PipeInspectionFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        PipeInspectionLayout = inflater.inflate(R.layout.fragment_pipe_inspection, container, false);

        initData();
        initViews();
        setListeners();

        return PipeInspectionLayout;
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
