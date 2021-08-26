package com.augurit.agmobile.agwater5.common.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventClbzItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 材料附件列表Adapter
 */
public class DoMorePopupAdapter extends RecyclerView.Adapter<DoMorePopupAdapter.ViewHolder> {
    private List<String> list;
    private Context mContext;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setList(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public DoMorePopupAdapter(Context ctx) {
        mContext = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_domore_popup, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            holder.btn_item_name.setText(list.get(position));

            holder.btn_item_name.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(holder.btn_item_name, position);
                }
            });

        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button btn_item_name;

        public ViewHolder(View itemView) {
            super(itemView);
            btn_item_name = itemView.findViewById(R.id.btn_item_name);
        }
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public interface ItemClickListener {
        void onItemClick(Button button, int position);
    }

}
