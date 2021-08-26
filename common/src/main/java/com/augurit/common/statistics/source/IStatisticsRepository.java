package com.augurit.common.statistics.source;


import com.augurit.common.statistics.model.InstallDetailInfo;
import com.augurit.common.statistics.model.InstallInfo;
import com.augurit.common.statistics.model.ReportStatisticInfo;
import com.augurit.common.statistics.model.SignStatisticInfo;
import com.augurit.common.statistics.model.TwoDayReportInfo;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.statistics.source
 * @createTime 创建时间 ：2018-09-05
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public interface IStatisticsRepository {

    /**
     * 获取统计区域信息
     *
     * @return
     */
    Observable<List<String>> getAreaInfoFromDict(boolean roleType);

    /**
     * 获取安装率信息
     *
     * @param areaName 区域名称
     * @param roleType 用户角色类型
     * @return
     */
    Observable<InstallInfo.InstallData> getInstallInfo(String areaName, boolean roleType);

    /**
     * 获取信息
     *
     * @param areaName
     * @return
     */
    Observable<List<SignStatisticInfo>> getSignInfo(String areaName);

    /**
     * 从数据字典获取问题上报统计的设施类型
     *
     * @return
     */
    Observable<List<String>> getFacilityTypeFromDict();

    /**
     * 问题上报数据
     *
     * @param areaName
     * @param facilityName
     * @param startTime
     * @param endTime
     * @return
     */
    Observable<ReportStatisticInfo> getReportStatisticsInfo(String areaName, String facilityName, long startTime, long endTime);

    /**
     * 获取昨天和今天的上报数据
     *
     * @param facilityName
     * @return
     */
    Observable<TwoDayReportInfo> getTwoDayReportInfo(String facilityName);

    /**
     * 获取安装人员详细信息
     *
     * @param url
     * @return
     */
    Observable<List<InstallDetailInfo.InstallUser>> getInstallDetailInfo(String url);
}
