package com.augurit.agmobile.agwater5.gcjs.zcfg;

import android.graphics.Color;
import android.support.design.widget.TabLayout;

import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListActivity;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;

import java.util.ArrayList;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.zcfg
 * Created by sdb on 2019/5/30  11:42.
 * Desc：
 */

public class ZcfgActivity extends BaseViewListActivity {

    ZcfgFragment mZcfgFragment, mZcfgFragment1, mZcfgFragment2, mZcfgFragment3;
    ZcwjFragment mZcwjFragment;

    @Override
    protected void initView() {
        super.initView();
        TabLayout tableLayout = mView.getTabLayout();
        tableLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tableLayout.setBackgroundColor(Color.parseColor("#eeeeee"));
    }

    @Override
    protected ArrayList<? extends BaseViewListFragment> getFragments() {
        mZcfgFragment = ZcfgFragment.getInsance("guojiaRegulation");
        mZcfgFragment1 = ZcfgFragment.getInsance("hebeiRegulation");
        mZcfgFragment2 = ZcfgFragment.getInsance("tangshanRegulation");
//        mZcfgFragment3 = ZcfgFragment.getInsance("house");
        ArrayList<BaseViewListFragment> list = new ArrayList<>();
        list.add(mZcfgFragment);
        list.add(mZcfgFragment1);
        list.add(mZcfgFragment2);
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
        titles.add("国家政策法规");
        titles.add("河北政策法规");
        titles.add("唐山政策法规");
        return titles;
    }
}
