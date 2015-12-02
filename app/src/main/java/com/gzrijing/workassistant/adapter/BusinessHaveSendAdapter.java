package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.BusinessHaveSend;
import com.gzrijing.workassistant.view.ReportInfoActivity;

import java.util.List;

public class BusinessHaveSendAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater listContainer;
    private List<BusinessHaveSend> BHSList;

    public BusinessHaveSendAdapter(Context context, List<BusinessHaveSend> BHSList) {
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.BHSList = BHSList;
    }

    @Override
    public int getCount() {
        return BHSList.size();
    }

    @Override
    public Object getItem(int position) {
        return BHSList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder v = null;
        if (convertView == null) {
            v = new ViewHolder();
            convertView = listContainer.inflate(
                    R.layout.listview_item_business_have_send, parent, false);
            v.orderId = (TextView) convertView.findViewById(
                    R.id.listview_item_business_have_send_order_id_tv);
            v.content = (TextView) convertView.findViewById(
                    R.id.listview_item_business_have_send_content_tv);
            v.state = (TextView) convertView.findViewById(
                    R.id.listview_item_business_have_send_state_tv);
            v.executors = (TextView) convertView.findViewById(
                    R.id.listview_item_business_have_send_executors_tv);
            v.deadline = (TextView) convertView.findViewById(
                    R.id.listview_item_business_have_send_deadline_tv);
            v.reportInfo = (TextView) convertView.findViewById(
                    R.id.listview_item_business_have_send_report_info_tv);
            v.machine = (Button) convertView.findViewById(
                    R.id.listview_item_business_have_send_machine_verify_btn);
            v.supplies = (Button) convertView.findViewById(
                    R.id.listview_item_business_have_send_supplies_verify_btn);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.orderId.setText(BHSList.get(position).getOrderId());
        v.content.setText(BHSList.get(position).getContent());
        v.state.setText(BHSList.get(position).getState());
        v.executors.setText(BHSList.get(position).getExecutors());
        v.deadline.setText(BHSList.get(position).getDeadline());
        v.reportInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReportInfoActivity.class);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    class ViewHolder {
        private TextView orderId;
        private TextView content;
        private TextView state;
        private TextView executors;
        private TextView deadline;
        private TextView reportInfo;
        private Button machine;
        private Button supplies;
    }
}
