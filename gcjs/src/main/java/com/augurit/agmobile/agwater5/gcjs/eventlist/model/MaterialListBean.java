package com.augurit.agmobile.agwater5.gcjs.eventlist.model;

import java.io.Serializable;
import java.util.List;

/**
 * 4.0返回的材料列表bean
 */
public class MaterialListBean implements Serializable {

    /**
     * matId : 293deb8f-41fc-4be4-a077-552758b65d26
     * matName : 超限高层建筑工程抗震设防审查申报表
     * matFrom : 10
     * fileType : null
     * realPaperCount : 1
     * realCopyCount : null
     * attCount : 2
     * paperMatinstId : bdd247f6-4fe2-43b6-95f9-f3607f6412fa
     * copyMatinstId : null
     * attMatinstId : 488ec530-4260-4383-84a8-0646fb336ceb
     * hasDwg : null
     * sortNo : null
     * fileList : [{"bscAttDir":null,"bscAttDetail":null,"bscAttLink":{"detailId":null,"attCode":null,"attName":null,"attSize":null,"attType":null,"attFormat":null,"sortNo":null,"storeType":null,"attPath":null,"isEncrypt":null,"messageDigest":null,"memo1":null,"memo2":null,"memo3":null,"memo4":null,"memo5":null,"memo6":null,"creater":null,"createTime":null,"modifier":null,"modifyTime":null,"attDiskName":null,"encryptClass":null,"isRelativePath":null,"dirId":null,"orgId":null,"StringId":null,"geoStringId":null,"isNeedPdf":null,"isConvertPdf":null,"convertPdfResult":null,"convertPdfTime":null,"linkId":"3a009ec7-1ac1-4450-ae87-6547e0c6fd7c","tableName":"AEA_HI_ITEM_MATINST","recordId":"488ec530-4260-4383-84a8-0646fb336ceb","pkName":"MATINST_ID","linkType":null},"fileName":"u=2280862038,498367946&fm=27&gp=0.jpg","fileSize":"10266","fileType":"jpg","updateTime":1583976775000,"orgId":"0368948a-1cdf-4bf8-a828-71d796ba89f6","md5Key":null,"fileId":"7b86ae21-94ae-4648-81ec-d18ed1901e79","parentId":null,"parentName":null,"orgName":null,"createrName":"潘锦俊","isEncrypt":null},{"bscAttDir":null,"bscAttDetail":null,"bscAttLink":{"detailId":null,"attCode":null,"attName":null,"attSize":null,"attType":null,"attFormat":null,"sortNo":null,"storeType":null,"attPath":null,"isEncrypt":null,"messageDigest":null,"memo1":null,"memo2":null,"memo3":null,"memo4":null,"memo5":null,"memo6":null,"creater":null,"createTime":null,"modifier":null,"modifyTime":null,"attDiskName":null,"encryptClass":null,"isRelativePath":null,"dirId":null,"orgId":null,"StringId":null,"geoStringId":null,"isNeedPdf":null,"isConvertPdf":null,"convertPdfResult":null,"convertPdfTime":null,"linkId":"a67328f2-3049-48d8-bf8e-6545e1fcc4ac","tableName":"AEA_HI_ITEM_MATINST","recordId":"488ec530-4260-4383-84a8-0646fb336ceb","pkName":"MATINST_ID","linkType":null},"fileName":"doge.jpg","fileSize":"16644","fileType":"jpg","updateTime":1583976790000,"orgId":"0368948a-1cdf-4bf8-a828-71d796ba89f6","md5Key":null,"fileId":"d75e28c3-62fe-4af1-ad60-254c7dc72c2c","parentId":null,"parentName":null,"orgName":null,"createrName":"潘锦俊","isEncrypt":null}]
     * duePaperCount : 1
     * dueCopyCount : 0
     * matProp : null
     * certinstId : null
     * forminstId : null
     * linkmanInfoId : null
     * certFileList : null
     * certinstSource : null
     * itemVerIds : 2d93440c-0d55-4a80-9b50-625654da2dc1
     * certMatinstId : null
     * certId : null
     * matCode : MAT-C0000010598
     * stoFormId : null
     * certinstList : []
     */

    private String matId;
    private String matName;
    private String matFrom;
    private String fileType;
    private Integer realPaperCount;
    private Integer realCopyCount;
    private Integer attCount;
    private String paperMatinstId;
    private String copyMatinstId;
    private String attMatinstId;
    private String hasDwg;
    private String sortNo;
    private Integer duePaperCount;
    private Integer dueCopyCount;
    private String matProp;
    private String certinstId;
    private String forminstId;
    private String linkmanInfoId;
    private String certFileList;
    private String certinstSource;
    private String itemVerIds;
    private String certMatinstId;
    private String certId;
    private String matCode;
    private String stoFormId;
    private String paperIsRequire;
    private List<FileListBean> fileList;
    private List<?> certinstList;
    private String attIsRequire;

    public String getAttIsRequire() {
        return attIsRequire;
    }

    public void setAttIsRequire(String attIsRequire) {
        this.attIsRequire = attIsRequire;
    }

    public String getPaperIsRequire() {
        return paperIsRequire;
    }

    public void setPaperIsRequire(String paperIsRequire) {
        this.paperIsRequire = paperIsRequire;
    }

    public String getMatId() {
        return matId;
    }

    public void setMatId(String matId) {
        this.matId = matId;
    }

    public String getMatName() {
        return matName;
    }

    public void setMatName(String matName) {
        this.matName = matName;
    }

    public String getMatFrom() {
        return matFrom;
    }

    public void setMatFrom(String matFrom) {
        this.matFrom = matFrom;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Integer getRealPaperCount() {
        return realPaperCount;
    }

    public void setRealPaperCount(Integer realPaperCount) {
        this.realPaperCount = realPaperCount;
    }

    public Integer getRealCopyCount() {
        return realCopyCount;
    }

    public void setRealCopyCount(Integer realCopyCount) {
        this.realCopyCount = realCopyCount;
    }

    public Integer getAttCount() {
        return attCount;
    }

    public void setAttCount(Integer attCount) {
        this.attCount = attCount;
    }

    public String getPaperMatinstId() {
        return paperMatinstId;
    }

    public void setPaperMatinstId(String paperMatinstId) {
        this.paperMatinstId = paperMatinstId;
    }

    public String getCopyMatinstId() {
        return copyMatinstId;
    }

    public void setCopyMatinstId(String copyMatinstId) {
        this.copyMatinstId = copyMatinstId;
    }

    public String getAttMatinstId() {
        return attMatinstId;
    }

    public void setAttMatinstId(String attMatinstId) {
        this.attMatinstId = attMatinstId;
    }

    public String getHasDwg() {
        return hasDwg;
    }

    public void setHasDwg(String hasDwg) {
        this.hasDwg = hasDwg;
    }

    public String getSortNo() {
        return sortNo;
    }

    public void setSortNo(String sortNo) {
        this.sortNo = sortNo;
    }

    public Integer getDuePaperCount() {
        return duePaperCount;
    }

    public void setDuePaperCount(Integer duePaperCount) {
        this.duePaperCount = duePaperCount;
    }

    public Integer getDueCopyCount() {
        return dueCopyCount;
    }

    public void setDueCopyCount(Integer dueCopyCount) {
        this.dueCopyCount = dueCopyCount;
    }

    public String getMatProp() {
        return matProp;
    }

    public void setMatProp(String matProp) {
        this.matProp = matProp;
    }

    public String getCertinstId() {
        return certinstId;
    }

    public void setCertinstId(String certinstId) {
        this.certinstId = certinstId;
    }

    public String getForminstId() {
        return forminstId;
    }

    public void setForminstId(String forminstId) {
        this.forminstId = forminstId;
    }

    public String getLinkmanInfoId() {
        return linkmanInfoId;
    }

    public void setLinkmanInfoId(String linkmanInfoId) {
        this.linkmanInfoId = linkmanInfoId;
    }

    public String getCertFileList() {
        return certFileList;
    }

    public void setCertFileList(String certFileList) {
        this.certFileList = certFileList;
    }

    public String getCertinstSource() {
        return certinstSource;
    }

    public void setCertinstSource(String certinstSource) {
        this.certinstSource = certinstSource;
    }

    public String getItemVerIds() {
        return itemVerIds;
    }

    public void setItemVerIds(String itemVerIds) {
        this.itemVerIds = itemVerIds;
    }

    public String getCertMatinstId() {
        return certMatinstId;
    }

    public void setCertMatinstId(String certMatinstId) {
        this.certMatinstId = certMatinstId;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getMatCode() {
        return matCode;
    }

    public void setMatCode(String matCode) {
        this.matCode = matCode;
    }

    public String getStoFormId() {
        return stoFormId;
    }

    public void setStoFormId(String stoFormId) {
        this.stoFormId = stoFormId;
    }

    public List<FileListBean> getFileList() {
        return fileList;
    }

    public void setFileList(List<FileListBean> fileList) {
        this.fileList = fileList;
    }

    public List<?> getCertinstList() {
        return certinstList;
    }

    public void setCertinstList(List<?> certinstList) {
        this.certinstList = certinstList;
    }

    public static class FileListBean implements Serializable{
        /**
         * bscAttDir : null
         * bscAttDetail : null
         * bscAttLink : {"detailId":null,"attCode":null,"attName":null,"attSize":null,"attType":null,"attFormat":null,"sortNo":null,"storeType":null,"attPath":null,"isEncrypt":null,"messageDigest":null,"memo1":null,"memo2":null,"memo3":null,"memo4":null,"memo5":null,"memo6":null,"creater":null,"createTime":null,"modifier":null,"modifyTime":null,"attDiskName":null,"encryptClass":null,"isRelativePath":null,"dirId":null,"orgId":null,"StringId":null,"geoStringId":null,"isNeedPdf":null,"isConvertPdf":null,"convertPdfResult":null,"convertPdfTime":null,"linkId":"3a009ec7-1ac1-4450-ae87-6547e0c6fd7c","tableName":"AEA_HI_ITEM_MATINST","recordId":"488ec530-4260-4383-84a8-0646fb336ceb","pkName":"MATINST_ID","linkType":null}
         * fileName : u=2280862038,498367946&fm=27&gp=0.jpg
         * fileSize : 10266
         * fileType : jpg
         * updateTime : 1583976775000
         * orgId : 0368948a-1cdf-4bf8-a828-71d796ba89f6
         * md5Key : null
         * fileId : 7b86ae21-94ae-4648-81ec-d18ed1901e79
         * parentId : null
         * parentName : null
         * orgName : null
         * createrName : 潘锦俊
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
             * StringId : null
             * geoStringId : null
             * isNeedPdf : null
             * isConvertPdf : null
             * convertPdfResult : null
             * convertPdfTime : null
             * linkId : 3a009ec7-1ac1-4450-ae87-6547e0c6fd7c
             * tableName : AEA_HI_ITEM_MATINST
             * recordId : 488ec530-4260-4383-84a8-0646fb336ceb
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
            private String StringId;
            private String geoStringId;
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

            public String getStringId() {
                return StringId;
            }

            public void setStringId(String StringId) {
                this.StringId = StringId;
            }

            public String getGeoStringId() {
                return geoStringId;
            }

            public void setGeoStringId(String geoStringId) {
                this.geoStringId = geoStringId;
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
