package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.PersonSelectBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.agwater5.gcjs.eventlist.view.adapter.PersonSelectAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListActivity;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * 选择用户，最大数量，选中显示，可折叠，
 */
public class PersonSelectActivity extends AppCompatActivity {
    public static final String SELECT_NUM = "SELECT_NUM";//可选中最大数量
    public static final String SELECT_LIST = "SELECT_LIST";//可选中最大数量
    int num = 1;
    RecyclerView rv_user_select;
    PersonSelectAdapter adapter;
    Button btn_sure,btn_cancel;
    List<PersonSelectBean> list;
    EventRepository eventRepository;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcjs_user_select);
        rv_user_select = findViewById(R.id.rv_user_select);
        btn_sure = findViewById(R.id.btn_sure);
        btn_cancel = findViewById(R.id.btn_cancel);
        list = new ArrayList<>();
        num = getIntent().getIntExtra(SELECT_NUM,1);
        eventRepository = new EventRepository();
        Map<String,String> params = new HashMap<>();
        params.put("assigneePartInMode","2");
        //获取第一级的组织信息
        eventRepository.getOrgTree(params)
                .subscribe(apiResult -> {
                    if (apiResult.isSuccess()&&apiResult.getData()!=null) {
                        if (adapter==null) {
                            adapter = new PersonSelectAdapter(PersonSelectActivity.this,apiResult.getData(),num);
                        }
                        rv_user_select.setAdapter(adapter);
                        rv_user_select.setLayoutManager(new LinearLayoutManager(PersonSelectActivity.this));
                    }
                },throwable -> throwable.printStackTrace());
        btn_cancel.setOnClickListener(v->{
            finish();
        });
        btn_sure.setOnClickListener(v->{
            Intent intent = new Intent();
            if (adapter!=null) {
                List<PersonSelectBean> selectList = adapter.getSelectList();
                intent.putExtra(SELECT_LIST,(Serializable) selectList);
            }
            setResult(EventApprovalActivity.FINISH_CODE,intent);
            finish();
        });
    }
}
