package com.augurit.agmobile.agwater5.gcjspad.examine.model;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.examine.model
 * @createTime 创建时间 ：2020/12/10
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class TaskItemBean {
    private String pageKey;
    private String pageTotal;

    public TaskItemBean(String pageKey, String pageTotal) {
        this.pageKey = pageKey;
        this.pageTotal = pageTotal;
    }

    public String getPageKey() {
        return pageKey;
    }

    public void setPageKey(String pageKey) {
        this.pageKey = pageKey;
    }

    public String getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(String pageTotal) {
        this.pageTotal = pageTotal;
    }
}
