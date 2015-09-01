package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.AcceptanceList;

import java.util.List;

public class AcceptanceAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater listContainer;
    private List<AcceptanceList> acceptanceList;

    public AcceptanceAdapter(Context context, List<AcceptanceList> acceptanceList) {
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.acceptanceList = acceptanceList;
    }

    @Override
    public int getCount() {
        return acceptanceList.size();
    }

    @Override
    public Object getItem(int position) {
        return acceptanceList.get(position);
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
            v.accId = (TextView) convertView.findViewById(R.id.listview_item_acceptance_tv);
            v.calculate = (Button) convertView.findViewById(R.id.listview_item_acceptance_btn);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }
        v.accId.setText(acceptanceList.get(position).getId());
        v.calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView accId;
        private Button calculate;
    }
}
