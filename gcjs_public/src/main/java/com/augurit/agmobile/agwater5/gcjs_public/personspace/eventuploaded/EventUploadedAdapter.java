package com.augurit.agmobile.agwater5.gcjs_public.personspace.eventuploaded;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.common.lib.time.TimeUtil;

import java.util.Date;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent
 * Created by sdb on 2019/4/19  13:47.
 * Desc：
 */

public class EventUploadedAdapter extends BaseViewListAdapter<EventBean.ListBean> {

    private int state;

    public EventUploadedAdapter(Context context, int state) {
        super(context);
        this.state = state;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EventUploadedAdapter.LocalDraftViewHolder(View.inflate(mContext, R.layout.gcjs_public_event_uploaded_list_item, null));
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        EventUploadedAdapter.LocalDraftViewHolder mHolder = (EventUploadedAdapter.LocalDraftViewHolder) holder;
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
        EventBean.ListBean listBean = mDatas.get(position);
        mHolder.tv_code.setText(listBean.getLocalCode());
        mHolder.tv_name.setText(listBean.getProjName());
        mHolder.tv_evebt.setText(listBean.getIteminstName());
//        mHolder.tv_org.setText(listBean.getOrgName());
        mHolder.tv_done_time.setText(TimeUtil.getStringTimeYMD(new Date(listBean.getStartTime())));
//        if (state == EventState.HANDLING) {
//            mHolder.ll_done_time.setVisibility(View.VISIBLE);
//            mHolder.tv_pro_img.setVisibility(View.VISIBLE);
//            mHolder.tv_state_name.setText("状态");
//            mHolder.tv_state.setText("申办");
//        } else if (state == EventState.HANDLED) {
//            mHolder.ll_done_time.setVisibility(View.VISIBLE);
//            mHolder.tv_detail.setVisibility(View.VISIBLE);
//            mHolder.tv_state_name.setText("办结日期");
//        } else if (state == EventState.DRAFT) {
//            mHolder.tv_edit.setVisibility(View.VISIBLE);
//            mHolder.tv_delete.setVisibility(View.VISIBLE);
//            mHolder.tv_state_name.setText("申请日期");
//        }
//
//        mHolder.tv_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        mHolder.tv_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        mHolder.tv_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//        mHolder.tv_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    protected class LocalDraftViewHolder extends BaseDataViewHolder {
        TextView tv_state_name, tv_state, tv_pro_img, tv_detail, tv_edit, tv_delete;
        TextView tv_code, tv_name, tv_evebt, tv_org, tv_done_time;
        View ll_done_time;

        public LocalDraftViewHolder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
//            tv_state_name = itemView.findViewById(R.id.tv_state_name);
//            tv_state = itemView.findViewById(R.id.tv_state);
//            tv_pro_img = itemView.findViewById(R.id.tv_pro_img);
//            tv_detail = itemView.findViewById(R.id.tv_detail);
//            tv_edit = itemView.findViewById(R.id.tv_edit);
//            ll_done_time = itemView.findViewById(R.id.ll_done_time);
//            tv_delete = itemView.findViewById(R.id.tv_delete);
            tv_code = itemView.findViewById(R.id.tv_code);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_evebt = itemView.findViewById(R.id.tv_evebt);
            tv_org = itemView.findViewById(R.id.tv_org);
            tv_done_time = itemView.findViewById(R.id.tv_done_time);

        }
    }
}
