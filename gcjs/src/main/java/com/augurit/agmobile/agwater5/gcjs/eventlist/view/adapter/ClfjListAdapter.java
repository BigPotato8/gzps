package com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ClfjBean;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;

/**
 * 上报事件列表Adapter
 */
public class ClfjListAdapter extends BaseViewListAdapter<ClfjBean.FilesBean> {

    public ClfjListAdapter(Context context) {
        super(context);
    }


    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gcjs_clfj_list, parent, false);
        return new MaterialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder mholder, int position) {
        super.onBindViewHolder(mholder, position);
        if (mholder instanceof MaterialViewHolder) {
            MaterialViewHolder holder = (MaterialViewHolder) mholder;
            holder.tv_material_name.setText(mDatas.get(position).getAttName());
//            holder.tv_file_source.setText(mDatas.get(position).getCreaterName());
//            holder.tv_file_info.setText(mDatas.get(position).getOrgName());

        }

    }

    public static class MaterialViewHolder extends BaseDataViewHolder {

        TextView tv_material_name;
        TextView tv_paper_count;
        TextView tv_copy_count;
        TextView tv_copy_receive;
        TextView tv_attCount;

        public MaterialViewHolder(View itemView) {
            super(itemView);
            tv_material_name = itemView.findViewById(R.id.tv_material_name);
            tv_paper_count = itemView.findViewById(R.id.tv_paper_count);
            tv_copy_count = itemView.findViewById(R.id.tv_copy_count);
            tv_copy_receive = itemView.findViewById(R.id.tv_copy_receive);
            tv_attCount = itemView.findViewById(R.id.tv_attCount);
        }
    }
}
