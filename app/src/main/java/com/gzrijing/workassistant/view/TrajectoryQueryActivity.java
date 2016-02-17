package com.gzrijing.workassistant.view;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.base.BaseActivity;
import com.gzrijing.workassistant.entity.SubordinateLocation;

import java.util.ArrayList;
import java.util.List;

public class TrajectoryQueryActivity extends BaseActivity implements View.OnClickListener {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Button btn_replay;
    private Button btn_exit;
    private List<SubordinateLocation> locInfos;
    private TextView tv_trajectory;
    private List<LatLng> pts = new ArrayList<LatLng>();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    btn_exit.setVisibility(View.VISIBLE);
                    btn_replay.setVisibility(View.VISIBLE);
                    break;

                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_trajectory_query);

        initViews();
        initData();
        setListeners();
    }

    private void initData() {
        initMap();
        initMarker();

    }

    private void initMarker() {
        locInfos = getEmployeeMapInfo();
        for (int i = 0; i < locInfos.size(); i++) {
            LatLng point = new LatLng(locInfos.get(i).getLatitude(), locInfos
                    .get(i).getLongitude());
            // 构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.map_flag_blue);
            // 构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions().position(point).icon(
                    bitmap);
            // 在地图上添加Marker，并显示
            mBaiduMap.addOverlay(option);
        }
    }

    private List<SubordinateLocation> getEmployeeMapInfo() {
        double[] latitudes = {23.043248, 23.045206, 23.044973};
        double[] longitudes = {113.315723, 113.31403, 113.316671};
        String[] names = {"张三", "李四", "王五"};
        String[] tels = {"12345678910", "13579246810", "10987654321"};
        String[] lastTimes = {"2015-5-21  10:20", "2015-5-21  10:20",
                "2015-5-21  10:20"};
        List<SubordinateLocation> locInfos = new ArrayList<SubordinateLocation>();
        for (int i = 0; i < names.length; i++) {
            SubordinateLocation locInfo = new SubordinateLocation();
            locInfo.setLatitude(latitudes[i]);
            locInfo.setLongitude(longitudes[i]);
            locInfo.setName(names[i]);
            locInfo.setTel(tels[i]);
            locInfo.setLastTime(lastTimes[i]);
            locInfos.add(locInfo);
        }
        return locInfos;
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

    private void initViews() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMapView = (MapView) findViewById(R.id.bmapView);
        btn_replay = (Button) findViewById(R.id.trajectory_query_replay_btn);
        btn_exit = (Button) findViewById(R.id.trajectory_query_exit_btn);
    }

    private void setListeners() {
        btn_replay.setOnClickListener(this);
        btn_exit.setOnClickListener(this);

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker markerInfo) {
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.listview_item_trajectory_query, null);
                TextView tv_name = (TextView) view
                        .findViewById(R.id.listview_item_trajectory_query_name_tv);
                TextView tv_tel = (TextView) view
                        .findViewById(R.id.listview_item_trajectory_query_tel_tv);
                TextView tv_lastTime = (TextView) view
                        .findViewById(R.id.listview_item_trajectory_query_last_time_tv);
                tv_trajectory = (TextView) view
                        .findViewById(R.id.listview_item_trajectory_query_trajectory_tv);
                LatLng point = markerInfo.getPosition();
                for (int i = 0; i < locInfos.size(); i++) {
                    if (point.latitude == locInfos.get(i).getLatitude()
                            && point.longitude == locInfos.get(i).getLongitude()) {
                        tv_name.setText("姓名："+locInfos.get(i).getName());
                        tv_tel.setText("电话：" + locInfos.get(i).getTel());
                        tv_lastTime.setText("最后记录时间："
                                + locInfos.get(i).getLastTime());
                    }
                }
                InfoWindow infoWindow = new InfoWindow(view, point, -48);
                mBaiduMap.showInfoWindow(infoWindow);
                tv_trajectory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn_exit.setVisibility(View.GONE);
                        btn_replay.setVisibility(View.GONE);
                        pts = getLatLng();
                        mBaiduMap.clear();
                        // 回放历史轨迹
                        ReplayTrack(pts);
                    }
                });
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.trajectory_query_replay_btn:
                btn_exit.setVisibility(View.GONE);
                btn_replay.setVisibility(View.GONE);
                mBaiduMap.clear();
                ReplayTrack(pts);
                break;

            case R.id.trajectory_query_exit_btn:
                btn_exit.setVisibility(View.GONE);
                btn_replay.setVisibility(View.GONE);
                mBaiduMap.clear();
                initMarker();
                break;

        }

    }

    private void ReplayTrack(final List<LatLng> pts) {
        new Thread() {
            public void run() {
                List<LatLng> points = new ArrayList<LatLng>();
                for (int i = 0; i < pts.size(); i++) {
                    mBaiduMap.clear();
                    if (i == 0) {
                        points.add(pts.get(0));
                    } else {
                        points.add(pts.get(i));
                        OverlayOptions polygonOption = null;
                        polygonOption = new PolylineOptions().points(points)
                                .color(0xAA000000).width(12);
                        mBaiduMap.addOverlay(polygonOption);
                    }
                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.map_flag_blue);
                    OverlayOptions option = new MarkerOptions().position(
                            pts.get(i)).icon(bitmap);
                    mBaiduMap.addOverlay(option);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(1);
            }

            ;
        }.start();

    }

    private List<LatLng> getLatLng() {
        double[] data = {23.043356, 113.31571, 23.043281, 113.315661,
                23.043215, 113.315616, 23.043161, 113.315576, 23.043098,
                113.315526, 23.043036, 113.315486, 23.042974, 113.315441,
                23.042924, 113.315495, 23.042882, 113.315553, 23.042849,
                113.315602, 23.042749, 113.315683, 23.042662, 113.315661,
                23.042591, 113.315593, 23.042546, 113.315549, 23.042483,
                113.315486, 23.042421, 113.315432, 23.042334, 113.315355,
                23.042288, 113.315315, 23.042218, 113.315247, 23.04213,
                113.315157, 23.042068, 113.315063, 23.04208, 113.314969,
                23.042134, 113.314883, 23.04218, 113.314816, 23.042143,
                113.314874, 23.042101, 113.314937, 23.042068, 113.315032,
                23.042101, 113.315121, 23.042155, 113.315189, 23.042222,
                113.315247, 23.042305, 113.315324, 23.042388, 113.315409,
                23.042455, 113.315463, 23.042563, 113.315562, 23.042658,
                113.315656, 23.042775, 113.315678, 23.042849, 113.315602,
                23.042949, 113.315463, 23.043012, 113.315373, 23.04312,
                113.315225, 23.043199, 113.315117, 23.043303, 113.314973,
                23.043373, 113.314829, 23.043307, 113.314722, 23.043132,
                113.314614, 23.042995, 113.314547, 23.042845, 113.314461,
                23.042729, 113.314394, 23.042579, 113.314304, 23.042471,
                113.31425, 23.042338, 113.314174, 23.042205, 113.314093,
                23.042072, 113.314012, 23.041956, 113.313954, 23.041889,
                113.314075, 23.041819, 113.314196, 23.04176, 113.314299,
                23.041677, 113.314434, 23.041606, 113.314564, 23.041523,
                113.31473, 23.04144, 113.31486, 23.04134, 113.314928,
                23.041274, 113.31482, 23.041328, 113.314658, 23.041394,
                113.31451, 23.041461, 113.314362, 23.041519, 113.314218,
                23.041606, 113.314003, 23.041498, 113.313922, 23.041394,
                113.313859, 23.041494, 113.313922, 23.04161, 113.313994,
                23.041673, 113.31385, 23.041756, 113.313738, 23.041885,
                113.313751, 23.041964, 113.31385, 23.041955, 113.313958,
                23.042097, 113.31403, 23.042246, 113.314115, 23.042396,
                113.314205, 23.042537, 113.314281, 23.042683, 113.314371,
                23.042816, 113.314443, 23.042932, 113.314506, 23.043053,
                113.314573, 23.043182, 113.31465, 23.043298, 113.314717,
                23.043365, 113.314816, 23.043336, 113.314928, 23.043252,
                113.31504, 23.043165, 113.315162, 23.043078, 113.315278,
                23.042974, 113.315427, 23.043065, 113.315512, 23.043182,
                113.315584, 23.043248, 113.315723};
        List<LatLng> pts = new ArrayList<LatLng>();
        for (int i = 0; i < data.length; i += 2) {
            LatLng pt = new LatLng(data[i], data[i + 1]);
            pts.add(pt);
        }
        return pts;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
