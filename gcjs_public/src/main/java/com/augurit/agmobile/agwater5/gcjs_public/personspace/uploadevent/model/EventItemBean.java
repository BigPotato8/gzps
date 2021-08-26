package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model;

import java.io.Serializable;

/**
 * @description 事项item
 * @date: $date$ $time$
 * @author: xieruibin
 */
public class EventItemBean implements Serializable{

    /**
     * itemVerId : b760bd15-b3a8-4a0c-9859-7061eeb52d3e
     * itemName : 影响交通安全的占路施工业务
     */

    private String itemVerId;
    private String itemName;

    public String getItemVerId() {
        return itemVerId;
    }

    public void setItemVerId(String itemVerId) {
        this.itemVerId = itemVerId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
