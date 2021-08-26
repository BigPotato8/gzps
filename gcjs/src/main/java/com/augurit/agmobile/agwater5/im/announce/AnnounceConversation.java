package com.augurit.agmobile.agwater5.im.announce;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;

import com.augurit.agmobile.agwater5.gcjs.announce.AnnounceRepository;
import com.augurit.agmobile.agwater5.gcjs.model.Announce;
import com.augurit.agmobile.common.im.timchat.model.Conversation;
import com.augurit.agmobile.common.im.timchat.model.message.Message;
import com.augurit.agmobile.common.lib.common.Callback;
import com.augurit.agmobile.common.lib.common.Callback1;
import com.augurit.agmobile.common.lib.validate.StringUtil;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;

import java.util.ArrayList;
import java.util.List;

/**
 * 通知公告会话 主动获取模式
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.im
 * @createTime 创建时间 ：2019-06-10
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class AnnounceConversation extends Conversation {

    private AnnounceRepository mRepository;
    private ArrayList<Announce.RowsBean> mBeans;

    public AnnounceConversation() {
        identify = "pub_announce";
        type = TIMConversationType.System;
        name = "通知公告";
        mRepository = new AnnounceRepository();
        mBeans = new ArrayList<>();
    }

    @Override
    public void init(Callback1<Conversation> callback) {

    }

    @Override
    public long getLastMessageTime() {
        if (!mBeans.isEmpty()) {
            return mBeans.get(0).getPublishTime() / 1000;
        }
        return 0;
    }

    @Override
    public long getUnreadNum() {
        return 0;
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
            Announce.RowsBean bean = mBeans.get(0);
            if (StringUtil.isNotEmpty(bean.getContentAuthor())) {
                sb.append(bean.getContentAuthor()).append(": ");
            }
            if (StringUtil.isNotEmpty(bean.getContentTitle())) {
                sb.append(bean.getContentTitle());
            } else if (StringUtil.isNotEmpty(bean.getContentText())) {
                sb.append(bean.getContentText());
            } else {
                return null;
            }
        } else {
            sb.append("暂无公告");
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
        return AnnounceChatActivity.getIntent(context, identify, name);
    }

    @Override
    public void refresh(Callback callback) {
        mRepository.getAnnounces(0, 1)
                .subscribe(listApiResult -> {
                    if (listApiResult.isSuccess()) {
                        List<Announce.RowsBean> data = listApiResult.getData();
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
