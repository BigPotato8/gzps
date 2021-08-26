package com.augurit.agmobile.agwater5.gcjs.serach;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.wyt.searchbox.utils.KeyBoardUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author 创建人 ：SDB
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzsz.facilityquery
 * @modifyMemo 修改备注：
 */

public class SerachFragment extends BaseViewListFragment {


    private String keyword = "";

    public static SerachFragment newInstance() {
        SerachFragment fragment = new SerachFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        View searchView = View.inflate(getActivity(), R.layout.gcjs_search_activity, null);
        EditText et_search = searchView.findViewById(R.id.et_search);
        searchView.findViewById(R.id.search).setOnClickListener(v -> {
            keyword = et_search.getText().toString().trim();
            loadDatas(true);
        });

        et_search.setOnEditorActionListener((v, actionId, event) -> {
            KeyBoardUtils.closeKeyboard(getActivity(),et_search);
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                keyword = et_search.getText().toString().trim();
                if (TextUtils.isEmpty(keyword)) {
                    return true;
                }
                loadDatas(true);
                return true;
            }
            return false;
        });
        head_container.addView(searchView);
    }

    @Override
    public void onItemClick(View itemView, int position, Object data) {
    }

    @Override
    public boolean onItemLongClick(View itemView, int position, Object data) {
        return false;
    }

    @Override
    protected BaseViewListAdapter initAdapter() {
        return new SearchAdapter(getActivity());
    }

    @Override
    protected Observable<ApiResult<List<String>>> loadDatas(int page, int rows, Map filterParams) {
        refresh_layout.setNoMoreData(true);

        return Observable.create(emitter -> {
            List<String> strings = new ArrayList<>();
            strings.add("111111111");
            strings.add("111111111");
            strings.add("111111111");
            strings.add("111111111");
            strings.add("111111111");
            strings.add("111111111");
            strings.add("111111111");
            strings.add("111111111");
            strings.add("111111111");
            ApiResult<List<String>> listApiResult = new ApiResult<>();
            if (TextUtils.isEmpty(keyword)) {
                strings.clear();
            }
            listApiResult.setData(strings);
            emitter.onNext(listApiResult);
        });
    }


}
