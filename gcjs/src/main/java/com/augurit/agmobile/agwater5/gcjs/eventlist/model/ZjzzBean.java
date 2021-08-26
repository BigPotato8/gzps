package com.augurit.agmobile.agwater5.gcjs.eventlist.model;

import java.io.Serializable;
import java.util.List;

/**
 * 批文批复--证件证照bean
 */
public class ZjzzBean implements Serializable {

    /**
     * certinstId : 99b06e89-908e-41b2-9937-19dfb5a66691
     * certId : f3380a08-e668-497b-803a-ad46c98a397b
     * certinstCode : 2324
     * certCode : 2324
     * certinstName : 通用结果物002
     * issueDate : 2020-07-21 00:00
     * issueOrgId : 3bbc1cb1-0fb2-42b3-977f-383ee695a969
     * iteminstId : 4221033b-ab76-4c23-882b-0a411f6a18e6
     * applyinstId : cf011033-e915-49e0-a476-c560bc580984
     * projScale : 1213
     * memo : 123
     * creater : 梁每
     * createTime : 1595294536000
     * isOnlyRead : 0
     * matId : 4f41b29e-cf7a-4709-a292-01d515f27520
     * matinstId : 392452d3-bb40-4448-b026-a26b27ce46f0
     * pickUpStatus : null
     * pickUpTime : null
     * certArrivedTime : null
     * certArrivedUserId : null
     * attFiles : [{"bscAttDir":null,"bscAttDetail":null,"bscAttLink":{"detailId":null,"attCode":null,"attName":null,"attSize":null,"attType":null,"attSource":null,"attFormat":null,"sortNo":null,"storeType":null,"attPath":null,"isEncrypt":null,"messageDigest":null,"memo1":null,"memo2":null,"memo3":null,"memo4":null,"memo5":null,"memo6":null,"creater":null,"createTime":null,"modifier":null,"modifyTime":null,"attDiskName":null,"encryptClass":null,"isRelativePath":null,"dirId":null,"orgId":null,"objectId":null,"geoObjectId":null,"isNeedPdf":null,"isConvertPdf":null,"convertPdfResult":null,"convertPdfTime":null,"dirNameSeq":null,"linkId":"c9e71501-652f-4240-b7df-f674237eb3eb","tableName":"AEA_HI_ITEM_MATINST","recordId":"392452d3-bb40-4448-b026-a26b27ce46f0","pkName":"MATINST_ID","linkType":null},"fileName":"NIO.png","fileSize":"128501","fileType":"png","fileSource":null,"updateTime":"2020-07-21 09:22:17","orgId":"0368948a-1cdf-4bf8-a828-71d796ba89f6","md5Key":null,"fileId":"2c36d339-3dfb-42db-af2a-6c8b4ce84020","parentId":null,"parentName":null,"orgName":null,"createrName":"梁每","isEncrypt":null},{"bscAttDir":null,"bscAttDetail":null,"bscAttLink":{"detailId":null,"attCode":null,"attName":null,"attSize":null,"attType":null,"attSource":null,"attFormat":null,"sortNo":null,"storeType":null,"attPath":null,"isEncrypt":null,"messageDigest":null,"memo1":null,"memo2":null,"memo3":null,"memo4":null,"memo5":null,"memo6":null,"creater":null,"createTime":null,"modifier":null,"modifyTime":null,"attDiskName":null,"encryptClass":null,"isRelativePath":null,"dirId":null,"orgId":null,"objectId":null,"geoObjectId":null,"isNeedPdf":null,"isConvertPdf":null,"convertPdfResult":null,"convertPdfTime":null,"dirNameSeq":null,"linkId":"4783d37a-ceea-4da2-959c-e68ec3f197b2","tableName":"AEA_HI_ITEM_MATINST","recordId":"392452d3-bb40-4448-b026-a26b27ce46f0","pkName":"MATINST_ID","linkType":null},"fileName":"统一发件.png","fileSize":"146735","fileType":"png","fileSource":null,"updateTime":"2020-07-21 09:21:05","orgId":"0368948a-1cdf-4bf8-a828-71d796ba89f6","md5Key":null,"fileId":"feeb02d4-77a5-452d-bf35-211703ae4ca1","parentId":null,"parentName":null,"orgName":null,"createrName":"梁每","isEncrypt":null}]
     */

    private String certinstId;
    private String certId;
    private String certinstCode;
    private String certCode;
    private String certinstName;
    private String issueDate;
    private String issueOrgId;
    private String iteminstId;
    private String applyinstId;
    private String projScale;
    private String memo;
    private String creater;
    private String createTime;
    private String isOnlyRead;
    private String matId;
    private String matinstId;
    private String pickUpStatus;
    private String pickUpTime;
    private String certArrivedTime;
    private String certArrivedUserId;
    private List<AttFilesBean> attFiles;

    public String getCertinstId() {
        return certinstId;
    }

    public void setCertinstId(String certinstId) {
        this.certinstId = certinstId;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getCertinstCode() {
        return certinstCode;
    }

    public void setCertinstCode(String certinstCode) {
        this.certinstCode = certinstCode;
    }

    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode;
    }

    public String getCertinstName() {
        return certinstName;
    }

    public void setCertinstName(String certinstName) {
        this.certinstName = certinstName;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssueOrgId() {
        return issueOrgId;
    }

    public void setIssueOrgId(String issueOrgId) {
        this.issueOrgId = issueOrgId;
    }

    public String getIteminstId() {
        return iteminstId;
    }

    public void setIteminstId(String iteminstId) {
        this.iteminstId = iteminstId;
    }

    public String getApplyinstId() {
        return applyinstId;
    }

    public void setApplyinstId(String applyinstId) {
        this.applyinstId = applyinstId;
    }

    public String getProjScale() {
        return projScale;
    }

    public void setProjScale(String projScale) {
        this.projScale = projScale;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsOnlyRead() {
        return isOnlyRead;
    }

    public void setIsOnlyRead(String isOnlyRead) {
        this.isOnlyRead = isOnlyRead;
    }

    public String getMatId() {
        return matId;
    }

    public void setMatId(String matId) {
        this.matId = matId;
    }

    public String getMatinstId() {
        return matinstId;
    }

    public void setMatinstId(String matinstId) {
        this.matinstId = matinstId;
    }

    public String getPickUpStatus() {
        return pickUpStatus;
    }

    public void setPickUpStatus(String pickUpStatus) {
        this.pickUpStatus = pickUpStatus;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getCertArrivedTime() {
        return certArrivedTime;
    }

    public void setCertArrivedTime(String certArrivedTime) {
        this.certArrivedTime = certArrivedTime;
    }

    public String getCertArrivedUserId() {
        return certArrivedUserId;
    }

    public void setCertArrivedUserId(String certArrivedUserId) {
        this.certArrivedUserId = certArrivedUserId;
    }

    public List<AttFilesBean> getAttFiles() {
        return attFiles;
    }

    public void setAttFiles(List<AttFilesBean> attFiles) {
        this.attFiles = attFiles;
    }

    public static class AttFilesBean implements Serializable{
        /**
         * bscAttDir : null
         * bscAttDetail : null
         * bscAttLink : {"detailId":null,"attCode":null,"attName":null,"attSize":null,"attType":null,"attSource":null,"attFormat":null,"sortNo":null,"storeType":null,"attPath":null,"isEncrypt":null,"messageDigest":null,"memo1":null,"memo2":null,"memo3":null,"memo4":null,"memo5":null,"memo6":null,"creater":null,"createTime":null,"modifier":null,"modifyTime":null,"attDiskName":null,"encryptClass":null,"isRelativePath":null,"dirId":null,"orgId":null,"objectId":null,"geoObjectId":null,"isNeedPdf":null,"isConvertPdf":null,"convertPdfResult":null,"convertPdfTime":null,"dirNameSeq":null,"linkId":"c9e71501-652f-4240-b7df-f674237eb3eb","tableName":"AEA_HI_ITEM_MATINST","recordId":"392452d3-bb40-4448-b026-a26b27ce46f0","pkName":"MATINST_ID","linkType":null}
         * fileName : NIO.png
         * fileSize : 128501
         * fileType : png
         * fileSource : null
         * updateTime : 2020-07-21 09:22:17
         * orgId : 0368948a-1cdf-4bf8-a828-71d796ba89f6
         * md5Key : null
         * fileId : 2c36d339-3dfb-42db-af2a-6c8b4ce84020
         * parentId : null
         * parentName : null
         * orgName : null
         * createrName : 梁每
         * isEncrypt : null
         */

        private String bscAttDir;
        private String bscAttDetail;
        private BscAttLinkBean bscAttLink;
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

        public String getBscAttDir() {
            return bscAttDir;
        }

        public void setBscAttDir(String bscAttDir) {
            this.bscAttDir = bscAttDir;
        }

        public String getBscAttDetail() {
            return bscAttDetail;
        }

        public void setBscAttDetail(String bscAttDetail) {
            this.bscAttDetail = bscAttDetail;
        }

        public BscAttLinkBean getBscAttLink() {
            return bscAttLink;
        }

        public void setBscAttLink(BscAttLinkBean bscAttLink) {
            this.bscAttLink = bscAttLink;
        }

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

        public static class BscAttLinkBean implements Serializable{
            /**
             * detailId : null
             * attCode : null
             * attName : null
             * attSize : null
             * attType : null
             * attSource : null
             * attFormat : null
             * sortNo : null
             * storeType : null
             * attPath : null
             * isEncrypt : null
             * messageDigest : null
             * memo1 : null
             * memo2 : null
             * memo3 : null
             * memo4 : null
             * memo5 : null
             * memo6 : null
             * creater : null
             * createTime : null
             * modifier : null
             * modifyTime : null
             * attDiskName : null
             * encryptClass : null
             * isRelativePath : null
             * dirId : null
             * orgId : null
             * objectId : null
             * geoObjectId : null
             * isNeedPdf : null
             * isConvertPdf : null
             * convertPdfResult : null
             * convertPdfTime : null
             * dirNameSeq : null
             * linkId : c9e71501-652f-4240-b7df-f674237eb3eb
             * tableName : AEA_HI_ITEM_MATINST
             * recordId : 392452d3-bb40-4448-b026-a26b27ce46f0
             * pkName : MATINST_ID
             * linkType : null
             */

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
            private String geoObjectId;
            private String isNeedPdf;
            private String isConvertPdf;
            private String convertPdfResult;
            private String convertPdfTime;
            private String dirNameSeq;
            private String linkId;
            private String tableName;
            private String recordId;
            private String pkName;
            private String linkType;

            public String getDetailId() {
                return detailId;
            }

            public void setDetailId(String detailId) {
                this.detailId = detailId;
            }

            public String getAttCode() {
                return attCode;
            }

            public void setAttCode(String attCode) {
                this.attCode = attCode;
            }

            public String getAttName() {
                return attName;
            }

            public void setAttName(String attName) {
                this.attName = attName;
            }

            public String getAttSize() {
                return attSize;
            }

            public void setAttSize(String attSize) {
                this.attSize = attSize;
            }

            public String getAttType() {
                return attType;
            }

            public void setAttType(String attType) {
                this.attType = attType;
            }

            public String getAttSource() {
                return attSource;
            }

            public void setAttSource(String attSource) {
                this.attSource = attSource;
            }

            public String getAttFormat() {
                return attFormat;
            }

            public void setAttFormat(String attFormat) {
                this.attFormat = attFormat;
            }

            public String getSortNo() {
                return sortNo;
            }

            public void setSortNo(String sortNo) {
                this.sortNo = sortNo;
            }

            public String getStoreType() {
                return storeType;
            }

            public void setStoreType(String storeType) {
                this.storeType = storeType;
            }

            public String getAttPath() {
                return attPath;
            }

            public void setAttPath(String attPath) {
                this.attPath = attPath;
            }

            public String getIsEncrypt() {
                return isEncrypt;
            }

            public void setIsEncrypt(String isEncrypt) {
                this.isEncrypt = isEncrypt;
            }

            public String getMessageDigest() {
                return messageDigest;
            }

            public void setMessageDigest(String messageDigest) {
                this.messageDigest = messageDigest;
            }

            public String getMemo1() {
                return memo1;
            }

            public void setMemo1(String memo1) {
                this.memo1 = memo1;
            }

            public String getMemo2() {
                return memo2;
            }

            public void setMemo2(String memo2) {
                this.memo2 = memo2;
            }

            public String getMemo3() {
                return memo3;
            }

            public void setMemo3(String memo3) {
                this.memo3 = memo3;
            }

            public String getMemo4() {
                return memo4;
            }

            public void setMemo4(String memo4) {
                this.memo4 = memo4;
            }

            public String getMemo5() {
                return memo5;
            }

            public void setMemo5(String memo5) {
                this.memo5 = memo5;
            }

            public String getMemo6() {
                return memo6;
            }

            public void setMemo6(String memo6) {
                this.memo6 = memo6;
            }

            public String getCreater() {
                return creater;
            }

            public void setCreater(String creater) {
                this.creater = creater;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getModifier() {
                return modifier;
            }

            public void setModifier(String modifier) {
                this.modifier = modifier;
            }

            public String getModifyTime() {
                return modifyTime;
            }

            public void setModifyTime(String modifyTime) {
                this.modifyTime = modifyTime;
            }

            public String getAttDiskName() {
                return attDiskName;
            }

            public void setAttDiskName(String attDiskName) {
                this.attDiskName = attDiskName;
            }

            public String getEncryptClass() {
                return encryptClass;
            }

            public void setEncryptClass(String encryptClass) {
                this.encryptClass = encryptClass;
            }

            public String getIsRelativePath() {
                return isRelativePath;
            }

            public void setIsRelativePath(String isRelativePath) {
                this.isRelativePath = isRelativePath;
            }

            public String getDirId() {
                return dirId;
            }

            public void setDirId(String dirId) {
                this.dirId = dirId;
            }

            public String getOrgId() {
                return orgId;
            }

            public void setOrgId(String orgId) {
                this.orgId = orgId;
            }

            public String getObjectId() {
                return objectId;
            }

            public void setObjectId(String objectId) {
                this.objectId = objectId;
            }

            public String getGeoObjectId() {
                return geoObjectId;
            }

            public void setGeoObjectId(String geoObjectId) {
                this.geoObjectId = geoObjectId;
            }

            public String getIsNeedPdf() {
                return isNeedPdf;
            }

            public void setIsNeedPdf(String isNeedPdf) {
                this.isNeedPdf = isNeedPdf;
            }

            public String getIsConvertPdf() {
                return isConvertPdf;
            }

            public void setIsConvertPdf(String isConvertPdf) {
                this.isConvertPdf = isConvertPdf;
            }

            public String getConvertPdfResult() {
                return convertPdfResult;
            }

            public void setConvertPdfResult(String convertPdfResult) {
                this.convertPdfResult = convertPdfResult;
            }

            public String getConvertPdfTime() {
                return convertPdfTime;
            }

            public void setConvertPdfTime(String convertPdfTime) {
                this.convertPdfTime = convertPdfTime;
            }

            public String getDirNameSeq() {
                return dirNameSeq;
            }

            public void setDirNameSeq(String dirNameSeq) {
                this.dirNameSeq = dirNameSeq;
            }

            public String getLinkId() {
                return linkId;
            }

            public void setLinkId(String linkId) {
                this.linkId = linkId;
            }

            public String getTableName() {
                return tableName;
            }

            public void setTableName(String tableName) {
                this.tableName = tableName;
            }

            public String getRecordId() {
                return recordId;
            }

            public void setRecordId(String recordId) {
                this.recordId = recordId;
            }

            public String getPkName() {
                return pkName;
            }

            public void setPkName(String pkName) {
                this.pkName = pkName;
            }

            public String getLinkType() {
                return linkType;
            }

            public void setLinkType(String linkType) {
                this.linkType = linkType;
            }
        }
    }
}
