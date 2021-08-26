package com.augurit.common.common.manager;

/**
 * com.augurit.agmobile.agwater5.sewage.common
 * Created by sdb on 2018/12/6  16:30.
 * Desc：
 */

public class NWUrlConstant {
    /**
     * 185测试环境
     */
    public static final String BASE_URL = "http://139.159.243.185:8084/agweb_ha/";

    public static final String BASE_SUPPORT_URL = "http://139.159.243.185:8085/agsupport_swj/";//8085
    public static final String BASE_SUPPORT_URL_8081 = "http://139.159.243.185:8081/agsupport_swj/";//8081

    private static final String SERVICE_ROOT_URL = "rest/";
    /**
     * //登录
     */
    public static final String LOGIN_URL = SERVICE_ROOT_URL + "system/getUser/";
    /**
     * //获取全部数据字典
     */
    public static final String GET_ALL_DICT = SERVICE_ROOT_URL + "agdic/allDics";
    /**
     * 问题上报
     */
    public static final String PROBLEM_UPLOAD = SERVICE_ROOT_URL + "nwWorkflow/wfBusSave";


    /**
     * 维管人员（R2）获取上面的维管经理（R1）     &&  镇街获取下面的维管经理
     */
    public static final String GETWGJLBYWGRYNAME = SERVICE_ROOT_URL + "nwWorkflow/getWgjlByWgryName";
    /**
     * 维管经理(R1)获取维管单位下面的维管人员(R2)
     */
    public static final String GETWGRYBYWGJLNAME = SERVICE_ROOT_URL + "nwWorkflow/getWgryByWgjlName";
    /**
     * 维管经理(R1)获取镇管理人员（R0）
     */
    public static final String GETZJBYWGJLNAME = SERVICE_ROOT_URL + "nwWorkflow/getZjByWgjlName";

    /**
     * 获取组织机构列表
     * 问题上报如果是Rg或者Rm 需要此操作
     */
    public static final String GETALLQU = SERVICE_ROOT_URL + "nwWorkflow/getAllQu";
    /**
     * 获取组织机构列表
     */
    public static final String GETZJBYLOGINNAME = SERVICE_ROOT_URL + "nwWorkflow/getZjByLoginName";

    /**
     * 获取市级用户 获取市考核的 污水办
     */
    public static final String GETSJUSER = SERVICE_ROOT_URL + "nwWorkflow/getSjUser";

    /**
     * 根据区获取各镇
     */
    public static final String GETZJBYORGCODE = SERVICE_ROOT_URL + "nwWorkflow/getZjByOrgCode";


    /**
     * 根据组织编码获取各区Rm用户
     */
    public static final String GETQUUSERBYORGCODE = SERVICE_ROOT_URL + "nwWorkflow/getQuUserByOrgCode";

    /**
     * 根据组织编码获取各镇用户
     */
    public static final String GETZJUSERBYORGCODE = SERVICE_ROOT_URL + "nwWorkflow/getZjUserByOrgCode";




    /**
     * 获取事件详情
     */
    public static final String GETDETAIL= SERVICE_ROOT_URL + "nwWorkflow/getDetail";

    /**
     * 获取事件处理情况及施工日志，参数sjid要经过加密处理，加密接口见GzpsService的AESEncoode
     */
    public static final String GET_TRACERECORD_AND_SGGCLOG_LIST= SERVICE_ROOT_URL + "nwWorkflow/getTraceRecordAndSggcLogList";


    /**
     * 推送到一下环节
     */
    public static final String SEND_TASK_TO_NEXT_LINK= SERVICE_ROOT_URL + "nwWorkflow/wfSend";
    /**
     * 任务推送到一下环节
     */
    public static final String SEND_NEXT_LINK = SERVICE_ROOT_URL + "app/asiWorkflow/wfSend";

    /**
     * 保存节点附件，推送到下一节点时用
     */
    public static final String SAVEJDFILE= SERVICE_ROOT_URL + "nwWorkflow/saveJdFile";


    /**
     * 回退到上一环节
     */
    public static final String RETURNPREVTASK= SERVICE_ROOT_URL + "nwWorkflow/returnPrevTask";


    /**
     * 删掉当前任务
     */
    public static final String DELETEPROCESSINSTANCE= SERVICE_ROOT_URL + "nwWorkflow/deleteProcessInstance";


    /**
     * 编辑事件问题
     */
    public static final String UPDATEWTSB= SERVICE_ROOT_URL + "nwWorkflow/updateWtsb";

    /**
     * 撤回下一环节
     */
    public static final String RETRIEVETASK= SERVICE_ROOT_URL + "nwWorkflow/retrieveTask";


    /**
     * 上传督办意见
     */
    public static final String SGGCCONTENTSAVE= SERVICE_ROOT_URL + "nwWorkflow/sggcContentSave";

    /**
     * 上传施工日志
     */
    public static final String SGGCLOGSAVE= SERVICE_ROOT_URL + "nwWorkflow/sggcLogSave";
    /**
     * 获取设施详情
     */
    public static final String GETUPLOADINFO= SERVICE_ROOT_URL + "reces/toCollectView";
    /**
     * 获取巡检项
     */
    public static final String INSERTCHECKRECORD= SERVICE_ROOT_URL + "check/insertCheckRecord";
    /**
     * 获取巡检日志列表
     */
    public static final String GETINSPECTRECORDSBYLOGINNAME= SERVICE_ROOT_URL + "check/getRecordsByLoginName";

    /**
     * 查询考核列表
     *
     */
    public static final String GETASSESSINDEXDEFWITHSCORELIST= SERVICE_ROOT_URL + "assessIndexApp/getAssessIndexDefWithScoreList";
    /**
     * 查询月份考核评分列表
     *
     */
    public static final String GETVILLAGEASSESSLIST= SERVICE_ROOT_URL + "assess/getVillageAssessList";
    /**
     * 查看考核表详情和扣分情况
     */
    public static final String GETASSESSINDEXITEMS= SERVICE_ROOT_URL + "assessIndexApp/getAssessIndexItems";


    /**
     * 获取事件详情
     */
    public static final String GET_EVENT_DETAIL = SERVICE_ROOT_URL + "app/asiWorkflow/getReportDetail";




















    /**
     * 获取数据上报列表
     */
    public static final String POST_SEARCH_FACILITY_AFFAIR_LIST = SERVICE_ROOT_URL + "reces/searchCollectAll";

    /**
     * 获取数据上报详情
     */
    public static final String  POST_FACILITY_DETAIL = SERVICE_ROOT_URL + "parts/toView";
    /**
     * 数据上报详情，获取审批意见
     */
    public static final String  POST_FACILITY_DETAIL_RECORD = SERVICE_ROOT_URL + "parts/toCheckRecord";

    public static final boolean IS_EASY_MOCK = true;
    /**
     * Esay_mock模拟接口接触url
     */
    public static final String NW_EASY_MOCK_BASE_URL = " http://192.168.19.191:7300/mock/5c3837359413a43224e17ba6/agwater/";
    /**
     * 农污测试环境接口
     */
    //测试环境（污水）
    public static final String GZPS_AGWEB = "139.159.243.185:8084/agweb_ha/";
//    public static final String GZPS_AGSUPPORT = "139.159.243.185:8085/agsupport_swj/";
//    public static final String UPLOAD_AGSUPPORT = "http://139.159.243.185/";//设施上报的URL
//    public static final String COLUMN_URL = "http://139.159.243.185:8099/";     //专栏根URL
    /**
     * 获取管网运行图
     */
    public static final String GET_CUBE_RUN_PIC = SERVICE_ROOT_URL + "projInfoCollect/gwyxtView";

    /**vvvvvvvvvvvvvvvvvvv Easy_mock模拟接口 vvvvvvvvvvvvvvvvvvv**/
    /**
     * 获取线上字典版本
     */
//    public static final String GET_UPDATE_GETFUNUPDATEINFO = "/agweb_ha/rest/update/getFunUpdateInfo";
    /**
     * 获取所有字典
     */
    public static final String GET_AGDIC_ALLDICS = "/agweb_ha/" + SERVICE_ROOT_URL + "agdic/allDics";

    /**
     * ^^^^^^^^^^^^^^^^^^^^^ Easy_mock模拟接口 ^^^^^^^^^^^^^^^^^^^^^
     **/

    public static String getNWBaseUrl() {
        return IS_EASY_MOCK ? NW_EASY_MOCK_BASE_URL : GZPS_AGWEB;
    }

    /**
     * 获取已办列表
     *
     * @param loginName
     * @return
     */
    public static final String GETYBSUMMARY = SERVICE_ROOT_URL + "nwWorkflow/getYbSummary";

    /**
     * 获取待在办列表
     *
     * @param loginName
     * @return
     */
    public static final String GETDZBSUMMARY = SERVICE_ROOT_URL + "nwWorkflow/getDZbSummary";
    /**
     * 获取已办结列表
     *
     * @param loginName
     * @return
     */
    public static final String GETBJSUMMARY = SERVICE_ROOT_URL + "nwWorkflow/getBjSummary";
    /**
     * 获取我的上报
     *
     * @param loginName
     * @return
     */
    public static final String GETMYPROBLEMREPORT = SERVICE_ROOT_URL + "nwWorkflow/getMyProblemReport";


    /**
     * 事务公开—考核问题列表
     * State：0处理中1已办结,不传默认全部
     */
    public static final String PUBLIC_GET_UPLOAD_PROBLEM = SERVICE_ROOT_URL + "nwWorkflow/getAssessProblemReport";
    /**
     * 问题上报详情，加载处理情况列表
     */
    public static final String PROBLEM_LOAD_DEAL_INFO = SERVICE_ROOT_URL + "nwWorkflow/getTraceRecordAndSggcLogList";

    /**
     * 获取巡查问题列表
     */
    public static final String PUBLIC_GET_PROBLEM_REPORT = SERVICE_ROOT_URL + "nwWorkflow/getProblemReport";
    /**
     * 数据上报，id新增获取详情
     */
    public static final String PUBLIC_GET_UPLOAD_DETAIL = SERVICE_ROOT_URL + "reces/toCollectView";
    /**
     * 获取审批意见
     */
    public static final String PUBLIC_GET_CHECK_RECORD = SERVICE_ROOT_URL + "reces/toCheckRecord";

    /**
     * 获取巡检记录列表
     */
    public static final String PUBLIC_GET_INSPECT_LIST = SERVICE_ROOT_URL + "check/getDailyInspectRecords";
    /**
     * 获取巡检项
     */
    public static final String GET_INSPECT_RECORD_ITEM = SERVICE_ROOT_URL + "checkOptions/getCheckOptions";

    /**
     * 领取事件
     */
    public static final String GET_SIGN_EVENT = SERVICE_ROOT_URL + "asiWorkflow/takeTasks";
    /**
     * 筛选获取已上报列表
     */
    public static final String GET_PROBLEM_REPORT = SERVICE_ROOT_URL + "app/asiWorkflow/getProblemReport";
    /**
     * AES加密
     *
     * @param content
     * @return
     */
    public static final String GET_AESEN_CODE = SERVICE_ROOT_URL + "app/asiWorkflow/AESEncode";
    /**
     * 转派
     */
    public static final String REASSIGN_EVENT = SERVICE_ROOT_URL + "app/asiWorkflow/wfReassign";
    /**
     * 通过组织机构名称和组织名称获取该机构下的人名列表,以供选择
     * 问题上报如果是Rg或者Rm 需要此操作
     */
    public static final String GET_USER_BY_ORG_CODE = SERVICE_ROOT_URL + "app/asiWorkflow/getUsersByorgCode";
    /**
     * 获取组织机构列表
     * 问题上报如果是Rg或者Rm 需要此操作
     */
    public static final String GET_ALL_ORG = SERVICE_ROOT_URL + "app/asiWorkflow/getAllOrg";
    /**
     * 问题上报时获取下一环节处理人
     * 参数 loginName
     */
    public static final String GET_TASK_USER_BY_LOGIN_NAME = SERVICE_ROOT_URL + "app/asiWorkflow/getAssignees";
    /**
     * 该设施是否已经被同区域的人报过
     */
    public static final String POST_PARTS_CHECKRECORD = SERVICE_ROOT_URL + "app/parts/checkRecord";

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
     * 我的数据新增列表
     */
    public static final String GET_MY_UPLOAD_LIST = SERVICE_ROOT_URL + "app/parts/getPartsNew";

    /**
     * 我的数据校核列表
     */
    public static final String GET_MY_CORR_LIST = SERVICE_ROOT_URL + "app/parts/getPartsCorr";

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
    public static final String POST_OPINION = SERVICE_ROOT_URL + "app/parts/toCheckRecord";
}
