package com.augurit.agmobile.agwater5.gcjs.eventlist.model;

import java.io.Serializable;
import java.util.List;

/**
 * @description 批文批复Bean
 * @date: $date$ $time$
 * @author: xieruibin
 */
public class PwpfBean implements Serializable{

    /**
     * itemMatinst : [{"matinstId":"2f1d4d77-450d-4a91-8feb-023a2e225c76","matinstName":"可行性研究报告的批复","matId":"0a26a475-2ddd-49f9-8ee0-77c39659d141","officialDocNo":"撒打算打算所多2","officialDocTitle":"撒大声地2","officialDocDueDate":"2020-03-10","officialDocPublishDate":"2020-03-18","docTypeName":"电子","docCount":1,"creator":"林伟俊","createDate":"2020.03.06","memo":"不在","attFiles":[{"bscAttDir":null,"bscAttDetail":null,"bscAttLink":{"detailId":null,"attCode":null,"attName":null,"attSize":null,"attType":null,"attFormat":null,"sortNo":null,"storeType":null,"attPath":null,"isEncrypt":null,"messageDigest":null,"memo1":null,"memo2":null,"memo3":null,"memo4":null,"memo5":null,"memo6":null,"creater":null,"createTime":null,"modifier":null,"modifyTime":null,"attDiskName":null,"encryptClass":null,"isRelativePath":null,"dirId":null,"orgId":null,"objectId":null,"geoObjectId":null,"isNeedPdf":null,"isConvertPdf":null,"convertPdfResult":null,"convertPdfTime":null,"linkId":"fc6f9253-cb93-47b1-8c5c-1556b35d5b5b","tableName":"AEA_HI_ITEM_MATINST","recordId":"2f1d4d77-450d-4a91-8feb-023a2e225c76","pkName":"MATINST_ID","linkType":null},"fileName":"u=2611804295,1292825829&fm=27&gp=0.jpg","fileSize":"38148","fileType":"jpg","updateTime":1583479370000,"orgId":"0368948a-1cdf-4bf8-a828-71d796ba89f6","md5Key":null,"fileId":"65ab77dd-d050-469b-b35a-f958b3aa266f","parentId":null,"parentName":null,"orgName":null,"createrName":"林伟俊","isEncrypt":null}]},{"matinstId":"f8a950a5-4550-4534-a8ad-beace2b6a600","matinstName":"可行性研究报告的批复","matId":"0a26a475-2ddd-49f9-8ee0-77c39659d141","officialDocNo":"123","officialDocTitle":"123","officialDocDueDate":"2020-03-06","officialDocPublishDate":"2020-03-06","docTypeName":"电子","docCount":0,"creator":"林伟俊","createDate":"2020.03.06","memo":"123","attFiles":[]}]
     * orgName : 江门市蓬江区发展改革局
     * orgId : null
     * userName : null
     */

    private String orgName;
    private String orgId;
    private String userName;
    private List<ItemMatinstBean> itemMatinst;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<ItemMatinstBean> getItemMatinst() {
        return itemMatinst;
    }

    public void setItemMatinst(List<ItemMatinstBean> itemMatinst) {
        this.itemMatinst = itemMatinst;
    }

    public static class ItemMatinstBean implements Serializable{
        /**
         * matinstId : 2f1d4d77-450d-4a91-8feb-023a2e225c76
         * matinstName : 可行性研究报告的批复
         * matId : 0a26a475-2ddd-49f9-8ee0-77c39659d141
         * officialDocNo : 撒打算打算所多2
         * officialDocTitle : 撒大声地2
         * officialDocDueDate : 2020-03-10
         * officialDocPublishDate : 2020-03-18
         * docTypeName : 电子
         * docCount : 1
         * creator : 林伟俊
         * createDate : 2020.03.06
         * memo : 不在
         * attFiles : [{"bscAttDir":null,"bscAttDetail":null,"bscAttLink":{"detailId":null,"attCode":null,"attName":null,"attSize":null,"attType":null,"attFormat":null,"sortNo":null,"storeType":null,"attPath":null,"isEncrypt":null,"messageDigest":null,"memo1":null,"memo2":null,"memo3":null,"memo4":null,"memo5":null,"memo6":null,"creater":null,"createTime":null,"modifier":null,"modifyTime":null,"attDiskName":null,"encryptClass":null,"isRelativePath":null,"dirId":null,"orgId":null,"objectId":null,"geoObjectId":null,"isNeedPdf":null,"isConvertPdf":null,"convertPdfResult":null,"convertPdfTime":null,"linkId":"fc6f9253-cb93-47b1-8c5c-1556b35d5b5b","tableName":"AEA_HI_ITEM_MATINST","recordId":"2f1d4d77-450d-4a91-8feb-023a2e225c76","pkName":"MATINST_ID","linkType":null},"fileName":"u=2611804295,1292825829&fm=27&gp=0.jpg","fileSize":"38148","fileType":"jpg","updateTime":1583479370000,"orgId":"0368948a-1cdf-4bf8-a828-71d796ba89f6","md5Key":null,"fileId":"65ab77dd-d050-469b-b35a-f958b3aa266f","parentId":null,"parentName":null,"orgName":null,"createrName":"林伟俊","isEncrypt":null}]
         */

        private String matinstId;
        private String matinstName;
        private String matId;
        private String officialDocNo;
        private String officialDocTitle;
        private String officialDocDueDate;
        private String officialDocPublishDate;
        private String docTypeName;
        private int docCount;
        private String creator;
        private String createDate;
        private String memo;
        private String pickUpTime;
        private String certArrivedTime;
        private String pickUpStatus;
        private List<AttFilesBean> attFiles;

        public String getMatinstId() {
            return matinstId;
        }

        public void setMatinstId(String matinstId) {
            this.matinstId = matinstId;
        }

        public String getMatinstName() {
            return matinstName;
        }

        public void setMatinstName(String matinstName) {
            this.matinstName = matinstName;
        }

        public String getMatId() {
            return matId;
        }

        public void setMatId(String matId) {
            this.matId = matId;
        }

        public String getOfficialDocNo() {
            return officialDocNo;
        }

        public void setOfficialDocNo(String officialDocNo) {
            this.officialDocNo = officialDocNo;
        }

        public String getOfficialDocTitle() {
            return officialDocTitle;
        }

        public void setOfficialDocTitle(String officialDocTitle) {
            this.officialDocTitle = officialDocTitle;
        }

        public String getOfficialDocDueDate() {
            return officialDocDueDate;
        }

        public void setOfficialDocDueDate(String officialDocDueDate) {
            this.officialDocDueDate = officialDocDueDate;
        }

        public String getOfficialDocPublishDate() {
            return officialDocPublishDate;
        }

        public void setOfficialDocPublishDate(String officialDocPublishDate) {
            this.officialDocPublishDate = officialDocPublishDate;
        }

        public String getDocTypeName() {
            return docTypeName;
        }

        public void setDocTypeName(String docTypeName) {
            this.docTypeName = docTypeName;
        }

        public int getDocCount() {
            return docCount;
        }

        public void setDocCount(int docCount) {
            this.docCount = docCount;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
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

        public String getPickUpStatus() {
            return pickUpStatus;
        }

        public void setPickUpStatus(String pickUpStatus) {
            this.pickUpStatus = pickUpStatus;
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
             * bscAttLink : {"detailId":null,"attCode":null,"attName":null,"attSize":null,"attType":null,"attFormat":null,"sortNo":null,"storeType":null,"attPath":null,"isEncrypt":null,"messageDigest":null,"memo1":null,"memo2":null,"memo3":null,"memo4":null,"memo5":null,"memo6":null,"creater":null,"createTime":null,"modifier":null,"modifyTime":null,"attDiskName":null,"encryptClass":null,"isRelativePath":null,"dirId":null,"orgId":null,"objectId":null,"geoObjectId":null,"isNeedPdf":null,"isConvertPdf":null,"convertPdfResult":null,"convertPdfTime":null,"linkId":"fc6f9253-cb93-47b1-8c5c-1556b35d5b5b","tableName":"AEA_HI_ITEM_MATINST","recordId":"2f1d4d77-450d-4a91-8feb-023a2e225c76","pkName":"MATINST_ID","linkType":null}
             * fileName : u=2611804295,1292825829&fm=27&gp=0.jpg
             * fileSize : 38148
             * fileType : jpg
             * updateTime : 1583479370000
             * orgId : 0368948a-1cdf-4bf8-a828-71d796ba89f6
             * md5Key : null
             * fileId : 65ab77dd-d050-469b-b35a-f958b3aa266f
             * parentId : null
             * parentName : null
             * orgName : null
             * createrName : 林伟俊
             * isEncrypt : null
             */

            private String bscAttDir;
            private String bscAttDetail;
            private BscAttLinkBean bscAttLink;
            private String fileName;
            private String fileSize;
            private String fileType;
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
                 * linkId : fc6f9253-cb93-47b1-8c5c-1556b35d5b5b
                 * tableName : AEA_HI_ITEM_MATINST
                 * recordId : 2f1d4d77-450d-4a91-8feb-023a2e225c76
                 * pkName : MATINST_ID
                 * linkType : null
                 */

                private String detailId;
                private String attCode;
                private String attName;
                private String attSize;
                private String attType;
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
}
