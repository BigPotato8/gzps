package com.augurit.agmobile.agwater5.gcjs.eventlist.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventState.FINISHED;
import static com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventState.AFFAIR;
import static com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventState.DRAFT;
import static com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventState.HANDLED;
import static com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventState.HANDLING;
import static com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventState.UPLOADED;
import static com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventState.DEPARTMENT_ALL;


/**
 * 事件状态
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.drainage.eventlist.view
 * @createTime 创建时间 ：2018/9/4
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
@IntDef({HANDLING, HANDLED, FINISHED, UPLOADED, AFFAIR, DRAFT, DEPARTMENT_ALL})
@Retention(RetentionPolicy.SOURCE)
public @interface EventState {

    int HANDLING = 1;

    int HANDLED = 6;

    int FINISHED = 3;

    int UPLOADED = 4;

    int AFFAIR = 5;
    int DRAFT = 7;

    int DEPARTMENT_ALL=8;//部门所有
}
