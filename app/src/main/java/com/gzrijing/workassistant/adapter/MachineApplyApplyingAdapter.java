package com.gzrijing.workassistant.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.data.MachineData;
import com.gzrijing.workassistant.entity.Machine;

import org.litepal.crud.DataSupport;

import java.util.List;

public class MachineApplyApplyingAdapter extends BaseAdapter {

    private List<Machine> receivedList;
    private MachineApplyReceivedAdapter receivedAdapter;
    private LayoutInflater listContainer;
    private List<Machine> applyList;

    public MachineApplyApplyingAdapter(Context context, List<Machine> applyList,
                                       List<Machine> receivedList, MachineApplyReceivedAdapter receivedAdapter) {
        listContainer = LayoutInflater.from(context);
        this.applyList = applyList;
        this.receivedList = receivedList;
        this.receivedAdapter = receivedAdapter;
    }

    @Override
    public int getCount() {
        return applyList.size();
    }

    @Override
    public Object getItem(int position) {
        return applyList.get(position);
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
                    R.layout.listview_item_machine_apply_applying, parent, false);
            v.name = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_applying_name_tv);
            v.spec = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_applying_spec_tv);
            v.unit = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_applying_unit_tv);
            v.num = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_applying_num_tv);
            v.state = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_applying_state_tv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.name.setText(applyList.get(position).getName());
        v.spec.setText(applyList.get(position).getSpec());
        v.unit.setText(applyList.get(position).getUnit());
        v.num.setText(applyList.get(position).getNum() + "");
        v.state.setText(applyList.get(position).getState());

        v.state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("flag", "领用");
                values.put("returnType", "正常");
                values.put("state", "领用");
                DataSupport.update(MachineData.class, values, applyList.get(position).getDataId());
                applyList.get(position).setReturnType("正常");
                applyList.get(position).setState("领用");
                receivedList.add(applyList.get(position));
                receivedAdapter.notifyDataSetChanged();
                applyList.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    class ViewHolder {
        private TextView name;
        private TextView spec;
        private TextView unit;
        private TextView num;
        private TextView state;
    }
}
