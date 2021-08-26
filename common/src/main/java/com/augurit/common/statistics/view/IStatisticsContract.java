package com.augurit.common.statistics.view;

import android.content.Context;

import com.augurit.common.statistics.model.InstallDetailInfo;
import com.augurit.common.statistics.model.InstallInfo;
import com.augurit.common.statistics.model.ReportStatisticInfo;
import com.augurit.common.statistics.model.SignStatisticInfo;
import com.augurit.common.statistics.model.TwoDayReportInfo;

import java.util.List;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.statistics.view
 * @createTime 创建时间 ：2018-09-05
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public interface IStatisticsContract {
    interface View {

        void showAreaInfo(List<String> areaList);

        void showLoading();

        void hideLoading();

        void showLoadError(Exception e);

        default void showFacilityType(List<String> facilityList) {
        }

        default void showInstallData(InstallInfo.InstallData installData) {
        }

        default void showSignData(List<SignStatisticInfo> signListData) {
        }
        default void showReportData(ReportStatisticInfo reportStatisticInfo){}

        default void showTwoDayReportData(TwoDayReportInfo twoDayReportInfo){}

        default void showInstallDetailData(List<InstallDetailInfo.InstallUser> installUser){}

        Context getContext();
    }

    interface Presenter {

        void loadAreaInfo(boolean roleType);

        void loadInstallInfo(String area, boolean roleType);

        void loadSignInfo(String area);

        void loadFacilityTypeInfo();

        void loadReportInfo(String areaName, String facilityName, long startTime, long endTime);

        void loadTwoDayReportInfo(String facility);

        void loadInstallDetailInfo(String url);

        void destroyDisposable();
    }
}
