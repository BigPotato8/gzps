package com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.single;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;

import com.augurit.agmobile.agwater5.gcjs_public.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs_public.common.GcjsPuUrlConstant;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.EventItemBean;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.single.model.Situation;
import com.augurit.agmobile.busi.bpm.dict.util.DictBuilder;
import com.augurit.agmobile.busi.bpm.form.util.ElementBuilder;
import com.augurit.agmobile.busi.bpm.form.util.FormBuilder;
import com.augurit.agmobile.busi.bpm.form.util.WidgetBuilder;
import com.augurit.agmobile.busi.bpm.form.view.ElementPosition;
import com.augurit.agmobile.busi.bpm.widget.WidgetListener;
import com.augurit.agmobile.busi.bpm.widget.WidgetType;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.augurit.common.common.form.AwFormFragment;
import com.augurit.common.common.http.HttpUtil;
import com.augurit.common.common.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.eventlist.EventListFragment.EVENT_ITEM;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent.uploadevent
 * Created by sdb on 2019/4/22  15:28.
 * Desc：
 */

public class SingleUploadEventSecondFragment extends AwFormFragment implements WidgetListener {

    private EventItemBean mEventItemBean;
    protected CompositeDisposable mCompositeDisposable;

    @Override
    protected void initArguments() {
        super.initArguments();
        mCompositeDisposable = new CompositeDisposable();
//        mFormCode = GcjsFormConfig.FORM_EMPTY;
        mEventItemBean = (EventItemBean) getActivity().getIntent().getSerializableExtra(EVENT_ITEM);
        getSingleSeries(true, "", "");

    }

    @Override
    protected void onFormWillLoad() {
        super.onFormWillLoad();
        View view = View.inflate(getActivity(), R.layout.view_title_indicator, null);
        TextView textView = view.findViewById(R.id.tv_indicatetor_name);
        textView.setText("请选择情形");
        mFormContainer.addView(view);

        btn_container.setVisibility(View.GONE);
        btn_save.setVisibility(View.GONE);
        btn_submit.setVisibility(View.GONE);

        mFormPresenter.addWidgetListener(this);
    }

    @Override
    protected void onFormLoaded() {
        super.onFormLoaded();
    }


    private void getSingleSeries(boolean isParent, String summoner, String itemStateId) {
//        showLoadingDialog();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("isParent", "" + isParent);
        hashMap.put("itemStateId", itemStateId);
        hashMap.put("itemVerId", mEventItemBean.getItemVerId());
        hashMap.put("token", LoginManager.getInstance().getCurrentUser().getUserType());
        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.SINGLE_SERIES, hashMap)
                .subscribe(s -> {
//                    hideLoadingDialog();

                    if (StringUtils.isJson(s)) {
                        ApiResult<Situation> apiResult = new Gson().fromJson(s, new TypeToken<ApiResult<Situation>>() {
                        }.getType());

                        if (apiResult != null && apiResult.getData() != null
                                && !ListUtil.isEmpty(apiResult.getData().getStateQuestions())) {
                            generateForm(summoner, apiResult);
                        } else {
                            if (!TextUtils.isEmpty(summoner)) {
                                removeElements(summoner);
                            }
                        }
                    } else {
                        if (!TextUtils.isEmpty(summoner)) {
                            removeElements(summoner);
                        }
                    }

                }, Throwable::printStackTrace);

        mCompositeDisposable.add(subscribe);

    }

    private void generateForm(String summoner, ApiResult<Situation> apiResult) {
        List<Situation.StateQuestionsBean> stateQuestions = apiResult.getData().getStateQuestions();
        // 根据返回结果构造元素列表

        for (Situation.StateQuestionsBean data : stateQuestions) {
            FormBuilder formBuilder = new FormBuilder();

            Situation.StateQuestionsBean.QuestionBean question = data.getQuestion();
            List<Situation.StateQuestionsBean.AnswersBean> answers = data.getAnswers();
            // 字典
            DictBuilder dictBuilder = new DictBuilder();
            for (Situation.StateQuestionsBean.AnswersBean answer : answers) {
                dictBuilder.item(answer.getStateName(), answer.getItemStateId());
            }
            // 元素
            formBuilder.addElement(new ElementBuilder(question.getItemStateId())
                    .canSummon(true)    // 如果该元素还能生成元素，则为true，如果不能则不需设置
                    .summoner(summoner) // 标识该元素的生成者，用于移除
                    .widget(new WidgetBuilder(WidgetType.RADIO_CHECKBOX)
                            .title(question.getStateName())
                            .columnCount(1)
                            .maxLimit(1)
                            .isRequired("1".equals(question.getMustAnswer()))
                            .addProperty("data", apiResult.getData().getAeaItemMats())
                            .dictArray(dictBuilder.build())
                            .build())
                    .build());

            if (!TextUtils.isEmpty(summoner)) {
                // 移除之前生成的
                mFormPresenter.removeElements(summoner, true);
            }
            // 添加
            mFormPresenter.addElements(summoner, ElementPosition.BELOW, formBuilder.build().getElements());
        }


    }

    private void showLoadingDialog() {
        ProgressDialogUtil.showProgressDialog(getActivity(), false);
    }

    private void hideLoadingDialog() {
        ProgressDialogUtil.dismissAll();
    }

    @Override
    public void onValueChange(BaseFormWidget widget, Object value, @Nullable Object item, boolean isEffective) {
        String elementCode = widget.getElement().getElementCode();
        boolean canSummon = widget.getElement().getCanSummon();
        if (canSummon) {
            if (isEffective) {
                // todo ((IDictItem) item).getValue() 暂时用这个
                getSingleSeries(false, elementCode, ((IDictItem) item).getValue());
            } else {
                removeElements(elementCode);
            }
        }
    }


    /**
     * 级联移除某个元素产生的所有关联元素（不移除该元素自身）
     *
     * @param summoner 元素编号
     */
    private void removeElements(String summoner) {
        mFormPresenter.removeElements(summoner, true);
    }


    public List<Situation.AeaItemMatsBean> postMaterialList() {
        HashMap<String, String> widgetValues = mFormPresenter.getWidgetValues();

        List<Situation.AeaItemMatsBean> aeaItemMatsBeanList = new ArrayList<>();
        for (String keyCode : widgetValues.keySet()) {
            List<Situation.AeaItemMatsBean> list = (List<Situation.AeaItemMatsBean>) mFormPresenter.getWidget(keyCode)
                    .getElement().getWidget().getObjectProperty("data");
            if (!ListUtil.isEmpty(list)) {
                aeaItemMatsBeanList.addAll(list);
            }
        }

        return aeaItemMatsBeanList;
//        EventBus.getDefault().post(aeaItemMatsBeanList);

    }

    public String getStageList() {
        HashMap<String, String> widgetValues = mFormPresenter.getWidgetValues();
        List<StateBean> list = new ArrayList<>();
        for (String questionId : widgetValues.keySet()) {
            if (!TextUtils.isEmpty(widgetValues.get(questionId))) {
                StateBean stateBean = new StateBean(questionId, widgetValues.get(questionId));
                list.add(stateBean);
            }
        }

        if (!ListUtil.isEmpty(list)) {
            return new Gson().toJson(list);
        } else {
            return "";
        }
    }

    class StateBean {
        StateBean(String questionId, String answerId) {
            this.questionId = questionId;
            this.answerId = answerId;
        }

        String questionId;
        String answerId;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    public boolean validateForm() {
        return mFormPresenter.validate();
    }

}
