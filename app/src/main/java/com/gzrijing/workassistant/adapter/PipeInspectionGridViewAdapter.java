package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.InspectionParameter;
import com.gzrijing.workassistant.view.PipeInspectionMapActivity;

import java.util.Calendar;
import java.util.List;

public class PipeInspectionGridViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater listContainer;
    private final List<Integer> tNums;
    private final String[] months;

    public PipeInspectionGridViewAdapter(
            Context context, List<Integer> tNums, String[] months) {
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.tNums = tNums;
        this.months = months;
    }

    @Override
    public int getCount() {
        return months.length;
    }

    @Override
    public Object getItem(int position) {
        return months[position];
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
                    R.layout.listview_item_pipe_inspection_gridview, parent, false);
            v.tNum = (TextView) convertView.findViewById(
                    R.id.pipe_inspection_gridview_num_tv);
            v.month = (TextView) convertView.findViewById(
                    R.id.pipe_inspection_gridview_month_tv);
            v.ll = (LinearLayout) convertView.findViewById(R.id.pipe_inspection_gridview_ll);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }
        Calendar c = Calendar.getInstance();
        String month = c.get(Calendar.MONTH)+1+"";
        if(month.equals(String.valueOf(position+1))){
            v.tNum.setTextColor(convertView.getResources().getColor(R.color.blue));
            v.month.setTextColor(convertView.getResources().getColor(R.color.blue));
        }else{
            v.tNum.setTextColor(convertView.getResources().getColor(R.color.black));
            v.month.setTextColor(convertView.getResources().getColor(R.color.black));
        }
        v.tNum.setText(tNums.get(position) + "");
        v.month.setText(months[position]);

        v.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PipeInspectionMapActivity.class);
                intent.putExtra("title", months[position]);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView tNum;
        private TextView month;
        private LinearLayout ll;
    }
}
