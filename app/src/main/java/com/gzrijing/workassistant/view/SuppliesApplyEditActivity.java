package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
import com.gzrijing.workassistant.adapter.SuppliesAdapter;
import com.gzrijing.workassistant.adapter.SuppliesQueryAdapter;
import com.gzrijing.workassistant.data.BusinessData;
import com.gzrijing.workassistant.data.SuppliesData;
import com.gzrijing.workassistant.entity.Supplies;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SuppliesApplyEditActivity extends AppCompatActivity implements View.OnClickListener {

    private String userName;
    private ImageView iv_delAll;
    private Button btn_add;
    private EditText et_name;
    private EditText et_spec;
    private EditText et_unit;
    private EditText et_id;
    private Button btn_id;
    private EditText et_keyword;
    private Button btn_keyword;
    private ListView lv_supplies;
    private ListView lv_query;
    private String orderId;
    private List<Supplies> suppliesQueries;
    private List<Supplies> suppliesList;
    private SuppliesAdapter adapter;
    private SuppliesQueryAdapter queryAdapter;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplies_apply_edit);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        orderId = intent.getStringExtra("orderId");
        suppliesList = (List<Supplies>) intent.getSerializableExtra("suppliesList");
        suppliesQueries = new ArrayList<Supplies>();

    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iv_delAll = (ImageView) findViewById(R.id.supplies_del_all_iv);
        btn_add = (Button) findViewById(R.id.supplies_add_btn);
        et_name = (EditText) findViewById(R.id.supplies_name_et);
        et_spec = (EditText) findViewById(R.id.supplies_spec_et);
        et_unit = (EditText) findViewById(R.id.supplies_unit_et);
        et_id = (EditText) findViewById(R.id.supplies_query_id_et);
        btn_id = (Button) findViewById(R.id.supplies_query_id_btn);
        et_keyword = (EditText) findViewById(R.id.supplies_query_keyword_et);
        btn_keyword = (Button) findViewById(R.id.supplies_query_keyword_btn);
        lv_supplies = (ListView) findViewById(R.id.supplies_supplies_lv);
        adapter = new SuppliesAdapter(this, suppliesList);
        lv_supplies.setAdapter(adapter);

        lv_query = (ListView) findViewById(R.id.supplies_query_lv);
        queryAdapter = new SuppliesQueryAdapter(this, suppliesQueries);
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
                Supplies query = suppliesQueries.get(position);
                Supplies supplies = new Supplies();
                supplies.setId(query.getId());
                supplies.setName(query.getName());
                supplies.setSpec(query.getSpec());
                supplies.setUnit(query.getUnit());
                supplies.setNum(1);
                supplies.setState("申请中");
                suppliesList.add(supplies);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.supplies_del_all_iv:
                suppliesList.clear();
                adapter.notifyDataSetChanged();
                break;

            case R.id.supplies_add_btn:
                addCustomSupplies();
                break;

            case R.id.supplies_query_id_btn:
                queryId();
                break;

            case R.id.supplies_query_keyword_btn:
                queryKeyword();
                break;
        }
    }

    private void queryId() {
        suppliesQueries.clear();
        for (int i = 1; i < 5; i++) {
            Supplies query = new Supplies();
            query.setId("SQ00" + i);
            query.setName("名称" + i);
            query.setSpec("规格" + i);
            query.setUnit("单位" + i);
            suppliesQueries.add(query);
        }
        queryAdapter.notifyDataSetChanged();
    }

    private void queryKeyword() {
        suppliesQueries.clear();
        for (int i = 1; i < 5; i++) {
            Supplies query = new Supplies();
            query.setId("SQ00" + i);
            query.setName("名称" + i);
            query.setSpec("规格" + i);
            query.setUnit("单位" + i);
            suppliesQueries.add(query);
        }
        queryAdapter.notifyDataSetChanged();
    }

    private void addCustomSupplies() {
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
        Supplies supplies = new Supplies();
        supplies.setName(name);
        supplies.setSpec(spec);
        supplies.setUnit(unit);
        supplies.setNum(1);
        supplies.setState("申请中");
        suppliesList.add(supplies);
        adapter.notifyDataSetChanged();
        et_name.setText("");
        et_spec.setText("");
        et_unit.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_supplies_apply_edit, menu);
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
            BusinessData businessData = DataSupport.where("user = ? and orderId = ?", userName, orderId).find(BusinessData.class, true).get(0);
            List<SuppliesData> datas = businessData.getSuppliesDataList();
            for(SuppliesData data : datas){
                if(data.getFlag().equals("创建")){
                    data.delete();
                }
            }
            for (int i = 0; i < suppliesList.size(); i++) {
                SuppliesData data = new SuppliesData();
                data.setNo(suppliesList.get(i).getId());
                data.setName(suppliesList.get(i).getName());
                data.setSpec(suppliesList.get(i).getSpec());
                data.setUnit(suppliesList.get(i).getUnit());
                data.setNum(suppliesList.get(i).getNum());
                data.setState(suppliesList.get(i).getState());
                data.setFlag("创建");
                data.save();
                businessData.getSuppliesDataList().add(data);
            }
            businessData.save();
            Intent intent = getIntent();
            intent.putExtra("suppliesList", (Serializable) suppliesList);
            setResult(10, intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
