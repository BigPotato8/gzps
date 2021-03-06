package com.augurit.agmobile.agwater5.gcjspad;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ListFilterBean;
import com.augurit.agmobile.agwater5.gcjspad.event.FilterBeanEvent;
import com.augurit.agmobile.agwater5.gcjspad.mine.message.MessageListPadFragment;
import com.augurit.agmobile.agwater5.gcjspad.homepage.UserBean;
import com.augurit.agmobile.agwater5.gcjspad.mine.notice.NoticeListPadFragment;
import com.augurit.agmobile.agwater5.gcjspad.widget.PadEditText2;
import com.augurit.agmobile.agwater5.im.IMConstant;
import com.augurit.agmobile.agwater5.login.LoginActivity;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.form.util.ElementBuilder;
import com.augurit.agmobile.busi.bpm.form.util.FormBuilder;
import com.augurit.agmobile.busi.bpm.form.util.WidgetBuilder;
import com.augurit.agmobile.busi.bpm.view.model.ViewInfo;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.common.im.timchat.manager.IMManager;
import com.augurit.agmobile.common.lib.json.JsonUtil;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.combineview.AGEditText;
import com.augurit.agmobile.common.view.combineview.AGMultiSpinner;
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.augurit.agmobile.common.view.widget.WEUIButton;
import com.augurit.common.common.util.ProgressDialogUtil;
import com.augurit.common.dict.DictUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.augurit.agmobile.busi.bpm.widget.WidgetType.EDITTEXT;
import static com.augurit.agmobile.busi.bpm.widget.WidgetType.SPINNER;
import static com.augurit.agmobile.busi.bpm.widget.WidgetType.TIME_INTERVAL;

/**
 * ???????????????
 */
public class MainPadActivity extends BasePadFilterActivity {
    private ImageView iv_user_info;
    private ImageView iv_message;
    private ImageView iv_notice;
    private Fragment mMainFragment;
    private NoticeListPadFragment noticeListPadFragment;
    private ViewGroup ll_personal_info;
    private ViewGroup ll_personal_pwd;
    private MessageListPadFragment messageListPadFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //???????????????
        mMainFragment = MainPadFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, mMainFragment).commit();
        iv_user_info = findViewById(R.id.iv_user_info);
        iv_message = findViewById(R.id.iv_message);
        iv_notice = findViewById(R.id.iv_notice);
        iv_user_info.setOnClickListener(v -> {
            View view = View.inflate(this, R.layout.layout_personal_setting_pad, null);
            AlertDialog alertDialogChoose = new AlertDialog.Builder(this)
                    .setView(view)
                    .create();
            alertDialogChoose.setCancelable(false);
            ImageView iv_close = view.findViewById(R.id.iv_close);
            TextView tv_username = view.findViewById(R.id.tv_username);
            TextView tv_login_name = view.findViewById(R.id.tv_login_name);
            TextView tv_department = view.findViewById(R.id.tv_department);
            WEUIButton quitBtn = view.findViewById(R.id.btn_quit);
            ll_personal_info = view.findViewById(R.id.ll_personal_info);
            ll_personal_pwd = view.findViewById(R.id.ll_personal_pwd);
            String userName = LoginManager.getInstance().getCurrentUser().getUserName();
            String loginName = LoginManager.getInstance().getCurrentUser().getLoginName();
            String orgName = LoginManager.getInstance().getCurrentUser().getOrgName();
            tv_username.setText(userName);
            tv_login_name.setText(loginName);
            tv_department.setText(orgName);
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogChoose.dismiss();
                }
            });
            quitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogChoose.dismiss();
                    LoginManager.getInstance().logoff();
                    if (IMConstant.IS_ENABLED) {
                        IMManager.getInstance().logout(null, null);
                    }
                    Intent intent = new Intent(MainPadActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
            ll_personal_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogChoose.dismiss();
                    doGetPersonalInfo();
                }
            });
            ll_personal_pwd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogChoose.dismiss();
                    doModifyPwd();
                }
            });
            alertDialogChoose.show();

        });
        iv_notice.setOnClickListener(v -> {
            //todo ??????
            if (noticeListPadFragment == null) {
                noticeListPadFragment = new NoticeListPadFragment();
            }
            if (getSupportFragmentManager().getFragments().contains(noticeListPadFragment)) {
                return;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, noticeListPadFragment).commit();
        });
        iv_message.setOnClickListener(v -> {
            //todo ??????
            if (messageListPadFragment == null) {
                messageListPadFragment = new MessageListPadFragment();
            }
            if (getSupportFragmentManager().getFragments().contains(messageListPadFragment)) {
                return;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.fl_content, messageListPadFragment).commit();
        });
        initFilterDatas();
    }

    private void doGetPersonalInfo() {
        ProgressDialogUtil.showProgressDialog(MainPadActivity.this, false);
        HashMap<String, String> map = new HashMap<>();
        map.put("loginName", LoginManager.getInstance().getCurrentUser().getLoginName());
        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_NEW_USER_INFO, map)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        ProgressDialogUtil.dismissAll();
                        ApiResult<UserBean> resultApi = new Gson().fromJson(s, new TypeToken<ApiResult<UserBean>>() {
                        }.getType());
                        UserBean data = resultApi.getData();
                        if (data != null) {
                            showPersonalInfo(data);
                        } else {
                            ToastUtil.shortToast(MainPadActivity.this, "????????????????????????");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ProgressDialogUtil.dismissAll();
                        ToastUtil.shortToast(MainPadActivity.this, "????????????????????????");
                    }
                });
        compositeDisposable.add(subscribe);

    }

    private void showPersonalInfo(UserBean data) {
        View view = View.inflate(this, R.layout.layout_personal_info_pad, null);
        AlertDialog alertDialogChoose = new AlertDialog.Builder(this)
                .setView(view)
                .create();
        Button cancel = view.findViewById(R.id.btn_cancel);
        Button save = view.findViewById(R.id.btn_save);
        PadEditText2 infoName = view.findViewById(R.id.pe_info_name);
        PadEditText2 infoNum = view.findViewById(R.id.pe_info_number);
        PadEditText2 infoAddr = view.findViewById(R.id.pe_info_addr);
        PadEditText2 infoRegistered = view.findViewById(R.id.pe_info_registered);
        PadEditText2 infoPhone = view.findViewById(R.id.pe_info_phone);
        PadEditText2 infoMail = view.findViewById(R.id.pe_info_mail);
        PadEditText2 infoPosition = view.findViewById(R.id.pe_info_position);
        infoName.setEnable(false);
        infoNum.setEnable(false);
        infoName.setValue(data.getUserName());
        infoNum.setValue(data.getUserCode());
        infoAddr.setValue(data.getUserFamilyAddress());
        infoRegistered.setValue(data.getUserDomicilePlace());
        infoPhone.setValue(data.getUserMobile());
        infoMail.setValue(data.getUserEmail());
        infoPosition.setValue(data.getUserPost());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogChoose.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogChoose.dismiss();
                HashMap<String, String> personalInfo = new HashMap<>();
                personalInfo.put("userName", infoName.getValue());
                personalInfo.put("userCode", infoNum.getValue());
                personalInfo.put("userFamilyAddress", infoAddr.getValue());
                personalInfo.put("userDomicilePlace", infoRegistered.getValue());
                personalInfo.put("userMobile", infoPhone.getValue());
                personalInfo.put("userEmail", infoMail.getValue());
                personalInfo.put("userPost", infoPosition.getValue());
                personalInfo.put("userId", data.getUserId());
                savePersonalInfo(personalInfo);
            }
        });
        alertDialogChoose.show();
    }

    private void savePersonalInfo(HashMap<String, String> personalInfo) {
        ProgressDialogUtil.showProgressDialog(MainPadActivity.this, false);
        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).post(GcjsUrlConstant.SAVE_NEW_USER_INFO, personalInfo)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        ProgressDialogUtil.dismissAll();
                        ApiResult<UserBean> resultApi = new Gson().fromJson(s, new TypeToken<ApiResult<UserBean>>() {
                        }.getType());
                        if (resultApi.isSuccess()) {
                            ToastUtil.shortToast(MainPadActivity.this, "????????????");
                        } else {
                            ToastUtil.shortToast(MainPadActivity.this, "????????????");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ProgressDialogUtil.dismissAll();
                        ToastUtil.shortToast(MainPadActivity.this, "????????????");
                    }
                });
        compositeDisposable.add(subscribe);
    }


    private void doModifyPwd() {
        View view = View.inflate(this, R.layout.layout_personal_pwd_pad, null);
        AlertDialog alertDialogChoose = new AlertDialog.Builder(this)
                .setView(view)
                .create();
        Button cancel = view.findViewById(R.id.btn_cancel);
        Button save = view.findViewById(R.id.btn_save);
        PadEditText2 pwd_original = view.findViewById(R.id.pe_pwd_original);
        PadEditText2 pwd_new_pwd = view.findViewById(R.id.pe_pwd_new_pwd);
        PadEditText2 pwd_new_pwd_again = view.findViewById(R.id.pe_pwd_new_pwd_again);
        pwd_original.setInputType(AGEditText.TEXT_PASSWORD);
        pwd_new_pwd.setInputType(AGEditText.TEXT_PASSWORD);
        pwd_new_pwd_again.setInputType(AGEditText.TEXT_PASSWORD);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogChoose.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String originalPwd = pwd_original.getValue();
                String newPwd = pwd_new_pwd.getValue();
                String newPwdAgain = pwd_new_pwd_again.getValue();
                if (TextUtils.isEmpty(originalPwd)) {
                    ToastUtil.shortToast(MainPadActivity.this, "??????????????????");
                    return;
                }
                if (TextUtils.isEmpty(newPwd)) {
                    ToastUtil.shortToast(MainPadActivity.this, "??????????????????");
                    return;
                }
                if (TextUtils.isEmpty(newPwdAgain)) {
                    ToastUtil.shortToast(MainPadActivity.this, "????????????????????????");
                    return;
                }
                if (!newPwd.equals(newPwdAgain)) {
                    ToastUtil.shortToast(MainPadActivity.this, "??????????????????????????????????????????????????????");
                    return;
                }
                HashMap<String, String> checkOldPwdMap = new HashMap<>();
                checkOldPwdMap.put("oldPassword", originalPwd);
                HashMap<String, String> modifyPwdMap = new HashMap<>();
                modifyPwdMap.put("newPassword", newPwd);
                modifyPwdMap.put("oldPassword", originalPwd);
                ProgressDialogUtil.showProgressDialog(MainPadActivity.this, false);
                Observable<String> checkOldPwd = HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_CHECK_OLD_PWD, checkOldPwdMap);
                Observable<String> modifyPwd = HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).putParmas(GcjsUrlConstant.PUT_MODIFY_PWD, modifyPwdMap);
                Disposable subscribe = checkOldPwd.flatMap((Function<String, ObservableSource<String>>) s -> {
                    JSONObject jsonObject = new JSONObject(s);
                    boolean isSuccess = false;
                    if (jsonObject.has("success")) {
                        isSuccess = jsonObject.getBoolean("success");
                    }
                    if (isSuccess) {
                        return modifyPwd;
                    }
                    ProgressDialogUtil.dismissAll();
                    ToastUtil.shortToast(MainPadActivity.this, "?????????????????????????????????");
                    return Observable.empty();
                }).subscribe(s -> {
                    JSONObject jsonObject = new JSONObject(s);
                    boolean isSuccess = false;
                    if (jsonObject.has("success")) {
                        isSuccess = jsonObject.getBoolean("success");
                    }
                    if (isSuccess) {
                        ToastUtil.shortToast(MainPadActivity.this, "??????????????????");
                    } else {
                        ToastUtil.shortToast(MainPadActivity.this, "??????????????????");
                    }
                    ProgressDialogUtil.dismissAll();
                    alertDialogChoose.dismiss();
                }, throwable -> {
                    throwable.printStackTrace();
                    ToastUtil.shortToast(MainPadActivity.this, "??????????????????");
                    ProgressDialogUtil.dismissAll();
                    alertDialogChoose.dismiss();
                });
                compositeDisposable.add(subscribe);
            }
        });
        alertDialogChoose.show();
    }

    private void initFilterDatas() {
        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_EVENT_LIST_FILTER_INFO, new HashMap<>())
                .onErrorReturnItem("")
                .map(s -> {
                    ApiResult<ListFilterBean> data = new Gson().fromJson(s, new TypeToken<ApiResult<ListFilterBean>>() {
                    }.getType());
                    return data;
                })
                .subscribe(apiResult -> {
                    if (apiResult.isSuccess()) {
                        ListFilterBean data = apiResult.getData();
                        if (data == null) {
                            return;
                        }
                        if (!ListUtil.isEmpty(data.getThemeList())) {
                            List<IDictItem> list = new ArrayList<>();
                            for (ListFilterBean.ThemeListBean themeListBean : data.getThemeList()) {
                                DictItem dictItem = new DictItem();
                                dictItem.setLabel(themeListBean.getThemeName());
                                dictItem.setValue(themeListBean.getThemeId());
                                list.add(dictItem);
                            }
                            for (Map.Entry<String, ViewInfo> entry : getMainViewInfos().entrySet()) {
                                AGMultiSpinner themeSpinner = (AGMultiSpinner) getFilterWidget(entry.getKey(), "theme").getView();
                                themeSpinner.initData(list);
                            }
                        }
//                        if (!ListUtil.isEmpty(data.getInstStateList())) {
//                            List<IDictItem> list = new ArrayList<>();
//                            DictItem dictItemdd = new DictItem();
//                            dictItemdd.setLabel("??????");
//                            dictItemdd.setValue("");
//                            list.add(dictItemdd);
//                            for (ListFilterBean.InstStateListBean instStateListBean : data.getInstStateList()) {
//                                DictItem dictItem = new DictItem();
//                                dictItem.setLabel(instStateListBean.getItemName());
//                                dictItem.setValue(instStateListBean.getItemCode());
//                                list.add(dictItem);
//                            }
//                            AGMultiCheck mAGMultiCheck = (AGMultiCheck) getFilterWidget("1", "instState").getView();
//                            mAGMultiCheck.initData(list);
//                        }
//                        //applySource
//                        if (!ListUtil.isEmpty(data.getApplySourceList())) {
//                            List<IDictItem> list = new ArrayList<>();
//                            DictItem dictItemdd = new DictItem();
//                            dictItemdd.setLabel("??????");
//                            dictItemdd.setValue("");
//                            list.add(dictItemdd);
//                            for (ListFilterBean.ApplySourceListBean applySourceListBean : data.getApplySourceList()) {
//                                DictItem dictItem = new DictItem();
//                                dictItem.setLabel(applySourceListBean.getItemName());
//                                dictItem.setValue(applySourceListBean.getItemCode());
//                                list.add(dictItem);
//                            }
//                            AGMultiCheck mAGMultiCheck = (AGMultiCheck) getFilterWidget("1", "applySource").getView();
//                            mAGMultiCheck.initData(list);
//                        }
//                        //applyType
//                        if (!ListUtil.isEmpty(data.getApplyTypeList())) {
//                            List<IDictItem> list = new ArrayList<>();
//                            DictItem dictItemdd = new DictItem();
//                            dictItemdd.setLabel("??????");
//                            dictItemdd.setValue("");
//                            list.add(dictItemdd);
//                            for (ListFilterBean.ApplyTypeListBean applyTypeListBean : data.getApplyTypeList()) {
//                                DictItem dictItem = new DictItem();
//                                dictItem.setLabel(applyTypeListBean.getItemName());
//                                dictItem.setValue(applyTypeListBean.getItemCode());
//                                list.add(dictItem);
//                            }
//                            AGMultiCheck mAGMultiCheck = (AGMultiCheck) getFilterWidget("1", "applyType").getView();
//                            mAGMultiCheck.initData(list);
//                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                });
        compositeDisposable.add(subscribe);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_pad;
    }

    @Override
    protected Map<String, ViewInfo> getMainViewInfos() {
        HashMap<String, ViewInfo> viewInfoHashMap = new HashMap<>();
        viewInfoHashMap.put("????????????", getHandingViewInfo());//??????????????????????????????
        viewInfoHashMap.put("????????????", getHandedViewInfo());//????????????????????????
        viewInfoHashMap.put("????????????", getMyAllListViewInfo());//??????????????????????????????
        viewInfoHashMap.put("????????????", getRejectViewInfo());
        viewInfoHashMap.put("????????????", getApplyInfo());
        viewInfoHashMap.put("????????????", getApplyInfo());
        return viewInfoHashMap;
    }

    //??????????????????????????????
    private ViewInfo getHandingViewInfo() {
        ViewInfo viewInfo = new FormBuilder()
                .addElement(new ElementBuilder("keyword")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("?????????")
                                .hint("??????????????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("theme")
                        .widget(new WidgetBuilder(SPINNER)
                                .title("????????????")
                                .maxLimit(1)
                                .hint("?????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("approvalStage")
                        .widget(new WidgetBuilder(SPINNER)
                                .title("????????????")
                                .maxLimit(1)
                                .initData(DictUtil.getApprovalStage())
                                .hint("?????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("taskName")
                        .widget(new WidgetBuilder(SPINNER)
                                .title("????????????")
                                .maxLimit(1)
                                .initData(DictUtil.getTaskName())
                                .hint("?????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("applyTime")
                        .widget(new WidgetBuilder(TIME_INTERVAL)
                                .title("????????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("arriveTime")
                        .widget(new WidgetBuilder(TIME_INTERVAL)
                                .title("????????????")
                                .build())
                        .build())
                .buildAsViewInfo();
        return viewInfo;
    }

    private ViewInfo getHandedViewInfo() {
        ViewInfo viewInfo = new FormBuilder()
                .addElement(new ElementBuilder("keyword")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("?????????")
                                .hint("??????????????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("theme")
                        .widget(new WidgetBuilder(SPINNER)
                                .title("????????????")
                                .maxLimit(1)
                                .hint("?????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("approvalStage")
                        .widget(new WidgetBuilder(SPINNER)
                                .title("????????????")
                                .maxLimit(1)
                                .initData(DictUtil.getApprovalStage())
                                .hint("?????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("applyTime")
                        .widget(new WidgetBuilder(TIME_INTERVAL)
                                .title("????????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("processTime")
                        .widget(new WidgetBuilder(TIME_INTERVAL)
                                .title("??????????????????")
                                .build())
                        .build())
                .buildAsViewInfo();
        return viewInfo;
    }

    private ViewInfo getFinishedViewInfo() {
        ViewInfo viewInfo = new FormBuilder()
                .addElement(new ElementBuilder("keyword")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("?????????")
                                .hint("??????????????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("theme")
                        .widget(new WidgetBuilder(SPINNER)
                                .title("????????????")
                                .maxLimit(1)
                                .hint("?????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("approvalStage")
                        .widget(new WidgetBuilder(SPINNER)
                                .title("????????????")
                                .maxLimit(1)
                                .initData(DictUtil.getApprovalStage())
                                .hint("?????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("acceptTime")
                        .widget(new WidgetBuilder(TIME_INTERVAL)
                                .title("????????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("concludeTime")
                        .widget(new WidgetBuilder(TIME_INTERVAL)
                                .title("??????????????????")
                                .build())
                        .build())
                .buildAsViewInfo();
        return viewInfo;
    }

    private ViewInfo getMyAllListViewInfo() {
        ViewInfo viewInfo = new FormBuilder()
                .addElement(new ElementBuilder("keyword")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("?????????")
                                .hint("??????????????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("theme")
                        .widget(new WidgetBuilder(SPINNER)
                                .title("????????????")
                                .maxLimit(1)
                                .hint("?????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("approvalStage")
                        .widget(new WidgetBuilder(SPINNER)
                                .title("????????????")
                                .maxLimit(1)
                                .initData(DictUtil.getApprovalStage())
                                .hint("?????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("applyTime")
                        .widget(new WidgetBuilder(TIME_INTERVAL)
                                .title("????????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("processTime")
                        .widget(new WidgetBuilder(TIME_INTERVAL)
                                .title("??????????????????")
                                .build())
                        .build())
                .buildAsViewInfo();
        return viewInfo;
    }

    //??????????????????????????????
    private ViewInfo getRejectViewInfo() {
        ViewInfo viewInfo = new FormBuilder()
                .addElement(new ElementBuilder("keyword")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("?????????")
                                .hint("??????????????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("theme")
                        .widget(new WidgetBuilder(SPINNER)
                                .title("????????????")
                                .maxLimit(1)
                                .hint("?????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("applyTime")
                        .widget(new WidgetBuilder(TIME_INTERVAL)
                                .title("????????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("dismissedTime")
                        .widget(new WidgetBuilder(TIME_INTERVAL)
                                .title("??????????????????")
                                .build())
                        .build())
                .buildAsViewInfo();
        return viewInfo;
    }

    //??????????????????????????????
    private ViewInfo getApplyInfo() {
        ViewInfo viewInfo = new FormBuilder()
                .addElement(new ElementBuilder("keyword")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("?????????")
                                .hint("??????????????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("theme")
                        .widget(new WidgetBuilder(SPINNER)
                                .title("????????????")
                                .maxLimit(1)
                                .hint("?????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("approvalStage")
                        .widget(new WidgetBuilder(SPINNER)
                                .title("????????????")
                                .maxLimit(1)
                                .initData(DictUtil.getApprovalStage())
                                .hint("?????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("applyTime")
                        .widget(new WidgetBuilder(TIME_INTERVAL)
                                .title("????????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("acceptTime")
                        .widget(new WidgetBuilder(TIME_INTERVAL)
                                .title("????????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("concludedTime")
                        .widget(new WidgetBuilder(TIME_INTERVAL)
                                .title("????????????")
                                .build())
                        .build())
                .buildAsViewInfo();
        return viewInfo;
    }


    @Override
    protected void onFilterReset(String pageKey) {


    }

    @Override
    protected void onFilterFinish(String pageKey) {
        EventBus.getDefault().post(new FilterBeanEvent(pageKey));
    }

    @Override
    protected void onFilterValueChange(String pageKey, BaseFormWidget widget, Object value, Object item, Boolean isEffective) {

    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (ListUtil.isNotEmpty(fragments) && fragments.size() > 1) {
            getSupportFragmentManager().beginTransaction().remove(fragments.get(fragments.size() - 1)).commit();
        } else {
            super.onBackPressed();
        }
    }
}
