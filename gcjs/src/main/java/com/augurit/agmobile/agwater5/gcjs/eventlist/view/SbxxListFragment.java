package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import com.augurit.agmobile.agwater5.common.view.NoscollRecyclerView;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventInfoBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter.EventSbxxAdapter;
import com.augurit.agmobile.busi.bpm.form.view.ElementPosition;
import com.augurit.agmobile.busi.bpm.form.view.FormState;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.common.common.form.AwFormConfig;
import com.augurit.common.common.form.AwFormFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description 申报信息、申报事项列表、办件信息
 * @date: 20200302
 * @author: xieruibin
 */
public class SbxxListFragment extends AwFormFragment {
    EventSbxxAdapter adapter;
    String taskId;
    EventInfoBean eventInfoBean;
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
    public static SbxxListFragment getInstance(String taskId) {
        SbxxListFragment baseInfoFragment = new SbxxListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("taskId", taskId);
        baseInfoFragment.setArguments(bundle);
        return baseInfoFragment;
    }

    @Override
    protected void initArguments() {
        super.initArguments();
        mFormCode = AwFormConfig.FORM_GCJS_SBXX;
        mFormState = FormState.STATE_READ;
        taskId = getArguments().getString("taskId");
    }

    @Override
    protected void onFormLoaded() {
        super.onFormLoaded();
        NoscollRecyclerView noscollRecyclerView = new NoscollRecyclerView(getActivity());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        noscollRecyclerView.setLayoutParams(layoutParams);
        List<EventInfoBean.IteminstListBean> iteminstList = new ArrayList<>();
        if (eventInfoBean!=null&&eventInfoBean.getIteminstList()!=null) {
            iteminstList = eventInfoBean.getIteminstList();
        }
        adapter = new EventSbxxAdapter(getActivity(), iteminstList);
        noscollRecyclerView.setAdapter(adapter);
        noscollRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        mFormPresenter.addViews("sbsxs", ElementPosition.BELOW, noscollRecyclerView);


    }


    @Subscribe
    public void getEventInfo(EventInfoBean eventInfoBean) {
        this.eventInfoBean = eventInfoBean;
        EventInfoBean.ApplyInfoVoBean applyInfoVo = eventInfoBean.getApplyInfoVo();
        Gson gson = new Gson();
        String toJson = gson.toJson(applyInfoVo);
        Map<String, String> map = gson.fromJson(toJson, new TypeToken<Map<String, String>>() {
        }.getType());

        mRecord = new FormRecord();
        mRecord.setValues(map);
        loadForm();
    }

    @Override
    protected boolean shouldLoadImmediately() {
        return false;
    }
}
