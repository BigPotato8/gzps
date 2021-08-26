package com.augurit.common.common.otherview;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.augurit.agmobile.busi.map.common.BaseMapOnTouchListener;
import com.augurit.agmobile.busi.map.common.IActionContainerBehavior;
import com.augurit.agmobile.busi.map.common.IMapManager;
import com.augurit.agmobile.busi.map.common.MapUISetting;
import com.augurit.agmobile.busi.map.identify.IIdentifyContract;
import com.augurit.agmobile.busi.map.identify.IdentifyPresenter;
import com.augurit.agmobile.busi.map.identify.IdentifyUtils;
import com.augurit.agmobile.busi.map.identify.candidate.IdentifyCandidateView;
import com.augurit.agmobile.busi.map.identify.source.IIdentifyDataSource;
import com.augurit.agmobile.busi.map.identify.source.IdentifyDataSourceImpl;
import com.augurit.agmobile.busi.map.identify.view.callout.IdentifyCalloutView;
import com.augurit.agmobile.busi.ui.MapUIManager;
import com.augurit.agmobile.common.arcgis.model.AMFindResult;
import com.augurit.agmobile.common.arcgis.model.LayerInfo;
import com.augurit.agmobile.common.arcgis.util.GeometryUtil;
import com.augurit.agmobile.common.arcgis.util.ZoomUtil;
import com.augurit.agmobile.common.lib.common.AppUtils;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.Symbol;

import java.util.List;

import io.reactivex.Observable;

/**
 * 点查点击事件
 *
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.busi.ui.identify
 * @createTime 创建时间 ：18/3/14
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：18/3/14
 * @modifyMemo 修改备注：
 */
//todo xcl 当在小级别下，应该查询的是面，当缩放到大级别时，才优先查点。缺少测试图层以及arcgis 返回的数据显示为空
public class IdentifyBaseMapOnTouchListener extends BaseMapOnTouchListener implements IIdentifyContract.MapView {

    private final String TAG = this.getClass().getSimpleName();

    protected Context mApplicationContext;
    /**
     * 用于显示高亮Identify结果
     */
    protected GraphicsLayer mGLayer;
    protected IMapManager mIMapManager;
    protected IActionContainerBehavior mActionContainerBehavior;

    protected IIdentifyContract.Presenter mIdentifyContainerPresenter;
    /**
     * 是否隐藏Callout
     */
    protected boolean mHideCallout;
    /**
     * 是否允许点查
     */
    protected boolean mIfEnabled;
    private IIdentifyContract.CalloutView mCalloutView;
    private IIdentifyContract.CandidateView mCandidateView;
    protected IIdentifyDataSource mDataSource;
    private View layerProj;
    private View idtfView;
    private View addAndCheck;
    private MapUIManager mMapUIManager;
    private boolean isShowTools = true;

    public IdentifyBaseMapOnTouchListener(Context context,
                                          MapView view,
                                          IMapManager mapManager,
                                          IActionContainerBehavior actionContainerBehavior) {
        //todo 修改为先查离线数据，如果查询离线数据为空再查在线数据
        this(context, view, mapManager, actionContainerBehavior, new IdentifyDataSourceImpl()/*new IdentifyArcgisDataSource()*/);
    }

    public IdentifyBaseMapOnTouchListener(Context context,
                                          MapView view,
                                          IMapManager mapManager,
                                          IActionContainerBehavior actionContainerBehavior,
                                          IIdentifyDataSource dataSource) {
        super(context, view);
        this.mApplicationContext = AppUtils.getApp();
        this.mIMapManager = mapManager;
        this.mActionContainerBehavior = actionContainerBehavior;
        this.mDataSource = dataSource;
//        IIdentifyContract.CalloutView calloutView = new IdentifyCalloutView(context, mapManager);
//        IIdentifyContract.CandidateView candidateView = new IdentifyCandidateView(actionContainerBehavior);
//        this.mIdentifyContainerPresenter = new IdentifyPresenter(getCalloutView(), getCandidateView(), this, dataSource, mapManager);
//        this.mActionContainerBehavior = actionContainerBehavior;
//        setIdentifyEnabled(true);
//        isMapOnTouch = true;
    }

    public void setIdentifyCandidateViewAndCalloutView(IIdentifyContract.CandidateView candidateView, IIdentifyContract.CalloutView calloutView) {
        this.mCandidateView = candidateView;
        this.mCalloutView = calloutView;
        if (this.mCandidateView == null) {
            this.mCandidateView = new IdentifyCandidateView(this.mActionContainerBehavior);
        }
        if (this.mCalloutView == null) {
            this.mCalloutView = new IdentifyCalloutView(context, this.mIMapManager);
        }

        this.mIdentifyContainerPresenter = new IdentifyPresenter(getCalloutView(), getCandidateView(), this, this.mDataSource, this.mIMapManager);
        mIdentifyContainerPresenter.setOnlyQueryVisibleLayer(false);    // 查询不可见图层
        setIdentifyEnabled(true);
        isMapOnTouch = true;
    }

    private IIdentifyContract.CalloutView getCalloutView() {
        return mCalloutView;
    }

    private IIdentifyContract.CandidateView getCandidateView() {
        return mCandidateView;
    }

    @Override
    public void setPresenter(IIdentifyContract.Presenter presenter) {
        mIdentifyContainerPresenter = presenter;
    }

    @Override
    public Activity getActivity() {
        return null;
    }

    protected void initGLayer() {

        if (mGLayer == null) {
            //  mGLayer = new GraphicsLayer(GraphicsLayer.RenderingMode.STATIC);
            mGLayer = new GraphicsLayer();
//            mGLayer.setSelectionColor(Color.BLUE);
//            mGLayer.setSelectionColorWidth(3);
            mapView.addLayer(mGLayer);
        }
    }

    /**
     * 控制高亮层GraphicsLayer的添加和清除
     *
     * @param enabled
     */
    @Override
    public void setIdentifyEnabled(boolean enabled) {
        this.mIfEnabled = enabled;
        if (!enabled) {//如果不允许点查，则清除用于高亮的GraphicsLayer
            resetGLayer();
            mIdentifyContainerPresenter.hide();
        } else {//如果允许点查，则添加用于高亮的GraphicsLayer，并设置样式
            initGLayer();
        }
    }

    @Override
    public void pauseIdentify() {

    }

    @Override
    public void restoreIdentfy() {

    }

    private void resetGLayer() {
        if (mGLayer != null) {
            mGLayer.removeAll();
        }
    }


    @Override
    public void highLight(AMFindResult findResult) {
        Geometry geometry = findResult.getGeometry();
        highLight(geometry);
    }


    /**
     * 清空地图界面
     */
    @Override
    public void clearMap() {
        mHideCallout = true;
        resetGLayer();
        mIdentifyContainerPresenter.hide();
    }

    @Override
    public Observable<List<LayerInfo>> getAllVisibleLayers() {
        return Observable.just(mIMapManager.getAllVisibleLayers());
    }

    @Override
    public SpatialReference getSpatialReference() {

        return mapView.getSpatialReference();
    }

    @Override
    public int getWidth() {

        return mapView.getWidth();
    }

    @Override
    public int getHeight() {

        return mapView.getHeight();
    }

    @Override
    public Envelope getEnvelope() {
        Envelope extent = new Envelope();
        mapView.getExtent().queryEnvelope(extent);
        return extent;
    }

    @Override
    public void removeAllGraphic() {
        initGLayer();
        mGLayer.removeAll();
    }

    @Override
    public boolean isAbleToShow() {
        return mIfEnabled;
    }

    @Override
    public boolean isMapOnTouch() {
        return isMapOnTouch;
    }

    @Override
    public void centerAt(AMFindResult findResult) {
        Point point = GeometryUtil.getGeometryCenter(findResult.getGeometry());
        if (point == null) return;
        if (mActionContainerBehavior.isPad()
                && mActionContainerBehavior.isContainerVisible(IActionContainerBehavior.CANDIDATE_CONTAINER)) {
            int orientation = mActionContainerBehavior.getUISetting().getLayoutOrientation();
            Point centerPoint = ZoomUtil.getCenterPointInVisibleArea(point, mapView,
                    mActionContainerBehavior.getToolBar().getView(),
                    orientation == MapUISetting.LayoutOrientation.RIGHT ? Gravity.RIGHT : Gravity.LEFT);
            mapView.centerAt(centerPoint, true);
        } else {
            mapView.centerAt(point, true);
        }
    }

    @Override
    public void setHideCallout(boolean hideCallout) {
        mHideCallout = hideCallout;
    }

    @Override
    public void onDestroy() {
        clearMap();
        mapView = null;
        mApplicationContext = null;

        mIMapManager = null;
        if (mIdentifyContainerPresenter != null) {
            mIdentifyContainerPresenter.unsubscribe();
            mIdentifyContainerPresenter = null;
        }
    }

    @Override
    public boolean onSingleTap(MotionEvent point) {

        if (mIMapManager == null && !mIfEnabled) {
            return false;
        }
        if (mIMapManager != null && !mIfEnabled) {
            if (!isShowTools) {
                showAnimY(mMapUIManager.getTitleBar().getView());
                showAnim(layerProj);
                showAnim(idtfView);
                showAnim(addAndCheck);
                isShowTools = true;
            } else {
                hideAnimY(mMapUIManager.getTitleBar().getView());
                hideAnim(layerProj);
                hideAnim(idtfView);
                hideAnim(addAndCheck);
                isShowTools = false;
            }
            return true;
        }

        //如果没有达到允许点查的级别，屏蔽点击事件
        if (maxScaleAllowToIdentify != 0 && mapView.getScale() > maxScaleAllowToIdentify) {
            return false;
        }

        Point mapPoint = mapView.toMapPoint(point.getX(), point.getY());
        mIdentifyContainerPresenter.selectGeometry(mapPoint);
        return true;
    }


    @Override
    public boolean onFling(MotionEvent from, MotionEvent to, float velocityX, float velocityY) {

        if (validateMotionEvent(from, to)) {
            return true;
        }

        if (mHideCallout) {
            clearMap();
        }
        return super.onFling(from, to, velocityX, velocityY);
    }

    @Override
    public boolean onDragPointerMove(MotionEvent from, MotionEvent to) {

        if (validateMotionEvent(from, to)) {
            return true;
        }

        if (mHideCallout) {
            clearMap();
        }
        return super.onDragPointerMove(from, to);
    }


    private boolean validateMotionEvent(MotionEvent from, MotionEvent to) {
        return from == null || to == null;
    }


    /**
     * 高亮
     *
     * @param geometry 要高亮的图形
     */
    private void highLight(Geometry geometry) {
        Symbol symbol = IdentifyUtils.getSymbol(geometry, mApplicationContext);
        Graphic graphic = new Graphic(geometry, symbol);
        initGLayer();
        mGLayer.removeAll();
        int i = mGLayer.addGraphic(graphic);
//        int[] ids = new int[]{i};
//        mGLayer.setSelectedGraphics(ids, true);
        //判断数据量多不多，多的话进行提示
        //大于10万个点
        //        if (pointCount >= 100000) {
        //            ToastUtil.longToast(mApplicationContext, mApplicationContext.getString(R.string.identify_result_is_too_large_please_wait));
        //        }
    }

    private void showAnim(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 200, 0);
        animator.setDuration(1000);
        animator.start();

    }


    private void showAnimY(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", -200, 0);
        animator.setDuration(1000);
        animator.start();
    }

    private void hideAnim(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", 0, 200);
        animator.setDuration(1000);
        animator.start();
    }

    private void hideAnimY(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0, -200);
        animator.setDuration(1000);
        animator.start();

    }

    public void setToolsView(View layerProj, View idtfView, View addAndCheck, MapUIManager mMapUIManager) {
        this.layerProj = layerProj;
        this.idtfView = idtfView;
        this.addAndCheck = addAndCheck;
        this.mMapUIManager = mMapUIManager;
    }
}
