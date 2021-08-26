package com.augurit.common.common.otherview;

import android.support.v7.app.AppCompatActivity;

//import com.esri.android.map.GraphicsLayer;
//import com.esri.android.map.LocationDisplayManager;
//import com.esri.android.map.MapView;
//import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
//import com.esri.android.map.event.OnStatusChangedListener;

public class LocActivity extends AppCompatActivity {
//
//    //    private static final String strMapUrl = "http://t0.tianditu.com/vec_c/wmts?SERVICE=WMTS&REQUEST=GetCapabilities&tk=5e882b49b3c81c2a3b5f99a4cbc67e8b";
//    private static final String strMapUrl = "http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineCommunity/MapServer";
//    //    private static final String strMapUrl = "http://t0.tianditu.com/cva_c/wmts&tk=5e882b49b3c81c2a3b5f99a4cbc67e8b";
//    private final int WRITE_PERMISSION_REQ_CODE = 100;
////    private ArcGISTiledMapServiceLayer arcGISTiledMapServiceLayer = null;
//
//    private boolean isHasPermission;
//    private TextView mLng;
//    private TextView mLat;
//    private TextView tv_radius, sat_num, sat_qua, loc_type;
//    private MapView mMap_view;
//    private CheckBox mCb_gps;
//    private CheckBox mCb_jz;
//    private CheckBox mCb_net;
//    private Marker mMMarker;
//    private ProgressBar pb_progress;
//    private LocationClient mLocationClient;
//
//    //    private GraphicsLayer mGraphicsLayer;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main_loc);
//        mLng = findViewById(R.id.lng);
//        mLat = findViewById(R.id.lat);
//        mCb_gps = findViewById(R.id.cb_gps);
//        mCb_jz = findViewById(R.id.cb_jz);
//        mCb_net = findViewById(R.id.cb_net);
//        tv_radius = findViewById(R.id.radius);
//        sat_num = findViewById(R.id.sat_num);
//        sat_qua = findViewById(R.id.sat_qua);
//        mMap_view = findViewById(R.id.map_view);
//        loc_type = findViewById(R.id.loc_type);
//        pb_progress = findViewById(R.id.pb_progress);
//        ImageView iv_loc = findViewById(R.id.iv_loc);
//        isHasPermission = checkPublishPermission();
////        aotuLoc(true);
//        locOnceTime();
//
//        iv_loc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                locOnceTime();
//            }
//        });
//        mCb_net.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    loc_type.setText("定位方式:未知");
//                    mCb_jz.setChecked(false);
//                    mCb_gps.setChecked(false);
////                    aotuLoc(true);
////                    LocLoactionManager.getInstance().setIsGps(true);
//                    Toast.makeText(LocActivity.this, "已开启网络定位", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });
//        mCb_gps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    loc_type.setText("定位方式:未知");
//                    mCb_jz.setChecked(false);
//                    mCb_net.setChecked(false);
////                    aotuLoc(true);
////                    LocLoactionManager.getInstance().setIsGps(true);
//                    Toast.makeText(LocActivity.this, "已开启GPS定位", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });
//        mCb_jz.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    loc_type.setText("定位方式:未知");
//                    mCb_gps.setChecked(false);
//                    mCb_net.setChecked(false);
////                    aotuLoc(false);
////                    LocLoactionManager.getInstance().setIsGps(false);
//                    Toast.makeText(LocActivity.this, "已开启混合定位", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//
//    }
//
//
//    private void locOnceTime() {
//        if (isHasPermission) {
//            pb_progress.setVisibility(View.VISIBLE);
//            if (mLocationClient == null) {
//                mLocationClient = new LocationClient(getApplicationContext());
//                setOption();
//                BDAbstractLocationListener bdAbstractLocationListener = new BDAbstractLocationListener() {
//                    @Override
//                    public void onReceiveLocation(BDLocation bdLocation) {
//                        pb_progress.setVisibility(View.GONE);
//                        setView(bdLocation);
//                        mLocationClient.stop(); //停止定位
//                    }
//                };
//                mLocationClient.registerLocationListener(bdAbstractLocationListener);
//                mLocationClient.start();
//            } else {
//                setOption();
//                if (mLocationClient.isStarted()) {
//                    mLocationClient.requestLocation();
//                } else {
//                    mLocationClient.start();
//                }
//
//            }
//
//        } else {
//            isHasPermission = checkPublishPermission();
//        }
//    }
//
//    private void setOption() {
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(mCb_jz.isChecked() ? LocationClientOption.LocationMode.Hight_Accuracy
//                : (mCb_gps.isChecked() ? LocationClientOption.LocationMode.Device_Sensors
//                : LocationClientOption.LocationMode.Battery_Saving));
//        option.setCoorType("bd09ll");
//        option.setScanSpan(0);
//        option.setOpenGps(true);
//        option.setLocationNotify(true);
//        option.setIgnoreKillProcess(false);
//        option.SetIgnoreCacheException(false);
//        option.setWifiCacheTimeOut(5 * 60 * 1000);
//        option.setEnableSimulateGps(false);
//        mLocationClient.setLocOption(option);
//    }
//
//    private void aotuLoc(boolean isGps) {
//        if (isHasPermission) {   //有权限
//            LocLoactionManager.getInstance().startLocate(this, 5, 0, new BDAbstractLocationListener() {
//                @Override
//                public void onReceiveLocation(BDLocation location) {
//                    setView(location);
//                }
//            });
//
//        } else {
//            Toast.makeText(LocActivity.this, "请授予app所需要的权限，否则不可用", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private void setView(BDLocation location) {
//        if (location == null) {
//            Toast.makeText(LocActivity.this, "定位失败", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        double latitude = location.getLatitude();
//        double longitude = location.getLongitude();
//
//
//        BDPointer bdPointer = new BDPointer(latitude, longitude);
//        WGSPointer wgsPointer = bdPointer.toWGSPointer();
//
//        mLng.setText("wgs经度:" + wgsPointer.getLongtitude());
//        mLat.setText("wgs纬度:" + wgsPointer.getLatitude());
//        float radius = location.getRadius();
//        tv_radius.setText("误差半径:" + radius + "米");
//
//        /**
//         * 定位类型
//         BDLocation.TypeGpsLocation----gps定位
//         BDLocation.TypeNetWorkLocation----网络定位(wifi基站定位)
//         以及其他定位失败信息
//         */
//        int locType = location.getLocType();
//
//        if (locType == BDLocation.TypeGpsLocation) {
//            loc_type.setText("定位方式:GPS");
//        } else if (locType == BDLocation.TypeNetWorkLocation) {
//            loc_type.setText("定位方式:网络");
//        } else if (locType == BDLocation.TypeCacheLocation) {
//            loc_type.setText("定位方式:缓存");
//        } else if (locType == BDLocation.TypeOffLineLocation) {
//            loc_type.setText("定位方式:离线");
//        } else {
//            loc_type.setText("定位方式:未知");
//        }
//
//        if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS类型定位结果
//            int satelliteNumber = location.getSatelliteNumber();//卫星数目，gps定位成功最少需要4颗卫星
//            int gpsAccuracyStatus = location.getGpsCheckStatus();//gps质量判断
//            sat_num.setText("卫星数目:" + satelliteNumber);
//            String quaStr;
//            if (gpsAccuracyStatus == BDLocation.GPS_ACCURACY_BAD) {
//                quaStr = "差";
//                sat_qua.setVisibility(View.VISIBLE);
//            } else if (gpsAccuracyStatus == BDLocation.GPS_ACCURACY_GOOD) {
//                quaStr = "好";
//                sat_qua.setVisibility(View.VISIBLE);
//            } else if (gpsAccuracyStatus == BDLocation.GPS_ACCURACY_MID) {
//                quaStr = "中";
//                sat_qua.setVisibility(View.VISIBLE);
//            } else {
//                quaStr = "未知";
//                sat_qua.setVisibility(View.GONE);
//
//            }
//            sat_qua.setText("GPS质量:" + quaStr);
//        } else {
//            sat_num.setText("卫星数目:未知");
//
//            sat_qua.setText("GPS质量:未知");
//            sat_qua.setVisibility(View.GONE);
//        }
//
//        //初始化地图
//        BaiduMap mBaidumap = mMap_view.getMap();
//        //设定中心点坐标
//        LatLng cenpt = new LatLng(latitude, longitude);
//        //定义地图状态
//        MapStatus mMapStatus = new MapStatus.Builder()
//                .target(cenpt)
//                .zoom(18)
//                .build();
//        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
//        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//        //改变地图状态
//        mBaidumap.setMapStatus(mMapStatusUpdate);
//        mBaidumap.setMyLocationEnabled(true);
//
////        if (mMMarker != null) {
////            mMMarker.remove();
////        }
////        //定义Maker坐标点
////        LatLng point = new LatLng(latitude, longitude);
////        //构建Marker图标
////        BitmapDescriptor bitmap = BitmapDescriptorFactory
////                .fromResource(R.mipmap.location_ic_select);
////        //构建MarkerOption，用于在地图上添加Marker
////        OverlayOptions option = new MarkerOptions()
////                .position(point)
////                .icon(bitmap);
////        //在地图上添加Marker，并显示
////        mMMarker = (Marker) mBaidumap.addOverlay(option);
//
//
//        MyLocationData locData = new MyLocationData.Builder()
//                .accuracy(location.getRadius())
//                // 此处设置开发者获取到的方向信息，顺时针0-360
//                .direction(location.getDirection()).latitude(location.getLatitude())
//                .longitude(location.getLongitude()).build();
//        mBaidumap.setMyLocationData(locData);
//    }
//
//
//    private boolean checkPublishPermission() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            List<String> permissions = new ArrayList<>();
//            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            }
//            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
//            }
//            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
//            }
//            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//            }
//            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)) {
//                permissions.add(Manifest.permission.READ_PHONE_STATE);
//            }
//
//            if (permissions.size() != 0) {
//
//                ActivityCompat.requestPermissions(this,
//                        permissions.toArray(new String[0]),
//                        WRITE_PERMISSION_REQ_CODE);
//                return false;
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case WRITE_PERMISSION_REQ_CODE:
//                for (int ret : grantResults) {
//                    if (ret != PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(LocActivity.this, "您已拒绝运行app所需要的权限，请到权限管理打开系统所需要的权限，否则不可用", Toast.LENGTH_LONG).show();
//                        return;
//                    }
//                }
//                isHasPermission = true;
////                aotuLoc(true);
//                locOnceTime();
//                break;
//            default:
//                break;
//        }
//    }
//
//
////    private void dingweilocation() {
////        //定位的方法
////        LocationDisplayManager locationDisplayManager = mMap_view.getLocationDisplayManager();
////
////
//////        (1) COMPASS：定位到你所在的位置（作为中心位置显示）并按照手机所指向的方向旋转地图（非行驶状态）。
//////
//////         （2）LOCATION：自动定位到你的位置（作为中心位置显示）
//////
//////         （3）NAVIGATION:默认情况下，这将图标放置在屏幕底部，并将地图旋转至行驶的方向。
//////
//////         （4）OFF：不会自动定位，它只会简单地显示地图（默认）
////        locationDisplayManager.setAutoPanMode(LocationDisplayManager.AutoPanMode.LOCATION);
////
////        locationDisplayManager.setLocationListener(new LocationListener() {
////            @Override
////            public void onLocationChanged(Location location) {
////
////                String bdlat = location.getLatitude() + "";
////                String bdlon = location.getLongitude() + "";
////                if (bdlat.indexOf("E") == -1 | bdlon.indexOf("E") == -1) {
////                    //这里做个判断是因为，可能因为gps信号问题，定位出来的经纬度不正常。
////                    double latitude = location.getLatitude();//纬度
////                    double longitude = location.getLongitude();//经度
////                    mLng.setText("经度:" + longitude);
////                    mLat.setText("纬度:" + latitude);
////                    mMap_view.centerAt(latitude, longitude, true);
////                    mMap_view.setScale(8000);//设置显示比例
////                }
////
////            }
////
////            @Override
////            public void onStatusChanged(String s, int i, Bundle bundle) {
////
////            }
////
////            @Override
////            public void onProviderEnabled(String s) {
////
////            }
////
////            @Override
////            public void onProviderDisabled(String s) {
////
////            }
////        });
////        locationDisplayManager.start();
////    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
////        BaiduLoactionManager.getInstance().stopLocate();
//    }
}
