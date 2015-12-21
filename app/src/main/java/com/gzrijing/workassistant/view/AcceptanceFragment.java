package com.gzrijing.workassistant.view;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.AcceptanceAdapter;
import com.gzrijing.workassistant.entity.Acceptance;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class AcceptanceFragment extends Fragment {


    private View layoutView;
    private ListView lv_accList;
    private List<String> accList = new ArrayList<String>();
    private AcceptanceAdapter adapter;
    private String userNo;

    public AcceptanceFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences app = getActivity().getSharedPreferences(
                "saveUser", getActivity().MODE_PRIVATE);
        userNo = app.getString("userNo", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_acceptance, container, false);
        initViews();
        setListeners();
        return layoutView;
    }

    @Override
    public void onStart() {
        initData();
        super.onStart();
    }

    private void initData() {
        String url = null;
        try {
            url = "cmd=getmyconsconfirm&userno="+ URLEncoder.encode(userNo, "UTF-8")+"&ispass=";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpUtils.sendHttpGetRequest(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
            }

            @Override
            public void onError(Exception e) {

            }
        });


            accList.add("工单007");
    }

    private void initViews() {
        lv_accList = (ListView) layoutView.findViewById(R.id.acceptance_lv);
        adapter = new AcceptanceAdapter(getActivity(), accList);
        lv_accList.setAdapter(adapter);
    }

    private void setListeners() {

    }
}
