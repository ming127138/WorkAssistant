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

public class MachineQueryAdapter extends BaseAdapter {

    private LayoutInflater listContainer;
    private List<Machine> machineQueries;

    public MachineQueryAdapter(Context context, List<Machine> machineQueries) {
        listContainer = LayoutInflater.from(context);
        this.machineQueries = machineQueries;
    }

    @Override
    public int getCount() {
        return machineQueries.size();
    }

    @Override
    public Object getItem(int position) {
        return machineQueries.get(position);
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
                    R.layout.listview_item_machine_query, parent, false);
            v.id = (TextView) convertView.findViewById(R.id.listview_item_machine_query_id_tv);
            v.name = (TextView) convertView.findViewById(R.id.listview_item_machine_query_name_tv);
            v.spec = (TextView) convertView.findViewById(R.id.listview_item_machine_query_spec_tv);
            v.unit = (TextView) convertView.findViewById(R.id.listview_item_machine_query_unit_tv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.id.setText(machineQueries.get(position).getId());
        v.name.setText(machineQueries.get(position).getName());
        v.spec.setText(machineQueries.get(position).getSpec());
        v.unit.setText(machineQueries.get(position).getUnit());

        return convertView;
    }

    class ViewHolder {
        private TextView id;
        private TextView name;
        private TextView spec;
        private TextView unit;
    }
}
