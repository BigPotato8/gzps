package com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListBean;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;

/**
 * 事项列表Adapter
 */
public class EventListAdapter extends BaseViewListAdapter<EventListBean> {


    public EventListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gcjs_eventlist, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof EventViewHolder) {
            EventViewHolder myHolder = (EventViewHolder) holder;
            myHolder.tv_department.setText(mDatas.get(position).getOrgName());
            myHolder.tv_event_name.setText(mDatas.get(position).getIteminstName());
        }
    }

    public static class EventViewHolder extends BaseDataViewHolder {


        private TextView tv_department;
        private TextView tv_event_name;

        public EventViewHolder(View itemView) {
            super(itemView);
            tv_department = itemView.findViewById(R.id.tv_department);
            tv_event_name = itemView.findViewById(R.id.tv_event_name);

        }
    }

}
