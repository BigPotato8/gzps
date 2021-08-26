package com.augurit.common.login;

import android.util.Base64;

import com.augurit.agmobile.busi.common.common.constant.UrlConstant;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.method.ILoginMethod;
import com.augurit.agmobile.busi.common.login.method.SsoAccountLoginMethod;
import com.augurit.agmobile.busi.common.login.model.Authentication;
import com.augurit.agmobile.busi.common.login.model.Role;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.busi.common.login.source.LoginDataSource;
import com.augurit.agmobile.busi.common.organization.model.Organization;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.common.common.manager.AwUrlConstant;
import com.augurit.common.common.manager.AwUrlManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhouyou.http.EasyHttp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmList;
import retrofit2.HttpException;

public class NewAwSsoAccountLoginMethod extends SsoAccountLoginMethod {
    private String mOrgId = "406";

    public NewAwSsoAccountLoginMethod() {
        mDataSource = LoginDataSource.getInstance();
        mAuthBaseUrl = LoginManager.getInstance().getSettings().getServerUrl().concat(AwUrlConstant.PORT_AUTHENTICATION);
        mOpusBaseUrl = LoginManager.getInstance().getSettings().getServerUrl().concat(AwUrlConstant.PORT_OPUS_REST);
    }

    @Override
    protected String generateAuthenticationHeader() {
        byte[] value = "opus-sso-client2:opus-sso-client2123".getBytes();
        return "Basic " + Base64.encodeToString(value, 0, value.length, Base64.DEFAULT).replace("\n", "");
    }

    @Override
    public ILoginMethod setBaseUrl(String baseUrl) {
        mAuthBaseUrl = baseUrl.concat(AwUrlConstant.PORT_AUTHENTICATION);
        mOpusBaseUrl = baseUrl.concat(AwUrlConstant.PORT_OPUS_REST);
        return this;
    }

    public void setOrgId(String orgid) {
        mOrgId = orgid;
    }

    /**
     * SSO 登陆 获取授权信息
     */
    @Override
    protected Observable<ApiResult<Authentication>> authenticate() {
        return EasyHttp.post(UrlConstant.SSO_POST_AUTHENTICATION)
                .baseUrl(mAuthBaseUrl)
                .headers("Content-Type", "application/x-www-form-urlencoded")
                .headers("Authorization", generateAuthenticationHeader())
                .params("username", mUserName)
                .params("password", mPassword)
                .params("orgId", mOrgId)
                .params("deviceType", "normal")
                .params("isApp", "true")
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
                        if (jo.getBoolean("success")) {
                            String content = jo.getString("content");
                            JSONObject contentJson = jo.getJSONObject("content");
                            JSONObject opusLoginUserJo = contentJson.getJSONObject("opusLoginUser");
                            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                            Authentication authentication = gson.fromJson(content, Authentication.class);
                            authentication.setAccessTime(System.currentTimeMillis());
                            mOrgId = opusLoginUserJo.getString("currentOrgId");
                            apiResult.setSuccess(true);
                            apiResult.setData(authentication);
                        } else {
                            apiResult.setSuccess(false);
                            apiResult.setMessage(jo.getString("message"));
                        }
                    } else {
                        apiResult.setSuccess(false);
                    }
                    return apiResult;
                })
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取用户信息
     *
     * @param authentication 授权信息
     */
    @Override
    protected Observable<ApiResult<User>> getUserInfo(Authentication authentication) {
        return EasyHttp.get(AwUrlConstant.SSO_GET_USER_INFO)
                .baseUrl(mOpusBaseUrl)
                .headers("Authorization", authentication.generateHeader())
                .params("userName", mUserName)
                .connectTimeout(mTimeOut)
                .retryCount(0)
                .execute(String.class)
                .map(result -> {
                    ApiResult<User> apiResult = new ApiResult<>();
                    User user = new Gson().fromJson(result, User.class);
                    user.setLoginPwd(mPassword);    // TODO 加密
                    user.setAuthentication(authentication);
                    user.setOrgId(mOrgId);

                    apiResult.setSuccess(true);
                    apiResult.setData(user);
                    return apiResult;

//                    return getUserRole(user);
                })
                .subscribeOn(Schedulers.io());
    }

    /**
     * 在线获取用户所属组织列表
     *
     * @return 包含组织列表的Observable
     */
    @Override
    public Observable<ApiResult<List<Organization>>> getOrganizations() {
        return EasyHttp.get(AwUrlConstant.SSO_GET_USER_ORG)
                .baseUrl(mOpusBaseUrl)
                .params("username", mUserName)
                .params("password", mPassword)
                .connectTimeout(mTimeOut)
                .retryCount(0)
                .execute(String.class)
                .map(result -> {
                    ApiResult<List<Organization>> apiResult = new ApiResult<>();
                    JSONObject jo = new JSONObject(result);
                    boolean success = jo.getBoolean("success");
                    String message = jo.getString("message");
                    if (success) {
                        String content = jo.getString("content");
                        List<Organization> orgs = new Gson().fromJson(content,
                                new TypeToken<List<Organization>>() {
                                }.getType());
                        apiResult.setSuccess(true);
                        apiResult.setData(orgs);
                    } else {
                        apiResult.setSuccess(false);
                        apiResult.setMessage(message);
                    }
                    return apiResult;
                }).subscribeOn(Schedulers.io());
    }

    public Observable<ApiResult<User>> getUserRole(User user) {
        AwUrlManager.setServerUrl("");
        return EasyHttp.get(AwUrlConstant.GET_USER_ROLES)
                .baseUrl(AwUrlManager.serverUrl())
                .headers("Authorization", user.getAuthentication().generateHeader())
                .connectTimeout(mTimeOut)
                .retryCount(0)
                .execute(String.class)
                .map(result -> {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray roleList = jsonObject.getJSONArray("roleList");
                    RealmList<Role> realmList = new RealmList<>();
                    for (int i = 0; i < roleList.length(); i++) {

                        JSONObject jso = roleList.getJSONObject(i);
                        Role role = new Role();
                        role.setOrgRoleCode(jso.getString("roleCode"));
                        role.setOrgRoleId(jso.getString("roleId"));
                        role.setOrgRoleName(jso.getString("roleName"));
                        realmList.add(role);

                    }

                    user.setRole(realmList);
                    ApiResult<User> userApiResult = new ApiResult<>();
                    userApiResult.setSuccess(true);
                    userApiResult.setData(user);

                    return userApiResult;


                }).subscribeOn(Schedulers.io());


    }


    /**
     * 登录前获取用户所属组织列表
     *
     * @return 包含组织列表的Observable
     */
    public Observable<ApiResult<List<Organization>>> getOrganizationsBefore() {
//        return EasyHttp.get(AwUrlConstant.SSO_GET_USER_ORG)
//                .baseUrl(mOpusBaseUrl)
        return EasyHttp.post(AwUrlConstant.SSO_GET_USER_ORG_BEFORE)
                .baseUrl(mAuthBaseUrl)
                .params("username", mUserName)
                .params("password", mPassword)
                .connectTimeout(mTimeOut)
                .retryCount(0)
                .execute(String.class)
                .map(result -> {
                    ApiResult<List<Organization>> apiResult = new ApiResult<>();
                    JSONObject jo = new JSONObject(result);
                    boolean success = jo.getBoolean("success");
                    String message = jo.getString("message");
                    if (success) {
                        String content = jo.getString("content");
                        List<Organization> orgs = new Gson().fromJson(content,
                                new TypeToken<List<Organization>>() {
                                }.getType());
                        apiResult.setSuccess(true);
                        apiResult.setData(orgs);
                    } else {
                        apiResult.setSuccess(false);
                        apiResult.setMessage(message);
                    }
                    return apiResult;
                }).subscribeOn(Schedulers.io());
    }
}
