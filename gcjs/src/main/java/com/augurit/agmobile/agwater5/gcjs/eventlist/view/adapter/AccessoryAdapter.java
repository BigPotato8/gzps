package com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter;


import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.common.lib.model.FileBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agmobile5.bpm.view.adapter
 * @createTime 创建时间 ：2019/2/14
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class AccessoryAdapter extends BaseQuickAdapter<FileBean,BaseViewHolder> {

    public AccessoryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FileBean item) {
        helper.setText(R.id.tv_title,item.getName());
        helper.setImageResource(R.id.iv_imageView,item.getIcon());
        helper.addOnClickListener(R.id.ll_delete);
        helper.addOnClickListener(R.id.ll_content);
        helper.setVisible(R.id.tv_flag_upload,item.isUploaded());
    }
}
