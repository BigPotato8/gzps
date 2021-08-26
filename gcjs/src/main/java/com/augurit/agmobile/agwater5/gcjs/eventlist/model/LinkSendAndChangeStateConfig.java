package com.augurit.agmobile.agwater5.gcjs.eventlist.model;

import java.util.List;

/**
 * 窗口流程：流程审批并更改申请状态和事项状态按钮方法（使用于既要推动流程流转，也需要修改申报状态和事项状态时使用）
 */

public class LinkSendAndChangeStateConfig {

    private String applyinstId;//申请实例ID
    private String iteminstId;//事项id，可能为空
    private String itemState;//事项状态
    private String applyState;//申请状态
    private String toleranceTime;//null
    private String timeruleId;//null
    private String conclusionOptionValue;////当前节点任务办理结论：0不通过 1通过 2不涉及
    private List<LinkSendConfig> sendObjectStr;//流程节点实体字符串

    public String getApplyinstId() {
        return applyinstId;
    }

    public void setApplyinstId(String applyinstId) {
        this.applyinstId = applyinstId;
    }

    public String getIteminstId() {
        return iteminstId;
    }

    public void setIteminstId(String iteminstId) {
        this.iteminstId = iteminstId;
    }

    public String getItemState() {
        return itemState;
    }

    public void setItemState(String itemState) {
        this.itemState = itemState;
    }

    public String getApplyState() {
        return applyState;
    }

    public void setApplyState(String applyState) {
        this.applyState = applyState;
    }

    public String getToleranceTime() {
        return toleranceTime;
    }

    public void setToleranceTime(String toleranceTime) {
        this.toleranceTime = toleranceTime;
    }

    public String getTimeruleId() {
        return timeruleId;
    }

    public void setTimeruleId(String timeruleId) {
        this.timeruleId = timeruleId;
    }

    public String getConclusionOptionValue() {
        return conclusionOptionValue;
    }

    public void setConclusionOptionValue(String conclusionOptionValue) {
        this.conclusionOptionValue = conclusionOptionValue;
    }

    public List<LinkSendConfig> getSendObjectStr() {
        return sendObjectStr;
    }

    public void setSendObjectStr(List<LinkSendConfig> sendObjectStr) {
        this.sendObjectStr = sendObjectStr;
    }


}
