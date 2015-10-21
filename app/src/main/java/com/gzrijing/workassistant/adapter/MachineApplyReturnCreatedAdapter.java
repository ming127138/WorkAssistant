package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.Machine;

import java.util.List;

public class MachineApplyReturnCreatedAdapter extends BaseAdapter {

    private LayoutInflater listContainer;
    private List<Machine> machineList;

    public MachineApplyReturnCreatedAdapter(Context context, List<Machine> machineList) {
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
                    R.layout.listview_item_machine_apply_return_created, parent, false);
            v.name = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_return_created_name_tv);
            v.spec = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_return_created_spec_tv);
            v.num = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_return_created_num_tv);
            v.unit = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_return_created_unit_tv);
            v.returnType = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_return_created_type_tv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.name.setText(machineList.get(position).getName());
        v.spec.setText(machineList.get(position).getSpec());
        v.num.setText(machineList.get(position).getNum() + "");
        v.unit.setText(machineList.get(position).getUnit());
        v.returnType.setText(machineList.get(position).getReturnType());

        return convertView;
    }

    class ViewHolder {
        private TextView name;
        private TextView spec;
        private TextView num;
        private TextView unit;
        private TextView returnType;
    }
}
