package com.augurit.agmobile.agwater5.gcjs.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.home.model.MainNoticeModel;

import java.util.ArrayList;
import java.util.List;

public class MainNoticeActivity extends AppCompatActivity {
    private List<MainNoticeModel> mNoticeModelList;
    private RecyclerView mNoticeRv;
    private int mStatus;
    private LinearLayout mFragmentSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notice);
        mStatus = getIntent().getIntExtra("status",-1);//1：待办消息 0：通知公告
        initView();

        if(mStatus == 0){
            setTitle("通知公告");
        }else {
            setTitle("待办提醒");
            mFragmentSort.setVisibility(View.VISIBLE);
        }
        mNoticeModelList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MainNoticeModel mainNoticeModel = new MainNoticeModel("APP1.0.3版本更新清单");
            mNoticeModelList.add(mainNoticeModel);
        }
        MainNoticeAdapter adapter = new MainNoticeAdapter(this, mNoticeModelList,0);
        mNoticeRv.setAdapter(adapter);
        mNoticeRv.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    }

    private void initView() {
        mFragmentSort = findViewById(R.id.ll_fragment_sort);
        mNoticeRv = findViewById(R.id.notice_rv);
    }


}