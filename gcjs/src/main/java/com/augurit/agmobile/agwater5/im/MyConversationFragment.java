package com.augurit.agmobile.agwater5.im;

import android.Manifest;
import android.content.Intent;
import android.text.TextUtils;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.EventListActivity;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventState;
import com.augurit.agmobile.agwater5.gcjs.msg.MsgListActivity;
import com.augurit.agmobile.agwater5.im.work.GcjsConversation;
import com.augurit.agmobile.common.im.common.util.LogUtil;
import com.augurit.agmobile.common.im.timchat.event.MessageUnreadEvent;
import com.augurit.agmobile.common.im.timchat.manager.ConversationManager;
import com.augurit.agmobile.common.im.timchat.manager.GroupManager;
import com.augurit.agmobile.common.im.timchat.model.C2CConversation;
import com.augurit.agmobile.common.im.timchat.model.Conversation;
import com.augurit.agmobile.common.im.timchat.model.GroupConversation;
import com.augurit.agmobile.common.im.timchat.model.GroupInfo;
import com.augurit.agmobile.common.im.timchat.model.HeaderItem;
import com.augurit.agmobile.common.im.timchat.model.message.CustomMessage;
import com.augurit.agmobile.common.im.timchat.model.message.GroupTipMessage;
import com.augurit.agmobile.common.im.timchat.model.message.MessageFactory;
import com.augurit.agmobile.common.im.timchat.ui.AddMemberActivity;
import com.augurit.agmobile.common.im.timchat.ui.ConversationFragment;
import com.augurit.agmobile.common.im.timchat.ui.customview.MenuPopup;
import com.augurit.agmobile.common.lib.permission.PermissionUtil;
import com.augurit.agmobile.common.lib.ui.DisplayUtils;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.navigation.menu.MenuItem;
import com.augurit.agmobile.lib.qrcode.QrCoder;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 会话列表Fragment
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.im
 * @createTime 创建时间 ：2019-05-16
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class MyConversationFragment extends ConversationFragment {

    private static final String TAG = "MyConversationFragment";
    public static final int REQ_SCAN = 123;
    public volatile long mUnreadNum;

    @Override
    protected void initArguments() {
        super.initArguments();
        titleText = getString(R.string.menu_im);
    }

    @Override
    public List<Conversation> initCustomConversations() {
        ArrayList<Conversation> conversations = new ArrayList<>();
        conversations.add(new GcjsConversation(getContext()));
        //conversations.add(new AnnounceConversation());//通知公告
        //conversations.add(new MsgConversation());//个人消息
        return conversations;
    }

    @Override
    protected void initActions() {
        ArrayList<MenuItem> menuList = new ArrayList<>();
        menuList.add(new MenuItem(R.drawable.ic_create_group, getString(R.string.title_create_group), false));
        menuList.add(new MenuItem(R.mipmap.ic_qr_scan, getString(R.string.menu_scan), false));

        mMenuPopup = new MenuPopup(getContext(), menuList, MenuPopup.STYLE_DROPDOWN);
        mMenuPopup.setOnItemClickListener(position -> {
            if (position == 0) {
                Intent intent = AddMemberActivity.newIntent(getContext());
                startActivityForResult(intent, REQ_CREATE_GROUP);
            } else if (position == 1) {
                PermissionUtil.instance()
                        .with(this)
                        .permissions(Manifest.permission.CAMERA)
                        .check(permissions -> QrCoder.openScan(getActivity(), REQ_SCAN));
            }
        });
        int x = -DisplayUtils.dip2px(getContext(), 52);
        btn_more.setOnClickListener(v -> mMenuPopup.showAsDropDown(iv_more, x, 0));
    }

    @Override
    public void updateMessage(TIMMessage message) {
        if (message == null) {
            adapter.notifyDataSetChanged();
            return;
        }
        if (message.getConversation().getType() == TIMConversationType.System) {
            groupManagerPresenter.getGroupManageLastMessage();
            return;
        }
        if (!CustomMessage.shouldShow(message)) return;
        if (!GroupTipMessage.shouldShow(message)) return;

        Conversation conversation = null;
        if (message.getConversation().getType() == TIMConversationType.Group) {
            String peer = message.getConversation().getPeer();
            String groupName = GroupInfo.getInstance().getGroupName(peer);
            if (groupName.isEmpty()) {
                // 更新缓存，取群组
                GroupInfo.getInstance().refresh();
                groupName = GroupInfo.getInstance().getGroupName(peer);
                if (groupName.isEmpty()) {
                    // 从网络获取
                    GroupManager.getGroupDetailInfo(Collections.singletonList(peer), timGroupDetailInfos -> {
                        if (ListUtil.isEmpty(timGroupDetailInfos)) return;
                        // 添加到会话列表
                        GroupConversation groupConversation = new GroupConversation(message.getConversation());
                        groupConversation.setGroupDetailInfo(timGroupDetailInfos.get(0));
                        groupConversation.setLastMessage(MessageFactory.getMessage(message));
                        GroupInfo.getInstance().updateGroup(timGroupDetailInfos.get(0));
                        if (!mConversationList.contains(groupConversation)) {   // 避免获取过程中群组更新，导致会话重复
                            mConversationList.add(groupConversation);
                        }
                        refresh();
                    }, integer -> {
                        LogUtil.e(TAG, "获取群组详细资料失败, code: " + integer);
                    });
                    return;
                }
            }
            conversation = new GroupConversation(message.getConversation());
        } else if (message.getConversation().getType() == TIMConversationType.C2C) {
            conversation = new C2CConversation(message.getConversation());
        }
        if (conversation == null) return;

        Iterator<Conversation> iterator = mConversationList.iterator();
        while (iterator.hasNext()) {
            Conversation c = iterator.next();
            if (conversation.equals(c)) {
                conversation = c;
                iterator.remove();
                break;
            }
        }
        conversation.setLastMessage(MessageFactory.getMessage(message));
        mConversationList.add(conversation);
        ConversationManager.getInstance().fillMetas(mConversationList);
        doRefresh(0);
    }

    @Override
    public void refresh() {
        ConversationManager.getInstance().fillMetas(mConversationList);
        doRefresh(0);
        // 需要自定义刷新的会话 目前为 个人消息、通知公告
        for (Conversation conversation : mConversationList) {
            if (conversation.isCustomRefresh()) {
                conversation.refresh(() -> {
                    if (conversation.getUnreadNum() < 1) {
                        Collections.sort(mConversationList);
                        adapter.notifyDataSetChanged();
                        return;
                    }
                    doRefresh(conversation.getUnreadNum());
                });
            }
        }
    }

    private void doRefresh(long unreadNumToAdd) {
        Collections.sort(mConversationList);
        mUnreadNum = adapter.refresh() + unreadNumToAdd;
        // 通知界面变化
        EventBus.getDefault().post(new MessageUnreadEvent(true));
        EventBus.getDefault().post(new MessageUnreadEvent(mUnreadNum));
        // 20190520 现在未读消息数量通过同步方法获取，直接变更标题显示
        if (mUnreadNum > -1 && !TextUtils.isEmpty(titleText)) {
            String title;
            if (mUnreadNum > 0) {
                title = titleText + "(" + mUnreadNum + ")";
            } else {
                title = titleText;
            }
            tv_title.setText(title);
        }
    }

    /**
     * 重写小程序下拉
     */
    @Override
    protected void initAppsHeader() {
        mAppsHeader = view.findViewById(com.augurit.agmobile.common.im.R.id.apps_header);
        mAppsHeader.setRefreshLayout(refresh_layout);
        ArrayList<HeaderItem> itemList = new ArrayList<>();
        itemList.add(new HeaderItem("待办事项", com.augurit.agmobile.common.im.R.drawable.home_top_wait, EventListActivity.class)
                .addParam(EventListActivity.EXTRA_EVENT_STATE, EventState.HANDLING)
                .addParam(EventListActivity.EXTRA_TITLE, "待办事项"));
        itemList.add(new HeaderItem("已办事项", com.augurit.agmobile.common.im.R.drawable.home_top_finished, EventListActivity.class)
                .addParam(EventListActivity.EXTRA_EVENT_STATE, EventState.HANDLED)
                .addParam(EventListActivity.EXTRA_TITLE, "已办事项"));
        itemList.add(new HeaderItem("督办事项", com.augurit.agmobile.common.im.R.drawable.home_top_commited, EventListActivity.class)
                .addParam(EventListActivity.EXTRA_EVENT_STATE, EventState.FINISHED)
                .addParam(EventListActivity.EXTRA_TITLE, "督办事项"));
        itemList.add(new HeaderItem("个人消息", com.augurit.agmobile.common.im.R.drawable.home_top_ing, MsgListActivity.class)
                .addParam(EventListActivity.EXTRA_EVENT_STATE, EventState.UPLOADED)
                .addParam(EventListActivity.EXTRA_TITLE, "个人消息"));
        //配置功能模块
        mAppsHeader.setHeaderItems(itemList);
        //开启下拉菜单功能
        refresh_layout.setEnableRefresh(true);

    }
}
