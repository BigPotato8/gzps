package com.augurit.common.login;

import android.content.Context;
import android.util.Base64;

import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.method.ILoginMethod;
import com.augurit.agmobile.busi.common.login.model.Authentication;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.busi.common.login.source.ILoginDataSource;
import com.augurit.agmobile.busi.common.login.source.LoginDataSource;
import com.augurit.agmobile.busi.common.organization.model.Organization;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.sp.SharedPreferencesUtil;
import com.augurit.common.common.manager.AwUrlConstant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhouyou.http.EasyHttp;

import org.json.JSONObject;

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
    protected SharedPreferencesUtil mPreferencesUtil;
    private Context mContext;

    public AwSsoAccountLoginMethod(Context context) {
        mContext = context;
        mDataSource = LoginDataSource.getInstance();
        mAuthBaseUrl = LoginManager.getInstance().getSettings().getServerUrl().concat(AwUrlConstant.AW_PORT_AUTHENTICATION);
        mOpusBaseUrl = LoginManager.getInstance().getSettings().getServerUrl().concat(AwUrlConstant.AW_PORT_OPUS_REST);
    }

    /**
     * 设置自定义的数据存取<br>
     * 默认使用{@link LoginDataSource}
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
        return EasyHttp.get(AwUrlConstant.AW_SSO_GET_AUTHENTICATION+mUserName+"/"+mPassword)
                .baseUrl(mAuthBaseUrl)
                .connectTimeout(mTimeOut)
                .retryCount(0)
                .execute(String.class)
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just("");
                })
                .map(result -> {
                    ApiResult<Authentication> apiResult = new ApiResult<>();
                    if (result != null && !result.isEmpty()) {
                        JSONObject jo = new JSONObject(result);
                        if (jo.has("success") && jo.getBoolean("success")) {
                            JSONObject contentJson = jo.getJSONObject("content");
                            JSONObject opusLoginUserJo = contentJson.getJSONObject("opusLoginUser");
                            String userJson = contentJson.getString("user");
                            String orgJson = contentJson.getString("org");
                            Gson gson = new Gson();
                            if(mPreferencesUtil == null){
                                mPreferencesUtil = new SharedPreferencesUtil(mContext);
                            }
                            mPreferencesUtil.setString("psxj",contentJson.toString());
                            Authentication authentication = gson.fromJson(contentJson.toString(), Authentication.class);
                            mUser = gson.fromJson(userJson,User.class);
                            mOrgs = gson.fromJson(orgJson,new TypeToken<List<Organization>>(){}.getType());
                            mUser.setOrgId(opusLoginUserJo.getString("currentOrgId"));
                            authentication.setAccessTime(System.currentTimeMillis());
                            apiResult.setSuccess(true);
                            apiResult.setData(authentication);
                        } else {
                            String message = jo.has("message") ? jo.getString("message") : null;
                            apiResult.setSuccess(false);
                            apiResult.setMessage(message);
                        }
                    } else {
                        apiResult.setSuccess(false);
                    }
                    return apiResult;
                })
                .subscribeOn(Schedulers.io());
    }

    /**
     * 生成登录授权接口所需header
     * @return
     */
    protected String generateAuthenticationHeader() {
        byte[] value = "opus-rest:opus-rest123".getBytes();
        return "Basic " + Base64.encodeToString(value, 0, value.length, Base64.DEFAULT).replace("\n", "");
    }

    /**
     * 获取用户信息
     * @param authentication 授权信息
     */
    protected Observable<ApiResult<User>> getUserInfo(Authentication authentication) {
        return Observable.fromCallable(() ->{
            ApiResult<User> apiResult = new ApiResult<>();
            if(mUser!=null){
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
