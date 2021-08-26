package com.augurit.agmobile.agwater5.gcjspad.mine.message;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.model.Announce;
import com.augurit.agmobile.agwater5.gcjs.model.Message;
import com.augurit.common.common.util.TimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Date;

/**
 * 消息adapter
 */
public class MessageListAdapter extends BaseQuickAdapter<Message.RowsBean, BaseViewHolder> {

    public MessageListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Message.RowsBean item) {
        helper.setText(R.id.tv_title,item.getContentTitle());
        helper.setText(R.id.tv_send_person,item.getSendUserName());
        helper.setText(R.id.tv_time, TimeUtil.getStringTimeYMDFromDate(new Date(item.getCreateTime())));
        helper.addOnClickListener(R.id.tv_operation);
    }
}
