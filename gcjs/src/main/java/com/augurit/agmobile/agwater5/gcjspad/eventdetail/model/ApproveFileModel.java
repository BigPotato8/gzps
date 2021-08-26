package com.augurit.agmobile.agwater5.gcjspad.eventdetail.model;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjspad.eventdetail.model
 * @createTime 创建时间 ：2020/12/17
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class ApproveFileModel {
    private String bscAttDir;

    private String bscAttDetail;

    private BscAttLink bscAttLink;

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

    public void setBscAttDir(String bscAttDir) {
        this.bscAttDir = bscAttDir;
    }

    public String getBscAttDir() {
        return this.bscAttDir;
    }

    public void setBscAttDetail(String bscAttDetail) {
        this.bscAttDetail = bscAttDetail;
    }

    public String getBscAttDetail() {
        return this.bscAttDetail;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileSize() {
        return this.fileSize;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileSource(String fileSource) {
        this.fileSource = fileSource;
    }

    public String getFileSource() {
        return this.fileSource;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

    public String getMd5Key() {
        return this.md5Key;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileId() {
        return this.fileId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentName() {
        return this.parentName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public String getCreaterName() {
        return this.createrName;
    }

    public void setIsEncrypt(String isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

    public String getIsEncrypt() {
        return this.isEncrypt;
    }

    public BscAttLink getBscAttLink() {
        return bscAttLink;
    }

    public void setBscAttLink(BscAttLink bscAttLink) {
        this.bscAttLink = bscAttLink;
    }

    class BscAttLink {
        private String detailId;

        private String attCode;

        private String attName;

        private String attSize;

        private String attType;

        private String attSource;

        private String attFormat;

        private String sortNo;

        private String storeType;

        private String attPath;

        private String isEncrypt;

        private String messageDigest;

        private String memo1;

        private String memo2;

        private String memo3;

        private String memo4;

        private String memo5;

        private String memo6;

        private String creater;

        private String createTime;

        private String modifier;

        private String modifyTime;

        private String attDiskName;

        private String encryptClass;

        private String isRelativePath;

        private String dirId;

        private String orgId;

        private String objectId;

        private String bucketName;

        private String geoObjectId;

        private String isNeedPdf;

        private String isConvertPdf;

        private String convertPdfResult;

        private String convertPdfTime;

        private String isVersionMgmt;

        private String parentDetailId;

        private String versionMemo;

        private boolean increaseBigVerion;

        private String dirNameSeq;

        private String linkId;

        private String tableName;

        private String recordId;

        private String pkName;

        private String linkType;

        public void setDetailId(String detailId) {
            this.detailId = detailId;
        }

        public String getDetailId() {
            return this.detailId;
        }

        public void setAttCode(String attCode) {
            this.attCode = attCode;
        }

        public String getAttCode() {
            return this.attCode;
        }

        public void setAttName(String attName) {
            this.attName = attName;
        }

        public String getAttName() {
            return this.attName;
        }

        public void setAttSize(String attSize) {
            this.attSize = attSize;
        }

        public String getAttSize() {
            return this.attSize;
        }

        public void setAttType(String attType) {
            this.attType = attType;
        }

        public String getAttType() {
            return this.attType;
        }

        public void setAttSource(String attSource) {
            this.attSource = attSource;
        }

        public String getAttSource() {
            return this.attSource;
        }

        public void setAttFormat(String attFormat) {
            this.attFormat = attFormat;
        }

        public String getAttFormat() {
            return this.attFormat;
        }

        public void setSortNo(String sortNo) {
            this.sortNo = sortNo;
        }

        public String getSortNo() {
            return this.sortNo;
        }

        public void setStoreType(String storeType) {
            this.storeType = storeType;
        }

        public String getStoreType() {
            return this.storeType;
        }

        public void setAttPath(String attPath) {
            this.attPath = attPath;
        }

        public String getAttPath() {
            return this.attPath;
        }

        public void setIsEncrypt(String isEncrypt) {
            this.isEncrypt = isEncrypt;
        }

        public String getIsEncrypt() {
            return this.isEncrypt;
        }

        public void setMessageDigest(String messageDigest) {
            this.messageDigest = messageDigest;
        }

        public String getMessageDigest() {
            return this.messageDigest;
        }

        public void setMemo1(String memo1) {
            this.memo1 = memo1;
        }

        public String getMemo1() {
            return this.memo1;
        }

        public void setMemo2(String memo2) {
            this.memo2 = memo2;
        }

        public String getMemo2() {
            return this.memo2;
        }

        public void setMemo3(String memo3) {
            this.memo3 = memo3;
        }

        public String getMemo3() {
            return this.memo3;
        }

        public void setMemo4(String memo4) {
            this.memo4 = memo4;
        }

        public String getMemo4() {
            return this.memo4;
        }

        public void setMemo5(String memo5) {
            this.memo5 = memo5;
        }

        public String getMemo5() {
            return this.memo5;
        }

        public void setMemo6(String memo6) {
            this.memo6 = memo6;
        }

        public String getMemo6() {
            return this.memo6;
        }

        public void setCreater(String creater) {
            this.creater = creater;
        }

        public String getCreater() {
            return this.creater;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreateTime() {
            return this.createTime;
        }

        public void setModifier(String modifier) {
            this.modifier = modifier;
        }

        public String getModifier() {
            return this.modifier;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getModifyTime() {
            return this.modifyTime;
        }

        public void setAttDiskName(String attDiskName) {
            this.attDiskName = attDiskName;
        }

        public String getAttDiskName() {
            return this.attDiskName;
        }

        public void setEncryptClass(String encryptClass) {
            this.encryptClass = encryptClass;
        }

        public String getEncryptClass() {
            return this.encryptClass;
        }

        public void setIsRelativePath(String isRelativePath) {
            this.isRelativePath = isRelativePath;
        }

        public String getIsRelativePath() {
            return this.isRelativePath;
        }

        public void setDirId(String dirId) {
            this.dirId = dirId;
        }

        public String getDirId() {
            return this.dirId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getOrgId() {
            return this.orgId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getObjectId() {
            return this.objectId;
        }

        public void setBucketName(String bucketName) {
            this.bucketName = bucketName;
        }

        public String getBucketName() {
            return this.bucketName;
        }

        public void setGeoObjectId(String geoObjectId) {
            this.geoObjectId = geoObjectId;
        }

        public String getGeoObjectId() {
            return this.geoObjectId;
        }

        public void setIsNeedPdf(String isNeedPdf) {
            this.isNeedPdf = isNeedPdf;
        }

        public String getIsNeedPdf() {
            return this.isNeedPdf;
        }

        public void setIsConvertPdf(String isConvertPdf) {
            this.isConvertPdf = isConvertPdf;
        }

        public String getIsConvertPdf() {
            return this.isConvertPdf;
        }

        public void setConvertPdfResult(String convertPdfResult) {
            this.convertPdfResult = convertPdfResult;
        }

        public String getConvertPdfResult() {
            return this.convertPdfResult;
        }

        public void setConvertPdfTime(String convertPdfTime) {
            this.convertPdfTime = convertPdfTime;
        }

        public String getConvertPdfTime() {
            return this.convertPdfTime;
        }

        public void setIsVersionMgmt(String isVersionMgmt) {
            this.isVersionMgmt = isVersionMgmt;
        }

        public String getIsVersionMgmt() {
            return this.isVersionMgmt;
        }

        public void setParentDetailId(String parentDetailId) {
            this.parentDetailId = parentDetailId;
        }

        public String getParentDetailId() {
            return this.parentDetailId;
        }

        public void setVersionMemo(String versionMemo) {
            this.versionMemo = versionMemo;
        }

        public String getVersionMemo() {
            return this.versionMemo;
        }

        public void setIncreaseBigVerion(boolean increaseBigVerion) {
            this.increaseBigVerion = increaseBigVerion;
        }

        public boolean getIncreaseBigVerion() {
            return this.increaseBigVerion;
        }

        public void setDirNameSeq(String dirNameSeq) {
            this.dirNameSeq = dirNameSeq;
        }

        public String getDirNameSeq() {
            return this.dirNameSeq;
        }

        public void setLinkId(String linkId) {
            this.linkId = linkId;
        }

        public String getLinkId() {
            return this.linkId;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getTableName() {
            return this.tableName;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }

        public String getRecordId() {
            return this.recordId;
        }

        public void setPkName(String pkName) {
            this.pkName = pkName;
        }

        public String getPkName() {
            return this.pkName;
        }

        public void setLinkType(String linkType) {
            this.linkType = linkType;
        }

        public String getLinkType() {
            return this.linkType;
        }
    }


}
