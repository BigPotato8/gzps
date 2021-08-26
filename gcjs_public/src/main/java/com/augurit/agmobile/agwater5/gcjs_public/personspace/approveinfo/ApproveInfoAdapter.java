package com.augurit.agmobile.agwater5.gcjs_public.personspace.approveinfo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs_public.ApproveInfoBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.eventuploaded.EventBean;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.common.common.util.TimeUtil;

import java.util.Date;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent
 * Created by sdb on 2019/4/19  13:47.
 * Desc：
 */

public class ApproveInfoAdapter extends BaseViewListAdapter<ApproveInfoBean> {

    private int state;

    public ApproveInfoAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ApproveInfoAdapter.ApproveInfoViewHolder(View.inflate(mContext, R.layout.approve_info_item_view, null));
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ApproveInfoAdapter.ApproveInfoViewHolder mHolder = (ApproveInfoAdapter.ApproveInfoViewHolder) holder;
        ApproveInfoBean listBean = mDatas.get(position);
        mHolder.tv_name.setText("项目名称:"+(TextUtils.isEmpty(listBean.getProjName())?"":listBean.getProjName()));
        mHolder.tv_code.setText("项目代码:"+(TextUtils.isEmpty(listBean.getApplyinstCode())?"":listBean.getApplyinstCode()));
        mHolder.tv_org.setText("项目单位:"+(TextUtils.isEmpty(listBean.getOrgName())?"":listBean.getOrgName()));
        mHolder.tv_item_name.setText("项目属性:"+(TextUtils.isEmpty(listBean.getItemName())?"":listBean.getItemName()));
        mHolder.tv_start_time.setText("开始时间:"+(TextUtils.isEmpty(listBean.getStartTime()) ? "": TimeUtil.getStringTimeYMD(new Date(Long.valueOf(listBean.getStartTime())))));
        switch (listBean.getIteminstState()){
            case "0":
                mHolder.tv_state.setText("已办");
                mHolder.tv_state.setTextColor(Color.parseColor("#6FC643"));
                break;
            case "1":
                mHolder.tv_state.setText("办理中");
                mHolder.tv_state.setTextColor(Color.parseColor("#41A5FF"));
                break;
            case "2":
                mHolder.tv_state.setText("未办理");
                mHolder.tv_state.setTextColor(Color.parseColor("#ECAC4A"));
                break;
        }
    }

    protected class ApproveInfoViewHolder extends BaseDataViewHolder {
        TextView tv_name, tv_code, tv_org, tv_item_name, tv_start_time, tv_state;
        public ApproveInfoViewHolder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_code = itemView.findViewById(R.id.tv_code);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_org = itemView.findViewById(R.id.tv_org);
            tv_start_time = itemView.findViewById(R.id.tv_start_time);
            tv_state = itemView.findViewById(R.id.tv_state);
        }
    }
}
