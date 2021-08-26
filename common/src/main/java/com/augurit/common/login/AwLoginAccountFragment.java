package com.augurit.common.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.LoginSettings;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.busi.common.organization.model.Organization;
import com.augurit.agmobile.busi.ui.login.LoginAccountFragment;
import com.augurit.agmobile.common.lib.net.NetConnectionUtil;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.common.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * @AUTHOR 创建人:yaowang
 * @VERSION 版本:1.0
 * @PACKAGE 包名:com.augurit.agmobile.agwater5.login
 * @CREATETIME 创建时间:2018/8/29
 * @MODIFYTIME 修改时间:
 * @MODIFYBY 修改人:
 * @MODIFYMEMO 修改备注:
 */
public class AwLoginAccountFragment extends LoginAccountFragment {
    private View mView;
    private FrameLayout mFl;
    private RelativeLayout mRlLoading;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = super.onCreateView(inflater, container, savedInstanceState);
        mFl = mView.findViewById(R.id.fl_list);
        mRlLoading = mView.findViewById(R.id.rl_loading);
        //        mLoginMethod = new AwSsoAccountLoginMethod(mContext);
        mLoginMethod = new NewAwSsoAccountLoginMethod();
        LoginManager.getInstance().setTokenInterceptor(new AwTokenInterceptor(mContext));
        return mView;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        super.initView();
        btn_login.setOnClickListener(v -> {
            String account = at_account.getText().toString();
            String password = et_password.getText().toString();
            mLoginMethod.setParams(account, password);
            setViewLoading(true);
            NewAwSsoAccountLoginMethod method = (NewAwSsoAccountLoginMethod) mLoginMethod;
            method.getOrganizationsBefore()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> {

                        setViewLoading(false);

                        List<Organization> list = result.getData();
                        if (list == null || list.size() == 0) {
                            login();
                            return;
                        }
                        AwLoginOrganizationView awLoginOrganizationView = new AwLoginOrganizationView(getActivity(), mFl, list);
                        awLoginOrganizationView.setOnItemClickListener((position, organization) -> {
                            mRlLoading.setVisibility(View.VISIBLE);
                            ((NewAwSsoAccountLoginMethod) mLoginMethod).setOrgId(organization.getOrgId());
                            login();
                        });
                        awLoginOrganizationView.setOnDismissListener(aVoid -> {
                            mRlLoading.setVisibility(View.GONE);
                            setViewLoading(false);
                        });
                        awLoginOrganizationView.show();
                    }, throwable -> {
                        setViewLoading(false);
                        throwable.printStackTrace();
                        // 2019 06-19 update    if login fail  panming said enter directly
//                        login();

                        // 显示登录后界面
                        User currentUser = LoginManager.getInstance().getCurrentUser();
                        if (currentUser != null) {
                            currentUser.setOrganizations(null);
                            LoginManager.getInstance().saveUser(currentUser);
                        }
                        getBaseActivity().showPostLogin();
                        return;
                    });
        });
    }


    /**
     * 执行登录
     */
    protected void login() {
        String account = at_account.getText().toString();
        String password = et_password.getText().toString();
        mLoginMethod.setParams(account, password);
        setViewLoading(true);
        mLoginDisposable = LoginManager.getInstance()
                // 登录
                .login(mLoginMethod, !NetConnectionUtil.isConnected(getContext()))
                // 获取用户组织
                .flatMap(userApiResult -> {
                    if (userApiResult.isSuccess()) {
                        return LoginManager.getInstance()
                                .getOrganizations(mLoginMethod, !NetConnectionUtil.isConnected(getContext()));
                    } else {
                        ApiResult<List<Organization>> apiResult = new ApiResult<>();
                        userApiResult.passResult(apiResult);
                        return Observable.just(apiResult);
                    }
                })
                .subscribe(organizationsApiResult -> {
                    if (organizationsApiResult.isSuccess()) {
                        // 显示登录后界面
                        getBaseActivity().showPostLogin();
                        LoginSettings settings = LoginManager.getInstance().getSettings();
                        settings.setAutoLogin(cb_auto_login.isChecked());
                        LoginManager.getInstance().saveSettings(settings);
                    } else {
                        String msg = organizationsApiResult.getMessage();
                        if (TextUtils.isEmpty(msg)) {
                            msg = getString(R.string.login_toast_login_failed);
                        }
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                    setViewLoading(false);
                }, throwable -> {
                    throwable.printStackTrace();
                    String msg = throwable.getMessage();
                    if (throwable instanceof ConnectException) {
                        msg = getString(R.string.login_toast_login_failed_connect);
                    } else if (throwable instanceof SocketTimeoutException) {
                        msg = getString(R.string.login_toast_login_failed_timeout);
                    } else if (msg.isEmpty()) {
                        msg = getString(R.string.login_toast_login_failed);
                    } else if (msg.contains("登陆")) {
                        msg = msg.replace("登陆", "登录");
                    }
                    // 提示登录错误
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    setViewLoading(false);

                    // 2019 06-19 update    if login fail  panming said enter directly
                    // 显示登录后界面
                    User currentUser = LoginManager.getInstance().getCurrentUser();
                    if (currentUser != null) {
                        currentUser.setOrganizations(null);
                        LoginManager.getInstance().saveUser(currentUser);
                    }
                    getBaseActivity().showPostLogin();
                    return;

                });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            mContext = context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {
            mContext = activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
        mRlLoading.setVisibility(View.GONE);
    }
}
