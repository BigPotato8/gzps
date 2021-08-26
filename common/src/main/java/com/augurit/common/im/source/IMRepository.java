package com.augurit.common.im.source;


import com.augurit.common.im.model.FriendItem;
import com.augurit.common.im.model.GroupItem;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.im.source
 * @createTime 创建时间 ：2018-09-06
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public interface IMRepository {

    Observable<List<FriendItem>> getFriendList(String userId);

    Observable<List<GroupItem>> getGroupList(String userId);
}
