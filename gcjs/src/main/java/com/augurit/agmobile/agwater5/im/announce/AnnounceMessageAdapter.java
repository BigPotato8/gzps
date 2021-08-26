package com.augurit.agmobile.agwater5.im.announce;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.model.Announce;
import com.augurit.agmobile.common.im.timchat.utils.TimeUtil;

import java.util.List;

/**
 * 通知公告消息列表Adapter
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.im.announce
 * @createTime 创建时间 ：2019-06-11
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class AnnounceMessageAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Announce.RowsBean> mDatas;
    private OnItemClickListener onItemClickListener;

    public AnnounceMessageAdapter(Context context, List<Announce.RowsBean> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.announce_message_list_item, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        Announce.RowsBean data = mDatas.get(position);
        String time = TimeUtil.getTimeStr(data.getPublishTime() / 1000);
        holder.tv_time.setText(time);
        holder.tv_title.setText(data.getContentTitle());
        CharSequence content = "";
        if (!TextUtils.isEmpty(data.getContentText())) {
            content = Html.fromHtml(data.getContentText());
        }
        holder.tv_content.setText(content);
        holder.view_item.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position, data);
            }
        });
        return convertView;
    }

    class Holder {

        TextView tv_time;
        TextView tv_title;
        TextView tv_content;
        ViewGroup view_item;

        Holder(View itemView) {
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            view_item = itemView.findViewById(R.id.view_item);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, Announce.RowsBean data);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
