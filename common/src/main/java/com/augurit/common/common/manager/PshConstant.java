package com.augurit.common.common.manager;

/**
 * com.augurit.agmobile.agwater5.psh.common
 * Created by sdb on 2019/2/21  10:50.
 * Desc：
 */

public class PshConstant {


    /**
     * baseurl
     */
    public static final String BASE_URL = "http://139.159.243.185:8081/agsupport_swj/";

    /**
     * 获取事件列表
     */
    public static final String GET_DZB_SUMMARY = "rest/pshWtsb/getWtsbListByType";
    /**
     * 获取排水户信息详情
     */
    public static final String GET_DZB_SUMMARY_DETAIL = "rest/discharge/toCollectView";
    /**
     * 获取事件详情
     */
    public static final String GET_EVENT_DETAIL = "rest/pshWtsb/getDetail";
    /**
     * 获取事件流程
     */
    public static final String GET_EVENT_JOURNAL = "rest/pshWtsb/getSggcLogList";

    /**
     * 获取排水户列表
     */
    public static final String POST_GET_PSH_LIST = "rest/pshDiary/getPshBySGuid";

    /**
     * 获取排水户详情
     */
    public static final String POST_GET_PSH_DETAIL = "rest/discharge/toCollectView/";

    /**
     * 获取日志问题列表
     */
    public static final String POST_GET_PSH_WTSB = "rest/pshDiary/getPshWtsbByPshId";

    /**
     * 获取巡检日志
     */
    public static final String POST_GET_PSH_DIALY = "rest/pshDiary/getPshDiaryLog";


    /**
     * 获取巡检日志附件
     */
    public static final String POST_GET_PSH_DIALY_ATTACH = "rest/pshDiary/getPshDiaryAttach";

    /**
     * 新增巡检日志
     */
    public static final String POST_DIALY_ADD_NEW = "rest/pshDiary/addPshDiary";

    /**
     * 编辑巡检日志
     */
    public static final String POST_DIALY_EDIT = "rest/pshDiary/editPshDiary";

    /**
     * 巡检日志提交问题上报
     */
    public static final String POST_DIALY_ADD_WTSB = "rest/pshWtsb/add";

    /**
     * 巡检日志删除
     */
    public static final String POST_DIALY_DELETE_DIALY = "rest/pshDiary/deletePshDiary";

    /**
     * 巡检日志列表
     */
    public static final String GET_DIALY_LIST = "rest/pshDiary/getPshDiary";

    /**
     * 问题上报环节提交
     */
    public static final String POST_WTSB_SEND = "rest/pshWtsb/wfSend";
}
