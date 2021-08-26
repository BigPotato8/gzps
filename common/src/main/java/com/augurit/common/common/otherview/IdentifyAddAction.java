package com.augurit.common.common.otherview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.augurit.agmobile.busi.bpm.widget.view.map.arcgis.IMapSelectView;
import com.augurit.agmobile.busi.bpm.widget.view.map.arcgis.MapSelectMode;
import com.augurit.agmobile.busi.bpm.widget.view.map.arcgis.SelectAddrOrFeatureTouchListener;
import com.augurit.agmobile.busi.map.common.IActionContainerBehavior;
import com.augurit.agmobile.busi.ui.action.RightAction;
import com.augurit.agmobile.busi.ui.toolview.CollapseDragView;
import com.augurit.agmobile.common.arcgis.widget.locationmarker.LocationMarker;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.esri.android.map.MapView;

/**
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.sample.commonmap.action
 * @createTime 创建时间 ：2019-05-23
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class IdentifyAddAction extends RightAction{

//    protected IdentifyByMoveMarkerTouchListener mMapOnTouchListener;
    protected SelectAddrOrFeatureTouchListener mMapOnTouchListener;
    protected LocationMarker mLocationMarker;
    protected Context mContext;
    protected IActionContainerBehavior mActionContainerBehavior;
    protected IMapSelectView mIMapSelectView;
    private int state;
    public final static int NORMAL_STATE = 0;
    public final static int CLICK_STATE = 1;
    public IdentifyAddAction(@NonNull String actionName,
                             int actionIconResourceId,
                             int actionHighLightIconResourceId,
                             LocationMarker locationMarker) {
        super(actionName, actionIconResourceId);
        state = NORMAL_STATE;
        // 高亮状态图标显示
        mActionHighlightIconResourceId = mActionHighlightIconResourceIdPad = actionHighLightIconResourceId;
        mLocationMarker = locationMarker;
        mLocationMarker.setVisibility(View.GONE);
    }
        public int getState(){
        return state;
        }
    @Override
    protected void onCreateView(Context context, MapView mapView, IActionContainerBehavior actionContainerBehavior) {
        state = CLICK_STATE;
        mContext = context;
        if (context instanceof IMapSelectView ){
            mIMapSelectView = (IMapSelectView) context;
        }else{
            ToastUtil.shortToast(mContext,"请在activity里面实现IMapSelectView接口!!");
            return;
        }

        if (mMapOnTouchListener == null) {
            mActionContainerBehavior = actionContainerBehavior;
            mMapOnTouchListener = new SelectAddrOrFeatureTouchListener(context ,mIMapSelectView,mapView,
                    mLocationMarker,
                    MapSelectMode.LOC, null);
            mMapOnTouchListener.setIdentifyEnabled(true);
        }

        // 改变toolbar显示
        mActionContainerBehavior.getToolBar().showBack();
        mActionContainerBehavior.getToolBar().setContent("属性查询");
        mActionContainerBehavior.getToolBar().setOnBackClickListener(v -> {
            onBackPressed();
        });

        // 高亮右侧功能按钮图标
        CollapseDragView collapseDragView = (CollapseDragView) mActionContainerBehavior.getCollapseDragView();
        collapseDragView.setActionHighlight(mActionName, true);
        mapView.setOnTouchListener(mMapOnTouchListener);
        mLocationMarker.setVisibility(View.VISIBLE);
    }

    /**
     * Action已开启的情况下，被重复点击
     */
    @Override
    public void onRepeatedClick() {
        super.onRepeatedClick();
        state = NORMAL_STATE;
        // 这里如果再次点击，就退出Action
        mActionManager.exitCurrentAction(true);
    }

    @Override
    protected void onDestroyView() {
        if (mActionContainerBehavior != null) {
            // 还原图标
            CollapseDragView collapseDragView = (CollapseDragView) mActionContainerBehavior.getCollapseDragView();
            collapseDragView.setActionHighlight(mActionName, false);
            // 清除地图及标题状态等
            mMapOnTouchListener.clearMap();
            mLocationMarker.setVisibility(View.GONE);
            mActionContainerBehavior.getToolBar().showHome();
            mActionContainerBehavior.showContainer(IActionContainerBehavior.RIGHT_ACTION);
        }
    }

    @Override
    protected void onInit(Context context, MapView mapView, IActionContainerBehavior actionContainerBehavior) {

    }

    @Override
    public boolean ifShowTitleBar(IActionContainerBehavior actionContainerBehavior) {
        return false;
    }
}
