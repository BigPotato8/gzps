package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent;

import android.content.Intent;

import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListActivity;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;

import java.util.ArrayList;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent
 * Created by sdb on 2019/4/19  9:55.
 * Desc：
 */

public class UploadProjectListActivity extends BaseViewListActivity {
    UploadProjectListFragment mUploadProjectListFragment;

    @Override
    protected ArrayList<? extends BaseViewListFragment> getFragments() {
        ArrayList<BaseViewListFragment> list = new ArrayList<>();
        mUploadProjectListFragment = new UploadProjectListFragment();
        list.add(mUploadProjectListFragment);
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            mUploadProjectListFragment.onActivityResult(requestCode,resultCode,data);
        }
    }
}
