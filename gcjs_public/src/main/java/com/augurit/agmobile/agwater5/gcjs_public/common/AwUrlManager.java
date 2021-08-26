package com.augurit.agmobile.agwater5.gcjs_public.common;

/**
 * AgWater Url管理
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.common.common
 * @createTime 创建时间 ：2018/8/29
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class AwUrlManager {

    private static String BASE_URL_PSXJ = "";
    private static String BASE_URL_PSEMGY = "";
    private static String BASE_URL_OTHER ="http://zhsz.gzcc.gov.cn:8080";

    public static void setServerUrl(String serverUrl) {
//        BASE_URL_PSXJ = serverUrl.concat(":8490/");
//        BASE_URL_PSXJ = serverUrl.concat(":7050/");
        BASE_URL_PSXJ = serverUrl.concat(":7071/");
        BASE_URL_PSEMGY = "http://192.168.31.136".concat(":8083/psemgy/");
    }

    /**
     * 服务地址
     * @return 服务地址
     */
    public static String serverUrl() {
        return BASE_URL_PSXJ;

    }


    public static String serverOtherUrl(){
        return BASE_URL_OTHER;
    }

    /**
     * 服务地址
     * @return 服务地址
     */
    public static String serverMockUrl() {
        return " https://www.easy-mock.com/mock/5c10a7ef8c59f04d2e3a76bd/agwater/";
    }

    /**
     *海口 服务地址
     * 用于 12345，数字城管
     * @return 服务地址
     */
    public static String serverHKUrl() {
        return "http://www.hkszwx.com:8888";
    }


    public static String serverUrlPsemgy(){
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

    /**
     * 仅用于模拟的接口，
     */
    public static String easyMockUrl() {
        return "https://www.easy-mock.com/mock/5c10a7ef8c59f04d2e3a76bd/agwater/";
    }

}
