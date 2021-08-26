package com.augurit.common.common.otherview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.busi.map.common.IActionContainerBehavior;
import com.augurit.agmobile.busi.map.identify.view.attributelist.AttributeListAdapter;
import com.augurit.agmobile.common.arcgis.model.AMFindResult;
import com.augurit.agmobile.common.lib.common.Callback5;
import com.augurit.agmobile.common.view.bottomsheet.AnchorSheetBehavior;
import com.augurit.agmobile.common.view.bottomsheet.IBottomSheetBehavior;
import com.augurit.agmobile.common.view.pagergrid.PagerGridLayoutManager;
import com.augurit.agmobile.common.view.pagergrid.PagerGridSnapHelper;
import com.augurit.agmobile.common.view.videopicker.utils.ScreenUtils;
import com.augurit.common.R;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import skin.support.content.res.SkinCompatResources;

/**
 * @author 创建人 ：panming
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.common
 * @createTime 创建时间 ：2019-06-04
 * @modifyBy 修改人 ：panming
 * @modifyTime 修改时间 ：2019-06-04
 * @modifyMemo 修改备注：
 * 针对底部只显示简单的信息视图，即一个列表，不展示详细的信息
 */
public abstract  class ComplexCandidateView extends AbstractCandidateView{

    public ComplexCandidateView(@NonNull IActionContainerBehavior actionContainerBehavior, Context context) {
        super(actionContainerBehavior);
        mContext = context;
    }
    @Override
    protected void setBottomButtonStyle(ImageView leftIv, TextView leftTv, ImageView rightIv, TextView rightText) {
        ((View) rightIv.getParent()).setVisibility(View.GONE);
        rightIv.setVisibility(View.GONE);
        rightText.setVisibility(View.GONE);
        ((View) leftTv.getParent()).setVisibility(View.GONE);
        leftIv.setVisibility(View.GONE);
        leftTv.setVisibility(View.GONE);
    }



    @Override
    protected void showBottomView(String highlightText, List<AMFindResult> findResults, OnItemSelectListener showListener) {
        mView = LayoutInflater.from(actionContainerBehavior.getContext())
                .inflate(R.layout.identify_candidate_view, null);

        // 详情列表
        RecyclerView rv_attributes = mView.findViewById(R.id.rv_attributes);

        //直接从地图服务上获取数据则不需要调用此方法
//        HashMap<String,Object> data = null;
        //自己实现
        if (null != getIGetBottomData()) {
            //否则，判断当前是否有可见图层，如果有，显示加载中的callout，并进行点查操作
            getIGetBottomData().getDataFromNet(findResults.get(0).getValue(), null)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(objectHashMap -> {
                        if (objectHashMap != null && objectHashMap.size()>0)
                            findResults.get(0).getAttributes().putAll(objectHashMap);
                        handUISHow(highlightText, findResults, showListener, rv_attributes);
                    });
        } else {
            handUISHow(highlightText, findResults, showListener, rv_attributes);
        }
    }


    private void handUISHow(String highlightText, List<AMFindResult> findResults, OnItemSelectListener showListener, RecyclerView rv_attributes) {


//        if (!actionContainerBehavior.isFold()) {
//            if (rv_attributes.canScrollVertically(-1)) { //的值表示是否能向下滚动，false表示已经滚动到顶部
//                rv_attributes.requestDisallowInterceptTouchEvent(false);
//            } else {
//                rv_attributes.requestDisallowInterceptTouchEvent(true);
//            }
//        }

        amFindResultMain = findResults.get(0);
        actionContainerBehavior.setContentBottomSheetAllowPullUp(false);
        actionContainerBehavior.setContentBottomSheetAllowDragdown(false);
        AttributeListAdapter attributeAdapter = new AttributeListAdapter(mView.getContext(),
                filterResultAttributes(findResults.get(0).getAttributes()), highlightText);
        rv_attributes.setAdapter(attributeAdapter);
        rv_attributes.setLayoutManager(new LinearLayoutManager(mView.getContext()));

        // 候选项分页
        ViewGroup cl_header = mView.findViewById(R.id.cl_header);
        RecyclerView rv_candidate = mView.findViewById(R.id.rv_candidate);
        View btn_previous = mView.findViewById(R.id.btn_previous);
        View btn_next = mView.findViewById(R.id.btn_next);
        PagerGridLayoutManager pagerGridLayoutManager = new PagerGridLayoutManager(1, 1, PagerGridLayoutManager.HORIZONTAL);
        rv_candidate.setLayoutManager(pagerGridLayoutManager);
//        PshWellCandidateItemAdapter itemAdapter = new PshWellCandidateItemAdapter(actionContainerBehavior.getContext(), findResults);
        rv_candidate.setAdapter(getAdapter(actionContainerBehavior.getContext(), findResults));
        rv_candidate.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ScreenUtils.dp2px(getActivity(), 140)));
        pagerGridLayoutManager.setPageListener(new PagerGridLayoutManager.PageListener() {
            @Override
            public void onPageSizeChanged(int pageSize) {

            }

            @Override
            public void onPageSelect(int position) {
                if (findResults.size() == 1) {
                    btn_previous.setVisibility(View.GONE);
                    btn_next.setVisibility(View.GONE);
                    return;
                }
                if (position == 0) {
                    btn_previous.setVisibility(View.GONE);
                    btn_next.setVisibility(View.VISIBLE);
                } else if (position == findResults.size() - 1) {
                    btn_previous.setVisibility(View.VISIBLE);
                    btn_next.setVisibility(View.GONE);
                } else {
                    btn_previous.setVisibility(View.VISIBLE);
                    btn_next.setVisibility(View.VISIBLE);
                }
                attributeAdapter.setDatas(filterResultAttributes(findResults.get(position).getAttributes()));
                amFindResultMain = findResults.get(position);
                if (showListener != null) {
                    showListener.doShow(findResults.get(position));
                }
            }
        });
        PagerGridSnapHelper pagerGridSnapHelper = new PagerGridSnapHelper();
        pagerGridSnapHelper.attachToRecyclerView(rv_candidate);
        btn_previous.setVisibility(View.GONE);
        if (findResults.size() == 1) {
            btn_next.setVisibility(View.GONE);
        } else {
            btn_next.setVisibility(View.VISIBLE);
        }
        btn_next.setOnClickListener(v -> {
            pagerGridLayoutManager.nextPage();
        });
        btn_previous.setOnClickListener(v -> {
            pagerGridLayoutManager.prePage();
        });
        // 底部按钮栏
        View bottomView = View.inflate(actionContainerBehavior.getContext(), R.layout.layout_bottom_base_controller, null);
        View ll_controller = bottomView.findViewById(R.id.ll_controller);
        ImageView iv_controller = bottomView.findViewById(R.id.iv_controller);
        TextView tv_controller = bottomView.findViewById(R.id.tv_controller);
//        View.OnClickListener btnClick = v -> ToastUtil.shortToast(mView.getContext(), "功能建设中");
        initFunctionButtons(bottomView);
        AnchorSheetBehavior identifyBehavior = actionContainerBehavior.getIdentifyBehavior();
        identifyBehavior.assignNestedScrollingChild(rv_attributes);
        ll_controller.setOnClickListener(v -> {
            if (identifyBehavior.getState() == IBottomSheetBehavior.STATE_COLLAPSED) {
                identifyBehavior.setState(IBottomSheetBehavior.STATE_EXPANDED);
            } else if (identifyBehavior.getState() == IBottomSheetBehavior.STATE_EXPANDED) {
                identifyBehavior.setState(IBottomSheetBehavior.STATE_COLLAPSED);
            }
        });
        identifyBehavior.setBottomSheetCallback(new AnchorSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int oldState, int newState) {
                bottomSheet.requestLayout();
                if (newState == IBottomSheetBehavior.STATE_COLLAPSED) {
                    tv_controller.setText("显示详情");
                    iv_controller.setImageResource(R.drawable.icon_detail);
                } else if (newState == IBottomSheetBehavior.STATE_EXPANDED) {
                    tv_controller.setText("显示地图");
                    iv_controller.setImageResource(R.drawable.icon_map);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                View titleBar = actionContainerBehavior.getTitleBar().getView();
                titleBar.setVisibility(View.VISIBLE);
                if (bottomSheet.getTop() < actionContainerBehavior.getTitleBarHeight()) {
                    titleBar.setTranslationY(-bottomSheet.getTop());
                } else {
                    titleBar.setVisibility(View.GONE);
                }
                if (slideOffset > 0.2) {
                    actionContainerBehavior.hideHomeToolBar(true);
                } else {
                    actionContainerBehavior.showHomeToolbar(true);
                }
            }
        });
        tv_controller.setText("显示详情");
        iv_controller.setImageResource(R.drawable.icon_detail);
        identifyBehavior.setState(IBottomSheetBehavior.STATE_COLLAPSED);

        // 设置PeekHeight
        cl_header.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int peekHeight = cl_header.getHeight() + bottomView.getHeight();
                identifyBehavior.setPeekHeight(peekHeight);
                cl_header.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        // Toolbar显示
        actionContainerBehavior.getToolBar().showBack();
        actionContainerBehavior.getToolBar().setContent("查询结果");
        actionContainerBehavior.getToolBar().setContentColor(SkinCompatResources.getColor(actionContainerBehavior.getContext(), R.color.agmobile_text_body));
        actionContainerBehavior.getTitleBar().setTitle("查询结果");
        actionContainerBehavior.showHomeToolbar(false);
        actionContainerBehavior.hideTitleBar(false);

        if (!mInAction) {
            View.OnClickListener backListener = v -> presenter.hide();
            actionContainerBehavior.getToolBar().setOnBackClickListener(backListener);
            actionContainerBehavior.getTitleBar().setBackListener(backListener);
            actionContainerBehavior.setOnBackPressListener(new Callback5() {
                @Override
                public boolean onCallBack() {
                    backListener.onClick(null);
                    return false;
                }
            });
        }

        actionContainerBehavior.removeAllViews(IActionContainerBehavior.CANDIDATE_CONTAINER);
        actionContainerBehavior.addView(IActionContainerBehavior.CANDIDATE_CONTAINER, mView, null);
        actionContainerBehavior.showContainer(IActionContainerBehavior.CANDIDATE_CONTAINER);
        actionContainerBehavior.removeAllViews(IActionContainerBehavior.BOTTOM_VIEW);
        actionContainerBehavior.addView(IActionContainerBehavior.BOTTOM_VIEW, bottomView, null);
        actionContainerBehavior.showContainer(IActionContainerBehavior.BOTTOM_VIEW);
    }

    //设置适配器
    public abstract RecyclerView.Adapter getAdapter(Context context ,List<AMFindResult> findResults);

}
