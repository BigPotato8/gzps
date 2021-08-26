package com.augurit.agmobile.agwater5.im.keyword;

import android.content.Context;

import com.augurit.agmobile.common.im.common.entity.KeywordModel;
import com.augurit.agmobile.common.im.common.source.IKeywordRepository;
import com.augurit.agmobile.common.lib.file.FileUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.im.keyword
 * @createTime 创建时间 ：2019/7/24
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class MyKeywordRepository implements IKeywordRepository {
    @Override
    public Observable<List<KeywordModel>> getKeywordsAsyn(Context context) {
        return null;
    }

    @Override
    public List<KeywordModel> getKeywords(Context context) {
        String s = FileUtils.readAssetsFile(context, "im_my_keywords.json");
        Gson gson = new Gson();
        return gson.fromJson(s,new TypeToken<List<KeywordModel>>(){}.getType());
    }
}
