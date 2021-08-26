package com.augurit.agmobile.agwater5.im.work.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.common.im.timchat.model
 * @createTime 创建时间 ：2019/8/1
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class PubItem<T extends PubItemChild> implements Serializable {
    protected int id;
    protected ArrayList<T> child;
    protected long time;
    protected String name;
    protected String iconUrl;
    protected int icon;
    protected int type;//1.通知公告；2.个人消息；3.新闻消息

    public PubItem(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<T> getChild() {
        return child;
    }

    public void setChild(ArrayList<T> child) {
        this.child = child;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
