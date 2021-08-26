package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventSituationBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.busi.bpm.form.model.FormInfo;
import com.augurit.agmobile.busi.bpm.form.util.ElementBuilder;
import com.augurit.agmobile.busi.bpm.form.util.FormBuilder;
import com.augurit.agmobile.busi.bpm.form.util.WidgetBuilder;
import com.augurit.agmobile.busi.bpm.form.view.ElementPosition;
import com.augurit.agmobile.busi.bpm.form.view.FormState;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.common.common.form.AwFormConfig;
import com.augurit.common.common.form.AwFormFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
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
 * 基本信息-申报主体信息
 */
public class DeclareMainListFragment extends AwFormFragment {
    EventRepository mEventRepository;
    private boolean isFirst = true;
    private EventListItem.DataBean mEventListItem;
    private EventBean mData;

    private MaterialListFragment mMaterialListFragment;
    private EventListFragment eventListFragment;

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
        mFormCode = AwFormConfig.FORM_DECLARE_MAIN;
        mEventRepository = new EventRepository();

        getEventData();

    }


    @Override
    protected void onFormLoaded() {
        super.onFormLoaded();

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
                            if (eventInfoBean != null) {//全局发送实体类信息
                                //EventBus.getDefault().post(eventInfoBean);
                            }
                            for (EventInfoBean.UnitInfoVoListBean bean : eventInfoBean.getUnitInfoVoList()) {
                                bean.setUnitTypeText(getUnitType(bean.getUnitType()));
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
//                            //插入详情查看
//                            //updateForm(data.getmap);
//                            mRecord = new FormRecord();
//                            mRecord.setValues(map);
//                            loadForm();
//
//                            if (mData != null) {//全局发送实体类信息
//                                EventBus.getDefault().post(mData);
//                            }
//                            getEventSituation();//情形查看
//                        } else {//加载失败
//                            ToastUtil.longToast(getActivity(), "加载失败");
//                        }
//                    }, throwable -> {
//                        ProgressDialogUtil.dismissProgressDialog();
//                        throwable.printStackTrace();
//                    });
        }

    }

    private String getUnitType(String unitType){
        switch (unitType){
            case "1":
                return "建设单位";
            case "2":
                return "施工单位";
            case "3":
                return "勘察单位";
            case "4":
                return "设计单位";
            case "5":
                return "监理单位";
            case "6":
                return "代建单位";
            case "7":
                return "经办单位";
            case "8":
                return "其他";
            case "9":
                return "审图机构";
        }
        return "";
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
                .addDivider("单位信息1（实名认证）");
//                .addElement(new ElementBuilder("projectBuildUnit")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("单位类型")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("projectBuildUnit")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("统一社会信用代码")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("projectBuildUnit")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("单位名称")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("projectBuildUnit")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("单位地址")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("projectBuildUnit")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("法定代表人")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("projectBuildUnit")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("法定代表人证件号")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("projectBuildUnit")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("联系人名称")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("projectBuildUnit")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("联系人证件号")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("projectBuildUnit")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("联系人手机")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("projectBuildUnit")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("联系人邮箱")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("projectBuildUnit")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("工商登记号")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("projectBuildUnit")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("组织机构代码")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("projectBuildUnit")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("纳税人识别号")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("projectBuildUnit")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("信用标记码")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("projectBuildUnit")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("注册资本")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("projectBuildUnit")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("注册登记机关")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("projectBuildUnit")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("经营范围")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("projectBuildUnit")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("邮政编码")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("projectBuildUnit")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("行政区（园区）")
//                                .isEnable(false)
//                                .build())
//                        .build());

        if (!ListUtil.isEmpty(list)) {
            for (EventInfoBean.UnitInfoVoListBean listBean : list) {
                fb.addElement(new ElementBuilder("unitTypeText")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("单位类型")
                                .defaultValue(listBean.getUnitTypeText())
//                                .dictTypeCode("XM_DWLX")
                                .isEnable(false)
                                .build())
                        .build())
                        .addElement(new ElementBuilder("unifiedSocialCreditCode")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("统一社会信用代码")
                                        .defaultValue(listBean.getUnifiedSocialCreditCode())
                                        .isEnable(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("applicant")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("单位名称")
                                        .defaultValue(listBean.getApplicant())
                                        .isEnable(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("applicantDetailSite")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("单位地址")
                                        .defaultValue(listBean.getApplicantDetailSite())
                                        .isEnable(false)
                                        .isVisible(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("idrepresentative")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("法定代表人")
                                        .defaultValue(listBean.getIdrepresentative())
                                        .isEnable(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("idno")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("法定代表人证件号")
                                        .defaultValue(listBean.getIdno())
                                        .isEnable(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("linkmanName")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("联系人名称")
                                        .defaultValue(listBean.getLinkmanName())
                                        .isEnable(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("linkmanCertNo")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("联系人证件号")
                                        .defaultValue(listBean.getLinkmanCertNo())
                                        .isEnable(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("linkmanMobilePhone")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("联系人手机")
                                        .defaultValue(listBean.getLinkmanMobilePhone())
                                        .isEnable(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("linkmanMail")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("联系人邮箱")
                                        .defaultValue(listBean.getLinkmanMail())
                                        .isEnable(false)
                                        .isVisible(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("induCommRegNum")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("工商登记号")
                                        .defaultValue(listBean.getInduCommRegNum())
                                        .isEnable(false)
                                        .isVisible(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("organizationalCode")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("组织机构代码")
                                        .defaultValue(listBean.getOrganizationalCode())
                                        .isEnable(false)
                                        .isVisible(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("taxpayerNum")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("纳税人识别号")
                                        .defaultValue(listBean.getTaxpayerNum())
                                        .isEnable(false)
                                        .isVisible(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("creditFlagNum")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("信用标记码")
                                        .defaultValue(listBean.getCreditFlagNum())
                                        .isEnable(false)
                                        .isVisible(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("registeredCapital")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("注册资本")
                                        .defaultValue(listBean.getRegisteredCapital())
                                        .isEnable(false)
                                        .isVisible(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("registerAuthority")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("注册登记机关")
                                        .defaultValue(listBean.getRegisterAuthority())
                                        .isEnable(false)
                                        .isVisible(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("managementScope")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("经营范围")
                                        .defaultValue(listBean.getManagementScope())
                                        .isEnable(false)
                                        .isVisible(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("postalCode")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("邮政编码")
                                        .defaultValue(listBean.getPostalCode())
                                        .isEnable(false)
                                        .isVisible(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("applicantDistrict")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("行政区（园区）")
                                        .defaultValue(listBean.getApplicantDistrict())
                                        .isEnable(false)
                                        .isVisible(false)
                                        .build())
                                .build())
                        .addDivider();

            }
        }
        if (!ListUtil.isEmpty(linkList)) {
            EventInfoBean.LinkmanVoListBean linkmanVoListBean = linkList.get(0);
//            fb.addDivider("申请人信息")
                    fb.addElement(new ElementBuilder("申请人")
                            .widget(new WidgetBuilder(EDITTEXT)
                                    .title("申请人")
                                    .defaultValue(linkmanVoListBean.getLinkmanName())
                                    .isEnable(false)
                                    .build())
                            .build())
                    .addElement(new ElementBuilder("linkmanCertNo")
                            .widget(new WidgetBuilder(EDITTEXT)
                                    .title("申请人身份证号")
                                    .defaultValue(linkmanVoListBean.getLinkmanCertNo())
                                    .isEnable(false)
                                    .build())
                            .build())
                    .addElement(new ElementBuilder("linkmanMobilePhone")
                            .widget(new WidgetBuilder(EDITTEXT)
                                    .title("申请人电话")
                                    .defaultValue(linkmanVoListBean.getLinkmanMobilePhone())
                                    .isEnable(false)
                                    .build())
                            .build())
                    .addElement(new ElementBuilder("linkmanMail")
                            .widget(new WidgetBuilder(EDITTEXT)
                                    .title("申请人邮箱")
                                    .defaultValue(linkmanVoListBean.getLinkmanMail())
                                    .isEnable(false)
                                    .build())
                            .build());
            if (linkList.size()==2) {
                EventInfoBean.LinkmanVoListBean linkmanVoListBean1 = linkList.get(1);
                fb.addDivider("联系人信息")
                        .addElement(new ElementBuilder("联系人")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("联系人")
                                        .defaultValue(linkmanVoListBean.getLinkmanName())
                                        .isEnable(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("linkmanCertNo")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("联系人身份证号")
                                        .defaultValue(linkmanVoListBean.getLinkmanCertNo())
                                        .isEnable(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("linkmanMobilePhone")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("联系人电话")
                                        .defaultValue(linkmanVoListBean.getLinkmanMobilePhone())
                                        .isEnable(false)
                                        .build())
                                .build())
                        .addElement(new ElementBuilder("linkmanMail")
                                .widget(new WidgetBuilder(EDITTEXT)
                                        .title("联系人邮箱")
                                        .defaultValue(linkmanVoListBean.getLinkmanMail())
                                        .isEnable(false)
                                        .build())
                                .build());
            }
        }

        return fb.build();
    }

}

