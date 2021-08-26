package com.augurit.agmobile.agwater5.gcjs_public.personspace.projectprocess.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.projectprocess.model.ProjectDetailBean;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;


/**
 * @author 创建人 ：SDB
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzsz.facilityquery
 * @modifyMemo 修改备注：
 */

public class ProjectProcessAdapter extends BaseViewListAdapter<ProjectDetailBean.ProjectInfoBean> {


    public ProjectProcessAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.project_process_item_view, parent, false);
        return new LocalDraftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
       LocalDraftViewHolder mHolder = (LocalDraftViewHolder) holder;
        ProjectDetailBean.ProjectInfoBean bean = mDatas.get(position);
//        mHolder.tv_title.setText("项目代码:"+bean.getLocalCode());
//        mHolder.tv_name.setText("项目名称:"+bean.getProjName());
//        mHolder.tv_theme.setText("项目主题:"+bean.getThemeName());
        mHolder.tv_title.setText(bean.getLocalCode());
        mHolder.tv_name.setText(bean.getProjName());
        mHolder.tv_theme.setText(bean.getThemeName());
    }

    protected class LocalDraftViewHolder extends BaseDataViewHolder {
        private TextView tv_title;
        private TextView tv_name;
        private TextView tv_theme;
//        private Button btn_graphical;

        public LocalDraftViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_theme = itemView.findViewById(R.id.tv_theme);
//            btn_graphical = itemView.findViewById(R.id.btn_graphical);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        }
    }
}
