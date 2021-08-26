package com.augurit.agmobile.agwater5.gcjs.model;

/**
 * 任务数目实体类
 */
public class TasksCountBean {
    private String toDoTotal;//代办数
    private String overDueTotal;//逾期数
    private String warningTotal;//预警数

    public String getToDoTotal() {
        return toDoTotal;
    }

    public void setToDoTotal(String toDoTotal) {
        this.toDoTotal = toDoTotal;
    }

    public String getOverDueTotal() {
        return overDueTotal;
    }

    public void setOverDueTotal(String overDueTotal) {
        this.overDueTotal = overDueTotal;
    }

    public String getWarningTotal() {
        return warningTotal;
    }

    public void setWarningTotal(String warningTotal) {
        this.warningTotal = warningTotal;
    }
}
