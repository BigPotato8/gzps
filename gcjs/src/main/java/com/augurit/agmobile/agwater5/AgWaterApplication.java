package com.augurit.agmobile.agwater5;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.augurit.agmobile.agwater5.common.model.AwRealmModule;
import com.augurit.agmobile.agwater5.login.LoginActivity;
import com.augurit.agmobile.busi.bpm.common.model.BusinessBPMRealmModule;
import com.augurit.agmobile.busi.common.common.model.BusinessCommonRealmModule;
import com.augurit.agmobile.busi.map.common.model.BusinessArcGISRealmModule;
import com.augurit.agmobile.busi.ui.AGMobileSDK;
import com.augurit.agmobile.common.arcgis.model.CommonArcigsRealmModule;
import com.augurit.agmobile.common.im.common.model.LibIMRealmModule;
import com.augurit.agmobile.common.lib.common.AppUtils;
import com.augurit.agmobile.common.lib.database.RealmSingleton;
import com.augurit.agmobile.common.lib.log.LogUtil;
import com.augurit.agmobile.common.lib.net.interceptor.LoggingInterceptor;
import com.augurit.agmobile.common.view.skin.SkinManager;
import com.augurit.agmobile.lib.baidumap.BaiduMapSDK;
import com.esri.android.runtime.ArcGISRuntime;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.zhouyou.http.EasyHttp;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.augurit.agmobile.busi.common.auth.CommonConstants.BACK;
import static com.augurit.agmobile.busi.common.auth.CommonConstants.FRONT;

//import com.augurit.agmobile.busi.common.auth.AuthFileManager;
//import com.augurit.agmobile.common.lib.file.FilePathUtil;

//import com.squareup.leakcanary.LeakCanary;

/**
 * @author ????????? ???xiejiexin
 * @version 1.0
 * @package ?????? ???com.augurit.agmobile.agwater5
 * @createTime ???????????? ???2018/8/17
 * @modifyBy ????????? ???xiejiexin
 * @modifyTime ???????????? ???2018/8/17
 * @modifyMemo ???????????????
 */
public class AgWaterApplication extends MultiDexApplication {

    private static final String CLIENT_ID = "1eFHW78avlnRUPHm";

    private boolean isRunInBackground;
    private int activityCount;

    @Override
    public void onCreate() {
        super.onCreate();
//        // MultiDex
        MultiDex.install(this);
//
//        // Realm
//        Realm.init(this);
//        RongIM.init(this);
//        RealmConfiguration configuration = new RealmConfiguration.Builder()
//                // ????????????????????????????????????RealmModule
//                .modules(Realm.getDefaultModule(),
//                        new AwRealmModule(),
//                        new BusinessBPMRealmModule(),
//                        new BusinessArcGISRealmModule(),
//                        new BusinessCommonRealmModule())
//                .deleteRealmIfMigrationNeeded()
//                .build();
//        // ?????????Realm??????
//        RealmSingleton.getInstance().init(configuration);
//
//        SkinManager.init(this);
//
//        ArcGISRuntime.setClientId(CLIENT_ID);
//
//        AGMobileSDK.initialize(this);
//
//        EasyHttp.getInstance().addInterceptor(new LoggingInterceptor());
//
//        Bugly.init(this, "3ae3062954", false);
// ???????????????????????????
        initAll();
        initBugly();
    }

    private void initBugly() {
        Context context = getApplicationContext();
// ??????????????????
        String packageName = context.getPackageName();
// ?????????????????????
        String processName = getProcessName(android.os.Process.myPid());
// ???????????????????????????
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
// ?????????Bugly
        CrashReport.initCrashReport(context, "fc215a5d10", true, strategy);

    }
    /**
     * ?????????????????????????????????
     *
     * @param pid ?????????
     * @return ?????????
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
    public void initAll() {
        AppUtils.init(this);
//        RongIM.init(AgWaterApplication.this);
//        RongIM.setUserInfoProvider(this, true);
//        RongIM.setGroupInfoProvider(this, true);
//        RongIM.setGroupUserInfoProvider(this, true);
        BaiduMapSDK.initialize(AgWaterApplication.this);

        initThirdService();
        initBackgroundCallBack();//??????app???????????????????????????
        closeAndroidPDialog();
    }

    private void initThirdService() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                // MultiDex
                MultiDex.install(AgWaterApplication.this);
//                LeakCanary.install(AgWaterApplication.this);

                // Realm
                Realm.init(AgWaterApplication.this);

                RealmConfiguration configuration = new RealmConfiguration.Builder()
                        // ????????????????????????????????????RealmModule
                        .modules(Realm.getDefaultModule(),
                                //new AwRealmModule(),
                                new BusinessBPMRealmModule(),
                                new BusinessArcGISRealmModule(),
                                new BusinessCommonRealmModule(),
                                new LibIMRealmModule(),
                                new CommonArcigsRealmModule())
                        .deleteRealmIfMigrationNeeded()
                        .build();


                // ?????????Realm??????
                RealmSingleton.getInstance().init(configuration);

                SkinManager.init(AgWaterApplication.this);

                ArcGISRuntime.setClientId(CLIENT_ID);

                AGMobileSDK.initialize(AgWaterApplication.this);

                EasyHttp.getInstance()
//                        .setCertificates(new Buffer()
//                        .writeUtf8(GcjsConstant.CER_KEY)
//                        .inputStream())
                        .addInterceptor(new LoggingInterceptor());

                initQbSDK();

//                Bugly.init(AgWaterApplication.this, "d4d2e2ced3", false);
            }
        }
//        .start();
        .run(); // ??????????????????????????????APP????????????????????????????????????????????????????????????
    }

    private void initQbSDK() {
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
                LogUtil.d("AgMobileApplication","onCoreInitFinished");
            }

            @Override
            public void onViewInitFinished(boolean b) {
                LogUtil.d("AgMobileApplication",b?"X5??????????????????":"X5??????????????????");
            }
        });
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
                    //??????????????????????????? ??????????????????
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
                    //?????????????????? ??????????????????
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
        if (!(activity instanceof LoginActivity)) {
            EventBus.getDefault().post(FRONT);
        }
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


    /***
     * android 9??????????????????????????????api???dialog??????????????????????????????
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
