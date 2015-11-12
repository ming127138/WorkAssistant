package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.MachineApplyAdapter;
import com.gzrijing.workassistant.adapter.MachineQueryAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.Machine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MachineApplyEditActivity extends BaseActivity implements View.OnClickListener{

    private ImageView iv_delAll;
    private Button btn_add;
    private EditText et_name;
    private EditText et_spec;
    private EditText et_unit;
    private EditText et_id;
    private Button btn_id;
    private EditText et_keyword;
    private Button btn_keyword;
    private ListView lv_apply;
    private ListView lv_query;
    private List<Machine> machineList;
    private List<Machine> machineQueries;
    private MachineApplyAdapter applyAdapter;
    private MachineQueryAdapter queryAdapter;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_apply_edit);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        machineList = (List<Machine>) intent.getSerializableExtra("machineList");
        machineQueries = new ArrayList<Machine>();

    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iv_delAll = (ImageView) findViewById(R.id.machine_apply_edit_del_all_iv);
        btn_add = (Button) findViewById(R.id.machine_apply_edit_add_btn);
        et_name = (EditText) findViewById(R.id.machine_apply_edit_name_et);
        et_spec = (EditText) findViewById(R.id.machine_apply_edit_spec_et);
        et_unit = (EditText) findViewById(R.id.machine_apply_edit_unit_et);
        et_id = (EditText) findViewById(R.id.machine_apply_edit_query_id_et);
        btn_id = (Button) findViewById(R.id.machine_apply_edit_query_id_btn);
        et_keyword = (EditText) findViewById(R.id.machine_apply_edit_query_keyword_et);
        btn_keyword = (Button) findViewById(R.id.machine_apply_edit_query_keyword_btn);
        lv_apply = (ListView) findViewById(R.id.machine_apply_edit_apply_lv);
        applyAdapter = new MachineApplyAdapter(this, machineList);
        lv_apply.setAdapter(applyAdapter);

        lv_query = (ListView) findViewById(R.id.machine_apply_edit_query_lv);
        queryAdapter = new MachineQueryAdapter(this, machineQueries);
        lv_query.setAdapter(queryAdapter);
    }

    private void setListeners() {
        iv_delAll.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_id.setOnClickListener(this);
        btn_keyword.setOnClickListener(this);

        lv_query.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Machine query = machineQueries.get(position);
                Machine apply = new Machine();
                apply.setId(query.getId());
                apply.setName(query.getName());
                apply.setSpec(query.getSpec());
                apply.setUnit(query.getUnit());
                apply.setNum(1);
                apply.setState("新添加");
                machineList.add(apply);
                applyAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.machine_apply_edit_del_all_iv:
                machineList.clear();
                applyAdapter.notifyDataSetChanged();
                break;

            case R.id.machine_apply_edit_add_btn:
                addCustomMachine();
                break;

            case R.id.machine_apply_edit_query_id_btn:
                queryId();
                break;

            case R.id.machine_apply_edit_query_keyword_btn:
                queryKeyword();
                break;
        }
    }

    private void addCustomMachine() {
        String name = et_name.getText().toString().trim();
        String spec = et_spec.getText().toString().trim();
        String unit = et_unit.getText().toString().trim();
        if (name.equals("")) {
            if (mToast == null) {
                mToast = Toast.makeText(this, "请填写名称", Toast.LENGTH_SHORT);
            } else {
                mToast.setText("请填写名称");
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
            return;
        }
        if (spec.equals("")) {
            if (mToast == null) {
                mToast = Toast.makeText(this, "请填写规格", Toast.LENGTH_SHORT);
            } else {
                mToast.setText("请填写规格");
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
            return;
        }
        if (unit.equals("")) {
            if (mToast == null) {
                mToast = Toast.makeText(this, "请填写单位", Toast.LENGTH_SHORT);
            } else {
                mToast.setText("请填写单位");
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
            return;
        }
        Machine machine = new Machine();
        machine.setName(name);
        machine.setSpec(spec);
        machine.setUnit(unit);
        machine.setNum(1);
        machine.setState("新添加");
        machineList.add(machine);
        applyAdapter.notifyDataSetChanged();
        et_name.setText("");
        et_spec.setText("");
        et_unit.setText("");
    }

    private void queryId() {
        machineQueries.clear();
        for (int i = 1; i < 5; i++) {
            Machine query = new Machine();
            query.setId("SQ00" + i);
            query.setName("机械" + i);
            query.setSpec("规格" + i);
            query.setUnit("单位" + i);
            machineQueries.add(query);
        }
        queryAdapter.notifyDataSetChanged();
    }

    private void queryKeyword() {
        machineQueries.clear();
        for (int i = 1; i < 5; i++) {
            Machine query = new Machine();
            query.setId("SQ00" + i);
            query.setName("机械" + i);
            query.setSpec("规格" + i);
            query.setUnit("单位" + i);
            machineQueries.add(query);
        }
        queryAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_machine_apply_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.action_save) {
            Intent intent = getIntent();
            intent.putExtra("machineList", (Serializable) machineList);
            setResult(10, intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
