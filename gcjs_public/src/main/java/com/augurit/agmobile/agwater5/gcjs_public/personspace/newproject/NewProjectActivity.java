package com.augurit.agmobile.agwater5.gcjs_public.personspace.newproject;

import android.support.annotation.Nullable;
import android.view.View;

import com.augurit.agmobile.busi.bpm.widget.WidgetListener;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.common.common.form.AwFormActivity;
import com.augurit.common.common.form.AwFormConfig;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.perspace.uploadevent.uploadevent
 * Created by sdb on 2019/4/22  15:28.
 * Desc：
 */

public class NewProjectActivity extends AwFormActivity implements WidgetListener {


    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initArguments() {
        super.initArguments();

        mFormCode = AwFormConfig.FORM_GCJS_PUBLIC_NEW_PROJECT;

    }

    @Override
    public void onValueChange(BaseFormWidget widget, Object value, @Nullable Object item, boolean isEffective) {

    }

    @Override
    protected void onFormLoaded() {
        super.onFormLoaded();
        btn_save.setVisibility(View.GONE);
    }
}
