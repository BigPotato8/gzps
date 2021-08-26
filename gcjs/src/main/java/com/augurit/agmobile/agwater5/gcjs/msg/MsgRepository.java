package com.augurit.agmobile.agwater5.gcjs.msg;

import com.augurit.agmobile.agwater5.common.common.AwUrlManager;
import com.augurit.agmobile.agwater5.common.http.HttpUtil;
import com.augurit.agmobile.agwater5.common.utils.StringUtils;
import com.augurit.agmobile.agwater5.gcjs.common.GcjsUrlConstant;
import com.augurit.agmobile.agwater5.gcjs.msg.model.MsgBean;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * 个人消息数据仓库
 *
 * @author 创建人 ：xiejiexin
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.gcjs.msg
 * @createTime 创建时间 ：2019-06-11
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class MsgRepository {

    public Observable<ApiResult<List<MsgBean.RowsBean>>> getMsgs(int page, int row) {
        Map<String, String> param = new HashMap<>();
        param.put("pageNum", page + "");
        param.put("pageSize", row + "");
//        return HttpUtil.getInstance("http://192.168.30.125:8087/").post(GcjsUrlConstant.PERSON_MSG_LIST, param)
        return HttpUtil.getInstance(AwUrlManager.getGcjsUrl()).post(GcjsUrlConstant.PERSON_MSG_LIST, param)
                .map(s -> {
                    if (StringUtils.isJson(s)) {
                        ApiResult<MsgBean> apiResult = new Gson().fromJson(s, new TypeToken<ApiResult<MsgBean>>() {
                        }.getType());
                        MsgBean data = apiResult.getData();
                        if (data != null) {
                            List<MsgBean.RowsBean> rows = data.getRows();
                            ApiResult<List<MsgBean.RowsBean>> listApiResult = new ApiResult<>();
                            listApiResult.setSuccess(true);
                            listApiResult.setData(rows);
                            EventBus.getDefault().post(data.getTotal());
                            return listApiResult;
                        } else {
                            return new ApiResult<>();
                        }
                    } else {
                        return new ApiResult<>();
                    }
                });
    }
}
