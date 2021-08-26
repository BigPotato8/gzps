package com.augurit.agmobile.agwater5.gcjs_public.bszn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.augurit.agmobile.agwater5.R;
import com.augurit.agmobile.agwater5.gcjs_public.common.AwUrlManager;
import com.augurit.agmobile.agwater5.gcjs_public.common.GcjsPuUrlConstant;
import com.augurit.agmobile.agwater5.gcjs_public.common.WatchImageOrPdfActivity;
import com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.MyProjectBean;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListAdapter;
import com.augurit.agmobile.busi.bpm.viewlist.view.BaseViewListFragment;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.augurit.common.common.http.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.wyt.searchbox.utils.KeyBoardUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

import static com.augurit.agmobile.agwater5.gcjs_public.personspace.uploadevent.model.ProjectDetailBean.ProjectInfoBean;

/**
 * @author 创建人 ：SDB
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.gzsz.facilityquery
 * @modifyMemo 修改备注：
 */

public class BsznFragment extends BaseViewListFragment <String>{


    private String keyword = "";
    public static BsznFragment newInstance() {
        BsznFragment fragment = new BsznFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onItemClick(View itemView, int position, String data) {
        ArrayList<String> paths = new ArrayList<>();
        paths.add(AwUrlManager.serverUrl()+GcjsPuUrlConstant.PRE2+data.split("###")[0]);
        Intent intent = WatchImageOrPdfActivity.newIntent(getActivity(), paths);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(View itemView, int position, String data) {
        return false;
    }

    @Override
    protected BaseViewListAdapter<String> initAdapter() {
        return new BsznAdapter(getActivity());
    }

    @Override
    protected Observable<ApiResult<List<String>>> loadDatas(int page, int rows, Map filterParams) {
        return HttpUtil.getInstance(AwUrlManager.serverUrl()).get(GcjsPuUrlConstant.GET_BSZN_LIST_FILE).map(
                s -> {
                    List<String> strings = new ArrayList<>();

                    ApiResult listApiResult1 = new Gson().fromJson(s, new TypeToken<ApiResult>() {
                    }.getType());
                    LinkedTreeMap<String,LinkedTreeMap<String,String>> map = (LinkedTreeMap<String, LinkedTreeMap<String, String>>) listApiResult1.getData();
                    LinkedTreeMap<String,String> map1 = map.get("bszn");
                    Iterator it = map1.entrySet().iterator();
                    String key,value;
                    while (it.hasNext()) {
                       // entry的输出结果如key0=value0等
                       Map.Entry entry =(Map.Entry) it.next();
                        key = (String) entry.getKey();
                        value = (String) entry.getValue();
                        strings.add(key+"###"+value);
                      }
                    ApiResult<List<String>>  apiResult= new ApiResult<>();
                    apiResult.setData(strings);
                    apiResult.setMessage(listApiResult1.getMessage());
                    apiResult.setSuccess(listApiResult1.isSuccess());
                    return apiResult;
                }
        );

    }


}
