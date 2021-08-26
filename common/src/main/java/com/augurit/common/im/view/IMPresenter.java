package com.augurit.common.im.view;


import com.augurit.common.im.source.IMRepositoryImpl;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.im.view
 * @createTime 创建时间 ：2018-09-06
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class IMPresenter implements IMContract.Presenter {
    private IMContract.View mView;
    private final IMRepositoryImpl mRepository;
    protected CompositeDisposable compositeDisposable;

    public IMPresenter(IMContract.View view) {
        mView = view;
        mRepository = new IMRepositoryImpl(mView.getContext());
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadFriends(String userId) {
        Disposable disposable = mRepository.getFriendList(userId)
                .subscribe(friendItems -> mView.showFriendList(friendItems),
                        Throwable::printStackTrace);
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadGroups(String userId) {
        Disposable disposable = mRepository.getGroupList(userId)
                .subscribe(groupItems -> mView.showGroupList(groupItems),
                        Throwable::printStackTrace);
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
