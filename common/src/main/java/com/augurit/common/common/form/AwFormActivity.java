package com.augurit.common.common.form;

import android.view.ViewGroup;
import android.widget.ScrollView;

import com.augurit.agmobile.busi.bpm.BpmInjection;
import com.augurit.agmobile.busi.bpm.form.view.FormActivity;
import com.augurit.agmobile.busi.bpm.form.view.FormMapConfig;
import com.augurit.agmobile.busi.bpm.form.view.FormPresenter;
import com.augurit.agmobile.busi.bpm.form.view.FormView;
import com.augurit.common.common.manager.AwWidgetLayerRepository;

/**
 * AgWater通用表单Activity
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.form.view
 * @createTime 创建时间 ：2018/8/29
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class AwFormActivity extends FormActivity {

    @Override
    protected void initContract(ViewGroup container, ScrollView scrollView) {
        mFormView = new FormView(container, scrollView, new AwWidgetFactory());
        mFormPresenter = new FormPresenter(
                this,
                mFormView,
                AgWaterInjection.provideFormRepository(this),
                BpmInjection.provideRecordRepository(this),
                AgWaterInjection.provideDictRepository(this));
    }

    @Override
    protected FormMapConfig getMapConfig() {
        FormMapConfig mapConfig = super.getMapConfig();
        mapConfig.widgetLayerRepository = new AwWidgetLayerRepository();
        return mapConfig;
    }
}
