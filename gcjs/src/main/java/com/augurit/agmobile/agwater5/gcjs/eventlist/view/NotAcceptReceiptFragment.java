package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.solver.GoalRow;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.AnswerRange;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.NotAcceptReceiptBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.busi.bpm.form.model.FormInfo;
import com.augurit.agmobile.busi.bpm.form.util.ElementBuilder;
import com.augurit.agmobile.busi.bpm.form.util.FormBuilder;
import com.augurit.agmobile.busi.bpm.form.util.WidgetBuilder;
import com.augurit.agmobile.busi.bpm.form.view.ElementPosition;
import com.augurit.agmobile.busi.bpm.form.view.FormState;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.agmobile.busi.bpm.widget.WidgetListener;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.augurit.common.common.form.AwFormConfig;
import com.augurit.common.common.form.AwFormFragment;
import com.augurit.common.common.form.DictLocalConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

import static com.augurit.agmobile.agwater5.gcjs.eventlist.view.MaterialCorrectionActivity.POST_SUCCESS;
import static com.augurit.agmobile.busi.bpm.widget.WidgetProperty.PROPERTY_TITLE;
import static com.augurit.agmobile.busi.bpm.widget.WidgetType.CHECKBOX;
import static com.augurit.agmobile.busi.bpm.widget.WidgetType.EDITTEXT;

public class NotAcceptReceiptFragment extends AwFormFragment {
    EventRepository mEventRepository;
    private boolean isFirst = true;
    private EventListItem.DataBean mEventListItem;
    private EventBean mData;
    private NotAcceptReceiptBean notAcceptReceiptBean;
    private FillBlankView mFillBlankView1;
    private FillBlankView mFillBlankView;
    private LinearLayout mLlReasonWrite;
    private FillBlankView mReasonView;
    private CheckBox mCb1;
    private CheckBox mCb2;
    private TextView mTv2;
    private CheckBox mCb3;
    private TextView mTv3;
    private CheckBox mCb4;
    private TextView mTv4;
    private CheckBox mCb5;
    private EditText mEt5;
//    boolean[] mBooleansCheckBox = {false,false,false,false,false};
    private List<CheckBox> mCheckBoxList = new ArrayList<>();
    String  unreceiveReasonFirst; //1.申请不属于项目受理范围，应向提出申请。
    String  unreceiveReasonFirstParam; //123
    String  unreceiveReasonSecond; //2.经告知补正材料后，未及时提交申请材料。
    String  unreceiveReasonThird; //3.补正材料后，申请材料仍不符合要求。
    String  unreceiveReasonFourth; //4.申请单位不符合申请资格。
    String  unreceiveReasonFifth; //123
    private List<AnswerRange> mRangeList;

    public void setEventListItem(EventListItem.DataBean eventListItem) {
        mEventListItem = eventListItem;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initArguments() {
        super.initArguments();
        mFormState = FormState.STATE_EDIT;
        mFormCode = AwFormConfig.FORM_NOT_ACCEPT_RECEIPT;
        mEventRepository = new EventRepository();

        getEventData();

    }


    @Override
    protected void onFormLoaded() {
        super.onFormLoaded();
        btn_delete.setVisibility(View.VISIBLE);
        btn_save.setVisibility(View.GONE);
        btn_submit.setText("保存");
        generateInfo();
    }

    public void generateInfo() {
        //拒绝
        mFillBlankView1 = new FillBlankView(getActivity(), 1);
        mFillBlankView1.setVisible(View.GONE);
        String reject = "     申请人如有异议，可自收到本决定书之日起六十日内向        (行政区域)           人民政府复议委员会或        上级主管部门           申请行政复议，或者六个月内依法向佛山市顺德区人民法院提起行政诉讼。";
        List<AnswerRange> rejectList = new ArrayList<>();
        rejectList.add(new AnswerRange(29, 54));
        rejectList.add(new AnswerRange(64, 89));
        mFillBlankView1.setData(reject, rejectList);
        mFormPresenter.addViews("linkmanPhone", ElementPosition.BELOW, mFillBlankView1);
        if (notAcceptReceiptBean.getReconsiderRegionName() != null) {
            mFillBlankView1.fillAnswer(notAcceptReceiptBean.getReconsiderRegionName(), 0);//行政区域
        }
        if (notAcceptReceiptBean.getSuperiorOrgName() != null) {
            mFillBlankView1.fillAnswer(notAcceptReceiptBean.getSuperiorOrgName(), 1);// 上级主管部门
        }
        generateReason();

        mFillBlankView = new FillBlankView(getActivity(), 0);
        String content = "     你单位于            年            月            日申请办理                              ，根据         （相关法律条款或理由）          ，不符合受理条件，不予受理。\n     具体理由如下(打\"√\"部分)：";
        // 答案范围集合
        mRangeList = new ArrayList<>();

        // 答案范围集合
        mRangeList.add(new AnswerRange(9, 21));
        mRangeList.add(new AnswerRange(22, 34));
        mRangeList.add(new AnswerRange(35, 47));
        mRangeList.add(new AnswerRange(52, 82));
        mRangeList.add(new AnswerRange(85, 115));
        mFillBlankView.setData(content, mRangeList);
        mFormPresenter.addViews("linkmanPhone", ElementPosition.BELOW, mFillBlankView);
        //2、日历类的时间操作
        Calendar calendar = Calendar.getInstance();
        // 设置值
        mFillBlankView.fillAnswer(String.valueOf(calendar.get(Calendar.YEAR)), 0);//年
        mFillBlankView.fillAnswer(String.valueOf(calendar.get(Calendar.MONTH) + 1), 1);//月
        mFillBlankView.fillAnswer(String.valueOf(calendar.get(Calendar.DATE)), 2);//日
        if (notAcceptReceiptBean.getApplyBusinessName() != null) {
            mFillBlankView.fillAnswer(notAcceptReceiptBean.getApplyBusinessName(), 3);//
        }
        if (notAcceptReceiptBean.getLegislativeAuthority() != null) {
            mFillBlankView.fillAnswer(notAcceptReceiptBean.getLegislativeAuthority(), 4);//
        }
    }

    /**
     * 具体理由界面(多选)
     */
    private void generateReason() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.checkbox_reason_layout, null);
        mLlReasonWrite = (LinearLayout) view.findViewById(R.id.ll_reason_write);
        //第一条理由
        mReasonView = new FillBlankView(getActivity(), 2);
        mReasonView.setTextSize(14);
        mReasonView.setVisible(View.GONE);
        String content = "1.申请不属于项目受理范围，应向               提出申请。";
        List<AnswerRange> reasonList = new ArrayList<>();
        reasonList.add(new AnswerRange(16, 31));
        mReasonView.setData(content, reasonList);
        mLlReasonWrite.addView(mReasonView);
        initReasonView(view);
        mFormPresenter.addViews("linkmanPhone", ElementPosition.BELOW, view);
        if(notAcceptReceiptBean.getUnreceiveReasonFirst() != null&&!"".equals(notAcceptReceiptBean.getUnreceiveReasonFirst())){
            mCb1.setChecked(true);
            if(notAcceptReceiptBean.getUnreceiveReasonFirstParam()!=null&&!"".equals(notAcceptReceiptBean.getUnreceiveReasonFirstParam())){
                mReasonView.fillAnswer(notAcceptReceiptBean.getUnreceiveReasonFirstParam(),0);
            }
        }
        if(notAcceptReceiptBean.getUnreceiveReasonSecond() != null &&!"".equals(notAcceptReceiptBean.getUnreceiveReasonSecond())){
            mCb2.setChecked(true);
        }
        if(notAcceptReceiptBean.getUnreceiveReasonThird() != null &&!"".equals(notAcceptReceiptBean.getUnreceiveReasonThird())){
            mCb3.setChecked(true);
        }
        if(notAcceptReceiptBean.getUnreceiveReasonFourth() != null &&!"".equals(notAcceptReceiptBean.getUnreceiveReasonFourth())){
            mCb4.setChecked(true);
        }
        if(notAcceptReceiptBean.getUnreceiveReasonFifth() != null&&!"".equals(notAcceptReceiptBean.getUnreceiveReasonFifth())){
            mCb5.setChecked(true);
            mEt5.setText(notAcceptReceiptBean.getUnreceiveReasonFifth());
        }
    }

    private void initReasonView(View v) {
        mCb1 = (CheckBox) v.findViewById(R.id.cb_1);
        mCb2 = (CheckBox) v.findViewById(R.id.cb_2);
        mTv2 = (TextView) v.findViewById(R.id._tv2);
        mCb3 = (CheckBox) v.findViewById(R.id.cb_3);
        mTv3 = (TextView) v.findViewById(R.id.tv_3);
        mCb4 = (CheckBox) v.findViewById(R.id.cb_4);
        mTv4 = (TextView) v.findViewById(R.id.tv_4);
        mCb5 = (CheckBox) v.findViewById(R.id.cb_5);
        mEt5 = (EditText) v.findViewById(R.id.et_5);
        mCheckBoxList.add(mCb1);
        mCheckBoxList.add(mCb2);
        mCheckBoxList.add(mCb3);
        mCheckBoxList.add(mCb4);
        mCheckBoxList.add(mCb5);
    }

    private void getReasonValue() {
        for (int i = 0; i < mCheckBoxList.size(); i++) {
            switch (i){
                case 0:
                    if(mCheckBoxList.get(i).isChecked()){
                        unreceiveReasonFirst = "1.申请不属于项目受理范围，应向提出申请。";
                        unreceiveReasonFirstParam = mReasonView.getAnswerList().get(0);
                    }else {
                        unreceiveReasonFirst = "";
                        unreceiveReasonFirstParam =  "";
                    }
                    break;
                case 1:
                    if(mCheckBoxList.get(i).isChecked()){
                        unreceiveReasonSecond = mTv2.getText().toString();
                    }else{
                        unreceiveReasonSecond = "";
                    }
                    break;
                case 2:
                    if(mCheckBoxList.get(i).isChecked()){
                        unreceiveReasonThird = mTv3.getText().toString();
                    }else{
                        unreceiveReasonThird = "";
                    }
                    break;
                case 3:
                    if(mCheckBoxList.get(i).isChecked()){
                        unreceiveReasonFourth = mTv4.getText().toString();
                    }else{
                        unreceiveReasonFourth = "";
                    }
                    break;
                case 4:
                    if(mCheckBoxList.get(i).isChecked()){
                        unreceiveReasonFifth = mEt5.getText().toString();
                    }else{
                        unreceiveReasonFifth = "";
                    }
                    break;
            }
        }
    }

    @Override
    protected void submit() {
        HashMap<String, String> widgetValues = mFormPresenter.getWidgetValues();
        getReasonValue();
        List<String> list = mFillBlankView.getAnswerList();
        StringBuilder time = new StringBuilder();
        String applyBusinessName = null;
        String legislativeAuthority = null;
        for (int i = 0; i < list.size(); i++) {
            if (i <= 2) {
                switch (i) {
                    case 0:
                        String year = list.get(i);
                        if(year == null){
                            ToastUtil.shortToast(getActivity(), "输入的年份为空");
                            return;
                        }
                        if(year.length() != 4){
                            ToastUtil.shortToast(getActivity(), "输入的年份有误");
                            return;
                        }
                        time.append(year + "-");
                        break;
                    case 1:
                        String month = list.get(i);
                        if (month != null) {
                            if (Integer.parseInt(month) < 10) {
                                month = "0" + month;
                            }
                            time.append(month+"-");
                        }else {
                            ToastUtil.shortToast(getActivity(), "输入的月份为空");
                            return;
                        }
                        break;
                    case 2:
                        String day = list.get(i);
                        if (day != null) {
                            if (Integer.parseInt(day) < 10) {
                                day = "0" + day;
                            }
                            time.append(day);
                        }else {
                            ToastUtil.shortToast(getActivity(), "输入的日为空");
                            return;
                        }
                }
            }
            if (i == 3) {
                if (list.get(i) == null) {
                    ToastUtil.shortToast(getActivity(), "申办事项/阶段名称输入的内容为空");
                    return;
                } else {
                    applyBusinessName = list.get(i);
                }
            }
            if (i == 4) {
                if (list.get(i) == null) {
                    ToastUtil.shortToast(getActivity(), "依据法律条款或理由输入的内容为空");
                    return;
                } else {
                    legislativeAuthority = list.get(i);
                }
            }
        }
        widgetValues.put("applyTime4String",time+"");
        widgetValues.put("applyBusinessName",applyBusinessName);
        widgetValues.put("legislativeAuthority",legislativeAuthority);

        widgetValues.put("unreceiveReasonFirst",unreceiveReasonFirst);
        widgetValues.put("unreceiveReasonFirstParam",unreceiveReasonFirstParam);
        widgetValues.put("unreceiveReasonSecond",unreceiveReasonSecond);
        widgetValues.put("unreceiveReasonThird",unreceiveReasonThird);
        widgetValues.put("unreceiveReasonFourth",unreceiveReasonFourth);
        widgetValues.put("unreceiveReasonFifth",unreceiveReasonFifth);

        widgetValues.put("applyinstId", notAcceptReceiptBean.getApplyinstId());
        widgetValues.put("iteminstId", notAcceptReceiptBean.getIteminstId());
        widgetValues.put("orgName", notAcceptReceiptBean.getOrgName());
        widgetValues.put("receiptInstrumentName", notAcceptReceiptBean.getReceiptInstrumentName());
//        if (TextUtils.isEmpty(widgetValues.get("superiorOrgName"))) {
////            widgetValues.put("superiorOrgName", LoginManager.getInstance().getCurrentUser().getCurrentOrg().getName());
//            widgetValues.put("superiorOrgName", "");
//        }
//        if (TextUtils.isEmpty(widgetValues.get("reconsiderRegionName"))) {
////            widgetValues.put("reconsiderRegionName",LoginManager.getInstance().getCurrentUser().getUserCode());
//            widgetValues.put("reconsiderRegionName", "");
//        }
        List<String> rejectList = mFillBlankView1.getAnswerList();
        String reconsiderRegionName = null; //行政区域
        String superiorOrgName = null; // 上级主管部门
        for (int i = 0; i < rejectList.size(); i++) {
            switch (i){
                case 0:
                    reconsiderRegionName = rejectList.get(i);
                    break;
                case 1:
                    superiorOrgName = rejectList.get(i);
                    break;
            }
        }
        widgetValues.put("reconsiderRegionName",reconsiderRegionName);
        widgetValues.put("superiorOrgName",superiorOrgName);

        String toJson = new Gson().toJson(widgetValues);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("正在发送，请等待");
        HashMap<String, String> params = new HashMap<>();
        params.put("aeaHiUnreceivingReceiptJson ", toJson);
        progressDialog.show();
        mEventRepository.postNotAcceptReceipt(params)
                .subscribe(new Consumer<ApiResult>() {
                    @Override
                    public void accept(ApiResult apiResult) throws Exception {
                        progressDialog.dismiss();
                        if (apiResult.isSuccess()) {
                            ToastUtil.longToast(getContext(), "保存成功");
                            getActivity().setResult(POST_SUCCESS);
                            getActivity().finish();
                        } else {
                            ToastUtil.longToast(getContext(), "保存失败");
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    progressDialog.dismiss();
                    ToastUtil.longToast(getContext(), "保存失败");
                });
    }


    @Override
    protected void delete() {
        String receiptId = notAcceptReceiptBean.getUnreceiveReceiptId();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", receiptId);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("确定删除？");
        //设置确定按钮
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mEventRepository.deleteNotAcceptReceipt(params)
                        .subscribe(new Consumer<ApiResult>() {
                            @Override
                            public void accept(ApiResult apiResult) throws Exception {
                                if (apiResult.isSuccess()) {
                                    ToastUtil.longToast(getContext(), "删除成功");
                                    getActivity().finish();
                                } else {
                                    ToastUtil.longToast(getContext(), "删除失败");
                                }
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            ToastUtil.longToast(getContext(), "删除失败");
                        });
            }
        });
        //设置取消按钮
        builder.setPositiveButton("取消", null);
        //显示提示框
        builder.show();

    }

    private void getEventData() {
        if (isFirst) {
            ProgressDialogUtil.showProgressDialog(getActivity(), false);
            mEventRepository.getNotReceiptInfo(mEventListItem == null ? "" : mEventListItem.getApplyinstId(), mEventListItem == null ? "" : mEventListItem.getTaskId())
                    .subscribe(listApiResult -> {
                        ProgressDialogUtil.dismissProgressDialog();
                        if (listApiResult.isSuccess()) {
                            isFirst = false;
                            notAcceptReceiptBean = listApiResult.getData();
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
                            mRecord = new FormRecord();
                            mRecord.setValues(map);
                            FormInfo formInfo = getGcjsProjectDetail();
                            loadForm(formInfo);

                        }
                    }, throwable -> {
                        ProgressDialogUtil.dismissProgressDialog();
                        throwable.printStackTrace();
                    });
        }

    }


    @Override
    protected boolean shouldLoadImmediately() {
        return false;
    }


    @Subscribe
    public void receive(EventInfoBean mData) {

    }

    private FormInfo getGcjsProjectDetail() {
        FormBuilder fb = new FormBuilder("")
                .addElement(new ElementBuilder("instrumentNo")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("文书编号")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("applyinstCode")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("申报流水号")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("applyUnitName")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("申请单位名称")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("unitIdNo")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("统一社会信用代码/营业执照注册号")
                                .isEnable(true)
                                .build())
                        .build())
                .addElement(new ElementBuilder("projName")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("申请项目名称")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("projCode")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("统一项目代码")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("unitLinkPhone")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("单位联系电话")
                                .isEnable(true)
                                .build())
                        .build())
                .addElement(new ElementBuilder("unitAdress")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("单位通讯地址")
                                .isEnable(true)
                                .build())
                        .build())
//                .addElement(new ElementBuilder("reconsiderRegionName")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("行政区域")
//                                .hint("请填写")
//                                .defaultValue("")
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("superiorOrgName")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("上级主管部门")
//                                .defaultValue("")
//                                .hint("请填写")
//                                .build())
//                        .build())
                .addElement(new ElementBuilder("legalRepresentativeName")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("法定代表人姓名")
                                .isEnable(true)
                                .build())
                        .build())
                .addElement(new ElementBuilder("legalRepresentativeIdNo")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("法定代表人证件号码")
                                .isEnable(true)
                                .build())
                        .build())
                .addElement(new ElementBuilder("linkmanName")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("受委托人/联系人姓名")
                                .isEnable(true)
                                .build())
                        .build())
                .addElement(new ElementBuilder("linkmanIdNo")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("受委托人/联系人证件号")
                                .isEnable(true)
                                .build())
                        .build())
                .addElement(new ElementBuilder("linkmanPhone")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("联系电话")
                                .isEnable(true)
                                .build())
                        .build());
//                .addElement(new ElementBuilder("applyTime4String")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("申请日期")
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("applyBusinessName")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("申办事项/阶段名称")
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("legislativeAuthority")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("依据法律条款或理由")
//                                .build())
//                        .build())
//                .addDivider("不予受理理由")
//                .addElement(new ElementBuilder("byslly")
//                        .widget(new WidgetBuilder(CHECKBOX)
//                                .addProperty(PROPERTY_TITLE, "不予受理理由")
////                                .dictTypeCode("sewage_station_jgxs")
//                                .initData(DictLocalConfig.notAcceptReason())
//                                .hint("请选择")
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("unreceiveReasonFirstParam")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("转申请的环节")
//                                .isVisible(true)
//                                .hint("请填写应向哪个环节提出申请")
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("unreceiveReasonFifth")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .isVisible(true)
//                                .title("其他理由")
//                                .hint("请填写")
//                                .build())
//                        .build());
//                .addElement(new ElementBuilder("unreceiveReasonFirstParam")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("不予受理理由1")
//                                .hint("请填写")
//                                .defaultValue("")
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("unreceiveReasonSecond")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("不予受理理由2")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("unreceiveReasonThird")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("不予受理理由3")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("unreceiveReasonFourth")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("不予受理理由4")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("unreceiveReasonFifth")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("不予受理理由5")
//                                .isEnable(false)
//                                .build())
//                        .build());
        return fb.build();
    }
}
