package com.augurit.agmobile.agwater5.gcjs.zcfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.publicaffair.WatchImageOrPdfActivity;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.agmobile.common.lib.sp.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * 政策文件fragment，有主页菜单迁移进来
 * com.augurit.agmobile.agwater5.gcjs_public.zcfg
 * Created by sdb on 2019/5/30  11:42.
 * Desc：
 */

public class ZcwjFragment extends BaseViewListFragment<ZcfgBean> {
    private String mRawFileUrl;
    private SharedPreferencesUtil mPreferencesUtil;
    private String mOrgCode;

    public static ZcwjFragment getInsance(String orgCode) {
        ZcwjFragment zcfgFragment = new ZcwjFragment();
        Bundle bundle = new Bundle();
        bundle.putString("orgCode", orgCode);
        zcfgFragment.setArguments(bundle);
        return zcfgFragment;
    }


    @Override
    protected void initArguments() {
        super.initArguments();

        //mOrgCode = getArguments().getString("orgCode", "");
        mPreferencesUtil = new SharedPreferencesUtil(getActivity());
        mRawFileUrl = AwUrlManager.getGcjsUrl() + GcjsUrlConstant.GET_RAW_FILE;
//        mRawFileUrl = "http://106.116.160.201:7050/" + GcjsPuUrlConstant.GET_RAW_FILE;
        mPreferencesUtil.setString(GcjsUrlConstant.GET_RAW_FILE, mRawFileUrl);
    }


    @Override
    protected BaseViewListAdapter<ZcfgBean> initAdapter() {
        return new ZcfgAdapter(getActivity());
    }

    @Override
    protected Observable<ApiResult<List<ZcfgBean>>> loadDatas(int page, int rows, Map filterParams) {

        return Observable.create((ObservableOnSubscribe<ApiResult<List<ZcfgBean>>>) emitter -> {
            ApiResult<List<ZcfgBean>> listApiResult = new ApiResult<>();
            List<ZcfgBean> zcfgBeans = new ArrayList<>();
            ZcfgBean zcfgBean = new ZcfgBean(getString(R.string.menu_rawFile), mRawFileUrl);
            zcfgBeans.add(zcfgBean);
            listApiResult.setData(zcfgBeans);
            emitter.onNext(listApiResult);
        });

//        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.GET_INFORMPROMISE, null)
//                .map(s -> {
//                    if (StringUtils.isJson(s)) {
//                        JSONObject jsonObject = new JSONObject(s);
//                        boolean success = jsonObject.getBoolean("success");
//                        if (success) {
//                            JSONObject contentObject = jsonObject.getJSONObject("content");
//                            String jsonString = contentObject.getJSONObject(mOrgCode).toString();
//                            Map<String, String> stringMap = new Gson().fromJson(jsonString, new TypeToken<Map<String, String>>() {
//                            }.getType());
//                            List<ZcfgBean> zcfgBeans = new ArrayList<>();
//                            for (String key : stringMap.keySet()) {
//                                ZcfgBean zcfgBean = new ZcfgBean(stringMap.get(key), AwUrlManager.serverUrl() + "cp-rest/" + key);
//                                zcfgBeans.add(zcfgBean);
//                            }
//
//                            ApiResult<List<ZcfgBean>> listApiResult = new ApiResult<>();
//                            listApiResult.setData(zcfgBeans);
//
//                            return listApiResult;
//                        }
//                    }
//                    return new ApiResult<>();
//                });

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
