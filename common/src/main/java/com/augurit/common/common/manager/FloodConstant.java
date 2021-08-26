package com.augurit.common.common.manager;

/**
 * 包名：com.augurit.agmobile.agwater5.flood.dispatch.constant
 * 类描述：
 * 创建人：luobiao
 * 创建时间：2019/4/4 15:04
 * 修改人：luobiao
 * 修改时间：2019/4/4 15:04
 * 修改备注：
 */
public class FloodConstant {

    /**
     * 获取队伍列表
     */
    public static final String QUEUE_LIST = "psemgy/yjTeam/listAll?orgType=";

    public static final String BASE_TEST_URL = "https://www.easy-mock.com/mock/5c10a7ef8c59f04d2e3a76bd/agwater/";
    public static final String BASE_URL_MONITOR = "http://192.168.31.60:8093/psjk/app/";

    /**
     * 返回气象灾害预警列表
     */
    public static final String GET_HYDROLOG_LIST = "psemgy/meteoHydrologAlarm/disasteJson";

    /**
     * 保存添加气象预警数据
     */
    public static final String POST_HYDROLOG_SAVE = "psemgy/meteoHydrologAlarm/saveJson";

    /**
     * 气象预警数据删除
     */
    public static final String POST_HYDROLOG_DELETE = "psemgy/meteoHydrologAlarm/deleteMore";

    /**
     * 气象水文预警明细查询
     */
    public static final String GET_HYDROLOG_DETAIL = "psemgy/meteoHydrologAlarm/inputJson";

    /**************************************涝情发布*******************************************/
    /**
     * 根据id获取涝情记录明细
     */
    public static final String GET_WATERLOGGED_DETAIL = "psemgy/floodRecord/inputJson";

    /**
     * 返回涝情数据列表
     */
    public static final String GET_WATERLOGGED_LIST = "psemgy/floodRecord/listJson";

    /**
     * 保存涝情记录
     */
    public static final String POST_WATERLOGGED_SAVE = "psemgy/floodRecord/saveOneJson";

    /**************************************调度指令*******************************************/

    /**
     * 短信调度分页查询
     */
    public static final String POST_SMS_LIST = "psemgy/yjSmsRecord/listJson";

    /**
     * 根据id返回短信调度明细
     */
    public static final String GET_SMS_DETAIL = "psemgy/yjSmsRecord/inputJson";

    /**
     * 获取组织架构的信息
     */
    public static final String POST_SMS_ORGANIZATION = "psemgy/omOrg/getOpuOmOrgZHBZTree";

    /**
     * 发送短信
     */
    public static final String POST_SMS_SAVE = "psemgy/yjSmsRecord/saveSmsJson";

    /**
     * 获取短信预警模板
     */
    public static final String POST_SMS_TEMPLATE = "psemgy/yaTemplateSms/getAlarmTypeSms";


    /**
     * 在线监测
     */
    //矢量地图
    public static final String ShiLiangUrl = "http://192.168.30.147:6080/arcgis/rest/services/basemap/base_vecmap/MapServer";
    //地貌晕渲图
    public static final String DeMUrl = "http://192.168.30.147:6080/arcgis/rest/services/basemap/base_dem/MapServer";
    //影像地图
    public static final String YingXiangUrl = "http://192.168.30.147:6080/arcgis/rest/services/basemap/base_image_qp/MapServer";

    /**
     * 易涝隐患点（WL）：
     * 积水点水位（WL-2）   隐患点视频（WL-5）
     * <p>
     * 河道（ZZ）：
     * 水位（ZZ-2）  流量（ZZ-1）   视频 （ZZ-5）
     * <p>
     * 雨量站（PP）：
     * 雨量（PP-3）
     * <p>
     * 泵站（DP）：
     * 排水泵站水位（DP-2）    防洪泵站水位（DP-6）  视频（DP-5）
     * <p>
     * 防洪闸（DD）
     * 水位（DD-2）   视频（DD-5）
     * <p>
     * 窨井（UP）
     * 水位（UP-2）  流量 （UP-1）
     */
    //易涝隐患点（WL）
    public static final String WL_2 = "WL-2";
    public static final String WL_5 = "WL-5";
    //河道（ZZ）
    public static final String ZZ_2 = "ZZ-2";
    public static final String ZZ_1 = "ZZ-1";
    public static final String ZZ_5 = "ZZ-5";
    //雨量站（PP）
    public static final String PP_3 = "PP-3";
    //泵站（DP）
    public static final String DP_2 = "DP-2";
    public static final String DP_6 = "DP-6";
    public static final String DP_5 = "DP-5";
    //防洪闸（DD）
    public static final String DD_2 = "DD-2";
    public static final String DD_5 = "DD-5";
    //窨井（UP）
    public static final String UP_2 = "UP-2";
    public static final String UP_1 = "DP-6";
    public static final String NAME_PUMP = "泵站设施";
    public static final String CODE_PUMP = "bz";
    public static final String NAME_TRAFFIC_SLUICE = "交通闸";
    public static final String CODE_TRAFFIC_SLUICE = "jtz";
    public static final String NAME_EM_BANKMENT_CULVERT = "穿堤涵管";
    public static final String CODE_EM_BANKMENT_CULVERT = "cthg";
    public static final String NAME_FH_FLOOD_BANK = "防洪闸";
    public static final String CODE_FH_FLOOD_BANK = "fhz";

    //获取图例
    public static final String MONITOR_GET_LEGEND = "psjk/app/getLegend";
    //获取所有的測站信息
    public static final String MONITOR_GET_STATIONS = "psjk/app/getStations";
    //获取实时水位信息
    public static final String MONITOR_GET_REALDATA = "psjk/app/realData";
    /**
     * 查询设施信息根据图层
     */
    public static final String GCGH_BY_TYPE_AND_ID = "psjk/station/getWxStationByMap";

    /**
     * 获取所有设施点位
     */
    public static final String DEVICE_INFO_LIST = "psjk/station/getAllStation";
    /**
     * 获取所有测站最新检测值
     */
    public static final String GET_ALL_STATION_VALUE = "rest/app/psjk/getAllStationValue";

    /**
     * 根据时间 获取某个积水点水位监测数据 WL-2
     */
    public static final String GET_FLOOD_POINT_DATE_BY_TIME = "rest/app/psjk/getFloodPointDateByTime";

    /**
     * 最近监测数据时间
     */
    public static final String GET_DATE_RANFE = "rest/app/psjk/getDateRange";

    /**
     * 根据时间 获取某个河道水位监测数据 ZZ-2
     */
    public static final String GET_RIVER_DATA_BY_TIME = "psengineer/rest/app/psjk/getRiverDataByTime";

    /**
     * 根据时间 获取某个排水泵站水位监测数据 DP-2
     */
    public static final String GET_PUMP_DATA_BY_TIME = "psengineer/rest/app/psjk/getPumpDataByTime";

    /**
     * 根据时间 获取某个防洪闸水位监测数据 DD-2
     */
    public static final String GET_WAS_DATE_BY_TIME = "psengineer/rest/app/psjk/getWasDateByTime";

    /**
     * 根据时间 获取某个窨井水位监测数据 UP-2
     */
    public static final String GET_DRAINAGE_DATA_BY_TIME = "psengineer/rest/app/psjk/getDrainageDataByTime";

    /**
     * 获取窨井警戒水位请求
     */
    public static final String GET_STATION_PROPERTIES = "rest/app/psjk/getStationProperties";

    /**
     * 获取所有堤防信息
     */
    public static final String GET_ALL_DF_INFO = "rest/app/hedaoResource/getDfAll";

    /**
     * 获取所有机构信息
     */
    public static final String GET_ALL_ORG_INFO = "psemgy/omOrg/getOpuOmOrgZHBZTree";

    /**
     * 获取预警信息
     */
    public static final String GET_ALARM_INFO = "psjk/app/getAlarm";

    /**
     * 获取所有堤防信息
     */
    public static final String GET_DF_ALL = "riverinfo/rest/app/hedaoResource/getDfAll";



}
