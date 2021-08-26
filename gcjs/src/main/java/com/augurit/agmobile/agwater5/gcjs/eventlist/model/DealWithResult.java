package com.augurit.agmobile.agwater5.gcjs.eventlist.model;

import java.util.List;

public class DealWithResult {

    /**
     * success : true
     * message :
     * content : ["受理","不予受理","转办"]
     */

    private boolean success;
    private String message;
    private List<String> content;

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

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
