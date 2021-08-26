package com.augurit.agmobile.agwater5.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.busi.common.organization.model.Organization;
import com.augurit.agmobile.busi.ui.login.adapter.OrganizationListAdapter;
import com.augurit.agmobile.common.view.navigation.SlidrActivity;

import io.realm.RealmList;

/**
 * 我的单位Activity
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agmobile5.mine
 * @createTime 创建时间 ：2018/4/28
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/4/28
 * @modifyMemo 修改备注：
 */

public class MyOrgActivity extends SlidrActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_my_org);
        initView();
    }

    private void initView() {
        RecyclerView rv_org = findViewById(R.id.rv_org);

        User currentUser = LoginManager.getInstance().getCurrentUser();
        RealmList<Organization> organizations = currentUser.getOrganizations();

        LinearLayoutManager manager = new LinearLayoutManager(this);

        OrganizationListAdapter adapter = new OrganizationListAdapter(this, organizations, currentUser.getOrgId());
        rv_org.setLayoutManager(manager);
        rv_org.setAdapter(adapter);
        adapter.setOnItemClickListener((position, organization) -> {
            String selectedOrgId = adapter.getSelectedOrgId();
            for (int i = 0; i < organizations.size(); i++) {
                if (organizations.get(i) != null) {
                    if (organizations.get(i).getOrgId().equals(selectedOrgId)) {
                        User user = LoginManager.getInstance().getCurrentUser();
                        user.setOrgSelected(i);
                        LoginManager.getInstance().saveUser(user);
                        break;
                    }
                }
            }
        });

    }
}
