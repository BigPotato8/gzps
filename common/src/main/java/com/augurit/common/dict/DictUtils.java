package com.augurit.common.dict;

import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.dict.util.DictBuilder;

import java.util.ArrayList;

/**
 * @author 创建人 ：xuciluan
 * @version 1.0
 * @package 包名 ：com.augurit.agmobile.agwater5.psh.sewerage.source
 * @createTime 创建时间 ：2018-12-04
 * @modifyBy 修改人 ：xuciluan
 * @modifyTime 修改时间 ：2018-12-04
 * @modifyMemo 修改备注：
 */

public class DictUtils {

        public static ArrayList<DictItem> getDischargerType() {
            return new DictBuilder()
//             .item("全部")
//                    .childItem("全部")
             .item("生活排污类")
//                    .childItem("全部")
                    .childItem("机关企事业单位")
                    .childItem("学校")
                    .childItem("商场")
                    .childItem("居民住宅")
                    .childItem("其他")
             .item("餐饮排污类")
//                    .childItem("全部")
                    .childItem("餐饮店")
                    .childItem("农家乐")
                    .childItem("酒店")
                    .childItem("大型食堂")
                    .childItem("其他")
             .item("沉淀物排污类")
//                    .childItem("全部")
                    .childItem("洗车、修车档")
                    .childItem("沙场")
                    .childItem("建筑工地")
                    .childItem("养殖场")
                    .childItem("农贸市场")
                    .childItem("其他")
              .item("有毒有害排污类")
//                    .childItem("全部")
                    .childItem("化工")
                    .childItem("印染")
                    .childItem("电镀、小五金")
                    .childItem("医疗")
                    .childItem("洗涤")
                    .childItem("食品加工")
                    .childItem("制衣、皮革加工")
                    .childItem("其他")
                            .build();
        }

    public static ArrayList<DictItem> getWorkState() {
        return new DictBuilder()
                .item("开启")
                .item("关闭")
                .item("故障")
                .item("其他")
                .build();
    }
}
