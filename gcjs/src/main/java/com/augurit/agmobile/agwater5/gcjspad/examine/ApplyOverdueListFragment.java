package com.augurit.agmobile.agwater5.gcjspad.examine;

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
 * @createTime 创建时间 ：2020/12/15
 * @modifyBy 修改人 ：xiezhiwei
 * @modifyTime 修改时间 ：2020/12/15
 * @modifyMemo 修改备注： 申报逾期页面
 */
public class ApplyOverdueListFragment extends BaseTaskFragment {

    public static ApplyOverdueListFragment newInstance() {
        ApplyOverdueListFragment applyOverdueListFragment = new ApplyOverdueListFragment();
        return applyOverdueListFragment;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mBaseAdapter = new ExamineListAdapter(R.layout.item_examine_right_list, 7);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mBaseAdapter);
    }

    @Override
    protected void initArguments() {
        mTitleName = "申报逾期";
        mBaseUrl = GcjsUrlConstant.APPLY_ALERT_LIST;
        //申报状态
        mBaseMap.put("handler", "false");
        mBaseMap.put("entrust", "true");
        mBaseMap.put("isConcluding", "0");
        mBaseMap.put("assigned", "false");
        mBaseMap.put("instState", "3");
    }

    @Override
    protected void initData() {

        getDataList(1, mFilterMap);
    }


}
