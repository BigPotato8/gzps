package com.augurit.agmobile.agwater5.gcjspad.eventdetail.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventProcess;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 创建人 ：xiezhiwei
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.eventdetail.adapter
 * @createTime 创建时间 ：2020/12/9
 * @modifyBy 修改人 ：xiezhiwei
 * @modifyTime 修改时间 ：2020/12/9
 * @modifyMemo 修改备注：审批详情适配器
 */
public class ApproveSetpItemOutterAdapter extends BaseQuickAdapter<EventProcess, BaseViewHolder> {
    public ApproveSetpItemOutterAdapter(int layoutResId, @Nullable List<EventProcess> data) {
        super(layoutResId, data);
    }

    public ApproveSetpItemOutterAdapter(@Nullable List<EventProcess> data) {
        super(data);
    }

    public ApproveSetpItemOutterAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder helper, int position) {
        EventProcess item = mData.get(position);

        super.onBindViewHolder(helper, position);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventProcess item) {
        RecyclerView recyclerView = helper.getView(R.id.rv_approve_node_list);
        helper.setText(R.id.tv_approve_title, item.getNodeName());
        helper.setImageResource(R.id.iv_approve_node, R.drawable.agmobile_node_hole);
        //审批状态
        switch (item.getTaskState()) {
            case 0:
            case 1:
                helper.setText(R.id.tv_approve_status, "办理中");
                helper.setBackgroundRes(R.id.tv_approve_status, R.drawable.agmobile_btn_line);
                break;
            case 2:
                helper.setText(R.id.tv_approve_status, "已完成");
                helper.setBackgroundRes(R.id.tv_approve_status, R.drawable.agmobile_btn_line_green);
                helper.setImageResource(R.id.iv_approve_node, R.drawable.agmobile_node_fill);
                break;
            default:
                break;
        }
        //右侧子项显示隐藏按钮
        helper.setOnClickListener(R.id.iv_approve_node_list, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(recyclerView.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                helper.getView(R.id.iv_approve_node_list).setRotation(recyclerView.getVisibility() == View.VISIBLE ? 0 : 270);
            }
        });
        recyclerView.setVisibility(View.VISIBLE);
        ApproveSetpItemInnerAdapter approveSetpItemInnerAdapter;
        if (item.getChildNode() != null && item.getChildNode().size() > 0) {
            approveSetpItemInnerAdapter = new ApproveSetpItemInnerAdapter(R.layout.item_approve_setp_detail, item.getChildNode());
        } else {
            List<EventProcess> singleList = new ArrayList<>();
            singleList.add(item);
            approveSetpItemInnerAdapter = new ApproveSetpItemInnerAdapter(R.layout.item_approve_setp_detail, singleList);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(approveSetpItemInnerAdapter);


    }
}
