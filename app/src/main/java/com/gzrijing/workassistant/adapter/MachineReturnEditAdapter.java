package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.Machine;

import java.util.List;

public class MachineReturnEditAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater listContainer;
    private List<Machine> machineList;

    public MachineReturnEditAdapter(Context context, List<Machine> machineList) {
        this.context = context;
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
            v.unit = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_unit_tv);
            v.num = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_num_tv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.name.setText(machineList.get(position).getName());
        v.unit.setText(machineList.get(position).getUnit());
        v.num.setText(machineList.get(position).getNum() + "");
        v.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(position);
            }
        });

        return convertView;
    }

    private void delete(int position) {
        machineList.remove(position);
        notifyDataSetChanged();
    }

    class ViewHolder {
        private ImageView del;
        private TextView name;
        private TextView unit;
        private TextView num;
    }
}
