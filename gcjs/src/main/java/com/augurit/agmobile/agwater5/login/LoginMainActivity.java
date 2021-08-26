package com.augurit.agmobile.agwater5.login;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.LoginSettings;
import com.augurit.agmobile.busi.common.update.ApkUpdateManager;
import com.augurit.agmobile.busi.common.update.utils.CheckUpdateUtils;
import com.augurit.agmobile.busi.common.update.utils.UpdateState;
import com.augurit.agmobile.busi.ui.login.BaseLoginActivity;

import java.util.Random;

public class LoginMainActivity extends AppCompatActivity {

    private Button mBtnAccount;
    private RelativeLayout mBtnPassword;
    private ApkUpdateManager mApkUpdateManager;
    private String[] appUpdateUrlArr = {"http://19.130.245.196:18081/"};
//    private String[] appUpdateUrlArr = {"http://219.130.221.47:8084/"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            finish();
            return;
        }
        setContentView(R.layout.login_view_account_gcjs_main);
        this.initView();
        //检查更新
        mApkUpdateManager = new ApkUpdateManager(this, UpdateState.INNER_UPDATE, () -> {
        });
        checkVersionUpdateWithPermissonCheck();
        LoginSettings settings = LoginManager.getInstance().getSettings();
        try {
            if (settings.isAutoLogin()) {
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        TextView versionText = findViewById(R.id.text_version);
        try {
            PackageManager packageManager = getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);

            versionText.setText("当前版本：V" + packageInfo.versionName);
        } catch (Exception e) {
            versionText.setVisibility(View.GONE);
        }
        mBtnAccount = (Button) findViewById(R.id.btn_account);
        mBtnPassword = (RelativeLayout) findViewById(R.id.btn_password);
        mBtnAccount.setOnClickListener(view -> {
            Intent intent = new Intent(LoginMainActivity.this,LoginActivity.class);
            intent.putExtra("status",0);//0是用户名登录，1是手机验证码登录
            startActivity(intent);
        });
        mBtnPassword.setOnClickListener(view -> {
            Intent intent = new Intent(LoginMainActivity.this,LoginActivity.class);
            intent.putExtra("status",1);//0是用户名登录，1是手机验证码登录
            startActivity(intent);
        });
    }

    private void checkVersionUpdateWithPermissonCheck() {
        String updateUrl = appUpdateUrlArr[new Random().nextInt(appUpdateUrlArr.length)];
//        String updateUrl = "http://" + LoginConstant.BASE_GZPS_URL + "/appFile/apk_version.json";
//        String updateUrl =  "http://zhsz.gzcc.gov.cn:8080/agsupport/pdaUpdate/apk_version.json";
        CheckUpdateUtils.setServerUrl(updateUrl);
        CheckUpdateUtils.setUpdatePath("aplanmis-rest/app/apk_version.json");
        mApkUpdateManager.checkUpdate(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mApkUpdateManager != null) {
            mApkUpdateManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}