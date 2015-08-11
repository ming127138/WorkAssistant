package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.InspectionFault;
import com.gzrijing.workassistant.entity.InspectionFault;
import com.gzrijing.workassistant.view.PipeInspectionFormActivity;

import java.util.List;

public class PipeInspectionFaultAdapter extends BaseAdapter {

    private LayoutInflater listContainer;
    private List<InspectionFault> faults;

    public PipeInspectionFaultAdapter(
            Context context, List<InspectionFault> faults) {
        listContainer = LayoutInflater.from(context);
        this.faults = faults;
    }

    @Override
    public int getCount() {
        return faults.size();
    }

    @Override
    public Object getItem(int position) {
        return faults.get(position);
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
                    R.layout.listview_item_inspection_standard, parent, false);
            v.ll = (LinearLayout) convertView.findViewById(R.id.listview_item_inspection_standard_ll);
            v.checkBox = (ImageView) convertView.findViewById(R.id.listview_item_inspection_standard_checkbox_iv);
            v.fault = (TextView) convertView.findViewById(R.id.listview_item_inspection_standard_content_tv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        final boolean isCheck = faults.get(position).isCheck();
        if(isCheck){
            v.checkBox.setImageResource(R.drawable.login_checkbox_on);
        }else{
            v.checkBox.setImageResource(R.drawable.login_checkbox_off);
        }

        v.fault.setText(faults.get(position).getFault());

        final ImageView iv_checkBox = v.checkBox;
        v.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck) {
                    faults.get(position).setIsCheck(false);
                    iv_checkBox.setImageResource(R.drawable.login_checkbox_off);
                } else {
                    faults.get(position).setIsCheck(true);
                    iv_checkBox.setImageResource(R.drawable.login_checkbox_on);
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    class ViewHolder {
        private LinearLayout ll;
        private ImageView checkBox;
        private TextView fault;
    }
}
