package com.augurit.agmobile.agwater5.common.common;

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
    public static final String AW_PORT_AUTHENTICATION = ":8090/"; //测试
//    public static final String AW_PORT_AUTHENTICATION = ":8084/";//正式

    /**
     * OPUS REST 应用端口
     */
    public static final String AW_PORT_OPUS_REST = ":8883/";

    /**
     * 工程建设aplanmis-rest端口
     */
    public static final String AW_PORT_CP_REST = ":8086/";

//    public static final String AW_PORT_CP_REST_TEST = ":7050";
    public static final String AW_PORT_CP_REST_TEST = ":18081";
//    public static final String AW_PORT_CP_REST_TEST = "";
//    public static final String AW_PORT_CP_REST_TEST = ":8490";

    /**
     * 登录 用户信息 组织列表
     */
    public static final String AW_SSO_GET_AUTHENTICATION = "opus-front-sso/authentication/form";
    /**
     * 登录 验证码登录
     */
    public static final String VERCODE_AW_SSO_GET_AUTHENTICATION = "opus-front-sso/authentication/mobile";
//    /**
//     * 登录 用户信息 组织列表
//     */
//    public static final String AW_SSO_GET_AUTHENTICATION = "rest/app/login/";

    /**
     * 获取用户信息
     */
    public static final String AW_GET_USER_INFO = "aplanmis-rest/rest/opus/om/getOpuOmUserInfoByloginName.do";

    /**
     * 获取用户组织
     */
    public static final String AW_GET_USER_ORGANIZATIONS = "aplanmis-rest/rest/opus/om/listUserTopOrg.do";
    public static final String GET_USER_ORGANIZATIONS="aplanmis-rest/rest/opus/om/listCurrentUserTopOrg.do";

    /**
     * 获取组织架构层级详情
     */
    public static final String AW_GET_ORGANIZATION_TREE = "aplanmis-rest/rest/opus/om/listOpuOmSubOrgPosUserByParentId.do";

    /**
     * 获取所有数据字典类型
     * 参数 typeKeyword
     */
    public static final String AW_GET_ALL_DICTS = "rest/app/listTypes";


    /**
     * 获取数据字典子项
     * 参数 typeCode orgId
     */
    public static final String AW_GET_DICTITEM_BY_TYPECODE = "rest/app/lgetItemsByTypeCode";

    /**
     * 获取数据字典树形结构
     * 参数 typeCode orgId
     */
    public static final String AW_GET_DICTTREEITEM_BY_TYPECODE = "rest/app/getItemTreeByTypeCode";

    /**
     * 图形验证码
     */
    public static final String LOGIN_CODE = "opus-front-sso/code/image";
    /**
     * 短信验证码
     */
    public static final String VER_CODE = "opus-front-sso/code/sms";
}
