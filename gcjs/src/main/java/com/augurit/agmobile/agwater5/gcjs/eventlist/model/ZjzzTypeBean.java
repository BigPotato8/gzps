package com.augurit.agmobile.agwater5.gcjs.eventlist.model;

import java.io.Serializable;
import java.util.List;

public class ZjzzTypeBean implements Serializable {

    /**
     * certList : [{"certId":"b68a2cb6-b5bc-496c-a32c-9887af5922f8","certCode":"test-001001","certName":"测试证照","matId":"490e67f0-926e-4858-bdfa-0857e29a34e9"}]
     * opuOmOrgList : [{"orgId":"a313df86-3b3d-4ad4-8273-482c3739ed89","orgCode":"R029-G389","orgName":"江门市蓬江区发展改革局"}]
     * projScale : 111.0
     */

    private double projScale;
    private List<CertListBean> certList;
    private List<OpuOmOrgListBean> opuOmOrgList;
    private List<ResultGoodsBean> resultsList;

    public List<ResultGoodsBean> getResultsList() {
        return resultsList;
    }

    public void setResultsList(List<ResultGoodsBean> resultsList) {
        this.resultsList = resultsList;
    }

    public double getProjScale() {
        return projScale;
    }

    public void setProjScale(double projScale) {
        this.projScale = projScale;
    }

    public List<CertListBean> getCertList() {
        return certList;
    }

    public void setCertList(List<CertListBean> certList) {
        this.certList = certList;
    }

    public List<OpuOmOrgListBean> getOpuOmOrgList() {
        return opuOmOrgList;
    }

    public void setOpuOmOrgList(List<OpuOmOrgListBean> opuOmOrgList) {
        this.opuOmOrgList = opuOmOrgList;
    }

    public static class CertListBean implements Serializable{
        /**
         * certId : b68a2cb6-b5bc-496c-a32c-9887af5922f8
         * certCode : test-001001
         * certName : 测试证照
         * matId : 490e67f0-926e-4858-bdfa-0857e29a34e9
         */

        private String certId;
        private String certCode;
        private String certName;
        private String matId;

        public String getCertId() {
            return certId;
        }

        public void setCertId(String certId) {
            this.certId = certId;
        }

        public String getCertCode() {
            return certCode;
        }

        public void setCertCode(String certCode) {
            this.certCode = certCode;
        }

        public String getCertName() {
            return certName;
        }

        public void setCertName(String certName) {
            this.certName = certName;
        }

        public String getMatId() {
            return matId;
        }

        public void setMatId(String matId) {
            this.matId = matId;
        }
    }

    public static class OpuOmOrgListBean implements Serializable{
        /**
         * orgId : a313df86-3b3d-4ad4-8273-482c3739ed89
         * orgCode : R029-G389
         * orgName : 江门市蓬江区发展改革局
         */

        private String orgId;
        private String orgCode;
        private String orgName;

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }
    }
}
