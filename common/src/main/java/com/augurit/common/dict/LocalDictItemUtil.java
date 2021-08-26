package com.augurit.common.dict;

import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.dict.util.DictBuilder;

import java.util.ArrayList;

/**
 * @description 市政的本地类字典项
 * @date: $date$ $time$
 * @author: xieruibin
 */
public class LocalDictItemUtil {
    /**
     * 获取有无列表
     * @return
     */
    public static ArrayList<DictItem> getYesOrNot(){
        DictBuilder dictBuilder = new DictBuilder();
        dictBuilder.item("无","0");
        dictBuilder.item("有","1");
        return dictBuilder.build();
    }
    /**
     * 数据挖掘占用，施工状态
     * @return
     */
    public static ArrayList<DictItem> getMiningState(){
        DictBuilder dictBuilder = new DictBuilder();
        dictBuilder.item("施工中","0");
        dictBuilder.item("已完工","1");
        dictBuilder.item("未修复","2");
        //dictBuilder.item("已修复","3");
        return dictBuilder.build();
    }
    /**
     * 获取处理状态
     * @return
     */
    public static ArrayList<DictItem> getDisposeState(){
        DictBuilder dictBuilder = new DictBuilder();
        dictBuilder.item("未处理","0");
        dictBuilder.item("已处理","1");
        return dictBuilder.build();
    }
    /**
     * 获取处理方式
     * @return
     */
    public static ArrayList<DictItem> getAction(){
        DictBuilder dictBuilder = new DictBuilder();
        dictBuilder.item("增防","0");
        dictBuilder.item("撤防","1");
        dictBuilder.item("布防","2");
        dictBuilder.item("普通上报","3");
        return dictBuilder.build();
    }
}
