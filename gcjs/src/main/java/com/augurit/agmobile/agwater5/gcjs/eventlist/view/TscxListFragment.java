package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.content.Intent;
import android.view.View;

import com.augurit.agmobile.agwater5.gcjs.eventlist.model.TscxBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter.EventDetailTscxAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.common.common.form.AwFormActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * 特殊程序列表
 */
public class TscxListFragment extends BaseViewListFragment<TscxBean.SpecialListBean> {
    TscxBean mBean;
    public static TscxListFragment getInstance(TscxBean bean){
        TscxListFragment fragment = new TscxListFragment();
        fragment.mBean = bean;

        return fragment;
    }

    @Override
    protected BaseViewListAdapter<TscxBean.SpecialListBean> initAdapter() {
        return new EventDetailTscxAdapter(getActivity());
    }

    @Override
    protected void initView() {
        super.initView();
        loadDatas(true);
    }

    @Override
    protected Observable<ApiResult<List<TscxBean.SpecialListBean>>> loadDatas(int page, int rows, Map<String, String> filterParams) {
        refresh_layout.setEnableLoadMore(false);
        refresh_layout.setEnableRefresh(false);
        return Observable.create(new ObservableOnSubscribe<ApiResult<List<TscxBean.SpecialListBean>>>() {
            @Override
            public void subscribe(ObservableEmitter<ApiResult<List<TscxBean.SpecialListBean>>> emitter) throws Exception {
                ApiResult<List<TscxBean.SpecialListBean>> apiResult = new ApiResult<>();
                apiResult.setSuccess(true);
                List<TscxBean.SpecialListBean> list = new ArrayList<>();
                if (mBean!=null){
                    list.addAll(mBean.getSpecialList());
                }
                apiResult.setData(list);

                emitter.onNext(apiResult);
            }
        });

    }

    @Override
    public void onItemClick(View itemView, int position, TscxBean.SpecialListBean data) {
        Intent intent = new Intent(getContext(), TscxDetailActivity.class);
        intent.putExtra(TscxDetailActivity.TSCX_INFO,data);
        intent.putExtra(AwFormActivity.EXTRA_TITLE,"第"+(mDatas.size()-position)+"次发起");
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(View itemView, int position, TscxBean.SpecialListBean data) {
        return false;
    }
}
