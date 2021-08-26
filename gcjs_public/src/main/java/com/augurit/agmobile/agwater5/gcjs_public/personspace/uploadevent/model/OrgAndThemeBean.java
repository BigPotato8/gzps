package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model;

import java.util.List;

/**
 * @description 获取主题和部门
 * @date: $date$ $time$
 * @author: xieruibin
 */
public class OrgAndThemeBean {

    private List<ThemesBean> themes;
    private List<OpuOmOrgsBean> opuOmOrgs;

    public List<ThemesBean> getThemes() {
        return themes;
    }

    public void setThemes(List<ThemesBean> themes) {
        this.themes = themes;
    }

    public List<OpuOmOrgsBean> getOpuOmOrgs() {
        return opuOmOrgs;
    }

    public void setOpuOmOrgs(List<OpuOmOrgsBean> opuOmOrgs) {
        this.opuOmOrgs = opuOmOrgs;
    }

    public static class ThemesBean {
        /**
         * themeId : 9a55af6c-5b0e-4112-a56d-8e1be4109d72
         * themeName : 政府投资房屋建筑项目
         */

        private String themeId;
        private String themeName;

        public String getThemeId() {
            return themeId;
        }

        public void setThemeId(String themeId) {
            this.themeId = themeId;
        }

        public String getThemeName() {
            return themeName;
        }

        public void setThemeName(String themeName) {
            this.themeName = themeName;
        }
    }

    public static class OpuOmOrgsBean {
        /**
         * orgId : 07fa45af-b91c-47b1-ad67-6f000b206793
         * orgName : 唐山市公安交通管理局
         */

        private String orgId;
        private String orgName;

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
    }
}
