package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.eventlist.EventListActivity;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.ProjectReportBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.multi.MultiUploadEventActivity;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent
 * Created by sdb on 2019/4/19  13:47.
 * Desc：
 */

public class UploadProjectListAdapter extends BaseViewListAdapter<ProjectReportBean> {
    public static final String PROJECT = "project";

    public UploadProjectListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UploadProjectListAdapter.LocalDraftViewHolder(View.inflate(mContext, R.layout.gcjs_public_upload_list_item, null));
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        UploadProjectListAdapter.LocalDraftViewHolder mHolder = (UploadProjectListAdapter.LocalDraftViewHolder) holder;
        mHolder.tv_code.setText(mDatas.get(position).getLocalCode());
        mHolder.tv_name.setText(mDatas.get(position).getProjName());

        mHolder.tv_state_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MultiUploadEventActivity.class);
                intent.putExtra(PROJECT,mDatas.get(position));
                mContext.startActivity(intent);
            }
        });

        mHolder.tv_single_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EventListActivity.class);
                intent.putExtra(PROJECT,mDatas.get(position));
                mContext.startActivity(intent);
            }
        });

    }

    protected class LocalDraftViewHolder extends BaseDataViewHolder {
        TextView tv_state_upload, tv_single_upload;
        TextView tv_name, tv_code;
        public LocalDraftViewHolder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            tv_state_upload = itemView.findViewById(R.id.tv_state_upload);
            tv_single_upload = itemView.findViewById(R.id.tv_single_upload);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_code = itemView.findViewById(R.id.tv_code);
        }
    }
}
