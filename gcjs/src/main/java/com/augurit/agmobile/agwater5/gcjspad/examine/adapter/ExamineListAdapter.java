package com.augurit.agmobile.agwater5.gcjspad.examine.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjspad.examine.listener.OnTaskClickListener;
import com.augurit.agmobile.common.lib.time.TimeCompare;
import com.augurit.common.common.util.TimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.ParseException;
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
public class ExamineListAdapter extends BaseQuickAdapter<EventListItem.DataBean, BaseViewHolder> {
    private int type;//1.待办，2.已办，3.办结，4.我的办件 5.不予受理,6.申报预警,7.申报逾期
    private OnTaskClickListener mOnTaskClickListener;

    public ExamineListAdapter(int layoutResId, @Nullable List<EventListItem.DataBean> data) {
        super(layoutResId, data);
    }

    public ExamineListAdapter(@Nullable List<EventListItem.DataBean> data) {
        super(data);
    }

    public ExamineListAdapter(int layoutResId, int type) {
        super(layoutResId);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, EventListItem.DataBean item) {
        //申报阶段
        helper.setText(R.id.tv_apply_type, item.getApplyType() + "申报");
        //流水号
        helper.setText(R.id.tv_right_content_serial_num, "申报流水号：" + item.getApplyinstCode());
        //单位名称
        helper.setText(R.id.tv_unit_name, TextUtils.isEmpty(item.getApplyUnitName()) ? item.getLinkmanName() : item.getApplyUnitName());
        if (!TextUtils.isEmpty(item.getGcbm())) {
            helper.setText(R.id.tv_right_content_title, item.getProjName() + "(" + item.getGcbm() + ")");
        } else {
            helper.setText(R.id.tv_right_content_title, item.getProjName());
        }
        helper.setText(R.id.tv_examine_result, "");
        //承诺办结时间
        helper.setText(R.id.tv_commit_time, "承诺办结时间：" + (!TextUtils.isEmpty(item.getExpiryDate()) ? item.getExpiryDate() : "-"));
        //申报时间
        helper.setText(R.id.tv_upload_time, !TextUtils.isEmpty(item.getApplyTime()) ? item.getApplyTime() : "-");

        //剩余时间
        ProgressBar timeBar = helper.getView(R.id.sb_time_status);
        helper.setText(R.id.tv_commit_left_time, !TextUtils.isEmpty(item.getRemainingOrOverTimeText()) ? item.getRemainingOrOverTimeText() : "-");
        String overdueTime = item.getOverdueTime();
        String remainingTime = item.getRemainingTime();
        if (!TextUtils.isEmpty(overdueTime) && Double.parseDouble(overdueTime) > 0) {
            helper.setTextColor(R.id.tv_commit_left_time, Color.RED);
            timeBar.setProgress(100);
            timeBar.setProgressDrawable(mContext.getDrawable(R.drawable.agmobile_progress_bar_red));
        } else if (!TextUtils.isEmpty(remainingTime) && Double.parseDouble(remainingTime) <= 2) {
            helper.setTextColor(R.id.tv_commit_left_time, ResourcesCompat.getColor(mContext.getResources(), R.color.orange, null));
            timeBar.setProgressDrawable(mContext.getDrawable(R.drawable.agmobile_progress_bar_orange));
        } else if (!TextUtils.isEmpty(remainingTime) && Double.parseDouble(remainingTime) > 2) {
            helper.setTextColor(R.id.tv_commit_left_time, ResourcesCompat.getColor(mContext.getResources(), R.color.green, null));
            timeBar.setProgressDrawable(mContext.getDrawable(R.drawable.agmobile_progress_bar));
        }

        //剩余时间进度条
        if (!TextUtils.isEmpty(item.getApplyTime()) && !TextUtils.isEmpty(item.getExpiryDate()) && !TextUtils.isEmpty(item.getRemainingTime())) {
            TimeCompare timeCompare = null;
            try {
                timeCompare = TimeUtil.compareDate(item.getApplyTime(), item.getExpiryDate(), 0);
                int days = timeCompare.getDifference();
                if (!TextUtils.isEmpty(remainingTime) && Double.parseDouble(remainingTime) > 0) {
                    float reaminPresent = (float) (days - (int) Float.parseFloat(item.getRemainingTime())) / days * 100;
                    timeBar.setProgress((int) reaminPresent);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            timeBar.setVisibility(View.GONE);
        }

        //阶段名称
        helper.setText(R.id.tv_stage_name, item.getStageName());
        helper.setGone(R.id.tv_stage_name, !TextUtils.isEmpty(item.getStageName()));
        //事项名称
        helper.setText(R.id.tv_item_name, !TextUtils.isEmpty(item.getItemName()) ? item.getItemName() : "-");
        //签收状态
        helper.setGone(R.id.ll_status_bar, "0.0".equals(item.getSignState()));
        //办理按钮
        helper.setGone(R.id.btn_handle, "1.0".equals(item.getSignState()));
        //查收按钮
        helper.setGone(R.id.btn_check, "0.0".equals(item.getSignState()));
        //签收按钮
        helper.setGone(R.id.btn_sign, "0.0".equals(item.getSignState()));
        switch (type) {
            case 1:
                //审查决定
                helper.setText(R.id.tv_examine_result, item.getTaskName() + "(" + item.getApplyinstStateName() + ")");
                break;
            case 2:
                //审查决定
                helper.setText(R.id.tv_examine_result, item.getTaskName());
                break;
            case 5:
                //承诺办结时间
                helper.setText(R.id.tv_commit_time, "不予受理时间：" + (!TextUtils.isEmpty(item.getDismissedTime()) ? item.getDismissedTime() : "-"));
                break;
            case 7:
            default:
                break;
        }

        if (mOnTaskClickListener != null) {
            helper.setOnClickListener(R.id.btn_handle, v -> {
                mOnTaskClickListener.onHandleClick(item);
            });
            helper.setOnClickListener(R.id.btn_check, v -> {
                mOnTaskClickListener.onCheckClick(item);
            });
            helper.setOnClickListener(R.id.btn_sign, v -> {
                mOnTaskClickListener.onSignClick(item);
            });
        }
    }


    public OnTaskClickListener getmOnTaskClickListener() {
        return mOnTaskClickListener;
    }

    public void setmOnTaskClickListener(OnTaskClickListener mOnTaskClickListener) {
        this.mOnTaskClickListener = mOnTaskClickListener;
    }

}
