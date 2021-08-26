package com.augurit.agmobile.agwater5.gcjspad.event;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.event
 * @createTime 创建时间 ：2020/12/9
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class FilterBeanEvent {
    private String pageKey;

    public FilterBeanEvent(String pageKey) {
        this.pageKey = pageKey;
    }

    public String getPageKey() {
        return pageKey;
    }

    public void setPageKey(String pageKey) {
        this.pageKey = pageKey;
    }
}
