package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ClbzDetailBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.CorrectReceiptBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventClbzItemBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.MaterialCorrectInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter.EventClbzAdapter;
import com.augurit.agmobile.busi.bpm.form.view.ElementPosition;
import com.augurit.agmobile.busi.bpm.form.view.FormState;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.common.common.form.AwFormActivity;
import com.augurit.common.common.form.AwFormConfig;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * 办理流程---材料补正界面
 */
public class MaterialCorrectionActivity extends AwFormActivity {
    public final static int POST_SUCCESS = 1001;
    RecyclerView rv_clbz;
    private EventClbzAdapter clbzAdapter;
    EventRepository eventRepository;
    List<EventClbzItemBean.SubmittedMatsBean> mList;
    private boolean isToReceipt;
    private EventListItem.DataBean mEventListItem;
    private String correctId;
    private String correctMemo;

    @Override
    protected void initArguments() {
        super.initArguments();
        Intent intent = getIntent();
        mTitleText = "材料补正";
        mFormCode = AwFormConfig.FORM_GCJS_CLBZ;
        mFormState = FormState.STATE_EDIT;
        eventRepository = new EventRepository();
        if (intent!=null){
            mEventListItem = (EventListItem.DataBean) intent.getSerializableExtra("mEventListItem");
//            correctId=intent.getStringExtra("correctId");
        }

        getMaterials();//联网获取补正材料
    }

    @Override
    protected void onFormLoaded() {
        super.onFormLoaded();
        //修改提交按钮
        btn_save.setVisibility(View.GONE);
        btn_submit.setText("补正开始");
        View clbzView = LayoutInflater.from(this).inflate(R.layout.gcjs_clbz_view, null);
        rv_clbz = clbzView.findViewById(R.id.rv_clbz);
        rv_clbz.setHasFixedSize(true);
        rv_clbz.setNestedScrollingEnabled(false);
        rv_clbz.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        clbzAdapter = new EventClbzAdapter(this, mList);
        rv_clbz.setAdapter(clbzAdapter);
        mFormPresenter.addViews("bz", ElementPosition.BELOW, clbzView);//材料列表

    }

    @SuppressLint("CheckResult")
    @Override
    protected void submit() {
        if (mFormPresenter.validate()) {
            List<EventClbzItemBean.SubmittedMatsBean> selectList = clbzAdapter.getSelectList();
            for (EventClbzItemBean.SubmittedMatsBean bean : selectList) {
                if(bean.isSelected()){
                    if("".equals(bean.getAttDueIninstOpinion())||bean.getAttDueIninstOpinion()==null){
                        ToastUtil.longToast(this, bean.getMatName()+"的补正意见不能为空");
                        return;
                    }
                }
            }
            if (selectList.size() == 0) {
                ToastUtil.longToast(this, "待补正列表不能为空");
                return;
            }
            String applyinstId = getIntent().getStringExtra("applyinstId");
            String iteminstId = getIntent().getStringExtra("iteminstId");
            String taskInstId = getIntent().getStringExtra("taskInstId");
            String projInfoId = getIntent().getStringExtra("projInfoId");
            HashMap<String, String> values = mFormPresenter.getWidgetValues();
            HashMap<String, String> params = new HashMap<>();
            params.put("applyinstId",applyinstId);//申请实例ID
            params.put("iteminstId",iteminstId);//事项实例ID
            params.put("taskinstId",taskInstId);//节点实例ID
            params.put("projInfoId",projInfoId);//项目ID
            params.put("isFlowTrigger","1");//是否在流程办理的过程中发起补正,1是0否
            String matCorrectDtosJson = new Gson().toJson(selectList);
            params.put("matCorrectDtosJson",matCorrectDtosJson);//补正材料json
            params.put("correctDueDays",values.get("correctDueDays"));
            params.put("correctMemo",values.get("correctMemo"));

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在发送，请等待");
            progressDialog.show();

            eventRepository.postMaterialCorrection(params)
                    .subscribe(new Consumer<ApiResult>() {
                        @Override
                        public void accept(ApiResult apiResult) throws Exception {
                            progressDialog.dismiss();
                            if (apiResult.isSuccess()) {
                                try {
                                    Gson gson = new Gson();
                                    String correctinstDtoJson=gson.toJson(apiResult.getData());
                                    ToastUtil.shortToast(MaterialCorrectionActivity.this,"发起成功");
                                    setResult(POST_SUCCESS);
                                    isToReceipt=true;
                                    Intent intent = new Intent(MaterialCorrectionActivity.this, ReceiptListActivity.class);
                                    intent.putExtra("isToReceipt", isToReceipt);
                                    intent.putExtra("map",mFormPresenter.getWidgetValues());
                                    intent.putExtra("correctinstDtoJson",correctinstDtoJson);
                                    intent.putExtra("mEventListItem",mEventListItem);
                                    startActivity(intent);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                finish();
                            }else{
                                ToastUtil.longToast(MaterialCorrectionActivity.this,"材料补正发起失败:"+apiResult.getMessage());
                            }
                        }
                    },throwable -> {
                        throwable.printStackTrace();
                        progressDialog.dismiss();
                        ToastUtil.longToast(this,"材料补正发起失败");
                    });
        }
    }

    private void getMaterials() {
        String applyinstId = getIntent().getStringExtra("applyinstId");
        String iteminstId = getIntent().getStringExtra("iteminstId");

        eventRepository.getMaterials(applyinstId, iteminstId)
                .subscribe(new Consumer<ApiResult<EventClbzItemBean>>() {
                    @Override
                    public void accept(ApiResult<EventClbzItemBean> apiResult) throws Exception {
                        EventClbzItemBean data = apiResult.getData();
                        if (data==null) {
                            ToastUtil.longToast(MaterialCorrectionActivity.this,apiResult.getMessage());
//                            finish();
                            return;
                        }
                        List<EventClbzItemBean.SubmittedMatsBean> matCorrectDtos = data.getMatCorrectDtos();//未交材料
                        List<EventClbzItemBean.SubmittedMatsBean> submittedMats = data.getSubmittedMats();//已交材料
                        mList = new ArrayList<>();
                        mList.addAll(matCorrectDtos);
                        mList.addAll(submittedMats);
                        data.setMatCorrectDtos(null);
                        data.setSubmittedMats(null);
                        Gson gson = new Gson();
                        String toJson = gson.toJson(data);
                        Map<String, String> fromJson = gson.fromJson(toJson, new TypeToken<Map<String, String>>() {
                        }.getType());
                        mRecord = new FormRecord();
                        mRecord.setFormCode(AwFormConfig.FORM_GCJS_CLBZ);
                        mRecord.setValues(fromJson);
                        loadForm();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    ToastUtil.longToast(this,"获取补正材料列表失败");
                });
    }

    @Override
    protected boolean shouldLoadImmediately() {
        return false;
    }
}
