package com.gzrijing.workassistant.view;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.data.BusinessData;
import com.gzrijing.workassistant.data.DetailedInfoData;

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

        String[] keys = {"　　性质", "水表编号", "用户名称", "用户地址", "联系电话", "水表口径", "　　备注", "任务内容"};
        String[] values = {"报装", "SBBH007", "李XX", "XXXXXX地址", "135XXXXXXXX", "DN36", "XXXX备注", "水表定位"};

        BusinessData data = new BusinessData();
        data.setUser(userName);
        data.setOrderId("工单007");
        data.setUrgent(true);
        data.setType("水表工程");
        data.setState("未处理");
        data.setDeadline("2015-10-1");
        data.setFlag("确认收到");
        for (int i = 0; i < keys.length; i++) {
            DetailedInfoData data1 = new DetailedInfoData();
            data1.setKey(keys[i]);
            data1.setValue(values[i]);
            data1.save();
            data.getDetailedInfoList().add(data1);
        }
        data.save();

        BusinessData data2 = new BusinessData();
        data2.setUser(userName);
        data2.setOrderId("工单009");
        data2.setUrgent(false);
        data2.setType("供水管网巡检");
        data2.setState("未处理");
        data2.setDeadline("2015-10-31");
        data2.setFlag("确认收到");
        data2.save();
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
