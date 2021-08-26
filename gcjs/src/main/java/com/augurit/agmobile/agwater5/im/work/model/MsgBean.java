package com.augurit.agmobile.agwater5.im.work.model;

import java.io.Serializable;
import java.util.List;

/**
 * com.augurit.agmobile.agwater5.gcjs.msg.model
 * Created by sdb on 2019/4/29  16:31.
 * Desc：
 */

public class MsgBean implements Serializable {

    /**
     * rows : [{"rangeId":"10001","contentId":"10001","userId":"2985c7cc-de2c-4c57-a7cc-b97f8531c7cf","receiveTime":null,"isRead":"0","readTime":null,"creater":"80000","createTime":1546272000000,"keyword":null,"orgId":"0368948a-1cdf-4bf8-a828-71d796ba89f6","isUserSend":"1","sendUserId":"ca1284bd-1ebd-47b1-8355-0dbf440a19d6","sendSystemName":null,"contentTitle":"[待受理]暨南大学广州知识产权人才基地建设项目","isImportant":"1","contentMemo":null,"createrMsgContent":"admin","createTimeMsgContent":1546272000000,"modifier":"admin","modifyTime":1546272000000,"contentText":"[待受理]暨南大学广州知识产权人才基地建设项目","isJump":null,"jumpUrl":null,"jumpMode":null,"sendUserName":null,"receiveUserName":null}]
     * total : 1
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

    public static class RowsBean implements Serializable {
        /**
         * rangeId : 10001
         * contentId : 10001
         * userId : 2985c7cc-de2c-4c57-a7cc-b97f8531c7cf
         * receiveTime : null
         * isRead : 0
         * readTime : null
         * creater : 80000
         * createTime : 1546272000000
         * keyword : null
         * orgId : 0368948a-1cdf-4bf8-a828-71d796ba89f6
         * isUserSend : 1
         * sendUserId : ca1284bd-1ebd-47b1-8355-0dbf440a19d6
         * sendSystemName : null
         * contentTitle : [待受理]暨南大学广州知识产权人才基地建设项目
         * isImportant : 1
         * contentMemo : null
         * createrMsgContent : admin
         * createTimeMsgContent : 1546272000000
         * modifier : admin
         * modifyTime : 1546272000000
         * contentText : [待受理]暨南大学广州知识产权人才基地建设项目
         * isJump : null
         * jumpUrl : null
         * jumpMode : null
         * sendUserName : null
         * receiveUserName : null
         */

        private String rangeId;
        private String contentId;
        private String userId;
        private Object receiveTime;
        private String isRead;
        private Object readTime;
        private String creater;
        private long createTime;
        private Object keyword;
        private String orgId;
        private String isUserSend;
        private String sendUserId;
        private Object sendSystemName;
        private String contentTitle;
        private String isImportant;
        private Object contentMemo;
        private String createrMsgContent;
        private long createTimeMsgContent;
        private String modifier;
        private long modifyTime;
        private String contentText;
        private Object isJump;
        private Object jumpUrl;
        private Object jumpMode;
        private Object sendUserName;
        private Object receiveUserName;

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

        public Object getReceiveTime() {
            return receiveTime;
        }

        public void setReceiveTime(Object receiveTime) {
            this.receiveTime = receiveTime;
        }

        public String getIsRead() {
            return isRead;
        }

        public void setIsRead(String isRead) {
            this.isRead = isRead;
        }

        public Object getReadTime() {
            return readTime;
        }

        public void setReadTime(Object readTime) {
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

        public Object getKeyword() {
            return keyword;
        }

        public void setKeyword(Object keyword) {
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

        public Object getSendSystemName() {
            return sendSystemName;
        }

        public void setSendSystemName(Object sendSystemName) {
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

        public Object getContentMemo() {
            return contentMemo;
        }

        public void setContentMemo(Object contentMemo) {
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

        public Object getIsJump() {
            return isJump;
        }

        public void setIsJump(Object isJump) {
            this.isJump = isJump;
        }

        public Object getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(Object jumpUrl) {
            this.jumpUrl = jumpUrl;
        }

        public Object getJumpMode() {
            return jumpMode;
        }

        public void setJumpMode(Object jumpMode) {
            this.jumpMode = jumpMode;
        }

        public Object getSendUserName() {
            return sendUserName;
        }

        public void setSendUserName(Object sendUserName) {
            this.sendUserName = sendUserName;
        }

        public Object getReceiveUserName() {
            return receiveUserName;
        }

        public void setReceiveUserName(Object receiveUserName) {
            this.receiveUserName = receiveUserName;
        }
    }
}
