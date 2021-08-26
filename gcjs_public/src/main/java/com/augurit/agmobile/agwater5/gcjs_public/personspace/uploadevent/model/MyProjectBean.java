package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author 创建人 ：panming
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model
 * @createTime 创建时间 ：2019-10-29
 * @modifyBy 修改人 ：panming
 * @modifyTime 修改时间 ：2019-10-29
 * @modifyMemo 修改备注：
 */
public class MyProjectBean implements Serializable {

    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProjectBean getContent() {
        return content;
    }

    public void setContent(ProjectBean content) {
        this.content = content;
    }

    private  ProjectBean content;


//static class ProjectListBean implements Serializable{
//    private String pageNum;
//    private String pageSize;
//    private String size;
//    private String startRow;
//    private String endRow;
//    private String total;
//    private String pages;
//    private String prePage;
//    private String nextPage;
//    private String isFirstPage;
//    private String isLastPage;
//    private String hasPreviousPage;
//    private String hasNextPage;
//    private String navigatePages;
//    //    private String navigatepageNums": [1, 2, 3, 4, 5, 6, 7, 8],
//    private String navigateFirstPage;
//    private String navigateLastPage;
//    private String firstPage;
//    private String lastPage;
//    private List<ProjectDetailBean.ProjectInfoBean> list;
//
//
//    public String getPageNum() {
//        return pageNum;
//    }
//
//    public void setPageNum(String pageNum) {
//        this.pageNum = pageNum;
//    }
//
//    public String getPageSize() {
//        return pageSize;
//    }
//
//    public void setPageSize(String pageSize) {
//        this.pageSize = pageSize;
//    }
//
//    public String getSize() {
//        return size;
//    }
//
//    public void setSize(String size) {
//        this.size = size;
//    }
//
//    public String getStartRow() {
//        return startRow;
//    }
//
//    public void setStartRow(String startRow) {
//        this.startRow = startRow;
//    }
//
//    public String getEndRow() {
//        return endRow;
//    }
//
//    public void setEndRow(String endRow) {
//        this.endRow = endRow;
//    }
//
//    public String getTotal() {
//        return total;
//    }
//
//    public void setTotal(String total) {
//        this.total = total;
//    }
//
//    public String getPages() {
//        return pages;
//    }
//
//    public void setPages(String pages) {
//        this.pages = pages;
//    }
//
//    public String getPrePage() {
//        return prePage;
//    }
//
//    public void setPrePage(String prePage) {
//        this.prePage = prePage;
//    }
//
//    public String getNextPage() {
//        return nextPage;
//    }
//
//    public void setNextPage(String nextPage) {
//        this.nextPage = nextPage;
//    }
//
//    public String getIsFirstPage() {
//        return isFirstPage;
//    }
//
//    public void setIsFirstPage(String isFirstPage) {
//        this.isFirstPage = isFirstPage;
//    }
//
//    public String getIsLastPage() {
//        return isLastPage;
//    }
//
//    public void setIsLastPage(String isLastPage) {
//        this.isLastPage = isLastPage;
//    }
//
//    public String getHasPreviousPage() {
//        return hasPreviousPage;
//    }
//
//    public void setHasPreviousPage(String hasPreviousPage) {
//        this.hasPreviousPage = hasPreviousPage;
//    }
//
//    public String getHasNextPage() {
//        return hasNextPage;
//    }
//
//    public void setHasNextPage(String hasNextPage) {
//        this.hasNextPage = hasNextPage;
//    }
//
//    public String getNavigatePages() {
//        return navigatePages;
//    }
//
//    public void setNavigatePages(String navigatePages) {
//        this.navigatePages = navigatePages;
//    }
//
//    public String getNavigateFirstPage() {
//        return navigateFirstPage;
//    }
//
//    public void setNavigateFirstPage(String navigateFirstPage) {
//        this.navigateFirstPage = navigateFirstPage;
//    }
//
//    public String getNavigateLastPage() {
//        return navigateLastPage;
//    }
//
//    public void setNavigateLastPage(String navigateLastPage) {
//        this.navigateLastPage = navigateLastPage;
//    }
//
//    public String getFirstPage() {
//        return firstPage;
//    }
//
//    public void setFirstPage(String firstPage) {
//        this.firstPage = firstPage;
//    }
//
//    public String getLastPage() {
//        return lastPage;
//    }
//
//    public void setLastPage(String lastPage) {
//        this.lastPage = lastPage;
//    }
//
//    public List<ProjectDetailBean.ProjectInfoBean> getList() {
//        return list;
//    }
//
//    public void setList(List<ProjectDetailBean.ProjectInfoBean> list) {
//        this.list = list;
//    }
//
//
//    }
}
