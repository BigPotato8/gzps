package com.augurit.common.common.model;

import com.augurit.agmobile.common.lib.net.model.ApiResult;

/**
 * Created by liangsh on 2017/11/8.
 */

public class Result3<T> extends ApiResult<T>{

    private String no;
    private String pass;
    private String doubt;
    private String total;
    //暂存类型的数量
    private String zc;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPass() {
        return pass;
    }

    public void setPasss(String passs) {
        this.pass = passs;
    }

    public String getDoubt() {
        return doubt;
    }

    public void setDoubt(String doubt) {
        this.doubt = doubt;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getZc() {
        return zc;
    }

    public void setZc(String zc) {
        this.zc = zc;
    }
}
