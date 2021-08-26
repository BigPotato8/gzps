package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.content.Intent;
import android.view.View;

import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ClbzDetailBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter.EventListClbzAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.common.common.form.AwFormActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 材料补正列表
 */
public class ClbzListFragment extends BaseViewListFragment<ClbzDetailBean> {
    List<ClbzDetailBean> mList;
    public static ClbzListFragment getInstance(List<ClbzDetailBean> list){
        ClbzListFragment fragment = new ClbzListFragment();
        fragment.mList = list;

        return fragment;
    }

    @Override
    protected BaseViewListAdapter<ClbzDetailBean> initAdapter() {
        return new EventListClbzAdapter(getActivity());
    }

    @Override
    protected void initView() {
        super.initView();
        loadDatas(true);
    }

    @Override
    protected Observable<ApiResult<List<ClbzDetailBean>>> loadDatas(int page, int rows, Map<String, String> filterParams) {
        refresh_layout.setEnableLoadMore(false);
        refresh_layout.setEnableRefresh(false);
        return Observable.create(emitter -> {
            ApiResult<List<ClbzDetailBean>> apiResult = new ApiResult<>();
            apiResult.setSuccess(true);
            List<ClbzDetailBean> list = new ArrayList<>();
            if (mList!=null){
                list.addAll(mList);
            }
            apiResult.setData(list);

            emitter.onNext(apiResult);
        });

    }

    @Override
    public void onItemClick(View itemView, int position, ClbzDetailBean data) {
        Intent intent = new Intent(getContext(), ClbzDetailActivity.class);
        intent.putExtra(ClbzDetailActivity.CLBZ_BEAN_KEY,data);
        intent.putExtra(AwFormActivity.EXTRA_TITLE,"第"+(mDatas.size()-position)+"次发起");
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(View itemView, int position, ClbzDetailBean data) {
        return false;
    }
}
