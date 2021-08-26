package com.augurit.agmobile.agwater5.gcjs.model;

import java.util.List;

public class Message {

    /**
     * rows : [{"rangeId":"31db9766-be5c-4755-9dab-905cd1dbea0c","contentId":"37672e1f-84ce-442c-abbf-36f85d7978f0","userId":"3a7d6d4d-8b73-496c-85e0-390be1da35f4","receiveTime":null,"isRead":"0","readTime":null,"creater":"ckry","createTime":1607909837000,"keyword":null,"orgId":"0368948a-1cdf-4bf8-a828-71d796ba89f6","isUserSend":"1","sendUserId":"3a7d6d4d-8b73-496c-85e0-390be1da35f4","sendSystemName":"ckry","contentTitle":"撒旦撒撒多撒多","isImportant":null,"contentMemo":"都是撒撒撒多撒多","createrMsgContent":"ckry","createTimeMsgContent":1607909837000,"modifier":"ckry","modifyTime":1607909837000,"contentText":"dsadsadsadsasdsadsdsdsdsadsadsadsadsassdsad","isJump":null,"jumpUrl":null,"jumpMode":null,"sendUserName":"窗口人员","receiveUserName":"窗口人员","coditionSendTimeBegin":null,"coditionSendTimeEnd":null},{"rangeId":"0bdc743e-c8a6-454b-982d-5cdce9ec5b58","contentId":"0f08bd44-6f06-4510-9220-9ab90cccbaf9","userId":"3a7d6d4d-8b73-496c-85e0-390be1da35f4","receiveTime":null,"isRead":"1","readTime":null,"creater":"ckry","createTime":1607666397000,"keyword":null,"orgId":"0368948a-1cdf-4bf8-a828-71d796ba89f6","isUserSend":"1","sendUserId":"3a7d6d4d-8b73-496c-85e0-390be1da35f4","sendSystemName":"ckry","contentTitle":"test","isImportant":null,"contentMemo":"1212","createrMsgContent":"ckry","createTimeMsgContent":1607666397000,"modifier":"ckry","modifyTime":1607666397000,"contentText":"121313","isJump":null,"jumpUrl":null,"jumpMode":null,"sendUserName":"窗口人员","receiveUserName":"窗口人员","coditionSendTimeBegin":null,"coditionSendTimeEnd":null}]
     * total : 2
     */

    private int total;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * rangeId : 31db9766-be5c-4755-9dab-905cd1dbea0c
         * contentId : 37672e1f-84ce-442c-abbf-36f85d7978f0
         * userId : 3a7d6d4d-8b73-496c-85e0-390be1da35f4
         * receiveTime : null
         * isRead : 0
         * readTime : null
         * creater : ckry
         * createTime : 1607909837000
         * keyword : null
         * orgId : 0368948a-1cdf-4bf8-a828-71d796ba89f6
         * isUserSend : 1
         * sendUserId : 3a7d6d4d-8b73-496c-85e0-390be1da35f4
         * sendSystemName : ckry
         * contentTitle : 撒旦撒撒多撒多
         * isImportant : null
         * contentMemo : 都是撒撒撒多撒多
         * createrMsgContent : ckry
         * createTimeMsgContent : 1607909837000
         * modifier : ckry
         * modifyTime : 1607909837000
         * contentText : dsadsadsadsasdsadsdsdsdsadsadsadsadsassdsad
         * isJump : null
         * jumpUrl : null
         * jumpMode : null
         * sendUserName : 窗口人员
         * receiveUserName : 窗口人员
         * coditionSendTimeBegin : null
         * coditionSendTimeEnd : null
         */

        private String rangeId;
        private String contentId;
        private String userId;
        private String receiveTime;
        private String isRead;
        private String readTime;
        private String creater;
        private long createTime;
        private String keyword;
        private String orgId;
        private String isUserSend;
        private String sendUserId;
        private String sendSystemName;
        private String contentTitle;
        private String isImportant;
        private String contentMemo;
        private String createrMsgContent;
        private long createTimeMsgContent;
        private String modifier;
        private long modifyTime;
        private String contentText;
        private String isJump;
        private String jumpUrl;
        private String jumpMode;
        private String sendUserName;
        private String receiveUserName;
        private String coditionSendTimeBegin;
        private String coditionSendTimeEnd;

        public String getRangeId() {
            return rangeId;
        }

        public void setRangeId(String rangeId) {
            this.rangeId = rangeId;
        }

        public String getContentId() {
            return contentId;
        }

        public void setContentId(String contentId) {
            this.contentId = contentId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getReceiveTime() {
            return receiveTime;
        }

        public void setReceiveTime(String receiveTime) {
            this.receiveTime = receiveTime;
        }

        public String getIsRead() {
            return isRead;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
        }

        public String getReadTime() {
            return readTime;
        }

        public void setReadTime(String readTime) {
            this.readTime = readTime;
        }

        public String getCreater() {
            return creater;
        }

        public void setCreater(String creater) {
            this.creater = creater;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getIsUserSend() {
            return isUserSend;
        }

        public void setIsUserSend(String isUserSend) {
            this.isUserSend = isUserSend;
        }

        public String getSendUserId() {
            return sendUserId;
        }

        public void setSendUserId(String sendUserId) {
            this.sendUserId = sendUserId;
        }

        public String getSendSystemName() {
            return sendSystemName;
        }

        public void setSendSystemName(String sendSystemName) {
            this.sendSystemName = sendSystemName;
        }

        public String getContentTitle() {
            return contentTitle;
        }

        public void setContentTitle(String contentTitle) {
            this.contentTitle = contentTitle;
        }

        public String getIsImportant() {
            return isImportant;
        }

        public void setIsImportant(String isImportant) {
            this.isImportant = isImportant;
        }

        public String getContentMemo() {
            return contentMemo;
        }

        public void setContentMemo(String contentMemo) {
            this.contentMemo = contentMemo;
        }

        public String getCreaterMsgContent() {
            return createrMsgContent;
        }

        public void setCreaterMsgContent(String createrMsgContent) {
            this.createrMsgContent = createrMsgContent;
        }

        public long getCreateTimeMsgContent() {
            return createTimeMsgContent;
        }

        public void setCreateTimeMsgContent(long createTimeMsgContent) {
            this.createTimeMsgContent = createTimeMsgContent;
        }

        public String getModifier() {
            return modifier;
        }

        public void setModifier(String modifier) {
            this.modifier = modifier;
        }

        public long getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(long modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getContentText() {
            return contentText;
        }

        public void setContentText(String contentText) {
            this.contentText = contentText;
        }

        public String getIsJump() {
            return isJump;
        }

        public void setIsJump(String isJump) {
            this.isJump = isJump;
        }

        public String getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
        }

        public String getJumpMode() {
            return jumpMode;
        }

        public void setJumpMode(String jumpMode) {
            this.jumpMode = jumpMode;
        }

        public String getSendUserName() {
            return sendUserName;
        }

        public void setSendUserName(String sendUserName) {
            this.sendUserName = sendUserName;
        }

        public String getReceiveUserName() {
            return receiveUserName;
        }

        public void setReceiveUserName(String receiveUserName) {
            this.receiveUserName = receiveUserName;
        }

        public String getCoditionSendTimeBegin() {
            return coditionSendTimeBegin;
        }

        public void setCoditionSendTimeBegin(String coditionSendTimeBegin) {
            this.coditionSendTimeBegin = coditionSendTimeBegin;
        }

        public String getCoditionSendTimeEnd() {
            return coditionSendTimeEnd;
        }

        public void setCoditionSendTimeEnd(String coditionSendTimeEnd) {
            this.coditionSendTimeEnd = coditionSendTimeEnd;
        }
    }
}
