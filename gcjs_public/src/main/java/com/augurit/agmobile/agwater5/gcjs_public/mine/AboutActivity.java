package com.augurit.agmobile.agwater5.gcjs_public.mine;

import android.os.Bundle;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.BuildConfig;
import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.busi.common.common.constant.UrlConstant;
import com.augurit.agmobile.busi.common.update.ApkUpdateManager;
import com.augurit.agmobile.busi.common.update.utils.CheckUpdateUtils;
import com.augurit.agmobile.busi.common.update.utils.UpdateState;
import com.augurit.agmobile.common.view.navigation.SlidrActivity;
import com.augurit.agmobile.common.view.widget.WEUICell;

import java.util.Random;

/**
 * 关于Activity
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agmobile5.mine
 * @createTime 创建时间 ：2018/4/28
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/4/28
 * @modifyMemo 修改备注：
 */

public class AboutActivity extends SlidrActivity {
    private String[] appUpdateUrlArr = {UrlConstant.APP_UPDATE_ONE, UrlConstant.APP_UPDATE_TWO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_about);
        initView();
    }

    private void initView() {
        TextView tv_logo = findViewById(R.id.tv_logo);
        WEUICell cell_check_update = findViewById(R.id.cell_check_update);

        String versionText = "AGMobile " + BuildConfig.VERSION_NAME;
        tv_logo.setText(versionText);

        cell_check_update.setOnClickListener(v -> {

            checkVersionUpdateWithPermissonCheck();
//            ToastUtil.shortToast(AboutActivity.this, "已是最新版本");
        });
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
}
