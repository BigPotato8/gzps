package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventSituationBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.busi.bpm.form.model.FormInfo;
import com.augurit.agmobile.busi.bpm.form.util.ElementBuilder;
import com.augurit.agmobile.busi.bpm.form.util.FormBuilder;
import com.augurit.agmobile.busi.bpm.form.util.WidgetBuilder;
import com.augurit.agmobile.busi.bpm.form.view.FormState;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.agmobile.busi.bpm.widget.WidgetListener;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.common.common.form.AwFormConfig;
import com.augurit.common.common.form.AwFormFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

import static com.augurit.agmobile.busi.bpm.widget.WidgetProperty.PROPERTY_DEFAULT_VALUE;
import static com.augurit.agmobile.busi.bpm.widget.WidgetProperty.PROPERTY_MAX_LIMIT;
import static com.augurit.agmobile.busi.bpm.widget.WidgetProperty.PROPERTY_TITLE;
import static com.augurit.agmobile.busi.bpm.widget.WidgetType.EDITTEXT;
import static com.augurit.agmobile.busi.bpm.widget.WidgetType.SPINNER;
import static com.augurit.agmobile.busi.bpm.widget.WidgetType.TIMEPICKER;

/**
 * @description ????????????-??????????????????fragment
 * @date: 20190402
 * @author: xieruibin
 */
public class EventBaseInfoFragment extends AwFormFragment {
    EventRepository mEventRepository;
    private boolean isFirst = true;
    private EventListItem.DataBean mEventListItem;
    private EventBean mData;

    private MaterialListFragment mMaterialListFragment;
    private EventListFragment eventListFragment;
    private String mProjLevel;
    private String mProjGategory;
    private String mProjAddr;

    public void setEventListItem(EventListItem.DataBean eventListItem) {
        mEventListItem = eventListItem;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initArguments() {
        super.initArguments();
        mFormState = FormState.STATE_READ;
        mFormCode = AwFormConfig.FORM_GCJS_PROJECT_DETAIL;
        mEventRepository = new EventRepository();

        getEventData();

    }


    @Override
    protected void onFormLoaded() {
        super.onFormLoaded();
        if(mProjLevel != null && !"".equals(mProjLevel)){//????????????
            mFormPresenter.getWidget("projLevel").getView().setVisibility(View.VISIBLE);
        }else {
            mFormPresenter.getWidget("projLevel").getView().setVisibility(View.GONE);
        }
        if(mProjGategory != null && !"".equals(mProjGategory)){//????????????
            mFormPresenter.getWidget("projNature").getView().setVisibility(View.VISIBLE);
        }else {
            mFormPresenter.getWidget("projCategory").getView().setVisibility(View.GONE);
        }
        if(mProjAddr != null &&!"".equals(mProjAddr)){//????????????
            mFormPresenter.setWidgetValue("projAddr",mProjAddr);
        }else {
//            mFormPresenter.getWidget("projAddr").setValue(mProjAddr);
            mFormPresenter.getWidget("projAddr").setValue("-");
        }
    }

    private void getEventData() {
        if (isFirst) {
            ProgressDialogUtil.showProgressDialog(getActivity(), false);
            mEventRepository.getBaseInfo(mEventListItem == null ? "" : mEventListItem.getApplyinstId(), mEventListItem == null ? "" : mEventListItem.getTaskId())
                    .subscribe(listApiResult -> {
                        ProgressDialogUtil.dismissProgressDialog();
                        if (listApiResult.isSuccess()) {
                            isFirst = false;
                            EventInfoBean eventInfoBean = listApiResult.getData();
                            mProjLevel = eventInfoBean.getProjInfoList().get(0).getProjLevel();
                            mProjGategory=eventInfoBean.getProjInfoList().get(0).getProjCategory();
                            mProjAddr=eventInfoBean.getProjInfoList().get(0).getProjAddr();
                            if (eventInfoBean != null) {//???????????????????????????
                                EventBus.getDefault().post(eventInfoBean);
                            }
                            Gson gson = new Gson();
                            Map<String, String> map = new HashMap<>();
                            if (!ListUtil.isEmpty(eventInfoBean.getProjInfoList())) {
                                EventInfoBean.ProjInfoListBean projInfo = eventInfoBean.getProjInfoList().get(0);
                                String projInfoJson = gson.toJson(projInfo);
                                map.putAll(gson.fromJson(projInfoJson, new TypeToken<Map<String, String>>() {
                                }.getType()));
                            }
                            mRecord = new FormRecord();
                            mRecord.setValues(map);
                            FormInfo formInfo = getGcjsProjectDetail(eventInfoBean.getUnitInfoVoList(),eventInfoBean.getLinkmanVoList());
                            loadForm(formInfo);

                        }
                    }, throwable -> {
                        ProgressDialogUtil.dismissProgressDialog();
                        throwable.printStackTrace();
                    });
//            mEventRepository.getBaseInfo(mEventListItem == null ? "" : mEventListItem.getApplyinstCode(), "false", mEventListItem.getBusRecordId(), mEventListItem.getTaskId())
//                    .subscribe(listApiResult -> {
//                        ProgressDialogUtil.dismissProgressDialog();
//                        if (listApiResult.isSuccess()) {
//                            isFirst = false;
//                            mData = listApiResult.getData();
//                            String s1 = mData.getProjectBuildUnit().replaceAll("<br>", "\n").replaceAll("</br>", "\n");
//                            mData.setProjectBuildUnit(s1);
//                            String s2 = mData.getBuildUnitIDCard().replaceAll("<br>", "\n").replaceAll("</br>", "\n");
//                            mData.setBuildUnitIDCard(s2);
//                            String s3 = mData.getItemCode().replaceAll("<br>", "\n").replaceAll("</br>", "\n");
//                            mData.setItemCode(s3);
//                            String s4 = mData.getItemName().replaceAll("<br>", "\n").replaceAll("</br>", "\n");
//                            mData.setItemName(s4);
//                            Gson gson = new Gson();
//                            String toJson = gson.toJson(mData);
//                            Map<String, String> map = gson.fromJson(toJson, new TypeToken<Map<String, String>>() {
//                            }.getType());
//                            //??????????????????
//                            //updateForm(data.getmap);
//                            mRecord = new FormRecord();
//                            mRecord.setValues(map);
//                            loadForm();
//
//                            if (mData != null) {//???????????????????????????
//                                EventBus.getDefault().post(mData);
//                            }
//                            getEventSituation();//????????????
//                        } else {//????????????
//                            ToastUtil.longToast(getActivity(), "????????????");
//                        }
//                    }, throwable -> {
//                        ProgressDialogUtil.dismissProgressDialog();
//                        throwable.printStackTrace();
//                    });
        }

    }

    private void getEventSituation() {
        Observable<ApiResult<List<EventSituationBean>>> observable = null;
        if ("??????".equals(mEventListItem.getApplyType())) {
            observable = mEventRepository.getEventSituationA(mData.getApplyinstId());
        } else {
            observable = mEventRepository.getEventSituationB(mData.getApplyinstId());
        }
        observable.subscribe(eventBeanApiResult -> {
                    if (eventBeanApiResult.isSuccess()) {
                        List<EventSituationBean> data = eventBeanApiResult.getData();//????????????
                        Map<String, String> map = new HashMap<>();
                        for (EventSituationBean esb : data) {
                            map.put(esb.getQuestion().getStateName(), esb.getAnswer().getStateName());
                        }
                        updateForm(map);
                    }
                }, (throwable) -> throwable.printStackTrace()
        );
    }


    private void updateForm(Map<String, String> map) {
        if (!ListUtil.isEmpty(map)) {
            //FormBuilder fb = new FormBuilder(mFormPresenter.getFormInfo());//????????????
            FormBuilder fb = new FormBuilder();//????????????
            fb.addDivider();
            for (String item : map.keySet()) {
                fb.addElement(new ElementBuilder(item)
                        .widget(new WidgetBuilder(EDITTEXT)
                                .addProperty(PROPERTY_TITLE, item)
                                .addProperty(PROPERTY_MAX_LIMIT, "200")
                                .addProperty(PROPERTY_DEFAULT_VALUE, map.get(item))
                                .build())
                        .build());
            }
            mFormView.load(fb.build(), FormState.STATE_READ);

        }
    }


    @Override
    protected boolean shouldLoadImmediately() {
        return false;
    }


    @Subscribe
    public void receive(EventInfoBean mData) {

    }

    private FormInfo getGcjsProjectDetail(List<EventInfoBean.UnitInfoVoListBean> list,List<EventInfoBean.LinkmanVoListBean> linkList) {
        FormBuilder fb =  new FormBuilder("")
//                .addDivider("??????/????????????")
                .addElement(new ElementBuilder("localCode")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????")
                                //.defaultValue("ZBM-P-20190326-66536115#1")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("gcbm")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????")
                                //.defaultValue("ZBM-P-20190326-66536115#1")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("projName")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("??????/????????????")
                                .hint("??????????????????")
                                //.defaultValue("????????????????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("regionalism")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("themeName")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????")
                                .isEnable(false)
                                .isVisible(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("projAddr")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("isGxyq")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("?????????????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("financialInvestment")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("financialSource")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("projType")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("isForeign")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("investType")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("landSource")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("isAreaEstimate")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("??????????????????????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("isDesignSolution")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("?????????????????????")
                                .isEnable(false)
                                .isVisible(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("projNature")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("projCategory")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("nstartTime")
                        .widget(new WidgetBuilder(TIMEPICKER)
                                .title("???????????????")
                                .formatDisplay("yyyy-MM-dd")
                                .formatValue("yyyy-MM-dd")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("endTime")
                        .widget(new WidgetBuilder(TIMEPICKER)
                                .title("???????????????")
                                .formatDisplay("yyyy-MM-dd")
                                .formatValue("yyyy-MM-dd")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("investSum")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("?????????????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("buildHeight")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("monomerSpan")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("???????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("buildAreaSum")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("??????????????????m?????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("xmYdmj")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("???????????????m?????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("projLevel")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????")
                                .isEnable(false)
                                .isVisible(true)
                                .build())
                        .build())
                .addElement(new ElementBuilder("theIndustry")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("gbCodeYear")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("??????????????????????????????")
                                .isEnable(false)
                                .isVisible(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("scaleContent")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("?????????????????????")
                                .isEnable(false)
                                .build())
                        .build());

                //.addDivider("????????????");

//        if (!ListUtil.isEmpty(list)) {
//            for (EventInfoBean.UnitInfoVoListBean listBean : list) {
//                fb.addElement(new ElementBuilder("unitType")
//                        .widget(new WidgetBuilder(SPINNER)
//                                .title("????????????")
//                                .defaultValue(listBean.getUnitType())
//                                .dictTypeCode("XM_DWLX")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                        .addElement(new ElementBuilder("unifiedSocialCreditCode")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("????????????????????????")
//                                        .defaultValue(listBean.getUnifiedSocialCreditCode())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("applicant")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("????????????")
//                                        .defaultValue(listBean.getApplicant())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("applicantDetailSite")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("????????????")
//                                        .defaultValue(listBean.getApplicantDetailSite())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("idrepresentative")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("???????????????")
//                                        .defaultValue(listBean.getIdrepresentative())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("idno")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("????????????????????????")
//                                        .defaultValue(listBean.getIdno())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("linkmanName")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("???????????????")
//                                        .defaultValue(listBean.getLinkmanName())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("linkmanCertNo")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("??????????????????")
//                                        .defaultValue(listBean.getLinkmanCertNo())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("linkmanMobilePhone")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("???????????????")
//                                        .defaultValue(listBean.getLinkmanMobilePhone())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("linkmanMail")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("???????????????")
//                                        .defaultValue(listBean.getLinkmanMail())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("induCommRegNum")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("???????????????")
//                                        .defaultValue(listBean.getInduCommRegNum())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("organizationalCode")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("??????????????????")
//                                        .defaultValue(listBean.getOrganizationalCode())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("taxpayerNum")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("??????????????????")
//                                        .defaultValue(listBean.getTaxpayerNum())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("creditFlagNum")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("???????????????")
//                                        .defaultValue(listBean.getCreditFlagNum())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("registeredCapital")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("????????????")
//                                        .defaultValue(listBean.getRegisteredCapital())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("registerAuthority")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("??????????????????")
//                                        .defaultValue(listBean.getRegisterAuthority())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("managementScope")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("????????????")
//                                        .defaultValue(listBean.getManagementScope())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("postalCode")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("????????????")
//                                        .defaultValue(listBean.getPostalCode())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("applicantDistrict")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("?????????????????????")
//                                        .defaultValue(listBean.getApplicantDistrict())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addDivider();
//
//            }
//        }
//        if (!ListUtil.isEmpty(linkList)) {
//            EventInfoBean.LinkmanVoListBean linkmanVoListBean = linkList.get(0);
//            fb.addDivider("???????????????")
//                    .addElement(new ElementBuilder("?????????")
//                            .widget(new WidgetBuilder(EDITTEXT)
//                                    .title("?????????")
//                                    .defaultValue(linkmanVoListBean.getLinkmanName())
//                                    .isEnable(false)
//                                    .build())
//                            .build())
//                    .addElement(new ElementBuilder("linkmanCertNo")
//                            .widget(new WidgetBuilder(EDITTEXT)
//                                    .title("?????????????????????")
//                                    .defaultValue(linkmanVoListBean.getLinkmanCertNo())
//                                    .isEnable(false)
//                                    .build())
//                            .build())
//                    .addElement(new ElementBuilder("linkmanMobilePhone")
//                            .widget(new WidgetBuilder(EDITTEXT)
//                                    .title("???????????????")
//                                    .defaultValue(linkmanVoListBean.getLinkmanMobilePhone())
//                                    .isEnable(false)
//                                    .build())
//                            .build())
//                    .addElement(new ElementBuilder("linkmanMail")
//                            .widget(new WidgetBuilder(EDITTEXT)
//                                    .title("???????????????")
//                                    .defaultValue(linkmanVoListBean.getLinkmanMail())
//                                    .isEnable(false)
//                                    .build())
//                            .build());
//            if (linkList.size()==2) {
//                EventInfoBean.LinkmanVoListBean linkmanVoListBean1 = linkList.get(1);
//                fb.addDivider("???????????????")
//                        .addElement(new ElementBuilder("?????????")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("?????????")
//                                        .defaultValue(linkmanVoListBean.getLinkmanName())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("linkmanCertNo")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("?????????????????????")
//                                        .defaultValue(linkmanVoListBean.getLinkmanCertNo())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("linkmanMobilePhone")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("???????????????")
//                                        .defaultValue(linkmanVoListBean.getLinkmanMobilePhone())
//                                        .isEnable(false)
//                                        .build())
//                                .build())
//                        .addElement(new ElementBuilder("linkmanMail")
//                                .widget(new WidgetBuilder(EDITTEXT)
//                                        .title("???????????????")
//                                        .defaultValue(linkmanVoListBean.getLinkmanMail())
//                                        .isEnable(false)
//                                        .build())
//                                .build());
//            }
//        }

        return fb.build();
    }

}
