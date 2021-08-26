package com.augurit.agmobile.agwater5.gcjs_public.personspace.mymaterial;

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
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.common.common.http.HttpUtil;
import com.baidu.mapapi.common.SysOSUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

public class MyMaterialFragment extends BaseViewListFragment<MaterialBean.ContentBean> {


    private String keyword = "";

    public static MyMaterialFragment newInstance() {
        MyMaterialFragment fragment = new MyMaterialFragment();
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

        searchView.setVisibility(View.GONE);
        et_search.setHint("搜索你要找的材料");
        searchView.findViewById(R.id.search).setOnClickListener(v -> {
            keyword = et_search.getText().toString().trim();
            loadDatas(true);
        });

        et_search.setOnEditorActionListener((v, actionId, event) -> {
            KeyBoardUtils.closeKeyboard(getActivity(), et_search);
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
    public void onItemClick(View itemView, int position, MaterialBean.ContentBean data) {
    }

    @Override
    public boolean onItemLongClick(View itemView, int position, MaterialBean.ContentBean data) {
        return false;
    }

    @Override
    protected BaseViewListAdapter initAdapter() {
        return new MyMaterialAdapter(getActivity());
    }

    @Override
    protected Observable<ApiResult<List<MaterialBean.ContentBean>>> loadDatas(int page, int rows, Map filterParams) {
        Map<String, String> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("pageNum", page + "");
        params.put("pageSize", rows + "");

        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.MY_MATERIAL_LIST, params).map(
                s -> {

                    MaterialBean bean = new Gson().fromJson(s, new TypeToken<MaterialBean>() {
                    }.getType());
                    if (!bean.isSuccess()) {
                        ToastUtil.shortToast(getActivity(),bean.getMessage());
                        return new ApiResult<>();
                    }

                    ApiResult<List<MaterialBean.ContentBean>> listApiResult = new ApiResult<>();
                        if(bean.isSuccess()){
                            listApiResult.setData(bean.getContent());
                            listApiResult.setSuccess(true);
                            listApiResult.setCode(200);
                        }
                    return listApiResult;
                }
        );

    }


}
