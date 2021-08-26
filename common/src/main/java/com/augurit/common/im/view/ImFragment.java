package com.augurit.common.im.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.common.R;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

/**
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.im
 * @createTime 创建时间 ：2018/8/21
 * @modifyBy 修改人 ：taoerxiang
 * @modifyTime 修改时间 ：2018/9/6
 * @modifyMemo 修改备注：
 */
public class ImFragment extends Fragment {
    private ConversationListFragment mConversationListFragment = null;
    private String token = "Lbg3e2ikuPJ5QelsH71sLde5YldyjbA06Pv4GJOicupHyF9NeIrHhsK1uQXcO9CweTDN8dtqd+wl/XSJxhkImQ==";//系统管理员
    private String token2 = "Gbgjem0QoNqQaZ7uXKDU7de5YldyjbA06Pv4GJOicuoixnwaEQdP8qtVXY/SODpd2Uskth3s7M/n2pQhA3/zVw==";//巡查员

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.im_fragment_main, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initTab(view);
        User user = LoginManager.getInstance().getCurrentUser();
        if (user.getLoginName().equals("ps_admin")) {
            connect(token);
        } else {
            connect(token2);
        }

    }

    private void initTab(View view) {
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        List<Fragment> fragments = new ArrayList<>();
        Fragment conversationList = initConversationList();
        fragments.add(conversationList);
        fragments.add(new FriendListFragment());
        fragments.add(new WorkGroupFragment());
        String[] titles = new String[]{
                getString(R.string.conversation),
                getString(R.string.friend_list),
                getString(R.string.work_list)
        };
        MyPageAdapter pageAdapter = new MyPageAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setAdapter(pageAdapter);
        viewPager.setOffscreenPageLimit(titles.length);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void connect(String token) {
        if (getActivity().getApplicationInfo().packageName.equals(getCurProcessName(getActivity()))) {
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                @Override
                public void onTokenIncorrect() {
                    //Toast.makeText(getActivity(), "连接融云失败--", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(String userId) {
                    //Toast.makeText(getActivity(), "IM连接成功--" + userId, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    //Toast.makeText(getActivity(), "IM连接失败--" + errorCode.getValue(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private Fragment initConversationList() {
        if (mConversationListFragment == null) {
            ConversationListFragment listFragment = new ConversationListFragment();
            Uri uri;
            uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")//群组
                    .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")//公共服务号
                    .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "false")//订阅号
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//系统
                    .build();
            listFragment.setUri(uri);
            mConversationListFragment = listFragment;
            return listFragment;
        } else {
            return mConversationListFragment;
        }
    }

    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;
        private String[] mTitles;

        private MyPageAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
