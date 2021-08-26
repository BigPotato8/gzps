package com.augurit.common.common.http;

import android.text.TextUtils;

import com.augurit.agmobile.common.lib.model.FileBean;
import com.google.gson.Gson;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.model.HttpParams;
import com.zhouyou.http.request.GetRequest;

import org.apache.commons.collections4.MapUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.HttpException;

/**
 * com.augurit.agmobile.agwater5.common.http
 * Created by sdb on 2019/3/25  17:19.
 * Desc：简单封装http请求
 */

public class HttpUtil {

    public static HttpUtil sHttpUtil;
    public String baseUrl;

    public static HttpUtil getInstance(String baseUrl) {
        if (sHttpUtil == null) {
            synchronized (HttpUtil.class) {
                if (sHttpUtil == null) {
                    sHttpUtil = new HttpUtil();
                }
            }
        }
        sHttpUtil.baseUrl = baseUrl;
        return sHttpUtil;
    }

    public Observable<String> get(String url, Map<String, String> map) {
        HttpParams httpParams = new HttpParams();
        if (!MapUtils.isEmpty(map)) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                httpParams.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
            }
        }

        return EasyHttp.get(url).baseUrl(baseUrl).params(httpParams).execute(String.class)
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just("");
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<String> get(String url) {
        return EasyHttp.get(url).baseUrl(baseUrl).execute(String.class)
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just("");
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<String> postJsonBody(String url, Map<String, String> map) {
        String json = "";
        if (!MapUtils.isEmpty(map)) {
            json = new Gson().toJson(map);
        }

        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), json);

        return EasyHttp.post(url).baseUrl(baseUrl).requestBody(requestBody).
                execute(String.class)
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just("");
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> post(String url, Map<String, String> map) {
        HttpParams httpParams = new HttpParams();
        if (!MapUtils.isEmpty(map)) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                httpParams.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
            }
        }

        return EasyHttp.post(url).baseUrl(baseUrl).params(httpParams).execute(String.class)
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just("");
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> postWithSameNameFile(String url, Map<String, String> map, Map<String, ArrayList<FileBean>> files) {
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        if (!MapUtils.isEmpty(files)) {
            for (Map.Entry<String, ? extends List<FileBean>> entry : files.entrySet()) {
                String prefix = entry.getKey();
                int i = 0;
                for (FileBean fileBean : entry.getValue()) {
                    if (!TextUtils.isEmpty(fileBean.getPath())) {
                        File file = new File(fileBean.getPath());
                        bodyBuilder.addFormDataPart("file", prefix.concat(file.getName()),
                                RequestBody.create(MediaType.parse("image/*"), file));
                        i++;
                    }
                }

            }
        }
        if (!MapUtils.isEmpty(map)) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                bodyBuilder.addFormDataPart(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
            }
        }

        return EasyHttp.post(url).baseUrl(baseUrl).requestBody(bodyBuilder.build()).execute(String.class)
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just("");
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> upJson(String url, String json) {


        return EasyHttp.post(url).baseUrl(baseUrl).upJson(json).
                execute(String.class)
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just("");
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<String> postWithFile(String url, Map<String, String> map, Map<String, ArrayList<FileBean>> files) {
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        if (!MapUtils.isEmpty(files)) {
            for (Map.Entry<String, ? extends List<FileBean>> entry : files.entrySet()) {
                String prefix = entry.getKey();
                int i = 0;
                for (FileBean fileBean : entry.getValue()) {
                    if (!TextUtils.isEmpty(fileBean.getPath())) {
                        File file = new File(fileBean.getPath());
                        bodyBuilder.addFormDataPart("file" + i, prefix.concat(file.getName()),
                                RequestBody.create(MediaType.parse("image/*"), file));
                        i++;
                    }
                }

            }
        }
        if (!MapUtils.isEmpty(map)) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                bodyBuilder.addFormDataPart(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
            }
        }

        return EasyHttp.post(url).baseUrl(baseUrl).requestBody(bodyBuilder.build()).execute(String.class)
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just("");
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<String> getWithHeader(String url, Map<String, String> map,Map<String, String> header) {
        HttpParams httpParams = new HttpParams();
        if (!MapUtils.isEmpty(map)) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                httpParams.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
            }
        }

        GetRequest getRequest = EasyHttp.get(url);
        String mapKey,mapValue = null;
        for(Map.Entry<String, String> entry : header.entrySet()){
            mapKey = entry.getKey();
            mapValue = entry.getValue();
            getRequest.headers(mapKey, mapValue);
        }

        return getRequest.baseUrl(baseUrl).params(httpParams)

                .execute(String.class)
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just("");
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
    public Observable<String> postJsonBodyByObject(String url, Map<String, Object> map) {
        String json = "";
        if (!MapUtils.isEmpty(map)) {
            json = new Gson().toJson(map);
        }

        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), json);

        return EasyHttp.post(url).baseUrl(baseUrl).requestBody(requestBody).
                execute(String.class)
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just("");
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
//    private boolean isJson(String json) {
//        if (TextUtils.isEmpty(json)) {
//            return false;
//        }
//        try {
//            new JsonParser().parse(json);
//            return true;
//        } catch (JsonSyntaxException e) {
//            return false;
//        } catch (JsonParseException e) {
//            return false;
//        }
//    }

}
