package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.MachineNo;
import com.gzrijing.workassistant.entity.SendMachine;
import com.gzrijing.workassistant.view.QrcodeActivity;

import java.util.List;

public class SendMachineAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater listContainer;
    private List<SendMachine> list;

    public SendMachineAdapter(Context context, List<SendMachine> list) {
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
                    R.layout.listview_item_send_machine, parent, false);
            v.name = (TextView) convertView.findViewById(R.id.listview_item_send_machine_tv);
            v.qrcode = (ImageView) convertView.findViewById(R.id.listview_item_send_machine_iv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.name.setText(list.get(position).getMachineName());
        v.qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QrcodeActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView name;
        private ImageView qrcode;
    }
}
