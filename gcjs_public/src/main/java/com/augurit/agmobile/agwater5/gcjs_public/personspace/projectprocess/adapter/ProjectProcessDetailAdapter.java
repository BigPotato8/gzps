package com.augurit.agmobile.agwater5.gcjs_public.personspace.projectprocess.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.projectprocess.model.ProjectProcessDetail;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;


/**
 * @author 创建人 ：SDB
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzsz.facilityquery
 * @modifyMemo 修改备注：
 */

public class ProjectProcessDetailAdapter extends BaseViewListAdapter<ProjectProcessDetail.ProcessItem> {


    public ProjectProcessDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewListAdapter.BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.project_process_detail_item_view, parent, false);
        return new LocalDraftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
       LocalDraftViewHolder mHolder = (LocalDraftViewHolder) holder;
        ProjectProcessDetail.ProcessItem bean = mDatas.get(position);

        if(position == 0){
            mHolder.view_content.setVisibility(View.VISIBLE);
        }else{
            if(mDatas.get(position-1).getIteminstState().equals(mDatas.get(position).getIteminstState())){
                mHolder.view_content.setVisibility(View.GONE);
            }else{
                mHolder.view_content.setVisibility(View.VISIBLE);
            }
        }
       switch (mDatas.get(position).getItemStateType()){
           case "0":
               mHolder.tv_state.setText("已办結");
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
        mHolder.tv_orgName.setText(mDatas.get(position).getOrgName());
        mHolder.tv_sx.setText("1".equals(bean.getIteminstState())?"并联事项":"并行事项");
        mHolder.tv_itemName.setText(bean.getItemName());
    }

    protected class LocalDraftViewHolder extends BaseDataViewHolder {
        private TextView tv_sx;
        private TextView tv_itemName,tv_orgName,tv_state;
        private View view_content,view_detail;
        public LocalDraftViewHolder(View itemView) {
            super(itemView);
            tv_sx = itemView.findViewById(R.id.tv_sx);
            tv_itemName = itemView.findViewById(R.id.tv_itemName);
            tv_orgName = itemView.findViewById(R.id.tv_orgName);
            view_content = itemView.findViewById(R.id.view_content);
            view_detail = itemView.findViewById(R.id.view_detail);
            tv_state = itemView.findViewById(R.id.tv_state);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        }
    }
}
