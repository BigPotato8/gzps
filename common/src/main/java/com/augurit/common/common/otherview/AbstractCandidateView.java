package com.augurit.common.common.otherview;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.busi.map.common.IActionContainerBehavior;
import com.augurit.agmobile.busi.map.identify.IIdentifyContract;
import com.augurit.agmobile.common.arcgis.model.AMFindResult;
import com.augurit.agmobile.common.view.bottomsheet.IBottomSheetBehavior;
import com.augurit.common.R;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author 创建人 ：panming
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.common
 * @createTime 创建时间 ：2019-06-04
 * @modifyBy 修改人 ：panming
 * @modifyTime 修改时间 ：2019-06-04
 * @modifyMemo 修改备注：
 */
public abstract  class AbstractCandidateView implements IIdentifyContract.CandidateView{
    protected IActionContainerBehavior actionContainerBehavior;
    protected IIdentifyContract.Presenter presenter;
    protected boolean mInAction;    // true:点查由Action开启 / false:点查是地图默认点击事件

    protected View choosePositionBtn;
    protected View navigationBtn;

    protected View mView;
    protected AMFindResult amFindResultMain;

    protected Context mContext;
    protected IGetBottomData getIGetBottomData() {
        return mIGetBottomData;
    }

    public void setIGetBottomData(IGetBottomData IGetBottomData) {
        mIGetBottomData = IGetBottomData;
    }
    private IGetBottomData mIGetBottomData;

    public interface IGetBottomData{
        //从网络上获取数据
        Observable<HashMap<String,Object>> getDataFromNet(Object...values);
    }

    public AbstractCandidateView(@NonNull IActionContainerBehavior actionContainerBehavior) {
        this(actionContainerBehavior, false);
    }
    public AbstractCandidateView(@NonNull IActionContainerBehavior actionContainerBehavior,
                                 boolean inAction) {
        this.actionContainerBehavior = actionContainerBehavior;
        this.mInAction = inAction;
    }
    @Override
    public void setPresenter(IIdentifyContract.Presenter presenter) {
        this.presenter = presenter;
    }
    @Override
    public Activity getActivity() {
        return actionContainerBehavior.getActivity();
    }
    @Override
    public void collapseContainer() {
        actionContainerBehavior.collapseIdentifyCandidateContainer();
    }

    @Override
    public void hide() {
        actionContainerBehavior.restoreOnBackPressListener();
        if (actionContainerBehavior.isPad()) {
            // 平板
            if (actionContainerBehavior.isContainerVisible(IActionContainerBehavior.CANDIDATE_CONTAINER)) {
                actionContainerBehavior.getToolBar().showHome();
            }
//            if(presenter.getIdentifyEnable()){
//                actionContainerBehavior.getToolBar().showHome();
//            }
            actionContainerBehavior.removeAllViews(IActionContainerBehavior.CANDIDATE_CONTAINER);
            actionContainerBehavior.hideContainer(IActionContainerBehavior.CANDIDATE_CONTAINER);
            actionContainerBehavior.getTitleBar().restoreDefaultBackListener();
            actionContainerBehavior.getToolBar().restoreDefaultContentClickListener();
            actionContainerBehavior.getToolBar().restoreDefaultFoldListener();
            actionContainerBehavior.setFold(false, null);
            actionContainerBehavior.hideBtnFold();
        } else {
            // 手机
            actionContainerBehavior.removeAllViews(IActionContainerBehavior.CANDIDATE_CONTAINER);
            actionContainerBehavior.hideContainer(IActionContainerBehavior.CANDIDATE_CONTAINER);
            actionContainerBehavior.hideContainer(IActionContainerBehavior.BOTTOM_VIEW);
            actionContainerBehavior.getIdentifyBehavior().setState(IBottomSheetBehavior.STATE_COLLAPSED);
            actionContainerBehavior.getIdentifyBehavior().setBottomSheetCallback(null);
//            actionContainerBehavior.hideTitleBar(true);
//            actionContainerBehavior.getTitleBar().getView().setTranslationY(0);
            if(actionContainerBehavior.hasTitleBarGlobal()){
                actionContainerBehavior.getTitleBar().restoreDefaultBackListener();
                actionContainerBehavior.showTitleBar(actionContainerBehavior.getTitleGlobal(),false);
                actionContainerBehavior.getTitleBar().getView().setTranslationY(0);
            }else {
                actionContainerBehavior.hideTitleBar(false);
            }
            if (!mInAction) {
                if(actionContainerBehavior.hasHomeToolBarGlobal()){
                    actionContainerBehavior.getTitleBar().restoreDefaultBackListener();
                    actionContainerBehavior.getToolBar().restoreDefaultContentClickListener();
                    actionContainerBehavior.getToolBar().showHome();
                    actionContainerBehavior.showHomeToolbar(false);
                }else {
                    actionContainerBehavior.hideHomeToolBar(false);
                }
            }
        }
    }
    @Override
    public void showMoreAttributesFragment(String title, String highlighText, LinkedHashMap<String, Object> map) {

    }

    protected void unFoldDetail() {
//        actionContainerBehavior.getToolBar().setOnFoldListener(new IToolBar.OnFoldListener() {
//            @Override
//            public void unfold() {
//                actionContainerBehavior.showContainer(IActionContainerBehavior.CANDIDATE_CONTAINER);
//            }
//
//            @Override
//            public void fold() {
//                actionContainerBehavior.hideContainer(IActionContainerBehavior.CANDIDATE_CONTAINER);
//            }
//        });
        actionContainerBehavior.setFold(false, null);
        actionContainerBehavior.showBtnFold();
    }
    protected Map<String, Object> filterResultAttributes(Map<String, Object> attributes) {
        Map<String, Object> filterAttributes = filter(attributes);
        Map<String, Object> finalAttributes = attributes;
        if(filterAttributes.keySet().size() > 3){
            finalAttributes = filterAttributes;
        }
        return finalAttributes;
    }





    @Override
    public void destroy() {
        actionContainerBehavior = null;
        presenter = null;
        amFindResultMain = null;
    }
    /**
     * 初始化功能按钮
     * 子类可重写此方法来实现自定义功能操作
     * @param bottomView 手机底部view
     */
    protected void initFunctionButtons(View bottomView) {
        if(!actionContainerBehavior.isPad()){
            choosePositionBtn = bottomView.findViewById(R.id.ll_choose_address);
            navigationBtn = bottomView.findViewById(R.id.ll_navigation);
        }else {
            choosePositionBtn = mView.findViewById(R.id.rl_choose_address);
            navigationBtn = mView.findViewById(R.id.rl_navigation);
        }
        choosePositionBtn.setOnClickListener(v -> choosePosition(amFindResultMain));
        navigationBtn.setOnClickListener(v -> navigateToPosition(amFindResultMain));
        setBottomButtonStyle((bottomView.findViewById(R.id.left_imageview)), (bottomView.findViewById(R.id.left_text)), (bottomView.findViewById(R.id.right_imageview)), (bottomView.findViewById(R.id.right_text)));
    }


    @Override
    public void showCandidateList(String highlightText, List<AMFindResult> findResults, OnItemSelectListener showListener) {
       if (findResults == null || findResults.size() == 0) return;
       showBottomView( highlightText,  findResults,  showListener);
        unFoldDetail();//显示折叠按钮
    }

    //底部按钮点击事件
    public  void  choosePosition(AMFindResult amFindResultMain){}

    //底部按钮点击事件
    protected  void navigateToPosition(AMFindResult amFindResultMain){}

    //由子类重写,底部的2个按钮样式和文字
    protected abstract void  setBottomButtonStyle(ImageView leftIv,TextView leftTv,ImageView rightIv,TextView rightText);
    //顯示底部控件內容包括：2個recycleview和上一個下一個控件
    protected abstract void  showBottomView(String highlightText, List<AMFindResult> findResults, OnItemSelectListener showListener);

    protected abstract  LinkedHashMap<String, Object> filter(Map<String, Object> attributes);


    public void restoreTitleBarHeight(){};
}
