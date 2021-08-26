package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.BaseWebViewActivity;
import com.augurit.agmobile.agwater5.gcjs.eventlist.EventListActivity;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.AnswerRange;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.CorrectReceiptBean;
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
import com.augurit.agmobile.busi.bpm.widget.WidgetListener;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.ui.progressdialog.ProgressDialogUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.common.common.form.AwFormConfig;
import com.augurit.common.common.form.AwFormFragment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

import static com.augurit.agmobile.agwater5.gcjs.eventlist.view.MaterialCorrectionActivity.POST_SUCCESS;
import static com.augurit.agmobile.busi.bpm.widget.WidgetType.EDITFIELD;
import static com.augurit.agmobile.busi.bpm.widget.WidgetType.EDITTEXT;

public class CorrectionReceiptFragment extends AwFormFragment implements WidgetListener {
    EventRepository mEventRepository;
    private boolean isFirst = true;
    private EventListItem.DataBean mEventListItem;
    private EventBean mData;
    CorrectReceiptBean correctReceiptBean;
    private String isDelete;
    private String matCorrectDtosJson;
    private String correctDueDays;
    private String isMulti;
    private String correctId;
    private String correctinstDtoJson;
    private FillBlankView mFillBlankView;
    private FillBlankView mFillBlankView1;
    List<CorrectReceiptBean.MatOpinionVosBean> matOpinionVos;

    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }

    private HashMap<String,String>map;

    public void setEventListItem(String correctinstDtoJson,EventListItem.DataBean mEventListItem) {
        this.correctinstDtoJson = correctinstDtoJson;
        this.mEventListItem=mEventListItem;
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
        mFormCode = AwFormConfig.FORM_CORRECTION_RECEIPT;
        mEventRepository = new EventRepository();

        getEventData();
    }

    @Override
    protected void onFormLoaded() {
        super.onFormLoaded();
        btn_delete.setVisibility(View.GONE);
        btn_save.setVisibility(View.GONE);
        btn_submit.setText("保存");
        mFillBlankView = new FillBlankView(getActivity(),0);
        mFillBlankView1 = new FillBlankView(getActivity(),1);
        String content = "     你单位于            年            月            日申请办理                              ，根据         （相关法律条款或理由）          ，需补正的申请材料如下：";
        // 答案范围集合
        List<AnswerRange> rangeList = new ArrayList<>();

        // 答案范围集合
        rangeList.add(new AnswerRange(9, 21));
        rangeList.add(new AnswerRange(22, 34));
        rangeList.add(new AnswerRange(35, 47));
        rangeList.add(new AnswerRange(52, 82));
        rangeList.add(new AnswerRange(85, 115));
        mFillBlankView.setData(content, rangeList);
        mFormPresenter.addViews("matOpinionVos", ElementPosition.ABOVE, mFillBlankView);
        mFormPresenter.getWidget("matOpinionVos").getView().findViewById(R.id.view_widget_divider_top).setVisibility(View.GONE);
//        领取材料时限
        String content1 = "     请收到本告知书之日起          内提供补正材料，逾期未补正的，作退件办理";
        List<AnswerRange> rangeList1 = new ArrayList<>();
        rangeList1.add(new AnswerRange(15, 25));
        mFillBlankView1.setData(content1, rangeList1);
        mFillBlankView1.setVisible(View.GONE);
        mFormPresenter.addViews("opinion", ElementPosition.BELOW, mFillBlankView1);
        //2、日历类的时间操作
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH)+1);
        System.out.println(calendar.get(Calendar.DATE));
        // 设置值
        mFillBlankView.fillAnswer(String.valueOf(calendar.get(Calendar.YEAR)),0);//年
        mFillBlankView.fillAnswer(String.valueOf(calendar.get(Calendar.MONTH)+1),1);//月
        mFillBlankView.fillAnswer(String.valueOf(calendar.get(Calendar.DATE)),2);//日
        if(correctReceiptBean.getApplyBusinessName()!=null){
            mFillBlankView.fillAnswer(correctReceiptBean.getApplyBusinessName(),3);//
        }
        if(correctReceiptBean.getLegislativeAuthority()!= null){
            mFillBlankView.fillAnswer(correctReceiptBean.getLegislativeAuthority(),4);//
        }
        if(correctReceiptBean.getCorrectTimeRange() != null){
            if(correctReceiptBean.getCorrectTimeRange().contains("个工作日")){
                mFillBlankView1.fillAnswer(correctReceiptBean.getCorrectTimeRange(),0);
            }
        }
    }

    @Override
    protected void submit() {
//        if (!mFormPresenter.validate()) {
//            return;
//        }
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
        String receiveResultsTimeRange = mFillBlankView1.getAnswerList().get(0);

//        widgetValues.put("appTime",System.currentTimeMillis()+"");
        widgetValues.put("applyTime",time+"");
        widgetValues.put("correctTimeRange",receiveResultsTimeRange);
        widgetValues.put("applyBusinessName",applyBusinessName);
        widgetValues.put("legislativeAuthority",legislativeAuthority);

        Gson gson=new Gson();
        String matOpinionVo=gson.toJson(matOpinionVos);
        widgetValues.put("matOpinionVos","");

        String toJson = new Gson().toJson(widgetValues);
        HashMap<String, String> params = new HashMap<>();
        params.put("aeaHiCorrectReceiptJson", toJson);
        params.put("matOpinionVos",matOpinionVo.replace("\\",""));


        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("正在发送，请等待");
        progressDialog.show();

        mEventRepository.postCorrectReceipt(params)
                .subscribe(new Consumer<ApiResult>() {
                    @Override
                    public void accept(ApiResult apiResult) throws Exception {
                        progressDialog.dismiss();
                        if (apiResult.isSuccess()) {
                            ToastUtil.longToast(getContext(), "保存成功");
                            getActivity().setResult(POST_SUCCESS);
                            getActivity().finish();
//                            Intent intent = new Intent(getContext(), EventListActivity.class);
//                            startActivity(intent);

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

    @SuppressLint("CheckResult")
    private void getEventData() {
        if (isFirst) {
            isMulti="0";
            ProgressDialogUtil.showProgressDialog(getActivity(), false);
//            String json = "{\"applyinstId\":\"5aa1de36-4bce-464f-b062-4f8d9eac780e\",\"iteminstId\":\"a5a140f9-0bab-42e0-bb58-875322f3d2eb\",\n" +
//                    "\"correctDueDays\":\"20\",\"matCorrectDtosJson\":\"[{\\\"matId\\\":\\\"2e62cc57-bfa0-43d0-baf2-50efec112070\\\",\\\"matName\\\":\\\"取水许可申请书\\\",\\\"matCode\\\":\\\"MAT-C0000000100\\\",\\\"matTypeId\\\":null,\\\"reviewKeyPoints\\\":\\\"加盖经认证的电子公章，涉及取水权人名称和法定代表人等取水许可事项变更的，须在取水许可申请书“取水理由及依据”栏中提出变更理由。\\\",\\\"reviewSampleDoc\\\":null,\\\"sampleDoc\\\":null,\\\"templateDoc\\\":null,\\\"matinstName\\\":\\\"取水许可申请书\\\",\\\"attMatinstId\\\":\\\"4bb225cf-f767-4f79-ba6f-4c91ff27ecc5\\\",\\\"attTempMatinstId\\\":null,\\\"paperMatinstId\\\":null,\\\"paperTempMatinstId\\\":null,\\\"copyMatinstId\\\":null,\\\"copyTempMatinstId\\\":null,\\\"paperCount\\\":0,\\\"copyCount\\\":0,\\\"isNeedAtt\\\":\\\"1\\\",\\\"attIsCollected\\\":\\\"1\\\",\\\"paperIsCollected\\\":null,\\\"copyIsCollected\\\":null,\\\"attCount\\\":1,\\\"realPaperCount\\\":null,\\\"realCopyCount\\\":null,\\\"attInoutinstId\\\":null,\\\"paperInoutinstId\\\":null,\\\"copyInoutinstId\\\":null,\\\"attDueIninstId\\\":null,\\\"copyDueIninstId\\\":null,\\\"paperDueIninstId\\\":null,\\\"attDueIninstOpinion\\\":\\\"123\\\",\\\"copyDueIninstOpinion\\\":null,\\\"paperDueIninstOpinion\\\":null,\\\"attRealIninstId\\\":null,\\\"copyRealIninstId\\\":null,\\\"paperRealIninstId\\\":null,\\\"attIsPass\\\":null,\\\"copyIsPass\\\":null,\\\"paperIsPass\\\":null,\\\"attFiles\\\":[],\\\"matProp\\\":null,\\\"certId\\\":null,\\\"ybKbDetailIds\\\":null,\\\"attIsRequire\\\":\\\"1\\\",\\\"outerFormId\\\":null,\\\"stoForminstId\\\":null,\\\"correctNo\\\":null,\\\"correctIsRequire\\\":null,\\\"outcomeId\\\":null,\\\"outcomeIsPass\\\":null,\\\"rowType\\\":3,\\\"rowTypeText\\\":\\\"电子件\\\",\\\"tmpMatId\\\":\\\"2e62cc57-bfa0-43d0-baf2-50efec112070_att\\\",\\\"rowspan\\\":1,\\\"colspan\\\":1,\\\"uIndex\\\":2,\\\"opinionContent\\\":\\\"123\\\"}]\",\"correctId\":\"e800cb6c79804fd0809a48f430a1b5fe\"}";
            org.json.JSONObject object = null;
            String finalStr = null;
//            JSONObject finalJsonObject = new JSONObject();
            try {
                object = new org.json.JSONObject(correctinstDtoJson.substring(correctinstDtoJson.indexOf("[")+1,correctinstDtoJson.lastIndexOf("]")));
                finalStr = (String) object.get("matCorrectDtos");
//                finalJsonObject.put("matCorrectDtosJson",finalStr);
//                finalJsonObject.put("applyinstId",mEventListItem.getApplyinstId());
//                finalJsonObject.put("iteminstId",mEventListItem.getIteminstId());
//                finalJsonObject.put("correctDueDays",map.get("correctDueDays"));
//                finalJsonObject.put("correctId",object.get("correctId"));
//                finalJsonObject.put("isMulti",isMulti);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                mEventRepository.getCorrectionReceiptInfo(finalStr,mEventListItem.getApplyinstId(),mEventListItem.getIteminstId(),map.get("correctDueDays"),object.get("correctId"),isMulti)
    //            mEventRepository.getCorrectionReceiptInfo(json)
                        .subscribe(listApiResult -> {
                            ProgressDialogUtil.dismissProgressDialog();
                            if (listApiResult.isSuccess()) {
                                isFirst = false;
                                correctReceiptBean = listApiResult.getData();
    //                            if (correctReceiptBean != null) {//全局发送实体类信息
    //                                EventBus.getDefault().post(correctReceiptBean);
    //                            }
                                Gson gson = new Gson();
                                Map<String, String> map = new HashMap<>();
                                matOpinionVos=correctReceiptBean.getMatOpinionVos();
                                if (!ListUtil.isEmpty(correctReceiptBean)) {
                                    correctReceiptBean.setMatOpinionVos(null);
                                    CorrectReceiptBean contentInfo = correctReceiptBean;
                                    String projInfoJson = gson.toJson(contentInfo);
                                    map.putAll(gson.fromJson(projInfoJson, new TypeToken<Map<String, String>>() {
                                    }.getType()));
                                }
                                StringBuilder builder = new StringBuilder();
                                StringBuilder builder1 = new StringBuilder();
                                for (int i=0;i<matOpinionVos.size();i++){
                                    if(i == matOpinionVos.size() - 1){
                                        builder.append(matOpinionVos.get(i).getMatName());
                                        builder1.append(matOpinionVos.get(i).getOpinion());
                                    }else{
                                        builder.append(matOpinionVos.get(i).getMatName()+"\n");
                                        builder1.append(matOpinionVos.get(i).getOpinion()+"\n");
                                    }
                                    map.put("matOpinionVos",builder+"");
                                    map.put("opinion",builder1+"");
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
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
                .addElement(new ElementBuilder("applyinstId")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("参数")
                                .isEnable(false)
                                .isVisible(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("iteminstId")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("参数")
                                .isEnable(false)
                                .isVisible(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("correctId")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("参数")
                                .isEnable(false)
                                .isVisible(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("isMulti")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("参数")
                                .isEnable(false)
                                .isVisible(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("receiptInstrumentName")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("参数")
                                .isEnable(false)
                                .isVisible(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("receiptInstrumentId")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("参数")
                                .isEnable(false)
                                .isVisible(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("detailId")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("参数")
                                .isEnable(false)
                                .isVisible(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("isMulti")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("参数")
                                .isEnable(false)
                                .isVisible(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("signTime")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("参数")
                                .isEnable(false)
                                .isVisible(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("instrumentNo")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("文书编号")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("applyinstCode")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("审批流水号")
                                .isEnable(true)
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
                                .isEnable(false)
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
                        .build())
                .addElement(new ElementBuilder("matOpinionVos")
                        .widget(new WidgetBuilder(EDITFIELD)
                                .title("材料清单")
                                .isEnable(true)
                                .maxLimit(800)
                                .build())
                        .build())
                .addElement(new ElementBuilder("opinion")
                        .widget(new WidgetBuilder(EDITFIELD)
                                .title("补正意见")//暂不显示
                                .isEnable(true)
                                .isVisible(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("consultName")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("咨询联系人")
                                .build())
                        .build())
                .addElement(new ElementBuilder("consultPhone")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("咨询电话")
                                .build())
                        .build());

        return fb.build();
    }

    @Override
    public void onValueChange(BaseFormWidget widget, Object value, @Nullable Object item, boolean isEffective) {

    }

}