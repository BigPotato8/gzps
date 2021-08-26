package com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.AgWaterInjection;
import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ClbzDetailBean;
import com.augurit.agmobile.busi.bpm.dict.IDictRepository;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;

import java.util.List;

/**
 * 申报信息--材料补正详情，材料列表Adapter
 */
public class EventDetailClbzAdapter extends RecyclerView.Adapter<EventDetailClbzAdapter.ClbzViewHolder> {
    private List<ClbzDetailBean.MatCorrectDtosBean> beanList;
    private Context mContext;

    public EventDetailClbzAdapter(Context ctx, List<ClbzDetailBean.MatCorrectDtosBean> list) {
        mContext = ctx;
        beanList = list;
    }

    public void setBeanList(List<ClbzDetailBean.MatCorrectDtosBean> beanList) {
        this.beanList = beanList;
        notifyDataSetChanged();
    }

    @Override
    public ClbzViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gcjs_detail_clbz_listitem, parent, false);
        return new ClbzViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClbzViewHolder holder, int position) {
        if (holder instanceof ClbzViewHolder) {
            holder.tv_item_name.setText(beanList.get(position).getMatinstName());

            if (beanList.get(position).getRealCopyCount() != null) {
                holder.tv_type.setText("复印件");
                holder.tv_receive_num.setText(beanList.get(position).getCopyIsCollected());//已收份数
                holder.tv_bz_num.setText(beanList.get(position).getCopyCount() + "");//补全份数
                holder.tv_bz_advice.setText(beanList.get(position).getCopyDueIninstOpinion());//补全意见
            } else if (beanList.get(position).getRealPaperCount() != null) {
                holder.tv_type.setText("原件");
                holder.tv_receive_num.setText(beanList.get(position).getPaperIsCollected());//已收份数
                holder.tv_bz_num.setText(beanList.get(position).getPaperCount() + "");//补全份数
                holder.tv_bz_advice.setText(beanList.get(position).getPaperDueIninstOpinion());//补全意见
            } else {//未交材料
                if (!TextUtils.isEmpty(beanList.get(position).getCopyIsCollected())) {
                    holder.tv_type.setText("复印件");
                    holder.tv_receive_num.setText(beanList.get(position).getCopyIsCollected());//已收份数
                    holder.tv_bz_num.setText(beanList.get(position).getCopyCount() == null ? "" : beanList.get(position).getCopyCount() + "");//补全份数
                    holder.tv_bz_advice.setText(beanList.get(position).getCopyDueIninstOpinion());//补全意见
                } else {
                    holder.tv_type.setText("原件");
                    holder.tv_receive_num.setText(beanList.get(position).getPaperIsCollected());//已收份数
                    holder.tv_bz_num.setText(beanList.get(position).getPaperCount() == null ? "" : beanList.get(position).getPaperCount() + "");//补全份数
                    holder.tv_bz_advice.setText(beanList.get(position).getPaperDueIninstOpinion());//补全意见
                }

            }


        }

    }

    public static class ClbzViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_name;
        TextView tv_type;
        TextView tv_bz_num;
        TextView tv_receive_num;
        TextView tv_bz_advice;

        LinearLayout ll_content;

        public ClbzViewHolder(View itemView) {
            super(itemView);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_bz_num = itemView.findViewById(R.id.tv_bz_num);
            tv_receive_num = itemView.findViewById(R.id.tv_receive_num);
            tv_bz_advice = itemView.findViewById(R.id.tv_bz_advice);
            ll_content = itemView.findViewById(R.id.ll_content);
        }
    }


    @Override
    public int getItemCount() {
        return beanList == null ? 0 : beanList.size();
    }


    private String getItemLabel(String itemValue) {
        IDictRepository iDictRepository = AgWaterInjection.provideDictRepository(mContext);
        List<DictItem> item_property = iDictRepository.getDictItemByParentTypeCode("ITEM_PROPERTY");
        for (DictItem dictItem : item_property) {
            if (dictItem.getValue().equals(itemValue)) {
                return dictItem.getLabel();
            }
        }
        return "";
    }
}
