package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.common.view.sweetdialog.SweetAlertDialog;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.AcceptReceiptBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ApproveNotThroughBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ApproveThroughBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.DealWithResult;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.LinkSendConfig;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.NextLink;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.NotAcceptReceiptBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.PostInfo;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.dict.util.DictBuilder;
import com.augurit.agmobile.busi.bpm.form.model.FormInfo;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.agmobile.busi.bpm.widget.WidgetListener;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.combineview.AGEditText;
import com.augurit.agmobile.common.view.combineview.AGMultiCheck;
import com.augurit.agmobile.common.view.combineview.AGMultiRadioGroup;
import com.augurit.agmobile.common.view.combineview.AGMultiRadioGroupAdapter;
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.augurit.common.common.form.AwFormActivity;
import com.augurit.common.common.form.AwFormConfig;
import com.augurit.common.common.form.DictLocalConfig;
import com.augurit.common.common.util.DialogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.bugly.crashreport.CrashReport;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * ?????????????????????
 * <p>
 * Created by liangsh on 2017/11/15.
 */

public class SendNextLinkFormActivity extends AwFormActivity implements WidgetListener {


    private ProgressDialog pd;
    private String taskInstId;
    private String taskName;
    private String viewId;
    private CompositeDisposable mCompositeDisposable;
    private DealWithResult mDealWithResult;
    private NextLink mNextLink;
    private boolean mNeedSelectAssignee;
    private String mDestActId;
    private boolean mMultiTask;
    private boolean mUserTask;
    private String mAssigneesId;
    private NextLink mNextLinkView;
    private BaseFormWidget mOrgWidget;
    private BaseFormWidget mAssigneeWidget;
    private BaseFormWidget mContentWidget;
    private BaseFormWidget mResultWidget;
    private BaseFormWidget mResultScWidget;
    private BaseFormWidget mTaskWidget;
    private AGEditText mReceiptWidget;
    //    private AGMultiRadioGroup mAssigneeWidget;
    private String mValue = "1";
    private String taskValue = "1";
    private String sValue = "1";

    private EventListItem.DataBean mEventListItem;
    private boolean isDelete;
    private boolean isOne = true;
    private int step = 1;
    private EventRepository mEventRepository;

    boolean approveThroughInfo = false;//??????????????????
    boolean approveNotThroughInfo = false;//?????????????????????
    boolean receiptInfo = false;//????????????
    boolean notReceiptInfo = false;//??????????????????
    private String isParallelSingleItem;//????????????????????????
    private boolean isShowInfo = true;//????????????????????????

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initArguments() {
        super.initArguments();
        this.mEventListItem = (EventListItem.DataBean) getIntent().getSerializableExtra("mEventListItem");
        this.taskInstId = getIntent().getStringExtra("taskInstId");
        this.taskName = getIntent().getStringExtra("taskName");
        this.viewId = getIntent().getStringExtra("viewId");
        this.isParallelSingleItem = getIntent().getStringExtra("parallelSingleItem");
//        CrashReport.testJavaCrash();
        mCompositeDisposable = new CompositeDisposable();
        mFormCode = AwFormConfig.FORM_SEND_TO_NEXTLINK;
        mEventRepository = new EventRepository();
    }

    /**
     * ?????????????????????
     */
    @SuppressLint("CheckResult")
    public void getNotApproveInfo() {
        mEventRepository.getNotApproveInfo(mEventListItem == null ? "" : mEventListItem.getApplyinstId(), mEventListItem == null ? "" : mEventListItem.getIteminstId())
                .subscribe(listApiResult -> {
                    ProgressDialogUtil.dismissProgressDialog();
                    if (listApiResult.isSuccess()) {
                        ApproveNotThroughBean approveNotThroughBean = listApiResult.getData();
                        if (approveNotThroughBean != null) {//???????????????????????????
                            EventBus.getDefault().post(approveNotThroughBean);
                        }
                        Gson gson = new Gson();
                        Map<String, String> map = new HashMap<>();
                        if (!ListUtil.isEmpty(approveNotThroughBean)) {
                            ApproveNotThroughBean contentInfo = approveNotThroughBean;
                            String projInfoJson = gson.toJson(contentInfo);
                            map.putAll(gson.fromJson(projInfoJson, new TypeToken<Map<String, String>>() {
                            }.getType()));
                        }
                        String unapproveReceiptId = approveNotThroughBean.getUnapproveReceiptId();
                        if ((unapproveReceiptId != null && !"".equals(unapproveReceiptId))) {
                            getReceiptInfo("???????????????????????????", "??????");
                            approveNotThroughInfo = true;
                        } else {
                            getReceiptInfo("???????????????????????????", "????????????");
                        }

                    }
                }, throwable -> {
                    ProgressDialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                });
    }

    /**
     * ??????????????????
     */
    @SuppressLint("CheckResult")
    public void getApproveThroughInfo() {
        mEventRepository.getApproveThroughInfo(mEventListItem == null ? "" : mEventListItem.getApplyinstId(), mEventListItem == null ? "" : mEventListItem.getIteminstId())
                .subscribe(listApiResult -> {
                    ProgressDialogUtil.dismissProgressDialog();
                    if (listApiResult.isSuccess()) {
                        ApproveThroughBean approveThroughBean = listApiResult.getData();
                        if (approveThroughBean != null) {//???????????????????????????
                            EventBus.getDefault().post(approveThroughBean);
                        }
                        Gson gson = new Gson();
                        Map<String, String> map = new HashMap<>();
                        if (!ListUtil.isEmpty(approveThroughBean)) {
                            ApproveThroughBean contentInfo = approveThroughBean;
                            String projInfoJson = gson.toJson(contentInfo);
                            map.putAll(gson.fromJson(projInfoJson, new TypeToken<Map<String, String>>() {
                            }.getType()));
                        }
                        String approveReceiptId = approveThroughBean.getApproveReceiptId();
                        if ((approveReceiptId != null && !"".equals(approveReceiptId))) {
                            getReceiptInfo("???????????????????????????", "??????");
                            approveThroughInfo = true;
                        } else {
                            getReceiptInfo("???????????????????????????", "????????????");
                        }

                    }
                }, throwable -> {
                    ProgressDialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                });
    }

    /**
     * ????????????
     */
    @SuppressLint("CheckResult")
    public void getReceiptInfo() {
        mEventRepository.getReceiptInfo(mEventListItem == null ? "" : mEventListItem.getApplyinstId(), mEventListItem == null ? "" : mEventListItem.getTaskId())
                .subscribe(listApiResult -> {
                    ProgressDialogUtil.dismissProgressDialog();
                    if (listApiResult.isSuccess()) {
                        AcceptReceiptBean acceptReceiptBean = listApiResult.getData();
                        if (acceptReceiptBean != null) {//???????????????????????????
                            EventBus.getDefault().post(acceptReceiptBean);
                        }
                        Gson gson = new Gson();
                        Map<String, String> map = new HashMap<>();
                        if (!ListUtil.isEmpty(acceptReceiptBean)) {
                            AcceptReceiptBean contentInfo = acceptReceiptBean;
                            String projInfoJson = gson.toJson(contentInfo);
                            map.putAll(gson.fromJson(projInfoJson, new TypeToken<Map<String, String>>() {
                            }.getType()));
                        }
                        String receiveReceiptId = acceptReceiptBean.getReceiveReceiptId();
                        if ((receiveReceiptId != null && !"".equals(receiveReceiptId))) {
                            getReceiptInfo("???????????????????????????", "??????");
                            receiptInfo = true;
                        } else {
                            getReceiptInfo("???????????????????????????", "????????????");
                        }

                    }
                }, throwable -> {
                    ProgressDialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                });
    }

    /**
     * ??????????????????
     */
    @SuppressLint("CheckResult")
    public void getNotReceiptInfo() {
        mEventRepository.getNotReceiptInfo(mEventListItem == null ? "" : mEventListItem.getApplyinstId(), mEventListItem == null ? "" : mEventListItem.getTaskId())
                .subscribe(listApiResult -> {
                    ProgressDialogUtil.dismissProgressDialog();
                    if (listApiResult.isSuccess()) {
                        NotAcceptReceiptBean notAcceptReceiptBean = listApiResult.getData();
                        if (notAcceptReceiptBean != null) {//???????????????????????????
                            EventBus.getDefault().post(notAcceptReceiptBean);
                        }
                        Gson gson = new Gson();
                        Map<String, String> map = new HashMap<>();
                        if (!ListUtil.isEmpty(notAcceptReceiptBean)) {
                            NotAcceptReceiptBean contentInfo = notAcceptReceiptBean;
                            String projInfoJson = gson.toJson(contentInfo);
                            map.putAll(gson.fromJson(projInfoJson, new TypeToken<Map<String, String>>() {
                            }.getType()));
                        }
                        String unreceiveReceiptId = notAcceptReceiptBean.getUnreceiveReceiptId();
                        if ((unreceiveReceiptId != null && !"".equals(unreceiveReceiptId))) {
                            getReceiptInfo("???????????????????????????", "??????");
                            notReceiptInfo = true;
                        } else {
                            getReceiptInfo("???????????????????????????", "????????????");
                        }

                    }
                }, throwable -> {
                    ProgressDialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                });
    }

    @Override
    protected void onFormLoaded() {
        super.onFormLoaded();
        mFormPresenter.addWidgetListener(this);
        setView();
        step = 1;
    }

    private void setView() {

        btn_save.setVisibility(View.GONE);

        mOrgWidget = mFormPresenter.getWidget("org");//????????????
        mAssigneeWidget = mFormPresenter.getWidget("assignee");
        mContentWidget = mFormPresenter.getWidget("content");
        mResultWidget = mFormPresenter.getWidget("bljg");
        mResultScWidget = mFormPresenter.getWidget("bljgsc");
        mTaskWidget = mFormPresenter.getWidget("bljgxs");


        mReceiptWidget = (AGEditText) mFormPresenter.getWidget("schz").getView();
        if (taskName.equals("????????????") || taskName.equals("????????????") || taskName.equals("??????") || taskName.equals("??????????????????") || taskName.equals("????????????")) {
//            mReceiptWidget.getView().setVisibility(View.GONE);
            mFormPresenter.getWidget("schz").setVisible(false);
            isShowInfo = false;
        } else {
            isShowInfo = true;
            if (mEventListItem != null && isShowInfo) {
                if ("????????????".equals(mEventListItem.getTaskName())) {
                    if (!"".equals(sValue)) {
                        if ("1".equals(sValue)) {
//                            titleBar.setTitleText("??????????????????");

                            getApproveThroughInfo();
                        } else {
//                            titleBar.setTitleText("?????????????????????");
                            getNotApproveInfo();
                        }
                    }
                } else {
                    try {
                        if (!"".equals(mValue)) {
                            if ("1".equals(mValue)) {//??????
//                                titleBar.setTitleText("????????????");
                                getReceiptInfo();
                            } else {//mValue???0
                                getNotReceiptInfo();
//                                titleBar.setTitleText("??????????????????");

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        getDealWithResult();
        try {
            getNextLink();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * ?????????????????????????????????
     */
    private void getReceiptInfo(String hint, String buttonText) {
        mFormPresenter.getWidget("schz").setVisible(true);
        mReceiptWidget.setValue(hint);
        mReceiptWidget.setVisibility(View.VISIBLE);
        mReceiptWidget.showOnlyEditText(false);
        mReceiptWidget.setMoreButtonIcon(0);
        mReceiptWidget.setMoreButtonText(buttonText);
        View btn_receipt = mReceiptWidget.getMoreButton();
        btn_receipt.setBackgroundResource(R.drawable.bg_btn_line_receipt);
        ((TextView) btn_receipt.findViewById(R.id.tv_more)).setTextColor(Color.parseColor("#EC808D"));
        btn_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getReceipt();
            }
        });
    }

    /**
     * ????????????
     */
    public void getReceipt() {
        Intent intent = new Intent(SendNextLinkFormActivity.this, ReceiptListActivity.class);
        intent.putExtra("mEventListItem", mEventListItem);
        intent.putExtra("mValue", mValue);
        intent.putExtra("taskValue", taskValue);
        intent.putExtra("sValue", sValue);
//        intent.putExtra("sendObjectStr",sendObjectStr);
        startActivityForResult(intent, 1);

        //???????????????????????????
        //mReceiptWidget.getMoreButton().setVisibility(View.GONE);
        mReceiptWidget.setValue("???????????????????????????");
        mReceiptWidget.setMoreButtonText("??????");

    }

    @Subscribe
    public void getPostInfo(PostInfo info) {
        isDelete = info.getInfo();
        if (isDelete) {
            getReceiptInfo("???????????????????????????", "????????????");
            if (mEventListItem != null && isShowInfo) {
                if ("????????????".equals(mEventListItem.getTaskName())) {
                    if (!"".equals(sValue)) {
                        if ("1".equals(sValue)) {
//                            titleBar.setTitleText("??????????????????");
                            approveThroughInfo = false;
                        } else {
//                            titleBar.setTitleText("?????????????????????");
                            approveNotThroughInfo = false;
                        }
                    }
                } else {
                    try {
                        if (!"".equals(mValue) || !"".equals(taskValue)) {
                            if ("1".equals(mValue) || "1".equals(taskValue) ) {//??????
//                                titleBar.setTitleText("????????????");
                                receiptInfo = false;
                            } else {//mValue???0
                                notReceiptInfo = false;
//                                titleBar.setTitleText("??????????????????");

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * ??????????????????
     */
    public void getDealWithResult() {
        /**
         * mResultWidget:????????????
         * mResultScWidget:???????????????????????????
         * mTaskWidget:??????????????????????????????????????????
         */
        if (taskName.equals("??????") || taskName.equals("??????????????????")) {//???????????????
            mResultWidget.setVisible(false);
            mResultScWidget.setVisible(false);
            mTaskWidget.setVisible(false);
        } else if (mEventListItem.getApplyType().equals("??????") && !taskName.equals("????????????") || isParallelSingleItem.equals("1")) {//????????????????????????????????????
            //????????????????????????,?????????????????????????????????????????????
            if (!taskName.equals("????????????") && !taskName.equals("????????????") && !taskName.equals("????????????")) {
                mResultWidget.setVisible(false);
                mResultScWidget.setVisible(false);
                mTaskWidget.setVisible(true);
                mTaskWidget.initData(DictLocalConfig.getHandle2());
            } else {//????????????????????????,????????????????????????
                mResultWidget.setVisible(false);
                mResultScWidget.setVisible(false);
                mTaskWidget.setVisible(true);
                mTaskWidget.initData(DictLocalConfig.getHandle3());
            }
        } else if (mEventListItem.getApplyType().equals("??????") && !taskName.equals("????????????")) {
            if (taskName.equals("????????????") || taskName.equals("????????????") || taskName.equals("????????????")) {
                //????????????????????????,????????????????????????
                mResultWidget.setVisible(false);
                mResultScWidget.setVisible(false);
                mTaskWidget.setVisible(true);
                mTaskWidget.initData(DictLocalConfig.getHandle3());
            }
        } else if (taskName.equals("????????????")) {
            mResultScWidget.setVisible(true);
            mResultWidget.setVisible(false);
            mTaskWidget.setVisible(false);
        } else {
            mResultScWidget.setVisible(false);
            mResultWidget.setVisible(true);
            mTaskWidget.setVisible(false);
        }
    }

    /**
     * ??????????????????
     */
    private void getNextLink() {
        Map<String, String> map = new HashMap<>();
        map.put("taskId", taskInstId);
        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_NEXT_LINK_4_0, map)
                .subscribe(s -> {
                    NextLink nextLink = new Gson().fromJson(s, new TypeToken<NextLink>() {
                    }.getType());
                    if (nextLink != null && nextLink.isSuccess()) {
                        mNextLink = nextLink;
                        if (!ListUtil.isEmpty(nextLink.getContent()) && nextLink.getContent().size() == 1) {
                            List<NextLink.ContentBean> content = nextLink.getContent();
                            NextLink.ContentBean contentBean = content.get(0);
                            if (!contentBean.isNeedSelectAssignee()) {//????????????????????????????????????????????????????????????????????????
                                mAssigneeWidget.setVisible(!TextUtils.isEmpty(contentBean.getDefaultSendAssignees()));
                                mAssigneeWidget.initData(new DictBuilder().item(contentBean.getDefaultSendAssignees()
                                                == null ? "" : contentBean.getDefaultSendAssignees()
                                        , contentBean.getDefaultSendAssigneesId()
                                                == null ? "" : contentBean.getDefaultSendAssigneesId()
                                ).build());
                                mDestActId = contentBean.getDestActId();
                                mMultiTask = contentBean.isMultiTask();
                                mUserTask = contentBean.isUserTask();
                                mAssigneesId = contentBean.getDefaultSendAssigneesId();
                            }
                        }
                        setNextLinkView(nextLink);
                    }
                });

        mCompositeDisposable.add(subscribe);
    }

    /**
     * ????????????
     *
     * @param nextLink
     */
    public void setNextLinkView(NextLink nextLink) {
        if (nextLink.getContent() != null) {
            DictBuilder builder = new DictBuilder();
            for (int i = 0; i < nextLink.getContent().size(); i++) {
                String destActName = nextLink.getContent().get(i).getDestActName();
                if (!TextUtils.isEmpty(destActName)) {
                    builder.item(destActName);
                }
            }
            mOrgWidget.initData(builder.build());
        }
    }


    @Override
    public void onValueChange(BaseFormWidget baseFormWidget, Object o, @Nullable Object o1, boolean b) {
        String elementCode = baseFormWidget.getElement().getElementCode();
        mValue = (String) mFormPresenter.getWidget("bljg").getValue();
        sValue = (String) mFormPresenter.getWidget("bljgsc").getValue();
        taskValue = (String) mFormPresenter.getWidget("bljgxs").getValue();
        if ("bljgsc".equals(elementCode) || "bljgxs".equals(elementCode) || "bljg".equals(elementCode)) {
            if (mEventListItem != null && isShowInfo) {
                if ("????????????".equals(mEventListItem.getTaskName())) {
                    if (!"".equals(sValue)) {
                        if ("1".equals(sValue)) {
//                            titleBar.setTitleText("??????????????????");
                            if (!approveThroughInfo) {
                                getApproveThroughInfo();
                            } else {
                                getReceiptInfo("???????????????????????????", "??????");
                            }
                        } else {
//                            titleBar.setTitleText("?????????????????????");
                            if (!approveNotThroughInfo) {
                                getNotApproveInfo();
                            } else {
                                getReceiptInfo("???????????????????????????", "??????");
                            }
                        }
                    }
                } else {
                    try {
                        if("bljgxs".equals(elementCode) ){
                            if (!"".equals(taskValue)) {
                                if ("1".equals(taskValue)) {//??????
//                                titleBar.setTitleText("????????????");
                                    if (!receiptInfo) {
                                        getReceiptInfo();
                                    } else {
                                        getReceiptInfo("???????????????????????????", "??????");
                                    }
                                } else {//mValue???0
                                    if (!notReceiptInfo) {
                                        getNotReceiptInfo();
                                    } else {
                                        getReceiptInfo("???????????????????????????", "??????");
                                    }
//                                titleBar.setTitleText("??????????????????");

                                }
                            }
                        }else if("bljg".equals(elementCode)){
                            if (!"".equals(mValue)) {
                                if ("1".equals(mValue)) {//??????
//                                titleBar.setTitleText("????????????");
                                    if (!receiptInfo) {
                                        getReceiptInfo();
                                    } else {
                                        getReceiptInfo("???????????????????????????", "??????");
                                    }
                                } else {//mValue???0
                                    if (!notReceiptInfo) {
                                        getNotReceiptInfo();
                                    } else {
                                        getReceiptInfo("???????????????????????????", "??????");
                                    }
//                                titleBar.setTitleText("??????????????????");

                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        /**
         * ?????????????????????mValue???1  sValue???1
         * ????????????????????????----mValue???1  sValue???0
         */
        if ("org".endsWith(baseFormWidget.getElement().getElementCode())) {
            if (mNextLink != null) {
                for (NextLink.ContentBean contentBean : mNextLink.getContent()) {
                    if (o != null && o.equals(contentBean.getDestActName())) {
                        mDestActId = contentBean.getDestActId();
                        mMultiTask = contentBean.isMultiTask();
                        mUserTask = contentBean.isUserTask();
                        if (contentBean.isNeedSelectAssignee()) {//????????????????????????
                            getNextLinkAssigneers(mDestActId);
                        } else {//??????
                            mAssigneeWidget.setVisible(!TextUtils.isEmpty(contentBean.getDefaultSendAssignees()));
                            mAssigneeWidget.initData(new DictBuilder().item(contentBean.getDefaultSendAssignees() == null ?
                                            "" : contentBean.getDefaultSendAssignees()
                                    , contentBean.getDefaultSendAssigneesId()
                                            == null ? "" : contentBean.getDefaultSendAssigneesId()
                            ).build());

                            ((AGMultiRadioGroup) mAssigneeWidget.getView()).setColumnCount(1);
//                            mAssigneesId=contentBean.getDefaultSendAssigneesId();
                        }
                        mAssigneesId = contentBean.getDefaultSendAssigneesId();
                        break;
                    }
                }
            }
        }
        if (mFormPresenter.getWidget("org").isVisible() && sValue.equals("0")) {//????????????????????????????????????
            mFormPresenter.getWidget("org").setEnable(true);
            if ("bljgsc".equals(elementCode)) {
                if (step == 1) {
                    AGMultiCheck org1 = (AGMultiCheck) mFormPresenter.getWidget("org").getView();
                    int index = -1;
                    for (int i = 0; i < mNextLink.getContent().size(); i++) {
                        String destActName = mNextLink.getContent().get(i).getDestActName();
                        if ("??????".equals(destActName)) {
                            index = i;
                        }
                    }
                    if (index >= 0) {
                        org1.setSelection(index);
                    }
                }
            }
        } else if (mFormPresenter.getWidget("org").isVisible() && sValue.equals("1")) {//??????????????????
            try {
                if (mEventListItem.getItemName() != null && mEventListItem.getItemName().equals("????????????????????????")) {
                    mFormPresenter.getWidget("org").setEnable(true);
                } else {
                    mFormPresenter.getWidget("org").setEnable(false);
                    if ("bljgsc".equals(elementCode)) {
                        AGMultiCheck org1 = (AGMultiCheck) mFormPresenter.getWidget("org").getView();
                        int index = -1;
                        for (int i = 0; i < mNextLink.getContent().size(); i++) {
                            String destActName = mNextLink.getContent().get(i).getDestActName();
                            if ("????????????".equals(destActName)) {
                                index = i;
                            }
                        }
                        if (index >= 0) {
                            org1.setSelection(index);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (mValue.equals("2") || taskName.equals("????????????") || taskName.equals("????????????") || taskName.equals("??????") || taskName.equals("??????????????????") || taskName.equals("????????????")) {//???????????????
            mFormPresenter.getWidget("schz").setVisible(false);
        } else {
            mFormPresenter.getWidget("schz").setVisible(true);
        }
    }


    /**
     * ????????????????????????????????????
     *
     * @param destActId
     */
    private void getNextLinkAssigneers(String destActId) {
        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_NEXT_LINK_USER + "/" + taskInstId + "/" + destActId, null)
                .subscribe(s -> {
                    ApiResult<List<User>> result = new Gson().fromJson(s, new TypeToken<ApiResult<List<User>>>() {
                    }.getType());

                    if (result != null && result.isSuccess()) {
                        if (!ListUtil.isEmpty(result.getData())) {
                            List<User> data = result.getData();
                            DictBuilder builder = new DictBuilder();
                            for (User user : data) {
                                if (user.getUserName() != null && user.getLoginName() != null) {
                                    builder.item(user.getUserName(), user.getLoginName());
                                }
                            }
                            mAssigneeWidget.initData(builder.build());
                            mAssigneeWidget.setVisible(true);
                            StringBuilder builder1 = new StringBuilder();
                            for (int i = 0; i < data.size(); i++) {
                                if (i == data.size() - 1) {
                                    builder1.append(data.get(i).getLoginName());
                                } else {
                                    builder1.append(data.get(i).getLoginName() + ",");
                                }
                            }
                            ((AGMultiRadioGroup) mAssigneeWidget.getView()).setValue(builder1 + "");
                            ((AGMultiRadioGroup) mAssigneeWidget.getView()).setColumnCount(3);
                        } else {
                            mAssigneeWidget.setVisible(false);
                        }
                    }
                });

        mCompositeDisposable.add(subscribe);


    }

    @Override
    protected void submit() {
        //??????
        if (!mFormPresenter.validate()) {
            return;
        }
        //????????????????????????
        if (mFormPresenter.getWidget("schz").isVisible() && !"???????????????????????????".equals(mReceiptWidget.getValue())) {
            ToastUtil.shortToast(SendNextLinkFormActivity.this, "???????????????????????????");
            return;
        }
//        if(mEventListItem!=null){
//            if("????????????".equals(mEventListItem.getTaskName())){
//                if (!"".equals(sValue)){
//                    if ("1".equals(sValue)){
////                            titleBar.setTitleText("??????????????????");
//                        if(!approveThroughInfo){
//                            ToastUtil.shortToast(SendNextLinkFormActivity.this,"???????????????????????????");
//                            return;
//                        }
//                    }else {
////                            titleBar.setTitleText("?????????????????????");
//                        if(!approveNotThroughInfo){
//                            ToastUtil.shortToast(SendNextLinkFormActivity.this,"???????????????????????????");
//                            return;
//                        }
//                    }
//                }
//            }else{
//                try {
//                    if (!"".equals(mValue)){
//                        if ("1".equals(mValue)){//??????
////                                titleBar.setTitleText("????????????");
//                            if(!receiptInfo){
//                                ToastUtil.shortToast(SendNextLinkFormActivity.this,"???????????????????????????");
//                                return;
//                            }
//                        }else {//mValue???0
//                            if(!notReceiptInfo){
//                                ToastUtil.shortToast(SendNextLinkFormActivity.this,"???????????????????????????");
//                                return;
//                            }
////                                titleBar.setTitleText("??????????????????");
//
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }5
        LinkSendConfig linkSendConfig = new LinkSendConfig();
        linkSendConfig.setTaskId(taskInstId);
        List<LinkSendConfig.SendConfigsBean> sendConfigs = new ArrayList<>();
        LinkSendConfig.SendConfigsBean sendConfigsBean = new LinkSendConfig.SendConfigsBean();
        sendConfigsBean.setAssignees(mAssigneeWidget.getValue() == null ? "" : mAssigneeWidget.getValue().toString());
//        sendConfigsBean.setAssignees(mAssigneesId== null ? "" :mAssigneesId);
        sendConfigsBean.setDestActId(mDestActId);
        sendConfigsBean.setIsEnableMultiTask(mMultiTask);
        sendConfigsBean.setIsUserTask(mUserTask);
        sendConfigs.add(sendConfigsBean);
        linkSendConfig.setSendConfigs(sendConfigs);
        if (mResultWidget.isVisible()) {//??????1????????????0????????????2
            if (!mFormPresenter.getWidget("bljg").getValue().equals("")) {
                linkSendConfig.setConclusionOptionValue((String) mFormPresenter.getWidget("bljg").getValue());
            }
        } else if (mTaskWidget.isVisible()) {
            if (!mFormPresenter.getWidget("bljgxs").getValue().equals("")) {
                linkSendConfig.setConclusionOptionValue((String) mFormPresenter.getWidget("bljgxs").getValue());
            }
        } else if (mResultScWidget.isVisible()) {//??????????????????1????????????0
            if (!mFormPresenter.getWidget("bljgsc").getValue().equals("")) {
                linkSendConfig.setConclusionOptionValue((String) mFormPresenter.getWidget("bljgsc").getValue());
            }
        } else {
            linkSendConfig.setConclusionOptionValue("");
        }
//        linkSendConfig.setConclusionOptionValue(mFormPresenter.getWidgetValues().get("bljg"));
        Map<String, String> map = new HashMap<>();
        String opinion = mFormPresenter.getWidgetValues().get("opinion");
        map.put("opinion", opinion);

        Gson gson = new Gson();
        String sendObjectStr = gson.toJson(linkSendConfig);
        map.put("sendObjectStr", sendObjectStr);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("????????????????????????");
        progressDialog.show();

        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.serverUrl()).post(GcjsUrlConstant.SEND_TO_NEXT_LINK
                , map)
                .subscribe(s -> {
                    progressDialog.dismiss();
                    ApiResult apiResult = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                    }.getType());
                    if (apiResult != null && apiResult.isSuccess()) {
                        try {
                            List<IDictItem> data = ((AGMultiRadioGroup) mAssigneeWidget.getView()).getSelectedData();
                            StringBuilder builder = new StringBuilder();
                            for (int i = 0; i < data.size(); i++) {
                                if (i == data.size() - 1) {
                                    builder.append(data.get(i).getLabel());
                                } else {
                                    builder.append(data.get(i).getLabel() + "???");
                                }
                            }
//                            String name = ((AGMultiRadioGroup) mAssigneeWidget.getView()).getSelectedData().get(0).getLabel();
                            String nextTip = ((TextUtils.isEmpty(builder)) ? "" : ",??????????????????????????????" + builder);
                            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("????????????")
                                    .showCancelButton(false)
                                    .setContentText("?????????????????????" + mOrgWidget.getValue().toString() + " ??????" + nextTip)
                                    .setConfirmClickListener(sweetAlertDialog -> {
                                        setResult(RESULT_OK);
                                        finish();
                                    })
                                    .show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("????????????")
                                .showCancelButton(false)
                                .setContentText(apiResult.getMessage())
                                .show();
//                        ToastUtil.shortToast(SendNextLinkActivity.this, "????????????:" + apiResult.getMessage());
                    }

                });
        mCompositeDisposable.add(subscribe);

    }


    @Override
    public void onBackPressed() {
        DialogUtil.MessageBox(this, "??????", "?????????????????????????????????"
                , (dialog, which) -> finish(), (dialog, which) -> dialog.dismiss());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
        EventBus.getDefault().unregister(this);
    }


}

