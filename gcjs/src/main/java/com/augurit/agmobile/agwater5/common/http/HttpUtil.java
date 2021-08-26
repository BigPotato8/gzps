package com.augurit.agmobile.agwater5.common.http;

import android.text.TextUtils;

import com.augurit.agmobile.common.lib.model.FileBean;
import com.google.gson.Gson;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBack;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.HttpHeaders;
import com.zhouyou.http.model.HttpParams;
import com.zhouyou.http.request.GetRequest;

import org.apache.commons.collections4.MapUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
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


    public Observable<String> get(String url) {
        return EasyHttp.get(url).baseUrl(baseUrl)
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

    public Observable<String> get(String url, Map<String, String> map) {
        HttpParams httpParams = new HttpParams();
        if (!MapUtils.isEmpty(map)) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                httpParams.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
            }
        }

        return EasyHttp.get(url).baseUrl(baseUrl).params(httpParams)
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


    public Observable<String> get(String url, Map<String, String> map, Map<String, String> headers) {
        HttpParams httpParams = new HttpParams();
        if (!MapUtils.isEmpty(map)) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                httpParams.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
            }
        }

        GetRequest getRequest = EasyHttp.get(url).baseUrl(baseUrl).params(httpParams);
        if (!MapUtils.isEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                getRequest.headers(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
            }
        }
        return getRequest
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

    public Observable<String> delete(String url, Map<String, String> map) {

        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                HttpParams httpParams = new HttpParams();
                if (!MapUtils.isEmpty(map)) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        httpParams.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
                    }
                }
                EasyHttp.delete(url).baseUrl(baseUrl).params(httpParams).execute(new CallBack<String>() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(ApiException e) {
                        emitter.onError(e);
                    }

                    @Override
                    public void onSuccess(String s) {
                        emitter.onNext(s);
                    }
                });
            }
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

    public Observable<String> postJsonBody1(String url, Map<String, String> map) {
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

    public Observable<String> postJsonBody(String url, Map<String, String> map) {
        HttpParams httpParams = new HttpParams();
        if (!MapUtils.isEmpty(map)) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                httpParams.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
            }
        }

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                bodyBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        String json = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/form-data; charset=utf-8"), json);
//                MediaType.parse("application/json; charset=utf-8"), json);
        bodyBuilder.addPart(requestBody);
        return EasyHttp.post(url).baseUrl(baseUrl).requestBody(bodyBuilder.build()).
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

    public Observable<String> putParmas(String url, Map<String,String> map){
        HttpParams httpParams = new HttpParams();
        if (!MapUtils.isEmpty(map)) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                httpParams.put(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
            }
        }

        return EasyHttp.put(url).baseUrl(baseUrl).params(httpParams).execute(String.class)
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



    public Observable<String> postWithFile(String url, Map<String, String> map, Map<String, FileBean> files) {
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        if (!MapUtils.isEmpty(files)) {
            for (Map.Entry<String, ? extends FileBean> entry : files.entrySet()) {
                String fileParamName = entry.getKey();
                FileBean fileBean = entry.getValue();
                if (!TextUtils.isEmpty(fileBean.getPath())) {
                    File file = new File(fileBean.getPath());
                    bodyBuilder.addFormDataPart(fileParamName, file.getName(),
                            RequestBody.create(MediaType.parse("multipart/form-data"), file));
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

    /**
     * 使用同一个参数字段上传文件集合
     * @param url
     * @param map
     * @param files
     * @return
     */
    public Observable<String> postWithFileBySameName(String url, Map<String, String> map, Map<String, List<FileBean>> files) {
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        if (!MapUtils.isEmpty(files)) {
            for (Map.Entry<String, ? extends List<FileBean>> entry : files.entrySet()) {
                String fileParamName = entry.getKey();
                List<FileBean> fileBeans = entry.getValue();
                for (FileBean fileBean : fileBeans) {
                    if (!TextUtils.isEmpty(fileBean.getPath())) {
                        File file = new File(fileBean.getPath());
                        bodyBuilder.addFormDataPart(fileParamName, file.getName(),
                                RequestBody.create(MediaType.parse("multipart/form-data"), file));
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

    public void downFile(String downloadPath, String savePath, String saveName, DownloadListener listener) {
        EasyHttp.downLoad(downloadPath).savePath(savePath).saveName(saveName).execute(new DownloadProgressCallBack<File>() {
            public void onStart() {
            }

            public void onError(ApiException e) {
                if (listener != null) {
                    listener.onError();
                }
            }

            public void update(long bytesRead, long contentLength, boolean done) {
            }

            public void onComplete(String path) {
                if (listener != null) {
                    listener.onComplete();
                }
            }
        });
    }


    public interface DownloadListener {
        void onComplete();

        void update(long bytesRead, long contentLength, boolean done);

        void onError();
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
