package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.augurit.agmobile.agwater5.gcjs.eventlist.model.AcceptReceiptBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.AnswerRange;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ApproveNotThroughBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.PostInfo;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.busi.bpm.form.model.FormInfo;
import com.augurit.agmobile.busi.bpm.form.util.ElementBuilder;
import com.augurit.agmobile.busi.bpm.form.util.FormBuilder;
import com.augurit.agmobile.busi.bpm.form.util.WidgetBuilder;
import com.augurit.agmobile.busi.bpm.form.view.ElementPosition;
import com.augurit.agmobile.busi.bpm.form.view.FormState;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.common.common.form.AwFormConfig;
import com.augurit.common.common.form.AwFormFragment;
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
import static com.augurit.agmobile.busi.bpm.widget.WidgetType.EDITTEXT;

public class ApproveNotThroughFragment extends AwFormFragment {
    EventRepository mEventRepository;
    private boolean isFirst = true;
    private EventListItem.DataBean mEventListItem;
    private EventBean mData;
    ApproveNotThroughBean approveNotThroughBean;
    private boolean isDelete;
    private FillBlankView mFillBlankView;
    private FillBlankView mFillBlankView1;


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
        mFormCode = AwFormConfig.FORM_ACCEPT_RECEIPT;
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

    public void generateInfo(){
        //??????
        mFillBlankView1 = new FillBlankView(getActivity(), 1);
        mFillBlankView1.setVisible(View.GONE);
        String reject = "     ????????????????????????????????????????????????????????????????????????        (????????????)           ??????????????????????????????        ??????????????????           ???????????????????????????????????????????????????????????????????????????????????????????????????";
        List<AnswerRange> rejectList = new ArrayList<>();
        rejectList.add(new AnswerRange(29, 54));
        rejectList.add(new AnswerRange(64, 89));
        mFillBlankView1.setData(reject, rejectList);
        mFormPresenter.addViews("linkmanPhone", ElementPosition.BELOW, mFillBlankView1);
        if(approveNotThroughBean.getReconsiderRegionName()!=null){
            mFillBlankView1.fillAnswer(approveNotThroughBean.getReconsiderRegionName(),0);//????????????
        }
        if(approveNotThroughBean.getSuperiorOrgName()!= null){
            mFillBlankView1.fillAnswer(approveNotThroughBean.getSuperiorOrgName(),1);// ??????????????????
        }

        mFillBlankView = new FillBlankView(getActivity(), 0);
        String content = "     ????????????            ???            ???            ???????????????                              ?????????         ?????????????????????????????????          ??????????????????????????????????????????????????????";
        // ??????????????????
        List<AnswerRange> rangeList = new ArrayList<>();

        // ??????????????????
        rangeList.add(new AnswerRange(9, 21));
        rangeList.add(new AnswerRange(22, 34));
        rangeList.add(new AnswerRange(35, 47));
        rangeList.add(new AnswerRange(52, 82));
        rangeList.add(new AnswerRange(85, 115));
        mFillBlankView.setData(content, rangeList);
        mFormPresenter.addViews("linkmanPhone", ElementPosition.BELOW, mFillBlankView);
        //2???????????????????????????
        Calendar calendar = Calendar.getInstance();
        // ?????????
        mFillBlankView.fillAnswer(String.valueOf(calendar.get(Calendar.YEAR)),0);//???
        mFillBlankView.fillAnswer(String.valueOf(calendar.get(Calendar.MONTH)+1),1);//???
        mFillBlankView.fillAnswer(String.valueOf(calendar.get(Calendar.DATE)),2);//???
        if(approveNotThroughBean.getApplyBusinessName()!=null){
            mFillBlankView.fillAnswer(approveNotThroughBean.getApplyBusinessName(),3);//
        }
        if(approveNotThroughBean.getLegislativeAuthority()!= null){
            mFillBlankView.fillAnswer(approveNotThroughBean.getLegislativeAuthority(),4);//
        }
    }

    @Override
    protected void submit() {
        HashMap<String, String> widgetValues = mFormPresenter.getWidgetValues();
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
        widgetValues.put("applyinstCode",approveNotThroughBean.getApplyinstCode());
        widgetValues.put("applyinstId",approveNotThroughBean.getApplyinstId());
        widgetValues.put("iteminstId",approveNotThroughBean.getIteminstId());
        widgetValues.put("receiptInstrumentName",approveNotThroughBean.getReceiptInstrumentName());

        String toJson = new Gson().toJson(widgetValues);
//        String toJson = new Gson().toJson(approveThroughBean);
        HashMap<String, String> params = new HashMap<>();
        params.put("aeaHiUnapproveReceiptJson ", toJson);
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("????????????????????????");
        progressDialog.show();
        mEventRepository.postNotApproveThroughInfo(params)
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
    protected void delete(){
        String receiptId=approveNotThroughBean.getUnapproveReceiptId();
        HashMap<String, String> params = new HashMap<>();
        params.put("id",receiptId);
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setMessage("???????????????");
        //??????????????????
        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mEventRepository.deleteNotApprove(params)
                        .subscribe(new Consumer<ApiResult>() {
                            @Override
                            public void accept(ApiResult apiResult) throws Exception {
                                if (apiResult.isSuccess()) {
                                    ToastUtil.longToast(getContext(),"????????????");
                                    EventBus.getDefault().post(new PostInfo(true));
                                    getActivity().finish();
                                }else{
                                    ToastUtil.longToast(getContext(),"????????????");
                                }
                            }
                        },throwable -> {
                            throwable.printStackTrace();
                            ToastUtil.longToast(getContext(),"????????????");
                        });
            }
        });
        //??????????????????
        builder.setPositiveButton("??????",null);
        //???????????????
        builder.show();

    }

    private void getEventData() {
        if (isFirst) {
            ProgressDialogUtil.showProgressDialog(getActivity(), false);
            mEventRepository.getNotApproveInfo(mEventListItem == null ? "" : mEventListItem.getApplyinstId(), mEventListItem == null ? "" : mEventListItem.getIteminstId())
                    .subscribe(listApiResult -> {
                        ProgressDialogUtil.dismissProgressDialog();
                        if (listApiResult.isSuccess()) {
                            isFirst = false;
                            approveNotThroughBean = listApiResult.getData();
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
        FormBuilder fb =  new FormBuilder("")
                .addElement(new ElementBuilder("instrumentNo")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("applyinstCode")
//                .addElement(new ElementBuilder("unapproveReceiptId")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("???????????????")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("applyUnitName")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("??????????????????")
                                .isEnable(true)
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
                                .isEnable(true)
                                .build())
                        .build())
                .addElement(new ElementBuilder("projCode")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("??????????????????")
                                .isEnable(true)
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
                .addElement(new ElementBuilder("legalRepresentativeName")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("?????????????????????")
                                .isEnable(true)
                                .build())
                        .build())
//                .addElement(new ElementBuilder("reconsiderRegionName")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("????????????")
//                                .isEnable(true)
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("superiorOrgName")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("??????????????????")
//                                .defaultValue("")
//                                .build())
//                        .build())

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
            /*    .addElement(new ElementBuilder("receiveResultsTimeRange")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("??????????????????(????????????)")
                                .isEnable(false)
                                .build())
                        .build())*/
//                .addElement(new ElementBuilder("applyTime4String")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("????????????")
//                                .hint("?????????????????????-???-???")
//                                .build())
//                        .build())
//                .addElement(new ElementBuilder("applyBusinessName")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("????????????/????????????")
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
//                        .build());
        return fb.build();
    }
}
