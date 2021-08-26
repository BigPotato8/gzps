package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.view.EditAndTextView;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.AcceptReceiptBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.AnswerRange;
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
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.augurit.common.common.form.AwFormConfig;
import com.augurit.common.common.form.AwFormFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

import static com.augurit.agmobile.agwater5.gcjs.eventlist.view.MaterialCorrectionActivity.POST_SUCCESS;
import static com.augurit.agmobile.busi.bpm.widget.WidgetType.EDITFIELD;
import static com.augurit.agmobile.busi.bpm.widget.WidgetType.EDITTEXT;

public class AcceptReceiptFragment extends AwFormFragment implements WidgetListener {
    EventRepository mEventRepository;
    private boolean isFirst = true;
    private EventListItem.DataBean mEventListItem;
    private EventBean mData;
    AcceptReceiptBean acceptReceiptBean;
    private String isDelete;
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
        btn_submit.setText("保存");
        mFillBlankView = new FillBlankView(getActivity(),0);
        mFillBlankView1 = new FillBlankView(getActivity(),1);
        String spaceStr="        ";//空格表示填空的横线
        String str1="     你单位于";//此处空格表示首行空两格
        String str2=str1+spaceStr+"年";
        String str3=str2+spaceStr+"月";
        String str4=str3+spaceStr+"日申请办理";
        String str5=str4+spaceStr+"，根据";
        String content=str5+" （相关法律条款或理由） ，符合受理条件，予以受理。";
//        String content = "     你单位于            年            月            日申请办理                              ，根据         （相关法律条款或理由）          ，符合受理条件，予以受理。";

        // 横线范围集合
        List<AnswerRange> rangeList = new ArrayList<>();

        // 横线范围集合
//        rangeList.add(new AnswerRange(9, 21));
//        rangeList.add(new AnswerRange(22, 34));
//        rangeList.add(new AnswerRange(35, 47));
//        rangeList.add(new AnswerRange(52, 82));
//        rangeList.add(new AnswerRange(85, 115));
        int spaceLen=spaceStr.length();
        rangeList.add(new AnswerRange(str1.length(), str1.length()+spaceLen));
        rangeList.add(new AnswerRange(str2.length(), str2.length()+spaceLen));
        rangeList.add(new AnswerRange(str3.length(), str3.length()+spaceLen));
        rangeList.add(new AnswerRange(str4.length(), str4.length()+spaceLen));
        rangeList.add(new AnswerRange(str5.length(), content.length()-13));//特殊情况
        mFillBlankView.setData(content, rangeList);
        mFormPresenter.addViews("materialList", ElementPosition.ABOVE, mFillBlankView);
        mFormPresenter.getWidget("materialList").getView().findViewById(R.id.view_widget_divider_top).setVisibility(View.GONE);
//        领取材料时限
        String content1 = "     请于申请之日起          (不含特殊程序审查期限)后领取结果，具体请登录广东政务服务网(佛山)查询办理状态";
        List<AnswerRange> rangeList1 = new ArrayList<>();
        rangeList1.add(new AnswerRange(12, 22));
        mFillBlankView1.setData(content1, rangeList1);
        mFillBlankView1.setVisible(View.GONE);
        mFormPresenter.addViews("materialList", ElementPosition.BELOW, mFillBlankView1);
        //2、日历类的时间操作
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH)+1);
        System.out.println(calendar.get(Calendar.DATE));
        // 设置值
        mFillBlankView.fillAnswer(String.valueOf(calendar.get(Calendar.YEAR)),0);//年
        mFillBlankView.fillAnswer(String.valueOf(calendar.get(Calendar.MONTH)+1),1);//月
        mFillBlankView.fillAnswer(String.valueOf(calendar.get(Calendar.DATE)),2);//日
        if(acceptReceiptBean.getApplyBusinessName()!=null){
            mFillBlankView.fillAnswer(acceptReceiptBean.getApplyBusinessName(),3);//
        }
        if(acceptReceiptBean.getLegislativeAuthority()!= null){
            mFillBlankView.fillAnswer(acceptReceiptBean.getLegislativeAuthority(),4);//
        }
        if(acceptReceiptBean.getReceiveResultsTimeRange() != null){
            if(acceptReceiptBean.getReceiveResultsTimeRange().contains("个工作日")){
                mFillBlankView1.fillAnswer(acceptReceiptBean.getReceiveResultsTimeRange(),0);
            }
        }

    }

    @Override
    protected void submit() {
//        if (!mFormPresenter.validate()) {
//            return;
//        }
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
        HashMap<String, String> widgetValues = mFormPresenter.getWidgetValues();
//        widgetValues.put("appTime",System.currentTimeMillis()+"");
        String mats= (String) mFormPresenter.getWidget("materialList").getValue();

        widgetValues.put("applyTime4String",time+"");
        widgetValues.put("receiveResultsTimeRange",receiveResultsTimeRange);
        widgetValues.put("applyBusinessName",applyBusinessName);
        widgetValues.put("legislativeAuthority",legislativeAuthority);
        widgetValues.put("applyinstCode", acceptReceiptBean.getApplyinstCode());
        widgetValues.put("iteminstId", acceptReceiptBean.getIteminstId());
        widgetValues.put("receiptInstrumentName", acceptReceiptBean.getReceiptInstrumentName());
        widgetValues.put("applyinstId",acceptReceiptBean.getApplyinstId());
        widgetValues.put("materialList",mats.replace("\n",","));

        String toJson = new Gson().toJson(widgetValues);

        HashMap<String, String> params = new HashMap<>();
        params.put("aeaHiReceivingReceiptJson", toJson);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("正在发送，请等待");
        progressDialog.show();

        mEventRepository.postAcceptReceipt(params)
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
        String receiptId = acceptReceiptBean.getReceiveReceiptId();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", receiptId);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("确定删除？");
        //设置确定按钮
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mEventRepository.deleteAcceptReceipt(params)
                        .subscribe(new Consumer<ApiResult>() {
                            @Override
                            public void accept(ApiResult apiResult) throws Exception {
                                if (apiResult.isSuccess()) {
                                    ToastUtil.longToast(getContext(), "删除成功");
                                    EventBus.getDefault().post(new PostInfo((apiResult.isSuccess())));
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
            mEventRepository.getReceiptInfo(mEventListItem == null ? "" : mEventListItem.getApplyinstId(), mEventListItem == null ? "" : mEventListItem.getTaskId())
                    .subscribe(listApiResult -> {
                        ProgressDialogUtil.dismissProgressDialog();
                        if (listApiResult.isSuccess()) {
                            isFirst = false;
                            acceptReceiptBean = listApiResult.getData();
                            if (acceptReceiptBean != null) {//全局发送实体类信息
                                EventBus.getDefault().post(acceptReceiptBean);
                                String materialList = acceptReceiptBean.getMaterialList();
                                String materialList1=materialList.replace(",","\n");
                                acceptReceiptBean.setMaterialList(materialList1);
                            }
                            Gson gson = new Gson();
                            Map<String, String> map = new HashMap<>();
                            if (!ListUtil.isEmpty(acceptReceiptBean)) {
                                AcceptReceiptBean contentInfo = acceptReceiptBean;
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
                                .title("审批流水号")
                                .isEnable(false)
                                .build())
                        .build())
                .addElement(new ElementBuilder("applyUnitName")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("申请单位名称")
                                .isEnable(true)
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
                                .isEnable(true)
                                .build())
                        .build())
                .addElement(new ElementBuilder("projCode")
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("统一项目代码")
                                .isEnable(true)
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
                .addElement(new ElementBuilder("materialList")
                .widget(new WidgetBuilder(EDITFIELD)
                        .title("材料清单")
                        .maxLimit(800)
                        .isEnable(true)
                        .build())
                .build());
//                .addElement(new ElementBuilder("receiveResultsTimeRange")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("领取结果时限(工作日内)")
//                                .isEnable(true)
//                                .build())
//                        .build());
//                .addElement(new ElementBuilder("applyTime4String")
//                        .widget(new WidgetBuilder(EDITTEXT)
//                                .title("申请日期")
//                                .hint("填写格式为：年-月-日")
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

        return fb.build();
    }

    @Override
    public void onValueChange(BaseFormWidget widget, Object value, @Nullable Object item, boolean isEffective) {

    }
}
