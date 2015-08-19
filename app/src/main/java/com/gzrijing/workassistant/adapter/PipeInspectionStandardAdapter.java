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
import com.gzrijing.workassistant.entity.InspectionStandard;
import com.gzrijing.workassistant.view.PipeInspectionFormActivity;

import java.util.List;

public class PipeInspectionStandardAdapter extends BaseAdapter {

    private LayoutInflater listContainer;
    private List<InspectionStandard> standards;
    private ImageView iv_checkAll;

    public PipeInspectionStandardAdapter(
            Context context, List<InspectionStandard> standards, ImageView iv_checkAll) {
        listContainer = LayoutInflater.from(context);
        this.standards = standards;
        this.iv_checkAll = iv_checkAll;
    }

    @Override
    public int getCount() {
        return standards.size();
    }

    @Override
    public Object getItem(int position) {
        return standards.get(position);
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
            v.standard = (TextView) convertView.findViewById(R.id.listview_item_inspection_standard_content_tv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        final boolean isCheck = standards.get(position).isCheck();
        if(isCheck){
            v.checkBox.setImageResource(R.drawable.login_checkbox_on);
        }else{
            v.checkBox.setImageResource(R.drawable.login_checkbox_off);
        }

        v.standard.setText(standards.get(position).getStandard());

        v.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck) {
                    standards.get(position).setIsCheck(false);
                } else {
                    standards.get(position).setIsCheck(true);
                }
                notifyDataSetChanged();
            }
        });

        for(InspectionStandard standard : standards){
            if(!standard.isCheck()){
                iv_checkAll.setImageResource(R.drawable.login_checkbox_off);
                PipeInspectionFormActivity.isCheck = false;
                return convertView;
            }
        }
        iv_checkAll.setImageResource(R.drawable.login_checkbox_on);
        PipeInspectionFormActivity.isCheck = true;
        return convertView;
    }

    class ViewHolder {
        private LinearLayout ll;
        private ImageView checkBox;
        private TextView standard;
    }
}
