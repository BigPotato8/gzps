package com.augurit.agmobile.agwater5.gcjspad.eventdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventProcess;
import com.augurit.agmobile.agwater5.gcjspad.BasePadFragment;
import com.augurit.agmobile.agwater5.gcjspad.eventdetail.adapter.ApproveSetpItemOutterAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 审批步骤列表
 */
public class ApprovePerStepPadFragment extends BasePadFragment {
    EventProcess data;
    RecyclerView rv_items;
    protected ApproveSetpItemOutterAdapter mApproveSetpItemOutterAdapter;

    public static ApprovePerStepPadFragment getInstance(EventProcess data) {
        ApprovePerStepPadFragment fragment = new ApprovePerStepPadFragment();
        fragment.data = data;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_approve_per_step_pad, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv_items = view.findViewById(R.id.rv_items);
        mApproveSetpItemOutterAdapter = new ApproveSetpItemOutterAdapter(R.layout.item_approve_setp);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setAutoMeasureEnabled(true);
        rv_items.setLayoutManager(linearLayoutManager);
        rv_items.setAdapter(mApproveSetpItemOutterAdapter);
        if (data != null && data.getChildNode().size() > 0) {
            mApproveSetpItemOutterAdapter.setNewData(data.getChildNode());
        } else {
            List<EventProcess> singleList = new ArrayList<>();
            singleList.add(data);
            mApproveSetpItemOutterAdapter.setNewData(singleList);
        }
        mApproveSetpItemOutterAdapter.notifyDataSetChanged();
    }
}
