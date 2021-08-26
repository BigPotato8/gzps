package com.augurit.common.login.mock;

import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.LoginSettings;
import com.augurit.agmobile.busi.common.login.source.ILoginDataSource;

public class MockLoginUtil {
    /**
     * 给定用户信息模拟登录
     */
    public static void mockLogin() {
        ILoginDataSource iLoginDataSource = new FakeLoginDataSource();
        iLoginDataSource.saveLoginSettings(new LoginSettings("",
                "", false));
        LoginManager.getInstance().setDataSource(iLoginDataSource);
        LoginManager.getInstance().autoLogin();
    }
}
