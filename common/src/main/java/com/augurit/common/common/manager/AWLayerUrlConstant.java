package com.augurit.common.common.manager;

/**
 * @description 地图服务管理
 * @date: 2019/05/08
 * @author: xieruibin
 */
public class AWLayerUrlConstant {
    /**
     * 天地图_全球矢量地图服务
     */
    public static final String TIANDITU_VEC = "http://t0.tianditu.com/vec_c/wmts";
    /**
     * 天地图_全球矢量中文注记服务
     */
    public static final String TIANDITU_CVA = "http://t0.tianditu.com/cva_c/wmts";
    /**
     * 天地图_全球影像地图服务
     */
    public static final String TIANDITU_IMG = "http://t0.tianditu.com/img_c/wmts";
    /**
     * 广州影像地图
     */
    public static final String GUANGZHOU_MAP_IMG = "http://139.159.243.230:6080/arcgis/rest/services/ditu/img/MapServer";
    /**
     * 广州矢量地图
     */
    public static final String GUANGZHOU_MAP_VEC = "http://139.159.243.230:6080/arcgis/rest/services/vec/vec1/MapServer";



    /***********排水巡检************/
    /**
     * 排水巡检 GZSWGXFS 查询图层
     */
    public static final String GZPS_GZSWGXFS_URL = "http://139.159.243.185:6080/arcgis/rest/services/GZPS/GZSWGXFS/MapServer";
    /**
     * 排水巡检 数据上报图层
     */
    public static final String GZPS_SSJB_URL = "http://139.159.243.185:6080/arcgis/rest/services/GZPS/ssjb_ms/MapServer";
    /**
     * 排水巡检 设施图层
     */
    public static final String GZPS_SSTC_URL = "http://139.159.243.185:6080/arcgis/rest/services/GZPS/GZSWPSGXOwnDept/MapServer";
    /**
     * 排水巡检 巡检日志
     */
    public static final String GZPS_XJRZ_URL = "http://139.159.243.185:6080/arcgis/rest/services/GZPS/dbzt/MapServer";
    /**
     * 排水巡检 养护计划                             http://139.159.243.185:6080/arcgis/rest/services/GZPS/YHJH/MapServer
     */
    public static final String GZPS_YHJH_URL = "http://139.159.243.185:6080/arcgis/rest/services/GZPS/YHJH/MapServer";
    /**http://139.159.243.185:6080/arcgis/rest/services/GZPS/YHJH/MapServer
     * 排水巡检 数据上报
     */
    public static final String GZPS_SJSB_URL = "http://139.159.243.185:6080/arcgis/rest/services/report/MapServer";



    /***********排水户************/
    /**
     * 排水户 接驳井
     */
    public static final String PSH_JBJ_URL = "http://139.159.243.185:6080/arcgis/rest/services/PAISHUIHU/jiebojing_MS/MapServer";
    /**
     * 排水户 接驳井连线
     */
    public static final String PSH_JBJLX_URL = "http://139.159.243.230:6080/arcgis/rest/services/PAISHUIHU/GZPSGX_MS/MapServer";
    /**
     * 排水户 门牌号
     */
    public static final String PSH_MPH_URL = "http://139.159.243.185:6080/arcgis/rest/services/PAISHUIHU/menpaihao_ms/MapServer";
    /**
     * 排水户 房屋面
     */
    public static final String PSH_FWM_URL = "http://139.159.243.185:6080/arcgis/rest/services/PAISHUIHU/fagnwumian_ms/MapServer";



    /***********广州农污************/
    /**
     * 农污 农污上报
     */
    public static final String NW_NWSB_URL = "http://139.159.243.185:6080/arcgis/rest/services/GZNW/nw_report1/MapServer";






    /***********南宁邕江************/
    /**
     * 邕江
     */
    public static final String YONG_JIANG_MAP_URL = "http://39.108.76.192:6080/arcgis/rest/services/nnyj/nnriver2/MapServer";
    /**
     * 防洪堤
     */
    public static final String FLOOD_BANK_MAP_URL = "http://39.108.76.192:6080/arcgis/rest/services/nnyj/floodbank/MapServer";
    /**
     * 穿堤管
     */
    public static final String EM_BANKMENT_CULVERT = "http://120.198.125.231:20021/arcgis/rest/services/EmbankmentCulvert/MapServer";
    /**
     * 防洪闸
     */
    public static final String FH_FLOOD_BANK_MAP_URL = "http://120.198.125.231:20021/arcgis/rest/services/floodbank/MapServer";
    /**
     * 泵站
     */
    public static final String PUMP_MAP_URL = "http://120.198.125.231:20021/arcgis/rest/services/pump/MapServer";
    /**
     * 交通闸
     */
    public static final String TRAFFIC_SLUICE_MAP_URL = "http://120.198.125.231:20021/arcgis/rest/services/TrafficSluice/MapServer";
    /**
     * 管线
     */
    public static final String NNPS_FACILITY_MAP_URL = "http://39.108.76.192:6080/arcgis/rest/services/nnpsfacility/MapServer";

}
