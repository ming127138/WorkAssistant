package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.Machine;

import java.util.List;

public class MachineApplyAdapter extends BaseAdapter {

    private LayoutInflater listContainer;
    private List<Machine> machineList;

    public MachineApplyAdapter(Context context, List<Machine> machineList) {
        listContainer = LayoutInflater.from(context);
        this.machineList = machineList;
    }

    @Override
    public int getCount() {
        return machineList.size();
    }

    @Override
    public Object getItem(int position) {
        return machineList.get(position);
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
                    R.layout.listview_item_machine_apply, parent, false);
            v.del = (ImageView) convertView.findViewById(R.id.listview_item_machine_apply_del_iv);
            v.name = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_name_tv);
            v.spec = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_spec_tv);
            v.unit = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_unit_tv);
            v.num = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_num_tv);
            v.up = (ImageView) convertView.findViewById(R.id.listview_item_machine_apply_num_up_iv);
            v.down = (ImageView) convertView.findViewById(R.id.listview_item_machine_apply_num_down_iv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.name.setText(machineList.get(position).getName());
        v.spec.setText(machineList.get(position).getSpec());
        v.unit.setText(machineList.get(position).getUnit());
        v.num.setText(machineList.get(position).getNum() + "");

        v.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(position);
            }
        });

        v.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                up(position);
            }
        });

        v.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                down(position);
            }
        });

        return convertView;
    }

    private void down(int position) {
        int num = machineList.get(position).getNum();
        if (num > 1) {
            num--;
            machineList.get(position).setNum(num);
            notifyDataSetChanged();
        }
    }

    private void up(int position) {
        int num = machineList.get(position).getNum();
        num++;
        machineList.get(position).setNum(num);
        notifyDataSetChanged();
    }

    private void delete(int position) {
        machineList.remove(position);
        notifyDataSetChanged();
    }

    class ViewHolder {
        private ImageView del;
        private TextView name;
        private TextView spec;
        private TextView unit;
        private TextView num;
        private ImageView up;
        private ImageView down;
    }
}
