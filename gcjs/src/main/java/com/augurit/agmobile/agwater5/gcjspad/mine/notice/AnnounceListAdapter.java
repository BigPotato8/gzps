package com.augurit.agmobile.agwater5.gcjspad.mine.notice;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.model.Announce;
import com.augurit.common.common.util.TimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Date;

/**
 * 公告adapter
 */
public class AnnounceListAdapter extends BaseQuickAdapter<Announce.RowsBean, BaseViewHolder> {

    public AnnounceListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Announce.RowsBean item) {
        helper.setText(R.id.tv_title,item.getContentTitle());
        helper.setText(R.id.tv_time, TimeUtil.getStringTimeYMDFromDate(new Date(item.getPublishTime())));
        helper.addOnClickListener(R.id.tv_operation);
    }
}
