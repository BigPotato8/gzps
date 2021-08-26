package com.augurit.agmobile.agwater5.gcjs_public.mine.sign.view;


import com.augurit.agmobile.agwater5.gcjs_public.mine.sign.model.SignBean;
import com.augurit.agmobile.agwater5.gcjs_public.mine.sign.model.SignResultBean;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.mine.sign.view
 * @createTime 创建时间 ：2018-09-10
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public interface ISignContract {

    interface View {

        void showSignInfo(SignBean signBean);

        void showSignResult(SignResultBean signResult);
    }

    interface Presenter {

        void getSignInfo(String userId, String yearMonth);

        void doSign(double x, double y, String street, String address, String appVersion);

        void destroyDisposable();
    }
}
