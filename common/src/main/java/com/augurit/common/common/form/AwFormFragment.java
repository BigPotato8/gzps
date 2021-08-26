package com.augurit.common.common.form;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.augurit.common.common.manager.AwWidgetLayerRepository;
import com.augurit.agmobile.busi.bpm.BpmInjection;
import com.augurit.agmobile.busi.bpm.form.view.FormFragment;
import com.augurit.agmobile.busi.bpm.form.view.FormMapConfig;
import com.augurit.agmobile.busi.bpm.form.view.FormPresenter;
import com.augurit.agmobile.busi.bpm.form.view.FormView;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;

import javax.annotation.Nullable;

/**
 * 水务表单Fragment
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.form.view
 * @createTime 创建时间 ：2018/9/7
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class AwFormFragment extends FormFragment {

    public static AwFormFragment newInstance(String formCode, int formState, @Nullable FormRecord formRecord) {
        Bundle args = new Bundle();
        args.putString(EXTRA_FORM_CODE, formCode);
        args.putInt(EXTRA_FORM_STATE, formState);
        if (formRecord != null) {
            args.putSerializable(EXTRA_FORM_RECORD, formRecord);
        }

        AwFormFragment fragment = new AwFormFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initContract(ViewGroup container, ScrollView scrollView) {
        mFormView = new FormView(container, scrollView, new AwWidgetFactory());
        mFormPresenter = new FormPresenter(
                getContext(),
                mFormView,
                AgWaterInjection.provideFormRepository(getContext()),
                BpmInjection.provideRecordRepository(getContext()),
                AgWaterInjection.provideDictRepository(getContext()));
    }

    @Override
    protected FormMapConfig getMapConfig() {
        FormMapConfig mapConfig = super.getMapConfig();
        mapConfig.widgetLayerRepository = new AwWidgetLayerRepository();
        return mapConfig;
    }
}
