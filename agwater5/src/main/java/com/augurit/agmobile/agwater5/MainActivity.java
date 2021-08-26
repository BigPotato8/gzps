package com.augurit.agmobile.agwater5;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.augurit.agmobile.agwater5.drainage.GzpsDrainageFragment;
//import com.augurit.agmobile.agwater5.nanning.NNNanNingFragment;
import com.augurit.agmobile.busi.common.auth.AuthDialogActivity;
import com.augurit.agmobile.busi.common.auth.AuthDownloadListener;
import com.augurit.agmobile.busi.common.auth.AuthDownloadManager;
import com.augurit.agmobile.busi.common.auth.AuthFileManager;
import com.augurit.agmobile.busi.common.auth.AuthTaskService;
import com.augurit.agmobile.busi.common.common.constant.UrlConstant;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.Authentication;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.busi.common.update.ApkUpdateManager;
import com.augurit.agmobile.busi.common.update.utils.CheckUpdateUtils;
import com.augurit.agmobile.busi.common.update.utils.UpdateState;
import com.augurit.agmobile.busi.map.utils.DensityUtil;
import com.augurit.agmobile.common.lib.file.FilePathUtil;
import com.augurit.agmobile.common.lib.json.JsonUtil;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.permission.PermissionUtil;
import com.augurit.agmobile.common.lib.sp.SharedPreferencesUtil;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.view.imagepicker.ImagePicker;
import com.augurit.agmobile.common.view.navigation.NavigationTab;
import com.augurit.agmobile.common.view.navigation.NavigationTabLayout;
import com.augurit.agmobile.common.view.navigation.OnTabChangedListner;
import com.augurit.agmobile.psh.GzPshFragment;
import com.augurit.common.common.manager.AwUrlConstant;
import com.augurit.common.common.manager.AwUrlManager;
import com.augurit.common.common.util.GlideTokenImageLoader;
import com.augurit.common.im.view.ImFragment;
import com.augurit.common.mine.MineFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.request.PostRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

import static com.augurit.agmobile.busi.common.auth.AuthDialogActivity.NO_ACCESS;
import static com.augurit.agmobile.busi.common.auth.CommonConstants.BACK;
import static com.augurit.agmobile.busi.common.auth.CommonConstants.EXTRA_CONTENT;
import static com.augurit.agmobile.busi.common.auth.CommonConstants.EXTRA_STATE;
import static com.augurit.agmobile.busi.common.auth.CommonConstants.FRONT;

public class MainActivity extends AppCompatActivity {

    private NavigationTabLayout navigation_tab_layout;
    private String[] appUpdateUrlArr = {UrlConstant.APP_UPDATE_ONE, UrlConstant.APP_UPDATE_TWO};

    private AuthFileManager mAuthFileManager;
    private boolean toCheck = false;
    private String savePath;
    private String baseUrl = "https://yaowang4749-1258218961.cos.ap-guangzhou.myqcloud.com/";
    private String authUrl = "f1174d53-c2a6-48c8-ad41-da6017208297";
    private AlertDialog mShow;
    private SharedPreferencesUtil mSharedPreferencesUtil;
    private String mContentValue;
    private String mPsxj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        mSharedPreferencesUtil = new SharedPreferencesUtil(this);
        mContentValue = mSharedPreferencesUtil.getString("fdAuthentication", "");
        mPsxj = mSharedPreferencesUtil.getString("psxj", "");
        savePath = new FilePathUtil(this).getAuthCachePath();
        if (LoginManager.isDestroyed()) {
            // 内存不足导致应用重启，若未初始化则跳转初始化
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        initView();
//        checkVersionUpdateWithPermissonCheck();   // TODO 临时注释
        ImagePicker.getInstance().setImageLoader(GlideTokenImageLoader.getInstance());
        checkAuth(toCheck);//检查授权
    }

    public void checkAuth(boolean toCheck) {
        if (!toCheck) {
            return;
        }
        AuthFileManager.init(savePath);
        mAuthFileManager = AuthFileManager.getInstance();
        if (!mAuthFileManager.getIsAccessible()) {
            ProgressDialogUtil.showProgressDialog(this, "正在下载证书...", false);
            AuthDownloadManager.getInstance()
                    .serverUrl(baseUrl)
                    .downLoadPath(authUrl)
                    .savePath(savePath)
                    .saveName("augur_auth")
                    .Download(new AuthDownloadListener() {
                        @Override
                        public void success() {
                            ProgressDialogUtil.dismissProgressDialog();
                            checkAuth(toCheck);
                        }

                        @Override
                        public void failed() {
                            //跳出对话框，提示无证书
                            ProgressDialogUtil.dismissProgressDialog();
                            Intent intent = new Intent(getApplicationContext(), AuthDialogActivity.class);
                            intent.putExtra(EXTRA_CONTENT, "无证书，请联系管理员！");
                            intent.putExtra(EXTRA_STATE, NO_ACCESS);
                            startActivity(intent);
                        }
                    });
        } else {
            //获取文件，如果有文件则进入service读取
            startService(new Intent(MainActivity.this, AuthTaskService.class));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveFront(String str) {
        if (FRONT.equals(str)) {
            checkAuth(toCheck);
        }
    }

    private void initView() {

        navigation_tab_layout = findViewById(R.id.navigation_tab_layout);

        NavigationTab tab_work = findViewById(R.id.tab_work);
//        NavigationTab  = findViewById(R.id.);
//        NavigationTab tab_nw = findViewById(R.id.tab_nw);
//        NavigationTab tab_contact = findViewById(R.id.tab_contact);
//        NavigationTab tab_nanning = findViewById(R.id.tab_nanning);
//        NavigationTab tab_message = findViewById(R.id.tab_message);
//        NavigationTab tab_mine = findViewById(R.id.tab_mine);
//        NavigationTab tab_pipeline = findViewById(R.id.tab_pipeline);

        ViewPager view_pager = findViewById(R.id.view_pager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ImFragment());
//        try {
//            //注意：key是步骤一中的meta的name属性，即“APP_PKG_KEY”
//            PackageManager pm = getPackageManager();
//            ApplicationInfo appInfo = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
//            String app_pkg_key = appInfo.metaData.getString("APP_PKG_KEY");
//
//            if ("agwater".equals(app_pkg_key)) {
                User currentUser = LoginManager.getInstance().getCurrentUser();
                if (currentUser != null && currentUser.getOrganizations() != null) {
                    fragments.add(new GzpsDrainageFragment());
                } else {
                    navigation_tab_layout.removeView(tab_work);
                }

                fragments.add(new GzPshFragment());
//                fragments.add(new NWSewageFragment());
//                fragments.add(new FloodMainFragment());
//                //        fragments.add(new ShizhengFragment());
//                fragments.add(new NNNanNingFragment());
//                fragments.add(new PipelineFragment());


//            }
//            else if ("gzps".equals(app_pkg_key)) {
//                navigation_tab_layout.removeAllViews();
//                navigation_tab_layout.addTabView(tab_message);
//                navigation_tab_layout.addTabView(tab_work);
//                navigation_tab_layout.addTabView(tab_mine);
//                fragments.add(new DrainageFragment());
//            } else if ("psh".equals(app_pkg_key)) {
//                navigation_tab_layout.removeAllViews();
//                navigation_tab_layout.addTabView(tab_message);
//                navigation_tab_layout.addTabView();
//                navigation_tab_layout.addTabView(tab_mine);
//                fragments.add(new PshFragment());
//            } else if ("gzws".equals(app_pkg_key)) {
//                navigation_tab_layout.removeAllViews();
//                navigation_tab_layout.addTabView(tab_message);
//                navigation_tab_layout.addTabView(tab_home);
//                navigation_tab_layout.addTabView(tab_mine);
//                fragments.add(new SewageFragment());
//            } else if ("fxyj".equals(app_pkg_key)) {
//                navigation_tab_layout.removeAllViews();
//                navigation_tab_layout.addTabView(tab_message);
//                navigation_tab_layout.addTabView(tab_contact);
//                navigation_tab_layout.addTabView(tab_mine);
//                fragments.add(new FloodMainFragment());
//            } else if ("nn".equals(app_pkg_key)) {
//                navigation_tab_layout.removeAllViews();
//                navigation_tab_layout.addTabView(tab_message);
//                navigation_tab_layout.addTabView(tab_nanning);
//                navigation_tab_layout.addTabView(tab_mine);
//                fragments.add(new NNNanNingFragment());
//            }

            fragments.add(new MineFragment());

            MyPageAdapter pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
            view_pager.setAdapter(pageAdapter);
            view_pager.setOffscreenPageLimit(fragments.size());
            navigation_tab_layout.setViewPager(view_pager);
            navigation_tab_layout.setOnTabChangedListner(new OnTabChangedListner() {
                @Override
                public void onTabSelected(int i) {
//                    if (fragments.get(i) instanceof FloodMainFragment) {
//                        if (StringUtil.isEmpty(mContentValue)) {
//                            mContentValue = mSharedPreferencesUtil.getString("fdAuthentication", "");
//                            if (StringUtil.isEmpty(mContentValue)) {
//                                showInputDialog();
//                            }
//                        } else {
//                            Authentication authentication = JsonUtil.getObject(mContentValue, Authentication.class);
//                            if (authentication != null) {
//                                LoginManager.getInstance().refreshAuthentication(authentication);
//                            }
//                        }
//                    } else {
//                        if (!StringUtil.isEmpty(mPsxj)) {
//                            Authentication authentication = JsonUtil.getObject(mPsxj, Authentication.class);
//                            if (authentication != null) {
//                                LoginManager.getInstance().refreshAuthentication(authentication);
//                            }
//                        }
//                    }
                }
            });
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;

        private MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            // 返回键不退出
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.instance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void checkVersionUpdateWithPermissonCheck() {
        String updateUrl = appUpdateUrlArr[new Random().nextInt(appUpdateUrlArr.length)];
        //String updateUrl = "http://" + LoginConstant.BASE_GZPS_URL + "/appFile/apk_version.json";
        CheckUpdateUtils.setServerUrl(updateUrl);
        new ApkUpdateManager(this, UpdateState.INNER_UPDATE, new ApkUpdateManager.NoneUpdateCallback() {
            @Override
            public void onFinish() {

            }
        }).checkUpdate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        EventBus.getDefault().post(BACK);
    }

    private void showInputDialog() {

        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.flood_login_dialog, null);
        inputDialog.setView(view);
        View viewById = view.findViewById(R.id.subBtn);
        EditText accountEt = view.findViewById(R.id.accountEt);
        EditText pwdEt = view.findViewById(R.id.pwdEt);
        viewById.setOnClickListener(v -> {
            if (mShow != null && mShow.isShowing()) {
                mShow.dismiss();
            }
            login(accountEt.getText().toString(), pwdEt.getText().toString());
            ToastUtil.shortToast(this, "正在登录！");
        });
        inputDialog.setCancelable(true);
        mShow = inputDialog.create();
        Window window = mShow.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mShow.show();
        WindowManager.LayoutParams params = mShow.getWindow().getAttributes();
        params.width = DensityUtil.dp2px(this, 240);
        mShow.getWindow().setAttributes(params);

    }

    private void login(String account, String pwd) {
        String base = "psmonitoring:psmonitoring123";
        String base64encodedString = Base64.encodeToString(base.getBytes(), Base64.DEFAULT).replace("\n", "");
//        String base64encodedString = "cHNtb25pdG9yaW5nOnBzbW9uaXRvcmluZzEyMw==";
        PostRequest getRequest = EasyHttp.post(AwUrlConstant.AW_POST_FLOOD_LOGIN_URL)
                .baseUrl(AwUrlManager.getBaseUrlFlood())//
                .params("username", account)
                .params("password", pwd)
                .params("isApp", String.valueOf(true))
                .params("orgId", "406")
                .headers("Authorization", "Basic " + base64encodedString);
        getRequest.execute(String.class)
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just("");
                })
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (!TextUtils.isEmpty(s)) {
                        ApiResult<Authentication> apiResult = new Gson().fromJson(s, new TypeToken<ApiResult<Authentication>>() {
                        }.getType());

                        if (apiResult != null && apiResult.isSuccess()) {
                            Authentication data = apiResult.getData();
                            mSharedPreferencesUtil.setString("fdAuthentication", JsonUtil.getJson(data));
                            data.setAccessTime(System.currentTimeMillis());
                            data.setTokenType("Bearer");
                            LoginManager.getInstance().refreshAuthentication(data);
                        } else {
                            ToastUtil.shortToast(this, "登录失败！");
                        }
                    } else {
                        ToastUtil.shortToast(this, "登录失败！");
                    }
                });
    }
}
