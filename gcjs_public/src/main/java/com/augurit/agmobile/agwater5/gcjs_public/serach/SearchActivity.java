package com.augurit.agmobile.agwater5.gcjs_public.serach;


import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListActivity;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;

import java.util.ArrayList;

/**
 * @author 创建人 ：SDB
 * @version 1.0
 * @modifyMemo 修改备注：
 */

public class SearchActivity extends BaseViewListActivity {


    @Override
    protected void initView() {
        mTitleText = "搜索";
        super.initView();
    }

    @Override
    protected ArrayList<? extends BaseViewListFragment> getFragments() {
        ArrayList<BaseViewListFragment> fragments = new ArrayList<>();
        SerachFragment serachFragment = SerachFragment.newInstance();
        fragments.add(serachFragment);
        return fragments;
    }
}
