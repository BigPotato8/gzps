package com.augurit.common.statistics.view;

import com.augurit.agmobile.common.lib.log.LogUtil;
import com.augurit.common.statistics.model.InstallDetailInfo;
import com.augurit.common.statistics.model.ReportStatisticInfo;
import com.augurit.common.statistics.model.SignStatisticInfo;
import com.augurit.common.statistics.model.TwoDayReportInfo;
import com.augurit.common.statistics.source.StatisticsRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.statistics.view
 * @createTime 创建时间 ：2018-09-05
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class StatisticsPresenter implements IStatisticsContract.Presenter {
    private IStatisticsContract.View mView;
    private final StatisticsRepository mRepository;
    private CompositeDisposable compositeDisposable;

    public StatisticsPresenter(IStatisticsContract.View view) {
        mView = view;
        compositeDisposable = new CompositeDisposable();
        mRepository = new StatisticsRepository(mView.getContext());
    }

    @Override
    public void loadAreaInfo(boolean roleType) {
        Disposable disposable = mRepository.getAreaInfoFromDict(roleType).subscribe(mView::showAreaInfo);
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadInstallInfo(String area, boolean roleType) {
        mView.showLoading();
        Disposable disposable = mRepository.getInstallInfo(area, roleType)
                .subscribe(installData -> {
                    mView.hideLoading();
                    mView.showInstallData(installData);
                }, throwable -> {
                    mView.hideLoading();
                    throwable.printStackTrace();
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadSignInfo(String area) {
        mView.showLoading();
        Disposable disposable = mRepository.getSignInfo(area).subscribe(new Consumer<List<SignStatisticInfo>>() {
            @Override
            public void accept(List<SignStatisticInfo> signListData) throws Exception {
                mView.hideLoading();
                mView.showSignData(signListData);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtil.e(getClass().getSimpleName(), "loadSignInfo:"+throwable.getMessage());
            }
        });
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadFacilityTypeInfo() {
        Disposable disposable = mRepository.getFacilityTypeFromDict().subscribe(mView::showFacilityType);
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadReportInfo(String areaName, String facilityName, long startTime, long endTime) {
        mView.showLoading();
        Disposable disposable = mRepository.getReportStatisticsInfo(areaName, facilityName, startTime, endTime)
                .subscribe(new Consumer<ReportStatisticInfo>() {
                    @Override
                    public void accept(ReportStatisticInfo reportStatisticInfo) throws Exception {
                        mView.hideLoading();
                        mView.showReportData(reportStatisticInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.e(getClass().getSimpleName(), "loadReportInfo:"+throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadTwoDayReportInfo(String facilityName) {
        Disposable disposable = mRepository.getTwoDayReportInfo(facilityName)
                .subscribe(new Consumer<TwoDayReportInfo>() {
                    @Override
                    public void accept(TwoDayReportInfo twoDayReportInfo) throws Exception {
                        mView.showTwoDayReportData(twoDayReportInfo);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadInstallDetailInfo(String url) {
        mView.showLoading();
        Disposable disposable = mRepository.getInstallDetailInfo(url)
                .subscribe(new Consumer<List<InstallDetailInfo.InstallUser>>() {
                    @Override
                    public void accept(List<InstallDetailInfo.InstallUser> installUser) throws Exception {
                        mView.hideLoading();
                        mView.showInstallDetailData(installUser);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtil.e(getClass().getSimpleName(), "loadInstallDetailInfo:"+throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void destroyDisposable() {
        if(compositeDisposable!=null){
            compositeDisposable.clear();
            compositeDisposable=null;
        }
    }
}
