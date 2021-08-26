package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model;

import java.util.List;

/**
 * @description 并联情形bean
 * @date: $date$ $time$
 * @author: xieruibin
 */
public class MultiSituationBean {

    /**
     * parentQuestionStateId : root
     * answerList : [{"id":null,"name":null,"priority":null,"progress":null,"nodeTypeCode":null,"nodeTypeName":null,"note":null,"bindDestTypeCode":null,"bindDestId":null,"isQuestion":"0","bindDisplayJson":null,"isGlobal":null,"extra":null,"linkProcessStart":null,"terminateSituation":null,"situationAnswerNum":null,"pid":null,"open":null,"childs":null,"parStateId":"4460cc7b-3114-49c8-8429-424f37d4448c","stageId":"cab97786-535a-4389-a226-3c8112470875","stateName":"是","useEl":"0","stateEl":null,"answerType":null,"mustAnswer":null,"parentStateId":"2320627a-d44e-413d-bffc-881fce32b7a6","stateSeq":".2320627a-d44e-413d-bffc-881fce32b7a6.4460cc7b-3114-49c8-8429-424f37d4448c.","sortNo":11,"isActive":"ACTIVE","stateMemo":null,"creater":"4df31dde-9ed6-4f7c-968b-aa22fdd9ab7f","createTime":[2019,3,29,14,47,38],"modifier":null,"modifyTime":null,"isDeleted":"NOT_DELETED","isQuestionOri":null,"keyword":null},{"id":null,"name":null,"priority":null,"progress":null,"nodeTypeCode":null,"nodeTypeName":null,"note":null,"bindDestTypeCode":null,"bindDestId":null,"isQuestion":"0","bindDisplayJson":null,"isGlobal":null,"extra":null,"linkProcessStart":null,"terminateSituation":null,"situationAnswerNum":null,"pid":null,"open":null,"childs":null,"parStateId":"724d0b60-8211-4deb-bc82-ae792cdc43f1","stageId":"cab97786-535a-4389-a226-3c8112470875","stateName":"否","useEl":"0","stateEl":null,"answerType":null,"mustAnswer":null,"parentStateId":"2320627a-d44e-413d-bffc-881fce32b7a6","stateSeq":".2320627a-d44e-413d-bffc-881fce32b7a6.724d0b60-8211-4deb-bc82-ae792cdc43f1.","sortNo":13,"isActive":"ACTIVE","stateMemo":null,"creater":"4df31dde-9ed6-4f7c-968b-aa22fdd9ab7f","createTime":[2019,3,29,14,47,38],"modifier":null,"modifyTime":null,"isDeleted":"NOT_DELETED","isQuestionOri":null,"keyword":null}]
     * questionState : {"id":null,"name":null,"priority":null,"progress":null,"nodeTypeCode":null,"nodeTypeName":null,"note":null,"bindDestTypeCode":null,"bindDestId":null,"isQuestion":"1","bindDisplayJson":null,"isGlobal":null,"extra":null,"linkProcessStart":null,"terminateSituation":null,"situationAnswerNum":null,"pid":null,"open":null,"childs":null,"parStateId":"2320627a-d44e-413d-bffc-881fce32b7a6","stageId":"cab97786-535a-4389-a226-3c8112470875","stateName":"是否市发改部门审批、市国土部门预审的项目","useEl":"0","stateEl":null,"answerType":"y","mustAnswer":"1","parentStateId":null,"stateSeq":".2320627a-d44e-413d-bffc-881fce32b7a6.","sortNo":10,"isActive":"ACTIVE","stateMemo":null,"creater":"4df31dde-9ed6-4f7c-968b-aa22fdd9ab7f","createTime":[2019,3,29,14,47,38],"modifier":null,"modifyTime":null,"isDeleted":"NOT_DELETED","isQuestionOri":null,"keyword":null}
     */

    private String parentQuestionStateId;
    private QuestionStateBean questionState;
    private List<AnswerListBean> answerList;

    public String getParentQuestionStateId() {
        return parentQuestionStateId;
    }

    public void setParentQuestionStateId(String parentQuestionStateId) {
        this.parentQuestionStateId = parentQuestionStateId;
    }

    public QuestionStateBean getQuestionState() {
        return questionState;
    }

    public void setQuestionState(QuestionStateBean questionState) {
        this.questionState = questionState;
    }

    public List<AnswerListBean> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<AnswerListBean> answerList) {
        this.answerList = answerList;
    }

    public static class QuestionStateBean {
        /**
         * id : null
         * name : null
         * priority : null
         * progress : null
         * nodeTypeCode : null
         * nodeTypeName : null
         * note : null
         * bindDestTypeCode : null
         * bindDestId : null
         * isQuestion : 1
         * bindDisplayJson : null
         * isGlobal : null
         * extra : null
         * linkProcessStart : null
         * terminateSituation : null
         * situationAnswerNum : null
         * pid : null
         * open : null
         * childs : null
         * parStateId : 2320627a-d44e-413d-bffc-881fce32b7a6
         * stageId : cab97786-535a-4389-a226-3c8112470875
         * stateName : 是否市发改部门审批、市国土部门预审的项目
         * useEl : 0
         * stateEl : null
         * answerType : y
         * mustAnswer : 1
         * parentStateId : null
         * stateSeq : .2320627a-d44e-413d-bffc-881fce32b7a6.
         * sortNo : 10
         * isActive : ACTIVE
         * stateMemo : null
         * creater : 4df31dde-9ed6-4f7c-968b-aa22fdd9ab7f
         * createTime : [2019,3,29,14,47,38]
         * modifier : null
         * modifyTime : null
         * isDeleted : NOT_DELETED
         * isQuestionOri : null
         * keyword : null
         */

        private Object id;
        private Object name;
        private Object priority;
        private Object progress;
        private Object nodeTypeCode;
        private Object nodeTypeName;
        private Object note;
        private Object bindDestTypeCode;
        private Object bindDestId;
        private String isQuestion;
        private Object bindDisplayJson;
        private Object isGlobal;
        private Object extra;
        private Object linkProcessStart;
        private Object terminateSituation;
        private Object situationAnswerNum;
        private Object pid;
        private Object open;
        private Object childs;
        private String parStateId;
        private String stageId;
        private String stateName;
        private String useEl;
        private Object stateEl;
        private String answerType;
        private String mustAnswer;
        private Object parentStateId;
        private String stateSeq;
        private int sortNo;
        private String isActive;
        private Object stateMemo;
        private String creater;
        private Object modifier;
        private Object modifyTime;
        private String isDeleted;
        private Object isQuestionOri;
        private Object keyword;
        private List<Integer> createTime;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getPriority() {
            return priority;
        }

        public void setPriority(Object priority) {
            this.priority = priority;
        }

        public Object getProgress() {
            return progress;
        }

        public void setProgress(Object progress) {
            this.progress = progress;
        }

        public Object getNodeTypeCode() {
            return nodeTypeCode;
        }

        public void setNodeTypeCode(Object nodeTypeCode) {
            this.nodeTypeCode = nodeTypeCode;
        }

        public Object getNodeTypeName() {
            return nodeTypeName;
        }

        public void setNodeTypeName(Object nodeTypeName) {
            this.nodeTypeName = nodeTypeName;
        }

        public Object getNote() {
            return note;
        }

        public void setNote(Object note) {
            this.note = note;
        }

        public Object getBindDestTypeCode() {
            return bindDestTypeCode;
        }

        public void setBindDestTypeCode(Object bindDestTypeCode) {
            this.bindDestTypeCode = bindDestTypeCode;
        }

        public Object getBindDestId() {
            return bindDestId;
        }

        public void setBindDestId(Object bindDestId) {
            this.bindDestId = bindDestId;
        }

        public String getIsQuestion() {
            return isQuestion;
        }

        public void setIsQuestion(String isQuestion) {
            this.isQuestion = isQuestion;
        }

        public Object getBindDisplayJson() {
            return bindDisplayJson;
        }

        public void setBindDisplayJson(Object bindDisplayJson) {
            this.bindDisplayJson = bindDisplayJson;
        }

        public Object getIsGlobal() {
            return isGlobal;
        }

        public void setIsGlobal(Object isGlobal) {
            this.isGlobal = isGlobal;
        }

        public Object getExtra() {
            return extra;
        }

        public void setExtra(Object extra) {
            this.extra = extra;
        }

        public Object getLinkProcessStart() {
            return linkProcessStart;
        }

        public void setLinkProcessStart(Object linkProcessStart) {
            this.linkProcessStart = linkProcessStart;
        }

        public Object getTerminateSituation() {
            return terminateSituation;
        }

        public void setTerminateSituation(Object terminateSituation) {
            this.terminateSituation = terminateSituation;
        }

        public Object getSituationAnswerNum() {
            return situationAnswerNum;
        }

        public void setSituationAnswerNum(Object situationAnswerNum) {
            this.situationAnswerNum = situationAnswerNum;
        }

        public Object getPid() {
            return pid;
        }

        public void setPid(Object pid) {
            this.pid = pid;
        }

        public Object getOpen() {
            return open;
        }

        public void setOpen(Object open) {
            this.open = open;
        }

        public Object getChilds() {
            return childs;
        }

        public void setChilds(Object childs) {
            this.childs = childs;
        }

        public String getParStateId() {
            return parStateId;
        }

        public void setParStateId(String parStateId) {
            this.parStateId = parStateId;
        }

        public String getStageId() {
            return stageId;
        }

        public void setStageId(String stageId) {
            this.stageId = stageId;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getUseEl() {
            return useEl;
        }

        public void setUseEl(String useEl) {
            this.useEl = useEl;
        }

        public Object getStateEl() {
            return stateEl;
        }

        public void setStateEl(Object stateEl) {
            this.stateEl = stateEl;
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

        public Object getParentStateId() {
            return parentStateId;
        }

        public void setParentStateId(Object parentStateId) {
            this.parentStateId = parentStateId;
        }

        public String getStateSeq() {
            return stateSeq;
        }

        public void setStateSeq(String stateSeq) {
            this.stateSeq = stateSeq;
        }

        public int getSortNo() {
            return sortNo;
        }

        public void setSortNo(int sortNo) {
            this.sortNo = sortNo;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

        public Object getStateMemo() {
            return stateMemo;
        }

        public void setStateMemo(Object stateMemo) {
            this.stateMemo = stateMemo;
        }

        public String getCreater() {
            return creater;
        }

        public void setCreater(String creater) {
            this.creater = creater;
        }

        public Object getModifier() {
            return modifier;
        }

        public void setModifier(Object modifier) {
            this.modifier = modifier;
        }

        public Object getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(Object modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }

        public Object getIsQuestionOri() {
            return isQuestionOri;
        }

        public void setIsQuestionOri(Object isQuestionOri) {
            this.isQuestionOri = isQuestionOri;
        }

        public Object getKeyword() {
            return keyword;
        }

        public void setKeyword(Object keyword) {
            this.keyword = keyword;
        }

        public List<Integer> getCreateTime() {
            return createTime;
        }

        public void setCreateTime(List<Integer> createTime) {
            this.createTime = createTime;
        }
    }

    public static class AnswerListBean {
        /**
         * id : null
         * name : null
         * priority : null
         * progress : null
         * nodeTypeCode : null
         * nodeTypeName : null
         * note : null
         * bindDestTypeCode : null
         * bindDestId : null
         * isQuestion : 0
         * bindDisplayJson : null
         * isGlobal : null
         * extra : null
         * linkProcessStart : null
         * terminateSituation : null
         * situationAnswerNum : null
         * pid : null
         * open : null
         * childs : null
         * parStateId : 4460cc7b-3114-49c8-8429-424f37d4448c
         * stageId : cab97786-535a-4389-a226-3c8112470875
         * stateName : 是
         * useEl : 0
         * stateEl : null
         * answerType : null
         * mustAnswer : null
         * parentStateId : 2320627a-d44e-413d-bffc-881fce32b7a6
         * stateSeq : .2320627a-d44e-413d-bffc-881fce32b7a6.4460cc7b-3114-49c8-8429-424f37d4448c.
         * sortNo : 11
         * isActive : ACTIVE
         * stateMemo : null
         * creater : 4df31dde-9ed6-4f7c-968b-aa22fdd9ab7f
         * createTime : [2019,3,29,14,47,38]
         * modifier : null
         * modifyTime : null
         * isDeleted : NOT_DELETED
         * isQuestionOri : null
         * keyword : null
         */

        private Object id;
        private Object name;
        private Object priority;
        private Object progress;
        private Object nodeTypeCode;
        private Object nodeTypeName;
        private Object note;
        private Object bindDestTypeCode;
        private Object bindDestId;
        private String isQuestion;
        private Object bindDisplayJson;
        private Object isGlobal;
        private Object extra;
        private Object linkProcessStart;
        private Object terminateSituation;
        private Object situationAnswerNum;
        private Object pid;
        private Object open;
        private Object childs;
        private String parStateId;
        private String stageId;
        private String stateName;
        private String useEl;
        private Object stateEl;
        private Object answerType;
        private Object mustAnswer;
        private String parentStateId;
        private String stateSeq;
        private int sortNo;
        private String isActive;
        private Object stateMemo;
        private String creater;
        private Object modifier;
        private Object modifyTime;
        private String isDeleted;
        private Object isQuestionOri;
        private Object keyword;
        private List<Integer> createTime;

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getPriority() {
            return priority;
        }

        public void setPriority(Object priority) {
            this.priority = priority;
        }

        public Object getProgress() {
            return progress;
        }

        public void setProgress(Object progress) {
            this.progress = progress;
        }

        public Object getNodeTypeCode() {
            return nodeTypeCode;
        }

        public void setNodeTypeCode(Object nodeTypeCode) {
            this.nodeTypeCode = nodeTypeCode;
        }

        public Object getNodeTypeName() {
            return nodeTypeName;
        }

        public void setNodeTypeName(Object nodeTypeName) {
            this.nodeTypeName = nodeTypeName;
        }

        public Object getNote() {
            return note;
        }

        public void setNote(Object note) {
            this.note = note;
        }

        public Object getBindDestTypeCode() {
            return bindDestTypeCode;
        }

        public void setBindDestTypeCode(Object bindDestTypeCode) {
            this.bindDestTypeCode = bindDestTypeCode;
        }

        public Object getBindDestId() {
            return bindDestId;
        }

        public void setBindDestId(Object bindDestId) {
            this.bindDestId = bindDestId;
        }

        public String getIsQuestion() {
            return isQuestion;
        }

        public void setIsQuestion(String isQuestion) {
            this.isQuestion = isQuestion;
        }

        public Object getBindDisplayJson() {
            return bindDisplayJson;
        }

        public void setBindDisplayJson(Object bindDisplayJson) {
            this.bindDisplayJson = bindDisplayJson;
        }

        public Object getIsGlobal() {
            return isGlobal;
        }

        public void setIsGlobal(Object isGlobal) {
            this.isGlobal = isGlobal;
        }

        public Object getExtra() {
            return extra;
        }

        public void setExtra(Object extra) {
            this.extra = extra;
        }

        public Object getLinkProcessStart() {
            return linkProcessStart;
        }

        public void setLinkProcessStart(Object linkProcessStart) {
            this.linkProcessStart = linkProcessStart;
        }

        public Object getTerminateSituation() {
            return terminateSituation;
        }

        public void setTerminateSituation(Object terminateSituation) {
            this.terminateSituation = terminateSituation;
        }

        public Object getSituationAnswerNum() {
            return situationAnswerNum;
        }

        public void setSituationAnswerNum(Object situationAnswerNum) {
            this.situationAnswerNum = situationAnswerNum;
        }

        public Object getPid() {
            return pid;
        }

        public void setPid(Object pid) {
            this.pid = pid;
        }

        public Object getOpen() {
            return open;
        }

        public void setOpen(Object open) {
            this.open = open;
        }

        public Object getChilds() {
            return childs;
        }

        public void setChilds(Object childs) {
            this.childs = childs;
        }

        public String getParStateId() {
            return parStateId;
        }

        public void setParStateId(String parStateId) {
            this.parStateId = parStateId;
        }

        public String getStageId() {
            return stageId;
        }

        public void setStageId(String stageId) {
            this.stageId = stageId;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getUseEl() {
            return useEl;
        }

        public void setUseEl(String useEl) {
            this.useEl = useEl;
        }

        public Object getStateEl() {
            return stateEl;
        }

        public void setStateEl(Object stateEl) {
            this.stateEl = stateEl;
        }

        public Object getAnswerType() {
            return answerType;
        }

        public void setAnswerType(Object answerType) {
            this.answerType = answerType;
        }

        public Object getMustAnswer() {
            return mustAnswer;
        }

        public void setMustAnswer(Object mustAnswer) {
            this.mustAnswer = mustAnswer;
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

        public int getSortNo() {
            return sortNo;
        }

        public void setSortNo(int sortNo) {
            this.sortNo = sortNo;
        }

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

        public Object getStateMemo() {
            return stateMemo;
        }

        public void setStateMemo(Object stateMemo) {
            this.stateMemo = stateMemo;
        }

        public String getCreater() {
            return creater;
        }

        public void setCreater(String creater) {
            this.creater = creater;
        }

        public Object getModifier() {
            return modifier;
        }

        public void setModifier(Object modifier) {
            this.modifier = modifier;
        }

        public Object getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(Object modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }

        public Object getIsQuestionOri() {
            return isQuestionOri;
        }

        public void setIsQuestionOri(Object isQuestionOri) {
            this.isQuestionOri = isQuestionOri;
        }

        public Object getKeyword() {
            return keyword;
        }

        public void setKeyword(Object keyword) {
            this.keyword = keyword;
        }

        public List<Integer> getCreateTime() {
            return createTime;
        }

        public void setCreateTime(List<Integer> createTime) {
            this.createTime = createTime;
        }
    }
}
