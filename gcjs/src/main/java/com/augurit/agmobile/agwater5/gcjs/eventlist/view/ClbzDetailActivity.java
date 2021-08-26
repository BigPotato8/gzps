package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import com.augurit.agmobile.agwater5.common.view.NoscollRecyclerView;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.ClbzDetailBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter.EventDetailClbzAdapter;
import com.augurit.agmobile.busi.bpm.form.view.ElementPosition;
import com.augurit.agmobile.busi.bpm.form.view.FormState;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.common.common.form.AwFormActivity;
import com.augurit.common.common.form.AwFormConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description 材料补正信息，材料列表
 * @date: 20200302
 * @author: xieruibin
 */
public class ClbzDetailActivity extends AwFormActivity {
    public final static String CLBZ_BEAN_KEY = "CLBZ_BEAN_KEY";
    EventDetailClbzAdapter adapter;
    ClbzDetailBean mBean;
    private List<ClbzDetailBean.MatCorrectDtosBean> iteminstList;

    @Override
    protected void initArguments() {
        super.initArguments();

        mBean = (ClbzDetailBean) getIntent().getSerializableExtra(CLBZ_BEAN_KEY);
        if (mBean!=null) {
            if (mBean != null && mBean.getMatCorrectDtos() != null) {
                iteminstList = new ArrayList<>(mBean.getMatCorrectDtos());
            } else {
                iteminstList = new ArrayList<>();
            }
            mBean.setMatCorrectDtos(null);
            Gson gson = new Gson();
            String toJson = gson.toJson(mBean);
            Map<String, String> map = gson.fromJson(toJson, new TypeToken<Map<String, String>>() {
            }.getType());
            map.put("applyinstCode1",map.get("applyinstCode"));
            map.put("createrName1",map.get("createrName"));
            mRecord = new FormRecord();
            mRecord.setValues(map);
        }
        mFormCode = AwFormConfig.FORM_GCJS_DETAIL_CLBZ;
        mFormState = FormState.STATE_READ;
//        mTitleText = "材料补正详情";

    }

    @Override
    protected void onFormLoaded() {
        super.onFormLoaded();
        NoscollRecyclerView noscollRecyclerView = new NoscollRecyclerView(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        noscollRecyclerView.setLayoutParams(layoutParams);

        adapter = new EventDetailClbzAdapter(this, iteminstList);
        noscollRecyclerView.setAdapter(adapter);
        noscollRecyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        mFormPresenter.addViews("sbsxs", ElementPosition.BELOW, noscollRecyclerView);

    }

}
