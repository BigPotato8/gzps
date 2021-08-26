package com.augurit.agmobile.agwater5.gcjs.model;

import java.io.Serializable;
import java.util.List;

/**
 * com.augurit.agmobile.agwater5.gcjs.model
 * Created by sdb on 2019/4/28  10:31.
 * Desc：
 */

public class Announce implements Serializable {

    /**
     * rows : [{"contentId":"25d80208-1896-4654-8865-4171774955f1","orgId":"012aa547-7104-418d-87cc-824f24f1a278","categoryId":"2a74d42e-e569-457b-bedc-eeb545157e62","contentTitle":"关于发布《学分制管理规范》的通知","contentAuthor":"沈丽媛","coverAttLinkId":"333","contentText":"<p><span style=\"color: rgb(25, 31, 37); font-family: &quot;Microsoft YaHei&quot;, &quot;Segoe UI&quot;, system-ui, Roboto, &quot;Droid Sans&quot;, &quot;Helvetica Neue&quot;, sans-serif; font-variant-numeric: normal; font-variant-east-asian: normal; line-height: 22px; white-space: pre-wrap; widows: 1; background-color: rgb(255, 255, 255);\"> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;关于发布《学分制管理规范》的通知\n &nbsp; &nbsp; &nbsp; 为调动员工的学习积极性，激发员工主动学习意愿，创建学习型组织，使个人职业生涯规划与公司发展相结合。秉持\u201c训用合一\u201d的原则，培养和储备各类人才，为公司的可持续发展提供人力资源保障。根据公司《员工手册》培训管理和学分管理的相关内容，特制定《学分制管理规范》，具体内容详见附件，请全体员工遵照执行。\n &nbsp; &nbsp; &nbsp; 以上决定自发文之日起生效。<\/span><\/p><p><span style=\"color: rgb(25, 31, 37); font-family: &quot;Microsoft YaHei&quot;, &quot;Segoe UI&quot;, system-ui, Roboto, &quot;Droid Sans&quot;, &quot;Helvetica Neue&quot;, sans-serif; font-variant-numeric: normal; font-variant-east-asian: normal; line-height: 22px; white-space: pre-wrap; widows: 1; background-color: rgb(255, 255, 255);\">\n &nbsp; &nbsp; &nbsp; 特此通知\n附件：《学分制管理规范》\n &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 广州奥格智能科技有限公司\n &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;2018年8月24日<\/span><\/p>","contentSortNo":null,"needSendMsg":"0","publishUserName":"沈丽媛","publishTime":1556267631000,"contentMemo":"关于发布《学分制管理规范》的通知","creater":"082100","createTime":1535597136000,"modifier":"161279","modifyTime":1556267631000,"keyword":null,"categoryName":"人力资源类"}]
     * total : 7
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

    public static class RowsBean  implements Serializable {
        /**
         * contentId : 25d80208-1896-4654-8865-4171774955f1
         * orgId : 012aa547-7104-418d-87cc-824f24f1a278
         * categoryId : 2a74d42e-e569-457b-bedc-eeb545157e62
         * contentTitle : 关于发布《学分制管理规范》的通知
         * contentAuthor : 沈丽媛
         * coverAttLinkId : 333
         * contentText : <p><span style="color: rgb(25, 31, 37); font-family: &quot;Microsoft YaHei&quot;, &quot;Segoe UI&quot;, system-ui, Roboto, &quot;Droid Sans&quot;, &quot;Helvetica Neue&quot;, sans-serif; font-variant-numeric: normal; font-variant-east-asian: normal; line-height: 22px; white-space: pre-wrap; widows: 1; background-color: rgb(255, 255, 255);"> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;关于发布《学分制管理规范》的通知
         * &nbsp; &nbsp; &nbsp; 为调动员工的学习积极性，激发员工主动学习意愿，创建学习型组织，使个人职业生涯规划与公司发展相结合。秉持“训用合一”的原则，培养和储备各类人才，为公司的可持续发展提供人力资源保障。根据公司《员工手册》培训管理和学分管理的相关内容，特制定《学分制管理规范》，具体内容详见附件，请全体员工遵照执行。
         * &nbsp; &nbsp; &nbsp; 以上决定自发文之日起生效。</span></p><p><span style="color: rgb(25, 31, 37); font-family: &quot;Microsoft YaHei&quot;, &quot;Segoe UI&quot;, system-ui, Roboto, &quot;Droid Sans&quot;, &quot;Helvetica Neue&quot;, sans-serif; font-variant-numeric: normal; font-variant-east-asian: normal; line-height: 22px; white-space: pre-wrap; widows: 1; background-color: rgb(255, 255, 255);">
         * &nbsp; &nbsp; &nbsp; 特此通知
         * 附件：《学分制管理规范》
         * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 广州奥格智能科技有限公司
         * &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;2018年8月24日</span></p>
         * contentSortNo : null
         * needSendMsg : 0
         * publishUserName : 沈丽媛
         * publishTime : 1556267631000
         * contentMemo : 关于发布《学分制管理规范》的通知
         * creater : 082100
         * createTime : 1535597136000
         * modifier : 161279
         * modifyTime : 1556267631000
         * keyword : null
         * categoryName : 人力资源类
         */

        private String contentId;
        private String orgId;
        private String categoryId;
        private String contentTitle;
        private String contentAuthor;
        private String coverAttLinkId;
        private String contentText;
        private Object contentSortNo;
        private String needSendMsg;
        private String publishUserName;
        private long publishTime;
        private String contentMemo;
        private String creater;
        private long createTime;
        private String modifier;
        private long modifyTime;
        private Object keyword;
        private String categoryName;

        public String getContentId() {
            return contentId;
        }

        public void setContentId(String contentId) {
            this.contentId = contentId;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getContentTitle() {
            return contentTitle;
        }

        public void setContentTitle(String contentTitle) {
            this.contentTitle = contentTitle;
        }

        public String getContentAuthor() {
            return contentAuthor;
        }

        public void setContentAuthor(String contentAuthor) {
            this.contentAuthor = contentAuthor;
        }

        public String getCoverAttLinkId() {
            return coverAttLinkId;
        }

        public void setCoverAttLinkId(String coverAttLinkId) {
            this.coverAttLinkId = coverAttLinkId;
        }

        public String getContentText() {
            return contentText;
        }

        public void setContentText(String contentText) {
            this.contentText = contentText;
        }

        public Object getContentSortNo() {
            return contentSortNo;
        }

        public void setContentSortNo(Object contentSortNo) {
            this.contentSortNo = contentSortNo;
        }

        public String getNeedSendMsg() {
            return needSendMsg;
        }

        public void setNeedSendMsg(String needSendMsg) {
            this.needSendMsg = needSendMsg;
        }

        public String getPublishUserName() {
            return publishUserName;
        }

        public void setPublishUserName(String publishUserName) {
            this.publishUserName = publishUserName;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public String getContentMemo() {
            return contentMemo;
        }

        public void setContentMemo(String contentMemo) {
            this.contentMemo = contentMemo;
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

        public Object getKeyword() {
            return keyword;
        }

        public void setKeyword(Object keyword) {
            this.keyword = keyword;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }
    }
}
