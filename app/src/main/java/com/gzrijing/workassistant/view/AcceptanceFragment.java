package com.gzrijing.workassistant.view;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.AcceptanceAdapter;
import com.gzrijing.workassistant.entity.Acceptance;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.JsonParseUtils;
import com.gzrijing.workassistant.util.ToastUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class AcceptanceFragment extends Fragment {


    private View layoutView;
    private ListView lv_accList;
    private List<Acceptance> accList = new ArrayList<Acceptance>();
    private AcceptanceAdapter adapter;
    private String userNo;
    private Handler handler = new Handler();

    public AcceptanceFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences app = getActivity().getSharedPreferences(
                "saveUser", getActivity().MODE_PRIVATE);
        userNo = app.getString("userNo", "");

        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_acceptance, container, false);
        initViews();
        return layoutView;
    }

    private void initData() {
        String url = null;
        try {
            url = "?cmd=getfinishconstruction&userno="+URLEncoder.encode(userNo, "UTF-8")+"&fileno=&enddate=&isfinish=1";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpUtils.sendHttpGetRequest(url, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                Log.e("response", response);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<Acceptance> list = JsonParseUtils.getAcceptanceInfo(response);
                        accList.clear();
                        accList.addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(getActivity(), "与服务器断开连接", Toast.LENGTH_SHORT);
                    }
                });
            }
        });

    }

    private void initViews() {
        lv_accList = (ListView) layoutView.findViewById(R.id.acceptance_lv);
        adapter = new AcceptanceAdapter(getActivity(), accList);
        lv_accList.setAdapter(adapter);
    }

}
