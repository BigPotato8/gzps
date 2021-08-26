package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.single.model;


import com.augurit.agmobile.common.lib.model.FileBean;

import java.util.List;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent.single.model
 * Created by sdb on 2019/5/14  18:39.
 * Desc：
 */

public class Situation {
    private List<AeaItemMatsBean> aeaItemMats;
    private List<StateQuestionsBean> stateQuestions;

    public List<AeaItemMatsBean> getAeaItemMats() {
        return aeaItemMats;
    }

    public void setAeaItemMats(List<AeaItemMatsBean> aeaItemMats) {
        this.aeaItemMats = aeaItemMats;
    }

    public List<StateQuestionsBean> getStateQuestions() {
        return stateQuestions;
    }

    public void setStateQuestions(List<StateQuestionsBean> stateQuestions) {
        this.stateQuestions = stateQuestions;
    }

    public static class AeaItemMatsBean {
        /**
         * matId : e15d11cf-fbeb-41a9-811f-b9c73741f507
         * matCode : MAT-C0000003725
         * matName : 城市绿化工程设计方案及工程建设项目附属绿化设计变更方案和图纸
         * isGlobalShare : 1
         * matTypeId : 3
         * receiveMode : null
         * paperMatType : null
         * duePaperType : 2
         * duePaperCount : 1
         * dueCopyType : 0
         * dueCopyCount : 0
         * matFrom : null
         * sampleDoc : null
         * templateDoc : null
         * matRequire : null
         * matBasis : null
         * paperIsRequire : 1
         * sortNo : null
         * matMemo : null
         * isActive : 1
         * isDeleted : 0
         * creater : htry
         * createTime : 2019-03-20 10:25:12
         * modifier : htry
         * modifierTime : 2019-04-10 14:23:25
         * attDirId : null
         * matHolder : null
         * isOfficialDoc : null
         * attIsRequire : 0
         * isCommon : 1
         * reviewKeyPoints : 真实有效
         * reviewSampleDoc : null
         * reviewBasis : null
         * matFromDept : null
         * zcqy : 0
         */

        private String matId;
        private String matCode;
        private String matName;
        private String isGlobalShare;
        private String matTypeId;
        private String receiveMode;
        private String paperMatType;
        private String duePaperType;
        private String duePaperCount;
        private String dueCopyType;
        private String dueCopyCount;
        private String matFrom;
        private String sampleDoc;
        private String templateDoc;
        private String matRequire;
        private String matBasis;
        private String paperIsRequire;
        private String sortNo;
        private String matMemo;
        private String isActive;
        private String isDeleted;
        private String creater;
        private String createTime;
        private String modifier;
        private String modifierTime;
        private String attDirId;
        private String matHolder;
        private String isOfficialDoc;
        private String attIsRequire;
        private String isCommon;
        private String reviewKeyPoints;
        private String reviewSampleDoc;
        private String reviewBasis;
        private String matFromDept;
        private String zcqy;
        private List<FileBean> fileBeans;

        public List<FileBean> getFileBeans() {
            return fileBeans;
        }

        public void setFileBeans(List<FileBean> fileBeans) {
            this.fileBeans = fileBeans;
        }

        public String getMatId() {
            return matId;
        }

        public void setMatId(String matId) {
            this.matId = matId;
        }

        public String getMatCode() {
            return matCode;
        }

        public void setMatCode(String matCode) {
            this.matCode = matCode;
        }

        public String getMatName() {
            return matName;
        }

        public void setMatName(String matName) {
            this.matName = matName;
        }

        public String getIsGlobalShare() {
            return isGlobalShare;
        }

        public void setIsGlobalShare(String isGlobalShare) {
            this.isGlobalShare = isGlobalShare;
        }

        public String getMatTypeId() {
            return matTypeId;
        }

        public void setMatTypeId(String matTypeId) {
            this.matTypeId = matTypeId;
        }

        public String getReceiveMode() {
            return receiveMode;
        }

        public void setReceiveMode(String receiveMode) {
            this.receiveMode = receiveMode;
        }

        public String getPaperMatType() {
            return paperMatType;
        }

        public void setPaperMatType(String paperMatType) {
            this.paperMatType = paperMatType;
        }

        public String getDuePaperType() {
            return duePaperType;
        }

        public void setDuePaperType(String duePaperType) {
            this.duePaperType = duePaperType;
        }

        public String getDuePaperCount() {
            return duePaperCount;
        }

        public void setDuePaperCount(String duePaperCount) {
            this.duePaperCount = duePaperCount;
        }

        public String getDueCopyType() {
            return dueCopyType;
        }

        public void setDueCopyType(String dueCopyType) {
            this.dueCopyType = dueCopyType;
        }

        public String getDueCopyCount() {
            return dueCopyCount;
        }

        public void setDueCopyCount(String dueCopyCount) {
            this.dueCopyCount = dueCopyCount;
        }

        public String getMatFrom() {
            return matFrom;
        }

        public void setMatFrom(String matFrom) {
            this.matFrom = matFrom;
        }

        public String getSampleDoc() {
            return sampleDoc;
        }

        public void setSampleDoc(String sampleDoc) {
            this.sampleDoc = sampleDoc;
        }

        public String getTemplateDoc() {
            return templateDoc;
        }

        public void setTemplateDoc(String templateDoc) {
            this.templateDoc = templateDoc;
        }

        public String getMatRequire() {
            return matRequire;
        }

        public void setMatRequire(String matRequire) {
            this.matRequire = matRequire;
        }

        public String getMatBasis() {
            return matBasis;
        }

        public void setMatBasis(String matBasis) {
            this.matBasis = matBasis;
        }

        public String getPaperIsRequire() {
            return paperIsRequire;
        }

        public void setPaperIsRequire(String paperIsRequire) {
            this.paperIsRequire = paperIsRequire;
        }

        public String getSortNo() {
            return sortNo;
        }

        public void setSortNo(String sortNo) {
            this.sortNo = sortNo;
        }

        public String getMatMemo() {
            return matMemo;
        }

        public void setMatMemo(String matMemo) {
            this.matMemo = matMemo;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
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

        public String getModifierTime() {
            return modifierTime;
        }

        public void setModifierTime(String modifierTime) {
            this.modifierTime = modifierTime;
        }

        public String getAttDirId() {
            return attDirId;
        }

        public void setAttDirId(String attDirId) {
            this.attDirId = attDirId;
        }

        public String getMatHolder() {
            return matHolder;
        }

        public void setMatHolder(String matHolder) {
            this.matHolder = matHolder;
        }

        public String getIsOfficialDoc() {
            return isOfficialDoc;
        }

        public void setIsOfficialDoc(String isOfficialDoc) {
            this.isOfficialDoc = isOfficialDoc;
        }

        public String getAttIsRequire() {
            return attIsRequire;
        }

        public void setAttIsRequire(String attIsRequire) {
            this.attIsRequire = attIsRequire;
        }

        public String getIsCommon() {
            return isCommon;
        }

        public void setIsCommon(String isCommon) {
            this.isCommon = isCommon;
        }

        public String getReviewKeyPoints() {
            return reviewKeyPoints;
        }

        public void setReviewKeyPoints(String reviewKeyPoints) {
            this.reviewKeyPoints = reviewKeyPoints;
        }

        public String getReviewSampleDoc() {
            return reviewSampleDoc;
        }

        public void setReviewSampleDoc(String reviewSampleDoc) {
            this.reviewSampleDoc = reviewSampleDoc;
        }

        public String getReviewBasis() {
            return reviewBasis;
        }

        public void setReviewBasis(String reviewBasis) {
            this.reviewBasis = reviewBasis;
        }

        public String getMatFromDept() {
            return matFromDept;
        }

        public void setMatFromDept(String matFromDept) {
            this.matFromDept = matFromDept;
        }

        public String getZcqy() {
            return zcqy;
        }

        public void setZcqy(String zcqy) {
            this.zcqy = zcqy;
        }
    }

    public static class StateQuestionsBean {
        /**
         * question : {"itemStateId":"4e34337c-94fd-4b2f-a9bd-6823577922d4","itemId":"590238d0-5117-4005-b4cb-5da5b804fc94","itemVerId":"c4e74436-3543-49d8-a5b8-b3339b773d3f","stateVerId":"5b3be9e3-aa3f-4453-85f7-00169a843c97","stateName":"请选择适用范围","stateEl":null,"sortNo":"240","stateMemo":null,"isActive":"1","creater":"htry","createTime":"2019-03-20 09:59:58","modifier":"4df31dde-9ed6-4f7c-968b-aa22fdd9ab7f","modifyTime":"2019-03-21 09:17:37","userEl":null,"parentStateId":null,"stateSeq":".4e34337c-94fd-4b2f-a9bd-6823577922d4.","isQuestion":"1","answerType":"s","mustAnswer":"1","isDeleted":"0","isProcStartCond":"0"}
         * answers : [{"itemStateId":"3f830870-1442-4025-bbf5-dfda6fbe0aca","itemId":"590238d0-5117-4005-b4cb-5da5b804fc94","itemVerId":"c4e74436-3543-49d8-a5b8-b3339b773d3f","stateVerId":"5b3be9e3-aa3f-4453-85f7-00169a843c97","stateName":"房屋类工程建设项目，建设用地（含临时用地）规划红线范围内","stateEl":null,"sortNo":"241","stateMemo":null,"isActive":"1","creater":"htry","createTime":"2019-03-20 09:59:58","modifier":"4df31dde-9ed6-4f7c-968b-aa22fdd9ab7f","modifyTime":"2019-03-21 09:17:37","userEl":null,"parentStateId":"4e34337c-94fd-4b2f-a9bd-6823577922d4","stateSeq":".4e34337c-94fd-4b2f-a9bd-6823577922d4.3f830870-1442-4025-bbf5-dfda6fbe0aca.","isQuestion":"0","answerType":null,"mustAnswer":null,"isDeleted":"0","isProcStartCond":"0"},{"itemStateId":"feaa9038-192e-4f1c-968c-c72fb1223a3a","itemId":"590238d0-5117-4005-b4cb-5da5b804fc94","itemVerId":"c4e74436-3543-49d8-a5b8-b3339b773d3f","stateVerId":"5b3be9e3-aa3f-4453-85f7-00169a843c97","stateName":"园林绿化工程建设项目建设用地（含临时用地）规划红线范围内","stateEl":null,"sortNo":"249","stateMemo":null,"isActive":"1","creater":"htry","createTime":"2019-03-20 09:59:58","modifier":"4df31dde-9ed6-4f7c-968b-aa22fdd9ab7f","modifyTime":"2019-03-21 09:17:37","userEl":null,"parentStateId":"4e34337c-94fd-4b2f-a9bd-6823577922d4","stateSeq":".4e34337c-94fd-4b2f-a9bd-6823577922d4.feaa9038-192e-4f1c-968c-c72fb1223a3a.","isQuestion":"0","answerType":null,"mustAnswer":null,"isDeleted":"0","isProcStartCond":"0"},{"itemStateId":"c0fd0573-eef1-48cf-b021-ba7903e42520","itemId":"590238d0-5117-4005-b4cb-5da5b804fc94","itemVerId":"c4e74436-3543-49d8-a5b8-b3339b773d3f","stateVerId":"5b3be9e3-aa3f-4453-85f7-00169a843c97","stateName":"项目建设用地（含临时用地）规划红线范围外","stateEl":null,"sortNo":"250","stateMemo":null,"isActive":"1","creater":"htry","createTime":"2019-03-20 09:59:58","modifier":"4df31dde-9ed6-4f7c-968b-aa22fdd9ab7f","modifyTime":"2019-03-21 09:17:37","userEl":null,"parentStateId":"4e34337c-94fd-4b2f-a9bd-6823577922d4","stateSeq":".4e34337c-94fd-4b2f-a9bd-6823577922d4.c0fd0573-eef1-48cf-b021-ba7903e42520.","isQuestion":"0","answerType":null,"mustAnswer":null,"isDeleted":"0","isProcStartCond":"0"}]
         */

        private QuestionBean question;
        private List<AnswersBean> answers;

        public QuestionBean getQuestion() {
            return question;
        }

        public void setQuestion(QuestionBean question) {
            this.question = question;
        }

        public List<AnswersBean> getAnswers() {
            return answers;
        }

        public void setAnswers(List<AnswersBean> answers) {
            this.answers = answers;
        }

        public static class QuestionBean {

            /**
             * itemStateId : 4e34337c-94fd-4b2f-a9bd-6823577922d4
             * itemId : 590238d0-5117-4005-b4cb-5da5b804fc94
             * itemVerId : c4e74436-3543-49d8-a5b8-b3339b773d3f
             * stateVerId : 5b3be9e3-aa3f-4453-85f7-00169a843c97
             * stateName : 请选择适用范围
             * stateEl : null
             * sortNo : 240
             * stateMemo : null
             * isActive : 1
             * creater : htry
             * createTime : 2019-03-20 09:59:58
             * modifier : 4df31dde-9ed6-4f7c-968b-aa22fdd9ab7f
             * modifyTime : 2019-03-21 09:17:37
             * userEl : null
             * parentStateId : null
             * stateSeq : .4e34337c-94fd-4b2f-a9bd-6823577922d4.
             * isQuestion : 1
             * answerType : s
             * mustAnswer : 1
             * isDeleted : 0
             * isProcStartCond : 0
             */

            private String itemStateId;
            private String itemId;
            private String itemVerId;
            private String stateVerId;
            private String stateName;
            private String stateEl;
            private String sortNo;
            private String stateMemo;
            private String isActive;
            private String creater;
            private String createTime;
            private String modifier;
            private String modifyTime;
            private String userEl;
            private String parentStateId;
            private String stateSeq;
            private String isQuestion;
            private String answerType;
            private String mustAnswer;
            private String isDeleted;
            private String isProcStartCond;

            public String getItemStateId() {
                return itemStateId;
            }

            public void setItemStateId(String itemStateId) {
                this.itemStateId = itemStateId;
            }

            public String getItemId() {
                return itemId;
            }

            public void setItemId(String itemId) {
                this.itemId = itemId;
            }

            public String getItemVerId() {
                return itemVerId;
            }

            public void setItemVerId(String itemVerId) {
                this.itemVerId = itemVerId;
            }

            public String getStateVerId() {
                return stateVerId;
            }

            public void setStateVerId(String stateVerId) {
                this.stateVerId = stateVerId;
            }

            public String getStateName() {
                return stateName;
            }

            public void setStateName(String stateName) {
                this.stateName = stateName;
            }

            public String getStateEl() {
                return stateEl;
            }

            public void setStateEl(String stateEl) {
                this.stateEl = stateEl;
            }

            public String getSortNo() {
                return sortNo;
            }

            public void setSortNo(String sortNo) {
                this.sortNo = sortNo;
            }

            public String getStateMemo() {
                return stateMemo;
            }

            public void setStateMemo(String stateMemo) {
                this.stateMemo = stateMemo;
            }

            public String getIsActive() {
                return isActive;
            }

            public void setIsActive(String isActive) {
                this.isActive = isActive;
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

            public String getUserEl() {
                return userEl;
            }

            public void setUserEl(String userEl) {
                this.userEl = userEl;
            }

            public String getParentStateId() {
                return parentStateId;
            }

            public void setParentStateId(String parentStateId) {
                this.parentStateId = parentStateId;
            }

            public String getStateSeq() {
                return stateSeq;
            }

            public void setStateSeq(String stateSeq) {
                this.stateSeq = stateSeq;
            }

            public String getIsQuestion() {
                return isQuestion;
            }

            public void setIsQuestion(String isQuestion) {
                this.isQuestion = isQuestion;
            }

            public String getAnswerType() {
                return answerType;
            }

            public void setAnswerType(String answerType) {
                this.answerType = answerType;
            }

            public String getMustAnswer() {
                return mustAnswer;
            }

            public void setMustAnswer(String mustAnswer) {
                this.mustAnswer = mustAnswer;
            }

            public String getIsDeleted() {
                return isDeleted;
            }

            public void setIsDeleted(String isDeleted) {
                this.isDeleted = isDeleted;
            }

            public String getIsProcStartCond() {
                return isProcStartCond;
            }

            public void setIsProcStartCond(String isProcStartCond) {
                this.isProcStartCond = isProcStartCond;
            }
        }

        public static class AnswersBean {
            /**
             * itemStateId : 3f830870-1442-4025-bbf5-dfda6fbe0aca
             * itemId : 590238d0-5117-4005-b4cb-5da5b804fc94
             * itemVerId : c4e74436-3543-49d8-a5b8-b3339b773d3f
             * stateVerId : 5b3be9e3-aa3f-4453-85f7-00169a843c97
             * stateName : 房屋类工程建设项目，建设用地（含临时用地）规划红线范围内
             * stateEl : null
             * sortNo : 241
             * stateMemo : null
             * isActive : 1
             * creater : htry
             * createTime : 2019-03-20 09:59:58
             * modifier : 4df31dde-9ed6-4f7c-968b-aa22fdd9ab7f
             * modifyTime : 2019-03-21 09:17:37
             * userEl : null
             * parentStateId : 4e34337c-94fd-4b2f-a9bd-6823577922d4
             * stateSeq : .4e34337c-94fd-4b2f-a9bd-6823577922d4.3f830870-1442-4025-bbf5-dfda6fbe0aca.
             * isQuestion : 0
             * answerType : null
             * mustAnswer : null
             * isDeleted : 0
             * isProcStartCond : 0
             */

            private String itemStateId;
            private String itemId;
            private String itemVerId;
            private String stateVerId;
            private String stateName;
            private String stateEl;
            private String sortNo;
            private String stateMemo;
            private String isActive;
            private String creater;
            private String createTime;
            private String modifier;
            private String modifyTime;
            private String userEl;
            private String parentStateId;
            private String stateSeq;
            private String isQuestion;
            private String answerType;
            private String mustAnswer;
            private String isDeleted;
            private String isProcStartCond;

            public String getItemStateId() {
                return itemStateId;
            }

            public void setItemStateId(String itemStateId) {
                this.itemStateId = itemStateId;
            }

            public String getItemId() {
                return itemId;
            }

            public void setItemId(String itemId) {
                this.itemId = itemId;
            }

            public String getItemVerId() {
                return itemVerId;
            }

            public void setItemVerId(String itemVerId) {
                this.itemVerId = itemVerId;
            }

            public String getStateVerId() {
                return stateVerId;
            }

            public void setStateVerId(String stateVerId) {
                this.stateVerId = stateVerId;
            }

            public String getStateName() {
                return stateName;
            }

            public void setStateName(String stateName) {
                this.stateName = stateName;
            }

            public String getStateEl() {
                return stateEl;
            }

            public void setStateEl(String stateEl) {
                this.stateEl = stateEl;
            }

            public String getSortNo() {
                return sortNo;
            }

            public void setSortNo(String sortNo) {
                this.sortNo = sortNo;
            }

            public String getStateMemo() {
                return stateMemo;
            }

            public void setStateMemo(String stateMemo) {
                this.stateMemo = stateMemo;
            }

            public String getIsActive() {
                return isActive;
            }

            public void setIsActive(String isActive) {
                this.isActive = isActive;
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

            public String getUserEl() {
                return userEl;
            }

            public void setUserEl(String userEl) {
                this.userEl = userEl;
            }

            public String getParentStateId() {
                return parentStateId;
            }

            public void setParentStateId(String parentStateId) {
                this.parentStateId = parentStateId;
            }

            public String getStateSeq() {
                return stateSeq;
            }

            public void setStateSeq(String stateSeq) {
                this.stateSeq = stateSeq;
            }

            public String getIsQuestion() {
                return isQuestion;
            }

            public void setIsQuestion(String isQuestion) {
                this.isQuestion = isQuestion;
            }

            public String getAnswerType() {
                return answerType;
            }

            public void setAnswerType(String answerType) {
                this.answerType = answerType;
            }

            public String getMustAnswer() {
                return mustAnswer;
            }

            public void setMustAnswer(String mustAnswer) {
                this.mustAnswer = mustAnswer;
            }

            public String getIsDeleted() {
                return isDeleted;
            }

            public void setIsDeleted(String isDeleted) {
                this.isDeleted = isDeleted;
            }

            public String getIsProcStartCond() {
                return isProcStartCond;
            }

            public void setIsProcStartCond(String isProcStartCond) {
                this.isProcStartCond = isProcStartCond;
            }
        }
    }
}
