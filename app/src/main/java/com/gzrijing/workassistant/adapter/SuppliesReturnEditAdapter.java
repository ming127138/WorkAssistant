package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.db.SuppliesData;
import com.gzrijing.workassistant.entity.Supplies;

import org.litepal.crud.DataSupport;

import java.util.List;

public class SuppliesReturnEditAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater listContainer;
    private List<Supplies> suppliesList;
    private ImageView iv_checkAll;
    private boolean isCheckAll;

    public SuppliesReturnEditAdapter(
            Context context, List<Supplies> suppliesList, ImageView iv_checkAll, boolean isCheckAll) {
        listContainer = LayoutInflater.from(context);
        this.context = context;
        this.suppliesList = suppliesList;
        this.iv_checkAll = iv_checkAll;
        this.isCheckAll = isCheckAll;
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
                    R.layout.listview_item_supplies_return, parent, false);
            v.check = (ImageView) convertView.findViewById(R.id.listview_item_supplies_return_check_iv);
            v.name = (TextView) convertView.findViewById(R.id.listview_item_supplies_return_name_tv);
            v.spec = (TextView) convertView.findViewById(R.id.listview_item_supplies_return_spec_tv);
            v.unit = (TextView) convertView.findViewById(R.id.listview_item_supplies_return_unit_tv);
            v.num = (TextView) convertView.findViewById(R.id.listview_item_supplies_return_num_tv);
            v.numUp = (ImageView) convertView.findViewById(R.id.listview_item_supplies_return_num_up_iv);
            v.numDown = (ImageView) convertView.findViewById(R.id.listview_item_supplies_return_num_down_iv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }
        v.name.setText(suppliesList.get(position).getName());
        v.spec.setText(suppliesList.get(position).getSpec());
        v.spec.setText(suppliesList.get(position).getUnit());
        v.num.setText(suppliesList.get(position).getNum() + "");

        final boolean isCheck = suppliesList.get(position).isCheck();
        if (isCheck) {
            v.check.setImageResource(R.drawable.spinner_item_check_on);
        } else {
            v.check.setImageResource(R.drawable.spinner_item_check_off);
        }

        v.numUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                up(position);
            }
        });

        v.numDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                down(position);
            }
        });

        v.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck) {
                    suppliesList.get(position).setIsCheck(false);
                } else {
                    suppliesList.get(position).setIsCheck(true);
                }
                notifyDataSetChanged();
            }
        });

        for (Supplies supplies : suppliesList) {
            if (!supplies.isCheck()) {
                iv_checkAll.setImageResource(R.drawable.spinner_item_check_off);
                isCheckAll = false;
                return convertView;
            }
        }
        iv_checkAll.setImageResource(R.drawable.spinner_item_check_on);
        isCheckAll = true;
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
        SuppliesData SuppliesData = DataSupport.find(SuppliesData.class, suppliesList.get(position).getDataId());
        if (num < SuppliesData.getNum()) {
            num++;
            suppliesList.get(position).setNum(num);
            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        private ImageView check;
        private TextView name;
        private TextView spec;
        private TextView unit;
        private TextView num;
        private ImageView numUp;
        private ImageView numDown;
    }
}
