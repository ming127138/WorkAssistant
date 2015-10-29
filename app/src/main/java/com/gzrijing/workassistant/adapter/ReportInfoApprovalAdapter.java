package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.ReportComplete;
import com.gzrijing.workassistant.entity.Supplies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportInfoApprovalAdapter extends BaseAdapter {

    private HashMap<Integer, View> lmap = new HashMap<Integer, View>();
    private List<Supplies> suppliesList;
    private Context context;
    private LayoutInflater listContainer;
    private List<ReportComplete> infos;
    private boolean programmaticalTextChange = false;
    private KeyEvent UnknownKey = new KeyEvent(KeyEvent.ACTION_DOWN,
            KeyEvent.KEYCODE_UNKNOWN);
    private int index = -1;
    private SuppliesAdapter adapter;

    public ReportInfoApprovalAdapter(
            Context context, List<ReportComplete> infos) {
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.infos = infos;

        suppliesList = new ArrayList<Supplies>();
        for (int i = 1; i < 5; i++) {
            Supplies supplies = new Supplies();
            supplies.setName("名称" + i);
            supplies.setSpec("规格" + i);
            supplies.setUnit("单位" + i);
            supplies.setNum(i);
            suppliesList.add(supplies);
        }
    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String key = infos.get(position).getKey();
        if (key.equals("水表有效日期") || key.equals("排水时间") || key.equals("施工日期") || key.equals("完工日期") || key.equals("验收日期")) {
            if (lmap.get(position) == null) {
                convertView = listContainer.inflate(
                        R.layout.listview_item_fragment_report_complete_date, parent, false);
                TextView tv_key = (TextView) convertView.findViewById(
                        R.id.listview_item_fragment_report_complete_date_key_tv);
                TextView tv_value = (TextView) convertView.findViewById(
                        R.id.listview_item_fragment_report_complete_date_value_tv);
                tv_key.setText(key);
                if (tv_value != null) {
                    tv_value.setText(infos.get(position).getValue());
                }
                lmap.put(position, convertView);
            } else {
                convertView = lmap.get(position);
            }
            return convertView;
        } else if (position + 1 == infos.size()) {
            if (lmap.get(position) == null) {
                convertView = listContainer.inflate(
                        R.layout.listview_item_fragment_report_complete_supplies, parent, false);
                ListView lv_supplies = (ListView) convertView.findViewById(R.id.listview_item_fragment_report_complete_supplies_lv);
                adapter = new SuppliesAdapter(context, suppliesList);
                lv_supplies.setAdapter(adapter);
                lmap.put(position, convertView);
            } else {
                convertView = lmap.get(position);
            }
            return convertView;
        } else {
            if (key.equals("排水口径")) {
                if (infos.get(position).getValue() == null || infos.get(position).getValue().equals("")) {
                    infos.get(position).setValue("DN");
                }
            }

            convertView = listContainer.inflate(
                    R.layout.listview_item_fragment_report_complete_edittext, parent, false);
            TextView tv_key = (TextView) convertView.findViewById(
                    R.id.listview_item_fragment_report_complete_edittext_key_tv);
            EditText et_value = (EditText) convertView.findViewById(
                    R.id.listview_item_fragment_report_complete_edittext_value_et);
            getET(position, tv_key, et_value);
            return convertView;
        }
    }

    private void getET(final int position, TextView tv_key, EditText et_value) {
        tv_key.setText(infos.get(position).getKey());
        et_value.setText(infos.get(position).getValue());
        et_value.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String currentPwd = ((EditText) v).getText().toString().trim();
                infos.get(position).setValue(currentPwd);
                return false;
            }
        });
        et_value.addTextChangedListener(new MyTextWatcher(et_value));

        et_value.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    index = position;
                }
                return false;
            }
        });

        et_value.clearFocus();
        if (index != -1 && index == position) {
            // 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
            et_value.requestFocus();
        }
        et_value.setSelection(et_value.getText().length());
    }

    public class MyTextWatcher implements TextWatcher {
        private EditText editText;

        public MyTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            if (!programmaticalTextChange)
                editText.dispatchKeyEvent(UnknownKey);
        }
    }

}