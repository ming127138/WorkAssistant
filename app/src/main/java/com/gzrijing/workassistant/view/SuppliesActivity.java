package com.gzrijing.workassistant.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.gzrijing.workassistant.R;

public class SuppliesActivity extends AppCompatActivity implements View.OnClickListener{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplies);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {

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
        lv_query = (ListView) findViewById(R.id.supplies_query_lv);
    }

    private void setListeners() {
        iv_delAll.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_id.setOnClickListener(this);
        btn_keyword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.supplies_del_all_iv:

            break;

        case R.id.supplies_add_btn:

            break;

        case R.id.supplies_query_id_btn:

            break;

        case R.id.supplies_query_keyword_btn:

            break;
        }
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

        return super.onOptionsItemSelected(item);
    }
}
