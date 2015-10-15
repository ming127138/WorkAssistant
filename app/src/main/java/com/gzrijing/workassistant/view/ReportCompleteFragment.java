package com.gzrijing.workassistant.view;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.gzrijing.workassistant.util.JudgeDate;
import com.gzrijing.workassistant.widget.selectdate.ScreenInfo;
import com.gzrijing.workassistant.widget.selectdate.WheelMain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReportCompleteFragment extends Fragment implements View.OnClickListener {

    private View layoutView;
    private String orderId;
    private ListView lv_info;
    private List<ReportComplete> infos = new ArrayList<ReportComplete>();
    private ReportCompleteAdapter adapter;
    private LinearLayout ll_water;
    private ImageView iv_water;
    private LinearLayout ll_customer;
    private ImageView iv_customer;
    private Button btn_report;
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

        String[] key = {"排水口径：", "排水时间：", "施工日期：", "完工日期：", "验收日期：", "施工内容：", "土建项目：", "　　备注："};
        for (int i = 0; i < key.length; i++) {
            ReportComplete info = new ReportComplete();
            info.setKey(key[i]);
            infos.add(info);
        }
    }

    private void initViews() {
        ll_water = (LinearLayout) layoutView.findViewById(R.id.fragment_report_complete_water_ll);
        iv_water = (ImageView) layoutView.findViewById(R.id.fragment_report_complete_water_iv);
        ll_customer = (LinearLayout) layoutView.findViewById(R.id.fragment_report_complete_customer_ll);
        iv_customer = (ImageView) layoutView.findViewById(R.id.fragment_report_complete_customer_iv);
        btn_report = (Button) layoutView.findViewById(R.id.fragment_report_complete_report_btn);

        lv_info = (ListView) layoutView.findViewById(R.id.report_complete_info_lv);
        adapter = new ReportCompleteAdapter(getActivity(), infos);
        lv_info.setAdapter(adapter);
    }

    private void setListeners() {
        ll_water.setOnClickListener(this);
        ll_customer.setOnClickListener(this);
        btn_report.setOnClickListener(this);

        lv_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key = infos.get(position).getKey();
                if (key.equals("施工日期：") || key.equals("完工日期：") || key.equals("验收日期：")) {
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

            case R.id.fragment_report_complete_report_btn:
                report();
                break;
        }
    }

    private void report() {
        Log.e("1111111:", infos.get(0).getValue() + infos.get(1).getValue() + infos.get(2).getValue()
                + infos.get(3).getValue() + infos.get(4).getValue() + infos.get(5).getValue() + infos.get(6).getValue()
                + infos.get(7).getValue());
        Toast.makeText(getActivity(), infos.get(0).getValue() + infos.get(1).getValue() + infos.get(2).getValue()
                + infos.get(3).getValue() + infos.get(4).getValue() + infos.get(5).getValue() + infos.get(6).getValue()
                + infos.get(7).getValue(), Toast.LENGTH_LONG).show();
    }
}
