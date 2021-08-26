package com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ClbzDetailBean;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 申报信息--材料补正Adapter
 */
public class EventListClbzAdapter extends BaseViewListAdapter<ClbzDetailBean> {


    public EventListClbzAdapter(Context context) {
        super(context);
    }

    @Override
    public ClbzViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gcjs_list_clbz_listitem, parent, false);
        return new ClbzViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder baseDataViewHolder, int position) {
        super.onBindViewHolder(baseDataViewHolder, position);
        if (baseDataViewHolder instanceof ClbzViewHolder) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            ClbzViewHolder viewHolder = (ClbzViewHolder) baseDataViewHolder;
            viewHolder.tv_people.setText(mDatas.get(position).getCreaterName());
            viewHolder.tv_no.setText(mDatas.get(position).getApplyinstCode());
            viewHolder.tv_data_start.setText(format.format(new Date(mDatas.get(position).getCreateTime())));
            viewHolder.tv_data_end.setText(format.format(new Date(mDatas.get(position).getCorrectDueTime())));
            //补正状态，6表示待补正，7表示已补正（待确认），8表示已补正（已确认，材料齐全），5表示不予受理
            String state;
            int stateColor;
            switch (mDatas.get(position).getCorrectState()) {
                case "5":
                    state = "不予受理";
                    stateColor = 0xff999999;
                    break;
                case "6":
                    state = "待补正";
                    stateColor = 0xff169aff;
                    break;
                case "7":
                    state = "已补正（待确认）";
                    stateColor = 0xff00c161;
                    break;
                case "8":
                    state = "已补正（已确认，材料齐全）";
                    stateColor = 0xff00c161;
                    break;
                default:
                    state = "";
                    stateColor = 0xff999999;
                    break;
            }
            viewHolder.tv_state.setText(state);
            viewHolder.tv_state.setTextColor(stateColor);
            viewHolder.tv_item_name.setText("第" + (mDatas.size() - position) + "次发起");
        }
    }

    public static class ClbzViewHolder extends BaseDataViewHolder {
        TextView tv_item_name;
        TextView tv_no;
        TextView tv_people;
        TextView tv_data_start;
        TextView tv_data_end;
        TextView tv_state;

        LinearLayout ll_content;

        public ClbzViewHolder(View itemView) {
            super(itemView);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_no = itemView.findViewById(R.id.tv_no);
            tv_data_start = itemView.findViewById(R.id.tv_data_start);
            tv_data_end = itemView.findViewById(R.id.tv_data_end);
            tv_people = itemView.findViewById(R.id.tv_people);
            tv_state = itemView.findViewById(R.id.tv_state);
            ll_content = itemView.findViewById(R.id.ll_content);
        }
    }

}
