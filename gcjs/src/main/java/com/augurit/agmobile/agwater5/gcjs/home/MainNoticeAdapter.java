package com.augurit.agmobile.agwater5.gcjs.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.home.model.MainNoticeModel;

import java.util.List;

/**
 * 包名：com.augurit.agmobile.agwater5.gcjs
 * 文件描述：
 * 创建人：limeijuan
 * 创建时间：2021/8/19 11:41
 * 修改人：limeijuan
 * 修改时间：2021/8/19 11:41
 * 修改备注：
 */
public class MainNoticeAdapter extends RecyclerView.Adapter<MainNoticeAdapter.MYViewHolder>{

    private final Context context;
    private final List<MainNoticeModel> noticeModelList;
    private final int status; // 0代表通知公告 1代表更新清单详情

    public MainNoticeAdapter(Context context, List<MainNoticeModel> noticeModelList,int stauts){
        this.context = context;
        this.noticeModelList =noticeModelList;
        this.status = stauts;
    }
    @Override
    public MYViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_notice_item, parent,false);
        return new MYViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MYViewHolder holder, int position) {
        if(holder instanceof MYViewHolder){
            MainNoticeModel model = noticeModelList.get(position);
            if(status == 1){
                holder.ll_notice.setPadding(10,0,10,0);
                holder.tv_notice_adapter_title.setCompoundDrawables(null,null,null,null);
                holder.tv_notice_adapter_num.setVisibility(View.GONE);
                holder.tv_notice_adapter_title.setText((position+1)+"."+model.getTitle());
                holder.tv_notice_adapter_title.setPadding(10,0,10,5);
                holder.tv_notice_adapter_title.setTextSize(15);
                holder.notice_divider.setVisibility(View.GONE);
            } else if (status == 0) {
                holder.ll_notice.setOnClickListener((v) -> {
                    Intent intent = new Intent(context,NoticeDetailActivity.class);
                    context.startActivity(intent);
                });
                holder.tv_notice_adapter_title.setText(model.getTitle());
                holder.tv_notice_adapter_num.setText(String.valueOf(position+1));
            }

        }
    }

    @Override
    public int getItemCount() {
        return noticeModelList.size();
    }

    class MYViewHolder extends RecyclerView.ViewHolder{
        TextView tv_notice_adapter_num;
        TextView tv_notice_adapter_title;
        LinearLayout ll_notice;
        View notice_divider;
        public MYViewHolder(View itemView) {
            super(itemView);
            tv_notice_adapter_num = itemView.findViewById(R.id.tv_notice_adapter_num);
            tv_notice_adapter_title = itemView.findViewById(R.id.tv_notice_adapter_title);
            ll_notice = itemView.findViewById(R.id.ll_notice);
            notice_divider = itemView.findViewById(R.id.notice_divider);
        }
    }
}
