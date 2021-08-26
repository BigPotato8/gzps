package com.augurit.common.common.manager;

/**
 * @AUTHOR 创建人:yaowang
 * @VERSION 版本:1.0
 * @PACKAGE 包名:com.augurit.agmobile.agwater5.common.common
 * @CREATETIME 创建时间:2018/8/29
 * @MODIFYTIME 修改时间:
 * @MODIFYBY 修改人:
 * @MODIFYMEMO 修改备注:
 */
public class YJUrlConstant {

    //    public static final String SERVER_URL = "http://192.168.30.62:8080";
//    public static final String SERVER_URL = "http://192.168.30.62:8895";
//    public static final String SERVER_URL = "http://192.168.30.62";
    public static final String SERVER_URL = "http://192.168.30.222";
    //    public static final String SERVER_URL = "http://192.168.30.175:8082";
//    public static final String MOBINSP_URL = ":8890/awater_yj/";
//    public static final String PSMONITORING_URL = ":8891/psxj/";
//    public static final String MAINTAIN_URL = ":8892/psmaintain/";
    public static final String PSEMGY_URL = ":80/psengineer/";
    //    public static final String ENGINEER_URL = ":8894/psemgy/";
    public static  final String RIVER_INFO_URL = ":80/riverinfo/";
//    public static final String OASYS_URL = ":8896/oasys/";
//    public static final String INTERNALCONTROL_URL = ":8896/oasys/";
//    public static final boolean SHOULD_IM_LOGIN = false;


//    public static void setPsemgyUrl(String psemgyUrl) {
//        PSEMGY_URL = psemgyUrl;
//    }
//    public static void setRiverInfoUrl(String riverInfoUrl) {
//        RIVER_INFO_URL = riverInfoUrl;
//    }



    /**
     * 工程巡查上报
     */
    public static final String YJ_INSPECT_SUBMIT = "rest/app/gcxc/saveGcxc";

    /**
     * 工程问题上报
     */
    public static final String YJ_PROBLEM_SUBMIT = "rest/app/gcsb/save";

    /**
     * 获取问题列表
     */
    public static final String YJ_PROBLEM_LIST = "rest/app/gcsb/getByPage";


    /**
     * 获取单位下该设施下的设施名称
     */
    public static final String YJ_GET_FACILITY = "rest/app/gcxc/getGxchByType";

    /**
     * 河道上报
     */
//    public static final String YJ_RIVER_REPORT = "rest/app/problem/save";
    public static final String YJ_RIVER_REPORT = "rest/app/hd/problem/save";
    /**
     * 保存河道巡查记录
     */
    public static final String YJ_RIVER_XUN_CHA_UPLOAD = "rest/app/track/hedaoxc/save";
    /**
     * 获取河道检查项
     */
    public static final String YJ_RIVER_XUN_CHA_CHECK = "rest/app/hedaoResource/getChildByType";
    /**
     * 根据轨迹id获取河道巡查列表
     */
    public static final String YJ_TRACK_XUN_CHA_LIST = "rest/app/track/getByRecordByTrancId";
    /**
     * 根据巡查id获取巡查详情
     */
    public static final String YJ_TRACK_XUN_CHA_DETAIL = "rest/app/track/hedaoxc/getById";
    /**
     * 临河监督检查上报列表
     */
    public static final String BY_RIVER_PROJECT_LIST = "rest/app/lhjdjc/getByPage";

    /**
     * 临河监督检查上报
     */
    public static final String BY_RIVER_PROJECT_SAVE = "rest/app/lhjdjc/save";
    /**
     * 临河监督检查删除
     */
    public static final String BY_RIVER_PROJECT_DELETE = "rest/app/lhjdjc/delete";
    /**
     * 临河，获取全部堤防
     */
    public static final String BY_RIVER_GET_DF_ALL = "rest/app/hedaoResource/getDfAll";
    /**
     * 临河，堤防获取堤段
     */
    public static final String BY_RIVER_GET_DD_BY_DF = "rest/app/hedaoResource/getDdByDfId";
    /**
     * 获取临河工程基本信息
     */
    public static final String BY_RIVER_GET_LH_BY_DD = "rest/app/hedaoResource/getLhByDdId";
    /**
     * 我的河道上报列表
     */
    public static final String MY_RIVER_REPORT_LIST = "rest/app/hd/problem/getPageByUser";

    /**
     * 所有堤防
     */
    public static final String YJ_ALL_DIFANG = "rest/app/hedaoResource/getDfAll";

    /**
     * 堤防下所有堤段
     */
    public static final String YJ_ALL_DIDUAN = "rest/app/hedaoResource/getDdByDfId";

    /**
     * 临河获取问题详情
     */
    public static final String BY_RIVER_REPORT_DETAIL = "rest/app/lh/problem/getById";

    /**
     * 临河 保存/修改问题 新增上报记录
     */
    public static final String BY_RIVER_REPORT_PROBLRM = "rest/app/lh/problem/save";

    /**
     * 临河 删除上报问题记录
     */
    public static final String BY_RIVER_REPORT_DELETE = "rest/app/lh/problem/delete";

    /**
     * 临河 问题上报记录分页查询
     */
    public static final String BY_RIVER_GET_LIST_BY_PAGE = "rest/app/lh/problem/getByPage";

    /**
     * 临河 根据id查询问题详情
     */
    public static final String BY_RIVER_GET_DETAIL_BU_ID = "rest/app/lh/problem/getById";


    //待办 已办 办结

    /**
     * 工程待办
     */
    public static final String PROJECT_DB_LIST = "rest/app/gcsb/getDbSummary";
    /**
     * 河道待办
     */
    public static final String RIVER_DB_LIST = "rest/app/hd/problem/getDbSummary";
    /**
     * 工程已办列表
     */
    public static final String PROJECT_YB_LIST = "rest/app/gcsb/getYbSummary";
    /**
     * 河道已办列表
     */
    public static final String RIVER_YB_LIST = "rest/app/hd/problem/getYbSummary";
    /**
     * 工程办结列表
     */
    public static final String PROJECT_BJ_LIST = "rest/app/gcsb/getBjSummary";
    /**
     * 河道办结列表
     */
    public static final String RIVER_BJ_LIST = "rest/app/hd/problem/getBjSummary";
    /**
     * 启动河道流程
     */
    public static final String START_HD_WORK_FLOW = "rest/app/hd/problem/startWorkFlow";
    /**
     * 启动设施流程
     */
    public static final String START_SS_WORK_FLOW = "rest/app/gcsb/startWorkFlow";
    /**
     * 启动临河流程
     */
    public static final String START_LH_WORK_FLOW = "rest/app/lh/problem/startWorkFlow";
    /**
     * 临河待办列表
     */
    public static final String BY_RIVER_DB_LIST = "rest/app/lh/problem/getDbSummary";
    /**
     * 临河已办列表
     */
    public static final String BY_RIVER_YB_LIST = "rest/app/lh/problem/getYbSummary";
    /**
     * 临河办结列表
     */
    public static final String BY_RIVER_BJ_LIST = "rest/app/lh/problem/getBjSummary";
    /**
     * 查看处理情况流程 环节信息
     * //数据类型(type=gcgh(工程上报),type=hedao(河道上报),type=linhe(临河上报))
     */
    public static final String GET_EVENT_PROCESS_INFO = "rest/app/bpm/getHistoryCommentsByProcessInstanceId";
    /**
     * 查看河道问题 流程详情
     */
    public static final String GET_LINK_RIVER_DETAIL = "rest/app/hd/problem/getById";
    /**
     * 河道根据节点信息获取下一步处理人信息(树格式)
     */
    public static final String GET_LINK_RIVER_NEXT_USER = "rest/app/hd/problem/getSendTreeUsers";

    /**
     * 获取同级机构下的其他用户
     */
    public static final String GET_SAME_ORG_USER = "rest/app/bpm/listReassUsers";
    /**
     * 河道 转派 给级机构下的其他用户
     */
    public static final String RIVER_ASSIGN_SAME_ORG_USER = "rest/app/hd/problem/sendOnTask";
    /**
     * 回退任务
     */
    public static final String ROLLBACK_TASK = "rest/app/bpm/returnTask";
    /**
     * 河道 发送到下一环节
     */
    public static final String RIVER_SEND_TASK_TO_NEXT_LINK = "rest/app/hd/problem/wfSend";
    /**
     * 河道 删除流程
     */
    public static final String RIVER_DELETE_PROCESS = "rest/app/hd/problem/deleteProcessInstance";

    /**
     * 河道 获取发送下一步节点信息
     */
    public static final String RIVER_GET_NEXT_LINK_INFO = "rest/app/hd/problem/getTaskSendConfig";

    /**
     * 工程 根据id获取工程上报信息
     */
    public static final String GET_LINK_PORJECT_DETAIL = "rest/app/gcsb/getById";

    /**
     * 工程  获取下一步信息
     */
    public static final String PROJECT_GET_NEXT_LINK_INFO = "rest/app/gcsb/getTaskSendConfig";
    /**
     * 工程  发送到下一环节
     */
    public static final String PROJECT_SEND_TASK_TO_NEXT_LINK = "rest/app/gcsb/wfSend";

    /**
     * 工程 根据节点信息获取下一步处理人信息(树格式)
     */
    public static final String GET_LINK_PROJECT_NEXT_USER = "rest/app/gcsb/getSendTreeUsers";
    /**
     * 工程 转派
     */
    public static final String PROJECT_ASSIGN_SAME_ORG_USER = "rest/app/gcsb/sendOnTask";

    /**
     * 工程 删除流程
     */
    public static final String PROJECT_DELETE_PROCESS = "rest/app/gcsb/deleteProcessInstance";

    /**
     * 临河 根据ID查看详情
     */
    public static final String GET_LINK_BY_RIVER_DETAIL = "rest/app/lh/problem/getById";
    /**
     * 临河 获取发送下一步节点信息
     */
    public static final String BY_RIVER_GET_NEXT_LINK_INFO = "rest/app/lh/problem/getTaskSendConfig";
    /**
     * 临河 根据节点信息获取下一步处理人信息(树格式)
     */
    public static final String GET_LINK_BY_RIVER_NEXT_USER = "rest/app/lh/problem/getSendTreeUsers";

    /**
     * 临河 发送到下一环节
     */
    public static final String BY_RIVER_SEND_TASK_TO_NEXT_LINK = "rest/app/lh/problem/wfSend";
    /**
     * 临河 转派
     */
    public static final String BY_RIVER_ASSIGN_SAME_ORG_USER = "rest/app/hd/lh/problem/sendOnTask";
    /**
     * 临河 删除流程
     */
    public static final String BY_RIVER_DELETE_PROCESS = "rest/app/lh/problem/deleteProcessInstance";

    /**
     * 获取im token
     */
    public static final String IM_GET_TOKEN = "rest/app/rong/registerUser";

    /**
     * 获取通讯好友列表
     */
    public static final String IM_FRIEND_LIST = "rest/app/rong/listUsers";

    /**
     * 获取用户所在群组列表
     */
    public static final String IM_GROUP_LIST = "rest/app/rong/getGroupsByUserId";

    /**
     * 根据用户注册融云id获取用户信息
     */
    public static final String IM_GETUSER = "rest/app/rong/getUserByUserId";

    /**
     * 根据群组注册融云id获取群组信息
     */
    public static final String IM_GETGROUP = "rest/app/rong/getGroupByGroupId";

    /**
     * 根据群组id查询群组用户
     */
    public static final String IM_GROUP_USER = "rest/app/rong/getGroupUsers";


    /**
     * 获取巡检列表
     */
    public static final String GET_QUERY_LIST = "rest/app/gcxc/getGcxcList";
    /**
     * 根据id获取巡检详情
     */
    public static final String GET_QUERY_DETAIL_BY_ID = "rest/app/gcxc/getById";
}
