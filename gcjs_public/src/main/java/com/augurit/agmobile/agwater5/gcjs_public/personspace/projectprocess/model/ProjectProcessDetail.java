package com.augurit.agmobile.agwater5.gcjs_public.personspace.projectprocess.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 创建人 ：panming
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjs.progress
 * @createTime 创建时间 ：2019-11-25
 * @modifyBy 修改人 ：panming
 * @modifyTime 修改时间 ：2019-11-25
 * @modifyMemo 修改备注：
 */
public class ProjectProcessDetail implements Serializable {
//          "projName": "方玲居民光伏第一期",
//          "projInfoId": "03bd8344-ce55-456d-8ae0-dcf688ba9e0b",
//          "regionId": "185",
//          "themeName": "政府投融资不新增用地的房屋建筑类项目",
//          "themeId": "f6c90326-3a55-4fc0-a042-a5c1904adc9b",
//          "themeType": "A00001",
//          "aeaParStages": []

   private  String projName;
   private  String projInfoId;
   private  String regionId;
   private  String themeName;
    private  String themeId;
    private  String themeType;
    private List <ProcessBean> aeaParStages;


    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getProjInfoId() {
        return projInfoId;
    }

    public void setProjInfoId(String projInfoId) {
        this.projInfoId = projInfoId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public String getThemeType() {
        return themeType;
    }

    public void setThemeType(String themeType) {
        this.themeType = themeType;
    }

    public List<ProcessBean> getAeaParStages() {
        return aeaParStages;
    }

    public void setAeaParStages(List<ProcessBean> aeaParStages) {
        this.aeaParStages = aeaParStages;
    }





public class ProcessBean implements Serializable{
//            "stageName": "立项用地规划许可阶段-政府投融资不新增用地的房屋建筑类",
//            "stageId": "5a3a5af8-10e2-4f34-95ca-e9c6a0fc0c74",
//            "stageInstId": null,
//            "coreItemList": [],
//            "parallelItemList": []
// "stateType": "2",
//         "parallelDoneNumber": 0,
//         "coreDoneNumber": 0,
//         "stageRunTime": 0,
//         "stageItemNum": 9,
//         "endProp": "0%"


private String stageName;
private String stageId;
private String stageInstId;
private String stateType;
private String parallelDoneNumber;
private String coreDoneNumber;
private String stageRunTime;
private String endProp;
private List <ProcessItem>parallelItemList;
private List <ProcessItem>coreItemList;

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public String getStageInstId() {
        return stageInstId;
    }

    public void setStageInstId(String stageInstId) {
        this.stageInstId = stageInstId;
    }

    public String getStateType() {
        return stateType;
    }

    public void setStateType(String stateType) {
        this.stateType = stateType;
    }

    public String getParallelDoneNumber() {
        return parallelDoneNumber;
    }

    public void setParallelDoneNumber(String parallelDoneNumber) {
        this.parallelDoneNumber = parallelDoneNumber;
    }

    public String getCoreDoneNumber() {
        return coreDoneNumber;
    }

    public void setCoreDoneNumber(String coreDoneNumber) {
        this.coreDoneNumber = coreDoneNumber;
    }

    public String getStageRunTime() {
        return stageRunTime;
    }

    public void setStageRunTime(String stageRunTime) {
        this.stageRunTime = stageRunTime;
    }

    public String getEndProp() {
        return endProp;
    }

    public void setEndProp(String endProp) {
        this.endProp = endProp;
    }

    public List<ProcessItem> getParallelItemList() {
        return parallelItemList;
    }

    public void setParallelItemList(List<ProcessItem> parallelItemList) {
        this.parallelItemList = parallelItemList;
    }

    public List<ProcessItem> getCoreItemList() {
        return coreItemList;
    }

    public void setCoreItemList(List<ProcessItem> coreItemList) {
        this.coreItemList = coreItemList;

    }



}
    public static class ProcessItem implements  Serializable{
        private String itemVerId;
        private String itemName;
        private String iteminstState;
        private String iteminstRunTime;
        private String dueNum;
        private String iteminstStartTime;
        private String iteminstEndTime;
        private String orgName;
        private String itemStateType;

        public String getItemVerId() {
            return itemVerId;
        }

        public void setItemVerId(String itemVerId) {
            this.itemVerId = itemVerId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getIteminstState() {
            return iteminstState;
        }

        public void setIteminstState(String iteminstState) {
            this.iteminstState = iteminstState;
        }

        public String getIteminstRunTime() {
            return iteminstRunTime;
        }

        public void setIteminstRunTime(String iteminstRunTime) {
            this.iteminstRunTime = iteminstRunTime;
        }

        public String getDueNum() {
            return dueNum;
        }

        public void setDueNum(String dueNum) {
            this.dueNum = dueNum;
        }

        public String getIteminstStartTime() {
            return iteminstStartTime;
        }

        public void setIteminstStartTime(String iteminstStartTime) {
            this.iteminstStartTime = iteminstStartTime;
        }

        public String getIteminstEndTime() {
            return iteminstEndTime;
        }

        public void setIteminstEndTime(String iteminstEndTime) {
            this.iteminstEndTime = iteminstEndTime;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getItemStateType() {
            return itemStateType;
        }

        public void setItemStateType(String itemStateType) {
            this.itemStateType = itemStateType;
        }
    }

//    public class  coreItem{
//        private String itemVerId;
//        private String itemName;
//        private String iteminstState;
//        private String iteminstRunTime;
//        private String dueNum;
//        private String iteminstStartTime;
//        private String iteminstEndTime;
//        private String orgName;
//        private String itemStateType;
//
//        public String getItemVerId() {
//            return itemVerId;
//        }
//
//        public void setItemVerId(String itemVerId) {
//            this.itemVerId = itemVerId;
//        }
//
//        public String getItemName() {
//            return itemName;
//        }
//
//        public void setItemName(String itemName) {
//            this.itemName = itemName;
//        }
//
//        public String getIteminstState() {
//            return iteminstState;
//        }
//
//        public void setIteminstState(String iteminstState) {
//            this.iteminstState = iteminstState;
//        }
//
//        public String getIteminstRunTime() {
//            return iteminstRunTime;
//        }
//
//        public void setIteminstRunTime(String iteminstRunTime) {
//            this.iteminstRunTime = iteminstRunTime;
//        }
//
//        public String getDueNum() {
//            return dueNum;
//        }
//
//        public void setDueNum(String dueNum) {
//            this.dueNum = dueNum;
//        }
//
//        public String getIteminstStartTime() {
//            return iteminstStartTime;
//        }
//
//        public void setIteminstStartTime(String iteminstStartTime) {
//            this.iteminstStartTime = iteminstStartTime;
//        }
//
//        public String getIteminstEndTime() {
//            return iteminstEndTime;
//        }
//
//        public void setIteminstEndTime(String iteminstEndTime) {
//            this.iteminstEndTime = iteminstEndTime;
//        }
//
//        public String getOrgName() {
//            return orgName;
//        }
//
//        public void setOrgName(String orgName) {
//            this.orgName = orgName;
//        }
//
//        public String getItemStateType() {
//            return itemStateType;
//        }
//
//        public void setItemStateType(String itemStateType) {
//            this.itemStateType = itemStateType;
//        }
//    }

}
