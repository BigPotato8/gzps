package com.augurit.agmobile.agwater5.gcjs.zcfg;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.zcfg.model.ZczyBean;
import com.augurit.agmobile.common.lib.validate.ListUtil;

import java.util.List;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.NavigationHolder> {
    private List<ZczyBean.ChildrenBean> data;
    private Context context;
    private OnNavigationClick click;

    public void setClick(OnNavigationClick click) {
        this.click = click;
    }

    public NavigationAdapter(Context context, List<ZczyBean.ChildrenBean> data) {
        super();
        this.context = context;
        this.data = data;
        if (!ListUtil.isEmpty(this.data)){
            data.get(0).setCheck(true);//默认选中第一个
        }
    }

    @Override
    public NavigationAdapter.NavigationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_zcfg_navigation_list, parent, false);
        return new NavigationHolder(view);
    }

    @Override
    public void onBindViewHolder(NavigationAdapter.NavigationHolder holder, int position) {
        if (data.get(position).isCheck()) {
            holder.tv_navigation.setBackgroundColor(context.getResources().getColor(R.color.agmobile_primary));
            holder.tv_navigation.setTextColor(context.getResources().getColor(R.color.colorWhite));
        } else {
            holder.tv_navigation.setBackgroundColor(context.getResources().getColor(R.color.bg_gray));
            holder.tv_navigation.setTextColor(0xFF333333);
        }
        holder.tv_navigation.setText(data.get(position).getName());

        holder.tv_navigation.setOnClickListener((v) -> {
            for (ZczyBean.ChildrenBean datum : data) {
                datum.setCheck(false);
            }
            data.get(position).setCheck(true);
            if (click != null) {
                click.onClick(data.get(position));
            }
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    class NavigationHolder extends RecyclerView.ViewHolder {
        TextView tv_navigation;

        public NavigationHolder(View itemView) {
            super(itemView);
            tv_navigation = itemView.findViewById(R.id.tv_navigation);
        }
    }

    public interface OnNavigationClick {
        void onClick(ZczyBean.ChildrenBean item);
    }
}
