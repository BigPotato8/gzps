package com.augurit.agmobile.agwater5.im.announce;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs.GcjsFragment;
import com.augurit.agmobile.agwater5.gcjs.announce.AnnounceDetailActivity;
import com.augurit.agmobile.agwater5.gcjs.announce.AnnounceRepository;
import com.augurit.agmobile.agwater5.gcjs.model.Announce;
import com.augurit.agmobile.common.im.timchat.ui.CustomChatActivity;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.toast.ToastUtil;
import com.augurit.agmobile.common.lib.validate.ListUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.im
 * @createTime 创建时间 ：2019-06-10
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class AnnounceChatActivity extends CustomChatActivity {

    private AnnounceRepository mRepository;
    private CompositeDisposable mCompositeDisposable;
    private AnnounceMessageAdapter mAdapter;
    private List<Announce.RowsBean> mDatas;
    private String mTitleText;
    private int mPage = 1;
    private int mRows = 10;
    private TextView mValue0, mValue1, mValue2, mValue3, tv_announce_more;

    public static Intent getIntent(Context context, String id, String name) {
        Intent intent = new Intent(context, AnnounceChatActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        return intent;
    }

    @Override
    protected void initArguments() {
        mIdentify = getIntent().getStringExtra("id");
        mTitleText = getIntent().getStringExtra("name");
        mRepository = new AnnounceRepository();
        mCompositeDisposable = new CompositeDisposable();
        mDatas = new ArrayList<>();
    }

    @Override
    protected void initView() {
        super.initView();
        View headView = View.inflate(this, R.layout.handling_statistic_layout, null);
        mValue0 = headView.findViewById(R.id.tv_value_0);
        mValue1 = headView.findViewById(R.id.tv_value_1);
        mValue2 = headView.findViewById(R.id.tv_value_2);
        mValue3 = headView.findViewById(R.id.tv_value_3);
        mLvChat.addHeaderView(headView);
        mTitle.setTitleText(mTitleText);
        mInput.setVisibility(View.GONE);
        onRefresh(mRefreshLayout);
    }

    @Override
    protected void initAdapter() {
        mAdapter = new AnnounceMessageAdapter(this, mDatas);
        mAdapter.setOnItemClickListener((position, data) -> {
            Intent intent = new Intent(this, AnnounceDetailActivity.class);
            intent.putExtra("data", data);
            startActivity(intent);
        });
        mLvChat.setAdapter(mAdapter);
    }

    @Override
    protected void onRefresh(RefreshLayout refreshLayout) {
        mCompositeDisposable.clear();
        Disposable subscribe = Observable.zip(mRepository.getAnnounces(mPage, mRows).subscribeOn(Schedulers.io()), mRepository.getHandlingStatistic().subscribeOn(Schedulers.io()), (listApiResult, s) -> {
            Object[] objects = new Object[2];
            objects[0] = listApiResult;
            objects[1] = s;
            return objects;
        }).subscribe(objects -> {
            mRefreshLayout.finishRefresh();
            ApiResult<List<Announce.RowsBean>> listApiResult = (ApiResult<List<Announce.RowsBean>>) objects[0];
            bindAnnounceView(listApiResult);
            bindHandlingStatistic((String) objects[1]);
        });

//        Disposable subscribe = mRepository.getAnnounces(mPage, mRows)
//                .subscribe(new Consumer<ApiResult<List<Announce.RowsBean>>>() {
//                    @Override
//                    public void accept(ApiResult<List<Announce.RowsBean>> listApiResult) throws Exception {
//                        mRefreshLayout.finishRefresh();
//                        if (listApiResult.isSuccess()) {
//                            List<Announce.RowsBean> data = listApiResult.getData();
//                            if (ListUtil.isNotEmpty(data)) {
//                                Collections.reverse(data);
//                                mDatas.addAll(0, data);
//                                mAdapter.notifyDataSetChanged();
//                                if (mPage == 1) {
//                                    mLvChat.setSelection(mAdapter.getCount() - 1);
//                                }
//                                mPage++;
//                                mMaskLayout.hide();
//                            } else if (mPage == 1) {
//                                mMaskLayout.showEmpty(AnnounceChatActivity.this.getString(R.string.announce_no_result), null);
//                            }
//                        } else if (mDatas.isEmpty()) {
//                            mMaskLayout.showEmpty(AnnounceChatActivity.this.getString(R.string.announce_no_result), null);
//                        }
//                    }
//                }, throwable -> {
//                    mRefreshLayout.finishRefresh();
//                    throwable.printStackTrace();
//                });
        mCompositeDisposable.add(subscribe);
    }

    private void bindHandlingStatistic(String res) {
        try {
            JSONObject jsonObject = new JSONObject(res);
            boolean success = jsonObject.getBoolean("success");
            if (success) {
                String message = jsonObject.getString("message");
                String replaceJson = message.replace("\\\"", "");
                JSONObject resultObject = new JSONObject(replaceJson);
                JSONObject result = resultObject.getJSONObject("result");
                String nowMonthComplete = result.getString("nowMonthComplete");
                String nowMonthCount = result.getString("nowMonthCount");
                String allItemInstComplete = result.getString("allItemInstComplete");
                String allItemInstCount = result.getString("allItemInstCount");
                mValue0.setText(nowMonthComplete);
                mValue1.setText(nowMonthCount);
                mValue2.setText(allItemInstComplete);
                mValue3.setText(allItemInstCount);
            }
        } catch (Exception e) {
            ToastUtil.shortToast(AnnounceChatActivity.this, "获取办件统计数据失败");
        }
    }

    private void bindAnnounceView(ApiResult<List<Announce.RowsBean>> listApiResult) {
        //mRefreshLayout.finishRefresh();
        if (listApiResult.isSuccess()) {
            List<Announce.RowsBean> data = listApiResult.getData();
            if (ListUtil.isNotEmpty(data)) {
                Collections.reverse(data);
                mDatas.addAll(0, data);
                mAdapter.notifyDataSetChanged();
                if (mPage == 1) {
                    mLvChat.setSelection(mAdapter.getCount() - 1);
                }
                mPage++;
                mMaskLayout.hide();
            } else if (mPage == 1) {
                //mMaskLayout.showEmpty(AnnounceChatActivity.this.getString(R.string.announce_no_result), null);
            }
        } else if (mDatas.isEmpty()) {
            //mMaskLayout.showEmpty(AnnounceChatActivity.this.getString(R.string.announce_no_result), null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
