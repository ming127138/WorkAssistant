package com.gzrijing.workassistant.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.db.BusinessData;
import com.gzrijing.workassistant.entity.BusinessByLeader;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.ToastUtil;
import com.gzrijing.workassistant.view.BusinessHaveSendActivity;
import com.gzrijing.workassistant.view.BusinessLeaderByMyOrderActivity;
import com.gzrijing.workassistant.view.MachineApplyActivity;
import com.gzrijing.workassistant.view.ProgressActivity;
import com.gzrijing.workassistant.view.DistributeActivity;
import com.gzrijing.workassistant.view.ReportInfoCompleteActivity;
import com.gzrijing.workassistant.view.SuppliesVerifyActivity;
import com.gzrijing.workassistant.view.TemInfoActivity;
import com.gzrijing.workassistant.view.DetailedInfoActivity;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import org.litepal.crud.DataSupport;

import java.util.List;

public class BusinessLeaderAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater listContainer;
    private List<BusinessByLeader> orderList;
    private String userNo;
    private Handler handler = new Handler();

    public BusinessLeaderAdapter(Context context, List<BusinessByLeader> orderList, String userNo) {
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.orderList = orderList;
        this.userNo = userNo;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
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
                    R.layout.listview_item_business_leader, parent, false);
            v.orderId = (TextView) convertView.findViewById(
                    R.id.listview_item_business_leader_order_id_tv);
            v.completeInfo = (Button) convertView.findViewById(
                    R.id.listview_item_business_leader_complete_info_btn);
            v.suppliesQuery = (Button) convertView.findViewById(
                    R.id.listview_item_business_leader_query_supplies_btn);
            v.machineApply = (Button) convertView.findViewById(
                    R.id.listview_item_business_leader_machine_apply_btn);
            v.progress = (Button) convertView.findViewById(
                    R.id.listview_item_business_leader_progress_btn);
            v.info = (Button) convertView.findViewById(
                    R.id.listview_item_business_leader_info_btn);
            v.haveSend = (Button) convertView.findViewById(
                    R.id.listview_item_business_leader_have_send_btn);
            v.myOrder = (Button) convertView.findViewById(
                    R.id.listview_item_business_leader_my_order_btn);
            v.urgent = (ImageView) convertView.findViewById(
                    R.id.listview_item_business_leader_urgent_iv);
            v.type = (TextView) convertView.findViewById(
                    R.id.listview_item_business_leader_type_tv);
            v.state = (TextView) convertView.findViewById(
                    R.id.listview_item_business_leader_state_tv);
            v.deadline = (TextView) convertView.findViewById(
                    R.id.listview_item_business_leader_deadline_tv);
            v.temInfo = (TextView) convertView.findViewById(
                    R.id.listview_item_business_leader_tem_info_tv);
            v.flag = (TextView) convertView.findViewById(
                    R.id.listview_item_business_leader_flag_tv);
            v.bg_ll = (LinearLayout) convertView.findViewById(
                    R.id.listview_item_business_leader_bg_ll);
            v.btn_rl = (RelativeLayout) convertView.findViewById(
                    R.id.listview_item_business_leader_rl);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.orderId.setText(orderList.get(position).getOrderId());
        v.type.setText(orderList.get(position).getType());
        v.state.setText(orderList.get(position).getState());
        v.deadline.setText(orderList.get(position).getDeadline());
        v.temInfo.setText(orderList.get(position).getTemInfoNum()+"条新临时信息");

        if (orderList.get(position).isUrgent()) {
            v.urgent.setVisibility(View.VISIBLE);
        } else {
            v.urgent.setVisibility(View.GONE);
        }

        v.completeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReportInfoCompleteActivity.class);
                intent.putExtra("orderId", orderList.get(position).getOrderId());
                context.startActivity(intent);
            }
        });

        v.suppliesQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SuppliesVerifyActivity.class);
                intent.putExtra("orderId", orderList.get(position).getOrderId());
                context.startActivity(intent);
            }
        });

        v.machineApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MachineApplyActivity.class);
                intent.putExtra("orderId", orderList.get(position).getOrderId());
                context.startActivity(intent);
            }
        });

        v.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedInfoActivity.class);
                intent.putExtra("orderId", orderList.get(position).getOrderId());
                context.startActivity(intent);
            }
        });

        v.progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProgressActivity.class);
                intent.putExtra("orderId", orderList.get(position).getOrderId());
                context.startActivity(intent);
            }
        });

        v.haveSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BusinessHaveSendActivity.class);
                intent.putExtra("orderId", orderList.get(position).getOrderId());
                context.startActivity(intent);
            }
        });

        v.myOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BusinessLeaderByMyOrderActivity.class);
                intent.putExtra("orderId", orderList.get(position).getOrderId());
                context.startActivity(intent);
            }
        });

        v.temInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TemInfoActivity.class);
                intent.putExtra("orderId", orderList.get(position).getOrderId());
                context.startActivity(intent);
            }
        });

        final String flag = orderList.get(position).getFlag();
        v.flag.setText(flag);
        if (flag.equals("确认收到")) {
            v.bg_ll.setBackgroundResource(R.color.pink_bg);
        } else {
            v.bg_ll.setBackgroundResource(R.color.white);
        }

        v.btn_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.equals("确认收到")) {
                    sendSure(position);
                }
                if (flag.equals("派工")) {
                    Intent intent = new Intent(context, DistributeActivity.class);
                    intent.putExtra("orderId", orderList.get(position).getOrderId());
                    context.startActivity(intent);
                }
            }
        });

        return convertView;
    }

    private void sendSure(final int position) {
        RequestBody requestBody = new FormEncodingBuilder()
                .add("cmd", "doreceive")
                .add("userno", userNo)
                .add("fileno", orderList.get(position).getOrderId())
                .build();
        HttpUtils.sendHttpPostRequest(requestBody, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                orderList.get(position).setFlag("派工");
                ContentValues values = new ContentValues();
                values.put("flag", "派工");
                DataSupport.updateAll(BusinessData.class, values,
                        "user = ? and orderId = ?", userNo, orderList.get(position).getOrderId());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(context, "与服务器断开连接", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }

    class ViewHolder {
        private TextView orderId;
        private ImageView urgent;
        private TextView type;
        private TextView state;
        private TextView deadline;
        private TextView temInfo;
        private Button completeInfo;
        private Button suppliesQuery;
        private Button machineApply;
        private Button progress;
        private Button info;
        private Button haveSend;
        private Button myOrder;
        private TextView flag;
        private LinearLayout bg_ll;
        private RelativeLayout btn_rl;
    }
}
