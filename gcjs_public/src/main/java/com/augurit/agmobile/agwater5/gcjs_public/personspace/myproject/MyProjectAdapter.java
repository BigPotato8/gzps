package com.augurit.agmobile.agwater5.gcjs_public.personspace.myproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.ProjectDetailBean;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;

import java.util.List;


/**
 * @author 创建人 ：SDB
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzsz.facilityquery
 * @modifyMemo 修改备注：
 */

public class MyProjectAdapter extends BaseViewListAdapter<ProjectDetailBean.ProjectInfoBean> {


    public MyProjectAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.my_project_item_view, parent, false);
        return new LocalDraftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
       LocalDraftViewHolder mHolder = (LocalDraftViewHolder) holder;
        ProjectDetailBean.ProjectInfoBean bean = mDatas.get(position);
        mHolder.tv_title.setText("项目代码:"+ (TextUtils.isEmpty(bean.getCentralCode())?"":bean.getCentralCode()));
        mHolder.tv_name.setText("项目名称:"+ (TextUtils.isEmpty(bean.getProjName())?"":bean.getProjName()));
        mHolder.tv_progress.setText("项目地址:"+(TextUtils.isEmpty(bean.getProjAddr())?"":bean.getProjAddr()));

//        mHolder.tv_state.setText("项目状态:"+bean.getProjState());
//        mHolder.tv_progress.setText("项目进度:"+bean.getCurrentProgress());
    }

    protected class LocalDraftViewHolder extends BaseDataViewHolder {
        private TextView tv_title;
        private TextView tv_name;
        private TextView tv_state;
        private TextView tv_progress;

        public LocalDraftViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_state = itemView.findViewById(R.id.tv_state);
            tv_progress = itemView.findViewById(R.id.tv_progress);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        }
    }
}
