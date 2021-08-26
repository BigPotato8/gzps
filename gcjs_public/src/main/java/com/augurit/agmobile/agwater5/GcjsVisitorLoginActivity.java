package com.augurit.agmobile.agwater5;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.augurit.agmobile.agwater5.gcjs_public.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs_public.login.LoginConstant;
import com.augurit.agmobile.agwater5.gcjs_public.login.method.AwSsoAccountLoginMethod;
import com.augurit.agmobile.agwater5.gcjs_public.login.util.AwTokenInterceptor;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.method.ILoginMethod;
import com.augurit.agmobile.busi.common.login.model.LoginSettings;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.busi.common.update.ApkUpdateManager;
import com.augurit.agmobile.busi.ui.login.BaseLoginActivity;
import com.augurit.agmobile.busi.ui.login.LoginSettingsFragment;
import com.augurit.agmobile.busi.ui.login.adapter.UserNameAdapter;
import com.augurit.agmobile.common.lib.net.NetConnectionUtil;
import com.augurit.agmobile.common.lib.permission.PermissionUtil;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.view.widget.WEUIButton;
import com.augurit.common.common.form.AwFormActivity;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.model.HttpHeaders;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 游客模式登录Activity
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.login
 * @createTime 创建时间 ：2018/8/17
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/8/17
 * @modifyMemo 修改备注：
 */
public class GcjsVisitorLoginActivity extends BaseLoginActivity {

    public static final int LOGIN_OK =  998;
    public static final int LOGIN_REQUEST =  988;
    public static final String LOGIN_OUT = "LOGIN_OUT";
    public static final String JUMP_TITLE = "JUMP_TITLE";
    public static final String JUMP_ACTIVITY= "JUMP_ACTIVITY";
    private Class nextActivity;

    private String[] appUpdateUrlArr = {"http://zhsz.gzcc.gov.cn:8080/"};   // 对应ip 210.72.5.106 模拟器无法解析时可用ip测试
    protected View view, tv_regist, tv_find_pwd;
    protected AutoCompleteTextView at_account;
    protected EditText et_password;
    protected WEUIButton btn_login;
    protected View ll_auto_login;
    protected CheckBox cb_auto_login;
    protected ILoginMethod mLoginMethod;
    protected Disposable mLoginDisposable;
    protected Button btn_setting;
    protected ApkUpdateManager mApkUpdateManager;
    private String mJump;

    /**
     * 获取Intent, 以此方式启动，登录成功后将跳转至指定Activity
     *
     * @param context      context
     * @param nextActivity 登录成功后跳转的Activity
     * @return intent
     */
    public static Intent getIntent(Context context, Class nextActivity) {
        Intent intent = new Intent(context, GcjsVisitorLoginActivity.class);
        intent.putExtra(JUMP_ACTIVITY, nextActivity);
        return intent;
    }

    @Override
    public void showIndex() {


    }



    @Override
    protected LoginViewSettings initSettings() {
        PermissionUtil.instance().with(this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .requestCode(101)
                .check(permissions -> {
                });
        LoginManager.getInstance()
                .getSettings(new LoginSettings(LoginConstant.SERVER_URL,
                        LoginConstant.SERVER_URL, false));
        return super.initSettings();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (!isTaskRoot()) {
//            finish();
//            return;
//        }

        mJump = getIntent().getStringExtra(JUMP_TITLE);
        nextActivity = (Class) getIntent().getSerializableExtra(JUMP_ACTIVITY);

        this.initView();

        LoginSettings settings = LoginManager.getInstance().getSettings();
        EasyHttp.getInstance().setBaseUrl(AwUrlManager.serverUrl());


        //设置token刷新机制
        EasyHttp.getInstance()
                .setRetryCount(1)
                .addInterceptor(new AwTokenInterceptor());

        if (settings.isAutoLogin()) {
            autoLogin();
        }
    }

    protected void initView() {
        setContentView(R.layout.login_view_account_visitor);

        View btn_back = findViewById(R.id.btn_back);
        btn_back.setVisibility(View.GONE);
//        findViewById(R.id.ll_login_bottom).setVisibility(View.GONE);

        this.btn_login = findViewById(R.id.btn_login);
        this.tv_find_pwd = findViewById(R.id.tv_find_pwd);
        this.tv_regist = findViewById(R.id.tv_regist);
        this.btn_setting = findViewById(R.id.btn_setting);
        this.at_account = findViewById(R.id.at_account);
        this.et_password = findViewById(R.id.et_password);
        this.cb_auto_login = findViewById(R.id.cb_auto_login);
        this.ll_auto_login = findViewById(R.id.ll_auto_login);
        this.et_password.setOnEditorActionListener((v, actionId, event) -> {
            if ((actionId == 6 || event != null && 66 == event.getKeyCode() && 0 == event.getAction()) && this.btn_login.isEnabled()) {
                this.btn_login.performClick();
            }

            return false;
        });
        btn_setting.setVisibility(View.GONE);
        btn_setting.setOnClickListener((v) -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction
                    .setCustomAnimations(R.anim.anim_view_right_in, R.anim.anim_view_left_out,
                            R.anim.anim_view_left_in, R.anim.anim_view_right_out)
                    .replace(R.id.fl_content, new LoginSettingsFragment())
                    .addToBackStack(null)
                    .commit();
        });
        this.ll_auto_login.setOnClickListener((v) -> {
            this.cb_auto_login.performClick();
        });
        this.btn_login.setOnClickListener((v) -> {
            this.login();
        });
//        this.btn_login.setEnabled(false);
        User lastUser = LoginManager.getInstance().getLastUser(true);
        if (lastUser != null) {
            this.at_account.setText(lastUser.getLoginName());
        }

        List<User> users = LoginManager.getInstance().getUsers(true);
        UserNameAdapter adapter = new UserNameAdapter(this, users);
        this.at_account.setAdapter(adapter);
        this.at_account.setThreshold(1);
        this.at_account.setDropDownVerticalOffset(2);
        adapter.setOnItemClickListener(new UserNameAdapter.OnItemClickListener() {
            public void onNameClick(View v, User user) {
                at_account.setText(user.getLoginName());
                at_account.setSelection(at_account.getText().length());
                at_account.dismissDropDown();
            }

            public void onDeleteClick(View v, User user) {
                LoginManager.getInstance().saveUser(user, false);
            }
        });
        BaseLoginActivity.LoginViewSettings viewSettings = getViewSettings();
        if (viewSettings.checkAutoLogin) {
            this.cb_auto_login.setChecked(true);
        }


        //检查更新
//        mApkUpdateManager = new ApkUpdateManager(this, UpdateState.INNER_UPDATE, () -> {
//        });
//        checkVersionUpdateWithPermissonCheck();

    }

    @Override
    public void jumpToMain() {
        LoginSettings loginSettings = LoginManager.getInstance().getSettings();
        AwUrlManager.setServerUrl(loginSettings.getServerUrl());
        EasyHttp.getInstance().setBaseUrl(AwUrlManager.serverUrl());

        try {
            Intent intent = new Intent(this, nextActivity);
            intent.putExtra(AwFormActivity.EXTRA_TITLE, mJump);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }

        setResult(LOGIN_OK);
        finish();
    }

    protected void login() {
        this.mLoginMethod = new AwSsoAccountLoginMethod();

        String account = this.at_account.getText().toString();
        String password = this.et_password.getText().toString();
        if (TextUtils.isEmpty(account)) {
            ToastUtil.shortToast(this, "请输入账号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.shortToast(this, "请输密码");
            return;
        }

        this.mLoginMethod.setParams(account, password);
        this.setViewLoading(true);

        this.mLoginDisposable = LoginManager.getInstance().login(this.mLoginMethod, !NetConnectionUtil.isConnected(this))
                .subscribe((organizationsApiResult) -> {
                    if (organizationsApiResult.isSuccess() && organizationsApiResult.getData() != null) {

                        LoginSettings settings = LoginManager.getInstance().getSettings();
                        settings.setAutoLogin(this.cb_auto_login.isChecked());
                        LoginManager.getInstance().saveSettings(settings);

                        HttpHeaders httpHeaders = new HttpHeaders("Authorization", organizationsApiResult.getData().getUserType());
                        EasyHttp.getInstance().addCommonHeaders(httpHeaders);
                        jumpToMain();

                    } else {
                        String msg = organizationsApiResult.getMessage();
                        if (TextUtils.isEmpty(msg)) {
                            msg = "用户名或密码错误";
                        }
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

//                        //TODO 目前失败也进去
//                        jumpToMain();
                    }

                    this.setViewLoading(false);
                }, (throwable) -> {
                    throwable.printStackTrace();
                    String msg = throwable.getMessage();
                    if (throwable instanceof ConnectException) {
                        msg = this.getString(R.string.login_toast_login_failed_connect);
                    } else if (throwable instanceof SocketTimeoutException) {
                        msg = this.getString(R.string.login_toast_login_failed_timeout);
                    } else if (msg.isEmpty()) {
                        msg = this.getString(R.string.login_toast_login_failed);
                    } else if (msg.contains("登陆")) {
                        msg = msg.replace("登陆", "登录");
                    }

                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                    this.setViewLoading(false);

//                    //TODO 目前失败也进去
//                    jumpToMain();

                });

    }

    protected void setViewLoading(boolean isLoading) {
        this.btn_login.setLoading(isLoading);
        this.at_account.setEnabled(!isLoading);
        this.et_password.setEnabled(!isLoading);
        this.ll_auto_login.setClickable(!isLoading);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mLoginDisposable != null && !this.mLoginDisposable.isDisposed()) {
            this.mLoginDisposable.dispose();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mApkUpdateManager != null) {
            mApkUpdateManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
