package com.gzrijing.workassistant.view;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.ReportCompleteAdapter;
import com.gzrijing.workassistant.entity.ReportComplete;
import com.gzrijing.workassistant.service.ReportCompleteService;
import com.gzrijing.workassistant.util.JudgeDate;
import com.gzrijing.workassistant.util.ToastUtil;
import com.gzrijing.workassistant.widget.selectdate.ScreenInfo;
import com.gzrijing.workassistant.widget.selectdate.WheelMain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ReportCompleteFragment extends Fragment implements View.OnClickListener {

    private View layoutView;
    private String orderId;
    private ListView lv_info;
    private ArrayList<ReportComplete> infos = new ArrayList<ReportComplete>();
    private ReportCompleteAdapter adapter;
    private LinearLayout ll_water;
    private ImageView iv_water;
    private LinearLayout ll_customer;
    private ImageView iv_customer;
    private Button btn_print;
    private boolean isCheck = false;
    private WheelMain wheelMain;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public ReportCompleteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.fragment_report_complete, container, false);

        initViews();
        setListeners();
        return layoutView;
    }

    private void initData() {
        orderId = getArguments().getString("orderId");

        String[] key = getResources().getStringArray(R.array.reportComplete_form1);
//        String[] key = {"表身编号", "水表产地", "排水口径", "施工内容", "土建项目", "　　备注", "排水时间", "施工日期", "完工日期", "验收日期", "水表有效日期", "", ""};
        for (int i = 0; i < key.length; i++) {
            ReportComplete info = new ReportComplete();
            info.setKey(key[i]);
            infos.add(info);
        }

        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("action.com.gzrijing.workassistant.ReportComplete");
        //注册广播
        getActivity().registerReceiver(mBroadcastReceiver, mIntentFilter);

    }

    private void initViews() {
        ll_water = (LinearLayout) layoutView.findViewById(R.id.fragment_report_complete_water_ll);
        iv_water = (ImageView) layoutView.findViewById(R.id.fragment_report_complete_water_iv);
        ll_customer = (LinearLayout) layoutView.findViewById(R.id.fragment_report_complete_customer_ll);
        iv_customer = (ImageView) layoutView.findViewById(R.id.fragment_report_complete_customer_iv);
        btn_print = (Button) layoutView.findViewById(R.id.fragment_report_complete_print_btn);

        lv_info = (ListView) layoutView.findViewById(R.id.report_complete_info_lv);
        adapter = new ReportCompleteAdapter(getActivity(), infos, btn_print, orderId);
        lv_info.setAdapter(adapter);
    }

    private void setListeners() {
        ll_water.setOnClickListener(this);
        ll_customer.setOnClickListener(this);
        btn_print.setOnClickListener(this);

        lv_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key = infos.get(position).getKey();
                if (key.equals("水表有效日期") || key.equals("排水时间") || key.equals("施工日期") || key.equals("完工日期") || key.equals("验收日期")) {
                    TextView tv_value = (TextView) view.findViewById(
                            R.id.listview_item_fragment_report_complete_date_value_tv);
                    getDate(position, tv_value);
                }

            }
        });
    }

    private void getDate(final int position, final TextView tv_value) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View timepickerview = inflater.inflate(R.layout.timepicker, null);
        ScreenInfo screenInfo = new ScreenInfo(getActivity());
        wheelMain = new WheelMain(timepickerview, true);
        wheelMain.screenheight = screenInfo.getHeight();
        String time = tv_value.getText().toString();
        Calendar calendar = Calendar.getInstance();
        if (JudgeDate.isDate(time, "yyyy-MM-dd HH:mm")) {
            try {
                calendar.setTime(dateFormat.parse(time));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        int d = c.get(Calendar.DAY_OF_MONTH);
        int h = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        wheelMain.initDateTimePicker(y, m - 1, d, h, min);
        new AlertDialog.Builder(getActivity())
                .setTitle("选择时间")
                .setView(timepickerview)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tv_value.setText(wheelMain.getTime());
                        infos.get(position).setValue(tv_value.getText().toString());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_report_complete_water_ll:
                if (isCheck) {
                    iv_water.setImageResource(R.drawable.spinner_item_check_on);
                    iv_customer.setImageResource(R.drawable.spinner_item_check_off);
                    isCheck = !isCheck;
                }
                break;

            case R.id.fragment_report_complete_customer_ll:
                if (!isCheck) {
                    iv_water.setImageResource(R.drawable.spinner_item_check_off);
                    iv_customer.setImageResource(R.drawable.spinner_item_check_on);
                    isCheck = !isCheck;
                }
                break;

            case R.id.fragment_report_complete_print_btn:
                Intent intent = new Intent(getActivity(), PrintActivity.class);
                intent.putParcelableArrayListExtra("infos", infos);
                intent.putExtra("isCheck", isCheck);
                startActivity(intent);
                break;
        }
    }

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("action.com.gzrijing.workassistant.ReportComplete")){
                String result = intent.getStringExtra("result");
                if(result.equals("汇报成功")){
                    btn_print.setVisibility(View.VISIBLE);
                    ToastUtil.showToast(context, "汇报成功", Toast.LENGTH_SHORT);
                }
                if(result.equals("汇报失败")){
                    ToastUtil.showToast(context, "汇报失败", Toast.LENGTH_SHORT);
                }
                if(result.equals("与服务器断开连接")){
                    ToastUtil.showToast(context, "与服务器断开连接", Toast.LENGTH_SHORT);
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}
