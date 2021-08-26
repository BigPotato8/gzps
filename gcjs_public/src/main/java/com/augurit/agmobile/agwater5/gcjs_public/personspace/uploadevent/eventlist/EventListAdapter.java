package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.eventlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.EventItemBean;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent
 * Created by sdb on 2019/4/19  13:47.
 * Desc：
 */

public class EventListAdapter extends BaseViewListAdapter<EventItemBean> {


    public EventListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventListAdapter.LocalDraftViewHolder(View.inflate(mContext, R.layout.gcjs_public_event_list_item, null));
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        EventListAdapter.LocalDraftViewHolder mHolder = (EventListAdapter.LocalDraftViewHolder) holder;
        mHolder.tv_item.setText(mDatas.get(position).getItemName());

    }

    protected class LocalDraftViewHolder extends BaseDataViewHolder {
        TextView tv_item;

        public LocalDraftViewHolder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            tv_item = itemView.findViewById(R.id.tv_item);

        }
    }
}
