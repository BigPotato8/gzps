package com.augurit.agmobile.agwater5.gcjs_public;

/**
 * @description 描述一下作用
 * @date: $date$ $time$
 * @author: xieruibin
 */
public class GcjsPublicPresenter implements GcjsPublicContract.Presenter {
    private GcjsPublicContract.View mView;
    private GcjsPublicRepository mRepository;

    public GcjsPublicPresenter(GcjsPublicContract.View mView) {
        mRepository = new GcjsPublicRepository();
        this.mView = mView;
    }

    @Override
    public void loadApproveNews() {
        mRepository.getApproveInfo()
                .subscribe(listApiResult -> {
                            mView.showPatrolDynamicNews(listApiResult.getData());
                        }, throwable -> {
                            throwable.printStackTrace();
                            mView.showPatrolDynamicNews(null);
                        }
                );
    }

    @Override
    public void loadPshPatrolDynamicNews() {

    }

    @Override
    public void loadPatrolEventNums() {

    }

    @Override
    public void destroyDisposable() {

    }
}
