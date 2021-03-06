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
//                    loc_type.setText("????????????:??????");
//                    mCb_jz.setChecked(false);
//                    mCb_gps.setChecked(false);
////                    aotuLoc(true);
////                    LocLoactionManager.getInstance().setIsGps(true);
//                    Toast.makeText(LocActivity.this, "?????????????????????", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });
//        mCb_gps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    loc_type.setText("????????????:??????");
//                    mCb_jz.setChecked(false);
//                    mCb_net.setChecked(false);
////                    aotuLoc(true);
////                    LocLoactionManager.getInstance().setIsGps(true);
//                    Toast.makeText(LocActivity.this, "?????????GPS??????", Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });
//        mCb_jz.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    loc_type.setText("????????????:??????");
//                    mCb_gps.setChecked(false);
//                    mCb_net.setChecked(false);
////                    aotuLoc(false);
////                    LocLoactionManager.getInstance().setIsGps(false);
//                    Toast.makeText(LocActivity.this, "?????????????????????", Toast.LENGTH_LONG).show();
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
//                        mLocationClient.stop(); //????????????
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
//        if (isHasPermission) {   //?????????
//            LocLoactionManager.getInstance().startLocate(this, 5, 0, new BDAbstractLocationListener() {
//                @Override
//                public void onReceiveLocation(BDLocation location) {
//                    setView(location);
//                }
//            });
//
//        } else {
//            Toast.makeText(LocActivity.this, "?????????app????????????????????????????????????", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    private void setView(BDLocation location) {
//        if (location == null) {
//            Toast.makeText(LocActivity.this, "????????????", Toast.LENGTH_LONG).show();
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
//        mLng.setText("wgs??????:" + wgsPointer.getLongtitude());
//        mLat.setText("wgs??????:" + wgsPointer.getLatitude());
//        float radius = location.getRadius();
//        tv_radius.setText("????????????:" + radius + "???");
//
//        /**
//         * ????????????
//         BDLocation.TypeGpsLocation----gps??????
//         BDLocation.TypeNetWorkLocation----????????????(wifi????????????)
//         ??????????????????????????????
//         */
//        int locType = location.getLocType();
//
//        if (locType == BDLocation.TypeGpsLocation) {
//            loc_type.setText("????????????:GPS");
//        } else if (locType == BDLocation.TypeNetWorkLocation) {
//            loc_type.setText("????????????:??????");
//        } else if (locType == BDLocation.TypeCacheLocation) {
//            loc_type.setText("????????????:??????");
//        } else if (locType == BDLocation.TypeOffLineLocation) {
//            loc_type.setText("????????????:??????");
//        } else {
//            loc_type.setText("????????????:??????");
//        }
//
//        if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS??????????????????
//            int satelliteNumber = location.getSatelliteNumber();//???????????????gps????????????????????????4?????????
//            int gpsAccuracyStatus = location.getGpsCheckStatus();//gps????????????
//            sat_num.setText("????????????:" + satelliteNumber);
//            String quaStr;
//            if (gpsAccuracyStatus == BDLocation.GPS_ACCURACY_BAD) {
//                quaStr = "???";
//                sat_qua.setVisibility(View.VISIBLE);
//            } else if (gpsAccuracyStatus == BDLocation.GPS_ACCURACY_GOOD) {
//                quaStr = "???";
//                sat_qua.setVisibility(View.VISIBLE);
//            } else if (gpsAccuracyStatus == BDLocation.GPS_ACCURACY_MID) {
//                quaStr = "???";
//                sat_qua.setVisibility(View.VISIBLE);
//            } else {
//                quaStr = "??????";
//                sat_qua.setVisibility(View.GONE);
//
//            }
//            sat_qua.setText("GPS??????:" + quaStr);
//        } else {
//            sat_num.setText("????????????:??????");
//
//            sat_qua.setText("GPS??????:??????");
//            sat_qua.setVisibility(View.GONE);
//        }
//
//        //???????????????
//        BaiduMap mBaidumap = mMap_view.getMap();
//        //?????????????????????
//        LatLng cenpt = new LatLng(latitude, longitude);
//        //??????????????????
//        MapStatus mMapStatus = new MapStatus.Builder()
//                .target(cenpt)
//                .zoom(18)
//                .build();
//        //??????MapStatusUpdate??????????????????????????????????????????????????????
//        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//        //??????????????????
//        mBaidumap.setMapStatus(mMapStatusUpdate);
//        mBaidumap.setMyLocationEnabled(true);
//
////        if (mMMarker != null) {
////            mMMarker.remove();
////        }
////        //??????Maker?????????
////        LatLng point = new LatLng(latitude, longitude);
////        //??????Marker??????
////        BitmapDescriptor bitmap = BitmapDescriptorFactory
////                .fromResource(R.mipmap.location_ic_select);
////        //??????MarkerOption???????????????????????????Marker
////        OverlayOptions option = new MarkerOptions()
////                .position(point)
////                .icon(bitmap);
////        //??????????????????Marker????????????
////        mMMarker = (Marker) mBaidumap.addOverlay(option);
//
//
//        MyLocationData locData = new MyLocationData.Builder()
//                .accuracy(location.getRadius())
//                // ?????????????????????????????????????????????????????????0-360
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
//                        Toast.makeText(LocActivity.this, "??????????????????app???????????????????????????????????????????????????????????????????????????????????????", Toast.LENGTH_LONG).show();
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
////        //???????????????
////        LocationDisplayManager locationDisplayManager = mMap_view.getLocationDisplayManager();
////
////
//////        (1) COMPASS?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
//////
//////         ???2???LOCATION????????????????????????????????????????????????????????????
//////
//////         ???3???NAVIGATION:?????????????????????????????????????????????????????????????????????????????????????????????
//////
//////         ???4???OFF??????????????????????????????????????????????????????????????????
////        locationDisplayManager.setAutoPanMode(LocationDisplayManager.AutoPanMode.LOCATION);
////
////        locationDisplayManager.setLocationListener(new LocationListener() {
////            @Override
////            public void onLocationChanged(Location location) {
////
////                String bdlat = location.getLatitude() + "";
////                String bdlon = location.getLongitude() + "";
////                if (bdlat.indexOf("E") == -1 | bdlon.indexOf("E") == -1) {
////                    //??????????????????????????????????????????gps???????????????????????????????????????????????????
////                    double latitude = location.getLatitude();//??????
////                    double longitude = location.getLongitude();//??????
////                    mLng.setText("??????:" + longitude);
////                    mLat.setText("??????:" + latitude);
////                    mMap_view.centerAt(latitude, longitude, true);
////                    mMap_view.setScale(8000);//??????????????????
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
