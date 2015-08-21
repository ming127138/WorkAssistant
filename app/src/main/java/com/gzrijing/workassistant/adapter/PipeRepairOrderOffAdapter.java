package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.PipeRepairDistributeOff;
import com.gzrijing.workassistant.view.PipeRepairDistributeInfoActivity;

import java.util.List;

public class PipeRepairOrderOffAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater listContainer;
    private List<PipeRepairDistributeOff> repairOffInfos;

    public PipeRepairOrderOffAdapter(
            Context context, List<PipeRepairDistributeOff> repairOffInfos) {
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.repairOffInfos = repairOffInfos;
    }

    @Override
    public int getCount() {
        return repairOffInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return repairOffInfos.get(position);
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
                    R.layout.listview_item_pipe_repair_order_off, parent, false);
            v.id = (TextView) convertView.findViewById(
                    R.id.listview_item_pipe_repair_order_off_id_tv);
            v.urgent = (ImageView) convertView.findViewById(
                    R.id.listview_item_pipe_repair_order_off_urgent_iv);
            v.one = (TextView) convertView.findViewById(
                    R.id.listview_item_pipe_repair_order_off_info1_tv);
            v.two = (TextView) convertView.findViewById(
                    R.id.listview_item_pipe_repair_order_off_info2_tv);
            v.three = (TextView) convertView.findViewById(
                    R.id.listview_item_pipe_repair_order_off_info3_tv);
            v.confirm = (RelativeLayout) convertView.findViewById(
                    R.id.listview_item_pipe_repair_order_off_confirm_rl);
            v.icon = (ImageView) convertView.findViewById(
                    R.id.listview_item_pipe_repair_order_off_icon_iv);
            v.sign = (TextView) convertView.findViewById(
                    R.id.listview_item_pipe_repair_order_off_sign_tv);
            v.time = (TextView) convertView.findViewById(
                    R.id.listview_item_pipe_repair_order_off_time_tv);
            v.bg = (LinearLayout) convertView.findViewById(
                    R.id.listview_item_pipe_repair_order_off_bg_ll);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }
        v.id.setText(repairOffInfos.get(position).getId());
        v.one.setText(repairOffInfos.get(position).getInfo1());
        v.two.setText(repairOffInfos.get(position).getInfo2());
        v.three.setText(repairOffInfos.get(position).getInfo3());
        v.time.setText(repairOffInfos.get(position).getTime());
        if(repairOffInfos.get(position).isUrgent()){
            v.urgent.setVisibility(View.VISIBLE);
        }else{
            v.urgent.setVisibility(View.GONE);
        }
        if(repairOffInfos.get(position).isFlag()){
            v.bg.setBackgroundResource(R.color.white);
            v.icon.setImageResource(R.drawable.icon_distribute_task);
            v.sign.setText("开始处理");
        }else{
            v.bg.setBackgroundResource(R.color.pink_bg);
            v.icon.setImageResource(R.drawable.icon_confirm);
            v.sign.setText("确认收到");
        }

        v.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(repairOffInfos.get(position).isFlag()){
//                    Intent intent = new Intent(context, PipeRepairorderInfoActivity.class);
//                    intent.putExtra("id", repairOffInfos.get(position).getId());
//                    context.startActivity(intent);
                }else{
                    repairOffInfos.get(position).setFlag(true);
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView id;
        private ImageView urgent;
        private TextView one;
        private TextView two;
        private TextView three;
        private RelativeLayout confirm;
        private ImageView icon;
        private TextView sign;
        private TextView time;
        private LinearLayout bg;
    }
}
