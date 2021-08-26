package com.augurit.common.login;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.augurit.agmobile.busi.common.organization.model.Organization;
import com.augurit.agmobile.busi.ui.login.adapter.OrganizationListAdapter;
import com.augurit.agmobile.common.lib.common.Callback1;
import com.augurit.common.R;

import java.util.List;

/**
 * 登录组织架构选取视图
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.login
 * @createTime 创建时间 ：2019-05-22
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class AwLoginOrganizationView {

    protected Context mContext;
    protected ViewGroup mParent;
    protected View mView;
    protected View btn_back;
    protected RecyclerView rv_list;
    protected OrganizationListAdapter mAdapter;
    protected OrganizationListAdapter.OnItemClickListener mOnItemClickListener;

    protected List<Organization> mOrganizations;
    protected Callback1<Void> mOnDismissListener;

    public AwLoginOrganizationView(Context context, ViewGroup parent, List<Organization> organizations) {
        mContext = context;
        mParent = parent;
        mOrganizations = organizations;
        initView();
    }

    protected void initView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.login_view_organization, mParent, false);
        btn_back = mView.findViewById(R.id.btn_back);
        rv_list = mView.findViewById(R.id.rv_organization);

        mAdapter = new OrganizationListAdapter(mContext, mOrganizations, "");
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
        rv_list.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((position, organization) -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(position, organization);
            }
        });

        btn_back.setOnClickListener(v -> {
            dismiss();
            if (mOnDismissListener != null) {
                mOnDismissListener.onCallback(null);
            }
        });
    }

    public void setOnItemClickListener(OrganizationListAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnDismissListener(Callback1<Void> onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    public void show() {
        mParent.addView(mView);
        mView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_view_right_in));
    }

    public void dismiss() {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anim_view_right_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mParent.removeView(mView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mView.startAnimation(animation);
    }

}
