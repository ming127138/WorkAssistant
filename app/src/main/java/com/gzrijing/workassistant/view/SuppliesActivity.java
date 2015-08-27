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
import com.gzrijing.workassistant.data.PipeRepairOrderData;
import com.gzrijing.workassistant.data.SuppliesData;
import com.gzrijing.workassistant.entity.Supplies;
import com.gzrijing.workassistant.entity.SuppliesQuery;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SuppliesActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_delAll;
    private Button btn_add;
    private EditText et_name;
    private EditText et_spec;
    private EditText et_unit;
    private EditText et_unitPrice;
    private EditText et_id;
    private Button btn_id;
    private EditText et_keyword;
    private Button btn_keyword;
    private ListView lv_supplies;
    private ListView lv_query;
    private String orderId;
    private List<SuppliesQuery> suppliesQueries;
    private List<Supplies> suppliesList;
    private SuppliesAdapter adapter;
    private SuppliesQueryAdapter queryAdapter;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplies);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        suppliesList = (List<Supplies>) intent.getSerializableExtra("suppliesList");
        suppliesQueries = new ArrayList<SuppliesQuery>();

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
        et_unitPrice = (EditText) findViewById(R.id.supplies_unit_price_et);
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
                SuppliesQuery query = suppliesQueries.get(position);
                Supplies supplies = new Supplies(query.getId(), query.getName(), query.getSpec(),
                        query.getUnit(), query.getUnitPrice(), 1);
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
            SuppliesQuery query = new SuppliesQuery("SQ00" + i, "名称" + i, "规格" + i, "单位" + i, "单价" + i);
            suppliesQueries.add(query);
        }
        queryAdapter.notifyDataSetChanged();
    }

    private void queryKeyword() {
        suppliesQueries.clear();
        for (int i = 1; i < 5; i++) {
            SuppliesQuery query = new SuppliesQuery("SQ00" + i, "名称" + i, "规格" + i, "单位" + i, "单价" + i);
            suppliesQueries.add(query);
        }
        queryAdapter.notifyDataSetChanged();
    }

    private void addCustomSupplies() {
        String name = et_name.getText().toString().trim();
        String spec = et_spec.getText().toString().trim();
        String unit = et_unit.getText().toString().trim();
        String unitPrice = et_unitPrice.getText().toString().trim();
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
        if (unitPrice.equals("")) {
            if (mToast == null) {
                mToast = Toast.makeText(this, "请填写单价", Toast.LENGTH_SHORT);
            } else {
                mToast.setText("请填写单价");
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
            return;
        }
        Supplies supplies = new Supplies();
        supplies.setName(name);
        supplies.setSpec(spec);
        supplies.setUnit(unit);
        supplies.setUnitPrice(unitPrice);
        supplies.setNum(1);
        suppliesList.add(supplies);
        adapter.notifyDataSetChanged();
        et_name.setText("");
        et_spec.setText("");
        et_unit.setText("");
        et_unitPrice.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_supplies, menu);
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
            List<PipeRepairOrderData> orderDatas = DataSupport.where("orderId = ?", orderId).find(PipeRepairOrderData.class);
            List<SuppliesData> datas = orderDatas.get(0).getSuppliesDataList();
            for(SuppliesData data : datas){
                data.delete();
            }
            for (int i = 0; i < suppliesList.size(); i++) {
                SuppliesData data = new SuppliesData();
                data.setNo(suppliesList.get(i).getId());
                data.setName(suppliesList.get(i).getName());
                data.setSpec(suppliesList.get(i).getSpec());
                data.setUnit(suppliesList.get(i).getUnit());
                data.setUnitPrice(suppliesList.get(i).getUnitPrice());
                data.setNum(suppliesList.get(i).getNum());
                data.setPipeRepairOrderData(orderDatas.get(0));
                datas.add(data);
            }
            DataSupport.saveAll(datas);
            orderDatas.get(0).setSuppliesDataList(datas);
            orderDatas.get(0).save();
            Intent intent = getIntent();
            intent.putExtra("suppliesList", (Serializable) suppliesList);
            setResult(10, intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
