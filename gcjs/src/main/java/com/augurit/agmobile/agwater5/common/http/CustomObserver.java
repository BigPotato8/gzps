package com.augurit.agmobile.agwater5.common.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * com.augurit.agmobile.agwater5.common.http
 * Created by sdb on 2019/3/25  17:19.
 * Desc：简单封装Observer
 */

public abstract class CustomObserver<T> implements Observer<String> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(String json) {
        if (isJson(json)) {
            T data = new Gson().fromJson(json, new TypeToken<T>() {
            }.getType());
            onSuccess(data);
        } else {
            onSuccess(null);
        }
    }

    @Override
    public void onError(Throwable e) {
        onSuccess(null);
    }

    @Override
    public void onComplete() {

    }


    private boolean isJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        } catch (JsonParseException e) {
            return false;
        }
    }

    public abstract void onSuccess(T o);
}
