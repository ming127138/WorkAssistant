package com.gzrijing.workassistant.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.ManageGridViewAdapter;
import com.gzrijing.workassistant.adapter.MoreGridViewAdapter;


public class MoreFragment extends Fragment {

    private View layoutView;
    private int[] iconIds;
    private String[] texts;
    private GridView gv_menu;

    public MoreFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_more, container, false);
        initViews();
        setListeners();
        return layoutView;
    }

    private void initData() {
        iconIds = new int[]{R.drawable.icon_notice};
        texts = new String[]{"通知公告"};
    }

    private void initViews() {
        gv_menu = (GridView) layoutView.findViewById(R.id.more_gv);
        MoreGridViewAdapter adapter = new MoreGridViewAdapter(getActivity(), iconIds, texts);
        gv_menu.setAdapter(adapter);
    }

    private void setListeners() {

    }

}
