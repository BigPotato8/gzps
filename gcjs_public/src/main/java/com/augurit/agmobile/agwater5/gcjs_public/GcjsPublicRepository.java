package com.augurit.agmobile.agwater5.gcjs_public;

import com.augurit.agmobile.agwater5.gcjs_public.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs_public.common.GcjsPuUrlConstant;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.projectprocess.model.MyProjectBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.projectprocess.model.ProjectDetailBean;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.common.common.http.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @description 工程建设公众版网络请求
 * @date: $date$ $time$
 * @author: xieruibin
 */
public class GcjsPublicRepository {

    /**
     * 首页 --> 审批情况
     */
    public Observable<ApiResult<List<ApproveInfoBean>>> getApproveInfo() {
        Map<String, String> param = new HashMap<>();
        param.put("pageNum", "1");
        param.put("pageSize", "15");
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.GET_APPROVE_INFO, param)
                .map(s -> {
                    ApiResult <MyProjectBean.ProjectBean>apiResult  =   new Gson().fromJson(s, new TypeToken<ApiResult<MyProjectBean.ProjectBean>>() {
                    }.getType());
                    List<ProjectDetailBean.ProjectInfoBean> list = apiResult.getData().getList();
                    Gson gs = new Gson();
                List<ApproveInfoBean> data =   new Gson().fromJson(gs.toJson(list), new TypeToken<List<ApproveInfoBean>>() {
                            }.getType());

                    ApiResult<List<ApproveInfoBean>> listApiResult = new ApiResult<>();
                    listApiResult.setData(data);
                    return listApiResult;
                });
    }


}
