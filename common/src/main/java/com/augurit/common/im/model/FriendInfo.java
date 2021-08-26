package com.augurit.common.im.model;

import java.util.List;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzps.im.bean
 * @createTime 创建时间 ：2017-11-09
 * @modifyBy 修改人 ：taoerxiang
 * @modifyTime 修改时间 ：2017-11-09
 * @modifyMemo 修改备注：
 */

public class FriendInfo  {
    private List<FriendItem> data;

    public List<FriendItem> getData() {
        return data;
    }

    public void setData(List<FriendItem> data) {
        this.data = data;
    }
}
