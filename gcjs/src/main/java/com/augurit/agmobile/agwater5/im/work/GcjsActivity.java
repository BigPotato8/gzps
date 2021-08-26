package com.augurit.agmobile.agwater5.im.work;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.common.webview.WebViewActivity;
import com.augurit.agmobile.agwater5.gcjs.common.webview.WebViewConstant;
import com.augurit.agmobile.agwater5.gcjs.publicaffair.WatchImageOrPdfActivity;
import com.augurit.agmobile.agwater5.gcjs.zcfg.ZcfgActivity;
import com.augurit.agmobile.agwater5.im.work.model.PubItem;
import com.augurit.agmobile.common.im.imsdk.ui.TemplateTitle;
import com.augurit.agmobile.common.im.timchat.model.PubMenuItem;
import com.augurit.agmobile.common.im.timchat.ui.chatui.DisplayUtils;
import com.augurit.agmobile.common.im.timchat.ui.customview.ChatBottomMenuView;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import static com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListActivity.EXTRA_TITLE;

/**
 * @author 创建人 ：taoerxiang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.im
 * @createTime 创建时间 ：2019-08-28
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class GcjsActivity extends AppCompatActivity {
    private SubscriptionRepository mRepository;
    private CompositeDisposable mCompositeDisposable;
    private SubscriptionMessageAdapter mAdapter;
    private TemplateTitle templateTitle;
    private RecyclerView mRecyclerView;
    private String mTitleText;
    private String mIdentify;
    private ChatBottomMenuView chatBottomMenuView;
    private int mPage = 1;
    private int mRows = 10;

    public static Intent getIntent(Context context, String id, String name) {
        Intent intent = new Intent(context, GcjsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcjs_layout);
        initArguments();
        initView();
        initData();
    }

    private void initArguments() {
        mIdentify = getIntent().getStringExtra("id");
        mTitleText = getIntent().getStringExtra("name");
        mRepository = new SubscriptionRepository();
        mCompositeDisposable = new CompositeDisposable();
    }

    private void initView() {
        templateTitle = findViewById(R.id.title_template);
        chatBottomMenuView = findViewById(R.id.cbm_menu);
        templateTitle.setTitleText("工作消息");
        mRecyclerView = findViewById(R.id.rv_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new IMPubItemSpaceDecoration(
                DisplayUtils.dip2px(this, 12), DisplayUtils.dip2px(this, 12), DisplayUtils.dip2px(this, 12)));
        mRecyclerView.setHasFixedSize(true);
        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new SubscriptionMessageAdapter(R.layout.item_subscription_layout);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        Disposable subscribe1 = mRepository.getSubscriptions(this, 0, 10)
                .subscribe(listApiResult -> {
                    if (listApiResult.isSuccess()) {
                        List<PubItem> data = listApiResult.getData();
                        if (ListUtil.isNotEmpty(data)) {
                            mAdapter.setNewData(data);
                        }
                    }
                }, throwable -> {

                });
        mCompositeDisposable.add(subscribe1);

        Disposable subscribe2 = mRepository.getPubMenuItems(this)
                .subscribe(listApiResult -> {
                    if (listApiResult.isSuccess()) {
                        List<PubMenuItem> data = listApiResult.getData();
                        if (ListUtil.isNotEmpty(data)) {
                            chatBottomMenuView.initMenuItems(data,false);
                            chatBottomMenuView.setOnMenuJumpListener(inputMenuItem -> {
                                String url = inputMenuItem.getUrl();
                                String title = inputMenuItem.getTitle();
                                if ("办事指南".equals(title)) {
                                    Intent intent2 = new Intent(GcjsActivity.this, WebViewActivity.class);
                                    intent2.putExtra(WebViewConstant.WEBVIEW_TITLE, getString(R.string.menu_fileIndicate));
                                    intent2.putExtra(WebViewConstant.WEBVIEW_URL_PATH, AwUrlManager.getGcjsUrl() + GcjsUrlConstant.GET_BUSINESSGUIDE);
                                    GcjsActivity.this.startActivity(intent2);
                                }
                                if ("政策法规".equals(title)) {
                                    Intent intent = new Intent(GcjsActivity.this, ZcfgActivity.class);
                                    intent.putExtra(EXTRA_TITLE, getString(R.string.menu_informPromise));
                                    GcjsActivity.this.startActivity(intent);
                                }
                                if ("政策文件".equals(title)) {
                                    Intent intent = new Intent(GcjsActivity.this, WatchImageOrPdfActivity.class);
                                    ArrayList<String> rawFiles = new ArrayList<>();
                                    rawFiles.add(AwUrlManager.getGcjsUrl() + GcjsUrlConstant.GET_RAW_FILE);
                                    intent.putExtra(WatchImageOrPdfActivity.FILES_PATH, rawFiles);
                                    GcjsActivity.this.startActivity(intent);
                                }
                                if ("改革流程图".equals(title)) {
                                    Intent intent = new Intent(GcjsActivity.this, WatchImageOrPdfActivity.class);
                                    ArrayList<String> rawFiles2 = new ArrayList<>();
                                    rawFiles2.add(AwUrlManager.getGcjsUrl() + GcjsUrlConstant.GET_REFORMFLOW);
                                    intent.putExtra(WatchImageOrPdfActivity.FILES_PATH, rawFiles2);
                                    GcjsActivity.this.startActivity(intent);
                                }
                                if ("办件统计".equals(title)) {
                                    Intent intent = new Intent(GcjsActivity.this, StatisticsActivity.class);
                                    GcjsActivity.this.startActivity(intent);
                                }

                            });
                        }
                    }
                }, throwable -> {

                });
        mCompositeDisposable.add(subscribe2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
