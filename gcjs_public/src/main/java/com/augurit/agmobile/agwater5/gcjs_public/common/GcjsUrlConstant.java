package com.augurit.agmobile.agwater5.gcjs_public.common;

/**
 * com.augurit.agmobile.agwater5.gcjs.common
 * Created by sdb on 2019/4/4  9:44.
 * Desc：
 */

public class GcjsUrlConstant {

    //获取用户信息
    public static final String GET_USER_INFO = "rest/opus/om/getOpuOmUserInfoByUserId.do";
    /**
     * 待办已办中根据id获取事件详情基本信息的项目申报接口
     * 返回项目申报基本信息
     */
    public static final String GET_EVENT_INFO = "cp-rest/rest/approve/common/getApplyDetail";

    /**
     * 办理意见列表接口	返回意见的list
     */
    public static final String GET_EVENT_ADVICE_INFO = "cp-rest/rest/bpm/task/listHistoryComment";
    /**
     * 事项列表接口	返回事项的list
     */
    public static final String GET_EVENT_ISMT_INFO = "cp-rest/rest/approve/iteminst/list";
    /**
     * 并联材料的list
     */
    public static final String GET_EVENT_PARMATINS_INFOA = "cp-rest/rest/item/getMatisntListByApplyinstId";
    /**
     * 单项材料的list
     * applyinstId=d7f86eac-6ea7-4037-b49b-b37fb6d9f661
     */
    public static final String GET_EVENT_PARMATINS_INFOB = "cp-rest/rest/item/getItemMatListByApplyinstId";
    /**
     * 材料列表,根据列表id获取item附件
     */
    public static final String GET_EVENT_PARMATINS_FILE_INFO = "cp-rest/rest/approve/matinst/att/list";

    /**
     * 批文批复的list并联
     * 材料列表,根据批文批复id获取item材料附件
     */
    public static final String GET_EVENT_PWPF_FILE_INFO = "cp-rest/rest/approve/matinst/att/list";


    /**
     * 附件删除
     */
    public static final String DELETE_ATTACHMENT = "cp-rest/rest/mall/apply/deleteFile";


    /**
     * 审批附件上传
     */
    public static final String UPLOAD_ATTACHMENT = "cp-rest/rest/mall/apply/uploadApproveFile";

    /**
     * 材料列表的附件上传
     */
    public static final String UPLOAD_MATERIAL = "cp-rest/rest/mall/apply/uploadFile";

//    /**
//     * 材料列表,根据列表id获取item材料
//     */
//    public static final String GET_EVENT_PARMATINS_FILE_INFO = "cp-rest/rest/mall/apply/getAttFiles";
//
//    /**
//     * 批文批复的list并联
//     * 材料列表,根据批文批复id获取item材料
//     */
//    public static final String GET_EVENT_PWPF_FILE_INFO = "cp-rest/rest/mall/apply/getAttFiles";

    /**
     * 待办已办中根据id获取事件详情基本信息的批文批复列表（含附件）接口	返回批文批复的list
     */
    public static final String GET_EVENT_PWPF_INFOA = "cp-rest/rest/approve/docs/parallel";

    /**
     * 批文批复的list单项
     */
    public static final String GET_EVENT_PWPF_INFOB = "cp-rest/rest/approve/docs/single";
//    /**
//     * 附件上传
//     */
//    public static final String URL_FILE_UPLOAD = "bpm-rest/me/bsc/att/webUploader.do";
//
//    /**
//     * 附件查询
//     * bpm-rest/bsc/att/listAttLinkAndDetailNoPage.do?tableName=TEST_FORM&pkName=photos&recordId=018f96ee-cdea-492a-95a9-eac215fa0127
//     */
//    public static final String SEARCH = "bpm-rest/bsc/att/listAttLinkAndDetailNoPage.do";

    /**
     * 附件下载
     * bpm-rest/bsc/att/downloadAttachment.do?detailId=a309e36e-b51e-4db6-95a9-b0b4bfedae25
     */
    public static final String URL_FILE_DONMLOAD = "cp-rest/rest/mall/apply/downloadAttachment";

//    /**
//     * 附件删除
//     * bpm-rest/bsc/att/admin/remove.do?fileIds=3847ca61-51e1-43d0-8c97-ed4dec9e7e1f
//     */
//    public static final String URL_FILE_DELETE = "bpm-rest/bsc/att/admin/remove.do";
//
//    /**
//     * 根据viewcode获取ViewID
//     */
//    public static final String GET_VIEWID_BY_VIEW_CODE = "aplanmis-front/dg/view/getViewIdByViewCode.do";
//    /**
//     * 根据ViewID获取待办列表
//     */
//    public static final String GET_DB_EVENT_BY_VIEWID = "front/dynamic/view/getBootstrapPageViewData.do";
//
//    /**
//     * 获取所有流程数据总数量
//     */
//    public static final String GET_ALL_PROCESS_COUNT = "bpm-rest/rest/bpm/history/getAllProcessCount";
    /**
     * 获取待办数量
     */
    public static final String GET_DB_COUNT = "cp-rest/rest/bpm/history/getGtasksCount";

    /**
     * 获取已办数量
     */
    public static final String GET_YB_COUNT = "cp-rest/rest/bpm/history/getYtasksCount";
    /**
     * 获取视图
     */
    public static final String GET_VIEW_LIST = "cp-rest/rest/app/view/getPageViewData";

    /**
     * 获取下一环节
     */
    public static final String GET_NEXT_LINK = "cp-rest/rest/bpm/task/getBpmDestTaskConfig";
    /**
     * 获取下一环节范围
     */
    public static final String GET_NEXT_LINK_USER = "cp-rest/rest/bpm/task/getAssigneeRange";
    /**
     * 发送到下一环节
     */
    public static final String SEND_TO_NEXT_LINK = "cp-rest/rest/bpm/task/wfSend";

    //网厅部分

    /**
     * 获取政策文件
     */
//    public static final String GET_RAW_FILE = "cp-rest/rest/mall/getPolicyDocument";
    public static final String GET_RAW_FILE = "cp-rest/docs/policy_file.pdf";
    /**
     * 办事指南
     */
//    public static final String GET_BUSINESSGUIDE = "cp-rest/rest/mall/getBusinessGuide";
    public static final String GET_BUSINESSGUIDE = "cp-rest/rest/html5/guide/list";


    /**
     * 办事指南详情
     */
//    public static final String GET_BUSINESSGUIDE = "cp-rest/rest/mall/getBusinessGuide";
    public static final String GET_BUSINESSGUIDE_DETAIL = "cp-rest/rest/html5/guide/detail";
    /**
     * 改革流程图
     */
//    public static final String GET_REFORMFLOW = "cp-rest/rest/mall/getReformFlow";
    public static final String GET_REFORMFLOW = "cp-rest/docs/process_diagram.pdf";
    /**
     * 告知承诺
     */
    public static final String GET_INFORMPROMISE = "cp-rest/rest/mall/getInformPromise";
    /**
     * 办公统计
     */
    public static final String GET_STATISTICALANALYSIS = "cp-rest/rest/mall/getStatisticalAnalysis";


    /**
     * 批文批复新增
     */
    public static final String CREATE_PWPF = "cp-rest/rest/approve/docs/create";

    /**
     * 批文批复删除
     */
    public static final String DELETE_PWPF = "cp-rest/rest/approve/docs/delete";

    /**
     * 批文批复编辑
     */
    public static final String EDIT_PWPF = "cp-rest/rest/approve/docs/edit";

    /**
     * 审批材料列表
     */
    public static final String CLFJ_LIST = "cp-rest/rest/approve/loadApplyData";

    /**
     * 删除申报材料或批文批复附件
     */
    public static final String DELETE_PWPF_MATERIAL_ATTACHMENT = "cp-rest/rest/approve/att/remove";

    /**
     * 上传申报材料或批文批复附件
     */
    public static final String UPLOAD_PWPF_MATERIAL_ATTACHMENT = "cp-rest/rest/approve/att/upload";
    /**
     * 问题上报环节提交
     */
    public static final String POST_WTSB_SEND = "rest/pshWtsb/wfSend";

    /**
     * 通知公告
     */
    public static final String ANNOUNCE_LIST = "cp-rest/rest/aoa/notice/content/getPageAoaNoticeContentCascade";

    /**
     * 个人消息
     */
    public static final String PERSON_MSG_LIST = "cp-rest/rest/aoa/msg/range/getPageAoaMsgRangeForMyReceive";

    /**
     * 更新消息已读状态
     */
    public static final String UPDATE_MSG_STATE = "cp-rest/rest/aoa/msg/range/updateAoaMsgRangeReaded";
}
