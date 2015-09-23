package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.data.MachineData;
import com.gzrijing.workassistant.entity.Machine;

import org.litepal.crud.DataSupport;

import java.util.HashMap;
import java.util.List;

public class MachineReturnEditAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater listContainer;
    private List<Machine> machineList;
    private ImageView iv_checkAll;
    private boolean isCheckAll;
    private HashMap<Integer, Integer> dialogFlag = new HashMap<Integer, Integer>();

    public MachineReturnEditAdapter(
            Context context, List<Machine> machineList, ImageView iv_checkAll, boolean isCheckAll) {
        listContainer = LayoutInflater.from(context);
        this.context = context;
        this.machineList = machineList;
        this.iv_checkAll = iv_checkAll;
        this.isCheckAll = isCheckAll;
    }

    @Override
    public int getCount() {
        return machineList.size();
    }

    @Override
    public Object getItem(int position) {
        return machineList.get(position);
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
                    R.layout.listview_item_machine_return, parent, false);
            v.check = (ImageView) convertView.findViewById(R.id.listview_item_machine_return_check_iv);
            v.name = (TextView) convertView.findViewById(R.id.listview_item_machine_return_name_tv);
            v.spec = (TextView) convertView.findViewById(R.id.listview_item_machine_return_spec_tv);
            v.num = (TextView) convertView.findViewById(R.id.listview_item_machine_return_num_tv);
            v.numUp = (ImageView) convertView.findViewById(R.id.listview_item_machine_return_num_up_iv);
            v.numDown = (ImageView) convertView.findViewById(R.id.listview_item_machine_return_num_down_iv);
            v.ll_type = (LinearLayout) convertView.findViewById(R.id.listview_item_machine_return_type_ll);
            v.tv_type = (TextView) convertView.findViewById(R.id.listview_item_machine_return_type_tv);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }
        v.name.setText(machineList.get(position).getName());
        v.spec.setText(machineList.get(position).getSpec());
        v.num.setText(machineList.get(position).getNum() + "");
        final ViewHolder finalV = v;
        final String[] item = new String[]{"正常", "故障"};
        if(dialogFlag.get(position) == null){
            dialogFlag.put(position, 0);
        }
        v.ll_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setTitle("选择退回类型")
                        .setSingleChoiceItems(item, dialogFlag.get(position), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.e("which", which + "");
                                finalV.tv_type.setText(item[which]);
                                machineList.get(position).setReturnType(item[which]);
                                dialogFlag.put(position, which);
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        final boolean isCheck = machineList.get(position).isCheck();
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
                    machineList.get(position).setIsCheck(false);
                } else {
                    machineList.get(position).setIsCheck(true);
                }
                notifyDataSetChanged();
            }
        });

        for (Machine name : machineList) {
            if (!name.isCheck()) {
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
        int num = machineList.get(position).getNum();
        if (num > 1) {
            num--;
            machineList.get(position).setNum(num);
            notifyDataSetChanged();
        }
    }

    private void up(int position) {
        int num = machineList.get(position).getNum();
        MachineData machineData = DataSupport.find(MachineData.class, machineList.get(position).getDataId());
        if (num < machineData.getNum()) {
            num++;
            machineList.get(position).setNum(num);
            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        private ImageView check;
        private TextView name;
        private TextView spec;
        private TextView num;
        private ImageView numUp;
        private ImageView numDown;
        private LinearLayout ll_type;
        private TextView tv_type;
    }
}
