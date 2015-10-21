package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.DetailedInfo;

import java.util.List;

public class DetailedInfoAdapter extends BaseAdapter {

    private LayoutInflater listContainer;
    private List<DetailedInfo> list;

    public DetailedInfoAdapter(Context context, List<DetailedInfo> list) {
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
                    R.layout.listview_item_detailed_info, parent, false);
            v.key = (TextView) convertView.findViewById(R.id.listview_item_detailed_info_key_tv);
            v.value = (TextView) convertView.findViewById(R.id.listview_item_detailed_info_value_tv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }
        v.key.setText(list.get(position).getKey());
        v.value.setText(list.get(position).getValue());
        return convertView;
    }

    class ViewHolder {
        private TextView key;
        private TextView value;
    }
}
