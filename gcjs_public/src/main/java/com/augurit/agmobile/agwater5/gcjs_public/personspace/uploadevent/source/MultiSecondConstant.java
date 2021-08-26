package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.source;

import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.EventItemBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.MultiSituationBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.StageBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.ThemeAndMultiBean;

import java.util.List;

/**
 * @description 并联审批的第二阶段
 * @date: $date$ $time$
 * @author: xieruibin
 */
public interface MultiSecondConstant {
    interface View{
        void setTheme(List<ThemeAndMultiBean.ThemesBean> themes);
        void setMultiStage(List<StageBean> data, String themeId);
        void setMultiSituation(List<MultiSituationBean> data, String parentId, String elementCode);
        void setMultiEventList(List<EventItemBean> data);
    }

    interface Presenter{
        void getThemeAndOrg();
        void getMultiStage(String themeId);
        void getMultiSituation(String parentId, String stageId, String elementCode);
        void getMultiEventList(String themeId, String stageId);

        void stopRequest();
    }
}
