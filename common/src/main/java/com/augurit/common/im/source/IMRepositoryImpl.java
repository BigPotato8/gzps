package com.augurit.common.im.source;

import android.content.Context;

import com.augurit.agmobile.common.lib.json.JsonUtil;
import com.augurit.common.common.manager.AwUrlManager;
import com.augurit.common.im.model.FriendInfo;
import com.augurit.common.im.model.FriendItem;
import com.augurit.common.im.model.GroupInfo;
import com.augurit.common.im.model.GroupItem;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.request.GetRequest;

import java.util.ArrayList;
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
public class IMRepositoryImpl implements IMRepository {
    private Context mContext;

    public IMRepositoryImpl(Context context) {
        mContext = context;
    }

    @Override
    public Observable<List<FriendItem>> getFriendList(String userId) {
        GetRequest getRequest = EasyHttp.get("im/friends/" + userId)
                .baseUrl(AwUrlManager.easyMockUrl());
        return getRequest.execute(String.class)
                .map(result -> {
                    FriendInfo friendInfo = JsonUtil.getObject(result, FriendInfo.class);
                    if (friendInfo != null
                            && friendInfo.getData() != null) {
                        return friendInfo.getData();
                    } else {
                        return new ArrayList<>();
                    }
                });
    }

    @Override
    public Observable<List<GroupItem>> getGroupList(String userId) {
        GetRequest getRequest = EasyHttp.get("im/group/" + userId)
                .baseUrl(AwUrlManager.easyMockUrl());
        return getRequest.execute(String.class)
                .map(result -> {
                    GroupInfo groupInfo = JsonUtil.getObject(result, GroupInfo.class);
                    if (groupInfo != null
                            && groupInfo.getData() != null) {
                        return groupInfo.getData();
                    } else {
                        return new ArrayList<>();
                    }
                });
    }
}
