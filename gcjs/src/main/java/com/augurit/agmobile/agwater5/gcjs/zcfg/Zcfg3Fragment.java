package com.augurit.agmobile.agwater5.gcjs.zcfg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.common.FilePreviewUtil;
import com.augurit.agmobile.agwater5.gcjs.zcfg.model.DirsFileBean;
import com.augurit.agmobile.agwater5.gcjs.zcfg.model.ZczyBean;
import com.augurit.agmobile.agwater5.gcjs.zcfg.source.ZczyRepository;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.validate.ListUtil;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.zcfg
 * Created by sdb on 2019/5/30  11:42.
 * Desc：
 */

public class Zcfg3Fragment extends BaseViewListFragment<DirsFileBean> {
    private List<ZczyBean.ChildrenBean> data;
    private ZczyBean.ChildrenBean selectItem;
    RecyclerView rv_navigation;
    NavigationAdapter navigationAdapter;
    ZczyRepository zczyRepository;
    public static Zcfg3Fragment getInstance(List<ZczyBean.ChildrenBean> data){
        Zcfg3Fragment fragment = new Zcfg3Fragment();
        fragment.data = data;
        fragment.zczyRepository = new ZczyRepository();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_zcfg3_layout,container,false);
        initArguments();
        initView();
        return mView;
    }

    @Override
    protected void initArguments() {
        super.initArguments();
        navigationAdapter = new NavigationAdapter(getActivity(),data);

        navigationAdapter.setClick(item -> {
            selectItem = item;
            loadDatas(true);
        });
    }

    @Override
    protected void initView() {
        super.initView();
        rv_navigation = mView.findViewById(R.id.rv_navigation);
        rv_navigation.setAdapter(navigationAdapter);
        rv_navigation.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (!ListUtil.isEmpty(data)) {
            selectItem = data.get(0);
            loadDatas(true);
        }
    }

    @Override
    protected BaseViewListAdapter<DirsFileBean> initAdapter() {
        return new DetailAdapter(getActivity());
    }

    @Override
    protected Observable<ApiResult<List<DirsFileBean>>> loadDatas(int page, int rows, Map filterParams) {
        refresh_layout.setNoMoreData(true);
        return zczyRepository.getGuideFilesByDir(selectItem.getId());

    }

    @Override
    public void onItemClick(View itemView, int position, DirsFileBean data) {
        FilePreviewUtil.downFile(getActivity(),data.getAttName(),data.getAttFormat(),data.getDetailId());

    }

    @Override
    public boolean onItemLongClick(View itemView, int position, DirsFileBean data) {
        return false;
    }
}
