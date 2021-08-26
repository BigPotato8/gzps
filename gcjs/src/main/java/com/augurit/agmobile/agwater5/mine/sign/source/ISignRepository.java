package com.augurit.agmobile.agwater5.mine.sign.source;

import com.augurit.agmobile.agwater5.mine.sign.model.SignBean;
import com.augurit.agmobile.agwater5.mine.sign.model.SignResultBean;

import io.reactivex.Observable;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.mine.sign.source
 * @createTime 创建时间 ：2018-09-10
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public interface ISignRepository {

    Observable<SignBean> getSignInfo(String signerId, String yearMonth);


    Observable<SignResultBean> getSignResult(String userId, String userName, String orgName, String orgCode, double x, double y, String street, String address, String appVersion);
}
