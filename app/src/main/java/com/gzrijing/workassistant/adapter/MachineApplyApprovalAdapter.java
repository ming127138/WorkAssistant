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

public class MachineApplyApprovalAdapter extends BaseAdapter {

    private List<Machine> receivedList;
    private MachineApplyReceivedAdapter receivedAdapter;
    private LayoutInflater listContainer;
    private List<Machine> approvalList;

    public MachineApplyApprovalAdapter(Context context, List<Machine> approvalList,
                                       List<Machine> receivedList, MachineApplyReceivedAdapter receivedAdapter) {
        listContainer = LayoutInflater.from(context);
        this.approvalList = approvalList;
        this.receivedList = receivedList;
        this.receivedAdapter = receivedAdapter;
    }

    @Override
    public int getCount() {
        return approvalList.size();
    }

    @Override
    public Object getItem(int position) {
        return approvalList.get(position);
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
                    R.layout.listview_item_machine_apply_approval, parent, false);
            v.name = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_approval_name_tv);
            v.spec = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_approval_spec_tv);
            v.unit = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_approval_unit_tv);
            v.num = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_approval_num_tv);
            v.state = (TextView) convertView.findViewById(R.id.listview_item_machine_apply_approval_state_tv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.name.setText(approvalList.get(position).getName());
        v.spec.setText(approvalList.get(position).getSpec());
        v.unit.setText(approvalList.get(position).getUnit());
        v.num.setText(approvalList.get(position).getNum() + "");
        v.state.setText(approvalList.get(position).getState());

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
