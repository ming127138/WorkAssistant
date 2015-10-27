package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.view.PrintActivity;

import java.util.List;

public class AcceptanceAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater listContainer;
    private List<String> list;

    public AcceptanceAdapter(Context context, List<String> list) {
        this.context = context;
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
                    R.layout.listview_item_acceptance, parent, false);
            v.accId = (TextView) convertView.findViewById(R.id.listview_item_acceptance_id_tv);
            v.print = (Button) convertView.findViewById(R.id.listview_item_acceptance_print_btn);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }
        v.accId.setText(list.get(position));
        v.print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PrintActivity.class);
                intent.putExtra("flag", 1);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView accId;
        private Button print;
    }
}
