package com.augurit.agmobile.agwater5.gcjs.eventlist.model;

import java.io.Serializable;
import java.util.List;

/**
 * 结果物补充材料接口返回的数据
 */
public class ResultGoodsMaterialBean implements Serializable {

    /**
     * bscAttFileAndDir : null
     * iteminstName : 房屋建筑工程和市政基础设施工程竣工验收备案
     * iteminstId : 133fcb31-0446-4f62-a9b7-47e480116f53
     * itemVerId : b75a8422-41da-4a12-bab4-a593098acb3d
     * outerDoneState :
     * matinstList : null
     * resultCount : 1
     * itemHisResultFileList : [{"fileName":"蓝色胖猫.jpg","fileSize":"64823","fileType":"jpg","fileSource":"1","updateTime":"2021-08-05 11:46:27","orgId":"0368948a-1cdf-4bf8-a828-71d796ba89f6","md5Key":null,"fileId":"7504a213-833c-4b87-8cc9-2b781c76df7b","parentId":null,"parentName":null,"orgName":null,"createrName":null,"isEncrypt":null,"isActive":"0"}]
     */

    private String bscAttFileAndDir;
    private String iteminstName;
    private String iteminstId;
    private String itemVerId;
    private String outerDoneState;
    private String matinstList;
    private Integer resultCount;
    private List<ItemHisResultFileListBean> itemHisResultFileList;

    public String getBscAttFileAndDir() {
        return bscAttFileAndDir;
    }

    public void setBscAttFileAndDir(String bscAttFileAndDir) {
        this.bscAttFileAndDir = bscAttFileAndDir;
    }

    public String getIteminstName() {
        return iteminstName;
    }

    public void setIteminstName(String iteminstName) {
        this.iteminstName = iteminstName;
    }

    public String getIteminstId() {
        return iteminstId;
    }

    public void setIteminstId(String iteminstId) {
        this.iteminstId = iteminstId;
    }

    public String getItemVerId() {
        return itemVerId;
    }

    public void setItemVerId(String itemVerId) {
        this.itemVerId = itemVerId;
    }

    public String getOuterDoneState() {
        return outerDoneState;
    }

    public void setOuterDoneState(String outerDoneState) {
        this.outerDoneState = outerDoneState;
    }

    public String getMatinstList() {
        return matinstList;
    }

    public void setMatinstList(String matinstList) {
        this.matinstList = matinstList;
    }

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public List<ItemHisResultFileListBean> getItemHisResultFileList() {
        return itemHisResultFileList;
    }

    public void setItemHisResultFileList(List<ItemHisResultFileListBean> itemHisResultFileList) {
        this.itemHisResultFileList = itemHisResultFileList;
    }

    public static class ItemHisResultFileListBean implements Serializable{
        /**
         * fileName : 蓝色胖猫.jpg
         * fileSize : 64823
         * fileType : jpg
         * fileSource : 1
         * updateTime : 2021-08-05 11:46:27
         * orgId : 0368948a-1cdf-4bf8-a828-71d796ba89f6
         * md5Key : null
         * fileId : 7504a213-833c-4b87-8cc9-2b781c76df7b
         * parentId : null
         * parentName : null
         * orgName : null
         * createrName : null
         * isEncrypt : null
         * isActive : 0
         */

        private String fileName;
        private String fileSize;
        private String fileType;
        private String fileSource;
        private String updateTime;
        private String orgId;
        private String md5Key;
        private String fileId;
        private String parentId;
        private String parentName;
        private String orgName;
        private String createrName;
        private String isEncrypt;
        private String isActive;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public String getFileSource() {
            return fileSource;
        }

        public void setFileSource(String fileSource) {
            this.fileSource = fileSource;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getMd5Key() {
            return md5Key;
        }

        public void setMd5Key(String md5Key) {
            this.md5Key = md5Key;
        }

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getParentName() {
            return parentName;
        }

        public void setParentName(String parentName) {
            this.parentName = parentName;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getCreaterName() {
            return createrName;
        }

        public void setCreaterName(String createrName) {
            this.createrName = createrName;
        }

        public String getIsEncrypt() {
            return isEncrypt;
        }

        public void setIsEncrypt(String isEncrypt) {
            this.isEncrypt = isEncrypt;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }
    }
}
