package com.augurit.agmobile.agwater5.gcjs.publicaffair;


import com.augurit.agmobile.busi.bpm.form.view.FormState;
import com.augurit.common.common.form.AwFormActivity;
import com.augurit.common.common.form.AwFormConfig;

/**
 * com.augurit.agmobile.agwater5.gcjs.publicaffair
 * Created by sdb on 2019/4/2  9:23.
 * Desc：
 */

public class GcjsPublicAffairDetailActivity extends AwFormActivity {

    @Override
    protected void initArguments() {
        super.initArguments();
        // 在此设置指定的表单编号，也可通过bundle传入（key为 FormActivity.EXTRA_FORM_CODE）
        mFormCode = AwFormConfig.FORM_GCJS_DECLARE_DETAIL;

        mFormState = FormState.STATE_READ;
    }
}
