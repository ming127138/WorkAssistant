package com.gzrijing.workassistant.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.entity.InspectionParameter;
import com.gzrijing.workassistant.view.PipeInspectionFormActivity;

import java.util.List;

public class PipeInspectionParameterAdapter extends BaseAdapter {

    private LayoutInflater listContainer;
    private List<InspectionParameter> parameters;
    private boolean programmaticalTextChange = false;
    private KeyEvent UnknownKey = new KeyEvent(KeyEvent.ACTION_DOWN,
            KeyEvent.KEYCODE_UNKNOWN);

    public PipeInspectionParameterAdapter(
            Context context, List<InspectionParameter> parameters) {
        listContainer = LayoutInflater.from(context);
        this.parameters = parameters;
    }

    @Override
    public int getCount() {
        return parameters.size();
    }

    @Override
    public Object getItem(int position) {
        return parameters.get(position);
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
                    R.layout.listview_item_inspection_parameter, parent, false);
            v.key = (TextView) convertView.findViewById(
                    R.id.listview_item_inspection_parameter_key_tv);
            v.value = (EditText) convertView.findViewById(
                    R.id.listview_item_inspection_parameter_value_et);
            convertView.setTag(v);
        } else {
            v = (ViewHolder) convertView.getTag();
        }
        v.key.setText(parameters.get(position).getKey());
        v.value.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String currentPwd = ((EditText) v).getText().toString().trim();
                parameters.get(position).setValue(currentPwd);
                return false;
            }
        });
        v.value.addTextChangedListener(new MyTextWatcher(v.value));
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

    class ViewHolder {
        private TextView key;
        private EditText value;
    }
}
