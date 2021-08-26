package com.augurit.agmobile.agwater5.im.msg;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;

import com.augurit.agmobile.agwater5.gcjs.msg.MsgRepository;
import com.augurit.agmobile.agwater5.gcjs.msg.model.MsgBean;
import com.augurit.agmobile.common.im.timchat.model.Conversation;
import com.augurit.agmobile.common.im.timchat.model.message.Message;
import com.augurit.agmobile.common.lib.common.Callback;
import com.augurit.agmobile.common.lib.common.Callback1;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.lib.validate.StringUtil;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人消息会话 主动获取模式
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.im.personalmessage
 * @createTime 创建时间 ：2019-06-11
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class MsgConversation extends Conversation {

    private MsgRepository mRepository;
    private ArrayList<MsgBean.RowsBean> mBeans;

    public MsgConversation() {
        identify = "pub_personal_message";
        type = TIMConversationType.System;
        name = "政策法规";
        mRepository = new MsgRepository();
        mBeans = new ArrayList<>();
    }

    @Override
    public void init(Callback1<Conversation> callback) {

    }

    @Override
    public long getLastMessageTime() {
        if (!mBeans.isEmpty()) {
            return mBeans.get(0).getCreateTime() / 1000;
        }
        return 0;
    }

    @Override
    public long getUnreadNum() {
        int unreadNum = 0;
        if (ListUtil.isNotEmpty(mBeans)) {
            for (MsgBean.RowsBean bean : mBeans) {
                if (bean.getIsRead().equals("0")) {
                    unreadNum++;
                }
            }
        }
        return unreadNum;
    }

    @Override
    public void readAllMessage() {

    }

    @Override
    public String getFaceUrl() {
        return null;
    }

    @Override
    public SpannableStringBuilder getLastMessageSummary() {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        if (!mBeans.isEmpty()) {
            MsgBean.RowsBean bean = mBeans.get(0);
            if (StringUtil.isNotEmpty(bean.getCreater())) {
                sb.append(bean.getCreater()).append(": ");
            }
            if (StringUtil.isNotEmpty(bean.getContentTitle())) {
                sb.append(bean.getContentTitle());
            } else if (StringUtil.isNotEmpty(bean.getContentText())) {
                sb.append(bean.getContentText());
            } else {
                return null;
            }
        } else {
            sb.append("暂无个人消息");
        }
        return sb;
    }

    @Override
    public void setLastMessage(Message lastMessage) {

    }

    @Override
    public Message getLastMessage() {
        return null;
    }

    @Override
    public TIMConversation getConversation() {
        return null;
    }

    @Override
    public Intent getDetailIntent(Context context) {
        return MsgChatActivity.getIntent(context, identify, name);
    }

    @Override
    public void refresh(Callback callback) {
        mRepository.getMsgs(0, 100)
                .subscribe(listApiResult -> {
                    if (listApiResult.isSuccess()) {
                        List<MsgBean.RowsBean> data = listApiResult.getData();
                        mBeans.clear();
                        mBeans.addAll(data);
                        callback.onCallback();
                    }
                }, Throwable::printStackTrace);
    }

    @Override
    public boolean isCustomRefresh() {
        return true;
    }

}
