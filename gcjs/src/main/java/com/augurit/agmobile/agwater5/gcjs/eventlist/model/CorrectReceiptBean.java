package com.augurit.agmobile.agwater5.gcjs.eventlist.model;

import java.util.List;

public class CorrectReceiptBean {

    /**
     * correctReceiptId : null
     * receiptInstrumentId : null
     * correctId : 21165ba5657f40dba62893eae8e4568c
     * receiptInstrumentName : 申请材料补正告知书
     * instrumentNo : CLBZ20210805001201
     * applyUnitName : 梁少媚
     * unitIdNo : 440681197808210268
     * projName : app测试1
     * projCode : FS-2011-440604010-01-01-005445
     * unitLinkPhone : 13929106199
     * unitAdress : null
     * legalRepresentativeName : null
     * legalRepresentativeIdNo : null
     * linkmanName : 梁少媚
     * linkmanIdNo : 440681197808210268
     * linkmanPhone : 13929106199
     * applyTime : 1628129652000
     * applyBusinessName : 新增取水许可
     * legislativeAuthority : null
     * correctMaterialListJsonString : [{"attCount":0,"matName":"取水许可申请书","opinion":"123","sortNum":1}]
     * correctTimeRange : 20个工作日
     * consultName : 程飞展
     * consultPhone : null
     * signTime : null
     * detailId : null
     * totalPage : null
     * isDeleted : null
     * creater : null
     * createTime : null
     * modifier : null
     * modifyTime : null
     * applyinstCode : 202108050012
     * applyinstId : 435c62e7-9cb1-40e0-a761-03ecbe94b41e
     * iteminstId : 6fc9d5c4-f33b-4b52-a88a-a5a6f1ab521d
     * matOpinionVos : [{"sortNum":1,"matName":"取水许可申请书","opinion":"123","attCount":0}]
     * isMulti : null
     * generateAttFileFlag : null
     */

    private String correctReceiptId;
    private String receiptInstrumentId;
    private String correctId;
    private String receiptInstrumentName;
    private String instrumentNo;
    private String applyUnitName;
    private String unitIdNo;
    private String projName;
    private String projCode;
    private String unitLinkPhone;
    private String unitAdress;
    private String legalRepresentativeName;
    private String legalRepresentativeIdNo;
    private String linkmanName;
    private String linkmanIdNo;
    private String linkmanPhone;
    private long applyTime;
    private String applyBusinessName;
    private String legislativeAuthority;
//    private String correctMaterialListJsonString;
    private String correctTimeRange;
    private String consultName;
    private String consultPhone;
    private String signTime;
    private String detailId;
    private String totalPage;
    private String isDeleted;
    private String creater;
    private String createTime;
    private String modifier;
    private String modifyTime;
    private String applyinstCode;
    private String applyinstId;
    private String iteminstId;
    private String isMulti;
    private String generateAttFileFlag;
    private List<MatOpinionVosBean> matOpinionVos;
//    private List<MaterialListJson>correctMaterialListJsonString;
    private String correctMaterialListJsonString;

    public class MaterialListJson{

//[{"attCount":0,"matName":"取水许可申请书","opinion":"123","sortNum":1}]
        private int attCount;
        private String matName;
        private String opinion;
        private int sortNum;

        public int getAttCount() {
            return attCount;
        }

        public void setAttCount(int attCount) {
            this.attCount = attCount;
        }

        public String getMatName() {
            return matName;
        }

        public void setMatName(String matName) {
            this.matName = matName;
        }

        public String getOpinion() {
            return opinion;
        }

        public void setOpinion(String opinion) {
            this.opinion = opinion;
        }

        public int getSortNum() {
            return sortNum;
        }

        public void setSortNum(int sortNum) {
            this.sortNum = sortNum;
        }
    }

    public String getCorrectReceiptId() {
        return correctReceiptId;
    }

    public void setCorrectReceiptId(String correctReceiptId) {
        this.correctReceiptId = correctReceiptId;
    }

    public String getReceiptInstrumentId() {
        return receiptInstrumentId;
    }

    public void setReceiptInstrumentId(String receiptInstrumentId) {
        this.receiptInstrumentId = receiptInstrumentId;
    }

    public String getCorrectId() {
        return correctId;
    }

    public void setCorrectId(String correctId) {
        this.correctId = correctId;
    }

    public String getReceiptInstrumentName() {
        return receiptInstrumentName;
    }

    public void setReceiptInstrumentName(String receiptInstrumentName) {
        this.receiptInstrumentName = receiptInstrumentName;
    }

    public String getInstrumentNo() {
        return instrumentNo;
    }

    public void setInstrumentNo(String instrumentNo) {
        this.instrumentNo = instrumentNo;
    }

    public String getApplyUnitName() {
        return applyUnitName;
    }

    public void setApplyUnitName(String applyUnitName) {
        this.applyUnitName = applyUnitName;
    }

    public String getUnitIdNo() {
        return unitIdNo;
    }

    public void setUnitIdNo(String unitIdNo) {
        this.unitIdNo = unitIdNo;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getProjCode() {
        return projCode;
    }

    public void setProjCode(String projCode) {
        this.projCode = projCode;
    }

    public String getUnitLinkPhone() {
        return unitLinkPhone;
    }

    public void setUnitLinkPhone(String unitLinkPhone) {
        this.unitLinkPhone = unitLinkPhone;
    }

    public String getUnitAdress() {
        return unitAdress;
    }

    public void setUnitAdress(String unitAdress) {
        this.unitAdress = unitAdress;
    }

    public String getLegalRepresentativeName() {
        return legalRepresentativeName;
    }

    public void setLegalRepresentativeName(String legalRepresentativeName) {
        this.legalRepresentativeName = legalRepresentativeName;
    }

    public String getLegalRepresentativeIdNo() {
        return legalRepresentativeIdNo;
    }

    public void setLegalRepresentativeIdNo(String legalRepresentativeIdNo) {
        this.legalRepresentativeIdNo = legalRepresentativeIdNo;
    }

    public String getLinkmanName() {
        return linkmanName;
    }

    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName;
    }

    public String getLinkmanIdNo() {
        return linkmanIdNo;
    }

    public void setLinkmanIdNo(String linkmanIdNo) {
        this.linkmanIdNo = linkmanIdNo;
    }

    public String getLinkmanPhone() {
        return linkmanPhone;
    }

    public void setLinkmanPhone(String linkmanPhone) {
        this.linkmanPhone = linkmanPhone;
    }

    public long getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(long applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyBusinessName() {
        return applyBusinessName;
    }

    public void setApplyBusinessName(String applyBusinessName) {
        this.applyBusinessName = applyBusinessName;
    }

    public String getLegislativeAuthority() {
        return legislativeAuthority;
    }

    public void setLegislativeAuthority(String legislativeAuthority) {
        this.legislativeAuthority = legislativeAuthority;
    }

    public String getCorrectMaterialListJsonString() {
        return correctMaterialListJsonString;
    }

    public void setCorrectMaterialListJsonString(String correctMaterialListJsonString) {
        this.correctMaterialListJsonString = correctMaterialListJsonString;
    }
//    public List<MaterialListJson> getCorrectMaterialListJsonString() {
//        return correctMaterialListJsonString;
//    }
//
//    public void setCorrectMaterialListJsonString(List<MaterialListJson> correctMaterialListJsonString) {
//        this.correctMaterialListJsonString = correctMaterialListJsonString;
//    }
//    public String getCorrectMaterialListJsonString() {
//        return correctMaterialListJsonString;
//    }
//
//    public void setCorrectMaterialListJsonString(String correctMaterialListJsonString) {
//        this.correctMaterialListJsonString = correctMaterialListJsonString;
//    }

    public String getCorrectTimeRange() {
        return correctTimeRange;
    }

    public void setCorrectTimeRange(String correctTimeRange) {
        this.correctTimeRange = correctTimeRange;
    }

    public String getConsultName() {
        return consultName;
    }

    public void setConsultName(String consultName) {
        this.consultName = consultName;
    }

    public String getConsultPhone() {
        return consultPhone;
    }

    public void setConsultPhone(String consultPhone) {
        this.consultPhone = consultPhone;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getApplyinstCode() {
        return applyinstCode;
    }

    public void setApplyinstCode(String applyinstCode) {
        this.applyinstCode = applyinstCode;
    }

    public String getApplyinstId() {
        return applyinstId;
    }

    public void setApplyinstId(String applyinstId) {
        this.applyinstId = applyinstId;
    }

    public String getIteminstId() {
        return iteminstId;
    }

    public void setIteminstId(String iteminstId) {
        this.iteminstId = iteminstId;
    }

    public String getIsMulti() {
        return isMulti;
    }

    public void setIsMulti(String isMulti) {
        this.isMulti = isMulti;
    }

    public String getGenerateAttFileFlag() {
        return generateAttFileFlag;
    }

    public void setGenerateAttFileFlag(String generateAttFileFlag) {
        this.generateAttFileFlag = generateAttFileFlag;
    }

    public List<MatOpinionVosBean> getMatOpinionVos() {
        return matOpinionVos;
    }

    public void setMatOpinionVos(List<MatOpinionVosBean> matOpinionVos) {
        this.matOpinionVos = matOpinionVos;
    }

    public static class MatOpinionVosBean {
        /**
         * sortNum : 1
         * matName : 取水许可申请书
         * opinion : 123
         * attCount : 0
         */

        private int sortNum;
        private String matName;
        private String opinion;
        private int attCount;

        public int getSortNum() {
            return sortNum;
        }

        public void setSortNum(int sortNum) {
            this.sortNum = sortNum;
        }

        public String getMatName() {
            return matName;
        }

        public void setMatName(String matName) {
            this.matName = matName;
        }

        public String getOpinion() {
            return opinion;
        }

        public void setOpinion(String opinion) {
            this.opinion = opinion;
        }

        public int getAttCount() {
            return attCount;
        }

        public void setAttCount(int attCount) {
            this.attCount = attCount;
        }
    }
}
