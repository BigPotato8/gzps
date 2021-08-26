package com.augurit.agmobile.agwater5.gcjs.announce;

import android.content.Intent;

import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListActivity;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;

import java.util.ArrayList;

/**
 * com.augurit.agmobile.agwater5.gcjs.eventlist
 * Created by sdb on 2019/4/2  15:50.
 * Desc：通知公告
 */

public class AnnounceListActivity extends BaseViewListActivity {

    private AnnounceListFragment mAnnounceListFragment;

    @Override
    protected void initArguments() {
        super.initArguments();

    }


    @Override
    protected void initView() {
        mTitleText = "通知公告";
        super.initView();
    }


    @Override
    protected ArrayList<? extends BaseViewListFragment> getFragments() {
        ArrayList<BaseViewListFragment> fragments = new ArrayList<>();
        mAnnounceListFragment = AnnounceListFragment.newInstance();
        fragments.add(mAnnounceListFragment);

        return fragments;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            mAnnounceListFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

}
