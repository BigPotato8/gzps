package com.augurit.agmobile.agwater5.gcjs_public.common;

/**
 * @description url
 * @date: $date$ $time$
 * @author: xieruibin
 */
public class GcjsPuUrlConstant {
    public static final String PRE1 = "cp-rest";
    public static final String PRE2 = "/aplanmis-rest";
    public static final String PRE3 = "cp-rest";
    /**
     * 公众版登录 用户信息 组织列表
     */
//    public static final String AW_SSO_GET_AUTHENTICATION_PUBLIC = "cp-rest/rest/mall/unit/login";
    public static final String AW_SSO_GET_AUTHENTICATION_PUBLIC = PRE2+"/rest/mall/mobile/login";

    /**
     * 企业空间 --> 我要申报 --> 查询
     */
    public static String GET_PROJECT_INFO = "cp-rest/rest/mall/unit/proj/getProjectInfo";

    /**
     * 企业空间 --> 我要申报 --> 我的项目列表
     */
    public static String GET_MY_PROJECT = "cp-rest/rest/mall/unit/proj/owner";

    /**
     * 首页 --> 审批情况
     */
    //TODO 新的
//    public static String GET_APPROVE_INFO = "/aplanmis-mall/rest/main/approve/list";

    public static String GET_APPROVE_INFO = PRE2+"/rest/index/approve/details";
    /**
     * 查询所有部门
     */
    public static String GET_DEPARTMENT_LIST = "cp-rest/rest/guide/theme/and/org/list";
    /**
     * 查询部门下所有事项
     */
    public static String GET_EVENTLIST_BY_DEPARTMENT = "cp-rest/rest/guide/item/list";
    /**
     * 获取事项及项目，单位，联系人信息
     */
    public static String GET_EVENT_AND_PROJECT_INFO = "cp-rest/rest/mall/unit/proj/getAeaItemAndProjinfo";
    /**
     * 获取事项及项目，单位，联系人信息
     */
    public static String SINGLE_EVENT_UPLOAD = "cp-rest/rest/mall/unit/startNetSeriesFlow";
    /**
     * 获取单项的情形
     */
    public static String SINGLE_SERIES = "cp-rest/rest/mall/unit/getSeriesMatList";

    /**
     * 选择阶段-获取主题和部门列表
     */
    public static String GET_THEME_AND_ORG = "cp-rest/rest/guide/theme/and/org/list";
    /**
     * 选择阶段-获取阶段
     */
    public static String GET_STAGE = "cp-rest/rest/guide/stage/list";

    /**
     * 选择阶段-根据主题阶段获取事项列表
     */
    public static String GET_EVEVTLIST_BY_THEMESTAGE = "cp-rest/rest/guide/item/list";

    /**
     * 选择阶段-根据阶段获取情形、子情形
     */
    public static String GET_MULTI_SITUATION = "cp-rest/rest/mall/unit/parallel/states";

    /**
     * 查询并联申报所需的所有材料
     */
    public static String GET_MULTI_MATS = "cp-rest/rest/mall/unit/parallel/mats";

    /**
     * 并联发起申报
     */
    public static String POST_MULTI_UPLOAD = "cp-rest/rest/mall/unit/parallel/start";
    /**
     * 已申报项目列表
     */
    public static String GET_EVENT_LIST = "cp-rest/rest/mall/unit/proj/list";
    /**
     * 获取办件统计
     */
    public static String GET_STATS = PRE2+"/rest/index/approve/stats";
    /**
     * 材料列表的附件上传
     */
    public static final String UPLOAD_MATERIAL = "cp-rest/rest/mall/apply/uploadFile";
    /**
     * 上传附件
     */
    public static String UPLOAD_FILES = "cp-rest/rest/mall/apply/uploadFile";


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
//    public static final String GET_REFORMFLOW = "aplanmis-mall/mall/file/tangshan/shortcutEntry/%E6%B5%81%E7%A8%8B%E5%9B%BE.pdf";
    public static final String GET_REFORMFLOW = "http://106.52.77.101:8084/aplanmis-mall/mall/file/tangshan/shortcutEntry/%E6%B5%81%E7%A8%8B%E5%9B%BE.pdf";
    /**
     * 政策法规
     */
    public static final String GET_INFORMPROMISE = PRE2+"/rest/index/policy/pdf";

    /**
     * 事项清单
     */
    public static final String GET_EVENT_LIST_FILE = "cp-rest/docs/item_mat_list.xlsx";

    /**
     * 办事指南
     */
    public static final String GET_BSZN_LIST_FILE = PRE2+"/rest/index/policy/handingGuides";


    /**
     * 首页-->根据项目名称编码流水号查询功能接口
     */
    public static final String INDEX_SEARCH_PROJECT = "/aplanmis-mall/rest/main/applyinst/list";
    /**
     * 首页-->获取审批情况列表数据
     */
    public static final String GET_APPROVE_LIST = "/aplanmis-mall/rest/main/approveData/list";
    /**
     * 首页-->我的项目库
     */
//    public static final String GET_MY_PROJECT_LIST = "/aplanmis-mall/rest/user/proj/list";
    //我的项目库
    public static final String GET_MY_PROJECT_LIST =  PRE2+"/rest/user/proj/list";
    //我的项目库搜索
    public static final String GET_MY_PROJECT_SEARCH_LIST =PRE2+"/rest/user/searchProj/list";
    /**
     * 首页-->项目进度
     */
    public static final String GET_MY_PROJECT_PROGRESS = PRE2+"/rest/user/itemSchedule/list";

    /**
     * 首页-->已申报列表
     */
    public static final String GET_EVENT_UPLOADED_LIST = PRE2+"/rest/user/hadApplyItem/list";
    /**
     * 首页-->材料补全
     */
    public static final String COMPLETE_MATERIAL_LIST = PRE2+"/rest/user/matComplete/matComplet/list";
    /**
     * 首页-->办件列表
     */
    public static final String APPROVE_LIST = PRE2+"/rest/user/approve/list";
    /**
     * 首页-->材料补正
     */
    public static final String MATERIAL_SUPPLY_LIST = PRE2+"/rest/user/matSupply/list";
    /**
     * 首页-->审批情况
     */
//    public static final String ANNOUNCE_LIST = PRE2+"/rest/main/approveData/list";
//    public static final String ANNOUNCE_LIST =PRE2+"/rest/user/approve/list";
    public static final String ANNOUNCE_LIST = PRE2+"/rest/index/approve/details";

    /**
     * 首页-->我的材料库
     */
    public static final String MY_MATERIAL_LIST = PRE2+"/rest/user/mat/list";

    /**
     * 获取数据字典
     */
    public static final String GET_DICT = PRE2+"/rest/user/getDicContents";
}
