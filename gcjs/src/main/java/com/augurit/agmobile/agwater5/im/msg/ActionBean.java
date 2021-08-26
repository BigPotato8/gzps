package com.augurit.agmobile.agwater5.im.msg;

import java.io.Serializable;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.im.msg
 * @createTime 创建时间 ：2019-08-15
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class ActionBean implements Serializable {
    private String actionName;
    private int actionUri;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public int getActionUri() {
        return actionUri;
    }

    public void setActionUri(int actionUri) {
        this.actionUri = actionUri;
    }
}
