package com.augurit.agmobile.agwater5.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.common.view.widget.WEUICell;

/**
 * 设置Activity
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agmobile5.mine
 * @createTime 创建时间 ：2018/4/26
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/4/26
 * @modifyMemo 修改备注：
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_settings);
        initView();
    }

    private void initView() {
        WEUICell cell_skin = findViewById(R.id.cell_skin);
        WEUICell cell_my_org = findViewById(R.id.cell_my_org);
        WEUICell cell_about = findViewById(R.id.cell_about);

        cell_skin.setOnClickListener(v -> {
            startActivity(new Intent(SettingsActivity.this, SkinSettingsActivity.class));
        });

        cell_my_org.setOnClickListener(v -> {
            startActivity(new Intent(SettingsActivity.this, MyOrgActivity.class));
        });

        cell_about.setOnClickListener(v -> {
            startActivity(new Intent(SettingsActivity.this, AboutActivity.class));
        });

    }
}
