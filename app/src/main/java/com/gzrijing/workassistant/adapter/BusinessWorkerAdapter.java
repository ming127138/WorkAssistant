package com.gzrijing.workassistant.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
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
import com.gzrijing.workassistant.entity.BusinessByWorker;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.ToastUtil;
import com.gzrijing.workassistant.view.MachineApplyActivity;
import com.gzrijing.workassistant.view.PipeInspectionMapActivity;
import com.gzrijing.workassistant.view.ReportActivity;
import com.gzrijing.workassistant.view.SuppliesApplyActivity;
import com.gzrijing.workassistant.view.TemInfoActivity;
import com.gzrijing.workassistant.view.DetailedInfoActivity;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import org.litepal.crud.DataSupport;

import java.util.List;

public class BusinessWorkerAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater listContainer;
    private List<BusinessByWorker> orderList;
    private String userNo;
    private Handler handler = new Handler();

    public BusinessWorkerAdapter(Context context, List<BusinessByWorker> orderList, String userNo) {
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
                    R.layout.listview_item_business_worker, parent, false);
            v.orderId = (TextView) convertView.findViewById(
                    R.id.listview_item_business_worker_order_id_tv);
            v.urgent = (ImageView) convertView.findViewById(
                    R.id.listview_item_business_worker_urgent_iv);
            v.type = (TextView) convertView.findViewById(
                    R.id.listview_item_business_worker_type_tv);
            v.state = (TextView) convertView.findViewById(
                    R.id.listview_item_business_worker_state_tv);
            v.deadline = (TextView) convertView.findViewById(
                    R.id.listview_item_business_worker_deadline_tv);
            v.temInfo = (TextView) convertView.findViewById(
                    R.id.listview_item_business_worker_tem_info_tv);
            v.suppliesApply = (Button) convertView.findViewById(
                    R.id.listview_item_business_worker_supplies_apply_btn);
            v.machineApply = (Button) convertView.findViewById(
                    R.id.listview_item_business_worker_machine_apply_btn);
            v.info = (Button) convertView.findViewById(
                    R.id.listview_item_business_worker_info_btn);
            v.flag = (TextView) convertView.findViewById(
                    R.id.listview_item_business_worker_flag_tv);
            v.bg_ll = (LinearLayout) convertView.findViewById(
                    R.id.listview_item_business_worker_bg_ll);
            v.btn_rl = (RelativeLayout) convertView.findViewById(
                    R.id.listview_item_business_worker_rl);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.orderId.setText(orderList.get(position).getOrderId());
        v.type.setText(orderList.get(position).getType());
        v.state.setText(orderList.get(position).getState());
        v.deadline.setText(orderList.get(position).getDeadline());

        if (orderList.get(position).getType().equals("供水管网巡查")) {
            v.info.setVisibility(View.GONE);
            v.machineApply.setVisibility(View.GONE);
            v.suppliesApply.setVisibility(View.GONE);
        }
        if (orderList.get(position).isUrgent()) {
            v.urgent.setVisibility(View.VISIBLE);
        } else {
            v.urgent.setVisibility(View.GONE);
        }
        final String flag = orderList.get(position).getFlag();
        v.flag.setText(flag);
        if (flag.equals("确认收到") || flag.equals("处理")) {
            v.bg_ll.setBackgroundResource(R.color.pink_bg);
        } else {
            v.bg_ll.setBackgroundResource(R.color.white);
        }

        v.temInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TemInfoActivity.class);
                intent.putExtra("orderId", orderList.get(position).getOrderId());
                context.startActivity(intent);
            }
        });

        v.suppliesApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SuppliesApplyActivity.class);
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

        v.btn_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.equals("确认收到")) {
                    sendSure(position);
//                    orderList.get(position).setFlag("处理");
//                    ContentValues values = new ContentValues();
//                    values.put("flag", "处理");
//                    DataSupport.updateAll(BusinessData.class, values,
//                            "orderId = ?", orderList.get(position).getOrderId());
//                    notifyDataSetChanged();
                }

                if (flag.equals("处理")) {
                    new AlertDialog.Builder(context).setMessage("确定开始处理？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sendDone(position);
//                                    if (orderList.get(position).getType().equals("供水管网巡检")) {
//                                        orderList.get(position).setFlag("巡检");
//                                        orderList.get(position).setState("正在处理");
//                                        ContentValues values = new ContentValues();
//                                        values.put("flag", "巡检");
//                                        values.put("state", "正在处理");
//                                        DataSupport.updateAll(BusinessData.class, values,
//                                                "orderId = ?", orderList.get(position).getOrderId());
//                                    } else {
//                                        orderList.get(position).setFlag("汇报");
//                                        orderList.get(position).setState("正在处理");
//                                        ContentValues values = new ContentValues();
//                                        values.put("flag", "汇报");
//                                        values.put("state", "正在处理");
//                                        DataSupport.updateAll(BusinessData.class, values,
//                                                "orderId = ?", orderList.get(position).getOrderId());
//                                    }
//                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }

                if (flag.equals("汇报")) {
                    Intent intent = new Intent(context, ReportActivity.class);
                    intent.putExtra("orderId", orderList.get(position).getOrderId());
                    context.startActivity(intent);
                }

                if (flag.equals("巡检")) {
                    Intent intent = new Intent(context, PipeInspectionMapActivity.class);
                    intent.putParcelableArrayListExtra("inspectionList", orderList.get(position).getInspectionInfos());
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
            public void onFinish(final String response) {
                if (response.equals("ok")) {
                    orderList.get(position).setFlag("处理");
                    ContentValues values = new ContentValues();
                    values.put("flag", "处理");
                    DataSupport.updateAll(BusinessData.class, values,
                            "user = ? and orderId = ?", userNo, orderList.get(position).getOrderId());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(context, response, Toast.LENGTH_SHORT);
                        }
                    });
                }
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

    private void sendDone(final int position) {
        RequestBody requestBody = new FormEncodingBuilder()
                .add("cmd", "doinstallreceive")
                .add("userno", userNo)
                .add("fileno", orderList.get(position).getOrderId())
                .build();
        HttpUtils.sendHttpPostRequest(requestBody, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                if (response.equals("ok")) {
                    if (orderList.get(position).getType().equals("供水管网巡查")) {
                        orderList.get(position).setFlag("巡检");
                        orderList.get(position).setState("正在处理");
                        ContentValues values = new ContentValues();
                        values.put("flag", "巡检");
                        values.put("state", "正在处理");
                        DataSupport.updateAll(BusinessData.class, values,
                                "user = ? and orderId = ?", userNo, orderList.get(position).getOrderId());
                    } else {
                        orderList.get(position).setFlag("汇报");
                        orderList.get(position).setState("正在处理");
                        ContentValues values = new ContentValues();
                        values.put("flag", "汇报");
                        values.put("state", "正在处理");
                        DataSupport.updateAll(BusinessData.class, values,
                                "user = ? and orderId = ?", userNo, orderList.get(position).getOrderId());
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            notifyDataSetChanged();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(context, response, Toast.LENGTH_SHORT);
                        }
                    });
                }
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
        private TextView flag;
        private Button suppliesApply;
        private Button machineApply;
        private Button info;
        private LinearLayout bg_ll;
        private RelativeLayout btn_rl;
    }
}
