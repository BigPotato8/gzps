package com.augurit.common.common.form;

import android.content.Context;

import com.augurit.agmobile.busi.bpm.form.model.FormInfo;
import com.augurit.agmobile.busi.bpm.form.model.FormMeta;
import com.augurit.agmobile.busi.bpm.form.source.IFormRepository;
import com.augurit.agmobile.common.lib.net.model.ApiResult;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 水务表单仓库
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.form.source
 * @createTime 创建时间 ：2018/8/28
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/8/28
 * @modifyMemo 修改备注：
 */
public class AwFormRepository implements IFormRepository {

    protected AwFormConfig mFormConfig;

    public AwFormRepository(Context context) {
        mFormConfig = new AwFormConfig(context);
    }

    @Override
    public Observable<FormInfo> getFormInfo(String orgId, String formCode) {
        return Observable.just(formCode)
                .map(mFormConfig::getFormInfo)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Integer> updateFormInfos(Map<String, String> params) {
        return Observable.just(1);
    }

    /**
     * @deprecated  暂时没有用到
     */
    @Deprecated
    @Override
    public Observable<ApiResult<List<FormMeta>>> getUserForms(boolean withWorkFlow, String orgId, String userId, boolean refresh) {
        return null;
    }

    /**
     * @deprecated  暂时没有用到
     */
    @Deprecated
    @Override
    public Disposable getUserForms(boolean withWorkFlow, String orgId, String userId, Consumer<? super ApiResult<List<FormMeta>>> onNext, Consumer<? super Throwable> onError) {
        return null;
    }
}
