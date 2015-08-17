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
import com.gzrijing.workassistant.entity.PipeRepairDistributeOn;
import com.gzrijing.workassistant.view.PipeInspectionMapActivity;
import com.gzrijing.workassistant.view.PipeRepairProgressActivity;

import java.util.Calendar;
import java.util.List;

public class PipeRepairDistributeOnAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater listContainer;
    private List<PipeRepairDistributeOn> repairOnInfos;

    public PipeRepairDistributeOnAdapter(
            Context context, List<PipeRepairDistributeOn> repairOnInfos) {
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.repairOnInfos = repairOnInfos;
    }

    @Override
    public int getCount() {
        return repairOnInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return repairOnInfos.get(position);
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
                    R.layout.listview_item_pipe_repair_distribute_on, parent, false);
            v.id = (TextView) convertView.findViewById(
                    R.id.listview_item_pipe_repair_distribute_on_id_tv);
            v.urgent = (ImageView) convertView.findViewById(
                    R.id.listview_item_pipe_repair_distribute_on_urgent_iv);
            v.one = (TextView) convertView.findViewById(
                    R.id.listview_item_pipe_repair_distribute_on_info1_tv);
            v.two = (TextView) convertView.findViewById(
                    R.id.listview_item_pipe_repair_distribute_on_info2_tv);
            v.three = (TextView) convertView.findViewById(
                    R.id.listview_item_pipe_repair_distribute_on_info3_tv);
            v.progress = (RelativeLayout) convertView.findViewById(
                    R.id.listview_item_pipe_repair_distribute_on_progress_rl);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }
        v.id.setText(repairOnInfos.get(position).getId());
        v.one.setText(repairOnInfos.get(position).getInfo2());
        v.two.setText(repairOnInfos.get(position).getInfo3());
        v.three.setText(repairOnInfos.get(position).getInfo4());
        if(repairOnInfos.get(position).isUrgent()){
            v.urgent.setVisibility(View.VISIBLE);
        }else{
            v.urgent.setVisibility(View.GONE);
        }

        v.progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PipeRepairProgressActivity.class);
                intent.putExtra("title", repairOnInfos.get(position).getId());
                context.startActivity(intent);
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
        private RelativeLayout progress;
    }
}
