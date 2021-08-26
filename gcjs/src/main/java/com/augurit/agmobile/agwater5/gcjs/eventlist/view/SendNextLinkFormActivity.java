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
 * 推送到下一环节
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

    boolean approveThroughInfo = false;//审批决定通过
    boolean approveNotThroughInfo = false;//审批决定不通过
    boolean receiptInfo = false;//受理回执
    boolean notReceiptInfo = false;//不予受理回执
    private String isParallelSingleItem;//是否并联单项的件
    private boolean isShowInfo = true;//是否显示回执信息

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
     * 审批决定不通过
     */
    @SuppressLint("CheckResult")
    public void getNotApproveInfo() {
        mEventRepository.getNotApproveInfo(mEventListItem == null ? "" : mEventListItem.getApplyinstId(), mEventListItem == null ? "" : mEventListItem.getIteminstId())
                .subscribe(listApiResult -> {
                    ProgressDialogUtil.dismissProgressDialog();
                    if (listApiResult.isSuccess()) {
                        ApproveNotThroughBean approveNotThroughBean = listApiResult.getData();
                        if (approveNotThroughBean != null) {//全局发送实体类信息
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
                            getReceiptInfo("已生成回执，请点击", "修改");
                            approveNotThroughInfo = true;
                        } else {
                            getReceiptInfo("回执未生成，请点击", "生成回执");
                        }

                    }
                }, throwable -> {
                    ProgressDialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                });
    }

    /**
     * 审批决定通过
     */
    @SuppressLint("CheckResult")
    public void getApproveThroughInfo() {
        mEventRepository.getApproveThroughInfo(mEventListItem == null ? "" : mEventListItem.getApplyinstId(), mEventListItem == null ? "" : mEventListItem.getIteminstId())
                .subscribe(listApiResult -> {
                    ProgressDialogUtil.dismissProgressDialog();
                    if (listApiResult.isSuccess()) {
                        ApproveThroughBean approveThroughBean = listApiResult.getData();
                        if (approveThroughBean != null) {//全局发送实体类信息
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
                            getReceiptInfo("已生成回执，请点击", "修改");
                            approveThroughInfo = true;
                        } else {
                            getReceiptInfo("回执未生成，请点击", "生成回执");
                        }

                    }
                }, throwable -> {
                    ProgressDialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                });
    }

    /**
     * 受理回执
     */
    @SuppressLint("CheckResult")
    public void getReceiptInfo() {
        mEventRepository.getReceiptInfo(mEventListItem == null ? "" : mEventListItem.getApplyinstId(), mEventListItem == null ? "" : mEventListItem.getTaskId())
                .subscribe(listApiResult -> {
                    ProgressDialogUtil.dismissProgressDialog();
                    if (listApiResult.isSuccess()) {
                        AcceptReceiptBean acceptReceiptBean = listApiResult.getData();
                        if (acceptReceiptBean != null) {//全局发送实体类信息
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
                            getReceiptInfo("已生成回执，请点击", "修改");
                            receiptInfo = true;
                        } else {
                            getReceiptInfo("回执未生成，请点击", "生成回执");
                        }

                    }
                }, throwable -> {
                    ProgressDialogUtil.dismissProgressDialog();
                    throwable.printStackTrace();
                });
    }

    /**
     * 不予受理回执
     */
    @SuppressLint("CheckResult")
    public void getNotReceiptInfo() {
        mEventRepository.getNotReceiptInfo(mEventListItem == null ? "" : mEventListItem.getApplyinstId(), mEventListItem == null ? "" : mEventListItem.getTaskId())
                .subscribe(listApiResult -> {
                    ProgressDialogUtil.dismissProgressDialog();
                    if (listApiResult.isSuccess()) {
                        NotAcceptReceiptBean notAcceptReceiptBean = listApiResult.getData();
                        if (notAcceptReceiptBean != null) {//全局发送实体类信息
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
                            getReceiptInfo("已生成回执，请点击", "修改");
                            notReceiptInfo = true;
                        } else {
                            getReceiptInfo("回执未生成，请点击", "生成回执");
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

        mOrgWidget = mFormPresenter.getWidget("org");//下一环节
        mAssigneeWidget = mFormPresenter.getWidget("assignee");
        mContentWidget = mFormPresenter.getWidget("content");
        mResultWidget = mFormPresenter.getWidget("bljg");
        mResultScWidget = mFormPresenter.getWidget("bljgsc");
        mTaskWidget = mFormPresenter.getWidget("bljgxs");


        mReceiptWidget = (AGEditText) mFormPresenter.getWidget("schz").getView();
        if (taskName.equals("形式审查") || taskName.equals("任务分派") || taskName.equals("复核") || taskName.equals("现场踏勘上传") || taskName.equals("资料审查")) {
//            mReceiptWidget.getView().setVisibility(View.GONE);
            mFormPresenter.getWidget("schz").setVisible(false);
            isShowInfo = false;
        } else {
            isShowInfo = true;
            if (mEventListItem != null && isShowInfo) {
                if ("审查决定".equals(mEventListItem.getTaskName())) {
                    if (!"".equals(sValue)) {
                        if ("1".equals(sValue)) {
//                            titleBar.setTitleText("审批决定通过");

                            getApproveThroughInfo();
                        } else {
//                            titleBar.setTitleText("审批决定不通过");
                            getNotApproveInfo();
                        }
                    }
                } else {
                    try {
                        if (!"".equals(mValue)) {
                            if ("1".equals(mValue)) {//受理
//                                titleBar.setTitleText("受理回执");
                                getReceiptInfo();
                            } else {//mValue为0
                                getNotReceiptInfo();
//                                titleBar.setTitleText("不予受理回执");

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
     * 生成回执信息的控件展示
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
     * 生成回执
     */
    public void getReceipt() {
        Intent intent = new Intent(SendNextLinkFormActivity.this, ReceiptListActivity.class);
        intent.putExtra("mEventListItem", mEventListItem);
        intent.putExtra("mValue", mValue);
        intent.putExtra("taskValue", taskValue);
        intent.putExtra("sValue", sValue);
//        intent.putExtra("sendObjectStr",sendObjectStr);
        startActivityForResult(intent, 1);

        //生成回执后隐藏按钮
        //mReceiptWidget.getMoreButton().setVisibility(View.GONE);
        mReceiptWidget.setValue("已生成回执，请点击");
        mReceiptWidget.setMoreButtonText("修改");

    }

    @Subscribe
    public void getPostInfo(PostInfo info) {
        isDelete = info.getInfo();
        if (isDelete) {
            getReceiptInfo("回执未生成，请点击", "生成回执");
            if (mEventListItem != null && isShowInfo) {
                if ("审查决定".equals(mEventListItem.getTaskName())) {
                    if (!"".equals(sValue)) {
                        if ("1".equals(sValue)) {
//                            titleBar.setTitleText("审批决定通过");
                            approveThroughInfo = false;
                        } else {
//                            titleBar.setTitleText("审批决定不通过");
                            approveNotThroughInfo = false;
                        }
                    }
                } else {
                    try {
                        if (!"".equals(mValue) || !"".equals(taskValue)) {
                            if ("1".equals(mValue) || "1".equals(taskValue) ) {//受理
//                                titleBar.setTitleText("受理回执");
                                receiptInfo = false;
                            } else {//mValue为0
                                notReceiptInfo = false;
//                                titleBar.setTitleText("不予受理回执");

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
     * 获取办理结果
     */
    public void getDealWithResult() {
        /**
         * mResultWidget:办理结果
         * mResultScWidget:审查决定的办理结果
         * mTaskWidget:形式审查和任务分派的办理结果
         */
        if (taskName.equals("复核") || taskName.equals("现场踏勘上传")) {//无办理结果
            mResultWidget.setVisible(false);
            mResultScWidget.setVisible(false);
            mTaskWidget.setVisible(false);
        } else if (mEventListItem.getApplyType().equals("单项") && !taskName.equals("审查决定") || isParallelSingleItem.equals("1")) {//单项非审查决定环节的办件
            //无“不涉及”情况,且为受理，不予受理（一般情况）
            if (!taskName.equals("形式审查") && !taskName.equals("任务分派") && !taskName.equals("资料审查")) {
                mResultWidget.setVisible(false);
                mResultScWidget.setVisible(false);
                mTaskWidget.setVisible(true);
                mTaskWidget.initData(DictLocalConfig.getHandle2());
            } else {//无“不涉及”情况,且为同意，不同意
                mResultWidget.setVisible(false);
                mResultScWidget.setVisible(false);
                mTaskWidget.setVisible(true);
                mTaskWidget.initData(DictLocalConfig.getHandle3());
            }
        } else if (mEventListItem.getApplyType().equals("并联") && !taskName.equals("审查决定")) {
            if (taskName.equals("形式审查") || taskName.equals("任务分派") || taskName.equals("资料审查")) {
                //无“不涉及”情况,且为同意，不同意
                mResultWidget.setVisible(false);
                mResultScWidget.setVisible(false);
                mTaskWidget.setVisible(true);
                mTaskWidget.initData(DictLocalConfig.getHandle3());
            }
        } else if (taskName.equals("审查决定")) {
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
     * 获取下一环节
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
                            if (!contentBean.isNeedSelectAssignee()) {//如果下一环节不是结束，则显示返回的下一环节处理人
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
     * 下一环节
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
                if ("审查决定".equals(mEventListItem.getTaskName())) {
                    if (!"".equals(sValue)) {
                        if ("1".equals(sValue)) {
//                            titleBar.setTitleText("审批决定通过");
                            if (!approveThroughInfo) {
                                getApproveThroughInfo();
                            } else {
                                getReceiptInfo("已生成回执，请点击", "修改");
                            }
                        } else {
//                            titleBar.setTitleText("审批决定不通过");
                            if (!approveNotThroughInfo) {
                                getNotApproveInfo();
                            } else {
                                getReceiptInfo("已生成回执，请点击", "修改");
                            }
                        }
                    }
                } else {
                    try {
                        if("bljgxs".equals(elementCode) ){
                            if (!"".equals(taskValue)) {
                                if ("1".equals(taskValue)) {//受理
//                                titleBar.setTitleText("受理回执");
                                    if (!receiptInfo) {
                                        getReceiptInfo();
                                    } else {
                                        getReceiptInfo("已生成回执，请点击", "修改");
                                    }
                                } else {//mValue为0
                                    if (!notReceiptInfo) {
                                        getNotReceiptInfo();
                                    } else {
                                        getReceiptInfo("已生成回执，请点击", "修改");
                                    }
//                                titleBar.setTitleText("不予受理回执");

                                }
                            }
                        }else if("bljg".equals(elementCode)){
                            if (!"".equals(mValue)) {
                                if ("1".equals(mValue)) {//受理
//                                titleBar.setTitleText("受理回执");
                                    if (!receiptInfo) {
                                        getReceiptInfo();
                                    } else {
                                        getReceiptInfo("已生成回执，请点击", "修改");
                                    }
                                } else {//mValue为0
                                    if (!notReceiptInfo) {
                                        getNotReceiptInfo();
                                    } else {
                                        getReceiptInfo("已生成回执，请点击", "修改");
                                    }
//                                titleBar.setTitleText("不予受理回执");

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
         * 审查决定通过：mValue：1  sValue：1
         * 审查决定不不通过----mValue：1  sValue：0
         */
        if ("org".endsWith(baseFormWidget.getElement().getElementCode())) {
            if (mNextLink != null) {
                for (NextLink.ContentBean contentBean : mNextLink.getContent()) {
                    if (o != null && o.equals(contentBean.getDestActName())) {
                        mDestActId = contentBean.getDestActId();
                        mMultiTask = contentBean.isMultiTask();
                        mUserTask = contentBean.isUserTask();
                        if (contentBean.isNeedSelectAssignee()) {//指定还是手动选择
                            getNextLinkAssigneers(mDestActId);
                        } else {//指定
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
        if (mFormPresenter.getWidget("org").isVisible() && sValue.equals("0")) {//审查决定不通过的下一环节
            mFormPresenter.getWidget("org").setEnable(true);
            if ("bljgsc".equals(elementCode)) {
                if (step == 1) {
                    AGMultiCheck org1 = (AGMultiCheck) mFormPresenter.getWidget("org").getView();
                    int index = -1;
                    for (int i = 0; i < mNextLink.getContent().size(); i++) {
                        String destActName = mNextLink.getContent().get(i).getDestActName();
                        if ("结束".equals(destActName)) {
                            index = i;
                        }
                    }
                    if (index >= 0) {
                        org1.setSelection(index);
                    }
                }
            }
        } else if (mFormPresenter.getWidget("org").isVisible() && sValue.equals("1")) {//审查决定通过
            try {
                if (mEventListItem.getItemName() != null && mEventListItem.getItemName().equals("工程竣工验收监督")) {
                    mFormPresenter.getWidget("org").setEnable(true);
                } else {
                    mFormPresenter.getWidget("org").setEnable(false);
                    if ("bljgsc".equals(elementCode)) {
                        AGMultiCheck org1 = (AGMultiCheck) mFormPresenter.getWidget("org").getView();
                        int index = -1;
                        for (int i = 0; i < mNextLink.getContent().size(); i++) {
                            String destActName = mNextLink.getContent().get(i).getDestActName();
                            if ("证件签发".equals(destActName)) {
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

        if (mValue.equals("2") || taskName.equals("形式审查") || taskName.equals("任务分派") || taskName.equals("复核") || taskName.equals("现场踏勘上传") || taskName.equals("资料审查")) {//无回执情况
            mFormPresenter.getWidget("schz").setVisible(false);
        } else {
            mFormPresenter.getWidget("schz").setVisible(true);
        }
    }


    /**
     * 获取下一环节及处理人列表
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
        //校验
        if (!mFormPresenter.validate()) {
            return;
        }
        //校验回执是否填写
        if (mFormPresenter.getWidget("schz").isVisible() && !"已生成回执，请点击".equals(mReceiptWidget.getValue())) {
            ToastUtil.shortToast(SendNextLinkFormActivity.this, "请填写回执后再提交");
            return;
        }
//        if(mEventListItem!=null){
//            if("审查决定".equals(mEventListItem.getTaskName())){
//                if (!"".equals(sValue)){
//                    if ("1".equals(sValue)){
////                            titleBar.setTitleText("审批决定通过");
//                        if(!approveThroughInfo){
//                            ToastUtil.shortToast(SendNextLinkFormActivity.this,"请填写回执后再提交");
//                            return;
//                        }
//                    }else {
////                            titleBar.setTitleText("审批决定不通过");
//                        if(!approveNotThroughInfo){
//                            ToastUtil.shortToast(SendNextLinkFormActivity.this,"请填写回执后再提交");
//                            return;
//                        }
//                    }
//                }
//            }else{
//                try {
//                    if (!"".equals(mValue)){
//                        if ("1".equals(mValue)){//受理
////                                titleBar.setTitleText("受理回执");
//                            if(!receiptInfo){
//                                ToastUtil.shortToast(SendNextLinkFormActivity.this,"请填写回执后再提交");
//                                return;
//                            }
//                        }else {//mValue为0
//                            if(!notReceiptInfo){
//                                ToastUtil.shortToast(SendNextLinkFormActivity.this,"请填写回执后再提交");
//                                return;
//                            }
////                                titleBar.setTitleText("不予受理回执");
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
        if (mResultWidget.isVisible()) {//同意1，不同意0，不涉及2
            if (!mFormPresenter.getWidget("bljg").getValue().equals("")) {
                linkSendConfig.setConclusionOptionValue((String) mFormPresenter.getWidget("bljg").getValue());
            }
        } else if (mTaskWidget.isVisible()) {
            if (!mFormPresenter.getWidget("bljgxs").getValue().equals("")) {
                linkSendConfig.setConclusionOptionValue((String) mFormPresenter.getWidget("bljgxs").getValue());
            }
        } else if (mResultScWidget.isVisible()) {//审查决定通过1和不通过0
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
        progressDialog.setMessage("正在发送，请等待");
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
                                    builder.append(data.get(i).getLabel() + "，");
                                }
                            }
//                            String name = ((AGMultiRadioGroup) mAssigneeWidget.getView()).getSelectedData().get(0).getLabel();
                            String nextTip = ((TextUtils.isEmpty(builder)) ? "" : ",下一环环节处理人为：" + builder);
                            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("发送成功")
                                    .showCancelButton(false)
                                    .setContentText("流程已发送至：" + mOrgWidget.getValue().toString() + " 环节" + nextTip)
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
                                .setTitleText("发送失败")
                                .showCancelButton(false)
                                .setContentText(apiResult.getMessage())
                                .show();
//                        ToastUtil.shortToast(SendNextLinkActivity.this, "发送失败:" + apiResult.getMessage());
                    }

                });
        mCompositeDisposable.add(subscribe);

    }


    @Override
    public void onBackPressed() {
        DialogUtil.MessageBox(this, "提示", "是否确定放弃本次编辑？"
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

