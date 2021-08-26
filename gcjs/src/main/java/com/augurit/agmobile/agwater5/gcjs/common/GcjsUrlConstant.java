package com.augurit.agmobile.agwater5.gcjs.common;

/**
 * com.augurit.agmobile.agwater5.gcjs.common
 * Created by sdb on 2019/4/4  9:44.
 * Desc：
 */

public class GcjsUrlConstant {

    //获取用户信息
    public static final String GET_USER_INFO = "rest/opus/om/getOpuOmUserInfoByUserId.do";

    //头部url
    public static final String HEADER_URL = "aplanmis-rest/";

    /**
     * 获取用户角色种类
     */
    public static final String GET_NEW_USER_TYPE = HEADER_URL + "rest/om/users/currOpusLoginUser";

    /**
     * 窗口人员获取项目数量
     */
    public static final String GET_WIN_PORTAL_COUNT = HEADER_URL + "rest/win/portal/count";

    /**
     * 部门人员获取项目数量
     */
    public static final String GET_ORG_PORTAL_COUNT = HEADER_URL + "rest/org/portal/count";

    /**
     * 获取个人信息
     */
    public static final String GET_NEW_USER_INFO = HEADER_URL + "opus/front/om/users";

    /**
     * 保存个人信息
     */
    public static final String SAVE_NEW_USER_INFO = HEADER_URL + "opus/front/om/user/info/saveUserInfo.do";

    /**
     * 检验旧密码
     */
    public static final String GET_CHECK_OLD_PWD = HEADER_URL + "rest/om/users/passwordCheckout";

    /**
     * 保存密码
     */
    public static final String PUT_MODIFY_PWD = HEADER_URL + "rest/om/users/password";

    /**
     * 获取审批待办已办列表中，筛选的信息
     */
    public static final String GET_EVENT_LIST_FILTER_INFO = HEADER_URL + "rest/query/task/dic/list";
    /**
     * 获取审批待办列表
     */
    public static final String GET_EVENT_DB_LIST = HEADER_URL + "rest/approve/listWaitDoTasks";

    /**
     * 获取审批已办列表
     */
    public static final String GET_EVENT_YB_LIST = HEADER_URL + "rest/approve/listDoneTasks";

    /**
     * 获取审批办结列表
     */
    public static final String GET_EVENT_BJ_LIST = HEADER_URL + "rest/query/listConcludedTasks";

    /**
     * 获取所有办件列表
     */
    public static final String GET_EVENT_ALL_LIST = HEADER_URL + "rest/query/listParts";




    /**
     * 待办已办中根据id获取事件详情基本信息的项目申报接口
     * 返回项目申报基本信息
     */
    public static final String GET_EVENT_INFO = HEADER_URL + "rest/approve/common/getApplyDetail";
    /**
     * 待办已办中根据id获取事件详情基本信息的项目申报接口
     * 返回项目申报基本信息,4.0版本
     */
    public static final String GET_EVENT_INFO_4_0 = HEADER_URL + "rest/approve/basic/apply/info";

    /**
     * 获取取件人信息
     */
    public static final String GET_EVENT_SMS_INFO = HEADER_URL + "rest/project/hi/sms/info";
    /**
     * 获取受理回执信息
     */
    public static final String GET_ACCEPT_RECEIPT_INFO = HEADER_URL+"rest/approve/getReceivingReceiptInfo";
    /**
     * 保存受理回执
     */
    public static final String POST_ACCEPT_RECEIPT = HEADER_URL+"rest/approve/saveAeaHiReceivingReceipt";
    /**
     * 保存不予受理回执
     */
    public static final String POST_NOT_ACCEPT_RECEIPT = HEADER_URL+"rest/approve/saveAeaHiUnreceivingReceipt";
    /**
     * 删除受理回执
     */
    public static final String DELETE_ACCEPT_RECEIPT = HEADER_URL+"rest/approve/deleteAeaHiReceivingReceiptById.do";
    /**
     * 删除不予受理回执
     */
    public static final String DELETE_NOT_ACCEPT_RECEIPT = HEADER_URL+"rest/approve/deleteAeaHiUnapproveReceiptById.do";
    /**
     * 删除审批决定通过
     */
    public static final String DELETE_APPROVE = HEADER_URL+"rest/approve/deleteAeaHiApproveReceiptById.do";
    /**
     * 删除审批决定不通过
     */
    public static final String DELETE_NOT_APPROVE = HEADER_URL+"rest/approve/deleteAeaHiUnapproveReceiptById.do";
    /**
     * 获取不予受理回执信息
     */
    public static final String GET_NOT_ACCEPT_RECEIPT_INFO = HEADER_URL+"rest/approve/getUnreceivingReceiptInfo";
    /**
     * 保存不予受理回执信息
     */
    public static final String POST_SAVE_NOT_ACCEPT_RECEIPT_INFO = HEADER_URL+"/rest/approve/saveAeaHiUnapproveReceipt";
    /**
     * 获取审批通过回执信息
     *
     */
    public static final String GET_APPROVE_THROUGH_INFO = HEADER_URL+"rest/approve/getApproveReceiptInfo";
    /**
     * 获取审批不通过回执信息
     */
    public static final String GET_APPROVE_NOT_THROUGH_INFO = HEADER_URL+"rest/approve/getUnapproveReceiptInfo";
    /**
     * 保存补正回执
     */
    public static final String POST_CORRECT_RECEIPT = HEADER_URL+"rest/approve/saveAeaHiCorrectReceipt";
    /**
     * 删除补正回执
     */
    public static final String DELETE_CORRECT_RECEIPT = HEADER_URL+"rest/approve/deleteAeaHiCorrectReceiptById.do";
    /**
     * 获取补正回执信息
     */
    public static final String GET_CORRECT_RECEIPT_INFO = HEADER_URL+"rest/approve/getCorrectReceiptInfo";
    /**
     * 审批器决定通过保存,审批不通过决定保存
     */
    public static final String GET_APPROVE_THROUGH_SAVE_INFO = HEADER_URL+"/rest/approve/saveAeaHiApproveReceipt";

    /**
     * 获取阶段信息
     */
    public static final String GET_EVENT_STATE_INFO = HEADER_URL + "rest/approve/type/and/state";

    /**
     * 查看已申报项目所选情形  并联
     * 3bed6998-f08a-4447-9812-041f1303184a
     */
    public static final String GET_EVENT_SITUATIONA = HEADER_URL + "rest/approve/parallel/proj/applied/states";
    /**
     * 查看已申报项目所选情形  单项
     * 86cad292-215f-4af8-bfef-c278ad5da171
     */
    public static final String GET_EVENT_SITUATIONB = HEADER_URL + "rest/approve/item/proj/applied/states";

    /**
     * /rest/query/queryItemInfoTaskId?iteminstId=ac9a0975-7f72-4346-8e64-3bc25848ca82&handler=false
     * 根据itemid查taskid
     */
    public static final String GET_TASKID_BY_ITEMID = HEADER_URL + "rest/query/queryItemInfoTaskId";

    /**
     * /rest/front/process/getProcessTurningParams/05db446e-c218-4162-978c-0035da83ed08?isNotCompareAssignee=true
     * 根据taskid获取项目id
     */
    public static final String GET_PROJECTID_BY_TASKID = HEADER_URL + "rest/front/process/getProcessTurningParams";

    /**
     * 办理意见列表接口	返回意见的list
     */
    public static final String GET_EVENT_ADVICE_INFO = HEADER_URL + "rest/bpm/task/listHistoryComment";
    /**
     * 审批意见的list，4.0
     */
    public static final String GET_EVENT_ADVICE_LIST_4_0 = HEADER_URL + "rest/bpm/approve/comment/tree";
    /**
     * 事项列表接口	返回事项的list
     */
    public static final String GET_EVENT_ISMT_INFO = HEADER_URL + "rest/approve/iteminst/list";
    /**
     * 事项列表接口	获取iteminstId
     */
    public static final String GET_EVENT_ITEM_ID_4_0 = HEADER_URL + "rest/approve/basic/getIteminstId";
    /**
     * 并联材料的list
     */
    @Deprecated
    public static final String GET_EVENT_PARMATINS_INFOA = HEADER_URL + "rest/item/getMatisntList";
    /**
     * 单项材料的list
     * applyinstId=d7f86eac-6ea7-4037-b49b-b37fb6d9f661
     */
    @Deprecated
    public static final String GET_EVENT_PARMATINS_INFOB = HEADER_URL + "rest/item/getMatisntList";

    /**
     * 材料列表,根据列表id获取item附件
     */
    @Deprecated
    public static final String GET_EVENT_PARMATINS_FILE_INFO = HEADER_URL + "rest/approve/matinst/att/list";
    /**
     * 材料列表，根据id获取item附件，4.0
     * applyinstId matId
     */
    public static final String GET_EVENT_MATERIAL_LIST = HEADER_URL + "rest/approve/series/matinst/list";
    /**
     * 结果物补充物材料
     */
    public static final String GET_RESULT_MATERIAL_LIST = HEADER_URL + "rest/approve/getOuterIteminstOutput";
    /**
     * 材料列表，上传材料，4.0
     * rest/approve/matinst/uploadAtt
     * applyinstId 申请实例ID  matId 材料定义ID  matinstId 材料实例ID
     */
    public static final String UPLOAD_EVENT_MATERIAL_FILE = HEADER_URL + "rest/approve/matinst/uploadAtt";
    /**
     * 材料列表,材料item的材料库
     */
    public static final String GET_EVENT_MATERIAL_STORE = HEADER_URL + "rest/approve/matinst/matinstFileLibrary";
    /**
     * 材料item关联材料库的材料
     */
    public static final String POST_EVENT_MATERIAL_FILE_LINK = HEADER_URL + "rest/approve/matinst/createMatinstAndFileLink";
    /**
     * 批文批复的list并联
     * 材料列表,根据批文批复id获取item材料附件
     */
    @Deprecated
    public static final String GET_EVENT_PWPF_FILE_INFO = HEADER_URL + "rest/approve/matinst/att/list";

    /**
     * 附件删除
     */
    public static final String DELETE_ATTACHMENT = HEADER_URL + "rest/mall/apply/deleteFile";


    /**
     * 审批附件上传
     */
    public static final String UPLOAD_ATTACHMENT = HEADER_URL + "rest/mall/apply/uploadApproveFile";

    /**
     * 待办已办中根据id获取事件详情基本信息的批文批复列表（含附件）接口	返回批文批复的list
     */
    @Deprecated
    public static final String GET_EVENT_PWPF_INFOA = HEADER_URL + "rest/approve/docs/parallel";

    /**
     * 批文批复的list单项
     */
    @Deprecated
    public static final String GET_EVENT_PWPF_INFOB = HEADER_URL + "rest/approve/docs/single";
    /**
     * 批文批复的list 4.0
     */
    public static final String GET_EVENT_PWPF_LIST = HEADER_URL + "rest/approve/docs/list";
    /**
     * 批文批复的list 4.0
     * 编辑批文批复-查询当前批复下附件列表
     */
    public static final String GET_EVENT_PWPF_FILE_LIST = HEADER_URL + "rest/approve/getOfficeAttListByMatinstId";
    /**
     * 获取批文批复的批文类型
     */
    public static final String GET_EVENT_PWPF_TYPE = HEADER_URL + "rest/approve/getItemInOfficeMat";
    /**
     * 证件证照的list 4.0
     */
    public static final String GET_EVENT_ZJZZ_LIST = HEADER_URL + "rest/approve/getCertinstListByApplyinstIdAndIteminstId";
    /**
     * 获取批文批复的批文类型
     */
    public static final String GET_EVENT_ZJZZ_TYPE = HEADER_URL + "rest/approve/getItemOutputCert";

    /**
     * 附件下载
     * bpm-rest/bsc/att/downloadAttachment.do?detailId=a309e36e-b51e-4db6-95a9-b0b4bfedae25
     */
    @Deprecated
    public static final String URL_FILE_DONMLOAD = HEADER_URL + "rest/mall/apply/downloadAttachment";

    /**
     * 附件下载4.0
     * rest/user/file/applydetail/mat/download/{detailId}
     */
    public static final String URL_FILE_DONMLOAD_4_0 = HEADER_URL + "rest/file/applydetail/mat/download";
    /**
     * 证件证照下载4.0
     * rest/user/file/applydetail/mat/download/{detailId}
     */
    public static final String URL_ZJZZ_FILE_DONMLOAD_4_0 = HEADER_URL + "rest/receive/preview/construct/permit";
    /**
     * 获取待办数量
     */
    public static final String GET_DB_COUNT = HEADER_URL + "rest/bpm/history/getGtasksCount";

    /**
     * 获取代办、逾期、预警任务数量(主页红色的角标显示)
     */
    public static final String GET_All_COUNT = HEADER_URL + "rest/approve/tasksCount";

    /**
     * 获取已办数量
     */
    public static final String GET_YB_COUNT = HEADER_URL + "rest/bpm/history/getYtasksCount";
    /**
     * 获取待办已办数量4.0
     */
    public static final String GET_DB_YB_COUNT_4_0 = HEADER_URL + "rest/org/portal/count";

    /**
     * 获取视图
     */
    public static final String GET_VIEW_LIST = HEADER_URL + "rest/app/view/getPageViewData";

    /**
     * 获取下一环节
     */
    public static final String GET_NEXT_LINK = HEADER_URL + "rest/bpm/task/getBpmDestTaskConfig";
    /**
     * 获取办理结果
     */
    public static final String GET_DEAL_WITH = HEADER_URL+"rest/mobile/task/getAuthorizeElementPlus";
    /**
     * 获取下一环节4.0
     */
    public static final String GET_NEXT_LINK_4_0 = HEADER_URL + "rest/approve/getTaskSendConfig";
    /**
     * 获取审批按钮列表4.2
     */
    public static final String GET_ELEMENT_BUTTON_4_2 = HEADER_URL + "rest/mobile/task/getAuthorizeElementPlus";
    /**
     * 添加审批意见4.2
     */
    public static final String ADD_TASK_COMMENT_4_2 = HEADER_URL + "rest/bpm/approve/addTaskComment";
    /**
     * 获取下一环节范围
     */
    public static final String GET_NEXT_LINK_USER = HEADER_URL + "rest/bpm/task/getAssigneeRange";
    /**
     * 发送到下一环节
     */
    public static final String SEND_TO_NEXT_LINK = HEADER_URL + "rest/approve/wfSend";
    /**
     * 窗口流程：流程审批并更改申请状态和事项状态按钮方法（使用于既要推动流程流转，也需要修改申报状态和事项状态时使用）
     */
    public static final String SEND_AND_CHANGE = HEADER_URL + "rest/approve/btn/win/wfSendAndChangeApplyAndItemState";
    /**
     * 材料补正--获取材料列表
     */
    public static final String CLBZ_GET_MATERIALS = HEADER_URL + "rest/approve/correct/getLackMats";
    /**
     * 发起材料补正
     */
    public static final String CLBZ_START_FLOW = HEADER_URL + "rest/approve/correct/createMatCorrectinst";
    /**
     * 获取材料补正详情信息
     */
    public static final String CLBZ_DETAIL_INFO = HEADER_URL + "rest/approve/getItemCorrectList";
    /**
     * 获取特殊程序详情信息
     */
    public static final String TSCX_DETAIL_INFO = HEADER_URL + "rest/approve/getItemSpecialList";
    /**
     * 获取通用结果物列表
     */
    public static final String GET_RESULT_GOODS = HEADER_URL + "rest/approve/generic/results";
    /**
     * 获取其它结果物列表
     */
    public static final String GET_OTHER_GOODS = HEADER_URL + "rest/approve/docs/list/other/result";
    /**
     * 新增其他结果物
     */
    public static final String CREATE_OTHER_GOODS = HEADER_URL + "rest/approve/docs/create/other/result";

    //网厅部分

    /**
     * 获取政策文件
     */
//    public static final String GET_RAW_FILE = HEADER_URL +"/rest/mall/getPolicyDocument";
    public static final String GET_RAW_FILE = HEADER_URL + "docs/policy_file.pdf";
    /**
     * 办事指南
     */
//    public static final String GET_BUSINESSGUIDE = HEADER_URL +"/rest/mall/getBusinessGuide";
    public static final String GET_BUSINESSGUIDE = HEADER_URL + "rest/html5/guide/list";
    /**
     * 改革流程图
     */
//    public static final String GET_REFORMFLOW = HEADER_URL +"/rest/mall/getReformFlow";
    public static final String GET_REFORMFLOW = HEADER_URL + "docs/process_diagram.pdf";
    /**
     * 告知承诺
     */
    public static final String GET_INFORMPROMISE = HEADER_URL + "rest/mall/getInformPromise";

    /**
     * 政策法规
     */
    public static final String GET_POLICY = HEADER_URL + "rest/index/policy/pdf";

    /**
     * 政策指引，获取tab和item
     */
    public static final String GET_ZCZY_GUIDE_DIRS = HEADER_URL+"rest/user/guide/cloud/getOPGuideDirs";

    /**
     * 政策指引，根据item获取文件列表
     * rest/user/guide/cloud/getGuideFilesByDir/{dirId}
     */
    public static final String GET_ZCZY_FILES_BY_DIRS = HEADER_URL+"rest/user/guide/cloud/getGuideFilesByDir";
    /**
     * 办公统计
     */
    //public static final String GET_STATISTICALANALYSIS = HEADER_URL +"/rest/mall/getStatisticalAnalysis";

    public static final String GET_STATISTICALANALYSIS = HEADER_URL + "rest/index/approve/stats";


    //待办已办
    /**
     * 批文批复新增
     */
    public static final String CREATE_PWPF = HEADER_URL + "rest/approve/docs/create";
    /**
     * 批文批复--保存或更新输出的证照实例
     * rest/approve/saveAeaCertinst
     */
    public static final String SAVE_ZJZZ = HEADER_URL + "rest/approve/saveAeaCertinst";
    /**
     * 批文批复删除
     */
    public static final String DELETE_PWPF = HEADER_URL + "rest/approve/docs/delete";
    /**
     * 批文批复--证件证照删除
     */
    public static final String DELETE_ZJZZ = HEADER_URL + "rest/approve/certinst/batch/delete";

    /**
     * 批文批复编辑
     */
    public static final String EDIT_PWPF = HEADER_URL + "rest/approve/docs/edit";

    /**
     * 审批材料列表
     */
    public static final String CLFJ_LIST = HEADER_URL + "rest/approve/loadApplyData";

    /**
     * 删除申报材料或批文批复附件
     */
    public static final String DELETE_PWPF_MATERIAL_ATTACHMENT = HEADER_URL + "rest/file/delelteAttachment";

    /**
     * 上传申报材料或批文批复附件
     */
    public static final String UPLOAD_PWPF_MATERIAL_ATTACHMENT = HEADER_URL + "rest/approve/att/upload";
    /**
     * 审核情况
     */
    public static final String CHECK_INDEX_LIST = HEADER_URL + "rest/index/approve/details";

    /**
     * 通知公告
     */
    public static final String ANNOUNCE_LIST = HEADER_URL + "rest/aoa/notice/content/getPageAoaNoticeContentCascade";
    /**
     * 我的消息
     */
    public static final String MESSAGE_LIST = HEADER_URL + "rest/aoa/msg/range/getPageAoaMsgRangeForMyReceive";
    /**
     * 审批情况
     */
    public static String GET_APPROVE_INFO = HEADER_URL + "rest/index/approve/details";
    /**
     * 个人消息
     */
    public static final String PERSON_MSG_LIST = HEADER_URL + "rest/aoa/msg/range/getPageAoaMsgRangeForMyReceive";
    /**
     * 更新消息已读状态
     */
    public static final String UPDATE_MSG_STATE = HEADER_URL + "rest/aoa/msg/range/updateAoaMsgRangeReaded";

    /**
     * 不予受理列表
     */
    public static final String REJECT_LIST = HEADER_URL + "rest/query/listDismissedApply";

    /**
     * 申报预警列表
     * instState 2.预警 3.逾期
     */
    public static final String APPLY_ALERT_LIST = HEADER_URL + "rest/query/listApplyinfo";

    /**
     * 签收任务
     * /rest/front/task/signTask/{taskId}
     */
    public static final String SIGN_TASK = HEADER_URL+"rest/front/task/signTask";

    /**
     * 获取组织列表
     * admin/process/getAssigneeOrgTreeData.do
     */
    public static final String GET_ORG_TREE = HEADER_URL+"rest/admin/process/getAssigneeOrgTreeData.do";

    /**
     * 任务转办
     * rest/front/task/sendOnTask
     */
    public static final String SEND_ON_TASK = HEADER_URL+"rest/front/task/sendOnTask";

}
