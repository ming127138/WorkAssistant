package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.SuppliesQuery;

import java.util.List;

public class SuppliesQueryAdapter extends BaseAdapter {

    private LayoutInflater listContainer;
    private List<SuppliesQuery> suppliesQueries;

    public SuppliesQueryAdapter(Context context, List<SuppliesQuery> suppliesQueries) {
        listContainer = LayoutInflater.from(context);
        this.suppliesQueries = suppliesQueries;
    }

    @Override
    public int getCount() {
        return suppliesQueries.size();
    }

    @Override
    public Object getItem(int position) {
        return suppliesQueries.get(position);
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
                    R.layout.listview_item_supplies_query, parent, false);
            v.id = (TextView) convertView.findViewById(R.id.listview_item_supplies_query_id_tv);
            v.name = (TextView) convertView.findViewById(R.id.listview_item_supplies_query_name_tv);
            v.spec = (TextView) convertView.findViewById(R.id.listview_item_supplies_query_spec_tv);
            v.unit = (TextView) convertView.findViewById(R.id.listview_item_supplies_query_unit_tv);
            v.unitPrice = (TextView) convertView.findViewById(R.id.listview_item_supplies_query_unit_price_tv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.id.setText(suppliesQueries.get(position).getId());
        v.name.setText(suppliesQueries.get(position).getName());
        v.spec.setText(suppliesQueries.get(position).getSpec());
        v.unit.setText(suppliesQueries.get(position).getUnit());
        v.unitPrice.setText(suppliesQueries.get(position).getUnitPrice());

        return convertView;
    }

    class ViewHolder {
        private TextView id;
        private TextView name;
        private TextView spec;
        private TextView unit;
        private TextView unitPrice;
    }
}
