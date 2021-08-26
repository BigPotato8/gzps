package com.augurit.agmobile.agwater5.gcjspad.eventdetail;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.dict.GcjsDictUtils;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.common.view.sweetdialog.SweetAlertDialog;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.LinkSendConfig;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.NextLink;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.dict.util.DictBuilder;
import com.augurit.agmobile.busi.common.login.model.User;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.combineview.AGFlowCheck;
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 处理的弹框（受理、不予受理）
 */
public class EventDealDialog extends Dialog {
    Map<String, String> params;
    TextView tv_title;
    Button btn_cancel;
    Button btn_send;
    EditText et_content;
    AGFlowCheck flow_check;//办理结果
    AGFlowCheck flow_step;//下一环节
    AGFlowCheck flow_step_people;//下一环节处理人
    LinearLayout ll_flow_step;

    private String mDestActId;
    private String taskInstId;
    private String applyinstId;
    private String iteminstId;
    private String processInstanceId;
    private boolean mMultiTask;
    private boolean mUserTask;
    private NextLink mNextLink;
    private CompositeDisposable mCompositeDisposable;
    private String title;
    private String isDeal;//办理"1"，受理"2"，不予受理"3"
    private EventRepository eventRepository;
    private SuccessListener successListener;

    public void setSuccessListener(SuccessListener successListener) {
        this.successListener = successListener;
    }

    public EventDealDialog(@NonNull Context context) {
        super(context);
    }

    public EventDealDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected EventDealDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
        this.taskInstId = params.get("taskInstId");
        this.processInstanceId = params.get("processInstanceId");
        this.title = params.get("title");
        this.isDeal = params.get("isDeal");
        this.applyinstId = params.get("applyinstId");
        this.iteminstId = params.get("iteminstId");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_event_deal);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = getWindow().getWindowManager().getDefaultDisplay().getWidth() * 4 / 5;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(layoutParams);
        setCancelable(true);
        mCompositeDisposable = new CompositeDisposable();
        eventRepository = new EventRepository();
        initView();
    }


    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        ll_flow_step = findViewById(R.id.ll_flow_step);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_send = findViewById(R.id.btn_send);
        et_content = findViewById(R.id.et_content);
        flow_check = findViewById(R.id.flow_check);
        flow_step = findViewById(R.id.flow_step);
        flow_step_people = findViewById(R.id.flow_step_people);

        tv_title.setText(title);
        flow_step.setMaxLimit(1);
        flow_step_people.setMaxLimit(1);
        flow_check.initData(GcjsDictUtils.getAdviceDictItems());
        flow_check.setMaxLimit(1);

        if ("1".equals(isDeal)) {
            flow_step.setOnItemSelectedListener((value, item, position, selected) -> {
                if (selected) {
                    if (mNextLink != null) {
                        for (NextLink.ContentBean contentBean : mNextLink.getContent()) {
                            if (value != null && value.equals(contentBean.getDestActName())) {
                                mDestActId = contentBean.getDestActId();
                                mMultiTask = contentBean.isMultiTask();
                                mUserTask = contentBean.isUserTask();
                                if (contentBean.isNeedSelectAssignee()) {
                                    getNextLinkAssigneers(mDestActId);
                                } else {
                                    flow_step_people.setVisibility(!TextUtils.isEmpty(contentBean.getDefaultSendAssignees()) ? View.VISIBLE : View.GONE);

                                    List<IDictItem> list = new ArrayList<>();
                                    DictItem dItem = new DictItem();
                                    dItem.setLabel(contentBean.getDefaultSendAssignees()
                                            == null ? "" : contentBean.getDefaultSendAssignees());
                                    dItem.setValue(contentBean.getDefaultSendAssigneesId()
                                            == null ? "" : contentBean.getDefaultSendAssigneesId());
                                    list.add(dItem);
                                    flow_step_people.initData(list);

                                }

                                break;
                            }
                        }
                    }

                }
            });
            getNextLink();
        } else {
            findViewById(R.id.ll_flow_step).setVisibility(View.GONE);
        }


        btn_cancel.setOnClickListener(v -> {
            dismiss();
        });

        btn_send.setOnClickListener(v -> {
            String etString = et_content.getText().toString();
            String flow_checkValue = flow_check.getValue();
            Log.d("vvvv", flow_checkValue + " : " + etString);
            doEventFlow(etString, flow_checkValue);
        });
    }


    /**
     * 获取下一环节
     */

    private void getNextLink() {
        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.GET_NEXT_LINK + "/" + taskInstId, null)
                .subscribe(s -> {
                    NextLink nextLink = new Gson().fromJson(s, new TypeToken<NextLink>() {
                    }.getType());
                    if (nextLink != null && nextLink.isSuccess()) {
                        mNextLink = nextLink;
                        if (!ListUtil.isEmpty(nextLink.getContent()) && nextLink.getContent().size() == 1) {
                            List<NextLink.ContentBean> content = nextLink.getContent();
                            NextLink.ContentBean contentBean = content.get(0);
                            if (!contentBean.isNeedSelectAssignee()) {
                                flow_step_people.setVisibility(!TextUtils.isEmpty(contentBean.getDefaultSendAssignees()) ? View.VISIBLE : View.GONE);
//                                mAssigneeWidget.initData(new DictBuilder().item(contentBean.getDefaultSendAssignees()
//                                                == null ? "" : contentBean.getDefaultSendAssignees()
//                                        , contentBean.getDefaultSendAssigneesId()
//                                                == null ? "" : contentBean.getDefaultSendAssigneesId()
//                                ).build());

                                List<IDictItem> list = new ArrayList<>();
                                DictItem item = new DictItem();
                                item.setLabel(contentBean.getDefaultSendAssignees()
                                        == null ? "" : contentBean.getDefaultSendAssignees());
                                item.setValue(contentBean.getDefaultSendAssigneesId()
                                        == null ? "" : contentBean.getDefaultSendAssigneesId());
                                list.add(item);
                                flow_step_people.initData(list);
                                mDestActId = contentBean.getDestActId();
                                mMultiTask = contentBean.isMultiTask();
                                mUserTask = contentBean.isUserTask();
                            }
                        }

                        setNextLinkView(nextLink);
                    }
                });

        mCompositeDisposable.add(subscribe);
    }

    public void setNextLinkView(NextLink nextLink) {
        if (nextLink.getContent() != null) {
//            DictBuilder builder = new DictBuilder();
//            for (int i = 0; i < nextLink.getContent().size(); i++) {
//                String destActName = nextLink.getContent().get(i).getDestActName();
//                if (!TextUtils.isEmpty(destActName)) {
//                    builder.item(destActName);
//                }
//            }
            List<IDictItem> list = new ArrayList<>();
            for (int i = 0; i < nextLink.getContent().size(); i++) {
                String destActName = nextLink.getContent().get(i).getDestActName();
                if (!TextUtils.isEmpty(destActName)) {
                    DictItem item = new DictItem();
                    item.setLabel(destActName);
                    item.setValue(destActName);
                    list.add(item);
                }

            }
            flow_step.initData(list);
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
                            List<IDictItem> list = new ArrayList<>();
                            for (User user : data) {
                                DictItem dictItem = new DictItem();
                                dictItem.setValue(user.getUserName() == null ?
                                        "" : user.getUserName());
                                list.add(dictItem);
                            }
                            flow_step_people.initData(list);
                            flow_step_people.setVisibility(View.VISIBLE);
                        } else {
                            flow_step_people.setVisibility(View.GONE);
                        }
                    }
                });

        mCompositeDisposable.add(subscribe);


    }

    private void doEventFlow(String etString, String flow_checkValue) {
        if ("1".equals(isDeal)) {//办理
            if (TextUtils.isEmpty(flow_step.getValue())) {
                ToastUtil.longToast(getContext(), "请选择下一环节");
                return;
            }
            if (TextUtils.isEmpty(flow_step_people.getValue())) {
                ToastUtil.longToast(getContext(), "请选择审批人");
                return;
            }
        }
        if (TextUtils.isEmpty(flow_checkValue)) {
            ToastUtil.longToast(getContext(), "请选择办理结果");
            return;
        }
        if (TextUtils.isEmpty(etString)) {
            ToastUtil.longToast(getContext(), "请填写办理意见");
            return;
        }
        if ("1".equals(isDeal)) {//办理
            submitDeal(etString, flow_checkValue);
        } else if ("2".equals(isDeal)) {//预审通过（窗口）
            preApprovePass(etString, flow_checkValue);
        } else if ("3".equals(isDeal)) {//不予通过（窗口）
            outScope(etString, flow_checkValue);
        }


    }

    private void submitDeal(String etString, String flow_checkValue) {
        LinkSendConfig linkSendConfig = new LinkSendConfig();
        linkSendConfig.setTaskId(taskInstId);
        List<LinkSendConfig.SendConfigsBean> sendConfigs = new ArrayList<>();
        LinkSendConfig.SendConfigsBean sendConfigsBean = new LinkSendConfig.SendConfigsBean();
        sendConfigsBean.setAssignees(flow_step_people.getValue() == null ? "" : flow_step_people.getValue());
        sendConfigsBean.setDestActId(mDestActId);
        sendConfigsBean.setIsEnableMultiTask(mMultiTask);
        sendConfigsBean.setIsUserTask(mUserTask);
        sendConfigs.add(sendConfigsBean);
        linkSendConfig.setSendConfigs(sendConfigs);
        linkSendConfig.setConclusionOptionValue(flow_checkValue);
        Map<String, String> map = new HashMap<>();
        map.put("opinion", etString);

        Gson gson = new Gson();
        String sendObjectStr = gson.toJson(linkSendConfig);
        map.put("sendObjectStr", sendObjectStr);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("正在发送，请等待");
        progressDialog.show();

        Disposable subscribe = HttpUtil.getInstance(AwUrlManager.serverUrl()).post(GcjsUrlConstant.SEND_TO_NEXT_LINK
                , map)
                .subscribe(s -> {
                    progressDialog.dismiss();
                    ApiResult apiResult = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                    }.getType());
                    if (apiResult != null && apiResult.isSuccess()) {
                        String name = apiResult.getMessage();
                        String nextTip = name == null ? "" : ",下一环环节处理人为：" + name;
                        new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("发送成功")
                                .showCancelButton(false)
                                .setContentText("流程已发送至：" + flow_step.getValue() + " 环节" + nextTip)
                                .setConfirmClickListener(sweetAlertDialog -> {
                                    sweetAlertDialog.dismiss();
                                    EventDealDialog.this.dismiss();
                                    if (successListener != null) {
                                        successListener.onSuccessListener();
                                    }
                                })
                                .show();

                    } else {
                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("发送失败")
                                .showCancelButton(false)
                                .setContentText(apiResult.getMessage())
                                .show();
//                        ToastUtil.shortToast(SendNextLinkActivity.this, "发送失败:" + apiResult.getMessage());
                    }

                });
        mCompositeDisposable.add(subscribe);

    }

    //预审通过（窗口）
    private void preApprovePass(String etString, String flow_checkValue) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("正在发送，请等待");
        progressDialog.show();

        eventRepository.addTaskComment(etString, taskInstId, processInstanceId)
                .subscribe(apiResult -> {
//                    if (apiResult.isSuccess()) {
                        //保存节点意见成功，启动流程
                        LinkSendConfig configsBean = new LinkSendConfig();
                        configsBean.setTaskId(taskInstId);
                        configsBean.setConclusionGroupCode("groupCode001");
                        configsBean.setConclusionOptionValue(flow_checkValue);
                        configsBean.setEnableConclusion("1");
                        List<LinkSendConfig.SendConfigsBean> beans = new ArrayList<>();
                        LinkSendConfig.SendConfigsBean sendConfigsBean = new LinkSendConfig.SendConfigsBean();
                        sendConfigsBean.setIsUserTask(true);
                        sendConfigsBean.setIsEnableMultiTask(false);
                        sendConfigsBean.setDestActId("bumenshenpi");
                        beans.add(sendConfigsBean);
                        configsBean.setSendConfigs(beans);

                        Map<String, String> map = new HashMap<>();
                        map.put("applyinstId", applyinstId);
                        map.put("iteminstId",iteminstId);
                        map.put("itemState", "ACCEPT_DEAL");
                        map.put("applyState", "ACCEPT_DEAL");
                        map.put("conclusionOptionValue",flow_checkValue);

                        Gson gson = new Gson();
                        String sendObjectStr = gson.toJson(configsBean);
                        map.put("sendObjectStr", sendObjectStr);

                        eventRepository.sendAndChange(map)
                                .subscribe(apiResult1 -> {
                                    progressDialog.dismiss();
                                    if (apiResult1 != null && apiResult1.isSuccess()) {
                                        String messageString = apiResult1.getMessage();
                                        String tip = TextUtils.isEmpty(messageString) ? "发送成功" : messageString;
                                        new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                                .setTitleText("发送成功")
                                                .setContentText(tip)
                                                .setConfirmClickListener(sweetAlertDialog -> {
                                                    sweetAlertDialog.dismiss();
                                                    EventDealDialog.this.dismiss();
                                                    if (successListener != null) {
                                                        successListener.onSuccessListener();
                                                    }
                                                })
                                                .show();
                                    }else{
                                        String messageString = apiResult1.getMessage();
                                        String tip = TextUtils.isEmpty(messageString) ? "发送失败" : messageString;
                                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("发送失败")
                                                .setContentText(tip)
                                                .setConfirmClickListener(sweetAlertDialog -> {
                                                    sweetAlertDialog.dismiss();
                                                    EventDealDialog.this.dismiss();
                                                    if (successListener != null) {
                                                        successListener.onSuccessListener();
                                                    }
                                                })
                                                .show();
                                    }
                                }, throwable -> {
                                    throwable.printStackTrace();
                                    progressDialog.dismiss();
                                    ToastUtil.longToast(getContext(),"网络错误");
                                });
//                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    progressDialog.dismiss();
                    ToastUtil.longToast(getContext(),"保存节点意见失败");
                });
    }

    //不予通过（窗口）
    private void outScope(String etString, String flow_checkValue) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("正在发送，请等待");
        progressDialog.show();

        eventRepository.addTaskComment(etString, taskInstId, processInstanceId)
                .subscribe(apiResult -> {
//                    if (apiResult.isSuccess()) {
                        //保存节点意见成功，启动流程
                        LinkSendConfig configsBean = new LinkSendConfig();
                        configsBean.setTaskId(taskInstId);
                        configsBean.setConclusionGroupCode("groupCode001");
                        configsBean.setConclusionOptionValue(flow_checkValue);
                        configsBean.setEnableConclusion("1");
                        List<LinkSendConfig.SendConfigsBean> beans = new ArrayList<>();
                        LinkSendConfig.SendConfigsBean sendConfigsBean = new LinkSendConfig.SendConfigsBean();
                        sendConfigsBean.setIsUserTask(false);
                        sendConfigsBean.setIsEnableMultiTask(false);
                        sendConfigsBean.setDestActId("jieshu");
                        beans.add(sendConfigsBean);
                        configsBean.setSendConfigs(beans);

                        Map<String, String> map = new HashMap<>();
                        map.put("applyinstId", applyinstId);
                        map.put("iteminstId", iteminstId);
                        map.put("itemState", "OUT_SCOPE");
                        map.put("applyState", "OUT_SCOPE");
                        map.put("conclusionOptionValue", flow_checkValue);

                        Gson gson = new Gson();
                        String sendObjectStr = gson.toJson(configsBean);
                        map.put("sendObjectStr", sendObjectStr);

                        eventRepository.sendAndChange(map)
                                .subscribe(apiResult1 -> {
                                    progressDialog.dismiss();
                                    if (apiResult1 != null && apiResult1.isSuccess()) {
                                        String messageString = apiResult1.getMessage();
                                        String tip = TextUtils.isEmpty(messageString) ? "发送成功" : messageString;
                                        new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                                .setTitleText("发送成功")
                                                .setContentText(tip)
                                                .setConfirmClickListener(sweetAlertDialog -> {
                                                    sweetAlertDialog.dismiss();
                                                    EventDealDialog.this.dismiss();
                                                    if (successListener != null) {
                                                        successListener.onSuccessListener();
                                                    }
                                                })
                                                .show();
                                    }else{
                                        String messageString = apiResult1.getMessage();
                                        String tip = TextUtils.isEmpty(messageString) ? "发送失败" : messageString;
                                        new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                                .setTitleText("发送失败")
                                                .setContentText(tip)
                                                .setConfirmClickListener(sweetAlertDialog -> {
                                                    sweetAlertDialog.dismiss();
                                                    EventDealDialog.this.dismiss();
                                                    if (successListener != null) {
                                                        successListener.onSuccessListener();
                                                    }
                                                })
                                                .show();
                                    }
                                }, throwable -> {
                                    throwable.printStackTrace();
                                    ToastUtil.longToast(getContext(),"网络错误");
                                    progressDialog.dismiss();
                                });
//                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    progressDialog.dismiss();
                    ToastUtil.longToast(getContext(),"保存节点意见失败");
                });
    }


    public interface SuccessListener {
        /**
         * 流程发送成功操作
         */
        void onSuccessListener();
    }
}
