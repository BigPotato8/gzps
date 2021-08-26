package com.augurit.agmobile.agwater5.gcjs.eventlist.model;

import java.io.Serializable;

/**
 * @description 材料bean
 * @date: $date$ $time$
 * @author: xieruibin
 */
@Deprecated
public class MaterialBean implements Serializable {


    /**
     * matId : null
     * matName : 孩子出生证明
     * matFrom : null
     * fileType : mat
     * realPaperCount : 1
     * realCopyCount : 1
     * attCount : null
     * paperMatinstId : ec9ab16a-9a72-4cf5-ad7b-5cc7d89ee6d6
     * copyMatinstId : 85724dc5-c525-423b-a8f2-c0ad48a7e13e
     * attMatinstId : null
     * hasDwg : null
     */

    private String matId;
    private String matName;
    private String matFrom;
    private String fileType;
    private int realPaperCount;
    private int realCopyCount;
    private String attCount;
    private String paperMatinstId;
    private String copyMatinstId;
    private String hasDwg;
    private String attMatinstId;

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

    public int getRealPaperCount() {
        return realPaperCount;
    }

    public void setRealPaperCount(int realPaperCount) {
        this.realPaperCount = realPaperCount;
    }

    public int getRealCopyCount() {
        return realCopyCount;
    }

    public void setRealCopyCount(int realCopyCount) {
        this.realCopyCount = realCopyCount;
    }

    public String getAttCount() {
        return attCount;
    }

    public void setAttCount(String attCount) {
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
}
