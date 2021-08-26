package com.augurit.agmobile.agwater5.gcjspad.examine;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjspad.examine.adapter.ExamineListAdapter;

/**
 * @author 创建人 ：xiezhiwei
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.examine
 * @createTime 创建时间 ：2020/12/14
 * @modifyBy 修改人 ：xiezhiwei
 * @modifyTime 修改时间 ：2020/12/14
 * @modifyMemo 修改备注： 申报预警列表
 */
public class ApplyAlertListFragment extends BaseTaskFragment {

    public static ApplyAlertListFragment newInstance() {
        ApplyAlertListFragment applyAlertListFragment = new ApplyAlertListFragment();
        return applyAlertListFragment;
    }

    @Override
    protected void initArguments() {
        mBaseUrl = GcjsUrlConstant.APPLY_ALERT_LIST;
        mTitleName = "申报预警";
        //申报状态
        mBaseMap.put("handler", "false");
        mBaseMap.put("entrust", "true");
        mBaseMap.put("isConcluding", "0");
        mBaseMap.put("assigned", "false");
        mBaseMap.put("instState", "2");
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mBaseAdapter = new ExamineListAdapter(R.layout.item_examine_right_list, 6);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mBaseAdapter);
    }

    @Override
    protected void initData() {
//        &handler=false&entrust=true&isConcluding=0&assigned=false

        getDataList(1, mFilterMap);
    }
}
