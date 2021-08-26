package com.augurit.agmobile.agwater5.gcjspad.eventdetail.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.common.FilePreviewUtil;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.PwpfBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ZjzzBean;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.FileDeleteListener;
import com.augurit.agmobile.common.lib.time.TimeUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.eventdetail.adapter
 * @createTime 创建时间 ：2020/12/16
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class ApproveMaterialAdapter extends BaseQuickAdapter<ZjzzBean, BaseViewHolder> {
    private FileDeleteListener deleteListener;

    public ApproveMaterialAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ZjzzBean item) {
        RecyclerView recyclerView = helper.getView(R.id.rv_files);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        TextView tv_no_files = helper.getView(R.id.tv_no_files);
        helper.setText(R.id.tv_num, String.valueOf(helper.getAdapterPosition() + 1));
        helper.setText(R.id.tv_title, item.getCertinstName());
        helper.setText(R.id.tv_code, item.getCertinstCode());
        helper.setText(R.id.tv_create_user, item.getCreater());
        try {
            helper.setText(R.id.tv_create_time, TimeUtil.parseTimeStamp(Long.parseLong(item.getCreateTime())));
        }catch (Exception e){
            helper.setText(R.id.tv_create_time, "-");
        }
        if("0".equals(item.getPickUpStatus())){
            helper.setText(R.id.tv_receive_state, "部分领取");
        }else if("1".equals(item.getPickUpStatus())){
            helper.setText(R.id.tv_receive_state, "已领取");
        }else {
            helper.setText(R.id.tv_receive_state, "未领取");
        }
        helper.setText(R.id.tv_receive_time, !TextUtils.isEmpty(item.getPickUpTime()) ? item.getPickUpTime() : "-");
        helper.setText(R.id.tv_notify_time, !TextUtils.isEmpty(item.getCertArrivedTime()) ? item.getCertArrivedTime() : "-");
        helper.setOnClickListener(R.id.ll_num, v -> {
            recyclerView.setVisibility(recyclerView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            helper.getView(R.id.iv_arrow).setRotation(recyclerView.getVisibility() == View.VISIBLE ? 0 : 270);
        });
        helper.addOnClickListener(R.id.tv_delete);
        if (!ListUtil.isEmpty(item.getAttFiles())) {
            ApproveFileAdapter2 adapter = new ApproveFileAdapter2(R.layout.layout_approve_file_item, item.getAttFiles());
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener((adapter1, view, position) -> {
                ZjzzBean.AttFilesBean attFilesBean = (ZjzzBean.AttFilesBean) adapter1.getItem(position);
                FilePreviewUtil.downFile(mContext, attFilesBean.getFileName(),attFilesBean.getFileType(),attFilesBean.getFileId());
            });
            adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (deleteListener != null) {
                        deleteListener.onFileDelete(adapter, view, position, item);
                    }
                }
            });
            tv_no_files.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            tv_no_files.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }

    public void setDeleteListener(FileDeleteListener listener) {
        this.deleteListener = listener;
    }
}
