package com.augurit.agmobile.agwater5.gcjspad.examine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjspad.event.FilterBeanEvent;
import com.augurit.agmobile.agwater5.gcjspad.event.ResetBeanEvent;
import com.augurit.agmobile.agwater5.gcjspad.examine.adapter.ExamineListAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author 创建人 ：xiezhiwei
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.examine
 * @createTime 创建时间 ：2020/12/14
 * @modifyBy 修改人 ：xiezhiwei
 * @modifyTime 修改时间 ：2020/12/14
 * @modifyMemo 修改备注： 不予受理列表
 */
public class RejectListFragment extends BaseTaskFragment {

    public static RejectListFragment newInstance() {
        Bundle args = new Bundle();
        RejectListFragment rejectListFragment = new RejectListFragment();
        rejectListFragment.setArguments(args);
        return rejectListFragment;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        spinner_InstState.setVisibility(View.GONE);
        mTitleView.setText(mTitleName);
        mBaseAdapter = new ExamineListAdapter(R.layout.item_examine_right_list, 5);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mBaseAdapter);
    }

    @Override
    protected void initArguments() {
        mTitleName = "不予受理";
        mBaseUrl = GcjsUrlConstant.REJECT_LIST;
    }

    @Override
    protected void initData() {
        getDataList(1, mFilterMap);
    }
}
