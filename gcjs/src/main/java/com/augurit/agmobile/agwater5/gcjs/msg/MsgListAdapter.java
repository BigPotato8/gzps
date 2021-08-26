package com.augurit.agmobile.agwater5.gcjs.msg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.msg.model.MsgBean;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.common.lib.time.TimeUtil;

import java.util.Date;

/**
 * 事件列表Adapter
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.drainage.eventlist.view
 * @createTime 创建时间 ：2018/9/4
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class MsgListAdapter extends BaseViewListAdapter<MsgBean.RowsBean> {


    public MsgListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gcjs_announce_list_item, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof EventViewHolder) {
            MsgBean.RowsBean item = mDatas.get(position);

            EventViewHolder myHolder = (EventViewHolder) holder;
            myHolder.tv_title.setText(item.getContentTitle());
            myHolder.tv_date.setText(TimeUtil.getStringTimeYMDMChines(new Date(item.getCreateTime())));
            myHolder.iv_dot.setVisibility("0".equals(item.getIsRead()) ? View.VISIBLE : View.GONE);
            myHolder.tv_title.setTextColor("0".equals(item.getIsRead())
                    ? mContext.getResources().getColor(R.color.agmobile_black)
                    : mContext.getResources().getColor(R.color.agmobile_grey_1));
        }
    }

    public static class EventViewHolder extends BaseDataViewHolder {

        public TextView tv_title, tv_date;
        public ImageView iv_dot;


        public EventViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_date = itemView.findViewById(R.id.tv_date);
            iv_dot = itemView.findViewById(R.id.iv_dot);
        }
    }
}
