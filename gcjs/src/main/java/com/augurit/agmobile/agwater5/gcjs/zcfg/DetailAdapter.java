package com.augurit.agmobile.agwater5.gcjs.zcfg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.zcfg.model.DirsFileBean;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;

public class DetailAdapter extends BaseViewListAdapter<DirsFileBean> {

    public DetailAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewListAdapter.BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gcjs_zcfg_list_item, parent, false);
        return new ZcfgAdapter.ParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseViewListAdapter.BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ZcfgAdapter.ParentViewHolder) {
            ((ZcfgAdapter.ParentViewHolder) holder).tv_item.setText(mDatas.get(position).getAttName());
        }
    }

    public static class ParentViewHolder extends BaseViewListAdapter.BaseDataViewHolder {

        public TextView tv_item;

        public ParentViewHolder(View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(R.id.tv_item);
        }
    }
}
