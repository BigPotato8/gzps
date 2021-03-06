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
    String  unreceiveReasonFirst; //1.?????????????????????????????????????????????????????????
    String  unreceiveReasonFirstParam; //123
    String  unreceiveReasonSecond; //2.?????????????????????????????????????????????????????????
    String  unreceiveReasonThird; //3.???????????????????????????????????????????????????
    String  unreceiveReasonFourth; //4.????????????????????????????????????
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
        btn_submit.setText("??????");
        generateInfo();
    }

    public void generateInfo() {
        //??????
        mFillBlankView1 = new FillBlankView(getActivity(), 1);
        mFillBlankView1.setVisible(View.GONE);
        String reject = "     ????????????????????????????????????????????????????????????????????????        (????????????)           ??????????????????????????????        ??????????????????           ???????????????????????????????????????????????????????????????????????????????????????????????????";
        List<AnswerRange> rejectList = new ArrayList<>();
        rejectList.add(new AnswerRange(29, 54));
        rejectList.add(new AnswerRange(64, 89));
        mFillBlankView1.setData(reject, rejectList);
        mFormPresenter.addViews("linkmanPhone", ElementPosition.BELOW, mFillBlankView1);
        if (notAcceptReceiptBean.getReconsiderRegionName() != null) {
            mFillBlankView1.fillAnswer(notAcceptReceiptBean.getReconsiderRegionName(), 0);//????????????
        }
        if (notAcceptReceiptBean.getSuperiorOrgName() != null) {
            mFillBlankView1.fillAnswer(notAcceptReceiptBean.getSuperiorOrgName(), 1);// ??????????????????
        }
        generateReason();

        mFillBlankView = new FillBlankView(getActivity(), 0);
        String content = "     ????????????            ???            ???            ???????????????                              ?????????         ?????????????????????????????????          ??????????????????????????????????????????\n     ??????????????????(???\"???\"??????)???";
        // ??????????????????
        mRangeList = new ArrayList<>();

        // ??????????????????
        mRangeList.add(new AnswerRange(9, 21));
        mRangeList.add(new AnswerRange(22, 34));
        mRangeList.add(new AnswerRange(35, 47));
        mRangeList.add(new AnswerRange(52, 82));
        mRangeList.add(new AnswerRange(85, 115));
        mFillBlankView.setData(content, mRangeList);
        mFormPresenter.addViews("linkmanPhone", ElementPosition.BELOW, mFillBlankView);
        //2???????????????????????????
        Calendar calendar = Calendar.getInstance();
        // ?????????
        mFillBlankView.fillAnswer(String.valueOf(calendar.get(Calendar.YEAR)), 0);//???
        mFillBlankView.fillAnswer(String.valueOf(calendar.get(Calendar.MONTH) + 1), 1);//???
        mFillBlankView.fillAnswer(String.valueOf(calendar.get(Calendar.DATE)), 2);//???
        if (notAcceptReceiptBean.getApplyBusinessName() != null) {
            mFillBlankView.fillAnswer(notAcceptReceiptBean.getApplyBusinessName(), 3);//
        }
        if (notAcceptReceiptBean.getLegislativeAuthority() != null) {
            mFillBlankView.fillAnswer(notAcceptReceiptBean.getLegislativeAuthority(), 4);//
        }
    }

    /**
     * ??????????????????(??????)
     */
    private void generateReason() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.checkbox_reason_layout, null);
        mLlReasonWrite = (LinearLayout) view.findViewById(R.id.ll_reason_write);
        //???????????????
        mReasonView = new FillBlankView(getActivity(), 2);
        mReasonView.setTextSize(14);
        mReasonView.setVisible(View.GONE);
        String content = "1.??????????????????????????????????????????               ???????????????";
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
                        unreceiveReasonFirst = "1.?????????????????????????????????????????????????????????";
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
                            ToastUtil.shortToast(getActivity(), "?????????????????????");
                            return;
                        }
                        if(year.length() != 4){
                            ToastUtil.shortToast(getActivity(), "?????????????????????");
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
                            ToastUtil.shortToast(getActivity(), "?????????????????????");
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
                            ToastUtil.shortToast(getActivity(), "??????????????????");
                            return;
                        }
                }
            }
            if (i == 3) {
                if (list.get(i) == null) {
                    ToastUtil.shortToast(getActivity(), "????????????/?????????????????????????????????");
                    return;
                } else {
                    applyBusinessName = list.get(i);
                }
            }
            if (i == 4) {
                if (list.get(i) == null) {
                    ToastUtil.shortToast(getActivity(), "????????????????????????????????????????????????");
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
        String reconsiderRegionName = null; //????????????
        String superiorOrgName = null; // ??????????????????
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
        progressDialog.setMessage("????????????????????????");
        HashMap<String, String> params = new HashMap<>();
        params.put("aeaHiUnreceivingReceiptJson ", toJson);
        progressDialog.show();
        mEventRepository.postNotAcceptReceipt(params)
                .subscribe(new Consumer<ApiResult>() {
                    @Override
                    public void accept(ApiResult apiResult) throws Exception {
                        progressDialog.dismiss();
                        if (apiResult.isSuccess()) {
                            ToastUtil.longToast(getContext(), "????????????");
                            getActivity().setResult(POST_SUCCESS);
                            getActivity().finish();
                        } else {
                            ToastUtil.longToast(getContext(), "????????????");
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    progressDialog.dismiss();
                    ToastUtil.longToast(getContext(), "????????????");
                });
    }


    @Override
    protected void delete() {
        String receiptId = notAcceptReceiptBean.getUnreceiveReceiptId();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", receiptId);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("???????????????");
        //??????????????????
        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mEventRepository.deleteNotAcceptReceipt(params)
                        .subscribe(new Consumer<ApiResult>() {
                            @Override
                            public void accept(ApiResult apiResult) throws Exception {
                                if (apiResult.isSuccess()) {
                                    ToastUtil.longToast(getContext(), "????????????");
                                    getActivity().finish();
                                } else {
                                    ToastUtil.longToast(getContext(), "????????????");
                                }
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            ToastUtil.longToast(getContext(), "????????????");
                        });
            }
        });
        //??????????????????
        builder.setPositiveButton("??????", null);
        //???????????????
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
                                .title("????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("applyinstCode")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("???????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("applyUnitName")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("??????????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("unitIdNo")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????????????????/?????????????????????")
                                .isEnable(true)
                                .build())
                        .build())
                .addElement(new ElementBuilder("projName")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("??????????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("projCode")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("??????????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("unitLinkPhone")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("??????????????????")
                                .isEnable(true)
                                .build())
                        .build())
                .addElement(new ElementBuilder("unitAdress")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("??????????????????")
                                .isEnable(true)
                                .build())
                        .build())
//                .addElement(new ElementBuilder("reconsiderRegionName")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("????????????")
//                                .hint("?????????")
//                                .defaultValue("")
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("superiorOrgName")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("??????????????????")
//                                .defaultValue("")
//                                .hint("?????????")
//                                .build())
//                        .build())
                .addElement(new ElementBuilder("legalRepresentativeName")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("?????????????????????")
                                .isEnable(true)
                                .build())
                        .build())
                .addElement(new ElementBuilder("legalRepresentativeIdNo")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("???????????????????????????")
                                .isEnable(true)
                                .build())
                        .build())
                .addElement(new ElementBuilder("linkmanName")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????/???????????????")
                                .isEnable(true)
                                .build())
                        .build())
                .addElement(new ElementBuilder("linkmanIdNo")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????/??????????????????")
                                .isEnable(true)
                                .build())
                        .build())
                .addElement(new ElementBuilder("linkmanPhone")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????")
                                .isEnable(true)
                                .build())
                        .build());
//                .addElement(new ElementBuilder("applyTime4String")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("????????????")
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("applyBusinessName")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("????????????/????????????")
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("legislativeAuthority")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("???????????????????????????")
//                                .build())
//                        .build())
//                .addDivider("??????????????????")
//                .addElement(new ElementBuilder("byslly")
//                        .widget(new WidgetBuilder(CHECKBOX)
//                                .addProperty(PROPERTY_TITLE, "??????????????????")
////                                .dictTypeCode("sewage_station_jgxs")
//                                .initData(DictLocalConfig.notAcceptReason())
//                                .hint("?????????")
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("unreceiveReasonFirstParam")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("??????????????????")
//                                .isVisible(true)
//                                .hint("???????????????????????????????????????")
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("unreceiveReasonFifth")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .isVisible(true)
//                                .title("????????????")
//                                .hint("?????????")
//                                .build())
//                        .build());
//                .addElement(new ElementBuilder("unreceiveReasonFirstParam")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("??????????????????1")
//                                .hint("?????????")
//                                .defaultValue("")
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("unreceiveReasonSecond")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("??????????????????2")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("unreceiveReasonThird")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("??????????????????3")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("unreceiveReasonFourth")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("??????????????????4")
//                                .isEnable(false)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("unreceiveReasonFifth")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("??????????????????5")
//                                .isEnable(false)
//                                .build())
//                        .build());
        return fb.build();
    }
}
