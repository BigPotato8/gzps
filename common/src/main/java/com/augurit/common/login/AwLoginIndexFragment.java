package com.augurit.common.login;

import android.view.View;

import com.augurit.agmobile.busi.ui.login.LoginIndexFragment;
import com.augurit.common.R;
import com.augurit.common.login.mock.MockLoginUtil;

public class AwLoginIndexFragment extends LoginIndexFragment {
    @Override
    protected void initView() {
        super.initView();
        View btn_mock = view.findViewById(R.id.btn_mock);
        btn_mock.setVisibility(View.VISIBLE);
        btn_mock.setOnClickListener(v->{
            MockLoginUtil.mockLogin();
            getBaseActivity().autoLogin();
        });
    }
}
