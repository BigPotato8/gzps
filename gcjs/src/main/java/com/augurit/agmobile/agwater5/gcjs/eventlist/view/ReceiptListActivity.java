package com.augurit.agmobile.agwater5.gcjs.eventlist.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventClbzItemBean;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventListItem;
import com.augurit.agmobile.agwater5.gcjs.eventlist.model.EventState;
import com.augurit.agmobile.agwater5.gcjs.eventlist.source.EventRepository;
import com.augurit.agmobile.busi.bpm.record.model.FormRecord;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListActivity;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.busi.common.login.LoginManager;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.view.navigation.TitleBar;
import com.augurit.common.common.form.AwFormActivity;
import com.augurit.common.common.util.StringUtil;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.augurit.agmobile.agwater5.gcjs.eventlist.view.MaterialCorrectionActivity.POST_SUCCESS;

public class ReceiptListActivity extends AppCompatActivity {
    private String[] mTitles;
    private List<Fragment> mFragments;
    private EventListItem.DataBean mEventListItem;
    private String mValue;
    private String sValue;
    private String taskValue;
    EventRepository eventRepository;
    private boolean isToReceipt;
    String matCorrectDtosJson;
    String correctDueDays;
    private String correctId;
    private String correctinstDtoJson;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_table);

//        EventBus.getDefault().register(this);
        initArguments();
        initView();
    }

    protected void initArguments() {
        Intent intent = getIntent();
        if (intent != null) {
            mEventListItem = (EventListItem.DataBean) intent.getSerializableExtra("mEventListItem");
            if (mEventListItem == null) {
                String dataJson = intent.getStringExtra("dataJson");
                if (!StringUtil.isEmpty(dataJson)) {
                    mEventListItem = new Gson().fromJson(dataJson, EventListItem.DataBean.class);
                }
            }
            mValue = getIntent().getStringExtra("mValue");
            sValue = getIntent().getStringExtra("sValue");
            taskValue = getIntent().getStringExtra("taskValue");
            isToReceipt = getIntent().getBooleanExtra("isToReceipt", isToReceipt);
//            matCorrectDtosJson = getIntent().getStringExtra("matCorrectDtosJson");
//            correctDueDays = getIntent().getStringExtra("correctDueDays");
//            correctId=getIntent().getStringExtra("correctId");
            correctinstDtoJson = getIntent().getStringExtra("correctinstDtoJson");
        }
    }

    protected void initView() {
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager view_pager = findViewById(R.id.view_pager);
        TitleBar titleBar = findViewById(R.id.title_bar);
        Button submit_btn = findViewById(R.id.btn_submit);
        Button delete_btn = findViewById(R.id.btn_delete);

        mTitles = new String[]{
                getString(R.string.gcjs_item_paperList)
        };
        mFragments = new ArrayList<>();
        if (isToReceipt) {
            titleBar.setTitleText("材料补正回执");
            titleBar.setBackButtonVisible(false);
            CorrectionReceiptFragment correctionReceiptFragment = new CorrectionReceiptFragment();
            correctionReceiptFragment.setEventListItem(correctinstDtoJson, mEventListItem);
            correctionReceiptFragment.setMap((HashMap<String, String>) getIntent().getSerializableExtra("map"));
            mFragments.add(correctionReceiptFragment);
        } else {
            if (mEventListItem != null) {
                if ("审查决定".equals(mEventListItem.getTaskName())) {
                    if (!"".equals(sValue)) {
                        if ("1".equals(sValue)) {
                            titleBar.setTitleText("审批决定通过");
                            ApproveThroughFragment approveThroughFragment = new ApproveThroughFragment();
                            approveThroughFragment.setEventListItem(mEventListItem);
                            mFragments.add(approveThroughFragment);
                        } else {
                            titleBar.setTitleText("审批决定不通过");
                            ApproveNotThroughFragment approveNotThroughFragment = new ApproveNotThroughFragment();
                            approveNotThroughFragment.setEventListItem(mEventListItem);
                            mFragments.add(approveNotThroughFragment);
                        }
                    }
                } else {
                    try {
                        if (!"".equals(mValue) || !"".equals(taskValue)) {
                            if ("1".equals(mValue) && "1".equals(taskValue)) {//受理
                                titleBar.setTitleText("受理回执");
                                AcceptReceiptFragment acceptReceiptFragment = new AcceptReceiptFragment();
                                acceptReceiptFragment.setEventListItem(mEventListItem);
                                mFragments.add(acceptReceiptFragment);
                            } else {//mValue为0
                                titleBar.setTitleText("不予受理回执");
                                NotAcceptReceiptFragment notAcceptReceiptFragment = new NotAcceptReceiptFragment();
                                notAcceptReceiptFragment.setEventListItem(mEventListItem);
                                mFragments.add(notAcceptReceiptFragment);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        MyPageAdapter adapter = new MyPageAdapter(getSupportFragmentManager());
        view_pager.setAdapter(adapter);
        view_pager.setOffscreenPageLimit(mFragments.size());
        tabLayout.setupWithViewPager(view_pager);
        //移除下划线
        // ...setselectedTabIndicatorHeight(0)
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setTabTextColors(0xff666666, 0xff0190F2);

    }

    private class MyPageAdapter extends FragmentPagerAdapter {

        MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }


}
