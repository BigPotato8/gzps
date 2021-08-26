package com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ResultGoodsMaterialBean;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;

public class ResultGoodsMaterialAdapter extends BaseViewListAdapter<ResultGoodsMaterialBean> {

    public ResultGoodsMaterialAdapter(Context context) {
        super(context);
    }
    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gcjs_result_goods_material, parent, false);
        return new MaterialViewHolder(view);
    }
    @Override
    public void onBindViewHolder(BaseDataViewHolder mholder, int position) {
        super.onBindViewHolder(mholder, position);
        if (mholder instanceof MaterialViewHolder) {
            MaterialViewHolder holder = (MaterialViewHolder) mholder;
            holder.tv_item_name.setText(mDatas.get(position).getIteminstName());//事项
//            if (mDatas.get(position).getDuePaperCount() != null && mDatas.get(position).getRealPaperCount() != null) {//结果物来源
//                holder.tv_material_source.setText(mDatas.get(position).getDuePaperCount() + "/" + mDatas.get(position).getRealPaperCount());
//            } else {
//                holder.tv_material_source.setText("");
//            }
            if (mDatas.get(position).getResultCount() != null) {//电子件
                holder.tv_parts.setText(mDatas.get(position).getResultCount()+"");
            }else{
                holder.tv_parts.setText("0");
            }
        }

    }
    public static class MaterialViewHolder extends BaseDataViewHolder {

        TextView tv_item_name;
        TextView tv_material_source;
        TextView tv_parts;

        public MaterialViewHolder(View itemView) {
            super(itemView);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_material_source = itemView.findViewById(R.id.tv_material_source);
            tv_parts = itemView.findViewById(R.id.tv_parts);
        }

    }

}
