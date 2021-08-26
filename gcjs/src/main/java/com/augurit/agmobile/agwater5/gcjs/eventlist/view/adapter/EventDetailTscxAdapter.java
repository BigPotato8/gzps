package com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.AgWaterInjection;
import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.TscxBean;
import com.augurit.agmobile.busi.bpm.dict.IDictRepository;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;

import java.util.List;

/**
 * 申报信息--特殊程序Adapter
 */
public class EventDetailTscxAdapter extends BaseViewListAdapter<TscxBean.SpecialListBean> {


    public EventDetailTscxAdapter(Context context) {
        super(context);
    }

    @Override
    public ClbzViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gcjs_detail_tscx_listitem, parent, false);
        return new ClbzViewHolder(view);
    }
    @Override
    public void onBindViewHolder(BaseDataViewHolder baseDataViewHolder, int position) {
        super.onBindViewHolder(baseDataViewHolder, position);
        if (baseDataViewHolder instanceof ClbzViewHolder) {
            ClbzViewHolder viewHolder = (ClbzViewHolder) baseDataViewHolder;
            viewHolder.tv_people.setText(mDatas.get(position).getCreater());
            viewHolder.tv_type.setText(getItemLabel(mDatas.get(position).getSpecialType()));
            viewHolder.tv_unit.setText(mDatas.get(position).getChargeOrgName());
            //specialState (特殊程序状态，9表示开始，10表示结束)
            String state = "10".equals(mDatas.get(position).getSpecialState())?"已结束":"待结束";
            viewHolder.tv_state.setText(state);
            viewHolder.tv_state.setTextColor("10".equals(mDatas.get(position).getSpecialState())?0xff00c161:0xff169aff);
            viewHolder.tv_item_name.setText("第"+(mDatas.size()-position)+"次发起");
        }
    }

    public static class ClbzViewHolder extends BaseDataViewHolder {
        TextView tv_item_name;
        TextView tv_type;
        TextView tv_people;
        TextView tv_unit;
        TextView tv_state;

        LinearLayout ll_content;

        public ClbzViewHolder(View itemView) {
            super(itemView);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_unit = itemView.findViewById(R.id.tv_unit);
            tv_people = itemView.findViewById(R.id.tv_people);
            tv_state = itemView.findViewById(R.id.tv_state);
            ll_content = itemView.findViewById(R.id.ll_content);
        }
    }

    private String getItemLabel(String itemValue){
        IDictRepository iDictRepository = AgWaterInjection.provideDictRepository(mContext);
        List<DictItem> item_property = iDictRepository.getDictItemByParentTypeCode("SPECIAL_TYPE");
        for (DictItem dictItem : item_property) {
            if (dictItem.getValue().equals(itemValue)){
                return dictItem.getLabel();
            }
        }
        return "";
    }
}
