package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.source;

import android.text.TextUtils;


import com.augurit.agmobile.agwater5.gcjs_public.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs_public.common.GcjsPuUrlConstant;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.EventItemBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.MultiMaterialBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.MultiSituationBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.OrgAndThemeBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.ProjectDetailBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.ProjectReportBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.StageBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.ThemeAndMultiBean;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.common.common.http.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * @description 申报网络请求
 * @date: $date$ $time$
 * @author: xieruibin
 */
public class ReportRepository {

    /**
     * 获取项目申报信息
     *
     * @return
     */
    public Observable<ApiResult<ProjectReportBean>> getProjectReportInfo(Map<String, String> map) {
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.GET_PROJECT_INFO, map)
                .map(s -> {
                            ApiResult<ProjectReportBean> data =
                                    new Gson().fromJson(s, new TypeToken<ApiResult<ProjectReportBean>>() {
                                    }.getType());
                            return data;
                        }
                )
                .onErrorReturnItem(new ApiResult<>());
    }

    /**
     * 获取我的项目列表
     *
     * @return
     */
    public Observable<ApiResult<List<ProjectReportBean>>> getMyProjectList(Map<String, String> map) {
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.GET_MY_PROJECT, map)
                .map(new Function<String, ApiResult<List<ProjectReportBean>>>() {
                         @Override
                         public ApiResult<List<ProjectReportBean>> apply(String s) throws Exception {
                             JSONObject data = new JSONObject(s);
                             boolean isSuccess = data.getBoolean("success");
                             if (isSuccess) {
                                 JSONObject content = data.getJSONObject("content");
                                 JSONObject list = content.getJSONObject("list");
                                 String rows = list.getJSONArray("rows").toString();
                                 List<ProjectReportBean> reportBeanList = new Gson().fromJson(rows, new TypeToken<List<ProjectReportBean>>() {
                                 }.getType());
                                 ApiResult<List<ProjectReportBean>> apiResult = new ApiResult<>();
                                 apiResult.setData(reportBeanList);
                                 return apiResult;
                             }
                             return new ApiResult<List<ProjectReportBean>>();
                         }
                     }
                ).onErrorReturnItem(new ApiResult<>());
    }

    /**
     * 获取部门列表
     *
     * @return
     */
    public Observable<ApiResult<OrgAndThemeBean>> getDepartmentList() {
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.GET_DEPARTMENT_LIST, null)
                .map(s -> {
                    ApiResult<OrgAndThemeBean> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<OrgAndThemeBean>>() {
                            }.getType());
                    return data;
                });
    }

    /**
     * 获取部门下所有事项列表
     *
     * @param itemName 事项名称
     * @param orgId    部门id
     * @param stageId  阶段id
     * @param themeId  主题id
     * @return
     */
    public Observable<ApiResult<List<EventItemBean>>> getEventListByDepartment(String itemName, String orgId, String stageId, String themeId) {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(itemName)) {
            map.put("itemName", itemName);
        }else{
            map.put("itemName", "");
        }
        if (!TextUtils.isEmpty(orgId)) {
            map.put("orgId", orgId);
        }else{
            map.put("orgId", "");
        }
        if (!TextUtils.isEmpty(stageId)) {
            map.put("stageId", stageId);
        }else{
            map.put("stageId", "");
        }
        if (!TextUtils.isEmpty(themeId)) {
            map.put("themeId", themeId);
        }else{
            map.put("themeId", "");
        }
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.GET_EVENTLIST_BY_DEPARTMENT, map)
                .map(s -> {
                    ApiResult<List<EventItemBean>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<EventItemBean>>>() {
                            }.getType());
                    return data;
                });
    }

    /**
     * 单项-项目详情
     *
     * @param itemVerId
     * @param localCode
     * @return
     */
    public Observable<ApiResult<ProjectDetailBean>> getSingleInfoDetail(String itemVerId, String localCode) {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(itemVerId)) {
            map.put("itemVerId", itemVerId);
        }else{
            map.put("itemVerId", "");
        }
        if (!TextUtils.isEmpty(localCode)) {
            map.put("localCode", localCode);
        }else{
            map.put("localCode", "");
        }
        map.put("token", LoginManager.getInstance().getCurrentUser().getUserType());
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.GET_EVENT_AND_PROJECT_INFO, map)
                .map(new Function<String, ApiResult<ProjectDetailBean>>() {
                    @Override
                    public ApiResult<ProjectDetailBean> apply(String s) throws Exception {
                        ApiResult<ProjectDetailBean> data =
                                new Gson().fromJson(s, new TypeToken<ApiResult<ProjectDetailBean>>() {
                                }.getType());
                        return data;
                    }
                });
    }

    /**
     * 获取主题和部门
     */
    public Observable<ApiResult<ThemeAndMultiBean>> getThemeAndOrg() {
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.GET_THEME_AND_ORG, null)
                .map(s -> {
                    ApiResult<ThemeAndMultiBean> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<ThemeAndMultiBean>>() {
                            }.getType());
                    return data;
                });
    }

    /**
     * 获取阶段
     *
     * @param themeId
     * @return
     */
    public Observable<ApiResult<List<StageBean>>> getMultiState(String themeId) {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(themeId)) {
            map.put("themeId", themeId);
        }else{
            map.put("themeId", "");
        }
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.GET_STAGE, map)
                .map(s -> {
                    ApiResult<List<StageBean>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<StageBean>>>() {
                            }.getType());
                    return data;
                });
    }

    /**
     * 获取并联情形
     *
     * @return
     */
    public Observable<ApiResult<List<MultiSituationBean>>> getMultiSituation(String parentId, String stageId) {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(parentId)) {
            map.put("parentId", parentId);
        }else{
            map.put("parentId", "");
        }
        if (!TextUtils.isEmpty(stageId)) {
            map.put("stageId", stageId);
        }else{
            map.put("stageId", "");
        }
        map.put("token", LoginManager.getInstance().getCurrentUser().getUserType());
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.GET_MULTI_SITUATION, map)
                .map(s -> {
                    ApiResult<List<MultiSituationBean>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<MultiSituationBean>>>() {
                            }.getType());
                    return data;
                });
    }

    /**
     * 获取事项列表
     *
     * @param themeId
     * @param stageId
     * @return
     */
    public Observable<ApiResult<List<EventItemBean>>> getMultiEventList(String themeId, String stageId) {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(themeId)){
            map.put("themeId", themeId);
        }else{
            map.put("themeId", "");
        }
        if (!TextUtils.isEmpty(stageId)){
            map.put("stageId", stageId);
        }else{
            map.put("stageId", "");
        }
        map.put("orgId", "");
        map.put("itemName", "");
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.GET_EVEVTLIST_BY_THEMESTAGE, map)
                .map(s -> {
                    ApiResult<List<EventItemBean>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<EventItemBean>>>() {
                            }.getType());
                    return data;
                });
    }

    /**
     * 查询并联申报所需的所有材料GET_MULTI_MATS
     */
    public Observable<ApiResult<List<MultiMaterialBean>>> getMultiMats(String stageId, String stateIds) {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(stageId)) {
            map.put("stageId", stageId);
        }else{
            map.put("stageId", "");
        }
        if (!TextUtils.isEmpty(stateIds)) {
            map.put("stateIds", stateIds);
        }else{
            map.put("stateIds", "");
        }
        map.put("token", LoginManager.getInstance().getCurrentUser().getUserType());
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.GET_MULTI_MATS, map)
                .map(s -> {
                    ApiResult<List<MultiMaterialBean>> data =
                            new Gson().fromJson(s, new TypeToken<ApiResult<List<MultiMaterialBean>>>() {
                            }.getType());
                    return data;
                });
    }
}
