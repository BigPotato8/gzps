package com.augurit.agmobile.agwater5.gcjs.gzcn;


import android.content.Intent;
import android.view.View;

import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * com.augurit.agmobile.agwater5.gcjs.bszn
 * Created by sdb on 2019/4/3  14:40.
 * Desc：程序性审查
 */

public class ProjectCheckFragment extends BaseViewListFragment<String> {


    @Override
    protected BaseViewListAdapter<String> initAdapter() {
        return new GzcnListAdapter(getActivity());
    }

    @Override
    protected Observable<ApiResult<List<String>>> loadDatas(int i, int i1, Map map) {
        return Observable.create(emitter -> {
            List<String> list = new ArrayList<>();
            list.add("1");
            list.add("1");
            list.add("1");
            list.add("1");
            list.add("1");
            list.add("1");
            list.add("1");
            list.add("1");
            list.add("1");
            list.add("1");
            list.add("1");
            list.add("1");
            ApiResult<List<String>> listApiResult = new ApiResult<>();
            listApiResult.setData(list);
            emitter.onNext(listApiResult);
        });
    }

    @Override
    public void onItemClick(View view, int i, String o) {
        getActivity().startActivity(new Intent(getActivity(), GzcnDetailActivity.class));
    }

    @Override
    public boolean onItemLongClick(View view, int i, String o) {
        return false;
    }
}
