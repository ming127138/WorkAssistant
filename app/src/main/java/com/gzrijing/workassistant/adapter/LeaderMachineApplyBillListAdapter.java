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
import com.gzrijing.workassistant.entity.LeaderMachineApplyBill;
import com.gzrijing.workassistant.view.LeaderMachineApplyBillByInfoActivity;

import java.util.ArrayList;

public class LeaderMachineApplyBillListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater listContainer;
    private ArrayList<LeaderMachineApplyBill> list;

    public LeaderMachineApplyBillListAdapter(Context context, ArrayList<LeaderMachineApplyBill> list) {
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
                    R.layout.listview_item_leader_machine_apply_bill_list, parent, false);
            v.billNo = (TextView) convertView.findViewById(R.id.listview_item_leader_machine_apply_bill_list_id_tv);
            v.orderId = (TextView) convertView.findViewById(R.id.listview_item_leader_machine_apply_bill_list_order_id_tv);
            v.plan = (Button) convertView.findViewById(R.id.listview_item_leader_machine_apply_bill_list_plan_btn);
            v.info = (Button) convertView.findViewById(R.id.listview_item_leader_machine_apply_bill_list_info_btn);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.billNo.setText(list.get(position).getBillNo());
        v.orderId.setText(list.get(position).getOrderId());

        v.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LeaderMachineApplyBillByInfoActivity.class);
                intent.putExtra("bill", list.get(position));
                context.startActivity(intent);
            }
        });

        v.plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    class ViewHolder {
        private TextView billNo;
        private TextView orderId;
        private Button plan;
        private Button info;

    }
}
