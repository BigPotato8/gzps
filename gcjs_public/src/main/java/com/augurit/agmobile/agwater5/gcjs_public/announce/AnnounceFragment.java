package com.augurit.agmobile.agwater5.gcjs_public.announce;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs_public.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs_public.common.GcjsPuUrlConstant;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.common.common.http.HttpUtil;
import com.wyt.searchbox.utils.KeyBoardUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.zcfg
 * Created by sdb on 2019/5/30  11:42.
 * Desc：
 */

public class AnnounceFragment extends BaseViewListFragment<String> {


    private String state, keyword, keyword1;
    public static final String ANNOUNCE_STATE_FIRST = "0";
    public static final String ANNOUNCE_STATE_SECOND = "1";
    public static final String ANNOUNCE_STATE_THIRD = "2";

    @Override
    protected void initView() {
        super.initView();
        View searchView = View.inflate(getActivity(), R.layout.announce_search_view, null);
        EditText et_search = searchView.findViewById(R.id.et_search);
        EditText et_search1 = searchView.findViewById(R.id.et_search1);
        searchView.findViewById(R.id.search).setOnClickListener(v -> {
            keyword = et_search.getText().toString().trim();
            keyword1 = et_search1.getText().toString().trim();
            loadDatas(true);
        });

        et_search.setOnEditorActionListener((v, actionId, event) -> {
            KeyBoardUtils.closeKeyboard(getActivity(), et_search);
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                keyword = et_search.getText().toString().trim();
                keyword1 = et_search1.getText().toString().trim();
                if (TextUtils.isEmpty(keyword) && TextUtils.isEmpty(keyword1)) {
                    return true;
                }
                loadDatas(true);
                return true;
            }
            return false;
        });
        head_container.addView(searchView);
    }


    public static AnnounceFragment getInsance(String orgCode) {
        AnnounceFragment announceFragment = new AnnounceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("state", orgCode);
        announceFragment.setArguments(bundle);
        return announceFragment;
    }


    @Override
    protected void initArguments() {
        super.initArguments();

        state = getArguments().getString("state", "");
    }


    @Override
    protected BaseViewListAdapter<String> initAdapter() {
        return new AnnounceAdapter(getActivity());
    }

    @Override
    protected Observable<ApiResult<List<String>>> loadDatas(int page, int rows, Map filterParams) {

        Map<String, String> params = new HashMap<>();
//        params.put("projInfoName", keyword);
//        params.put("applyinstCode", keyword1);
//        params.put("applyState", state);
        params.put("pageNum", page + "");
        params.put("pageSize", rows + "");
        params.put("keyword",  "");
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.ANNOUNCE_LIST, params).map(
                s -> {
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

                    return listApiResult;
                }
        );

    }

    @Override
    public void onItemClick(View itemView, int position, String data) {

//        ArrayList<String> rawFiles = new ArrayList<>();
//        rawFiles.add(data.getUrl());
//        Intent intent = WatchImageOrPdfActivity.newIntent(getActivity(), rawFiles);
//        getActivity().startActivity(intent);

    }

    @Override
    public boolean onItemLongClick(View itemView, int position, String data) {
        return false;
    }
}
