package com.augurit.common.statistics.source;

import android.content.Context;

import com.augurit.agmobile.busi.bpm.dict.IDictRepository;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.dict.model.DictTreeItem;
import com.augurit.agmobile.common.lib.json.JsonUtil;
import com.augurit.common.dict.AwDictRepository;
import com.augurit.common.statistics.model.InstallDetailInfo;
import com.augurit.common.statistics.model.InstallInfo;
import com.augurit.common.statistics.model.ReportStatisticInfo;
import com.augurit.common.statistics.model.SignStatisticInfo;
import com.augurit.common.statistics.model.TwoDayReportInfo;
import com.google.gson.reflect.TypeToken;
import com.zhouyou.http.EasyHttp;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.statistics.source
 * @createTime 创建时间 ：2018-09-05
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class StatisticsRepository implements IStatisticsRepository {

    private final IDictRepository mDictRepository;
    private Context mContext;

    public StatisticsRepository(Context context) {
        mContext = context;
        mDictRepository = new AwDictRepository();
    }

    @Override
    public Observable<List<String>> getAreaInfoFromDict(boolean roleType) {
        return Observable.just(getAreaList(roleType));
    }

    @Override
    public Observable<List<String>> getFacilityTypeFromDict() {
        return Observable.just(getFacilityList());
    }

    /**
     * 问题上报统计
     *
     * @param areaName
     * @param facilityName
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Observable<ReportStatisticInfo> getReportStatisticsInfo(String areaName, String facilityName, long startTime, long endTime) {

        return EasyHttp.get("rest/app/parts/toPartsCount")
                .params("parentOrgName", areaName)
                .params("reportType", facilityName)
                .params("startTime", startTime + "")
                .params("endTime", endTime + "")
                .execute(String.class)
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String>>() {
                    @Override
                    public ObservableSource<? extends String> apply(Throwable throwable) throws Exception {
                        if (throwable.getCause() instanceof HttpException) {
                            HttpException exception = (HttpException) throwable.getCause();
                            return Observable.just(exception.response().errorBody().string());
                        }
                        return Observable.just("");
                    }
                }).map(new Function<String, ReportStatisticInfo>() {
                    @Override
                    public ReportStatisticInfo apply(String s) throws Exception {
                        return JsonUtil.getObject(s, ReportStatisticInfo.class);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取昨天和今天的上报数据
     *
     * @param facilityName
     * @return
     */
    @Override
    public Observable<TwoDayReportInfo> getTwoDayReportInfo(String facilityName) {
        return EasyHttp.get("rest/app/parts/toPastNowDay")
                .params("layerName", facilityName)
                .execute(String.class)
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String>>() {
                    @Override
                    public ObservableSource<? extends String> apply(Throwable throwable) throws Exception {
                        if (throwable.getCause() instanceof HttpException) {
                            HttpException exception = (HttpException) throwable.getCause();
                            return Observable.just(exception.response().errorBody().string());
                        }
                        return Observable.just("");
                    }
                })
                .map(new Function<String, TwoDayReportInfo>() {
                    @Override
                    public TwoDayReportInfo apply(String s) throws Exception {
                        return JsonUtil.getObject(s, TwoDayReportInfo.class);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 安装人员详细信息
     *
     * @param url
     * @return
     */
    @Override
    public Observable<List<InstallDetailInfo.InstallUser>> getInstallDetailInfo(String url) {
        return EasyHttp.get(url)
                .execute(String.class)
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String>>() {
                    @Override
                    public ObservableSource<? extends String> apply(Throwable throwable) throws Exception {
                        if (throwable.getCause() instanceof HttpException) {
                            HttpException exception = (HttpException) throwable.getCause();
                            return Observable.just(exception.response().errorBody().string());
                        }
                        return Observable.just("");
                    }
                })
                .map(new Function<String, List<InstallDetailInfo.InstallUser>>() {
                    @Override
                    public List<InstallDetailInfo.InstallUser> apply(String s) throws Exception {
                        InstallDetailInfo installDetailInfo = JsonUtil.getObject(s, InstallDetailInfo.class);
                        if (installDetailInfo.getData().getUsers() == null) {
                            return new ArrayList<>();
                        }
                        return installDetailInfo.getData().getUsers();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 安装率统计
     *
     * @param areaName 区域名称
     * @param roleType 用户角色类型
     * @return
     */
    @Override
    public Observable<InstallInfo.InstallData> getInstallInfo(String areaName, boolean roleType) {
        return EasyHttp.get("rest/app/installRecord/StatisticalApp")
                .params("org_name", areaName)
                .params("roleType", roleType + "")
                .execute(String.class)
                .onErrorResumeNext(throwable -> {
                    if (throwable.getCause() instanceof HttpException) {
                        HttpException exception = (HttpException) throwable.getCause();
                        return Observable.just(exception.response().errorBody().string());
                    }
                    return Observable.just("");
                })
                .map(new Function<String, InstallInfo.InstallData>() {
                    @Override
                    public InstallInfo.InstallData apply(String result) throws Exception {
                        return JsonUtil.getObject(result, InstallInfo.class).getData();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 签到统计
     *
     * @param areaName
     * @return
     */
    @Override
    public Observable<List<SignStatisticInfo>> getSignInfo(String areaName) {
        return EasyHttp.get("rest/app/dailySign/getStatisticsInfo")
                .params("orgName", areaName)
                .execute(String.class)
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String>>() {
                    @Override
                    public ObservableSource<? extends String> apply(Throwable throwable) throws Exception {
                        if (throwable.getCause() instanceof HttpException) {
                            HttpException exception = (HttpException) throwable.getCause();
                            return Observable.just(exception.response().errorBody().string());
                        }
                        return Observable.just("");
                    }
                })
                .map(s -> {
                    List<SignStatisticInfo> dataList = JsonUtil.getObject(s, new TypeToken<List<SignStatisticInfo>>() {
                    }.getType());
                    return dataList;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 从数据字典获取区域信息
     *
     * @param roleType
     * @return
     */
    private List<String> getAreaList(boolean roleType) {
        List<DictItem> dicItemList;
        ArrayList<String> areaList = new ArrayList<>();
        if (roleType) {
            dicItemList = mDictRepository.getDictItemByParentTypeCode("dri_lead_statistics_area");
        } else {
            dicItemList = mDictRepository.getDictItemByParentTypeCode("dri_worker_statistics_area");
        }
        for (DictItem dictItem : dicItemList) {
            areaList.add(dictItem.getLabel());
        }
        return areaList;
    }

    /**
     * 从数据字典获取设施类型
     *
     * @return
     */
    private List<String> getFacilityList() {
        ArrayList<String> facilityTypeList = new ArrayList<>();
        List<DictTreeItem> dicItemList = mDictRepository.getDictTreeItemByParentTypeCode("dri-problem");
        for (DictTreeItem dictItem : dicItemList) {
            facilityTypeList.add(dictItem.getItemName());
        }
        return facilityTypeList;
    }
}
