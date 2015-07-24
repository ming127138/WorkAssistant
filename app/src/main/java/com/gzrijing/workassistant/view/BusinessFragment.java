package com.gzrijing.workassistant.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gzrijing.workassistant.R;


public class BusinessFragment extends Fragment implements View.OnClickListener {

    private View layoutView;
    private Button btn_pipeInspection;
    private Button btn_pipeRepair;
    private Button btn_meterInstall;
    private Button btn_meterRepair;
    private PipeInspectionFragment pipeInspectionFragment;
    private PipeRepairFragment pipeRepairFragment;
    private MeterInstallFragment meterInstallFragment;
    private MeterRepairFragment meterRepairFragment;

    public BusinessFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_business, container, false);

        initData();
        initViews();
        setListeners();

        if (savedInstanceState == null) {
            Fragment fragment = getChildFragmentManager().findFragmentByTag(0 + "");
            if (fragment == null) {
                setTabSelection(0);
            }
        }

        return layoutView;
    }

    private void initData() {


    }

    private void initViews() {
        btn_pipeInspection = (Button) layoutView.findViewById(R.id.business_pipe_inspection_btn);
        btn_pipeRepair = (Button) layoutView.findViewById(R.id.business_pipe_repair_btn);
        btn_meterInstall = (Button) layoutView.findViewById(R.id.business_meter_install_btn);
        btn_meterRepair = (Button) layoutView.findViewById(R.id.business_meter_repair_btn);
    }

    private void setListeners() {
        btn_pipeInspection.setOnClickListener(this);
        btn_pipeRepair.setOnClickListener(this);
        btn_meterInstall.setOnClickListener(this);
        btn_meterRepair.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.business_pipe_inspection_btn:
                setTabSelection(0);
                break;

            case R.id.business_pipe_repair_btn:
                setTabSelection(1);
                break;

            case R.id.business_meter_install_btn:
                setTabSelection(2);
                break;

            case R.id.business_meter_repair_btn:
                setTabSelection(3);
                break;
        }
    }

    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                btn_pipeInspection.setBackgroundResource(R.drawable.business_btn_on);
                btn_pipeInspection.setTextColor(getResources().getColor(R.color.blue));
                if (pipeInspectionFragment == null) {
                    pipeInspectionFragment = new PipeInspectionFragment();
                    transaction.add(R.id.content, pipeInspectionFragment);
                } else {
                    transaction.show(pipeInspectionFragment);
                }
                break;
            case 1:
                btn_pipeRepair.setBackgroundResource(R.drawable.business_btn_on);
                btn_pipeRepair.setTextColor(getResources().getColor(R.color.blue));
                if (pipeRepairFragment == null) {
                    pipeRepairFragment = new PipeRepairFragment();
                    transaction.add(R.id.content, pipeRepairFragment);
                } else {
                    transaction.show(pipeRepairFragment);
                }
                break;
            case 2:
                btn_meterInstall.setBackgroundResource(R.drawable.business_btn_on);
                btn_meterInstall.setTextColor(getResources().getColor(R.color.blue));
                if (meterInstallFragment == null) {
                    meterInstallFragment = new MeterInstallFragment();
                    transaction.add(R.id.content, meterInstallFragment);
                } else {
                    transaction.show(meterInstallFragment);
                }
                break;
            case 3:
                btn_meterRepair.setBackgroundResource(R.drawable.business_btn_on);
                btn_meterRepair.setTextColor(getResources().getColor(R.color.blue));
                if (meterRepairFragment == null) {
                    meterRepairFragment = new MeterRepairFragment();
                    transaction.add(R.id.content, meterRepairFragment);
                } else {
                    transaction.show(meterRepairFragment);
                }
                break;

        }
        transaction.commit();
    }

    private void clearSelection() {
        btn_pipeInspection.setBackgroundResource(R.drawable.business_btn_off);
        btn_pipeInspection.setTextColor(getResources().getColor(R.color.grey_text));
        btn_pipeRepair.setBackgroundResource(R.drawable.business_btn_off);
        btn_pipeRepair.setTextColor(getResources().getColor(R.color.grey_text));
        btn_meterInstall.setBackgroundResource(R.drawable.business_btn_off);
        btn_meterInstall.setTextColor(getResources().getColor(R.color.grey_text));
        btn_meterRepair.setBackgroundResource(R.drawable.business_btn_off);
        btn_meterRepair.setTextColor(getResources().getColor(R.color.grey_text));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (pipeInspectionFragment != null) {
            transaction.hide(pipeInspectionFragment);
        }
        if (pipeRepairFragment != null) {
            transaction.hide(pipeRepairFragment);
        }
        if (meterInstallFragment != null) {
            transaction.hide(meterInstallFragment);
        }
        if (meterRepairFragment != null) {
            transaction.hide(meterRepairFragment);
        }
    }


}
