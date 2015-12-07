package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.BusinessByWorker;
import com.gzrijing.workassistant.entity.Inspection;
import com.gzrijing.workassistant.entity.Marker;

import java.util.ArrayList;
import java.util.List;


public class PipeInspectionMapActivity extends BaseActivity {

    private BaiduMap mBaiduMap;
    private MapView mMapView;
    private List<Inspection> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_pipe_inspection_map);

        initViews();
        initData();
        setListeners();
    }

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMapView = (MapView) findViewById(R.id.bmapView);
    }

    private void initData() {
        initMap();
        initMarker();
    }


    private void initMap() {
        mBaiduMap = mMapView.getMap();
        // 普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 定义Maker坐标点
        LatLng point = new LatLng(23.043248, 113.315723);
        // 定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder().target(point).zoom(18)
                .build();
        // 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
                .newMapStatus(mMapStatus);
        // 改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

    private void initMarker() {
        markers = getMarker();
        for (Inspection marker : markers) {
            LatLng point = new LatLng(marker.getLatitude(), marker.getLongitude());
            BitmapDescriptor bitmap = null;
            if (marker.getType().equals("0")) {
                // 构建Marker图标
                bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.map_flag_green);
            }
            if (marker.getType().equals("1")) {
                bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.map_flag_blue);
            }
            if (marker.getType().equals("2")) {
                bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.map_flag_black);
            }
            // 构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
            // 在地图上添加Marker，并显示
            mBaiduMap.addOverlay(option);
        }
    }

    private List<Inspection> getMarker() {
        Intent intent = getIntent();
        List<Inspection> inspectionList = intent.getParcelableArrayListExtra("inspectionList");

        return inspectionList;

//        String[] ids = {"BH001", "BH002", "BH003", "BH004"};
//        String[] types = {"供水阀门井","供水消防栓","供水阀门井","污水井"};
//        String[] areas = {"A片区","A片区","A片区","A片区"};
//        String[] addrs = {"XXX市XXX区XXX街XXX号","XXX市XXX区XXX街XXX号","XXX市XXX区XXX街XXX号","XXX市XXX区XXX街XXX号"};
//        double[] latitudes = {23.044807, 23.043248, 23.045206, 23.044973};
//        double[] longitudes = {113.314668, 113.315723, 113.31403, 113.316671};
//        List<Marker> markers = new ArrayList<Marker>();
//        for (int i = 0; i < ids.length; i++) {
//            Marker marker = new Marker();
//            marker.setId(ids[i]);
//            marker.setLatitude(latitudes[i]);
//            marker.setLongitude(longitudes[i]);
//            marker.setType(types[i]);
//            marker.setArea(areas[i]);
//            marker.setAddress(addrs[i]);
//            markers.add(marker);
//        }
//        return markers;
    }

    private void setListeners() {
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final com.baidu.mapapi.map.Marker markerInfo) {
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.listview_item_pipe_inspection_map_marker, mMapView, false);
                TextView tv_id = (TextView) view.findViewById(R.id.listview_item_pipe_inspection_map_marker_id_tv);
                TextView tv_name = (TextView) view.findViewById(R.id.listview_item_pipe_inspection_map_marker_name_tv);
                TextView tv_model = (TextView) view.findViewById(R.id.listview_item_pipe_inspection_map_marker_model_tv);
                TextView tv_valveNo = (TextView) view.findViewById(R.id.listview_item_pipe_inspection_map_marker_valveNo_tv);
                TextView tv_valveGNo = (TextView) view.findViewById(R.id.listview_item_pipe_inspection_map_marker_valveGNo_tv);
                TextView tv_address = (TextView) view.findViewById(R.id.listview_item_pipe_inspection_map_marker_address_tv);
                Button btn_inspection = (Button) view.findViewById(R.id.listview_item_pipe_inspection_map_marker_inspection_btn);
                LatLng point = markerInfo.getPosition();
                for (final Inspection marker : markers) {
                    if (point.latitude == marker.getLatitude()
                            && point.longitude == marker.getLongitude()) {
                        tv_id.setText(marker.getNo());
                        tv_name.setText(marker.getName());
                        tv_model.setText(marker.getModel());
                        tv_valveNo.setText(marker.getModel());
                        tv_valveGNo.setText(marker.getValveGNo());
                        tv_address.setText(marker.getAddress());
                        btn_inspection.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(PipeInspectionMapActivity.this, PipeInspectionFormActivity.class);
                                intent.putExtra("id", marker.getNo());
                                intent.putExtra("type", marker.getType());
                                startActivity(intent);
                            }
                        });
                    }
                }
                InfoWindow infoWindow = new InfoWindow(view, point, -48);
                mBaiduMap.showInfoWindow(infoWindow);
                return false;
            }
        });

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }

            @Override
            public void onMapClick(LatLng arg0) {
                mBaiduMap.hideInfoWindow();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pipe_inspection_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.pipe_inspection_overflow_download:
                Intent intent = new Intent(this, DownloadOfflineMapActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

}
