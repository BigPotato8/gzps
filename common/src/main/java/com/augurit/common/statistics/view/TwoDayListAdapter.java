package com.augurit.common.statistics.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.common.R;
import com.augurit.common.statistics.model.TwoDayReportInfo;

import java.text.DecimalFormat;
import java.util.List;


/**
 * @author : taoerxiang
 * @data : 2017-11-11  12:04
 * @des :
 */

public class TwoDayListAdapter extends RecyclerView.Adapter<TwoDayListAdapter.MyViewHolder> {

    private Context context;
    private List<TwoDayReportInfo.ToDayEntity> toDay;
    private List<TwoDayReportInfo.YestDayEntity> yestDay;
    private LayoutInflater mInflater;

    public TwoDayListAdapter(Context context) {
        this.context = context;
    }

    public void refresh(List<TwoDayReportInfo.YestDayEntity> yestDay, List<TwoDayReportInfo.ToDayEntity> toDay) {
        this.toDay = toDay;
        this.yestDay = yestDay;
        notifyDataSetChanged();
    }


    @Override
    public TwoDayListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(context);
        View itemView = mInflater.inflate(R.layout.upload_statisc_lt_item, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        TwoDayReportInfo.ToDayEntity toDayEntity = toDay.get(position);
        TwoDayReportInfo.YestDayEntity yestdayEntity = yestDay.get(position);
        String areaName = toDayEntity.getName();
        if (areaName.contains("净水")) {
            holder.distric_tv.setText("净水公司");
        } else {
            holder.distric_tv.setText(areaName.substring(0, 2));//大于四个字的截取前2个字
        }
        holder.yd_all_tv.setText(getFormatInt((yestdayEntity.getLackCount() + yestdayEntity.getCorrCount())) + "");
        holder.yd_new_tv.setText(getFormatInt(yestdayEntity.getLackCount()) + "");
        holder.yd_correct_tv.setText(getFormatInt(yestdayEntity.getCorrCount()) + "");
        holder.td_all_tv.setText(getFormatInt(toDayEntity.getLackCount() + toDayEntity.getCorrCount()) + "");
        holder.td_new_tv.setText(getFormatInt(toDayEntity.getLackCount()) + "");
        holder.td_correct_tv.setText(getFormatInt(toDayEntity.getCorrCount()) + "");
    }

    private String getFormatInt(int n) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(n);
    }

    @Override
    public int getItemCount() {
        return ListUtil.isEmpty(toDay) ? 0 : toDay.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView distric_tv;
        TextView yd_all_tv;
        TextView yd_new_tv;
        TextView yd_correct_tv;
        TextView td_all_tv;
        TextView td_new_tv;
        TextView td_correct_tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            distric_tv = itemView.findViewById(R.id.upload_statisc_distrc);
            yd_all_tv = itemView.findViewById(R.id.yd_all);
            yd_new_tv = itemView.findViewById(R.id.yd_new);
            yd_correct_tv = itemView.findViewById(R.id.yd_correct);
            td_all_tv = itemView.findViewById(R.id.td_all);
            td_new_tv = itemView.findViewById(R.id.td_new);
            td_correct_tv = itemView.findViewById(R.id.td_correct);
        }
    }


}
