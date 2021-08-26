package com.augurit.agmobile.agwater5.gcjs_public.announce;

import android.graphics.Color;
import android.support.design.widget.TabLayout;

import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListActivity;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;

import java.util.ArrayList;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.zcfg
 * Created by sdb on 2019/5/30  11:42.
 * Desc：审批情况公示
 */

public class AnnounceActivity extends BaseViewListActivity {

    AnnounceFragment mAnnounceFragment, mAnnounceFragment1, mAnnounceFragment2;

    @Override
    protected void initView() {
        super.initView();
        TabLayout tableLayout = mView.getTabLayout();
//        tableLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tableLayout.setBackgroundColor(Color.parseColor("#eeeeee"));
    }

    @Override
    protected ArrayList<? extends BaseViewListFragment> getFragments() {
        mAnnounceFragment = AnnounceFragment.getInsance(AnnounceFragment.ANNOUNCE_STATE_FIRST);
        mAnnounceFragment1 = AnnounceFragment.getInsance(AnnounceFragment.ANNOUNCE_STATE_SECOND);
        mAnnounceFragment2 = AnnounceFragment.getInsance(AnnounceFragment.ANNOUNCE_STATE_THIRD);
//        mZcfgFragment3 = ZcfgFragment.getInsance("house");
        ArrayList<BaseViewListFragment> list = new ArrayList<>();
        list.add(mAnnounceFragment);
        list.add(mAnnounceFragment1);
        list.add(mAnnounceFragment2);
//        list.add(mZcfgFragment3);


        return list;
    }


    /**
     * 获取页面标题，将显示在TabLayout中<br></>
     * 单个Fragment无需重写该方法，TabLayout也不会显示<br></>
     * 多个Fragment时请确保列表数量与{@link #getFragments()}一致
     *
     * @return 页面标题列表
     */
    @Override
    protected ArrayList<String> getPageTitles() {
        ArrayList<String> titles = new ArrayList<>();
        titles.add("受理情况");
        titles.add("拟审批意见");
        titles.add("审批决定");
        return titles;
    }
}
