package com.augurit.agmobile.agwater5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GcjsLaunchActivity extends AppCompatActivity {
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        compositeDisposable = new CompositeDisposable();
        Disposable subscribe = Observable.timer(2000, TimeUnit.MILLISECONDS).subscribe(aLong -> {
            startActivity(GcjsLoginActivity.getIntent(GcjsLaunchActivity.this, GcjsMainActivity.class));
            finish();
        });
        compositeDisposable.add(subscribe);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(compositeDisposable!=null){
            compositeDisposable.clear();
        }
    }
}
