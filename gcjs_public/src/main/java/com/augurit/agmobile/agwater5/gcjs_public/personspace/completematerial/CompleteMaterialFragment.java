package com.augurit.agmobile.agwater5.gcjs_public.personspace.completematerial;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * @author 创建人 ：SDB
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzsz.facilityquery
 * @modifyMemo 修改备注：
 */

public class CompleteMaterialFragment extends BaseViewListFragment {


    private String keyword = "";

    public static CompleteMaterialFragment newInstance() {
        CompleteMaterialFragment fragment = new CompleteMaterialFragment();
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
    public boolean onItemLongClick(View itemView, int position, Object data) {
        return false;
    }

    @Override
    protected BaseViewListAdapter initAdapter() {
        return new CompleteMaterialAdapter(getActivity());
    }

    @Override
    protected Observable<ApiResult<List<String>>> loadDatas(int page, int rows, Map filterParams) {
        Map<String, String> params = new HashMap<>();
//        params.put("userId", keyword);
        params.put("pageNum", page + "");
        params.put("pageSize", rows + "");

        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.COMPLETE_MATERIAL_LIST, params).map(
                s -> {//TODO   s: {"success":false,"message":"查材料补全列表查询接口异常"}
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
    public void onItemClick(View itemView, int position, Object data) {

    }
}
