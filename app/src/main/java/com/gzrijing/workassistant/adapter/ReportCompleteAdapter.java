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
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.ReportCompleteInfo;

import java.util.List;

public class ReportCompleteAdapter extends BaseAdapter {

    private LayoutInflater listContainer;
    private List<ReportCompleteInfo> infos;
    private boolean programmaticalTextChange = false;
    private KeyEvent UnknownKey = new KeyEvent(KeyEvent.ACTION_DOWN,
            KeyEvent.KEYCODE_UNKNOWN);
    private int index = -1;

    public ReportCompleteAdapter(
            Context context, List<ReportCompleteInfo> infos) {
        listContainer = LayoutInflater.from(context);
        this.infos = infos;
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
        convertView = listContainer.inflate(
                R.layout.listview_item_fragment_report_complete, parent, false);
        TextView key = (TextView) convertView.findViewById(
                R.id.listview_item_fragment_report_complete_key_tv);
        EditText value = (EditText) convertView.findViewById(
                R.id.listview_item_fragment_report_complete_value_et);
        key.setText(infos.get(position).getKey());
        value.setText(infos.get(position).getValue());
        value.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String currentPwd = ((EditText) v).getText().toString().trim();
                infos.get(position).setValue(currentPwd);
                return false;
            }
        });
        value.addTextChangedListener(new MyTextWatcher(value));

        value.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    index = position;
                }
                return false;
            }
        });

        value.clearFocus();
        if (index != -1 && index == position) {
            // 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
            value.requestFocus();
        }
        value.setSelection(value.getText().length());
        return convertView;
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
