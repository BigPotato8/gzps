package com.augurit.agmobile.agwater5.gcjs.eventlist.model;

import java.util.List;

public class ElementButton {
    private List form;
    private List<PanelBean> panel;

    public List getForm() {
        return form;
    }

    public void setForm(List form) {
        this.form = form;
    }

    public List<PanelBean> getPanel() {
        return panel;
    }

    public void setPanel(List<PanelBean> panel) {
        this.panel = panel;
    }

    public static class PanelBean{

        /**
         * isReadonly : 0
         * elementCode : wf_button_toolbar
         * childElement : [{"sortNo":0,"columnType":"primaryBtn","isReadonly":"0","elementRender":"","elementCode":"yushentongguo_chuangkou","isHidden":"1","elementName":"预审通过（窗口）"}]
         * isHidden : 0
         * elementName : 审批按钮工具栏
         */

        private String isReadonly;
        private String elementCode;
        private String isHidden;
        private String elementName;
        private int sortNo;
        private String columnType;
        private String elementRender;
        private List<PanelBean> childElement;

        public int getSortNo() {
            return sortNo;
        }

        public void setSortNo(int sortNo) {
            this.sortNo = sortNo;
        }

        public String getColumnType() {
            return columnType;
        }

        public void setColumnType(String columnType) {
            this.columnType = columnType;
        }

        public String getElementRender() {
            return elementRender;
        }

        public void setElementRender(String elementRender) {
            this.elementRender = elementRender;
        }

        public String getIsReadonly() {
            return isReadonly;
        }

        public void setIsReadonly(String isReadonly) {
            this.isReadonly = isReadonly;
        }

        public String getElementCode() {
            return elementCode;
        }

        public void setElementCode(String elementCode) {
            this.elementCode = elementCode;
        }

        public String getIsHidden() {
            return isHidden;
        }

        public void setIsHidden(String isHidden) {
            this.isHidden = isHidden;
        }

        public String getElementName() {
            return elementName;
        }

        public void setElementName(String elementName) {
            this.elementName = elementName;
        }

        public List<PanelBean> getChildElement() {
            return childElement;
        }

        public void setChildElement(List<PanelBean> childElement) {
            this.childElement = childElement;
        }


    }
}
