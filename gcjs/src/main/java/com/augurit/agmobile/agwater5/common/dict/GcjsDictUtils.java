package com.augurit.agmobile.agwater5.common.dict;

import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.dict.util.DictBuilder;
import com.augurit.agmobile.common.view.combineview.IDictItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.flood.commom
 * @createTime 创建时间 ：2019-04-28
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：2019-04-28
 * @modifyMemo 修改备注：
 */
public class GcjsDictUtils {

    /**
     * 当前节点任务办理结论：0不通过 1通过 2不涉及
     * @return
     */
    public static ArrayList<DictItem> getResultDictItems() {
        return new DictBuilder()
                .item("不通过","0")
                .item("通过","1")
                .item("不涉及","2")
                .build();
    }

    /**
     * 当前节点任务办理：0不同意 1同意 2不涉及
     * @return
     */
    public static List<IDictItem> getAdviceDictItems() {
        List<IDictItem> list = new ArrayList<>();
        DictItem dictItem1 = new DictItem();
        dictItem1.setValue("1");
        dictItem1.setLabel("同意");
        list.add(dictItem1);

        DictItem dictItem0 = new DictItem();
        dictItem0.setValue("0");
        dictItem0.setLabel("不同意");
        list.add(dictItem0);

        DictItem dictItem2 = new DictItem();
        dictItem2.setValue("2");
        dictItem2.setLabel("不涉及");
        list.add(dictItem2);

        return list;
    }
}
