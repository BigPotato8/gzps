package com.augurit.agmobile.agwater5.gcjs.eventlist.model;

import java.util.List;

/**
 * com.augurit.agmobile.agwater5.gcjs.eventlist.model
 * Created by sdb on 2019/4/10  17:38.
 * Desc：
 */

public class LinkSendConfig {
//    {
//        taskId: taskId, //当前节点任务实例id
//        conclusionOptionValue: '1', //当前节点任务办理结论：0不通过 1通过 2不涉及
//        sendConfigs: [{ //发送的目标节点集合
//              isUserTask: true, //是否用户节点 true是false否
//              isEnableMultiTask: false, //节点是否启用多工作项 true是false否
//              assignees: '161219', //下一节点审批人（这里是登录用户的登录名），多个用逗号隔开
//              destActId: 'shenpi' //下一节点定义id（节点编号）
//        }]
//    }
    /**
     * taskId : taskId
     * conclusionOptionValue : 1
     * sendConfigs : [{"isUserTask":true,"isEnableMultiTask":false,"assignees":"161219","destActId":"shenpi"}]
     */

    private String taskId;
    private String conclusionOptionValue;
    private String conclusionGroupCode;
    private String enableConclusion;//发送弹窗是否展示结论
    private List<SendConfigsBean> sendConfigs;

    public String getConclusionGroupCode() {
        return conclusionGroupCode;
    }

    public void setConclusionGroupCode(String conclusionGroupCode) {
        this.conclusionGroupCode = conclusionGroupCode;
    }

    public String getEnableConclusion() {
        return enableConclusion;
    }

    public void setEnableConclusion(String enableConclusion) {
        this.enableConclusion = enableConclusion;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getConclusionOptionValue() {
        return conclusionOptionValue;
    }

    public void setConclusionOptionValue(String conclusionOptionValue) {
        this.conclusionOptionValue = conclusionOptionValue;
    }

    public List<SendConfigsBean> getSendConfigs() {
        return sendConfigs;
    }

    public void setSendConfigs(List<SendConfigsBean> sendConfigs) {
        this.sendConfigs = sendConfigs;
    }

    public static class SendConfigsBean {
        /**
         * isUserTask : true
         * isEnableMultiTask : false
         * assignees : 161219
         * destActId : shenpi
         */

        private boolean isUserTask;
        private boolean isEnableMultiTask;
        private String assignees;
        private String destActId;

        public boolean isIsUserTask() {
            return isUserTask;
        }

        public void setIsUserTask(boolean isUserTask) {
            this.isUserTask = isUserTask;
        }

        public boolean isIsEnableMultiTask() {
            return isEnableMultiTask;
        }

        public void setIsEnableMultiTask(boolean isEnableMultiTask) {
            this.isEnableMultiTask = isEnableMultiTask;
        }

        public String getAssignees() {
            return assignees;
        }

        public void setAssignees(String assignees) {
            this.assignees = assignees;
        }

        public String getDestActId() {
            return destActId;
        }

        public void setDestActId(String destActId) {
            this.destActId = destActId;
        }
    }


}
