package com.augurit.agmobile.agwater5.gcjspad.examine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjspad.BasePadFragment;
import com.augurit.agmobile.agwater5.gcjspad.MainPadFragment;
import com.augurit.agmobile.agwater5.gcjspad.examine.adapter.ExamineLeftFunBarAdapter;
import com.augurit.agmobile.agwater5.gcjspad.examine.listener.OnTitleClickListener;
import com.augurit.agmobile.agwater5.gcjspad.examine.model.TaskItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.widget.TabView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExaminePadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExaminePadFragment extends BasePadFragment implements VerticalTabLayout.OnTabSelectedListener {

    protected ViewGroup mLeftContainer;

    private ViewPager mViewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private List<TaskItemBean> mTitles = new ArrayList<>();
    private MyViewPagerAdapter myViewPagerAdapter;
    private RecyclerView mFunctionRecyclerView;
    /**
     * 左侧功能列表适配器
     */
    protected ExamineLeftFunBarAdapter mExamineLeftFunBarAdapter;

    protected HandingListFragment mHandlingListFragment;
    protected HandedListFragment mHandedListFragment;
    protected FinishedListFragment mFinishedListFragment;
    protected RejectListFragment mRejectListFragment;
    protected ApplyAlertListFragment mApplyAlertListFragment;
    protected MyAllListFragment myAllListFragment;
    protected ApplyOverdueListFragment mApplyOverdueListFragment;

    protected LinearLayout ll_back;

    public static ExaminePadFragment newInstance() {
        ExaminePadFragment fragment = new ExaminePadFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //左侧功能栏
        mTitles.add(new TaskItemBean("待办任务", "0"));
        mTitles.add(new TaskItemBean("已办任务", "0"));
//        mTitles.add(new TaskItemBean("办结任务", "0"));
        mTitles.add(new TaskItemBean("我的办件", "0"));
        mTitles.add(new TaskItemBean("不予受理", "0"));
        mTitles.add(new TaskItemBean("申报预警", "0"));
        mTitles.add(new TaskItemBean("申报逾期","0"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_examine_pad, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLeftContainer = view.findViewById(R.id.ll_left_container);
        ll_back = view.findViewById(R.id.ll_back);

        mFunctionRecyclerView = view.findViewById(R.id.rv_left_function_bar);
        mFunctionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mExamineLeftFunBarAdapter = new ExamineLeftFunBarAdapter(R.layout.item_examine_left_function, mTitles);
        mFunctionRecyclerView.setAdapter(mExamineLeftFunBarAdapter);

        mViewPager = view.findViewById(R.id.viewPager);
        mHandlingListFragment = HandingListFragment.newInstance();
        mHandedListFragment = HandedListFragment.newInstance();
//        mFinishedListFragment = FinishedListFragment.newInstance();
        myAllListFragment = MyAllListFragment.newInstance();//我的办件
        mRejectListFragment = RejectListFragment.newInstance();
        mApplyAlertListFragment = ApplyAlertListFragment.newInstance();
        mApplyOverdueListFragment = ApplyOverdueListFragment.newInstance();


        //设置标题点击事件
        mHandlingListFragment.setOnTitleClickListener(this::switchLeftContainer);
//        mFinishedListFragment.setOnTitleClickListener(this::switchLeftContainer);
        mHandedListFragment.setOnTitleClickListener(this::switchLeftContainer);
        myAllListFragment.setOnTitleClickListener(this::switchLeftContainer);
        mRejectListFragment.setOnTitleClickListener(this::switchLeftContainer);
        mApplyAlertListFragment.setOnTitleClickListener(this::switchLeftContainer);
        mApplyOverdueListFragment.setOnTitleClickListener(this::switchLeftContainer);
        ll_back.setOnClickListener(v -> {
            switchLeftContainer();
        });

        mFragments.add(mHandlingListFragment);
        mFragments.add(mHandedListFragment);
        //       mFragments.add(mFinishedListFragment);
        mFragments.add(myAllListFragment);
        mFragments.add(mRejectListFragment);
        mFragments.add(mApplyAlertListFragment);
        mFragments.add(mApplyOverdueListFragment);

        myViewPagerAdapter = new MyViewPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(myViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(mFragments.size());
        ((MainPadFragment) getParentFragment()).getVerticalTableLayout().addOnTabSelectedListener(this);


        mExamineLeftFunBarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                indexPosition(position);
            }
        });
    }

    /**
     * 指示器指示指定位置
     *
     * @param position
     */
    private void indexPosition(int position) {
        mExamineLeftFunBarAdapter.setmSelectPosition(position);
        mViewPager.setCurrentItem(position, false);
        mExamineLeftFunBarAdapter.notifyDataSetChanged();
    }

    private void switchLeftContainer() {
        mLeftContainer.setVisibility(mLeftContainer.isShown() ? View.GONE : View.VISIBLE);
    }

    /**
     * 当跳转到审批界面时的回调
     *
     * @param tab
     * @param position
     */
    @Override
    public void onTabSelected(TabView tab, int position) {
        int tempIndex = ((MainPadFragment) getParentFragment()).getTempIndex();
        indexPosition(tempIndex);
    }

    @Override
    public void onTabReselected(TabView tab, int position) {

    }


    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    /**
     * 设置任务的数量
     */
    public void setChooseItemDatasCount(String pageKey, String total) {
        List<TaskItemBean> data = mExamineLeftFunBarAdapter.getData();
        for (TaskItemBean bean : data) {
            if (bean.getPageKey().equals(pageKey)) {
                bean.setPageTotal(total);
                break;
            }
        }
        mExamineLeftFunBarAdapter.notifyDataSetChanged();
    }


}
