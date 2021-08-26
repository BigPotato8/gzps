package com.augurit.agmobile.agwater5.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.login.method.AwSsoAccountLoginMethod;
import com.augurit.agmobile.agwater5.login.util.AwTokenInterceptor;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.method.SsoAccountLoginMethod;
import com.augurit.agmobile.busi.ui.login.LoginAccountFragment;


/**
 * @AUTHOR 创建人:yaowang
 * @VERSION 版本:1.0
 * @PACKAGE 包名:com.augurit.agmobile.agwater5.login
 * @CREATETIME 创建时间:2018/8/29
 * @MODIFYTIME 修改时间:
 * @MODIFYBY 修改人:
 * @MODIFYMEMO 修改备注:
 */
public class AwLoginAccountFragment extends LoginAccountFragment{
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_view_account_gcjs, (ViewGroup)null);
        initView();
        mLoginMethod = new SsoAccountLoginMethod();

        mView = view;
//        mLoginMethod = new AwSsoAccountLoginMethod();
//        LoginManager.getInstance().setTokenInterceptor(new AwTokenInterceptor());
        return mView;
    }
}
