package com.augurit.agmobile.agwater5.gcjs_public.personspace.projectprocess.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.projectprocess.model.ProjectProcessDetail;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;

/**
 * @author 创建人 ：panming
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjs.progress
 * @createTime 创建时间 ：2019-11-25
 * @modifyBy 修改人 ：panming
 * @modifyTime 修改时间 ：2019-11-25
 * @modifyMemo 修改备注：
 */
public class ProcessDetailTopAdapter extends BaseViewListAdapter<ProjectProcessDetail.ProcessBean> {

    public ProcessDetailTopAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_process_detail_top, parent, false);
        return new ProcessDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof ProcessDetailViewHolder) {
            ProjectProcessDetail.ProcessBean item = mDatas.get(position);
            ProcessDetailViewHolder myHolder = (ProcessDetailViewHolder) holder;
            String [] infos = item.getStageName().split("-");
//            myHolder.tv_phase.setText(infos[0].substring(0,(infos[0].length())/2)+"\n"+infos[0].substring((infos[0].length())/2));
            myHolder.tv_phase.setText(infos[0]);
            myHolder.tv_duration.setText("历时(工作日) "+item.getCoreDoneNumber());
            myHolder.tv_sz.setText("事项 "+item.getCoreDoneNumber());
            myHolder.tv_rate.setText("办结率 "+item.getEndProp());
            switch (item.getStateType()){
                case "0":
                    myHolder.tvState.setText("已办");
                    myHolder.tvState.setTextColor(Color.parseColor("#6FC643"));
                    break;
                case "1":
                    myHolder.tvState.setText("办理中");
                    myHolder.tvState.setTextColor(Color.parseColor("#41A5FF"));
                    break;
                case "2":
                    myHolder.tvState.setText("未办理");
                    myHolder.tvState.setTextColor(Color.parseColor("#ECAC4A"));
                    break;
            }
            switch (position%4){
                case 0:
                    myHolder.iv_imageView.setImageResource(R.mipmap.lxyd);
                    break;
                case 1:
                    myHolder.iv_imageView.setImageResource(R.mipmap.gcjs_detail);
                    break;
                case 2:
                    myHolder.iv_imageView.setImageResource(R.mipmap.sgxk);
                    break;
                case 3:
                    myHolder.iv_imageView.setImageResource(R.mipmap.jgys);
                    break;
            }
        }
    }

    public static class ProcessDetailViewHolder extends BaseDataViewHolder {

        public TextView tv_phase;
        public TextView tv_sz;
        public TextView tv_rate;
        public TextView tv_duration;
        public TextView tvState;
        public ImageView iv_imageView;
        public ProcessDetailViewHolder(View itemView) {
            super(itemView);
//            viewDividerTop = itemView.findViewById(R.id.view_divider_top);
//            ivPhoto = itemView.findViewById(R.id.iv_photo);
            tv_phase = itemView.findViewById(R.id.tv_phase);
            tv_sz = itemView.findViewById(R.id.tv_sz);
            tv_rate = itemView.findViewById(R.id.tv_rate);
            tv_duration = itemView.findViewById(R.id.tv_duration);
            tvState = itemView.findViewById(R.id.iv_state);
            iv_imageView = itemView.findViewById(R.id.iv_imageView);
        }
    }
}
