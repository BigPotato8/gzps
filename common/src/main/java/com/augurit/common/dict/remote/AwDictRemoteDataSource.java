package com.augurit.common.dict.remote;

import com.augurit.agmobile.busi.bpm.dict.model.Dict;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.dict.model.DictTreeItem;
import com.augurit.agmobile.busi.bpm.dict.source.remote.DictRemoteDataSource;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.common.lib.json.JsonUtil;
import com.augurit.common.common.manager.AwUrlConstant;
import com.augurit.common.common.manager.AwUrlManager;
import com.google.gson.reflect.TypeToken;
import com.zhouyou.http.EasyHttp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.HttpException;

/**
 * @AUTHOR 创建人:yaowang
 * @VERSION 版本:1.0
 * @PACKAGE 包名:com.augurit.agmobile.agwater5.dict.remote
 * @CREATETIME 创建时间:2018/8/29
 * @MODIFYTIME 修改时间:
 * @MODIFYBY 修改人:
 * @MODIFYMEMO 修改备注:
 */
public class AwDictRemoteDataSource extends DictRemoteDataSource {

    @Override
    public Observable<List<Dict>> getAllDict() {
        return EasyHttp.get(AwUrlConstant.AW_GET_ALL_DICTS)
                .baseUrl(AwUrlManager.serverUrl())
//                .baseUrl(LoginManager.getInstance().getSettings().getServerUrl().concat(AwUrlConstant.PORT_OPUS_REST))
                .params("typeKeyword", "")
                .params("orgId", LoginManager.getInstance().getCurrentUser() != null ? LoginManager.getInstance().getCurrentUser().getOrgId() : "")
                .execute(String.class)
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just("");
                })
                .map(s -> {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray data = jsonObject.getJSONArray("data");
                    return JsonUtil.getObject(data.toString(), new TypeToken<List<Dict>>() {
                    }.getType());
                });
    }


    @Override
    public Observable<List<DictItem>> getDictItemByParentTypeCode(String parentTypeCode) {
        return EasyHttp.get(AwUrlConstant.AW_GET_DICTITEM_BY_TYPECODE)
                .baseUrl(AwUrlManager.serverUrl())
                .params("typeCode", parentTypeCode)
                .params("orgId", LoginManager.getInstance().getCurrentUser() != null ? LoginManager.getInstance().getCurrentUser().getOrgId() : "")
                .execute(String.class)
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just("");
                })
                .map(s -> {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray data = jsonObject.getJSONArray("data");
                    return JsonUtil.getObject(data.toString(), new TypeToken<List<DictItem>>() {
                    }.getType());
                });
    }


    @Override
    public Observable<List<DictTreeItem>> getDictTreeItemByParentTypeCode(String parentTypeCode) {
        return EasyHttp.get(AwUrlConstant.AW_GET_DICTTREEITEM_BY_TYPECODE)
                .baseUrl(AwUrlManager.serverUrl())
                .params("typeCode", parentTypeCode)
                .params("orgId", LoginManager.getInstance().getCurrentUser() != null ? LoginManager.getInstance().getCurrentUser().getOrgId() : "")
                .execute(String.class)
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just("");
                })
                .map(s -> {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray data = jsonObject.getJSONArray("data");
                    return JsonUtil.getObject(data.toString(), new TypeToken<List<DictTreeItem>>() {
                    }.getType());
                });
    }

}
