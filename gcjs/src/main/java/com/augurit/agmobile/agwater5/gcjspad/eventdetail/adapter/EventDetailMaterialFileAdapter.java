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
public class EventDetailMaterialFileAdapter extends RecyclerView.Adapter<EventDetailMaterialFileAdapter.SecondViewHolder> {
    private Context context;
    private List<MaterialListBean.FileListBean> list;

    public void setList(List<MaterialListBean.FileListBean> listA) {
        list = listA;
        notifyDataSetChanged();
    }

    public EventDetailMaterialFileAdapter(Context context, List<MaterialListBean.FileListBean> listA) {
        this.context = context;
        list = listA;
    }

    @Override
    public EventDetailMaterialFileAdapter.SecondViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event_detail_material_file_pad, parent, false);
        return new EventDetailMaterialFileAdapter.SecondViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onBindViewHolder(EventDetailMaterialFileAdapter.SecondViewHolder holder, int position) {
        holder.tv_title.setText(list.get(position).getFileName());
        holder.ll_content.setOnClickListener(v ->{
            MaterialListBean.FileListBean attFilesBean = list.get(position);
            FilePreviewUtil.downFile(context, attFilesBean.getFileName(),attFilesBean.getFileType(),attFilesBean.getFileId());
        });
    }


    class SecondViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        View ll_content;
        public SecondViewHolder(View itemView) {
            super(itemView);
            tv_title =  itemView.findViewById(R.id.tv_title);
            ll_content =  itemView.findViewById(R.id.ll_content);

        }
    }

    public void clearData() {
        if (list != null) {
            list.clear();
            notifyDataSetChanged();
        }
    }

}
