package com.augurit.common.statistics.model;

import java.util.ArrayList;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.statistics.model
 * @createTime 创建时间 ：2018-09-05
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class InstallInfo {

    private InstallData data;

    public InstallData getData() {
        return data;
    }

    public void setData(InstallData data) {
        this.data = data;
    }

    public class InstallData {
        private int total;
        private int install;
        private ArrayList<ChildArea> child_orgs;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getInstall() {
            return install;
        }

        public void setInstall(int install) {
            this.install = install;
        }

        public ArrayList<ChildArea> getChild_area() {
            return child_orgs;
        }

        public void setChild_area(ArrayList<ChildArea> child_area) {
            this.child_orgs = child_area;
        }
    }

    public class ChildArea {
        private String install_percent;
        private String org_name;

        public ChildArea(String install_percent, String org_name) {
            this.install_percent = install_percent;
            this.org_name = org_name;
        }

        public String getInstall_percent() {
            return install_percent;
        }

        public void setInstall_percent(String install_percent) {
            this.install_percent = install_percent;
        }

        public String getArea_name() {
            return org_name;
        }

        public void setArea_name(String area_name) {
            this.org_name = area_name;
        }
    }

}
