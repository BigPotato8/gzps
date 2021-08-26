package com.augurit.agmobile.agwater5.gcjspad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjspad.examine.ExaminePadFragment;
import com.augurit.agmobile.agwater5.gcjspad.homepage.HomePageFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.TabView;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;

public class MainPadFragment extends BasePadFragment {
    VerticalTabLayout tab_layout;
    FrameLayout fl_content_pad;
    List<Fragment> fragments;
    private int tempIndex = 0;


    public static MainPadFragment newInstance() {
        Bundle args = new Bundle();
        MainPadFragment fragment = new MainPadFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_main_pad, container, false);
        initView(inflate);
        return inflate;

    }

    private void initView(View view) {
        tab_layout = view.findViewById(R.id.tab_layout);
        fl_content_pad = view.findViewById(R.id.fl_content_pad);
        fragments = new ArrayList<>();
        fragments.add(HomePageFragment.newInstance());
        fragments.add(ExaminePadFragment.newInstance());
        MyTabAdapter adapter = new MyTabAdapter();
        FragmentManager childFragmentManager = getChildFragmentManager();
        tab_layout.setTabAdapter(adapter);
        tab_layout.setupWithFragment(childFragmentManager, R.id.fl_content_pad, fragments, adapter);
    }


    private class MyTabAdapter implements TabAdapter {

        List<MenuBean> menus;

        public MyTabAdapter() {
            menus = new ArrayList<>();
            Collections.addAll(menus, new MenuBean(R.mipmap.home_selected, R.mipmap.home_unselect, "首页")
                    , new MenuBean(R.mipmap.shenpi_selected, R.mipmap.shenpi_normal, "审批")
            )
            ;
        }

        @Override
        public int getCount() {
            return menus.size();
        }

        @Override
        public TabView.TabBadge getBadge(int position) {
//            return new TabView.TabBadge.Builder().setBadgeNumber(999).setBackgroundColor(0xff2faae5)
//                    .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
//                        @Override
//                        public void onDragStateChanged(int dragState, Badge badge, View targetView) {
//                        }
//                    }).build();
            return null;
        }

        @Override
        public TabView.TabIcon getIcon(int position) {
            MenuBean menu = menus.get(position);
            return new TabView.TabIcon.Builder()
                    .setIcon(menu.mSelectIcon, menu.mNormalIcon)
                    .setIconGravity(Gravity.TOP)
//                    .setIconMargin(dp2px(25))
                    .setIconSize(dp2px(25), dp2px(25))
                    .build();
        }

        @Override
        public TabView.TabTitle getTitle(int position) {
            MenuBean menu = menus.get(position);
            return new TabView.TabTitle.Builder()
                    .setContent(menu.mTitle)
                    .setTextSize(15)
                    .setTextColor(0xFFffffff, 0xFF99a0ae)
                    .build();
        }

        @Override
        public int getBackground(int position) {
            return -1;
        }

    }

    public class MenuBean {
        int mSelectIcon;
        int mNormalIcon;
        String mTitle;

        public MenuBean(int mSelectIcon, int mNormalIcon, String mTitle) {
            this.mSelectIcon = mSelectIcon;
            this.mNormalIcon = mNormalIcon;
            this.mTitle = mTitle;
        }
    }

    public List<Fragment> getMainPadFragments(){
        return fragments;
    }

    /**
     * 跳转至审批界面
     * @param tempIndex
     */
    public void JumpToExaminePadFragment(int tempIndex){
        this.tempIndex = tempIndex;
        tab_layout.setTabSelected(1);
    }

    /**
     * 获取垂直tablayout控件
     * @return
     */
    public VerticalTabLayout getVerticalTableLayout(){
        return tab_layout;
    }

    /**
     * 获取跳转至哪个列表的position
     * @return
     */
    public int getTempIndex(){
        return tempIndex;
    }

}
