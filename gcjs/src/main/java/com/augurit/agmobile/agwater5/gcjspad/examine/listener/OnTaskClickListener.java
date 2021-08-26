package com.augurit.agmobile.agwater5.gcjspad.examine.listener;

import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;

/**
 * @author 创建人 ：xiezhiwei
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.examine.listener
 * @createTime 创建时间 ：2020/12/17
 * @modifyBy 修改人 ：xiezhiwei
 * @modifyTime 修改时间 ：2020/12/17
 * @modifyMemo 修改备注：
 */
public interface OnTaskClickListener {
    /**
     * 查看按钮
     *
     * @param item
     */
    void onCheckClick(EventListItem.DataBean item);

    /**
     * 签收按钮
     *
     * @param item
     */
    void onSignClick(EventListItem.DataBean item);

    /**
     * 办理按钮
     *
     * @param item
     */
    void onHandleClick(EventListItem.DataBean item);
}
