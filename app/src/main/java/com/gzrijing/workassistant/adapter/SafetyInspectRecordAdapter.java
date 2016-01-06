package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.Notice;

import java.util.ArrayList;
import java.util.List;

public class SafetyInspectRecordAdapter extends BaseAdapter {

    private LayoutInflater listContainer;
    private ArrayList<String> list;

    public SafetyInspectRecordAdapter(Context context, ArrayList<String> list) {
        listContainer = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
                    R.layout.listview_item_safety_inspect_record, parent, false);
            v.image = (ImageView) convertView.findViewById(R.id.listview_item_safety_inspect_record_image_iv);
            v.name = (TextView) convertView.findViewById(R.id.listview_item_safety_inspect_record_name_tv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.name.setText(list.get(position));
        return convertView;
    }

    class ViewHolder {
        private ImageView image;
        private TextView name;
    }
}
