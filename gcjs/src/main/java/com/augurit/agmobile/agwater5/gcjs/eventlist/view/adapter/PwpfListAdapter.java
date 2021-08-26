package com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.PwpfBean;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;

/**
 * 上报事件列表Adapter
 */
public class PwpfListAdapter extends BaseViewListAdapter<PwpfBean.ItemMatinstBean> {


    public PwpfListAdapter(Context context) {
        super(context);
    }


    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gcjs_pwpflist, parent, false);
        return new PwpfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder mholder, int position) {
        super.onBindViewHolder(mholder, position);
        if (mholder instanceof PwpfViewHolder) {
            PwpfViewHolder holder = (PwpfViewHolder) mholder;
            holder.tv_pw_name.setText(mDatas.get(position).getOfficialDocTitle());
            holder.tv_creator.setText(mDatas.get(position).getCreator());
            holder.tv_create_time.setText(mDatas.get(position).getCreateDate());
            holder.tv_file_num.setText(mDatas.get(position).getDocCount()+"");
            holder.tv_file_type.setText(mDatas.get(position).getDocTypeName());
        }

    }

    public static class PwpfViewHolder extends BaseDataViewHolder {

        TextView tv_pw_name;
        TextView tv_file_type;
        TextView tv_file_num;
        TextView tv_creator;
        TextView tv_create_time;

        public PwpfViewHolder(View itemView) {
            super(itemView);
            tv_pw_name = itemView.findViewById(R.id.tv_pw_name);
            tv_file_type = itemView.findViewById(R.id.tv_file_type);
            tv_file_num = itemView.findViewById(R.id.tv_file_num);
            tv_creator = itemView.findViewById(R.id.tv_creator);
            tv_create_time = itemView.findViewById(R.id.tv_create_time);
        }
    }

}
