package com.augurit.common.dict;

import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.dict.util.DictBuilder;

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
public class DictUtil {



    public static ArrayList<DictItem> getArea() {
        return new DictBuilder()
                .item("天河区")
                .item("越秀区")
                .item("白云区")
                .item("荔湾区")
                .item("番禺区")
                .item("花都区")
                .build();
    }


    /**
     * 签收状态
     * @return
     */
    public static List<DictItem> getSignState() {
        return new DictBuilder()
                .item("未签收","0")
                .item("已签收","1")
                .build();
    }


    /**
     * 审批类型
     * @return
     */
    public static List<DictItem> getApplyType() {
        return new DictBuilder()
                .item("并联","0")
                .item("单项","1")
                .build();
    }


    /**
     * 审批时限
     * @return
     */
    public static List<DictItem> getInstState() {
        return new DictBuilder()
                .item("预警","2")
                .item("逾期","3")
                .build();
    }


    /**
     * 所在节点
     * @return
     */
    public static List<DictItem> getTaskName(){
        return new DictBuilder()
                .item("预受理")
                .item("行政服务中心汇总初审意见")
                .item("窗口收件")
                .item("办结")
                .build();
    }

    /**
     * 审批阶段
     * @return
     */
    public static List<DictItem> getApprovalStage(){
        return new DictBuilder()
                .item("立项用地规划许可阶段", "1")
                .item("工程建设许可阶段", "2")
                .item("施工许可阶段", "3")
                .item("竣工验收阶段", "4")
                .build();
    }


}
