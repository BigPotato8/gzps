package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.single;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.augurit.agmobile.agwater5.R;

import com.augurit.agmobile.agwater5.gcjs_public.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs_public.common.GcjsPuUrlConstant;
import com.augurit.agmobile.agwater5.gcjs_public.common.webview.WebViewActivity;
import com.augurit.agmobile.agwater5.gcjs_public.common.webview.WebViewConstant;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.EventItemBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.ProjectDetailBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.ProjectReportBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.source.ReportRepository;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.form.view.ElementPosition;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.agmobile.busi.bpm.widget.WidgetListener;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.agmobile.common.lib.other.BeanUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.combineview.AGMultiFunctionSpinner;
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.augurit.common.common.form.AwFormConfig;
import com.augurit.common.common.form.AwFormFragment;
import com.augurit.agmobile.agwater5.gcjs_public.common.view.AgTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.UploadProjectListAdapter.PROJECT;
import static com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.eventlist.EventListFragment.EVENT_ITEM;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent.uploadevent
 * Created by sdb on 2019/4/22  15:28.
 * Desc：
 */

public class SingleUploadEventFirstFragment extends AwFormFragment implements WidgetListener {
    ReportRepository mRepository;
    private ProjectReportBean mProjectReportBean;
    private EventItemBean mEventItemBean;
    private ProjectDetailBean mData;
    //申明对象
    CityPickerView mPicker = new CityPickerView();
    private AgTextView agTextView1;

//    @Override
//    protected void initView() {
//        super.initView();
//        title_bar.setVisibility(View.VISIBLE);
//    }

    @Override
    protected void initArguments() {
        super.initArguments();
        mFormCode = AwFormConfig.FORM_GCJS_PUBLIC_SINGLE_UPLOAD_EVENT;
        mProjectReportBean = (ProjectReportBean) getActivity().getIntent().getSerializableExtra(PROJECT);
        mEventItemBean = (EventItemBean) getActivity().getIntent().getSerializableExtra(EVENT_ITEM);
        mRepository = new ReportRepository();
        mRepository.getSingleInfoDetail(mEventItemBean.getItemVerId(), mProjectReportBean.getLocalCode())
                .subscribe(listApiResult -> {
                    mData = listApiResult.getData();
                    ProjectDetailBean.ProjectInfoBean projectInfo = mData.getProjectInfo();//项目信息
                    ProjectDetailBean.ProjectInfoBean projectInfoNew = new ProjectDetailBean.ProjectInfoBean();
                    BeanUtil.copyProperties(projectInfo, projectInfoNew);
                    projectInfoNew.setAeaUnitInfoList(null);
                    projectInfoNew.setAeaItemBasiList(null);
                    projectInfoNew.setLinkmanList(null);
                    Map<String, String> map = jsonToMap(projectInfoNew);

                    if (!ListUtil.isEmpty(mData.getProjectInfo().getAeaUnitInfoList())) {
                        ProjectDetailBean.ProjectInfoBean.AeaUnitInfoListBean aeaUnitInfoListBean = mData.getProjectInfo().getAeaUnitInfoList().get(0);
                        ProjectDetailBean.ProjectInfoBean.AeaUnitInfoListBean aeaUnitInfoListBeanNew = new ProjectDetailBean.ProjectInfoBean.AeaUnitInfoListBean();
                        BeanUtil.copyProperties(aeaUnitInfoListBean, aeaUnitInfoListBeanNew);//企业信息
                        aeaUnitInfoListBeanNew.setAeaLinkmanInfoList(null);
                        map.putAll(jsonToMap(aeaUnitInfoListBeanNew));
                    }

                    ProjectDetailBean.ItemInfoBean itemInfo = mData.getItemInfo();
                    ProjectDetailBean.ItemInfoBean itemInfoNew = new ProjectDetailBean.ItemInfoBean();
                    BeanUtil.copyProperties(itemInfo, itemInfoNew);//事项信息
                    itemInfoNew.setCreateTime(null);
                    itemInfoNew.setModifyTime(null);
                    map.putAll(jsonToMap(itemInfoNew));

                    if (!ListUtil.isEmpty(mData.getProjectInfo().getAeaUnitInfoList()) && !ListUtil.isEmpty(mData.getProjectInfo().getAeaUnitInfoList().get(0).getAeaLinkmanInfoList())) {
                        ProjectDetailBean.ProjectInfoBean.AeaUnitInfoListBean.AeaLinkmanInfoListBean aeaLinkmanInfoListBean = mData.getProjectInfo().getAeaUnitInfoList().get(0).getAeaLinkmanInfoList().get(0);
                        ProjectDetailBean.ProjectInfoBean.AeaUnitInfoListBean.AeaLinkmanInfoListBean aeaLinkmanInfoListBeanNew = new ProjectDetailBean.ProjectInfoBean.AeaUnitInfoListBean.AeaLinkmanInfoListBean();
                        BeanUtil.copyProperties(aeaLinkmanInfoListBean, aeaLinkmanInfoListBeanNew);//企业法人、联系人,creator重复，放后面覆盖掉
                        map.putAll(jsonToMap(aeaLinkmanInfoListBeanNew));
                    }
                    if (mRecord != null) {
                        mRecord.setValues(map);
                        loadForm();
                    } else {
                        FormRecord formRecord = new FormRecord();
                        formRecord.setValues(map);
                        mRecord = formRecord;
                        loadForm();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    loadForm();
                });
    }

    @Override
    protected void initView() {
        super.initView();
        //预先加载仿iOS滚轮实现的全部数据
        mPicker.init(getActivity());


    }

    @Override
    protected void onFormLoaded() {
        super.onFormLoaded();

        btn_container.setVisibility(View.GONE);
        btn_save.setVisibility(View.GONE);
        btn_submit.setVisibility(View.GONE);

        mFormPresenter.addWidgetListener(this);

//        TextView textView = new TextView(getActivity());
//        textView.setText("办事指南>>  ");
//        textView.setTextColor(getResources().getColor(R.color.agmobile_blue));
//        textView.setTextSize(ScreenUtils.sp2px(getActivity(), 5));
//        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT));
//        textView.setGravity(Gravity.RIGHT);
//        textView.setBackgroundColor(Color.WHITE);
//        int lr = ScreenUtils.dp2px(getActivity(), 10);
//        int tb = ScreenUtils.dp2px(getActivity(), 5);
//        textView.setPadding(lr, tb, lr, 0);
//        mFormPresenter.addViews("itemName", ElementPosition.ABOVE, textView);

        AgTextView agTextView = new AgTextView(getActivity());
        agTextView.setTextViewName("办事指南");
        agTextView.setEditTextColor(getResources().getColor(R.color.agmobile_blue));
        agTextView.setValue("立即查看>> ");
        mFormPresenter.addViews("itemName", ElementPosition.ABOVE, agTextView);
        agTextView1 = new AgTextView(getActivity());
        agTextView1.setTextViewName("收件人地区");
        agTextView1.setHint("请选择收件人地区");
        agTextView1.showRequireTag(true);
        mFormPresenter.addViews("receive_addr", ElementPosition.ABOVE, agTextView1);

        List<IDictItem> names = new ArrayList<>();
        if (!ListUtil.isEmpty(mData.getProjectInfo().getLinkmanList())) {
            for (ProjectDetailBean.ProjectInfoBean.LinkmanListBean lb : mData.getProjectInfo().getLinkmanList()) {
                DictItem di = new DictItem();
                di.setLabel(lb.getLinkmanName());
                di.setValue(lb.getLinkmanInfoId());
                names.add(di);
            }
        }
        AGMultiFunctionSpinner ac = (AGMultiFunctionSpinner) mFormPresenter.getWidget("linkmanName").getView();
        ac.initData(names);
        ac.setOnSpinnerEditListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {//关联字段
                for (ProjectDetailBean.ProjectInfoBean.LinkmanListBean lb : mData.getProjectInfo().getLinkmanList()) {
                    if (s.toString().equals(lb.getLinkmanName())) {
                        mFormPresenter.setWidgetValue("linkmanMobilePhone1", lb.getLinkmanMobilePhone());
                        mFormPresenter.setWidgetValue("linkmanCertNo1", lb.getLinkmanCertNo());

                        mFormPresenter.setWidgetValue("draw_name", s.toString());
                        mFormPresenter.setWidgetValue("draw_phone", lb.getLinkmanMobilePhone());
                        mFormPresenter.setWidgetValue("draw_card_num", lb.getLinkmanCertNo());

                        mFormPresenter.setWidgetValue("receive_name", s.toString());
                        mFormPresenter.setWidgetValue("receive_phone", lb.getLinkmanMobilePhone());
                        mFormPresenter.setWidgetValue("receive_addr", lb.getLinkmanAddr());
                    }
                }
            }
        });

        agTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra(WebViewConstant.WEBVIEW_TITLE, getString(R.string.menu_fileIndicate));
                intent.putExtra(WebViewConstant.WEBVIEW_URL_PATH, AwUrlManager.serverUrl() + GcjsPuUrlConstant.GET_BUSINESSGUIDE_DETAIL + (mData == null ? "" : "?itemVerId=" + mData.getItemInfo().getItemVerId()));
                startActivity(intent);

            }
        });
        agTextView1.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示
                mPicker.showCityPicker();
            }
        });

        CityConfig cityConfig = new CityConfig.Builder().build();
        mPicker.setConfig(cityConfig);

//监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                //省份province
                //城市city
                //地区district
                agTextView1.setValue(province.getName() + "  " + city.getName() + "  " + district.getName());
            }

            @Override
            public void onCancel() {
            }
        });

    }

    private Map<String, String> jsonToMap(Object obj) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        return gson.fromJson(json, new TypeToken<Map<String, String>>() {
        }.getType());
    }

    @Override
    protected boolean shouldLoadImmediately() {
        return false;
    }

    public boolean validateForm() {
        if (agTextView1.getVisibility()==View.VISIBLE && TextUtils.isEmpty(agTextView1.getValue())){
            agTextView1.showErrorText(true);
            return false & mFormPresenter.validate();
        }else{
            agTextView1.showErrorText(false);
        }
        return mFormPresenter.validate();
    }

    public String getProjectInfoId() {
        if (mData != null) {
            return mData.getProjectInfo().getProjInfoId();
        }

        return "";
    }

    @Override
    public void onValueChange(BaseFormWidget widget, Object value, @Nullable Object item, boolean isEffective) {
        switch (widget.getElement().getElementCode()) {
            case "receive_mode":
                String mode = (String) value;
                if ("邮政快递".equals(mode)) {
                    mFormPresenter.setWidgetVisible("receive_type", true);

                    mFormPresenter.setWidgetVisible("receive_name", true);
                    mFormPresenter.setWidgetVisible("receive_phone", true);
                    mFormPresenter.setWidgetVisible("receive_addr", true);

                    mFormPresenter.setWidgetVisible("draw_name", false);
                    mFormPresenter.setWidgetVisible("draw_phone", false);
                    mFormPresenter.setWidgetVisible("draw_card_num", false);

                    agTextView1.setVisibility(View.VISIBLE);
                } else if ("窗口取证".equals(mode)) {
                    mFormPresenter.setWidgetVisible("receive_type", false);

                    mFormPresenter.setWidgetVisible("receive_name", false);
                    mFormPresenter.setWidgetVisible("receive_phone", false);
                    mFormPresenter.setWidgetVisible("receive_addr", false);

                    mFormPresenter.setWidgetVisible("draw_name", true);
                    mFormPresenter.setWidgetVisible("draw_phone", true);
                    mFormPresenter.setWidgetVisible("draw_card_num", true);

                    agTextView1.setVisibility(View.GONE);
                }
                break;
            case "linkmanName":
                String s = (String) value;
                for (ProjectDetailBean.ProjectInfoBean.LinkmanListBean lb : mData.getProjectInfo().getLinkmanList()) {
                    if (s.equals(lb.getLinkmanName())) {
                        mFormPresenter.setWidgetValue("linkmanMobilePhone1", lb.getLinkmanMobilePhone());
                        mFormPresenter.setWidgetValue("linkmanCertNo1", lb.getLinkmanCertNo());

                        mFormPresenter.setWidgetValue("draw_name", value);
                        mFormPresenter.setWidgetValue("draw_phone", lb.getLinkmanMobilePhone());
                        mFormPresenter.setWidgetValue("draw_card_num", lb.getLinkmanCertNo());

                        mFormPresenter.setWidgetValue("receive_name", value);
                        mFormPresenter.setWidgetValue("receive_phone", lb.getLinkmanMobilePhone());
                        mFormPresenter.setWidgetValue("receive_addr", lb.getLinkmanAddr());
                    }
                }
                break;
        }
    }
}
