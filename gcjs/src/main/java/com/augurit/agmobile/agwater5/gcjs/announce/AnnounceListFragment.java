package com.augurit.agmobile.agwater5.gcjs.announce;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.common.utils.StringUtils;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.model.Announce;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

import static android.app.Activity.RESULT_OK;

/**
 * com.augurit.agmobile.agwater5.gcjs.eventlist
 * Created by sdb on 2019/4/2  15:50.
 * Desc：通知公告fragment
 */

public class AnnounceListFragment extends BaseViewListFragment<Announce.RowsBean> {

    public static AnnounceListFragment newInstance() {
        AnnounceListFragment fragment = new AnnounceListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected void initArguments() {
        super.initArguments();
    }

    private Observable<ApiResult<List<Announce.RowsBean>>> getEvnetList(String startRow, String pageSize, Map<String, String> filterParams) {

        Map<String, String> param = new HashMap<>();

        param.put("pageNum", startRow);
        param.put("pageSize", pageSize);

//        return HttpUtil.getInstance("http://192.168.30.125:8087").get(GcjsUrlConstant.ANNOUNCE_LIST, param)
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsUrlConstant.ANNOUNCE_LIST, param)
                .map(s -> {
                            if (StringUtils.isJson(s)) {
                                ApiResult<Announce> apiResult = new Gson().fromJson(s, new TypeToken<ApiResult<Announce>>() {
                                }.getType());
                                Announce data = apiResult.getData();
                                if (data != null) {
                                    List<Announce.RowsBean> rows = data.getRows();
                                    ApiResult<List<Announce.RowsBean>> listApiResult = new ApiResult<>();
                                    listApiResult.setSuccess(true);
                                    listApiResult.setData(rows);
                                    return listApiResult;
                                } else {
                                    return new ApiResult<>();
                                }
                            } else {
                                return new ApiResult<>();
                            }
                        }
                );


    }

    /**
     * 子类在此方法中初始化列表所需Adapter
     *
     * @return adapter
     */
    @Override
    protected BaseViewListAdapter<Announce.RowsBean> initAdapter() {
        return new AnnounceListAdapter(getContext());
    }

    /**
     * 子类在此方法中实现数据的加载<br>
     * 如果数据格式不符合ApiResult<List<T>>，可通过RxJava map操作符转换
     *
     * @param page         页数
     * @param rows         每页数量 {@link #mRows}
     * @param filterParams 筛选条件参数
     * @return 包含数据的Observable
     */
    @Override
    protected Observable<ApiResult<List<Announce.RowsBean>>> loadDatas(int page, int rows, Map<String, String> filterParams) {

        return getEvnetList(page + "", rows + "", filterParams);
    }

    @Override
    public void onItemClick(View itemView, int position, Announce.RowsBean data) {
        Intent intent = new Intent(getActivity(), AnnounceDetailActivity.class);
        intent.putExtra("data", data);
        getActivity().startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(View itemView, int position, Announce.RowsBean data) {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (refresh_layout != null) {
                loadDatas(true);
            }
        }
    }
}
