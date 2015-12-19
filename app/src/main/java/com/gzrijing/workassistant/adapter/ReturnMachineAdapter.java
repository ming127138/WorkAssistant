package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.ReturnMachine;
import com.gzrijing.workassistant.entity.SendMachine;
import com.gzrijing.workassistant.view.QrcodeActivity;
import com.gzrijing.workassistant.view.SendMachineInfoActivity;

import java.util.List;

public class ReturnMachineAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater listContainer;
    private List<ReturnMachine> list;

    public ReturnMachineAdapter(Context context, List<ReturnMachine> list) {
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
            v.name = (TextView) convertView.findViewById(R.id.listview_item_send_machine_name_tv);
            v.info = (Button) convertView.findViewById(R.id.listview_item_send_machine_info_btn);
            v.qrcode = (ImageView) convertView.findViewById(R.id.listview_item_send_machine_qrcode_iv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.name.setText(list.get(position).getMachineName());

        v.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SendMachineInfoActivity.class);
                intent.putExtra("flag", "ReturnMachine");
                intent.putExtra("ReturnMachine", (Parcelable) list.get(position));
                context.startActivity(intent);
            }
        });

        v.qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QrcodeActivity.class);
                intent.putExtra("billNo", list.get(position).getBillNo());
                intent.putExtra("flag", "return");
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView name;
        private Button info;
        private ImageView qrcode;
    }
}
