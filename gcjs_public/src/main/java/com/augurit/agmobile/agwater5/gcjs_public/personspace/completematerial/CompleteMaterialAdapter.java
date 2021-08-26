package com.augurit.agmobile.agwater5.gcjs_public.personspace.completematerial;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;


/**
 * @author 创建人 ：SDB
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzsz.facilityquery
 * @modifyMemo 修改备注：
 */

public class CompleteMaterialAdapter extends BaseViewListAdapter<String> {


    public CompleteMaterialAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CompleteMaterialAdapter.LocalDraftViewHolder(View.inflate(mContext, R.layout.complete_material_item_view, null));
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
//        FacilityQueryAdapter.LocalDraftViewHolder mHolder = (FacilityQueryAdapter.LocalDraftViewHolder) holder;
//        RoadInfo.RoadBean roadBean = mDatas.get(position);
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


    }

    protected class LocalDraftViewHolder extends BaseDataViewHolder {

        public LocalDraftViewHolder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        }
    }
}
