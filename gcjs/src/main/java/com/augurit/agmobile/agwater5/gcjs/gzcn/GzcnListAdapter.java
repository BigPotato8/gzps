package com.augurit.agmobile.agwater5.gcjs.gzcn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.agwater5.AgWaterInjection;
import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventState;
import com.augurit.agmobile.busi.bpm.dict.IDictRepository;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;

/**
 * 事件列表Adapter
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.drainage.eventlist.view
 * @createTime 创建时间 ：2018/9/4
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class GzcnListAdapter extends BaseViewListAdapter<String> {

    protected int mEventState = EventState.HANDLING;

    protected IDictRepository mDictRepository;

    public GzcnListAdapter(Context context) {
        super(context);
        mDictRepository = AgWaterInjection.provideDictRepository(context);
    }

    public GzcnListAdapter(Context context, int state) {
        super(context);
        mDictRepository = AgWaterInjection.provideDictRepository(context);
        this.mEventState = state;
    }

//    public EventListAdapter(Context context, int layoutStyle) {
//        super(context, layoutStyle);
//        mDictRepository = AgWaterInjection.provideDictRepository(context);
//    }

    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gcjs_gzcn_list_item, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof EventViewHolder) {
//            EventListItem item = mDatas.get(position);
//            EventViewHolder myHolder = (EventViewHolder) holder;
//
//            String componentType = null;
//            if (!TextUtils.isEmpty(item.getComponentType())) {
//                componentType = mDictRepository.getDictLabelByTypeCode(item.getComponentType());
//            }
//            String eventType = null;
//            if (!TextUtils.isEmpty(item.getEventType())) {
//                eventType = mDictRepository.joinDictLabelByTypeCodes("，",
//                        item.getEventType().split(","));
//            }
//            if (TextUtils.isEmpty(componentType)) componentType = "窨井";
//            if (TextUtils.isEmpty(eventType)) eventType = "井盖破损";
//
//            myHolder.tvLink.setText(!TextUtils.isEmpty(item.getActivityName())
//                    ? item.getActivityName() : item.getActivityChineseName());
//            myHolder.tvTitle.setText(componentType.concat("  ").concat(eventType));
//            myHolder.tvUploader.setText(item.getReportUser());
//            myHolder.tvAddr.setText(item.getAddr());
//            myHolder.tvTime.setText(TimeUtil.getStringTimeYMDS(new Date(item.getReportTime())));
//
//            if (mEventState == EventState.HANDLING
//                    && DrainageConstant.EVENT_OPEN.equals(item.getState())) {
//                myHolder.tvState.setVisibility(View.VISIBLE);
//                myHolder.tvState.setText("待处理");
//            } else {
//                myHolder.tvState.setVisibility(View.GONE);
//            }
//
//            if (!TextUtils.isEmpty(item.getImgPath())) {
//                Glide.with(mContext)
//                        .load(item.getImgPath())
//                        .placeholder(R.mipmap.bga_pp_ic_holder_light)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .dontAnimate()
//                        .into(myHolder.ivPhoto);
//            } else {
//                myHolder.ivPhoto.setImageResource(R.mipmap.bga_pp_ic_holder_light);
//            }

        }
    }

    public static class EventViewHolder extends BaseDataViewHolder {

//        public View viewDividerTop;
//        public ImageView ivPhoto;
//        public TextView tvTitle;
//        public TextView tvLink;
//        public TextView tvAddr;
//        public TextView tvUploader;
//        public TextView tvTime;
//        public TextView tvState;

        public EventViewHolder(View itemView) {
            super(itemView);
//            viewDividerTop = itemView.findViewById(R.id.view_divider_top);
//            ivPhoto = itemView.findViewById(R.id.iv_photo);
//            tvTitle = itemView.findViewById(R.id.tv_title);
//            tvLink = itemView.findViewById(R.id.tv_link);
//            tvAddr = itemView.findViewById(R.id.tv_addr);
//            tvUploader = itemView.findViewById(R.id.tv_uploader);
//            tvState = itemView.findViewById(R.id.tv_state);
//            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}
