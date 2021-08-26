package com.augurit.common.common.manager;

/**
 * AgWater Url管理
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.common.common
 * @createTime 创建时间 ：2018/8/29
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class AwUrlManager {
    /**
     * 基础ip
     */
    public static String BASE_URL = "http://192.168.31.60";
    /**
     * 基础端口
     */
    public static String BASE_PROT = "8093";
    /**
     * 排水巡检通用baseurl
     */
    private static String BASE_URL_PSXJ = "";
//    private static final String TEST_URL = "http://192.168.30.6:8092/";
//    private static final String TEST_URL = "http://192.168.31.252:8092/";
    private static final String TEST_URL = "http://121.37.1.89:8092/";
    /**
     * 应急调度通用baseurl
     */
    private static String BASE_URL_PSEMGY = "";

    private static String BASE_URL_PSXJ2 = "";
    private static String BASE_URL_OTHER = "http://zhsz.gzcc.gov.cn:8080";
    private static String BASE_URL_FLOOD = "http://192.168.31.60:8093";

    public static void setServerUrl(String serverUrl) {
//        BASE_URL_PSXJ = "http://192.168.30.151".concat(":8089/psxj/");
        BASE_URL_PSXJ = "http://192.168.31.60".concat(":8093/psxj/");
//        BASE_URL_PSXJ = TEST_URL;
        BASE_URL_PSXJ2 = "http://192.168.31.60".concat(":8093/psxj/");
        BASE_URL_PSEMGY = "http://192.168.31.136".concat(":8083/psemgy/");
    }

    /**
     * 服务地址   用于图片访问的ip
     *
     * @return 服务地址
     */
    public static String serverIP() {
//        return "http://192.168.30.147:8093";
        return "http://192.168.31.60:8093";
    }
    /**
     * ps设施服务地址
     *
     * @return 服务地址
     */
    public static String serverFacility() {
//        return "http://192.168.30.147:8093/";
        return "http://192.168.31.60:8093/";

    }

    /**
     * 服务地址
     *
     * @return 服务地址
     */
    public static String serverUrl() {
        return BASE_URL_PSXJ;

    }
    /**
     * 服务地址
     *
     * @return 服务地址
     */
    public static String qualityServerUrl() {
        return TEST_URL;

    }

    /**
     * 服务地址
     *
     * @return 服务地址
     */
    public static String serverUrl2() {
        return BASE_URL_PSXJ2;

    }

    public static String serverOtherUrl() {
        return BASE_URL_OTHER;
    }

    /**
     * 服务地址
     *
     * @return 服务地址
     */
    public static String serverMockUrl() {
        return " https://www.easy-mock.com/mock/5c10a7ef8c59f04d2e3a76bd/agwater/";
    }

    /**
     * 海口 服务地址
     * 用于 12345，数字城管
     *
     * @return 服务地址
     */
    public static String serverHKUrl() {
        return "http://www.hkszwx.com:8888";
    }


    public static String serverUrlPsemgy() {
        return BASE_URL_PSEMGY;
    }

    /**
     * 仅用于测试目前没有的接口，之后会删除，非测试情况使用{@link #serverUrl()}
     */
    public static String testAgSupportUrl() {
//        return "http://139.159.243.185:8080/agsupport_swj/";
        return "http://139.159.243.185:8081/agsupport_swj/";
//        return "http://139.159.243.230:9090/agsupport_swj/";
//        return "http://192.168.30.62:8080/psxj/";
    }

    public static String pipelineMaintenanceUrl(){
//        return "http://192.168.30.147:8093/maintenance/";
        return "http://192.168.30.151:8089/maintenance/";
//        return "http://192.168.31.60:8093/maintenance/";
    }

    /**
     * 仅用于模拟的接口，
     */
    public static String easyMockUrl() {
        return "https://www.easy-mock.com/mock/5c10a7ef8c59f04d2e3a76bd/agwater/";
    }

    public static String getBaseUrlFlood() {
        return BASE_URL_FLOOD;
    }

}