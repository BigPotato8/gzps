package com.augurit.common.mine.sign.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.common.lib.json.JsonUtil;
import com.augurit.agmobile.common.lib.location.BaiduRequestAddress;
import com.augurit.agmobile.common.lib.location.IRequestAddress;
import com.augurit.agmobile.common.lib.location.LocationConfiguration;
import com.augurit.agmobile.common.lib.location.LocationListener;
import com.augurit.agmobile.common.lib.permission.PermissionUtil;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.lib.baidumap.location.BaiduLocationManager;
import com.augurit.agmobile.lib.baidumap.location.model.BDPointer;
import com.augurit.agmobile.lib.baidumap.location.model.WGSPointer;
import com.augurit.common.R;
import com.augurit.common.mine.sign.model.SignBean;
import com.augurit.common.mine.sign.model.SignResultBean;

import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SignActivity extends AppCompatActivity implements ISignContract.View {
    private int year;
    private int month;
    private int day;
    private TextView dateTv;
    private Button btnSign;
    private WebView mWebView;
    private double x = 0;
    private double y = 0;
    private String mStreet;
    private String mAddress;
    private SignPresenter mPresenter;
    private BaiduLocationManager mLocationManager;
    private BaiduRequestAddress mRequestAddress;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        compositeDisposable = new CompositeDisposable();
        mPresenter = new SignPresenter(this);
        initView();
        initDate();

    }

    private void initDate() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        dateTv.setText(year + "-" + month);
    }

    private void initView() {
        mLocationManager = new BaiduLocationManager();
        mRequestAddress = new BaiduRequestAddress();
        findViewById(R.id.ll_back).setOnClickListener(view -> finish());
        mWebView = findViewById(R.id.wv_sign);
        btnSign = findViewById(R.id.btn_sign);
        btnSign.setOnClickListener(view -> checkLocationPermission());
        dateTv = findViewById(R.id.year_month_tv);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/echarts/sign.html");
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                loadSignData();
            }
        });
    }

    private void checkLocationPermission() {
        PermissionUtil.instance()
                .with(this)
                .rationale(getString(com.augurit.agmobile.busi.bpm.R.string.need_location_permission))
                .permissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .check(permissions -> mLocationManager.startLocate(LocationConfiguration.getDefaultConfiguration(SignActivity.this), new MyLocationListener()));
    }

    private class MyLocationListener implements LocationListener {
        boolean isLocated = false;
        @Override
        public void onLocationChanged(Location location,Location location1) {
            if (!isLocated) {
                Disposable disposable = mRequestAddress.parseLocation(location.getLatitude(), location.getLongitude(), IRequestAddress.COORD_TYPE_BD09LL)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(address -> {
                            BDPointer bdPointer = new BDPointer(location.getLatitude(), location.getLongitude());
                            WGSPointer wgsPointer = bdPointer.toWGSPointer();
                            double curX = wgsPointer.getLongtitude();
                            double curY = wgsPointer.getLatitude();
                            if (curX <= 0 || curY <= 0) {
                                ToastUtil.shortToast(SignActivity.this, "定位失败，无法签到，请确认是否已授予定位权限");
                                return;
                            }
                            x = curX;
                            y = curY;
                            mStreet = address.getStreet();
                            mAddress = address.getDetailAddress();
                            mLocationManager.stopLocate(false);
                            mPresenter.doSign(x, y, mStreet, mAddress, getLocalVersionName(SignActivity.this));
                        }, Throwable::printStackTrace);
                compositeDisposable.add(disposable);
                isLocated = true;
            }
        }
    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }


    public void preMonth(View view) {
        if (month == 1) {
            dateTv.setText(year - 1 + "-" + 12);
            month = 12;
            year--;
        } else {
            dateTv.setText(year + "-" + (month - 1));
            month--;
        }
        loadSignData();
    }

    public void nextMonth(View view) {
        if (month == 12) {
            dateTv.setText(year + 1 + "-" + 1);
            month = 1;
            year++;
        } else {
            dateTv.setText(year + "-" + (month + 1));
            month++;
        }
        loadSignData();
    }

    private void loadSignData() {
        String monthStr = "" + month;
        if (month < 10) {
            monthStr = "0" + monthStr;
        }
        User currentUser = LoginManager.getInstance().getCurrentUser();
        String userId = currentUser.getUserId();
        mPresenter.getSignInfo(userId, year + "" + monthStr + "");
    }

    @Override
    public void showSignInfo(SignBean signBean) {
        if (signBean == null || signBean.getMonthlySignDate() == null) {
            signBean = new SignBean();
            signBean.setMonthlySignDate(new ArrayList<>());
        }
        btnSign.setVisibility(View.VISIBLE);
        String signList = JsonUtil.getJson(signBean);
        mWebView.loadUrl("javascript:setData(" + year + "," + month + "," + signList + ");");
        String dayStr = "" + day;
        if (day < 10) {
            dayStr = "0" + day;
        }
        if (signBean.getMonthlySignDate() != null && signBean.getMonthlySignDate().contains(dayStr)) {
            btnSign.setEnabled(false);
            btnSign.setText("今日已签到");
            btnSign.setTextColor(SignActivity.this.getResources().getColor(R.color.agmobile_text_tag));
        } else {
            //startLocateAndCheck();
        }
    }

    @Override
    public void showSignResult(SignResultBean signResult) {
        if(signResult == null){
            ToastUtil.shortToast(SignActivity.this,"签到失败，请重试");
            return;
        }
        ToastUtil.shortToast(SignActivity.this,"签到成功");
        btnSign.setEnabled(false);
        btnSign.setTextColor(SignActivity.this.getResources().getColor(R.color.agmobile_text_tag));
        btnSign.setText("今日已签到");
        loadSignData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable!=null){
            compositeDisposable.clear();
            compositeDisposable=null;
        }
        if(mPresenter!=null){
            mPresenter.destroyDisposable();
        }
    }
}
