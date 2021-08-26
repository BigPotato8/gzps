package com.augurit.agmobile.agwater5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.augurit.agmobile.agwater5.login.LoginActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class LaunchActivity extends AppCompatActivity {
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        compositeDisposable = new CompositeDisposable();
        Disposable subscribe = Observable.timer(2000, TimeUnit.MILLISECONDS).subscribe(aLong -> {
//            User mUser = new User();
//            mUser.setLoginName("18814121642");
//            mUser.setUserName("管理员");
//            mUser.setOrgName("水务局");
//            mUser.setOrgId("123");
//            mUser.setId("123");
//            LoginManager.getInstance().saveUser(mUser);
//            LoginManager.getInstance()
//                    .getSettings(new LoginSettings(LoginConstant.SERVER_URL,
//                            LoginConstant.SERVER_URL, false));
//            LoginSettings settings = LoginManager.getInstance().getSettings();
//            AwUrlManager.setServerUrl(settings.getServerUrl());
//            EasyHttp.getInstance().setBaseUrl(AwUrlManager.serverUrl());
            startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
            finish();
        });
        compositeDisposable.add(subscribe);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}
