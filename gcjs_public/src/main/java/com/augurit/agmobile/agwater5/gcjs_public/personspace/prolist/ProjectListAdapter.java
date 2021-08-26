package com.augurit.agmobile.agwater5.gcjs_public.personspace.prolist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class ProjectListAdapter extends BaseViewListAdapter<ProjectDetailBean.ProjectInfoBean> {


    public ProjectListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProjectListAdapter.PjrjectHanderViewHolder(View.inflate(mContext, R.layout.my_pro_handle_item_view, null));
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        PjrjectHanderViewHolder mHolder = (PjrjectHanderViewHolder) holder;
        ProjectDetailBean.ProjectInfoBean bean = mDatas.get(position);

        mHolder.tv_bjbh.setText("办件编号:"+bean.getIteminstCode());
        mHolder.tv_sxmc.setText("事项名称:"+bean.getStageIteminstName());
        mHolder.tv_xmdm.setText("项目名称:"+bean.getProjName());
        mHolder.tv_xmmc.setText("项目代码:"+bean.getLocalCode());
        mHolder.tv_sszt.setText("实施主体:"+bean.getOrgName());

        switch (bean.getIteminstState()){
            case "3" :
                mHolder.tv_state.setText("已受理");
                break;
            case "1" :
                mHolder.tv_state.setText("已接件");
                break;
            case "2" :
                mHolder.tv_state.setText("已办结");
                break;
        }
    }

    protected class PjrjectHanderViewHolder extends BaseDataViewHolder {
        private TextView tv_bjbh;
        private TextView tv_sxmc;
        private TextView tv_xmdm;
        private TextView tv_xmmc;
        private TextView tv_sszt;
        private TextView tv_state;

        public PjrjectHanderViewHolder(View itemView) {
            super(itemView);
            tv_bjbh = itemView.findViewById(R.id.tv_bjbh);
            tv_sxmc = itemView.findViewById(R.id.tv_sxmc);
            tv_xmdm = itemView.findViewById(R.id.tv_xmdm);
            tv_xmmc = itemView.findViewById(R.id.tv_xmmc);
            tv_sszt = itemView.findViewById(R.id.tv_sszt);
            tv_state = itemView.findViewById(R.id.tv_state);

            itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        }
    }
}
