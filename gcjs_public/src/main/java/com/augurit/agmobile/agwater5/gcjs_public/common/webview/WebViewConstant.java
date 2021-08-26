package com.augurit.agmobile.agwater5.gcjs_public.common.webview;

/**
 * Created by xcl on 2017/10/23.
 */

public class WebViewConstant {

    /**
     * {@link WebViewActivity}的标题
     */
    public static final String WEBVIEW_TITLE = "WEBVIEW_TITLE";
    public static final String WEBVIEW_URL_PATH = "WEBVIEW_URL_PATH";
    public static final String COLUMN_URL = "http://139.159.243.230:8099";

    public static class H5_URLS {

        private final static String HTML_PATH = "/DrainageFacilities/html/";
        /**
         * 安装率、签到率统计URL
         */
        public final static String MY_SIGN_IN_STATISTIC_URL = HTML_PATH + "Statistics.html";
        /**
         * 每日签到的Url
         */
        public final static String MY_SIGN_IN_URL = HTML_PATH + "EverydaySign.html";
        /**
         * 经验交流的Url
         */
        public final static String EXPERIMENT_SHARE_URL = COLUMN_URL + "/jyjl/index.jhtml";
        /**
         * 修改密码的Url
         */
        public final static String CHANGE_PASSWORD_URL = HTML_PATH + "ModifyPassword.html";

        /**
         * 政策法规的url
         */
        public final static String POLICY_REGULATIOS = COLUMN_URL + "/zcfg/index.jhtml";
        /**
         * 标准规范
         */
        public final static String STANDARD_SPECIFICATION = COLUMN_URL + "/bzgf/index.jhtml";

        /**
         * 新闻动态URL
         */
        public final static String RECENT_NEWS = COLUMN_URL + "/xwdt/index.jhtml";

        /**
         * 操作须知URL
         */
        public final static String OPERATE_KNOW = COLUMN_URL + "/czxz/index.jhtml";

        /**
         * 通知公告
         */
        public final static String NOTIFICATIONS = COLUMN_URL + "/tzgg/index.jhtml";
        /**
         * 红榜单
         */
        public final static String RED_BOARD = COLUMN_URL + "/redb/index.jhtml";
        /**
         * 黑榜单
         */
        public final static String BLACK_BOARD = COLUMN_URL + "/blackb/index.jhtml";
    }
}
