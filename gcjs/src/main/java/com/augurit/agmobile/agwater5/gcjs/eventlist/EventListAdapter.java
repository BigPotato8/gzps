package com.augurit.agmobile.agwater5.gcjs.eventlist;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.AgWaterInjection;
import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventState;
import com.augurit.agmobile.busi.bpm.dict.IDictRepository;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;

/**
 * 事件列表Adapter,待办事项，已办事项
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.drainage.eventlist.view
 * @createTime 创建时间 ：2018/9/4
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class EventListAdapter extends BaseViewListAdapter<EventListItem.DataBean> {

    protected int mEventState = EventState.HANDLING;

    protected IDictRepository mDictRepository;

    public EventListAdapter(Context context) {
        super(context);
        mDictRepository = AgWaterInjection.provideDictRepository(context);
    }

    public EventListAdapter(Context context, int state) {
        super(context);
        mDictRepository = AgWaterInjection.provideDictRepository(context);
        this.mEventState = state;
    }

//    public EventListAdapter(Context context, int layoutStyle) {
//        super(context, layoutStyle);
//        mDictRepository = AgWaterInjection.provideDictRepository(context);
//    }

    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (mEventState == EventState.HANDLING) {
            view = LayoutInflater.from(mContext).inflate(R.layout.gcjs_event_listitem_data, parent, false);
        } else if (mEventState == EventState.HANDLED) {
            view = LayoutInflater.from(mContext).inflate(R.layout.gcjs_event_yb_listitem_data, parent, false);
        }else {
            view = LayoutInflater.from(mContext).inflate(R.layout.gcjs_event_all_listitem_data, parent, false);
        }
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof EventViewHolder) {
            EventListItem.DataBean item = mDatas.get(position);

            EventViewHolder myHolder = (EventViewHolder) holder;
            myHolder.tv_project_name.setText(item.getProjName());
//            myHolder.tv_project_link.setText(item.getItemName());
//            myHolder.tv_project_limit.setText(item.getPROJ_NAME());
            myHolder.tv_project_date.setText(item.getApplyTime());
//            myHolder.tv_project_stage.setText(item.getStageName());
//            if (mEventState == EventState.HANDLED){
//                myHolder.tv_project_date.setText(TextUtils.isEmpty(item.getApplyTime()) && mEventState == EventState.HANDLED ? item.getApplyTime() : item.getAcceptTime());
//                myHolder.tv_project_stage.setText(item.getStageName());
//            }else if(mEventState == EventState.HANDLED){
//                myHolder.tv_project_date.setText(TextUtils.isEmpty(item.getAcceptTime()) && mEventState == EventState.HANDLED ? item.getProcessTime() : item.getAcceptTime());
//                myHolder.tv_project_stage.setText(item.getStageName());
//            }else{
//                myHolder.tv_project_date.setText(TextUtils.isEmpty(item.getApplyTime())?"":item.getApplyTime());
//                myHolder.tv_project_stage.setText(item.getIteminstState());//办理状态
//            }
//            myHolder.tv_project_date.setText(TextUtils.isEmpty(item.getAcceptTime()) && mEventState == EventState.HANDLED ? item.getProcessTime() : item.getAcceptTime());
//            myHolder.tv_project_stage.setText(item.getStageName());
            myHolder.tv_project_code.setText(item.getApplyinstCode());
            if (mEventState == EventState.DEPARTMENT_ALL){
                if ("单项".equals(item.getApplyType())) {
                    myHolder.tv_stage_describe.setText("事项名称");
                    myHolder.tv_project_stage.setText(item.getItemName());
                }else if("并联".equals(item.getApplyType())){
                    myHolder.tv_stage_describe.setText("申请阶段");
                    myHolder.tv_project_stage.setText(item.getStageName());
                }
                myHolder.tv_project_state.setText(item.getCurrentLinkName());
            }else {
                if ("单项".equals(item.getApplyType())) {
                    myHolder.tv_stage_describe.setText("事项名称");
                    myHolder.tv_project_stage.setText(item.getItemName());
                }else if("并联".equals(item.getApplyType())){
                    myHolder.tv_stage_describe.setText("申请阶段");
                    myHolder.tv_project_stage.setText(item.getStageName());
                }
                if (TextUtils.isEmpty(item.getApplyinstStateName())) {
                    myHolder.tv_project_state.setText(item.getTaskName());
                } else {
//                    myHolder.tv_project_state.setText(item.getTaskName() + "(" + item.getIteminstStateName() + ")");
                    myHolder.tv_project_state.setText(item.getCurrentLinkName());
                }
            }
            myHolder.tv_project_finish_time.setText(item.getExpiryDate());
           if ("1".equals(item.getIsInformPromise())) {
               myHolder.tv_promise.setVisibility(View.VISIBLE);
            }
            myHolder.tv_project_time_limit.setText(TextUtils.isEmpty(item.getRemainingOrOverTimeText())?
                            "剩余"+item.getDueNumText(): item.getRemainingOrOverTimeText());
            if (!TextUtils.isEmpty(item.getInstState())) {
                switch (item.getInstState()) {
                    case "1"://正常
                        myHolder.tv_project_time_limit.setTextColor(mContext.getResources().getColor(R.color.agmobile_blue));
                        break;
                    case "2"://预警
                        myHolder.tv_project_time_limit.setTextColor(mContext.getResources().getColor(R.color.mark_drak_yellow));
                        break;
                    case "3"://逾期
                        myHolder.tv_project_time_limit.setTextColor(mContext.getResources().getColor(R.color.agmobile_red));
                        break;
                }
            }else{
                myHolder.tv_project_time_limit.setTextColor(mContext.getResources().getColor(R.color.agmobile_blue));
            }
        }
    }

    public static class EventViewHolder extends BaseDataViewHolder {

        public TextView tv_project_name;
        public TextView tv_state;
        public TextView tv_stage_describe;
//        public TextView tv_project_link;
        public TextView tv_project_limit;
        public TextView tv_project_date;
        public TextView tv_project_stage;
        public TextView tv_project_code;
        public TextView tv_project_state;
        public TextView tv_project_finish_time;
        public TextView tv_project_time_limit;
        public TextView tv_promise;


        public EventViewHolder(View itemView) {
            super(itemView);
            tv_project_name = itemView.findViewById(R.id.tv_project_name);
            tv_promise = itemView.findViewById(R.id.tv_promise);
            tv_project_stage = itemView.findViewById(R.id.tv_project_stage);
            tv_stage_describe = itemView.findViewById(R.id.tv_stage_describe);
//            tv_state = itemView.findViewById(R.id.tv_state);
//            tv_project_link = itemView.findViewById(R.id.tv_project_link);
            tv_project_limit = itemView.findViewById(R.id.tv_project_limit);
            tv_project_date = itemView.findViewById(R.id.tv_project_date);
            tv_project_code = itemView.findViewById(R.id.tv_project_code);
            tv_project_state = itemView.findViewById(R.id.tv_project_state);
            tv_project_finish_time = itemView.findViewById(R.id.tv_project_finish_time);
            tv_project_time_limit = itemView.findViewById(R.id.tv_project_time_limit);
        }
    }


//    承诺办结时间： expiryDate
//    剩余时限：显示 remainingOrOverTimeText  根据  instState  显示不同颜色状态，
//            1： 正常
//2：预警
//3： 逾期
}
