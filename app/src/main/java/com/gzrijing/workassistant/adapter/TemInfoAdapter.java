package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gzrijing.workassistant.R;

import java.util.List;

public class TemInfoAdapter extends BaseAdapter {

    private LayoutInflater listContainer;
    private List<String> list;

    public TemInfoAdapter(Context context, List<String> list) {
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
                    R.layout.listview_item_tem_info, parent, false);
            v.info = (TextView) convertView.findViewById(R.id.listview_item_tem_info_tv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }
        if(list.get(position).length()>8){
            v.info.setText((position + 1) + "." + list.get(position).substring(0, 8) + "...");
        }else {
            v.info.setText((position + 1) + "." + list.get(position));
        }
        return convertView;
    }

    class ViewHolder {
        private TextView info;
    }
}
