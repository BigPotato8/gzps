package com.augurit.common.common.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.common.lib.ui.ClickUtil;
import com.augurit.common.R;
import com.augurit.common.common.BaseWebViewActivity;
import com.augurit.common.common.manager.IntentConstant;
import com.augurit.common.common.model.MenuItem;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * 头部菜单View
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.common.view.adapter
 * @createTime 创建时间 ：2018/8/20
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/8/20
 * @modifyMemo 修改备注：
 */
public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MenuHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<MenuItem> mItems;
    private HashMap<Integer, Object> mBadgeMap;
    private int mItemLayout;
    private OnItemClickListener mOnItemClickListener;
    private int mIconTextColor;

    public MenuItemAdapter(Context context, List<MenuItem> items) {
        this(context, items, R.layout.common_view_header_menu_item);
    }

    public MenuItemAdapter(Context context, List<MenuItem> items, int itemLayout) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mItems = items;
        mItemLayout = itemLayout;
        mBadgeMap = new HashMap<>();
        mIconTextColor = -1;
    }

    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(mItemLayout, parent, false);
        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuHolder holder, int position) {
        MenuItem menuItem = mItems.get(position);
        if (menuItem.getIconUrl() != null) {
            Glide.with(mContext).load(menuItem.getIconUrl()).into(holder.iv_icon);
            holder.iv_icon.setVisibility(View.VISIBLE);
        } else if (menuItem.getIconRes() != -1) {
            holder.iv_icon.setImageResource(menuItem.getIconRes());
            holder.iv_icon.setVisibility(View.VISIBLE);
        } else {
            holder.iv_icon.setVisibility(View.GONE);
        }

        holder.tv_name.setText(menuItem.getName());
        if (mIconTextColor != -1) {
            holder.tv_name.setTextColor(mIconTextColor);
        }

        if (mBadgeMap.containsKey(position)) {
            Object o = mBadgeMap.get(position);
            if(holder.badge == null) {
                holder.badge = new QBadgeView(mContext)
                        .bindTarget(holder.iv_tag)
                        .setShowShadow(false);
            }
            if (o instanceof String) {
                holder.badge.setBadgeText((String) o);
            } else if (o instanceof Integer) {
                holder.badge.setBadgeNumber((Integer) o);
            }
            holder.itemView.setTag(holder.badge);
        } else {
            Object tag = holder.itemView.getTag();
            if (tag != null && tag instanceof Badge) {
                ((Badge) tag).setBadgeText("");
                ((Badge) tag).setBadgeNumber(0);
            }
        }

        ClickUtil.bind(holder.itemView, v -> {
            boolean consumed = false;
            if (mOnItemClickListener != null) {
                consumed = mOnItemClickListener.onItemClick(position, menuItem);
            }
            if (!consumed) {
                if (menuItem.getClazz() != null) {
                    Intent intent = new Intent(mContext, menuItem.getClazz());
                    if (menuItem.getParams() != null) {
                        for (Map.Entry<String, Serializable> entry : menuItem.getParams().entrySet()) {
                            intent.putExtra(entry.getKey(), entry.getValue());
                        }
                    }
                    mContext.startActivity(intent);
                } else if (menuItem.getUrl() != null) {
                    Intent intent = new Intent(mContext, BaseWebViewActivity.class);
                    intent.putExtra(IntentConstant.AW_DISPATCH_URL,menuItem.getUrl());
                    intent.putExtra(IntentConstant.AW_DISPATCH_TITLE,menuItem.getName());
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setBadge(int position, int badge) {
        mBadgeMap.put(position, badge);
    }

    public void setBadge(int position, String badge) {
        mBadgeMap.put(position, badge);
    }

    public void setIconTextColor(int color) {
        mIconTextColor = color;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {

        /**
         * 菜单项点击
         * @param position 位置
         * @param item 菜单项
         * @return true 已处理点击事件 false 未处理点击事件，此时将会采取默认处理方式处理
         */
        boolean onItemClick(int position, MenuItem item);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    class MenuHolder extends RecyclerView.ViewHolder {
        Badge badge;//角数量
        ImageView iv_icon;
        ImageView iv_tag;
        TextView tv_name;

        MenuHolder(View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            iv_tag = itemView.findViewById(R.id.iv_tag);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }

}