package com.gzrijing.workassistant.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.data.BusinessData;
import com.gzrijing.workassistant.entity.BusinessByLeader;
import com.gzrijing.workassistant.entity.MachineVerify;
import com.gzrijing.workassistant.view.MachineVerifyActivity;
import com.gzrijing.workassistant.view.ProgressActivity;
import com.gzrijing.workassistant.view.DistributeActivity;
import com.gzrijing.workassistant.view.SuppliesVerifyActivity;
import com.gzrijing.workassistant.view.WaterSupplyRepairInfoActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

public class BusinessLeaderAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater listContainer;
    private List<BusinessByLeader> orderList;

    public BusinessLeaderAdapter(Context context, List<BusinessByLeader> orderList) {
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
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
                    R.layout.listview_item_business_leader, parent, false);
            v.orderId = (TextView) convertView.findViewById(
                    R.id.listview_item_business_leader_order_id_tv);
            v.progress = (Button) convertView.findViewById(
                    R.id.listview_item_business_leader_progress_btn);
            v.info = (Button) convertView.findViewById(
                    R.id.listview_item_business_leader_info_btn);
            v.urgent = (ImageView) convertView.findViewById(
                    R.id.listview_item_business_leader_urgent_iv);
            v.type = (TextView) convertView.findViewById(
                    R.id.listview_item_business_leader_type_tv);
            v.state = (TextView) convertView.findViewById(
                    R.id.listview_item_business_leader_state_tv);
            v.deadline = (TextView) convertView.findViewById(
                    R.id.listview_item_business_leader_deadline_tv);
            v.machineVerify = (TextView) convertView.findViewById(
                    R.id.listview_item_business_leader_machine_verify_tv);
            v.suppliesVerify = (TextView) convertView.findViewById(
                    R.id.listview_item_business_leader_supplies_verify_tv);
            v.temInfo = (TextView) convertView.findViewById(
                    R.id.listview_item_business_leader_tem_info_tv);
            v.flag = (TextView) convertView.findViewById(
                    R.id.listview_item_business_leader_flag_tv);
            v.bg_ll = (LinearLayout) convertView.findViewById(
                    R.id.listview_item_business_leader_bg_ll);
            v.btn_rl = (RelativeLayout) convertView.findViewById(
                    R.id.listview_item_business_leader_rl);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.orderId.setText(orderList.get(position).getOrderId());
        v.type.setText(orderList.get(position).getType());
        v.state.setText(orderList.get(position).getState());
        v.deadline.setText(orderList.get(position).getDeadline());

        if (orderList.get(position).isUrgent()) {
            v.urgent.setVisibility(View.VISIBLE);
        } else {
            v.urgent.setVisibility(View.GONE);
        }

        v.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WaterSupplyRepairInfoActivity.class);
                intent.putExtra("orderId", orderList.get(position).getOrderId());
                context.startActivity(intent);
            }
        });

        v.progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProgressActivity.class);
                intent.putExtra("orderId", orderList.get(position).getOrderId());
                context.startActivity(intent);
            }
        });

        v.machineVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MachineVerifyActivity.class);
                intent.putExtra("orderId", orderList.get(position).getOrderId());
                context.startActivity(intent);
            }
        });

        v.suppliesVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SuppliesVerifyActivity.class);
                intent.putExtra("orderId", orderList.get(position).getOrderId());
                context.startActivity(intent);
            }
        });

        final String flag = orderList.get(position).getFlag();
        v.flag.setText(flag);
        if (flag.equals("确认收到")) {
            v.bg_ll.setBackgroundResource(R.color.pink_bg);
        } else {
            v.bg_ll.setBackgroundResource(R.color.white);
        }

        v.btn_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.equals("确认收到")) {
                    orderList.get(position).setFlag("派发");
                    ContentValues values = new ContentValues();
                    values.put("flag", "派发");
                    DataSupport.updateAll(BusinessData.class, values,
                            "orderId = ?", orderList.get(position).getOrderId());
                    notifyDataSetChanged();
                }

                if (flag.equals("派发")) {
                    Intent intent = new Intent(context, DistributeActivity.class);
                    intent.putExtra("orderId", orderList.get(position).getOrderId());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        private TextView orderId;
        private ImageView urgent;
        private TextView type;
        private TextView state;
        private TextView deadline;
        private TextView machineVerify;
        private TextView suppliesVerify;
        private TextView temInfo;
        private Button progress;
        private Button  info;
        private TextView flag;
        private LinearLayout bg_ll;
        private RelativeLayout btn_rl;
    }
}
