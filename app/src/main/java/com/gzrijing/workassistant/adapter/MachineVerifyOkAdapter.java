package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.MachineVerifyInfo;

import java.util.List;

public class MachineVerifyOkAdapter extends BaseAdapter {

    private LayoutInflater listContainer;
    private List<MachineVerifyInfo> mvInfoList;

    public MachineVerifyOkAdapter(Context context, List<MachineVerifyInfo> mvInfoList) {
        listContainer = LayoutInflater.from(context);
        this.mvInfoList = mvInfoList;
    }

    @Override
    public int getCount() {
        return mvInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mvInfoList.get(position);
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
                    R.layout.listview_item_machine_verify_ok_info, parent, false);
            v.name = (TextView) convertView.findViewById(R.id.listview_item_machine_verify_ok_info_name_tv);
            v.spec = (TextView) convertView.findViewById(R.id.listview_item_machine_verify_ok_info_spec_tv);
            v.unit = (TextView) convertView.findViewById(R.id.listview_item_machine_verify_ok_info_unit_tv);
            v.num = (TextView) convertView.findViewById(R.id.listview_item_machine_verify_ok_info_num_tv);
            v.isAgree = (ImageView) convertView.findViewById(R.id.listview_item_machine_verify_ok_info_yes_or_no_iv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.name.setText(mvInfoList.get(position).getName());
        v.spec.setText(mvInfoList.get(position).getSpec());
        v.unit.setText(mvInfoList.get(position).getUnit());
        v.num.setText(mvInfoList.get(position).getNum() + "");

        if(mvInfoList.get(position).isCheck()){
            v.isAgree.setImageResource(R.drawable.icon_ok);
        }else {
            v.isAgree.setImageResource(R.drawable.icon_delete);
        }

        return convertView;
    }

    class ViewHolder {
        private TextView name;
        private TextView spec;
        private TextView unit;
        private TextView num;
        private ImageView isAgree;
    }
}
