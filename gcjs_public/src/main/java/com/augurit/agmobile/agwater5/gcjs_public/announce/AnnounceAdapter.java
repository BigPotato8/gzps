package com.augurit.agmobile.agwater5.gcjs_public.announce;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.zcfg
 * Created by sdb on 2019/5/30  13:50.
 * Desc：
 */

public class AnnounceAdapter extends BaseViewListAdapter<String> {

    public AnnounceAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.announce_item_view, parent, false);
        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ParentViewHolder) {
//            ((ParentViewHolder) holder).tv_title.setText(mDatas.get(position).getName());
        }
    }

    public static class ParentViewHolder extends BaseDataViewHolder {

        public TextView tv_title;
        public TextView tv_link;
        public TextView tv_per;
        public TextView tv_time;
        public TextView tv_state;

        public ParentViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_link = itemView.findViewById(R.id.tv_link);
            tv_per = itemView.findViewById(R.id.tv_per);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_state = itemView.findViewById(R.id.tv_state);
        }
    }
}
