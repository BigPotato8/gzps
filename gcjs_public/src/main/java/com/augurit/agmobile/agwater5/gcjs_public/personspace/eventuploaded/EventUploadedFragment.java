package com.augurit.agmobile.agwater5.gcjs_public.personspace.eventuploaded;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs_public.common.AwUrlManager;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.common.common.http.HttpUtil;
import com.augurit.common.common.util.StringUtils;
import com.augurit.agmobile.agwater5.gcjs_public.common.GcjsPuUrlConstant;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
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

public class EventUploadedFragment extends BaseViewListFragment<EventBean.ListBean> {
    private String keyword;
    private int mState;
    private User mCurrentUser;


    public static EventUploadedFragment getInstance(@EventState int eventState) {
        EventUploadedFragment eventUploadedFragment = new EventUploadedFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("state", eventState);
        eventUploadedFragment.setArguments(bundle);
        return eventUploadedFragment;
    }

    @Override
    protected void initArguments() {
        super.initArguments();
        mState = getArguments().getInt("state");
        mCurrentUser = LoginManager.getInstance().getCurrentUser();
    }

    @Override
    protected BaseViewListAdapter<EventBean.ListBean> initAdapter() {
        return new EventUploadedAdapter(getActivity(), mState);

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
            head_container.addView(searchView);
        }
    }

    @Override
    protected Observable<ApiResult<List<EventBean.ListBean>>> loadDatas(int pageNum, int pageCount, Map map) {
        Map<String, String> params = new HashMap<>();

//        params.put("token", mCurrentUser == null ? "" : mCurrentUser.getUserType());
        params.put("pageNum", pageNum+ "");
        params.put("pageSize", pageCount+ "");
        params.put("keyword", keyword == null ? "" : keyword);
        params.put("state", mState == EventState.HANDLING ? "1" : "0");
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.GET_EVENT_UPLOADED_LIST, params)
                .map(s -> {
                    if (StringUtils.isJson(s)) {
                        ApiResult<EventBean> listApiResult = new Gson().fromJson(s, new TypeToken<ApiResult<EventBean>>() {
                        }.getType());

                        if (listApiResult.isSuccess()) {
                            EventBean data = listApiResult.getData();
                            List<EventBean.ListBean> list = data.getList();
                            ApiResult<List<EventBean.ListBean>> apiResult = new ApiResult<>();
                            apiResult.setData(list);
                            return apiResult;
                        }else{
                            ToastUtil.shortToast(getActivity(),listApiResult.getMessage());
                            return new ApiResult<>();
                        }
                    }
                    return new ApiResult<List<EventBean.ListBean>>();
                });
    }

    @Override
    public void onItemClick(View view, int i, EventBean.ListBean o) {

    }

    @Override
    public boolean onItemLongClick(View view, int i, EventBean.ListBean o) {
        return false;
    }
}
