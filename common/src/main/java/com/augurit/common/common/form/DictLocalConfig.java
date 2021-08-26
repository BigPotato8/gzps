package com.augurit.common.common.form;
import com.augurit.agmobile.busi.bpm.dict.model.DictItem;
import com.augurit.agmobile.busi.bpm.dict.util.DictBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * com.augurit.agmobile.yjfh.form.model
 * Created by sdb on 2018/10/11  11:15.
 * Desc：
 */

public class DictLocalConfig {
    public static ArrayList<DictItem> getYJFacilityTypeDicts() {
        return new DictBuilder()
                .item("泵站")
                .childItem("竹排冲泵站1")
                .childItem("竹排冲泵站2")
                .childItem("竹排冲泵站3")
                .childItem("竹排冲泵站0")
                .childItem("竹排冲泵站5")
                .childItem("竹排冲泵站6")
                .childItem("竹排冲泵站7")
                .item("防洪闸")
                .childItem("防洪闸1")
                .childItem("防洪闸2")
                .childItem("防洪闸3")
                .childItem("防洪闸4")
                .childItem("防洪闸5")
                .childItem("防洪闸6")
                .childItem("防洪闸7")
                .item("交通闸")
                .childItem("交通闸1")
                .childItem("交通闸2")
                .childItem("交通闸3")
                .childItem("交通闸4")
                .childItem("交通闸5")
                .childItem("交通闸6")
                .item("穿提涵管")
                .childItem("穿提涵管1")
                .childItem("穿提涵管2")
                .childItem("穿提涵管3")
                .childItem("穿提涵管4")
                .childItem("穿提涵管5")
                .build();
    }

    public static ArrayList<DictItem> getYJOrgNameDicts() {
        return new DictBuilder()
                .item("竹排冲泵站")
                .item("竹排冲泵站1")
                .item("竹排冲泵站2")
                .item("竹排冲泵站3")
                .item("竹排冲泵站4")
                .item("竹排冲泵站5")
                .item("竹排冲泵站6")
                .item("竹排冲泵站7")
                .build();
    }


    public static ArrayList<DictItem> getSewageFacilityState() {
        return new DictBuilder()
                .item("运行", "0")
                .item("废弃", "1")
                .build();
    }

    public static ArrayList<DictItem> getSewageCardCode() {
        return new DictBuilder()
                .item("有").childItem("")
                .item("无")
                .build();
    }

    public static ArrayList<DictItem> getSewageAccessObj() {
        return new DictBuilder()
                .item("设施点")
                .item("市政管网")
                .item("其他")
                .build();
    }

    public static ArrayList<DictItem> getSewageWellClassify() {
        return new DictBuilder()
                .item("市政接驳井")
                .item("提升泵井")
                .item("其他井")
                .build();
    }

    public static ArrayList<DictItem> getProcessCraft() {
        return new DictBuilder()
                .item("厌氧池+人工湿地")
                .item("厌氧池")
                .item("MBR")
                .item("A/O工艺")
                .item("A2/O工艺")
                .item("生物接触氧化法")
                .item("生物转盘")
                .item("厌氧池+生态塘，生态沟")
                .item("其它")
                .build();
    }

    public static ArrayList<DictItem> getOutWaterStandard() {
        return new DictBuilder()
                .item("《农田灌溉水质标准》（GB-5084-2005）排放标准")
                .item("《城镇污水处理厂污染物排放标准》（GB18918-2002）二级排放标准")
                .item("《城镇污水处理厂污染物排放标准》（GB18918-2002）一级B排放标准")
                .build();
    }

    public static ArrayList<DictItem> getPowertype() {
        return new DictBuilder()
                .item("有动力", "0")
                .item("微动力", "1")
                .item("无动力", "2")
                .build();
    }

    public static ArrayList<DictItem> getFacilityState() {
        return new DictBuilder()
                .item("运行", "0")
                .item("废弃", "1")
                .item("维修", "2")
                .build();
    }

    public static ArrayList<DictItem> getYJIsNormalDicts() {
        return new DictBuilder()
                .item("正常")
                .item("不正常")
                .build();
    }

//    public static ArrayList<DictItem> getJJCDDicts() {
//        return new DictBuilder()
//                .item("一般")
//                .item("紧急")
//                .item("较紧急")
//                .build();
//    }

    public static List<DictItem> getYJFacilityStatus() {
        return new DictBuilder()
                .item("未检查", "0")
                .item("正常", "1")
                .item("异常", "2")
                .build();
    }

    public static List<DictItem> getOrgType() {
        return new DictBuilder()
                .item("竹排冲管理所")
                .item("大坑口管理所")
                .item("心圩江管理所")
                .item("亭子管理所")
                .item("凤凰江管理所")
                .build();

    }

    public static List<DictItem> getProblemType() {
        return new DictBuilder()
                .item("违法建筑")
                .item("堤防外观")
                .item("堤身")
                .item("附堤设施")
                .item("河岸(护坡)")
                .build();

    }

    public static List<DictItem> getCheckDate() {
        return new DictBuilder()
                .item("汛前")
                .item("汛后")
                .item("洪峰期间")
                .build();

    }

    public static List<DictItem> getInspectType() {
        return new DictBuilder()
                .item("正常检查")
                .item("定期检查")
                .item("土建检查")
                .build();

    }

    public static List<DictItem> getSolveType() {
        return new DictBuilder()
                .item("自行处理", "true")
                .item("下达任务", "false")
                .item("上报问题", "false")
                .build();

    }

    public static List<DictItem> getArea() {
        return new DictBuilder()
                .item("越秀区")
                .item("荔湾区")
                .item("从化区")
                .item("增城区")
                .item("南沙区")
                .item("花都区")
                .item("番禺区")
                .item("黄浦区")
                .item("天河区")
                .item("白云区")
                .item("海珠区")
                .build();

    }

    public static List<DictItem> getPshProblemType() {
        return new DictBuilder()
                .item("错漏混接", "A204001")
                .item("雨污未分流", "A204002")
                .item("直排", "A204003")
                .item("无预处理设施", "A204004")
                .item("设施故障及停用", "A204005")
                .item("未及时维护", "A204006")
                .item("超标排放", "A204007")
                .build();

    }
    public static List<DictItem> getHandle() {//办理意见
        return new DictBuilder()
                .item("受理", "1")
                .item("不予受理", "0")
                .item("不涉及", "2")
                .build();

    }
    public static List<DictItem> getHandle1() {//办理意见
        return new DictBuilder()
                .item("审查决定通过", "1")
                .item("审查决定不通过", "0")
                .build();

    }
    public static List<DictItem> getHandle2() {//办理意见
        return new DictBuilder()
                .item("受理", "1")
                .item("不予受理", "0")
                .build();

    }
    public static List<DictItem> getHandle3() {//办理意见
        return new DictBuilder()
                .item("同意", "1")
                .item("不同意", "0")
                .build();

    }

    public static List<DictItem> notAcceptReason() {//不予办理理由
        return new DictBuilder()
                .item("申请单位不符合申请资格","")
                .item("补正材料后，申请材料仍不符合要求", "")
                .item("申请不属于项目受理范围,转其他环节申请", "")
                .item("经告知补正材料后，未及时提交申请材料", "")
//                .item("其他","")
                .build();

    }

    public static List<DictItem> getFacility() {
        return new DictBuilder()
                .item("井")
                .childItem("井盖打不开")
                .childItem("水深超过一米")
                .childItem("有垃圾")
                .childItem("井身损坏")
                .childItem("井盖打不开1")
                .childItem("井盖打不开2")
                .childItem("井盖打不开3")
                .item("管道")
                .childItem("进水管有破损1")
                .childItem("进水管有破损2")
                .childItem("进水管有破损3")
                .childItem("进水管有破损4")
                .childItem("进水管有破损5")
                .childItem("进水管有破损6")
                .item("格栅池")
                .childItem("老化")
                .childItem("安装不规范1")
                .childItem("安装不规范2")
                .childItem("安装不规范3")
                .childItem("安装不规范4")
                .childItem("安装不规范5")
                .childItem("安装不规范6")
                .childItem("安装不规范7")
                .item("厌氧水解池")
                .childItem("有淤泥、垃圾1")
                .childItem("有淤泥、垃圾2")
                .childItem("有淤泥、垃圾3")
                .childItem("有淤泥、垃圾4")
                .childItem("有淤泥、垃圾5")
                .childItem("有淤泥、垃圾6")
                .item("人工湿地")
                .childItem("有淤泥、垃圾1")
                .childItem("有淤泥、垃圾2")
                .childItem("有淤泥、垃圾3")
                .childItem("有淤泥、垃圾4")
                .childItem("有淤泥、垃圾5")
                .childItem("有淤泥、垃圾6")
                .item("出水口")
                .childItem("有淤泥、垃圾1")
                .childItem("有淤泥、垃圾2")
                .childItem("有淤泥、垃圾3")
                .childItem("有淤泥、垃圾4")
                .childItem("有淤泥、垃圾5")
                .childItem("有淤泥、垃圾6")
                .item("生态塘")
                .childItem("有淤泥、垃圾1")
                .childItem("有淤泥、垃圾2")
                .childItem("有淤泥、垃圾3")
                .childItem("有淤泥、垃圾4")
                .childItem("有淤泥、垃圾5")
                .childItem("有淤泥、垃圾6")
                .item("水泵机电设备")
                .childItem("有淤泥、垃圾1")
                .childItem("有淤泥、垃圾2")
                .childItem("有淤泥、垃圾3")
                .childItem("有淤泥、垃圾4")
                .childItem("有淤泥、垃圾5")
                .childItem("有淤泥、垃圾6")
                .build();

    }

    public static List<DictItem> getNW_Problem_JJCD() {
        return new DictBuilder()
                .item("一般", "0")
                .item("较紧急", "1")
                .item("紧急", "2")
                .build();
    }

    public static List<DictItem> getNW_Problem_isbyself() {
        return new DictBuilder()
                .item("自行处理", "0")
                .item("上报问题", "2")
                .build();
    }

    public static List<DictItem> getSewageAddNewFacilityType() {
        return new DictBuilder()
                .item("井")
                .item("设施点")
                .build();
    }

    public static List<DictItem> getWellFeature() {
        return new DictBuilder()
                .item("方形")
                .item("圆形")
                .build();
    }

    public static List<DictItem> getRainType() {
        return new DictBuilder()
                .item("雨水")
                .item("污水")
                .item("雨污合流")
                .build();
    }

    public static List<DictItem> getCoverMaterial() {
        return new DictBuilder()
                .item("铸铁")
                .item("钢")
                .item("砼")
                .item("塑")
                .item("玻璃钢")
                .item("大理石")
                .build();
    }

    public static List<DictItem> getAddFacilityType() {
        return new DictBuilder()
                .item("格栅池")
                .item("集水井")
                .item("厌氧水解池")
                .item("人工湿地")
                .item("生态塘、生态沟渠")
                .item("接触氧化、膜生物处理等生化处理系统")
                .item("水泵、鼓风系统等机电设备")
                .item("出水口")
                .item("管道")
                .item("其他")
                .build();
    }

    /**
     * 预警级别
     * @return
     */
    public static List<DictItem> getWarningLevel() {
        return new DictBuilder()
                .item("红色警报")
                .item("橙色警报")
                .item("黄色警报")
                .item("蓝色警报")
                .build();
    }

    /**
     * 预警指令
     * @return
     */
    public static List<DictItem> getWarningInstruct() {
        return new DictBuilder()
                .item("启动督办通知")
                .item("提交事中报告")
                .item("结束督办通知")
                .item("抢险督办通知")
                .item("启动响应预警")
                .item("升级应急响应预警")
                .item("降级应急响应预警")
                .item("解除预警")
                .build();
    }

    /**
     * 涝情类型
     * @return
     */
    public static List<DictItem> getWaterLoggerType() {
        return new DictBuilder()
                .item("河流洪水")
                .item("湖泊洪水")
                .item("风暴洪水")
                .build();
    }

    /**
     * 预警指令
     * @return
     */
    public static List<DictItem> getWarningType() {
        return new DictBuilder()
                .item("气象报警启动")
                .item("气象报警升级")
                .item("气象报警持续")
                .item("未知")
                .build();
    }

    /**
     * 预警状态
     * @return
     */
    public static List<DictItem> getWarningState() {
        return new DictBuilder()
                .item("已启动")
                .item("未启动")
                .item("已关闭")
                .build();
    }

    /**
     * 发布状态
     * @return
     */
    public static List<DictItem> getReleaseStatus() {
        return new DictBuilder()
                .item("已启动")
                .item("未启动")
                .item("已关闭")
                .build();
    }

    /**
     * 发布状态
     * @return
     */
    public static List<DictItem> getSendStatus() {
        return new DictBuilder()
                .item("全部")
                .item("发送成功")
                .item("发送失败")
                .item("未发送")
                .build();
    }

    /**
     * 等级  Ⅰ、Ⅱ、Ⅲ、Ⅳ、Ⅴ、Ⅵ、Ⅶ、..._魔方格
     * @return
     */
    public static List<DictItem> getGrade() {
        return new DictBuilder()
                .item("Ⅳ")
                .item("Ⅲ")
                .item("Ⅱ")
                .item("Ⅰ")
                .build();
    }

    /**
     * 预案等级
     * @return
     */
    public static List<DictItem> getEmergencyLevel() {
        return new DictBuilder()
                .item("一级应急预案")
                .item("二级应急预案")
                .item("三级应急预案")
                .item("四级应急预案")
                .build();
    }

    /**
     * 预案类型
     * @return
     */
    public static List<DictItem> getEmergencyType() {
        return new DictBuilder()
                .item("防涝应急预案")
                .item("防旱应急预案")
                .build();
    }

    /**
     * 督办类型
     * @return
     */
    public static List<DictItem> getDBType() {
        return new DictBuilder()
                .item("启动应急响应")
                .item("提交事中报告")
                .item("结束应急响应")
                .build();
    }

    /**
     * 通知类型
     * @return
     */
    public static List<DictItem> getTZType() {
        return new DictBuilder()
                .item("督办通知")
                .item("短信通知")
                .build();
    }
}
