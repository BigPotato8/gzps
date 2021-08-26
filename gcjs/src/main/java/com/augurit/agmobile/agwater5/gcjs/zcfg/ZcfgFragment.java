package com.augurit.agmobile.agwater5.gcjs.zcfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.publicaffair.WatchImageOrPdfActivity;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.common.common.http.HttpUtil;
import com.augurit.common.common.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * com.augurit.agmobile.agwater5.gcjs_public.zcfg
 * Created by sdb on 2019/5/30  11:42.
 * Desc：
 */

public class ZcfgFragment extends BaseViewListFragment<ZcfgBean> {


    private String mOrgCode;

    public static ZcfgFragment getInsance(String orgCode) {
        ZcfgFragment zcfgFragment = new ZcfgFragment();
        Bundle bundle = new Bundle();
        bundle.putString("orgCode", orgCode);
        zcfgFragment.setArguments(bundle);
        return zcfgFragment;
    }


    @Override
    protected void initArguments() {
        super.initArguments();

        mOrgCode = getArguments().getString("orgCode", "");
    }


    @Override
    protected BaseViewListAdapter<ZcfgBean> initAdapter() {
        return new ZcfgAdapter(getActivity());
    }

    @Override
    protected Observable<ApiResult<List<ZcfgBean>>> loadDatas(int page, int rows, Map filterParams) {
        refresh_layout.setNoMoreData(true);
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_POLICY, null)
                .map(s -> {
                    if (StringUtils.isJson(s)) {
                        JSONObject jsonObject = new JSONObject(s);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            JSONObject contentObject = jsonObject.getJSONObject("content");
                            String jsonString = contentObject.getJSONObject(mOrgCode).toString();
                            Map<String, String> stringMap = new Gson().fromJson(jsonString, new TypeToken<Map<String, String>>() {
                            }.getType());
                            List<ZcfgBean> zcfgBeans = new ArrayList<>();
                            for (String key : stringMap.keySet()) {
                                ZcfgBean zcfgBean = new ZcfgBean(stringMap.get(key), AwUrlManager.getGcjsUrl() + "cp-rest/" + key);
                                zcfgBeans.add(zcfgBean);
                            }

                            ApiResult<List<ZcfgBean>> listApiResult = new ApiResult<>();
                            listApiResult.setData(zcfgBeans);

                            return listApiResult;
                        }
                    }
                    return new ApiResult<>();
                });

    }

    @Override
    public void onItemClick(View itemView, int position, ZcfgBean data) {

        ArrayList<String> rawFiles = new ArrayList<>();
        rawFiles.add(data.getUrl());
        Intent intent = WatchImageOrPdfActivity.newIntent(getActivity(), rawFiles);
        getActivity().startActivity(intent);

    }

    @Override
    public boolean onItemLongClick(View itemView, int position, ZcfgBean data) {
        return false;
    }
}
