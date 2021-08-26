package com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.AgWaterInjection;
import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.busi.bpm.dict.IDictRepository;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;

import java.util.List;

/**
 * 申报信息--事项列表Adapter
 */
public class EventSbxxAdapter extends RecyclerView.Adapter<EventSbxxAdapter.ClbzViewHolder> {
    private List<EventInfoBean.IteminstListBean> beanList;
    private Context mContext;

    public EventSbxxAdapter(Context ctx, List<EventInfoBean.IteminstListBean> list) {
        mContext = ctx;
        beanList = list;
    }

    public void setBeanList(List<EventInfoBean.IteminstListBean> beanList) {
        this.beanList = beanList;
        notifyDataSetChanged();
    }

    @Override
    public ClbzViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gcjs_sbxx_listitem, parent, false);
        return new ClbzViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClbzViewHolder holder, int position) {
        if (holder instanceof ClbzViewHolder) {
            holder.tv_item_name.setText(beanList.get(position).getIteminstName());
            holder.tv_type.setText(beanList.get(position).getItemProperty());
            //typeCodeList.add("ITEM_PROPERTY");//办件类型
            holder.tv_work_time.setText(beanList.get(position).getDueNum()+beanList.get(position).getDueNumUnit());
            holder.tv_state.setText(getItemLabel(beanList.get(position).getIteminstState()));
            //typeCodeList.add("ITEMINST_STATE");//事项实例状态iteminstState
            holder.tv_main_body.setText(beanList.get(position).getApproveOrgName());

        }

    }

    public static class ClbzViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_name;
        TextView tv_type;
        TextView tv_work_time;
        TextView tv_main_body;
        TextView tv_state;

        LinearLayout ll_content;

        public ClbzViewHolder(View itemView) {
            super(itemView);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_work_time = itemView.findViewById(R.id.tv_work_time);
            tv_main_body = itemView.findViewById(R.id.tv_main_body);
            tv_state = itemView.findViewById(R.id.tv_state);
            ll_content = itemView.findViewById(R.id.ll_content);
        }
    }


    @Override
    public int getItemCount() {
        return beanList == null ? 0 : beanList.size();
    }


    private String getItemLabel(String itemValue){
        IDictRepository iDictRepository = AgWaterInjection.provideDictRepository(mContext);
        List<DictItem> item_property = iDictRepository.getDictItemByParentTypeCode("ITEM_PROPERTY");
        for (DictItem dictItem : item_property) {
            if (dictItem.getValue().equals(itemValue)){
                return dictItem.getLabel();
            }
        }
        return "";
    }
}
