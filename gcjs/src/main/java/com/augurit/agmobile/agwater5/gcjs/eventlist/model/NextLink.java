package com.augurit.agmobile.agwater5.gcjs.eventlist.model;

import java.util.List;

/**
 * com.augurit.agmobile.agwater5.gcjs.eventlist.model
 * Created by sdb on 2019/4/9  18:58.
 * Desc：
 */

public class NextLink {

    /**
     * success : true
     * message : 
     * content : [{"destActName":"部门并联审批","destActId":"task1553937872998","needSelectAssignee":false,"defaultSendAssignees":null,"defaultSendAssigneesId":null,"message":"所选下一环节为单人审批模式，请选择一个办理人。","smsRemind":null,"mailRemind":null,"userMobile":null,"orgId":null,"orgName":null,"directSend":false,"multiTask":false,"userTask":true}]
     */

    private boolean success;
    private String message;
    private List<ContentBean> content;

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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * destActName : 部门并联审批
         * destActId : task1553937872998
         * needSelectAssignee : false
         * defaultSendAssignees : null
         * defaultSendAssigneesId : null
         * message : 所选下一环节为单人审批模式，请选择一个办理人。
         * smsRemind : null
         * mailRemind : null
         * userMobile : null
         * orgId : null
         * orgName : null
         * directSend : false
         * multiTask : false
         * userTask : true
         */

        private String destActName;
        private String destActId;
        private boolean needSelectAssignee;
        private String defaultSendAssignees;
        private String defaultSendAssigneesId;
        private String message;
        private String smsRemind;
        private String mailRemind;
        private String userMobile;
        private String orgId;
        private String orgName;
        private boolean directSend;
        private boolean multiTask;
        private boolean userTask;

        public String getDestActName() {
            return destActName;
        }

        public void setDestActName(String destActName) {
            this.destActName = destActName;
        }

        public String getDestActId() {
            return destActId;
        }

        public void setDestActId(String destActId) {
            this.destActId = destActId;
        }

        public boolean isNeedSelectAssignee() {
            return needSelectAssignee;
        }

        public void setNeedSelectAssignee(boolean needSelectAssignee) {
            this.needSelectAssignee = needSelectAssignee;
        }

        public String getDefaultSendAssignees() {
            return defaultSendAssignees;
        }

        public void setDefaultSendAssignees(String defaultSendAssignees) {
            this.defaultSendAssignees = defaultSendAssignees;
        }

        public String getDefaultSendAssigneesId() {
            return defaultSendAssigneesId;
        }

        public void setDefaultSendAssigneesId(String defaultSendAssigneesId) {
            this.defaultSendAssigneesId = defaultSendAssigneesId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getSmsRemind() {
            return smsRemind;
        }

        public void setSmsRemind(String smsRemind) {
            this.smsRemind = smsRemind;
        }

        public String getMailRemind() {
            return mailRemind;
        }

        public void setMailRemind(String mailRemind) {
            this.mailRemind = mailRemind;
        }

        public String getUserMobile() {
            return userMobile;
        }

        public void setUserMobile(String userMobile) {
            this.userMobile = userMobile;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public boolean isDirectSend() {
            return directSend;
        }

        public void setDirectSend(boolean directSend) {
            this.directSend = directSend;
        }

        public boolean isMultiTask() {
            return multiTask;
        }

        public void setMultiTask(boolean multiTask) {
            this.multiTask = multiTask;
        }

        public boolean isUserTask() {
            return userTask;
        }

        public void setUserTask(boolean userTask) {
            this.userTask = userTask;
        }
    }
}
