package com.augurit.agmobile.agwater5.im.work;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;

import com.augurit.agmobile.agwater5.im.work.model.PubItem;
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
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.im
 * @createTime 创建时间 ：2019-08-28
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class GcjsConversation extends Conversation {

    private SubscriptionRepository mRepository;
    private Context mContext;
    private ArrayList<PubItem> mBeans;

    public GcjsConversation(Context context) {
        mContext = context;
        identify = "pub_gcjs";
        type = TIMConversationType.System;
        mRepository = new SubscriptionRepository();
        name = "工作消息";
        mBeans = new ArrayList<>();
    }

    @Override
    public void init(Callback1<Conversation> callback) {

    }

    @Override
    public long getLastMessageTime() {
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
            PubItem bean = mBeans.get(0);
            if (StringUtil.isNotEmpty(bean.getName())) {
                sb.append(bean.getName());
            } else {
                return null;
            }
        } else {
            sb.append("暂无消息");
        }
        return sb;
    }

    @Override
    public void setLastMessage(Message lastMessage) {

    }

    @Override
    public Intent getDetailIntent(Context context) {
        return GcjsActivity.getIntent(context, identify, name);
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
    public void refresh(Callback callback) {
        mRepository.getSubscriptions(mContext, 0, 10)
                .subscribe(listApiResult -> {
                    if (listApiResult.isSuccess()) {
                        List<PubItem> data = listApiResult.getData();
                        if (ListUtil.isNotEmpty(data)) {
                            mBeans.clear();
                            mBeans.addAll(data);
                            callback.onCallback();
                        }
                    }
                }, throwable -> {

                });
    }

    @Override
    public boolean isCustomRefresh() {
        return true;
    }
}
