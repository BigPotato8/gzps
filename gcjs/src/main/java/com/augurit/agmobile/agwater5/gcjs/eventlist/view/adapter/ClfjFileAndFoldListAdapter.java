package com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ClfjBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.ClfjListFragment;
import com.augurit.agmobile.common.lib.validate.ListUtil;

/**
 * 材料附件列表Adapter
 */
public class ClfjFileAndFoldListAdapter extends RecyclerView.Adapter<ClfjFileAndFoldListAdapter.ClfjListViewHolder> {
    private ClfjBean mClfjBean;
    private Context mContext;
    private ItemClickListener mItemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void setClfjBean(ClfjBean clfjBean, ClfjListFragment fragment) {
        mClfjBean = clfjBean;
        notifyDataSetChanged();
        fragment.showEmpty(getItemCount()==0);
    }

    public ClfjFileAndFoldListAdapter(Context ctx, ClfjBean clfjBean){
        mContext =ctx;
        mClfjBean = clfjBean;
    }


    @Override
    public ClfjListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_gcjs_clfj_list, parent, false);
        return new ClfjListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClfjListViewHolder mholder, int position) {
        if (mholder instanceof ClfjListViewHolder) {
            ClfjListViewHolder holder = (ClfjListViewHolder) mholder;
            if(getItemViewType(position)==0){//文件夹
                holder.iv_imageView.setImageResource(R.drawable.filepicker_ic_folder_gray_48dp);
                holder.tv_file_name.setText(mClfjBean.getDirs().get(position).getDirName());
            }else{//文件
                int a = ListUtil.isEmpty(mClfjBean.getDirs())?0:mClfjBean.getDirs().size();
                holder.iv_imageView.setImageResource(R.drawable.filepicker_ic_document_box);
                holder.tv_file_name.setText(mClfjBean.getFiles().get(position-a).getAttName());
                holder.tv_upload_name.setText(mClfjBean.getFiles().get(position-a).getCreaterName());
            }
            if (mItemClickListener!=null){
                holder.ll_content.setOnClickListener(v-> mItemClickListener.itemClick(position,mClfjBean));
                holder.ll_content.setOnLongClickListener(v -> {
                    mItemClickListener.itemLongClick(position,mClfjBean);
                    return false;
                });
            }
        }

    }

    public static class ClfjListViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_imageView;
        TextView tv_file_name;
        TextView tv_upload_name;
        LinearLayout ll_content;

        public ClfjListViewHolder(View itemView) {
            super(itemView);
            tv_file_name = itemView.findViewById(R.id.tv_file_name);
            tv_upload_name = itemView.findViewById(R.id.tv_upload_name);
            iv_imageView = itemView.findViewById(R.id.iv_imageView);
            ll_content = itemView.findViewById(R.id.ll_content);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int a = ListUtil.isEmpty(mClfjBean.getDirs())?0:mClfjBean.getDirs().size();
        if (a>position) {
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        if (mClfjBean==null) {
            return 0;
        }
        int a = ListUtil.isEmpty(mClfjBean.getDirs())?0:mClfjBean.getDirs().size();
        int b = ListUtil.isEmpty(mClfjBean.getFiles())?0:mClfjBean.getFiles().size();
        return a+b;
    }

    public interface ItemClickListener{
        void itemClick(int position, ClfjBean mClfjBean);
        void itemLongClick(int position, ClfjBean mClfjBean);
    }
}
