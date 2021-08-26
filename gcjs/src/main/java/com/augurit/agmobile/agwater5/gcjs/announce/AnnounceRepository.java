package com.augurit.agmobile.agwater5.gcjs.announce;

import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.common.utils.StringUtils;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.model.Announce;

import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 通知公告数据仓库
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjs.announce
 * @createTime 创建时间 ：2019-06-10
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class AnnounceRepository {

    public Observable<ApiResult<List<Announce.RowsBean>>> getAnnounces(int page, int row) {
        Map<String, String> param = new HashMap<>();
        param.put("pageNum", page + "");
        param.put("pageSize", row + "");
//        return HttpUtil.getInstance("http://192.168.30.125:8087/").get(GcjsUrlConstant.ANNOUNCE_LIST, param)
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
                });
    }
    public Observable<String> getHandlingStatistic() {
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).get(GcjsUrlConstant.GET_STATISTICALANALYSIS, null);
//                .map(new Function<String, ApiResult<List<Announce.RowsBean>>>() {
//                    @Override
//                    public ApiResult<List<Announce.RowsBean>> apply(String s) throws Exception {
//                        if (StringUtils.isJson(s)) {
//                            ApiResult<Announce> apiResult = new Gson().fromJson(s, new TypeToken<ApiResult<Announce>>() {
//                            }.getType());
//                            Announce data = apiResult.getData();
//                            if (data != null) {
//                                List<Announce.RowsBean> rows = data.getRows();
//                                ApiResult<List<Announce.RowsBean>> listApiResult = new ApiResult<>();
//                                listApiResult.setSuccess(true);
//                                listApiResult.setData(rows);
//                                return listApiResult;
//                            } else {
//                                return new ApiResult<>();
//                            }
//                        } else {
//                            return new ApiResult<>();
//                        }
//                    }
//                });
    }

}
