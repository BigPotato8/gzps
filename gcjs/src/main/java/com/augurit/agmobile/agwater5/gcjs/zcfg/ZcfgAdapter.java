package com.augurit.agmobile.agwater5.gcjs.zcfg;

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

public class ZcfgAdapter extends BaseViewListAdapter<ZcfgBean> {

    public ZcfgAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gcjs_zcfg_list_item, parent, false);
        return new ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ParentViewHolder) {
            ((ParentViewHolder) holder).tv_item.setText(mDatas.get(position).getName());
        }
    }

    public static class ParentViewHolder extends BaseDataViewHolder {

        public TextView tv_item;

        public ParentViewHolder(View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(R.id.tv_item);
        }
    }
}
