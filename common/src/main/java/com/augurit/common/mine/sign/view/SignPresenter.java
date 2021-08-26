package com.augurit.common.mine.sign.view;

import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.common.mine.sign.source.SignRepository;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.mine.sign.view
 * @createTime 创建时间 ：2018-09-10
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class SignPresenter implements ISignContract.Presenter {
    private ISignContract.View mView;
    private final SignRepository mRepository;
    private CompositeDisposable compositeDisposable;

    public SignPresenter(ISignContract.View view) {
        compositeDisposable=new CompositeDisposable();
        mView = view;
        mRepository = new SignRepository();
    }

    @Override
    public void getSignInfo(String userId, String yearMonth) {
        Disposable disposable = mRepository.getSignInfo(userId, yearMonth).subscribe(signBean -> mView.showSignInfo(signBean));
        compositeDisposable.add(disposable);
    }

    @Override
    public void doSign(double x, double y, String street, String address, String appVersion) {
        User user = LoginManager.getInstance().getCurrentUser();
        String userId = user.getUserId();
        String userName = user.getUserName();
        String orgName = user.getOrgName();
        String orgCode = user.getCurrentOrg().getOrgCode();
        Disposable disposable = mRepository.getSignResult(userId, userName, orgName, orgCode, x, y, street, address, appVersion)
                .subscribe(signResultBean -> mView.showSignResult(signResultBean));
        compositeDisposable.add(disposable);

    }

    @Override
    public void destroyDisposable() {
        if (compositeDisposable!=null){
            compositeDisposable.clear();
            compositeDisposable=null;
        }

    }


}
