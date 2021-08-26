package com.augurit.agmobile.agwater5.gcjspad.eventdetail.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventProcess;
import com.augurit.agmobile.common.lib.time.TimeCompare;
import com.augurit.agmobile.common.lib.time.TimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @author 创建人 ：xiezhiwei
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.eventdetail.adapter
 * @createTime 创建时间 ：2020/12/9
 * @modifyBy 修改人 ：xiezhiwei
 * @modifyTime 修改时间 ：2020/12/9
 * @modifyMemo 修改备注：审批详情小项适配器
 */
public class ApproveSetpItemInnerAdapter extends BaseQuickAdapter<EventProcess, BaseViewHolder> {
    public ApproveSetpItemInnerAdapter(int layoutResId, @Nullable List<EventProcess> data) {
        super(layoutResId, data);
    }

    public ApproveSetpItemInnerAdapter(@Nullable List<EventProcess> data) {
        super(data);
    }

    public ApproveSetpItemInnerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventProcess item) {
        //审批状态
        switch (item.getTaskState()) {
            case 0:
                helper.setText(R.id.tv_comment_result, "未签收");
                break;
            case 1:
                helper.setText(R.id.tv_comment_result, "办理中");
                break;
            case 2:
                helper.setText(R.id.tv_comment_result, "已完成");
                break;
            default:
                break;
        }
        //节点名
        helper.setText(R.id.tv_node_name, TextUtils.isEmpty(item.getNodeName()) ? "-" : item.getNodeName());
        helper.setText(R.id.tv_org_name, TextUtils.isEmpty(item.getOrgName()) ? "-" : item.getOrgName());
        //办理人员
        helper.setText(R.id.tv_task_assignee, TextUtils.isEmpty(item.getTaskAssignee()) ? "-" : item.getTaskAssignee());
        helper.setGone(R.id.btn_more_assignee, item.getTaskAssignee() != null && item.getTaskAssignee().length() >= 15);
        //是否有办理意见
        if (TextUtils.isEmpty(item.getCommentMessage())) {
            helper.setText(R.id.tv_comment_msg, "-");
            helper.setText(R.id.tv_comment_content, "-");
        } else {
            helper.setText(R.id.tv_comment_content, item.getCommentMessage());
            helper.setText(R.id.tv_comment_msg, TextUtils.equals(item.getApproveResult(), "1") ? "[通过]" : "[未通过]");
            helper.setTextColor(R.id.tv_comment_msg, TextUtils.equals(item.getApproveResult(), "1") ? Color.GREEN : Color.RED);
        }
        //办理时间
        helper.setText(R.id.tv_signin_time, item.getEndDate() != null ? TimeUtil.parseTimeStamp(Long.parseLong(item.getEndDate())) : "-");
        //办理用时
        if (item.getEndDate() != null && item.getSigeInDate() != null) {
            String signinTime = TimeUtil.parseTimeStamp(Long.parseLong(item.getSigeInDate()));
            String endTime = TimeUtil.parseTimeStamp(Long.parseLong(item.getEndDate()));
            try {
                TimeCompare timeCompare = com.augurit.common.common.util.TimeUtil.compareDate(signinTime, endTime, 0);
                helper.setText(R.id.tv_total_time, timeCompare.getDifference() + "天");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }
}
