package com.augurit.agmobile.agwater5.gcjs_public.common;

import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.dict.util.DictBuilder;

import java.util.ArrayList;

public class LocalDictUtil {
    /**
     * 领取模式
     */
    public static ArrayList<DictItem> getReceiveType(){
        return new DictBuilder()
                .item("邮政快递","0")
                .item("窗口取证","1")
                .build();
    }

    /**
     * 快递类型
     */
    public static ArrayList<DictItem> getSendType(){
        return new DictBuilder()
                .item("上门取件","1")
                .item("普通快递","2")
                .build();
    }
}
