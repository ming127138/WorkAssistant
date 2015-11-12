package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.SubordinateAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.Subordinate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class SubordinateActivity extends BaseActivity {

    private TextView tv_selected;
    private ImageView iv_checkAll;
    private ListView lv_subordinate;
    public static boolean isCheck;
    private List<Subordinate> subordinates;
    private SubordinateAdapter adapter;
    private HashMap<Integer, String> names = new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subordinate);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        subordinates = (List<Subordinate>) intent.getSerializableExtra("subordinates");
        if (subordinates.toString().equals("[]")) {
            for (int i = 1; i < 8; i++) {
                Subordinate subordinate = new Subordinate();
                subordinate.setIsCheck(false);
                subordinate.setName("下属" + i);
                subordinates.add(subordinate);
            }
        }else{
            for(int i=0; i<subordinates.size(); i++){
                if(subordinates.get(i).isCheck()){
                    names.put(i, subordinates.get(i).getName());
                }
            }
        }
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_selected = (TextView) findViewById(R.id.subordinate_selected_tv);
        iv_checkAll = (ImageView) findViewById(R.id.subordinate_check_all_iv);
        lv_subordinate = (ListView) findViewById(R.id.subordinate_lv);
        adapter = new SubordinateAdapter(this, subordinates, iv_checkAll, tv_selected, names);
        lv_subordinate.setAdapter(adapter);
    }

    private void setListeners() {
        iv_checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck) {
                    for (Subordinate subordinate : subordinates) {
                        subordinate.setIsCheck(false);
                    }
                    names.clear();
                    iv_checkAll.setImageResource(R.drawable.login_checkbox_off);
                } else {
                    for (int i = 0; i < subordinates.size(); i++) {
                        subordinates.get(i).setIsCheck(true);
                        names.put(i, subordinates.get(i).getName());
                    }
                    iv_checkAll.setImageResource(R.drawable.login_checkbox_on);
                }
                adapter.notifyDataSetChanged();
                isCheck = !isCheck;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_subordinate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            String executors = tv_selected.getText().toString().trim();
            Intent intent = getIntent();
            intent.putExtra("executors", executors);
            intent.putExtra("list", (Serializable) subordinates);
            setResult(10, intent);
            finish();
            return true;
        }

        if(id == R.id.action_location){
            Intent intent = new Intent(this, SubordinateLocationActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        String executors = tv_selected.getText().toString().trim();
        Intent intent = getIntent();
        intent.putExtra("executors", executors);
        intent.putExtra("list", (Serializable) subordinates);
        setResult(10, intent);
        finish();
    }
}
