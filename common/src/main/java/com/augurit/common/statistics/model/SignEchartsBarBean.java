package com.augurit.common.statistics.model;

import java.util.List;

/**
 * Created by luob on 2017/12/27.
 */
public class SignEchartsBarBean {

    private String type1;
    private String title;
    private String imageUrl;
    private List<String> times;
    /*public List<Double> yesterdaydata;
    public List<Double> todaydata;*/
    private List<String> yesterdaydata;
    private List<String> todaydata;
    private List<String> percent;

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

    /*public List<Double> getYesterdaydata() {
        return yesterdaydata;
    }

    public void setYesterdaydata(List<Double> yesterdaydata) {
        this.yesterdaydata = yesterdaydata;
    }

    public List<Double> getTodaydata() {
        return todaydata;
    }

    public void setTodaydata(List<Double> todaydata) {
        this.todaydata = todaydata;
    }*/

    public List<String> getYesterdaydata() {
        return yesterdaydata;
    }

    public void setYesterdaydata(List<String> yesterdaydata) {
        this.yesterdaydata = yesterdaydata;
    }

    public List<String> getTodaydata() {
        return todaydata;
    }

    public void setTodaydata(List<String> todaydata) {
        this.todaydata = todaydata;
    }

    public List<String> getPercent() {
        return percent;
    }

    public void setPercent(List<String> percent) {
        this.percent = percent;
    }
}
