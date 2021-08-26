package com.augurit.agmobile.agwater5;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;

import com.augurit.common.common.form.AgWaterInjection;
import com.augurit.common.login.AwLoginAccountFragment;
import com.augurit.common.login.AwTokenInterceptor;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.LoginSettings;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.busi.common.organization.model.Organization;
import com.augurit.agmobile.busi.ui.login.BaseLoginActivity;
import com.augurit.agmobile.common.lib.common.AppUtils;
import com.augurit.agmobile.common.lib.permission.PermissionUtil;
import com.augurit.agmobile.common.lib.sp.SharedPreferencesUtil;
import com.augurit.common.R;
import com.augurit.common.common.manager.AwUrlManager;
import com.augurit.common.common.manager.LoginConstant;
import com.augurit.common.common.view.DialogUtil;
import com.zhouyou.http.EasyHttp;

import static com.augurit.agmobile.busi.common.auth.CommonConstants.SP_EXTRA_IMEI;

/**
 * 登录Activity
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.login
 * @createTime 创建时间 ：2018/8/17
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/8/17
 * @modifyMemo 修改备注：
 */
public class LoginActivity extends BaseLoginActivity {

    private static final String EXTRA_NEXT_ACTIVITY = "EXTRA_NEXT_ACTIVITY";

    private Class mNextActivity;

    /**
     * 获取Intent, 以此方式启动，登录成功后将跳转至指定Activity
     *
     * @param context      context
     * @param nextActivity 登录成功后跳转的Activity
     * @return intent
     */
    public static Intent getIntent(Context context, Class nextActivity) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(EXTRA_NEXT_ACTIVITY, nextActivity);
        return intent;
    }

    @Override
    public void jumpToMain() {

//        ImagePicker.getInstance().setImageLoader(new GlideTokenImageLoader());
        LoginManager.getInstance().setTokenInterceptor(new AwTokenInterceptor(this));
        LoginSettings settings = LoginManager.getInstance().getSettings();
        AwUrlManager.setServerUrl(settings.getServerUrl());
        EasyHttp.getInstance().setBaseUrl(AwUrlManager.serverUrl());
        AgWaterInjection.provideDictRepository(this)
                .updateDicts()
                .subscribe(booleans -> {
                }, Throwable::printStackTrace);
        Intent intent;
        if (mNextActivity != null) {
            intent = new Intent(this, mNextActivity);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }


    @Override
    public void showAccountLogin() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction
                .setCustomAnimations(R.anim.anim_view_right_in, R.anim.anim_view_left_out,
                        R.anim.anim_view_left_in, R.anim.anim_view_right_out)
                .replace(R.id.fl_content, new AwLoginAccountFragment())
                .addToBackStack(null)
                .commit();
        //获取设备IMEI并存储
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(this);
        PermissionUtil.instance()
                .with(this)
                .permissions(Manifest.permission.READ_PHONE_STATE)
                .check(permissions -> {
                    String imei = AppUtils.getIMEI();
                    sharedPreferencesUtil.setString(SP_EXTRA_IMEI, imei);
                });
    }

    @Override
    public void showPostLogin() {
        User currentUser = LoginManager.getInstance().getCurrentUser();
        if (currentUser != null && currentUser.getOrganizations() != null && currentUser.getOrganizations().size() > 1) {
            if (currentUser.getOrgId() != null) {
                for (int i = 0; i < currentUser.getOrganizations().size(); i++) {
                    Organization organization = currentUser.getOrganizations().get(i);
                    if (organization != null && organization.getOrgId().equals(currentUser.getOrgId())) {
                        currentUser.setOrgSelected(i);
                        LoginManager.getInstance().saveUser(currentUser);
                        jumpToMain();
                        return;
                    }
                }
            }
        }else{
            DialogUtil.MessageBox(this, "提示", "网络错误，继续登录将无法使用排水巡检相关功能，是否要继续登录？", (v, i) -> {
                v.dismiss();
                super.showPostLogin();
            }, (v, i) -> {
                v.dismiss();
            });
            return;
        }
        super.showPostLogin();


    }

    @Override
    protected LoginViewSettings initSettings() {
        PermissionUtil.instance().with(this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .requestCode(101)
                .check(permissions -> {
                });

        Intent intent = getIntent();
        if (intent != null
                && intent.hasExtra(EXTRA_NEXT_ACTIVITY)) {
            mNextActivity = (Class) intent.getSerializableExtra(EXTRA_NEXT_ACTIVITY);
        } else {
            mNextActivity = MainActivity.class;
        }

        LoginManager.getInstance()
                .getSettings(new LoginSettings(LoginConstant.SERVER_URL,
                        LoginConstant.SERVER_URL, false));

        LoginViewSettings loginViewSettings = super.initSettings();
        loginViewSettings.checkAutoLogin = true;
        return loginViewSettings;
    }
}
