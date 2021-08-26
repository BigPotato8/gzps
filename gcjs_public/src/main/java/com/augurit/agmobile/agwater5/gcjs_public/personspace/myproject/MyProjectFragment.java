package com.augurit.agmobile.agwater5.gcjs_public.personspace.myproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs_public.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs_public.common.GcjsPuUrlConstant;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.MyProjectBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.ProjectDetailBean;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.common.common.http.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wyt.searchbox.utils.KeyBoardUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

import static com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.ProjectDetailBean.*;

/**
 * @author 创建人 ：SDB
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzsz.facilityquery
 * @modifyMemo 修改备注：
 */

public class MyProjectFragment extends BaseViewListFragment <ProjectDetailBean.ProjectInfoBean>{


    private String keyword = "";
    public static MyProjectFragment newInstance() {
        MyProjectFragment fragment = new MyProjectFragment();
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
        searchView.setVisibility(View.GONE);
        head_container.addView(searchView);
    }

    @Override
    public void onItemClick(View itemView, int position, ProjectDetailBean.ProjectInfoBean data) {
    }

    @Override
    public boolean onItemLongClick(View itemView, int position, ProjectDetailBean.ProjectInfoBean data) {
        return false;
    }

    @Override
    protected BaseViewListAdapter<ProjectDetailBean.ProjectInfoBean> initAdapter() {
        return new MyProjectAdapter(getActivity());
    }

    @Override
    protected Observable<ApiResult<List<ProjectDetailBean.ProjectInfoBean>>> loadDatas(int page, int rows, Map filterParams) {
        Map<String, String> params = new HashMap<>();
        params.put("keyword", keyword);
        params.put("pageNum", page + "");
        params.put("pageSize", rows + "");
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(TextUtils.isEmpty(keyword)? GcjsPuUrlConstant.GET_MY_PROJECT_LIST
                :GcjsPuUrlConstant.GET_MY_PROJECT_SEARCH_LIST, params).map(
                s -> {
                    MyProjectBean listApiResult = new Gson().fromJson(s, new TypeToken<MyProjectBean>() {
                    }.getType());
                    if(!listApiResult.isSuccess()){
                        ToastUtil.shortToast(getActivity(),listApiResult.getMessage());
                        return new ApiResult<>();
                    }
                    ApiResult<List<ProjectDetailBean.ProjectInfoBean>>  apiResult= new ApiResult<>();
                    apiResult.setData(listApiResult.getContent().getList());
                    apiResult.setMessage(listApiResult.getMessage());
                    apiResult.setSuccess(listApiResult.isSuccess());

//                    if (TextUtils.isEmpty(keyword)) {
//                        strings.clear();
//                    }
//                    listApiResult.setData(strings);

                    return apiResult;
                }
        );

    }


}
