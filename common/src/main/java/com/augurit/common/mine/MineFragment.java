package com.augurit.common.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.busi.common.organization.model.Organization;
import com.augurit.agmobile.common.lib.sp.SharedPreferencesUtil;
import com.augurit.common.R;
import com.augurit.common.mine.sign.view.SignActivity;

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
    private SharedPreferencesUtil mPreferencesUtil;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.mine_fragment, null);
        mPreferencesUtil = new SharedPreferencesUtil(mContext);
        initView();
        return mView;
    }

    private void initView() {
        View mSign = mView.findViewById(R.id.my_sign);
        View rl_my_setting = mView.findViewById(R.id.rl_my_setting);
        View rl_exit = mView.findViewById(R.id.rl_exit);
        View cl_name_card = mView.findViewById(R.id.cl_name_card);
        TextView tv_username = mView.findViewById(R.id.tv_username);
        TextView tv_org = mView.findViewById(R.id.tv_org);
        iv_avatar = mView.findViewById(R.id.iv_avatar);

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
        rl_exit.setOnClickListener(v -> logout());

        cl_name_card.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SelfProfileActivity.class));
        });

        rl_my_setting.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SettingsActivity.class));
        });
        mSign.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), SignActivity.class);
            startActivity(intent);
        });
    }

    public void logout() {
        mPreferencesUtil.setString("fdAuthentication","");
        LoginManager.getInstance().logoff();
        Intent intent = new Intent();
        intent.setAction(getActivity().getPackageName()+".LoginActivity");
//        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            this.mContext = context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity != null) {
            this.mContext = activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }

}
