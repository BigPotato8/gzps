package com.augurit.agmobile.agwater5.gcjs_public.personspace.approveinfo;

import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListActivity;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;

import java.util.ArrayList;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent
 * Created by sdb on 2019/4/19  9:55.
 * Desc：
 */

public class ApproveInfoActivity extends BaseViewListActivity {
    @Override
    protected ArrayList<? extends BaseViewListFragment> getFragments() {
        ArrayList<BaseViewListFragment> list = new ArrayList<>();
        ApproveInfoFragment approveInfoFragment = ApproveInfoFragment.getInstance();
        list.add(approveInfoFragment);
        return list;
    }
}
