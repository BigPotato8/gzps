package com.augurit.agmobile.agwater5.im.work;

import android.widget.ImageView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.im.work.model.PubItemChild;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.common.im.timchat.ui.adapter
 * @createTime 创建时间 ：2019/8/1
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class PublicItemNewsAdapter extends BaseQuickAdapter<PubItemChild, BaseViewHolder> {

    public PublicItemNewsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PubItemChild item) {
        helper.setText(com.augurit.agmobile.common.im.R.id.tv_title, item.getTitle());
        Glide.with(mContext)
                .load(R.drawable.banner1)
                .into((ImageView) helper.getView(com.augurit.agmobile.common.im.R.id.iv_image));
        if (helper.getAdapterPosition() == getData().size()) {
            helper.setGone(com.augurit.agmobile.common.im.R.id.view_divider, false);
        } else {
            helper.setGone(com.augurit.agmobile.common.im.R.id.view_divider, true);
        }
    }
}
