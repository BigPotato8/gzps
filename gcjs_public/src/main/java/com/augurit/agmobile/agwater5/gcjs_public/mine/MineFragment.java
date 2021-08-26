package com.augurit.agmobile.agwater5.gcjs_public.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.GcjsVisitorLoginActivity;
import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs_public.login.LoginActivity;
import com.augurit.agmobile.agwater5.gcjs_public.login.LoginConstant;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.busi.common.organization.model.Organization;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.view.navigation.TitleBar;
import com.jakewharton.rxbinding2.view.RxView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import io.realm.RealmList;

import static com.augurit.agmobile.busi.common.auth.CommonConstants.LOGOUT;

/**
 * 我的功能界面
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agmobile5.mine
 * @createTime 创建时间 ：2018/4/24
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/4/24
 * @modifyMemo 修改备注：
 */

public class MineFragment extends Fragment {

    private View mView;
    private ImageView iv_avatar;
    private TextView tv_username;
    private TextView tv_org;
    private View rl_exit;
    private View cl_name_card;
    private View rl_my_setting;
    private View mSign;

    public static MineFragment newInstance(boolean showBack) {
        Bundle args = new Bundle();
        args.putBoolean("showBack", showBack);
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.mine_fragment, null);
        initView();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initUser();
    }

    private void initView() {
        mSign = mView.findViewById(R.id.my_sign);
        rl_my_setting = mView.findViewById(R.id.rl_my_setting);
        rl_exit = mView.findViewById(R.id.rl_exit);
        cl_name_card = mView.findViewById(R.id.cl_name_card);
        tv_username = mView.findViewById(R.id.tv_username);
        tv_org = mView.findViewById(R.id.tv_org);
        iv_avatar = mView.findViewById(R.id.iv_avatar);

        TitleBar titleBar = mView.findViewById(R.id.title_bar);
        titleBar.setBackListener(v -> {
            if (getActivity() != null) {
                getActivity().finish();
            }
        });
        RxView.clicks(rl_exit)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(o -> {
                    if (LoginConstant.IS_VISITOR_PATTERN) {//有游客模式的退出
                        visitorLogout();
                    } else {//直接退出到登录页面
                        logout();
                    }
                });


    }

    private void initUser() {
        User user = LoginManager.getInstance().getCurrentUser();
        if (user != null && !TextUtils.isEmpty(user.getLoginPwd())) {//已登录
            String org = "";
            RealmList<Organization> organizations = user.getOrganizations();
            if (organizations != null && !organizations.isEmpty()) {
                Organization organization;
                if (organizations.size() < 2) {
                    organization = organizations.get(0);
                } else {
                    organization = organizations.get(user.getOrgSelected());
                }
                if (organization != null) {
                    org = organization.getOrgName();
                }
            }

            tv_username.setText(user.getUserName());
            tv_org.setText(org);

        } else {//未登录
            tv_username.setText("游客模式，点击登录");
            tv_org.setText("");
        }
        cl_name_card.setOnClickListener(v -> {//点击个人card，判断是否需要登录，是则跳转到登录界面
            if (LoginManager.getInstance().getCurrentUser() == null
                    || TextUtils.isEmpty(LoginManager.getInstance().getCurrentUser().getLoginPwd())) {
                Intent intent = new Intent(getActivity(), GcjsVisitorLoginActivity.class);
                getActivity().startActivityForResult(intent, GcjsVisitorLoginActivity.LOGIN_REQUEST);
            }
//            startActivity(new Intent(getContext(), SelfProfileActivity.class));
        });

        rl_my_setting.setOnClickListener(v -> {
//            startActivity(new Intent(getContext(), SettingsActivity.class));
        });
        mSign.setOnClickListener(view -> {
//            Intent intent = new Intent(getActivity(), SignActivity.class);
//            startActivity(intent);
        });
    }

    private void logout() {
        LoginManager.getInstance().logoff();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveLogout(String str) {
        if (LOGOUT.equals(str)) {
            logout();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //判断已登录，进行ui更新
        if (resultCode == GcjsVisitorLoginActivity.LOGIN_OK) {
            User user = LoginManager.getInstance().getCurrentUser();
            String org = "";
            RealmList<Organization> organizations = user.getOrganizations();
            if (organizations != null && !organizations.isEmpty()) {
                Organization organization;
                if (organizations.size() < 2) {
                    organization = organizations.get(0);
                } else {
                    organization = organizations.get(user.getOrgSelected());
                }
                if (organization != null) {
                    org = organization.getOrgName();
                }
            }

            tv_username.setText(user.getUserName());
            tv_org.setText(org);

        }
    }

    private void visitorLogout() {
        User currentUser = LoginManager.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUser.setLoginPwd("");
            LoginManager.getInstance().saveUser(currentUser);
        }

//        LoginSettings settings = LoginManager.getInstance().getSettings();
//        if (settings != null) {
//            settings.setAutoLogin(false);
//            LoginManager.getInstance().saveSettings(settings);
//        }
        tv_username.setText("游客模式，点击登录");
        tv_org.setText("");
        LoginManager.getInstance().logoff();
        ToastUtil.shortToast(getActivity(), "退出成功");
    }

}
