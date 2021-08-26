package com.augurit.agmobile.agwater5.gcjspad.homepage.adapter;

import android.view.View;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.GcjsContract;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjspad.widget.StageItemView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.homepage.adapter
 * @createTime 创建时间 ：2020/12/2
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class HandlingListAdapter extends BaseQuickAdapter<EventListItem.DataBean, BaseViewHolder> {


    public HandlingListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, EventListItem.DataBean item) {
        helper.setText(R.id.tv_message_source, item.getApplySource());
        helper.setText(R.id.tv_message_type, item.getApplyType());
        helper.setText(R.id.tv_serial_number, item.getApplyinstCode());
        helper.setText(R.id.tv_project_name, item.getProjName());
        if (item.getItemName()!=null) {
            helper.setVisible(R.id.tv_stage_name,true);
            helper.setVisible(R.id.view_stage_name,false);
            helper.setText(R.id.tv_stage_name, item.getItemName());
        }else if(item.getStageName()!=null){
            int stageIndex = 0;
            if (item.getStageName().contains(GcjsConstant.STAGE_NAME_1)) {
                stageIndex = 1;
            }else if (item.getStageName().contains(GcjsConstant.STAGE_NAME_2)) {
                stageIndex = 2;
            }else if (item.getStageName().contains(GcjsConstant.STAGE_NAME_3)) {
                stageIndex = 3;
            }else if (item.getStageName().contains(GcjsConstant.STAGE_NAME_4)) {
                stageIndex = 4;
            }
            if (stageIndex==0) {
                helper.setVisible(R.id.tv_stage_name,true);
                helper.setVisible(R.id.view_stage_name,false);
                helper.setText(R.id.tv_stage_name, item.getStageName());
            }else {
                helper.setVisible(R.id.tv_stage_name, false);
                helper.setVisible(R.id.view_stage_name, true);
                StageItemView stageItemView = helper.getView(R.id.view_stage_name);

                stageItemView.setAttr(item.getStageName(), 0xffffffff, 4, stageIndex, 0xff26bd7f, 0xff169aff,
                        0xffdcdfe6, 0xffffffff, 15
                );
            }
        }else{
            helper.setVisible(R.id.tv_stage_name,false);
            helper.setVisible(R.id.view_stage_name,false);
        }

        helper.setText(R.id.tv_controller, item.getTaskName());
        if ("1.0".equals(item.getSignState())){//已签收
            helper.setImageResource(R.id.iv_message_receive,R.drawable.icon_message_opened);
        }else{
            helper.setImageResource(R.id.iv_message_receive,R.drawable.icon_message_closed);
        }

    }
}
