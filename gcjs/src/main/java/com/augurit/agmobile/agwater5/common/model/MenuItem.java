package com.augurit.agmobile.agwater5.common.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 首页菜单项
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agmobile5.home.model
 * @createTime 创建时间 ：2018/8/17
 * @modifyBy 修改人 ：xiejiexin
 * @modifyTime 修改时间 ：2018/8/17
 * @modifyMemo 修改备注：
 */
public class MenuItem {

    private String name;
    private int iconRes = -1;
    private String iconUrl;
    private String url;
    private Class clazz;
    private Map<String, Serializable> params;

    public MenuItem(String name, int iconRes, Class clazz) {
        this.name = name;
        this.iconRes = iconRes;
        this.clazz = clazz;
    }

    public MenuItem(String name,
                    int iconRes,
                    String iconUrl,
                    String url,
                    Class clazz,
                    Map<String, Serializable> params) {
        this.name = name;
        this.iconRes = iconRes;
        this.iconUrl = iconUrl;
        this.url = url;
        this.clazz = clazz;
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Map<String, Serializable> getParams() {
        return params;
    }

    public void setParams(Map<String, Serializable> params) {
        this.params = params;
    }

    public MenuItem addParam(String key, Serializable value) {
        if (this.params == null) {
            this.params = new HashMap<>();
        }
        this.params.put(key, value);
        return this;
    }
}
