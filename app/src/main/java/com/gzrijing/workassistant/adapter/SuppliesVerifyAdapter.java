package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.MachineVerify;
import com.gzrijing.workassistant.entity.SuppliesVerify;

import java.util.List;

public class SuppliesVerifyAdapter extends BaseAdapter {

    private LayoutInflater listContainer;
    private List<SuppliesVerify> svList;

    public SuppliesVerifyAdapter(Context context, List<SuppliesVerify> svList) {
        listContainer = LayoutInflater.from(context);
        this.svList = svList;
    }

    @Override
    public int getCount() {
        return svList.size();
    }

    @Override
    public Object getItem(int position) {
        return svList.get(position);
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
                    R.layout.listview_item_supplies_verify, parent, false);
            v.id = (TextView) convertView.findViewById(R.id.listview_item_supplies_verify_id_tv);
            v.applicant = (TextView) convertView.findViewById(R.id.listview_item_supplies_verify_applicant_tv);
            v.time = (TextView) convertView.findViewById(R.id.listview_item_supplies_verify_time_tv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.id.setText(svList.get(position).getId());
        v.applicant.setText(svList.get(position).getApplicant());
        v.time.setText(svList.get(position).getUseTime());

        return convertView;
    }

    class ViewHolder {
        private TextView id;
        private TextView applicant;
        private TextView time;
    }
}
