package com.augurit.common.im.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.common.R;
import com.augurit.common.im.model.FriendItem;
import com.augurit.common.im.model.GroupItem;
import com.augurit.common.im.view.adapter.MyGroupListAdapter;

import java.util.List;

import io.rong.imkit.RongIM;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.im.view
 * @createTime 创建时间 ：2018-09-06
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class WorkGroupFragment extends Fragment implements IMContract.View {

    private RecyclerView mRecycleView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workgroup, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecycleView = view.findViewById(R.id.group_rv);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        IMPresenter mPresenter = new IMPresenter(this);
        User currentUser = LoginManager.getInstance().getCurrentUser();
        String loginName = "1";
        if (currentUser != null) {
            loginName = currentUser.getLoginName();
        }
        mPresenter.loadGroups(loginName);
    }

    @Override
    public void showGroupList(List<GroupItem> groupList) {
        MyGroupListAdapter adapter = new MyGroupListAdapter(getActivity(), groupList);
        mRecycleView.setAdapter(adapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnItemClickListener(groupItem -> RongIM.getInstance().startGroupChat(getActivity(), groupItem.getId(), groupItem.getName()));
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void showFriendList(List<FriendItem> friendList) {

    }
}
