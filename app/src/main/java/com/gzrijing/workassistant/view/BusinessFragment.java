package com.gzrijing.workassistant.view;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.data.BusinessData;
import com.gzrijing.workassistant.data.WaterSupplyRepairData;

import org.litepal.crud.DataSupport;


public class BusinessFragment extends Fragment {

    private View layoutView;
    private LeaderFragment leaderFragment;
    private WorkerFragment workerFragment;
    private String userName;

    public BusinessFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_business, container, false);

        if (savedInstanceState == null) {
            Fragment fragment = getChildFragmentManager().findFragmentByTag(1 + "");
            if (fragment == null) {
                setTabSelection(1);
            }
        }

        return layoutView;
    }

    private void initData() {
        DataSupport.deleteAll(BusinessData.class);
        SharedPreferences app = getActivity().getSharedPreferences(
                "saveUserInfo", getActivity().MODE_PRIVATE);
        userName = app.getString("userName", "");

        WaterSupplyRepairData data1 = new WaterSupplyRepairData();
        data1.setTime("2015-9-14 10:10");
        data1.setAddress("XXX地址");
        data1.setReason("XXX故障原因");
        data1.setRePairType("XXX维修类型");
        data1.setContacts("张某某");
        data1.setTel("159XXXXXXXX");
        data1.setRemarks("XXX备注");
        data1.save();
        BusinessData data = new BusinessData();
        data.setUser(userName);
        data.setOrderId("工单007");
        data.setUrgent(true);
        data.setType("供水维修");
        data.setState("未派发");
        data.setDeadline("2015-10-1");
        data.setFlag("确认收到");
        data.setWaterSupplyRepairData(data1);
        data.save();


    }


    private void setTabSelection(int index) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (leaderFragment == null) {
                    leaderFragment = new LeaderFragment();
                    transaction.add(R.id.fragment_business, leaderFragment);
                } else {
                    transaction.show(leaderFragment);
                }
                break;
            case 1:
                if (workerFragment == null) {
                    workerFragment = new WorkerFragment();
                    transaction.add(R.id.fragment_business, workerFragment);
                } else {
                    transaction.show(workerFragment);
                }
                break;

        }
        transaction.commit();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (leaderFragment != null) {
            transaction.hide(leaderFragment);
        }
        if (workerFragment != null) {
            transaction.hide(workerFragment);
        }
    }


}
