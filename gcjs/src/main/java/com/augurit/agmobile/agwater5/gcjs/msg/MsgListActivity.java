package com.augurit.agmobile.agwater5.gcjs.msg;

import android.content.Intent;

import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListActivity;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;

import java.util.ArrayList;

/**
 * com.augurit.agmobile.agwater5.gcjs.eventlist
 * Created by sdb on 2019/4/2  15:50.
 * Desc：
 */

public class MsgListActivity extends BaseViewListActivity {

    private MsgListFragment mMsgListFragment;

    @Override
    protected void initArguments() {
        super.initArguments();

    }


    @Override
    protected void initView() {
        mTitleText = "个人消息";
        super.initView();
    }


    @Override
    protected ArrayList<? extends BaseViewListFragment> getFragments() {
        ArrayList<BaseViewListFragment> fragments = new ArrayList<>();
        mMsgListFragment = MsgListFragment.newInstance();
        fragments.add(mMsgListFragment);

        return fragments;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            mMsgListFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

}
