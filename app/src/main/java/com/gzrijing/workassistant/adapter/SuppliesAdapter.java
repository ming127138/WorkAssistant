package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.Supplies;

import java.util.List;

public class SuppliesAdapter extends BaseAdapter {

    private LayoutInflater listContainer;
    private List<Supplies> suppliesList;

    public SuppliesAdapter(Context context, List<Supplies> suppliesList) {
        listContainer = LayoutInflater.from(context);
        this.suppliesList = suppliesList;
    }

    @Override
    public int getCount() {
        return suppliesList.size();
    }

    @Override
    public Object getItem(int position) {
        return suppliesList.get(position);
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
                    R.layout.listview_item_supplies_supplies, parent, false);
            v.del = (ImageView) convertView.findViewById(R.id.listview_item_supplies_supplies_del_iv);
            v.name = (TextView) convertView.findViewById(R.id.listview_item_supplies_supplies_name_tv);
            v.spec = (TextView) convertView.findViewById(R.id.listview_item_supplies_supplies_spec_tv);
            v.unit = (TextView) convertView.findViewById(R.id.listview_item_supplies_supplies_unit_tv);
            v.num = (TextView) convertView.findViewById(R.id.listview_item_supplies_supplies_num_tv);
            v.up = (ImageView) convertView.findViewById(R.id.listview_item_supplies_supplies_num_up_iv);
            v.down = (ImageView) convertView.findViewById(R.id.listview_item_supplies_supplies_num_down_iv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.name.setText(suppliesList.get(position).getName());
        v.spec.setText(suppliesList.get(position).getSpec());
        v.unit.setText(suppliesList.get(position).getUnit());
        v.num.setText(suppliesList.get(position).getNum() + "");

        v.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(position);
            }
        });

        v.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                up(position);
            }
        });

        v.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                down(position);
            }
        });

        return convertView;
    }

    private void down(int position) {
        int num = suppliesList.get(position).getNum();
        if (num > 1) {
            num--;
            suppliesList.get(position).setNum(num);
            notifyDataSetChanged();
        }
    }

    private void up(int position) {
        int num = suppliesList.get(position).getNum();
        num++;
        suppliesList.get(position).setNum(num);
        notifyDataSetChanged();
    }

    private void delete(int position) {
        suppliesList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }

    class ViewHolder {
        private ImageView del;
        private TextView name;
        private TextView spec;
        private TextView unit;
        private TextView num;
        private ImageView up;
        private ImageView down;
    }
}
