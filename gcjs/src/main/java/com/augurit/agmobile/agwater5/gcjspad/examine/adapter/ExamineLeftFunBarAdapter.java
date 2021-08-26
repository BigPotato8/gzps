package com.augurit.agmobile.agwater5.gcjspad.examine.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjspad.examine.model.TaskItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author 创建人 ：xiezhiwei
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.examine.adapter
 * @createTime 创建时间 ：2020/12/2
 * @modifyBy 修改人 ：xiezhiwei
 * @modifyTime 修改时间 ：2020/12/2
 * @modifyMemo 修改备注：
 */
public class ExamineLeftFunBarAdapter extends BaseQuickAdapter<TaskItemBean, BaseViewHolder> {
    //选中位置，默认为零
    private int mSelectPosition = 0;

    public ExamineLeftFunBarAdapter(int layoutResId, @Nullable List<TaskItemBean> data) {
        super(layoutResId, data);
    }

    public ExamineLeftFunBarAdapter(@Nullable List<TaskItemBean> data) {
        super(data);
    }

    public ExamineLeftFunBarAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, TaskItemBean item) {
        helper.setText(R.id.tv_left_fun_title, item.getPageKey());
        //选中状态
        if (helper.getAdapterPosition() == mSelectPosition) {
            helper.setBackgroundColor(R.id.ll_selected_bg, mContext.getResources().getColor(R.color.agmobile_blue));
            helper.setTextColor(R.id.tv_left_fun_title, Color.WHITE);
        } else {
            helper.setBackgroundColor(R.id.ll_selected_bg, mContext.getResources().getColor(R.color.white));
            helper.setTextColor(R.id.tv_left_fun_title, Color.BLACK);
        }
        if("0".equals(item.getPageTotal())){
            helper.setVisible(R.id.tv_num, false);
        }else {
            helper.setVisible(R.id.tv_num, true);
            helper.setText(R.id.tv_num, item.getPageTotal());
        }
    }

    public int getmSelectPosition() {
        return mSelectPosition;
    }

    public void setmSelectPosition(int mSelectPosition) {
        this.mSelectPosition = mSelectPosition;
    }

}
