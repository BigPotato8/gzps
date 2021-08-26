package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.augurit.agmobile.agwater5.gcjs.eventlist.model.PersonSelectBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.busi.bpm.form.view.FormState;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.combineview.AGFormButton;
import com.augurit.agmobile.common.view.widget.WEUIButton;
import com.augurit.common.common.form.AwFormActivity;
import com.augurit.common.common.form.AwFormConfig;

import java.util.HashMap;
import java.util.List;

//转办界面
public class TransferActivity extends AwFormActivity {
    public static final String TASK_ID = "TASK_ID";
    public static final int USER_REQUEST_CODE = 789;
    private String taskId;
    private String loginName;
    private String showText;
    EventRepository eventRepository;
    @Override
    protected void initArguments() {
        super.initArguments();
        mFormCode = AwFormConfig.FORM_GCJS_TRANSFER;
        mFormState = FormState.STATE_EDIT;
        mTitleText = "转发任务";
        taskId = getIntent().getStringExtra(TASK_ID);
        eventRepository = new EventRepository();
    }

    @Override
    protected void onFormLoaded() {
        super.onFormLoaded();
        btn_save.setVisibility(View.GONE);
        btn_submit.setText("确认");

        AGFormButton button = (AGFormButton)mFormPresenter.getWidget("button").getView();
        ((WEUIButton)button.getButton()).setText("请选择转办人");
        button.getButton().setOnClickListener(v -> {
            Intent intent = new Intent(TransferActivity.this,PersonSelectActivity.class);
            intent.putExtra(PersonSelectActivity.SELECT_NUM,1);
            startActivityForResult(intent,USER_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==USER_REQUEST_CODE && data!=null){
            List<PersonSelectBean> list = (List<PersonSelectBean>) data.getSerializableExtra(PersonSelectActivity.SELECT_LIST);
            if (!ListUtil.isEmpty(list)){
                PersonSelectBean personSelectBean = list.get(0);
                loginName = personSelectBean.getTextValue();
                showText = personSelectBean.getName()+"("+personSelectBean.getTextValue()+")";
                mFormPresenter.getWidget("user").setValue(showText);
            }
        }
    }

    @Override
    protected void submit() {
        if (mFormPresenter.validate()) {
            if (TextUtils.isEmpty(loginName) || TextUtils.isEmpty(showText)){
                ToastUtil.longToast(this,"请选择转发人");
                return;
            }
            HashMap<String, String> widgetValues = mFormPresenter.getWidgetValues();

            eventRepository.sendOnTask(widgetValues.get("comment"),loginName,showText,taskId)
                    .subscribe(apiResult -> {
                        if (apiResult.isSuccess()) {//转办成功
                            ToastUtil.longToast(TransferActivity.this,"转办成功");
                            setResult(EventApprovalActivity.FINISH_CODE);
                            finish();
                        }else{
                            if (TextUtils.isEmpty(apiResult.getMessage())) {
                                ToastUtil.longToast(TransferActivity.this,"转办失败");
                            }else {
                                ToastUtil.longToast(TransferActivity.this,apiResult.getMessage());
                            }
                        }
                    },throwable -> {
                        throwable.printStackTrace();
                        ToastUtil.longToast(TransferActivity.this,"转办失败");
                    });
        }
    }
}
