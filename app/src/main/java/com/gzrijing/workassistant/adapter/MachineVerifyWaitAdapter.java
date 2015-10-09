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
import com.gzrijing.workassistant.entity.MachineVerifyInfo;

import java.util.List;

public class MachineVerifyWaitAdapter extends BaseAdapter {

    private LayoutInflater listContainer;
    private List<MachineVerifyInfo> mvInfoList;
    private ImageView iv_checkAll;
    private boolean isCheckAll;

    public MachineVerifyWaitAdapter(Context context, List<MachineVerifyInfo> mvInfoList,
                                    ImageView iv_checkAll, boolean isCheckAll) {
        listContainer = LayoutInflater.from(context);
        this.mvInfoList = mvInfoList;
        this.iv_checkAll = iv_checkAll;
        this.isCheckAll = isCheckAll;
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
                    R.layout.listview_item_machine_verify_wait_info, parent, false);
            v.check = (ImageView) convertView.findViewById(R.id.listview_item_machine_verify_wait_info_check_iv);
            v.name = (TextView) convertView.findViewById(R.id.listview_item_machine_verify_wait_info_name_tv);
            v.spec = (TextView) convertView.findViewById(R.id.listview_item_machine_verify_wait_info_spec_tv);
            v.unit = (TextView) convertView.findViewById(R.id.listview_item_machine_verify_wait_info_unit_tv);
            v.num = (TextView) convertView.findViewById(R.id.listview_item_machine_verify_wait_info_num_tv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.name.setText(mvInfoList.get(position).getName());
        v.spec.setText(mvInfoList.get(position).getSpec());
        v.unit.setText(mvInfoList.get(position).getUnit());
        v.num.setText(mvInfoList.get(position).getNum() + "");

        final boolean isCheck = mvInfoList.get(position).isCheck();
        if (isCheck) {
            v.check.setImageResource(R.drawable.spinner_item_check_on);
        } else {
            v.check.setImageResource(R.drawable.spinner_item_check_off);
        }

        v.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck) {
                    mvInfoList.get(position).setIsCheck(false);
                } else {
                    mvInfoList.get(position).setIsCheck(true);
                }
                notifyDataSetChanged();
            }
        });

        for (MachineVerifyInfo mvInfo : mvInfoList) {
            if (!mvInfo.isCheck()) {
                iv_checkAll.setImageResource(R.drawable.spinner_item_check_off);
                isCheckAll = false;
                return convertView;
            }
        }
        iv_checkAll.setImageResource(R.drawable.spinner_item_check_on);
        isCheckAll = true;

        return convertView;
    }

    class ViewHolder {
        private ImageView check;
        private TextView name;
        private TextView spec;
        private TextView unit;
        private TextView num;
    }
}
