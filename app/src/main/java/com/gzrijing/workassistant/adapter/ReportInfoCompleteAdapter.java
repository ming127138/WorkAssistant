package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.ReportComplete;
import com.gzrijing.workassistant.entity.ReportInfoComplete;
import com.gzrijing.workassistant.entity.ReportInfoProblem;

import java.util.List;

public class ReportInfoCompleteAdapter extends BaseAdapter {

    private LayoutInflater listContainer;
    private List<ReportInfoComplete> list;

    public ReportInfoCompleteAdapter(Context context, List<ReportInfoComplete> list) {
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
                    R.layout.listview_item_report_info_problem, parent, false);
            v.content = (TextView) convertView.findViewById(R.id.listview_item_report_info_problem_content_tv);
            v.flag = (TextView) convertView.findViewById(R.id.listview_item_report_info_problem_flag_tv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        if (list.get(position).getContent().length() > 10) {
            v.content.setText((position + 1) + "." + list.get(position).getContent().substring(0, 10) + "...");
        } else {
            v.content.setText((position + 1) + "." + list.get(position).getContent());
        }
        if(list.get(position).isFlag()){
            v.flag.setText("已审批");
        }else{
            v.flag.setText("未审批");
        }
        return convertView;
    }

    class ViewHolder {
        private TextView content;
        private TextView flag;
    }
}
