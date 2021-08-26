package com.augurit.agmobile.agwater5.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.login.LoginMainActivity;
import com.augurit.agmobile.common.im.timchat.model.SelfInfo;
import com.augurit.agmobile.agwater5.im.IMConstant;
import com.augurit.agmobile.agwater5.login.LoginActivity;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.busi.common.organization.model.Organization;
import com.augurit.agmobile.common.im.timchat.manager.IMManager;
import com.augurit.agmobile.common.im.timchat.ui.CollectionActivity;
import com.augurit.agmobile.common.im.timchat.ui.IMSettingsActivity;
import com.augurit.agmobile.common.view.navigation.TitleBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tencent.imsdk.TIMUserProfile;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private final int REQ_SELF_PROFILE = 101;
    private TextView mMineTvVersion;
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

    private void initView() {
        View mSign = mView.findViewById(R.id.my_sign);
        View rl_collections = mView.findViewById(R.id.rl_collections);
        View rl_my_setting = mView.findViewById(R.id.rl_my_setting);
        View rl_exit = mView.findViewById(R.id.rl_exit);
        View cl_name_card = mView.findViewById(R.id.cl_name_card);
        TextView tv_username = mView.findViewById(R.id.tv_username);
        TextView tv_org = mView.findViewById(R.id.tv_org);
        iv_avatar = mView.findViewById(R.id.iv_avatar);

        mMineTvVersion = (TextView) mView.findViewById(R.id.mine_tv_version);
        mMineTvVersion.setText("V"+getAppVersionName(getActivity()));
        TitleBar titleBar = mView.findViewById(R.id.title_bar);

        boolean showBack = true;
        if (getArguments() != null) {
            showBack = getArguments().getBoolean("showBack", true);
        }
        if (showBack) {
            titleBar.setBackButtonVisible(true);
            titleBar.setBackListener(v -> {
                if (getActivity() != null) {
                    getActivity().finish();
                }
            });
        } else {
            titleBar.setBackButtonVisible(false);
        }

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
        //rl_exit.setOnClickListener(v -> logout());
        rl_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("确定退出当前账号？");

                //设置确定按钮
                builder.setNegativeButton("退出登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                });
                //设置取消按钮
                builder.setPositiveButton("取消", null);
                //显示提示框
                builder.show();
            }
        });

        cl_name_card.setOnClickListener(v -> {
            startActivityForResult(new Intent(getContext(), SelfProfileActivity.class), REQ_SELF_PROFILE);
        });

        rl_collections.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), CollectionActivity.class));
        });
        rl_my_setting.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), IMSettingsActivity.class));
        });
        mSign.setOnClickListener(view -> {
//            Intent intent = new Intent(getActivity(), SignActivity.class);
//            startActivity(intent);
        });

        if (IMConstant.IS_ENABLED) {
            TIMUserProfile profile = SelfInfo.getInstance().getProfile();
            if (profile != null) {
                String nickName = profile.getNickName();
                if (!TextUtils.isEmpty(nickName)) {
                    tv_username.setText(nickName);
                }
            }
        }
        showAvatar();
    }

    private void showAvatar() {
        String url = SelfInfo.getInstance().getFaceUrl();
        if (!TextUtils.isEmpty(url)) {
            Glide.with(this)
                    .load(url)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.avatar_person_default)
                    .into(iv_avatar);
        }
    }

    /**
     * 获取版本号
     *
     * @return 版本号
     */

    public String getAppVersionName(Context context) {

        String versionName = "";

        try {

            PackageManager pm = context.getPackageManager();

            PackageInfo p1 = pm.getPackageInfo(context.getPackageName(), 0);

            versionName = p1.versionName;

            if (TextUtils.isEmpty(versionName) || versionName.length() <= 0) {

                return "";
            }

        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();

        }

        return versionName;

    }

    private void logout() {
        LoginManager.getInstance().logoff();
        if (IMConstant.IS_ENABLED) {
            IMManager.getInstance().logout(null, null);
        }
//        User user = LoginManager.getInstance().getCurrentUser();
//        user.setActiveEndTime("fff");
//        LoginManager.getInstance().saveUser(user);
        Intent intent = new Intent(getContext(), LoginMainActivity.class);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_SELF_PROFILE) {
            showAvatar();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

}
