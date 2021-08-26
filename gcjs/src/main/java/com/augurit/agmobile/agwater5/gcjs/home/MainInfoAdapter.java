package com.augurit.agmobile.agwater5.gcjs.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.home.model.MainInfoModel;
import com.esri.core.geometry.Line;

import java.util.List;

/**
 * 包名：com.augurit.agmobile.agwater5.gcjs.home
 * 文件描述：
 * 创建人：limeijuan
 * 创建时间：2021/8/20 11:11
 * 修改人：limeijuan
 * 修改时间：2021/8/20 11:11
 * 修改备注：
 */
public class MainInfoAdapter extends RecyclerView.Adapter<MainInfoAdapter.MYViewHolder> {

    private final Context context;
    private final List<MainInfoModel> modelList;

    public MainInfoAdapter(Context context, List<MainInfoModel> modelList){
        this.context = context;
        this.modelList = modelList;
    }

    @Override
    public MYViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_info_item, parent, false);
        MYViewHolder holder = new MYViewHolder(view);

        int parentHeight = parent.getHeight();
        holder.ll_info_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,MainNoticeActivity.class);
                intent.putExtra("status",1);//1：待办消息 0：通知公告
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MYViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class MYViewHolder extends RecyclerView.ViewHolder{

        LinearLayout ll_info_item;
        public MYViewHolder(View itemView) {
            super(itemView);
            ll_info_item = itemView.findViewById(R.id.ll_info_item);
        }
    }
}
