package com.gzrijing.workassistant.view;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.adapter.DownloadOfflineMapAdapter;
import com.gzrijing.workassistant.entity.LocalMap;
import com.gzrijing.workassistant.widget.MyProgressBar;

import java.util.ArrayList;
import java.util.List;

public class DownloadOfflineMapActivity extends Activity implements MKOfflineMapListener {

    private MKOfflineMap mOffline;
    private ListView lv_Package;
    /**
     * 已下载的离线地图信息列表
     */
    private ArrayList<MKOLUpdateElement> localMapList = null;
    private DownloadOfflineMapAdapter adapter;
    private List<LocalMap> localMaps;
    private MyProgressBar mProgressBar;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_offline_map);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//显示返回icon

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        mOffline = new MKOfflineMap();
        mOffline.init(this);

        localMaps = new ArrayList<LocalMap>();
        ArrayList<MKOLSearchRecord> cityList = mOffline.getOfflineCityList();
        for(MKOLSearchRecord province : cityList){
            if(province.cityID == 1){
                localMaps.add(new LocalMap(1, "全国概略图", province.size, 0));
            }
            if(province.cityID == 8){
                ArrayList<MKOLSearchRecord> citys = province.childCities;
                for(MKOLSearchRecord city : citys){
                    if(city.cityID == 187){
                        localMaps.add(new LocalMap(187, "中山市", city.size, 0));
                    }
                }
            }
        }

        // 获取已下过的离线地图信息
        localMapList = mOffline.getAllUpdateInfo();
        if (localMapList == null) {
            localMapList = new ArrayList<MKOLUpdateElement>();
        }

        for (MKOLUpdateElement e : localMapList) {
            if (e.cityID == 1) {
                localMaps.get(0).setRatio(e.ratio);
                localMaps.get(0).setSize(e.serversize);
            }
            if (e.cityID == 187) {
                localMaps.get(1).setRatio(e.ratio);
                localMaps.get(1).setSize(e.serversize);
            }
        }

    }

    private void initViews() {
        lv_Package = (ListView) findViewById(R.id.download_offline_map_lv);
        adapter = new DownloadOfflineMapAdapter(this, localMaps, mOffline);
        lv_Package.setAdapter(adapter);

    }

    private void setListeners() {

    }

    @Override
    public void onGetOfflineMapState(int type, int cityId) {
        switch (type) {
            case MKOfflineMap.TYPE_DOWNLOAD_UPDATE: {
                MKOLUpdateElement update = mOffline.getUpdateInfo(cityId);
                // 处理下载进度更新提示
                if (update != null) {
                    for(LocalMap city : localMaps){
                        if(city.getCityId() == cityId){
                            city.setRatio(update.ratio);
                        }
                    }
                    updateView();
                }
            }
            break;
            case MKOfflineMap.TYPE_NEW_OFFLINE:
                // 有新离线地图安装
                break;
            case MKOfflineMap.TYPE_VER_UPDATE:
                // 版本更新提示
                // MKOLUpdateElement e = mOffline.getUpdateInfo(state);

                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mOffline.destroy();
        super.onDestroy();
    }

    public void updateView() {
        adapter.notifyDataSetChanged();
    }
}
