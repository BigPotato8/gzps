package com.augurit.agmobile.agwater5.login.method;

import android.text.TextUtils;
import android.util.Base64;

import com.augurit.agmobile.agwater5.common.common.AwUrlConstant;
import com.augurit.agmobile.agwater5.common.utils.MD5Utils;
import com.augurit.agmobile.agwater5.common.utils.StringUtils;
import com.augurit.agmobile.agwater5.im.IMConstant;
import com.augurit.agmobile.busi.common.common.constant.UrlConstant;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.method.ILoginMethod;
import com.augurit.agmobile.busi.common.login.model.Authentication;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.busi.common.login.source.ILoginDataSource;
import com.augurit.agmobile.busi.common.login.source.LoginDataSource;
import com.augurit.agmobile.busi.common.organization.model.Organization;
import com.augurit.agmobile.common.lib.log.LogUtil;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.common.common.util.SM3;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhouyou.http.EasyHttp;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmList;
import okhttp3.FormBody;
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
    protected String mPhoneNum;
    protected String mVerCode;
    protected String mAccount;
    protected String mCodeSession;
    protected ILoginDataSource mDataSource;
    public String mAuthBaseUrl;
    protected String mOpusBaseUrl;
    protected int mTimeOut = 15000;

    protected User mUser;
    protected List<Organization> mOrgs;
    protected String mOrgId = "0368948a-1cdf-4bf8-a828-71d796ba89f6";
    private String mCode; //数字验证码
    private String mSession; //cookie
    private String mBase64Code; //base64验证码
    private int status;

    public AwSsoAccountLoginMethod(int status) {
        this.status=status;//0是用户名登录，1是手机验证码登录
        mDataSource = LoginDataSource.getInstance();
//        mAuthBaseUrl = LoginConstant.LOGIN_SERVER_URL;
//        mAuthBaseUrl = LoginManager.getInstance().getSettings().getServerUrl().concat(AwUrlConstant.AW_PORT_AUTHENTICATION);
        mAuthBaseUrl = LoginManager.getInstance().getSettings().getServerUrl().concat(AwUrlConstant.AW_PORT_AUTHENTICATION);
//        mOpusBaseUrl = LoginManager.getInstance().getSettings().getServerUrl().concat(AwUrlConstant.AW_PORT_CP_REST);
        mOpusBaseUrl = LoginManager.getInstance().getSettings().getServerUrl().concat(AwUrlConstant.AW_PORT_CP_REST_TEST);//测试
//        mOpusBaseUrl = LoginManager.getInstance().getSettings().getServerUrl().concat(AwUrlConstant.AW_PORT_AUTHENTICATION); //正式
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

    public ILoginMethod setParams(String userName, String password, String code, String session, String base64Code) {
        mUserName = userName;
        mPassword = password;
        mCode = code;
        mSession = session;
        mBase64Code = base64Code;
        return this;
    }

    /**
     *
     * @param phoneNum 手机号
     * @param verCode 短信验证码
     * @return
     */
    public ILoginMethod setParam(String account,String phoneNum, String verCode,String verCodeSession) {
        mPhoneNum = phoneNum;
        mVerCode = verCode;
        mUserName=account;
        mSession=verCodeSession;
        return this;
    }

    /**
     * 设置需要登录到的组织id
     *
     * @param org
     */
    public void setLoginOrg(String org) {
        mOrgId = org;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getPassword() {
        return mPassword;
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
        FormBody.Builder builder = new FormBody.Builder();
        String url;
        if (status==0){//0是用户名登录，1是手机验证码登录
            //正式
            String nameBase64 = Base64.encodeToString(mUserName.getBytes(), 0, mUserName.getBytes().length, Base64.DEFAULT);
            String mOrgIdBase64 = Base64.encodeToString(mOrgId.getBytes(), 0, mOrgId.getBytes().length, Base64.DEFAULT);
            builder.add("username", nameBase64);
            String sm3md5Pw = SM3.encrypt(new MD5Utils().getMD5ofStr(mPassword));
            builder.add("password", sm3md5Pw);
            builder.add("orgId", mOrgIdBase64);
            builder.add("imageCode", mCode);

            LogUtil.e("username",nameBase64);
            LogUtil.e("password", sm3md5Pw);
            LogUtil.e("orgId",mOrgId);
            LogUtil.e("orgId base64",mOrgIdBase64);

            builder.add("deviceType", "normal");
            builder.add("isApp", "true");
            url=AwUrlConstant.AW_SSO_GET_AUTHENTICATION;
        }else{
            url=AwUrlConstant.VERCODE_AW_SSO_GET_AUTHENTICATION;
            String nameBase64 = Base64.encodeToString(mUserName.getBytes(), 0, mUserName.getBytes().length, Base64.DEFAULT);
            String mOrgIdBase64 = Base64.encodeToString(mOrgId.getBytes(), 0, mOrgId.getBytes().length, Base64.DEFAULT);
            builder.add("username", nameBase64);
            String sm3md5Pw = SM3.encrypt(new MD5Utils().getMD5ofStr(mPassword));
            builder.add("mobile", mPhoneNum);
            builder.add("smsCode",mVerCode);
            builder.add("orgId", mOrgIdBase64);

            builder.add("deviceType", "normal");
            builder.add("isApp", "true");
        }
        FormBody build = builder.build();
        return EasyHttp.post(url)
//                .params("username",mUserName)
//                .params("password",mPassword)
//                .params("orgId","B")
//                .params("deviceType","normal")
//                .params("isApp","true")
                .requestBody(build)
                .headers("Authorization", "Basic b3B1cy1yZXN0Om9wdXMtcmVzdDEyMw==")
                .headers("Content-Type", "application/x-www-form-urlencoded")
                .headers("Cookie",mSession)
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
//                    return Observable.just(TOKEN);
//                    return Observable.just(TOKEN2);
                })
                .map(result -> {
                    ApiResult<Authentication> apiResult = new ApiResult<>();
                    if (StringUtils.isJson(result)) {
                        JSONObject jo = new JSONObject(result);
                        if (jo.has("success") && jo.getBoolean("success")) {
                            JSONObject contentJson = jo.getJSONObject("content");
                            JSONObject opusLoginUserJo = contentJson.getJSONObject("opusLoginUser");
                            JSONObject userJson = opusLoginUserJo.getJSONObject("user");
                            String orgJson = opusLoginUserJo.getString("orgs");
                            Gson gson = new Gson();
                            Authentication authentication = gson.fromJson(contentJson.toString(), Authentication.class);
                            mUser = gson.fromJson(userJson.toString(), User.class);
                            mOrgs = gson.fromJson(orgJson, new TypeToken<List<Organization>>() {
                            }.getType());
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
                        apiResult.setMessage(result);
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

//    /**
//     * 获取用户信息
//     *
//     * @param authentication 授权信息
//     */
//    protected Observable<ApiResult<User>> getUserInfo(Authentication authentication) {
//        return Observable.fromCallable(() -> {
//            ApiResult<User> apiResult = new ApiResult<>();
//            if (mUser != null) {
//                mUser.setLoginPwd(mPassword);
//                mUser.setAuthentication(authentication);
//                apiResult.setSuccess(true);
//                apiResult.setData(mUser);
//            }
//            return apiResult;
//        }).subscribeOn(Schedulers.io());
//    }

    /**
     * 获取用户信息
     *
     * @param authentication 授权信息
     */
    protected Observable<ApiResult<User>> getUserInfo(Authentication authentication) {
        if (IMConstant.IS_TEST) {
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
        return EasyHttp.get(AwUrlConstant.AW_GET_USER_INFO)
                .baseUrl(mOpusBaseUrl)
                .headers("Authorization", authentication.generateHeader())
                .params("loginName", mUserName)
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
                    ApiResult<User> apiResult = new ApiResult<>();
                    if (StringUtils.isJson(result)) {
                        User user = new Gson().fromJson(result, User.class);
                        user.setLoginPwd(new MD5Utils().getMD5ofStr(mPassword));
                        user.setAuthentication(authentication);
                        apiResult.setSuccess(true);
                        apiResult.setData(user);
                    } else {
                        apiResult.setSuccess(false);
                        apiResult.setMessage(result);
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
        String mValue;
        String mKey;
        if(mPassword == null && mPhoneNum!=null){
//            if (mVerCode==null){
//                mKey="mobile";
//                mValue=mPhoneNum;
//            }else{
//                mKey="smsCode";
//                mValue=mVerCode;
//            }
            mKey="mobile";
            mValue=mPhoneNum;
        }else {
            mKey="password";
            mValue =new MD5Utils().getMD5ofStr(mPassword); ;
        }
        return EasyHttp.get(AwUrlConstant.AW_GET_USER_ORGANIZATIONS)
                .baseUrl(mOpusBaseUrl)
                .params("username", mUserName)
                .params(mKey,mValue)
//                .headers("Authorization","Basic b3B1cy1yZXN0Om9wdXMtcmVzdDEyMw==")
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
                    ApiResult<List<Organization>> apiResult = new ApiResult<>();
                    try {
                        List<Organization> orgs = new Gson().fromJson(result,
                                new TypeToken<List<Organization>>() {
                                }.getType());
                        apiResult.setSuccess(true);
                        apiResult.setData(orgs);
                    }catch (Exception e){
                        apiResult.setSuccess(false);
                        apiResult.setMessage(result);
                    }
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

    private static String TOKEN = "{\n" +
            "    \"success\": true,\n" +
            "    \"message\": \"登录成功\",\n" +
            "    \"content\": {\n" +
            "        \"access_token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcHVzTG9naW5Vc2VyIjp7InVzZXIiOnsidXNlcklkIjoiM2E3ZDZkNGQtOGI3My00OTZjLTg1ZTAtMzkwYmUxZGEzNWY0IiwidXNlck5hbWUiOiLnqpflj6PkurrlkZgiLCJsb2dpbk5hbWUiOiJja3J5IiwibG9naW5Qd2QiOm51bGwsImlzTG9jayI6bnVsbCwibG9naW5GYWlsTnVtIjpudWxsLCJsb2NrVGltZSI6bnVsbH0sIm9yZ3MiOm51bGwsImN1cnJlbnRPcmdJZCI6IjAzNjg5NDhhLTFjZGYtNGJmOC1hODI4LTcxZDc5NmJhODlmNiIsImN1cnJlbnRPcmdDb2RlIjoiUjAyOSIsImN1cnJlbnRUbW4iOiJub3JtYWwiLCJhcHBTb2Z0Q29udGV4dHMiOltdLCJ0bW5NZW51Q29udGV4dHMiOltdfSwidXNlcl9uYW1lIjoiY2tyeSIsInNjb3BlIjpbImFsbCJdLCJleHAiOjE2MTUwNTQ1ODQsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI2MjVlZGU4MS1jMGRiLTQ0MzQtOThhYS1iODFjN2JiYjgyNzciLCJjbGllbnRfaWQiOiJvcHVzLXJlc3QifQ.9E6F_TibfPLppr2U6r_nABYFcNX2uzDBEsXogKh09rg\",\n" +
            "        \"token_type\": \"bearer\",\n" +
            "        \"refresh_token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcHVzTG9naW5Vc2VyIjp7InVzZXIiOnsidXNlcklkIjoiM2E3ZDZkNGQtOGI3My00OTZjLTg1ZTAtMzkwYmUxZGEzNWY0IiwidXNlck5hbWUiOiLnqpflj6PkurrlkZgiLCJsb2dpbk5hbWUiOiJja3J5IiwibG9naW5Qd2QiOm51bGwsImlzTG9jayI6bnVsbCwibG9naW5GYWlsTnVtIjpudWxsLCJsb2NrVGltZSI6bnVsbH0sIm9yZ3MiOm51bGwsImN1cnJlbnRPcmdJZCI6IjAzNjg5NDhhLTFjZGYtNGJmOC1hODI4LTcxZDc5NmJhODlmNiIsImN1cnJlbnRPcmdDb2RlIjoiUjAyOSIsImN1cnJlbnRUbW4iOiJub3JtYWwiLCJhcHBTb2Z0Q29udGV4dHMiOltdLCJ0bW5NZW51Q29udGV4dHMiOltdfSwidXNlcl9uYW1lIjoiY2tyeSIsInNjb3BlIjpbImFsbCJdLCJhdGkiOiI2MjVlZGU4MS1jMGRiLTQ0MzQtOThhYS1iODFjN2JiYjgyNzciLCJleHAiOjE2MTU1Mjk3ODQsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiI2YmM4MDU1Ny1iYjQ0LTRlOTItODY5OC0xMmJhNzhkYjkwZTYiLCJjbGllbnRfaWQiOiJvcHVzLXJlc3QifQ.bMs7wObk1NzWsPYPi9_tMucDbfoNPznjdUCjW7tceao\",\n" +
            "        \"expires_in\": 129599,\n" +
            "        \"scope\": \"all\",\n" +
            "        \"opusLoginUser\": {\n" +
            "            \"user\": {\n" +
            "                \"userId\": \"3a7d6d4d-8b73-496c-85e0-390be1da35f4\",\n" +
            "                \"userName\": \"窗口人员\",\n" +
            "                \"loginName\": \"ckry\",\n" +
            "                \"loginPwd\": null,\n" +
            "                \"isLock\": null,\n" +
            "                \"loginFailNum\": null,\n" +
            "                \"lockTime\": null\n" +
            "            },\n" +
            "            \"orgs\": null,\n" +
            "            \"currentOrgId\": \"0368948a-1cdf-4bf8-a828-71d796ba89f6\",\n" +
            "            \"currentOrgCode\": \"R029\",\n" +
            "            \"currentTmn\": \"normal\",\n" +
            "            \"appSoftContexts\": [],\n" +
            "            \"tmnMenuContexts\": []\n" +
            "        },\n" +
            "        \"jti\": \"625ede81-c0db-4434-98aa-b81c7bbb8277\"\n" +
            "    }\n" +
            "}" ;

    private static String TOKEN2 = "{\n" +
            "    \"success\": true,\n" +
            "    \"message\": \"登录成功\",\n" +
            "    \"content\": {\n" +
            "        \"access_token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcHVzTG9naW5Vc2VyIjp7InVzZXIiOnsidXNlcklkIjoiYzkzZDlkYzctOWU0Ni00ZjlkLWI4NjktODcwNTY5ZDNiY2Y1IiwidXNlck5hbWUiOiLlkLTmtbflro8iLCJsb2dpbk5hbWUiOiJ3dWhhaWhvbmciLCJsb2dpblB3ZCI6bnVsbCwiaXNMb2NrIjpudWxsLCJsb2dpbkZhaWxOdW0iOm51bGwsImxvY2tUaW1lIjpudWxsfSwib3JncyI6bnVsbCwiY3VycmVudE9yZ0lkIjoiMDM2ODk0OGEtMWNkZi00YmY4LWE4MjgtNzFkNzk2YmE4OWY2IiwiY3VycmVudE9yZ0NvZGUiOiJSMDI5IiwiY3VycmVudFRtbiI6Im5vcm1hbCIsImFwcFNvZnRDb250ZXh0cyI6W10sInRtbk1lbnVDb250ZXh0cyI6W119LCJ1c2VyX25hbWUiOiJ3dWhhaWhvbmciLCJzY29wZSI6WyJhbGwiXSwiZXhwIjoxNjE1MDM3MDc4LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiYWIwMGEwODMtZmUxZC00YWY1LTk2NmYtOWQ1YTk4OWM2MDJkIiwiY2xpZW50X2lkIjoib3B1cy1yZXN0In0.fMVe8OxTjtVq0JdeKC8EAyyXmVamQXoegSgrzSXEKJc\",\n" +
            "        \"token_type\": \"bearer\",\n" +
            "        \"refresh_token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcHVzTG9naW5Vc2VyIjp7InVzZXIiOnsidXNlcklkIjoiYzkzZDlkYzctOWU0Ni00ZjlkLWI4NjktODcwNTY5ZDNiY2Y1IiwidXNlck5hbWUiOiLlkLTmtbflro8iLCJsb2dpbk5hbWUiOiJ3dWhhaWhvbmciLCJsb2dpblB3ZCI6bnVsbCwiaXNMb2NrIjpudWxsLCJsb2dpbkZhaWxOdW0iOm51bGwsImxvY2tUaW1lIjpudWxsfSwib3JncyI6bnVsbCwiY3VycmVudE9yZ0lkIjoiMDM2ODk0OGEtMWNkZi00YmY4LWE4MjgtNzFkNzk2YmE4OWY2IiwiY3VycmVudE9yZ0NvZGUiOiJSMDI5IiwiY3VycmVudFRtbiI6Im5vcm1hbCIsImFwcFNvZnRDb250ZXh0cyI6W10sInRtbk1lbnVDb250ZXh0cyI6W119LCJ1c2VyX25hbWUiOiJ3dWhhaWhvbmciLCJzY29wZSI6WyJhbGwiXSwiYXRpIjoiYWIwMGEwODMtZmUxZC00YWY1LTk2NmYtOWQ1YTk4OWM2MDJkIiwiZXhwIjoxNjE1NTEyMjc4LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiZDUyMzBhOTQtODlhYy00M2NkLWFiNDgtOTg4NDU4YmFlMGE1IiwiY2xpZW50X2lkIjoib3B1cy1yZXN0In0.CC2K1zZlsIK_Vt4X7SSzpNBR8GjZW3dujbgeULaOLbI\",\n" +
            "        \"expires_in\": 129599,\n" +
            "        \"scope\": \"all\",\n" +
            "        \"opusLoginUser\": {\n" +
            "            \"user\": {\n" +
            "                \"userId\": \"c93d9dc7-9e46-4f9d-b869-870569d3bcf5\",\n" +
            "                \"userName\": \"吴海宏\",\n" +
            "                \"loginName\": \"wuhaihong\",\n" +
            "                \"loginPwd\": null,\n" +
            "                \"isLock\": null,\n" +
            "                \"loginFailNum\": null,\n" +
            "                \"lockTime\": null\n" +
            "            },\n" +
            "            \"orgs\": null,\n" +
            "            \"currentOrgId\": \"0368948a-1cdf-4bf8-a828-71d796ba89f6\",\n" +
            "            \"currentOrgCode\": \"R029\",\n" +
            "            \"currentTmn\": \"normal\",\n" +
            "            \"appSoftContexts\": [],\n" +
            "            \"tmnMenuContexts\": []\n" +
            "        },\n" +
            "        \"jti\": \"ab00a083-fe1d-4af5-966f-9d5a989c602d\"\n" +
            "    }\n" +
            "}";
}
