package com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventProcess;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.common.view.imagepicker.view.BGASortableNinePhotoLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import skin.support.content.res.SkinCompatResources;

/**
 * 上报事件列表Adapter
 */
public class AdviceListAdapter extends BaseViewListAdapter<EventProcess> {

    private final Drawable mTimePointSelectedDone;
    private boolean mIsFinished = false;
    protected Calendar mCalendar;
    protected Drawable mTitleNormal;
    protected Drawable mTitleSelected;
    protected Drawable mTimeLineNormal;
    protected Drawable mTimeLineSelected;
    protected Drawable mTimePointNormal;
    protected Drawable mTimePointSelected;

    public AdviceListAdapter(Context context) {
        super(context);

        mCalendar = Calendar.getInstance();
        mTitleNormal = SkinCompatResources.getDrawable(context, R.drawable.widget_bg_timeline_title_normal);
        mTitleSelected = SkinCompatResources.getDrawable(context, R.drawable.widget_bg_timeline_title_selected);
        mTimeLineNormal = SkinCompatResources.getDrawable(context, R.drawable.widget_bg_timeline_line_normal);
        mTimeLineSelected = SkinCompatResources.getDrawable(context, R.drawable.widget_bg_timeline_line_selected);
        mTimePointNormal = SkinCompatResources.getDrawable(context, R.drawable.widget_bg_timeline_point_normal);
        mTimePointSelected = SkinCompatResources.getDrawable(context, R.drawable.widget_bg_timeline_point_selected);
        mTimePointSelectedDone = SkinCompatResources.getDrawable(context, R.drawable.widget_bg_timeline_point_green);
    }


    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gcjs_event_listitem_process_copy, parent, false);
        return new AdviceViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(BaseDataViewHolder baseDataViewHolder, int position) {
        super.onBindViewHolder(baseDataViewHolder, position);
        AdviceViewHolder holder = (AdviceViewHolder) baseDataViewHolder;
//        holder.setIsRecyclable(false);
        EventProcess data = mDatas.get(position);
        // 时间点、线样式
//        if (position == 0 && !mIsFinished) {
//        holder.view_time_line_bottom.setVisibility(View.VISIBLE);
//        holder.view_time_point.setBackground(data.getTaskState() == 2 ? mTimePointSelectedDone : mTimePointSelected);
        holder.tv_process_node_bg.setBackgroundResource(data.getTaskState() == 2 ? R.drawable.bg_process_btn_green : R.drawable.bg_process_btn_blue);
//        holder.tv_process_node_bg.setWidth(data.getNodeLevel() == 0 ? 38 : 30);
//        holder.tv_process_node_bg.setHeight(data.getNodeLevel() == 0 ? 38 : 30);
        //TODO:颜色切换完成，还差一级二级的大小切换
//        holder.mTvProcessTitle.setBackground(mTitleSelected);
//        if (position == 0) {
//            holder.view_time_line_top.setVisibility(View.GONE);
//        } else {
//            holder.view_time_line_top.setVisibility(View.VISIBLE);
//        }
        if (data.getNodeLevel() == 1) {
            holder.mTvProcessTitle.setTextSize(14);
            if("1".equals(data.getIsItemNode())){
                // 事项节点
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.fl_icon.getLayoutParams();
                layoutParams.leftMargin = 27;
                holder.tv_process_item.setVisibility(View.VISIBLE);
                holder.tv_process_node_bg.setVisibility(View.GONE);
                holder.tv_process_node_bg_two.setVisibility(View.GONE );
                holder.tv_process_node_bg_three.setVisibility(View.VISIBLE);
                holder.tv_process_node_bg_three.setBackgroundResource(data.getTaskState() == 2 ? R.drawable.bg_process_btn_green : R.drawable.bg_process_btn_blue);
                holder.icon_node_white.setVisibility(View.GONE);
            }else {
                /**
                 * 设置二级节点的node
                 */
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.fl_icon.getLayoutParams();
                layoutParams.leftMargin = 12;
                holder.fl_icon.setLayoutParams(layoutParams);
                holder.tv_process_node_bg.setVisibility(View.GONE);
                holder.tv_process_node_bg_two.setVisibility(View.VISIBLE );
                holder.tv_process_node_bg_two.setBackgroundResource(data.getTaskState() == 2 ? R.drawable.bg_process_btn_green : R.drawable.bg_process_btn_blue);
            }
        }

        String linkName = data.getNodeName();
        String state = data.getTaskState() == 2 ? "已完成" : "处理中";
        holder.tv_state.setTextColor(data.getTaskState() == 2 ? 0xff008000 : 0xff36a3f7);//绿色处理中，蓝色已完成
//        holder.mTvProcessTitle.setBackgroundResource(data.getTaskState() == 2 ? R.drawable.widget_bg_timeline_title_selected_green : R.drawable.widget_bg_timeline_title_selected);
        setTextAndVisibility(holder.tv_state, state);
        if (data.getProcessNewModel() != null) {
            if (!"".equals(data.getProcessNewModel().getTitle())) {
                StringBuilder builder = new StringBuilder();
                StringBuilder builder1 = new StringBuilder();
                List<EventProcess> process = data.getProcessNewModel().getEventProcesses();
                if (process != null) {
                    for (int i = 0; i < process.size(); i++) {
                        if (process.get(i).getTaskState() == 1) {
                            builder1.append(process.get(i).getTaskAssignee());
                            continue;
                        }
                        if (i == process.size() - 1) {
                            builder.append(process.get(i).getTaskAssignee());
                        } else {
                            builder.append(process.get(i).getTaskAssignee() + "，");
                        }
                    }
                    if ("".contentEquals(builder1)) {
                        holder.tv_username.setText(builder);
                    } else {
                        holder.tv_username.setText(Html.fromHtml("<b><tt>" + builder1 + "</tt></b>" + "，" + builder));
                    }
                    holder.mTvProcessTitle.setText(data.getProcessNewModel().getTitle());
                    if (data.getChildNode() != null) {
                        if (data.getChildNode().size() != 0) {
                            dealData(holder, data);
                        }
                    }
                    handleTimeAndDate(holder,data);
                }
            }
        } else {
            setTextAndVisibility(holder.tv_material, null);//事项
            setTextAndVisibility(holder.mTvProcessTitle, linkName);//节点名称
            setTextAndVisibility(holder.tv_username, data.getTaskAssignee());
            if (data.getChildNode() != null && data.getChildNode().size() != 0) {
                dealData(holder, data);
            } else {
                handleTimeAndDate(holder,data);
                if(data.getBzNum() != null){
                    if(Integer.parseInt(data.getBzNum()) > 0){
                        holder.mTvProcessRepair.setVisibility(View.VISIBLE);
                    }
                }
                if (data.getCommentMessage() != null) {
                    holder.mRlResponse.setVisibility(View.VISIBLE);
                    holder.mTvResponse.setText(data.getCommentMessage());
                } else {
                    if (data.getNodeLevel() == 1) {
                        holder.mRlResponse.setVisibility(View.VISIBLE);
                        holder.mTvResponse.setText("暂无意见");
                    }
                }
            }

        }

//        holder.ll_next_info.setVisibility(View.GONE);

    }

    private void handleTimeAndDate(AdviceViewHolder holder, EventProcess data) {
        try {
            long time = 0;
            if (data.getTaskState() == 2) {
                if(data.getEndDate() != null){
                    time = Long.parseLong(data.getEndDate());
                }
            } else {
                if(data.getEndDate() != null){
                    time = Long.parseLong(data.getSigeInDate());
                }
            }
            mCalendar.setTimeInMillis(System.currentTimeMillis());
            int thisYear = mCalendar.get(Calendar.YEAR);
            mCalendar.setTimeInMillis(time);
            int year = mCalendar.get(Calendar.YEAR);

            SimpleDateFormat formatDate = new SimpleDateFormat(year == thisYear ? "MM-dd" : "yyyy-MM-dd");
            SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
            holder.tv_date.setText(formatDate.format(time));
            holder.tv_time.setText(formatTime.format(time));
            if (data.getTaskState() == 2) {
                holder.tv_date.setVisibility(View.VISIBLE);
                holder.tv_time.setVisibility(View.VISIBLE);
            } else {
                holder.tv_date.setVisibility(View.GONE);
                holder.tv_time.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void dealData(AdviceViewHolder holder, EventProcess data) {
        //先取最后一条数据 如果是taskState=2就取，不然就取前一条数据
        boolean isFindTwo = false;
        boolean isFindOne = false;
        if (data.getNodeLevel() == 1) {
            for (int i = data.getChildNode().size() - 1; i >= 0; i--) {
                EventProcess process = data.getChildNode().get(i);
                if (process.getTaskState() == 2) {
                    isFindTwo = true;
                    isFindOne = true;
                    String message = process.getCommentMessage();
                    if(process.getBzNum() != null){
                        if(Integer.parseInt(process.getBzNum()) > 0){
                            holder.mTvProcessRepair.setVisibility(View.VISIBLE);
                        }
                    }
                    handleTimeAndDate(holder,process);
                    if (message != null) {
                        holder.mRlResponse.setVisibility(View.VISIBLE);
                        holder.mTvResponse.setText(message);
                    } else {
                        if (data.getNodeLevel() == 1) {
                            holder.mRlResponse.setVisibility(View.VISIBLE);
                            holder.mTvResponse.setText("暂无意见");
                        }
                    }
                    setTextAndVisibility(holder.tv_username, process.getTaskAssignee());
                    break;
                }
            }
            if (!isFindTwo) {
                for (int i = 0; i < data.getChildNode().size(); i++) {
                    EventProcess process = data.getChildNode().get(i);
                    if (process.getTaskState() == 1) {
                        isFindOne = true;
                        if(process.getBzNum() != null){
                            if(Integer.parseInt(process.getBzNum()) > 0){
                                holder.mTvProcessRepair.setVisibility(View.VISIBLE);
                            }
                        }
                        String message = process.getCommentMessage();
                        handleTimeAndDate(holder,process);
                        if (message != null) {
                            holder.mRlResponse.setVisibility(View.VISIBLE);
                            holder.mTvResponse.setText(message);
                        } else {
                            if (data.getNodeLevel() == 1) {
                                holder.mRlResponse.setVisibility(View.VISIBLE);
                                holder.mTvResponse.setText("暂无意见");
                            }
                        }
                        setTextAndVisibility(holder.tv_username, process.getTaskAssignee());
                        break;
                    }
                }

            }
            if (!isFindOne) {
                for (int i = 0; i < data.getChildNode().size(); i++) {
                    EventProcess process = data.getChildNode().get(i);
                    if (process.getTaskState() == 0) {
                        String message = process.getCommentMessage();
                        handleTimeAndDate(holder,process);
                        if(process.getBzNum() != null){
                            if(Integer.parseInt(process.getBzNum()) > 0){
                                holder.mTvProcessRepair.setVisibility(View.VISIBLE);
                            }
                        }
                        if (message != null) {
                            holder.mRlResponse.setVisibility(View.VISIBLE);
                            holder.mTvResponse.setText(message);
                        } else {
                            if (data.getNodeLevel() == 1) {
                                holder.mRlResponse.setVisibility(View.VISIBLE);
                                holder.mTvResponse.setText("暂无意见");
                            }
                        }
                        setTextAndVisibility(holder.tv_username, process.getTaskAssignee());
                        break;
                    }
                }
            }
        }else if(data.getNodeLevel() == 0){
            handleTimeAndDate(holder, data);
        }
    }

    public static class AdviceViewHolder extends BaseDataViewHolder {


//        View view_time_point;
//        View view_time_line_top;
//        View view_time_line_bottom;
        LinearLayout tv_node;
        TextView tv_date;
        TextView tv_time;
        TextView tv_username;
//        TextView tv_organization;
//        TextView tv_content;
        ImageView iv_phone;
        TextView tv_state;
//        BGASortableNinePhotoLayout photo_layout;
//        ViewGroup ll_next_info;
//        TextView tv_next_username;
//        ImageView iv_next_phone;
//        TextView tv_next_phone;
        TextView tv_material;
        TextView mTvResponse;
         RelativeLayout mRlResponse;
         TextView mTvProcessRepair;
         TextView mTvProcessTitle;
         TextView tv_process_node_bg;
         TextView tv_process_node_bg_two;
         TextView tv_process_node_bg_three;
         TextView tv_process_item;
         ImageView icon_node_white;
         FrameLayout fl_icon;


        public AdviceViewHolder(View itemView) {
            super(itemView);

            mTvProcessRepair = (TextView) itemView.findViewById(R.id.tv_process_repair);
            icon_node_white = (ImageView) itemView.findViewById(R.id.icon_node_white);
            fl_icon = (FrameLayout) itemView.findViewById(R.id.fl_icon);
            tv_process_node_bg = (TextView) itemView.findViewById(R.id.tv_process_node_bg);
            tv_process_item = (TextView) itemView.findViewById(R.id.tv_process_item);
            tv_process_node_bg_two = (TextView) itemView.findViewById(R.id.tv_process_node_bg_two);
            tv_process_node_bg_three = (TextView) itemView.findViewById(R.id.tv_process_node_bg_three);
            mTvProcessTitle = (TextView) itemView.findViewById(R.id.tv_process_title);
            mRlResponse = (RelativeLayout) itemView.findViewById(R.id.rl_response);
            mTvResponse = (TextView) itemView.findViewById(R.id.tv_response);
//            view_time_line_bottom = itemView.findViewById(R.id.view_time_line_bottom);
//            view_time_line_top = itemView.findViewById(R.id.view_time_line_top);
//            view_time_point = itemView.findViewById(R.id.view_time_point);
            tv_node = itemView.findViewById(R.id.tv_node);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_username = itemView.findViewById(R.id.tv_username);
//            tv_organization = itemView.findViewById(R.id.tv_organization);
            tv_material = itemView.findViewById(R.id.tv_material);
//            tv_content = itemView.findViewById(R.id.tv_content);
            tv_state = itemView.findViewById(R.id.tv_state);
            iv_phone = itemView.findViewById(R.id.iv_phone);
//            photo_layout = itemView.findViewById(R.id.photo_layout);
//            ll_next_info = itemView.findViewById(R.id.ll_next_info);
//            tv_next_username = itemView.findViewById(R.id.tv_next_username);
//            iv_next_phone = itemView.findViewById(R.id.iv_next_phone);
//            tv_next_phone = itemView.findViewById(R.id.tv_next_phone);

        }
    }

    private void setTextAndVisibility(TextView textView, String text) {
        if (!TextUtils.isEmpty(text)) {
            textView.setText(text);
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

}
