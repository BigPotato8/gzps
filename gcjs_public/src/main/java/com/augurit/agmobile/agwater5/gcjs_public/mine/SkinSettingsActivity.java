package com.augurit.agmobile.agwater5.gcjs_public.mine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.common.view.skin.SkinManager;

import skin.support.SkinCompatManager;

/**
 * 皮肤设置Activity
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agmobile5.mine
 * @createTime 创建时间 ：2018-06-19
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018-06-19
 * @modifyMemo 修改备注：
 */
public class SkinSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_skin_settings);
        initView();
    }

    private void initView() {
        View view_default = findViewById(R.id.view_default);
        view_default.setOnClickListener(v -> {
            loadSkin(SkinManager.SKIN_DEFAULT);
        });

        View view_green = findViewById(R.id.view_green);
        view_green.setOnClickListener(v -> {
            loadSkin(SkinManager.SKIN_GREEN);
        });

        View view_night = findViewById(R.id.view_night);
        view_night.setOnClickListener(v -> {
            loadSkin(SkinManager.SKIN_NIGHT);
        });
    }

    private void loadSkin(String name) {
        SkinManager.getInstance().loadSkin(name, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
    }

}
