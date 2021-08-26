package com.augurit.common.common.manager;

/**
 * 应急接口
 */
public interface EmergencyConstant {
    /**
     * 获取应急问题列表
     */
    String YJPROBLEMREPORT_LISTJSON = "psemgy/yjProblemReport/listJson";
    /**
     * 新增应急问题
     */
    String YJPROBLEMREPORT_UPLOADJSON = "psemgy/yjProblemReport/uploadJson";
    /**
     * 处理应急问题
     */
    String YJPROBLEMREPORT_DEALPROBLEMUPLOAD = "psemgy/yjProblemReport/dealProblemUpload";
    /**
     * 保存值班信息
     */
    String DUTYMANAGEMENT_SAVEJSON = "psemgy/dutyManagement/saveJson";
    /**
     * 删除值班信息
     */
    String DUTYMANAGEMENT_DELETEMORE = "psemgy/dutyManagement/deleteMore";
    /**
     * 获取值班信息
     */
    String DUTYMANAGEMENT_LISTJSON = "psemgy/dutyManagement/listJson";
    /**
     * 获取值班人员列表
     */
    String DUTYMANAGEMENT_GETPERSONLIST = "psemgy/dutyManagement/getPersonList";
}
