package com.augurit.agmobile.agwater5.gcjs.publicaffair;


import android.content.Intent;
import android.view.View;

import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * com.augurit.agmobile.agwater5.gcjs.publicaffair
 * Created by sdb on 2019/4/2  9:23.
 * Desc：
 */

public class GcjsPublicAffairFragment extends BaseViewListFragment {
    @Override
    protected BaseViewListAdapter initAdapter() {
        return new GcjsPublicAffairAdapter(getActivity());
    }

    @Override
    protected Observable<ApiResult<List<String>>> loadDatas(int i, int i1, Map map) {
        return Observable.create((ObservableOnSubscribe<ApiResult<List<String>>>) emitter -> {
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
    public void onItemClick(View view, int i, Object o) {
        startActivity(new Intent(getActivity(), GcjsPublicAffairDetailActivity.class));
    }

    @Override
    public boolean onItemLongClick(View view, int i, Object o) {
        return false;
    }
}
