package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.SafetyInspectFailItem;
import com.gzrijing.workassistant.entity.SubordinateLocation;
import com.gzrijing.workassistant.listener.HttpCallbackListener;
import com.gzrijing.workassistant.util.HttpUtils;
import com.gzrijing.workassistant.util.ToastUtil;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;

public class SafetyInspectFailItemAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater listContainer;
    private ArrayList<SafetyInspectFailItem> list;
    private String[] workerItem;
    private HashMap<Integer, boolean[]> mapList = new HashMap<Integer, boolean[]>();
    private Handler handler = new Handler();

    public SafetyInspectFailItemAdapter(Context context, ArrayList<SafetyInspectFailItem> list,
                                        ArrayList<SubordinateLocation> workerList) {
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.list = list;
        workerItem = new String[workerList.size()];
        for(int i = 0; i < workerList.size(); i++){
            workerItem[i] = workerList.get(i).getName();
        }

        for(int i = 0; i < list.size(); i++){
            boolean[] checkedItems = new boolean[workerItem.length];
            mapList.put(i, checkedItems);
        }
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
                    R.layout.listview_item_safety_inspect_fail_item, parent, false);
            v.content = (TextView) convertView.findViewById(R.id.listview_item_safety_inspect_fail_item_content_tv);
            v.isHandle = (TextView) convertView.findViewById(R.id.listview_item_safety_inspect_fail_item_is_handle_tv);
            v.worker = (TextView) convertView.findViewById(R.id.listview_item_safety_inspect_fail_item_worker_tv);
            v.remark = (EditText) convertView.findViewById(R.id.listview_item_safety_inspect_fail_item_remark_et);
            v.submit = (Button) convertView.findViewById(R.id.listview_item_safety_inspect_fail_item_submit_btn);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }

        v.content.setText(list.get(position).getContent());
        v.worker.setText(list.get(position).getWorker());
        String flag = list.get(position).getIsHandle();
        if(flag.equals("1")){
            v.isHandle.setText("已处理");
            v.submit.setVisibility(View.GONE);
        }else {
            v.isHandle.setText("未处理");
        }

        v.worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean[] checkedItems = mapList.get(position);
                new AlertDialog.Builder(context).setTitle("选择整改人：").setMultiChoiceItems(
                        workerItem, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                checkedItems[which] = isChecked;
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < checkedItems.length; i++) {
                                    if (checkedItems[i]) {
                                        sb.append(workerItem[i] + ",");
                                    }
                                }
                                if(sb.length() > 0){
                                    String str = sb.toString().substring(0, sb.length() - 1);
                                    list.get(position).setWorker(str);
                                    notifyDataSetChanged();
                                }
                            }
                        }).show();
            }
        });

        final ViewHolder finalV = v;
        v.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalV.remark.getText().toString();
                RequestBody requestBody = new FormEncodingBuilder()
                        .add("cmd", "safeinf_handle")
                        .add("RecordId", list.get(position).getRecordId())
                        .add("UserNo", list.get(position).getWorker())
                        .add("UserInf", "abcd")
                        .build();

                Log.e("RecordId", list.get(position).getRecordId());
                Log.e("UserNo", list.get(position).getWorker());

                HttpUtils.sendHttpPostRequest(requestBody, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        Log.e("response", response);
                        if(response.equals("Ok")){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showToast(context, "提交成功", Toast.LENGTH_SHORT);
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
        });

        return convertView;
    }

    class ViewHolder {
        private TextView content;
        private TextView isHandle;
        private TextView worker;
        private EditText remark;
        private Button submit;
    }
}
