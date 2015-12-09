package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.DetailedInfo;
import com.gzrijing.workassistant.entity.PicUrl;
import com.gzrijing.workassistant.view.ImageBrowserForLocationActivity;

import java.util.ArrayList;
import java.util.List;

public class DetailedInfoAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater listContainer;
    private List<DetailedInfo> list;
    private ArrayList<PicUrl> picUrls;

    public DetailedInfoAdapter(Context context, List<DetailedInfo> list, ArrayList<PicUrl> picUrls) {
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.list = list;
        this.picUrls = picUrls;
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
        if (picUrls.size() > 0 && position + 1 == list.size()) {
            convertView = listContainer.inflate(
                    R.layout.listview_item_gridview_image, parent, false);
            GridView gv_image = (GridView) convertView.findViewById(R.id.listview_item_gridview_image_gv);
            GridViewImageAdapter adapter = new GridViewImageAdapter(context, picUrls);
            gv_image.setAdapter(adapter);
            gv_image.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (picUrls.size() > 0) {
                        Intent intent = new Intent(context, ImageBrowserForLocationActivity.class);
                        intent.putExtra("position", position);
                        intent.putParcelableArrayListExtra("picUrls", picUrls);
                        context.startActivity(intent);
                    }
                }
            });
        } else {
            convertView = listContainer.inflate(
                    R.layout.listview_item_detailed_info, parent, false);
            TextView key = (TextView) convertView.findViewById(R.id.listview_item_detailed_info_key_tv);
            TextView value = (TextView) convertView.findViewById(R.id.listview_item_detailed_info_value_tv);
            key.setText(list.get(position).getKey());
            value.setText(list.get(position).getValue());
        }
        return convertView;
    }
}
