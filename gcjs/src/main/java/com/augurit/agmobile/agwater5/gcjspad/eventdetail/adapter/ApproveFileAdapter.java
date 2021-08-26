package com.augurit.agmobile.agwater5.gcjspad.eventdetail.adapter;

import android.support.annotation.Nullable;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.PwpfBean;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.model.ApproveFileModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.eventdetail.adapter
 * @createTime 创建时间 ：2020/12/17
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class ApproveFileAdapter extends BaseQuickAdapter<PwpfBean.ItemMatinstBean.AttFilesBean, BaseViewHolder> {


    public ApproveFileAdapter(int layoutResId, @Nullable List<PwpfBean.ItemMatinstBean.AttFilesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PwpfBean.ItemMatinstBean.AttFilesBean item) {
        helper.setText(R.id.tv_filename, item.getFileName());
        helper.addOnClickListener(R.id.iv_delete);
    }
}
