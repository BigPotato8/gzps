package com.augurit.agmobile.agwater5.im.keyword;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.augurit.agmobile.common.im.common.entity.KeywordModel;
import com.augurit.agmobile.common.im.common.source.IKeywordClickUtil;

/**
 * @author 创建人 ：yaowang
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.im.keyword
 * @createTime 创建时间 ：2019/7/24
 * @modifyBy 修改人 ：
 * @modifyTime 修改时间 ：
 * @modifyMemo 修改备注：
 */
public class MyKeywordClickUtil implements IKeywordClickUtil {

    @Override
    public void performKeywordClick(Context context, KeywordModel keywordModel) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(context,
                keywordModel.getActivityClass()));
        intent.putExtra("state", 1);
        intent.putExtra("dataJson", keywordModel.getData());
        context.startActivity(intent);
    }
}
