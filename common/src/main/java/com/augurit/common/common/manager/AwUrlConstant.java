package com.augurit.common.common.manager;

/**
 * @AUTHOR 创建人:yaowang
 * @VERSION 版本:1.0
 * @PACKAGE 包名:com.augurit.agmobile.agwater5.common.common
 * @CREATETIME 创建时间:2018/8/29
 * @MODIFYTIME 修改时间:
 * @MODIFYBY 修改人:
 * @MODIFYMEMO 修改备注:
 */
public class AwUrlConstant {
    /**
     * 登录认证服务端口
     */
    public static final String PORT_AUTHENTICATION = ":8890/";
    /**
     * OPUS REST 应用端口
     */
    public static final String PORT_OPUS_REST = ":7080/";
    /**
     * sso 获取用户信息
     * 请求头 Authorization 格式 "${tokenType} ${accessToken}" 例如：bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcHVzTG9naW5Vc2VyIjp7InVzZXIiOnsidXNlcklkIjoiMzY5IiwidXNlck5hbWUiOiLns7vnu5_nrqHnkIblkZgiLCJsb2dpbk5hbWUiOiJhZG1pbiIsImxvZ2luUHdkIjpudWxsfSwib3JncyI6bnVsbCwiY3VycmVudE9yZ0lkIjoiIiwiY3VycmVudFRtbiI6Im5vcm1hbCIsImFwcFNvZnRDb250ZXh0cyI6W10sInRtbk1lbnVDb250ZXh0cyI6W119LCJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFsbCJdLCJleHAiOjE1MzE0MDMyNjMsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJjMTQzOGM5MS0xN2ZhLTQ2NDQtODU0Yy0wMjQxYTQ1YzgwNzkiLCJjbGllbnRfaWQiOiJvcHVzLXJlc3QifQ.5rpzy0_SoaWoKn9Llsmf3LZyM4qGCZ2UZpGjlAkMHng
     * 参数 userName
     */
    public static final String SSO_GET_USER_INFO = "opus-admin/rest/opus/om/getOpuOmUserInfoByUserId.do";
    /**
     * sso 获取用户组织
     * 无需请求头
     * 参数 username
     * 参数 password
     */
    public static final String SSO_GET_USER_ORG = "opus-admin/rest/opus/om/listOpuOmUserOrg.do";
    /**
     * 获取用户角色
     * 参数 username
     * 参数 password
     */
    public static final String GET_USER_ROLES = "omOrgRest/getUserRoleByUserId";
    /**
     * sso 获取用户组织
     * 无需请求头
     * 参数 username
     * 参数 password
     */
    public static final String SSO_GET_USER_ORG_BEFORE = "opus-front-sso/authentication/org/listOpuOmUserOrg";

    /**
     * 登录认证服务端口
     */
    //public static final String AW_PORT_AUTHENTICATION = ":8080/psxj/";
    public static final String AW_PORT_AUTHENTICATION = ":8090/";

    /**
     * OPUS REST 应用端口
     */
    public static final String AW_PORT_OPUS_REST = ":8883/";


    private static final String SERVICE_ROOT_URL = "rest/";
    /**
     * 登录 用户信息 组织列表
     */
    //public static final String AW_SSO_GET_AUTHENTICATION = SERVICE_ROOT_URL + "app/login/";
    public static final String AW_SSO_GET_AUTHENTICATION = "opus-front-sso/authentication/form";

    /**
     * 获取所有数据字典类型
     * 参数 typeKeyword
     */
    public static final String AW_GET_ALL_DICTS = SERVICE_ROOT_URL + "app/listTypes";



    /**
     * 获取数据字典子项
     * 参数 typeCode orgId
     */
    public static final String AW_GET_DICTITEM_BY_TYPECODE = SERVICE_ROOT_URL + "app/lgetItemsByTypeCode";

    /**
     * 获取数据字典树形结构
     * 参数 typeCode orgId
     */
    public static final String AW_GET_DICTTREEITEM_BY_TYPECODE = SERVICE_ROOT_URL + "app/getItemTreeByTypeCode";

    /**
     * 应急调度登录接口
     */
    public static final String AW_POST_FLOOD_LOGIN_URL = "opus-front-sso/authentication/form";

    public static final String AW_PORT_CP_REST_TEST = ":7050";

    /**
     * 获取组织架构层级详情
     */
    public static final String AW_GET_ORGANIZATION_TREE = "cp-rest/rest/opus/om/listOpuOmSubOrgPosUserByParentId.do";

    /**
     * 获取用户信息
     */
    public static final String AW_GET_USER_INFO = "cp-rest/rest/opus/om/getOpuOmUserInfoByloginName.do";

    /**
     * 获取用户组织
     */
    public static final String AW_GET_USER_ORGANIZATIONS = "cp-rest/rest/opus/om/listUserTopOrg.do";
}
