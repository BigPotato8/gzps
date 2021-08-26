package com.augurit.agmobile.agwater5.gcjs_public.login.method;

import android.text.TextUtils;
import android.util.Base64;

import com.augurit.agmobile.agwater5.gcjs_public.common.GcjsPuUrlConstant;
import com.augurit.agmobile.agwater5.gcjs_public.login.LoginConstant;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.method.ILoginMethod;
import com.augurit.agmobile.busi.common.login.model.Authentication;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.busi.common.login.source.ILoginDataSource;
import com.augurit.agmobile.busi.common.login.source.LoginDataSource;
import com.augurit.agmobile.busi.common.organization.model.Organization;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.common.common.util.MD5StringUtil;
import com.augurit.common.common.util.SM3;
import com.augurit.common.common.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhouyou.http.EasyHttp;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmList;
import retrofit2.HttpException;

/**
 * @AUTHOR 创建人:yaowang
 * @VERSION 版本:1.0
 * @PACKAGE 包名:com.augurit.agmobile.agwater5.login.method
 * @CREATETIME 创建时间:2018/8/29
 * @MODIFYTIME 修改时间:
 * @MODIFYBY 修改人:
 * @MODIFYMEMO 修改备注:
 */
public class AwSsoAccountLoginMethod implements ILoginMethod {
    protected String mUserName;
    protected String mPassword;
    protected ILoginDataSource mDataSource;
    protected String mAuthBaseUrl;
    protected String mOpusBaseUrl;
    protected int mTimeOut = 15000;

    protected User mUser;
    protected List<Organization> mOrgs;

    public AwSsoAccountLoginMethod() {
        mDataSource = LoginDataSource.getInstance();
//        mAuthBaseUrl = LoginConstant.LOGIN_SERVER_URL;
        mAuthBaseUrl = LoginManager.getInstance().getSettings().getServerUrl().concat(LoginConstant.AW_PORT_AUTHENTICATION);
//        mOpusBaseUrl = LoginManager.getInstance().getSettings().getServerUrl().concat(AwUrlConstant.AW_PORT_OPUS_REST);
    }

    /**
     * 设置自定义的数据存取<br>
     * 默认使用{@link LoginDataSource}
     *
     * @param dataSource dataSource
     * @return this
     */
    public AwSsoAccountLoginMethod setDataSource(ILoginDataSource dataSource) {
        mDataSource = dataSource;
        return this;
    }

    /**
     * 设置登录参数，同时也是获取组织参数<br>
     * 调用{@link #login()} 前请确保调用了此方法
     *
     * @param userName 用户名
     * @param password 密码
     * @return this
     */
    public ILoginMethod setParams(String userName, String password) {
        mUserName = userName;
        mPassword = password;
        return this;
    }

    /**
     * 在线登录
     *
     * @return 包含User对象的Observable
     */
    @Override
    public Observable<ApiResult<User>> login() {
        return authenticate()
                .flatMap(authenticationApiResult -> {
                    if (authenticationApiResult.isSuccess()) {
                        return getUserInfo(authenticationApiResult.getData());
                    } else {
                        ApiResult<User> apiResult = new ApiResult<>();
                        apiResult.setSuccess(false);
                        apiResult.setMessage(authenticationApiResult.getMessage());
                        return Observable.just(apiResult);
                    }
                });
    }

    /**
     * SSO 登陆 获取授权信息
     */
    protected Observable<ApiResult<Authentication>> authenticate() {

//        FormBody.Builder builder = new FormBody.Builder();
//        builder.add("username", mUserName);
//        builder.add("password", mPassword);
//        builder.add("orgId", "0368948a-1cdf-4bf8-a828-71d796ba89f6");
//        builder.add("deviceType", "normal");
//        builder.add("isApp", "true");
//        FormBody build = builder.build();

        return EasyHttp.post(GcjsPuUrlConstant.AW_SSO_GET_AUTHENTICATION_PUBLIC)
//                .params("userName", "三水")
//                .params("password", SM3.encrypt(MD5StringUtil.MD5Encode("123","utf-8")))
                .params("userName", mUserName)
                .params("password", SM3.encrypt(MD5StringUtil.MD5Encode(mPassword,"utf-8")))
//                .params("orgId","B")
//                .params("deviceType","normal")
//                .params("isApp","true")
//                .requestBody(build)
                .headers("Authorization", "Basic b3B1cy1yZXN0Om9wdXMtcmVzdDEyMw==")
                .headers("Content-Type", "application/x-www-form-urlencoded")
                .baseUrl(mAuthBaseUrl)
                .connectTimeout(mTimeOut)
                .retryCount(0)
                .execute(String.class)
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just(TextUtils.isEmpty(throwable.getMessage()) ? "连接失败" : throwable.getMessage());
                })
                .map(result -> {
                    ApiResult<Authentication> apiResult = new ApiResult<>();
                    if (StringUtils.isJson(result)) {
                        ApiResult<String> dataResult = new Gson().fromJson(result, new TypeToken<ApiResult<String>>() {
                        }.getType());
                        if (dataResult.isSuccess()) {
                            mUser = new User();
                            mUser.setLoginName(mUserName);
                            mUser.setUserName(mUserName);
                            mUser.setOrgId(mUserName);
                            //用这个字段当token
                            mUser.setUserType(dataResult.getData());
                            apiResult.setSuccess(true);
                        } else {
                            apiResult.setSuccess(false);
                            apiResult.setMessage("登录失败");
                        }
                    } else {
                        apiResult.setSuccess(false);
                        apiResult.setMessage("登录失败");
                    }
                    return apiResult;
                })
                .subscribeOn(Schedulers.io());
    }

    /**
     * 生成登录授权接口所需header
     *
     * @return
     */
    protected String generateAuthenticationHeader() {
        byte[] value = "opus-rest:opus-rest123".getBytes();
        return "Basic " + Base64.encodeToString(value, 0, value.length, Base64.DEFAULT).replace("\n", "");
    }

    /**
     * 获取用户信息
     *
     * @param authentication 授权信息
     */
    protected Observable<ApiResult<User>> getUserInfo(Authentication authentication) {
        return Observable.fromCallable(() -> {
            ApiResult<User> apiResult = new ApiResult<>();
            if (mUser != null) {
                mUser.setLoginPwd(mPassword);
                mUser.setAuthentication(authentication);
                apiResult.setSuccess(true);
                apiResult.setData(mUser);
            }
            return apiResult;
        }).subscribeOn(Schedulers.io());
    }

    /**
     * 离线登录
     *
     * @return 包含User对象的Observable
     */
    @Override
    public Observable<ApiResult<User>> loginOffline() {
        return Observable.just(mDataSource.getAllUsers(false))
                .map(users -> {
                    ApiResult<User> apiResult = new ApiResult<>();
                    for (User user : users) {
                        if (user.getLoginName().equals(mUserName)) {
                            if (user.getLoginPwd().equals(mPassword)) { // TODO 加密
                                apiResult.setSuccess(true);
                                apiResult.setData(user);
                            } else {
                                apiResult.setSuccess(false);
                                apiResult.setMessage("密码错误");
                            }
                            return apiResult;
                        }
                    }
                    apiResult.setSuccess(false);
                    apiResult.setMessage("离线登录失败：用户不存在或未在线登陆过");
                    return apiResult;
                });
    }

    /**
     * 在线获取用户所属组织列表
     *
     * @return 包含组织列表的Observable
     */
    @Override
    public Observable<ApiResult<List<Organization>>> getOrganizations() {
        return Observable.fromCallable(() -> {
            ApiResult<List<Organization>> apiResult = new ApiResult<>();
            apiResult.setSuccess(true);
            apiResult.setData(mOrgs);
            return apiResult;
        }).subscribeOn(Schedulers.io());
    }

    /**
     * 离线获取用户所属组织列表
     *
     * @return 包含组织列表的Observable
     */
    @Override
    public Observable<ApiResult<List<Organization>>> getOrganizationsOffline() {
        return loginOffline()
                .map(userApiResult -> {
                    ApiResult<List<Organization>> apiResult = new ApiResult<>();
                    if (userApiResult.isSuccess()) {
                        RealmList<Organization> organizations = userApiResult.getData().getOrganizations();
                        apiResult.setSuccess(true);
                        apiResult.setData(organizations);
                    } else {
                        apiResult.setSuccess(false);
                        apiResult.setMessage("获取用户组织失败");
                    }
                    return apiResult;
                });
    }
}
