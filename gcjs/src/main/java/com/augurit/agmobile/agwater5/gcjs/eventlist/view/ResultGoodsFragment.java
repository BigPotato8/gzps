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
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.PwpfBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ResultGoodsBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ZjzzBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.validate.ListUtil;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * 批文，证件，结果物tab
 */
public class ResultGoodsFragment extends Fragment {
    private EventListItem.DataBean mEventListItem;
    private EventInfoBean eventInfoBean;
    private PwpfListFragment mPwpfListFragment;
    private List<BaseViewListFragment> fragments;
    private ZjzzListFragment mZjzzListFragment;
    private OtherResultListFragment mOtherResultListFragment;
    private EventRepository mRepository;

    public static ResultGoodsFragment getInstance(EventListItem.DataBean dataBean,EventInfoBean eventInfoBean) {
        ResultGoodsFragment mFragment = new ResultGoodsFragment();
        mFragment.mEventListItem = dataBean;
        mFragment.eventInfoBean = eventInfoBean;
        return mFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gcjs_result_goods, container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        TabLayout tab_layout = view.findViewById(R.id.tab_layout);
        ViewPager view_pager = view.findViewById(R.id.view_pager);

        fragments = new ArrayList<>();
        mPwpfListFragment = PwpfListFragment.getInstance(mEventListItem);
        fragments.add(mPwpfListFragment);
        mZjzzListFragment = ZjzzListFragment.getInstance(mEventListItem);
        fragments.add(mZjzzListFragment);
        mOtherResultListFragment = OtherResultListFragment.getInstance(mEventListItem);
        fragments.add(mOtherResultListFragment);
        List<String> pageNames = new ArrayList<>();
        pageNames.add(getString(R.string.gcjs_title_pwpf));
        pageNames.add(getString(R.string.gcjs_title_zjzz));
        pageNames.add(getString(R.string.gcjs_title_qtjgw));
        MyPageAdapter adapter = new MyPageAdapter(getChildFragmentManager(), fragments, pageNames);
        view_pager.setAdapter(adapter);
        view_pager.setOffscreenPageLimit(pageNames.size());

        tab_layout.setupWithViewPager(view_pager);

        mRepository = new EventRepository();
        String applyinstId = eventInfoBean.getApplyInfoVo().getApplyinstId();
        String iteminstId = ListUtil.isEmpty(eventInfoBean.getIteminstList())?"":eventInfoBean.getIteminstList().get(0).getIteminstId();
        mRepository.getResultGoods(applyinstId,iteminstId)
                .subscribe(apiResult -> {
                    if (apiResult.isSuccess()) {
//                        ((EventApprovalActivity)getActivity()).setResultGoodsBeanAll(apiResult.getData());
                    }
                    loadDatas(eventInfoBean);
                },throwable -> {
                    loadDatas(eventInfoBean);
                });

    }

    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<String> mTitles;
        private List<? extends Fragment> mFragments;

        private MyPageAdapter(FragmentManager fm, List<? extends Fragment> fragments, List<String> titles) {
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
            return mTitles.get(position);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ZjzzListFragment.FLASH_CODE && resultCode == RESULT_OK) {
            if (mZjzzListFragment != null) {
                mZjzzListFragment.onActivityResult(requestCode, resultCode, data);
            }
        } else if (requestCode == PwpfListFragment.FLASH_CODE && resultCode == RESULT_OK) {
            if (mPwpfListFragment != null) {
                mPwpfListFragment.onActivityResult(requestCode, resultCode, data);
            }
        } else if (requestCode == OtherResultListFragment.FLASH_CODE && resultCode == RESULT_OK) {
            if (mOtherResultListFragment != null) {
                mOtherResultListFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
    public void loadDatas(EventInfoBean eventInfoBean){
        if (mPwpfListFragment!=null) {
            mPwpfListFragment.receive(eventInfoBean);
        }
        if (mZjzzListFragment!=null) {
            mZjzzListFragment.receive(eventInfoBean);
        }
        if (mOtherResultListFragment!=null) {
            mOtherResultListFragment.receive(eventInfoBean);
        }
    }


    public List<ResultGoodsBean> reflashGoodsList(List<ResultGoodsBean> resultGoodsBeanAll){
        List<PwpfBean.ItemMatinstBean> data = mPwpfListFragment.getData();
        List<ZjzzBean> data1 = mZjzzListFragment.getData();
        return reflashGoodsList(resultGoodsBeanAll,data,data1);
    }
    public List<ResultGoodsBean> reflashGoodsList(List<ResultGoodsBean> resultGoodsBeanAll,List<PwpfBean.ItemMatinstBean> data){
        List<ZjzzBean> data1 = mZjzzListFragment.getData();
        return reflashGoodsList(resultGoodsBeanAll,data,data1);
    }
    public List<ResultGoodsBean> reflashGoodsList(List<ResultGoodsBean> resultGoodsBeanAll, List<ZjzzBean> data1,int aaa){
        List<PwpfBean.ItemMatinstBean> data = mPwpfListFragment.getData();
        return reflashGoodsList(resultGoodsBeanAll,data,data1);
    }

    public List<ResultGoodsBean> reflashGoodsList(List<ResultGoodsBean> resultGoodsBeanAll,List<PwpfBean.ItemMatinstBean> data,List<ZjzzBean> data1){
        List<ResultGoodsBean> resultNew = new ArrayList<>(resultGoodsBeanAll);
        if (data!=null){
            for (PwpfBean.ItemMatinstBean bean : data) {
                for (ResultGoodsBean goodsBean : resultGoodsBeanAll) {
                    if (bean.getMatId().equals(goodsBean.getMatId())) {
                        resultNew.remove(goodsBean);
                    }
                }
            }
        }
        if (data1!=null){
            for (ZjzzBean zjzzBean : data1) {
                for (ResultGoodsBean goodsBean : resultGoodsBeanAll) {
                    if (zjzzBean.getMatId().equals(goodsBean.getMatId())) {
                        resultNew.remove(goodsBean);
                    }
                }
            }
        }
        return resultNew;
    }
}
