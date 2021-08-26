package com.augurit.agmobile.agwater5;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.augurit.agmobile.agwater5.gcjs.GcjsFragment;
import com.augurit.agmobile.agwater5.im.IMConstant;
import com.augurit.agmobile.agwater5.im.MyConversationFragment;
import com.augurit.agmobile.agwater5.im.keyword.MyKeywordClickUtil;
import com.augurit.agmobile.agwater5.im.keyword.MyKeywordRepository;
import com.augurit.agmobile.agwater5.im.share.MyShareClickUtil;
import com.augurit.agmobile.agwater5.login.LoginActivity;
import com.augurit.agmobile.agwater5.mine.MineFragment;
import com.augurit.agmobile.busi.common.auth.AuthDialogActivity;
import com.augurit.agmobile.busi.common.auth.AuthDownloadListener;
import com.augurit.agmobile.busi.common.auth.AuthDownloadManager;
import com.augurit.agmobile.busi.common.auth.AuthFileManager;
import com.augurit.agmobile.busi.common.auth.AuthTaskService;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.update.ApkUpdateManager;
import com.augurit.agmobile.busi.common.update.utils.CheckUpdateUtils;
import com.augurit.agmobile.busi.common.update.utils.UpdateState;
import com.augurit.agmobile.common.im.common.source.JumpUIManager;
import com.augurit.agmobile.common.im.common.source.KeywordManager;
import com.augurit.agmobile.common.im.timchat.event.MessageUnreadEvent;
import com.augurit.agmobile.common.im.timchat.manager.IMManager;
import com.augurit.agmobile.common.im.timchat.ui.AddressBookFragment;
import com.augurit.agmobile.common.im.timchat.utils.DesktopBadgeUtil;
import com.augurit.agmobile.common.lib.file.FilePathUtil;
import com.augurit.agmobile.common.lib.permission.PermissionUtil;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.view.imagepicker.ImagePicker;
import com.augurit.agmobile.common.view.navigation.NavigationTabLayout;
import com.augurit.common.common.util.GlideTokenImageLoader;

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
import static com.augurit.agmobile.common.im.timchat.ui.AddressBookFragment.MODE_ADDRESS_BOOK;

public class MainActivity extends AppCompatActivity {

    private NavigationTabLayout navigation_tab_layout;
    private ViewPager view_pager;
    private List<Fragment> mFragments;

    private MyConversationFragment mConversationFragment;

    private String[] appUpdateUrlArr = {"", ""};
    private AuthFileManager mAuthFileManager;
    private boolean toCheck = false;
    private String savePath;
    private String baseUrl = "https://yaowang4749-1258218961.cos.ap-guangzhou.myqcloud.com/";
    private String authUrl = "f1174d53-c2a6-48c8-ad41-da6017208297";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        savePath = new FilePathUtil(this).getAuthCachePath();
        if (LoginManager.isDestroyed()) {
            // 内存不足导致应用重启，若未初始化则跳转初始化
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        // IM后初始化
        IMManager.getInstance().postInit(getApplication());
        initView();
        checkVersionUpdateWithPermissionCheck();//TODO 临时注释
//        checkAuth(toCheck);//检查授权
        //设置关键字跳转逻辑工具类
        KeywordManager.getInstance().setKeywordClickUtil(new MyKeywordClickUtil());
        //设置关键字读取仓库
        KeywordManager.getInstance().setKeywordRepository(new MyKeywordRepository());
        //设置分享点击跳转逻辑工具类
        JumpUIManager.getInstance().setShareClickUtil(new MyShareClickUtil());
        //设置web跳转Activity
        JumpUIManager.getInstance().setWebSharePath("com.augurit.agmobile.agwater5.gcjs.common.webview.WebViewActivity");


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
        view_pager = findViewById(R.id.view_pager);
        mFragments = new ArrayList<>();
//        mFragments.add(mConversationFragment = new MyConversationFragment());
//        if (IMConstant.IS_TEST) {
//            ArrayList<String> testUserIds = IMConstant.getTestUserIds();
//            String[] strings = testUserIds.toArray(new String[0]);
//            mFragments.add(new AddressBookFragment.Builder()
//                    .mode(MODE_ADDRESS_BOOK)
//                    .testUsers(strings)
//                    .title(getString(R.string.menu_contacts))
//                    .build());
//        } else {
//            mFragments.add(new AddressBookFragment.Builder()
//                    .mode(MODE_ADDRESS_BOOK)
//                    .title(getString(R.string.menu_contacts))
//                    .build());
//        }
        mFragments.add(new GcjsFragment());
        mFragments.add(MineFragment.newInstance(false));

        MyPageAdapter pageAdapter = new MyPageAdapter(getSupportFragmentManager(), mFragments);
        view_pager.setAdapter(pageAdapter);
        view_pager.setOffscreenPageLimit(mFragments.size());
        navigation_tab_layout = findViewById(R.id.navigation_tab_layout);
        navigation_tab_layout.setVisibility(View.VISIBLE);
        navigation_tab_layout.setViewPager(view_pager);
        navigation_tab_layout.setOnTabChangedListner(tabNum -> {
            if (tabNum == 0 && view_pager.getCurrentItem() == tabNum) {
//                mConversationFragment.scrollToUnread();
            }
        });

//
//        NavigationTab tab_work = findViewById(R.id.tab_work);
//        NavigationTab tab_psh = findViewById(R.id.tab_psh);
//        NavigationTab tab_home = findViewById(R.id.tab_home);
//        NavigationTab tab_contact = findViewById(R.id.tab_contact);
//        NavigationTab tab_nanning = findViewById(R.id.tab_nanning);
//        NavigationTab tab_message = findViewById(R.id.tab_message);
//        NavigationTab tab_mine = findViewById(R.id.tab_mine);
//
//        ViewPager view_pager = findViewById(R.id.view_pager);
//        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(new ImFragment());
//        try {
//            //注意：key是步骤一中的meta的name属性，即“APP_PKG_KEY”
//            PackageManager pm = getPackageManager();
//            ApplicationInfo appInfo = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
//            String app_pkg_key = appInfo.metaData.getString("APP_PKG_KEY");
//
//            if ("gcjs".equals(app_pkg_key)) {
//                navigation_tab_layout.removeAllViews();
//                navigation_tab_layout.addTabView(tab_message);
//                navigation_tab_layout.addTabView(tab_home);
//                navigation_tab_layout.addTabView(tab_mine);
//                fragments.add(new GcjsFragment());
//            } else if ("agwater".equals(app_pkg_key)) {
//                fragments.add(new DrainageFragment());
//                fragments.add(new PshFragment());
//                fragments.add(new SewageFragment());
//                fragments.add(new FloodMainFragment());
//                //        fragments.add(new ShizhengFragment());
//                fragments.add(new NanNingFragment());
//
//            } else if ("gzps".equals(app_pkg_key)) {
//                navigation_tab_layout.removeAllViews();
//                navigation_tab_layout.addTabView(tab_message);
//                navigation_tab_layout.addTabView(tab_work);
//                navigation_tab_layout.addTabView(tab_mine);
//                fragments.add(new DrainageFragment());
//            } else if ("psh".equals(app_pkg_key)) {
//                navigation_tab_layout.removeAllViews();
//                navigation_tab_layout.addTabView(tab_message);
//                navigation_tab_layout.addTabView(tab_psh);
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
//                fragments.add(new NanNingFragment());
//            }
//
//            fragments.add(new MineFragment());
//
//            MyPageAdapter pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
//            view_pager.setAdapter(pageAdapter);
//            view_pager.setOffscreenPageLimit(fragments.size());
//            navigation_tab_layout.setViewPager(view_pager);
//
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

    private void checkVersionUpdateWithPermissionCheck() {
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mConversationFragment != null) {
            boolean b = mConversationFragment.onNotificationNewIntent(intent);
            if (b) {
                view_pager.setCurrentItem(0);
            }
        }
    }

    /**
     * 未读消息事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMessageUnreadEvent(MessageUnreadEvent event) {
        if (event.reset) {
            messageSum = 0;
            messageRefreshing = true;
            return;
        }
        if (messageRefreshing) {
            messageSum += event.number;
            navigation_tab_layout.getTabView(0).showNumber(messageSum);
            DesktopBadgeUtil.setBadge(this, messageSum);
            messageRefreshing = false;
        }
    }

    private int messageSum = 0;
    private boolean messageRefreshing = false;
}
