package com.augurit.common.common.manager;

/**
 * 排水巡检Url常量
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.drainage.constant
 * @createTime 创建时间 ：2018/8/31
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class DrainageUrlConstant {

    private static final String SERVICE_ROOT_URL = "rest/";
    /**
     * 事件上报
     */
    public static final String POST_WFBUSSAVE = SERVICE_ROOT_URL + "app/asiWorkflow/wfBusSave";
    /**
     * 编辑问题事件上报
     */
    public static final String UPDATE_WFBUSSAVE = SERVICE_ROOT_URL + "app/asiWorkflow/updateWtsb";
    //12345里面的处理流程
    public static final String GET_HANDLE_RECORD = "agcom/rest/task/getlist_by_proid?problemId=";


    /**
     * 数据上报-数据新增
     */
    public static final String POST_NEW_UPLOAD_DATA = SERVICE_ROOT_URL + "app/parts/partsNew";
    /**
     * 数据上报-数据校核
     */
    public static final String POST_CORRECT_COMFRIM_UPLOAD = SERVICE_ROOT_URL + "app/parts/partsCorr";


    public static final String POST_DELETE = SERVICE_ROOT_URL + "app/parts/deleteReport";

    /**
     * 获取设施上报列表（新增和校核）
     */
    public static final String POST_SEARCHCORROR_LACK = SERVICE_ROOT_URL + "app/parts/searchCorrOrLackByOId";

    /**
     * 该设施是否已经被同区域的人报过
     */
    public static final String POST_PARTS_CHECKRECORD = SERVICE_ROOT_URL + "app/parts/checkRecord";

    /**
     * 我的数据新增列表
     */
    public static final String GET_MY_UPLOAD_LIST = SERVICE_ROOT_URL + "app/parts/getPartsNew";

    /**
     * 数据上报附件
     */
    public static final String POST_MY_UPLOAD_ATTACH = SERVICE_ROOT_URL + "app/parts/getPartsNewAttach";
    /**
     * 数据校核附件
     */
    public static final String POST_MY_CORRECT_ATTACH = SERVICE_ROOT_URL + "app/parts/getPartsCorrAttach";
    /**
     * 审批意见
     */
    public static final String GET_OPINION_TOCHECKRECORD = SERVICE_ROOT_URL + "app/toCheckRecord";
    public static final String POST_OPINION = SERVICE_ROOT_URL + "app/parts/toCheckRecord";
    public static final String GET_TOVIEW =  "rest/app/toView";

    /**
     * 我的数据校核列表
     */
    public static final String GET_MY_CORR_LIST = SERVICE_ROOT_URL + "app/parts/getPartsCorr";

    /**
     * 问题上报时获取下一环节处理人
     * 参数 loginName
     */
    public static final String GET_TASK_USER_BY_LOGIN_NAME = SERVICE_ROOT_URL + "app/asiWorkflow/getAssignees";


    /**
     * 获取组织机构列表
     * 问题上报如果是Rg或者Rm 需要此操作
     */
    public static final String GET_ALL_ORG = SERVICE_ROOT_URL + "app/asiWorkflow/getAllOrg";

    /**
     * 通过组织机构名称和组织名称获取该机构下的人名列表,以供选择
     * 问题上报如果是Rg或者Rm 需要此操作
     */
    public static final String GET_USER_BY_ORG_CODE = SERVICE_ROOT_URL + "app/asiWorkflow/getUsersByorgCode";

    /**
     * 获取事件处理情况及施工日志，参数sjid要经过加密处理，加密接口见GzpsService的AESEncoode
     */
    public static final String GET_HANDLE_AND_LOG = SERVICE_ROOT_URL + "app/asiWorkflow/getTraceRecordAndSggcLogList";

    /**
     * 获取待在办列表
     */
    public static final String GET_DZB_SUMMARY = SERVICE_ROOT_URL + "app/asiWorkflow/getDZbSummary";

    /**
     * 获取已办列表
     */
    public static final String GET_YB_SUMMARY = SERVICE_ROOT_URL + "app/asiWorkflow/getYbSummary";

    /**
     * 获取办结列表
     */
    public static final String GET_BJ_SUMMARY = SERVICE_ROOT_URL + "app/asiWorkflow/getBjSummary";


    /**
     * 获取我的提交列表
     */
    public static final String GET_UPLOAD_SUMMARY = SERVICE_ROOT_URL + "app/asiWorkflow/getListsMyCommit";

    /**
     * 筛选获取已上报列表
     */
    public static final String GET_PROBLEM_REPORT = SERVICE_ROOT_URL + "app/getProblemReport";
//    public static final String GET_PROBLEM_REPORT = SERVICE_ROOT_URL + "app/asiWorkflow/getProblemReport";
    /**
     * 领取事件
     */
    public static final String GET_SIGN_EVENT = SERVICE_ROOT_URL + "asiWorkflow/takeTasks";

    /**
     * 问题上报列表（巡检日志）
     */
    public static final String GET_EVENT_LIST_DIALY = SERVICE_ROOT_URL + "app/parts/getProblemByObjectId";

    /**
     * 删掉当前任务
     */
    public static final String GET_DELETE_TASK = SERVICE_ROOT_URL + "app/asiWorkflow/deleteProcessInstance";

    /**
     * 获取事件详情
     */
    public static final String GET_EVENT_DETAIL = SERVICE_ROOT_URL + "app/asiWorkflow/getReportDetail";

    /**
     * 获取设施数据列表
     */
    public static final String POST_SEARCH_FACILITY_AFFAIR_LIST = SERVICE_ROOT_URL + "app/searchCorrOrLack";
//    public static final String POST_SEARCH_FACILITY_AFFAIR_LIST = SERVICE_ROOT_URL + "app/parts/searchCorrOrLack";


    /**
     * 获取巡查日志列表
     */
    public static final String POST_GET_FACILITY_DIARY = SERVICE_ROOT_URL + "parts/getFacilityDiary";
    /**
     * 获取巡查日志列表
     */
    public static final String POST_GET_FACILITY_DIARY_TEMP = SERVICE_ROOT_URL + "app/getFacilityDiary";
//    public static final String POST_GET_FACILITY_DIARY_TEMP = SERVICE_ROOT_URL + "app/parts/getFacilityDiary";

    /**
     * 获取巡查日志列表
     */
    public static final String POST_GET_LIST_DIARY = SERVICE_ROOT_URL + "app/parts/getDiayr";

    /**
     * 新增巡检日志
     */
    public static final String POST_DIALY_ADD_NEW = SERVICE_ROOT_URL + "app/parts/diayrNew";

    /**
     * 巡检日志再次编辑
     */
    public static final String POST_DIALY_EDIT = SERVICE_ROOT_URL + "app/parts/toEditDiary";
    /**
     * 巡检日志附件
     */
    public static final String POST_DIALY_ATTACH = SERVICE_ROOT_URL + "app/parts/getDiayrAttach";

    /**
     * 巡检日志删除
     */
    public static final String POST_DIALY_DELETE_DIALY = SERVICE_ROOT_URL + "app/parts/deleteDiary";

    /**
     * 获取班组成员
     */
    public static final String POST_TEAM_MEMBER = SERVICE_ROOT_URL + "app/parts/getTeamUser";

    /**
     * 获取交办反馈批次筛选
     */
    public static final String POST_FEED_BACK = SERVICE_ROOT_URL + "assign/searchQd";

    //TODO 排水巡检接口
    /**
     * AES加密
     *
     * @param content
     * @return
     */
    public static final String GET_AESEN_CODE = SERVICE_ROOT_URL + "app/asiWorkflow/AESEncode";
    /**
     * 查询轨迹记录
     */
    public static final String SZ_GET_TRACK_LIST = "/agsupport/rest/jwxcyh/queryGpsByUserid";


    /**
     * 获取轨迹配置信息
     */
    public static final String GET_TRACK_CONFIG = SERVICE_ROOT_URL + "app/gettrackConfig";
    /**
     * 保存轨迹
     */
    public static final String SAVE_TRACK = SERVICE_ROOT_URL + "app/saveTrackLine";
    /**
     * 保存轨迹点
     */
    public static final String SAVE_TRACK_POINT = SERVICE_ROOT_URL + "app/addTrackPoint";
    /**
     * 删除轨迹
     */
    public static final String DELETE_TRACK_LINE = SERVICE_ROOT_URL + "app/deleteTrackLine";
    /**
     * 根据用户id获取轨迹列表
     */
    public static final String GET_TRACK_LINE_BY_USER = SERVICE_ROOT_URL + "app/getTrackLinesByLoginName";
    /**
     * 根据轨迹id获取轨迹点
     */
    public static final String GET_TRACK_POINT_BY_LINE = SERVICE_ROOT_URL + "app/getFormByTrackId";

    /**
     * 撤回或者回退
     */
    public static final String RETRIEVE_EVENT = SERVICE_ROOT_URL + "app/asiWorkflow/returnPrevTask";


    /**
     * 转派
     */
    public static final String REASSIGN_EVENT = SERVICE_ROOT_URL + "app/asiWorkflow/wfReassign";


    /**
     * 保存节点附件
     */
    public static final String SAVE_JD = SERVICE_ROOT_URL + "app/asiWorkflow/saveJdFile";


    /**
     * 任务派发环节退回后的问题上报、任务处置、任务复核、巡查员复核环节提交时获取下一环节处理人
     */
    public static final String GET_NEXT_ACTIVITY_INFO = SERVICE_ROOT_URL + "app/asiWorkflow/getNextActivityInfo";

    /**
     * 任务推送到一下环节
     */
    public static final String SEND_NEXT_LINK = SERVICE_ROOT_URL + "app/asiWorkflow/wfSend";

    /**
     * 任务分发环节获取当前用户所在的机构下的养护班组
     */
    public static final String GET_BZ_ORG = SERVICE_ROOT_URL + "app/asiWorkflow/getbzOrg";

    /**
     * 添加施工日志
     */
    public static final String ADD_EVENT_JOURNAL = SERVICE_ROOT_URL + "app/asiWorkflow/sggcLogSave";
    /**
     * 添加管理层意见
     */
    public static final String ADD_EVENT_ADVICE = SERVICE_ROOT_URL + "app/asiWorkflow/sggcContentSave";


    /**
     * 新增门牌
     */
    public static final String ADD_DOOR_NO = SERVICE_ROOT_URL + "pshSbssInfRest/sbssMp";
    /**
     * 查询房屋详细信息
     */
    public static final String QUERY_HOUSE_DETAIL = SERVICE_ROOT_URL + "pshSbssInfRest/getBuildInfBySGuid";


    /**
     * 上报错误的门牌号码
     */
    public static final String UPLOAD_WRONG_DOOR = SERVICE_ROOT_URL + "pshSbssInfRest/updateIstatue";

    /**
     * 新增接驳井（排水户）
     */
    public static final String UPLOAD_NEW_WELL = SERVICE_ROOT_URL + "PshParts/partsNew";

    /**
     * 接驳井图片附件获取（排水户）
     */
    public static final String UPLOAD_WELL_ATTACH = SERVICE_ROOT_URL + "PshParts/getPartsNewAttach";

    /**
     * 获取接驳井附件图片
     */
    public static final String GET_WELL_PHOTO = SERVICE_ROOT_URL + "discharge/getImgsByObjId";


    /**
     * 获取接驳井上报列表（排水户）
     */
    public static final String GET_UPLOAD_WELL_LIST = SERVICE_ROOT_URL + "PshParts/getPartsNew";

    /**
     * 删除我的接驳井信息（排水户）
     */
    public static final String DELETE_MY_WELL_INFO = SERVICE_ROOT_URL + "PshParts/deleteReport";

    /**
     * 我的接驳井上报再次编辑（排水户）
     */
    public static final String POST_WELL_EDIT = SERVICE_ROOT_URL + "parts/partsCorr";

    /**
     * 我的接驳井上报审核意见列表（排水户）
     */
    public static final String POST_WELL_OPINION_LIST = SERVICE_ROOT_URL + "parts/toCheckRecord";

    /**
     * 获取我的门牌上报列表
     */
    public static final String POST_GET_MY_UPLOAD_DOOR_NO_LIST = SERVICE_ROOT_URL + "pshSbssInfRest/getMpList";


    /**
     * 根據sduid刪除門牌號
     */
    public static final String POST_DELETE_DOOR_NO = SERVICE_ROOT_URL + "pshSbssInfRest/deleteMp";

    /**
     * 获取门牌下调查信息 (排水户)
     */
    public static final String POST_GET_SEWERAGE_ITEM_DATA = SERVICE_ROOT_URL + "pshSbssInfRest/getHouseUnitPop";
    /**
     * 获取我的上报列表(排水户)
     */
    public static final String POST_GET_SEWERAGE_UPLOAD = SERVICE_ROOT_URL + "discharge/getCollectList";

    /**
     * 获取排水户信息详情(排水户)
     */
    public static final String POST_GET_SEWERAGE_DETAIL = SERVICE_ROOT_URL + "discharge/toCollectView";

    /**
     * 删除排水户信息(排水户)
     */
    public static final String POST_DELETE_COLLECT = SERVICE_ROOT_URL + "discharge/deleteCollect";

    /**
     * 新增排水户信息(排水户)
     */
    public static final String POST_ADD_SEWERAGE = SERVICE_ROOT_URL + "discharge/toAddCollect";
    /**
     * 编辑更新排水户信息(排水户)
     */
    public static final String POST_UPDATE_SEWERAGE = SERVICE_ROOT_URL + "discharge/toUpdateCollect";

    /**
     * 点击排水户信息列表进入详情界面(排水户)
     */
    public static final String POST_GET_DETAIL_SEWERAGE = SERVICE_ROOT_URL + "discharge/toCollectViewByUnitId";


    /**
     * 工程巡查上报
     */
    public static final String YJ_INSPECT_SUBMIT = SERVICE_ROOT_URL + "app/gcxc/saveGcxc";


    /**
     * 获取单位下该设施下的设施名称
     */
    public static final String YJ_GET_FACILITY = SERVICE_ROOT_URL + "app/gcxc/getGxchByType";
    /**
     * 上传道路获取附近道路
     */
    public static final String GET_RELATIVE_ROAD = "/agsupport/" + SERVICE_ROOT_URL + "jwxcyh/queryRoadname";

    /**
     * 获取养护情况列表
     */
    public static final String GET_MAINTAIN_LIST = SERVICE_ROOT_URL + "maintenance/getMaintenanceByPlanId";

    /**
     * 获取养护计划附件
     */
    public static final String GET_MAINTAIN_ATTACHMENT = SERVICE_ROOT_URL + "maintenance/toView/";

    /**
     * 新增养护计划
     */
    public static final String POST_MAINTAIN_ADD_EDIT = SERVICE_ROOT_URL + "maintenance/add";

    /**
     * 删除养护记录
     */
    public static final String POST_MAINTAIN_DELETE = SERVICE_ROOT_URL + "maintenance/delete";

    /**
     * 获取养护计划批次列表
     */
    public static final String POST_MAINTAIN_SELECT = SERVICE_ROOT_URL + "maintenance/searchPlanAll";


    public static final String POST_WFBUSSAVE1 = SERVICE_ROOT_URL + "app/wfBusSave";

    /**
     * 获取下一环节处理人
     */
    public static final String GET_NEXTLINK_USER = SERVICE_ROOT_URL + "app/getAssigneeRangeByCurrTaskId";
    /**
     * 获取下一环节处理人
     */
    public static final String GET_NEXTLINK_USER_TREE = SERVICE_ROOT_URL + "app/getAssigneeRangeZTree";
    /**
     * 获取下一环节信息
     */
    public static final String GET_NEXT_LINK = SERVICE_ROOT_URL + "app/getTaskSendConfig";
    /**
     * 发送到下一环节
     */
    public static final String SEND_TO_NEXT_LINK = SERVICE_ROOT_URL + "app/completeTask";

    /**
     * 获取待在办列表
     */
    public static final String GET_DZB_SUMMARY1 = SERVICE_ROOT_URL + "app/getDZbSummary";

    /**
     * 获取已办列表
     */
    public static final String GET_YB_SUMMARY1 = SERVICE_ROOT_URL + "app/getYbSummary";

    /**
     * 获取办结列表
     */
    public static final String GET_BJ_SUMMARY1 = SERVICE_ROOT_URL + "app/getBjSummary";

    /**
     * 我的提交列表
     */
    public static final String MY_UPLOAD_EVENT_LIST = SERVICE_ROOT_URL + "app/listMyGxProblemReport";

    /**
     * 获取详情
     */
    public static final String GET_EVENT_DETAIL1 = "asiWorkflow/getProblemForm.do";
    /**
     * 删除事件
     */
    public static final String DELETE_EVENT = SERVICE_ROOT_URL +"app/deleteGxProblemReport";

    /**
     * 获取流程意见
     */
    public static final String GET_EVENT_PROCESS = "gxProblemrReport/listHistoryComment";
    /**
     * 退回、撤回
     */
    public static final String ROLL_BACK_EVENT = SERVICE_ROOT_URL + "app/returnPrevTask";
    /**
     * 转派
     */
    public static final String RESSIGN_EVENT = SERVICE_ROOT_URL + "app/sendOnTask";

    /**
     * 获取转派人
     */
    public static final String RESSIGN_EVENT_MAN = SERVICE_ROOT_URL + "app/getCurrTaskUser";
    /**
     * 根据流程定义和任务节点id获取审批人
     */
    public static final String PROBLEM_SUBMIT_MAN = SERVICE_ROOT_URL + "app/getAssigneeRangeByProcessDefKey";
    /**
     * 待办、已办数量统计
     */
    public static final String GET_DB_YB_NUM = SERVICE_ROOT_URL + "app/getDbAndYbSummarySum";

    /**
     * 待办、已办数量统计
     */
    public static final String GET_LAST_TEN = SERVICE_ROOT_URL + "app/getLatestTen";

    /**
     * 数据新增
     */
    public static final String POST_NEW_UPLOAD_DATA1 = SERVICE_ROOT_URL + "app/partsNew";
    /**
     * 数据上报-数据校核
     */
    public static final String POST_CORRECT_COMFRIM_UPLOAD1 = SERVICE_ROOT_URL + "app/partsCorr";

    /**
     * 该设施是否已经被同区域的人报过
     */
    public static final String POST_PARTS_CHECKRECORD1 = SERVICE_ROOT_URL + "app/checkRecord";

    /**
     * 我的数据新增列表
     */
    public static final String GET_MY_UPLOAD_LIST1 = SERVICE_ROOT_URL + "app/getPartsNew";

    /**
     * 数据上报附件
     */
    public static final String POST_MY_UPLOAD_ATTACH1 = SERVICE_ROOT_URL + "app/getPartsNewAttach";
    /**
     * 数据校核附件
     */
    public static final String POST_MY_CORRECT_ATTACH1 = SERVICE_ROOT_URL + "app/getPartsCorrAttach";

    /**
     * 我的数据校核列表
     */
    public static final String GET_MY_CORR_LIST1 = SERVICE_ROOT_URL + "app/getPartsCorr";

    public static final String POST_DELETE1 = SERVICE_ROOT_URL + "app/deleteReport";
    /**
     * 审批意见
     */
    public static final String POST_OPINION1 = SERVICE_ROOT_URL + "app/toCheckRecord";

    /**
     * 数据上报附件（交办）
     */
    public static final String POST_FD_UPLOAD_ATTACH = "rest/parts/getPartsNewAttach";
    /**
     * 数据校核附件（交办）
     */
    public static final String POST_FD_CORRECT_ATTACH = "rest/parts/getPartsCorrAttach";
    /**
     * 我的巡检任务列表
     */
    public static final String MY_PATROL_TASK_LIST= "rest/app/listMyPlanTask";
    /**
     * 我的巡检任务详情
     */
    public static final String MY_PATROL_TASK_DETAIL= "patrolTask/get";
    /**
     * 修改任务状态
     */
    public static final String COMPLETE_TASK= "rest/app/updatePtrlTask";

    /**
     * 排水设施管理 测站
     */
    public static final String PS_FACILITY_CZ= "psfacility/app/listStationProperty";
    /**
     * 排水设施管理 测站 监测项
     */
    public static final String PS_FACILITY_CZ_CHECK= "psfacility/app/listStationMonItem";
    /**
     * 排水设施管理 根据表id获取对应的数据列表
     */
    public static final String PS_FACILITY_GXGD = "psfacility/app/listPipeLineorPointData";

    /**
     * 管线或管点表的id，通过listPipeLinePointTables接口获取的表的id
     * 获取所有列表的表id
     */
    public static final String PS_FACILITY_TABLEID = "psfacility/app/listPipeLinePointTables";

}
