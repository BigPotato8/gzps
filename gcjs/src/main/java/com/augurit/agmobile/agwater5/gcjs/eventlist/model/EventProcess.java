package com.augurit.agmobile.agwater5.gcjs.eventlist.model;


import java.io.Serializable;
import java.util.List;

/**
 * 办理意见bean
 * <p>
 * 处理流程列表项
 * Created by xcl on 2017/11/11.
 */

public class EventProcess implements Serializable {

    /**
     * dealingTask : false
     * firstOrgShortName : null
     * secondOrgShortName : null
     * isApprover : 1
     * attDetailNum : 0
     * processInstanceId : 4aeb4b82-e1fb-4d67-a31c-4d883d19b1b8
     * taskId : 11ee020a-3b5f-460b-ba7c-a8d270528fd2
     * nodeName : 窗口收件
     * commentMessage : 材料齐全给予收件
     * approveResult : null
     * taskAssignee : 系统管理
     * sigeInDate : 1582080620000
     * endDate : 1582080622000
     * taskState : 2
     * orgId : 0368948a-1cdf-4bf8-a828-71d796ba89f6
     * orgName : 江门市
     * userMobile :
     * isMultiTaskNode : 0
     * isItemNode : 0
     * iteminstId : null
     * bzNum : null
     * tscxNum : null
     * childNode : []
     * otherCommentList : null
     */

    private boolean dealingTask;
    private String firstOrgShortName;
    private String secondOrgShortName;
    private String isApprover;
    private int attDetailNum;
    private String processInstanceId;
    private String taskId;
    private String nodeName;
    private String commentMessage;
    private String approveResult;
    private String taskAssignee;
    private String sigeInDate;
    private String endDate;
    private int taskState;
    private String orgId;
    private String orgName;
    private String userMobile;
    private String isMultiTaskNode;
    private String isItemNode;
    private String iteminstId;
    private String bzNum;
    private String tscxNum;
    private String otherCommentList;
    private List<EventProcess> childNode;
    /**
     * 节点状态
     * 0 进行中；1 已完成；
     */
    private String itemState;
    private ProcessNewModel mProcessNewModel;
    private int nodeLevel;// 0是一级流程 ，1是二级流程，2是三级流程

    public int getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(int nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public ProcessNewModel getProcessNewModel() {
        return mProcessNewModel;
    }

    public void setProcessNewModel(ProcessNewModel processNewModel) {
        mProcessNewModel = processNewModel;
    }

    public String getItemState() {
        return itemState;
    }

    public void setItemState(String itemState) {
        this.itemState = itemState;
    }

    public boolean isDealingTask() {
        return dealingTask;
    }

    public void setDealingTask(boolean dealingTask) {
        this.dealingTask = dealingTask;
    }

    public String getFirstOrgShortName() {
        return firstOrgShortName;
    }

    public void setFirstOrgShortName(String firstOrgShortName) {
        this.firstOrgShortName = firstOrgShortName;
    }

    public String getSecondOrgShortName() {
        return secondOrgShortName;
    }

    public void setSecondOrgShortName(String secondOrgShortName) {
        this.secondOrgShortName = secondOrgShortName;
    }

    public String getIsApprover() {
        return isApprover;
    }

    public void setIsApprover(String isApprover) {
        this.isApprover = isApprover;
    }

    public int getAttDetailNum() {
        return attDetailNum;
    }

    public void setAttDetailNum(int attDetailNum) {
        this.attDetailNum = attDetailNum;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }

    public String getApproveResult() {
        return approveResult;
    }

    public void setApproveResult(String approveResult) {
        this.approveResult = approveResult;
    }

    public String getTaskAssignee() {
        return taskAssignee;
    }

    public void setTaskAssignee(String taskAssignee) {
        this.taskAssignee = taskAssignee;
    }

    public String getSigeInDate() {
        return sigeInDate;
    }

    public void setSigeInDate(String sigeInDate) {
        this.sigeInDate = sigeInDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getTaskState() {
        return taskState;
    }

    public void setTaskState(int taskState) {
        this.taskState = taskState;
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

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getIsMultiTaskNode() {
        return isMultiTaskNode;
    }

    public void setIsMultiTaskNode(String isMultiTaskNode) {
        this.isMultiTaskNode = isMultiTaskNode;
    }

    public String getIsItemNode() {
        return isItemNode;
    }

    public void setIsItemNode(String isItemNode) {
        this.isItemNode = isItemNode;
    }

    public String getIteminstId() {
        return iteminstId;
    }

    public void setIteminstId(String iteminstId) {
        this.iteminstId = iteminstId;
    }

    public String getBzNum() {
        return bzNum;
    }

    public void setBzNum(String bzNum) {
        this.bzNum = bzNum;
    }

    public String getTscxNum() {
        return tscxNum;
    }

    public void setTscxNum(String tscxNum) {
        this.tscxNum = tscxNum;
    }

    public String getOtherCommentList() {
        return otherCommentList;
    }

    public void setOtherCommentList(String otherCommentList) {
        this.otherCommentList = otherCommentList;
    }

    public List<EventProcess> getChildNode() {
        return childNode;
    }

    public void setChildNode(List<EventProcess> childNode) {
        this.childNode = childNode;
    }


}
