package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.multi;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;

import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.EventItemBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.MultiSituationBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.StageBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.ThemeAndMultiBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.source.MultiSecondConstant;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.source.MultiSecondPresenter;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.source.ReportRepository;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.form.util.ElementBuilder;
import com.augurit.agmobile.busi.bpm.form.util.FormBuilder;
import com.augurit.agmobile.busi.bpm.form.util.WidgetBuilder;
import com.augurit.agmobile.busi.bpm.form.view.ElementPosition;
import com.augurit.agmobile.busi.bpm.widget.WidgetListener;
import com.augurit.agmobile.busi.bpm.widget.WidgetType;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.combineview.AGMultiCheck;
import com.augurit.agmobile.common.view.combineview.AGMultiRadioGroup;
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.augurit.common.common.form.AwFormConfig;
import com.augurit.common.common.form.AwFormFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 主题    ->    阶段
 * 主题、阶段    ->    事项列表
 * 阶段、root    ->    一级情形
 * 阶段、父情形id    ->    子情形
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent.uploadevent
 * Created by sdb on 2019/4/22  15:28.
 * Desc：
 */

public class MultiUploadEventSecondFragment extends AwFormFragment implements MultiSecondConstant.View, WidgetListener {
    ReportRepository mRepository;
    MultiSecondConstant.Presenter mPresenter;
    public static final String THEME = "theme";
    public static final String STAGE = "stage";
    public static final String ITEM = "item";
    public static final String SITUATION = "situation";


    public List<DictItem> getSelectState() {
        List<BaseFormWidget> widgets = mFormPresenter.getWidgets(SITUATION, true);
        List<DictItem> list = new ArrayList<>();
        for (BaseFormWidget bfw : widgets) {
            if (bfw.getView() instanceof AGMultiRadioGroup) {
                AGMultiRadioGroup widget = (AGMultiRadioGroup) bfw.getView();
                if (!ListUtil.isEmpty(widget.getSelectedData())) {
                    for (IDictItem dictItem : widget.getSelectedData()) {
                        list.add((DictItem) dictItem);
                    }
                }
            }
        }
        return list;
    }

    @Override
    protected void initArguments() {
        super.initArguments();

        mFormCode = AwFormConfig.FORM_GCJS_PUBLIC_MULTI_SECOND_UPLOAD_EVENT;
        mPresenter = new MultiSecondPresenter(this);

    }

    @Override
    protected void onFormWillLoad() {
        super.onFormWillLoad();
        View view = View.inflate(getActivity(), R.layout.view_title_indicator, null);
        TextView textView = view.findViewById(R.id.tv_indicatetor_name);
        textView.setText("请选择情形");
        mFormContainer.addView(view);
    }

    @Override
    protected void onFormLoaded() {
        super.onFormLoaded();
        btn_container.setVisibility(View.GONE);
        btn_save.setVisibility(View.GONE);
        btn_submit.setVisibility(View.GONE);
        mFormPresenter.addWidgetListener(this);
        showLoadingDialog();
        mPresenter.getThemeAndOrg();
    }

    @Override
    public void setTheme(List<ThemeAndMultiBean.ThemesBean> themes) {//获取主题，默认选择第一个
        hideLoadingDialog();
        if (!ListUtil.isEmpty(themes)) {
            AGMultiRadioGroup agMultiCheck = (AGMultiRadioGroup) mFormPresenter.getWidget(THEME).getView();
            List<IDictItem> list = new ArrayList<>();
            for (ThemeAndMultiBean.ThemesBean bean : themes) {
                DictItem di = new DictItem();
                di.setLabel(bean.getThemeName());
                di.setValue(bean.getThemeId());
                list.add(di);
            }
            agMultiCheck.initData(list);
            agMultiCheck.setSelection(0);
            //mPresenter.getMultiState(themes.get(0).getThemeId());
        }
    }

    @Override
    public void setMultiStage(List<StageBean> data, String themeId) {//获取阶段，默认选择第一个
        hideLoadingDialog();
        if (!ListUtil.isEmpty(data)) {
            AGMultiCheck agMultiCheck = (AGMultiCheck) mFormPresenter.getWidget(STAGE).getView();
            List<IDictItem> list = new ArrayList<>();
            for (StageBean bean : data) {
                DictItem di = new DictItem();
                di.setLabel(bean.getStageName());
                di.setValue(bean.getStageId());
                list.add(di);
            }
            agMultiCheck.initData(list);
            agMultiCheck.setSelection(0);

            showLoadingDialog();
            mPresenter.getMultiSituation("root", data.get(0).getStageId(), SITUATION);
            mPresenter.getMultiEventList(themeId, data.get(0).getStageId());
        }
    }

    //加载情形
    @Override
    public void setMultiSituation(List<MultiSituationBean> data, String parentId, String elementCode) {
        hideLoadingDialog();

        generateForm(elementCode, data);

        //获取所有选中数据
        List<BaseFormWidget> widgets = mFormPresenter.getWidgets(SITUATION, true);
        AGMultiCheck agMultiCheck = (AGMultiCheck) mFormPresenter.getWidget(STAGE).getView();
        List<String> list = new ArrayList<>();
        for (BaseFormWidget bfw : widgets) {
            if (bfw.getView() instanceof AGMultiRadioGroup) {
                AGMultiRadioGroup widget = (AGMultiRadioGroup) bfw.getView();
                String value = widget.getValue();
                if (!TextUtils.isEmpty(value)) {
                    list.add(value);
                }
            }
        }
        if (getActivity() != null && getActivity() instanceof MultiUploadEventActivity) {
            ((MultiUploadEventActivity) getActivity()).stateIds = list;
            EventBus.getDefault().post(MultiUploadEventThirdFragment.TO_REFLASH);
        }

    }

    //加载事项列表
    @Override
    public void setMultiEventList(List<EventItemBean> data) {
        hideLoadingDialog();
        if (!ListUtil.isEmpty(data)) {
            ((MultiUploadEventActivity) getActivity()).eventItemBeans = data;//将事项列表发送给首页

            AGMultiCheck agMultiCheck = (AGMultiCheck) mFormPresenter.getWidget(ITEM).getView();
            List<IDictItem> list = new ArrayList<>();
            for (EventItemBean bean : data) {
                DictItem di = new DictItem();
                di.setLabel(bean.getItemName());
                di.setValue(bean.getItemVerId());
                list.add(di);
            }
            agMultiCheck.initData(list);
        }
    }

    private void showLoadingDialog() {
        if (getActivity() != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
//            ProgressDialogUtil.showProgressDialog(getActivity(),false);
        }
    }

    private void hideLoadingDialog() {
//        ProgressDialogUtil.dismissAll();
    }

    @Override
    public void onValueChange(BaseFormWidget widget, Object value, @Nullable Object item, boolean isEffective) {
        String elementCode = widget.getElement().getElementCode();
        switch (elementCode) {
            case THEME://主题，根据主题获取阶段
                AGMultiCheck stageG = (AGMultiCheck) mFormPresenter.getWidget(STAGE).getView();//清空阶段
                stageG.initData(null);
                AGMultiCheck stageI = (AGMultiCheck) mFormPresenter.getWidget(ITEM).getView();//清空事项
                stageI.initData(null);
                AGMultiRadioGroup stageSu = (AGMultiRadioGroup) mFormPresenter.getWidget(SITUATION).getView();//清空情形
                stageSu.initData(null);

                showLoadingDialog();
                mPresenter.getMultiStage(((DictItem) item).getValue());//加载阶段
                break;
            case STAGE://阶段,根据主题阶段获取情形列表
                if (isEffective) {
                    if (getActivity() != null && getActivity() instanceof MultiUploadEventActivity) {
                        ((MultiUploadEventActivity) getActivity()).stageId = ((DictItem) item).getValue();
                        ((MultiUploadEventActivity) getActivity()).stateIds = new ArrayList<>();//清空情形id
                        EventBus.getDefault().post(MultiUploadEventThirdFragment.TO_REFLASH);
                    }
                }

                AGMultiCheck stageItem = (AGMultiCheck) mFormPresenter.getWidget(ITEM).getView();//清空事项
                stageItem.initData(null);
                removeElements(SITUATION);//清空情形子项
                AGMultiRadioGroup stageSituation = (AGMultiRadioGroup) mFormPresenter.getWidget(SITUATION).getView();//清空一级情形
                stageSituation.initData(null);

                AGMultiRadioGroup checkTheme = (AGMultiRadioGroup) mFormPresenter.getWidget(THEME).getView();
                String s = checkTheme.getValue();

                showLoadingDialog();
                mPresenter.getMultiEventList(s, ((DictItem) item).getValue());//加载事项列表
                mPresenter.getMultiSituation("root", ((DictItem) item).getValue(), SITUATION);//加载一级情形
                break;
            case ITEM://事项列表，无点击事件

                break;
            case SITUATION://情形，根据情形获取子情形
                AGMultiCheck checkStage = (AGMultiCheck) mFormPresenter.getWidget(STAGE).getView();//获取阶段id
                String stageId = checkStage.getValue();

                showLoadingDialog();

                mPresenter.getMultiSituation(((DictItem) item).getValue(), stageId, elementCode);//加载二级情形
                break;
            default://子情形id，根据子情形获取子情形
                removeElements(elementCode);//清空情形子项
                AGMultiCheck check = (AGMultiCheck) mFormPresenter.getWidget(STAGE).getView();//清空事项
                String stage = check.getValue();

                showLoadingDialog();
                mPresenter.getMultiSituation(((DictItem) item).getValue(), stage, elementCode);//加载二级情形
                break;
        }
    }


    private void generateForm(String summoner, List<MultiSituationBean> list) {

        // 根据返回结果构造元素列表
        FormBuilder formBuilder = new FormBuilder();
        for (MultiSituationBean data : list) {
            // 字典
            ArrayList<DictItem> dictItems = new ArrayList<>();
            for (MultiSituationBean.AnswerListBean bean : data.getAnswerList()) {
                DictItem dictItem = new DictItem();
                dictItem.setLabel(bean.getStateName());
                dictItem.setValue(bean.getParStateId());
                dictItem.setParentTypeCode(bean.getParentStateId());
                dictItems.add(dictItem);
            }
            // 元素
            formBuilder.addElement(new ElementBuilder(data.getQuestionState().getParStateId())
                    .canSummon(true)    // 如果该元素还能生成元素，则为true，如果不能则不需设置
                    .summoner(summoner) // 标识该元素的生成者，用于移除
                    .widget(new WidgetBuilder(WidgetType.RADIO_CHECKBOX)
                            .title(data.getQuestionState().getStateName())
                            .columnCount(1)
                            .maxLimit(1)
                            .isRequired(true)
                            .dictArray(dictItems)
                            .build())
                    .build());

        }
        if (!TextUtils.isEmpty(summoner)) {
            // 移除之前生成的
            mFormPresenter.removeElements(summoner, true);
        }
        // 添加
        mFormPresenter.addElements(summoner, ElementPosition.BELOW, formBuilder.build().getElements());

    }

    /**
     * 级联移除某个元素产生的所有关联元素（不移除该元素自身）
     *
     * @param summoner 元素编号
     */
    private void removeElements(String summoner) {
        mFormPresenter.removeElements(summoner, true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.stopRequest();
    }

    public boolean validateForm() {
        return mFormPresenter.validate();
    }
}
