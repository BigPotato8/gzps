package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.agwater5.common.view.NoscollRecyclerView;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.MaterialListBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ResultGoodsMaterialBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter.EventSbxxAdapter;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter.MaterialListAdapter;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter.ResultGoodsMaterialAdapter;
import com.augurit.agmobile.busi.bpm.form.view.ElementPosition;
import com.augurit.agmobile.busi.bpm.form.view.FormLoadListener;
import com.augurit.agmobile.busi.bpm.form.view.FormState;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.common.common.form.AwFormConfig;
import com.augurit.common.common.form.AwFormFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 结果物补充材料fragment
 */
public class ResultGoodsMaterialFragment extends BaseViewListFragment<ResultGoodsMaterialBean>{
//public class ResultGoodsMaterialFragment extends AwFormFragment {
//    EventSbxxAdapter adapter;
//    String taskId;
//    EventInfoBean eventInfoBean;
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }
//    public static ResultGoodsMaterialFragment getInstance(String taskId) {
//        ResultGoodsMaterialFragment baseInfoFragment = new ResultGoodsMaterialFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("taskId", taskId);
//        baseInfoFragment.setArguments(bundle);
//        return baseInfoFragment;
//    }
//
//    @Override
//    protected void initArguments() {
//        super.initArguments();
//        mFormCode = AwFormConfig.FORM_RESULT_GOODS_MATERIAL;
//        mFormState = FormState.STATE_READ;
//        taskId = getArguments().getString("taskId");
//    }
//
//    @Override
//    protected void onFormLoaded() {
//        super.onFormLoaded();
//    }
//
//
//    @Subscribe
//    public void getEventInfo(EventInfoBean eventInfoBean) {
//        this.eventInfoBean = eventInfoBean;
//        loadForm();
//    }
//
//    @Override
//    protected boolean shouldLoadImmediately() {
//        return false;
//    }


    public final static int FLASH_RESULT_CODE = 1233;
    private EventRepository mEventRepository;
    private String mIsSeriesApproval;
    private EventInfoBean mEventBean;

    public static ResultGoodsMaterialFragment getInstance(String taskId) {
        ResultGoodsMaterialFragment resultGoodsMaterialFragment = new ResultGoodsMaterialFragment();
        Bundle bundle = new Bundle();
        bundle.putString("taskId", taskId);
        resultGoodsMaterialFragment.setArguments(bundle);
        return resultGoodsMaterialFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initArguments() {
        super.initArguments();
        mEventRepository = new EventRepository();
//        mIsSeriesApproval = getArguments().getString("isSeriesApproval");//isSeriesApprove=0并联，isSeriesApprove=1单项
    }

    @Override
    protected BaseViewListAdapter<ResultGoodsMaterialBean> initAdapter() {
        return new ResultGoodsMaterialAdapter(getContext());
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected Observable<ApiResult<List<ResultGoodsMaterialBean>>> loadDatas(int page, int rows, Map filterParams) {
        refresh_layout.setEnableLoadMore(false);
        //获取材料附件
        return mEventRepository.getResultGoodsMaterialFileList(mEventBean == null ? "" : mEventBean.getApplyInfoVo().getApplyinstId());

    }

    @Override
    public void onItemClick(View itemView, int position, ResultGoodsMaterialBean data) {

        Intent intent = ResultGoodsMaterialsDetailActivity.getIntent(getActivity(), data, mEventBean.getApplyInfoVo().getApplyinstId(),data.getIteminstId());
        getActivity().startActivityForResult(intent,FLASH_RESULT_CODE);//跳到材料详情
    }

    @Subscribe
    public void receive(EventInfoBean eventBean) {

        mEventBean = eventBean;
        if (refresh_layout != null) {
            loadDatas(true);
        }
    }

    @Override
    public boolean onItemLongClick(View itemView, int position, ResultGoodsMaterialBean data) {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FLASH_RESULT_CODE && resultCode == FLASH_RESULT_CODE) {
            loadDatas(true);
        }
    }
}
