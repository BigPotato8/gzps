package com.augurit.agmobile.agwater5;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Process;
import android.support.multidex.MultiDex;

import com.augurit.agmobile.busi.bpm.common.model.BusinessBPMRealmModule;
import com.augurit.agmobile.busi.common.common.model.BusinessCommonRealmModule;
import com.augurit.agmobile.busi.map.common.model.BusinessArcGISRealmModule;
import com.augurit.agmobile.busi.ui.AGMobileSDK;
import com.augurit.agmobile.common.arcgis.model.CommonArcigsRealmModule;
import com.augurit.agmobile.common.lib.common.AppUtils;
import com.augurit.agmobile.common.lib.database.RealmSingleton;
import com.augurit.agmobile.common.view.skin.SkinManager;
import com.augurit.agmobile.lib.baidumap.BaiduMapSDK;
import com.esri.android.runtime.ArcGISRuntime;
import com.tencent.bugly.Bugly;
import com.zhouyou.http.EasyHttp;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;

import static com.augurit.agmobile.busi.common.auth.CommonConstants.BACK;
import static com.augurit.agmobile.busi.common.auth.CommonConstants.FRONT;

//import com.augurit.agmobile.busi.common.auth.AuthFileManager;
//import com.augurit.agmobile.common.lib.file.FilePathUtil;

//import com.squareup.leakcanary.LeakCanary;

/**
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5
 * @createTime 创建时间 ：2018/8/17
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/8/17
 * @modifyMemo 修改备注：
 */
public class GcjsAgWaterApplication extends Application implements RongIM.UserInfoProvider, RongIM.GroupInfoProvider, RongIM.GroupUserInfoProvider {

    private static final String CLIENT_ID = "1eFHW78avlnRUPHm";

    private boolean isRunInBackground;
    private int activityCount;
    public static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
//        // MultiDex
//        MultiDex.install(this);
//
//        // Realm
//        Realm.init(this);
//        RongIM.init(this);
//        RealmConfiguration configuration = new RealmConfiguration.Builder()
//                // 在此处添加依赖到的模块的RealmModule
//                .modules(Realm.getDefaultModule(),
//                        new GcjsAwRealmModule(),
//                        new BusinessBPMRealmModule(),
//                        new BusinessArcGISRealmModule(),
//                        new BusinessCommonRealmModule())
//                .deleteRealmIfMigrationNeeded()
//                .build();
//        // 初始化Realm单例
//        RealmSingleton.getInstance().init(configuration);
//
//        SkinManager.init(this);
//
//        ArcGISRuntime.setClientId(CLIENT_ID);
//
        AGMobileSDK.initialize(this);
//
//        EasyHttp.getInstance().addInterceptor(new LoggingInterceptor());
//
//        Bugly.init(this, "d4d2e2ced3", false);

        AppUtils.init(this);
        //RongIM.init(GcjsAgWaterApplication.this);
        EasyHttp.init(GcjsAgWaterApplication.this);
        RongIM.setUserInfoProvider(this, true);
        RongIM.setGroupInfoProvider(this, true);
        RongIM.setGroupUserInfoProvider(this, true);
        initThirdService();

        initBackgroundCallBack();//监测app进入后台和回到前台
        BaiduMapSDK.initialize(this);

        //closeAndroidPDialog();
    }

    private void initThirdService() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                // MultiDex
                MultiDex.install(GcjsAgWaterApplication.this);
//                LeakCanary.install(GcjsAgWaterApplication.this);

//                // Realm
                Realm.init(GcjsAgWaterApplication.this);
//
//
                try {
                    //TODO
                    RealmConfiguration configuration = new RealmConfiguration.Builder()
                            // 在此处添加依赖到的模块的RealmModule

                            .modules(Realm.getDefaultModule(),
                                    //new AwCommonRealmModule(),
                                    new BusinessBPMRealmModule(),
                                    new BusinessArcGISRealmModule(),
                                    new BusinessCommonRealmModule(),
                                    new CommonArcigsRealmModule())
                            .deleteRealmIfMigrationNeeded()
                            .build();
                    // 初始化Realm单例
                    RealmSingleton.getInstance().init(configuration);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                SkinManager.init(GcjsAgWaterApplication.this);

                ArcGISRuntime.setClientId(CLIENT_ID);

                //AGMobileSDK.initialize(GcjsAgWaterApplication.this);

                //Retrofit 加载大文件 假如开启LoggingInterceptor会把文件加到内存在，可能会导致oom
//                EasyHttp.getInstance().addInterceptor(new LoggingInterceptor());

                Bugly.init(GcjsAgWaterApplication.this, "d4d2e2ced3", false);
            }
        }.start();
    }

    private void initBackgroundCallBack() {

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                activityCount++;
                if (isRunInBackground) {
                    //应用从后台回到前台 需要做的操作
                    back2App(activity);
                }

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                activityCount--;
                if (activityCount == 0) {
                    //应用进入后台 需要做的操作
                    leaveApp();
                }

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

    }

    private void back2App(Activity activity) {
        isRunInBackground = false;
//        if(!(activity instanceof GcjsLoginActivity)){
        EventBus.getDefault().post(FRONT);
//        }
    }

    private void leaveApp() {
        isRunInBackground = true;
        EventBus.getDefault().post(BACK);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    @Override
    public UserInfo getUserInfo(String s) {
        if (s.equals("ps1")) {
            UserInfo userInfo = new UserInfo("ps1", "系统管理员", null);
            return userInfo;
        } else {
            UserInfo userInfo = new UserInfo("ps2", "巡查员", null);
            return userInfo;
        }

    }

    @Override
    public Group getGroupInfo(String s) {
        return new Group("g1", "我的工作组", null);
    }

    @Override
    public GroupUserInfo getGroupUserInfo(String s, String s1) {
        return null;
    }


    /***
     * android 9会弹出提示调用了隐藏api的dialog，使用该方法进行隐藏
     */
    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
