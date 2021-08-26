package com.augurit.agmobile.agwater5.gcjs_public.personspace.eventuploaded;

import android.content.Intent;

import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListActivity;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;

import java.util.ArrayList;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent
 * Created by sdb on 2019/4/19  9:55.
 * Desc：
 */

public class EventUploadedActivity extends BaseViewListActivity {
    EventUploadedFragment mEventUploadedFragment, mEventUploadedFragment1, mEventUploadedFragment2;

    @Override
    protected ArrayList<? extends BaseViewListFragment> getFragments() {
        ArrayList<BaseViewListFragment> list = new ArrayList<>();
        mEventUploadedFragment = EventUploadedFragment.getInstance(EventState.HANDLING);
        mEventUploadedFragment1 = EventUploadedFragment.getInstance(EventState.HANDLED);
//        mEventUploadedFragment2 = EventUploadedFragment.getInstance(EventState.DRAFT);
        list.add(mEventUploadedFragment);
        list.add(mEventUploadedFragment1);
//        list.add(mEventUploadedFragment2);
        return list;
    }

    @Override
    protected ArrayList<String> getPageTitles() {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("办理中");
        titles.add("已办结");
//        titles.add("我的草稿");
        return titles;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            mEventUploadedFragment.onActivityResult(requestCode, resultCode, data);
            mEventUploadedFragment1.onActivityResult(requestCode, resultCode, data);
//            mEventUploadedFragment2.onActivityResult(requestCode, resultCode, data);
        }
    }
}
