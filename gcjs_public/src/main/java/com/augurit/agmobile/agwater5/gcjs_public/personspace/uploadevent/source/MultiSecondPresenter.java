package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.source;

import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.EventItemBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.MultiSituationBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.StageBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.ThemeAndMultiBean;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @description 描述一下作用
 * @date: $date$ $time$
 * @author: xieruibin
 */
public class MultiSecondPresenter implements MultiSecondConstant.Presenter{
    private MultiSecondConstant.View mView;
    private ReportRepository mRepository;
    private CompositeDisposable mCompositeDisposable;
    public MultiSecondPresenter(MultiSecondConstant.View view){
        this.mView = view;
        mRepository = new ReportRepository();
        mCompositeDisposable = new CompositeDisposable();
    }
    @Override
    public void getThemeAndOrg() {
        Disposable disposable = mRepository.getThemeAndOrg()
                .subscribe(themeAndMultiBeanApiResult -> {
                    if (themeAndMultiBeanApiResult.isSuccess()) {
                        List<ThemeAndMultiBean.ThemesBean> themes = themeAndMultiBeanApiResult.getData().getThemes();
                        mView.setTheme(themes);
                    }else{
                        mView.setTheme(null);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    mView.setTheme(null);
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void getMultiStage(String themeId) {
        Disposable disposable = mRepository.getMultiState(themeId)
                .subscribe(listApiResult -> {
                    if (listApiResult.isSuccess()) {
                        List<StageBean> data = listApiResult.getData();
                        mView.setMultiStage(data, themeId);
                    }else{
                        mView.setMultiStage(null, themeId);
                    }
                }, throwable -> {
                    throwable.printStackTrace();

                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void getMultiSituation(String parentId, String stageId,String elementCode) {
        Disposable disposable = mRepository.getMultiSituation(parentId, stageId)
                .subscribe(listApiResult -> {
                    if (listApiResult.isSuccess()) {
                        List<MultiSituationBean> data = listApiResult.getData();
                        mView.setMultiSituation(data,parentId,elementCode);
                    }else{
                        mView.setMultiSituation(null,parentId,elementCode);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    mView.setMultiSituation(null,parentId,elementCode);
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void getMultiEventList(String themeId, String stageId) {
        Disposable disposable = mRepository.getMultiEventList(themeId, stageId)
                .subscribe(listApiResult -> {
                    if (listApiResult.isSuccess()) {
                        List<EventItemBean> data = listApiResult.getData();
                        mView.setMultiEventList(data);
                    }else{
                        mView.setMultiEventList(null);
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    mView.setMultiEventList(null);
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void stopRequest() {
        if (mCompositeDisposable!=null) {
            mCompositeDisposable.clear();
        }
    }


}
