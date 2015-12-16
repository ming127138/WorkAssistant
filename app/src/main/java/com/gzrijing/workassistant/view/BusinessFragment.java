package com.gzrijing.workassistant.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gzrijing.workassistant.R;

public class BusinessFragment extends Fragment {

    private View layoutView;
    private LeaderFragment leaderFragment;
    private WorkerFragment workerFragment;
    private String userRank;

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
            Fragment fragment = getChildFragmentManager().findFragmentByTag("0");
            if (fragment == null) {
                setTabSelection(Integer.valueOf("0"));
            }
        }

        return layoutView;
    }

    private void initData() {
        SharedPreferences sp = getActivity().getSharedPreferences(
                "saveUser", Context.MODE_PRIVATE);
        userRank = sp.getString("userRank", "");
    }

    private void setTabSelection(int index) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (workerFragment == null) {
                    workerFragment = new WorkerFragment();
                    transaction.add(R.id.fragment_business, workerFragment, "0");
                } else {
                    transaction.show(workerFragment);
                }
                break;
            case 1:
                if (leaderFragment == null) {
                    leaderFragment = new LeaderFragment();
                    transaction.add(R.id.fragment_business, leaderFragment, "1");
                } else {
                    transaction.show(leaderFragment);
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
