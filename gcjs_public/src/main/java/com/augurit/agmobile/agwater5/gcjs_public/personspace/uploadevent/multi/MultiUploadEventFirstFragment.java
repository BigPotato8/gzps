package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.multi;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;


import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.ProjectReportBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.source.ReportRepository;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.form.view.ElementPosition;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.agmobile.busi.bpm.widget.WidgetListener;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.agmobile.busi.common.login.LoginManager;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.UploadProjectListAdapter.PROJECT;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent.uploadevent
 * Created by sdb on 2019/4/22  15:28.
 * Desc：
 */

public class MultiUploadEventFirstFragment extends AwFormFragment implements WidgetListener {
    //申明对象
    CityPickerView mPicker = new CityPickerView();
    private ProjectReportBean mPrb;
    private List<ProjectReportBean.LinkmanListBean> aList;
    private ReportRepository mRepository;
    private AgTextView agTextView1;

    @Override
    protected void initArguments() {
        super.initArguments();

        mFormCode = AwFormConfig.FORM_GCJS_PUBLIC_MULTI_FIRST_UPLOAD_EVENT;

        mPrb = (ProjectReportBean) getActivity().getIntent().getSerializableExtra(PROJECT);
        mRepository = new ReportRepository();
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("projectCode", mPrb.getLocalCode() == null ? "" : mPrb.getLocalCode());
        hashMap.put("token", LoginManager.getInstance().getCurrentUser().getUserType());
        mRepository.getProjectReportInfo(hashMap)
                .subscribe(projectReportBeanApiResult -> {
                    if (projectReportBeanApiResult.isSuccess()) {
                        mPrb = projectReportBeanApiResult.getData();
                        if (getActivity()!=null) {
                            ((MultiUploadEventActivity)getActivity()).projectReportBean = mPrb;
                        }

                        ProjectReportBean pNew = new ProjectReportBean();
                        BeanUtil.copyProperties(mPrb, pNew);
                        pNew.setLinkmanList(null);
                        pNew.setAeaUnitInfoList(null);
                        Map<String, String> map = jsonToMap(pNew);
                        map.put("unitName", mPrb.getUnitApplicant());

                        if (mRecord != null) {
                            mRecord.setValues(map);
                        } else {
                            FormRecord formRecord = new FormRecord();
                            formRecord.setValues(map);
                            mRecord = formRecord;
                        }
                        loadForm();
                    }
                }, throwable -> throwable.printStackTrace());

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

        aList = new ArrayList<>();
        List<IDictItem> names = new ArrayList<>();
//        String unitApplicant = mPrb.getUnitApplicant();
        if (!ListUtil.isEmpty(mPrb.getLinkmanList())) {
            for (ProjectReportBean.LinkmanListBean lb : mPrb.getLinkmanList()) {
//                if (unitApplicant.equals(lb.getApplicant())) {
                    aList.add(lb);
                    DictItem di = new DictItem();
                    di.setLabel(lb.getLinkmanName());
                    di.setValue(lb.getLinkmanInfoId());
                    names.add(di);
                }
//            }
        }
        AGMultiFunctionSpinner ac = (AGMultiFunctionSpinner) mFormPresenter.getWidget("applicator").getView();
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
                for (ProjectReportBean.LinkmanListBean lb : aList) {
                    if (s.toString().equals(lb.getLinkmanName())) {
                        mFormPresenter.setWidgetValue("linkmanMobilePhone", lb.getLinkmanMobilePhone());
                        mFormPresenter.setWidgetValue("linkmanidcard", lb.getLinkmanCertNo());

                        mFormPresenter.setWidgetValue("draw_name", lb.getLinkmanName());
                        mFormPresenter.setWidgetValue("draw_phone", lb.getLinkmanMobilePhone());
                        mFormPresenter.setWidgetValue("draw_card_num", lb.getLinkmanCertNo());

                        mFormPresenter.setWidgetValue("receive_name", lb.getLinkmanName());
                        mFormPresenter.setWidgetValue("receive_phone", lb.getLinkmanMobilePhone());
                        mFormPresenter.setWidgetValue("receive_addr", lb.getLinkmanAddr());
                    }
                }
            }
        });

        agTextView1 = new AgTextView(getActivity());
        agTextView1.setTextViewName("收件人地区");
        agTextView1.setHint("请选择收件人地区");
        agTextView1.showRequireTag(true);
        mFormPresenter.addViews("receive_addr", ElementPosition.ABOVE, agTextView1);

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

                //省份provinceFR
                //城市city
                //地区district
                agTextView1.setValue( province.getName() + "  " + city.getName() + "  " + district.getName());
            }

            @Override
            public void onCancel() {
            }
        });


        mFormPresenter.addWidgetListener(this);
    }

    private Map<String, String> jsonToMap(Object obj) {
        Gson gson = new Gson();
        String json = gson.toJson(obj);
        return gson.fromJson(json, new TypeToken<Map<String, String>>() {
        }.getType());
    }

    @Override
    public void onValueChange(BaseFormWidget baseFormWidget, Object o, @Nullable Object o1, boolean b) {
        String elementCode = baseFormWidget.getElement().getElementCode();
        switch (elementCode) {
            case "applicator":
                String s = (String) o;
                for (ProjectReportBean.LinkmanListBean lb : aList) {
                    if (s.equals(lb.getLinkmanName())) {
                        mFormPresenter.setWidgetValue("linkmanMobilePhone", lb.getLinkmanMobilePhone());
                        mFormPresenter.setWidgetValue("linkmanidcard", lb.getLinkmanCertNo());

                        mFormPresenter.setWidgetValue("draw_name", lb.getLinkmanName());
                        mFormPresenter.setWidgetValue("draw_phone", lb.getLinkmanMobilePhone());
                        mFormPresenter.setWidgetValue("draw_card_num", lb.getLinkmanCertNo());

                        mFormPresenter.setWidgetValue("receive_name", lb.getLinkmanName());
                        mFormPresenter.setWidgetValue("receive_phone", lb.getLinkmanMobilePhone());
                        mFormPresenter.setWidgetValue("receive_addr", lb.getLinkmanAddr());
                    }
                }
                break;
            case "receive_mode":
                String mode = (String) o;
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
        }
        if (getActivity() != null) {
            ((MultiUploadEventActivity) getActivity()).formValues =  mFormPresenter.getWidgetValues();//直接赋值给activity
            if (!mFormPresenter.getWidget("receive_type").isVisible()) {
                ((MultiUploadEventActivity)getActivity()).formValues.put("smsType","");//如果不可见，则为空
            }
        }

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


    @Override
    protected boolean shouldLoadImmediately() {
        return false;
    }
}
