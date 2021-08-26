package com.augurit.agmobile.agwater5.gcjs_public.login.util;

import android.text.TextUtils;
import android.util.Log;

import com.augurit.agmobile.agwater5.gcjs_public.common.GcjsPuUrlConstant;
import com.augurit.agmobile.agwater5.gcjs_public.login.method.AwSsoAccountLoginMethod;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.busi.common.login.util.ITokenInterceptor;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.common.common.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhouyou.http.EasyHttp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.HttpException;


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

    protected int mTimeOut = 15000;

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
//        if (response.code() == 401||response.body().string().contains("请重新登录")) {
        ResponseBody body = response.peekBody(Long.MAX_VALUE);
        if (body.string().contains("登录") || body.string().contains("过期") || body.string().contains("重新")) {
            try {
//                // 刷新token
//                String token = refreshToken();
//                if (token != null) {
//                    // 使用新token继续之前的请求
//                    Request newRequest = proceedRequest(request, token);
//                    response.close();
//                    return chain.proceed(newRequest);
//                }

                // 刷新token
                String token = refreshToken();
                if (token != null) {
                    // 使用新token继续之前的请求
                    Request newRequest = request.newBuilder().header("Authorization", token).build();
                    response.close();
                    return chain.proceed(newRequest);
                }
            } catch (Exception e) {
                Log.d("AwTokenInterceptor", "intercept:" + e.toString());
            }
            relogin();
//        }
        }
        return response;
    }

    private Request proceedRequest(Request request, String token) {
        String method = request.method();
        //重新设置token
        if ("GET".equals(method)) {
            //如果是get请求
            HttpUrl url = request.url();
            Set<String> params = url.queryParameterNames();
            HashMap<String, String> newParams = new HashMap<>();
            for (String key : params) {
                String value = url.queryParameter(key);
                if ("token".equals(key)) {
                    newParams.put(key, token);
                } else {
                    newParams.put(key, value);
                }
            }


            //重新拼接url
            HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
            for (String key : newParams.keySet()) {
                httpUrlBuilder.addQueryParameter(key, newParams.get(key));
            }

            Request.Builder requestBuilder = request.newBuilder();
            requestBuilder.url(httpUrlBuilder.build());
            return requestBuilder.build();


        } else if ("post".equals(method)) {
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                if (requestBody instanceof FormBody) {
                    FormBody.Builder formBody = new FormBody.Builder();
                    for (int i = 0; i < ((FormBody) requestBody).size(); i++) {
                        if ("token".equals(((FormBody) requestBody).encodedName(i))) {
                            formBody.add(((FormBody) requestBody).encodedName(i), token);
                        } else {
                            formBody.add(((FormBody) requestBody).encodedName(i), ((FormBody) requestBody).encodedValue(i));
                        }
                    }
                    return request.newBuilder().post(formBody.build()).build();

                } else if (requestBody instanceof MultipartBody) {
                    MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
                    for (int i = 0; i < ((MultipartBody) requestBody).size(); i++) {
                        List<MultipartBody.Part> parts = ((MultipartBody) requestBody).parts();
                        for (MultipartBody.Part part : parts) {
                            bodyBuilder.addPart(part);
                        }
                    }
                    //TODO 不知道重新设置行不行 --!
                    bodyBuilder.addFormDataPart("token", token);
                    return request.newBuilder().post(bodyBuilder.build()).build();
                } else {
                    //buffer流
                    Buffer buffer = new Buffer();
                    try {
                        requestBody.writeTo(buffer);
                        return request.newBuilder().post(requestBody).build();

                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            } else {
                //如果没body参数的post请求
                HttpUrl url = request.url();
                Set<String> params = url.queryParameterNames();
                HashMap<String, String> newParams = new HashMap<>();
                for (String key : params) {
                    String value = url.queryParameter(key);
                    if ("token".equals(key)) {
                        newParams.put(key, token);
                    } else {
                        newParams.put(key, value);
                    }
                }


                //重新拼接url
                HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
                for (String key : newParams.keySet()) {
                    httpUrlBuilder.addQueryParameter(key, newParams.get(key));
                }

                Request.Builder requestBuilder = request.newBuilder();
                requestBuilder.url(httpUrlBuilder.build());
                return requestBuilder.build();
            }

        }

        return null;
    }

    @Override
    public String refreshToken() {
        User currentUser = LoginManager.getInstance().getCurrentUser();
        if (currentUser == null) return null;

//        // TODO 目前暂无refresh接口，此处暂时再次请求登录接口
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(mTimeOut, TimeUnit.MILLISECONDS)
//                .build();
//        FormBody formBody = new FormBody.Builder()
//                .add("username", currentUser.getLoginName())
//                .add("password", currentUser.getLoginPwd())
//                .add("deviceType", "normal")
//                .add("orgId", "0368948a-1cdf-4bf8-a828-71d796ba89f6")
//                .add("isApp", "true")
//                .build();
//        Request request = new Request.Builder()
//                .url(mAuthBaseUrl + AwUrlConstant.AW_SSO_GET_AUTHENTICATION)
//                .header("Authorization", generateAuthenticationHeader())
//                .post(formBody)
//                .build();
//        try {
//            Response execute = client.newCall(request).execute();
//            if (execute.isSuccessful()) {
//                String result = execute.body().string();
//                JSONObject jo = new JSONObject(result);
//                if (jo.getBoolean("success")) {
//                    String contentJson = jo.getString("content");
//                    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//                    Authentication authentication = gson.fromJson(contentJson, Authentication.class);
//                    authentication.setAccessTime(System.currentTimeMillis());
//                    LoginManager.getInstance().refreshAuthentication(authentication);
//                    return authentication.generateHeader();
//                }
//            }
//        } catch (Exception e) {
//            Log.d("AwTokenInterceptor", "refreshToken:" + e.toString());
//        }

        EasyHttp.get(GcjsPuUrlConstant.AW_SSO_GET_AUTHENTICATION_PUBLIC)
                .params("userName", currentUser.getLoginName())
                .params("password", currentUser.getLoginPwd())
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
                    if (StringUtils.isJson(result)) {
                        ApiResult<String> dataResult = new Gson().fromJson(result, new TypeToken<ApiResult<String>>() {
                        }.getType());
                        if (dataResult.isSuccess()) {
                            currentUser.setUserType(dataResult.getData());
                            LoginManager.getInstance().saveUser(currentUser);
                            return dataResult.getData();
                        } else {
                            return null;
                        }
                    } else {
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io());
        return null;
    }


    @Override
    public void relogin() {
        LoginManager.getInstance().notifyRelogin();
    }
}
