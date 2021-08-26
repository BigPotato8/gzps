package com.augurit.agmobile.agwater5.gcjs.zcfg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.zcfg.model.ZczyBean;
import com.augurit.agmobile.agwater5.gcjs.zcfg.source.ZczyRepository;
import com.augurit.agmobile.busi.bpm.view.view.SearchTitleBar;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 政策指引
 */
public class ZczyActivity extends AppCompatActivity {
    SearchTitleBar search_title_bar;
    TabLayout tab_layout;
    NoScrollViewPager view_pager;
    ZczyRepository zczyRepository;
    List<String> titles;
    List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zcfg_layout);
        search_title_bar = findViewById(R.id.search_title_bar);
        tab_layout = findViewById(R.id.tab_layout);
        view_pager = findViewById(R.id.view_pager);
        search_title_bar.setBackListener(v ->{
            finish();
        });
        search_title_bar.setTitle("政策指引");
        search_title_bar.findViewById(R.id.btn_toggle_view).setVisibility(View.GONE);
        //todo 网络获取tab数量和子项
        zczyRepository = new ZczyRepository();
        zczyRepository.getOPGuideDirs()
                .subscribe(listApiResult -> {
                    if (listApiResult.isSuccess() && !ListUtil.isEmpty(listApiResult.getData())) {
                        titles = new ArrayList<>();
                        fragments = new ArrayList<>();
                        for (ZczyBean datum : listApiResult.getData()) {
                            titles.add(datum.getDirName());
                            fragments.add(Zcfg3Fragment.getInstance(datum.getChildren()));
                        }
                        MyPageAdapter adapter = new MyPageAdapter(getSupportFragmentManager(),fragments);
                        view_pager.setAdapter(adapter);
                        view_pager.setOffscreenPageLimit(fragments.size());
                        tab_layout.setupWithViewPager(view_pager);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    ToastUtil.longToast(this, "获取文件列表失败");
                    findViewById(R.id.view_net_error).setVisibility(View.VISIBLE);
                });
    }

    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;

        private MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
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
            return titles.get(position);
        }
    }
}
