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
 * @description 部门材料附件Fragment
 * @date: 20190402
 * @author: sdb
 */
public class ClfjFragment extends Fragment {
    private String[] mTitles;
    private List<Fragment> mFragments;
    private EventListItem.DataBean mEventListItem;
    private FjsbclFragment mFjsbclFragment;
    private FjpwpfFragment mFjpwpfFragment;

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
                getString(R.string.gcjs_fj_sbcl),
                getString(R.string.gcjs_fj_pwpf)
        };
        mFragments = new ArrayList<>();
        mFjsbclFragment = FjsbclFragment.getInstance(mEventListItem.getApplyType());
        mFragments.add(mFjsbclFragment);
        mFjpwpfFragment = FjpwpfFragment.getInstance(mEventListItem.getApplyType());
        mFragments.add(mFjpwpfFragment);

        MyPageAdapter adapter = new MyPageAdapter(getChildFragmentManager());
        view_pager.setAdapter(adapter);
        view_pager.setOffscreenPageLimit(mFragments.size());
        tabLayout.setupWithViewPager(view_pager);
        //移除下划线
        // ...setselectedTabIndicatorHeight(0)
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setTabTextColors(0xff666666, 0xff0190F2);
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
        if (requestCode == FjpwpfFragment.FJ_PWPF_REQUEST){
            mFjpwpfFragment.onActivityResult(requestCode,resultCode,data);
        }else if(requestCode == FjsbclFragment.FJ_SBCL_REQUEST){
            mFjsbclFragment.onActivityResult(requestCode,resultCode,data);
        }
    }
}
