package com.augurit.agmobile.agwater5.gcjspad.eventdetail.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.common.FilePreviewUtil;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.MaterialListBean;
import com.augurit.agmobile.common.lib.validate.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 审批结果物详情
 */
public class EventDetailMaterialAdapter extends RecyclerView.Adapter<EventDetailMaterialAdapter.SecondViewHolder> {
    private Context context;
    private List<MaterialListBean> list;

    public void setList(List<MaterialListBean> listA) {
        list = listA;
        notifyDataSetChanged();
    }

    public EventDetailMaterialAdapter(Context context, List<MaterialListBean> listA) {
        this.context = context;
        list = listA;
    }

    @Override
    public EventDetailMaterialAdapter.SecondViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event_detail_material_pad, parent, false);
        return new EventDetailMaterialAdapter.SecondViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onBindViewHolder(EventDetailMaterialAdapter.SecondViewHolder holder, int position) {
        holder.tv_material_name.setText(list.get(position).getMatName());
        holder.tv_material_tag.setVisibility("1".equals(list.get(position).getPaperIsRequire())?View.VISIBLE:View.GONE);

        holder.tv_paper_count.setText((list.get(position).getDuePaperCount()!=null?list.get(position).getDuePaperCount():"0")
                + "/" + (list.get(position).getRealPaperCount()!=null?list.get(position).getRealPaperCount():"0"));

        holder.tv_copy_count.setText((list.get(position).getDueCopyCount()!=null?list.get(position).getDueCopyCount():"0")
                + "/" + (list.get(position).getRealCopyCount()!=null?list.get(position).getRealCopyCount():"0"));

        if (ListUtil.isNotEmpty(list.get(position).getFileList())) {//设置材料项文件列表
            if (holder.rv_files.getAdapter() != null) {
                ((EventDetailMaterialFileAdapter) holder.rv_files.getAdapter()).setList(list.get(position).getFileList());
            }else{
                EventDetailMaterialFileAdapter adapter = new EventDetailMaterialFileAdapter(context,list.get(position).getFileList());
                adapter.setList(list.get(position).getFileList());
                holder.rv_files.setAdapter(adapter);
                holder.rv_files.setLayoutManager(new LinearLayoutManager(context));

            }
        } else {
            if (holder.rv_files.getAdapter() != null) {
                ((EventDetailMaterialFileAdapter) holder.rv_files.getAdapter()).setList(new ArrayList<>());
            }
        }

    }


    class SecondViewHolder extends RecyclerView.ViewHolder {
        TextView tv_material_name;
        View tv_material_tag;
        TextView tv_paper_count;
        TextView tv_copy_count;
        RecyclerView rv_files;

        public SecondViewHolder(View itemView) {
            super(itemView);
            tv_material_name =  itemView.findViewById(R.id.tv_material_name);
            tv_material_tag = itemView.findViewById(R.id.tv_material_tag);
            tv_paper_count = itemView.findViewById(R.id.tv_paper_count);
            tv_copy_count = itemView.findViewById(R.id.tv_copy_count);
            rv_files = itemView.findViewById(R.id.rv_files);

        }
    }

    public void clearData() {
        if (list != null) {
            list.clear();
            notifyDataSetChanged();
        }
    }

}
