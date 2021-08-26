package com.augurit.common.im.view;

import android.content.Context;

import com.augurit.common.im.model.FriendItem;
import com.augurit.common.im.model.GroupItem;

import java.util.List;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.im.view
 * @createTime 创建时间 ：2018-09-06
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public interface IMContract {


    interface View {
        Context getContext();

        void showFriendList(List<FriendItem> friendList);

        default void showGroupList(List<GroupItem> groupList){}
    }


    interface Presenter {
        void loadFriends(String userId);

        void loadGroups(String userId);

        void destroyDisposable();
    }


}
