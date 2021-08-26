package com.augurit.agmobile.agwater5.gcjs_public.personspace.mymaterial;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.common.lib.validate.ListUtil;


/**
 * @author 创建人 ：SDB
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzsz.facilityquery
 * @modifyMemo 修改备注：
 */

public class MyMaterialAdapter extends BaseViewListAdapter<MaterialBean.ContentBean> {


    public MyMaterialAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyMaterialAdapter.LocalDraftViewHolder(View.inflate(mContext, R.layout.my_materil_item_view, null));
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        MyMaterialAdapter.LocalDraftViewHolder mHolder = (MyMaterialAdapter.LocalDraftViewHolder) holder;
        MaterialBean.ContentBean roadBean = mDatas.get(position);
////        String stickyStr = TimeUtil.getStringTimeYMDS(new Date(formRecord.getSavedTime()));
////        mHolder.sticky.setText(stickyStr.split(" ")[0]);
////        String time = stickyStr.split(" ")[1];
////        mHolder.mTvTime.setText(time.substring(0, time.lastIndexOf(":")));
////        // 第一个item的吸顶信息肯定是展示的，并且标记tag为FIRST_STICKY_VIEW
////        mHolder.itemView.setTag(FIRST_STICKY_VIEW);
////        Map<String, String> values = formRecord.getValues();
//        mHolder.mTvProblem.setText(roadBean.getRoadName());
//        mHolder.mTvStatus.setText(roadBean.getMangerName());
////        mHolder.mTvAddress.setText(roadBean.getAreaName());
////        mHolder.mTvOutOfDate.setText(values.get("description") == null ? "" : values.get("description"));
//
//        if ("桥".equals(roadBean.getRoadType())) {
//            mHolder.mIvImg.setImageResource(R.mipmap.ic_bridge);
//        } else if ("隧道".equals(roadBean.getRoadType())) {
//            mHolder.mIvImg.setImageResource(R.mipmap.ic_tunnel);
//        } else {
//            mHolder.mIvImg.setImageResource(R.mipmap.ic_road);
//        }
        mHolder.tv_title.setText("文件名称："+roadBean.getMatinstName());
        mHolder.tv_date.setText("日期："+roadBean.getCreateTime());
        if(!ListUtil.isEmpty(roadBean.getAttFormList())) {
            mHolder.tv_size.setText(roadBean.getAttFormList().get(0).getAttSize());
        }else{
            mHolder.tv_size.setText("未知");
        }

    }

    protected class LocalDraftViewHolder extends BaseDataViewHolder {
       TextView tv_title,tv_date,tv_size;
        public LocalDraftViewHolder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            tv_title =  itemView.findViewById(R.id.tv_title);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_size = itemView.findViewById(R.id.tv_date);
        }
    }
}
