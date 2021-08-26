package com.augurit.agmobile.agwater5.gcjs_public.personspace.projectprocess.view;


import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListActivity;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;

import java.util.ArrayList;

/**
 * @author 创建人 ：SDB
 * @version 1.0
 * @modifyMemo 修改备注：
 * /aplanmis-rest/rest/user/item/schedule/diagram/{projInfoId}
 * /aplanmis-rest/rest/user/itemSchedule/list
 *
 *
 */

public class ProjectProcessActivity extends BaseViewListActivity {


    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected ArrayList<? extends BaseViewListFragment> getFragments() {
        ArrayList<BaseViewListFragment> fragments = new ArrayList<>();
        ProjectProcessFragment myProjectFragment = ProjectProcessFragment.newInstance();
        fragments.add(myProjectFragment);
        return fragments;
    }
}
