package com.augurit.agmobile.agwater5.im.msg;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.common.webview.WebViewActivity;
import com.augurit.agmobile.agwater5.gcjs.common.webview.WebViewConstant;
import com.augurit.agmobile.agwater5.gcjs.msg.MsgDetailActivity;
import com.augurit.agmobile.agwater5.gcjs.msg.MsgRepository;
import com.augurit.agmobile.agwater5.gcjs.msg.model.MsgBean;
import com.augurit.agmobile.agwater5.gcjs.publicaffair.WatchImageOrPdfActivity;
import com.augurit.agmobile.agwater5.gcjs.zcfg.ZcfgActivity;
import com.augurit.agmobile.common.im.timchat.ui.CustomChatActivity;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListActivity.EXTRA_TITLE;

/**
 * 个人消息 activity
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.im.msg
 * @createTime 创建时间 ：2019-06-11
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class MsgChatActivity extends CustomChatActivity {

    private MsgRepository mRepository;
    private CompositeDisposable mCompositeDisposable;
    private MsgAdapter mAdapter;
    //private List<MsgBean.RowsBean> mDatas;
    private List<ActionBean> mDatas;
    private String mTitleText;
    private int mPage = 1;
    private int mRows = 10;

    public static Intent getIntent(Context context, String id, String name) {
        Intent intent = new Intent(context, MsgChatActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        return intent;
    }

    @Override
    protected void initArguments() {
        mIdentify = getIntent().getStringExtra("id");
        mTitleText = getIntent().getStringExtra("name");
        mRepository = new MsgRepository();
        mCompositeDisposable = new CompositeDisposable();
        mDatas = new ArrayList<>();
    }

    @Override
    protected int getLayoutId() {
        return super.getLayoutId();
    }

    @Override
    protected void initView() {
        super.initView();
        mTitle.setTitleText(mTitleText);
        mInput.setVisibility(View.GONE);
        onRefresh(mRefreshLayout);
        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.setOnLoadMoreListener(this::onLoadMore);
    }

    @Override
    protected void initAdapter() {
        mAdapter = new MsgAdapter(this, mDatas);
        mAdapter.setOnItemClickListener(new MsgAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, ActionBean data) {
                switch (position) {
                    case 0:
                        Intent intent1 = new Intent(MsgChatActivity.this, WatchImageOrPdfActivity.class);
                        ArrayList<String> rawFiles = new ArrayList<>();
                        rawFiles.add(AwUrlManager.getGcjsUrl() + GcjsUrlConstant.GET_RAW_FILE);
                        intent1.putExtra(WatchImageOrPdfActivity.FILES_PATH, rawFiles);
                        MsgChatActivity.this.startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent(MsgChatActivity.this, WebViewActivity.class);
                        intent2.putExtra(WebViewConstant.WEBVIEW_TITLE, getString(R.string.menu_fileIndicate));
                        intent2.putExtra(WebViewConstant.WEBVIEW_URL_PATH, AwUrlManager.getGcjsUrl() + GcjsUrlConstant.GET_BUSINESSGUIDE);
                        MsgChatActivity.this.startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(MsgChatActivity.this, WatchImageOrPdfActivity.class);
                        ArrayList<String> rawFiles2 = new ArrayList<>();
                        rawFiles2.add(AwUrlManager.getGcjsUrl() + GcjsUrlConstant.GET_REFORMFLOW);
                        intent3.putExtra(WatchImageOrPdfActivity.FILES_PATH, rawFiles2);
                        MsgChatActivity.this.startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4 = new Intent(MsgChatActivity.this, ZcfgActivity.class);

                        intent4.putExtra(EXTRA_TITLE, getString(R.string.menu_informPromise));
                        MsgChatActivity.this.startActivity(intent4);
                        break;

                }

            }
        });
        mLvChat.setAdapter(mAdapter);
    }

    @Override
    protected void onRefresh(RefreshLayout refreshLayout) {
//        mCompositeDisposable.clear();
//        Disposable subscribe = mRepository.getMsgs(mPage, mRows)
//                .subscribe(listApiResult -> {
//                    mRefreshLayout.finishRefresh();
//                    if (listApiResult.isSuccess()) {
//                        List<MsgBean.RowsBean> data = listApiResult.getData();
//                        if (ListUtil.isNotEmpty(data)) {
//                            Collections.reverse(data);
//                            mDatas.addAll(0, data);
//                            mAdapter.notifyDataSetChanged();
//                            if (mPage == 1) {
//                                mLvChat.setSelection(mAdapter.getCount() - 1);
//                            }
//                            mPage++;
//                            mMaskLayout.hide();
//                        } else if (mPage == 1) {
//                            mMaskLayout.showEmpty(getString(R.string.msg_no_result), null);
//                        }
//                    } else if (mDatas.isEmpty()) {
//                        mMaskLayout.showEmpty(getString(R.string.msg_no_result), null);
//                    }
//                }, throwable -> {
//                    mRefreshLayout.finishRefresh();
//                    throwable.printStackTrace();
//                });
//        mCompositeDisposable.add(subscribe);
        ActionBean actionBean1 = new ActionBean();
        actionBean1.setActionName("政策文件");
        actionBean1.setActionUri(R.mipmap.rawfile);

        ActionBean actionBean2 = new ActionBean();
        actionBean2.setActionName("办事指南");
        actionBean2.setActionUri(R.mipmap.fileindicate);

        ActionBean actionBean3 = new ActionBean();
        actionBean3.setActionName("改革流程图");
        actionBean3.setActionUri(R.mipmap.reformtree);

        ActionBean actionBean4 = new ActionBean();
        actionBean4.setActionName("政策法规");
        actionBean4.setActionUri(R.mipmap.informpromise);

        mDatas.add(actionBean1);
        mDatas.add(actionBean2);
        mDatas.add(actionBean3);
        mDatas.add(actionBean4);
    }

    protected void onLoadMore(RefreshLayout refreshLayout) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
