package com.augurit.agmobile.agwater5.gcjs.eventlist.model;

import com.augurit.agmobile.common.lib.model.FileBean;

import java.io.Serializable;
import java.util.List;

/**
 * 上报事件列表实体
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.drainage.problem.model
 * @createTime 创建时间 ：2018/9/4
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class EventAffair {

    /**
     * handling : 32
     * finished : 8
     * list : [{"bhlx":"","bz":"","code":"","directOrgId":"1081","directOrgName":"黄埔区市政建设有限公司","files":[],"fjzd":"","id":985,"jdmc":"","jjcd":"","layerId":0,"layerName":"","layerurl":"","objectId":"","parentOrgId":"1068","parentOrgName":"黄埔区水务局","reportaddr":"","reportx":"","reporty":"","sbr":"测试1","sbsj":{"date":15,"day":3,"hours":15,"minutes":59,"month":10,"nanos":0,"seconds":6,"time":1510732746000,"timezoneOffset":-480,"year":117},"sslx":"","superviseOrgId":"","superviseOrgName":"","szwz":"","teamOrgId":"1125","teamOrgName":"巡查组","usid":"","wtms":"","x":"","y":"","yjgcl":""},{"bhlx":"","bz":"","code":"","directOrgId":"1081","directOrgName":"黄埔区市政建设有限公司","files":[],"fjzd":"","id":986,"jdmc":"","jjcd":"","layerId":0,"layerName":"","layerurl":"","objectId":"","parentOrgId":"1068","parentOrgName":"黄埔区水务局","reportaddr":"","reportx":"","reporty":"","sbr":"了解","sbsj":{"date":15,"day":3,"hours":16,"minutes":1,"month":10,"nanos":0,"seconds":30,"time":1510732890000,"timezoneOffset":-480,"year":117},"sslx":"","superviseOrgId":"","superviseOrgName":"","szwz":"","teamOrgId":"1125","teamOrgName":"巡查组","usid":"","wtms":"","x":"","y":"","yjgcl":""},{"bhlx":"","bz":"","code":"","directOrgId":"1081","directOrgName":"黄埔区市政建设有限公司","files":[],"fjzd":"","id":987,"jdmc":"","jjcd":"","layerId":0,"layerName":"","layerurl":"","objectId":"","parentOrgId":"1068","parentOrgName":"黄埔区水务局","reportaddr":"","reportx":"","reporty":"","sbr":"444","sbsj":{"date":15,"day":3,"hours":16,"minutes":1,"month":10,"nanos":0,"seconds":36,"time":1510732896000,"timezoneOffset":-480,"year":117},"sslx":"","superviseOrgId":"","superviseOrgName":"","szwz":"","teamOrgId":"1125","teamOrgName":"巡查组","usid":"","wtms":"","x":"","y":"","yjgcl":""},{"bhlx":"","bz":"","code":"","directOrgId":"1081","directOrgName":"黄埔区市政建设有限公司","files":[],"fjzd":"","id":988,"jdmc":"","jjcd":"","layerId":0,"layerName":"","layerurl":"","objectId":"","parentOrgId":"1068","parentOrgName":"黄埔区水务局","reportaddr":"","reportx":"","reporty":"","sbr":"测试","sbsj":{"date":15,"day":3,"hours":16,"minutes":1,"month":10,"nanos":0,"seconds":44,"time":1510732904000,"timezoneOffset":-480,"year":117},"sslx":"","superviseOrgId":"","superviseOrgName":"","szwz":"","teamOrgId":"1125","teamOrgName":"巡查组","usid":"","wtms":"","x":"","y":"","yjgcl":""},{"bhlx":"","bz":"","code":"","directOrgId":"1081","directOrgName":"黄埔区市政建设有限公司","files":[],"fjzd":"","id":989,"jdmc":"","jjcd":"","layerId":0,"layerName":"","layerurl":"","objectId":"","parentOrgId":"1068","parentOrgName":"黄埔区水务局","reportaddr":"","reportx":"","reporty":"","sbr":"测试2017115","sbsj":{"date":15,"day":3,"hours":16,"minutes":20,"month":10,"nanos":0,"seconds":2,"time":1510734002000,"timezoneOffset":-480,"year":117},"sslx":"","superviseOrgId":"","superviseOrgName":"","szwz":"","teamOrgId":"1125","teamOrgName":"巡查组","usid":"","wtms":"","x":"","y":"","yjgcl":""},{"bhlx":"","bz":"1234","code":"111","directOrgId":"1081","directOrgName":"黄埔区市政建设有限公司","files":[],"fjzd":"","id":990,"jdmc":"123","jjcd":"2","layerId":0,"layerName":"","layerurl":"","objectId":"","parentOrgId":"1068","parentOrgName":"黄埔区水务局","reportaddr":"","reportx":"","reporty":"","sbr":"奥格","sbsj":{"date":15,"day":3,"hours":16,"minutes":57,"month":10,"nanos":0,"seconds":1,"time":1510736221000,"timezoneOffset":-480,"year":117},"sslx":"1","superviseOrgId":"","superviseOrgName":"","szwz":"","teamOrgId":"1125","teamOrgName":"巡查组","usid":"","wtms":"12","x":"","y":"","yjgcl":"12"},{"bhlx":"","bz":"","code":"","directOrgId":"1081","directOrgName":"黄埔区市政建设有限公司","files":[],"fjzd":"","id":991,"jdmc":"","jjcd":"","layerId":0,"layerName":"","layerurl":"","objectId":"","parentOrgId":"1068","parentOrgName":"黄埔区水务局","reportaddr":"","reportx":"","reporty":"","sbr":"测试","sbsj":{"date":15,"day":3,"hours":17,"minutes":13,"month":10,"nanos":0,"seconds":41,"time":1510737221000,"timezoneOffset":-480,"year":117},"sslx":"","superviseOrgId":"","superviseOrgName":"","szwz":"","teamOrgId":"1125","teamOrgName":"巡查组","usid":"","wtms":"","x":"","y":"","yjgcl":""},{"bhlx":"","bz":"","code":"","directOrgId":"1081","directOrgName":"黄埔区市政建设有限公司","files":[],"fjzd":"","id":992,"jdmc":"","jjcd":"","layerId":0,"layerName":"","layerurl":"","objectId":"","parentOrgId":"1068","parentOrgName":"黄埔区水务局","reportaddr":"","reportx":"","reporty":"","sbr":"奥格4","sbsj":{"date":15,"day":3,"hours":17,"minutes":32,"month":10,"nanos":0,"seconds":23,"time":1510738343000,"timezoneOffset":-480,"year":117},"sslx":"","superviseOrgId":"","superviseOrgName":"","szwz":"","teamOrgId":"1125","teamOrgName":"巡查组","usid":"","wtms":"","x":"","y":"","yjgcl":""}]
     */

    private int handling;
    private int finished;
    private List<EventAffairBean> list;

    public int getHandling() {
        return handling;
    }

    public void setHandling(int handling) {
        this.handling = handling;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public List<EventAffairBean> getList() {
        return list;
    }

    public void setList(List<EventAffairBean> list) {
        this.list = list;
    }

    public static class EventAffairBean implements Serializable {
        /**
         * bhlx :
         * bz :
         * code :
         * directOrgId : 1081
         * directOrgName : 黄埔区市政建设有限公司
         * files : []
         * fjzd :
         * id : 985
         * jdmc :
         * jjcd :
         * layerId : 0
         * layerName :
         * layerurl :
         * objectId :
         * parentOrgId : 1068
         * parentOrgName : 黄埔区水务局
         * reportaddr :
         * reportx :
         * reporty :
         * sbr : 测试1
         * sbsj : {"date":15,"day":3,"hours":15,"minutes":59,"month":10,"nanos":0,"seconds":6,"time":1510732746000,"timezoneOffset":-480,"year":117}
         * sslx :
         * superviseOrgId :
         * superviseOrgName :
         * szwz :
         * teamOrgId : 1125
         * teamOrgName : 巡查组
         * usid :
         * wtms :
         * x :
         * y :
         * yjgcl :
         */
        private String activityName;
        private String activityChineseName;
        private String bhlx;
        private String bz;
        private String code;
        private String directOrgId;
        private String directOrgName;
        private String fjzd;
        private String id;
        private String jdmc;
        private String jjcd;
        private int layerId;
        private String layerName;
        private String layerurl;
        private String objectId;
        private String parentOrgId;
        private String parentOrgName;
        private String reportaddr;
        private String reportx;
        private String reporty;
        private String sbr;
        private String procInstDbId;
        //        private SbsjBean sbsj;
        private long sbsj2;
        private String sslx;
        private String superviseOrgId;
        private String superviseOrgName;
        private String szwz;
        private String teamOrgId;
        private String teamOrgName;
        private String usid;
        private String wtms;
        private String x;
        private String y;
        private String isbyself;
        private String yjgcl;
        private String state;     //事件状态：处理中active，  ended
        private String sfjb;      //是否交办
        private String yjwcsj2;      //预计处理完成时间
        private List<FileBean> files2;

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public String getActivityChineseName() {
            return activityChineseName;
        }

        public void setActivityChineseName(String activityChineseName) {
            this.activityChineseName = activityChineseName;
        }

        public String getProcInstDbId() {
            return procInstDbId;
        }

        public void setProcInstDbId(String procInstDbId) {
            this.procInstDbId = procInstDbId;
        }

        public String getBhlx() {
            return bhlx;
        }

        public void setBhlx(String bhlx) {
            this.bhlx = bhlx;
        }

        public String getBz() {
            return bz;
        }

        public void setBz(String bz) {
            this.bz = bz;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDirectOrgId() {
            return directOrgId;
        }

        public void setDirectOrgId(String directOrgId) {
            this.directOrgId = directOrgId;
        }

        public String getDirectOrgName() {
            return directOrgName;
        }

        public void setDirectOrgName(String directOrgName) {
            this.directOrgName = directOrgName;
        }

        public String getFjzd() {
            return fjzd;
        }

        public void setFjzd(String fjzd) {
            this.fjzd = fjzd;
        }


        public String getJdmc() {
            return jdmc;
        }

        public void setJdmc(String jdmc) {
            this.jdmc = jdmc;
        }

        public String getJjcd() {
            return jjcd;
        }

        public void setJjcd(String jjcd) {
            this.jjcd = jjcd;
        }

        public int getLayerId() {
            return layerId;
        }

        public void setLayerId(int layerId) {
            this.layerId = layerId;
        }

        public String getLayerName() {
            return layerName;
        }

        public void setLayerName(String layerName) {
            this.layerName = layerName;
        }

        public String getLayerurl() {
            return layerurl;
        }

        public void setLayerurl(String layerurl) {
            this.layerurl = layerurl;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getParentOrgId() {
            return parentOrgId;
        }

        public void setParentOrgId(String parentOrgId) {
            this.parentOrgId = parentOrgId;
        }

        public String getParentOrgName() {
            return parentOrgName;
        }

        public void setParentOrgName(String parentOrgName) {
            this.parentOrgName = parentOrgName;
        }

        public String getReportaddr() {
            return reportaddr;
        }

        public void setReportaddr(String reportaddr) {
            this.reportaddr = reportaddr;
        }

        public String getReportx() {
            return reportx;
        }

        public void setReportx(String reportx) {
            this.reportx = reportx;
        }

        public String getReporty() {
            return reporty;
        }

        public void setReporty(String reporty) {
            this.reporty = reporty;
        }

        public String getSbr() {
            return sbr;
        }

        public void setSbr(String sbr) {
            this.sbr = sbr;
        }

        public String getSslx() {
            return sslx;
        }

        public void setSslx(String sslx) {
            this.sslx = sslx;
        }

        public String getSuperviseOrgId() {
            return superviseOrgId;
        }

        public void setSuperviseOrgId(String superviseOrgId) {
            this.superviseOrgId = superviseOrgId;
        }

        public String getSuperviseOrgName() {
            return superviseOrgName;
        }

        public void setSuperviseOrgName(String superviseOrgName) {
            this.superviseOrgName = superviseOrgName;
        }

        public String getSzwz() {
            return szwz;
        }

        public void setSzwz(String szwz) {
            this.szwz = szwz;
        }

        public String getTeamOrgId() {
            return teamOrgId;
        }

        public void setTeamOrgId(String teamOrgId) {
            this.teamOrgId = teamOrgId;
        }

        public String getTeamOrgName() {
            return teamOrgName;
        }

        public void setTeamOrgName(String teamOrgName) {
            this.teamOrgName = teamOrgName;
        }

        public String getUsid() {
            return usid;
        }

        public void setUsid(String usid) {
            this.usid = usid;
        }

        public String getWtms() {
            return wtms;
        }

        public void setWtms(String wtms) {
            this.wtms = wtms;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public String getY() {
            return y;
        }

        public void setY(String y) {
            this.y = y;
        }

        public String getIsbyself() {
            return isbyself;
        }

        public void setIsbyself(String isbyself) {
            this.isbyself = isbyself;
        }

        public String getYjgcl() {
            return yjgcl;
        }

        public void setYjgcl(String yjgcl) {
            this.yjgcl = yjgcl;
        }

        public long getSbsj2() {
            return sbsj2;
        }

        public void setSbsj2(long sbsj2) {
            this.sbsj2 = sbsj2;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getSfjb() {
            return sfjb;
        }

        public void setSfjb(String sfjb) {
            this.sfjb = sfjb;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getYjwcsj2() {
            return yjwcsj2;
        }

        public void setYjwcsj2(String yjwcsj2) {
            this.yjwcsj2 = yjwcsj2;
        }

        public List<FileBean> getFiles2() {
            return files2;
        }

        public void setFiles2(List<FileBean> files2) {
            this.files2 = files2;
        }
    }
}
