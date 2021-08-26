package com.augurit.common.im.view.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.common.R;
import com.augurit.common.im.model.FriendItem;
import com.augurit.common.im.view.widget.SelectableRoundedImageView;

import java.util.List;


/**
 * @author 创建人 ：taoerxiang
 * @version zs.0
 * @package 包名 ：com.augurit.agmobile.gzps.im.adapter
 * @createTime 创建时间 ：2017-11-09
 * @modifyBy 修改人 ：taoerxiang
 * @modifyTime 修改时间 ：2017-11-09
 * @modifyMemo 修改备注：
 */

public class MyFriendListAdapter extends RecyclerView.Adapter<MyFriendListAdapter.MyViewHolder> {
    private List<FriendItem> friendList;
    private Context context;

    public MyFriendListAdapter(FragmentActivity activity, List<FriendItem> friendList) {
        this.friendList = friendList;
        this.context = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.friend_item, null);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvName.setText(friendList.get(position).getName());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(friendList.get(position)));
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        SelectableRoundedImageView mImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.frienduri);
            tvName =itemView.findViewById(R.id.friendName);
        }
    }

    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(FriendItem friendItem);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
