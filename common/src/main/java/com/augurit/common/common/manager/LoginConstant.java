package com.augurit.common.common.manager;

/**
 * 登录常量配置
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.login
 * @createTime 创建时间 ：2018/4/19
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/4/19
 * @modifyMemo 修改备注：
 */

public class LoginConstant {

    public static final String SERVER_URL = "http://192.168.31.60";
    //智慧水质SERVER_URL
    public static final String SERVER_QUALITY_URL = "http://121.37.1.89";

    public static final boolean SHOULD_IM_LOGIN = false;




    //正式环境(排水)
    public static String GZPS_AGWEB = "139.159.243.230:8081/agweb_ha";
    public static String GZPS_AGSUPPORT = "139.159.243.230:8080/agsupport_swj";//设施上报的URL

    //正式环境（污水）
    public static String GZWS_AGWEB = "139.159.232.250:8080/agweb_ha";
    public static String GZWS_AGSUPPORT = "139.159.232.250:8085/agsupport_swj";

    public static String GZPSH_AGSUPPORT = "139.159.243.185:8081/agsupport_swj";
    public static String UPLOAD_AGSUPPORT = "http://139.159.243.185";//设施上报的URL

    public static String pshUploadPort = ":8081";
    public static String pshAGWEBPort = ":8081";
    public static String GZPSH_AGWEB = "http://139.159.243.185:8081/agweb_ha";
    /**
     * 设施上报接口后缀
     */
    public static final String UPLOAD_POSTFIX = "/agsupport_swj";

    public static final String UPLOAD_AGWEB = "/agweb_ha";
    public static final int MAX_LENGTH_USERNAME = 16;    // 用户名可输入的最大长度(超过该长度给予提示)
    public static final int MAX_LENGTH_PASSWORD = 16;    // 密码可输入的最大长度(超过该长度给予提示)
    public static final boolean SAVE_USERNAME = true;    // 是否允许记住账号(关闭此选项仍会存到本地用以离线登录，只是不再显示登录过的账户)
    public static final boolean SAVE_PASSWORD = true;    // 记住密码选项是否开启(关闭此选项仍会存到本地用以离线登录)

    public static String SUPPORT_URL = GZPSH_AGSUPPORT;
    public static String LOGIN_URL = "/rest/system/getUser/";
    public static String USER_BY_ID_URL = "/rest/system/getUserByUserId/";
    public static String USER_BY_GROUPID = "/rest/system/getUsersByGroupId/";
    public static String GROUP_URL = "/rest/system/getGroupsByUserId/";
    public static String FRIEND_URL = "/rest/system/getContactsByUserId/";
    public static String COLUMNSUNREAD_URL = "/rest/columnsUnread/getUnreadInfo/";
    public static String CHECK_USERNAME_URL = "/rest/system/checkUserName/";
    public static String MODIFY_PASSWORD_URL = "/rest/system/updatePasswordByLoginName/";


    public static Boolean isCheckOut=true;//排水户-广州排水用户基本信息表是否校验，false:不检验。true:校验

    // 登录界面
    public final static String LOGIN_USERNAME_KEY = "loginName";        // 用户名（对应值:String）
    public final static String LOGIN_PASSWORD_KEY = "password";         // 密码（对应值:String）
    public final static String SAVE_USERNAME_STATE = "save_username";   // 保存用户名是否勾选（对应值:Boolean）
    public final static String SAVE_PASSWORD_STATE = "save_password";   // 保存密码是否勾选（对应值:Boolean）
    public final static String PRE_NAME_TAG = "AG_USER_NAME_";          // 本地保存用户名前缀（对应值:String）
    public final static String SHOW_PRE_NAME_TAG = "AG_USER_NAME_SHOW_"; // 这个用户是否显示在用户名输入框的自动提示列表中（对应值:Boolean）
    public final static String USERNAME_COUNT = "USRE_NAME_COUNT";      // 本地保存的用户数量（对应值:int）
    public final static String LAST_LOGIN_USER = "LAST_LOGIN_USER";     // 上次登录的用户本地ID（对应值:int）
    public final static String LAST_ONLINE_LOGIN_USER = "LAST_ONLINE_LOGIN_USER";     // 上次在线登录的用户本地ID（对应值:int）
    public final static String DEVICE_ID = "DEVICE_ID";                 // 设备ID（对应值：String）
    // 设置界面
    public final static String LOGIN_SERVER_URL_KEY = "login_server_url";       // serverurl（对应值:String）
    public final static String LOGIN_SUPPORT_URL_KEY = "login_support_url";     // supporturl（对应值:String）
    public final static String UPDATE_PROJECT_STATE = "update_project_state";   // 是否更新专题（对应值:Boolean）



}
