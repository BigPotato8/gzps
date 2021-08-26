package com.augurit.agmobile.agwater5.gcjs.eventlist.model;

import java.util.List;

/**
 * 包名：com.augurit.agmobile.agwater5.gcjs.eventlist.model
 * 文件描述：
 * 创建人：limeijuan
 * 创建时间：2021/8/2 15:06
 * 修改人：limeijuan
 * 修改时间：2021/8/2 15:06
 * 修改备注：
 */
public class ProcessNewModel {
    private String title;
    private List<EventProcess> mEventProcesses;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ProcessNewModel(String title) {
        this.title = title;
    }

    public List<EventProcess> getEventProcesses() {
        return mEventProcesses;
    }

    public void setEventProcesses(List<EventProcess> eventProcesses) {
        mEventProcesses = eventProcesses;
    }
}
