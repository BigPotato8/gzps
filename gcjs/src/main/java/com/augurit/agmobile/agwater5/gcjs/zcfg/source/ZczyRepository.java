package com.augurit.agmobile.agwater5.gcjs.zcfg.source;


import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.zcfg.model.DirsFileBean;
import com.augurit.agmobile.agwater5.gcjs.zcfg.model.ZczyBean;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.request.GetRequest;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class ZczyRepository {
    /**
     * 获取政策指引的tab和item
     *
     * @return
     */
    public Observable<ApiResult<List<ZczyBean>>> getOPGuideDirs() {
        GetRequest getRequest = EasyHttp.get(GcjsUrlConstant.GET_ZCZY_GUIDE_DIRS)
                .baseUrl(AwUrlManager.serverUrl());
        return getRequest.execute(String.class)
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just("");
                })
                .map(s -> {
                    System.out.println(s);
                    ApiResult<List<ZczyBean>> nodeInfos = new Gson().fromJson(s, new TypeToken<ApiResult<List<ZczyBean>>>() {
                    }.getType());
                    return nodeInfos;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 根据item获取文件列表
     * @return
     */
    public Observable<ApiResult<List<DirsFileBean>>> getGuideFilesByDir(String dirId) {
        GetRequest getRequest = EasyHttp.get(GcjsUrlConstant.GET_ZCZY_FILES_BY_DIRS + "/" + dirId)
                .baseUrl(AwUrlManager.serverUrl());
        return getRequest.execute(String.class)
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just("");
                })
                .map(s -> {
                    System.out.println(s);
                    ApiResult<List<DirsFileBean>> nodeInfos = new Gson().fromJson(s, new TypeToken<ApiResult<List<DirsFileBean>>>() {
                    }.getType());
                    return nodeInfos;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
