package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model;

/**
 * @author 创建人 ：panming
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model
 * @createTime 创建时间 ：2019-10-31
 * @modifyBy 修改人 ：panming
 * @modifyTime 修改时间 ：2019-10-31
 * @modifyMemo 修改备注：办件统计
 */
public class StatsBean {
   private boolean  success;
   private String  message;
   private Content content;

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

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

    public  class Content{

        private int allCount;
        private int allComplete;
        private int nowMonthCount;
        private int nowMonthComplete;

        public int getAllCount() {
            return allCount;
        }

        public void setAllCount(int allCount) {
            this.allCount = allCount;
        }

        public int getAllComplete() {
            return allComplete;
        }

        public void setAllComplete(int allComplete) {
            this.allComplete = allComplete;
        }

        public int getNowMonthCount() {
            return nowMonthCount;
        }

        public void setNowMonthCount(int nowMonthCount) {
            this.nowMonthCount = nowMonthCount;
        }

        public int getNowMonthComplete() {
            return nowMonthComplete;
        }

        public void setNowMonthComplete(int nowMonthComplete) {
            this.nowMonthComplete = nowMonthComplete;
        }


    }
}
