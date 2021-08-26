package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.single;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.single.model.Situation;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.common.lib.model.FileBean;
import com.augurit.agmobile.common.lib.validate.ListUtil;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent.uploadevent
 * Created by sdb on 2019/4/22  15:28.
 * Desc：
 */

public class SingleUploadEventThirdAdapter extends BaseViewListAdapter<Situation.AeaItemMatsBean> {


    public SingleUploadEventThirdAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public BaseDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SingleUploadEventThirdAdapter.LocalDraftViewHolder(View.inflate(mContext, R.layout.gcjs_public_upload_third_material_list_item, null));
    }

    @Override
    public void onBindViewHolder(BaseDataViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        SingleUploadEventThirdAdapter.LocalDraftViewHolder mHolder = (SingleUploadEventThirdAdapter.LocalDraftViewHolder) holder;
        mHolder.tv_material_name.setText(mDatas.get(position).getMatName());
        mHolder.tv_upload.setOnClickListener(v -> {
            if (mAttachmentClickListener != null) {
                mAttachmentClickListener.onClick(position + "");
            }
        });
        if (ListUtil.isEmpty(mDatas.get(position).getFileBeans())) {
            mHolder.ll_file_name.setVisibility(View.GONE);
            mHolder.tv_file_name.setText("");
        } else {
            mHolder.ll_file_name.setVisibility(View.VISIBLE);
            String name = "";
            for (FileBean fileBean : mDatas.get(position).getFileBeans()) {
                if (TextUtils.isEmpty(name)) {
                    name = name + fileBean.getName();
                } else {
                    name = name + "，" + fileBean.getName();
                }
            }
            mHolder.tv_file_name.setText(name);
        }
    }

    protected class LocalDraftViewHolder extends BaseDataViewHolder {
        TextView tv_material_name, tv_upload, tv_file_name;
        LinearLayout ll_file_name;

        public LocalDraftViewHolder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            tv_material_name = itemView.findViewById(R.id.tv_material_name);
            tv_upload = itemView.findViewById(R.id.tv_upload);
            tv_file_name = itemView.findViewById(R.id.tv_file_name);
            ll_file_name = itemView.findViewById(R.id.ll_file_name);
        }
    }

    public interface AttachmentClickListener {
        void onClick(String s);
    }

    private AttachmentClickListener mAttachmentClickListener;

    public void setAttachmentClickListener(AttachmentClickListener attachmentClickListener) {
        this.mAttachmentClickListener = attachmentClickListener;
    }
}
