package com.augurit.common.statistics.model;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.statistics.model
 * @createTime 创建时间 ：2018-09-05
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class NewInstallInfo {


        private String install_percent;
        private String org_name;

        public NewInstallInfo(String install_percent, String org_name) {
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
