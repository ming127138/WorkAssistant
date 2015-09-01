package com.gzrijing.workassistant.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.ManageGridViewAdapter;

public class ManageFragment extends Fragment {


    private View layoutView;
    private GridView gv_menu;
    private int[] iconIds;
    private String[] texts;

    public ManageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_manage, container, false);
        initViews();
        setListeners();
        return layoutView;
    }

    private void initData() {
        iconIds = new int[]{R.drawable.icon_trajectory_query, R.drawable.icon_entrust};
        texts = new String[]{"轨迹查询", "委托功能"};
    }

    private void initViews() {
        gv_menu = (GridView) layoutView.findViewById(R.id.manage_gv);
        ManageGridViewAdapter adapter = new ManageGridViewAdapter(getActivity(), iconIds, texts);
        gv_menu.setAdapter(adapter);
    }

    private void setListeners() {

    }


}
