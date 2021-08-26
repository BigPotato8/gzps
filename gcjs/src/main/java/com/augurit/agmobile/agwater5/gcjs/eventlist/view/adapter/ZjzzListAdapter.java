package com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ZjzzBean;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.common.common.util.TimeUtil;

import java.util.Date;

/**
 * 证件证照列表Adapter
 */
public class ZjzzListAdapter extends BaseViewListAdapter<ZjzzBean> {


    public ZjzzListAdapter(Context context) {
        super(context);
    }


    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gcjs_zjzzlist, parent, false);
        return new ZjzzViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder mholder, int position) {
        super.onBindViewHolder(mholder, position);
        if (mholder instanceof ZjzzViewHolder) {
            ZjzzViewHolder holder = (ZjzzViewHolder) mholder;
            holder.tv_zj_name.setText(mDatas.get(position).getCertinstName());
            holder.tv_creator.setText(mDatas.get(position).getCreater());
            try {
                holder.tv_create_time.setText(TimeUtil.getStringTimeYMD(new Date(Long.parseLong(mDatas.get(position).getCreateTime()))));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public static class ZjzzViewHolder extends BaseDataViewHolder {

        TextView tv_zj_name;
        TextView tv_creator;
        TextView tv_create_time;

        public ZjzzViewHolder(View itemView) {
            super(itemView);
            tv_zj_name = itemView.findViewById(R.id.tv_zj_name);
            tv_creator = itemView.findViewById(R.id.tv_creator);
            tv_create_time = itemView.findViewById(R.id.tv_create_time);
        }
    }

}
