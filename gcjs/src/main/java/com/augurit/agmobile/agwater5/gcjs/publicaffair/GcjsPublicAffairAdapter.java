package com.augurit.agmobile.agwater5.gcjs.publicaffair;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;

/**
 * com.augurit.agmobile.agwater5.gcjs.publicaffair
 * Created by sdb on 2019/4/2  10:25.
 * Desc：
 */

public class GcjsPublicAffairAdapter extends BaseViewListAdapter<String> {

    public GcjsPublicAffairAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gcjs_declare_item, parent, false);
        return new EventViewHolder(view);
    }

    public static class EventViewHolder extends BaseDataViewHolder {

        public EventViewHolder(View itemView) {
            super(itemView);
        }
    }
}
