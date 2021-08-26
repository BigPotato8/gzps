package com.augurit.agmobile.agwater5.mine.sign.source;

import android.text.TextUtils;

import com.augurit.agmobile.agwater5.mine.sign.model.SignBean;
import com.augurit.agmobile.agwater5.mine.sign.model.SignResultBean;
import com.augurit.agmobile.common.lib.json.JsonUtil;
import com.zhouyou.http.EasyHttp;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.mine.sign.source
 * @createTime 创建时间 ：2018-09-10
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class SignRepository implements ISignRepository {
    @Override
    public Observable<SignBean> getSignInfo(String userId, String yearMonth) {
        return EasyHttp.get("rest/app/dailySign/getSignInfo/" + userId + "/" + yearMonth)
                .execute(String.class)
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String>>() {
                    @Override
                    public ObservableSource<? extends String> apply(Throwable throwable) throws Exception {
                        return Observable.just("");
                    }
                })
                .map(new Function<String, SignBean>() {
                    @Override
                    public SignBean apply(String s) throws Exception {
                        if (s.contains("null")|| TextUtils.isEmpty(s)) {
                            return new SignBean();
                        }
                        return JsonUtil.getObject(s, SignBean.class);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<SignResultBean> getSignResult(String userId, String userName, String orgName, String orgCode, double x, double y, String street, String address, String appVersion) {
        return EasyHttp.get("rest/app/dailySign/sign")
                .params("signerId", userId)
                .params("signerName", userName)
                .params("orgName", orgName)
                .params("orgSeq", orgCode)
                .params("x", x + "")
                .params("y", y + "")
                .params("road", street)
                .params("addr", address)
                .params("appVersion", appVersion)
                .execute(String.class)
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String>>() {
                    @Override
                    public ObservableSource<? extends String> apply(Throwable throwable) throws Exception {
                        return Observable.just("");
                    }
                })
                .map(new Function<String, SignResultBean>() {
                    @Override
                    public SignResultBean apply(String s) throws Exception {
                        return JsonUtil.getObject(s, SignResultBean.class);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


}
