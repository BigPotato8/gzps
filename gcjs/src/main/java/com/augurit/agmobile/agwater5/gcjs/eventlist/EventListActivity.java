package com.augurit.agmobile.agwater5.gcjs.eventlist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Toast;

import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventState;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ListFilterBean;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.dict.util.DictBuilder;
import com.augurit.agmobile.busi.bpm.form.model.Element;
import com.augurit.agmobile.busi.bpm.form.util.ElementBuilder;
import com.augurit.agmobile.busi.bpm.form.util.FormBuilder;
import com.augurit.agmobile.busi.bpm.form.util.WidgetBuilder;
import com.augurit.agmobile.busi.bpm.view.model.ViewInfo;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListActivity;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.busi.bpm.viewlist.view.ViewListView;
import com.augurit.agmobile.busi.bpm.widget.view.BaseFormWidget;
import com.augurit.agmobile.common.lib.common.Function;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.augurit.agmobile.common.lib.validate.StringUtil;
import com.augurit.agmobile.common.view.combineview.AGMultiCheck;
import com.augurit.agmobile.common.view.combineview.AGMultiSpinner;
import com.augurit.agmobile.common.view.combineview.IDictItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.augurit.agmobile.busi.bpm.widget.WidgetType.CHECKBOX_EXPAND;
import static com.augurit.agmobile.busi.bpm.widget.WidgetType.EDITTEXT;

/**
 * com.augurit.agmobile.agwater5.gcjs.eventlist
 * Created by sdb on 2019/4/2  15:50.
 * Desc?????????????????????
 */

public class EventListActivity extends BaseViewListActivity {
    public static final String EXTRA_EVENT_STATE = "EXTRA_EVENT_STATE";

    protected int mEventState = EventState.HANDLING;
    //    private EventAffairFragment mEventAffairFragment;
    private EventListFragment mEventListFragment;

    @Override
    protected void initArguments() {
        super.initArguments();
        mEventState = getIntent().getIntExtra(EXTRA_EVENT_STATE, EventState.HANDLING);

    }

    @Override
    protected void initView() {
        super.initView();

        try{
            initFilterData();
        }catch (Exception e){
            e.printStackTrace();
        }
        mView.setResetListener(new ViewListView.OnResetListener() {//??????????????????
            @Override
            public void onReset(LinkedHashMap<String, BaseFormWidget> widgetsMap) {
                for (BaseFormWidget widget : widgetsMap.values()) {
                    String elementCode = widget.getElement().getElementCode();
                    switch (elementCode){
                        case "mkeyword":
                            widget.setValue("");
                            break;
                        case "instTimeState":
                            AGMultiCheck mAGMultiCheck = (AGMultiCheck) mView.getFilterWidget(getPageTitles().get(0), "instTimeState").getView();
                            if(mEventState == 1){
                                mAGMultiCheck.setSelection(0);
                            }else if(mEventState == 6){
                                mAGMultiCheck.setSelection(3);
                                mAGMultiCheck.setEnable(false);
                            }else if(mEventState == 8){
                                mAGMultiCheck.setSelection(2);
                                mAGMultiCheck.setEnable(false);
                            }
                            break;
                        case "taskName":
                            AGMultiCheck taskName = (AGMultiCheck) mView.getFilterWidget(getPageTitles().get(0), "taskName").getView();
                            taskName.setSelection(0);
                            break;
                    }

                }
            }
        });
    }


    @Override
    protected Map<String, ViewInfo> getViewInfos() {
        HashMap<String, ViewInfo> viewInfoHashMap = new HashMap<>();
        ArrayList<String> pageTitles = getPageTitles();
        viewInfoHashMap.put(pageTitles.get(0), getFilterView1());
        return viewInfoHashMap;
    }


    @Override
    protected ArrayList<? extends BaseViewListFragment> getFragments() {
        ArrayList<BaseViewListFragment> fragments = new ArrayList<>();
//        if (mEventState == EventState.UPLOADED) {
//            //
//            mEventAffairFragment = EventAffairFragment.newInstance(EventAffairFragment.MODE_MY_UPLOAD);
//            fragments.add(mEventAffairFragment);
//        } else {

        // ???????????????????????????????????????
        mEventListFragment = EventListFragment.newInstance(mEventState);
        fragments.add(mEventListFragment);

//        }
        return fragments;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EventState.UPLOADED:
//                    mEventAffairFragment.onActivityResult(requestCode, resultCode, data);
//                    break;
                case EventState.HANDLED:
                case EventState.FINISHED:
                case EventState.HANDLING:
                case EventState.DEPARTMENT_ALL:
                    mEventListFragment.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }
    }

    public ViewInfo getFilterView1() {
        DictBuilder builder = new DictBuilder();
//        builder.item("??????", "");
//        builder.item("??????", "1");
//        builder.item("??????", "2");
//        builder.item("??????", "3");

        DictBuilder builder2 = new DictBuilder();
//        builder2.item("??????", "");
//        builder2.item("????????????", "????????????");
//        builder2.item("??????", "??????");
//        builder2.item("??????", "??????");
//        builder2.item("????????????", "????????????");
        ViewInfo viewInfo = new FormBuilder()
//                .addDivider("????????????")
                .addElement(new ElementBuilder("mkeyword")//keyword??????????????????????????????keyword?????????????????????????????????""
                        .widget(new WidgetBuilder(EDITTEXT)
                                .title("???????????????")
                                .hint("???????????????/?????????????????????????????????")
                                .build())
                        .build())
                .addElement(new ElementBuilder("instTimeState")
                        .widget(new WidgetBuilder(CHECKBOX_EXPAND)
                                .title("????????????")
                                .maxLimit(1)
                                .defaultSelection(0)
                                .allowCancelCheck(true)
//                                .initData(builder.build())
                                .build())
                        .build())
                .addElement(new ElementBuilder("taskName")
                        .widget(new WidgetBuilder(CHECKBOX_EXPAND)
                                .title("??????")
                                .maxLimit(1)
                                .allowCancelCheck(true)
                                .defaultSelection(0)
//                                .initData(builder2.build())
                                .build())
                        .build())
                .buildAsViewInfo();

        return viewInfo;
    }

    //?????????????????????
    @SuppressLint("CheckResult")
    private void initFilterData() {
        HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_EVENT_LIST_FILTER_INFO, new HashMap<>())
                .onErrorReturnItem("")
                .map(s -> {
                    ApiResult<ListFilterBean> data = new Gson().fromJson(s, new TypeToken<ApiResult<ListFilterBean>>() {
                    }.getType());
                    return data;
                })
                .subscribe(apiResult -> {
                    if (apiResult.isSuccess()) {
                        ListFilterBean data = apiResult.getData();
                        if (data == null) return;
                        if (!ListUtil.isEmpty(data.getInstStateList())) {
                            List<IDictItem> list = new ArrayList<>();
                            DictItem dictItemtt= new DictItem();
                            dictItemtt.setLabel("??????");
                            dictItemtt.setValue("");
                            list.add(dictItemtt);
                            for (ListFilterBean.InstStateListBean instStateListBean : data.getInstStateList()) {
                                DictItem dictItem = new DictItem();
                                dictItem.setLabel(instStateListBean.getItemName());
                                dictItem.setValue(instStateListBean.getItemCode());
                                list.add(dictItem);
                            }
                            AGMultiCheck mAGMultiCheck = (AGMultiCheck) mView.getFilterWidget(getPageTitles().get(0), "instTimeState").getView();
                            mAGMultiCheck.initData(list);
                            if(mEventState == 1){//??????
                                mAGMultiCheck.setSelection(0);
                            }else if(mEventState == 6){//??????
                                mAGMultiCheck.setSelection(3);
                                mAGMultiCheck.setEnable(false);
                            }else if(mEventState == 8){//??????
                                mAGMultiCheck.setSelection(2);
                                mAGMultiCheck.setEnable(false);
                            }
                        }
                        //taskName
                        if (!ListUtil.isEmpty(data.getTaskNameList())) {
                            List<IDictItem> list = new ArrayList<>();
                            DictItem dictItemdd = new DictItem();
                            dictItemdd.setLabel("??????");
                            dictItemdd.setValue("");
                            list.add(dictItemdd);
                            for (ListFilterBean.TaskNameListBean taskNameList : data.getTaskNameList()) {
                                DictItem dictItem = new DictItem();
                                dictItem.setLabel(taskNameList.getTaskName());
                                dictItem.setValue(taskNameList.getTaskName());
                                list.add(dictItem);
                            }
                            AGMultiCheck mAGMultiCheck = (AGMultiCheck) mView.getFilterWidget(getPageTitles().get(0), "taskName").getView();
                            mAGMultiCheck.initData(list);
                            mAGMultiCheck.setSelection(0);
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                })
        ;
    }


}
