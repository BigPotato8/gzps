package com.augurit.agmobile.agwater5.gcjs.eventlist.model;

import java.io.Serializable;
import java.util.List;

/**
 * 选择组织或用户bean
 */
public class PersonSelectBean implements Serializable {
    //dataType 用户和组织两种
    public final static String USER = "USER";
    public final static String ORG = "ORG";

    /**
     * id : de5236e4-f6d7-4656-a67f-917e1e054000
     * pId : ab98a673-b4c4-4049-b33d-1dfc2af79c3e
     * name : 黄文达
     * textValue : huangwd
     * iconSkin : user
     * dataType : USER
     * isParent : false
     * chkDisabled : false
     * open : null
     * nocheck : null
     */

    private String id;
    private String pId;
    private String name;
    private String textValue;
    private String iconSkin;
    private String dataType;
    private boolean isParent;
    private boolean chkDisabled;
    private Boolean open = false;//是否展开，默认不展开
    private Boolean nocheck = false;//是否选中，仅成员可选中

    private List<PersonSelectBean> children;//子成员和组织

    public List<PersonSelectBean> getChildren() {
        return children;
    }

    public void setChildren(List<PersonSelectBean> children) {
        this.children = children;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isIsParent() {
        return isParent;
    }

    public void setIsParent(boolean isParent) {
        this.isParent = isParent;
    }

    public boolean isChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getNocheck() {
        return nocheck;
    }

    public void setNocheck(Boolean nocheck) {
        this.nocheck = nocheck;
    }
}
