package com.augurit.common.statistics.view;

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

import com.augurit.common.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.statistics.view
 * @createTime 创建时间 ：2018-09-05
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class NewStatisticsFragment extends Fragment  {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
//        View viewById = view.findViewById(R.id.iv_back);
//        viewById.setOnClickListener(v -> {
//            if (getActivity() != null) {
//                getActivity().finish();
//            }
//        });

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new NewInstallStatisticsFragment());
        fragments.add(new NewReportStatisticsFragment());
        fragments.add(new NewSignStatisticsFragment());

        String[] titles = new String[]{
                getString(R.string.install_statistic),
                getString(R.string.report_statistics),
                getString(R.string.sign_statistic)
        };

        MyPageAdapter pageAdapter = new MyPageAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setAdapter(pageAdapter);
        viewPager.setOffscreenPageLimit(titles.length);
        tabLayout.setupWithViewPager(viewPager);
    }

    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;
        private String[] mTitles;

        private MyPageAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
