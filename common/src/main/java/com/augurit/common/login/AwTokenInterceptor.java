package com.augurit.common.login;

import android.content.Context;
import android.util.Log;

import com.augurit.common.login.AwSsoAccountLoginMethod;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.Authentication;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.busi.common.login.util.ITokenInterceptor;
import com.augurit.common.common.manager.AwUrlConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * @AUTHOR 创建人:yaowang
 * @VERSION 版本:1.0
 * @PACKAGE 包名:com.augurit.agmobile.agwater5.login.util
 * @CREATETIME 创建时间:2018/8/29
 * @MODIFYTIME 修改时间:
 * @MODIFYBY 修改人:
 * @MODIFYMEMO 修改备注:
 */
public class AwTokenInterceptor extends AwSsoAccountLoginMethod implements ITokenInterceptor {
    public AwTokenInterceptor(Context context) {
        super(context);
    }

    protected int mTimeOut = 15000;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (response.code() == 401) {
            ResponseBody body = response.peekBody(Long.MAX_VALUE);
            try {
                String string = body.string();
                JSONObject jo = new JSONObject(string);
                String error = jo.getString("error");
                if (error.equals("invalid_token")) {
                    // 刷新token
                    String token = refreshToken();
                    if (token != null) {
                        // 使用新token继续之前的请求
                        Request newRequest = request.newBuilder().header("Authorization", token).build();
                        response.close();
                        return chain.proceed(newRequest);
                    }
                }
            } catch (Exception e) {
                Log.d("AwTokenInterceptor","intercept:"+e.toString());
            }
            relogin();
        }
        return response;
    }

    @Override
    public String refreshToken() {
        User currentUser = LoginManager.getInstance().getCurrentUser();
        if (currentUser == null) return null;

        // TODO 目前暂无refresh接口，此处暂时再次请求登录接口
        String url = mAuthBaseUrl.concat(AwUrlConstant.AW_SSO_GET_AUTHENTICATION+mUserName+"/"+mPassword);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(mTimeOut, TimeUnit.MILLISECONDS)
                .build();
//        FormBody formBody = new FormBody.Builder()
//                .add("username", currentUser.getLoginName())
//                .add("password", currentUser.getLoginPwd())
//                .add("deviceType", "normal")
//                .add("isApp", "true")
//                .build();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", generateAuthenticationHeader())
                .get()
                .build();
        try {
            Response execute = client.newCall(request).execute();
            if (execute.isSuccessful()) {
                String result = execute.body().string();
                JSONObject jo = new JSONObject(result);
                if (jo.getBoolean("success")) {
                    String contentJson = jo.getString("content");
                    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                    Authentication authentication = gson.fromJson(contentJson, Authentication.class);
                    authentication.setAccessTime(System.currentTimeMillis());
                    LoginManager.getInstance().refreshAuthentication(authentication);
                    return authentication.generateHeader();
                }
            }
        } catch (Exception e) {
            Log.d("AwTokenInterceptor","refreshToken:"+e.toString());
        }
        return null;
    }

    @Override
    public void relogin() {
        LoginManager.getInstance().notifyRelogin();
    }
}
