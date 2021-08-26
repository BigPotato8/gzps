package com.augurit.agmobile.agwater5.gcjs.eventlist.source;


import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventAffair;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventDetail;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventProcess;
import com.augurit.agmobile.common.lib.net.model.ApiResult;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import io.reactivex.Observable;


/**
 * 排水巡检数据仓库
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.drainage.source
 * @createTime 创建时间 ：2018/8/30
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public interface IEventRepository {
    /**
     * 删掉当前
     *
     * @param sjid
     * @param procInstId
     * @param taskInstDbid
     * @return
     */
//    @GET("rest/asiWorkflow/deleteProcessInstance")
    Observable<ApiResult> wfDelete(String sjid, String procInstId, String taskInstDbid);


    /**
     * 获取事件处理情况及施工日志，参数sjid要经过加密处理，加密接口见GzpsService的AESEncoode
     *
     * @return
     */
//    @GET("rest/asiWorkflow/getTraceRecordAndSggcLogList")
    Observable<ApiResult<List<EventProcess>>> getEventHandlesAndJournals(String sjid, int pageNo, int pageSize);


    /**
     * 领取事件
     *
     * @return
     */
//    @GET("rest/asiWorkflow/takeTasks")
    Observable<ApiResult<String>> signEvent(String loginName, String userName, String taskInstId);


    /**
     * 获取事件详情
     *
     * @return
     */
//    @POST("rest/asiWorkflow/getDetail")
    Observable<ApiResult<EventDetail>> getEventDetails(String masterEntityKey, String taskInstId, String procInstDbId, String isRetrView);

    /**
     * 获取上报事件列表
     *
     * @param pageNo    页数
     * @param pageSize  每页数量
     * @param keyword   关键字（上报人、地址）
     * @param xzqh      行政区划（市级人员才有）
     * @param sslx      设施类型，传数据字典编码
     * @param wtlx      问题类型，传数据字典编码
     * @param state     事件状态：是否已办结（0查处理中，1查已办结，不传则查全部）
     * @param startTime 时间戳
     * @param endTime   时间戳
     * @return
     */
    Observable<ApiResult<EventAffair>> searchEvent(int pageNo,
                                                   int pageSize,
                                                   @Nullable String keyword,
                                                   @Nullable String xzqh,
                                                   @Nullable String sslx,
                                                   @Nullable String wtlx,
                                                   @Nullable String state,
                                                   @Nullable String startTime,
                                                   @Nullable String endTime);

    /**
     * 获取上报事件列表
     *
     * @param pageNo   页数
     * @param pageSize 每页数量
     * @param params   参数
     * @return
     */
    Observable<ApiResult<EventAffair>> searchEvent(int pageNo,
                                                   int pageSize,
                                                   Map<String, String> params);

    /**
     * 分享微信AES加固
     */
    Observable<ApiResult<String>> AESEncode(String content);
}
