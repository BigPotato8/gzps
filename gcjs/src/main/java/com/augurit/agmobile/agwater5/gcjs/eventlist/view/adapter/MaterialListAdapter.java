package com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.MaterialBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.MaterialListBean;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;

/**
 * 上报事件列表Adapter
 */
public class MaterialListAdapter extends BaseViewListAdapter<MaterialListBean> {


    public MaterialListAdapter(Context context) {
        super(context);
    }


    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gcjs_materiallist, parent, false);
        return new MaterialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder mholder, int position) {
        super.onBindViewHolder(mholder, position);
        if (mholder instanceof MaterialViewHolder) {
            MaterialViewHolder holder = (MaterialViewHolder) mholder;
            holder.tv_material_name.setText(mDatas.get(position).getMatName());
            if (mDatas.get(position).getDuePaperCount()!=null && mDatas.get(position).getRealPaperCount()!=null) {
                holder.tv_paper_count.setText(mDatas.get(position).getDuePaperCount() + "/" + mDatas.get(position).getRealPaperCount());
            }else{
                holder.tv_paper_count.setText("0");
            }
            if (mDatas.get(position).getAttIsRequire()!=null) {//材料属性
                if (mDatas.get(position).getAttIsRequire().equals("0")){
                    holder.tv_material_attr.setText("可选");
                }else {
                    holder.tv_material_attr.setText("必交");
                }
            }
            if (mDatas.get(position).getDueCopyCount()!=null && mDatas.get(position).getRealCopyCount()!=null) {
                holder.tv_copy_count.setText(mDatas.get(position).getDueCopyCount() + "/" + mDatas.get(position).getRealCopyCount());
            }else{
                holder.tv_copy_count.setText("0");
            }
            if (mDatas.get(position).getAttCount()!=null) {
                holder.tv_attCount.setText(mDatas.get(position).getAttCount()+"");
            }else{
                holder.tv_attCount.setText("0");
            }
        }

    }

    public static class MaterialViewHolder extends BaseDataViewHolder {

        TextView tv_material_name;
        TextView tv_paper_count;
        TextView tv_copy_count;
        TextView tv_copy_receive;
        TextView tv_attCount;
        TextView tv_material_attr;

        public MaterialViewHolder(View itemView) {
            super(itemView);
            tv_material_name = itemView.findViewById(R.id.tv_material_name);
            tv_paper_count = itemView.findViewById(R.id.tv_paper_count);
            tv_copy_count = itemView.findViewById(R.id.tv_copy_count);
            tv_copy_receive = itemView.findViewById(R.id.tv_copy_receive);
            tv_attCount = itemView.findViewById(R.id.tv_attCount);
            tv_material_attr=itemView.findViewById(R.id.tv_material_attr);
        }
    }
}
