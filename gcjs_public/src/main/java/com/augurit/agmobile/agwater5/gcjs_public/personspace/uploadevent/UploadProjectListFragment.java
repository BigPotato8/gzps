package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent;

import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.ProjectReportBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.source.ReportRepository;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.wyt.searchbox.utils.KeyBoardUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent
 * Created by sdb on 2019/4/19  9:55.
 * Desc：
 */

public class UploadProjectListFragment extends BaseViewListFragment<ProjectReportBean> {
    private String keyword;
    private ReportRepository mRepository;

    @Override
    protected BaseViewListAdapter initAdapter() {
        return new UploadProjectListAdapter(getActivity());
    }


    @Override
    protected void initView() {
        super.initView();
        View searchView = View.inflate(getActivity(), R.layout.gcjs_search_activity, null);
        EditText et_search = searchView.findViewById(R.id.et_search);
        et_search.setHint("请输入您的项目代码..");
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
        mRepository = new ReportRepository();

    }

    @Override
    protected Observable<ApiResult<List<ProjectReportBean>>> loadDatas(int i, int i1, Map map) {
//        if(TextUtils.isEmpty(keyword)){
//            return Observable.create(emitter -> {
//                ApiResult<List<ProjectReportBean>> listApiResult = new ApiResult<>();
//                listApiResult.setCode(200);
//                emitter.onNext(listApiResult);
//            });
//        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("pageNum", i + "");
        hashMap.put("pageSize", i1 + "");
        hashMap.put("projectCode", keyword == null ? "" : keyword);
        hashMap.put("token", LoginManager.getInstance().getCurrentUser().getUserType());

        if (!TextUtils.isEmpty(keyword)) {
            return mRepository.getProjectReportInfo(hashMap)
                    .map(projectReportBeanApiResult -> {
                        if (projectReportBeanApiResult.isSuccess()) {
                            ApiResult<List<ProjectReportBean>> apiResult = new ApiResult<>();
                            apiResult.setCode(projectReportBeanApiResult.getCode());
                            apiResult.setSuccess(projectReportBeanApiResult.isSuccess());
                            List<ProjectReportBean> list = new ArrayList<>();
                            list.add(projectReportBeanApiResult.getData());
                            apiResult.setData(list);
                            return apiResult;
                        } else {
                            ApiResult<List<ProjectReportBean>> listApiResult = new ApiResult<>();
                            listApiResult.setCode(200);
                            return listApiResult;
                        }
                    })
                    .onErrorReturn(throwable -> {
                        ApiResult<List<ProjectReportBean>> listApiResult = new ApiResult<>();
                        listApiResult.setCode(200);
                        return listApiResult;
                    })
                    ;
        } else {
            return mRepository.getMyProjectList(hashMap)
                    .map(projectReportBeanApiResult -> {
//                        if (projectReportBeanApiResult.isSuccess()) {
//                            ApiResult<List<ProjectReportBean>> apiResult = new ApiResult<>();
//                            apiResult.setCode(projectReportBeanApiResult.getCode());
//                            apiResult.setSuccess(projectReportBeanApiResult.isSuccess());
//                            List<ProjectReportBean> list = new ArrayList<>();
//                            list.addAll(projectReportBeanApiResult.getData());
//                            apiResult.setData(projectReportBeanApiResult.getData());
//                            return apiResult;
//                        } else {
//                            ApiResult<List<ProjectReportBean>> listApiResult = new ApiResult<>();
//                            listApiResult.setCode(200);
                        return projectReportBeanApiResult;
//                        }
                    })
                    .onErrorReturn(throwable -> {
                        ApiResult<List<ProjectReportBean>> listApiResult = new ApiResult<>();
                        listApiResult.setCode(200);
                        return listApiResult;
                    })
                    ;
        }


    }

    @Override
    public void onItemClick(View view, int i, ProjectReportBean o) {

    }

    @Override
    public boolean onItemLongClick(View view, int i, ProjectReportBean o) {
        return false;
    }
}
