package com.augurit.agmobile.agwater5.common.view;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;

import java.util.ArrayList;
import java.util.List;

import skin.support.content.res.SkinCompatResources;

/**
 * 统计数量头部，用于列表头部的数量展示、点击筛选
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.common.view
 * @createTime 创建时间 ：2018/9/6
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class NumberHeaderView extends RelativeLayout {

    protected RecyclerView rv_items;
    protected ItemsAdapter mAdapter;

    protected List<Item> mItems;
    protected Item mItemSelected;
    protected OnItemSelectListener onItemSelectListener;

    protected boolean mAllowCancelSelection = false;    // 是否允许再次点击取消选择

    public NumberHeaderView(Context context) {
        super(context);
        initView();
    }

    public NumberHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public NumberHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    protected void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.common_view_number_header, this);
        rv_items = findViewById(R.id.rv_head_menu_items);

        mItems = new ArrayList<>();
        rv_items.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mAdapter = new ItemsAdapter();
        rv_items.setAdapter(mAdapter);
    }

    public void setItems(List<Item> items) {
        mItems.clear();
        mItems.addAll(items);
        rv_items.setLayoutManager(new GridLayoutManager(getContext(), items.size()));
        mAdapter.notifyDataSetChanged();
    }

    public void setValues(List<String> values) {
        if (!mItems.isEmpty()) {
            for (int i = 0; i < mItems.size(); i++) {
                mItems.get(i).value = values.get(i);
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public void setSelection(Item item) {
        mItemSelected = item;
        mAdapter.notifyDataSetChanged();
    }

    public void setSelection(int position) {
        if (mItems.isEmpty()) return;
        mItemSelected = mItems.get(position);
        mAdapter.notifyDataSetChanged();
    }

    public void clearSelection() {
        mItemSelected = null;
        mAdapter.notifyDataSetChanged();
    }

    public static class Item {

        private  String label;
        private  String value;
        @ColorRes
        private   int colorRes = R.color.agmobile_text_body;

        public Item(String label, String value) {
            this.label = label;
            this.value = value;
        }

        public Item(String label, String value, @ColorRes int colorRes) {
            this.label = label;
            this.value = value;
            this.colorRes = colorRes;
        }

    }

    public interface OnItemSelectListener {
        void onItemSelect(Item item, int position, boolean isSelected);
    }

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }

    class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemHolder> {

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.common_view_number_header_item, parent, false);
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            Item item = mItems.get(position);
            holder.tv_label.setText(item.label);
            holder.tv_value.setText(item.value);
            int color = SkinCompatResources.getColor(getContext(), item.colorRes);
            holder.tv_label.setTextColor(color);
            holder.tv_value.setTextColor(color);

            if (position == 0) {
                holder.view_divider.setVisibility(GONE);
            } else {
                holder.view_divider.setVisibility(VISIBLE);
            }

            boolean selected = mItemSelected != null && item.label.equals(mItemSelected.label);
            if (selected) {
                holder.itemView.setBackgroundColor(SkinCompatResources.getColor(getContext(), R.color.common_head_item_selected));
            } else {
                holder.itemView.setBackgroundColor(SkinCompatResources.getColor(getContext(), R.color.common_head_item_normal));
            }

            holder.itemView.setOnClickListener(v -> {
                if (onItemSelectListener != null) {
                    if (selected) {
                        if (mAllowCancelSelection) {
                            mItemSelected = null;
                        } else {
                            return;
                        }
                    } else {
                        mItemSelected = item;
                    }
                    notifyDataSetChanged();
                    onItemSelectListener.onItemSelect(item, position, !selected);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        class ItemHolder extends RecyclerView.ViewHolder {

            View view_divider;
            TextView tv_label;
            TextView tv_value;

            public ItemHolder(View itemView) {
                super(itemView);
                view_divider = itemView.findViewById(R.id.view_divider);
                tv_label = itemView.findViewById(R.id.tv_label);
                tv_value = itemView.findViewById(R.id.tv_value);
            }
        }
    }

}
