package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.NoticeAdapter;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.Notice;

import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends BaseActivity {

    private ListView lv_notice;
    private List<Notice> noticeList = new ArrayList<Notice>();
    private NoticeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        for (int i = 1; i < 5; i++) {
            Notice notice = new Notice();
            notice.setTitle("XXXXXX标题"+i);
            notice.setContent("　　XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX内容" + i);
            notice.setPromulgator("发布者" + i);
            notice.setDate("2015-10-29");
            noticeList.add(notice);
        }
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv_notice = (ListView) findViewById(R.id.notice_lv);
        adapter = new NoticeAdapter(this, noticeList);
        lv_notice.setAdapter(adapter);
    }

    private void setListeners() {
        lv_notice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NoticeActivity.this, NoticeContentActivity.class);
                intent.putExtra("notice", noticeList.get(position));
                startActivity(intent);
            }
        });
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
