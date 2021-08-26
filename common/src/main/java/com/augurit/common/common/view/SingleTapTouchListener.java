package com.augurit.common.common.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.augurit.agmobile.busi.bpm.R;
import com.augurit.agmobile.busi.bpm.widget.view.map.arcgis.IMapSelectView;
import com.augurit.agmobile.busi.bpm.widget.view.map.arcgis.MapSelectMode;
import com.augurit.agmobile.busi.map.common.BaseMapOnTouchListener;
import com.augurit.agmobile.busi.map.common.FeatureQueryService;
import com.augurit.agmobile.common.arcgis.model.AMFindResult;
import com.augurit.agmobile.common.arcgis.widget.locationmarker.LocationMarker;
import com.augurit.agmobile.common.lib.location.BaiduRequestAddress;
import com.augurit.agmobile.common.lib.location.IRequestAddress;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.esri.android.map.Callout;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 创建人 ：liangshenghong
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.busi.bpm.widget.view.map
 * @createTime 创建时间 ：2018-05-18
 * @modifyBy 修改人 ：liangshenghong
 * @modifyTime 修改时间 ：2018-05-18
 * @modifyMemo 修改备注：
 */
public class SingleTapTouchListener extends BaseMapOnTouchListener {

    protected LocationMarker mLocationMarker;
    private BaiduRequestAddress mRequestAddress;
    private ProgressDialog progressDialog;
    private IMapSelectView mMapSelectView;
    private GraphicsLayer mGraphicsLayer;    //查询位置图层
    //地图选择模式
    protected @MapSelectMode
    int mSelectMode;
    private boolean initFinished = true;

    protected boolean isSearching = false;
    private boolean isFirst = true;
    protected CompositeDisposable compositeDisposable;

    public SingleTapTouchListener(Context context, IMapSelectView mapSelectView,
                                  MapView view) {
        super(context, view);
        compositeDisposable = new CompositeDisposable();
        mMapSelectView = mapSelectView;
        mRequestAddress = new BaiduRequestAddress();
        mGraphicsLayer = new GraphicsLayer();
//        mapView.addLayer(mPersonGraphicsLayer);
    }

    private Point getMapCenterPoint() {
        //获取当前的位置
        return mapView.getCenter();
//        return mapView.toMapPoint(mLocationMarker.getX() + mLocationMarker.getWidth() / 2,
//                mLocationMarker.getY() + mLocationMarker.getHeight() / 2);
    }


    @Override
    public boolean onDragPointerMove(MotionEvent from, MotionEvent to) {
//        if (mapView.getCallout().isShowing()) {
//            mapView.getCallout().hide();
//        }
//        mMapSelectView.hideQueryResultView();
//
//        if(mPersonGraphicsLayer != null){
//            mPersonGraphicsLayer.removeAll();
//        }
        return super.onDragPointerMove(from, to);
    }

    @Override
    public boolean onDragPointerUp(MotionEvent from, MotionEvent to) {
        return super.onDragPointerUp(from, to);
    }

    @Override
    public boolean onPinchPointersUp(MotionEvent event) {
        if (event == null) {
            return true;
        }
        return super.onPinchPointersUp(event);
    }

    @Override
    public boolean onDoubleTap(MotionEvent point) {
        return super.onDoubleTap(point);
    }

    @Override
    public boolean onSingleTap(MotionEvent point) {
        if (mapView.getCallout().isShowing()) {
            mapView.getCallout().hide();
        }
        mMapSelectView.hideQueryResultView();
        int[] arr = mGraphicsLayer.getGraphicIDs();
        if(mGraphicsLayer != null){
            mGraphicsLayer.removeAll();
        }
        if(arr != null && arr.length > 0){
            return false;
        }
        if (initFinished && point != null) {
            final Point pointTemp = mapView.toMapPoint(point.getX(), point.getY());
            queryFeature(pointTemp);
        }
        return super.onSingleTap(point);
    }

    /**
     * 查询图层要素
     */
    private void queryFeature(Point point) {
//        showCalloutMessage("查询中...", null, null);
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("正在查询设施...");
            progressDialog.setCancelable(false);
        }

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        Observable<List<AMFindResult>> observable = null;

        Geometry geometry = GeometryEngine.buffer(point, mapView.getSpatialReference(), 40 * mapView.getResolution(), null);
        observable = new FeatureQueryService().queryFeatures(geometry, mMapSelectView.getLayerUrls());

        Disposable disposable = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(amFindResults -> {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    isSearching = false;
                    if (ListUtil.isEmpty(amFindResults) && mSelectMode == MapSelectMode.LOC_AND_FEATURE) {
                        ToastUtil.shortToast(context, "查无数据！");
                    } else if (mMapSelectView != null) {
//                        mLocationMarker.setVisibility(View.GONE);
                        if (mapView.getCallout().isShowing()) {
                            mapView.getCallout().hide();
                        }
                        try {
                            mMapSelectView.onQueryFeature(amFindResults);
                            if (!ListUtil.isEmpty(amFindResults)) {
                                drawPoint((Point) amFindResults.get(0).getGeometry());
                            }
                        } catch (Exception e) {
                            Log.d("SingleTapTouchListener","queryFeature1"+e.toString());
                            ToastUtil.shortToast(context, "显示查询结果出错！");
                        }
                        initFinished = true;
                    }
                }, throwable -> {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (!isSearching) {
                        return;
                    }
                    if (!initFinished) {
                        ToastUtil.longToast(context, "查询出错！");
                        initFinished = true;
                        return;
                    }
                    if (mSelectMode == MapSelectMode.LOC_AND_FEATURE) {
                        requestAddress(point.getX(), point.getY());
                    } else if (mMapSelectView != null) {
                        if (mapView.getCallout().isShowing()) {
                            mapView.getCallout().hide();
                        }
                        try {
                            mMapSelectView.onQueryFeature(new ArrayList<>());
                        } catch (Exception e) {
                            Log.d("SingleTapTouchListener","queryFeature2"+e.toString());
                            ToastUtil.shortToast(context, "显示查询结果出错！");
                        }
                        initFinished = true;
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void drawPoint(Point point) {
        if(isFirst) {
            mapView.addLayer(mGraphicsLayer);
            isFirst = false;
        }
        mGraphicsLayer.removeAll();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.widget_map_ic_my_location);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        mapView.centerAt(point, false);
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(bitmapDrawable);
        pictureMarkerSymbol.setOffsetY(7);
        Graphic graphic = new Graphic(point, pictureMarkerSymbol);
        mGraphicsLayer.addGraphic(graphic);
    }

    /**
     * 逆地理编码，用百度地图的API去获取经纬度对应的详细地址
     *
     * @param longitude
     * @param latitude
     */
    private void requestAddress(double longitude, double latitude) {
        Disposable disposable = mRequestAddress.parseLocation(latitude, longitude, IRequestAddress.COORD_TYPE_WGS84LL)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(address -> {
                    showCalloutMessage(address.getDetailAddress(), null, view -> {
                        if (mMapSelectView != null) {
                            mMapSelectView.onSelectAddress(address);
                        }
                    });
                    initFinished = true;
                }, Throwable::printStackTrace);
        compositeDisposable.add(disposable);
    }


    /**
     * @param message                        要显示的文本
     * @param point                          callout显示的位置，为null时取locationMarker的上方位置
     * @param calloutSureButtonClickListener "确定"按钮点击事件，为null时不显示“确定”按钮
     */
    private void showCalloutMessage(String message, Point point, final View.OnClickListener calloutSureButtonClickListener) {
        if (TextUtils.isEmpty(message)) {
            message = "查无位置信息";
        }
        final Callout callout = mapView.getCallout();
        View view = View.inflate(context, R.layout.view_map_identify_move_by_marker_callout, null);
        TextView title = view.findViewById(R.id.tv_listcallout_title);
        title.setText(message);
        View sureBtn = view.findViewById(R.id.btn_select_device);
        sureBtn.setOnClickListener(view1 -> {
            if (mapView.getCallout().isShowing()) {
                mapView.getCallout().hide();
            }
            if (calloutSureButtonClickListener != null) {
                calloutSureButtonClickListener.onClick(view1);
            }
        });
        if (calloutSureButtonClickListener == null) {
            view.findViewById(R.id.divider).setVisibility(View.GONE);
            sureBtn.setVisibility(View.GONE);
        }
        callout.setStyle(R.xml.editmap_callout_style);
        callout.setContent(view);
        if (point == null) {
            point = mapView.toMapPoint(mLocationMarker.getX() + mLocationMarker.getWidth() / 2,
                    mLocationMarker.getIvLocation().getTop());
        }
        callout.show(point);
    }


    @Override
    public void clearMap() {

    }

    @Override
    public void onDestroy() {
        if(compositeDisposable!=null){
            compositeDisposable.clear();
            compositeDisposable=null;
        }
    }

    @Override
    public void setIdentifyEnabled(boolean enabled) {

    }

    @Override
    public void pauseIdentify() {

    }

    @Override
    public void restoreIdentfy() {

    }

}
