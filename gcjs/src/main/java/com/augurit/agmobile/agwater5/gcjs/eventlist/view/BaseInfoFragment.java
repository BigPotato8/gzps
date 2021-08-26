package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 基本信息Fragment
 * @date: $date$ $time$
 * @author: xieruibin
 */
public class BaseInfoFragment extends Fragment {
    private String[] mTitles;
    private int[] mTabIcon;
    private List<Fragment> mFragments;
    private EventListItem.DataBean mEventListItem;
    private MaterialListFragment materialListFragment;

    public void setEventListItem(EventListItem.DataBean eventListItem) {
        mEventListItem = eventListItem;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gcjs_viewpager_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager view_pager = view.findViewById(R.id.view_pager);
        mTitles = new String[]{
//                getString(R.string.gcjs_item_baseInfo),
//                getString(R.string.gcjs_item_eventList),
//                getString(R.string.gcjs_item_adviceList),
//                getString(R.string.gcjs_item_materialList),
                getString(R.string.gcjs_item_paperList),
                getString(R.string.gcjs_item_projectList),
                getString(R.string.gcjs_item_declareList),
        };

        mTabIcon = new int[]{
                R.mipmap.ic_gcjs_detail_upload,
                R.mipmap.ic_gcjs_detail_event,
                R.mipmap.ic_gcjs_detail_opinion,
//                R.mipmap.ic_gcjs_detail_material,

        };
        mFragments = new ArrayList<>();
        SbxxListFragment sbxxListFragment = SbxxListFragment.getInstance(mEventListItem!=null?mEventListItem.getTaskId():"");//申报信息(办件信息)
//        eventListFragment.setEventListItem(mEventListItem);
        mFragments.add(sbxxListFragment);

        EventBaseInfoFragment eventBaseInfoFragment = new EventBaseInfoFragment();//项目申报(项目/工程信息)
        eventBaseInfoFragment.setEventListItem(mEventListItem);
        mFragments.add(eventBaseInfoFragment);

        DeclareMainListFragment declareMainListFragment=new DeclareMainListFragment();//申报主体信息
        declareMainListFragment.setEventListItem(mEventListItem);
        mFragments.add(declareMainListFragment);

        /*AdviceListFragment adviceListFragment = new AdviceListFragment();//处理意见(审批详情)
        adviceListFragment.setEventListItem(mEventListItem);
        mFragments.add(adviceListFragment);*/

        /*materialListFragment = MaterialListFragment.getInstance(mEventListItem != null
                ? mEventListItem.getApplyType() : "");//材料列表
        mFragments.add(materialListFragment);*/



        MyPageAdapter adapter = new MyPageAdapter(getChildFragmentManager());
        view_pager.setAdapter(adapter);
        view_pager.setOffscreenPageLimit(mFragments.size());
        tabLayout.setupWithViewPager(view_pager);
        //移除下划线
        // ...setselectedTabIndicatorHeight(0)
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setTabTextColors(0xff666666, 0xff0190F2);


        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setIcon(mTabIcon[i]);
            }
        }
    }

    private class MyPageAdapter extends FragmentPagerAdapter {

        MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //materialListFragment.onActivityResult(requestCode,resultCode,data);
    }
}
