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

    private Button btn_installMeter;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private ManageFragment manageFragment;
    private AcceptanceFragment acceptanceFragment;
    private MoreFragment moreFragment;
    private View layoutView;

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
        btn_installMeter = (Button) layoutView.findViewById(R.id.business_install_meter_btn);
        btn1 = (Button) layoutView.findViewById(R.id.button2);
        btn2 = (Button) layoutView.findViewById(R.id.button3);
        btn3 = (Button) layoutView.findViewById(R.id.button4);
    }

    private void setListeners() {
        btn_installMeter.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.business_install_meter_btn:
                setTabSelection(0);
                break;

            case R.id.button2:
                setTabSelection(1);
                break;

            case R.id.button3:
                setTabSelection(2);
                break;

            case R.id.button4:
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
                btn_installMeter.setBackgroundResource(R.drawable.business_btn_on);
                btn_installMeter.setTextColor(getResources().getColor(R.color.blue));
                if (manageFragment == null) {
                    manageFragment = new ManageFragment();
                    transaction.add(R.id.content, manageFragment);
                } else {
                    transaction.show(manageFragment);
                }
                break;
            case 1:
                btn1.setBackgroundResource(R.drawable.business_btn_on);
                btn1.setTextColor(getResources().getColor(R.color.blue));
                if (manageFragment == null) {
                    manageFragment = new ManageFragment();
                    transaction.add(R.id.content, manageFragment);
                } else {
                    transaction.show(manageFragment);
                }
                break;
            case 2:
                btn2.setBackgroundResource(R.drawable.business_btn_on);
                btn2.setTextColor(getResources().getColor(R.color.blue));
                if (acceptanceFragment == null) {
                    acceptanceFragment = new AcceptanceFragment();
                    transaction.add(R.id.content, acceptanceFragment);
                } else {
                    transaction.show(acceptanceFragment);
                }
                break;
            case 3:
                btn3.setBackgroundResource(R.drawable.business_btn_on);
                btn3.setTextColor(getResources().getColor(R.color.blue));
                if (moreFragment == null) {
                    moreFragment = new MoreFragment();
                    transaction.add(R.id.content, moreFragment);
                } else {
                    transaction.show(moreFragment);
                }
                break;

        }
        transaction.commit();
    }

    private void clearSelection() {
        btn_installMeter.setBackgroundResource(R.drawable.business_btn_off);
        btn_installMeter.setTextColor(getResources().getColor(R.color.black));
        btn1.setBackgroundResource(R.drawable.business_btn_off);
        btn1.setTextColor(getResources().getColor(R.color.black));
        btn2.setBackgroundResource(R.drawable.business_btn_off);
        btn2.setTextColor(getResources().getColor(R.color.black));
        btn3.setBackgroundResource(R.drawable.business_btn_off);
        btn3.setTextColor(getResources().getColor(R.color.black));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (manageFragment != null) {
            transaction.hide(manageFragment);
        }
        if (acceptanceFragment != null) {
            transaction.hide(acceptanceFragment);
        }
        if (moreFragment != null) {
            transaction.hide(moreFragment);
        }
    }


}
