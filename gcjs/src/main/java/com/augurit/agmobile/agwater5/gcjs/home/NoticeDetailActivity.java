package com.augurit.agmobile.agwater5.gcjs.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.home.model.MainNoticeModel;

import java.util.ArrayList;
import java.util.List;

public class NoticeDetailActivity extends AppCompatActivity {

    private RecyclerView mNoticeDetailRv;
    private List<MainNoticeModel> mNoticeModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        mNoticeDetailRv = findViewById(R.id.notice_detail_rv);
        mNoticeModelList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            MainNoticeModel mainNoticeModel = new MainNoticeModel("代码优化");
            mNoticeModelList.add(mainNoticeModel);
        }
        MainNoticeAdapter adapter = new MainNoticeAdapter(this, mNoticeModelList,1);
        mNoticeDetailRv.setAdapter(adapter);
        mNoticeDetailRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
    }
}