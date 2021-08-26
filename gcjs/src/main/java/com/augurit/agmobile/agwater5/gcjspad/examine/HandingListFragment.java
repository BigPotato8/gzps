package com.augurit.agmobile.agwater5.gcjspad.examine;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsConstant;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjspad.MainPadActivity;
import com.augurit.agmobile.agwater5.gcjspad.event.FilterBeanEvent;
import com.augurit.agmobile.agwater5.gcjspad.event.ResetBeanEvent;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.EventDetailPadFragment;
import com.augurit.agmobile.agwater5.gcjspad.examine.adapter.ExamineListAdapter;
import com.augurit.agmobile.agwater5.gcjspad.examine.listener.OnTaskClickListener;
import com.augurit.agmobile.agwater5.gcjspad.homepage.adapter.HandlingListAdapter;
import com.augurit.agmobile.agwater5.gcjspad.widget.PageControlView;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.lib.validate.StringUtil;
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.augurit.agmobile.common.view.multispinner.AMMultiSpinner;
import com.augurit.common.common.util.TimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.examine
 * @createTime 创建时间 ：2020/12/8
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class HandingListFragment extends BaseTaskFragment {

    public static HandingListFragment newInstance() {
        Bundle args = new Bundle();
        HandingListFragment fragment = new HandingListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initArguments() {
        mTitleName = "待办任务";
        mBaseUrl = GcjsUrlConstant.GET_EVENT_DB_LIST;
    }

    @Override
    protected void initData() {
        mBaseMap.put("viewCode", GcjsConstant.DB_VIEW_CODE);
        getDataList(1, mFilterMap);
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        ll_spinner_sign_state.setVisibility(View.VISIBLE);
        ll_spinner_InstState.setVisibility(View.VISIBLE);
        mBaseAdapter = new ExamineListAdapter(R.layout.item_examine_right_list, 1);
        mRecyclerView.setAdapter(mBaseAdapter);

        ((ExamineListAdapter) mBaseAdapter).setmOnTaskClickListener(new OnTaskClickListener() {
            @Override
            public void onCheckClick(EventListItem.DataBean item) {
                jumpToDetailFragment(item);
            }

            @Override
            public void onSignClick(EventListItem.DataBean item) {
                singTask(item.getTaskId());
            }

            @Override
            public void onHandleClick(EventListItem.DataBean item) {
                jumpToDetailFragment(item);
            }
        });

    }

    @Override
    protected void initListener() {
        super.initListener();
        //取消点击列表项进去详情页面
        mBaseAdapter.setOnItemClickListener(null);
    }



}
