package com.augurit.common.common.manager;

public interface QualityConstant {
    String SERVICE_ROOT_URL = "swguser/app/";
    /**
     * 获取所有字典
     */
    String GET_USER_LISTTYPES = SERVICE_ROOT_URL + "user/listTypes";
    /**
     * 根据数据字典类型编码获取字典子项列表
     */
    String GET_USER_LGETITEMSBYTYPECODE = SERVICE_ROOT_URL + "user/lgetItemsByTypeCode";
    /**
     * 根据类型编码获取类型树结构
     */
    String GET_USER_GETITEMTREEBYTYPECODE = SERVICE_ROOT_URL + "user/getItemTreeByTypeCode";

    /**
     * 新增公共投诉
     */
    String POST_COMPLAINT_ADDORUPDATEINFO = SERVICE_ROOT_URL + "complaint/addOrUpdateInfo";

    /**
     * 删除公共投诉
     */
    String POST_COMPLAINT_DELINFO = SERVICE_ROOT_URL + "complaint/delInfo";
    /**
     * 获取公共投诉列表
     */
    String POST_COMPLAINT_LISTINFO = SERVICE_ROOT_URL + "complaint/listInfo";

    /**
     * 河长巡检
     */
    /**
     * 获取轨迹配置信息
     */
    String GET_RIVER_GETTRACKCONFIG = SERVICE_ROOT_URL + "river/gettrackConfig";
    /**
     * 保存轨迹
     */
    String POST_RIVER_SAVETRACKLINE = SERVICE_ROOT_URL + "river/saveTrackLine";
    /**
     * 保存轨迹点
     */
    String POST_RIVER_ADDTRACKPOINT = SERVICE_ROOT_URL + "river/addTrackPoint";
    /**
     * 删除轨迹
     */
    String DELETE_RIVER_DELETETRACKLINE = SERVICE_ROOT_URL + "river/deleteTrackLine";
    /**
     * 根据用户id获取轨迹列表
     */
    String GET_RIVER_GETTRACKLINESBYLOGINNAME = SERVICE_ROOT_URL + "river/getTrackLinesByLoginName";
    /**
     * 根据轨迹id获取轨迹点
     */
    String GET_RIVER_GETFORMBYTRACKID = SERVICE_ROOT_URL + "river/getFormByTrackId";

    /**
     * 排水户巡检
     */
    /**
     * 获取轨迹配置信息
     */
    String GET_USER_GETTRACKCONFIG = SERVICE_ROOT_URL + "user/gettrackConfig";
    /**
     * 保存轨迹
     */
    String POST_USER_SAVETRACKLINE = SERVICE_ROOT_URL + "user/saveTrackLine";
    /**
     * 保存轨迹点
     */
    String POST_USER_ADDTRACKPOINT = SERVICE_ROOT_URL + "user/addTrackPoint";
    /**
     * 删除轨迹
     */
    String DELETE_USER_DELETETRACKLINE = SERVICE_ROOT_URL + "user/deleteTrackLine";
    /**
     * 根据用户id获取轨迹列表
     */
    String GET_USER_GETTRACKLINESBYLOGINNAME = SERVICE_ROOT_URL + "user/getTrackLinesByLoginName";
    /**
     * 根据轨迹id获取轨迹点
     */
    String GET_USER_GETFORMBYTRACKID = SERVICE_ROOT_URL + "user/getFormByTrackId";
    /**
     * 查询轨迹记录
     */
    String SZ_GET_TRACK_LIST = "/agsupport/rest/jwxcyh/queryGpsByUserid";
    /**
     * 河道问题上报
     */
    String RIVER_PROBLEM_UPLOAD = SERVICE_ROOT_URL + "river/addOrUpdateRiverProblem";
    /**
     * 河道 我的上报
     */
    String RIVER_PROBLEM_UPLOAD_LIST = SERVICE_ROOT_URL + "river/getDipReportList";
    /**
     * 河道 问题上报处理
     */
    String RIVER_PROBLEM_UPLOAD_DEAL = SERVICE_ROOT_URL + "river/dealRiverReportProblm";
    /**
     * 排水户 问题上报 - 获取排水户
     */
    String GET_PSH_LIST = SERVICE_ROOT_URL + "user/getDpiPerson";
    /**
     * 排水户 上报类型(问题上报功能使用，无参数)
     */
    String GET_PSH_UPLOAD_TYPE_ = SERVICE_ROOT_URL + "user/getInitTypeList";
    /**
     * 排水户 设施类型以及问题类型(问题上报功能使用，无参数)
     */
    String GET_PSH_FACILITY_PROBLEM_TYPE = SERVICE_ROOT_URL + "user/getItemList";
    /**
     * 排水户 问题上报列表
     */
    String GET_PSH_PROBLEM_LIST = SERVICE_ROOT_URL + "user/listDipReport";
    /**
     * 排水户 问题上报
     */
    String PSH_PROBLEM_UPLOAD = SERVICE_ROOT_URL + "user/addOrUpdateDpiProblm";
    /**
     * 排水户 问题上报处理
     */
    String PSH_PROBLEM_UPLOAD_DEAL = SERVICE_ROOT_URL + "user/dealDipReportProblm";
//    /**
//     * 排水户 根据数据字典类型编码获取字典子项列表 获取问题类型
//     */
//    String GET_PSH_PROBLEM_TYPE = SERVICE_ROOT_URL + "user/lgetItemsByTypeCode";
    /**
     * 河道 查看处理情况
     */
    String GET_DEAL_INFO_BYPROBLEM_ID = SERVICE_ROOT_URL + "river/getDealInfoByProblemId";
    /**
     * 排水户 查看处理情况
     */
    String GET_PSH_DEAL_INFO_BYPROBLEM_ID = SERVICE_ROOT_URL + "user/getDealInfo";
    /**
     * 获取所有测站
     */
    String GET_ALL_STATION = SERVICE_ROOT_URL + "monitor/getAllStation";
    /**
     * 获取测站水位水质数据
     */
    String GET_STATION_WATER_DATA = SERVICE_ROOT_URL + "monitor/getStationRealInfo";
    /**
     * 获取测站水质预警
     */
    String GET_STATION_WATER_ALARM = "monitor/monit/getWqAlarmOnce";
    /**
     * 附件
     */
    String GET_FILES = SERVICE_ROOT_URL + "user/listFileInfoByGroupId";
    /**
     * 下载附件
     */
    String GET_FILE_BY_ID = "/upload/getFile";
}
