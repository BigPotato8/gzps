package com.augurit.agmobile.agwater5.gcjs_public.bszn;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.ProjectDetailBean;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;


/**
 * @author 创建人 ：SDB
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzsz.facilityquery
 * @modifyMemo 修改备注：
 */

public class BsznAdapter extends BaseViewListAdapter<String> {


    public BsznAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.bszn_item_view, parent, false);
        return new LocalDraftViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        LocalDraftViewHolder mHolder = (LocalDraftViewHolder) holder;
       String [] arrays =  mDatas.get(position).split("###");
        String bean = mDatas.get(position);
        mHolder.tv_content.setText(mDatas.get(position).split("###")[1]);
    }

    protected class LocalDraftViewHolder extends BaseDataViewHolder {
        private TextView tv_content;
        public LocalDraftViewHolder(View itemView) {

            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        }
    }
}
