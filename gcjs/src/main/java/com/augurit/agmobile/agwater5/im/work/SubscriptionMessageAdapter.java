package com.augurit.agmobile.agwater5.im.work;


import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.im.work.model.PubItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.sample.im.subscription
 * @createTime 创建时间 ：2019/8/5
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class SubscriptionMessageAdapter extends BaseQuickAdapter<PubItem, BaseViewHolder> {

    public SubscriptionMessageAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PubItem item) {
        PublicItemNewsView publicItemNewsView = helper.getView(R.id.public_item_news_view);
        publicItemNewsView.setPublicItem(item,true);
        publicItemNewsView.requestLayout();
        publicItemNewsView.setOnChildItemClickListener(pubItemChild -> {

        });
        publicItemNewsView.setOnHeaderClickListener(pubItemChild -> {

        });
    }
}
