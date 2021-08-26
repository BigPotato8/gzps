package com.augurit.agmobile.agwater5.gcjs_public.personspace.mymaterial;

import java.io.Serializable;
import java.util.List;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.personspace.mymaterial
 * Created by sdb on 2019/10/28  10:47.
 * Desc：
 */
public class MaterialBean {

    /**
     * content : [{"attCount":0,"attFormList":[{"attCode":"","attDiskName":"","attFormat":"","attName":"","attPath":"","attSize":"","attType":"","attachmentContentChange":true,"base64":"","convertPdfResult":"","convertPdfTime":"","createTime":"","creater":"","createrName":"","curAttachmentList":[{"attCode":"","attDiskName":"","attFormat":"","attName":"","attPath":"","attSize":"","attType":"","attachmentContentChange":true,"base64":"","convertPdfResult":"","convertPdfTime":"","createTime":"","creater":"","createrName":"","curAttachmentList":[{}],"detailId":"","dirId":"","encryptClass":"","existFile":"","geoObjectId":"","isConvertPdf":"","isEncrypt":"","isNeedPdf":"","isRelativePath":"","linkId":"","linkType":"","memo1":"","memo2":"","memo3":"","memo4":"","memo5":"","memo6":"","messageDigest":"","modifier":"","modifyTime":"","newAttName":"","newAttPath":"","objectId":"","orgId":"","orgName":"","pkName":"","printTplPath":"","recordId":"","redHeadPath":"","sortNo":0,"storeType":"","tableName":"","templateCode":"","zhengwenPath":""}],"detailId":"","dirId":"","encryptClass":"","existFile":"","geoObjectId":"","isConvertPdf":"","isEncrypt":"","isNeedPdf":"","isRelativePath":"","linkId":"","linkType":"","memo1":"","memo2":"","memo3":"","memo4":"","memo5":"","memo6":"","messageDigest":"","modifier":"","modifyTime":"","newAttName":"","newAttPath":"","objectId":"","orgId":"","orgName":"","pkName":"","printTplPath":"","recordId":"","redHeadPath":"","sortNo":0,"storeType":"","tableName":"","templateCode":"","zhengwenPath":""}],"createTime":"","creater":"","detailId":"","dirId":"","isMakeUp":"","isOfficialDoc":"","linkmanInfoId":"","matId":"","matinstCode":"","matinstId":"","matinstName":"","matinstSource":"","memo":"","modifier":"","modifyTime":"","officialDocDueDate":"","officialDocNo":"","officialDocPublishDate":"","officialDocTitle":"","orgId":"","orgName":"","orgShortName":"","projInfoId":"","realCopyCount":0,"realPaperCount":0,"rootOrgId":"","unitInfoId":""}]
     * message :
     * success : true
     */

    private String message;
    private boolean success;
    private List<ContentBean> content;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean implements Serializable {
        /**
         * attCount : 0
         * attFormList : [{"attCode":"","attDiskName":"","attFormat":"","attName":"","attPath":"","attSize":"","attType":"","attachmentContentChange":true,"base64":"","convertPdfResult":"","convertPdfTime":"","createTime":"","creater":"","createrName":"","curAttachmentList":[{"attCode":"","attDiskName":"","attFormat":"","attName":"","attPath":"","attSize":"","attType":"","attachmentContentChange":true,"base64":"","convertPdfResult":"","convertPdfTime":"","createTime":"","creater":"","createrName":"","curAttachmentList":[{}],"detailId":"","dirId":"","encryptClass":"","existFile":"","geoObjectId":"","isConvertPdf":"","isEncrypt":"","isNeedPdf":"","isRelativePath":"","linkId":"","linkType":"","memo1":"","memo2":"","memo3":"","memo4":"","memo5":"","memo6":"","messageDigest":"","modifier":"","modifyTime":"","newAttName":"","newAttPath":"","objectId":"","orgId":"","orgName":"","pkName":"","printTplPath":"","recordId":"","redHeadPath":"","sortNo":0,"storeType":"","tableName":"","templateCode":"","zhengwenPath":""}],"detailId":"","dirId":"","encryptClass":"","existFile":"","geoObjectId":"","isConvertPdf":"","isEncrypt":"","isNeedPdf":"","isRelativePath":"","linkId":"","linkType":"","memo1":"","memo2":"","memo3":"","memo4":"","memo5":"","memo6":"","messageDigest":"","modifier":"","modifyTime":"","newAttName":"","newAttPath":"","objectId":"","orgId":"","orgName":"","pkName":"","printTplPath":"","recordId":"","redHeadPath":"","sortNo":0,"storeType":"","tableName":"","templateCode":"","zhengwenPath":""}]
         * createTime :
         * creater :
         * detailId :
         * dirId :
         * isMakeUp :
         * isOfficialDoc :
         * linkmanInfoId :
         * matId :
         * matinstCode :
         * matinstId :
         * matinstName :
         * matinstSource :
         * memo :
         * modifier :
         * modifyTime :
         * officialDocDueDate :
         * officialDocNo :
         * officialDocPublishDate :
         * officialDocTitle :
         * orgId :
         * orgName :
         * orgShortName :
         * projInfoId :
         * realCopyCount : 0
         * realPaperCount : 0
         * rootOrgId :
         * unitInfoId :
         */

        private int attCount;
        private String createTime;
        private String creater;
        private String detailId;
        private String dirId;
        private String isMakeUp;
        private String isOfficialDoc;
        private String linkmanInfoId;
        private String matId;
        private String matinstCode;
        private String matinstId;
        private String matinstName;
        private String matinstSource;
        private String memo;
        private String modifier;
        private String modifyTime;
        private String officialDocDueDate;
        private String officialDocNo;
        private String officialDocPublishDate;
        private String officialDocTitle;
        private String orgId;
        private String orgName;
        private String orgShortName;
        private String projInfoId;
        private int realCopyCount;
        private int realPaperCount;
        private String rootOrgId;
        private String unitInfoId;
        private List<AttFormListBean> attFormList;

        public int getAttCount() {
            return attCount;
        }

        public void setAttCount(int attCount) {
            this.attCount = attCount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCreater() {
            return creater;
        }

        public void setCreater(String creater) {
            this.creater = creater;
        }

        public String getDetailId() {
            return detailId;
        }

        public void setDetailId(String detailId) {
            this.detailId = detailId;
        }

        public String getDirId() {
            return dirId;
        }

        public void setDirId(String dirId) {
            this.dirId = dirId;
        }

        public String getIsMakeUp() {
            return isMakeUp;
        }

        public void setIsMakeUp(String isMakeUp) {
            this.isMakeUp = isMakeUp;
        }

        public String getIsOfficialDoc() {
            return isOfficialDoc;
        }

        public void setIsOfficialDoc(String isOfficialDoc) {
            this.isOfficialDoc = isOfficialDoc;
        }

        public String getLinkmanInfoId() {
            return linkmanInfoId;
        }

        public void setLinkmanInfoId(String linkmanInfoId) {
            this.linkmanInfoId = linkmanInfoId;
        }

        public String getMatId() {
            return matId;
        }

        public void setMatId(String matId) {
            this.matId = matId;
        }

        public String getMatinstCode() {
            return matinstCode;
        }

        public void setMatinstCode(String matinstCode) {
            this.matinstCode = matinstCode;
        }

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

        public String getMatinstSource() {
            return matinstSource;
        }

        public void setMatinstSource(String matinstSource) {
            this.matinstSource = matinstSource;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
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

        public String getOfficialDocDueDate() {
            return officialDocDueDate;
        }

        public void setOfficialDocDueDate(String officialDocDueDate) {
            this.officialDocDueDate = officialDocDueDate;
        }

        public String getOfficialDocNo() {
            return officialDocNo;
        }

        public void setOfficialDocNo(String officialDocNo) {
            this.officialDocNo = officialDocNo;
        }

        public String getOfficialDocPublishDate() {
            return officialDocPublishDate;
        }

        public void setOfficialDocPublishDate(String officialDocPublishDate) {
            this.officialDocPublishDate = officialDocPublishDate;
        }

        public String getOfficialDocTitle() {
            return officialDocTitle;
        }

        public void setOfficialDocTitle(String officialDocTitle) {
            this.officialDocTitle = officialDocTitle;
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

        public String getOrgShortName() {
            return orgShortName;
        }

        public void setOrgShortName(String orgShortName) {
            this.orgShortName = orgShortName;
        }

        public String getProjInfoId() {
            return projInfoId;
        }

        public void setProjInfoId(String projInfoId) {
            this.projInfoId = projInfoId;
        }

        public int getRealCopyCount() {
            return realCopyCount;
        }

        public void setRealCopyCount(int realCopyCount) {
            this.realCopyCount = realCopyCount;
        }

        public int getRealPaperCount() {
            return realPaperCount;
        }

        public void setRealPaperCount(int realPaperCount) {
            this.realPaperCount = realPaperCount;
        }

        public String getRootOrgId() {
            return rootOrgId;
        }

        public void setRootOrgId(String rootOrgId) {
            this.rootOrgId = rootOrgId;
        }

        public String getUnitInfoId() {
            return unitInfoId;
        }

        public void setUnitInfoId(String unitInfoId) {
            this.unitInfoId = unitInfoId;
        }

        public List<AttFormListBean> getAttFormList() {
            return attFormList;
        }

        public void setAttFormList(List<AttFormListBean> attFormList) {
            this.attFormList = attFormList;
        }

        public static class AttFormListBean implements Serializable{
            /**
             * attCode :
             * attDiskName :
             * attFormat :
             * attName :
             * attPath :
             * attSize :
             * attType :
             * attachmentContentChange : true
             * base64 :
             * convertPdfResult :
             * convertPdfTime :
             * createTime :
             * creater :
             * createrName :
             * curAttachmentList : [{"attCode":"","attDiskName":"","attFormat":"","attName":"","attPath":"","attSize":"","attType":"","attachmentContentChange":true,"base64":"","convertPdfResult":"","convertPdfTime":"","createTime":"","creater":"","createrName":"","curAttachmentList":[{}],"detailId":"","dirId":"","encryptClass":"","existFile":"","geoObjectId":"","isConvertPdf":"","isEncrypt":"","isNeedPdf":"","isRelativePath":"","linkId":"","linkType":"","memo1":"","memo2":"","memo3":"","memo4":"","memo5":"","memo6":"","messageDigest":"","modifier":"","modifyTime":"","newAttName":"","newAttPath":"","objectId":"","orgId":"","orgName":"","pkName":"","printTplPath":"","recordId":"","redHeadPath":"","sortNo":0,"storeType":"","tableName":"","templateCode":"","zhengwenPath":""}]
             * detailId :
             * dirId :
             * encryptClass :
             * existFile :
             * geoObjectId :
             * isConvertPdf :
             * isEncrypt :
             * isNeedPdf :
             * isRelativePath :
             * linkId :
             * linkType :
             * memo1 :
             * memo2 :
             * memo3 :
             * memo4 :
             * memo5 :
             * memo6 :
             * messageDigest :
             * modifier :
             * modifyTime :
             * newAttName :
             * newAttPath :
             * objectId :
             * orgId :
             * orgName :
             * pkName :
             * printTplPath :
             * recordId :
             * redHeadPath :
             * sortNo : 0
             * storeType :
             * tableName :
             * templateCode :
             * zhengwenPath :
             */

            private String attCode;
            private String attDiskName;
            private String attFormat;
            private String attName;
            private String attPath;
            private String attSize;
            private String attType;
            private boolean attachmentContentChange;
            private String base64;
            private String convertPdfResult;
            private String convertPdfTime;
            private String createTime;
            private String creater;
            private String createrName;
            private String detailId;
            private String dirId;
            private String encryptClass;
            private String existFile;
            private String geoObjectId;
            private String isConvertPdf;
            private String isEncrypt;
            private String isNeedPdf;
            private String isRelativePath;
            private String linkId;
            private String linkType;
            private String memo1;
            private String memo2;
            private String memo3;
            private String memo4;
            private String memo5;
            private String memo6;
            private String messageDigest;
            private String modifier;
            private String modifyTime;
            private String newAttName;
            private String newAttPath;
            private String objectId;
            private String orgId;
            private String orgName;
            private String pkName;
            private String printTplPath;
            private String recordId;
            private String redHeadPath;
            private int sortNo;
            private String storeType;
            private String tableName;
            private String templateCode;
            private String zhengwenPath;
            private List<CurAttachmentListBeanX> curAttachmentList;

            public String getAttCode() {
                return attCode;
            }

            public void setAttCode(String attCode) {
                this.attCode = attCode;
            }

            public String getAttDiskName() {
                return attDiskName;
            }

            public void setAttDiskName(String attDiskName) {
                this.attDiskName = attDiskName;
            }

            public String getAttFormat() {
                return attFormat;
            }

            public void setAttFormat(String attFormat) {
                this.attFormat = attFormat;
            }

            public String getAttName() {
                return attName;
            }

            public void setAttName(String attName) {
                this.attName = attName;
            }

            public String getAttPath() {
                return attPath;
            }

            public void setAttPath(String attPath) {
                this.attPath = attPath;
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

            public boolean isAttachmentContentChange() {
                return attachmentContentChange;
            }

            public void setAttachmentContentChange(boolean attachmentContentChange) {
                this.attachmentContentChange = attachmentContentChange;
            }

            public String getBase64() {
                return base64;
            }

            public void setBase64(String base64) {
                this.base64 = base64;
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

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCreater() {
                return creater;
            }

            public void setCreater(String creater) {
                this.creater = creater;
            }

            public String getCreaterName() {
                return createrName;
            }

            public void setCreaterName(String createrName) {
                this.createrName = createrName;
            }

            public String getDetailId() {
                return detailId;
            }

            public void setDetailId(String detailId) {
                this.detailId = detailId;
            }

            public String getDirId() {
                return dirId;
            }

            public void setDirId(String dirId) {
                this.dirId = dirId;
            }

            public String getEncryptClass() {
                return encryptClass;
            }

            public void setEncryptClass(String encryptClass) {
                this.encryptClass = encryptClass;
            }

            public String getExistFile() {
                return existFile;
            }

            public void setExistFile(String existFile) {
                this.existFile = existFile;
            }

            public String getGeoObjectId() {
                return geoObjectId;
            }

            public void setGeoObjectId(String geoObjectId) {
                this.geoObjectId = geoObjectId;
            }

            public String getIsConvertPdf() {
                return isConvertPdf;
            }

            public void setIsConvertPdf(String isConvertPdf) {
                this.isConvertPdf = isConvertPdf;
            }

            public String getIsEncrypt() {
                return isEncrypt;
            }

            public void setIsEncrypt(String isEncrypt) {
                this.isEncrypt = isEncrypt;
            }

            public String getIsNeedPdf() {
                return isNeedPdf;
            }

            public void setIsNeedPdf(String isNeedPdf) {
                this.isNeedPdf = isNeedPdf;
            }

            public String getIsRelativePath() {
                return isRelativePath;
            }

            public void setIsRelativePath(String isRelativePath) {
                this.isRelativePath = isRelativePath;
            }

            public String getLinkId() {
                return linkId;
            }

            public void setLinkId(String linkId) {
                this.linkId = linkId;
            }

            public String getLinkType() {
                return linkType;
            }

            public void setLinkType(String linkType) {
                this.linkType = linkType;
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

            public String getMessageDigest() {
                return messageDigest;
            }

            public void setMessageDigest(String messageDigest) {
                this.messageDigest = messageDigest;
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

            public String getNewAttName() {
                return newAttName;
            }

            public void setNewAttName(String newAttName) {
                this.newAttName = newAttName;
            }

            public String getNewAttPath() {
                return newAttPath;
            }

            public void setNewAttPath(String newAttPath) {
                this.newAttPath = newAttPath;
            }

            public String getObjectId() {
                return objectId;
            }

            public void setObjectId(String objectId) {
                this.objectId = objectId;
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

            public String getPkName() {
                return pkName;
            }

            public void setPkName(String pkName) {
                this.pkName = pkName;
            }

            public String getPrintTplPath() {
                return printTplPath;
            }

            public void setPrintTplPath(String printTplPath) {
                this.printTplPath = printTplPath;
            }

            public String getRecordId() {
                return recordId;
            }

            public void setRecordId(String recordId) {
                this.recordId = recordId;
            }

            public String getRedHeadPath() {
                return redHeadPath;
            }

            public void setRedHeadPath(String redHeadPath) {
                this.redHeadPath = redHeadPath;
            }

            public int getSortNo() {
                return sortNo;
            }

            public void setSortNo(int sortNo) {
                this.sortNo = sortNo;
            }

            public String getStoreType() {
                return storeType;
            }

            public void setStoreType(String storeType) {
                this.storeType = storeType;
            }

            public String getTableName() {
                return tableName;
            }

            public void setTableName(String tableName) {
                this.tableName = tableName;
            }

            public String getTemplateCode() {
                return templateCode;
            }

            public void setTemplateCode(String templateCode) {
                this.templateCode = templateCode;
            }

            public String getZhengwenPath() {
                return zhengwenPath;
            }

            public void setZhengwenPath(String zhengwenPath) {
                this.zhengwenPath = zhengwenPath;
            }

            public List<CurAttachmentListBeanX> getCurAttachmentList() {
                return curAttachmentList;
            }

            public void setCurAttachmentList(List<CurAttachmentListBeanX> curAttachmentList) {
                this.curAttachmentList = curAttachmentList;
            }

            public static class CurAttachmentListBeanX implements Serializable{
                /**
                 * attCode :
                 * attDiskName :
                 * attFormat :
                 * attName :
                 * attPath :
                 * attSize :
                 * attType :
                 * attachmentContentChange : true
                 * base64 :
                 * convertPdfResult :
                 * convertPdfTime :
                 * createTime :
                 * creater :
                 * createrName :
                 * curAttachmentList : [{}]
                 * detailId :
                 * dirId :
                 * encryptClass :
                 * existFile :
                 * geoObjectId :
                 * isConvertPdf :
                 * isEncrypt :
                 * isNeedPdf :
                 * isRelativePath :
                 * linkId :
                 * linkType :
                 * memo1 :
                 * memo2 :
                 * memo3 :
                 * memo4 :
                 * memo5 :
                 * memo6 :
                 * messageDigest :
                 * modifier :
                 * modifyTime :
                 * newAttName :
                 * newAttPath :
                 * objectId :
                 * orgId :
                 * orgName :
                 * pkName :
                 * printTplPath :
                 * recordId :
                 * redHeadPath :
                 * sortNo : 0
                 * storeType :
                 * tableName :
                 * templateCode :
                 * zhengwenPath :
                 */

                private String attCode;
                private String attDiskName;
                private String attFormat;
                private String attName;
                private String attPath;
                private String attSize;
                private String attType;
                private boolean attachmentContentChange;
                private String base64;
                private String convertPdfResult;
                private String convertPdfTime;
                private String createTime;
                private String creater;
                private String createrName;
                private String detailId;
                private String dirId;
                private String encryptClass;
                private String existFile;
                private String geoObjectId;
                private String isConvertPdf;
                private String isEncrypt;
                private String isNeedPdf;
                private String isRelativePath;
                private String linkId;
                private String linkType;
                private String memo1;
                private String memo2;
                private String memo3;
                private String memo4;
                private String memo5;
                private String memo6;
                private String messageDigest;
                private String modifier;
                private String modifyTime;
                private String newAttName;
                private String newAttPath;
                private String objectId;
                private String orgId;
                private String orgName;
                private String pkName;
                private String printTplPath;
                private String recordId;
                private String redHeadPath;
                private int sortNo;
                private String storeType;
                private String tableName;
                private String templateCode;
                private String zhengwenPath;
                private List<CurAttachmentListBean> curAttachmentList;

                public String getAttCode() {
                    return attCode;
                }

                public void setAttCode(String attCode) {
                    this.attCode = attCode;
                }

                public String getAttDiskName() {
                    return attDiskName;
                }

                public void setAttDiskName(String attDiskName) {
                    this.attDiskName = attDiskName;
                }

                public String getAttFormat() {
                    return attFormat;
                }

                public void setAttFormat(String attFormat) {
                    this.attFormat = attFormat;
                }

                public String getAttName() {
                    return attName;
                }

                public void setAttName(String attName) {
                    this.attName = attName;
                }

                public String getAttPath() {
                    return attPath;
                }

                public void setAttPath(String attPath) {
                    this.attPath = attPath;
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

                public boolean isAttachmentContentChange() {
                    return attachmentContentChange;
                }

                public void setAttachmentContentChange(boolean attachmentContentChange) {
                    this.attachmentContentChange = attachmentContentChange;
                }

                public String getBase64() {
                    return base64;
                }

                public void setBase64(String base64) {
                    this.base64 = base64;
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

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public String getCreater() {
                    return creater;
                }

                public void setCreater(String creater) {
                    this.creater = creater;
                }

                public String getCreaterName() {
                    return createrName;
                }

                public void setCreaterName(String createrName) {
                    this.createrName = createrName;
                }

                public String getDetailId() {
                    return detailId;
                }

                public void setDetailId(String detailId) {
                    this.detailId = detailId;
                }

                public String getDirId() {
                    return dirId;
                }

                public void setDirId(String dirId) {
                    this.dirId = dirId;
                }

                public String getEncryptClass() {
                    return encryptClass;
                }

                public void setEncryptClass(String encryptClass) {
                    this.encryptClass = encryptClass;
                }

                public String getExistFile() {
                    return existFile;
                }

                public void setExistFile(String existFile) {
                    this.existFile = existFile;
                }

                public String getGeoObjectId() {
                    return geoObjectId;
                }

                public void setGeoObjectId(String geoObjectId) {
                    this.geoObjectId = geoObjectId;
                }

                public String getIsConvertPdf() {
                    return isConvertPdf;
                }

                public void setIsConvertPdf(String isConvertPdf) {
                    this.isConvertPdf = isConvertPdf;
                }

                public String getIsEncrypt() {
                    return isEncrypt;
                }

                public void setIsEncrypt(String isEncrypt) {
                    this.isEncrypt = isEncrypt;
                }

                public String getIsNeedPdf() {
                    return isNeedPdf;
                }

                public void setIsNeedPdf(String isNeedPdf) {
                    this.isNeedPdf = isNeedPdf;
                }

                public String getIsRelativePath() {
                    return isRelativePath;
                }

                public void setIsRelativePath(String isRelativePath) {
                    this.isRelativePath = isRelativePath;
                }

                public String getLinkId() {
                    return linkId;
                }

                public void setLinkId(String linkId) {
                    this.linkId = linkId;
                }

                public String getLinkType() {
                    return linkType;
                }

                public void setLinkType(String linkType) {
                    this.linkType = linkType;
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

                public String getMessageDigest() {
                    return messageDigest;
                }

                public void setMessageDigest(String messageDigest) {
                    this.messageDigest = messageDigest;
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

                public String getNewAttName() {
                    return newAttName;
                }

                public void setNewAttName(String newAttName) {
                    this.newAttName = newAttName;
                }

                public String getNewAttPath() {
                    return newAttPath;
                }

                public void setNewAttPath(String newAttPath) {
                    this.newAttPath = newAttPath;
                }

                public String getObjectId() {
                    return objectId;
                }

                public void setObjectId(String objectId) {
                    this.objectId = objectId;
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

                public String getPkName() {
                    return pkName;
                }

                public void setPkName(String pkName) {
                    this.pkName = pkName;
                }

                public String getPrintTplPath() {
                    return printTplPath;
                }

                public void setPrintTplPath(String printTplPath) {
                    this.printTplPath = printTplPath;
                }

                public String getRecordId() {
                    return recordId;
                }

                public void setRecordId(String recordId) {
                    this.recordId = recordId;
                }

                public String getRedHeadPath() {
                    return redHeadPath;
                }

                public void setRedHeadPath(String redHeadPath) {
                    this.redHeadPath = redHeadPath;
                }

                public int getSortNo() {
                    return sortNo;
                }

                public void setSortNo(int sortNo) {
                    this.sortNo = sortNo;
                }

                public String getStoreType() {
                    return storeType;
                }

                public void setStoreType(String storeType) {
                    this.storeType = storeType;
                }

                public String getTableName() {
                    return tableName;
                }

                public void setTableName(String tableName) {
                    this.tableName = tableName;
                }

                public String getTemplateCode() {
                    return templateCode;
                }

                public void setTemplateCode(String templateCode) {
                    this.templateCode = templateCode;
                }

                public String getZhengwenPath() {
                    return zhengwenPath;
                }

                public void setZhengwenPath(String zhengwenPath) {
                    this.zhengwenPath = zhengwenPath;
                }

                public List<CurAttachmentListBean> getCurAttachmentList() {
                    return curAttachmentList;
                }

                public void setCurAttachmentList(List<CurAttachmentListBean> curAttachmentList) {
                    this.curAttachmentList = curAttachmentList;
                }

                public static class CurAttachmentListBean implements Serializable{
                }
            }
        }
    }
}
