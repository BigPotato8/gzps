package com.augurit.agmobile.agwater5.gcjs.eventlist.model;

/**
 * com.augurit.agmobile.agwater5.gcjs.eventlist.model
 * Created by sdb on 2019/4/10  19:35.
 * Desc：
 */

public class MallUrlBean {


    /**
     * success : true
     * message : http://192.168.32.46:8086aplanmis-mall/aea/mall/main/guide.do
     * content : 返回成功
     */

    private boolean success;
    private String message;
    private String content;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
