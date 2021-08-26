package com.augurit.common.statistics.model;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

/**
 * com.augurit.common.statistics.model
 * Created by sdb on 2019/9/26  13:56.
 * Desc：
 */
@SmartTable(name = "")
public class ReportTableBean {

    @SmartColumn(id = 1, name = "rowName")
    private String rowName;
    @SmartColumn(id = 2, name = "allNum")
    private int allNum;
    @SmartColumn(id = 3, name = "newNum")
    private int newNum;
    @SmartColumn(id = 4, name = "checkNum")
    private int checkNum;

    public ReportTableBean(String rowName, int allNum, int newNum, int checkNum) {
        this.rowName = rowName;
        this.allNum = allNum;
        this.newNum = newNum;
        this.checkNum = checkNum;
    }

    public String getRowName() {
        return rowName;
    }

    public void setRowName(String rowName) {
        this.rowName = rowName;
    }

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getNewNum() {
        return newNum;
    }

    public void setNewNum(int newNum) {
        this.newNum = newNum;
    }

    public int getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(int checkNum) {
        this.checkNum = checkNum;
    }
}
