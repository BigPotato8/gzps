package com.augurit.agmobile.agwater5.gcjs_public.personspace.approveinfo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs_public.ApproveInfoBean;
import com.augurit.agmobile.agwater5.gcjs_public.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs_public.common.GcjsPuUrlConstant;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.eventuploaded.EventBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.eventuploaded.EventState;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.projectprocess.model.MyProjectBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.projectprocess.model.ProjectDetailBean;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.common.common.http.HttpUtil;
import com.augurit.common.common.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wyt.searchbox.utils.KeyBoardUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent
 * Created by sdb on 2019/4/19  9:55.
 * Desc：
 */

public class ApproveInfoFragment extends BaseViewListFragment<ApproveInfoBean> {
    private String keyword;
    private int mState;


    public static ApproveInfoFragment getInstance() {
        ApproveInfoFragment approveInfoFragment = new ApproveInfoFragment();
        return approveInfoFragment;
    }

    @Override
    protected BaseViewListAdapter<ApproveInfoBean> initAdapter() {
        return new ApproveInfoAdapter(getActivity());
    }

    @Override
    protected void initView() {
        super.initView();
        if (mState != EventState.DRAFT) {
            View searchView = View.inflate(getActivity(), R.layout.gcjs_search_activity, null);
            EditText et_search = searchView.findViewById(R.id.et_search);
            //et_search.setHint("请输入您的项目代码或项目名称..");
            et_search.setHint("请输入您的项目代码..");
            searchView.findViewById(R.id.search).setOnClickListener(v -> {
                keyword =  et_search.getText().toString().trim();
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
    }

    @Override
    protected Observable<ApiResult<List<ApproveInfoBean>>> loadDatas(int pageNum, int pageCount, Map map) {
        Map<String, String> params = new HashMap<>();
        params.put("pageNum", pageNum+ "");
        params.put("pageSize", pageCount+ "");
        params.put("keyword", keyword == null ? "" : keyword);
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.GET_APPROVE_INFO, params)
                .map(s -> {
                    if (StringUtils.isJson(s)) {
                        ApiResult <MyProjectBean.ProjectBean>apiResult  =   new Gson().fromJson(s, new TypeToken<ApiResult<MyProjectBean.ProjectBean>>() {
                        }.getType());
                        List<ProjectDetailBean.ProjectInfoBean> list = apiResult.getData().getList();
                        Gson gs = new Gson();
                        List<ApproveInfoBean> data =   new Gson().fromJson(gs.toJson(list), new TypeToken<List<ApproveInfoBean>>() {
                        }.getType());

                        ApiResult<List<ApproveInfoBean>> listApiResult = new ApiResult<>();
                        listApiResult.setData(data);
                        return listApiResult;
                    }
                    return new ApiResult<List<ApproveInfoBean>>();
                });
    }

    @Override
    public void onItemClick(View view, int i, ApproveInfoBean o) {

    }

    @Override
    public boolean onItemLongClick(View view, int i, ApproveInfoBean o) {
        return false;
    }
}
