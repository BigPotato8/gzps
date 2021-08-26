package com.augurit.agmobile.agwater5.gcjspad.homepage.adapter;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.model.Announce;
import com.augurit.common.common.util.TimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Date;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.homepage.adapter
 * @createTime 创建时间 ：2020/12/2
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class AnnounceListAdapter extends BaseQuickAdapter<Announce.RowsBean, BaseViewHolder> {

    public AnnounceListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Announce.RowsBean item) {
        helper.setText(R.id.tv_announce_title,item.getContentTitle());
        helper.setText(R.id.tv_announce_data, TimeUtil.getStringTimeYMD(new Date(item.getPublishTime())));
    }
}
