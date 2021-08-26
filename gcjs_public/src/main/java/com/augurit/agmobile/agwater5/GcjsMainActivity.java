package com.augurit.agmobile.agwater5;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.augurit.agmobile.agwater5.gcjs_public.GcjsPublicFragment;
import com.augurit.agmobile.agwater5.gcjs_public.login.LoginConstant;
import com.augurit.agmobile.agwater5.gcjs_public.mine.MineFragment;
import com.augurit.agmobile.busi.common.auth.AuthDialogActivity;
import com.augurit.agmobile.busi.common.auth.AuthDownloadListener;
import com.augurit.agmobile.busi.common.auth.AuthDownloadManager;
import com.augurit.agmobile.busi.common.auth.AuthFileManager;
import com.augurit.agmobile.busi.common.auth.AuthTaskService;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.update.ApkUpdateManager;
import com.augurit.agmobile.busi.common.update.utils.CheckUpdateUtils;
import com.augurit.agmobile.busi.common.update.utils.UpdateState;
import com.augurit.agmobile.common.lib.file.FilePathUtil;
import com.augurit.agmobile.common.lib.permission.PermissionUtil;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.view.navigation.NavigationTab;
import com.augurit.agmobile.common.view.navigation.NavigationTabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.augurit.agmobile.busi.common.auth.AuthDialogActivity.NO_ACCESS;
import static com.augurit.agmobile.busi.common.auth.CommonConstants.BACK;
import static com.augurit.agmobile.busi.common.auth.CommonConstants.EXTRA_CONTENT;
import static com.augurit.agmobile.busi.common.auth.CommonConstants.EXTRA_STATE;
import static com.augurit.agmobile.busi.common.auth.CommonConstants.FRONT;

public class GcjsMainActivity extends AppCompatActivity {

    private NavigationTabLayout navigation_tab_layout;
    private String[] appUpdateUrlArr = {"", ""};

    private AuthFileManager mAuthFileManager;
    private boolean toCheck = false;
    private String savePath;
    private String baseUrl = "https://yaowang4749-1258218961.cos.ap-guangzhou.myqcloud.com/";
    private String authUrl = "f1174d53-c2a6-48c8-ad41-da6017208297";
    private GcjsPublicFragment mFragment;
    private MineFragment mineFragment;
    private List<Fragment> fragments;
    private MyPageAdapter pageAdapter;
    private ViewPager view_pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcjs_main);
        EventBus.getDefault().register(this);
        savePath = new FilePathUtil(this).getAuthCachePath();
        if (LoginManager.isDestroyed() && !LoginConstant.IS_VISITOR_PATTERN) {//判断是否为游客模式，不到登录界面
            // 内存不足导致应用重启，若未初始化则跳转初始化
            startActivity(new Intent(this, GcjsVisitorLoginActivity.class));
            finish();
            return;
        }
        // IM后初始化
        //IMManager.getInstance().postInit(getApplication());
        initView();

        checkVersionUpdateWithPermissonCheck();   // TODO 临时注释
//        checkAuth(toCheck);//检查授权
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
            startService(new Intent(GcjsMainActivity.this, AuthTaskService.class));
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
        NavigationTab tab_gcjs = findViewById(R.id.tab_gcjs);
        NavigationTab tab_mine = findViewById(R.id.tab_mine);
        navigation_tab_layout.removeAllViews();
        navigation_tab_layout.addTabView(tab_gcjs);
        navigation_tab_layout.addTabView(tab_mine);

        view_pager = findViewById(R.id.view_pager);
        fragments = new ArrayList<>();
        mFragment = new GcjsPublicFragment();
        mineFragment = new MineFragment();

        fragments.add(mFragment);
        fragments.add(mineFragment);
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        view_pager.setAdapter(pageAdapter);
        view_pager.setOffscreenPageLimit(fragments.size());
        navigation_tab_layout.setViewPager(view_pager);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFragment.onActivityResult(requestCode, resultCode, data);
        mineFragment.onActivityResult(requestCode, resultCode, data);

    }

    private class MyPageAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments;

        private MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        public void setmFragments(List<Fragment> mFragments) {
            this.mFragments = mFragments;
            notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
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
        if (!TextUtils.isEmpty(updateUrl)) {
            CheckUpdateUtils.setServerUrl(updateUrl);
            new ApkUpdateManager(this, UpdateState.INNER_UPDATE, () -> {

            }).checkUpdate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        EventBus.getDefault().post(BACK);
    }
}
