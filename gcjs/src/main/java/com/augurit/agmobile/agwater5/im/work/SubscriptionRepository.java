package com.augurit.agmobile.agwater5.im.work;

import android.content.Context;

import com.augurit.agmobile.agwater5.im.work.model.PubItem;
import com.augurit.agmobile.agwater5.im.work.model.PubItemChild;
import com.augurit.agmobile.common.im.timchat.model.PubMenuItem;
import com.augurit.agmobile.common.lib.file.FileUtils;
import com.augurit.agmobile.common.lib.net.model.ApiResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.sample.im
 * @createTime 创建时间 ：2019/8/2
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class SubscriptionRepository {

    public Observable<ApiResult<List<PubItem>>> getSubscriptions(Context context, int page, int row) {
        String data = FileUtils.readAssetsFile(context, "subscription_test_data.json");
        Gson gson = new Gson();
        ArrayList<PubItem> pubItems = gson.fromJson(data, new TypeToken<List<PubItem<PubItemChild>>>() {}.getType());
        ApiResult<List<PubItem>> apiResult = new ApiResult<>();
        apiResult.setCode(0);
        apiResult.setSuccess(true);
        apiResult.setData(pubItems);
        return Observable.just(apiResult);
    }

    public Observable<ApiResult<List<PubMenuItem>>> getPubMenuItems(Context context){
        String data = FileUtils.readAssetsFile(context, "pub_menu_items.json");
        Gson gson = new Gson();
        ArrayList<PubMenuItem> pubItems = gson.fromJson(data, new TypeToken<List<PubMenuItem>>() {}.getType());
        ApiResult<List<PubMenuItem>> apiResult = new ApiResult<>();
        apiResult.setCode(0);
        apiResult.setSuccess(true);
        apiResult.setData(pubItems);
        return Observable.just(apiResult);
    }

}
